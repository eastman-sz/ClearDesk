package com.main

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import com.base.BaseView
import com.glide.GlideHelper
import com.sz.sk.clear.desk.R
import com.util.BroadcastAction
import com.util.PrefHelper
import kotlinx.android.synthetic.main.main_bg_sub_view.view.*

class MainBgSubView : BaseView {

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs : AttributeSet) : super(context , attrs){
        init()
    }

    override fun initViews() {
        View.inflate(context , R.layout.main_bg_sub_view , this)
    }

    fun setImageResource(imgPath : String){
        GlideHelper.display(imageView , imgPath)
    }

    override fun addBroadCastAction(): java.util.ArrayList<String> {
        val list = ArrayList<String>()
        list.add(BroadcastAction.GALLERY_IMAGE_REFRESH)
        return list
    }

    override fun onBroadCastReceive(context: Context?, action: String?, intent: Intent?) {
        when(action){
            BroadcastAction.GALLERY_IMAGE_REFRESH -> {
                if (!PrefHelper.isGalleryEnabled()){
                    imageView.setImageResource(R.drawable.ic_launcher_background)
                }
            }
        }
    }

}