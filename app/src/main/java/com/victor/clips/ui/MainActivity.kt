package com.victor.clips.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.toolbar.*
import android.support.v4.view.ViewCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.view.animation.DecelerateInterpolator
import com.victor.clips.R
import com.victor.clips.ui.fragment.*
import com.victor.clips.util.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.victor.funny.util.ToastUtils


class MainActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    var actionbarScrollPoint: Float = 0f
    var summaryScrolled: Float = 0f
    var maxScroll: Float = 0f

    var currentFragment: Fragment? = null
    var actionBarShown = true
    var isInitialize = true
    var fontStyle: Typeface? = null

    companion object {
        fun  intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    override fun onResume() {
        super.onResume()
        switchFragment(currentFragment,getCurrentFrag())
        initData()
    }

    fun initialize () {
        setSupportActionBar(toolbar);

        injectViewsAndSetUpToolbar();
        // 左上角 Menu 按钮
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this);
        mFabMain.setOnClickListener(this)

        SharePreferencesUtil.putInt(this,Constant.CATEGORY_POSITION_KEY,0)

        fontStyle = Typeface.createFromAsset(assets, "fonts/ZuoAnLianRen.ttf");
        navigationView.getHeaderView(0).mTvOurOriginalIntention.typeface = fontStyle

    }

    fun injectViewsAndSetUpToolbar() {
        ViewCompat.setElevation(appbar_layout, DensityUtil.dip2px(this@MainActivity, 4.0f).toFloat())
    }

     fun switchFragment(fromFragment: Fragment?,toFragment: Fragment?) {
         if (currentFragment?.javaClass?.name == toFragment?.javaClass?.name) {
             return
         }

         currentFragment = toFragment

         val ft = supportFragmentManager.beginTransaction()
         ft.setCustomAnimations(R.anim.anim_fragment_enter, R.anim.anim_fragment_exit)
         if (toFragment?.isAdded()!!) {
             ft.show(toFragment)
         } else {
             ft.add(R.id.container, toFragment)
         }
         if (fromFragment != null) {
             ft.hide(fromFragment)
         }
         ft.commitAllowingStateLoss()
    }

    fun showActionbar(show: Boolean, animate: Boolean) {
        if (animate) {
            autoShowOrHideActionBar(show)
        } else {
            if (show) {
                supportActionBar!!.show()
            } else {
                supportActionBar!!.hide()
            }
        }
    }

    fun setToolbarAlpha(alpha: Float) {
        appbar_layout.getBackground().setAlpha((alpha * 255).toInt())
    }

    protected fun autoShowOrHideActionBar(show: Boolean) {
        if (show == actionBarShown) {
            return
        }
        actionBarShown = show
        onActionBarAutoShowOrHide(show)
    }

    protected fun onActionBarAutoShowOrHide(shown: Boolean) {
        val view = appbar_layout
        if (shown) {
            view.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setDuration(Constant.HEADER_HIDE_ANIM_DURATION.toLong())
                    .setInterpolator(DecelerateInterpolator())
        } else {
            view.animate()
                    .translationY((-view.getBottom()).toFloat())
                    .alpha(0f)
                    .setDuration(Constant.HEADER_HIDE_ANIM_DURATION.toLong())
                    .setInterpolator(DecelerateInterpolator())
        }
    }


    fun initData (){
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when(item.itemId) {
            R.id.action_search -> {
                SearchActivity.intentStart(this)
            }
            R.id.action_share -> {
                var intentshare = Intent(Intent.ACTION_SEND);
                intentshare.setType(Constant.SHARE_TYPE)
                        .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share))
                        .putExtra(Intent.EXTRA_TEXT,getString(R.string.share_app));
                Intent.createChooser(intentshare, getString(R.string.share));
                startActivity(intentshare);
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_auto_update -> {
                SnackbarUtil.ShortSnackbar(drawer,getString(R.string.auto_update_opened)).show();
            }
            R.id.nav_clear -> {
                DataCleanManager.cleanApplicationData(this);
                SnackbarUtil.ShortSnackbar(drawer,getString(R.string.all_cache_cleaned)).show()
            }
            R.id.nav_copyright -> {
                SnackbarUtil.ShortSnackbar(drawer,getString(R.string.copyright_content)).show()
            }
            R.id.nav_theme -> {
                ThemeSettingActivity.intentStart(this)
            }
            R.id.nav_about -> {
                AboutActivity.intentStart(this)
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mFabMain -> {
                CategoryActivity.intentStart(this, mFabMain, getString(R.string.transition_fab_icon))
            }
        }
    }


    fun getCurrentFrag ():Fragment {
        if (isInitialize) {
            isInitialize = false
            return VideoCategoryFragment.newInstance()
        }
        var position = SharePreferencesUtil.getInt(this,Constant.CATEGORY_POSITION_KEY,-1)
        when(position) {
            0 -> {
                return VideoCategoryFragment.newInstance()
            }
            1 -> {
                return WeeklyRankingFragment.newInstance()
            }
            2 -> {
                return MonthlyRankingFragment.newInstance()
            }
            3 -> {
                return TotalRankingFragment.newInstance()
            }
            4 -> {
                return FollowFragment.newInstance()
            }
        }
        return VideoCategoryFragment.newInstance()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            KeyEvent.KEYCODE_BACK ->{
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (currentFragment !is VideoCategoryFragment) {
                    SharePreferencesUtil.putInt(this,Constant.CATEGORY_POSITION_KEY,0)
                    switchFragment(currentFragment,VideoCategoryFragment.newInstance())
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    internal inner class OnScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //        val lastVisibleItemPosition = gridLayoutManager?.findLastVisibleItemPosition()

            if (dy > actionbarScrollPoint) {
                showActionbar(false, true)
            }

            if (dy < actionbarScrollPoint * -1) {
                showActionbar(true, true)
            }

            summaryScrolled += dy
            mIvBubbles.setTranslationY(-0.5f * summaryScrolled)
            var alpha = summaryScrolled / maxScroll
            alpha = Math.min(1.0f, alpha)

            setToolbarAlpha(alpha)
            //change background color on scroll
            val color = Math.max(Constant.BG_COLOR_MIN, Constant.BG_COLOR_MAX - summaryScrolled * 0.05f)
            mRlMainParent.setBackgroundColor(Color.argb(255, color.toInt(), color.toInt(), color.toInt()))
        }
    }

}
