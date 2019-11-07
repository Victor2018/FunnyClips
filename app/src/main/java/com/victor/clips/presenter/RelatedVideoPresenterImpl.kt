package com.victor.clips.presenter

import com.victor.clips.data.TrendingReq
import com.victor.clips.view.RelatedVideoView
import org.victor.khttp.library.annotation.HttpParms
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.inject.HttpInject
import org.victor.khttp.library.presenter.impl.BasePresenterImpl

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: RelatedVideoPresenterImpl.kt
 * Author: Victor
 * Date: 2018/8/24 13:49
 * Description: 
 * -----------------------------------------------------------------
 */
class RelatedVideoPresenterImpl(var relatedVideoView: RelatedVideoView?): BasePresenterImpl() {
    var TAG = "RelatedVideoPresenterImpl"
    /*Presenter作为中间层，持有View和Model的引用*/
    override fun onComplete(data: Any?, msg: String) {
        relatedVideoView?.OnRelatedVideo(data,msg)
    }

    override fun detachView() {
        relatedVideoView = null
    }

    @HttpParms (method = Request.GET,responseCls = TrendingReq::class)
    override fun sendRequest(url: String, header: HashMap<String, String>?, parms: String?) {
        HttpInject.inject(this);
        super.sendRequest(url, header, parms)
    }
}