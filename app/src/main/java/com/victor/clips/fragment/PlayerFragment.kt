package com.victor.clips.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: TrendingFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:40
 * Description: 
 * -----------------------------------------------------------------
 */
class PlayerFragment : BaseFragment(),AdapterView.OnItemClickListener{

    override fun getLayoutResource(): Int {
        return R.layout.fragment_player
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
    }

    fun initData () {
    }


    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

}