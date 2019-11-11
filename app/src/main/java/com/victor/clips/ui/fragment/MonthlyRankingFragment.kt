package com.victor.clips.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.ui.MainActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.victor.clips.ui.VideoDetailActivity
import com.victor.clips.ui.adapter.RankingAdapter
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.data.TrendingReq
import com.victor.clips.presenter.RankingPresenterImpl
import com.victor.clips.ui.adapter.ScaleInAnimatorAdapter
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.ui.view.RankingView
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_monthly_ranking.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MonthlyRankingFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:40
 * Description: 
 * -----------------------------------------------------------------
 */
class MonthlyRankingFragment : BaseFragment(),AdapterView.OnItemClickListener,RankingView,
        SwipeRefreshLayout.OnRefreshListener {

    var rankingAdapter: RankingAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    var rankingPresenter: RankingPresenterImpl? = null

    companion object {
        fun newInstance(): MonthlyRankingFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): MonthlyRankingFragment {
            val fragment = MonthlyRankingFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_monthly_ranking
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
        (activity as MainActivity).toolbar.setTitle(getString(R.string.monthly_ranking))

        rankingPresenter = RankingPresenterImpl(this)

        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSrlMonthlyRanking.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSrlMonthlyRanking.setOnRefreshListener(this);

        linearLayoutManager = mRvMonthlyRanking.layoutManager as LinearLayoutManager

        mRvMonthlyRanking.setHasFixedSize(true)

        rankingAdapter = RankingAdapter(activity!!,this)
        rankingAdapter?.setHeaderVisible(false)
        rankingAdapter?.setFooterVisible(false)

        val animatorAdapter = ScaleInAnimatorAdapter(rankingAdapter!!,mRvMonthlyRanking)
        mRvMonthlyRanking.adapter = animatorAdapter

        mRvMonthlyRanking.addOnScrollListener((activity as MainActivity).OnScrollListener())

    }

    fun initData () {
        sendMonthlyRankingRequest()
    }

    override fun onRefresh() {
        sendMonthlyRankingRequest()
    }

    fun sendMonthlyRankingRequest () {
        rankingPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.HOT_MONTHLY_URL),
                DeviceUtils.getPhoneModel()),null,null)
        mSrlMonthlyRanking.isRefreshing = true

    }

    override fun OnRanking(data: Any?, msg: String) {
        mSrlMonthlyRanking.isRefreshing = false
        var monthlyRankingReq = data!! as TrendingReq
        rankingAdapter?.clear()
        rankingAdapter?.add(monthlyRankingReq.itemList)
        rankingAdapter?.notifyDataSetChanged()
    }

    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        VideoDetailActivity.intentStart(activity as AppCompatActivity,
                rankingAdapter?.getItem(position) as HomeItemInfo,
                view?.findViewById(R.id.mIvRankingPoster) as View,
                getString(R.string.transition_video_img))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rankingPresenter?.detachView()
        rankingPresenter = null
    }

}