package com.victor.clips.data

import java.io.Serializable

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CategoryReq.java
 * Author: Victor
 * Date: 2019/10/15 14:03
 * Description:
 * -----------------------------------------------------------------
 */
class CategoryReq:Serializable {
    var id: Int = 0
    var tagId: Int = 0
    var defaultAuthorId: Int = 0
    var name: String? = null
    var description: String? = null
    var bgPicture: String? = null
    var headerImage: String? = null
}