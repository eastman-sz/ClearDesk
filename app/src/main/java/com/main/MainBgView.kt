package com.main

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import com.base.BaseView
import com.common.base.IonPageChangeListener
import com.gallery.GalleryDbHelper
import com.gallery.GalleryPagerAdapter
import com.sz.sk.clear.desk.R
import com.util.BroadcastAction
import com.util.ILog
import kotlinx.android.synthetic.main.main_bg_view.view.*

class MainBgView : BaseView {

    val dataList = ArrayList<String>()
    val list = ArrayList<MainBgSubView>()
    var adapter : GalleryPagerAdapter<MainBgSubView> ?= null

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_bg_view , this)

        list.add(MainBgSubView(context))
        list.add(MainBgSubView(context))
        list.add(MainBgSubView(context))

        adapter = GalleryPagerAdapter(context , list)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : IonPageChangeListener(){
            override fun onPageSelected(index: Int) {
                if (dataList.isEmpty()){
                    return
                }
                ILog.e("pageIndex: $index  地址 ${dataList.get(index % dataList.size)}")
                list.get(index % 3).setImageResource(dataList.get(index % dataList.size))
            }
        })

        refreshGallery()

        startLoop()
    }

    internal val handler = Handler(Looper.getMainLooper()){
        when(it.what){
            0 ->{
                viewPager.setCurrentItem(viewPager.currentItem + 1 , true)

                startLoop()
            }
        }
        true
    }

    fun startLoop(){
        handler.sendEmptyMessageDelayed(0 , 1500)
    }

    fun refreshGallery(){
        dataList.clear()
        dataList.addAll(GalleryDbHelper.getAllGalleryPath())
    }

    override fun addBroadCastAction(): java.util.ArrayList<String> {
        val list = ArrayList<String>()
        list.add(BroadcastAction.GALLERY_IMAGE_REFRESH)
        return list
    }

    override fun onBroadCastReceive(context: Context?, action: String?, intent: Intent?) {
        when(action){
            BroadcastAction.GALLERY_IMAGE_REFRESH -> {
                refreshGallery()
            }

        }
    }

}