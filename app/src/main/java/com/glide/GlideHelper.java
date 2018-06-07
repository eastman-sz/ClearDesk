package com.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sz.sk.clear.desk.R;
import com.utils.lib.ss.common.CheckHelper;
/**
 * http://blog.csdn.net/guolin_blog/article/details/53759439
 * http://www.jianshu.com/p/89567c934008
 * Created by E on 2017/12/6.
 */
public class GlideHelper {

    public static void display(ImageView imageView , String url){
        Glide.with(imageView.getContext()).load(comfirmHttpUrl(url))
                .crossFade()
                .animate(R.anim.anim_enter)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void displayDef(ImageView imageView , String url , int defIcon){
        Glide.with(imageView.getContext()).load(comfirmHttpUrl(url))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .error(defIcon)
                .fallback(defIcon)
                .placeholder(defIcon)
                .into(imageView);
    }

    public static void display(ImageView imageView , int url){
        Glide.with(imageView.getContext()).load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void playGif(Context context , ImageView imageView , int resid){
        Glide.with(context).load(resid).asGif().centerCrop().into(imageView);
    }


    public static void display(Context context , ImageView imageView , int url){
        Glide.with(context).load(url).into(imageView);
    }

    public static void display(ImageView imageView , String url , int corner){
        Glide.with(imageView.getContext()).load(comfirmHttpUrl(url))
                .bitmapTransform(new GlideRoundTransform(imageView.getContext() , corner))
                .into(imageView);
    }

    public static void display(ImageView imageView , int url , int corner){
        Glide.with(imageView.getContext()).load(url)
                .bitmapTransform(new GlideRoundTransform(imageView.getContext() , corner))
                .into(imageView);
    }

    public static void displayCircle(ImageView imageView , String url){
        Glide.with(imageView.getContext()).load(comfirmHttpUrl(url))
                .bitmapTransform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    public static void displayCircle(ImageView imageView , int url){
        Glide.with(imageView.getContext()).load(url)
                .bitmapTransform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    public static void displayCircle(ImageView imageView , String url , int defIcon){
        Glide.with(imageView.getContext()).load(comfirmHttpUrl(url))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .bitmapTransform(new GlideCircleTransform(imageView.getContext()))
                .error(defIcon)
                .fallback(defIcon)
                .into(imageView);
    }

    /**
     * 检查URL是否是HTTP开头，如果没有，则加上HTTP头，有则不加。
     * @param url 原始的URL
     * @return 完整的URL
     */
    private static String comfirmHttpUrl(String url){
        if (CheckHelper.isNullOrEmpty(url)) {
            return url;
        }
        if (url.startsWith("/storage/sdcard")
                ||url.startsWith("/storage/")  ) {
            return url = "file://" + url;
        }
        if (!url.contains("http")
                && !url.startsWith("file://")
                && !url.startsWith("drawable://")
                ) {
//            url = UrlPath.URL_FILES + url;
        }
        return url;
    }

}
