package com.victor.clips.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.ui.adapter.CategoryDetailAdapter
import com.victor.clips.data.CategoryReq
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.data.TrendingReq
import com.victor.clips.presenter.CategoryDetailPresenterImpl
import com.victor.clips.util.Constant
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.ImageUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.ui.view.CategoryDetailView
import kotlinx.android.synthetic.main.activity_video_category.*


class VideoCategoryActivity : BaseActivity(),View.OnClickListener,CategoryDetailView,
        AdapterView.OnItemClickListener {
    var categoryDetailPresenter: CategoryDetailPresenterImpl? = null
    var categoryDetailAdapter: CategoryDetailAdapter? = null

    companion object {
        fun  intentStart (activity: AppCompatActivity, category: CategoryReq, sharedElement: View, sharedElementName: String) {
            var intent = Intent(activity, VideoCategoryActivity::class.java)
            var bundle = Bundle()
            bundle.putSerializable(Constant.INTENT_DATA_KEY,category)
            intent.putExtras(bundle)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,sharedElement, sharedElementName)
            ActivityCompat.startActivity(activity!!, intent, options.toBundle())
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_video_category
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        categoryDetailPresenter = CategoryDetailPresenterImpl(this)

        mRvCategoryDetail.setHasFixedSize(true)
        categoryDetailAdapter = CategoryDetailAdapter(this,this)
        categoryDetailAdapter?.setHeaderVisible(false)
        categoryDetailAdapter?.setFooterVisible(false)

        mRvCategoryDetail.adapter = categoryDetailAdapter
    }

    fun initData (){
        var categoryReq = intent.extras.getSerializable(Constant.INTENT_DATA_KEY) as CategoryReq
        ImageUtils.instance.loadImage(this,mIvPoster,categoryReq.bgPicture)
        mCtlTitle.title = categoryReq.name
        sendCategoryDetailRequest(categoryReq.id)
    }

    fun sendCategoryDetailRequest (id: Int) {
        categoryDetailPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.CATEGORY_DETAIL_URL),
                id, DeviceUtils.getPhoneModel()),null,null)
    }

    override fun OnCategoryDetail(data: Any?, msg: String) {
        var category = data!! as TrendingReq
        categoryDetailAdapter?.add(category.itemList)
        categoryDetailAdapter?.notifyDataSetChanged()
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
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        VideoDetailActivity.intentStart(this,
                categoryDetailAdapter?.getItem(position) as HomeItemInfo,
                view?.findViewById(R.id.mIvCategoryDetailPoster) as View,
                getString(R.string.transition_video_img))
    }


    override fun onDestroy() {
        super.onDestroy()
        categoryDetailPresenter!!.detachView()
        categoryDetailPresenter = null
    }

}
