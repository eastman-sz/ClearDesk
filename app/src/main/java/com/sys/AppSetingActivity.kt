package com.sys

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.common.base.BaseAppCompactActivitiy
import com.common.base.CommonTitleView
import com.sz.sk.clear.desk.R
import kotlinx.android.synthetic.main.activity_app_seting.*

class AppSetingActivity : BaseAppCompactActivitiy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_seting)

        initActivitys()
    }

    override fun initTitle() {
        commonTitleView.setCenterTitleText("设置")
        commonTitleView.setOnTitleClickListener(object : CommonTitleView.OnTitleClickListener() {
            override fun onLeftBtnClick() {
                finish()
            }
        })
    }

    fun onBtnClick(v : View){
        when(v){
            sysetingTextView -> {
                startActivity(Intent(Settings.ACTION_SETTINGS))
            }

            appSettingTextView -> {

            }
        }


    }


}
