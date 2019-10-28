package com.victor.clips

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import com.victor.clips.data.ProgramReq
import com.victor.clips.presenter.ProgramPresenterImpl
import com.victor.clips.view.ProgramView
import kotlinx.android.synthetic.main.toolbar.*
import android.support.v4.view.ViewCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.view.animation.DecelerateInterpolator
import com.victor.clips.fragment.CategoryFragment
import com.victor.clips.util.*


class MainActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,ProgramView {

    var currentFragment: Fragment? = null
    var actionBarShown = true

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        switchFragment(CategoryFragment())

        setSupportActionBar(toolbar);

        injectViewsAndSetUpToolbar();
        // 左上角 Menu 按钮
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(this)
    }

    fun injectViewsAndSetUpToolbar() {
        ViewCompat.setElevation(appbar_layout, DensityUtil.dip2px(this@MainActivity, 4.0f).toFloat())
    }

     fun switchFragment(fragment: Fragment) {
        if (currentFragment == null || fragment.javaClass.name != currentFragment?.javaClass?.name) {
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
            currentFragment = fragment
        }
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
            R.id.action_share -> {
                SnackbarUtil.ShortSnackbar(drawer,"share").show()
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
                SnackbarUtil.ShortSnackbar(drawer,"about").show()
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fab -> {
                SnackbarUtil.ShortSnackbar(drawer,"github").show()
            }
        }
    }

    override fun OnProgram(data: Any?, msg: String) {
        if (data != null) {
            var mProgramReq = data!! as ProgramReq
//            liveAdapter!!.clear()
//            liveAdapter!!.add(mLiveReq.categorys)
//            liveAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode) {
            KeyEvent.KEYCODE_BACK ->{
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
