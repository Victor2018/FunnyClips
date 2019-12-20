package com.victor.clips.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.victor.clips.R
import com.victor.clips.module.DataObservable
import com.victor.clips.util.StatusBarUtil
import java.util.*
import com.victor.clips.data.Theme
import com.victor.clips.util.ConfigLocal
import com.victor.clips.util.SharePreferencesUtil
import com.victor.clips.util.ThemeUtils
import com.victor.update.library.ui.UpdateActivity


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseActivity.java
 * Author: Victor
 * Date: 2018/9/5 15:55
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class BaseActivity: UpdateActivity (),Observer {
    protected var TAG = javaClass.simpleName

    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
        setContentView(getLayoutResource())
        initializeSuper()
    }

    fun initializeSuper () {
        DataObservable.instance.addObserver(this)
        StatusBarUtil.translucentStatusBar(this, true,false,false)
    }

    private fun setTheme() {
        var showDayNight = ConfigLocal.needShowDayNightThemeGuide(this,"")
        if (showDayNight) {
            setTheme(R.style.NightTheme)
            return
        }
        val theme = SharePreferencesUtil.getCurrentTheme(this)
        when (theme) {
            Theme.Blue -> setTheme(R.style.BlueTheme)
            Theme.Red -> setTheme(R.style.RedTheme)
            Theme.Brown -> setTheme(R.style.BrownTheme)
            Theme.Green -> setTheme(R.style.GreenTheme)
            Theme.Purple -> setTheme(R.style.PurpleTheme)
            Theme.Teal -> setTheme(R.style.TealTheme)
            Theme.Pink -> setTheme(R.style.PinkTheme)
            Theme.DeepPurple -> setTheme(R.style.DeepPurpleTheme)
            Theme.Orange -> setTheme(R.style.OrangeTheme)
            Theme.Indigo -> setTheme(R.style.IndigoTheme)
            Theme.LightGreen -> setTheme(R.style.LightGreenTheme)
            Theme.Lime -> setTheme(R.style.LimeTheme)
            Theme.DeepOrange -> setTheme(R.style.DeepOrangeTheme)
            Theme.Cyan -> setTheme(R.style.CyanTheme)
            Theme.BlueGrey -> setTheme(R.style.BlueGreyTheme)
        }

    }

    override fun onResume() {
        super.onResume()
        var showDayNight = ConfigLocal.needShowDayNightThemeGuide(this,"")
        if (showDayNight) {
            ThemeUtils.setTheme(this,resources.getColor(R.color.colorNightPrimary))
            return
        }
        val theme = SharePreferencesUtil.getCurrentTheme(this)
        when (theme) {
            Theme.Blue -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorBluePrimary))
            Theme.Red -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorRedPrimary))
            Theme.Brown -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorBrownPrimary))
            Theme.Green -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorGreenPrimary))
            Theme.Purple -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorPurplePrimary))
            Theme.Teal -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorTealPrimary))
            Theme.Pink -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorPinkPrimary))
            Theme.DeepPurple -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorDeepPurplePrimary))
            Theme.Orange -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorOrangePrimary))
            Theme.Indigo -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorIndigoPrimary))
            Theme.LightGreen -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorLightGreenPrimary))
            Theme.Lime -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorLimePrimary))
            Theme.DeepOrange -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorDeepOrangePrimary))
            Theme.Cyan -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorCyanPrimary))
            Theme.BlueGrey -> ThemeUtils.setTheme(this,resources.getColor(R.color.colorBlueGreyPrimary))
        }
    }

    fun getCurrentTheme (): Int {
        var theme = resources.getColor(R.color.colorRedPrimary)
        val themeName = SharePreferencesUtil.getCurrentTheme(this)
        when (themeName) {
            Theme.Blue -> theme = resources.getColor(R.color.colorBluePrimary)
            Theme.Red -> theme = resources.getColor(R.color.colorRedPrimary)
            Theme.Brown -> theme = resources.getColor(R.color.colorBrownPrimary)
            Theme.Green -> theme = resources.getColor(R.color.colorGreenPrimary)
            Theme.Purple -> theme = resources.getColor(R.color.colorPurplePrimary)
            Theme.Teal -> theme = resources.getColor(R.color.colorTealPrimary)
            Theme.Pink -> theme = resources.getColor(R.color.colorPinkPrimary)
            Theme.DeepPurple -> theme = resources.getColor(R.color.colorDeepPurplePrimary)
            Theme.Orange -> theme = resources.getColor(R.color.colorOrangePrimary)
            Theme.Indigo -> theme = resources.getColor(R.color.colorIndigoPrimary)
            Theme.LightGreen -> theme = resources.getColor(R.color.colorLightGreenPrimary)
            Theme.Lime -> theme = resources.getColor(R.color.colorLimePrimary)
            Theme.DeepOrange -> theme = resources.getColor(R.color.colorDeepOrangePrimary)
            Theme.Cyan -> theme = resources.getColor(R.color.colorCyanPrimary)
            Theme.BlueGrey -> theme = resources.getColor(R.color.colorBlueGreyPrimary)
        }
        return theme
    }

    override fun onDestroy() {
        super.onDestroy()
        DataObservable.instance.deleteObserver(this)
    }

    override fun update(observable: Observable?, data: Any?) {
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_activity_enter_back, R.anim.anim_activity_exit_back);
    }
}