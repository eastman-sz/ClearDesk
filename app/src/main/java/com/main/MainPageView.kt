package com.main

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.base.BaseView
import com.sz.sk.clear.desk.R
import kotlinx.android.synthetic.main.main_page_view.view.*

class MainPageView : BaseView {

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_page_view , this)

        mainContentView.onMainContentViewPageChangeListener = object : OnMainContentViewPageChangeListener{
            override fun onPageIndexChange(index: Int) {
                dotSwitchView.onSwithItem(index)

            }
        }
    }

    override fun initListener() {

    }
}