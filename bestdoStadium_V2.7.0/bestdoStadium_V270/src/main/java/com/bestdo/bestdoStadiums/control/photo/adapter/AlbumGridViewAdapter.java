package com.bestdo.bestdoStadiums.control.photo.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.photo.util.BitmapCache;
import com.bestdo.bestdoStadiums.control.photo.util.BitmapCache.ImageCallback;
import com.bestdo.bestdoStadiums.control.photo.util.ImageItem;
import com.bestdo.bestdoStadiums.control.photo.vedio.AblumUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 这个是显示一个文件夹里面的所有图片时用的适配器
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日 下午11:49:35
 */
public class AlbumGridViewAdapter extends BaseAdapter {
	final String TAG = getClass().getSimpleName();
	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private ArrayList<ImageItem> selectedDataList;
	BitmapCache cache;
	AblumUtils mAblumUtils;
	private int itemWidth;

	public AlbumGridViewAdapter(Context c, ArrayList<ImageItem> dataList, ArrayList<ImageItem> selectedDataList) {
		mContext = c;
		cache = new BitmapCache();
		this.dataList = dataList;
		this.selectedDataList = selectedDataList;
		int widthPixels = ConfigUtils.getInstance().getPhoneWidHeigth(mContext).widthPixels;
		itemWidth = (widthPixels - 10) / 3;// 根据屏幕大小计算每列大小
		mAblumUtils = new AblumUtils();
	}

	public int getCount() {
		return dataList.size();
	}

	public Object getItem(int position) {
		return dataList.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	ImageCallback callback = new ImageCallback() {
		public void imageLoad(ImageView imageView, Bitmap bitmap, Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "callback, bmp not match");
				}
			} else {
				Log.e(TAG, "callback, bmp null");
			}
		}
	};

	/**
	 * 存放列表项控件句柄
	 */
	private class ViewHolder {
		public ImageView imageView;
		public ToggleButton toggleButton;
		public TextView choosetoggle;
		public RelativeLayout toggle;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.plugin_camera_select_imageview, parent, false);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
			viewHolder.toggleButton = (ToggleButton) convertView.findViewById(R.id.toggle_button);
			viewHolder.choosetoggle = (TextView) convertView.findViewById(R.id.choosedbt);
			viewHolder.toggle = (RelativeLayout) convertView.findViewById(R.id.toggle);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		RelativeLayout.LayoutParams itemParam = new RelativeLayout.LayoutParams(itemWidth, itemWidth);
		viewHolder.imageView.setLayoutParams(itemParam);
		viewHolder.toggle.setLayoutParams(itemParam);
		String path;
		if (dataList != null && dataList.size() > position)
			path = dataList.get(position).imagePath;
		else
			path = "camera_default";
		if (path.contains("camera_default")) {
			viewHolder.imageView.setImageResource(R.drawable.listdetail_none);
		} else {
			final ImageItem item = dataList.get(position);
			viewHolder.imageView.setTag(item.imagePath);
			cache.displayBmp(viewHolder.imageView, item.thumbnailPath, item.imagePath, callback);
		}
		viewHolder.toggleButton.setTag(position);
		viewHolder.choosetoggle.setTag(position);
		viewHolder.toggleButton.setOnClickListener(new ToggleClickListener(viewHolder.choosetoggle));
		if (selectedDataList.contains(dataList.get(position))) {
			viewHolder.toggleButton.setChecked(true);
			viewHolder.choosetoggle.setBackgroundResource(R.drawable.check_true);
		} else {
			viewHolder.toggleButton.setChecked(false);
			viewHolder.choosetoggle.setBackgroundResource(R.drawable.check_false);
		}
		return convertView;
	}

	private class ToggleClickListener implements OnClickListener {
		TextView chooseBt;

		public ToggleClickListener(TextView choosebt) {
			this.chooseBt = choosebt;
		}

		@Override
		public void onClick(View view) {
			if (view instanceof ToggleButton) {
				ToggleButton toggleButton = (ToggleButton) view;
				int position = (Integer) toggleButton.getTag();
				if (dataList != null && mOnItemClickListener != null && position < dataList.size()) {
					mOnItemClickListener.onItemClick(toggleButton, position, toggleButton.isChecked(), chooseBt);
				}
			}
		}
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	public interface OnItemClickListener {
		public void onItemClick(ToggleButton view, int position, boolean isChecked, TextView chooseBt);
	}

}
