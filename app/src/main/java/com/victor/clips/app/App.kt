package com.victor.clips.app

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
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
        fun get() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CrashReport.initCrashReport(getApplicationContext());
    }
}