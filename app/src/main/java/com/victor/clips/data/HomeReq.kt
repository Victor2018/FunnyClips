package com.victor.clips.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeReq.java
 * Author: Victor
 * Date: 2018/8/30 14:23
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeReq {
    var nextPageUrl: String? = null
    var newestIssueType: String? = null
    var nextPublishTime: Long = 0
    var issueList: List<IssueInfo>? = null
}