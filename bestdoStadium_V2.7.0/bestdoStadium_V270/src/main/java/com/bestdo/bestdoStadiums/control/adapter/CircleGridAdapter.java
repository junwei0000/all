package com.bestdo.bestdoStadiums.control.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.model.CircleBean;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.MeasureUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class CircleGridAdapter extends BaseAdapter {

	private String[] mFiles;
	private LayoutInflater mLayoutInflater;
	protected static final int MAX_W_H_RATIO = 3;
	private Context mContext;
	private String img_video_type;
	private CircleBean bean;

	public CircleGridAdapter(String[] files, String img_video_type, Context mContext, CircleBean bean) {
		this.mFiles = files;
		this.mContext = mContext;
		this.img_video_type = img_video_type;
		this.bean = bean;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mFiles == null ? 0 : mFiles.length;
	}

	@Override
	public String getItem(int position) {
		return mFiles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_gridview_circle, null);
			holder.imageView = (ImageView) convertView.findViewById(R.id.album_image);
			holder.album_out_rel = (RelativeLayout) convertView.findViewById(R.id.album_out_rel);
			holder.album_out_img = (ImageView) convertView.findViewById(R.id.album_out_img);
			holder.album_out_frame = (FrameLayout) convertView.findViewById(R.id.album_out_frame);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (bean.widthList != null && bean.widthList.size() > 0) {

			if (mFiles.length > 1) {
				// 根据屏幕宽度动态设置图片宽高
				int width = MeasureUtils.getWidth(mContext) - ConfigUtils.getInstance().dip2px(mContext, 70);
				int imageWidth = (width / 3);
				LayoutParams laParams = (LayoutParams) holder.imageView.getLayoutParams();
				laParams.height = imageWidth;
				laParams.width = imageWidth;
				holder.imageView.setLayoutParams(laParams);
				holder.album_out_rel.setGravity(Gravity.CENTER);
			} else if (mFiles.length == 1) {
				int width = MeasureUtils.getWidth(mContext) - ConfigUtils.getInstance().dip2px(mContext, 70);
				int imageWidth = (width / 3);
				LayoutParams laParams = (LayoutParams) holder.imageView.getLayoutParams();
				laParams.height = imageWidth;
				laParams.width = imageWidth;
				holder.imageView.setLayoutParams(laParams);
				holder.album_out_rel.setGravity(Gravity.CENTER);
			}
		}

		String url = getItem(position);
		ImageLoader.getInstance().displayImage(url, holder.imageView, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {

			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
				if (bitmap != null) {
					if (mFiles.length == 1) {
						int w = bitmap.getWidth();
						int h = bitmap.getHeight();
						int newW;
						int newH;
						int width = MeasureUtils.getWidth(mContext);
						int parentWidth = (width - 40 - 55);
						if (h > w * MAX_W_H_RATIO) {// h:w = 5:3
							newW = parentWidth / 2;
							newH = newW * 5 / 3;
						} else if (h > w && h < w * MAX_W_H_RATIO) {// h:w = 3:2
							newW = parentWidth / 3;
							newH = newW * h / w;
						} else if (h < w) {// h:w = 2:3
							newW = parentWidth * 2 / 3;
							newH = newW * h / w;
						} else {// newH:h = newW :w
							newW = parentWidth / 2;
							newH = newW;
						}
						LayoutParams lp = (LayoutParams) holder.imageView.getLayoutParams();
						lp.width = w;
						lp.height = h;
						holder.imageView.setLayoutParams(lp);
						System.err.println(newW + "   " + newH);
						if (img_video_type.equals("1")) {
							holder.album_out_img.setVisibility(View.VISIBLE);
						} else {
							holder.album_out_img.setVisibility(View.GONE);
						}
					} else {
						holder.album_out_img.setVisibility(View.GONE);
					}

				}

			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub

			}
		});

		return convertView;
	}

	private static class ViewHolder {
		ImageView imageView;
		ImageView album_out_img;
		RelativeLayout album_out_rel;
		FrameLayout album_out_frame;
	}
}
