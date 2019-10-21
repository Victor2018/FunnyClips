package com.victor.clips.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IssueInfo.java
 * Author: Victor
 * Date: 2018/8/30 14:24
 * Description: 
 * -----------------------------------------------------------------
 */
class IssueInfo {
    var releaseTime: Long = 0
    var date: Long = 0
    var publishTime: Long = 0
    var count: Int = 0
    var type: String? = null
    var itemList: List<HomeItemInfo>? = null
}