package com.victor.clips.util

import android.view.Gravity
import android.support.v4.view.WindowInsetsCompat
import android.annotation.SuppressLint



/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WindowInsetsCompatUtil.java
 * Author: Victor
 * Date: 2019/10/14 16:25
 * Description:
 * -----------------------------------------------------------------
 */
class WindowInsetsCompatUtil {
    companion object {
        fun copy(source: WindowInsetsCompat): WindowInsetsCompat {
            return copyExcluded(source, Gravity.NO_GRAVITY)
        }

        @SuppressLint("RtlHardcoded")
        fun copyExcluded(source: WindowInsetsCompat, gravity: Int): WindowInsetsCompat {
            val l = if (gravity and Gravity.LEFT == Gravity.LEFT) 0 else source.systemWindowInsetLeft
            val t = if (gravity and Gravity.TOP == Gravity.TOP) 0 else source.systemWindowInsetTop
            val r = if (gravity and Gravity.RIGHT == Gravity.RIGHT) 0 else source.systemWindowInsetRight
            val b = if (gravity and Gravity.BOTTOM == Gravity.BOTTOM) 0 else source.systemWindowInsetBottom
            return source.replaceSystemWindowInsets(l, t, r, b)
        }
    }
}