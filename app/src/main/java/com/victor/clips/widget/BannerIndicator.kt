package com.victor.clips.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.victor.clips.R
import com.victor.clips.util.Loger

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BannerIndicator.java
 * Author: Victor
 * Date: 2019/10/11 15:06
 * Description:
 * -----------------------------------------------------------------
 */
class BannerIndicator: LinearLayout {
    val TAG = "BannerIndicator"
    var count: Int = 6
    var mIndicatorMargin = 8

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    fun setBannerCount (count: Int) {
        this.count = count
        setPosition(0)
    }

    fun setPosition(positon: Int) {
        removeAllViews()
        for (i in 0 until count) {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            if (i == positon) {
                imageView.setImageResource(R.drawable.selected)
            } else {
                imageView.setImageResource(R.drawable.unselected)
            }
            val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            lp.setMargins(mIndicatorMargin, 0, mIndicatorMargin, 0)
            imageView.layoutParams = lp
            addView(imageView)
        }
    }

}