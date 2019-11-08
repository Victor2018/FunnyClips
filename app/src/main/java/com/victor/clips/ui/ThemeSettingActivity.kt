package com.victor.clips.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.victor.clips.util.*
import android.support.v7.widget.GridLayoutManager
import com.victor.clips.R
import com.victor.clips.ui.adapter.ColorAdapter
import com.victor.clips.data.Theme
import kotlinx.android.synthetic.main.activity_theme_setting.*
import kotlinx.android.synthetic.main.activity_video_detail.mVideoToolbar

class ThemeSettingActivity : BaseActivity(),View.OnClickListener,AdapterView.OnItemClickListener {

    var colorRes: Int = 0
    var colorAdapter: ColorAdapter? = null
    var gridLayoutManager: GridLayoutManager? = null
    var lastTheme: Int = 0;

    companion object {
        fun  intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, ThemeSettingActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_theme_setting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        setSupportActionBar(mVideoToolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        mRvColor.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(this, 5)//这里用线性宫格显示 类似于瀑布流
        mRvColor.setLayoutManager(gridLayoutManager)

        colorAdapter = ColorAdapter(this,this)
        colorAdapter?.setHeaderVisible(false)
        colorAdapter?.setFooterVisible(false)
        mRvColor.adapter = colorAdapter

        mBtnCancel.setOnClickListener(this)
        mBtnOk.setOnClickListener(this)

    }

    fun initData (){
        colorAdapter?.add(resources.getIntArray(R.array.color_list).toList())
        colorAdapter?.notifyDataSetChanged()

        lastTheme = getCurrentTheme()
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

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        colorRes = colorAdapter?.getItem(position)!!
        colorAdapter?.currentPosition = position
        colorAdapter?.notifyDataSetChanged()
        ThemeUtils.setTheme(this,colorRes)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mBtnCancel -> {
                ThemeUtils.setTheme(this,lastTheme)
                finish()
            }
            R.id.mBtnOk -> {
                finish()
            }
        }
    }

    fun getCurrentTheme (): Int {
        var theme = resources.getColor(R.color.colorRedPrimary)
        val themeName = SharePreferencesUtil.getCurrentTheme(this)
        when (themeName) {
            Theme.Blue -> theme = resources.getColor(R.color.colorBluePrimary)
            Theme.Red -> theme = resources.getColor(R.color.colorRedPrimary)
            Theme.Brown -> theme = resources.getColor(R.color.colorBrownPrimary)
            Theme.Green -> theme = resources.getColor(R.color.colorGreenPrimary)
            Theme.Purple -> theme = resources.getColor(R.color.colorPurplePrimary)
            Theme.Teal -> theme = resources.getColor(R.color.colorTealPrimary)
            Theme.Pink -> theme = resources.getColor(R.color.colorPinkPrimary)
            Theme.DeepPurple -> theme = resources.getColor(R.color.colorDeepPurplePrimary)
            Theme.Orange -> theme = resources.getColor(R.color.colorOrangePrimary)
            Theme.Indigo -> theme = resources.getColor(R.color.colorIndigoPrimary)
            Theme.LightGreen -> theme = resources.getColor(R.color.colorLightGreenPrimary)
            Theme.Lime -> theme = resources.getColor(R.color.colorLimePrimary)
            Theme.DeepOrange -> theme = resources.getColor(R.color.colorDeepOrangePrimary)
            Theme.Cyan -> theme = resources.getColor(R.color.colorCyanPrimary)
            Theme.BlueGrey -> theme = resources.getColor(R.color.colorBlueGreyPrimary)
        }
        return theme
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ThemeUtils.setTheme(this,lastTheme)
    }


}
