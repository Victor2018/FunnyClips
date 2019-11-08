package com.victor.clips.ui.widget

import android.content.Context
import android.content.res.Resources
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View
import com.victor.clips.interfaces.ColorUiInterface
import com.victor.clips.util.ViewAttributeUtil

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ColorFloatingActionButton.java
 * Author: Victor
 * Date: 2019/10/14 16:28
 * Description:
 * -----------------------------------------------------------------
 */
class ColorFloatingActionButton: FloatingActionButton,ColorUiInterface {
    private var attr_background = -1

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    override fun getView(): View {
        return this
    }

    override fun setTheme(themeId: Resources.Theme) {
        if (attr_background != -1) {
            ViewAttributeUtil.applyBackgroundTintColor(this, themeId, attr_background);
        }
    }


}