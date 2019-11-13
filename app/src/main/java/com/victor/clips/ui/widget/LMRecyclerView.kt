package com.victor.clips.ui.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.util.Log

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LMRecyclerView.java
 * Author: Victor
 * Date: 2019/11/11 11:09
 * Description:
 * -----------------------------------------------------------------
 */
class LMRecyclerView: RecyclerView {
    private val TAG = "LMRecyclerView"
    private var isScrollingToBottom = true

    val LINEAR = 0
    val GRID = 1
    val STAGGERED_GRID = 2

    //标识RecyclerView的LayoutManager是哪种
    protected var layoutManagerType: Int = 0
    // 瀑布流的最后一个的位置
    protected lateinit var lastPositions: IntArray
    // 最后一个的位置
    protected var lastVisibleItem: Int = 0

    private var mOnLoadMoreListener: OnLoadMoreListener? = null
    private var hasMore = true
    private var headerCount = 1

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onScrolled(dx: Int, dy: Int) {
        Log.e(TAG, "onScrolled()......")
        isScrollingToBottom = dy > 0
        val layoutManager = layoutManager
        if (layoutManager is LinearLayoutManager) {
            layoutManagerType = LINEAR
        } else if (layoutManager is GridLayoutManager) {
            layoutManagerType = GRID
        } else if (layoutManager is StaggeredGridLayoutManager) {
            layoutManagerType = STAGGERED_GRID
        } else {
            throw RuntimeException(
        "Unsupported LayoutManager used. Valid ones are " +
        "LinearLayoutManager, GridLayoutManager and " +
        "StaggeredGridLayoutManager")
        }

        Log.e(TAG, "onScrolled()......layoutManagerType = $layoutManagerType")
        when (layoutManagerType) {
            LINEAR -> lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            GRID -> lastVisibleItem = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                lastVisibleItem = findMax(lastPositions)
            }
        }
    }

    override fun onScrollStateChanged(screenState: Int) {
        Log.e(TAG, "onScrollStateChanged()......screenState = $screenState")
        if (screenState == RecyclerView.SCROLL_STATE_IDLE) {
            val totalItemCount = layoutManager!!.itemCount
            Log.e(TAG, "lastVisibleItem------>$lastVisibleItem")
            Log.e(TAG, "totalItemCount------->$totalItemCount")

            if (lastVisibleItem + 1 + headerCount >= totalItemCount && mOnLoadMoreListener != null && hasMore) {
                Log.e(TAG, "LOAD MORE DATA......")
                mOnLoadMoreListener?.OnLoadMore()//回调加载更多监听
            }
        }
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

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
        headerCount = if (hasMore) 1 else 0
    }

    interface OnLoadMoreListener {
        fun OnLoadMore()
    }

    fun setLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        mOnLoadMoreListener = loadMoreListener
    }
}