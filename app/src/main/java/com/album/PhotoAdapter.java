package com.album;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.common.base.IBaseAdapter;
import com.common.base.ViewHolder;
import com.imageloader.UniversalImageLoadTool;
import com.sz.sk.clear.desk.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class PhotoAdapter extends IBaseAdapter<PhotoInfo> {

	private int width = 0;
	
	private boolean needShowSelector = true;

	public PhotoAdapter(Context context, ArrayList<PhotoInfo> photoInfoList) {
		super(context , photoInfoList , R.layout.photo_adapter_view);

		width = context.getResources().getDisplayMetrics().widthPixels/3;
	}

	@Override
	public void getConvertView(View convertView, List<PhotoInfo> list, final int position) {
        ImageView image = ViewHolder.getView(convertView, R.id.imageView);
        ImageView selectImage = ViewHolder.getView(convertView, R.id.selectImage);

        PhotoInfo photoInfo = list.get(position);
        if (needShowSelector) {
            boolean selected = photoInfo.isChoose();
            selectImage.setVisibility(View.VISIBLE);
            selectImage.setImageResource(selected ? R.drawable.gou_selected : R.drawable.gou_normal);
        }else {
            selectImage.setVisibility(View.GONE);
        }

        LayoutParams layoutParams = image.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = width;
        image.setLayoutParams(layoutParams);

        UniversalImageLoadTool.disPlay(photoInfo.getPath_file(), image, R.drawable.def_loading_square_image);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != photoSelectListener) {
                    photoSelectListener.onPhotoSelectClick(position);
                }
            }
        });
	}

	public void setNeedShowSelector(boolean needShowSelector) {
		this.needShowSelector = needShowSelector;
	}
	
	private PhotoSelectListener photoSelectListener = null;
	
	static public class PhotoSelectListener {
		public void onPhotoSelectClick(int position){};
	}

	public void setPhotoSelectListener(PhotoSelectListener photoSelectListener) {
		this.photoSelectListener = photoSelectListener;
	}

}
