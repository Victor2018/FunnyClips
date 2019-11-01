package com.victor.clips.util

import android.graphics.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BitmapRoundCornerUtil.java
 * Author: Victor
 * Date: 2019/10/30 14:43
 * Description:
 * -----------------------------------------------------------------
 */
class BitmapRoundCornerUtil {
    companion object {

        // 获取圆型Bitmap
        fun getCircleBitmap(src: Bitmap): Bitmap {
            var diameter = Math.min(src.getWidth(), src.getHeight());
            var dst = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
            var canvas = Canvas(dst);
            var path = Path();
            path.addCircle(diameter / 2f, diameter / 2f, diameter / 2f, Path.Direction.CW);
            canvas.clipPath(path);
            canvas.drawBitmap(src, 0f, 0f, Paint());
            return dst;
        }

        // 获取圆型Bitmap
        fun getCircleBitmap2(src: Bitmap): Bitmap {
            var diameter = Math.min(src.getWidth(), src.getHeight());
            var dst = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
            var canvas = Canvas(dst);
            var shader = BitmapShader(src, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            var paint = Paint();
            paint.setShader(shader);
            canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, paint);
            return dst;
        }

        // 获取圆型Bitmap
        fun getCircleBitmap3(src: Bitmap): Bitmap {
            var diameter = Math.min(src.getWidth(), src.getHeight());
            var dst = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
            var canvas = Canvas(dst);
            var paint = Paint();
            paint.setAntiAlias(true);
            canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, paint);
            var xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
            paint.setXfermode(xfermode);
            canvas.drawBitmap(src, 0f, 0f, paint);
            return dst;
        }
        
        // 获取圆角矩形Bitmap
        fun getRoundCornerBitmap(src: Bitmap, radius:Float): Bitmap {
            var dst = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
            var canvas =  Canvas(dst);
            var path =  Path();
            var rect = RectF(0f, 0f, src.width.toFloat(), src.height.toFloat());
            path.addRoundRect(rect, radius, radius, Path.Direction.CW);
            canvas.clipPath(path);
            canvas.drawBitmap(src, 0f, 0f, Paint());
            return dst;
        }

        // 获取圆角矩形Bitmap
        fun getRoundCornerBitmap2(src: Bitmap, radius:Float): Bitmap {
            var width = src.width;
            var height = src.height;
            var roundCornerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            var canvas = Canvas(roundCornerBitmap);
            var paint = Paint();
            paint.setAntiAlias(true);
            var rect = Rect(0, 0, width, height);
            var rectF = RectF(rect);
            canvas.drawRoundRect(rectF, radius, radius, paint);
            var xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
            paint.setXfermode(xfermode);
            canvas.drawBitmap(src, rect, rect, paint);
            return roundCornerBitmap;
        }

        // 获取圆角矩形Bitmap
        fun getRoundCornerBitmap3(src: Bitmap, radius:Float): Bitmap {
            var dst = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
            var canvas = Canvas(dst);
            var rect = RectF(0f, 0f, dst.getWidth().toFloat(), dst.getHeight().toFloat());
            var shader = BitmapShader(src, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            var paint = Paint();
            paint.setShader(shader);
            canvas.drawRoundRect(rect, radius, radius, paint);
            return dst;
        }
    }
}