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
import kotlinx.android.synthetic.main.activity_main.*
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.data.TrendingReq
import com.victor.clips.presenter.RelatedVideoPresenterImpl
import com.victor.clips.util.*
import com.victor.clips.view.RelatedVideoView
import kotlinx.android.synthetic.main.activity_video_detail.*


class VideoDetailActivity : BaseActivity(), View.OnClickListener,RelatedVideoView,AdapterView.OnItemClickListener {

    var relatedVideoPresenter:RelatedVideoPresenterImpl? = null
    var relatedVideoAdapter: RelatedVideoAdapter? = null

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

        relatedVideoPresenter = RelatedVideoPresenterImpl(this)

        relatedVideoAdapter = RelatedVideoAdapter(this,this)
        relatedVideoAdapter?.setHeaderVisible(false)
        relatedVideoAdapter?.setFooterVisible(false)

        mRvRelatedVideo.adapter = relatedVideoAdapter

    }

    fun initData (){
        var data = intent.extras.getSerializable(Constant.INTENT_DATA_KEY) as HomeItemInfo
        ImageUtils.instance.imageGauss(this,mIvVideoPoster,data.data!!.cover!!.feed)
        ImageUtils.instance.loadAvatar(this,mIvAvatar,data.data!!.author!!.icon)
        mCtlVideoTitle.title = data.data!!.title
        mTvVideoDescription.setText(data.data!!.description)

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
            R.id.fab -> {
                SnackbarUtil.ShortSnackbar(drawer,"github").show()
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onDestroy() {
        super.onDestroy()
        relatedVideoPresenter!!.detachView()
        relatedVideoPresenter = null
    }

}
