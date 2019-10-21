package com.victor.clips.data

import java.io.Serializable

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeData.kt
 * Author: Victor
 * Date: 2018/8/30 14:24
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeData: Serializable {
    var dataType: String? = null
    var id: Int = 0
    var title: String? = null
    var description: String? = null
    var library: String? = null
    var resourceType: String? = null
    var category: String? = null
    var playUrl: String? = null
    var duration: Int = 0
    var releaseTime: Long = 0
    var date: String? = null
    var descriptionEditor: String? = null
    var consumption: ConsumptionInfo? = null
    var author: AuthorInfo? = null
    var cover: CoverInfo? = null
}