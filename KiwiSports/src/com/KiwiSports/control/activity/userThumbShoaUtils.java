package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class userThumbShoaUtils {

	HashMap<String, Marker> mMarkermap = new HashMap<String, Marker>();
	private Context mActivity;
	private BaiduMap mBaiduMap;

	public userThumbShoaUtils(Context mActivity, BaiduMap mBaiduMap) {
		super();
		this.mActivity = mActivity;
		this.mBaiduMap = mBaiduMap;
	}

	public void cleaiMap() {
		mMarkermap.clear();
	}

	/**
	 * 场馆定位
	 */
	public void initMyOverlay(ArrayList<VenuesUsersInfo> mMapList) {
		try {
			clearDate();
			ImageLoader.getInstance().clearMemoryCache();
			Log.e("map", "mMarkermap.size   " + mMarkermap.size());
			if (mMapList != null && mMapList.size() != 0) {
				// 遍历显示
				for (int i = 0; i < mMapList.size(); i++) {
					VenuesUsersInfo mVenuesUsersInfom = mMapList.get(i);
					double latitude = mVenuesUsersInfom.getLatitude();
					double longitude = mVenuesUsersInfom.getLongitude();
					String Album_url = mVenuesUsersInfom.getAlbum_url();
					String userid = mVenuesUsersInfom.getUid();
					String is_anonymous = mVenuesUsersInfom.getIs_anonymous();
					LatLng stadiumpoint = new LatLng(latitude, longitude);

					if (!TextUtils.isEmpty(is_anonymous) && is_anonymous.equals("1")) {
						Album_url = "";// 匿名时不显示头像
					}
					if (!TextUtils.isEmpty(Album_url)) {
						loadToBitmap(Album_url, userid, stadiumpoint);
					} else {
						Bitmap Roundbitmap = CommonUtils.getInstance().readBitMap(mActivity, R.drawable.ic_launcher);
						Roundbitmap = CommonUtils.getInstance().toRoundCorner(mActivity, Roundbitmap);
						Marker mMarker = addMarker(userid, stadiumpoint, Roundbitmap);
					}
				}

			} else {
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 没有数据时清除所有
	 */
	private void clearDate() {
		if (mMarkermap.size() > 0) {
			Iterator<Entry<String, Marker>> it = mMarkermap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Marker> entry = it.next();
				Marker mMarker = entry.getValue();
				if (mMarker != null) {
					mMarker.remove();
				}
			}
			mMarkermap.clear();
		}
	}

	@SuppressLint("NewApi")
	private Marker addMarker(String userid, LatLng point, Bitmap bitmap) {

		View convertView = LayoutInflater.from(mActivity).inflate(R.layout.venues_map_marker, null);
		CircleImageView iv_head = (CircleImageView) convertView.findViewById(R.id.iv_head);
		iv_head.setImageBitmap(bitmap);
		iv_head.setImageAlpha(0);
		BitmapDescriptor mmorenMarker = BitmapDescriptorFactory.fromView(convertView);
		OverlayOptions ooA = new MarkerOptions().position(point).icon(mmorenMarker).zIndex(5).draggable(false);
		Marker mMarker = (Marker) mBaiduMap.addOverlay(ooA);
		if (!mMarkermap.containsKey(userid)) {
			mMarkermap.put(userid, mMarker);
			Log.e("map", "add   " + userid + "  " + mMarkermap.size());
		}
		return mMarker;
	}

	Bitmap bitmap = null;

	public Bitmap loadToBitmap(String Album_url, final String userid, final LatLng stadiumpoint) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_launcher)// 设置图片加载或解码过程中发生错误显示的图片
				.bitmapConfig(Bitmap.Config.RGB_565)
				// 设置下载的图片是否缓存在内存中
				.cacheInMemory(false)
				// 设置下载的图片是否缓存在SD卡中
				.cacheOnDisc(true).build();
		ImageLoader.getInstance().loadImage(Album_url, options, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {

			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				bitmap = arg2;
				Log.e("map---bitmap", bitmap.toString());
				Log.e("map---arg0", arg0.toString());
				if (bitmap != null) {
					Marker mMarker = addMarker(userid, stadiumpoint, bitmap);
				}
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {

			}
		});
		return bitmap;
	}

}
