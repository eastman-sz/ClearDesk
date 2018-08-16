package com.album

import android.os.Bundle
import android.view.View
import com.common.base.BaseAppCompactActivitiy
import com.common.base.CommonTitleView
import com.gallery.GalleryDbHelper
import com.photo.album.ImgHelper
import com.photo.album.OnImgSelectResultListener
import com.photo.album.OnPhotoPreviewListener
import com.sz.sk.clear.desk.R
import com.util.BroadcastAction
import com.util.BroadcastUtil
import com.util.PrefHelper
import kotlinx.android.synthetic.main.activity_album_set.*
import java.util.ArrayList
/**
 * 背景滚动图片。
 * @author E
 */
class AlbumSetActivity : BaseAppCompactActivitiy() {

    private val list = ArrayList<String>()
    private var adapter : AlbumShowAdapter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_set)

        initActivitys()
    }

    override fun initTitle() {
        commonTitleView.setCenterTitleText("画册设置")
        commonTitleView.setOnTitleClickListener(object : CommonTitleView.OnTitleClickListener() {
            override fun onLeftBtnClick() {
                finish()
            }
        })
    }

    override fun initViews() {
        switchBtn.isChecked = PrefHelper.isGalleryEnabled()
        albumLayout.visibility = if (PrefHelper.isGalleryEnabled()) View.VISIBLE else View.GONE

        switchBtn.setOnCheckedChangeListener { _, isChecked ->
            albumLayout.visibility = if (isChecked) View.VISIBLE else View.GONE

            PrefHelper.setGalleryEnabled(isChecked)

            //广播刷新
            BroadcastUtil.sendBroadcast(BroadcastAction.GALLERY_IMAGE_REFRESH)
        }

        adapter = AlbumShowAdapter(context , list)
        gridViewExtend.adapter = adapter

        freshAlbums()

        gridViewExtend.setOnItemClickListener { _, _, position, _ ->
            when(position){
                0 ->{
                    ImgHelper.selectImg(context , 100 , object : OnImgSelectResultListener {
                        override fun onResult(imgList: List<String>) {
                            GalleryDbHelper.save(imgList)

                            freshAlbums()

                        }
                    })
                }

                else ->{

                    val imgList = list.filter { !it.startsWith("drawable")}

                    ImgHelper.imgPreview(context , imgList , position - 1 , true , object : OnPhotoPreviewListener{
                        override fun onImgDel(position: Int) {
                            runOnUiThread {
                                delImg(position + 1)
                            }

                        }
                    })

                }
            }
        }
    }

    private fun freshAlbums(){
        list.clear()
        list.add("drawable://${R.drawable.add_icon}")
        list.addAll(GalleryDbHelper.getAllGalleryPath())

        //计算一行显示几张图
        val size = list.size
        if (size <= 20){
            gridViewExtend.numColumns = 4
        }else if (size > 20 && size <= 30){
            gridViewExtend.numColumns = 5
        }else if (size > 20 && size <= 40){
            gridViewExtend.numColumns = 6
        } else{
            gridViewExtend.numColumns = 7
        }

        adapter?.notifyDataSetChanged()
    }

    private fun delImg(position : Int){
        val img = list[position]
        list.removeAt(position)
        //rm form db
        GalleryDbHelper.delete(img)

        //计算一行显示几张图
        val size = list.size
        if (size <= 20){
            gridViewExtend.numColumns = 4
        }else if (size > 20 && size <= 30){
            gridViewExtend.numColumns = 5
        }else if (size > 30 && size <= 40){
            gridViewExtend.numColumns = 6
        } else{
            gridViewExtend.numColumns = 7
        }

        adapter?.notifyDataSetChanged()

        //广播刷新
        BroadcastUtil.sendBroadcast(BroadcastAction.GALLERY_IMAGE_REFRESH)
    }


}
