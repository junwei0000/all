/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.bestdo.bestdoStadiums.control.adapter;

import java.util.List;

import com.bestdo.bestdoStadiums.control.activity.MainBannerActivity;
import com.bestdo.bestdoStadiums.control.activity.MainNavImgActivity;
import com.bestdo.bestdoStadiums.control.activity.StadiumDetailMapActivity;
import com.bestdo.bestdoStadiums.model.BannerInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * AdvertImagePagerAdapter
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class AdvertImagePagerAdapter extends RecyclingPagerAdapter {

	private Activity context;
	private List<BannerInfo> imageIdList;
	private String Skipto;
	private int size;
	private boolean isInfiniteLoop;
	private ImageLoader asyncImageLoader;
	private String cityid_select;
	private double longitude_select;
	private double latitude_select;

	public AdvertImagePagerAdapter(Activity context, List<BannerInfo> imageIdList, String Skipto, String cityid_select,
			double longitude_select, double latitude_select) {
		this.context = context;
		this.imageIdList = imageIdList;
		this.Skipto = Skipto;
		this.cityid_select = cityid_select;
		this.longitude_select = longitude_select;
		this.latitude_select = latitude_select;
		this.size = imageIdList.size();
		isInfiniteLoop = false;
		asyncImageLoader = new ImageLoader(context, "listdetail");
	}

	@Override
	public int getCount() {
		// Infinite loop
		return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
	}

	/**
	 * get really position
	 * 
	 * @param position
	 * @return
	 */
	private int getPosition(int position) {
		return position % size;
	}

	@Override
	public View getView(int position, View view, ViewGroup container) {
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.banner_viewpage, null);
			holder.imageView = (ImageView) view.findViewById(R.id.banner_img);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		BannerInfo mBannerInfo = imageIdList.get(getPosition(position));
		String thumb = mBannerInfo.getImg_url();
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, holder.imageView);
		} else {
			holder.imageView.setImageBitmap(asyncImageLoader.bitmap_orve);
		}
		if (Skipto.equals("SkipfromMain")) {
			holder.imageView.setTag(mBannerInfo);
			holder.imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					BannerInfo mBannerInfo = (BannerInfo) v.getTag();
					String thumb = mBannerInfo.getImg_url();
					String html_url = mBannerInfo.getHtml_url();
					if (!html_url.contains("/special_price.html")) {
						String name= mBannerInfo.getName();
						boolean status=true;
						CommonUtils.getInstance().toH5(context,html_url, name, "",status);
					} else {
						Intent intent = new Intent(context, MainBannerActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("thumb", thumb);
						intent.putExtra("cityid_select", cityid_select);
						intent.putExtra("longitude_select", longitude_select);
						intent.putExtra("latitude_select", latitude_select);
						context.startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, context);
					}

				}
			});
		}
		return view;
	}

	private static class ViewHolder {

		ImageView imageView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop() {
		return isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public AdvertImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}
}
