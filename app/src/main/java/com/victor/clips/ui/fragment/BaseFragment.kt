package com.victor.clips.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.victor.clips.module.DataObservable
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:26
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class BaseFragment : Fragment(),Observer {
    companion object {
        val ID_KEY = "ID_KEY"
        var fragmentId = -1
    }

    protected var rootView: View? = null
    //Fragment对用户可见的标记
    var isVisibleToUser: Boolean = false

    protected abstract fun getLayoutResource(): Int
    abstract fun handleBackEvent(): Boolean
    abstract fun freshFragData()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            this.isVisibleToUser = true
            lazyLoad()
        } else {
            this.isVisibleToUser = false
        }
    }

    fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isVisibleToUser) {
            freshFragData()
            //数据加载完毕,恢复标记,防止重复加载
            isVisibleToUser = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        DataObservable.instance.addObserver(this)
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false)
        }
        if (rootView!!.getParent() != null) {
            val parent = rootView!!.getParent() as ViewGroup
            parent!!.removeView(rootView)
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        DataObservable.instance.deleteObserver(this)
    }

    override fun update(observable: Observable, data: Any) {

    }


}