package com.sys

import android.os.Bundle
import com.common.base.BaseAppCompactActivitiy
import com.sz.sk.clear.desk.R

class DeskAppSetActivity : BaseAppCompactActivitiy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desk_app_set)

        initActivitys()
    }

    override fun initTitle() {

    }


}
