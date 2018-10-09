package com.victor.clips.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.app.App
import com.victor.clips.data.CategoryInfo
import com.victor.clips.holder.LiveContentViewHolder
import com.victor.clips.util.Loger
import com.victor.clips.widget.GravitySnapHelper
import kotlinx.android.synthetic.main.adapter_media_type_item.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: LiveCellAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:57
 * Description: 
 * -----------------------------------------------------------------
 */
class LiveAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
        BaseRecycleAdapter<CategoryInfo, RecyclerView.ViewHolder>(context, listener),
        GravitySnapHelper.SnapListener{

    override fun onSnap(position: Int) {
        Loger.d("onSnap()-position = ", position.toString())
    }

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: CategoryInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LiveContentViewHolder(mLayoutInflater!!.inflate(R.layout.adapter_media_type_item, parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: CategoryInfo, position: Int) {
        val contentViewHolder = viewHolder as LiveContentViewHolder
        contentViewHolder.itemView.title.setText(data.channel_category)
        contentViewHolder.itemView.recyclerView.layoutManager = LinearLayoutManager(
                contentViewHolder.itemView.context,
                LinearLayoutManager.HORIZONTAL,false)
        contentViewHolder.itemView.recyclerView.setOnFlingListener(null)
        LinearSnapHelper().attachToRecyclerView(contentViewHolder.itemView.recyclerView)

        var cellAdapter = LiveCellAdapter(App.instance(),mOnItemClickListener!!)
        cellAdapter.add(data.channels)

        contentViewHolder.itemView.recyclerView.adapter = cellAdapter
    }
}