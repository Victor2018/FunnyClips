package com.victor.clips.module

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.victor.clips.WebActivity
import com.victor.clips.util.Loger

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SchemaModule.kt
 * Author: Victor
 * Date: 2019/11/6 16:58
 * Description: schema跳转模块
 * -----------------------------------------------------------------
 */
class SchemaModule {

    companion object {
        var TAG = "SchemaModule"

        /**
         * 打开外部地址
         */
        val DOMAIN_OPEN_URL = "openurl"

        /**
         * 当前界面传入
         *
         * @param ctx
         * @param uri
         */
        fun dispatchSchema(ctx: Activity, uri: Uri?) {
            Loger.e(TAG, "dispatchSchema()-uri = $uri")
            if (uri == null) return
            val domain = uri.authority
            val url = uri.getQueryParameter("url")
            Loger.e(TAG, "dispatchSchema()-domain = $domain")
            try {
                when (domain) {
                    DOMAIN_OPEN_URL -> {
                        //打开浏览器
                        schemaWeb(ctx, uri, "")
                    }
                }
            } catch (e: UnsupportedOperationException) {
                e.printStackTrace()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }

        /**
         * 从App内部点击进入
         *
         * @param uri
         */

        private fun schemaWeb(context: Context, uri: Uri, title: String) {
            val url = uri.getQueryParameter("url")
            Loger.e(TAG, "schemaWeb()-url = " + url)
            if (TextUtils.isEmpty(url)) return
            WebActivity.intentStart(context, title, url)
        }

    }


}