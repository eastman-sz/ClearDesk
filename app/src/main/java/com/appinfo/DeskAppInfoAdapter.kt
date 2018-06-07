package com.appinfo

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.common.base.CustomFontTextView
import com.common.base.ViewHolder
import com.draggridview.IBaseDynamicGridAdapter
import com.sz.sk.clear.desk.R

class DeskAppInfoAdapter : IBaseDynamicGridAdapter<AppInfo>{

    private var width = 0

    constructor(context: Context , list: List<AppInfo>) : super(context , list , 4 , R.layout.appinfo_adapter){
        width = context.resources.displayMetrics.widthPixels / 4
    }

    override fun getConvertView(convertView: View?, list: List<AppInfo>, position: Int) {
        val bgLayout = ViewHolder.getView<LinearLayout>(convertView, R.id.playout)

        val params = bgLayout.layoutParams
        params.width = width
        bgLayout.layoutParams = params

        val iconImageView = ViewHolder.getView<ImageView>(convertView, R.id.iconImageView)
        val nameTextView = ViewHolder.getView<CustomFontTextView>(convertView, R.id.nameTextView)

        val localAppInfo = getItem(position)
        val drawable = localAppInfo.drawable
        val name = localAppInfo.name

        iconImageView.setImageDrawable(drawable)
        nameTextView.text = name
    }

}