package com.album

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.common.base.IBaseAdapter
import com.common.base.ViewHolder
import com.photo.third.UniversalImageLoadTool
import com.sz.sk.clear.desk.R
import com.utils.lib.ss.info.DeviceInfo

class AlbumShowAdapter : IBaseAdapter<String> {

    private var mWidth = 0

    constructor(context: Context , list: List<String>) : super(context , list , R.layout.album_show_adapter){
        mWidth = (context.resources.displayMetrics.widthPixels - DeviceInfo.dip2px(context , 13f))/4
    }

    override fun getConvertView(convertView: View, list: List<String>, position: Int) {
        val imageView = ViewHolder.getView<ImageView>(convertView , R.id.imageView)

        val params = imageView.layoutParams
        params.width = mWidth
        params.height = mWidth
        imageView.layoutParams = params

        val img = list[position]

        UniversalImageLoadTool.disPlay(img , imageView , R.drawable.def_loading_square_image)

    }

    override fun notifyDataSetChanged() {
        //计算一行显示几张图
        val size = list.size
        var numColumns = 4
        if (size > 20 && size <= 30){
            numColumns = 5
        } else if (size > 30 && size <= 40) {
            numColumns = 6
        } else if (size > 40){
            numColumns = 7
        }
        mWidth = (context.resources.displayMetrics.widthPixels - DeviceInfo.dip2px(context , (10f + numColumns - 1)))/numColumns

        super.notifyDataSetChanged()
    }

}