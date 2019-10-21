package com.victor.clips.presenter

import com.victor.clips.data.CategoryReq
import com.victor.clips.data.HomeReq
import com.victor.clips.util.Loger
import com.victor.clips.view.BannerView
import com.victor.clips.view.CategoryView
import org.victor.khttp.library.annotation.HttpParms
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.inject.HttpInject
import org.victor.khttp.library.presenter.impl.BasePresenterImpl

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CategoryPresenterImpl.kt
 * Author: Victor
 * Date: 2018/8/24 13:49
 * Description: 
 * -----------------------------------------------------------------
 */
class CategoryPresenterImpl(var categoryView: CategoryView?): BasePresenterImpl() {
    var TAG = "CategoryPresenterImpl"
    /*Presenter作为中间层，持有View和Model的引用*/
    override fun onComplete(data: Any?, msg: String) {
        categoryView?.OnCategory(data,msg)
    }

    override fun detachView() {
        categoryView = null
    }

    @HttpParms (method = Request.GET,responseCls = CategoryReq::class)
    override fun sendRequest(url: String, header: HashMap<String, String>?, parms: String?) {
        HttpInject.inject(this);
        super.sendRequest(url, header, parms)
    }
}