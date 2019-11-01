package com.victor.clips.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.victor.clips.R
import com.victor.clips.holder.BottomViewHolder
import com.victor.clips.holder.HeaderViewHolder
import java.util.ArrayList

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseRecycleAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:20
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class BaseRecycleAdapter<T,VH:RecyclerView.ViewHolder>(context: Context,listener:AdapterView.OnItemClickListener?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var TAG = "BaseRecycleAdapter"
    val LOADING = 0x0001//正在加载
    val LOADING_COMPLETE = 0x0002//加载完毕
    val LOADING_END = 0x0003
    var mLayoutInflater: LayoutInflater? = null
    var mOnItemClickListener: AdapterView.OnItemClickListener? = null
    var mContext: Context? = null
    private var datas: MutableList<T>? = ArrayList()
    var mHeaderCount = 0//头部View个数
    var mBottomCount = 0//底部View个数
    var ITEM_TYPE_HEADER = 0
    var ITEM_TYPE_CONTENT = 1
    var ITEM_TYPE_BOTTOM = 2
    private var loadState = LOADING_COMPLETE//上拉加载状态

    private var isHeaderVisible = false
    private var isFooterVisible = false

    abstract fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder?
    abstract fun onBindHeadVHolder(viewHolder: VH, data: T, position: Int)

    abstract fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): VH
    abstract fun onBindContentVHolder(viewHolder: VH, data: T, position: Int)

    init {
        mContext = context
        mOnItemClickListener = listener
        mLayoutInflater = LayoutInflater.from(context)
    }

    fun setHeaderVisible(visible: Boolean) {
        isHeaderVisible = visible
        mHeaderCount = 1
        if (!isHeaderVisible) {
            mHeaderCount = 0
        }
        notifyDataSetChanged()
    }

    fun setFooterVisible(visible: Boolean) {
        isFooterVisible = visible
        mBottomCount = 1
        if (!isFooterVisible) {
            mBottomCount = 0
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_HEADER) {
            return onCreateHeadVHolder(parent, viewType)!!
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return onCreateContentVHolder(parent, viewType)
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return BottomViewHolder(mLayoutInflater!!.inflate(R.layout.recyclerview_foot, parent, false))
        }
        return onCreateContentVHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is HeaderViewHolder) {
            onBindHeadVHolder(holder as VH, item!!, position)
        } else if (holder is BottomViewHolder) {
            setFooterViewState(holder)
        } else {
            onBindContentVHolder(holder as VH, item!!, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        var ITEM_TYPE = ITEM_TYPE_CONTENT
        val dataItemCount = getContentItemCount()
        if (mHeaderCount != 0 && position < mHeaderCount) {//头部View
            ITEM_TYPE = ITEM_TYPE_HEADER
        } else if (mBottomCount != 0 && position >= mHeaderCount + dataItemCount) {//底部View
            ITEM_TYPE = ITEM_TYPE_BOTTOM
        }
        return ITEM_TYPE
    }

    fun isHeaderView(position: Int): Boolean {
        return mHeaderCount != 0 && position < mHeaderCount
    }

    fun isBottomView(position: Int): Boolean {
        return mBottomCount != 0 && position >= mHeaderCount + getContentItemCount()
    }

    fun getContentItemCount(): Int {
        return if (datas == null) 0 else datas!!.size
    }

    override fun getItemCount(): Int {
        return mHeaderCount + getContentItemCount() + mBottomCount
    }

    private fun setFooterViewState(bottomViewHolder: BottomViewHolder) {
        when (loadState) {
            LOADING -> {
                bottomViewHolder.progressBar!!.setVisibility(View.VISIBLE)
                bottomViewHolder.mTvTitle!!.setVisibility(View.VISIBLE)
                bottomViewHolder.mLayoutEnd!!.setVisibility(View.GONE)
            }
            LOADING_COMPLETE -> {
                bottomViewHolder.progressBar!!.setVisibility(View.GONE)
                bottomViewHolder.mTvTitle!!.setVisibility(View.GONE)
                bottomViewHolder.mLayoutEnd!!.setVisibility(View.GONE)
            }
            LOADING_END -> {
                bottomViewHolder.progressBar!!.setVisibility(View.GONE)
                bottomViewHolder.mTvTitle!!.setVisibility(View.GONE)
                bottomViewHolder.mLayoutEnd!!.setVisibility(View.VISIBLE)
            }
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    fun setLoadState(loadState: Int) {
        this.loadState = loadState
        notifyDataSetChanged()
    }

    /**
     * 获取元素
     *
     * @param position
     * @return
     */
    fun getItem(position: Int): T? {
        //防止越界
        val index = if (position >= 0 && position < datas!!.size) position else 0
        return if (datas == null || datas!!.size == 0) {
            null
        } else datas!!.get(index)
    }

    /**
     * 添加元素
     *
     * @param item
     */
    fun add(item: T?) {
        if (item != null) {
            datas!!.add(item)
        }
    }

    /**
     * 添加元素
     *
     * @param item
     */
    fun add(index: Int, item: T?) {
        if (item != null) {
            datas!!.add(index, item)
        }
    }

    fun add(items: List<T>?) {
        if (items != null) {
            datas!!.addAll(items)
        }
    }

    /**
     * 重置元素
     *
     * @param items
     */
    fun setDatas(items: List<T>) {
        datas!!.clear()
        add(items)
    }

    /**
     * 移除
     *
     * @param index
     */
    fun removeItem(index: Int) {
        if (index >= 0 && index < datas!!.size) {
            datas!!.removeAt(index)
        }
    }

    fun getDatas(): List<T>? {
        return datas
    }

    fun clear() {
        datas!!.clear()
    }
}