package com.main

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.appinfo.AppInfo
import com.common.base.CustomFontTextView
import com.common.base.IBaseAdapter
import com.common.base.ViewHolder
import com.sz.sk.clear.desk.R
import com.utils.lib.ss.info.DeviceInfo

class BottmonAppAdapter : IBaseAdapter<AppInfo> {

    var w = 0
    var h = 0
    var w_img = 0

    constructor(context: Context , list: List<AppInfo>) : super(context , list , R.layout.bottom_app_adapter){
        w = (context.resources.displayMetrics.widthPixels - DeviceInfo.dip2px(context , 36f))/5
        h = DeviceInfo.dip2px(context , 80f)

        w_img = DeviceInfo.dip2px(context , 45f)
    }

    override fun getConvertView(convertView: View, list: List<AppInfo>, position: Int) {
        val topLayout = ViewHolder.getView<RelativeLayout>(convertView , R.id.topLayout)
        val iconImageView = ViewHolder.getView<ImageView>(convertView , R.id.iconImageView)
        val nameTextView = ViewHolder.getView<CustomFontTextView>(convertView , R.id.nameTextView)

        val layoutParams = topLayout.layoutParams
        layoutParams.width = w
        layoutParams.height = h
        topLayout.layoutParams = layoutParams

        val layoutParams1 = iconImageView.layoutParams
        layoutParams1.width = w_img
        layoutParams1.height = w_img
        iconImageView.layoutParams = layoutParams1


        val appInfo = list[position]
        val drawable = appInfo.drawable
        val name = appInfo.name

        iconImageView.setImageDrawable(drawable)
        nameTextView.text = name
    }

}