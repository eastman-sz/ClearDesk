package com.main

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import com.appinfo.LocalAppInfoAdapter
import com.base.BaseView
import com.draggridview.DynamicGridView
import com.sys.AppSetingActivity
import com.sz.sk.clear.desk.R
import com.utils.lib.ss.info.LocalAppInfo
import kotlinx.android.synthetic.main.main_page_a_view.view.*

class MainPage1VIew : BaseView {

    val list = ArrayList<LocalAppInfo>()
    var adapter : LocalAppInfoAdapter ?= null

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_page_a_view , this)

        val appInfo = LocalAppInfo(context.packageName , "应用设置" , "v1.0.0" , resources.getDrawable(R.drawable.setting))

        list.add(appInfo)

        adapter = LocalAppInfoAdapter(context , list)
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
            val pkgName = localAppInfo.pkgName
            when(pkgName){
                context.packageName -> {


                    context.startActivity(Intent(context , AppSetingActivity::class.java))
                }
            }



        }

    }

}