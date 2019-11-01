package com.victor.clips.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.adapter.VideoCategoryAdapter
import com.victor.clips.presenter.VideoCategoryPresenterImpl
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.view.VideoCategoryView
import android.support.v7.widget.GridLayoutManager
import com.victor.clips.data.CategoryReq
import kotlinx.android.synthetic.main.fragment_category.*
import com.victor.clips.MainActivity
import android.support.v7.widget.RecyclerView
import android.graphics.Color
import com.victor.clips.VideoCategoryActivity
import com.victor.clips.util.Constant
import kotlinx.android.synthetic.main.content_main.*
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_category.mRvCategory


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
class VideoCategoryFragment : BaseFragment(),AdapterView.OnItemClickListener,VideoCategoryView {
    var actionbarScrollPoint: Float = 0f
    var summaryScrolled: Float = 0f
    var maxScroll: Float = 0f

    var videoCategoryPresenter: VideoCategoryPresenterImpl? = null
    var categoryAdapter: VideoCategoryAdapter? = null
    var gridLayoutManager: GridLayoutManager? = null

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
        (activity as MainActivity).toolbar.setTitle("Category")
        videoCategoryPresenter = VideoCategoryPresenterImpl(this)

        mRvCategory.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(activity, 2)//这里用线性宫格显示 类似于瀑布流
        mRvCategory.setLayoutManager(gridLayoutManager)

        categoryAdapter = VideoCategoryAdapter(activity!!,this)
        categoryAdapter?.setHeaderVisible(false)
        categoryAdapter?.setFooterVisible(false)
        mRvCategory.adapter = categoryAdapter

        gridLayoutManager?.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if ((position + 2) % 5 == 4) {
                    return 2;
                } else {
                    return 1;
                }

            }
        })

        mRvCategory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
               val lastVisibleItemPosition = gridLayoutManager?.findLastVisibleItemPosition()

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
        sendCategoryRequest()
    }

    fun sendCategoryRequest () {
        videoCategoryPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.FIND_CATEGORIES_URL),
                DeviceUtils.getUDID(),DeviceUtils.getPhoneModel()),null,null)
    }

    override fun OnVideoCategory(data: Any?, msg: String) {
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