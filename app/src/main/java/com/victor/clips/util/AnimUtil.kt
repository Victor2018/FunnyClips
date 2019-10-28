package com.victor.clips.util

import com.victor.clips.app.App
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import com.victor.clips.R


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AnimUtil.java
 * Author: Victor
 * Date: 2019/10/25 15:11
 * Description:
 * -----------------------------------------------------------------
 */
class AnimUtil {
    companion object {
        fun topEnter(): Animation {
            val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_top_enter)
            animation.fillAfter = true
            return animation
        }
        fun bottomExit(): Animation {
            val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_bottom_exit)
            animation.fillAfter = true
            return animation
        }
        fun bottomEnter(): Animation {
            val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_bottom_enter)
            animation.fillAfter = true
            return animation
        }
        fun topExit(): Animation {
            val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_top_exit)
            animation.fillAfter = true
            return animation
        }
    }
}