package com.victor.clips.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.PendingIntent
import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils
import com.victor.clips.app.App
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppUtil.java
 * Author: Victor
 * Date: 2019/4/2 16:06
 * Description:
 * -----------------------------------------------------------------
 */
class AppUtil {
    companion object {
        // 应用版本
        fun getAppVersionCode(context: Context): Int {
            val packageName = context.packageName
            try {
                return context.packageManager.getPackageInfo(
                        packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException("System fault!!!", e)
            }

        }

        fun getAppVersionName(context: Context): String {
            val packageName = context.packageName
            try {
                return context.packageManager.getPackageInfo(
                        packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException("System fault!!!", e)
            }

        }

        /**
         * 获取APP名称
         * @param context
         * @return
         */
        fun getAppName(context: Context): String {
            var appName = ""
            try {
                val packageManager = context.packageManager

                val applicationInfo = packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                appName = packageManager.getApplicationLabel(applicationInfo).toString()
                return appName
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException("System fault!!!", e)
            }

        }

        /**
         * 启动到应用商店app详情界面
         *
         * @param appPkg    目标App的包名
         * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
         */
        fun launchAppDetail(activity: Activity, appPkg: String, marketPkg: String) {
            try {
                if (TextUtils.isEmpty(appPkg)) return

                val uri = Uri.parse("market://details?id=$appPkg")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                if (!TextUtils.isEmpty(marketPkg)) {
                    intent.setPackage(marketPkg)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * 判断某一个Activity是否存在任务栈里面
         * @return
         */
        fun isActivityInTask(cls: Class<*>): Boolean {
            val intent = Intent(App.get(), cls)
            val cmpName = intent.resolveActivity(App.get().getPackageManager())
            var flag = false
            if (cmpName != null) { // 说明系统中存在这个activity
                val am = App.get().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val taskInfoList = am.getRunningTasks(10)
                for (taskInfo in taskInfoList) {
                    if (taskInfo.baseActivity == cmpName) { // 说明它已经启动了
                        flag = true
                        break  //跳出循环，优化效率
                    }
                }
            }
            return flag
        }

        /**
         * 安全的启动APP
         *
         * @param ctx
         * @param intent
         */
        fun launchApp(ctx: Context?, intent: Intent): Boolean {
            if (ctx == null)
                throw NullPointerException("ctx is null")
            try {
                ctx.startActivity(intent)
                return true
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                return false
            }

        }

        /**
         * Launch From Notifycation
         *
         * @param context
         * @param intent
         */
        fun launchAppWithPending(context: Context?, intent: Intent?) {
            if (context == null || intent == null) {
                return
            }
            val ctx = context.applicationContext
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
            val pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            try {
                pendingIntent.send(ctx, 0, intent)
            } catch (e: PendingIntent.CanceledException) {
                e.printStackTrace()
            }

        }

        /**
         * 带有动画切换效果的启动Activity
         *
         * @param ctx
         * @param intent
         * @return
         */
        fun launchAppWithAnim(ctx: Context, intent: Intent): Boolean {
            return launchAppWithAnim(ctx, intent, android.R.anim.fade_in, android.R.anim.fade_out)
        }

        /**
         * 带有动画切换效果的启动Activity
         *
         * @param ctx
         * @param intent
         * @param enterAnim
         * @param exitAnim
         * @return
         */
        fun launchAppWithAnim(ctx: Context?, intent: Intent, enterAnim: Int, exitAnim: Int): Boolean {
            if (ctx == null)
                throw NullPointerException("ctx is null")
            try {
                ctx.startActivity(intent)
                if (ctx is Activity) {
                    ctx.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
                return true
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                return false
            }

        }

        fun startActivityForResult(ctx: Activity?, intent: Intent, code: Int): Boolean {
            if (ctx == null)
                throw NullPointerException("ctx is null")
            try {
                ctx.startActivityForResult(intent, code)
                return true
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                return false
            }

        }

        /**
         * Fragment和Activity均可唤起
         *
         * @param ctx
         * @param intent
         * @param requestCode
         * @return
         */
        fun launchAppForResult(ctx: Activity?, intent: Intent, requestCode: Int): Boolean {
            if (ctx == null)
                throw NullPointerException("ctx is null")
            try {
                ctx.startActivityForResult(intent, requestCode)
                return true
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                return false
            }

        }

        /**
         * 启动APP
         *
         * @param ctx   上下文
         * @param clazz 类名
         */
        fun launchApp(ctx: Context, clazz: Class<*>?) {
            if (clazz == null)
                throw NullPointerException("the parameter is null")
            val intent = Intent(ctx, clazz)
            launchApp(ctx, intent)
        }

        /**
         * 启动APP
         *
         * @param ctx         上下文
         * @param packageName 包名
         * @param className   类名
         */
        fun launchApp(ctx: Context, packageName: String?, className: String?) {
            if (packageName == null || className == null)
                throw NullPointerException("the parameter is null")
            val intent = Intent(Intent.ACTION_MAIN)
            intent.component = ComponentName(packageName, className)
            launchApp(ctx, intent)
        }

        /**
         * 结束当前页面
         *
         * @param ctx
         */
        fun finish(ctx: Context?) {
            if (ctx == null)
                throw NullPointerException("ctx is null")
            if (ctx is Activity) {
                ctx.finish()
            }
        }

        /**
         * 卸载APP
         *
         * @param ctx        上下文参数
         * @param packageUri 包URI
         */
        fun uninstallApp(ctx: Context?, packageUri: Uri?) {
            if (ctx == null || packageUri == null)
                throw NullPointerException("the parameter is null")
            val uninstallIntent = Intent(Intent.ACTION_DELETE, packageUri)
            launchApp(ctx, uninstallIntent)
        }

        /**
         * 卸载APP
         *
         * @param ctx     上下文
         * @param pkgName 包名
         */
        fun uninstallApp(ctx: Context, pkgName: String?) {
            if (pkgName == null)
                throw NullPointerException("the parameter is null")
            val packageUri = Uri.parse("package:$pkgName")
            uninstallApp(ctx, packageUri)
        }

        /**
         * 启动系统设置页面
         *
         * @param ctx 上下文
         */
        fun launchSettings(ctx: Context) {
            val intent = Intent(Settings.ACTION_SETTINGS)
            launchApp(ctx, intent)
        }

        /**
         * 启动系统浏览器页面
         *
         * @param ctx 上下文
         */
        fun launchBrowser(ctx: Context, url: String) {
            val browserUri = Uri.parse(url)
            if (null != browserUri) {
                val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
                browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                launchApp(ctx, browserIntent)
            }
        }

        /**
         * 获取包信息
         *
         * @return 包信息
         */
        fun getPackageInfo(ctx: Context): PackageInfo? {
            val pm = ctx.packageManager
            try {
                return pm.getPackageInfo(ctx.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return null
        }


        /**
         * 获取包信息
         *
         * @return 包信息
         */
        fun getPackageManager(ctx: Context): PackageManager {
            return ctx.packageManager
        }

        /**
         * 获取指定APP上下文
         *
         * @param context     上下文
         * @param packageName 包名
         * @return
         */
        fun getAppContext(context: Context?, packageName: String?): Context? {
            if (context == null || packageName == null)
                throw NullPointerException("the parameter is null")
            var ctx: Context? = null
            try {
                ctx = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return ctx
        }

        /**
         * 判断是否为系统APP
         *
         * @return
         */
        fun isSystemApp(ctx: Context, packageName: String): Boolean {
            val packageManager = ctx.packageManager
            var isSystemApp = false
            try {
                val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
                if (applicationInfo != null) {
                    isSystemApp = applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0 || applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return isSystemApp
        }

        /**
         * 获取版本Code
         *
         * @param packageName
         * @return
         */
        fun getVersionCode(packageName: String): Int {
            val packageManager = App.get().getPackageManager()
            try {
                val packageInfo = packageManager.getPackageInfo(packageName, 0)
                return packageInfo.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return -1
        }

        /**
         * 获取VersonName
         *
         * @param packageName
         * @return
         */
        fun getVersionName(packageName: String): String {
            val packageManager = App.get().getPackageManager()
            try {
                val packageInfo = packageManager.getPackageInfo(packageName, 0)
                return packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return ""
        }

        /**
         * 获取包名
         *
         * @return
         */
        fun getPackageName(): String {
            return App.get().getPackageName()
        }

        /**
         * com.tencent.mobileqq
         *
         * @param packageName
         * @return
         */
        fun isAppExist(packageName: String): Boolean {
            val packageManager = App.get().getPackageManager()
            val applicationInfos = packageManager.getInstalledApplications(0)
            for (info in applicationInfos) {
                if (TextUtils.equals(info.packageName, packageName)) {
                    return true
                }
            }
            return false
        }


        /**
         * 判断是否在主线程里面
         *
         * @return
         */
        fun inMainProcess(): Boolean {
            val packageName = App.get().getPackageName()
            val processName = getProcessName()
            return packageName == processName
        }

        fun getProcessName(): String? {
            var processName: String? = null
            val am = App.get().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    ?: return null
            while (true) {
                for (info in am.runningAppProcesses) {
                    if (info.pid == android.os.Process.myPid()) {
                        processName = info.processName
                        break
                    }
                }
                if (!TextUtils.isEmpty(processName)) {
                    return processName
                }
                try {
                    Thread.sleep(100L)
                } catch (ex: InterruptedException) {
                    ex.printStackTrace()
                }

            }
        }


        /**
         * 移动到顶部
         *
         * @param activity
         */
        @SuppressLint("MissingPermission")
        fun moveToFront(activity: Activity) {
            //移动到顶部
            val taskID = activity.taskId
            val manager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            manager.moveTaskToFront(taskID, ActivityManager.MOVE_TASK_WITH_HOME)
        }


        /**
         * 判断某个界面是否在前台
         *
         * @param activity
         * @return 是否在前台显示
         */
        fun isForeground(activity: Activity?): Boolean {
            if (activity == null) return false
            val am = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = am.getRunningTasks(1)
            if (list != null && list.size > 0) {
                val cpn = list[0].topActivity
                if (activity.javaClass.name == cpn.className) {
                    return true
                }
            }
            return false
        }

        /**
         * 获取系统语言
         * @return
         */
        fun getSysLanguage(): String {
            return Locale.getDefault().language
        }

        /**
         * 获取SDK版本
         */
        fun getSDKVersion(): Int {
            var version = 0
            try {
                version = Integer.valueOf(android.os.Build.VERSION.SDK)
            } catch (e: NumberFormatException) {
            }

            return version
        }

        fun scanForActivity(context: Context?): Activity? {
            if (context == null) return null
            if (context is Activity) {
                return context
            } else if (context is ContextWrapper) {
                return scanForActivity(context.baseContext)
            }
            return null
        }
    }
}
 