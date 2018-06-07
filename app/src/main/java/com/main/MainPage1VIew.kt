package com.main

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import com.appinfo.AppInfo
import com.appinfo.DeskAppInfoAdapter
import com.appinfo.LocalAppInfoAdapter
import com.base.BaseView
import com.draggridview.DynamicGridView
import com.sys.AppSetingActivity
import com.sz.sk.clear.desk.R
import com.utils.lib.ss.info.LocalAppInfo
import kotlinx.android.synthetic.main.main_page_a_view.view.*

class MainPage1VIew : BaseView {

    val list = ArrayList<AppInfo>()
    var adapter : DeskAppInfoAdapter ?= null

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_page_a_view , this)

        val appInfo = AppInfo()
        appInfo.packageName = context.packageName
        appInfo.name = "应用设置"
        appInfo.drawable = resources.getDrawable(R.drawable.setting)

        list.add(appInfo)

        adapter = DeskAppInfoAdapter(context , list)
        dynamicGridView.adapter = adapter

        adapter?.notifyDataSetChanged()

        dynamicGridView.setOnDragListener(object : DynamicGridView.OnDragListener{
            override fun onDragPositionsChanged(oldPosition: Int, newPosition: Int) {

            }

            override fun onDragStarted(position: Int) {

            }
        })

        dynamicGridView.setOnItemLongClickListener { parent, view, position, id ->
            dynamicGridView.startEditMode()
            true
        }

        dynamicGridView.setOnItemClickListener { parent, view, position, id ->
            val localAppInfo = list.get(position)
            val pkgName = localAppInfo.packageName
            when(pkgName){
                context.packageName -> {


                    context.startActivity(Intent(context , AppSetingActivity::class.java))
                }
            }



        }

    }

}