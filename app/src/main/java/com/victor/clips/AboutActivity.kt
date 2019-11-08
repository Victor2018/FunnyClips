package com.victor.clips

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import com.victor.clips.util.*
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_video_detail.mVideoToolbar

class AboutActivity : BaseActivity(),View.OnClickListener {

    var fontStyle: Typeface? = null

    companion object {
        fun  intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity,AboutActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_about
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setSupportActionBar(mAboutToolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        fontStyle = Typeface.createFromAsset(getAssets(), "fonts/ZuoAnLianRen.ttf");

        mCtvVersion.setTypeface(fontStyle)
        mTvGmail.setTypeface(fontStyle)
        mTvGithub.setTypeface(fontStyle)
        mTvDownloadApp.setTypeface(fontStyle)
        mTvSupport.setTypeface(fontStyle)

        mTvGithub.movementMethod = LinkMovementMethod.getInstance()
        mTvDownloadApp.movementMethod = LinkMovementMethod.getInstance()

        mFabGitHub.setOnClickListener(this)
    }

    fun initData (){
        mCtvVersion.setText(getString(R.string.current_version) + AppUtil.getAppVersionName(this))
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
            R.id.mFabGitHub -> {
                WebActivity.intentStart(this,getString(R.string.github),getString(R.string.github_url),false)
            }
        }
    }


}
