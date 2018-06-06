package com.application;

import android.app.Application;
import android.content.Context;

public class IApplication extends Application {

    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }


    public static Context getContext(){
        return context;
    }
}
