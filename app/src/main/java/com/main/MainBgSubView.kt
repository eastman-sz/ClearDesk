package com.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.base.BaseView
import com.sz.sk.clear.desk.R
import kotlinx.android.synthetic.main.main_bg_sub_view.view.*

class MainBgSubView : BaseView {

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_bg_sub_view , this)
    }

    fun setImageResource(resid : Int){
        imageView.setImageResource(resid)
    }

}