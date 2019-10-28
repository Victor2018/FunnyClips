package com.victor.clips.util

import android.content.Context
import android.util.Log
import com.victor.clips.data.Theme


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SharePreferencesUtil.java
 * Author: Victor
 * Date: 2019/10/24 16:29
 * Description:
 * -----------------------------------------------------------------
 */
class SharePreferencesUtil {
    companion object {
        private val TAG = "SharePreferencesUtil"

        fun putInt(context: Context, key: String, value: Int) {
            Log.e(TAG, "putInt()-$key=$value")
            val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
            val ed = sp.edit()
            ed.putInt(key, value)
            ed.commit()
        }

        fun getInt(context: Context, key: String): Int {
            val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
            val value = sp.getInt(key, 0)
            Log.e(TAG, "putInt()-$key=$value")
            return value
        }

        fun putLong(context: Context, key: String, value: Long) {
            Log.e(TAG, "putLong()-$key=$value")
            val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
            val ed = sp.edit()
            ed.putLong(key, value)
            ed.commit()
        }

        fun getLong(context: Context, key: String): Long? {
            val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
            val value = sp.getLong(key, 0)
            Log.e(TAG, "getLong()-$key=$value")
            return value
        }

        fun putString(context: Context, key: String, value: String) {
            Log.e(TAG, "putString()-$key=$value")
            val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
            val ed = sp.edit()
            ed.putString(key, value)
            ed.commit()
        }

        fun getString(context: Context, key: String, defaultValue: String): String {
            val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
            val value = sp.getString(key, defaultValue)
            Log.e(TAG, "getString()-$key=$value")
            return value
        }

        fun putBoolean(context: Context, key: String, value: Boolean) {
            Log.e(TAG, "putBoolean()-$key=$value")
            val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
            val ed = sp.edit()
            ed.putBoolean(key, value)
            ed.commit()
        }

        fun getBoolean(context: Context, key: String): Boolean {
            val sp = context.getSharedPreferences(Constant.MA_DATA, Context.MODE_PRIVATE)
            val value = sp.getBoolean(key, false)
            Log.e(TAG, "getBoolean()-$key=$value")
            return value
        }

        fun getCurrentTheme(context: Context): Theme {
            return Theme.valueOf(getString(context, "app_theme", Theme
                    .DeepOrange.name))
        }

        fun setCurrentTheme(context: Context, currentTheme: Theme) {
            putString(context, "app_theme", currentTheme.name)
        }
    }
}