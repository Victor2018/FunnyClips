package com.victor.clips.widget

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: FABBehavior.java
 * Author: Victor
 * Date: 2018/9/12 10:29
 * Description: 
 * -----------------------------------------------------------------
 */
class FABBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {
    private val hideBorder = context.resources.displayMetrics.heightPixels / 2

    override fun layoutDependsOn(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
        updateFABVisibility(dependency, child)
        return false
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: FloatingActionButton, layoutDirection: Int): Boolean {
        val dependencies = parent.getDependencies(child)
        for (i in 0 until dependencies.size) {
            val dependency = dependencies[i]
            if (dependency is AppBarLayout) {
                updateFABVisibility(dependency, child)
                break
            }
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    private fun updateFABVisibility(dependency: View, child: FloatingActionButton) {
        val show = dependency.bottom <= hideBorder
        if (show) child.show() else child.hide()
    }
}