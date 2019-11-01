package com.victor.clips.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FollowHeader.java
 * Author: Victor
 * Date: 2019/11/1 9:59
 * Description:
 * -----------------------------------------------------------------
 */
class FollowHeader {
    var id: Int = 0
    var uid: Int = 0
    var icon: String? = null
    var iconType: String? = null
    var title: String? = null
    var subTitle: String? = null
    var description: String? = null
    var actionUrl: String? = null
    var adTrack: String? = null
    var ifPgc: Boolean = false
    var expert: Boolean = false
    var ifShowNotificationIcon: Boolean = false
    var follow: FollowData? = null
}