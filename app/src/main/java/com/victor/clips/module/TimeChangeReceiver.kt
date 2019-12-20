package com.victor.clips.module

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Message
import com.victor.clips.util.Constant
import com.victor.clips.util.Loger
import org.victor.funny.util.ToastUtils
import org.victor.khttp.library.util.MainHandler
import java.text.SimpleDateFormat
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TimeChangeReceiver
 * Author: Victor
 * Date: 2019/12/20 14:17
 * Description: 
 * -----------------------------------------------------------------
 */

class TimeChangeReceiver: BroadcastReceiver() {
    var TAG = "TimeChangeReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_TIME_TICK -> {
                var isDayLight = isTimeDayLight(System.currentTimeMillis())
                Loger.e(TAG,"isDayLight = " + isDayLight)
                var msg = MainHandler.get().obtainMessage(Constant.Msg.TIME_CHANGE)
                msg.arg1 = if (isDayLight) 1 else 0
                MainHandler.get().sendMessage(msg);
            }
        }
    }

    /*
     * 是否是白天
     */
    fun isTimeDayLight(currentTime: Long): Boolean {
        val nowDate = Date(currentTime)
        val simpleDate = SimpleDateFormat("HH:MM", Locale.CHINA)
        val nowTime = simpleDate.format(nowDate)
        val startTime = simpleDate.format(simpleDate.parse("7:00"))
        val endTime = simpleDate.format(simpleDate.parse("22:00"))
        if (nowTime in startTime..endTime) {
            return true
        }
        return false

    }

}