package com.victor.clips.ui.widget.parallaximageview

import android.graphics.Canvas

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VerticalScaleStyle.java
 * Author: Victor
 * Date: 2019/11/8 15:00
 * Description:
 * -----------------------------------------------------------------
 */
class VerticalScaleStyle: ScrollParallaxImageView.ParallaxStyle {
    private var finalScaleRatio = 0.7f

    fun VerticalScaleStyle() {

    }

    fun VerticalScaleStyle(finalScaleRatio: Float) {
        this.finalScaleRatio = finalScaleRatio
    }

    fun setFinalScaleRatio(scale: Float) {
        finalScaleRatio = scale
    }

    override fun transform(view: ScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        // view's width and height
        val vWidth = view.width - view.paddingLeft - view.paddingRight
        val vHeight = view.height - view.paddingTop - view.paddingBottom
        // device's height
        val dHeight = view.resources.displayMetrics.heightPixels

        if (vHeight >= dHeight) {
            // Do nothing if imageView's height is bigger than device's height.
            return
        }

        val scale: Float
        val pivot = (dHeight - vHeight) / 2
        if (y <= pivot) {
            scale = 2f * (1 - finalScaleRatio) * (y + vHeight).toFloat() / (dHeight + vHeight) + finalScaleRatio
        } else {
            scale = 2f * (1 - finalScaleRatio) * (dHeight - y).toFloat() / (dHeight + vHeight) + finalScaleRatio
        }

        canvas.scale(scale, scale, (vWidth / 2).toFloat(), (vHeight / 2).toFloat())
    }

    override fun onAttachedToImageView(view: ScrollParallaxImageView) {

    }

    override fun onDetachedFromImageView(view: ScrollParallaxImageView) {

    }
}