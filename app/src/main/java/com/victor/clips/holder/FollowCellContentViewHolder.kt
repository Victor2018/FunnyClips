package com.victor.clips.holder

import android.view.View
import android.widget.AdapterView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FollowCellContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class FollowCellContentViewHolder(itemView: View,var parentPosition: Int) : ContentViewHolder(itemView) {

    override fun onClick(view: View) {
        mOnItemClickListener!!.onItemClick(null, view, getAdapterPosition(), parentPosition.toLong())
    }
}