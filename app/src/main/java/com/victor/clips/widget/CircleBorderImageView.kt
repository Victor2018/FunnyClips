package com.victor.clips.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.ImageView
import com.victor.clips.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CircleBorderImageView.java
 * Author: Victor
 * Date: 2019/10/29 16:29
 * Description:
 * -----------------------------------------------------------------
 */
class CircleBorderImageView: ImageView {
    val COLORS = intArrayOf(-0xbb449a, -0xaa3323, -0x4488cd, -0x99ab, -0x44bc, -0xbb5501)

    var COLORS_NUMBER = COLORS.size
    var DEFAULT_TEXT_COLOR = -0x1
    var DEFAULT_BOARDER_COLOR = -0x1
    var DEFAULT_BOARDER_WIDTH = 4
    var DEFAULT_TYPE_BITMAP = 0
    var DEFAULT_TYPE_TEXT = 1
    var DEFAULT_TEXT = ""
    var COLORDRAWABLE_DIMENSION = 1
    var DEFAULT_TEXT_SIZE_RATIO = 0.4f
    var DEFAULT_TEXT_MASK_RATIO = 0.8f
    var DEFAULT_BOARDER_SHOW = false
    var BITMAP_CONFIG_8888 = Bitmap.Config.ARGB_8888
    var BITMAP_CONFIG_4444 = Bitmap.Config.ARGB_4444

    var mRadius: Int = 0//the circle's radius
    var mCenterX: Int = 0
    var mCenterY: Int = 0
    var mType = DEFAULT_TYPE_BITMAP
    var mBgColor = COLORS[0]//background color when show text
    var mTextColor = DEFAULT_TEXT_COLOR
    var mBoarderColor = DEFAULT_BOARDER_COLOR
    var mBoarderWidth = DEFAULT_BOARDER_WIDTH
    var mTextSizeRatio = DEFAULT_TEXT_SIZE_RATIO//the text size divides (2 * mRadius)
    var mTextMaskRatio = DEFAULT_TEXT_MASK_RATIO//the inner-radius text divides outer-radius text
    var mShowBoarder = DEFAULT_BOARDER_SHOW
    var mText: String? = DEFAULT_TEXT

    var mPaintTextForeground: Paint? = null//draw text, in text mode
    var mPaintTextBackground: Paint? = null//draw circle, in text mode
    var mPaintDraw: Paint? = null//draw bitmap, int bitmap mode
    var mPaintCircle: Paint? = null//draw boarder
    var mFontMetrics: Paint.FontMetrics? = null

    var mBitmap: Bitmap? = null//the pic
    var mBitmapShader: BitmapShader? = null//used to adjust position of bitmap
    var mMatrix: Matrix? = null//used to adjust position of bitmap

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(context,attrs)
    }

    init {
        mMatrix = Matrix()

        mPaintTextForeground = Paint()
        mPaintTextForeground?.setColor(mTextColor)
        mPaintTextForeground?.setAntiAlias(true)
        mPaintTextForeground?.setTextAlign(Paint.Align.CENTER)

        mPaintTextBackground = Paint()
        mPaintTextBackground?.setAntiAlias(true)
        mPaintTextBackground?.setStyle(Paint.Style.FILL)

        mPaintDraw = Paint()
        mPaintDraw?.setAntiAlias(true)
        mPaintDraw?.setStyle(Paint.Style.FILL)

        mPaintCircle = Paint()
        mPaintCircle?.setAntiAlias(true)
        mPaintCircle?.setStyle(Paint.Style.STROKE)
        mPaintCircle?.setColor(mBoarderColor)
        mPaintCircle?.setStrokeWidth(mBoarderWidth.toFloat())
    }

    fun initAttr(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleBorderImageView) ?: return
        val n = a.indexCount
        for (i in 0 until n) {
            val attr = a.getIndex(i)
            if (attr == R.styleable.CircleBorderImageView_aiv_TextSizeRatio) {
                mTextSizeRatio = a.getFloat(attr, DEFAULT_TEXT_SIZE_RATIO)
            } else if (attr == R.styleable.CircleBorderImageView_aiv_TextMaskRatio) {
                mTextMaskRatio = a.getFloat(attr, DEFAULT_TEXT_MASK_RATIO)
            } else if (attr == R.styleable.CircleBorderImageView_aiv_BoarderWidth) {
                mBoarderWidth = a.getDimensionPixelSize(attr, DEFAULT_BOARDER_WIDTH)
            } else if (attr == R.styleable.CircleBorderImageView_aiv_BoarderColor) {
                mBoarderColor = a.getColor(attr, DEFAULT_BOARDER_COLOR)
            } else if (attr == R.styleable.CircleBorderImageView_aiv_TextColor) {
                mTextColor = a.getColor(attr, DEFAULT_TEXT_COLOR)
            } else if (attr == R.styleable.CircleBorderImageView_aiv_ShowBoarder) {
                mShowBoarder = a.getBoolean(attr, DEFAULT_BOARDER_SHOW)
            }
        }
        a.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val contentWidth = w - paddingLeft - paddingRight
        val contentHeight = h - paddingTop - paddingBottom

        mRadius = if (contentWidth < contentHeight) contentWidth / 2 else contentHeight / 2
        mCenterX = paddingLeft + mRadius
        mCenterY = paddingTop + mRadius
        refreshTextSizeConfig()
    }

    fun refreshTextSizeConfig() {
        mPaintTextForeground?.setTextSize(mTextSizeRatio * 2f * mRadius.toFloat())
        mFontMetrics = mPaintTextForeground?.getFontMetrics()
    }

    fun refreshTextConfig() {
        if (mBgColor != mPaintTextBackground?.getColor()) {
            mPaintTextBackground?.setColor(mBgColor)
        }
        if (mTextColor != mPaintTextForeground?.getColor()) {
            mPaintTextForeground?.setColor(mTextColor)
        }
    }

    fun setTextAndColor(text: String, bgColor: Int) {
        if (this.mType != DEFAULT_TYPE_TEXT || !stringEqual(text, this.mText) || bgColor != this.mBgColor) {
            this.mText = text
            this.mBgColor = bgColor
            this.mType = DEFAULT_TYPE_TEXT
            invalidate()
        }
    }

    fun setTextColor(textColor: Int) {
        if (this.mTextColor != textColor) {
            mTextColor = textColor
            mPaintTextForeground?.setColor(mTextColor)
            invalidate()
        }
    }

    fun setTextAndColorSeed(text: String, colorSeed: String) {
        setTextAndColor(text, getColorBySeed(colorSeed))
    }

    fun setBitmap(bitmap: Bitmap?) {
        if (bitmap == null) {
            return
        }
        if (this.mType != DEFAULT_TYPE_BITMAP || bitmap != this.mBitmap) {
            this.mBitmap = bitmap
            this.mType = DEFAULT_TYPE_BITMAP
            invalidate()
        }
    }

    fun setDrawable(drawable: Drawable?) {
        val bitmap = getBitmapFromDrawable(drawable)
        setBitmap(bitmap)
    }

    fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        try {
            val bitmap: Bitmap
            if (drawable is ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG_8888)
            } else {
                bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, BITMAP_CONFIG_8888)
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    override fun onDraw(canvas: Canvas) {
        if (mBitmap != null && mType == DEFAULT_TYPE_BITMAP) {
            toDrawBitmap(canvas)
        } else if (mText != null && mType == DEFAULT_TYPE_TEXT) {
            toDrawText(canvas)
        }
        if (mShowBoarder) {
            drawBoarder(canvas)
        }
    }

    private fun toDrawText(canvas: Canvas) {
        if (mText?.length == 1) {
            //draw text to the view's canvas directly
            drawText(canvas)
        } else {
            //draw text with clip effect, need to create a bitmap
            drawBitmap(canvas, createClipTextBitmap((mRadius / mTextMaskRatio).toInt()), false)
        }
    }

    private fun toDrawBitmap(canvas: Canvas) {
        if (mBitmap == null) return
        drawBitmap(canvas, mBitmap!!, true)
    }

    private fun drawBitmap(canvas: Canvas, bitmap: Bitmap, adjustScale: Boolean) {
        refreshBitmapShaderConfig(bitmap, adjustScale)
        mPaintDraw?.setShader(mBitmapShader)
        canvas.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), mRadius.toFloat(), mPaintDraw)
    }

    private fun refreshBitmapShaderConfig(bitmap: Bitmap, adjustScale: Boolean) {
        mBitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mMatrix!!.reset()
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height
        if (adjustScale) {
            val bSize = Math.min(bitmapWidth, bitmapHeight)
            val scale = mRadius * 2.0f / bSize//TODO
            mMatrix?.setScale(scale, scale)
            if (bitmapWidth > bitmapHeight) {
                mMatrix?.postTranslate(-(bitmapWidth * scale / 2 - mRadius.toFloat() - paddingLeft.toFloat()), paddingTop.toFloat())
            } else {
                mMatrix?.postTranslate(paddingLeft.toFloat(), -(bitmapHeight * scale / 2 - mRadius.toFloat() - paddingTop.toFloat()))
            }
        } else {
            mMatrix?.postTranslate((-(bitmapWidth * 1 / 2 - mRadius - paddingLeft)).toFloat(), (-(bitmapHeight * 1 / 2 - mRadius - paddingTop)).toFloat())
        }

        mBitmapShader?.setLocalMatrix(mMatrix)
    }

    private fun createClipTextBitmap(bitmapRadius: Int): Bitmap {
        val bitmapClipText = Bitmap.createBitmap(bitmapRadius * 2, bitmapRadius * 2, BITMAP_CONFIG_4444)
        val canvasClipText = Canvas(bitmapClipText)
        val paintClipText = Paint()
        paintClipText.style = Paint.Style.FILL
        paintClipText.isAntiAlias = true
        paintClipText.color = mBgColor
        canvasClipText.drawCircle(bitmapRadius.toFloat(), bitmapRadius.toFloat(), bitmapRadius.toFloat(), paintClipText)

        paintClipText.textSize = mTextSizeRatio * mRadius.toFloat() * 2f
        paintClipText.color = mTextColor
        paintClipText.textAlign = Paint.Align.CENTER
        val fontMetrics = paintClipText.fontMetrics
        canvasClipText.drawText(mText, 0, mText!!.length, bitmapRadius.toFloat(),
                bitmapRadius + Math.abs(fontMetrics.top + fontMetrics.bottom) / 2, paintClipText)
        return bitmapClipText
    }

    private fun drawText(canvas: Canvas) {
        refreshTextConfig()
        canvas.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), mRadius.toFloat(), mPaintTextBackground)
        canvas.drawText(mText, 0, mText!!.length, mCenterX.toFloat(),
                mCenterY + Math.abs(mFontMetrics!!.top + mFontMetrics!!.bottom) / 2, mPaintTextForeground)
    }

    fun drawBoarder(canvas: Canvas) {
        canvas.drawCircle(mCenterX.toFloat(), mCenterY.toFloat(), (mRadius - mBoarderWidth / 2).toFloat(), mPaintCircle)
    }

    fun getColorBySeed(seed: String): Int {
        return if (TextUtils.isEmpty(seed)) {
            COLORS[0]
        } else COLORS[Math.abs(seed.hashCode() % COLORS_NUMBER)]
    }

    override fun setImageDrawable(drawable: Drawable?) {
        setDrawable(drawable)
    }

    override fun setImageResource(resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getDrawable(resId)
        } else {
            drawable = context.resources.getDrawable(resId)
        }
    }
    fun stringEqual(a: String?, b: String?): Boolean {
        return if (a == null) {
            b == null
        } else {
            if (b == null) {
                false
            } else a == b
        }
    }
}