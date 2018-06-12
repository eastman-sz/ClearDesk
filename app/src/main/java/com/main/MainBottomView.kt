package com.main

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import com.appinfo.AppInfo
import com.base.BaseView
import com.sz.sk.clear.desk.R
import com.util.PkgUtil
import com.utils.lib.ss.common.PkgHelper
import kotlinx.android.synthetic.main.main_bottom_view.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainBottomView : BaseView {

    private val list = ArrayList<AppInfo>()
    private var adapter : BottmonAppAdapter ?= null

    private val TYPE_MSG = 10
    private val TYPE_TEL = 11
    private val TYPE_GALLERY = 12
    private val TYPE_CAMERA = 13
    private val TYPE_CONTACTS = 14

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_bottom_view , this)

        adapter = BottmonAppAdapter(context , list)
        gridView.adapter = adapter

        getDef()

        gridView.setOnItemClickListener { _, _, position, _ ->
            val appInfo = list[position]
            val packageName = appInfo.packageName
            val type = appInfo.type

            val openSuccess = PkgHelper.openAPKByPkgName(context , packageName)
            when(openSuccess){
                false ->{
                    when(type){
                        TYPE_MSG ->{
                        }
                        TYPE_TEL ->{
                            context.startActivity(Intent(Intent.ACTION_DIAL).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        }
                        TYPE_GALLERY ->{

                        }
                        TYPE_CAMERA ->{

                        }

                    }
                }
            }
        }
    }

    private fun getDef(){
        doAsync {
            val resultList = ArrayList<AppInfo>()
            //全部
            val allList = PkgUtil.getAllInstalledApps(context)
            //用来去重
            val typeList = ArrayList<Int>()

            //默认：电话，信息，相机，图片
            allList.forEach {
                val name = it.name
                val telEquals = name?.startsWith("电话")
                val msgEquals = name?.equals("信息")
                val msgEquals1 = name?.startsWith("短信")
                val cameraEquals1 = name?.startsWith("相机")
                val galleryEquals1 = name?.startsWith("相册")
                val contactsEquals1 = name?.startsWith("联系人")

                when(telEquals){
                    true ->{
                        if (!typeList.contains(TYPE_TEL)){
                            typeList.add(TYPE_TEL)

                            it.type = TYPE_TEL
                            resultList.add(it)
                        }

                    }
                    else ->{
                        when(msgEquals){
                            true ->{
                                if (!typeList.contains(TYPE_MSG)){
                                    typeList.add(TYPE_MSG)

                                    it.type = TYPE_MSG
                                    resultList.add(it)
                                }
                            }

                            else ->{
                                when(msgEquals1){
                                    true ->{
                                        if (!typeList.contains(TYPE_MSG)){
                                            typeList.add(TYPE_MSG)

                                            it.type = TYPE_MSG
                                            resultList.add(it)
                                        }
                                    }

                                    else ->{
                                        when(cameraEquals1){
                                            true ->{
                                                if (!typeList.contains(TYPE_CAMERA)){
                                                    typeList.add(TYPE_CAMERA)

                                                    it.type = TYPE_CAMERA
                                                    resultList.add(it)
                                                }
                                            }

                                            else ->{
                                                when(galleryEquals1){
                                                    true ->{
                                                        if (!typeList.contains(TYPE_GALLERY)){
                                                            typeList.add(TYPE_GALLERY)

                                                            it.type = TYPE_GALLERY
                                                            resultList.add(it)
                                                        }
                                                    }

                                                    else ->{
                                                        when(contactsEquals1){
                                                            true ->{
                                                                if (!typeList.contains(TYPE_CONTACTS)){
                                                                    typeList.add(TYPE_CONTACTS)

                                                                    it.type = TYPE_CONTACTS
                                                                    resultList.add(it)
                                                                }
                                                            }
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            uiThread {
                list.clear()
                list.addAll(resultList)

                adapter?.notifyDataSetChanged()
            }
        }
    }


}