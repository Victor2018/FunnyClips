package com.victor.clips.util

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import com.victor.clips.interfaces.WindowInsetsHandler



/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WindowInsetsHelper.java
 * Author: Victor
 * Date: 2019/10/14 16:20
 * Description:
 * -----------------------------------------------------------------
 */
class WindowInsetsHelper {
    companion object {
        fun onApplyWindowInsets(view: View, insets: Rect): Boolean {
            return view is WindowInsetsHandler && (view as WindowInsetsHandler).onApplyWindowInsets(insets)
        }

        fun dispatchApplyWindowInsets(parent: ViewGroup, insets: Rect): Boolean {
            val count = parent.childCount

            for (i in 0 until count) {
                if (onApplyWindowInsets(parent.getChildAt(i), insets)) {
                    return true
                }
            }

            return false
        }
    }
}