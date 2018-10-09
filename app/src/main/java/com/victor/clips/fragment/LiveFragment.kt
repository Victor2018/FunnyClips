package com.victor.clips.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.adapter.LiveAdapter
import com.victor.clips.adapter.YoutubeAdapter
import com.victor.clips.data.LiveReq
import com.victor.clips.presenter.LivePresenterImpl
import com.victor.clips.util.WebConfig
import com.victor.clips.view.LiveView
import kotlinx.android.synthetic.main.fragment_live.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: LiveFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:40
 * Description: 
 * -----------------------------------------------------------------
 */
class LiveFragment : BaseFragment(),AdapterView.OnItemClickListener, LiveView {
    var livePresenter: LivePresenterImpl? = null

    var liveAdapter: LiveAdapter? = null
    var mOnItemPhotoChangedListener: YoutubeAdapter.OnItemPhotoChangedListener? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_live
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
        livePresenter = LivePresenterImpl(this)
        liveAdapter = LiveAdapter(context!!,this)
        mRvLive.adapter = liveAdapter
        mRvLive.setHasFixedSize(true)

    }

    fun initData () {
        sendLiveRequest()
    }


    fun sendLiveRequest () {
        livePresenter?.sendRequest(WebConfig.LIVE_URL,null,null)
    }


    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun OnLive(data: Any?, error: String) {
        if (data != null) {
            var mLiveReq = data!! as LiveReq
            liveAdapter!!.clear()
            liveAdapter!!.add(mLiveReq.categorys)
            liveAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        livePresenter!!.detachView()
        livePresenter = null
    }
}