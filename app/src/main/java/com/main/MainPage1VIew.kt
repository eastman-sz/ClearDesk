package com.main

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import com.appinfo.AppInfo
import com.appinfo.DeskAppInfoAdapter
import com.appinfo.DeskAppInfoDbHelper
import com.base.BaseView
import com.draggridview.DynamicGridView
import com.sys.AppSettingActivity
import com.sz.sk.clear.desk.R
import com.util.BroadcastAction
import com.util.ILog
import com.util.PkgUtil
import com.utils.lib.ss.common.PkgHelper
import kotlinx.android.synthetic.main.main_page_a_view.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPage1VIew : BaseView {

    val list = ArrayList<AppInfo>()
    var adapter : DeskAppInfoAdapter ?= null

    private var loaded = false

    private var pageIndex : Int = 0

    constructor(context: Context , pageIndex : Int) : super(context){
        this.pageIndex = pageIndex
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_page_a_view , this)

        addDef()

        adapter = DeskAppInfoAdapter(context , list)
        dynamicGridView.adapter = adapter

        adapter?.notifyDataSetChanged()

        dynamicGridView.setOnDragListener(object : DynamicGridView.OnDragListener{
            override fun onDragPositionsChanged(oldPosition: Int, newPosition: Int) {

            }

            override fun onDragStarted(position: Int) {
                ILog.e("onDragStarted: $position")
            }
        })

        dynamicGridView.setOnItemLongClickListener { _, _, position, _ ->
            dynamicGridView.startEditMode()
            true
        }

        dynamicGridView.setOnItemClickListener { _, _, position, _ ->
            val localAppInfo = list[position]
            val pkgName = localAppInfo.packageName
            when(pkgName){
                context.packageName -> {


                    context.startActivity(Intent(context , AppSettingActivity::class.java))
                }

                else ->{
                    PkgHelper.openAPKByPkgName(context , pkgName)
                }
            }

        }

    }

    override fun freshByHand(forceUpdate: Boolean) {
        if (loaded){
            return
        }
        loaded = true

        onPageAppRefresh()
    }

    private fun onPageAppRefresh(){
        doAsync {
            //当前页面
            val pageApps = DeskAppInfoDbHelper.getDeskApps(pageIndex)
            val isEmpty = pageApps.isEmpty()
            when(isEmpty){
                false -> {
                    //全部非系统应用
                    val allApps = PkgUtil.getInstalledApps(context)
                    //pkgName mapping
                    val hashMap = HashMap<String? , AppInfo>()

                    allApps.forEach {
                        hashMap.put(it.packageName , it)
                    }

                    pageApps.forEach {
                        if (hashMap.containsKey(it.packageName)){
                            it.drawable = hashMap[it.packageName]?.drawable
                        }
                    }
                }
            }

            uiThread {
                list.clear()
                list.addAll(0 , pageApps)
                addDef()

                adapter?.set(list)

                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun addDef(){
        if (0 == pageIndex){
            val appInfo = AppInfo()
            appInfo.packageName = context.packageName
            appInfo.name = "应用设置"
            appInfo.drawable = resources.getDrawable(R.drawable.setting)

            list.add(appInfo)
        }
    }

    override fun addBroadCastAction(): java.util.ArrayList<String> {
        val list = ArrayList<String>()
        list.add(BroadcastAction.PAGE_APP_REFRESH)
        return list
    }

    override fun onBroadCastReceive(context: Context?, action: String?, intent: Intent?) {
        when(action){
            BroadcastAction.PAGE_APP_REFRESH ->{
                onPageAppRefresh()
            }
        }
    }

}