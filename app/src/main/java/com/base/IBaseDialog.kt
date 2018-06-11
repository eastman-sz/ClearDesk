package com.base

import android.content.Context
import com.common.dialog.BaseDialog
import com.sz.sk.clear.desk.R

open class IBaseDialog : BaseDialog {

    constructor(context: Context) : super(context , R.style.lable_del_dialog)

    override fun show() {
        super.show()

        var attributes = window.attributes
        attributes.width = context.resources.displayMetrics.widthPixels
        attributes.height = context.resources.displayMetrics.heightPixels
        window.attributes = attributes
    }

}