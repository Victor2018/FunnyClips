package com.victor.clips.adapter

import android.content.Context
import android.graphics.Typeface
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.data.CategoryReq
import com.victor.clips.holder.ContentViewHolder
import com.victor.clips.util.ImageUtils
import kotlinx.android.synthetic.main.rv_category_cell.view.mTvTitle
import kotlinx.android.synthetic.main.rv_category_cell.view.mIvPoster
import kotlinx.android.synthetic.main.rv_color_cell.view.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ColorAdapter.java
 * Author: Victor
 * Date: 2018/9/28 14:52
 * Description: 
 * -----------------------------------------------------------------
 */
class ColorAdapter(context: Context, listener: AdapterView.OnItemClickListener): BaseRecycleAdapter<Int, RecyclerView.ViewHolder>(context,listener) {
    var currentPosition = -1
    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: Int, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_color_cell, parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: Int, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder

        contentViewHolder.itemView.mCvColor.setCardBackgroundColor(data)
        if (currentPosition == position) {
            contentViewHolder.itemView.mViewFocus.visibility = View.VISIBLE
        } else {
            contentViewHolder.itemView.mViewFocus.visibility = View.GONE
        }
        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }

}