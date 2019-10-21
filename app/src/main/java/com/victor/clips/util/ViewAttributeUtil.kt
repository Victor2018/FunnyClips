package com.victor.clips.util

import android.widget.TextView
import com.victor.clips.interfaces.ColorUiInterface
import android.content.res.ColorStateList
import android.content.res.Resources
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.widget.ImageView


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ViewAttributeUtil.java
 * Author: Victor
 * Date: 2019/10/14 16:13
 * Description:
 * -----------------------------------------------------------------
 */
class ViewAttributeUtil {
    companion object {
        fun getAttributeValue(attr: AttributeSet?, paramInt: Int): Int {
            var value = -1
            val count = attr!!.getAttributeCount()
            for (i in 0 until count) {
                if (attr.getAttributeNameResource(i) === paramInt) {
                    val str = attr.getAttributeValue(i)
                    if (null != str && str!!.startsWith("?")) {
                        value = Integer.valueOf(str!!.substring(1, str!!.length)).toInt()
                        return value
                    }
                }
            }
            return value
        }

        fun getBackgroundAttibute(attr: AttributeSet?): Int {
            return getAttributeValue(attr, android.R.attr.background)
        }

        fun getBackgroundTintAttibute(attr: AttributeSet?): Int {
            return getAttributeValue(attr, android.R.attr.backgroundTint)
        }

        fun getCheckMarkAttribute(attr: AttributeSet?): Int {
            return getAttributeValue(attr, android.R.attr.checkMark)
        }

        fun getSrcAttribute(attr: AttributeSet?): Int {
            return getAttributeValue(attr, android.R.attr.src)
        }

        fun getTextApperanceAttribute(attr: AttributeSet?): Int {
            return getAttributeValue(attr, android.R.attr.textAppearance)
        }

        fun getDividerAttribute(attr: AttributeSet?): Int {
            return getAttributeValue(attr, android.R.attr.divider)
        }

        fun getTextColorAttribute(attr: AttributeSet?): Int {
            return getAttributeValue(attr, android.R.attr.textColor)
        }

        fun getTextLinkColorAttribute(attr: AttributeSet?): Int {
            return getAttributeValue(attr, android.R.attr.textColorLink)
        }

        fun applyBackgroundDrawable(ci: ColorUiInterface?, theme: Resources.Theme, paramInt: Int) {
            val ta = theme.obtainStyledAttributes(intArrayOf(paramInt))
            val drawable = ta.getDrawable(0)
            ci?.getView()?.setBackgroundDrawable(drawable)
            ta.recycle()
        }

        fun applyBackgroundTintColor(ci: ColorUiInterface?, theme: Resources.Theme, paramInt: Int) {
            val ta = theme.obtainStyledAttributes(intArrayOf(paramInt))
            val resourceId = ta.getColor(0, 0)
            if (null != ci) {
                (ci.getView() as FloatingActionButton).backgroundTintList = ColorStateList.valueOf(resourceId)
            }
            ta.recycle()
        }

        fun applyBackgroundTindDrawable(ci: ColorUiInterface?, theme: Resources.Theme, paramInt: Int) {
            val ta = theme.obtainStyledAttributes(intArrayOf(paramInt))
            val drawable = ta.getDrawable(0)
            ci?.getView()?.setBackgroundDrawable(drawable)
            ta.recycle()
        }

        fun applyImageDrawable(ci: ColorUiInterface?, theme: Resources.Theme, paramInt: Int) {
            val ta = theme.obtainStyledAttributes(intArrayOf(paramInt))
            val drawable = ta.getDrawable(0)
            if (null != ci && ci is ImageView) {
                (ci.getView() as ImageView).setImageDrawable(drawable)
            }
            ta.recycle()
        }

        fun applyTextAppearance(ci: ColorUiInterface?, theme: Resources.Theme, paramInt: Int) {
            val ta = theme.obtainStyledAttributes(intArrayOf(paramInt))
            val resourceId = ta.getResourceId(0, 0)
            if (null != ci && ci is TextView) {
                (ci.getView() as TextView).setTextAppearance(ci.getView().context, resourceId)
            }
            ta.recycle()
        }

        fun applyTextColor(ci: ColorUiInterface?, theme: Resources.Theme, paramInt: Int) {
            val ta = theme.obtainStyledAttributes(intArrayOf(paramInt))
            val resourceId = ta.getColor(0, 0)
            if (null != ci && ci is TextView) {
                (ci.getView() as TextView).setTextColor(resourceId)
            }
            ta.recycle()
        }

        fun applyTextLinkColor(ci: ColorUiInterface?, theme: Resources.Theme, paramInt: Int) {
            val ta = theme.obtainStyledAttributes(intArrayOf(paramInt))
            val resourceId = ta.getColor(0, 0)
            if (null != ci && ci is TextView) {
                (ci.getView() as TextView).setLinkTextColor(resourceId)
            }
            ta.recycle()
        }
    }
}