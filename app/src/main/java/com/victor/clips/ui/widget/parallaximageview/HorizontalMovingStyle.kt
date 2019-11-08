package com.victor.clips.ui.widget.parallaximageview

import android.graphics.Canvas
import android.widget.ImageView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HorizontalMovingStyle.java
 * Author: Victor
 * Date: 2019/11/8 14:56
 * Description:
 * -----------------------------------------------------------------
 */
class HorizontalMovingStyle:  ScrollParallaxImageView.ParallaxStyle {

    override fun onAttachedToImageView(view: ScrollParallaxImageView) {
        // only supports CENTER_CROP
        view.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    override fun onDetachedFromImageView(view: ScrollParallaxImageView) {

    }

    override fun transform(view: ScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        var x = x
        if (view.scaleType !== ImageView.ScaleType.CENTER_CROP) {
            return
        }

        // image's width and height
        val iWidth = view.drawable.intrinsicWidth
        val iHeight = view.drawable.intrinsicHeight
        if (iWidth <= 0 || iHeight <= 0) {
            return
        }

        // view's width and height
        val vWidth = view.width - view.paddingLeft - view.paddingRight
        val vHeight = view.height - view.paddingTop - view.paddingBottom

        // device's width
        val dWidth = view.resources.displayMetrics.widthPixels

        if (iWidth * vHeight > iHeight * vWidth) {
            // avoid over scroll
            if (x < -vWidth) {
                x = -vWidth
            } else if (x > dWidth) {
                x = dWidth
            }

            val imgScale = vHeight.toFloat() / iHeight.toFloat()
            val max_dx = Math.abs((iWidth * imgScale - vWidth) * 0.5f)
            val translateX = -(2f * max_dx * x.toFloat() + max_dx * (vWidth - dWidth)) / (vWidth + dWidth)
            canvas.translate(translateX, 0f)
        }
    }
}