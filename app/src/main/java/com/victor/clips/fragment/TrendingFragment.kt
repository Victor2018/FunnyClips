package com.victor.clips.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.presenter.DailySelectionPresenterImpl
import com.victor.clips.util.AppUtil
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.view.DailySelectionView
import kotlinx.android.synthetic.main.fragment_trending.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TrendingFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:40
 * Description: 
 * -----------------------------------------------------------------
 */
class TrendingFragment : BaseFragment(),AdapterView.OnItemClickListener,DailySelectionView {


    override fun getLayoutResource(): Int {
        return R.layout.fragment_trending
    }
    override fun handleBackEvent(): Boolean {
        return false
    }
    override fun freshFragData() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        txtLabel.text = "Trending Videos"
    }

    fun initData () {
    }


    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun OnDailySelection(data: Any?, msg: String) {
    }

}