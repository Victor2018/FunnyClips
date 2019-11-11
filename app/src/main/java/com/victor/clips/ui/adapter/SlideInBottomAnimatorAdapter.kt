package com.victor.clips.ui.adapter

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SlideInBottomAnimatorAdapter.java
 * Author: Victor
 * Date: 2019/11/11 16:38
 * Description:
 * -----------------------------------------------------------------
 */
class SlideInBottomAnimatorAdapter <T: RecyclerView.ViewHolder>
(var adapter: RecyclerView.Adapter<T>, var recyclerView: RecyclerView): AnimatorAdapter<T>(adapter,recyclerView) {
    private val TRANSLATION_Y = "translationY"

    override fun getAnimators(view: View): Array<Animator> {
        var anim = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, (mRecyclerView.measuredHeight shr 1).toFloat(), 0f)
        return arrayOf(anim)
    }

}