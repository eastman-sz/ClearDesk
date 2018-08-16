package com.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.common.base.BaseAppCompactActivitiy
import com.sz.sk.clear.desk.R
import com.util.LauncherHelper

class MainActivity : BaseAppCompactActivitiy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActivitys()

        Handler(Looper.getMainLooper()).postDelayed({
            LauncherHelper.resetPreferredLauncherAndOpenChooser(this)

        } , 1500)
    }
}
