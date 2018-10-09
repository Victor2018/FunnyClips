package com.victor.clips.fragment

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.victor.clips.R
import com.victor.clips.adapter.YoutubeAdapter
import com.victor.clips.app.App
import com.victor.clips.data.YoutubeReq
import com.victor.clips.module.SpiderHelper
import com.victor.clips.util.Constant
import com.victor.clips.util.WebConfig
import com.victor.clips.widget.gallery.AnimManager
import com.victor.clips.widget.gallery.GalleryRecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import org.victor.funny.util.ToastUtils
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:40
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeFragment : BaseFragment(),AdapterView.OnItemClickListener,GalleryRecyclerView.OnItemClickListener {

    var TAG = "HomeFragment"

    var spiderHelper:SpiderHelper? = null
    var youtubeAdapter: YoutubeAdapter? = null
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
        spiderHelper = SpiderHelper(App.instance())
        youtubeAdapter = YoutubeAdapter(App.instance(),this)
        mRvHot.adapter = youtubeAdapter
        mRvHot
                // 设置滑动速度（像素/s）
                .initFlingSpeed(9000)
                // 设置页边距和左右图片的可见宽度，单位dp
                .initPageParams(0, 40)
                // 设置切换动画的参数因子
                .setAnimFactor(0.1f)
                // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                // 设置点击事件
                .setOnItemClickListener(this)
                // 设置自动播放
                .autoPlay(false)
                // 设置自动播放间隔时间 ms
                .intervalTime(2000)
                // 设置初始化的位置
                .initPosition(1)
                // 在设置完成之后，必须调用setUp()方法
                .setUp()
    }

    fun initData () {
//        spiderHelper?.sendRequestWithParms(Constant.Msg.REQUEST_YOUTUBE,WebConfig.YOUTUBE_HOT_URL)
        spiderHelper?.sendRequestWithParms(Constant.Msg.REQUEST_VIMEO,"https://vimeo.com/categories/music")
    }

    /**
     * 设置背景高斯模糊
     */
    fun setBlurImage(forceUpdate: Boolean) {
        val mCurViewPosition = mRvHot.getScrolledPosition()

        val isSamePosAndNotUpdate = mCurViewPosition == mLastDraPosition && !forceUpdate

        if (youtubeAdapter == null || mRvHot == null || isSamePosAndNotUpdate) {
            return
        }
        mRecyclerView.post(Runnable {
            //如果是Fragment的话，需要判断Fragment是否Attach当前Activity，否则getResource会报错
            /*if (!isAdded()) {
            // fix fragment not attached to Activity
            return;
        }*/
            // 获取当前位置的图片资源ID
            val resourceId = (mRecyclerView.getAdapter() as RecyclerAdapter).getResId(mCurViewPosition)
            // 将该资源图片转为Bitmap
            val resBmp = BitmapFactory.decodeResource(resources, resourceId)
            // 将该Bitmap高斯模糊后返回到resBlurBmp
            val resBlurBmp = BlurBitmapUtil.blurBitmap(mRecyclerView.getContext(), resBmp, 15f)
            // 再将resBlurBmp转为Drawable
            val resBlurDrawable = BitmapDrawable(resBlurBmp)
            // 获取前一页的Drawable
            val preBlurDrawable = if (mTSDraCacheMap.get(KEY_PRE_DRAW) == null) resBlurDrawable else mTSDraCacheMap.get(KEY_PRE_DRAW)

            /* 以下为淡入淡出效果 */
            val drawableArr = arrayOf<Drawable>(preBlurDrawable, resBlurDrawable)
            val transitionDrawable = TransitionDrawable(drawableArr)
            mContainer.setBackgroundDrawable(transitionDrawable)
            transitionDrawable.startTransition(500)

            // 存入到cache中
            mTSDraCacheMap.put(KEY_PRE_DRAW, resBlurDrawable)
            // 记录上一次高斯模糊的位置
            mLastDraPosition = mCurViewPosition
        })
    }


    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        setBlurImage(true)
    }

    override fun update(observable: Observable, data: Any) {
        super.update(observable, data)
        if (data != null) {
            if (data is YoutubeReq) {
                var mYoutubeReq: YoutubeReq = data
                if (mYoutubeReq != null) {
                    if (mYoutubeReq.data != null && mYoutubeReq!!.data!!.size > 0) {
                        for (info in mYoutubeReq!!.data!!) {
                            Log.e(TAG,"info.videoName = ${info.videoName}")
                            Log.e(TAG,"info.url = ${info.url}")
                            Log.e(TAG,"info.poster = ${info.poster}")
                        }

                    }
                }
            }

        }
    }

    override fun onItemClick(view: View, position: Int) {
        ToastUtils.showShort("position=$position")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        spiderHelper!!.onDestroy()
        spiderHelper = null
    }

}