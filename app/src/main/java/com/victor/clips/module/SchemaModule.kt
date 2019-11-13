package com.victor.clips.module

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.victor.clips.R
import com.victor.clips.ui.WebActivity
import com.victor.clips.util.Constant
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
        val DOMAIN_MAIL = "mail"

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
            try {
                when (domain) {
                    DOMAIN_OPEN_URL -> {
                        //打开浏览器
                        schemaWeb(ctx, uri)
                    }
                    DOMAIN_MAIL -> {
                        //打开浏览器
                        sendEmail(ctx, uri)
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

        private fun schemaWeb(context: Context, uri: Uri) {
            val title = uri.getQueryParameter("title")
            val url = uri.getQueryParameter("url")
            Loger.e(TAG, "schemaWeb()-url = " + url)
            if (TextUtils.isEmpty(url)) return
            WebActivity.intentStart(context, title, url)
        }

        fun sendEmail (context: Context, uri: Uri) {
            val gmail = uri.getQueryParameter("gmail")
            // 必须明确使用mailto前缀来修饰邮件地址
            val uri = Uri.parse(String.format(Constant.MAIL_TO,gmail))
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.mail_to_me_tip)))
        }
    }


}