package com.victor.clips.data

import java.io.Serializable

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AuthorInfo.kt
 * Author: Victor
 * Date: 2018/8/30 14:24
 * Description: 
 * -----------------------------------------------------------------
 */
class AuthorInfo: Serializable {
    var id: Int = 0
    var videoNum: Int = 0
    var icon: String? = null
    var name: String? = null
    var description: String? = null
}