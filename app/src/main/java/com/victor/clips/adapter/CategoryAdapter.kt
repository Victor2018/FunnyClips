package com.victor.clips.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.data.CategoryInfo
import com.victor.clips.data.CategoryReq
import com.victor.clips.holder.ContentViewHolder
import com.victor.clips.util.ImageUtils
import kotlinx.android.synthetic.main.rv_category_cell.view.*
import kotlinx.android.synthetic.main.rv_video_category_cell.view.mTvTitle
import kotlinx.android.synthetic.main.rv_video_category_cell.view.mIvPoster

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CategoryAdapter.java
 * Author: Victor
 * Date: 2019/10/31 10:47
 * Description:
 * -----------------------------------------------------------------
 */
class CategoryAdapter(context: Context, listener: AdapterView.OnItemClickListener): BaseRecycleAdapter<CategoryInfo, RecyclerView.ViewHolder>(context,listener) {
    var fontStyle: Typeface? = null
    var currentPosition: Int = -1

    init {
        fontStyle = Typeface.createFromAsset(mContext?.getAssets(), "fonts/ZuoAnLianRen.ttf");
    }
    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: CategoryInfo, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(mLayoutInflater!!.inflate(R.layout.rv_category_cell, parent, false))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: CategoryInfo, position: Int) {
        val contentViewHolder = viewHolder as ContentViewHolder

        contentViewHolder.itemView.mTvCategoryTitle.setTypeface(fontStyle);

        contentViewHolder.itemView.mTvCategoryTitle.setText(data.categoryName)
        contentViewHolder.itemView.mCivCategoryAvatar.setImageResource(data.categoryImgRes)

        if (currentPosition == position) {
            contentViewHolder.itemView.mIvCategoryChecked.visibility = View.VISIBLE
        } else {
            contentViewHolder.itemView.mIvCategoryChecked.visibility = View.GONE
        }

        contentViewHolder.setOnItemClickListener(mOnItemClickListener)
    }

}
 