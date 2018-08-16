package com.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.main.MainActivity

class LauncherHelper {

    companion object {

        /**
         * Set Activity as default launcher.
         * @param context context
         */
        fun resetPreferredLauncherAndOpenChooser(context: Context) {
            try {
                val packageManager = context.packageManager
                val componentName = ComponentName(context, MainActivity::class.java!!)
                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP)

                //start launcher chooser
                val selector = Intent(Intent.ACTION_MAIN)
                selector.addCategory(Intent.CATEGORY_HOME)
                selector.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(selector)

                packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


    }

}