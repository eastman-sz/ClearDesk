package com.album;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E on 2017/4/26.
 */
public class AlbumUtils {

    private static AlbumUtils pageEnter = null;

    public static AlbumUtils getInstance(){
        if (null == pageEnter) {
            pageEnter = new AlbumUtils();
        }
        return pageEnter;
    }

    /**
     * 选择图片。
     * @param context 上下文环境
     * @param maxNum 可选最大数量
     * @param photoSelectedListener 选择的回调
     * @return PhotoFolderDialog
     */
    public PhotoFolderDialog toSelectPictures(Context context, int maxNum,
                                              PhotoFolderDialog.PhotoSelectedListener photoSelectedListener) {
        PhotoFolderDialog dialog = new PhotoFolderDialog(context);
        dialog.show();
        dialog.setMaxNum(maxNum);
        dialog.setPhotoSelectedListener(photoSelectedListener);
        return dialog;
    }

    /**
     * 预览图片。
     * @param context 上下文环境
     * @param image 图片地址
     * @param canBeDeleted 是否能被删除
     * @return PicPreviewDialog
     */
    public PicPreviewDialog toPreImge(Context context, String image,
                                      boolean canBeDeleted) {
        ArrayList<String> imageList = new ArrayList<String>();
        imageList.add(image);
        return toPreImge(context, imageList, 0, canBeDeleted);
    }

    /**
     * 预览图片。
     * @param context 上下文环境
     * @param image 图片地址
     * @param canBeDeleted 是否能被删除
     * @param picListener 回调 ，来监听图片被删除，保存等
     * @return PicPreviewDialog
     */
    public PicPreviewDialog toPreImge(Context context, String image,
                                      boolean canBeDeleted, PicPreviewDialog.PicListener picListener) {
        ArrayList<String> imageList = new ArrayList<String>();
        imageList.add(image);
        return toPreImge(context, imageList, 0, canBeDeleted, picListener);
    }

    /**
     * 预览图片。
     * @param context 上下文环境
     * @param imageList 图片集合
     * @param position 当前的Index
     * @param canBeDeleted 是否能被删除
     * @return PicPreviewDialog
     */
    public PicPreviewDialog toPreImge(Context context,
                                      List<String> imageList, int position, boolean canBeDeleted) {
        PicPreviewDialog dialog = new PicPreviewDialog(context);
        dialog.show();
        dialog.setPicList(imageList, position, canBeDeleted);
        return dialog;
    }

    /**
     * 预览图片。
     * @param context 上下文环境
     * @param imageList 图片集合
     * @param position 当前的Index
     * @param canBeDeleted 是否能被删除
     * @param picListener 回调 ，来监听图片被删除，保存等
     * @return PicPreviewDialog
     */
    public PicPreviewDialog toPreImge(Context context,
                                      List<String> imageList, int position, boolean canBeDeleted,
                                      PicPreviewDialog.PicListener picListener) {
        PicPreviewDialog dialog = new PicPreviewDialog(context);
        dialog.show();
        dialog.setPicList(imageList, position, canBeDeleted);
        dialog.setPicListener(picListener);
        return dialog;
    }

}
