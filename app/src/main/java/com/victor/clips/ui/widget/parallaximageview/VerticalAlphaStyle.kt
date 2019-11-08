package com.victor.clips.ui.widget.parallaximageview

import android.graphics.Canvas

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VerticalAlphaStyle.java
 * Author: Victor
 * Date: 2019/11/8 14:59
 * Description:
 * -----------------------------------------------------------------
 */
class VerticalAlphaStyle: ScrollParallaxImageView.ParallaxStyle {
    private var finalAlpha = 0.3f

    fun VerticalAlphaStyle() {

    }

    fun VerticalAlphaStyle(finalAlpha: Float) {
        if (finalAlpha < 0 || finalAlpha > 1.0f) {
            throw IllegalArgumentException("the alpha must between 0 and 1.")
        }
        this.finalAlpha = finalAlpha
    }

    fun setFinalAlpha(alpha: Float) {
        finalAlpha = alpha
    }

    override fun transform(view: ScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        // view's height
        val vHeight = view.height - view.paddingTop - view.paddingBottom
        // device's height
        val dHeight = view.resources.displayMetrics.heightPixels

        if (vHeight >= dHeight) {
            // Do nothing if imageView's height is bigger than device's height.
            return
        }

        val alpha: Float
        val pivot = (dHeight - vHeight) / 2
        if (y <= pivot) {
            alpha = 2f * (1 - finalAlpha) * (y + vHeight).toFloat() / (dHeight + vHeight) + finalAlpha
        } else {
            alpha = 2f * (1 - finalAlpha) * (dHeight - y).toFloat() / (dHeight + vHeight) + finalAlpha
        }
        view.alpha = alpha
    }

    override fun onAttachedToImageView(view: ScrollParallaxImageView) {

    }

    override fun onDetachedFromImageView(view: ScrollParallaxImageView) {

    }
}