package com.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import com.appinfo.AppInfo
import com.utils.lib.ss.common.PkgHelper
import java.util.ArrayList

class PkgUtil : PkgHelper() {

    companion object {

        fun getLauncherApps(context: Context){
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)

            val packageManager = context.packageManager
            val list = packageManager.queryIntentActivities(intent , 0)

            list.forEach {
                val name = it.loadLabel(packageManager)
                val packageName = it.activityInfo.packageName
                val drawable = it.loadIcon(packageManager)

                ILog.e("桌面应用信息: $name    $packageName")
            }
        }

        fun getInstalledApps(context: Context) : List<AppInfo>{
            val appInfos = ArrayList<AppInfo>()
            val manager = context.packageManager
            val pakList = manager.getInstalledPackages(0)

            try {
                val var4 = pakList.iterator()

                while (var4.hasNext()) {
                    val info = var4.next() as PackageInfo
                    if (info.applicationInfo.flags and 1 == 0) {
                        val icon = info.applicationInfo.loadIcon(manager)
                        val labelName = info.applicationInfo.loadLabel(manager) as String
                        val pkgName = info.packageName
                        val version = info.versionName

                        val appInfo = AppInfo()
                        appInfo.name = labelName
                        appInfo.drawable = icon
                        appInfo.packageName = pkgName
                        appInfo.version = version

                        appInfos.add(appInfo)
                    }
                }
            } catch (var11: Exception) {
                var11.printStackTrace()
            }
            return appInfos
        }

        fun getAllInstalledApps(context: Context) : List<AppInfo>{
            val appInfos = ArrayList<AppInfo>()
            val manager = context.packageManager
            val pakList = manager.getInstalledPackages(0)

            try {
                val var4 = pakList.iterator()

                while (var4.hasNext()) {
                    val info = var4.next() as PackageInfo

                    val icon = info.applicationInfo.loadIcon(manager)
                    val labelName = info.applicationInfo.loadLabel(manager) as String
                    val pkgName = info.packageName
                    val version = info.versionName

                    val appInfo = AppInfo()
                    appInfo.name = labelName
                    appInfo.drawable = icon
                    appInfo.packageName = pkgName
                    appInfo.version = version

                    appInfos.add(appInfo)
                }
            } catch (var11: Exception) {
                var11.printStackTrace()
            }
            return appInfos
        }









    }



}