package com.victor.clips.util

import android.view.ViewGroup
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.animation.Animator
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.victor.clips.R
import com.victor.clips.data.Theme


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ThemeUtils.java
 * Author: Victor
 * Date: 2019/10/24 16:25
 * Description:
 * -----------------------------------------------------------------
 */
class ThemeUtils {
    companion object {
        fun getThemeColor(context: Context, attrRes: Int): Int {
            val typedArray = context.obtainStyledAttributes(intArrayOf(attrRes))
            val color = typedArray.getColor(0, 0xffffff)
            typedArray.recycle()
            return color
        }

        fun setTheme(activity: AppCompatActivity, selectedColor: Int) {
            if (selectedColor == getThemeColor(activity, R.attr.colorPrimary))
                return

            when(selectedColor) {
                activity.resources.getColor(R.color.colorBluePrimary) -> {
                    activity.setTheme(R.style.BlueTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Blue)
                }
                activity.resources.getColor(R.color.colorRedPrimary) -> {
                    activity.setTheme(R.style.RedTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Red)
                }
                activity.resources.getColor(R.color.colorBrownPrimary) -> {
                    activity.setTheme(R.style.BrownTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Brown)
                }
                activity.resources.getColor(R.color.colorGreenPrimary) -> {
                    activity.setTheme(R.style.GreenTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Green)
                }
                activity.resources.getColor(R.color.colorPurplePrimary) -> {
                    activity.setTheme(R.style.PurpleTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Purple)
                }
                activity.resources.getColor(R.color.colorTealPrimary) -> {
                    activity.setTheme(R.style.TealTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Teal)
                }
                activity.resources.getColor(R.color.colorPinkPrimary) -> {
                    activity.setTheme(R.style.PinkTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Pink)
                }
                activity.resources.getColor(R.color.colorDeepPurplePrimary) -> {
                    activity.setTheme(R.style.DeepPurpleTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.DeepPurple)
                }
                activity.resources.getColor(R.color.colorOrangePrimary) -> {
                    activity.setTheme(R.style.OrangeTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Orange)
                }
                activity.resources.getColor(R.color.colorIndigoPrimary) -> {
                    activity.setTheme(R.style.IndigoTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Indigo)
                }
                activity.resources.getColor(R.color.colorLightGreenPrimary) -> {
                    activity.setTheme(R.style.LightGreenTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.LightGreen)
                }
                activity.resources.getColor(R.color.colorDeepOrangePrimary) -> {
                    activity.setTheme(R.style.DeepOrangeTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.DeepOrange)
                }
                activity.resources.getColor(R.color.colorLimePrimary) -> {
                    activity.setTheme(R.style.LimeTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Lime)
                }
                activity.resources.getColor(R.color.colorBlueGreyPrimary) -> {
                    activity.setTheme(R.style.BlueGreyTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.BlueGrey)
                }
                activity.resources.getColor(R.color.colorCyanPrimary) -> {
                    activity.setTheme(R.style.CyanTheme)
                    SharePreferencesUtil.setCurrentTheme(activity, Theme.Cyan)
                }
                activity.resources.getColor(R.color.colorNightPrimary) -> {
                    activity.setTheme(R.style.NightTheme)
                    ConfigLocal.updateDayNightThemeGuide(activity,"",true)
                }
            }
            val rootView = activity.window.decorView
            rootView.isDrawingCacheEnabled = true
            rootView.buildDrawingCache(true)

            val localBitmap = Bitmap.createBitmap(rootView.drawingCache)
            rootView.isDrawingCacheEnabled = false
            if (null != localBitmap && rootView is ViewGroup) {
                val tmpView = View(activity.applicationContext)
                tmpView.setBackgroundDrawable(BitmapDrawable(activity.resources, localBitmap))
                val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                rootView.addView(tmpView, params)
                tmpView.animate().alpha(0f).setDuration(1000).setListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        ColorUiUtil.changeTheme(rootView, activity.theme)
                        System.gc()
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        rootView.removeView(tmpView)
                        localBitmap.recycle()
                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                }).start()


            }
        }
    }
}