package com.victor.clips.ui.widget.parallaximageview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.ImageView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ScrollParallaxImageView.java
 * Author: Victor
 * Date: 2019/11/8 14:47
 * Description:
 * -----------------------------------------------------------------
 */
class ScrollParallaxImageView: ImageView, ViewTreeObserver.OnScrollChangedListener {
    private val viewLocation = IntArray(2)
    private var enableScrollParallax = true

    private var parallaxStyles: ParallaxStyle? = null

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        if (!enableScrollParallax || drawable == null) {
            super.onDraw(canvas)
            return
        }

        getLocationInWindow(viewLocation)
        parallaxStyles?.transform(this, canvas!!, viewLocation[0], viewLocation[1])
        super.onDraw(canvas)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnScrollChangedListener(this)
    }

    override fun onDetachedFromWindow() {
        viewTreeObserver.removeOnScrollChangedListener(this)
        super.onDetachedFromWindow()
    }

    override fun onScrollChanged() {
        if (enableScrollParallax) {
            invalidate()
        }
    }

    fun setParallaxStyles(styles: ParallaxStyle) {
        parallaxStyles?.onDetachedFromImageView(this)
        parallaxStyles = styles
        parallaxStyles?.onAttachedToImageView(this)
    }

    fun setEnableScrollParallax(enableScrollParallax: Boolean) {
        this.enableScrollParallax = enableScrollParallax
    }

    interface ParallaxStyle {
        fun onAttachedToImageView(view: ScrollParallaxImageView)
        fun onDetachedFromImageView(view: ScrollParallaxImageView)
        fun transform(view: ScrollParallaxImageView, canvas: Canvas, x: Int, y: Int)
    }
}