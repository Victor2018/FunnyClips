package com.victor.clips.interfaces

import android.content.res.Resources
import android.content.res.Resources.Theme
import android.view.View


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ColorUiInterface.java
 * Author: Victor
 * Date: 2019/10/14 16:08
 * Description:
 * -----------------------------------------------------------------
 */
interface ColorUiInterface {
    fun getView(): View

    fun setTheme(themeId: Resources.Theme)
}