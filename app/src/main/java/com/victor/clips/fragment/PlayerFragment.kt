package com.victor.clips.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.alibaba.fastjson.JSON
import com.victor.clips.R
import com.victor.clips.app.App
import com.victor.clips.data.YoutubeReq
import com.victor.clips.module.DataObservable
import com.victor.clips.util.Constant
import kotlinx.android.synthetic.main.fragment_player.*

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
class PlayerFragment : BaseFragment(),AdapterView.OnItemClickListener{

    var playUrl: String? = null

    var mHandler = Handler(){
        when(it.what) {
            Constant.Msg.SHOW_HOME_DATA ->{
            }
        }
        false
    };
    override fun getLayoutResource(): Int {
        return R.layout.fragment_player
    }
    override fun handleBackEvent(): Boolean {
        return false
    }
    override fun freshFragData() {

    }

    companion object {
        val TAG = PlayerFragment::class.java.simpleName
        private const val PLAY_URL = "PLAY_URL"

        fun newInstance(url: String?): PlayerFragment {
            val fragment = PlayerFragment()
            val bundle = Bundle()
            bundle.putSerializable(PLAY_URL, url)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
    }

    fun initData () {
        playUrl = arguments?.getString(PLAY_URL)
        Log.e(TAG,"playUrl = ${playUrl}")
    }


    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }



}