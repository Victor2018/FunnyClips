package com.victor.clips.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.victor.clips.HomeActivity
import com.victor.clips.R
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.util.Loger
import com.victor.clips.util.WebConfig
import com.victor.clips.widget.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import android.widget.*
import com.victor.clips.adapter.TrendingAdapter
import com.victor.clips.data.TrendingReq
import com.victor.clips.presenter.DailySelectionPresenterImpl
import com.victor.clips.view.DailySelectionView
import com.victor.clips.widget.VerticalBannerView
import android.util.TypedValue
import com.victor.clips.MainActivity
import android.widget.TextView
import android.widget.ViewSwitcher
import android.graphics.Color
import android.view.animation.AnimationUtils


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
class HomeFragment : BaseFragment(),AdapterView.OnItemClickListener,
        VerticalBannerView.OnFlipListener, DailySelectionView {

    var TAG = "HomeFragment"

    var dailySelectionPresenter: DailySelectionPresenterImpl? = null
    var trendingAdapter:TrendingAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    var homeActivity: HomeActivity? = null

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
        dailySelectionPresenter = DailySelectionPresenterImpl(this);

        homeActivity = activity as HomeActivity?
        trendingAdapter = TrendingAdapter(context!!,this)

        mRvHome.addItemDecoration(SimpleDividerItemDecoration(context))
        mRvHome.adapter = trendingAdapter
        mRvHome.setHasFixedSize(true)

        mVBanner.setOnFlipListener(this)

        mTsTitle.setInAnimation(AnimationUtils.loadAnimation(homeActivity,R.anim.anim_bottom_enter));
        mTsTitle.setOutAnimation(AnimationUtils.loadAnimation(homeActivity,R.anim.anim_top_exit));
        mTsTitle.setFactory(ViewSwitcher.ViewFactory {
            val tv = TextView(homeActivity)
            tv.setSingleLine(true)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28f)
            tv.setTextColor(Color.WHITE)
            tv
        })

    }

    fun initData () {
        sendDailySelectionRequest()

    }

    fun sendDailySelectionRequest () {
        dailySelectionPresenter?.sendRequest(WebConfig.getRequestUrl(WebConfig.DAILY_SELECTION_URL),null,null)
    }

    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Loger.e(TAG,"onItemClick()......")
        homeActivity?.playYoutube(trendingAdapter?.getItem(position)?.data!!.playUrl)
    }


    override fun OnDailySelection(data: Any?, msg: String) {
        Loger.e(TAG,"onComplete-OnDailySelection()......")
        var trendingReq = data as TrendingReq
        var bannerList: MutableList<HomeItemInfo> = mutableListOf()
        for (item in trendingReq.itemList!!) {
            if ("video".equals(item.type)) {
                bannerList.add(item)
            }
        }
        trendingAdapter!!.clear()
        trendingAdapter!!.add(bannerList)
        trendingAdapter!!.notifyDataSetChanged()

        mVBanner.setData(bannerList)
        mVBanner.resumePlay()

        mTsTitle.setText(trendingAdapter!!.getDatas()!!.get(0).data!!.title)
    }

    override fun onShowNext(flipper: ViewFlipper,position: Int) {
        mTsTitle.setText(trendingAdapter!!.getDatas()!!.get(position).data!!.title)
    }

    override fun update(observable: Observable, data: Any) {
        super.update(observable, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dailySelectionPresenter!!.detachView()
        dailySelectionPresenter = null
    }

}