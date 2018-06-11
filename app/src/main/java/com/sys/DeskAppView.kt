package com.sys

import android.content.Context
import android.view.View
import com.appinfo.AppInfo
import com.base.BaseView
import com.sz.sk.clear.desk.R
import kotlinx.android.synthetic.main.desk_app_view.view.*

class DeskAppView : BaseView {

    private var pageIndex : Int = 0

    private var adapter : DeskAppSetAdapter ?= null
    private val list = ArrayList<AppInfo>()


    constructor(context: Context , pageIndex : Int) : super(context){
        this.pageIndex = pageIndex
        init()

    }

    override fun initViews() {
        View.inflate(context , R.layout.desk_app_view , this)

        val appInfo = AppInfo()
        appInfo.type = 100

        list.add(appInfo)

        adapter = DeskAppSetAdapter(context , list)
        gridView.adapter = adapter

        adapter?.onDeskAppSetAdapterClickListener = object : OnDeskAppSetAdapterClickListener{
            override fun onAddApp() {
                val dialog = AppSelectDialog(context)
                dialog.show()
            }
            override fun onItemClick(position: Int, appInfo: AppInfo) {

            }
        }

    }








}