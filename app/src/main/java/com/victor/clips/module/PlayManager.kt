package com.victor.clips.module

import com.victor.clips.app.App
import android.view.TextureView
import android.view.SurfaceView
import com.victor.clips.widget.PlayLayout



/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PlayManager.java
 * Author: Victor
 * Date: 2019/10/23 13:50
 * Description:
 * -----------------------------------------------------------------
 */
class PlayManager {
    var mPlayManager: PlayManager? = null
    private var mPlayLayout: PlayLayout? = null
    private var mSurfaceView: SurfaceView? = null
    private var mTextureView: TextureView? = null

    private object Holder { val instance = PlayManager()}

    companion object {
        val  instance: PlayManager by lazy { PlayManager.Holder.instance }
    }

    init {
        mPlayLayout = PlayLayout(App.get())
        mSurfaceView = SurfaceView(App.get())
        mTextureView = TextureView(App.get())
    }

    fun getSurfaceView(): SurfaceView? {
        return mSurfaceView
    }

    fun getTextureView(): TextureView? {
        return mTextureView
    }

    fun setSurfaceView(mSurfaceView: SurfaceView) {
        this.mSurfaceView = mSurfaceView
    }

    fun setTextureView(mTextureView: TextureView) {
        this.mTextureView = mTextureView
    }
}