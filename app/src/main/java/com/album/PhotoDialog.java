package com.album;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.common.base.CommonTitleView;
import com.common.dialog.BaseDialog;
import com.imageloader.UniversalImageLoadTool;
import com.sz.sk.clear.desk.R;
import com.utils.lib.ss.common.ToastHelper;
import java.text.MessageFormat;
import java.util.ArrayList;
/**
 * 相册内的照片。 
 * @author E
 */
public class PhotoDialog extends BaseDialog {

	private CommonTitleView commonTitleView = null;
	
	private ArrayList<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
	private PhotoAdapter adapter = null;
	
	private OnPhotoSelectClickListener onPhotoSelectClickListener = null;
	//当前已选择的数量
	private int currentSelectedNum = 0;
	//最大选择数
	private int maxNum = 8;
	
	public PhotoDialog(Context context) {
		super(context ,R.style.photo_dialog);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_dialog_view);
		initTitle();
		initViews();
	}
	
	protected void initTitle(){
		 commonTitleView = (CommonTitleView) findViewById(R.id.commom_title_view);
		 commonTitleView.setLeftBtnText("返回");
		 commonTitleView.setRightBtnText("完成");
		 commonTitleView.setOnTitleClickListener(new CommonTitleView.OnTitleClickListener() {
			@Override
			public void onLeftBtnClick() {
				dismiss();
			}
			@Override
			public void onRightBtnClick() {
				onComfirm();
			}
		});
	}
	
	protected void initViews(){
		final GridView gridView = (GridView) findViewById(R.id.gridview);
		adapter = new PhotoAdapter(context, photoInfoList);
		gridView.setAdapter(adapter);
		adapter.setPhotoSelectListener(new PhotoAdapter.PhotoSelectListener(){
			@Override
			public void onPhotoSelectClick(int position) {
				int size = photoInfoList.size();
				if (0 == size) {
					return;
				}
				if (position >= size) {
					position = size -1 ;
				}
				PhotoInfo photoInfo = photoInfoList.get(position);
				if (photoInfo.isChoose()) {
					currentSelectedNum -- ;
					photoInfo.setChoose(false);
				}else {
					if (currentSelectedNum >= maxNum) {
						String toast_msg = MessageFormat.format("最多只能选{0}张图片", maxNum);
						ToastHelper.makeText(context, toast_msg);
						return;
					}
					currentSelectedNum ++;
					photoInfo.setChoose(true);
				}
                adapter.notifyDataSetChanged(gridView , position);

//				adapter.notifyDataSetChanged();
			}
		});
		
		gridView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				UniversalImageLoadTool.onScrollStateChanged(scrollState);
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				int size = photoInfoList.size();
				if (0 == size) {
					return;
				}
				if (position >= size) {
					position = size -1 ;
				}
				PhotoInfo photoInfo = photoInfoList.get(position);
				
				AlbumUtils.getInstance().toPreImge(context, photoInfo.getPath_absolute(), false);
			}
		});
	}
	
	public void setPhotoList(ArrayList<PhotoInfo> photoList){
		photoInfoList.clear();
		photoInfoList.addAll(photoList);
		adapter.notifyDataSetChanged();
		checkSelectedNum();
	}
	
	//检查选中的个数
	private void checkSelectedNum(){
		for (PhotoInfo photoInfo : photoInfoList) {
			if (photoInfo.isChoose()) {
				currentSelectedNum ++ ;
			}
		}
	}
	
	public void setTitle(String text){
		if (!TextUtils.isEmpty(text)) {
			int length = text.length();
			if (length > 11) {
				text = text.substring(0, 11) + "...";
			}
		}
		commonTitleView.setCenterTitleText(text);
	}
	
	public void setOnPhotoSelectClickListener(OnPhotoSelectClickListener onPhotoSelectClickListener) {
		this.onPhotoSelectClickListener = onPhotoSelectClickListener;
	}
	
    private ArrayList<PhotoInfo> selectedList = new ArrayList<PhotoInfo>();
    
	View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
//			case R.id.comfirm_textview:
//				//不能超过最大设定数
//				onComfirm();
//				break;
			default:
				break;
			}
		}
	};
	
	private void onComfirm(){
		//不能超过最大设定数
		selectedList.clear();
		for (PhotoInfo photoInfo : photoInfoList) {
			if (photoInfo.isChoose()) {
				selectedList.add(photoInfo);
			}
		}
		
		if (selectedList.isEmpty()) {
			ToastHelper.makeText(context, "还没有选择任何图片");
			return;
		}
		
		int selectedSize = selectedList.size();
		if (selectedSize > maxNum) {
			String toast_msg = MessageFormat.format("最多只能选{0}张图片", maxNum);
			ToastHelper.makeText(context, toast_msg);
			return;
		}
		if (null != onPhotoSelectClickListener) {
			onPhotoSelectClickListener.onPhotoSelectClickListener(selectedList);
		}
		dismiss();
	}
	
	public interface OnPhotoSelectClickListener {  
		public void onPhotoSelectClickListener(ArrayList<PhotoInfo> list);
	}
	
	//最多可以选 多少张照片
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
//	    adapter.setNeedShowSelector(!(maxNum <= 1));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
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

}
