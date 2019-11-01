package com.victor.clips.data

import java.io.Serializable

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FollowReq.java
 * Author: Victor
 * Date: 2019/10/15 14:03
 * Description:
 * -----------------------------------------------------------------
 */
class FollowReq {
    var count: Int = 0
    var total: Int = 0
    var refreshCount: Int = 0
    var lastStartId: Int = 0
    var nextPageUrl: String? = null
    var adExist: Boolean? = false
    var updateTime: String? = null
    var headerImage: String? = null

    var itemList:List<FollowItem>? = null
}