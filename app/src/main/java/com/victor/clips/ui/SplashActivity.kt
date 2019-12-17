package com.victor.clips.ui

import android.Manifest
import android.media.MediaPlayer
import android.os.Bundle
import com.victor.clips.R
import com.victor.clips.util.ConfigLocal
import android.net.Uri
import com.victor.clips.util.Loger
import kotlinx.android.synthetic.main.activity_splash.*
import permission.victor.com.library.OnPermissionCallback
import android.support.v7.app.AlertDialog
import permission.victor.com.library.PermissionHelper
import java.util.*
import android.content.DialogInterface


class SplashActivity : BaseActivity(), MediaPlayer.OnCompletionListener {

    override fun onCompletion(mp: MediaPlayer?) {
        toWelcome()
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        mVvPlay.setOnCompletionListener(this);
    }

    fun initData () {
        var need = ConfigLocal.needPlayWelcomeVideoGuide(this,"")
        if (need) {
            ConfigLocal.updateWelcomeVideoPlayGuide(this, "", false)
            val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.welcome)
            mVvPlay.setVideoURI(uri)
            mVvPlay.start()
            return
        }
        toWelcome()
    }

    fun toWelcome () {
        WelcomeActivity.intentStart(this)
        finish()
    }

}
