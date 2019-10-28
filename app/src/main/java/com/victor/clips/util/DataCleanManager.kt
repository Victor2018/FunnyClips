package com.victor.clips.util

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.math.BigDecimal


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DataCleanManager.java
 * Author: Victor
 * Date: 2019/10/28 14:45
 * Description: 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
 * -----------------------------------------------------------------
 */
class DataCleanManager {
    companion object {
        private val TAG = "DataCleanManager"
        /**
         * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
         *
         * @param context
         */
        fun cleanInternalCache(context: Context) {
            Loger.e(TAG, "cleanInternalCache()......")
            deleteFilesByDirectory(context.getCacheDir())
        }

        /**
         * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
         *
         * @param context
         */
        fun cleanDatabases(context: Context) {
            Loger.e(TAG, "cleanDatabases()......")
            deleteFilesByDirectory(File("/data/data/"
                    + context.getPackageName() + "/databases"))
        }

        /**
         * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
         *
         * @param context
         */
        fun cleanSharedPreference(context: Context) {
            Loger.e(TAG, "cleanSharedPreference()......")
            deleteFilesByDirectory(File("/data/data/"
                    + context.getPackageName() + "/shared_prefs"))
        }

        /**
         * * 按名字清除本应用数据库 * *
         *
         * @param context
         * @param dbName
         */
        fun cleanDatabaseByName(context: Context, dbName: String) {
            context.deleteDatabase(dbName)
        }

        /**
         * * 清除/data/data/com.xxx.xxx/files下的内容 * *
         *
         * @param context
         */
        fun cleanFiles(context: Context) {
            Loger.e(TAG, "cleanFiles()......")
            deleteFilesByDirectory(context.getFilesDir())
        }

        /**
         * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
         *
         * @param context
         */
        fun cleanExternalCache(context: Context) {
            Loger.e(TAG, "cleanExternalCache()......")
            if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                deleteFilesByDirectory(context.getExternalCacheDir())
            }
        }

        /**
         * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
         *
         * @param filePath
         */
        fun cleanCustomCache(filePath: String) {
            Loger.e(TAG, "cleanCustomCache()......")
            deleteFilesByDirectory(File(filePath))
        }

        /**
         * * 清除本应用所有的数据 * *
         *
         * @param context
         * @param filepath
         */
        fun cleanApplicationData(context: Context, vararg filepath: String) {
            cleanInternalCache(context)
            cleanExternalCache(context)
            cleanDatabases(context)
            cleanSharedPreference(context)
            cleanFiles(context)
            if (filepath == null) {
                return
            }
            for (filePath in filepath) {
                cleanCustomCache(filePath)
            }
        }

        /**
         * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
         *
         * @param directory
         */
        private fun deleteFilesByDirectory(directory: File?) {
            if (directory != null && directory!!.exists() && directory!!.isDirectory()) {
                for (item in directory!!.listFiles()) {
                    item.delete()
                }
            }
        }

        // 获取文件
        //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
        @Throws(Exception::class)
        fun getFolderSize(file: File): Long {
            var size: Long = 0
            try {
                val fileList = file.listFiles()
                for (i in fileList.indices) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i])
                    } else {
                        size = size + fileList[i].length()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return size
        }

        /**
         * 删除指定目录下文件及目录
         *
         * @param deleteThisPath
         * @return
         */
        fun deleteFolderFile(filePath: String, deleteThisPath: Boolean) {
            if (!TextUtils.isEmpty(filePath)) {
                try {
                    val file = File(filePath)
                    if (file.isDirectory()) {// 如果下面还有文件
                        val files = file.listFiles()
                        for (i in files.indices) {
                            deleteFolderFile(files[i].getAbsolutePath(), true)
                        }
                    }
                    if (deleteThisPath) {
                        if (!file.isDirectory()) {// 如果是文件，删除
                            file.delete()
                        } else {// 目录
                            if (file.listFiles().size === 0) {// 目录下没有文件或者目录，删除
                                file.delete()
                            }
                        }
                    }
                } catch (e: Exception) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

            }
        }

        /**
         * 格式化单位
         *
         * @param size
         * @return
         */
        fun getFormatSize(size: Double): String {
            val kiloByte = size / 1024
            if (kiloByte < 1) {
                return size.toString() + "Byte"
            }

            val megaByte = kiloByte / 1024
            if (megaByte < 1) {
                val result1 = BigDecimal(kiloByte.toString())
                return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                        .toPlainString() + "KB"
            }

            val gigaByte = megaByte / 1024
            if (gigaByte < 1) {
                val result2 = BigDecimal(megaByte.toString())
                return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                        .toPlainString() + "MB"
            }

            val teraBytes = gigaByte / 1024
            if (teraBytes < 1) {
                val result3 = BigDecimal(gigaByte.toString())
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                        .toPlainString() + "GB"
            }
            val result4 = BigDecimal(teraBytes)
            return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
        }


        @Throws(Exception::class)
        fun getCacheSize(file: File): String {
            return getFormatSize(getFolderSize(file).toDouble())
        }
    }
}