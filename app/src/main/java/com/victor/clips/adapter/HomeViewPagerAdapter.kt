package com.victor.clips.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeViewPagerAdapter.java
 * Author: Victor
 * Date: 2018/8/30 16:08
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeViewPagerAdapter(manager: FragmentManager,var mContext: Context,var fragments: List<Fragment>?) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return fragments!!.get(position)
    }

    override fun getCount(): Int {
        return fragments!!.size
    }
}