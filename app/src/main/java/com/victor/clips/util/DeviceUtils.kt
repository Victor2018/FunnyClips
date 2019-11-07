package com.victor.clips.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import com.victor.clips.app.App
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import kotlin.experimental.and

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DeviceUtils.kt
 * Author: Victor
 * Date: 2019/4/2 16:29
 * Description:
 * -----------------------------------------------------------------
 */
class DeviceUtils {
    companion object {
        private var mDisplayMetrics: DisplayMetrics? = null

        /**
         * 获取设备的宽和高
         *
         * @return
         */
        fun getDisplayMetrics(): DisplayMetrics? {
            if (mDisplayMetrics == null) {
                mDisplayMetrics = App.get().getResources().getDisplayMetrics()
            }
            return mDisplayMetrics
        }

        fun getDisplayWidth(): Int {
            if (mDisplayMetrics == null) {
                mDisplayMetrics = App.get().getResources().getDisplayMetrics()
            }
            return mDisplayMetrics!!.widthPixels
        }

        fun getDisplayHeight(): Int {
            if (mDisplayMetrics == null) {
                mDisplayMetrics = App.get().getResources().getDisplayMetrics()
            }
            return mDisplayMetrics!!.heightPixels
        }

        @SuppressLint("NewApi")
        fun checkPermission(context: Context, permission: String): Boolean {
            var result = false
            if (Build.VERSION.SDK_INT >= 23) {
                if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    result = true
                }
            } else {
                val pm = context.packageManager
                if (pm.checkPermission(permission, context.packageName) == PackageManager.PERMISSION_GRANTED) {
                    result = true
                }
            }
            return result
        }

        /**
         * 获取设备的唯一标识 物理地址加device id
         *
         * @return
         */
        @SuppressLint("MissingPermission")
        fun getUDID(): String {
            try {
                val tm = App.get().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                var device_id = ""
                if (checkPermission(App.get(), Manifest.permission.READ_PHONE_STATE)) {
                    device_id = tm.deviceId
                }
                if (TextUtils.isEmpty(device_id)) {
                    device_id = android.provider.Settings.Secure.getString(App.get().getContentResolver(),
                            android.provider.Settings.Secure.ANDROID_ID)
                }
                return CryptoUtils.MD5(device_id)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        fun getMac(): String {
            var mac_s = ""
            try {
                val mac: ByteArray
                val ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress()))
                mac = ne.hardwareAddress
                mac_s = byte2hex(mac)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return mac_s
        }

        fun getEthernetMac(): String {
            var reader: BufferedReader? = null
            var ethernetMac = ""
            try {
                reader = BufferedReader(FileReader(
                        "sys/class/net/eth0/address"))
                ethernetMac = reader.readLine()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return ethernetMac.toUpperCase()
        }

        fun getTVMac(): String {
            val tvMac = StringBuffer()
            val mac = getMac().toUpperCase()
            try {
                for (i in 0 until mac.length / 2) {
                    tvMac.append(mac.substring(i * 2, (i + 1) * 2) + ":")
                }
                tvMac.deleteCharAt(tvMac.lastIndexOf(":"))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return tvMac.toString()
        }

        fun byte2hex(b: ByteArray): String {
            var hs = StringBuffer(b.size)
            var stmp = ""
            val len = b.size
            for (n in 0 until len) {
                stmp = Integer.toHexString((b[n] and 0xFF.toByte()).toInt())
                if (stmp.length == 1)
                    hs = hs.append("0").append(stmp)
                else {
                    hs = hs.append(stmp)
                }
            }

            return hs.toString()
        }


        fun getLocalIpAddress(): String {
            var sLocalIPAddress = ""
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val netInterface = en.nextElement() as NetworkInterface

                    val ipaddr = netInterface.inetAddresses
                    while (ipaddr.hasMoreElements()) {

                        val inetAddress = ipaddr.nextElement() as InetAddress

                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                            sLocalIPAddress = inetAddress.getHostAddress().toString()
                        }
                    }
                }
            } catch (ex: SocketException) {
                ex.printStackTrace()
            }

            return sLocalIPAddress
        }

        /**
         * 获取手机品牌
         *
         * @return
         */
        fun getPhoneBrand(): String {
            return Build.BRAND
        }

        /**
         * 获取系统版本
         *
         * @return
         */
        fun getSysVersion(): String {
            return Build.VERSION.RELEASE
        }

        /**
         * 获取手机型号
         *
         * @return
         */
        fun getPhoneModel(): String {
            return Build.MODEL
        }

        fun getSysLanguage(): String {
            val locale = Locale.getDefault()
            return locale.language
        }

        fun getDeviceSN(): String {
            var serial = ""
            try {
                val c = Class.forName("android.os.SystemProperties")
                val get = c.getMethod("get", String::class.java)
                serial = get.invoke(c, "ro.serialno") as String
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return serial
        }

        fun getDeviceSN2(): String {
            return Build.SERIAL
        }
    }
}