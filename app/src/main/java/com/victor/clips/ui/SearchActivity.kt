package com.victor.clips.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.victor.clips.R
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.data.TrendingReq
import com.victor.clips.presenter.SearchVideoPresenterImpl
import com.victor.clips.ui.adapter.ScaleInAnimatorAdapter
import com.victor.clips.ui.adapter.SearchVideoAdapter
import com.victor.clips.ui.adapter.SlideInLeftAnimatorAdapter
import com.victor.clips.ui.view.SearchVideoView
import com.victor.clips.util.DeviceUtils
import com.victor.clips.util.SnackbarUtil
import com.victor.clips.util.WebConfig
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_weekly_ranking.*


class SearchActivity : BaseActivity(),SearchView.OnQueryTextListener,AdapterView.OnItemClickListener,
        SearchVideoView {

    var searchVideoPresenter: SearchVideoPresenterImpl? = null
    var searchVideoAdapter: SearchVideoAdapter? = null

    companion object {
        fun  intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, SearchActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_search
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun initialize () {
        setSupportActionBar(mSearchToolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        searchVideoPresenter = SearchVideoPresenterImpl(this)

        searchVideoAdapter = SearchVideoAdapter(this,this)
        searchVideoAdapter?.setHeaderVisible(false)
        searchVideoAdapter?.setFooterVisible(false)

        val animatorAdapter = ScaleInAnimatorAdapter(searchVideoAdapter!!,mRvSearch)
        mRvSearch.adapter = animatorAdapter
    }

    fun sendSearchRequest (query: String?) {
        searchVideoAdapter?.clear()
        searchVideoAdapter?.notifyDataSetChanged()

        searchVideoPresenter?.sendRequest(String.format(WebConfig.getRequestUrl(WebConfig.SEARCH_URL),
                query, DeviceUtils.getPhoneModel()),null,null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchMenuItem = menu?.findItem(R.id.action_search);
        val searchView = searchMenuItem?.actionView as SearchView
        val textView = searchView.findViewById(R.id.search_src_text) as TextView;
        textView.setTextColor(Color.WHITE);
        textView.setHighlightColor(getResources().getColor(R.color.colorAccent));
        textView.setCursorVisible(true);

        searchView.setMaxWidth(Integer.MAX_VALUE)
        searchView.onActionViewExpanded()
        searchView.setQueryHint(getString(R.string.search_tip))
        searchView.setOnQueryTextListener(this)
        searchView.setSubmitButtonEnabled(true)
        searchView.setFocusable(false)
        searchView.setFocusableInTouchMode(false)

        searchView.setOnQueryTextFocusChangeListener { view, queryTextFocused ->
            if (!queryTextFocused) {
                if (TextUtils.isEmpty(searchView.query)) {
                    onBackPressed()
                    return@setOnQueryTextFocusChangeListener
                }
                searchMenuItem.collapseActionView()
                searchView.setQuery("", false)
            }
        }

        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.setAccessible(true)
            val mGoButton = field.get(searchView) as ImageView
            mGoButton.setImageResource(R.drawable.ic_search)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onCreateOptionsMenu(menu)
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

    override fun OnSearchVideo(data: Any?, msg: String) {
        if (data == null) {
            SnackbarUtil.ShortSnackbar(mRvSearch,msg, SnackbarUtil.ALERT).show()
            return
        }
        var relatedVideo = data!! as TrendingReq
        searchVideoAdapter?.add( relatedVideo.itemList!!)
        searchVideoAdapter?.notifyDataSetChanged()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        VideoDetailActivity.intentStart(this,
                searchVideoAdapter?.getItem(position) as HomeItemInfo,
                view?.findViewById(R.id.mIvSearchPoster) as View,
                getString(R.string.transition_video_img))
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        sendSearchRequest(query)
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        searchVideoPresenter?.detachView()
        searchVideoPresenter = null
    }

}
