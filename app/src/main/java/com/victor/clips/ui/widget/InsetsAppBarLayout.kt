package com.victor.clips.ui.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.support.design.widget.AppBarLayout
import android.util.AttributeSet
import android.view.View
import com.victor.clips.interfaces.ColorUiInterface
import com.victor.clips.interfaces.WindowInsetsHandler
import com.victor.clips.util.ViewAttributeUtil
import android.support.design.widget.CoordinatorLayout
import com.victor.clips.interfaces.WindowInsetsHandlingBehavior
import com.victor.clips.util.WindowInsetsHelper


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: InsetsAppBarLayout.java
 * Author: Victor
 * Date: 2019/10/14 16:16
 * Description:
 * -----------------------------------------------------------------
 */
class InsetsAppBarLayout: AppBarLayout, WindowInsetsHandler, ColorUiInterface {
    private var attr_background = -1
    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    override fun onApplyWindowInsets(insets: Rect): Boolean {
        return WindowInsetsHelper.dispatchApplyWindowInsets(this, insets)
    }

    override fun getView(): View {
        return this
    }

    override fun setTheme(themeId: Resources.Theme) {
        if (attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
    }

    class Behavior : AppBarLayout.Behavior, WindowInsetsHandlingBehavior {

        constructor() {}

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

        override fun onApplyWindowInsets(layout: CoordinatorLayout, child: View, insets: Rect): Boolean {
            return WindowInsetsHelper.onApplyWindowInsets(child, insets)
        }

    }
}