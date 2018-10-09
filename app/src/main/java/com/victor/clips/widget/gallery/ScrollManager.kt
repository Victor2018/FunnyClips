package com.victor.clips.widget.gallery

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import com.victor.clips.app.App
import com.victor.clips.util.DensityUtil
import com.victor.clips.util.Loger

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: ScrollManager.java
 * Author: Victor
 * Date: 2018/9/28 14:30
 * Description: 
 * -----------------------------------------------------------------
 */
class ScrollManager(var mGalleryRecyclerView: GalleryRecyclerView) {
    private val TAG = "MainActivity_TAG"
    private var mPosition = 0

    /**
     * x方向消耗距离，使偏移量为左边距 + 左边Item的可视部分宽度
     */
    private var mConsumeX = 0
    private var mConsumeY = 0

    /**
     * 初始化SnapHelper
     *
     * @param helper int
     */
    fun initSnapHelper(helper: Int) {
        when (helper) {
            GalleryRecyclerView.LINEAR_SNAP_HELPER -> {
                val mLinearSnapHelper = LinearSnapHelper()
                mLinearSnapHelper.attachToRecyclerView(mGalleryRecyclerView)
            }
            GalleryRecyclerView.PAGER_SNAP_HELPER -> {
                val mPagerSnapHelper = PagerSnapHelper()
                mPagerSnapHelper.attachToRecyclerView(mGalleryRecyclerView)
            }
            else -> {
            }
        }
    }

    /**
     * 监听RecyclerView的滑动
     */
    fun initScrollListener() {
        val mScrollerListener = GalleryScrollerListener()
        mGalleryRecyclerView!!.addOnScrollListener(mScrollerListener)
    }

    fun updateConsume() {
        mConsumeX += DensityUtil.dip2px(App.instance(), (mGalleryRecyclerView!!.getDecoration()!!.mLeftPageVisibleWidth + mGalleryRecyclerView!!.getDecoration()!!.mPageMargin * 2).toFloat())
        mConsumeY += DensityUtil.dip2px(App.instance(),(mGalleryRecyclerView!!.getDecoration()!!.mLeftPageVisibleWidth + mGalleryRecyclerView!!.getDecoration()!!.mPageMargin * 2).toFloat())
        Loger.d(TAG, "ScrollManager updateConsume mConsumeX=$mConsumeX")
    }

    internal inner class GalleryScrollerListener : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            Loger.d(TAG, "ScrollManager newState=$newState")
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (mGalleryRecyclerView!!.getOrientation() === LinearLayoutManager.HORIZONTAL) {
                onHorizontalScroll(recyclerView!!, dx)
            } else {
                onVerticalScroll(recyclerView!!, dy)
            }
        }
    }

    /**
     * 垂直滑动
     *
     * @param recyclerView RecyclerView
     * @param dy           int
     */
    private fun onVerticalScroll(recyclerView: RecyclerView, dy: Int) {
        mConsumeY += dy

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemHeight的值拿不到
        recyclerView.post {
            val shouldConsumeY = mGalleryRecyclerView!!.getDecoration()!!.mItemConsumeY

            // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
            val offset = mConsumeY.toFloat() / shouldConsumeY.toFloat()
            // 获取当前页移动的百分值
            val percent = offset - offset.toInt()

            mPosition = offset.toInt()

            Loger.i(TAG, "ScrollManager offset=$offset; mConsumeY=$mConsumeY; shouldConsumeY=$mPosition")


            // 设置动画变化
            mGalleryRecyclerView!!.getAnimManager().setAnimation(recyclerView, mPosition, percent)
        }
    }

    /**
     * 水平滑动
     *
     * @param recyclerView RecyclerView
     * @param dx           int
     */
    private fun onHorizontalScroll(recyclerView: RecyclerView, dx: Int) {
        mConsumeX += dx

        // 让RecyclerView测绘完成后再调用，避免GalleryAdapterHelper.mItemWidth的值拿不到
        recyclerView.post {
            val shouldConsumeX = mGalleryRecyclerView!!.getDecoration()!!.mItemConsumeX

            // 位置浮点值（即总消耗距离 / 每一页理论消耗距离 = 一个浮点型的位置值）
            val offset = mConsumeX.toFloat() / shouldConsumeX.toFloat()

            // 获取当前页移动的百分值
            val percent = offset - offset.toInt()

            mPosition = offset.toInt()

            Loger.i(TAG, "ScrollManager offset=$offset; percent=$percent; mConsumeX=$mConsumeX; shouldConsumeX=$shouldConsumeX; position=$mPosition")

            // 设置动画变化
            mGalleryRecyclerView!!.getAnimManager().setAnimation(recyclerView, mPosition, percent)
        }

    }

    fun getPosition(): Int {
        return mPosition
    }
}