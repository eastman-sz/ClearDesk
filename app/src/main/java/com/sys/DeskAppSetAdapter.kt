package com.sys

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

class DeskAppSetAdapter : IBaseAdapter<AppInfo> {

    private var w : Int = 0
    private var h : Int = 0
    private var w1 : Int = 0

    var onDeskAppSetAdapterClickListener : OnDeskAppSetAdapterClickListener ?= null

    constructor(context: Context , list: List<AppInfo>) : super(context , list , R.layout.desk_app_set_adapter){
        w = (context.resources.displayMetrics.widthPixels - DeviceInfo.dip2px(context , 38f)) /5
        h = w + DeviceInfo.dip2px(context , 30f)
        w1 = w - DeviceInfo.dip2px(context , 15f)
    }

    override fun getConvertView(convertView: View?, list: List<AppInfo>, position: Int) {
        val topLayout = ViewHolder.getView<RelativeLayout>(convertView , R.id.topLayout)
        val appInfoLayout = ViewHolder.getView<RelativeLayout>(convertView , R.id.appInfoLayout)
        val iconImageView = ViewHolder.getView<ImageView>(convertView , R.id.iconImageView)
        val nameTextView = ViewHolder.getView<CustomFontTextView>(convertView , R.id.nameTextView)
        val addIconImageView = ViewHolder.getView<ImageView>(convertView , R.id.addIconImageView)

        val params = topLayout.layoutParams
        params?.width = w
        params?.height = h
        topLayout.layoutParams = params

        val params1 = iconImageView.layoutParams
        params1?.width = w1
        params1?.height = w1
        iconImageView.layoutParams = params1


        val appInfo = list[position]
        val drawable = appInfo.drawable
        val name = appInfo.name
        val type = appInfo.type

        iconImageView.setImageDrawable(drawable)
        nameTextView.text = name

        appInfoLayout.visibility = if (type == 0 ) View.VISIBLE else View.GONE
        addIconImageView.visibility = if (type > 0 ) View.VISIBLE else View.GONE

        if (type == 0){
            nameTextView.text = name
            iconImageView.setImageDrawable(drawable)


        }

        appInfoLayout.setOnClickListener {
            onDeskAppSetAdapterClickListener?.onItemClick(position , appInfo)
        }

        addIconImageView.setOnClickListener {
            onDeskAppSetAdapterClickListener?.onAddApp()
        }

    }

}