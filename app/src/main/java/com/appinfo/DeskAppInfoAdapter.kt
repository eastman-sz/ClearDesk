package com.appinfo

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.common.base.CustomFontTextView
import com.common.base.ViewHolder
import com.draggridview.IBaseDynamicGridAdapter
import com.sz.sk.clear.desk.R
import com.utils.lib.ss.info.DeviceInfo

class DeskAppInfoAdapter : IBaseDynamicGridAdapter<AppInfo>{

    private var w : Int = 0
    private var h : Int = 0
    private var w1 : Int = 0

    constructor(context: Context , list: List<AppInfo>) : super(context , list , 5 , R.layout.appinfo_adapter){
        w = (context.resources.displayMetrics.widthPixels - DeviceInfo.dip2px(context , 38f)) /5
        h = w + DeviceInfo.dip2px(context , 40f)
        w1 = w - DeviceInfo.dip2px(context , 15f)
    }

    override fun getConvertView(convertView: View?, list: List<AppInfo>, position: Int) {
        val bgLayout = ViewHolder.getView<RelativeLayout>(convertView, R.id.playout)

        val params = bgLayout.layoutParams
        params.width = w
        params.height = h
        bgLayout.layoutParams = params

        val iconImageView = ViewHolder.getView<ImageView>(convertView, R.id.iconImageView)
        val nameTextView = ViewHolder.getView<CustomFontTextView>(convertView, R.id.nameTextView)

        val params1 = iconImageView.layoutParams
        params1?.width = w1
        params1?.height = w1
        iconImageView.layoutParams = params1

        val localAppInfo = getItem(position)
        val drawable = localAppInfo.drawable
        val name = localAppInfo.name

        iconImageView.setImageDrawable(drawable)
        nameTextView.text = name
    }

}