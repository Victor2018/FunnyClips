package com.victor.clips.util

import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DateUtil.java
 * Author: Victor
 * Date: 2018/10/25 11:40
 * Description: 
 * -----------------------------------------------------------------
 */
class DateUtil {
    companion object {
        /**
         * 将毫秒数格式化为"##:##"的时间
         *
         * @param milliseconds 毫秒数
         * @return ##:##
         */
        fun formatPlayTime(milliseconds: Long): String {
            val totalSeconds = milliseconds / 1000
            val seconds = totalSeconds % 60
            val minutes = totalSeconds / 60 % 60
            val hours = totalSeconds / 3600
            val stringBuilder = StringBuilder()
            val mFormatter = Formatter(stringBuilder, Locale.getDefault())
            return if (hours > 0) {
                mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
            } else {
                mFormatter.format("%02d:%02d", minutes, seconds).toString()
            }
        }
    }
}