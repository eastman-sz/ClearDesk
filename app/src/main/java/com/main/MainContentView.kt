package com.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.base.BaseView
import com.common.base.BasePagerAdapter
import com.common.base.IonPageChangeListener
import com.info.InfoVIew
import com.sz.sk.clear.desk.R
import kotlinx.android.synthetic.main.main_content_view.view.*

class MainContentView : BaseView {

    var onMainContentViewPageChangeListener : OnMainContentViewPageChangeListener ?= null

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_content_view , this)

        val list = ArrayList<BaseView>()
        list.add(InfoVIew(context))
        list.add(MainPage1VIew(context , 0))
        list.add(MainPage1VIew(context , 1))

        val adapter = BasePagerAdapter<BaseView>(context , list)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : IonPageChangeListener(){
            override fun onPageSelected(index: Int) {
                onMainContentViewPageChangeListener?.onPageIndexChange(index)

                list[index].freshByHand(true)
            }
        })

        list[1].freshByHand(true)
    }

}