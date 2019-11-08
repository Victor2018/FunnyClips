package com.victor.clips.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.widget.ImageView
import com.victor.clips.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ArcImageView.java
 * Author: Victor
 * Date: 2019/10/30 17:42
 * Description:
 * -----------------------------------------------------------------
 */
class ArcImageView: ImageView {
    val TAG = "ArcImageView"
    var mArcHeight: Int = 100

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(context,attrs)
    }

    fun initAttr (context: Context,attrs: AttributeSet?) {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcImageView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcImageView_arcHeight, 0);
    }

    override fun onDraw(canvas: Canvas?) {
        var path = Path();
        path.moveTo(0f, 0f);
        path.lineTo(0f, height.toFloat());
        path.quadTo(getWidth() / 2f, getHeight() - 2f * mArcHeight, width.toFloat(), height.toFloat());
        path.lineTo(width.toFloat(), 0f);
        path.close();
        canvas?.clipPath(path);
        super.onDraw(canvas)
    }
}