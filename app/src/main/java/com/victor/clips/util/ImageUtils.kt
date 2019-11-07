package com.victor.clips.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import java.io.File

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ImageUtils.java
 * Author: Victor
 * Date: 2018/9/4 9:35
 * Description: 
 * -----------------------------------------------------------------
 */
class ImageUtils {
    val TAG = "ImageUtils"
    private val animDuration = 500

    private object Holder {
        val instance = ImageUtils()

    }
    private var options: RequestOptions? = getRequestOptions(ColorUtil.getDefaultRandomColor())

    companion object {
        val  instance: ImageUtils by lazy { ImageUtils.Holder.instance }
    }

    /*fun init() {
        getRequestOptions(ColorUtil.getDefaultRandomColor())
    }*/

    /**
     * 加载头像
     *
     * @param imageView
     * @param url
     */
    fun loadAvatar(context: Context,imageView: ImageView?, url: String?) {
        if (imageView == null)
            return
        if (TextUtils.isEmpty(url)) {
            val colorDrawable = ColorDrawable(ColorUtil.getDefaultRandomColor())
            imageView.setImageDrawable(colorDrawable)
        } else {
            Glide.with(context)
                    .load(url)
                    .apply(options!!.placeholder(ColorUtil.getDefaultRandomColor())
                    .error(ColorUtil.getDefaultRandomColor()))
                    .into(object : SimpleTarget<Drawable>() {
                        override fun onResourceReady(resource: Drawable, transition: com.bumptech.glide.request.transition.Transition<in Drawable>?) {
                            if (resource != null) {
                                val bd = resource as BitmapDrawable
                                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, bd.bitmap)
                                circularBitmapDrawable.isCircular = true
                                imageView.setImageDrawable(circularBitmapDrawable)
                            }
                        }
                    })
        }
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    fun loadImage(context: Context,imageView: ImageView?, url: String?): Target<*>? {
        return if (imageView == null) null else Glide.with(context)
                .load(url).apply(options!!.placeholder(ColorUtil.getDefaultRandomColor())
                        .error(ColorUtil.getDefaultRandomColor()))
                .transition(DrawableTransitionOptions().crossFade(animDuration))
                .into(imageView)
    }

    fun loadImage(context: Context,imageView: ImageView?, url: String, placeDrawable: Int, showPlaceDrawable: Boolean): Target<*>? {
        if (imageView == null)
            return null
        return if (showPlaceDrawable) {
            Glide.with(context)
                    .load(url).apply(options!!.placeholder(placeDrawable)
                            .error(placeDrawable))
                    .transition(DrawableTransitionOptions().crossFade(animDuration))
                    .into(imageView)
        } else Glide.with(context)
                .load(url).apply(options!!.error(placeDrawable))
                .transition(DrawableTransitionOptions().crossFade(animDuration))
                .into(imageView)
    }

    fun loadImage(context: Context,imageView: ImageView?, url: String, placeDrawable: Drawable): Target<*>? {
        return if (imageView == null) null else Glide.with(context)
                .load(url).apply(options!!.placeholder(placeDrawable)
                        .error(placeDrawable))
                .transition(DrawableTransitionOptions().crossFade(animDuration))
                .into(imageView)
    }

    fun loadImage(context: Context,imageView: ImageView?, bitmap: Bitmap): Target<*>? {
        return if (imageView == null) null else Glide.with(context)
                .load(bitmap).apply(options!!.placeholder(ColorUtil.getDefaultRandomColor())
                        .error(ColorUtil.getDefaultRandomColor()))
                .transition(DrawableTransitionOptions().crossFade(animDuration))
                .into(imageView)
    }

    fun loadImage(context: Context,imageView: ImageView?, url: String, placeDrawable: Int): Target<*>? {
        return if (imageView == null) null else Glide.with(context)
                .load(url).apply(options!!.placeholder(placeDrawable).fitCenter()
                        .error(placeDrawable))
                .transition(DrawableTransitionOptions().crossFade(animDuration))
                .into(imageView)
    }

    /**
     * 从文件加入
     *
     * @param imageView
     * @param file
     */
    fun loadImage(context: Context,imageView: ImageView?, file: File) {
        if (imageView == null)
            return
        Glide.with(context)
                .load(file)
                .apply(options!!.placeholder(ColorUtil.getDefaultRandomColor())
                .error(ColorUtil.getDefaultRandomColor()))
                .transition(DrawableTransitionOptions().crossFade(animDuration))
                .into(imageView)
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param drawableRes
     */
    fun loadImage(context: Context,imageView: ImageView?, drawableRes: Int) {
        if (imageView == null)
            return
        loadImage(context,imageView, drawableRes, ColorUtil.getDefaultRandomColor())
    }


    fun loadImage(context: Context,imageView: ImageView?, drawableRes: Int, placeDrawable: Int) {
        if (imageView == null)
            return
        Glide.with(context).load(drawableRes).apply(options!!.placeholder(placeDrawable)
                .error(placeDrawable)).transition(DrawableTransitionOptions()
                .crossFade(animDuration)).into(imageView)
    }

    /**
     * 加载高斯模糊背景图片
     *
     * @param imageView
     * @param url
     */
    fun imageGauss(context: Context,imageView: ImageView?, url: String?, radius: Int) {
        if (imageView == null) {
            Loger.e(TAG, "imageView == null")
            return
        }
        Glide.with(context)
                .load(url)
                .apply(options!!.placeholder(ColorUtil.getDefaultRandomColor())
                        .error(ColorUtil.getDefaultRandomColor()))
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: com.bumptech.glide.request.transition.Transition<in Drawable>?) {
                        imageGauss(imageView, drawableToBitmap(resource!!), radius)
                    }
                })
    }
    fun imageGauss(context: Context,imageView: ImageView?, resId: Int, radius: Int) {
        if (imageView == null) {
            Loger.e(TAG, "imageView == null")
            return
        }
        Glide.with(context)
                .load(resId)
                .apply(options!!.placeholder(ColorUtil.getDefaultRandomColor())
                        .error(ColorUtil.getDefaultRandomColor()))
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: com.bumptech.glide.request.transition.Transition<in Drawable>?) {
                        imageGauss(imageView, drawableToBitmap(resource!!), radius)
                    }
                })

    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {

        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        val config = if (drawable.opacity != PixelFormat.OPAQUE)
            Bitmap.Config.ARGB_8888
        else
            Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(w, h, config)
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        drawable.draw(canvas)

        return bitmap
    }

    fun imageGauss(context: Context,imageView: ImageView?, url: String?) {
        if (imageView == null) {
            Loger.e(TAG, "imageView == null")
            return
        }
        imageGauss(context,imageView, url, 10)
    }
    fun imageGauss(context: Context,imageView: ImageView?, resId: Int) {
        if (imageView == null) {
            Loger.e(TAG, "imageView == null")
            return
        }
        imageGauss(context,imageView, resId, 10)
    }

    fun imageGauss(imageView: ImageView, bitmap: Bitmap) {
        imageGauss(imageView, bitmap, 10)
    }

    fun imageGauss(imageView: ImageView?, bitmap: Bitmap?, radius: Int) {
        imageView!!.setImageBitmap(ImgBlurUtil.blurImg(bitmap, radius))
        //        loadImage(imageView,ImgBlurUtil.getInstanse().blurImg(bitmap,radius));

    }

    fun getRequestOptions(resId: Int): RequestOptions? {
        if (options == null) {
            options = RequestOptions()
                    .centerCrop()
                    .placeholder(resId)
                    .error(resId)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
        }
        return options
    }
}