package com.victor.clips.ui

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.victor.clips.R
import com.victor.clips.util.Constant
import com.victor.player.library.module.Player
import kotlinx.android.synthetic.main.activity_play.*
import org.victor.khttp.library.util.MainHandler

class PlayActivity : BaseActivity(),MainHandler.OnMainHandlerImpl {

    override fun handleMainMessage(message: Message?) {
        when (message?.what) {
            Player.PLAYER_PREPARING -> {
            }
            Player.PLAYER_PREPARED -> {
            }
            Player.PLAYER_ERROR -> {
            }
            Player.PLAYER_BUFFERING_START -> {
            }
            Player.PLAYER_BUFFERING_END -> {
            }
            Player.PLAYER_PROGRESS_INFO -> {
            }
            Player.PLAYER_COMPLETE -> {
            }
        }
    }

    var mPlayer: Player? = null

    companion object {
        fun  intentStart (activity: AppCompatActivity,playUrl: String?) {
            var intent = Intent(activity, PlayActivity::class.java)
            intent.putExtra(Constant.INTENT_DATA_KEY,playUrl)
            activity.startActivity(intent)
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_play
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData(intent)
    }

    fun initialize () {
        MainHandler.get().register(this)
        mPlayer = Player(mSvPlay, MainHandler.get())
    }

    fun initData (intent: Intent?) {
        mPlayer?.playUrl(intent?.getStringExtra(Constant.INTENT_DATA_KEY),false);
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initData(intent)
    }

    override fun onResume() {
        super.onResume()
        mPlayer?.resume()
    }

    override fun onPause() {
        super.onPause()
        mPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        MainHandler.get().unregister(this)
        mPlayer?.stop()
        mPlayer = null
    }

}
