package com.victor.clips.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import com.victor.clips.MainActivity
import com.victor.clips.R
import com.victor.clips.VideoDetailActivity
import com.victor.clips.adapter.FollowAdapter
import com.victor.clips.adapter.LiveAdapter
import com.victor.clips.data.FollowReq
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.presenter.FollowPresenterImpl
import com.victor.clips.presenter.LivePresenterImpl
import com.victor.clips.util.Constant
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.view.FollowView
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_follow.*
import kotlinx.android.synthetic.main.fragment_live.*
import kotlinx.android.synthetic.main.fragment_total_ranking.*
import kotlinx.android.synthetic.main.rv_follow_item_cell.view.*

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
    var actionbarScrollPoint: Float = 0f
    var summaryScrolled: Float = 0f
    var maxScroll: Float = 0f

    var followPresenter: FollowPresenterImpl? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var followAdapter: FollowAdapter? = null

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

        mRvFollow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = linearLayoutManager?.findLastVisibleItemPosition()

                if (dy > actionbarScrollPoint) {
                    (activity as MainActivity).showActionbar(false, true)
                }

                if (dy < actionbarScrollPoint * -1) {
                    (activity as MainActivity).showActionbar(true, true)
                }

                summaryScrolled += dy
                (activity as MainActivity).mIvBubbles.setTranslationY(-0.5f * summaryScrolled)
                var alpha = summaryScrolled / maxScroll
                alpha = Math.min(1.0f, alpha)

                (activity as MainActivity).setToolbarAlpha(alpha)
                //change background color on scroll
                val color = Math.max(Constant.BG_COLOR_MIN, Constant.BG_COLOR_MAX - summaryScrolled * 0.05f)
                (activity as MainActivity).mRlMainParent.setBackgroundColor(Color.argb(255, color.toInt(), color.toInt(), color.toInt()))
            }
        })
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