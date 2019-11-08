package com.victor.clips.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.victor.clips.R
import com.victor.clips.util.BitmapRoundCornerUtil

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: RoundRectImageView.java
 * Author: Victor
 * Date: 2019/10/30 14:03
 * Description:
 * -----------------------------------------------------------------
 */
class RoundRectImageView: ImageView {
    var TAG = "RoundRectImageView"

    var DEFAULT_CORNER_RADIUS = 20
    var mCornerRadius = DEFAULT_CORNER_RADIUS
    var paint: Paint? = null

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(context,attrs)
    }

    fun initAttr(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val a = context.obtainStyledAttributes(attrs, R.styleable.RoundRectImageView) ?: return
        val n = a.indexCount
        for (i in 0 until n) {
            val attr = a.getIndex(i)
            if (attr == R.styleable.RoundRectImageView_rriv_CornerRadius) {
                mCornerRadius = a.getDimensionPixelSize(attr, DEFAULT_CORNER_RADIUS)
            }
        }
        a.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        val drawable = drawable
        if (drawable != null) {
            val bitmap = drawableToBitmap(drawable)
            val b = BitmapRoundCornerUtil.getRoundCornerBitmap(bitmap, mCornerRadius.toFloat())
            val rectSrc = Rect(0, 0, b.getWidth(), b.getHeight())
            val rectDest = Rect(0, 0, width, height)
            paint?.reset()
            canvas?.drawBitmap(b, rectSrc, rectDest, paint)

        } else {
            super.onDraw(canvas)
        }
    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }


}