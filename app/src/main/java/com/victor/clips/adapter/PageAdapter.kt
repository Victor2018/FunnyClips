package com.victor.clips.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.victor.clips.R
import com.victor.clips.holder.ItemImage
import com.victor.clips.holder.ItemUser
import com.victor.clips.holder.PageItem
import com.victor.clips.util.PageDataSet

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PageAdapter.java
 * Author: Victor
 * Date: 2018/9/12 10:27
 * Description: 
 * -----------------------------------------------------------------
 */
class PageAdapter(private val count: Int,
                  private val dataSet: PageDataSet) : RecyclerView.Adapter<PageItem>() {
    private enum class ItemType(val value: Int) {
        USER(1),
        IMAGE(2);

        companion object {
            private val map = ItemType.values().associateBy(ItemType::value)
            fun fromInt(type: Int, defaultValue: ItemType = USER) = map.getOrElse(type) {defaultValue}
        }
    }

    override fun getItemCount() = count

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageItem {
        return when (ItemType.fromInt(viewType)) {
            ItemType.USER -> createItemUser(parent)
            ItemType.IMAGE -> createItemImage(parent)
        }
    }

    override fun onBindViewHolder(holder: PageItem, position: Int) {
        when (holder) {
            is ItemUser -> { holder.setContent(dataSet.getItemData(position)) }
            is ItemImage -> { holder.setImage(dataSet.secondItemImage) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return (if (position == 1) ItemType.IMAGE else ItemType.USER).value
    }

    override fun onViewRecycled(holder: PageItem) {
        super.onViewRecycled(holder)
        holder.clearContent()
    }

    private fun createItemUser(parent: ViewGroup): ItemUser {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return ItemUser(view)
    }

    private fun createItemImage(parent: ViewGroup): ItemImage {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_image, parent, false)
        return ItemImage(view)
    }
}