package com.victor.clips.util

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CryptoUtils.java
 * Author: Victor
 * Date: 2019/4/2 16:34
 * Description:
 * -----------------------------------------------------------------
 */
class CryptoUtils {
    companion object {
        /**
         * 获取MD5
         *
         * @param str
         * @return
         */
        fun MD5(str: String): String {
            try {
                val md5 = MessageDigest.getInstance("MD5")
                val charArray = str.toCharArray()
                val byteArray = ByteArray(charArray.size)

                for (i in charArray.indices) {
                    byteArray[i] = charArray[i].toByte()
                }
                val md5Bytes = md5.digest(byteArray)

                val hexValue = StringBuffer()
                for (i in md5Bytes.indices) {
                    val `val` = md5Bytes[i].toInt() and 0xff
                    if (`val` < 16) {
                        hexValue.append("0")
                    }
                    hexValue.append(Integer.toHexString(`val`))
                }
                return hexValue.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }

        /**
         * 文件MD5值
         *
         * @param filepath
         */
        fun md5File(filepath: String): String? {
            try {
                val file = File(filepath)
                val fis = FileInputStream(file)
                val md = MessageDigest.getInstance("MD5")
                val buffer = ByteArray(1024)
                var length = -1
                while (fis.read(buffer, 0, 1024) != -1) {
                    length = fis.read(buffer, 0, 1024)
                    md.update(buffer, 0, length)
                }
                val bigInt = BigInteger(1, md.digest())
                return bigInt.toString(16)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }
    }
}