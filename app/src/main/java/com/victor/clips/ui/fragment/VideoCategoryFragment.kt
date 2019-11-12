package com.victor.clips.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.ui.adapter.VideoCategoryAdapter
import com.victor.clips.presenter.VideoCategoryPresenterImpl
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.ui.view.VideoCategoryView
import android.support.v7.widget.GridLayoutManager
import com.victor.clips.data.CategoryReq
import com.victor.clips.ui.MainActivity
import com.victor.clips.ui.VideoCategoryActivity
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_category.mRvCategory
import android.view.animation.LayoutAnimationController
import android.view.animation.AnimationUtils
import com.victor.clips.ui.adapter.SlideInLeftAnimatorAdapter
import android.support.v7.widget.RecyclerView


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VideoCategoryFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:40
 * Description: 
 * -----------------------------------------------------------------
 */
class VideoCategoryFragment : BaseFragment(),AdapterView.OnItemClickListener,VideoCategoryView,
        SwipeRefreshLayout.OnRefreshListener {

    var videoCategoryPresenter: VideoCategoryPresenterImpl? = null
    var categoryAdapter: VideoCategoryAdapter? = null
    var gridLayoutManager: GridLayoutManager? = null


    companion object {
        fun newInstance(): VideoCategoryFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): VideoCategoryFragment {
            val fragment = VideoCategoryFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_category
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
        (activity as MainActivity).toolbar.setTitle(getString(R.string.category))
        videoCategoryPresenter = VideoCategoryPresenterImpl(this)

        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSrlVideoCategory.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSrlVideoCategory.setOnRefreshListener(this);

        mRvCategory.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(activity, 2)//这里用线性宫格显示 类似于瀑布流
        mRvCategory.setLayoutManager(gridLayoutManager)

        categoryAdapter = VideoCategoryAdapter(activity!!,this)
        categoryAdapter?.setHeaderVisible(false)
        categoryAdapter?.setFooterVisible(false)

        val animatorAdapter = SlideInLeftAnimatorAdapter(categoryAdapter!!, mRvCategory)

        mRvCategory.adapter = animatorAdapter

        gridLayoutManager?.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if ((position + 2) % 5 == 4) {
                    return 2;
                } else {
                    return 1;
                }

            }
        })

        mRvCategory.addOnScrollListener((activity as MainActivity).OnScrollListener())

    }

    override fun onRefresh() {
        sendCategoryRequest()
    }

    fun initData () {
        sendCategoryRequest()
    }

    fun sendCategoryRequest () {
        videoCategoryPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.FIND_CATEGORIES_URL),
                DeviceUtils.getUDID(),DeviceUtils.getPhoneModel()),null,null)
        mSrlVideoCategory.isRefreshing = true;

        categoryAdapter?.clear()
        categoryAdapter?.notifyDataSetChanged()
    }

    override fun OnVideoCategory(data: Any?, msg: String) {
        mSrlVideoCategory.isRefreshing = false;
        var category = data!! as List<CategoryReq>
        categoryAdapter?.add(category)
        categoryAdapter?.notifyDataSetChanged()
    }

    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        VideoCategoryActivity.intentStart(activity as AppCompatActivity,
                categoryAdapter?.getItem(position) as CategoryReq,
                view?.findViewById(R.id.mIvPoster) as View,
                getString(R.string.transition_album_img))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videoCategoryPresenter!!.detachView()
        videoCategoryPresenter = null
    }

}