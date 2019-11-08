package com.victor.clips.ui.widget.parallaximageview

import android.graphics.Canvas

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HorizontalScaleStyle.java
 * Author: Victor
 * Date: 2019/11/8 14:57
 * Description:
 * -----------------------------------------------------------------
 */
class HorizontalScaleStyle: ScrollParallaxImageView.ParallaxStyle {
    private var finalScaleRatio = 0.7f

    fun HorizontalScaleStyle() {

    }

    fun HorizontalScaleStyle(finalScaleRatio: Float) {
        this.finalScaleRatio = finalScaleRatio
    }

    fun setFinalScaleRatio(scale: Float) {
        finalScaleRatio = scale
    }

    override fun transform(view: ScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        // view's width and height
        val vWidth = view.width - view.paddingLeft - view.paddingRight
        val vHeight = view.height - view.paddingTop - view.paddingBottom
        // device's width
        val dWidth = view.resources.displayMetrics.widthPixels

        if (vWidth >= dWidth) {
            // Do nothing if imageView's width is bigger than device's width.
            return
        }

        val scale: Float
        val pivot = (dWidth - vWidth) / 2
        if (x <= pivot) {
            scale = 2f * (1 - finalScaleRatio) * (x + vWidth).toFloat() / (dWidth + vWidth) + finalScaleRatio
        } else {
            scale = 2f * (1 - finalScaleRatio) * (dWidth - x).toFloat() / (dWidth + vWidth) + finalScaleRatio
        }

        canvas.scale(scale, scale, (vWidth / 2).toFloat(), (vHeight / 2).toFloat())
    }

    override fun onAttachedToImageView(view: ScrollParallaxImageView) {

    }

    override fun onDetachedFromImageView(view: ScrollParallaxImageView) {

    }
}