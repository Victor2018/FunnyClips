package com.victor.clips.util

import android.util.Log

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Loger.java
 * Author: Victor
 * Date: 2019/11/5 16:25
 * Description: 日志打印工具
 * -----------------------------------------------------------------
 */
class Loger {
    companion object {
        fun d(TAG: String, msg: Any) {
            if (AppConfig.MODEL_DEBUG) {
                Log.d(TAG, msg.toString())
            }
        }

        fun e(TAG: String, msg: Any) {
            if (AppConfig.MODEL_DEBUG) {
                Log.e(TAG, msg.toString())
            }
        }

        fun i(TAG: String, msg: Any) {
            if (AppConfig.MODEL_DEBUG) {
                Log.i(TAG, msg.toString())
            }
        }
    }
}