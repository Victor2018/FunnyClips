package com.victor.clips

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.victor.clips.adapter.CategoryAdapter
import com.victor.clips.adapter.CategoryDetailAdapter
import com.victor.clips.data.CategoryInfo
import com.victor.clips.data.CategoryReq
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.data.TrendingReq
import com.victor.clips.module.DataObservable
import com.victor.clips.presenter.CategoryDetailPresenterImpl
import com.victor.clips.util.*
import com.victor.clips.view.CategoryDetailView
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_video_category.*
import kotlinx.android.synthetic.main.activity_video_category.toolbar


class CategoryActivity : BaseActivity(),View.OnClickListener,AdapterView.OnItemClickListener {
    var categoryAdapter: CategoryAdapter? = null
    companion object {
        fun  intentStart (activity: AppCompatActivity, sharedElement: View, sharedElementName: String) {
            var intent = Intent(activity,CategoryActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,sharedElement, sharedElementName)
            ActivityCompat.startActivity(activity!!, intent, options.toBundle())
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_category
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        mRvCategory.setHasFixedSize(true)
        categoryAdapter = CategoryAdapter(this,this)
        categoryAdapter?.setHeaderVisible(false)
        categoryAdapter?.setFooterVisible(false)
        mRvCategory.adapter = categoryAdapter

        mFabCategory.setOnClickListener(this)
    }

    fun initData (){
        var categoryTitleList = resources.getStringArray(R.array.category_list)
        var categoryImgResList = resources.obtainTypedArray(R.array.beautiful)
        for (index in 0 until categoryTitleList.size) {
            var info = CategoryInfo()
            info.categoryName = categoryTitleList[index]
            info.categoryImgRes = categoryImgResList.getResourceId(index, 0)
            categoryAdapter?.add(info)
        }
        var position = SharePreferencesUtil.getInt(this,Constant.CATEGORY_POSITION_KEY,-1)
        mIvCategoryPoster.setImageResource(categoryAdapter?.getItem(position)?.categoryImgRes!!)
        categoryAdapter?.currentPosition = position

        categoryAdapter?.notifyDataSetChanged()
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
            R.id.mFabCategory -> {
                SharePreferencesUtil.putInt(this,Constant.CATEGORY_POSITION_KEY,categoryAdapter?.currentPosition!!)
                onBackPressed()
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mIvCategoryPoster.setImageResource(categoryAdapter?.getItem(position)?.categoryImgRes!!)
        categoryAdapter?.currentPosition = position
        categoryAdapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
