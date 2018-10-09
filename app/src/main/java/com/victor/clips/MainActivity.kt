package com.victor.clips

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import com.victor.clips.adapter.HeaderAdapter
import com.victor.clips.adapter.HomeViewPagerAdapter
import com.victor.clips.data.ProgramReq
import com.victor.clips.fragment.*
import com.victor.clips.presenter.ProgramPresenterImpl
import com.victor.clips.util.ExampleDataSet
import com.victor.clips.util.HeaderItemTransformer
import com.victor.clips.util.WebConfig
import com.victor.clips.view.ProgramView
import com.victor.clips.widget.HeaderLayout
import com.victor.clips.widget.HeaderLayoutManager
import com.victor.clips.widget.NavigationToolBarLayout
import com.victor.clips.widget.SimpleSnapHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import kotlin.math.ceil
import kotlin.math.max

class MainActivity : BaseActivity(),ProgramView {
    private val itemCount = 40
    private val dataSet = ExampleDataSet()

    private var isExpanded = true
    private var prevAnchorPosition = 0
    var fragments = ArrayList<Fragment>()

    var programPresenter: ProgramPresenterImpl? = null

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val header = findViewById<NavigationToolBarLayout>(R.id.navigation_toolbar_layout)
        val viewPager = findViewById<ViewPager>(R.id.pager)

        initActionBar()
        initViewPager(header, viewPager)
        initHeader(header, viewPager)
    }

    fun initialize () {
        programPresenter = ProgramPresenterImpl(this);
    }

    fun initData (){
        sendProgramRequest()
    }

    fun sendProgramRequest () {
        programPresenter?.sendRequest(WebConfig.CATEGORY_URL,null,null)
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

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initActionBar() {
        val toolbar = navigation_toolbar_layout.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initViewPager(header: NavigationToolBarLayout, viewPager: ViewPager) {
        fragments.add(LiveFragment())
        fragments.add(HomeFragment())
        fragments.add(TrendingFragment())
        fragments.add(SubcriptionFragment())
        fragments.add(NotificationsFragment())
        fragments.add(LibraryFragment())
//        viewPager.adapter = ViewPagerAdapter(itemCount, dataSet.viewPagerDataSet)
        viewPager.adapter = HomeViewPagerAdapter(supportFragmentManager,this,fragments)

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                header.smoothScrollToPosition(position)
            }
        })
    }

    private fun initHeader(header: NavigationToolBarLayout, viewPager: ViewPager) {
        val titleLeftOffset = resources.getDimensionPixelSize(R.dimen.title_left_offset)
        val lineRightOffset = resources.getDimensionPixelSize(R.dimen.line_right_offset)
        val lineBottomOffset = resources.getDimensionPixelSize(R.dimen.line_bottom_offset)
        val lineTitleOffset = resources.getDimensionPixelSize(R.dimen.line_title_offset)

        val headerOverlay = findViewById<FrameLayout>(R.id.header_overlay)
        header.setItemTransformer(HeaderItemTransformer(headerOverlay,
                titleLeftOffset, lineRightOffset, lineBottomOffset, lineTitleOffset))
        header.setAdapter(HeaderAdapter(itemCount, dataSet.headerDataSet, headerOverlay))

        header.addItemChangeListener(object : HeaderLayoutManager.ItemChangeListener {
            override fun onItemChangeStarted(position: Int) {
                prevAnchorPosition = position
            }

            override fun onItemChanged(position: Int) {
                viewPager.currentItem = position
            }
        })

        header.addItemClickListener(object : HeaderLayoutManager.ItemClickListener {
            override fun onItemClicked(viewHolder: HeaderLayout.ViewHolder) {
                viewPager.currentItem = viewHolder.position
            }
        })

        SimpleSnapHelper().attach(header)
        initDrawerArrow(header)
        initHeaderDecorator(header)
    }

    private fun initDrawerArrow(header: NavigationToolBarLayout) {
        val drawerArrow = DrawerArrowDrawable(this)
        drawerArrow.color = ContextCompat.getColor(this, android.R.color.white)
        drawerArrow.progress = 1f

        header.addHeaderChangeStateListener(object : HeaderLayoutManager.HeaderChangeStateListener() {
            private fun changeIcon(progress: Float) {
                ObjectAnimator.ofFloat(drawerArrow, "progress", progress).start()
                isExpanded = progress == 1f
                if (isExpanded) {
                    prevAnchorPosition = header.getAnchorPos()
                }
            }

            override fun onMiddle() = changeIcon(0f)
            override fun onExpanded() = changeIcon(1f)
        })

        val toolbar = header.toolBar
        toolbar.navigationIcon = drawerArrow
        toolbar.setNavigationOnClickListener {
            if (!isExpanded) {
                return@setNavigationOnClickListener
            }
            val anchorPos = header.getAnchorPos()
            if (anchorPos == HeaderLayout.INVALID_POSITION) {
                return@setNavigationOnClickListener
            }

            if (anchorPos == prevAnchorPosition) {
                header.collapse()
            } else {
                header.smoothScrollToPosition(prevAnchorPosition)
            }
        }
    }

    private fun initHeaderDecorator(header: NavigationToolBarLayout) {
        val decorator = object :
                HeaderLayoutManager.ItemDecoration,
                HeaderLayoutManager.HeaderChangeListener {

            private val dp5 = resources.getDimensionPixelSize(R.dimen.decor_bottom)

            private var bottomOffset = dp5

            override fun onHeaderChanged(lm: HeaderLayoutManager, header: HeaderLayout, headerBottom: Int) {
                val ratio = max(0f, headerBottom.toFloat() / header.height - 0.5f) / 0.5f
                bottomOffset = ceil(dp5 * ratio).toInt()
            }

            override fun getItemOffsets(outRect: Rect, viewHolder: HeaderLayout.ViewHolder) {
                outRect.bottom = bottomOffset
            }
        }

        header.addItemDecoration(decorator)
        header.addHeaderChangeListener(decorator)
    }

    override fun OnProgram(data: Any?, msg: String) {
        if (data != null) {
            var mProgramReq = data!! as ProgramReq
//            liveAdapter!!.clear()
//            liveAdapter!!.add(mLiveReq.categorys)
//            liveAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        programPresenter!!.detachView()
        programPresenter = null
    }

}
