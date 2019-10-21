package com.victor.clips.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ContextThemeWrapper
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.victor.clips.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: StatusBarUtil.java
 * Author: Victor
 * Date: 2018/9/5 15:53
 * Description: 状态栏背景及字体颜色修改工具
 * -----------------------------------------------------------------
 */
class StatusBarUtil {
    companion object {

        fun translucentStatusBar(activity: Activity, hideStatusBarBackground: Boolean, statusTextColorBlack: Boolean, navigationBarTranslucent: Boolean) {
            val window = activity.window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val localLayoutParams = window.attributes
                localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (hideStatusBarBackground && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    }
                    if (statusTextColorBlack) {
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    }
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.TRANSPARENT
                    if (navigationBarTranslucent) {
                        // 虚拟导航键
                        window.setNavigationBarColor(Color.TRANSPARENT);
                    } else {
                        window.setNavigationBarColor(activity.getResources().getColor(R.color.half_alpha));
                    }
                }
            }
        }

        fun translucentStatusBar(activity: Activity, hideStatusBarBackground: Boolean, statusTextColorBlack: Boolean) {
            val window = activity.window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val localLayoutParams = window.attributes
                localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (hideStatusBarBackground && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    }
                    if (statusTextColorBlack) {
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    }
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.TRANSPARENT
                }
            }
        }
        fun translucentStatusBar(activity: Activity, hideStatusBarBackground: Boolean) {
            val window = activity.window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val localLayoutParams = window.attributes
                localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (hideStatusBarBackground && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    }
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = Color.TRANSPARENT
                }
            }
        }

        /**
         * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
         *
         * @param dark 状态栏字体和图标是否为深色
         */
        private fun setStatusBarFontDark(window: Window, dark: Boolean) {
            // 小米MIUI
            try {
                val clazz = window.javaClass
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                val darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (dark) {    //状态栏亮色且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
                } else {       //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // 魅族FlymeUI
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (dark) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // android6.0+系统
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (dark) {
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }

        /**
         * 隐藏虚拟按键，并且全屏
         */
        fun hideBottomUIMenu(window: Window?) {
            if (window == null) return
            //隐藏虚拟按键，并且全屏
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                val v = window.decorView
                v.systemUiVisibility = View.GONE
            } else if (Build.VERSION.SDK_INT >= 19) {
                //for new api versions.
                val decorView = window.decorView
                val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
                decorView.systemUiVisibility = uiOptions
            }
        }

        fun scanForActivity(context: Context?): Activity? {
            if (context == null) return null
            if (context is Activity) {
                return context
            } else if (context is ContextWrapper) {
                return scanForActivity(context.baseContext)
            }
            return null
        }

        /**
         * Get AppCompatActivity from context
         */
        fun getAppCompActivity(context: Context?): AppCompatActivity? {
            if (context == null) return null
            if (context is AppCompatActivity) {
                return context
            } else if (context is ContextThemeWrapper) {
                return getAppCompActivity(context.baseContext)
            }
            return null
        }

        @SuppressLint("RestrictedApi")
        fun showStatusBar(context: Context) {
            val decorView = scanForActivity(context)!!.window.decorView
            var systemUiVisibility = decorView.systemUiVisibility
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            systemUiVisibility = systemUiVisibility and flags.inv()
            decorView.systemUiVisibility = systemUiVisibility

            val appCompatActivity = getAppCompActivity(context)
            if (appCompatActivity != null) {
                val ab = appCompatActivity.supportActionBar
                if (ab != null && !ab.isShowing) {
                    ab.setShowHideAnimationEnabled(false)
                    ab.show()
                }
            }
        }

        @SuppressLint("RestrictedApi")
        fun hideStatusBar(context: Context) {
            val appCompatActivity = getAppCompActivity(context)
            if (appCompatActivity != null) {
                val ab = appCompatActivity.supportActionBar
                if (ab != null && ab.isShowing) {
                    ab.setShowHideAnimationEnabled(false)
                    ab.hide()
                }
            }
            val decorView = scanForActivity(context)!!.window.decorView
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = flags
        }

        /**
         * @param context
         * 隐藏底部虚拟按键
         */
        @SuppressLint("RestrictedApi")
        fun hideNavigationBar(context: Context) {
            val appCompatActivity = getAppCompActivity(context)
            if (appCompatActivity != null) {
                val ab = appCompatActivity.supportActionBar
                if (ab != null && ab.isShowing) {
                    ab.setShowHideAnimationEnabled(false)
                    ab.hide()
                }
            }
            val decorView = scanForActivity(context)!!.window.decorView
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            decorView.systemUiVisibility = flags
        }
    }
}