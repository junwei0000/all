package com.bestdo.bestdoStadiums.control.adapter;

import java.util.List;

import com.bestdo.bestdoStadiums.control.activity.MainNavImgActivity;
import com.bestdo.bestdoStadiums.model.BusinessBannerInfo;
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
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-8-18 上午10:54:29
 * @Description 类描述：
 */
public class BusinessAdvertImagePagerAdapter extends RecyclingPagerAdapter {

	private Activity context;
	private List<BusinessBannerInfo> bannerList;
	private int size;
	private boolean isInfiniteLoop;
	private ImageLoader asyncImageLoader;

	public BusinessAdvertImagePagerAdapter(Activity context, List<BusinessBannerInfo> bannerList) {
		this.context = context;
		this.bannerList = bannerList;
		this.size = bannerList.size();
		isInfiniteLoop = false;
		asyncImageLoader = new ImageLoader(context, "listdetail");
	}

	@Override
	public int getCount() {
		// Infinite loop
		return isInfiniteLoop ? Integer.MAX_VALUE : bannerList.size();
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
		BusinessBannerInfo mBannerInfo = bannerList.get(getPosition(position));
		String imgPath = mBannerInfo.getImgPath();
		if (!TextUtils.isEmpty(imgPath)) {
			asyncImageLoader.DisplayImage(imgPath, holder.imageView);
		} else {
			// holder.imageView.setBackgroundResource(R.drawable.banner_1);
		}

		holder.imageView.setTag(mBannerInfo);
		holder.imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BusinessBannerInfo mBannerInfo = (BusinessBannerInfo) v.getTag();
				String html_url = mBannerInfo.getUrl();
				if (!TextUtils.isEmpty(html_url) && !html_url.equals("null")) {
					
					String name= mBannerInfo.getName();
					boolean status=true;
					CommonUtils.getInstance().toH5(context,html_url, name, "",status);
				}
			}
		});
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
	public BusinessAdvertImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}
}
