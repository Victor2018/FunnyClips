package com.victor.clips.util

import android.view.Gravity
import android.R.attr.gravity
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.support.design.widget.Snackbar
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.victor.clips.R


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SnackbarUtil.java
 * Author: Victor
 * Date: 2019/10/15 10:18
 * Description:
 * -----------------------------------------------------------------
 */
class SnackbarUtil {
    companion object {
        val NORMAL = 1
        val CONFIRM = 2
        val WARNING = 3
        val ALERT = 4


        var red = -0xbbcca
        var green = -0xb350b0
        var blue = -0xde6a0d
        var orange = -0x3ef9

        /**
         * 短显示Snackbar，自定义颜色
         * @param view
         * @param message
         * @param messageColor
         * @param backgroundColor
         * @return
         */
        fun ShortSnackbar(view: View, message: String, messageColor: Int, backgroundColor: Int): Snackbar {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            setSnackbarColor(snackbar, messageColor, backgroundColor)
            return snackbar
        }

        /**
         * 长显示Snackbar，自定义颜色
         * @param view
         * @param message
         * @param messageColor
         * @param backgroundColor
         * @return
         */
        fun LongSnackbar(view: View, message: String, messageColor: Int, backgroundColor: Int): Snackbar {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            setSnackbarColor(snackbar, messageColor, backgroundColor)
            return snackbar
        }

        /**
         * 自定义时常显示Snackbar，自定义颜色
         * @param view
         * @param message
         * @param messageColor
         * @param backgroundColor
         * @return
         */
        fun IndefiniteSnackbar(view: View, message: String, duration: Int, messageColor: Int, backgroundColor: Int): Snackbar {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setDuration(duration)
            setSnackbarColor(snackbar, messageColor, backgroundColor)
            return snackbar
        }

        /**
         * 短显示Snackbar，可选预设类型
         * @param view
         * @param message
         * @param type
         * @return
         */
        fun ShortSnackbar(view: View, message: String, type: Int): Snackbar {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            switchType(snackbar, type)
            return snackbar
        }

        fun ShortSnackbar(view: View, message: String): Snackbar {
            return ShortSnackbar(view, message, NORMAL)
        }

        /**
         * 长显示Snackbar，可选预设类型
         * @param view
         * @param message
         * @param type
         * @return
         */
        fun LongSnackbar(view: View, message: String, type: Int): Snackbar {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            switchType(snackbar, type)
            return snackbar
        }

        /**
         * 自定义时常显示Snackbar，可选预设类型
         * @param view
         * @param message
         * @param type
         * @return
         */
        fun IndefiniteSnackbar(view: View, message: String, duration: Int, type: Int): Snackbar {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setDuration(duration)
            switchType(snackbar, type)
            return snackbar
        }

        //选择预设类型
        private fun switchType(snackbar: Snackbar, type: Int) {
            when (type) {
                NORMAL -> setSnackbarColor(snackbar, blue)
                CONFIRM -> setSnackbarColor(snackbar, green)
                WARNING -> setSnackbarColor(snackbar, orange)
                ALERT -> setSnackbarColor(snackbar, Color.YELLOW, red)
            }
        }

        /**
         * 设置Snackbar背景颜色
         * @param snackbar
         * @param backgroundColor
         */
        fun setSnackbarColor(snackbar: Snackbar, backgroundColor: Int) {
            val view = snackbar.view
            view?.setBackgroundColor(backgroundColor)
        }

        /**
         * 设置Snackbar文字和背景颜色
         * @param snackbar
         * @param messageColor
         * @param backgroundColor
         */
        fun setSnackbarColor(snackbar: Snackbar, messageColor: Int, backgroundColor: Int) {
            val view = snackbar.view
            if (view != null) {
                view.setBackgroundColor(backgroundColor)
                (view.findViewById(R.id.snackbar_text) as TextView).setTextColor(messageColor)
            }
        }

        /**
         * 向Snackbar中添加view
         * @param snackbar
         * @param layoutId
         * @param index 新加布局在Snackbar中的位置
         */
        fun SnackbarAddView(snackbar: Snackbar, layoutId: Int, index: Int) {
            val snackbarview = snackbar.view
            val snackbarLayout = snackbarview as Snackbar.SnackbarLayout

            val add_view = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null)

            val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            p.gravity = Gravity.CENTER_VERTICAL

            snackbarLayout.addView(add_view, index, p)
        }

    }
}