package com.victor.clips.widget

import android.view.GestureDetector
import android.view.MotionEvent

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: GestureControl.java
 * Author: Victor
 * Date: 2018/9/6 11:07
 * Description: 
 * -----------------------------------------------------------------
 */
class GestureControl : GestureDetector.SimpleOnGestureListener() {
    override fun onSingleTapUp(event: MotionEvent): Boolean {
        return true
    }
}