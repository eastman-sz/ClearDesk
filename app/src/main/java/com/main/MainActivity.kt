package com.main

import android.os.Bundle
import com.common.base.BaseAppCompactActivitiy
import com.sz.sk.clear.desk.R
import com.util.PermissionHelpler

class MainActivity : BaseAppCompactActivitiy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActivitys()

        PermissionHelpler.requestPermissions(this)
    }
}
