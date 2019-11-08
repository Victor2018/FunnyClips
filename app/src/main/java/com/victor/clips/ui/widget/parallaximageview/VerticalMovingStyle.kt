package com.victor.clips.ui.widget.parallaximageview

import android.graphics.Canvas
import android.widget.ImageView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VerticalMovingStyle.java
 * Author: Victor
 * Date: 2019/11/8 15:00
 * Description:
 * -----------------------------------------------------------------
 */
class VerticalMovingStyle: ScrollParallaxImageView.ParallaxStyle {

    override fun onAttachedToImageView(view: ScrollParallaxImageView) {
        // only supports CENTER_CROP
        view.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    override fun onDetachedFromImageView(view: ScrollParallaxImageView) {

    }

    override fun transform(view: ScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        var y = y
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

        // device's height
        val dHeight = view.resources.displayMetrics.heightPixels

        if (iWidth * vHeight < iHeight * vWidth) {
            // avoid over scroll
            if (y < -vHeight) {
                y = -vHeight
            } else if (y > dHeight) {
                y = dHeight
            }

            val imgScale = vWidth.toFloat() / iWidth.toFloat()
            val max_dy = Math.abs((iHeight * imgScale - vHeight) * 0.5f)
            val translateY = -(2f * max_dy * y.toFloat() + max_dy * (vHeight - dHeight)) / (vHeight + dHeight)
            canvas.translate(0f, translateY)
        }
    }
}