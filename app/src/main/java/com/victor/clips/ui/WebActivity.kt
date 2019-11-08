package com.victor.clips.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import com.victor.clips.R
import com.victor.clips.util.*
import kotlinx.android.synthetic.main.activity_web.*
import android.content.ClipData
import android.content.ClipboardManager
import kotlinx.android.synthetic.main.activity_main.*


class WebActivity : BaseActivity(),View.OnClickListener {
    var url: String? = null
    companion object {
        val WEB_TITLE_KEY = "WEB_TITLE_KEY"
        val WEB_URL_KEY = "WEB_URL_KEY"
        val WEB_VIEW_BLACK_KEY = "WEB_VIEW_BLACK_KEY"

        fun  intentStart (activity: Context,title: String, url: String) {
            Loger.e("WebActivity","intentStart()......title = " + title)
            Loger.e("WebActivity","intentStart()......url = " + url)
            var intent = Intent(activity, WebActivity::class.java)
            intent.putExtra(WEB_TITLE_KEY, title)
            intent.putExtra(WEB_URL_KEY, url)
            activity.startActivity(intent)
        }
        fun  intentStart (activity: AppCompatActivity,title: String, url: String, isBlackBackground: Boolean) {
            var intent = Intent(activity, WebActivity::class.java)
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
        url = intent.getStringExtra(WEB_URL_KEY)
        val isBlack = intent.getBooleanExtra(WEB_VIEW_BLACK_KEY, false)

        Loger.e(TAG,"title = $title")
        Loger.e(TAG,"url = $url")

        mWebToolbar.title = title
        mPWeb.setWebViewBackgroundColor(isBlack);
        mPWeb.loadUrl(url!!);
    }

    override fun onClick(v: View?) {
        when(v?.id) {
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_web, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when(item.itemId) {
            android.R.id.home -> {
                if (mPWeb.canGoBack()) return true
                onBackPressed()
                return true
            }
            R.id.action_cope -> {
                copyToClipboard()
                return true
            }
            R.id.action_open_browser -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                return true
            }
            R.id.action_share_web -> {
                var intentshare = Intent(Intent.ACTION_SEND);
                intentshare.setType(Constant.SHARE_TYPE)
                        .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share))
                        .putExtra(Intent.EXTRA_TEXT,url);
                Intent.createChooser(intentshare, getString(R.string.share));
                startActivity(intentshare);
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun copyToClipboard() {
        //获取剪贴板管理器：
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText("Label", url)
        // 将ClipData内容放到系统剪贴板里。
        cm!!.setPrimaryClip(mClipData)
        SnackbarUtil.ShortSnackbar(mPWeb,getString(R.string.copy_to_clipboard)).show()
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
