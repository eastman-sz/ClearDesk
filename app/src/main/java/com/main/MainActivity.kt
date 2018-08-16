package com.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.common.base.BaseAppCompactActivitiy
import com.sz.sk.clear.desk.R
import com.util.Constant
import com.util.LauncherHelper

class MainActivity : BaseAppCompactActivitiy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActivitys()

        Handler(Looper.getMainLooper()).postDelayed({
            if (Constant.DEBUG){
                return@postDelayed
            }
            LauncherHelper.resetPreferredLauncherAndOpenChooser(this)

        } , 1500)
    }

    override fun onBackPressed() {
        if (Constant.DEBUG){
            super.onBackPressed()
            return
        }
    }
}
