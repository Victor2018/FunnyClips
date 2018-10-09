package com.victor.clips.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.data.YoutubeInfo
import com.victor.clips.holder.LiveContentViewHolder
import com.victor.clips.holder.YoutubeContentViewHolder
import com.victor.clips.util.ImageUtils
import kotlinx.android.synthetic.main.rv_youtube_item.view.*
import kotlinx.android.synthetic.main.rv_live_cell_item.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: YoutubeAdapter.java
 * Author: Victor
 * Date: 2018/9/28 14:52
 * Description: 
 * -----------------------------------------------------------------
 */
class YoutubeAdapter(context: Context, listener: AdapterView.OnItemClickListener): BaseRecycleAdapter<YoutubeInfo, RecyclerView.ViewHolder>(context,listener) {
    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: YoutubeInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return YoutubeContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_youtube_item, parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: YoutubeInfo, position: Int) {
        val contentViewHolder = viewHolder as YoutubeContentViewHolder

        ImageUtils.instance.loadImage(contentViewHolder.itemView.mIvYoutubePoster, data.poster)
        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }

}