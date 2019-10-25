package com.victor.clips.util

import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.AbsListView
import android.view.ViewGroup
import com.victor.clips.interfaces.ColorUiInterface
import java.lang.reflect.InvocationTargetException
import java.util.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ColorUiUtil.java
 * Author: Victor
 * Date: 2019/10/24 16:34
 * Description:
 * -----------------------------------------------------------------
 */
class ColorUiUtil {
    companion object {
        private val rand = Random()
        /**
         * 切换应用主题
         *
         * @param rootView
         */
        fun changeTheme(rootView: View, theme: Resources.Theme) {
            if (rootView is ColorUiInterface) {
                (rootView as ColorUiInterface).setTheme(theme)
                if (rootView is ViewGroup) {
                    val count = (rootView as ViewGroup).childCount
                    for (i in 0 until count) {
                        changeTheme((rootView as ViewGroup).getChildAt(i), theme)
                    }
                }
                if (rootView is AbsListView) {
                    try {
                        val localField = AbsListView::class.java.getDeclaredField("mRecycler")
                        localField.isAccessible = true
                        val localMethod = Class.forName("android.widget.AbsListView\$RecycleBin").getDeclaredMethod("clear")
                        localMethod.isAccessible = true
                        localMethod.invoke(localField.get(rootView))
                    } catch (e1: NoSuchFieldException) {
                        e1.printStackTrace()
                    } catch (e2: ClassNotFoundException) {
                        e2.printStackTrace()
                    } catch (e3: NoSuchMethodException) {
                        e3.printStackTrace()
                    } catch (e4: IllegalAccessException) {
                        e4.printStackTrace()
                    } catch (e5: InvocationTargetException) {
                        e5.printStackTrace()
                    }

                }
            } else {
                if (rootView is ViewGroup) {
                    val count = (rootView as ViewGroup).childCount
                    for (i in 0 until count) {
                        changeTheme((rootView as ViewGroup).getChildAt(i), theme)
                    }
                }
                if (rootView is AbsListView) {
                    try {
                        val localField = AbsListView::class.java.getDeclaredField("mRecycler")
                        localField.isAccessible = true
                        val localMethod = Class.forName("android.widget.AbsListView\$RecycleBin").getDeclaredMethod("clear")
                        localMethod.isAccessible = true
                        localMethod.invoke(localField.get(rootView))
                    } catch (e1: NoSuchFieldException) {
                        e1.printStackTrace()
                    } catch (e2: ClassNotFoundException) {
                        e2.printStackTrace()
                    } catch (e3: NoSuchMethodException) {
                        e3.printStackTrace()
                    } catch (e4: IllegalAccessException) {
                        e4.printStackTrace()
                    } catch (e5: InvocationTargetException) {
                        e5.printStackTrace()
                    }

                }
            }
        }

        fun getRandomColor(): Int {
            val r = rand.nextInt(255)
            val g = rand.nextInt(255)
            val b = rand.nextInt(255)
            return Color.rgb(r, g, b)
        }
    }
}