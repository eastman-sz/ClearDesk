package com.appinfo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.base.CustomFontTextView;
import com.common.base.ViewHolder;
import com.draggridview.BaseDynamicGridAdapter;
import com.sz.sk.clear.desk.R;
import com.utils.lib.ss.info.LocalAppInfo;
import java.util.List;

public class LocalAppInfoAdapter extends BaseDynamicGridAdapter<LocalAppInfo> {

    private List<LocalAppInfo> list = null;

    private int width = 0;

    public LocalAppInfoAdapter(Context context, List<LocalAppInfo> items) {
        super(context, items, 4);
        this.list = items;

        width = context.getResources().getDisplayMetrics().widthPixels/4;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.appinfo_adapter, null);
        }

        if (list.isEmpty()){
            return convertView;
        }

        LinearLayout bgLayout = ViewHolder.getView(convertView , R.id.playout);

        ViewGroup.LayoutParams params = bgLayout.getLayoutParams();
        params.width = width;
        bgLayout.setLayoutParams(params);


        ImageView iconImageView = ViewHolder.getView(convertView , R.id.iconImageView);
        CustomFontTextView nameTextView = ViewHolder.getView(convertView , R.id.nameTextView);

        LocalAppInfo localAppInfo = getItem(position);
        Drawable drawable = localAppInfo.getIcon();//
        String name = localAppInfo.getLabelName();

        iconImageView.setImageDrawable(drawable);
        nameTextView.setText(name);

        return convertView;
    }

}
