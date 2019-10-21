package com.victor.clips.interfaces

import android.graphics.Rect
import android.support.design.widget.CoordinatorLayout
import android.view.View

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WindowInsetsHandlingBehavior.java
 * Author: Victor
 * Date: 2019/10/14 16:24
 * Description:
 * -----------------------------------------------------------------
 */
interface WindowInsetsHandlingBehavior {
    fun onApplyWindowInsets(layout: CoordinatorLayout, child: View, insets: Rect): Boolean
}