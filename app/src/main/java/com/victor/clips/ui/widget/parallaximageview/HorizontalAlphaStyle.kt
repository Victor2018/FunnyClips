package com.victor.clips.ui.widget.parallaximageview

import android.graphics.Canvas

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HorizontalAlphaStyle.java
 * Author: Victor
 * Date: 2019/11/8 14:52
 * Description:
 * -----------------------------------------------------------------
 */
class HorizontalAlphaStyle: ScrollParallaxImageView.ParallaxStyle {
    private var finalAlpha = 0.3f


    fun HorizontalAlphaStyle() {
    }

    fun HorizontalAlphaStyle(finalAlpha: Float) {
        if (finalAlpha < 0 || finalAlpha > 1.0f) {
            throw IllegalArgumentException("the alpha must between 0 and 1.")
        }
        this.finalAlpha = finalAlpha
    }

    fun setFinalAlpha(alpha: Float) {
        finalAlpha = alpha
    }

    override fun transform(view: ScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        // view's width
        val vWidth = view.width - view.paddingLeft - view.paddingRight
        // device's width
        val dWidth = view.resources.displayMetrics.widthPixels

        if (vWidth >= dWidth) {
            // Do nothing if imageView's width is bigger than device's width.
            return
        }

        val alpha: Float
        val pivot = (dWidth - vWidth) / 2
        if (x <= pivot) {
            alpha = 2f * (1 - finalAlpha) * (x + vWidth).toFloat() / (dWidth + vWidth) + finalAlpha
        } else {
            alpha = 2f * (1 - finalAlpha) * (dWidth - x).toFloat() / (dWidth + vWidth) + finalAlpha
        }
        view.alpha = alpha
    }

    override fun onAttachedToImageView(view: ScrollParallaxImageView) {

    }

    override fun onDetachedFromImageView(view: ScrollParallaxImageView) {

    }
}