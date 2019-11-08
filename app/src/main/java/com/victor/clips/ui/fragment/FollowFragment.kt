package com.victor.clips.ui.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import com.victor.clips.ui.MainActivity
import com.victor.clips.R
import com.victor.clips.ui.VideoDetailActivity
import com.victor.clips.ui.adapter.FollowAdapter
import com.victor.clips.data.FollowReq
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.presenter.FollowPresenterImpl
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.ui.view.FollowView
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_follow.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FollowFragment.java
 * Author: Victor
 * Date: 2019/11/1 9:44
 * Description:
 * -----------------------------------------------------------------
 */
class FollowFragment: BaseFragment(),FollowView,AdapterView.OnItemClickListener {

    var followPresenter: FollowPresenterImpl? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var followAdapter: FollowAdapter? = null

    companion object {
        fun newInstance(): FollowFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_follow
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
        (activity as MainActivity).toolbar.setTitle("Follow")

        followPresenter = FollowPresenterImpl(this)

        linearLayoutManager = mRvFollow.layoutManager as LinearLayoutManager

        mRvFollow.setHasFixedSize(true)
        followAdapter = FollowAdapter(context!!,this)
        mRvFollow.adapter = followAdapter

        mRvFollow.addOnScrollListener((activity as MainActivity).OnScrollListener())
    }

    fun initData () {
        sendFollowRequest()
    }

    fun sendFollowRequest () {
        followPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.FIND_FOLLOW_URL),
                DeviceUtils.getPhoneModel()),null,null)
    }

    override fun OnFollow(data: Any?, msg: String) {
        var followReq = data as FollowReq

        for (index in 0 until followReq.itemList!!.size) {
            for (item in followReq.itemList!!.get(index).data?.itemList!!) {
                item.id = index
            }
        }
        followAdapter?.add(followReq.itemList)

        followAdapter?.notifyDataSetChanged()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item: HomeItemInfo = followAdapter?.getItem(id.toInt())?.data?.itemList?.get(position)!!
        VideoDetailActivity.intentStart(activity as AppCompatActivity, item,
                view?.findViewById(R.id.mIvFollowCellPoster) as View,
                getString(R.string.transition_video_img))
    }

}