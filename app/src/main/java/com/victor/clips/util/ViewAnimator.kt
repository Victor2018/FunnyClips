package com.victor.clips.util

import android.animation.Animator
import android.os.SystemClock
import android.util.SparseArray
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.annotation.SuppressLint
import android.animation.AnimatorSet
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.View


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ViewAnimator.java
 * Author: Victor
 * Date: 2019/11/11 16:12
 * Description:
 * -----------------------------------------------------------------
 */
class ViewAnimator(var recyclerView: RecyclerView?) {
    /* Saved instance state keys */
    private val SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION = "savedinstancestate_firstanimatedposition"
    private val SAVEDINSTANCESTATE_LASTANIMATEDPOSITION = "savedinstancestate_lastanimatedposition"
    private val SAVEDINSTANCESTATE_SHOULDANIMATE = "savedinstancestate_shouldanimate"

    /* Default values */

    /**
     * The default delay in millis before the first animation starts.
     */
    private val INITIAL_DELAY_MILLIS = 150

    /**
     * The default delay in millis between view animations.
     */
    private val DEFAULT_ANIMATION_DELAY_MILLIS = 100

    /**
     * The default duration in millis of the animations.
     */
    private val DEFAULT_ANIMATION_DURATION_MILLIS = 300

    /* Fields */

    /**
     * The ListViewWrapper containing the ListView implementation.
     */
    private var mRecyclerView: RecyclerView? = null

    /**
     * The active Animators. Keys are hashcodes of the Views that are animated.
     */
    private var mAnimators = SparseArray<Animator>()

    /**
     * The delay in millis before the first animation starts.
     */
    private var mInitialDelayMillis = INITIAL_DELAY_MILLIS

    /**
     * The delay in millis between view animations.
     */
    private var mAnimationDelayMillis = DEFAULT_ANIMATION_DELAY_MILLIS

    /**
     * The duration in millis of the animations.
     */
    private var mAnimationDurationMillis = DEFAULT_ANIMATION_DURATION_MILLIS

    /**
     * The start timestamp of the first animation, as returned by [SystemClock.uptimeMillis].
     */
    private var mAnimationStartMillis: Long = 0

    /**
     * The position of the item that is the first that was animated.
     */
    private var mFirstAnimatedPosition: Int = 0

    /**
     * The position of the last item that was animated.
     */
    private var mLastAnimatedPosition: Int = 0

    /**
     * Whether animation is enabled. When this is set to false, no animation is applied to the views.
     */
    private var mShouldAnimate = true

    init {
        mAnimationStartMillis = -1;
        mFirstAnimatedPosition = -1;
        mLastAnimatedPosition = -1;
    }

    /**
     * Call this method to reset animation status on all views.
     */
    fun reset() {
        for (i in 0 until mAnimators.size()) {
            mAnimators.get(mAnimators.keyAt(i)).cancel()
        }
        mAnimators.clear()
        mFirstAnimatedPosition = -1
        mLastAnimatedPosition = -1
        mAnimationStartMillis = -1
        mShouldAnimate = true
    }

    /**
     * Set the starting position for which items should animate. Given position will animate as well.
     * Will also call [.enableAnimations].
     *
     * @param position the position.
     */
    fun setShouldAnimateFromPosition(position: Int) {
        enableAnimations()
        mFirstAnimatedPosition = position - 1
        mLastAnimatedPosition = position - 1
    }

    /**
     * Set the starting position for which items should animate as the first position which isn't currently visible on screen. This call is also valid when the [View]s
     * haven't been drawn yet. Will also call [.enableAnimations].
     */
    fun setShouldAnimateNotVisible() {
        enableAnimations()
        mFirstAnimatedPosition = (mRecyclerView?.getLayoutManager() as LinearLayoutManager).findLastVisibleItemPosition()
        mLastAnimatedPosition = (mRecyclerView?.getLayoutManager() as LinearLayoutManager).findLastVisibleItemPosition()
    }

    /**
     * Sets the value of the last animated position. Views with positions smaller than or equal to given value will not be animated.
     */
    fun setLastAnimatedPosition(lastAnimatedPosition: Int) {
        mLastAnimatedPosition = lastAnimatedPosition
    }

    /**
     * Sets the delay in milliseconds before the first animation should start. Defaults to {@value #INITIAL_DELAY_MILLIS}.
     *
     * @param delayMillis the time in milliseconds.
     */
    fun setInitialDelayMillis(delayMillis: Int) {
        mInitialDelayMillis = delayMillis
    }

    /**
     * Sets the delay in milliseconds before an animation of a view should start. Defaults to {@value #DEFAULT_ANIMATION_DELAY_MILLIS}.
     *
     * @param delayMillis the time in milliseconds.
     */
    fun setAnimationDelayMillis(delayMillis: Int) {
        mAnimationDelayMillis = delayMillis
    }

    /**
     * Sets the duration of the animation in milliseconds. Defaults to {@value #DEFAULT_ANIMATION_DURATION_MILLIS}.
     *
     * @param durationMillis the time in milliseconds.
     */
    fun setAnimationDurationMillis(durationMillis: Int) {
        mAnimationDurationMillis = durationMillis
    }

    /**
     * Enables animating the Views. This is the default.
     */
    fun enableAnimations() {
        mShouldAnimate = true
    }

    /**
     * Disables animating the Views. Enable them again using [.enableAnimations].
     */
    fun disableAnimations() {
        mShouldAnimate = false
    }

    /**
     * Cancels any existing animations for given View.
     */
    fun cancelExistingAnimation(view: View) {
        val hashCode = view.hashCode()
        val animator = mAnimators.get(hashCode)
        if (animator != null) {
            animator.end()
            mAnimators.remove(hashCode)
        }
    }

    /**
     * Animates given View if necessary.
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     */
    fun animateViewIfNecessary(position: Int, view: View, animators: Array<Animator?>) {
        if (mShouldAnimate && position > mLastAnimatedPosition) {
            if (mFirstAnimatedPosition === -1) {
                mFirstAnimatedPosition = position
            }

            animateView(position, view, animators)
            mLastAnimatedPosition = position
        }
    }

    /**
     * Animates given View.
     *
     * @param view the View that should be animated.
     */
    private fun animateView(position: Int, view: View, animators: Array<Animator?>) {
        if (mAnimationStartMillis.toInt() == -1) {
            mAnimationStartMillis = SystemClock.uptimeMillis()
        }

        ViewCompat.setAlpha(view, 0f)

        val set = AnimatorSet()
        set.playTogether(*animators)
        set.startDelay = calculateAnimationDelay(position).toLong()
        set.setDuration(mAnimationDurationMillis.toLong())
        set.start()

        mAnimators.put(view.hashCode(), set)
    }

    /**
     * Returns the delay in milliseconds after which animation for View with position mLastAnimatedPosition + 1 should start.
     */
    @SuppressLint("NewApi")
    private fun calculateAnimationDelay(position: Int): Int {
        var delay: Int
        var lastVisiblePosition = 0
        var firstVisiblePosition = 0
        if (mRecyclerView?.getLayoutManager() is LinearLayoutManager) {
            lastVisiblePosition = (mRecyclerView?.getLayoutManager() as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            firstVisiblePosition = (mRecyclerView?.getLayoutManager() as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        } else if (mRecyclerView?.getLayoutManager() is StaggeredGridLayoutManager) {
            lastVisiblePosition = getLastVisibleItemPosition(mRecyclerView?.getLayoutManager() as StaggeredGridLayoutManager)
            firstVisiblePosition = getFirstVisibleItemPosition(mRecyclerView?.getLayoutManager() as StaggeredGridLayoutManager)
        }

        if (mLastAnimatedPosition > lastVisiblePosition) lastVisiblePosition = mLastAnimatedPosition

        val numberOfItemsOnScreen = lastVisiblePosition - firstVisiblePosition
        val numberOfAnimatedItems = position - 1 - mFirstAnimatedPosition

        if (numberOfItemsOnScreen + 1 < numberOfAnimatedItems) {
            delay = mAnimationDelayMillis

            if (mRecyclerView?.getLayoutManager() is GridLayoutManager) {
                val numColumns = (mRecyclerView?.getLayoutManager() as GridLayoutManager).spanCount
                delay += mAnimationDelayMillis * (position % numColumns)
                Log.d("GAB", "Delay[$position]=*$lastVisiblePosition|$firstVisiblePosition|")
            }
        } else {
            val delaySinceStart = (position - mFirstAnimatedPosition) * mAnimationDelayMillis
            delay = Math.max(0, (-SystemClock.uptimeMillis() + mAnimationStartMillis + mInitialDelayMillis + delaySinceStart).toInt())
        }
        Log.d("GAB", "Delay[$position]=$delay|$lastVisiblePosition|$firstVisiblePosition|")
        return delay
    }

    /**
     * Returns a Parcelable object containing the AnimationAdapter's current dynamic state.
     */
    fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()

        bundle.putInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION, mFirstAnimatedPosition)
        bundle.putInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION, mLastAnimatedPosition)
        bundle.putBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE, mShouldAnimate)

        return bundle
    }

    /**
     * Restores this AnimationAdapter's state.
     *
     * @param parcelable the Parcelable object previously returned by [.onSaveInstanceState].
     */
    fun onRestoreInstanceState(parcelable: Parcelable) {
        if (parcelable is Bundle) {
            mFirstAnimatedPosition = parcelable.getInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION)
            mLastAnimatedPosition = parcelable.getInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION)
            mShouldAnimate = parcelable.getBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE)
        }
    }

    private fun getFirstVisibleItemPosition(staggeredGridLayoutManager: StaggeredGridLayoutManager): Int {
        val lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
        staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
        return lastPositions[0]
    }

    private fun getLastVisibleItemPosition(staggeredGridLayoutManager: StaggeredGridLayoutManager): Int {
        val lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
        staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
        return findMax(lastPositions)
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }
}