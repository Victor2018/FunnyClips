package com.victor.clips.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.alibaba.fastjson.JSON
import com.victor.clips.R
import com.victor.clips.app.App
import com.victor.clips.data.YoutubeReq
import com.victor.clips.module.DataObservable
import com.victor.clips.util.Constant
import com.victor.kplayer.library.data.FacebookReq
import com.victor.kplayer.library.data.VimeoReq
import com.victor.kplayer.library.interfaces.OnExtractListener
import com.victor.kplayer.library.module.PlayHelper
import kotlinx.android.synthetic.main.fragment_player.*

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
class PlayerFragment : BaseFragment(),AdapterView.OnItemClickListener,OnExtractListener{

    var playUrl: String? = null
    var playHelper:PlayHelper? = null

    var mHandler = Handler(){
        when(it.what) {
            Constant.Msg.SHOW_HOME_DATA ->{
            }
        }
        false
    };
    override fun getLayoutResource(): Int {
        return R.layout.fragment_player
    }
    override fun handleBackEvent(): Boolean {
        return false
    }
    override fun freshFragData() {

    }

    companion object {
        val TAG = PlayerFragment::class.java.simpleName
        private const val PLAY_URL = "PLAY_URL"

        fun newInstance(url: String?): PlayerFragment {
            val fragment = PlayerFragment()
            val bundle = Bundle()
            bundle.putSerializable(PLAY_URL, url)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        playHelper = PlayHelper(App.instance(),videoPlayer,mHandler)
    }

    fun initData () {
        playUrl = arguments?.getString(PLAY_URL)
        Log.e(TAG,"playUrl = ${playUrl}")
        playHelper?.play(playUrl!!,this)
    }


    override fun onItemClick(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun OnFacebook(facebookReq: FacebookReq?) {
    }

    override fun OnVimeo(vimeoReq: VimeoReq?) {
        Log.e(TAG, "OnYoutube-vimeoReq = " + JSON.toJSONString(vimeoReq))
        DataObservable.instance.setData(vimeoReq!!)
    }

    override fun OnYoutube(youtubeReq: com.victor.kplayer.library.data.YoutubeReq?) {
        Log.e(TAG, "OnYoutube-youtubeReq = " + JSON.toJSONString(youtubeReq))
        Log.e(TAG, "OnYoutube-title = " + youtubeReq?.title)
        Log.e(TAG, "OnYoutube-author = " + youtubeReq?.author)
        Log.e(TAG, "OnYoutube-bigthumb = " + youtubeReq?.bigthumb)
        Log.e(TAG, "OnYoutube-bigthumbhd = " + youtubeReq?.bigthumbhd)
        Log.e(TAG, "OnYoutube-category = " + youtubeReq?.category)
        Log.e(TAG, "OnYoutube-channelId = " + youtubeReq?.channelId)
        Log.e(TAG, "OnYoutube-description = " + youtubeReq?.description)
        Log.e(TAG, "OnYoutube-duration = " + youtubeReq?.duration)
        Log.e(TAG, "OnYoutube-formats = " + youtubeReq?.formats)
        Log.e(TAG, "OnYoutube-keywords = " + youtubeReq?.keywords)
        Log.e(TAG, "OnYoutube-viewcount = " + youtubeReq?.viewcount)
        Log.e(TAG, "OnYoutube-rating = " + youtubeReq?.rating)
        Log.e(TAG, "OnYoutube-hlsvp = " + youtubeReq?.hlsvp)
        Log.e(TAG, "OnYoutube-have_gdata = " + youtubeReq?.have_gdata)
        Log.e(TAG, "OnYoutube-have_basic = " + youtubeReq?.have_basic)
        Log.e(TAG, "OnYoutube-youtubeReq?.sm[0].itag = " + youtubeReq?.sm!![0].itag)
        Log.e(TAG, "OnYoutube-youtubeReq?.sm[0].fallbackHost = " + youtubeReq?.sm!![0].fallbackHost)
        Log.e(TAG, "OnYoutube-youtubeReq?.sm[0].type = " + youtubeReq?.sm!![0].type)
        Log.e(TAG, "OnYoutube-youtubeReq?.sm[0].s = " + youtubeReq?.sm!![0].s)
        Log.e(TAG, "OnYoutube-youtubeReq?.sm[0].quality = " + youtubeReq?.sm!![0].quality)
        Log.e(TAG, "OnYoutube-youtubeReq?.sm[0].url = " + youtubeReq?.sm!![0].url)
        Log.e(TAG, "OnYoutube-youtubeReq?.sm[0].sig = " + youtubeReq?.sm!![0].sig)

        DataObservable.instance.setData(youtubeReq!!)
    }

}