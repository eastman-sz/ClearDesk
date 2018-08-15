package com.weather

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import com.alibaba.fastjson.JSON
import com.base.BaseView
import com.sz.sk.clear.desk.R
import com.sz.ss.gd.map.GdLocationHelper
import com.sz.ss.gd.map.GdMapLocation
import com.sz.ss.gd.map.OnGdLocationChangedListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import com.util.ILog
import kotlinx.android.synthetic.main.weather_view.view.*
/**
 *
 */
class WeatherView : BaseView {

    private var gdLocationHelper : GdLocationHelper ?= null

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.weather_view , this)

        gdLocationHelper = GdLocationHelper(context as Activity , object : OnGdLocationChangedListener{
            override fun onLocationChanged(latitude: Double, longitude: Double, jsonInfo: String?) {
                gdLocationHelper?.stopLocation()

                ILog.e("位置信息: $jsonInfo")

                getWeatherNew(latitude , longitude , jsonInfo)
            }
        })

        gdLocationHelper?.startLocation()
    }

    private val mHandler = Handler(Looper.getMainLooper()){
        when(it.what){
            0 ->{
                gdLocationHelper?.startLocation()
            }
        }
        true
    }

    //每小时更新一次
    private fun getWeather(){
        mHandler.removeMessages(0)
        mHandler.sendEmptyMessageDelayed(0 , 3600)
/*        try {
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
        }*/
    }

    private fun getWeatherNew(latitude : Double , longitude : Double , jsonInfo: String?){
        mHandler.removeMessages(0)
        mHandler.sendEmptyMessageDelayed(0 , 3600*1000)

        try {
            doAsync {

                val url = "http://m.weather.com.cn/d/town/index?lat=$latitude&lon=$longitude"
                val user_Agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"
                val document = Jsoup.connect(url)
                        .timeout(30000)
                        .userAgent(user_Agent).get()

                val element = document.select("div.n_wd h1 span").first()

                val element1 = document.select("div.weather-up-li time")[1]

                val degree = element.toString().substring(element.toString().indexOf(">") + 1, element.toString().lastIndexOf("<"))

                val weather = element1.toString().substring(element1.toString().indexOf(">") + 1, element1.toString().lastIndexOf("<"))

                uiThread {

                    val gdMapLocation = JSON.parseObject(jsonInfo , GdMapLocation::class.java)

                    textView.text = gdMapLocation.district.plus(" $weather $degree℃")

                    ILog.e("天气数据:: ${textView.text}")

                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onDetachedFromWindow() {
        mHandler.removeMessages(0)
        super.onDetachedFromWindow()
    }

}