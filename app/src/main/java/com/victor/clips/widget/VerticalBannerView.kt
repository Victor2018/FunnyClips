package com.victor.clips.widget

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ViewFlipper
import com.victor.clips.R
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.util.ImageUtils
import com.victor.clips.util.Loger


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VerticalBannerView.java
 * Author: Victor
 * Date: 2019/10/11 10:42
 * Description:
 * -----------------------------------------------------------------
 */
class VerticalBannerView: ViewFlipper {
    val TAG = "VerticalBannerView"
    var datas: MutableList<HomeItemInfo> = mutableListOf()
    var mOnFlipListener: OnFlipListener? = null
    var position:Int = 0;

   interface OnFlipListener{
       fun onShowNext(flipper: ViewFlipper,position: Int)
   }

    constructor(context: Context): this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        isAutoStart = false
        setFlipInterval(10000)
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_bottom_enter)
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_top_exit)

    }

    fun setData(list: MutableList<HomeItemInfo>?) {
        datas.addAll(list!!)
        for (item in list) {
            var mIvBanner = ImageView(context)
            mIvBanner.scaleType = ImageView.ScaleType.CENTER_CROP
            ImageUtils.instance.imageGauss(context!!,mIvBanner, item!!.data!!.cover!!.feed)
            addView(mIvBanner)
        }
    }

    override fun showNext() {
        super.showNext()
        position++
        if (position >= datas.size) {
            position = 0
        }
        mOnFlipListener?.onShowNext(this,position)
        Loger.e(TAG,"showNext-position = " + position)
    }

    fun pausePlay () {
        stopFlipping()
    }

    fun resumePlay() {
        startFlipping()
    }

    fun destroy() {
        pausePlay()
    }

    fun setOnFlipListener (listener: OnFlipListener) {
        mOnFlipListener = listener
    }
}