package com.victor.clips.widget.gallery

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.victor.clips.app.App
import com.victor.clips.util.DensityUtil
import com.victor.clips.util.Loger

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: GalleryItemDecoration.java
 * Author: Victor
 * Date: 2018/9/28 14:19
 * Description: 
 * -----------------------------------------------------------------
 */
class GalleryItemDecoration: RecyclerView.ItemDecoration() {
    private val TAG = "GalleryItemDecoration"

    /**
     * 每一个页面默认页边距
     */
    var mPageMargin = 0
    /**
     * 中间页面左右两边的页面可见部分宽度
     */
    var mLeftPageVisibleWidth = 50

    var mItemConsumeY = 0
    var mItemConsumeX = 0

    private var onItemClickListener: GalleryRecyclerView.OnItemClickListener? = null

    private var mOnItemSizeMeasuredListener: OnItemSizeMeasuredListener? = null

    internal fun GalleryItemDecoration() {}

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        Loger.d(TAG, "GalleryItemDecoration getItemOffset() --> position = " + parent.getChildAdapterPosition(view))

        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter.itemCount

        parent.post {
            if ((parent as GalleryRecyclerView).getOrientation() === LinearLayoutManager.HORIZONTAL) {
                onSetHorizontalParams(parent, view, position, itemCount)
            } else {
                onSetVerticalParams(parent, view, position, itemCount)
            }
        }

        view.setOnClickListener { v ->
            if (onItemClickListener != null) {
                onItemClickListener!!.onItemClick(v, position)
            }
        }
    }

    private fun onSetVerticalParams(parent: ViewGroup, itemView: View, position: Int, itemCount: Int) {
        val itemNewWidth = parent.width
        val itemNewHeight = parent.height - DensityUtil.dip2px(App.instance(), (4 * mPageMargin + 2 * mLeftPageVisibleWidth).toFloat())

        mItemConsumeY = itemNewHeight + DensityUtil.dip2px(App.instance(), (2 * mPageMargin).toFloat())

        if (mOnItemSizeMeasuredListener != null) {
            mOnItemSizeMeasuredListener!!.onItemSizeMeasured(mItemConsumeY)
        }

        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
        val topMargin = if (position == 0) DensityUtil.dip2px(App.instance(), (mLeftPageVisibleWidth + 2 * mPageMargin).toFloat()) else DensityUtil.dip2px(App.instance(), mPageMargin.toFloat())
        val bottomMargin = if (position == itemCount - 1) DensityUtil.dip2px(App.instance(), (mLeftPageVisibleWidth + 2 * mPageMargin).toFloat()) else DensityUtil.dip2px(App.instance(), mPageMargin.toFloat())

        setLayoutParams(itemView, 0, topMargin, 0, bottomMargin, itemNewWidth, itemNewHeight)
    }

    /**
     * 设置水平滚动的参数
     *
     * @param parent    ViewGroup
     * @param itemView  View
     * @param position  int
     * @param itemCount int
     */
    private fun onSetHorizontalParams(parent: ViewGroup, itemView: View, position: Int, itemCount: Int) {
        val itemNewWidth = parent.width - DensityUtil.dip2px(App.instance(), (4 * mPageMargin + 2 * mLeftPageVisibleWidth).toFloat())
        val itemNewHeight = parent.height

        mItemConsumeX = itemNewWidth + DensityUtil.dip2px(App.instance(), (2 * mPageMargin).toFloat())

        if (mOnItemSizeMeasuredListener != null) {
            mOnItemSizeMeasuredListener!!.onItemSizeMeasured(mItemConsumeX)
        }

        Loger.d(TAG, "GalleryItemDecoration onSetHorizontalParams -->" + "parent.width=" + parent.width + ";mPageMargin=" + DensityUtil.dip2px(App.instance(), mPageMargin.toFloat())
                + ";mLeftVis=" + DensityUtil.dip2px(App.instance(), mLeftPageVisibleWidth.toFloat()) + ";itemNewWidth=" + itemNewWidth)

        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
        val leftMargin = if (position == 0) DensityUtil.dip2px(App.instance(), (mLeftPageVisibleWidth + 2 * mPageMargin).toFloat()) else DensityUtil.dip2px(App.instance(), mPageMargin.toFloat())
        val rightMargin = if (position == itemCount - 1) DensityUtil.dip2px(App.instance(), (mLeftPageVisibleWidth + 2 * mPageMargin).toFloat()) else DensityUtil.dip2px(App.instance(), mPageMargin.toFloat())

        setLayoutParams(itemView, leftMargin, 0, rightMargin, 0, itemNewWidth, itemNewHeight)
    }

    /**
     * 设置参数
     *
     * @param itemView   View
     * @param left       int
     * @param top        int
     * @param right      int
     * @param bottom     int
     * @param itemWidth  int
     * @param itemHeight int
     */
    private fun setLayoutParams(itemView: View, left: Int, top: Int, right: Int, bottom: Int, itemWidth: Int, itemHeight: Int) {

        Loger.d(TAG, "GalleryItemDecoration setLayoutParams -->" + "left=" + left + ";top=" + top
                + ";right=" + right + ";bottom=" + bottom + ";itemWidth=" + itemWidth + ";itemHeight=" + itemHeight)

        val lp = itemView.layoutParams as RecyclerView.LayoutParams
        var mMarginChange = false
        var mWidthChange = false
        var mHeightChange = false

        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom)
            mMarginChange = true
        }
        if (lp.width != itemWidth) {
            lp.width = itemWidth
            mWidthChange = true
        }
        if (lp.height != itemHeight) {
            lp.height = itemHeight
            mHeightChange = true

        }

        // 因为方法会不断调用，只有在真正变化了之后才调用
        if (mWidthChange || mMarginChange || mHeightChange) {
            itemView.layoutParams = lp
        }
    }

    fun setOnItemClickListener(onItemClickListener: GalleryRecyclerView.OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemSizeMeasuredListener(itemSizeMeasuredListener: OnItemSizeMeasuredListener) {
        this.mOnItemSizeMeasuredListener = itemSizeMeasuredListener
    }

    interface OnItemSizeMeasuredListener {
        /**
         * Item的大小测量完成
         * @param size int
         */
        fun onItemSizeMeasured(size: Int)
    }
}