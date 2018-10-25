package com.victor.clips.util

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import android.net.ConnectivityManager
import java.net.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpUtil.java
 * Author: Victor
 * Date: 2018/9/28 10:20
 * Description: 
 * -----------------------------------------------------------------
 */
class HttpUtil {
    companion object {
        val TAG = "HttpUtil"
        val CONNECT_TIME_OUT = 10000
        val READ_TIME_OUT = 15000
        val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63"
        val ENCODE = "utf-8"

        @Synchronized
        fun HttpPostRequest(requestUrl: String, headrs: HashMap<String, String>?, parms: JSONObject?): String {
            var result = ""
            try {
                val url = URL(requestUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.setConnectTimeout(CONNECT_TIME_OUT)
                conn.setReadTimeout(READ_TIME_OUT)
                conn.setDoInput(true)
                conn.setDoOutput(true)

                //add reuqest header
                conn.setRequestMethod("POST")
                conn.setRequestProperty("User-Agent", USER_AGENT)
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
                conn.setRequestProperty("Accept-Charset", ENCODE)
                conn.setRequestProperty("Content-type", "application/json")
                for (entry in headrs!!.entries) {
                    conn.setRequestProperty(entry.key, entry.value)
                }

                if (parms != null) {
                    val dos = DataOutputStream(conn.getOutputStream())
                    dos.write(parms.toString().toByteArray(StandardCharsets.UTF_8))
                    dos.flush()
                    dos.close()
                }

                val responseCode = conn.getResponseCode()
                Log.e(TAG, "requestUrl = $requestUrl")
                Log.e(TAG, "Response Code = $responseCode")

                val `is` = conn.getInputStream()
                val bos = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var size = `is`.read(buffer)
                while (size != -1) {
                    bos.write(buffer, 0, size)
                    size = `is`.read(buffer)
                }
                result = String(bos.toByteArray(), Charset.forName(ENCODE))
                `is`.close()
                bos.close()
                conn.disconnect()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: ProtocolException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return result
        }

        @Synchronized
        @Throws(SocketTimeoutException::class)
        fun HttpGetRequest(requestUrl: String, headrs: HashMap<String, String>?): String {
            var result = ""
            try {
                val url = URL(requestUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = CONNECT_TIME_OUT
                conn.readTimeout = READ_TIME_OUT
                // optional default is GET
                conn.requestMethod = "GET"

                //add request header
                conn.setRequestProperty("User-Agent", USER_AGENT)

                conn.setRequestProperty("Content-Type", "application/json")
                for (entry in headrs!!.entries) {
                    conn.setRequestProperty(entry.key, entry.value)
                }

                val responseCode = conn.responseCode
                Log.e(TAG, "HttpGetRequest-responseCode=$responseCode")

                val `is` = conn.inputStream
                val bos = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var size = `is`.read(buffer)
                while (size != -1) {
                    bos.write(buffer, 0, size)
                    size = `is`.read(buffer)
                }
                result = String(bos.toByteArray(), Charset.forName(ENCODE))
                `is`.close()
                bos.close()
                conn.disconnect()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return result
        }

        @Synchronized
        @Throws(SocketTimeoutException::class)
        fun GetReponseCode(requestUrl: String): Int {
            var responseCode = 0
            try {
                val url = URL(requestUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = CONNECT_TIME_OUT
                conn.readTimeout = READ_TIME_OUT
                // optional default is GET
                conn.requestMethod = "GET"

                //add request header
                conn.setRequestProperty("User-Agent", USER_AGENT)

                conn.setRequestProperty("Content-Type", "application/json")

                responseCode = conn.responseCode
                Log.e(TAG, "HttpGetRequest-responseCode=$responseCode")
                val `is` = conn.inputStream
                `is`.close()
                conn.disconnect()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return responseCode
        }

        /**
         * 检测网络是否可用
         * @param context
         * @return
         */
        @Synchronized
        fun isNetEnable(context: Context): Boolean {
            val connManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkinfo = connManager.activeNetworkInfo
            return if (networkinfo == null || !networkinfo.isAvailable) {
                false
            } else true

        }
    }
}