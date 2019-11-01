package com.victor.clips.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.data.CategoryReq
import com.victor.clips.holder.ContentViewHolder
import com.victor.clips.util.ImageUtils
import kotlinx.android.synthetic.main.rv_video_category_cell.view.mTvTitle
import kotlinx.android.synthetic.main.rv_video_category_cell.view.mIvPoster


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VideoCategoryAdapter.java
 * Author: Victor
 * Date: 2018/9/28 14:52
 * Description: 
 * -----------------------------------------------------------------
 */
class VideoCategoryAdapter(context: Context, listener: AdapterView.OnItemClickListener): BaseRecycleAdapter<CategoryReq, RecyclerView.ViewHolder>(context,listener) {
    val BIG_ITEM = 3
    val NORMAL_ITEM = 4
    val HEADER_ITEM = 5
    var showHeader = false
    var fontStyle: Typeface? = null

    init {
        fontStyle = Typeface.createFromAsset(mContext?.getAssets(), "fonts/ZuoAnLianRen.ttf");
    }
    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: CategoryReq, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == NORMAL_ITEM) {
            return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_video_category_normal_cell, parent, false))
        }
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_video_category_cell, parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: CategoryReq, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder

        contentViewHolder.itemView.mTvTitle.setTypeface(fontStyle);

        contentViewHolder.itemView.mTvTitle.setText(data.name)
        ImageUtils.instance.loadImage(mContext!!,contentViewHolder.itemView.mIvPoster, data.bgPicture)
        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }

    fun getViewType(position: Int): Int {
        return if (showHeader) {
            if (position == 0) {
                HEADER_ITEM
            } else if (position % 5 == 3) {
                BIG_ITEM
            } else {
                NORMAL_ITEM
            }
        } else {
            if ((position + 2) % 5 == 4) {
                BIG_ITEM
            } else {
                NORMAL_ITEM
            }
        }
    }

}