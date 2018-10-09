package com.victor.clips.util

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.victor.clips.data.YoutubeInfo
import com.victor.clips.data.YoutubeReq
import com.victor.clips.module.DataObservable
import com.victor.player.library.util.PlayUtil
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.ArrayList

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: SpiderActions.java
 * Author: Victor
 * Date: 2018/9/28 10:15
 * Description: 
 * -----------------------------------------------------------------
 */
class SpiderActions {
    companion object {

        var TAG = "SpiderActions"
        fun spideYoutubeData(context: Context, url: String) {
            Log.e(TAG, "spideYoutubeData()......url = $url")
            var status = 0
            val youtubeReq = YoutubeReq()
            val datas = ArrayList<YoutubeInfo>()
            if (HttpUtil.isNetEnable(context)) {
                try {
                    val document = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")
                            .timeout(10000).get()
                    if (document != null) {
                        val result = document.toString()
                        if (!TextUtils.isEmpty(result)) {
                            if (result.contains("\n")) {
                                val items = document.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                                for (i in items.indices) {
                                    if (items[i].contains("itemListElement")) {
                                        val res = JSONObject(items[i])
                                        if (res != null) {
                                            val list = res.getJSONArray("itemListElement")
                                            if (list != null && list.length() > 0) {
                                                for (j in 0 until list.length()) {
                                                    val cell = list.getJSONObject(j)
                                                    val item = cell.getJSONObject("item")
                                                    if (item != null) {
                                                        val itemArry = item.getJSONArray("itemListElement")
                                                        if (itemArry != null && itemArry.length() > 0) {
                                                            for (k in 0 until itemArry.length()) {
                                                                val info = itemArry.getJSONObject(k)
                                                                val data = YoutubeInfo()
                                                                data.url = info.optString("url")
                                                                if (!TextUtils.isEmpty(data.url)) {
                                                                    data.poster = String.format(Constant.YOUTUBE_IMG_URL, PlayUtil.getVideoId(data.url))
                                                                }
                                                                datas.add(data)
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                youtubeReq.data = datas
                            }
                        }
                    }
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                    status = Constant.Msg.SOCKET_TIME_OUT
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } else {
                status = Constant.Msg.NETWORK_ERROR
            }
            youtubeReq.status = status
            DataObservable.instance.setData(youtubeReq)
        }

        fun spideVimeoData(context: Context, url: String) {
            Log.e(TAG, "requestVimeoData()......url = $url")
            var status = 0
            val youtubeReq = YoutubeReq()
            val datas = ArrayList<YoutubeInfo>()
            if (HttpUtil.isNetEnable(context)) {
                try {
                    val document = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")
                            .timeout(30000).get()
                    if (document != null) {
                        val result = document.toString()
                        Log.e(TAG,"result = ${result}")
                        if (!TextUtils.isEmpty(result)) {
                            if (result.contains("\n")) {
                                val items = document.toString().split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                                for (i in items.indices) {
                                    if (items[i].contains("explore_data")) {
                                        val json = items[i].replace("vimeo.explore_data", "").replace("=", "").replace(";", "")
                                        val res = JSONObject(json)
                                        if (res != null) {
                                            val modules = res.getJSONArray("modules")
                                            if (modules != null && modules.length() > 0) {
                                                for (j in 0 until modules.length()) {
                                                    val module = modules.getJSONObject(j)
                                                    val collectionItems = module.optJSONArray("collection_items")
                                                    if (collectionItems != null && collectionItems.length() > 0) {
                                                        for (k in 0 until collectionItems.length()) {
                                                            val item = collectionItems.getJSONObject(k)
                                                            val info = YoutubeInfo()
                                                            info.url = item.optString("url").replace("/", "")
                                                            info.poster = item.optString("thumbnail")
                                                            info.videoName = item.optString("title")
                                                            datas.add(info)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                youtubeReq.data = datas
                            }
                        }
                    }
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                    status = Constant.Msg.SOCKET_TIME_OUT
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } else {
                status = Constant.Msg.NETWORK_ERROR
            }
            youtubeReq.status = status
            DataObservable.instance.setData(youtubeReq)
        }
    }
}