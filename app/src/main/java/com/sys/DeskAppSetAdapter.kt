package com.sys

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.appinfo.AppInfo
import com.common.base.CustomFontTextView
import com.common.base.IBaseAdapter
import com.common.base.ViewHolder
import com.sz.sk.clear.desk.R

class DeskAppSetAdapter : IBaseAdapter<AppInfo> {

    constructor(context: Context , list: List<AppInfo>) : super(context , list , R.layout.desk_app_set_adapter)

    override fun getConvertView(convertView: View?, list: List<AppInfo>, position: Int) {
        val iconImageView = ViewHolder.getView<ImageView>(convertView , R.id.iconImageView)
        val nameTextView = ViewHolder.getView<CustomFontTextView>(convertView , R.id.nameTextView)

        val appInfo = list[position]
        val drawable = appInfo.drawable
        val name = appInfo.name

        iconImageView.setImageDrawable(drawable)
        nameTextView.text = name

    }

}