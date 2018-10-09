package com.victor.clips.widget

import java.lang.ref.WeakReference

import com.victor.clips.widget.HeaderLayoutManager.ScrollState
import com.victor.clips.widget.HeaderLayoutManager.ScrollState.IDLE
/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: SimpleSnapHelper.java
 * Author: Victor
 * Date: 2018/9/12 10:11
 * Description: 
 * -----------------------------------------------------------------
 */
class SimpleSnapHelper: HeaderLayoutManager.ScrollStateListener {
    private var toolBarRef: WeakReference<NavigationToolBarLayout>? = null

    override fun onScrollStateChanged(state: ScrollState) {
        if (state != IDLE) {
            return
        }

        toolBarRef?.get()?.also { toolbar ->
            toolbar.smoothScrollToPosition(toolbar.getAnchorPos())
        }
    }

    fun attach(toolbar: NavigationToolBarLayout) {
        toolBarRef = WeakReference(toolbar)
        toolbar.addScrollStateListener(this)
    }

    fun detach(toolbar: NavigationToolBarLayout) {
        toolBarRef = null
        toolbar.removeScrollStateListener(this)
    }
}