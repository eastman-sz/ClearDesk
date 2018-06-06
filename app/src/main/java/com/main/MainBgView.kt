package com.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.base.BaseView
import com.common.base.BasePagerAdapter
import com.sz.sk.clear.desk.R
import kotlinx.android.synthetic.main.main_bg_view.view.*

class MainBgView : BaseView {

    val dataList = ArrayList<String>()
    val list = ArrayList<MainBgSubView>()
    var adapter : BasePagerAdapter<MainBgSubView> ?= null

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_bg_view , this)

        list.add(MainBgSubView(context))
        list.add(MainBgSubView(context))
        list.add(MainBgSubView(context))

        adapter = BasePagerAdapter(context , list)
        viewPager.adapter = adapter


    }

}