package com.victor.clips.presenter

import com.victor.clips.data.LiveReq
import com.victor.clips.view.DailySelectionView
import org.victor.khttp.library.annotation.HttpParms
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.inject.HttpInject
import org.victor.khttp.library.presenter.impl.BasePresenterImpl

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LivePresenterImpl.kt
 * Author: Victor
 * Date: 2018/8/24 13:49
 * Description: 
 * -----------------------------------------------------------------
 */
class DailySelectionPresenterImpl(var dailySelectionView: DailySelectionView?): BasePresenterImpl() {
    /*Presenter作为中间层，持有View和Model的引用*/
    override fun onComplete(data: Any?, msg: String) {
        dailySelectionView?.OnDailySelection(data,msg)
    }

    override fun detachView() {
        dailySelectionView = null
    }

    @HttpParms (method = Request.GET,responseCls = String::class)
    override fun sendRequest(url: String, header: HashMap<String, String>?, parms: String?) {
        HttpInject.inject(this);
        super.sendRequest(url, header, parms)
    }
}