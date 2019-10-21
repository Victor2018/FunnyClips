package com.victor.clips.interfaces

import android.graphics.Rect

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WindowInsetsHandler.java
 * Author: Victor
 * Date: 2019/10/14 16:09
 * Description:
 * -----------------------------------------------------------------
 */
interface WindowInsetsHandler {
    fun onApplyWindowInsets(insets: Rect): Boolean
}