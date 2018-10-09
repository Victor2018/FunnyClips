package com.victor.clips.widget

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: HeaderOverlayBehavior.java
 * Author: Victor
 * Date: 2018/9/12 10:27
 * Description: 
 * -----------------------------------------------------------------
 */
class HeaderOverlayBehavior(context: Context, attrs: AttributeSet) :
        CoordinatorLayout.Behavior<FrameLayout>(context, attrs) {
    override fun layoutDependsOn(parent: CoordinatorLayout, child: FrameLayout, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: FrameLayout, dependency: View): Boolean {
        update(dependency, child)
        return false
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: FrameLayout, layoutDirection: Int): Boolean {
        val dependencies = parent.getDependencies(child)
        for (i in 0 until dependencies.size) {
            val dependency = dependencies[i]
            if (dependency is AppBarLayout) {
                update(dependency, child)
                break
            }
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    private fun update(dependency: View, child: FrameLayout) {
        child.y = (dependency.bottom - child.height).toFloat()
    }
}