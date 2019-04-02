package com.victor.clips.module

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import com.victor.clips.util.Constant
import com.victor.clips.util.SpiderActions
import java.util.HashMap

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SpiderHelper.java
 * Author: Victor
 * Date: 2018/9/28 10:02
 * Description: 
 * -----------------------------------------------------------------
 */
class SpiderHelper (var context: Context) {
    var TAG = "SpiderHelper"
    var mRequestHandler: Handler? = null
    var mRequestHandlerThread: HandlerThread? = null

    init {
        startRequestTask()
    }

    fun startRequestTask () {
        mRequestHandlerThread = HandlerThread("HttpRequestTask")
        mRequestHandlerThread?.start()
        mRequestHandler = object : Handler(mRequestHandlerThread!!.getLooper()) {
            override fun handleMessage(msg: Message) {
                var parmsMap = msg.obj as HashMap<Int, Any>
                when (msg.what) {
                    Constant.Msg.REQUEST_YOUTUBE -> {
                        val youtubeUrl = parmsMap[Constant.Msg.REQUEST_YOUTUBE] as String
                        SpiderActions.spideYoutubeData(context, youtubeUrl)
                    }
                    Constant.Msg.REQUEST_VIMEO -> {
                        val vimeoUrl = parmsMap[Constant.Msg.REQUEST_VIMEO] as String
                        SpiderActions.spideVimeoData(context, vimeoUrl)
                    }
                }
            }
        }
    }

    fun sendRequestWithParms(Msg: Int, requestData: Any) {
        val requestMap = HashMap<Int, Any>()
        requestMap[Msg] = requestData
        val msg = mRequestHandler!!.obtainMessage(Msg, requestMap)
        mRequestHandler!!.sendMessage(msg)
    }

    fun sendRequest(msg: Int) {
        mRequestHandler!!.sendEmptyMessage(msg)
    }

    fun onDestroy() {
        if (mRequestHandlerThread != null) {
            mRequestHandlerThread!!.quit()
            mRequestHandlerThread = null
        }
    }
}