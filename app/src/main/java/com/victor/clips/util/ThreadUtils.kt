package com.victor.clips.util

import android.os.Handler
import android.os.Looper

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ThreadUtils.java
 * Author: Victor
 * Date: 2018/9/28 14:27
 * Description: 
 * -----------------------------------------------------------------
 */
class ThreadUtils {
    companion object {
        fun runOnUiThread(r: Runnable) {
            if (isMainThread()) {
                r.run()
            } else {
                LazyHolder.sUiThreadHandler.post(r)
            }
        }

        fun runOnUiThread(r: Runnable, delay: Long) {
            LazyHolder.sUiThreadHandler.postDelayed(r, delay)
        }

        fun removeCallbacks(r: Runnable) {
            LazyHolder.sUiThreadHandler.removeCallbacks(r)
        }

        private fun isMainThread(): Boolean {
            return Looper.getMainLooper() == Looper.myLooper()
        }

        private object LazyHolder {
            val sUiThreadHandler = Handler(Looper.getMainLooper())
        }
    }

}