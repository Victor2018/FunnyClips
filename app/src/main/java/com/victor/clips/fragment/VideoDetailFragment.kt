package com.victor.clips.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.util.Constant
import com.victor.clips.util.DateUtil
import com.victor.kplayer.library.data.YoutubeReq
import com.victor.kplayer.library.util.PlayUtil
import kotlinx.android.synthetic.main.fragment_video_detail.*
import org.victor.khttp.library.util.MainHandler
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: TrendingFragment.java
 * Author: Victor
 * Date: 2018/8/30 15:40
 * Description: 
 * -----------------------------------------------------------------
 */
@SuppressLint("ValidFragment")
class VideoDetailFragment : BaseFragment(),AdapterView.OnItemClickListener,MainHandler.OnMainHandlerImpl{
    override fun handleMainMessage(msg: Message?) {
        when (msg?.what) {
            Constant.Msg.SHOW_YOUTUBE_DETAIL -> {
                mTvTitle.setText((msg.obj as YoutubeReq).title)
                mTvRating.setText((msg.obj as YoutubeReq).rating)
                mTvViewCount.setText("${(msg.obj as YoutubeReq).viewcount} views")
                mTvDuration.setText(DateUtil.formatPlayTime((msg.obj as YoutubeReq).length * 1000))
                mTvType.setText((msg.obj as YoutubeReq).sm!![0].type)
            }
        }
    }

    var playUrl: String? = null

    override fun getLayoutResource(): Int {
        return R.layout.fragment_video_detail
    }
    override fun handleBackEvent(): Boolean {
        return false
    }
    override fun freshFragData() {
    }

    companion object {
        val TAG = VideoDetailFragment::class.java.simpleName
        private const val PLAY_URL = "PLAY_URL"

        fun newInstance(url: String?): VideoDetailFragment {
            val fragment = VideoDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable(PLAY_URL, url)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initialize()
    }

    fun initialize () {
        MainHandler.get().register(this)
    }

    fun initData () {
        playUrl = arguments?.getString(PLAY_URL)
        Log.e(TAG,"playUrl = ${playUrl}")
    }



    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun update(observable: Observable, data: Any) {
        super.update(observable, data)
        if (data is YoutubeReq) {
            var msg = MainHandler.get().obtainMessage(Constant.Msg.SHOW_YOUTUBE_DETAIL,data)
            MainHandler.get().sendMessage(msg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainHandler.get().unregister(this)
    }

}