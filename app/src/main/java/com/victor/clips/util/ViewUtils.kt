package com.victor.clips.util

import android.content.Context

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ViewUtils
 * Author: Victor
 * Date: 2019/11/22 11:54
 * Description: 
 * -----------------------------------------------------------------
 */

class ViewUtils {
    companion object {
        /**
         * Determine if the navigation bar will be on the bottom of the screen, based on logic in
         * PhoneWindowManager.
         */
        fun isNavBarOnBottom(context: Context): Boolean {
            val res = context.resources
            val cfg = context.resources.configuration
            val dm = res.displayMetrics
            val canMove = dm.widthPixels != dm.heightPixels && cfg.smallestScreenWidthDp < 600
            return !canMove || dm.widthPixels < dm.heightPixels
        }
    }
}