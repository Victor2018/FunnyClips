package com.victor.clips.interfaces

import android.view.View

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GestureListener.java
 * Author: Victor
 * Date: 2018/9/6 11:00
 * Description: 
 * -----------------------------------------------------------------
 */
interface GestureListener {
    fun onClick(view: View)
    fun onDismiss(view: View)
    fun onScale(percentage: Float)
    fun onSwipe(percentage: Float)
    fun onExpand(isExpanded: Boolean)
}