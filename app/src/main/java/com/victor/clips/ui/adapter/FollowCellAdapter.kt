package com.victor.clips.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.data.HomeItemInfo
import com.victor.clips.ui.holder.FollowCellContentViewHolder
import com.victor.clips.util.ImageUtils
import kotlinx.android.synthetic.main.rv_follow_item_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FollowCellAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:57
 * Description: 
 * -----------------------------------------------------------------
 */
class FollowCellAdapter(context: Context, listener: AdapterView.OnItemClickListener,var parentPosition: Int) :
        BaseRecycleAdapter<HomeItemInfo, RecyclerView.ViewHolder>(context, listener) {

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
        return FollowCellContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_follow_item_cell ,parent, false),parentPosition)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo, position: Int) {
        val contentViewHolder = viewHolder as FollowCellContentViewHolder

        contentViewHolder.itemView.mTvFollowCellTitle.typeface = fontStyle

        ImageUtils.instance.loadImage(mContext!!,contentViewHolder.itemView.mIvFollowCellPoster, data.data?.cover?.feed)
        contentViewHolder.itemView.mTvFollowCellTitle.setText(data.data?.title)
        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }
}