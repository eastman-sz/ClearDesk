package com.start

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.common.base.BaseAppCompactActivitiy
import com.main.MainActivity
import com.sz.sk.clear.desk.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : BaseAppCompactActivitiy() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        initActivitys()
    }

    override fun initViews() {
        pathTextView.setTextColor(context.resources.getColor(R.color.c8))
        pathTextView.setTextSize(getResources().getDimensionPixelSize(R.dimen.s7).toFloat())
        pathTextView.setTextWeight(4)
        pathTextView.setShadow(1 ,1,1 ,context.resources.getColor(R.color.c3))
        pathTextView.setDuration(3000)
        pathTextView.init("More clear More Happy")

        handler.sendEmptyMessageDelayed(0 , 3000)
    }

    private val handler = Handler(Looper.getMainLooper()){
        when(it.what){
            0 ->{
                pathTextView.init("You konow")

                it.target.sendEmptyMessageDelayed(1 , 3500)
            }

            1 ->{
                startActivity(Intent(context , MainActivity::class.java))

                finish()
            }

        }
        true
    }

    override fun onDestroy() {
        handler.removeMessages(0)
        handler.removeMessages(1)
        super.onDestroy()
    }

}
