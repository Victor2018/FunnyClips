package com.victor.clips

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.victor.clips.adapter.RelatedVideoAdapter
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.data.TrendingReq
import com.victor.clips.presenter.RelatedVideoPresenterImpl
import com.victor.clips.util.*
import com.victor.clips.view.RelatedVideoView
import kotlinx.android.synthetic.main.activity_video_detail.*
import android.support.design.widget.AppBarLayout
import android.view.ViewGroup
import com.victor.clips.util.StatusBarUtil
import android.content.pm.ActivityInfo
import android.os.Message
import org.victor.khttp.library.util.MainHandler
import android.content.res.Configuration
import android.support.design.widget.CoordinatorLayout
import com.victor.player.library.module.Player


class VideoDetailActivity : BaseActivity(), View.OnClickListener,RelatedVideoView,
        AdapterView.OnItemClickListener,MainHandler.OnMainHandlerImpl {

    override fun handleMainMessage(message: Message?) {
        when (message?.what) {
            Player.PLAYER_PREPARING -> {
            }
            Player.PLAYER_PREPARED -> {
                mIvVideoPoster.setVisibility(View.INVISIBLE)
            }
            Player.PLAYER_ERROR -> {
            }
            Player.PLAYER_BUFFERING_START -> {
            }
            Player.PLAYER_BUFFERING_END -> {
            }
            Player.PLAYER_PROGRESS_INFO -> {
            }
            Player.PLAYER_COMPLETE -> {
            }
        }
    }

    var relatedVideoPresenter:RelatedVideoPresenterImpl? = null
    var relatedVideoAdapter: RelatedVideoAdapter? = null
    var mPlayer: Player? = null
    var isSmallScreenPlay: Boolean = false
    var isFullScreenPlay: Boolean = false


    companion object {
        fun  intentStart (activity: AppCompatActivity, data: HomeItemInfo, sharedElement: View, sharedElementName: String) {
            var intent = Intent(activity,VideoDetailActivity::class.java)
            var bundle = Bundle()
            bundle.putSerializable(Constant.INTENT_DATA_KEY,data)
            intent.putExtras(bundle)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,sharedElement, sharedElementName)
            ActivityCompat.startActivity(activity!!, intent, options.toBundle())
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_video_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setSupportActionBar(mVideoToolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        MainHandler.get().register(this)
        relatedVideoPresenter = RelatedVideoPresenterImpl(this)

        relatedVideoAdapter = RelatedVideoAdapter(this,this)
        relatedVideoAdapter?.setHeaderVisible(false)
        relatedVideoAdapter?.setFooterVisible(false)

        mRvRelatedVideo.adapter = relatedVideoAdapter

        mPlayer = Player(mTvPlay,MainHandler.get())


        mFabFullScreen.setOnClickListener(this)

        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, yOffset: Int) {
                if (isFullScreenPlay) return
                if (yOffset == 0) {
                    //展开状态
                    mIvVideoPoster.setVisibility(View.INVISIBLE);
                    mPlSmallPlay.setVisibility(View.GONE);
                    removePlayViewFormParent();
                    mCtlVideoTitle.addView(mTvPlay,2);
                    isSmallScreenPlay = false;
                } else if (Math.abs(yOffset) >= appBarLayout!!.totalScrollRange) {
                    //折叠状态
                    mIvVideoPoster.setVisibility(View.VISIBLE);
                    mPlSmallPlay.setVisibility(View.VISIBLE);
                    removePlayViewFormParent();
                    mPlSmallPlay.addView(mTvPlay);
                    isSmallScreenPlay = true;
                } else {
                    //中间状态
                }
            }
        })
    }

    fun initData (){
        var data = intent.extras.getSerializable(Constant.INTENT_DATA_KEY) as HomeItemInfo
        ImageUtils.instance.loadImage(this,mIvVideoPoster,data.data!!.cover!!.feed)
        ImageUtils.instance.loadAvatar(this,mIvAvatar,data.data!!.author!!.icon)
        mCtlVideoTitle.title = data.data!!.title
        mTvVideoDescription.setText(data.data!!.description)

        mPlayer?.playUrl(data!!.data!!.playUrl!!,false)

        sendRelatedVideoRequest(data.data!!.id)
    }

    fun sendRelatedVideoRequest (id: Int) {
        relatedVideoPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.RELATED_VIDEO_URL),
                id,DeviceUtils.getPhoneModel()),null,null)
    }

    override fun OnRelatedVideo(data: Any?, msg: String) {
        var relatedVideo = data!! as TrendingReq
        for (item in relatedVideo.itemList!!) {
            if ("videoSmallCard".equals(item.type)) {
                relatedVideoAdapter?.add(item)
            }
        }
        relatedVideoAdapter?.notifyDataSetChanged()
    }

    private fun removePlayViewFormParent() {
        val parent = mTvPlay.getParent()
        if (parent != null && parent is ViewGroup) {
            parent.removeView(mTvPlay)
        }
    }

    fun fullScreen () {
        mNscrollView.fling(0);
        mNscrollView.smoothScrollTo(0, 0);
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        StatusBarUtil.hideStatusBar(this)
        val layoutParams = appbar.getLayoutParams() as CoordinatorLayout.LayoutParams
        layoutParams.height = CoordinatorLayout.LayoutParams.MATCH_PARENT
        layoutParams.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
        appbar.layoutParams = layoutParams

        isSmallScreenPlay = !isSmallScreenPlay
        isFullScreenPlay = !isFullScreenPlay
        mFabFullScreen.setImageResource(R.mipmap.ic_exit_fullscreen)
    }

    fun exitFullScreen () {
        val mConfiguration = this.resources.configuration //获取设置的配置信息
        val ori = mConfiguration.orientation //获取屏幕方向
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//强制为竖屏
            StatusBarUtil.showStatusBar(this)

            val layoutParams = appbar.getLayoutParams() as CoordinatorLayout.LayoutParams
            layoutParams.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
            layoutParams.height = resources.getDimension(R.dimen.dp_456).toInt()
            appbar.layoutParams = layoutParams

            removePlayViewFormParent()
            if (isSmallScreenPlay) {
                mIvVideoPoster.setVisibility(View.VISIBLE)
                mPlSmallPlay.visibility = View.VISIBLE
                mPlSmallPlay.removeAllViews()
                mPlSmallPlay.addView(mTvPlay)
            } else {
                mIvVideoPoster.setVisibility(View.INVISIBLE)
                mPlSmallPlay.visibility = View.GONE
                mCtlVideoTitle.addView(mTvPlay, 2)
            }
        }
        isSmallScreenPlay = !isSmallScreenPlay
        isFullScreenPlay = !isFullScreenPlay
        mFabFullScreen.setImageResource(R.mipmap.ic_fullscreen)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mFabFullScreen -> {
                if (isFullScreenPlay) {
                    exitFullScreen()
                } else {
                    fullScreen()
                }
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onBackPressed() {
        if (isFullScreenPlay) {
            exitFullScreen()
            return
        }
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        mPlayer?.resume()
    }

    override fun onPause() {
        super.onPause()
        mPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        relatedVideoPresenter!!.detachView()
        relatedVideoPresenter = null
        MainHandler.get().unregister(this)
        mPlayer?.stop()
        mPlayer = null
    }

}
