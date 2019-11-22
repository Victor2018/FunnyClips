package com.victor.clips.util

import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import com.victor.clips.R
import com.victor.clips.app.App
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ColorUtil.java
 * Author: Victor
 * Date: 2018/9/4 9:40
 * Description: 
 * -----------------------------------------------------------------
 */
class ColorUtil {
    companion object {
        fun getDefaultRandomColor(): Int {
            val colors = intArrayOf(
                    App.get().getResources().getColor(R.color.color_E6FCFC),
                    App.get().getResources().getColor(R.color.color_E6FBFD),
                    App.get().getResources().getColor(R.color.color_E5FAFE),
                    App.get().getResources().getColor(R.color.color_E5F9FE))
            val random = Random()
            return colors[random.nextInt(colors.size)]
        }

        /**
         * Set the alpha component of `color` to be `alpha`.
         */
        fun modifyAlpha(@ColorInt color: Int, alpha: Int): Int {
            return color and 0x00ffffff or (alpha shl 24)
        }

        /**
         * Set the alpha component of `color` to be `alpha`.
         */
        fun modifyAlpha(@ColorInt color: Int, alpha: Float): Int {
            return modifyAlpha(color, (255f * alpha).toInt())
        }
    }
}