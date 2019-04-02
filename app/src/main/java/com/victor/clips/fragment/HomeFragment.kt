package com.victor.clips.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.alibaba.fastjson.JSON
import com.victor.clips.HomeActivity
import com.victor.clips.R
import com.victor.clips.adapter.YoutubeAdapter
import com.victor.clips.app.App
import com.victor.clips.data.YoutubeReq
import com.victor.clips.module.SpiderHelper
import com.victor.clips.util.Constant
import com.victor.clips.util.Loger
import com.victor.clips.util.WebConfig
import com.victor.clips.widget.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:40
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeFragment : BaseFragment(),AdapterView.OnItemClickListener {

    var TAG = "HomeFragment"

    var spiderHelper:SpiderHelper? = null
    var youtubeAdapter: YoutubeAdapter? = null
    var homeActivity: HomeActivity? = null

    var mHandler = Handler(){
        when(it.what) {
            Constant.Msg.SHOW_HOME_DATA ->{
                Log.e(TAG,"SHOW_HOME_DATA")
                var mYoutubeReq = it.obj as YoutubeReq
                Log.e(TAG,"SHOW_HOME_DATA-mYoutubeReq = " + JSON.toJSONString(mYoutubeReq))
                youtubeAdapter!!.clear()
                youtubeAdapter!!.add(mYoutubeReq!!.data)
                youtubeAdapter!!.notifyDataSetChanged()
            }
        }
        false
    };

    override fun getLayoutResource(): Int {
        return R.layout.fragment_home
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
        homeActivity = activity as HomeActivity?
        spiderHelper = SpiderHelper(App.get())
        youtubeAdapter = YoutubeAdapter(App.get(),this)
        mRvHome.addItemDecoration(SimpleDividerItemDecoration(context))
        mRvHome.adapter = youtubeAdapter
        mRvHome.setHasFixedSize(true)

    }

    fun initData () {
        spiderHelper?.sendRequestWithParms(Constant.Msg.REQUEST_YOUTUBE,WebConfig.YOUTUBE_HOT_URL)
//        spiderHelper?.sendRequestWithParms(Constant.Msg.REQUEST_VIMEO,"https://vimeo.com/categories/music")
    }
    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Loger.e(TAG,"onItemClick()......")
        homeActivity?.playYoutube(youtubeAdapter?.getItem(position)?.url)
    }

    override fun update(observable: Observable, data: Any) {
        super.update(observable, data)
        if (data != null) {
            if (data is YoutubeReq) {
                var msg = mHandler.obtainMessage(Constant.Msg.SHOW_HOME_DATA,data);
                mHandler.sendMessage(msg)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        spiderHelper!!.onDestroy()
        spiderHelper = null
    }

}