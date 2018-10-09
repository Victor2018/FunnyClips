package com.victor.clips.app

import android.app.Application

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: App.kt
 * Author: Victor
 * Date: 2018/8/16 11:38
 * Description: 
 * -----------------------------------------------------------------
 */
class App : Application () {
    companion object {
        private var instance : App ?= null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}