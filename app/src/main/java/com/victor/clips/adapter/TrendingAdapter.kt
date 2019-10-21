package com.victor.clips.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.holder.LiveCellContentViewHolder
import com.victor.clips.holder.TrendingContentViewHolder
import com.victor.clips.util.ImageUtils
import kotlinx.android.synthetic.main.rv_live_cell_item.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LiveCellAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:57
 * Description: 
 * -----------------------------------------------------------------
 */
class TrendingAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
        BaseRecycleAdapter<HomeItemInfo, RecyclerView.ViewHolder>(context, listener) {

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrendingContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_trending_cell_item,parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo, position: Int) {
        val contentViewHolder = viewHolder as TrendingContentViewHolder
        ImageUtils.instance.loadImage(mContext!!,contentViewHolder.itemView.mIvPoster, data.data!!.cover!!.feed)
        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }
}