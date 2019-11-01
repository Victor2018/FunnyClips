package com.victor.clips.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.MainActivity
import android.support.v7.widget.RecyclerView
import android.graphics.Color
import com.victor.clips.util.Constant
import kotlinx.android.synthetic.main.content_main.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.victor.clips.VideoDetailActivity
import com.victor.clips.adapter.RankingAdapter
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.data.TrendingReq
import com.victor.clips.presenter.RankingPresenterImpl
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.view.RankingView
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_monthly_ranking.*
import kotlinx.android.synthetic.main.fragment_total_ranking.*
import kotlinx.android.synthetic.main.fragment_weekly_ranking.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TotalRankingFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:40
 * Description: 
 * -----------------------------------------------------------------
 */
class TotalRankingFragment : BaseFragment(),AdapterView.OnItemClickListener,RankingView {

    var actionbarScrollPoint: Float = 0f
    var summaryScrolled: Float = 0f
    var maxScroll: Float = 0f

    var rankingAdapter: RankingAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    var rankingPresenter: RankingPresenterImpl? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_total_ranking
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
        (activity as MainActivity).toolbar.setTitle("Total ranking")

        rankingPresenter = RankingPresenterImpl(this)

        linearLayoutManager = mRvTotalRanking.layoutManager as LinearLayoutManager

        mRvTotalRanking.setHasFixedSize(true)

        rankingAdapter = RankingAdapter(activity!!,this)
        rankingAdapter?.setHeaderVisible(false)
        rankingAdapter?.setFooterVisible(false)
        mRvTotalRanking.adapter = rankingAdapter


        mRvTotalRanking.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        sendMonthlyRankingRequest()
    }

    fun sendMonthlyRankingRequest () {
        rankingPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.HOT_TOTAL_RANKING_URL),
                DeviceUtils.getPhoneModel()),null,null)
    }

    override fun OnRanking(data: Any?, msg: String) {
        var totalRankingReq = data!! as TrendingReq
        rankingAdapter?.add(totalRankingReq.itemList)
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