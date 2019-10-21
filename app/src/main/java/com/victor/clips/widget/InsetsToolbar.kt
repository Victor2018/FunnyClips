package com.victor.clips.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.victor.clips.interfaces.ColorUiInterface
import com.victor.clips.interfaces.WindowInsetsHandler
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import com.victor.clips.R
import com.victor.clips.util.ViewAttributeUtil


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: InsetsToolbar.java
 * Author: Victor
 * Date: 2019/10/14 16:06
 * Description:
 * -----------------------------------------------------------------
 */
class InsetsToolbar: Toolbar, WindowInsetsHandler, ColorUiInterface {
    private var attr_background = -1

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.toolbarStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs)
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            val l = insets.systemWindowInsetLeft
            val t = insets.systemWindowInsetTop
            val r = insets.systemWindowInsetRight
            setPadding(l, t, r, 0)
            insets.consumeSystemWindowInsets()
        }

    }

    override fun onApplyWindowInsets(insets: Rect): Boolean {
        setPadding(insets.left, insets.top, insets.right, 0);
        return true
    }

    override fun getView(): View {
        return this
    }

    override fun setTheme(themeId: Resources.Theme) {
        if (attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
    }
}