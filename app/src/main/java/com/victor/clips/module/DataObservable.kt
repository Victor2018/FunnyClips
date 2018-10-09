package com.victor.clips.module

import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: DataObservable.java
 * Author: Victor
 * Date: 2018/8/30 15:34
 * Description:
 *  观察者模式
 * 定义对象间一种一对多的依赖关系，使得当每一个对象改变状态，则所有依赖于它的对象都会得到通知并自动更新。
 * 观察者模式定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。
 * 这个主题对象在状态上发生变化时，会通知所有观察者对象，使它们能够自动更新自己。
 * -----------------------------------------------------------------
 */
class DataObservable : Observable() {
    private val TAG = "DataObservable"
    private object Holder { val instance = DataObservable()}
    private var data: Any? = null

    companion object {
        val  instance: DataObservable by lazy { DataObservable.Holder.instance }
    }
    fun setData(data: Any) {
        this.data = data
        setChanged()
        //只有在setChanged()被调用后notifyObservers()才会去调用update()，否则什么都不干
        notifyObservers(data)
    }

    fun getData(): Any? {
        return data
    }
}