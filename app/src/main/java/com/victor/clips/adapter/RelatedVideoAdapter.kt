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
import kotlinx.android.synthetic.main.rv_related_video_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: RelatedVideoAdapter.java
 * Author: Victor
 * Date: 2018/9/28 14:52
 * Description: 
 * -----------------------------------------------------------------
 */
class RelatedVideoAdapter(context: Context, listener: AdapterView.OnItemClickListener): BaseRecycleAdapter<HomeItemInfo, RecyclerView.ViewHolder>(context,listener) {

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
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_related_video_cell, parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder

        contentViewHolder.itemView.mTvRelatedVideoName.setText(data.data?.title)
        contentViewHolder.itemView.mTvRelatedVideoName.setTypeface(fontStyle);
        contentViewHolder.itemView.mTvRelatedVideoVideoLikes.setTypeface(fontStyle);
        contentViewHolder.itemView.mTvRelatedVideoVideoShare.setTypeface(fontStyle);
        contentViewHolder.itemView.mTvRelatedVideoVideoComment.setTypeface(fontStyle);
        ImageUtils.instance.loadImage(mContext!!,contentViewHolder.itemView.mIvRelatedVideoPoster, data.data?.cover!!.feed)
        ImageUtils.instance.loadAvatar(mContext!!,contentViewHolder.itemView.mIvRelatedVideoAvatar, data.data?.author!!.icon)
        contentViewHolder.itemView.mTvRelatedVideoVideoLikes.setText(data.data?.consumption?.collectionCount.toString())
        contentViewHolder.itemView.mTvRelatedVideoVideoShare.setText(data.data?.consumption?.shareCount.toString())
        contentViewHolder.itemView.mTvRelatedVideoVideoComment.setText(data.data?.consumption?.replyCount.toString())

        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }

}