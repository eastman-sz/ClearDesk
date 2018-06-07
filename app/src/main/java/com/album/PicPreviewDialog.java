package com.album;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.album.gesture.PhotoView;
import com.album.gesture.PhotoViewAttacher;
import com.common.dialog.BaseDialog;
import com.common.dialog.CommonDialog;
import com.common.dialog.OnCommonDialogBtnClickListener;
import com.imageloader.ImageLoadListener;
import com.imageloader.UniversalImageLoadTool;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.sz.sk.clear.desk.R;
import com.utils.lib.ss.common.BitmapHelper;
import com.utils.lib.ss.common.CheckHelper;
import com.utils.lib.ss.common.ToastHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * 图片预览的Dialog。
 * @author E
 */
public class PicPreviewDialog extends BaseDialog {

	private ViewPager viewPager = null;
	private ArrayList<String> picUrlList = new ArrayList<String>();
	//缓存加载的图片
	private SparseArray<Bitmap> bmp = new SparseArray<Bitmap>();
	private ImageAdapter adapter = null;
	
	private boolean mayBeDeleted = true;
	private int curr = 0;
	
	public PicPreviewDialog(Context context) {
		super(context, R.style.lable_del_dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_preview_activity_view);
		initViews();
	}
	
	protected void initViews(){
		viewPager = (ViewPager) findViewById(R.id.pager);
		adapter = new ImageAdapter(context);
		viewPager.setAdapter(adapter);
		viewPager.addOnPageChangeListener(pageListener);
	}
	
	public void setPicList(List<String> picUrls , int currentPosition , boolean mayBeDelete){
		if (CheckHelper.listIsEmpty(picUrls)) {
			return;
		}
		this.mayBeDeleted = mayBeDelete;
		picUrlList.addAll(picUrls);
		viewPager.setCurrentItem(currentPosition);
		adapter.notifyDataSetChanged();
		curr = currentPosition;
	}
	
	public void setPicListener(PicListener picListener) {
		this.picListener = picListener;
	}
	
	OnPageChangeListener pageListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			curr = arg0;
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	class ImageAdapter extends PagerAdapter {
		
		private Context context = null;
		
		public ImageAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			return picUrlList.size();
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0.equals(arg1);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			View view = LayoutInflater.from(context).inflate(R.layout.pic_preview_adapter_view, container, false);
			final PhotoView imageView = (PhotoView) view.findViewById(R.id.image);
//			ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading);
			ImageView delImageView = (ImageView) view.findViewById(R.id.del_imageview);
			if (mayBeDeleted) {
				delImageView.setVisibility(View.VISIBLE);
			}else {
				delImageView.setVisibility(View.GONE);
			}
			String picUrl = picUrlList.get(position);
			if (picUrl.contains("small_")) {
				picUrl = picUrl.replaceAll("small_", "");
			}
			if (picUrl.contains("th_")) {
				picUrl = picUrl.replaceAll("th_", "");
			}
			if(picUrl.contains(Environment.getExternalStorageDirectory().getPath()) && !picUrl.startsWith("file:")){
				picUrl = "file://"+picUrl;
			}
			UniversalImageLoadTool.disPlay(picUrl, imageView, R.drawable.def_loading_rectangle_image, new ImageLoadListener(){
				@Override
				public void onLoadingComplete(String arg0, View arg1 , Bitmap bitmap) {
//					spinner.setVisibility(View.GONE);
					if(null == bmp.get(position)){
						bmp.put(position, bitmap);
					}
				}

				@Override
				public void onLoadingFailed(String arg0, View imgView, FailReason arg2) {
				    String imgUrl = picUrlList.get(position);
                    UniversalImageLoadTool.disPlay(imgUrl , imageView , R.drawable.def_loading_rectangle_image);
				}
			});
			container.addView(view, 0);
			
			imageView.setClickable(true);
			imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
				@Override
				public void onViewTap(View view, float x, float y) {
					dismiss();
				}
			});
			imageView.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					longTouched(position);
					return false;
				}
			});
			delImageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showdelteImageDialog(position);
				}
			});
			return view;
		}
	}
	
	public PicListener picListener = null;

	/**
	 * 监听对图片的操作。
	 * @author E
	 */
	public static abstract class PicListener{
		public void onImageDelete(int delPostion){};
		public void onImageSaved(int delPostion){};
	}
	
	private void showdelteImageDialog(final int delPostion){
		final CommonDialog dialog = new CommonDialog(context);
		dialog.show();
		dialog.setDialogText("" ,"确定删除这个图片吗？" , "是" , "否");
		dialog.setOnCommonDialogBtnClickListener(new OnCommonDialogBtnClickListener() {
			@Override
			public void onLeftBtnClik() {
				handler.obtainMessage(0, delPostion).sendToTarget();
				dialog.dismiss();
				if (null != picListener) {
					picListener.onImageDelete(delPostion);
				}
			}

			@Override
			public void onRightBtnClik() {
				dialog.dismiss();
			}
		});
	}
	
	private void longTouched(final int position){
//		final CommonSelectorDialog dialog = new CommonSelectorDialog(context);
//		dialog.show();
//		dialog.setSingleItemText(context.getString(R.string.string_1365));
//		dialog.setLastItemText(context.getString(R.string.cancel));
//		dialog.setItemSelectorListener(new ItemSelectorLister() {
//
//			@Override
//			public void onSingleItemClicked() {
//				dialog.dismiss();
//				saveImageToLocalAlbum();
//				if (null != picListener) {
//					picListener.onImageSaved(position);
//				}
//			}
//			@Override
//			public void onLastItemClicked() {
//				dialog.dismiss();
//			}
//		});
	}
	
	/**
	 * 将照片保存到系统相册。
	 */
	private void saveImageToLocalAlbum(){
		if (null != bmp.get(curr)) {
			try {
				ContentResolver cr = context.getContentResolver();
				File path = BitmapHelper.saveBmpToFile(bmp.get(curr));
				
				String url  = MediaStore.Images.Media.insertImage(cr, path.getAbsolutePath(), "iwy_image", null);
				context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ path.getAbsolutePath())));
				String tip = "";
				if (null!= url) {
					tip = "保存成功";
				}else {
					tip = "保存失败";
				}
				ToastHelper.makeText(context, tip);
			} catch (Exception e) {
				e.printStackTrace();
				ToastHelper.makeText(context, "保存失败");
			}
		}
	}
	
	Handler handler = new Handler(Looper.getMainLooper()){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				int posistion = Integer.valueOf(msg.obj.toString());
				int size = picUrlList.size();
				if (posistion >= size) {
					posistion = size - 1;
				}
				picUrlList.remove(posistion);
				if (picUrlList.isEmpty()) {
					dismiss();
					return;
				}
				adapter = new ImageAdapter(context);
				viewPager.setAdapter(adapter);
				viewPager.setCurrentItem(posistion);
				adapter.notifyDataSetChanged();
				curr = posistion;
				break;
			default:
				break;
			}
		}
	};

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
	
}
