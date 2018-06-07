package com.application;

import android.app.Application;
import android.content.Context;

import com.imageloader.UniversalImageLoadTool;

public class IApplication extends Application {

    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if (null == context){
            context = this;

            //图片加载
            UniversalImageLoadTool.init(context);
        }
    }

    public static Context getContext(){
        return context;
    }
}
