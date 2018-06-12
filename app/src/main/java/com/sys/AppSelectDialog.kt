package com.sys

import android.content.Context
import android.os.Bundle
import android.view.View
import com.appinfo.AppInfo
import com.appinfo.DeskAppInfoDbHelper
import com.base.IBaseDialog
import com.common.base.CommonTitleView
import com.sz.sk.clear.desk.R
import com.util.BroadcastAction
import com.util.BroadcastUtil
import com.util.ILog
import com.util.PkgUtil
import kotlinx.android.synthetic.main.app_select_dialog.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AppSelectDialog : IBaseDialog {

    private val list = ArrayList<AppInfo>()
    private var adapter : AppSelectDialogAdapter ?= null

    private var pageIndex : Int = 0

    constructor(context: Context) : super(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_select_dialog)

        init()
    }

    override fun initTitle() {
        commonTitleView.setCenterTitleText("应用选择")
        commonTitleView.setOnTitleClickListener(object : CommonTitleView.OnTitleClickListener(){
            override fun onLeftBtnClick() {
                dismiss()
            }
            override fun onRightBtnClick() {
                //确定
                doAsync {
                    list.forEach {
                        if (it.selected){
                            DeskAppInfoDbHelper.save(it.name , it.packageName , pageIndex)
                        }
                    }
                    uiThread {
                        dismiss()

                        BroadcastUtil.sendBroadcast(BroadcastAction.PAGE_APP_REFRESH)
                    }
                }

            }
        })
    }

    override fun initViews() {
        adapter = AppSelectDialogAdapter(context , list)
        gridView.adapter = adapter

        adapter?.onDeskAppSetAdapterClickListener = object : OnDeskAppSetAdapterClickListener{
            override fun onAddApp() {
            }
            override fun onItemClick(position: Int, appInfo: AppInfo) {
                ILog.e("--------onItemClick---------: ${appInfo.name}")

                appInfo.selected = !appInfo.selected

                adapter?.notifyDataSetChanged(gridView , position)

                cal()
            }
        }
    }

    fun setPageIndex(pageIndex : Int){
        this.pageIndex = pageIndex

        doAsync {
            //当前页面的
            val pageList = DeskAppInfoDbHelper.getDeskApps(pageIndex)
            //所有的
            val resultList = PkgUtil.getInstalledApps(context)
            //唯一
            val pkgNameList = ArrayList<String?>()
            pageList.forEach {
                pkgNameList.add(it.packageName)
            }
            resultList.forEach {
                val contains = pkgNameList.contains(it.packageName)
                when(contains){
                    true -> it.selected = true
                }
            }

            uiThread {
                list.addAll(resultList)

                adapter?.notifyDataSetChanged()

                cal()
            }
        }
    }

    fun cal(){
        doAsync {
            var count = 0

            list.forEach {
                if (it.selected){
                    count ++
                }
            }

            uiThread {
                commonTitleView.setRightBtnText("确定($count)")
                commonTitleView.setRightBtnVisibility(if (0 == count) View.GONE else View.VISIBLE)
            }
        }
    }

}