package com.start

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.common.base.BaseAppCompactActivitiy
import com.main.MainActivity
import com.sz.sk.clear.desk.R

class StartActivity : BaseAppCompactActivitiy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(Intent(context , MainActivity::class.java))

        } , 500)

    }



}
