package com.victor.clips.widget.gallery

import android.support.v7.widget.RecyclerView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: AnimManager.java
 * Author: Victor
 * Date: 2018/9/28 14:18
 * Description: 
 * -----------------------------------------------------------------
 */
class AnimManager {
    companion object {
        const val ANIM_BOTTOM_TO_TOP = 0
        const val ANIM_TOP_TO_BOTTOM = 1
    }

    /**
     * 动画类型
     */
    private var mAnimType = ANIM_BOTTOM_TO_TOP
    /**
     * 变化因子
     */
    private var mAnimFactor = 0.2f

    fun setAnimation(recyclerView: RecyclerView, position: Int, percent: Float) {
        when (mAnimType) {
            ANIM_BOTTOM_TO_TOP -> setBottomToTopAnim(recyclerView, position, percent)
            ANIM_TOP_TO_BOTTOM -> setTopToBottomAnim(recyclerView, position, percent)
            else -> setBottomToTopAnim(recyclerView, position, percent)
        }
    }


    /**
     * 从下到上的动画效果
     *
     * @param recyclerView RecyclerView
     * @param position int
     * @param percent float
     */
    private fun setBottomToTopAnim(recyclerView: RecyclerView, position: Int, percent: Float) {
        // 中间页
        val mCurView = recyclerView.layoutManager.findViewByPosition(position)
        // 右边页
        val mRightView = recyclerView.layoutManager.findViewByPosition(position + 1)
        // 左边页
        val mLeftView = recyclerView.layoutManager.findViewByPosition(position - 1)
        // 右右边页
        val mRRView = recyclerView.layoutManager.findViewByPosition(position + 2)

        if (mLeftView != null) {
            mLeftView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
            mLeftView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
        }
        if (mCurView != null) {
            mCurView.scaleX = 1 - percent * mAnimFactor
            mCurView.scaleY = 1 - percent * mAnimFactor
        }
        if (mRightView != null) {
            mRightView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
            mRightView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
        }
        if (mRRView != null) {
            mRRView.scaleX = 1 - percent * mAnimFactor
            mRRView.scaleY = 1 - percent * mAnimFactor
        }
    }


    /***
     * 从上到下的效果
     * @param recyclerView RecyclerView
     * @param position int
     * @param percent int
     */
    private fun setTopToBottomAnim(recyclerView: RecyclerView, position: Int, percent: Float) {
        // 中间页
        val mCurView = recyclerView.layoutManager.findViewByPosition(position)
        // 右边页
        val mRightView = recyclerView.layoutManager.findViewByPosition(position + 1)
        // 左边页
        val mLeftView = recyclerView.layoutManager.findViewByPosition(position - 1)
        // 左左边页
        val mLLView = recyclerView.layoutManager.findViewByPosition(position - 2)

        if (mLeftView != null) {
            mLeftView.scaleX = 1 - percent * mAnimFactor
            mLeftView.scaleY = 1 - percent * mAnimFactor
        }
        if (mCurView != null) {
            mCurView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
            mCurView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
        }
        if (mRightView != null) {
            mRightView.scaleX = 1 - percent * mAnimFactor
            mRightView.scaleY = 1 - percent * mAnimFactor
        }
        if (mLLView != null) {
            mLLView.scaleX = 1 - mAnimFactor + percent * mAnimFactor
            mLLView.scaleY = 1 - mAnimFactor + percent * mAnimFactor
        }
    }

    fun setAnimFactor(mAnimFactor: Float) {
        this.mAnimFactor = mAnimFactor
    }

    fun setAnimType(mAnimType: Int) {
        this.mAnimType = mAnimType
    }
}