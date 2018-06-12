package com.sys

import android.content.Context
import android.content.Intent
import android.view.View
import com.appinfo.AppInfo
import com.appinfo.DeskAppInfoDbHelper
import com.base.BaseView
import com.sz.sk.clear.desk.R
import com.util.BroadcastAction
import com.util.PkgUtil
import kotlinx.android.synthetic.main.desk_app_view.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DeskAppView : BaseView {

    private var pageIndex : Int = 0

    private var adapter : DeskAppSetAdapter ?= null
    private val list = ArrayList<AppInfo>()

    private var loaded = false

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
                dialog.setPageIndex(pageIndex)
            }
            override fun onItemClick(position: Int, appInfo: AppInfo) {

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

                adapter?.notifyDataSetChanged()
            }
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