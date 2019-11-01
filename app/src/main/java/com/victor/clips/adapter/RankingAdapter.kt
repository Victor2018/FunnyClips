package com.victor.clips.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.holder.ContentViewHolder
import com.victor.clips.util.ImageUtils
import kotlinx.android.synthetic.main.rv_ranking_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: RankingAdapter.java
 * Author: Victor
 * Date: 2018/9/28 14:52
 * Description: 
 * -----------------------------------------------------------------
 */
class RankingAdapter(context: Context, listener: AdapterView.OnItemClickListener): BaseRecycleAdapter<HomeItemInfo, RecyclerView.ViewHolder>(context,listener) {
    var fontStyle: Typeface? = null

    init {
        fontStyle = Typeface.createFromAsset(mContext?.getAssets(), "fonts/ZuoAnLianRen.ttf");
    }

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_ranking_cell, parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder
        contentViewHolder.itemView.mTvRankingTitle.setTypeface(fontStyle);
        contentViewHolder.itemView.mTvRankingTitle.setText(data.data!!.title)
        ImageUtils.instance.loadImage(mContext!!,contentViewHolder.itemView.mIvRankingPoster, data.data!!.cover!!.feed)
        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }

}