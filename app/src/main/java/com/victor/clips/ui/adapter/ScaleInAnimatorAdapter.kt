package com.victor.clips.ui.adapter

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View.SCALE_Y
import android.view.View.SCALE_X
import android.icu.lang.UCharacter.GraphemeClusterBreak.T






/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ScaleInAnimatorAdapter.java
 * Author: Victor
 * Date: 2019/11/11 16:38
 * Description:
 * -----------------------------------------------------------------
 */
class ScaleInAnimatorAdapter <T: RecyclerView.ViewHolder>
(var adapter: RecyclerView.Adapter<T>, var recyclerView: RecyclerView): AnimatorAdapter<T>(adapter,recyclerView) {

    private val DEFAULT_SCALE_FROM = 0.6f

    private val SCALE_X = "scaleX"
    private val SCALE_Y = "scaleY"

    private val mScaleFrom: Float = 0f

    override fun getAnimators(view: View): Array<Animator> {
        val scaleX = ObjectAnimator.ofFloat(view, SCALE_X, mScaleFrom, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, SCALE_Y, mScaleFrom, 1f)
        return arrayOf(scaleX,scaleY)
    }

}