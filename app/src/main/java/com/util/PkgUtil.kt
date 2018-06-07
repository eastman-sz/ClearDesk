package com.util

import android.content.Context
import android.content.Intent
import com.utils.lib.ss.common.PkgHelper

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
    }

}