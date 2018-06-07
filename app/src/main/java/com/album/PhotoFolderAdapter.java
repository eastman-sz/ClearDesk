package com.album;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.common.base.CustomFontTextView;
import com.common.base.IBaseAdapter;
import com.common.base.ViewHolder;
import com.imageloader.UniversalImageLoadTool;
import com.sz.sk.clear.desk.R;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class PhotoFolderAdapter extends IBaseAdapter<AlbumInfo> {

	private String uint = null;
	
	public PhotoFolderAdapter(Context context, ArrayList<AlbumInfo> list) {
		super(context , list , R.layout.photo_folder_adapter_view);

		uint = "张";
	}

	@Override
	public void getConvertView(View convertView, List<AlbumInfo> list, int position) {
		ImageView image = ViewHolder.getView(convertView, R.id.imageView);
		CustomFontTextView text = ViewHolder.getView(convertView, R.id.info);
		CustomFontTextView num = ViewHolder.getView(convertView, R.id.num);


		AlbumInfo albumInfo = list.get(position);
		UniversalImageLoadTool.disPlay(albumInfo.getPath_file(), image, R.drawable.def_loading_square_image);
		text.setText(albumInfo.getName_album());
		num.setText("( "+albumInfo.getList().size()+ " " + uint + ")");
	}

}
