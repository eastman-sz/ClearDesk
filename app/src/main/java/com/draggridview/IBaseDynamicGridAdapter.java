package com.draggridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class IBaseDynamicGridAdapter<T> extends BaseDynamicGridAdapter<T> {

    protected Context context = null;
    protected List<T> list = null;
    private int layoutId = 0;

    public IBaseDynamicGridAdapter(Context context , List<T> list , int itemCount , int layoutId){
        super(context , list , itemCount);
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = LayoutInflater.from(context).inflate(layoutId , null);
        }
        getConvertView(convertView , list , position);
        return convertView;
    }

    public abstract void getConvertView(View convertView ,List<T> list , int position);
}
