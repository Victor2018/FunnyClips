package com.victor.clips.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Message
import android.view.MenuItem
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import com.victor.clips.util.*
import android.support.v7.widget.GridLayoutManager
import android.widget.CompoundButton
import com.victor.clips.R
import com.victor.clips.ui.adapter.ColorAdapter
import com.victor.clips.data.Theme
import com.victor.clips.ui.adapter.ScaleInAnimatorAdapter
import kotlinx.android.synthetic.main.activity_theme_setting.*
import kotlinx.android.synthetic.main.activity_video_detail.mVideoToolbar
import org.victor.khttp.library.util.MainHandler

class ThemeSettingActivity : BaseActivity(),View.OnClickListener,AdapterView.OnItemClickListener,
        CompoundButton.OnCheckedChangeListener,MainHandler.OnMainHandlerImpl {

    var colorRes: Int = 0
    var colorAdapter: ColorAdapter? = null
    var gridLayoutManager: GridLayoutManager? = null
    var lastTheme: Int = 0;
    var fontStyle: Typeface? = null

    override fun handleMainMessage(message: Message?) {
        when(message?.what) {
            Constant.Msg.TIME_CHANGE -> {
                if (message.arg1 == 1) {
                    ThemeUtils.setTheme(this,getCurrentTheme())
                    ConfigLocal.updateDayNightThemeGuide(this, "", false)
                    mToggleDayNightsetting.isChecked = false
                } else {
                    ThemeUtils.setTheme(this,resources.getColor(R.color.colorNightPrimary))
                    ConfigLocal.updateDayNightThemeGuide(this, "", true)
                    mToggleDayNightsetting.isChecked = true
                }
            }
        }
    }

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
        MainHandler.get().register(this)
        setSupportActionBar(mVideoToolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        fontStyle = Typeface.createFromAsset(assets, "fonts/ZuoAnLianRen.ttf")
        mCtvThemeSelection.typeface = fontStyle
        mBtnOk.typeface = fontStyle
        mBtnCancel.typeface = fontStyle

        mRvColor.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(this, 5)//这里用线性宫格显示 类似于瀑布流
        mRvColor.setLayoutManager(gridLayoutManager)

        colorAdapter = ColorAdapter(this,this)
        colorAdapter?.setHeaderVisible(false)
        colorAdapter?.setFooterVisible(false)

        var animationAdapter = ScaleInAnimatorAdapter(colorAdapter!!,mRvColor)
        mRvColor.adapter = animationAdapter

        mBtnCancel.setOnClickListener(this)
        mBtnOk.setOnClickListener(this)
        mToggleDayNightsetting.setOnCheckedChangeListener(this)

    }

    fun initData (){
        colorAdapter?.add(resources.getIntArray(R.array.color_list).toList())
        colorAdapter?.notifyDataSetChanged()

        lastTheme = getCurrentTheme()

        var showDayNight = ConfigLocal.needShowDayNightThemeGuide(this,"")
        mToggleDayNightsetting.isChecked = showDayNight
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
        mToggleDayNightsetting.isChecked = false;
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id) {
            R.id.mToggleDayNightsetting -> {
                ConfigLocal.updateDayNightThemeGuide(this, "", isChecked)
                if (isChecked) {
                    ThemeUtils.setTheme(this,resources.getColor(R.color.colorNightPrimary))
                } else {
                    ThemeUtils.setTheme(this,lastTheme)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mBtnCancel -> {
                ThemeUtils.setTheme(this,lastTheme)
                finish()
            }
            R.id.mBtnOk -> {
                ConfigLocal.updateDayNightThemeGuide(this, "", false)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ThemeUtils.setTheme(this,lastTheme)
    }

    override fun onDestroy() {
        super.onDestroy()
        MainHandler.get().register(this)
    }

}
