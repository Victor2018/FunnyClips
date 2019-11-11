package com.victor.clips.ui.adapter

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.view.View
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View.TRANSLATION_Y
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T







/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SlideInLeftAnimatorAdapter.java
 * Author: Victor
 * Date: 2019/11/11 16:08
 * Description:
 * -----------------------------------------------------------------
 */
class SlideInLeftAnimatorAdapter<T: RecyclerView.ViewHolder>
(var adapter: RecyclerView.Adapter<T>, var recyclerView: RecyclerView): AnimatorAdapter<T>(adapter,recyclerView) {
    private val TRANSLATION_X = "translationX"

    override fun getAnimators(view: View): Array<Animator> {
        var anim = ObjectAnimator.ofFloat(view, TRANSLATION_X, 0f - mRecyclerView.getLayoutManager().getWidth(), 0f)
        return arrayOf(anim)
    }
}