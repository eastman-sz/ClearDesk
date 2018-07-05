package com.lunar

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import com.base.BaseView
import com.lunar.Lunar.chineseDateFormat
import com.sz.sk.clear.desk.R
import com.utils.lib.ss.common.DateHepler
import kotlinx.android.synthetic.main.lunar_view.view.*
import java.util.*
import kotlin.collections.ArrayList

class LunarView : BaseView {

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.lunar_view , this)

        showDate()

    }

    fun showDate(){
        val currentTimestamp = System.currentTimeMillis()/1000

        val today = Calendar.getInstance()
        today.time = chineseDateFormat.parse(DateHepler.timestampFormat(currentTimestamp , "yyyy年MM月dd日"))
        val lunar = Lunar(today)

        val dayOfWeek = DateHepler.getDayOfWeekTxt(DateHepler.getDayOfWeek(currentTimestamp))

        currentDateTextView.text = DateHepler.timestampFormat(currentTimestamp , "MM月dd日").plus("  ").plus(dayOfWeek)

        currentTimeTextView.text = DateHepler.timestampFormat(currentTimestamp , "HH:mm")

        lunarTextView.text = lunar.toString()

    }

    override fun addBroadCastAction(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add(Intent.ACTION_TIME_TICK)
        return super.addBroadCastAction()
    }

    override fun onBroadCastReceive(context: Context?, action: String?, intent: Intent?) {
        when(action){
            Intent.ACTION_TIME_TICK ->{
                showDate()
            }
        }
    }

}