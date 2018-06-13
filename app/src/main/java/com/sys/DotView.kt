package com.sys

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.base.BaseView
import com.sz.sk.clear.desk.R
import com.util.ILog
import kotlinx.android.synthetic.main.dotview.view.*

class DotView : BaseView {

    constructor(context: Context) : super(context){
        init()
    }


    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.dotview , this)
    }


    fun onSwithItem(item : Int){
        dot0ImageView.alpha = if (0 == item){1f}else{0.3f}
        dot1ImageView.alpha = if (1 == item){1f}else{0.3f}

        ILog.e("item : $item")
    }

}