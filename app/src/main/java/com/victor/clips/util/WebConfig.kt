package com.victor.clips.util

import android.content.Context
import android.net.Uri
import com.victor.clips.BuildConfig
import java.util.HashMap

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: WebConfig.kt
 * Author: Victor
 * Date: 2018/8/17 13:47
 * Description: 
 * -----------------------------------------------------------------
 */
class WebConfig {
    companion object {
        const val YOUTUBE_IMG_URL: String = "https://i.ytimg.com/vi/%s/hqdefault.jpg?sqp=-oaymwEWCMQBEG5IWvKriqkDCQgBFQAAiEIYAQ==&rs=AOn4CLBTZi8JCWrkzvXrj3TsxNGrbkmzpw"
        const val YOUTUBE_HOST = "www.youtube.com"
        const val YOUTUBE_HOST2 = "youtu.be"
        const val GANK_URL: String = "https://gank.io/api/xiandu/categories"
        const val LIVE_URL: String = "https://raw.githubusercontent.com/Victor2018/SeagullTv/master/docs/channels.json"
        const val CATEGORY_URL: String = "https://raw.githubusercontent.com/Victor2018/SeagullTv/master/docs/category.json"
        const val YOUTUBE_HOT_URL: String = "https://www.youtube.com/channel/UCF0pVplsI8R5kcAqgtoRqoA"

        const val EYEPETIZER_BASE_URL: String = "https://baobab.kaiyanapp.com/api/"
        const val CATEGORY_DETAIL_URL: String = "v4/categories/videoList?&id=%d&udid=d2807c895f0348a180148c9dfa6f2feeac0781b5&deviceModel=%s"//首页banner
        const val RELATED_VIDEO_URL: String = "v4/video/related?&id=%d&udid=d2807c895f0348a180148c9dfa6f2feeac0781b5&deviceModel=%s"
        const val BANNER_URL: String = "v2/feed?num=1&udid=%s&deviceModel=%s"//首页banner
        const val DAILY_SELECTION_URL: String = "v4/tabs/selected"//每日精选
        const val FIND_FOLLOW_URL: String = "v4/tabs/follow?udid=%s&deviceModel=%s"//发现-关注
        const val FIND_CATEGORIES_URL: String = "v4/categories?udid=%s&deviceModel=%s"//发现-分类
        const val HOT_WEEKLY_URL: String = "v4/rankList/videos?strategy=weekly&udid=%s&deviceModel=%s"//热门-周排行
        const val HOT_MONTHLY_URL: String = "v4/rankList/videos?strategy=monthly&udid=%s&deviceModel=%s"//热门-月排行
        const val HOT_TOTAL_RANKING_URL: String = "v4/rankList/videos?strategy=historical&udid=%s&deviceModel=%s"//热门-总排行

        fun getServer(): String? {
            if (BuildConfig.MODEL_ONLINE) {
                return EYEPETIZER_BASE_URL
            }
            return EYEPETIZER_BASE_URL
        }
        fun getServerIp(): String? {
            val uri = Uri.parse(getServer())
            return uri.host
        }

        fun getRequestUrl(api: String): String {
            return getServer() + api
        }

        fun getHttpHeaderParm(context: Context): HashMap<String, String> {
            val header = HashMap<String, String>()
            header["appName"] = "hometownNews "
            header["appVersion"] = AppUtil.getAppVersionName(context)
            header["systemType"] = "Android"
            return header
        }

    }
}