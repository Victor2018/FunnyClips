package org.victor.funny.util

import android.support.annotation.IntegerRes
import android.widget.Toast
import com.victor.clips.app.App
import com.victor.clips.util.AppConfig

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ToastUtils.java
 * Author: Victor
 * Date: 2018/9/4 11:34
 * Description: 吐司工具类
 * -----------------------------------------------------------------
 */
class ToastUtils {
    companion object {

        /**
         * 调试模式下可显示
         *
         * @param msg
         */
        fun showDebug(msg: String) {
            if (AppConfig.MODEL_DEBUG) {
                Toast.makeText(App.get(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * 调试模式下可显示
         *
         * @param resId
         */
        fun showDebug(@IntegerRes resId: Int) {
            if (AppConfig.MODEL_DEBUG) {
                val text = ResUtils.getStringRes(resId)
                Toast.makeText(App.get(), text, Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * 短暂显示
         *
         * @param msg
         */
        fun showShort(msg: CharSequence) {
            Toast.makeText(App.get(), msg, Toast.LENGTH_SHORT).show()
        }

        /**
         * 短暂显示
         *
         * @param resId
         */
        fun showShort(resId: Int) {
            val text = ResUtils.getStringRes(resId)
            Toast.makeText(App.get(), text, Toast.LENGTH_SHORT).show()
        }

        /**
         * 长时间显示
         *
         * @param msg
         */
        fun showLong(msg: CharSequence) {
            Toast.makeText(App.get(), msg, Toast.LENGTH_LONG).show()
        }

        /**
         * 短暂显示
         *
         * @param resId
         */
        fun showLong(resId: Int) {
            val text = ResUtils.getStringRes(resId)
            Toast.makeText(App.get(), text, Toast.LENGTH_LONG).show()
        }
    }
}