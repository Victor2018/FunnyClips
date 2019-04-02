package com.victor.clips.util

import com.victor.clips.BuildConfig

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppConfig.java
 * Author: Victor
 * Date: 2018/9/4 10:22
 * Description: 
 * -----------------------------------------------------------------
 */
class AppConfig {
    companion object {
        /**
         * 开发者模式
         */
        const val MODEL_DEBUG = BuildConfig.MODEL_DEBUG
        /**
         * 线上模式
         */
        const val MODEL_ONLINE = BuildConfig.MODEL_ONLINE

        /**
         * 编译版本
         */
        const val BUILD_CODE = BuildConfig.BUILD_CODE
    }
}