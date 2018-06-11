package com.sys

import android.os.Bundle
import com.appinfo.AppInfo
import com.common.base.BaseAppCompactActivitiy
import com.common.base.BasePagerAdapter
import com.common.base.CommonTitleView
import com.sz.sk.clear.desk.R
import kotlinx.android.synthetic.main.activity_desk_app_set.*

class DeskAppSetActivity : BaseAppCompactActivitiy() {

    val list = ArrayList<AppInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desk_app_set)

        initActivitys()
    }

    override fun initTitle() {
        commonTitleView.setCenterTitleText("桌面应用设备")
        commonTitleView.setOnTitleClickListener(object : CommonTitleView.OnTitleClickListener(){
            override fun onLeftBtnClick() {
                finish()
            }
        })
    }

    override fun initViews() {
        val list = ArrayList<DeskAppView>()
        list.add(DeskAppView(context , 0))
        list.add(DeskAppView(context , 1))

        val adapter = BasePagerAdapter<DeskAppView>(context , list)
        viewPager.adapter = adapter

    }


}
