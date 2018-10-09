package com.victor.clips.widget

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: GravityPagerSnapHelper.java
 * Author: Victor
 * Date: 2018/9/4 10:49
 * Description: 
 * -----------------------------------------------------------------
 */
class GravityPagerSnapHelper: PagerSnapHelper {
    private var delegate: GravityDelegate? = null


    constructor(gravity: Int, enableSnapLastItem: Boolean,
                               snapListener: GravitySnapHelper.SnapListener?) {
        delegate = GravityDelegate(gravity, enableSnapLastItem, snapListener)
    }

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView != null && (recyclerView.layoutManager !is LinearLayoutManager || recyclerView.layoutManager is GridLayoutManager)) {
            throw IllegalStateException("GravityPagerSnapHelper needs a RecyclerView" + " with a LinearLayoutManager")
        }
        delegate!!.attachToRecyclerView(recyclerView)
        super.attachToRecyclerView(recyclerView)
    }

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager,
                                              targetView: View): IntArray? {
        return delegate!!.calculateDistanceToFinalSnap(layoutManager, targetView)
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return delegate!!.findSnapView(layoutManager)
    }

    /**
     * Enable snapping of the last item that's snappable.
     * The default value is false, because you can't see the last item completely
     * if this is enabled.
     *
     * @param snap true if you want to enable snapping of the last snappable item
     */
    fun enableLastItemSnap(snap: Boolean) {
        delegate!!.enableLastItemSnap(snap)
    }
}