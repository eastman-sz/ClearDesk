package com.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.base.BaseView
import com.sz.sk.clear.desk.R

class MainBottomView : BaseView {

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_bottom_view , this)
    }
}