package com.victor.clips.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: YoutubeReq.kt
 * Author: Victor
 * Date: 2018/8/17 11:49
 * Description: 
 * -----------------------------------------------------------------
 */
class YoutubeReq {
    var status: Int = 0
    var resCode: String? = null
    var resError: String? = null
    var count: Int = 0
    var title: String? = null
    var poster: String? = null
    var data: List<YoutubeInfo>? = null
}