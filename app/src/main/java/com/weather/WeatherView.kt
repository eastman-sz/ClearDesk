package com.weather

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import com.base.BaseView
import com.sz.sk.clear.desk.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import com.util.ILog
import kotlinx.android.synthetic.main.weather_view.view.*

class WeatherView : BaseView {

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.weather_view , this)

        getWeather()
    }

    internal val handler = Handler(Looper.getMainLooper()){
        when(it.what){
            0 ->{
                getWeather()
            }
        }
        true
    }

    //每小时更新一次
    private fun getWeather(){
        handler.removeMessages(0)
        handler.sendEmptyMessageDelayed(0 , 3600)
        try {
            doAsync {

                val url = "http://www.weather.com.cn/weather1d/101280601.shtml"
                val user_Agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"
                val document = Jsoup.connect(url)
                        .timeout(30000)
                        .userAgent(user_Agent).get()
                val element = document.getElementById("hidden_title")

                val result = element.attr("value").toString()

                uiThread {
                    ILog.e("天气信息:  $result")

                    result?.let {
                        val length = it.length
                        textView.text = if (length > 14)  result.substring(14) else result
                    }

                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onDetachedFromWindow() {
        handler.removeMessages(0)
        super.onDetachedFromWindow()
    }

}