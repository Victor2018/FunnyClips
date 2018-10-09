package com.victor.clips.util

import android.view.ViewOutlineProvider
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.victor.clips.holder.HeaderItem
import com.victor.clips.widget.DefaultItemTransformer
import com.victor.clips.widget.HeaderLayout
import com.victor.clips.widget.HeaderLayoutManager
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: HeaderItemTransformer.java
 * Author: Victor
 * Date: 2018/9/12 10:26
 * Description: 
 * -----------------------------------------------------------------
 */
class HeaderItemTransformer(
        private val headerOverlay: FrameLayout,
        private val titleLeftOffset: Int,
        private val lineRightOffset: Int,
        private val lineBottomOffset: Int,
        private val lineTitleOffset: Int) : DefaultItemTransformer() {
    private var prevChildCount = Int.MIN_VALUE
    private var prevHScrollOffset = Int.MIN_VALUE
    private var prevVScrollOffset = Int.MIN_VALUE
    private var prevHeaderBottom = Int.MIN_VALUE

    private var isOverlayLaidout = false

    override fun transform(lm: HeaderLayoutManager, header: HeaderLayout, headerBottom: Int) {
        super.transform(lm, header, headerBottom)

        if (!isOverlayLaidout) {
            headerOverlay.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    headerOverlay.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    isOverlayLaidout = true
                    transformOverlay(header)
                }
            })
            return
        }

        if (!checkForChanges(header, headerBottom)) {
            return
        }

        transformOverlay(header)
    }

    private fun checkForChanges(header: HeaderLayout, headerBottom: Int): Boolean {
        val childCount = header.childCount
        if (childCount == 0) {
            return false
        }

        val (hs, vs) = header.getChildAt(0).let { it.left to it.top }
        val nothingChanged =
                hs == prevHScrollOffset && vs == prevVScrollOffset
                        && childCount == prevChildCount
                        && prevHeaderBottom == headerBottom
        if (nothingChanged) {
            return false
        }

        prevChildCount = childCount
        prevHScrollOffset = hs
        prevVScrollOffset = vs
        prevHeaderBottom = headerBottom

        return true
    }

    private fun transformOverlay(header: HeaderLayout) {
        val invertedBottomRatio = 1f - currentRatioBottomHalf
        val headerCenter = header.width / 2f

        val lineAlpha = (abs((min(0.8f, max(0.2f, currentRatioBottomHalf)) - 0.2f) / 0.6f - 0.5f) / 0.5f).pow(11)

        for (i in 0 until header.childCount) {
            val card = header.getChildAt(i)
            val holder = HeaderLayout.getChildViewHolder(card) as HeaderItem

            val cardWidth = card.width
            val cardHeight = card.height
            val cardCenterX = card.x + cardWidth / 2
            val cardCenterY = card.y + cardHeight / 2

            val ratioHorizontalPosition = (card.x / cardWidth) * invertedBottomRatio
            val ratioHorizontalOffset = (1f - min(headerCenter, abs(headerCenter - cardCenterX)) / headerCenter * invertedBottomRatio)
            val alphaTitle = 0.7f + 0.3f * ratioHorizontalOffset

            holder.overlayTitle?.also { title ->
                val titleLeft = card.x + titleLeftOffset
                val titleCenter = cardCenterX - title.width / 2
                val titleCurrentLeft = titleLeft + (titleCenter - titleLeft) * invertedBottomRatio
                val titleTop = cardCenterY - title.height / 2
                val titleOffset = (-ratioHorizontalPosition * cardWidth / 2) * currentRatioTopHalf

                title.x = titleCurrentLeft + titleOffset
                title.y = titleTop
                title.alpha = alphaTitle
                title.scaleX = min(1f, 0.8f + 0.2f * ratioHorizontalOffset)
                title.scaleY = title.scaleX
            }

            holder.overlayLine?.also { line ->
                val lineWidth = line.width
                val lineHeight = line.height
                val lineLeft = cardCenterX - lineWidth / 2
                val lineTop = cardCenterY + (holder.overlayTitle?.let { it.height / 2 + lineTitleOffset } ?: 0)
                val hBottomOffset = ((card.right - lineRightOffset - lineWidth) - lineLeft) * currentRatioBottomHalf
                val hTopOffset = -ratioHorizontalPosition * cardWidth / 1.1f * (1f - currentRatioTopHalf)
                val vOffset = ((card.bottom - lineBottomOffset - lineHeight) - lineTop) * currentRatioBottomHalf
                line.x = lineLeft + hBottomOffset + hTopOffset
                line.y = lineTop + vOffset
                line.alpha = if (currentRatioTopHalf == 1f) lineAlpha else alphaTitle
            }

            val background = holder.backgroundLayout
            if (currentRatioBottomHalf != 0f) {
                background.translationX = 0f
                background.alpha = 1f
                card.outlineProvider = ViewOutlineProvider.BOUNDS
            } else {
                card.outlineProvider = null
                if (ratioHorizontalPosition <= -1f || ratioHorizontalPosition >= 1f) {
                    background.translationX = cardWidth * ratioHorizontalPosition
                    background.alpha = 0f
                } else if (ratioHorizontalPosition == 0f) {
                    background.translationX = cardWidth * ratioHorizontalPosition
                    background.alpha = 1f
                } else {
                    background.translationX = cardWidth * -ratioHorizontalPosition
                    background.alpha = 1f - abs(ratioHorizontalPosition)
                }
            }
        }
    }
}