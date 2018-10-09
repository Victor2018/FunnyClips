package com.victor.clips.widget.gallery

import android.content.Context
import android.os.Parcelable
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.victor.clips.R
import com.victor.clips.util.Loger
import com.victor.clips.util.ThreadUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: GalleryRecyclerView.java
 * Author: Victor
 * Date: 2018/9/28 14:21
 * Description: 
 * -----------------------------------------------------------------
 */
class GalleryRecyclerView(context: Context, attrs: AttributeSet?, defStyle: Int): RecyclerView(context,attrs,defStyle),View.OnTouchListener, GalleryItemDecoration.OnItemSizeMeasuredListener {

    private val TAG = "MainActivity_TAG"

    companion object {
        const val LINEAR_SNAP_HELPER = 0
        const val PAGER_SNAP_HELPER = 1
    }
    /**
     * 滑动速度
     */
    private var mFlingSpeed = 1000
    /**
     * 是否自动播放
     */
    private var mAutoPlay = false
    /**
     * 自动播放间隔时间
     */
    private var mInterval = 1000

    private var mInitPos = -1


    private lateinit var mAnimManager: AnimManager
    private var mScrollManager: ScrollManager? = null
    private var mDecoration: GalleryItemDecoration? = null

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.GalleryRecyclerView)
        val helper = ta.getInteger(R.styleable.GalleryRecyclerView_helper, LINEAR_SNAP_HELPER)
        ta.recycle()

        Loger.d(TAG, "GalleryRecyclerView constructor")

        mAnimManager = AnimManager()
        attachDecoration()
        attachToRecyclerHelper(helper)

        //设置触碰监听
        setOnTouchListener(this)
    }

    private val mAutoPlayTask = object : Runnable {
        override fun run() {
            if (getAdapter() == null || getAdapter().getItemCount() <= 0) {
                return
            }

            val position = getScrolledPosition()
            val itemCount = getAdapter().getItemCount()

            val newPosition = (position + 1) % itemCount
            smoothScrollToPosition(newPosition)

            ThreadUtils.removeCallbacks(this)
            ThreadUtils.runOnUiThread(this, mInterval.toLong())
        }
    }

    fun getDecoration(): GalleryItemDecoration? {
        return mDecoration
    }

    fun getAnimManager(): AnimManager {
        return mAnimManager
    }

    protected override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
    }


    private fun attachDecoration() {
        Loger.d(TAG, "GalleryRecyclerView attachDecoration")

        mDecoration = GalleryItemDecoration()
        mDecoration!!.setOnItemSizeMeasuredListener(this)
        addItemDecoration(mDecoration)
    }


    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        var velocityX = velocityX
        var velocityY = velocityY
        velocityX = balanceVelocity(velocityX)
        velocityY = balanceVelocity(velocityY)
        return super.fling(velocityX, velocityY)
    }

    /**
     * 返回滑动速度值
     *
     * @param velocity int
     * @return int
     */
    private fun balanceVelocity(velocity: Int): Int {
        return if (velocity > 0) {
            Math.min(velocity, mFlingSpeed)
        } else {
            Math.max(velocity, -mFlingSpeed)
        }
    }

    /**
     * 连接RecyclerHelper
     *
     * @param helper int
     */
    private fun attachToRecyclerHelper(helper: Int) {
        Loger.d(TAG, "GalleryRecyclerView attachToRecyclerHelper")

        mScrollManager = ScrollManager(this)
        mScrollManager!!.initScrollListener()
        mScrollManager!!.initSnapHelper(helper)
    }

    /**
     * 设置页面参数，单位dp
     *
     * @param pageMargin           默认：0dp
     * @param leftPageVisibleWidth 默认：50dp
     * @return GalleryRecyclerView
     */
    fun initPageParams(pageMargin: Int, leftPageVisibleWidth: Int): GalleryRecyclerView {
        mDecoration!!.mPageMargin = pageMargin
        mDecoration!!.mLeftPageVisibleWidth = leftPageVisibleWidth
        return this
    }

    /**
     * 设置滑动速度（像素/s）
     *
     * @param speed int
     * @return GalleryRecyclerView
     */
    fun initFlingSpeed(@IntRange(from = 0) speed: Int): GalleryRecyclerView {
        this.mFlingSpeed = speed
        return this
    }

    /**
     * 设置动画因子
     *
     * @param factor float
     * @return GalleryRecyclerView
     */
    fun setAnimFactor(@FloatRange(from = 0.0) factor: Float): GalleryRecyclerView {
        mAnimManager.setAnimFactor(factor)
        return this
    }

    /**
     * 设置动画类型
     *
     * @param type int
     * @return GalleryRecyclerView
     */
    fun setAnimType(type: Int): GalleryRecyclerView {
        mAnimManager.setAnimType(type)
        return this
    }

    /**
     * 设置点击事件
     *
     * @param mListener OnItemClickListener
     */
    fun setOnItemClickListener(mListener: OnItemClickListener): GalleryRecyclerView {
        if (mDecoration != null) {
            mDecoration!!.setOnItemClickListener(mListener)
        }
        return this
    }

    /**
     * 是否自动滚动
     *
     * @param auto boolean
     * @return GalleryRecyclerView
     */
    fun autoPlay(auto: Boolean): GalleryRecyclerView {
        this.mAutoPlay = auto
        return this
    }

    /**
     * 自动播放
     */
    private fun autoPlayGallery() {
        if (mAutoPlay) {
            ThreadUtils.removeCallbacks(mAutoPlayTask)
            ThreadUtils.runOnUiThread(mAutoPlayTask, mInterval.toLong())
        }
    }

    /**
     * 移除自动播放Runnable
     */
    private fun removeAutoPlayTask() {
        if (mAutoPlay) {
            ThreadUtils.removeCallbacks(mAutoPlayTask)
        }
    }

    /**
     * 装载
     *
     * @return GalleryRecyclerView
     */
    fun setUp(): GalleryRecyclerView {
        if (getAdapter().getItemCount() <= 0) {
            return this
        }

        smoothScrollToPosition(0)
        mScrollManager!!.updateConsume()

        autoPlayGallery()

        return this
    }

    /**
     * 释放资源
     */
    fun release() {
        removeAutoPlayTask()
    }


    fun getOrientation(): Int {

        return if (getLayoutManager() is LinearLayoutManager) {
            if (getLayoutManager() is GridLayoutManager) {
                throw RuntimeException("请设置LayoutManager为LinearLayoutManager")
            } else {
                (getLayoutManager() as LinearLayoutManager).orientation
            }
        } else {
            throw RuntimeException("请设置LayoutManager为LinearLayoutManager")
        }
    }

    fun getScrolledPosition(): Int {
        return if (mScrollManager == null) {
            0
        } else {
            mScrollManager!!.getPosition()
        }
    }

    protected override fun onSaveInstanceState(): Parcelable? {
        Loger.d(TAG, "GalleryRecyclerView onSaveInstanceState()")
        return super.onSaveInstanceState()
    }

    protected override fun onRestoreInstanceState(state: Parcelable) {
        super.onRestoreInstanceState(state)

        // 如果是横竖屏切换（Fragment销毁），不应该走smoothScrollToPosition(0)，因为这个方法会导致ScrollManager的onHorizontalScroll不断执行，而ScrollManager.mConsumeX已经重置，会导致这个值紊乱
        // 而如果走scrollToPosition(0)方法，则不会导致ScrollManager的onHorizontalScroll执行，所以ScrollManager.mConsumeX这个值不会错误
        scrollToPosition(0)
        // 但是因为不走ScrollManager的onHorizontalScroll，所以不会执行切换动画，所以就调用smoothScrollBy(int dx, int dy)，让item轻微滑动，触发动画
        smoothScrollBy(10, 0)
        smoothScrollBy(0, 0)

        autoPlayGallery()
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> removeAutoPlayTask()
            MotionEvent.ACTION_MOVE -> removeAutoPlayTask()
            MotionEvent.ACTION_UP -> autoPlayGallery()
            else -> {
            }
        }
        return false
    }

    /**
     * 播放间隔时间 ms
     *
     * @param interval int
     * @return GalleryRecyclerView
     */
    fun intervalTime(@IntRange(from = 10) interval: Int): GalleryRecyclerView {
        this.mInterval = interval
        return this
    }

    /**
     * 开始处于的位置
     *
     * @param i int
     * @return GalleryRecyclerView
     */
    fun initPosition(@IntRange(from = 0) i: Int): GalleryRecyclerView {
        var i = i
        if (i >= getAdapter().getItemCount()) {
            i = getAdapter().getItemCount() - 1
        } else if (i < 0) {
            i = 0
        }
        mInitPos = i
        return this
    }

    override fun onItemSizeMeasured(size: Int) {
        if (mInitPos < 0) {
            return
        }
        if (mInitPos == 0) {
            scrollToPosition(0)
        } else {
            if (getOrientation() == LinearLayoutManager.HORIZONTAL) {
                smoothScrollBy(mInitPos * size, 0)
            } else {
                smoothScrollBy(0, mInitPos * size)
            }
        }
        mInitPos = -1
    }

    interface OnItemClickListener {
        /**
         * 点击事件
         *
         * @param view     View
         * @param position int
         */
        fun onItemClick(view: View, position: Int)
    }
}