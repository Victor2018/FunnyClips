package com.victor.clips.presenter

import com.victor.clips.data.TrendingReq
import com.victor.clips.ui.view.CategoryDetailView
import org.victor.khttp.library.annotation.HttpParms
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.inject.HttpInject
import org.victor.khttp.library.presenter.impl.BasePresenterImpl

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CategoryDetailPresenterImpl.kt
 * Author: Victor
 * Date: 2018/8/24 13:49
 * Description: 
 * -----------------------------------------------------------------
 */
class CategoryDetailPresenterImpl(var categoryDetailView: CategoryDetailView?): BasePresenterImpl() {
    var TAG = "CategoryDetailPresenterImpl"
    /*Presenter作为中间层，持有View和Model的引用*/
    override fun onComplete(data: Any?, msg: String) {
        categoryDetailView?.OnCategoryDetail(data,msg)
    }

    override fun detachView() {
        categoryDetailView = null
    }

    @HttpParms (method = Request.GET,responseCls = TrendingReq::class)
    override fun sendRequest(url: String, header: HashMap<String, String>?, parms: String?) {
        HttpInject.inject(this);
        super.sendRequest(url, header, parms)
    }
}