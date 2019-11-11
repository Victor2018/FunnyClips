package com.victor.clips.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.data.CategoryReq
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.data.TrendingReq
import com.victor.clips.presenter.CategoryDetailPresenterImpl
import com.victor.clips.ui.adapter.*
import com.victor.clips.util.Constant
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.ImageUtils
import com.victor.clips.util.WebConfig
import com.victor.clips.ui.view.CategoryDetailView
import kotlinx.android.synthetic.main.activity_video_category.*
import kotlinx.android.synthetic.main.activity_video_category.appbar


class VideoCategoryActivity : BaseActivity(),View.OnClickListener,CategoryDetailView,
        AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener,
        AppBarLayout.OnOffsetChangedListener {

    var categoryDetailPresenter: CategoryDetailPresenterImpl? = null
    var categoryDetailAdapter: CategoryDetailAdapter? = null
    var categoryId: Int = 0

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

        //设置 进度条的颜色变化，最多可以设置4种颜色
        mSrlCategory.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSrlCategory.setOnRefreshListener(this);

        mRvCategoryDetail.setHasFixedSize(true)
        categoryDetailAdapter = CategoryDetailAdapter(this,this)
        categoryDetailAdapter?.setHeaderVisible(false)
        categoryDetailAdapter?.setFooterVisible(false)

//        val animatorAdapter = AlphaAnimatorAdapter(categoryDetailAdapter!!,mRvCategoryDetail)
        val animatorAdapter = ScaleInAnimatorAdapter(categoryDetailAdapter!!,mRvCategoryDetail)
//        val animatorAdapter = SlideInLeftAnimatorAdapter(categoryDetailAdapter!!,mRvCategoryDetail)
//        val animatorAdapter = SlideInRightAnimatorAdapter(categoryDetailAdapter!!,mRvCategoryDetail)
//        val animatorAdapter = SlideInBottomAnimatorAdapter(categoryDetailAdapter!!,mRvCategoryDetail)
//        val animatorAdapter = SwingBottomInAnimationAdapter(categoryDetailAdapter!!,mRvCategoryDetail)
        mRvCategoryDetail.adapter = animatorAdapter

        mVideoCategoryGithub.setOnClickListener(this)

        appbar.addOnOffsetChangedListener(this)
    }

    fun initData (){
        var categoryReq = intent.extras.getSerializable(Constant.INTENT_DATA_KEY) as CategoryReq
        ImageUtils.instance.loadImage(this,mIvPoster,categoryReq.bgPicture)
        mCtlTitle.title = categoryReq.name
        categoryId = categoryReq.id
        sendCategoryDetailRequest()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset == 0) {
            //展开状态
            mSrlCategory.isEnabled = true;
        } else if (Math.abs(verticalOffset) >= appBarLayout!!.totalScrollRange) {
            //折叠状态
            mSrlCategory.isEnabled = false;
        } else {
            //中间状态
        }
    }

    override fun onRefresh() {
        sendCategoryDetailRequest()
    }

    fun sendCategoryDetailRequest () {
        categoryDetailPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.CATEGORY_DETAIL_URL),
                categoryId, DeviceUtils.getPhoneModel()),null,null)
        mSrlCategory.isRefreshing = true
    }

    override fun OnCategoryDetail(data: Any?, msg: String) {
        mSrlCategory.isRefreshing = false
        var category = data!! as TrendingReq
        categoryDetailAdapter?.clear()
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
            R.id.mVideoCategoryGithub -> {
                WebActivity.intentStart(this, getString(R.string.github), getString(R.string.github_url), false)
            }
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
