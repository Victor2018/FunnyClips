package com.victor.clips.data

import java.io.Serializable

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeItemInfo.kt
 * Author: Victor
 * Date: 2018/8/30 14:24
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeItemInfo: Serializable {
    var type: String? = null
    var id: Int = 0
    var adIndex: Int = 0
    var data: HomeData? = null
}