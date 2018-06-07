package com.album;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.common.base.CommonTitleView;
import com.common.dialog.BaseDialog;
import com.imageloader.IwyScrollListener;
import com.sz.sk.clear.desk.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PhotoFolderDialog extends BaseDialog {

	private PhotoFolderAdapter adapter = null;
	private ArrayList<AlbumInfo> albumInfoList = new ArrayList<AlbumInfo>();
	
	private int maxNum = 8;
	
	public PhotoFolderDialog(Context context) {
		super(context , R.style.photo_dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_folder_dialog_view);
		initTitle();
		initViews();
		initData();
	}
	
	protected void initTitle(){
		 CommonTitleView commonTitleView = (CommonTitleView) findViewById(R.id.commom_title_view);
		 commonTitleView.setLeftBtnText("返回");
		 commonTitleView.setCenterTitleText("相册");
		 commonTitleView.setRightBtnVisibility(View.GONE);
		 commonTitleView.setOnTitleClickListener(new CommonTitleView.OnTitleClickListener() {
			@Override
			public void onLeftBtnClick() {
				dismiss();
			}
		});
	}
	
	protected void initViews(){
		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new PhotoFolderAdapter(context, albumInfoList);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(new IwyScrollListener());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				
				int size = albumInfoList.size();
				if (0 == size) {
					return;
				}
				if (position >= size) {
					position = size -1;
				}
				AlbumInfo albumInfo = albumInfoList.get(position);
				ArrayList<PhotoInfo> photoInfoList = albumInfo.getList();
				
				PhotoDialog photoDialog = new PhotoDialog(context);
				photoDialog.show();
				photoDialog.setTitle(albumInfo.getName_album());
				photoDialog.setPhotoList(photoInfoList);
				photoDialog.setOnPhotoSelectClickListener(new PhotoSelectListener());
				photoDialog.setMaxNum(maxNum);
			}
		});
	}
	
	private void initData(){
		new ImageAsyncTask().execute();
	}
	
	@SuppressWarnings("unchecked")
    Handler handler = new Handler(Looper.getMainLooper()){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				ArrayList<AlbumInfo> tempAlbumList = (ArrayList<AlbumInfo>) msg.obj;
				albumInfoList.clear();
				albumInfoList.addAll(tempAlbumList);
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}
	};
	
	ArrayList<String> selectedList = new ArrayList<String>();
	
	class PhotoSelectListener implements PhotoDialog.OnPhotoSelectClickListener {
		@Override
		public void onPhotoSelectClickListener(ArrayList<PhotoInfo> list) {
			selectedList.clear();
			
			for (PhotoInfo photoInfo : list) {
				boolean isChoosen = photoInfo.isChoose();
				if (isChoosen) {
					selectedList.add(photoInfo.getPath_absolute());
				}
			}
			dismiss();
			if (null != photoSelectedListener) {
				photoSelectedListener.onPhotoSelected(selectedList);
			}
		}
	}
	
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public void setPhotoSelectedListener(PhotoSelectedListener photoSelectedListener) {
		this.photoSelectedListener = photoSelectedListener;
	}

	public PhotoSelectedListener photoSelectedListener = null;
	
	public interface PhotoSelectedListener {
		public void onPhotoSelected(ArrayList<String> photoPathList);
	}
	
	private class ImageAsyncTask extends AsyncTask<Void, Void, Object> {
		
		private ArrayList<AlbumInfo> tempAlbumList = new ArrayList<AlbumInfo>();
		
		@Override
		protected Object doInBackground(Void... params) {
			//获取缩略图
			ContentResolver contentResolver = context.getContentResolver();
			ThumbnailsUtil.clear();
			String[] projection = { Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA };
			Cursor cur = contentResolver.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);

			if (null != cur && cur.moveToFirst()) {
				int image_id;
				String image_path;
				int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
				int dataColumn = cur.getColumnIndex(Thumbnails.DATA);
				do {
					image_id = cur.getInt(image_idColumn);
					image_path = cur.getString(dataColumn);
					ThumbnailsUtil.put(image_id, "file://"+image_path);
				} while (cur.moveToNext());
			}

			//获取原图
			Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, "date_modified DESC");

			String _path="_data";
			String _album="bucket_display_name";

			HashMap<String,AlbumInfo> myhash = new HashMap<String, AlbumInfo>();
			AlbumInfo albumInfo = null;
			PhotoInfo photoInfo = null;
			if (cursor!=null&&cursor.moveToFirst()){  
				do{  
					int index = 0;
					int _id = cursor.getInt(cursor.getColumnIndex("_id")); 
					String path = cursor.getString(cursor.getColumnIndex(_path));
					String album = cursor.getString(cursor.getColumnIndex(_album));
					ArrayList<PhotoInfo> stringList = new ArrayList<PhotoInfo>();
					photoInfo = new PhotoInfo();
					if(myhash.containsKey(album)){
						albumInfo = myhash.remove(album);
						if(tempAlbumList.contains(albumInfo))
							index = tempAlbumList.indexOf(albumInfo);
						photoInfo.setImage_id(_id);
						photoInfo.setPath_file("file://"+path);
						photoInfo.setPath_absolute(path);
						albumInfo.getList().add(photoInfo);
						tempAlbumList.set(index, albumInfo);
						myhash.put(album, albumInfo);
					}else{
						albumInfo = new AlbumInfo();
						stringList.clear();
						photoInfo.setImage_id(_id);
						photoInfo.setPath_file("file://"+path);
						photoInfo.setPath_absolute(path);
						stringList.add(photoInfo);
						albumInfo.setImage_id(_id);
						albumInfo.setPath_file("file://"+path);
						albumInfo.setPath_absolute(path);
						albumInfo.setName_album(album);
						albumInfo.setList(stringList);
						tempAlbumList.add(albumInfo);
						myhash.put(album, albumInfo);
					}
				}while (cursor.moveToNext());
			}
			return null;
		}
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			handler.obtainMessage(0, tempAlbumList).sendToTarget();
		}
	}
	
	@Override
	public void show() {
		super.show();
		Window window = getWindow();
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		layoutParams.width = metrics.widthPixels;
		layoutParams.height = metrics.heightPixels;
		window.setAttributes(layoutParams);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
