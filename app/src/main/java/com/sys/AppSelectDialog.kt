package com.sys

import android.content.Context
import android.os.Bundle
import com.base.IBaseDialog
import com.sz.sk.clear.desk.R

class AppSelectDialog : IBaseDialog {

    constructor(context: Context) : super(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_select_dialog)

    }

}