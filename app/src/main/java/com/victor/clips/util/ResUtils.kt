package org.victor.funny.util

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import com.victor.clips.R
import com.victor.clips.app.App

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ResUtils.java
 * Author: Victor
 * Date: 2018/9/4 11:35
 * Description: 
 * -----------------------------------------------------------------
 */
class ResUtils {
    companion object {

        fun getStringRes(id: Int): String {
            try {
                return App.get().getResources().getString(id)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                return ""
            }

        }

        fun getStringRes(id: Int, vararg args: Any): String {
            try {
                return App.get().getResources().getString(id, args)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                return ""
            }

        }

        /**
         * 获取 String[] 值. 如果id对应的资源文件不存在, 则返回 null.
         *
         * @param id
         * @return
         */
        fun getStringArrayRes(id: Int): Array<String>? {
            try {
                return App.get().getResources().getStringArray(id)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * 获取dimension px值. 如果id对应的资源文件不存在, 则返回 -1.
         *
         * @param id
         * @return
         */
        fun getDimenPixRes(id: Int): Int {
            try {
                return App.get().getResources().getDimensionPixelOffset(id)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                return -1
            }

        }

        /**
         * 获取dimension float形式的 px值. 如果id对应的资源文件不存在, 则返回 -1.
         *
         * @param id
         * @return
         */
        fun getDimenFloatPixRes(id: Int): Float {
            try {
                return App.get().getResources().getDimension(id)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                return -1f
            }

        }

        /**
         * 获取 color 值. 如果id对应的资源文件不存在, 则返回 -1.
         *
         * @param id
         * @return
         */
        @ColorInt
        fun getColorRes(id: Int): Int {
            try {
                return ContextCompat.getColor(App.get(), id)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                return -1
            }

        }

        /**
         * 获取 Drawable 对象. 如果id对应的资源文件不存在, 则返回 null.
         *
         * @param id
         * @return
         */
        fun getDrawableRes(id: Int): Drawable? {
            try {
                return ContextCompat.getDrawable(App.get(), id)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
                return null
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * 获取资源
         *
         * @return
         */
        fun getResources(): Resources {
            return App.get().getResources()
        }

        fun getDrawableByName(name: String): Int {
            return getResources().getIdentifier(name, "drawable", App.get().getPackageName())
        }

        /**
         * 根据图片名字获取Id
         */
        fun getDrawableId(name: String): Int {
            try {
                val field = R.drawable::class.java!!.getField(name)
                return field.getInt(field.getName())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return -1
        }
    }
}