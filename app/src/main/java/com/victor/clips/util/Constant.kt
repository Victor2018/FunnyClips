package com.victor.clips.util

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
class Constant {
    class Msg {
        companion object {
            const val REQUEST_YOUTUBE  = 0x1001
            const val REQUEST_VIMEO    = 0x1002
            const val SOCKET_TIME_OUT    = 0x1003
            const val NETWORK_ERROR    = 0x1004
            const val SHOW_HOME_DATA    = 0x1005
            const val SHOW_YOUTUBE_DETAIL    = 0x1006
        }
    }

    companion object {
        const val YOUTUBE_IMG_URL: String = "https://i.ytimg.com/vi/%s/hqdefault.jpg?sqp=-oaymwEWCMQBEG5IWvKriqkDCQgBFQAAiEIYAQ==&rs=AOn4CLBTZi8JCWrkzvXrj3TsxNGrbkmzpw"
        const val YOUTUBE_HOST = "www.youtube.com"
        const val YOUTUBE_HOST2 = "youtu.be"
        const val GANK_URL: String = "https://gank.io/api/xiandu/categories"
        const val LIVE_URL: String = "https://raw.githubusercontent.com/Victor2018/SeagullTv/master/docs/channels.json"
        const val CATEGORY_URL: String = "https://raw.githubusercontent.com/Victor2018/SeagullTv/master/docs/category.json"
        const val YOUTUBE_HOT_URL: String = "https://www.youtube.com/channel/UCF0pVplsI8R5kcAqgtoRqoA"

    }

}