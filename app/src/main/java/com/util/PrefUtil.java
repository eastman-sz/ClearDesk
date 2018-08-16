package com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.application.IApplication;
/**
 * Created by E on 2017/4/26.
 */
public class PrefUtil {

    private SharedPreferences pref = null;
    public SharedPreferences.Editor editor = null;
    private static final String name = "zlkzbpref";
    private static PrefUtil instance = null;

    /**
     * 构造函数
     */
    private PrefUtil() {
        if (Build.VERSION.SDK_INT >= 11) {
            // android3.0以上支持
            if (null == pref) {
                pref = IApplication.getContext().getSharedPreferences(name,
                        Context.MODE_MULTI_PROCESS);
            }
        } else {
            if (null == pref) {
                pref = IApplication.getContext().getSharedPreferences(name,
                        Context.MODE_PRIVATE);
            }
        }
        if (null == editor) {
            editor = pref.edit();
        }
    }

    public static PrefUtil instance() {
        if (null == instance) {
            instance = new PrefUtil();
        }
        return instance;
    }

    public void setPref(String key, String value) {
        editor.putString(key, value).commit();
    }

    public String getPref(String key, String defValue) {
        return pref.getString(key, defValue);
    }

    public void setBooleanPref(String key, boolean value) {
        editor.putBoolean(key, value);
    }

    public boolean getBooleanPref(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public String getPref(String key) {
        return pref.getString(key, "");
    }

    public void setIntPref(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public int getIntPref(String key) {
        return pref.getInt(key, 0);
    }

    public int getIntPref(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public void setLongPref(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public long getLongPref(String key) {
        return pref.getLong(key, 0);
    }

    public void setFloatPref(String key, float value) {
        editor.putFloat(key, value).commit();
    }

    public Float getFloatPref(String key) {
        return pref.getFloat(key, 0);
    }

    public Float getFloatPref(String key , float def) {
        return pref.getFloat(key, def);
    }

    public void removePref(String key){
        editor.remove(key);
        editor.commit();
    }



}
