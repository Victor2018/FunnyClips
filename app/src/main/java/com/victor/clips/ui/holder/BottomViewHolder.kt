package com.victor.clips.ui.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.victor.clips.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BottomViewHolder.java
 * Author: Victor
 * Date: 2018/8/30 17:31
 * Description: 
 * -----------------------------------------------------------------
 */
class BottomViewHolder : RecyclerView.ViewHolder {
    var progressBar: ProgressBar? = null
    var mTvTitle: TextView? = null
    var mLayoutEnd: LinearLayout? = null


    constructor(itemView: View) : super(itemView) {
        progressBar = itemView.findViewById(R.id.progressBar)
        mTvTitle = itemView.findViewById(R.id.tv_title)
        mLayoutEnd = itemView.findViewById(R.id.ll_end)
    }
}