package com.victor.clips.ui.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.victor.clips.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ArcView.java
 * Author: Victor
 * Date: 2019/10/30 17:42
 * Description:
 * -----------------------------------------------------------------
 */
class ArcView: View {
    var mWidth:Int = 0;
    var mHeight:Int = 0;
    /**
     * 弧形高度
     */
    var mArcHeight:Int = 0;
    /**
     * 背景颜色
     */
    var mBgColor:Int = 0;
    var mPaint: Paint? = null;
    var mContext: Context? = null;

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(context,attrs)
    }

    fun initAttr (context: Context,attrs: AttributeSet?) {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcHeight, 0);
        mBgColor=typedArray.getColor(R.styleable.ArcView_bgColor, Color.parseColor("#303F9F"));

        mContext = context;
        mPaint = Paint();
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec);
        var widthMode = MeasureSpec.getMode(widthMeasureSpec);
        var heightSize = MeasureSpec.getSize(heightMeasureSpec);
        var heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mPaint?.setStyle(Paint.Style.FILL);
        mPaint?.setColor(mBgColor);

        var rect = Rect(0, 0, mWidth, mHeight - mArcHeight);
        canvas?.drawRect(rect, mPaint);


        var path = Path();
        path.moveTo(0f, (mHeight - mArcHeight).toFloat());
        path.quadTo(mWidth / 2f, mHeight.toFloat(), mWidth.toFloat(), (mHeight - mArcHeight).toFloat());
        canvas?.drawPath(path, mPaint);
    }
}