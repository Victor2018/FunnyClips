package com.victor.clips.util

import android.content.Context

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ConfigLocal.java
 * Author: Victor
 * Date: 2019/11/8 11:15
 * Description:
 * -----------------------------------------------------------------
 */
class ConfigLocal {
    companion object {
        private val WELCOME_VIDEO_PLAY_GUIDE    = "WELCOME_VIDEO_PLAY_GUIDE"
        private val DAY_NIGHT_GUIDE             = "DAY_NIGHT_GUIDE"

        /**
         * 是否需要播放开场欢迎视频
         * @param context
         * @param userId
         * @return
         */
        fun needPlayWelcomeVideoGuide (context: Context, userId: String): Boolean {
            return SharePreferencesUtil.getBoolean(context, WELCOME_VIDEO_PLAY_GUIDE + ":" + userId, true)
        }
        fun updateWelcomeVideoPlayGuide (context: Context, userId: String,enable: Boolean) {
            return SharePreferencesUtil.putBoolean(context, WELCOME_VIDEO_PLAY_GUIDE + ":" + userId, enable)
        }
        /**
         * 是否需要显示昼夜模式
         * @param context
         * @param userId
         * @return
         */
        fun needShowDayNightThemeGuide (context: Context, userId: String): Boolean {
            return SharePreferencesUtil.getBoolean(context, DAY_NIGHT_GUIDE + ":" + userId, false)
        }
        fun updateDayNightThemeGuide (context: Context, userId: String,enable: Boolean) {
            return SharePreferencesUtil.putBoolean(context, DAY_NIGHT_GUIDE + ":" + userId, enable)
        }
    }
}