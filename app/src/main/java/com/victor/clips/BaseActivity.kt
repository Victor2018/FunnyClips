package com.victor.clips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.victor.clips.module.DataObservable
import com.victor.clips.util.StatusBarUtil
import java.util.*

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
abstract class BaseActivity: AppCompatActivity(),Observer {
    protected var TAG = javaClass.simpleName

    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        initializeSuper()
    }

    fun initializeSuper () {
        DataObservable.instance.addObserver(this)
        StatusBarUtil.translucentStatusBar(this, true,false,false)
    }
    override fun update(observable: Observable?, data: Any?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        DataObservable.instance.deleteObserver(this)
    }
}