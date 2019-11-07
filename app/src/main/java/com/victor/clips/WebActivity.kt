package com.victor.clips

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.victor.clips.util.*
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity(),View.OnClickListener {
    companion object {
        val WEB_TITLE_KEY = "WEB_TITLE_KEY"
        val WEB_URL_KEY = "WEB_URL_KEY"
        val WEB_VIEW_BLACK_KEY = "WEB_VIEW_BLACK_KEY"

        fun  intentStart (activity: Context,title: String, url: String) {
            Loger.e("WebActivity","intentStart()......title = " + title)
            Loger.e("WebActivity","intentStart()......url = " + url)
            var intent = Intent(activity,WebActivity::class.java)
            intent.putExtra(WEB_TITLE_KEY, title)
            intent.putExtra(WEB_URL_KEY, url)
            activity.startActivity(intent)
        }
        fun  intentStart (activity: AppCompatActivity,title: String, url: String, isBlackBackground: Boolean) {
            var intent = Intent(activity,WebActivity::class.java)
            intent.putExtra(WEB_TITLE_KEY, title)
            intent.putExtra(WEB_URL_KEY, url)
            intent.putExtra(WEB_VIEW_BLACK_KEY, isBlackBackground)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_web
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setSupportActionBar(mWebToolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }

    fun initData (){
        val title = intent.getStringExtra(WEB_TITLE_KEY)
        val url = intent.getStringExtra(WEB_URL_KEY)
        val isBlack = intent.getBooleanExtra(WEB_VIEW_BLACK_KEY, false)

        Loger.e(TAG,"title = $title")
        Loger.e(TAG,"url = $url")

        mWebToolbar.setTitle(title)
        mPWeb.setWebViewBackgroundColor(isBlack);
        mPWeb.loadUrl(url);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                if (mPWeb.canGoBack()) return true
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

    override fun onResume() {
        super.onResume()
        mPWeb.onResume()
    }

    override fun onPause() {
        super.onPause()
        mPWeb.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPWeb.onDestory()
    }
}
