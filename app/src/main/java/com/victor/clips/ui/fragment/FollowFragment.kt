package com.victor.clips.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import com.victor.clips.ui.MainActivity
import com.victor.clips.R
import com.victor.clips.ui.VideoDetailActivity
import com.victor.clips.data.FollowReq
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.presenter.FollowPresenterImpl
import com.victor.clips.ui.adapter.*
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.ui.view.FollowView
import com.victor.clips.ui.widget.LMRecyclerView
import com.victor.clips.util.Loger
import com.victor.clips.util.SnackbarUtil
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_main.*
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
class FollowFragment: BaseFragment(),FollowView,AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,LMRecyclerView.OnLoadMoreListener {

    var followPresenter: FollowPresenterImpl? = null
    var followAdapter: FollowAdapter? = null
    var currentPage = 0

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
        (activity as MainActivity).toolbar.setTitle(getString(R.string.follow))

        followPresenter = FollowPresenterImpl(this)

        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSrlFollow.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSrlFollow.setOnRefreshListener(this);

        mRvFollow.setHasFixedSize(true)
        followAdapter = FollowAdapter(context!!,this)

        val animatorAdapter = ScaleInAnimatorAdapter(followAdapter!!,mRvFollow)
        mRvFollow.adapter = animatorAdapter

        mRvFollow.addOnScrollListener((activity as MainActivity).OnScrollListener())
        mRvFollow.setLoadMoreListener(this)
    }

    fun initData () {
        sendFollowRequest()
    }

    override fun onRefresh() {
        currentPage = 1
        sendFollowRequest()
    }

    override fun OnLoadMore() {
        Loger.e(TAG,"OnLoadMore()......currentPage = $currentPage")
        currentPage++
        sendFollowRequest()
    }

    fun sendFollowRequest () {
        followPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.FIND_FOLLOW_URL),
                currentPage * 2,2,DeviceUtils.getPhoneModel()),null,null)
        mSrlFollow.isRefreshing = true
    }

    fun showFollowData (data: Any?, msg: String) {
        if (data == null) {
            SnackbarUtil.ShortSnackbar(mRvFollow,msg, SnackbarUtil.ALERT).show()
            return
        }
        mSrlFollow.isRefreshing = false
        var followReq = data as FollowReq

        for (index in 0 until followReq.itemList!!.size) {
            for (item in followReq.itemList!!.get(index).data?.itemList!!) {
                item.id = index
            }
        }
        followAdapter?.setFooterVisible(true)
        mRvFollow?.setHasMore(true)
        if (currentPage == 0) {
            followAdapter?.clear()
        }
        followAdapter?.add(followReq.itemList)

        followAdapter?.notifyDataSetChanged()
    }

    override fun OnFollow(data: Any?, msg: String) {
        showFollowData(data,msg)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var item: HomeItemInfo = followAdapter?.getItem(id.toInt())?.data?.itemList?.get(position)!!
        VideoDetailActivity.intentStart(activity as AppCompatActivity, item,
                view?.findViewById(R.id.mIvFollowCellPoster) as View,
                getString(R.string.transition_video_img))
    }

}