package com.victor.clips.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.victor.clips.R
import com.victor.clips.util.HeaderDataSet
import com.victor.clips.widget.HeaderLayout

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: HeaderItem.java
 * Author: Victor
 * Date: 2018/9/12 10:25
 * Description: 
 * -----------------------------------------------------------------
 */
class HeaderItem(view: View) : HeaderLayout.ViewHolder(view) {
    private val gradient = view.findViewById<View>(R.id.gradient)
    private val background = view.findViewById<ImageView>(R.id.image)

    internal val backgroundLayout = view.findViewById<View>(R.id.backgroud_layout)

    internal var overlayTitle: TextView? = null
    internal var overlayLine: View? = null

    fun setContent(content: HeaderDataSet.ItemData, title: TextView?, line: View?) {
        gradient.setBackgroundResource(content.gradient)
        Glide.with(background).load(content.background).into(background)

        overlayTitle = title?.also {
            it.tag = position
            it.text = content.title
            it.visibility = View.VISIBLE
        }

        overlayLine = line
        overlayLine?.also {
            it.tag = position
            it.visibility = View.VISIBLE
        }
    }

    fun clearContent() {
        overlayTitle?.also {
            it.visibility = View.GONE
            it.tag = null
        }

        overlayLine?.also {
            it.tag = null
            it.visibility = View.GONE
        }

        overlayTitle = null
        overlayLine = null
    }
}