package com.victor.clips

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.transition.ChangeBounds
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.MenuItem
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import com.victor.clips.adapter.HomeViewPagerAdapter
import com.victor.clips.fragment.*
import com.victor.clips.interfaces.GestureListener
import com.victor.clips.util.Loger
import com.victor.clips.widget.VideoTouchHandler
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.home_content_main.*
import java.util.ArrayList

class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, GestureListener {

    /*
   *  Setting up guideline parameters to change the
   *  guideline percent value as per user touch event
   */
    private lateinit var paramsGlHorizontal: ConstraintLayout.LayoutParams
    private lateinit var paramsGlVertical: ConstraintLayout.LayoutParams
    private lateinit var paramsGlBottom: ConstraintLayout.LayoutParams
    private lateinit var paramsGlMarginEnd: ConstraintLayout.LayoutParams

    private val constraintSet = ConstraintSet()

    private lateinit var animationTouchListener: VideoTouchHandler

    override fun getLayoutResource(): Int {
        return R.layout.activity_home
    }


    var fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun initialize () {
        setSupportActionBar(toolbar)

        //禁止点击放大移动位置
        bottomNavigation.disableShiftMode(true)

        bottomNavigation.setOnNavigationItemSelectedListener(this)

//        fragments.add(LiveFragment())
        fragments.add(HomeFragment())
        fragments.add(TrendingFragment())
        fragments.add(SubcriptionFragment())
        fragments.add(NotificationsFragment())
        fragments.add(LibraryFragment())
        homePager.adapter = HomeViewPagerAdapter(supportFragmentManager,this,fragments)

        paramsGlHorizontal = guidelineHorizontal.layoutParams as ConstraintLayout.LayoutParams
        paramsGlVertical = guidelineVertical.layoutParams as ConstraintLayout.LayoutParams
        paramsGlBottom = guidelineBottom.layoutParams as ConstraintLayout.LayoutParams
        paramsGlMarginEnd = guidelineMarginEnd.layoutParams as ConstraintLayout.LayoutParams

        animationTouchListener = VideoTouchHandler(this, this)
        frmVideoContainer.setOnTouchListener(animationTouchListener)
        animationTouchListener.show()
        animationTouchListener.isExpanded = true
    }

    private fun fragmentSwitch(containRedId:Int,toFragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()

        ft.replace(containRedId, toFragment)
        ft.commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        if (animationTouchListener.isExpanded) {
            animationTouchListener.isExpanded = false
//            if (!isPortrait()) {
//                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.mFabCategory -> {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
            R.id.videoPlayer -> {
                if (!animationTouchListener.isExpanded) {
                    animationTouchListener.isExpanded = true
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                homePager.setCurrentItem(0)
                return true
            }
            R.id.navigation_library -> {
                homePager.setCurrentItem(4)
                return true
            }
            R.id.navigation_notifications -> {
                homePager.setCurrentItem(3)
                return true
            }

            R.id.navigation_trending -> {
                homePager.setCurrentItem(1)
                return true
            }

            R.id.navigation_subscription -> {
                homePager.setCurrentItem(2)
                return true
            }
        }

        return false
    }


    override fun onScale(percentage: Float) {
        scaleVideo(percentage)
    }

    override fun onSwipe(percentage: Float) {
        swipeVideo(percentage)
    }

    override fun onExpand(isExpanded: Boolean) {
        setViewExpanded(isExpanded)
    }

    override fun onDismiss(view: View) {
        dismiss()
    }

    /**
     * Scale the video as per given percentage of user scrolls
     * in up/down direction from current position
     */
    private fun scaleVideo(percentScrollUp: Float) {

        //Prevent guidelines to go out of screen bound
        val percentVerticalMoved = Math.max(0F, Math.min(VideoTouchHandler.MIN_VERTICAL_LIMIT, percentScrollUp))
        val movedPercent = percentVerticalMoved / VideoTouchHandler.MIN_VERTICAL_LIMIT
        val percentHorizontalMoved = VideoTouchHandler.MIN_HORIZONTAL_LIMIT * movedPercent
        val percentBottomMoved = 1F - movedPercent * (1F - VideoTouchHandler.MIN_BOTTOM_LIMIT)
        Loger.e(TAG,"Bottom : $percentBottomMoved")
        val percentMarginMoved = 1F - movedPercent * (1F - VideoTouchHandler.MIN_MARGIN_END_LIMIT)

        paramsGlHorizontal.guidePercent = percentVerticalMoved
        paramsGlVertical.guidePercent = percentHorizontalMoved
        paramsGlBottom.guidePercent = percentBottomMoved
        paramsGlMarginEnd.guidePercent = percentMarginMoved

        guidelineHorizontal.layoutParams = paramsGlHorizontal
        guidelineVertical.layoutParams = paramsGlVertical
        guidelineBottom.layoutParams = paramsGlBottom
        guidelineMarginEnd.layoutParams = paramsGlMarginEnd

        frmDetailsContainer.alpha = 1.0F - movedPercent
    }


    /**
     * Swipe animation on given percentage user has scroll on left/right
     * direction from the current position
     */
    private fun swipeVideo(percentScrollSwipe: Float) {

        //Prevent guidelines to go out of screen bound
        val percentHorizontalMoved = Math.max(-0.25F, Math.min(VideoTouchHandler.MIN_HORIZONTAL_LIMIT, percentScrollSwipe))
        val percentMarginMoved = percentHorizontalMoved + (VideoTouchHandler.MIN_MARGIN_END_LIMIT - VideoTouchHandler.MIN_HORIZONTAL_LIMIT)

        paramsGlVertical.guidePercent = percentHorizontalMoved
        paramsGlMarginEnd.guidePercent = percentMarginMoved

        guidelineVertical.layoutParams = paramsGlVertical
        guidelineMarginEnd.layoutParams = paramsGlMarginEnd
    }

    /**
     * Expand or collapse the video fragment animation
     */
    private fun setViewExpanded(isExpanded: Boolean) = rootContainer.updateParams(constraintSet) {

        setGuidelinePercent(guidelineHorizontal.id, if (isExpanded) 0F else VideoTouchHandler.MIN_VERTICAL_LIMIT)
        setGuidelinePercent(guidelineVertical.id, if (isExpanded) 0F else VideoTouchHandler.MIN_HORIZONTAL_LIMIT)
        setGuidelinePercent(guidelineBottom.id, if (isExpanded) 1F else VideoTouchHandler.MIN_BOTTOM_LIMIT)
        setGuidelinePercent(guidelineMarginEnd.id, if (isExpanded) 1F else VideoTouchHandler.MIN_MARGIN_END_LIMIT)
        setAlpha(frmDetailsContainer.id, if (isExpanded) 1.0F else 0F)

        TransitionManager.beginDelayedTransition(rootContainer, ChangeBounds().apply {
            interpolator = android.view.animation.AnticipateOvershootInterpolator(1.0f)
            duration = 250
        })
    }

    inline fun ConstraintLayout.updateParams(constraintSet: ConstraintSet = ConstraintSet(), updates: ConstraintSet.() -> Unit) {
        constraintSet.clone(this)
        constraintSet.updates()
        constraintSet.applyTo(this)
    }

    /**
     * Show dismiss animation when user have moved
     * more than 50% horizontally
     */
    private fun dismiss() = rootContainer.updateParams(constraintSet) {

        setGuidelinePercent(guidelineVertical.id, VideoTouchHandler.MIN_HORIZONTAL_LIMIT - VideoTouchHandler.MIN_MARGIN_END_LIMIT)
        setGuidelinePercent(guidelineMarginEnd.id, 0F)

        TransitionManager.beginDelayedTransition(rootContainer, ChangeBounds().apply {
            interpolator = AnticipateOvershootInterpolator(1.0f)
            duration = 250
            addListener(object : Transition.TransitionListener {
                override fun onTransitionResume(transition: Transition) {
                }

                override fun onTransitionPause(transition: Transition) {
                }

                override fun onTransitionCancel(transition: Transition) {
                }

                override fun onTransitionStart(transition: Transition) {
                }

                override fun onTransitionEnd(transition: Transition) {
                    //Remove Video when swipe animation is ended
//                    removeFragmentByID(R.id.frmVideoContainer)
                }
            })
        })
    }

    fun playYoutube (url: String?) {
        fragmentSwitch(R.id.frmVideoContainer,PlayerFragment.newInstance(url))
        fragmentSwitch(R.id.frmDetailsContainer,VideoDetailFragment.newInstance(url))
    }

}
