package com.victor.clips.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.ui.holder.ContentViewHolder
import com.victor.clips.util.ImageUtils
import kotlinx.android.synthetic.main.rv_related_video_cell.view.*
import kotlinx.android.synthetic.main.rv_related_video_cell.view.mIvRelatedVideoAvatar
import kotlinx.android.synthetic.main.rv_related_video_cell.view.mTvRelatedVideoName
import kotlinx.android.synthetic.main.rv_related_video_cell.view.mTvRelatedVideoVideoComment
import kotlinx.android.synthetic.main.rv_related_video_cell.view.mTvRelatedVideoVideoLikes
import kotlinx.android.synthetic.main.rv_related_video_cell.view.mTvRelatedVideoVideoShare
import kotlinx.android.synthetic.main.rv_search_video_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchVideoAdapter.java
 * Author: Victor
 * Date: 2018/9/28 14:52
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchVideoAdapter(context: Context, listener: AdapterView.OnItemClickListener): BaseRecycleAdapter<HomeItemInfo, RecyclerView.ViewHolder>(context,listener) {

    var fontStyle: Typeface? = null

    init {
        fontStyle = Typeface.createFromAsset(mContext?.assets, "fonts/ZuoAnLianRen.ttf");
    }

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_search_video_cell, parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder

        contentViewHolder.itemView.mTvSearchTitle.typeface = fontStyle;

        contentViewHolder.itemView.mTvSearchTitle.setText(data.data?.title)
        ImageUtils.instance.loadImage(mContext!!,contentViewHolder.itemView.mIvSearchPoster, data.data?.cover?.feed)

        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }

}