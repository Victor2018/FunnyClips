package com.victor.clips.util

import com.victor.clips.app.App
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import com.victor.clips.R
import android.animation.Animator
import android.content.Context
import android.view.animation.Interpolator


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
        private var fastOutSlowIn: Interpolator? = null
        private var fastOutLinearIn: Interpolator? = null

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

        fun concatAnimators(animators: Array<Animator>, alphaAnimator: Animator): Array<Animator?> {
            val allAnimators = arrayOfNulls<Animator>(animators.size + 1)
            var i = 0

            for (animator in animators) {
                allAnimators[i] = animator
                ++i
            }
            allAnimators[allAnimators.size - 1] = alphaAnimator
            return allAnimators
        }

        fun getFastOutSlowInInterpolator(context: Context): Interpolator? {
            if (fastOutSlowIn == null) {
                fastOutSlowIn = AnimationUtils.loadInterpolator(context,
                        android.R.interpolator.fast_out_slow_in)
            }
            return fastOutSlowIn
        }

    }
}