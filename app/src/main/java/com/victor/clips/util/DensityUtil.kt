package com.victor.clips.util

import android.app.Activity
import android.content.Context
import android.view.WindowManager

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: DensityUtil.java
 * Author: Victor
 * Date: 2018/9/28 14:32
 * Description: 
 * -----------------------------------------------------------------
 */
class DensityUtil {
    companion object {
        /**
         * 获取屏幕的高度
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val manager = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            return display.height
        }

        fun getScreenHeight(activity: Activity): Int {
            return activity.windowManager.defaultDisplay.height
        }

        fun getScreenWidth(activity: Activity): Int {
            return activity.windowManager.defaultDisplay.width
        }

        /**
         * 根据手机的分辨率 dp 的单位 转成 px(像素)
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率 px(像素) 的单位 转成 dp
         */
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }
    }
}