package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.VenuesUsersBusiness.GetVenuesUsersCallback;
import com.KiwiSports.business.VenuesTreasureBusiness;
import com.KiwiSports.business.VenuesTreasureBusiness.GetVenuesTreasureCallback;
import com.KiwiSports.business.VenuesUsersBusiness;
import com.KiwiSports.model.VenuesTreasInfo;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.GPSUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VenuesMapActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView map_iv_mylocation;
	private TextView pagetop_tv_you;
	private String posid;
	private String token;
	private String uid;
	private String access_token;
	private HashMap<String, String> mhashmap;
	protected ArrayList<VenuesUsersInfo> mMapList;
	private String name;
	private userThumbShoaUtils muserThumbShoaUtils;
	protected ArrayList<VenuesTreasInfo> mMapTreasureList;
	protected userTreasureShoaUtils muserTreasureShoaUtils;
	private LatLng topleftpoint;
	private LatLng bottomrightpoint;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.map_iv_mylocation:
			moveToCenter();
			break;
		case R.id.pagetop_tv_you:
			Intent intent = new Intent(this, VenuesRankActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("posid", posid);
			intent.putExtra("name", name);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.venues_map);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		map_iv_mylocation = (ImageView) findViewById(R.id.map_iv_mylocation);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		map_iv_mylocation.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText(getString(R.string.venues_rank_title));
	}

	@SuppressLint("NewApi")
	@Override
	protected void processLogic() {
		Intent intent = getIntent();
		name = intent.getExtras().getString("name", "");
		posid = intent.getExtras().getString("posid", "");
		double top_left_x = intent.getExtras().getDouble("top_left_x", 0);
		double top_left_y = intent.getExtras().getDouble("top_left_y", 0);
		double bottom_right_y = intent.getExtras().getDouble("bottom_right_y",
				0);
		double bottom_right_x = intent.getExtras().getDouble("bottom_right_x",
				0);
		token = intent.getExtras().getString("token", "");
		uid = intent.getExtras().getString("uid", "");
		access_token = intent.getExtras().getString("access_token", "");
		pagetop_tv_name.setText(name);
		topleftpoint = new LatLng(top_left_y, top_left_x);
		bottomrightpoint = new LatLng(bottom_right_y, bottom_right_x);
		initMapView();
	}

	protected void getVenuesUsers() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("posid", posid);
		Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
		new VenuesUsersBusiness(this, mhashmap, new GetVenuesUsersCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mMapList = (ArrayList<VenuesUsersInfo>) dataMap
								.get("mlist");
						if (muserThumbShoaUtils == null) {
							muserThumbShoaUtils = new userThumbShoaUtils(
									context, mBaiduMap);
						}
						muserThumbShoaUtils.initMyOverlay(mMapList);
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
						dataMap);

			}
		});

	}

	protected void getVenuesTreasure() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("posid", posid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
		new VenuesTreasureBusiness(this, "VenuesTreasure", mhashmap,
				new GetVenuesTreasureCallback() {
					@SuppressWarnings("unchecked")
					@Override
					public void afterDataGet(HashMap<String, Object> dataMap) {
						if (dataMap != null) {
							String status = (String) dataMap.get("status");
							if (status.equals("200")) {
								mMapTreasureList = (ArrayList<VenuesTreasInfo>) dataMap
										.get("mlist");
								if (muserTreasureShoaUtils == null) {
									muserTreasureShoaUtils = new userTreasureShoaUtils(
											context, mBaiduMap);
								}
								muserTreasureShoaUtils
										.initMyOverlayTreasure(mMapTreasureList);
							}
						}
						CommonUtils.getInstance().setClearCacheBackDate(
								mhashmap, dataMap);

					}
				});

	}

	/**
	 * =================定位======================
	 */
	private float STARTZOOM = 15.0f;
	private boolean isFirstLoc = true;

	private double longitude_me;
	private double latitude_me;

	private MapView mMapView;
	private AMap mBaiduMap;
	private AMapLocationClient mLocClient;
	private AMapLocationClientOption mLocationOption;
	private BitmapDescriptor realtimeBitmap;
	private Marker overlay;

	/**
	 * 初始化定位的SDK
	 */
	private void initMapView() {
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.mMapView);
		// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
		mMapView.onCreate(paramBundle);
		mBaiduMap = mMapView.getMap();
		/*
		 * 百度地图 UI 控制器
		 */
		UiSettings mUiSettings = mBaiduMap.getUiSettings();
		mUiSettings.setCompassEnabled(false);// 隐藏指南针
		mUiSettings.setRotateGesturesEnabled(false);// 阻止旋转
		mUiSettings.setZoomControlsEnabled(false);// 隐藏的缩放按钮
		// 设置显示缩放比例
		CameraUpdate msu = CameraUpdateFactory.zoomTo(STARTZOOM);
		mBaiduMap.moveCamera(msu);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setMapType(Constants.BaiduMapTYPE_NORMAL);

		// 定位初始化
		mLocClient = new AMapLocationClient(this);
		mLocClient.setLocationListener(mLocationListener);

		// 创建一个定位客户端的参数
		// 初始化定位参数
		mLocationOption = new AMapLocationClientOption();
		// 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
		mLocationOption
				.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		// 设置是否返回地址信息（默认返回地址信息）
		mLocationOption.setNeedAddress(true);
		// 设置是否只定位一次,默认为false
		mLocationOption.setOnceLocation(true);
		// 设置是否允许模拟位置,默认为false，不允许模拟位置
		mLocationOption.setMockEnable(false);
		// 设置定位间隔,单位毫秒,默认为2000ms
		mLocationOption.setInterval(2000);
		// 给定位客户端对象设置定位参数
		mLocClient.setLocationOption(mLocationOption);
		// 启动定位
		mLocClient.startLocation();
	}

	public AMapLocationListener mLocationListener = new AMapLocationListener() {

		@Override
		public void onLocationChanged(AMapLocation location) {
			if (location != null && location.getErrorCode() == 0) {

				try {
					System.err.println("*********************************");
					if (isFirstLoc) {
						// --------------------*定自己的位置信息-----------------------------------------------
						longitude_me = location.getLongitude();
						latitude_me = location.getLatitude();
						moveToCenter();
						getVenuesUsers();
						getVenuesTreasure();
						moveToVenue();
						isFirstLoc = false;
					}
				} catch (Exception e) {
				}

			}

		}
	};

	private void moveToVenue() {
		ArrayList<LatLng> mList = new ArrayList<LatLng>();
		mList.add(topleftpoint);
		mList.add(bottomrightpoint);
		STARTZOOM = GPSUtil.setZoom(mList);
		LatLng centerpoint = GPSUtil.getCenterpoint(mList);
		if (centerpoint != null) {
			CameraUpdate u = CameraUpdateFactory.newLatLngZoom(centerpoint, STARTZOOM);
			if (u != null && mBaiduMap != null) {
				mBaiduMap.moveCamera(u);// 以动画方式更新地图状态，动画耗时 300 ms
			}
		}
	}

	/**
	 * 设置中心点的焦点
	 */
	private void moveToCenter() {
		LatLng mypoint = new LatLng(latitude_me, longitude_me);
		CameraUpdate u = CameraUpdateFactory.newLatLngZoom(mypoint, STARTZOOM);
		if (u != null && mBaiduMap != null) {
			mBaiduMap.moveCamera(u);// 以动画方式更新地图状态，动画耗时 300 ms
		}

		// 自定义图标
		if (null == realtimeBitmap) {
			realtimeBitmap = BitmapDescriptorFactory.fromBitmap(BitmapFactory
					.decodeResource(getResources(),
							R.drawable.icon_myposition_map));
		}
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(mypoint);
		markerOptions.icon(realtimeBitmap);
		if (null != overlay) {
			overlay.remove();
		}
		overlay = mBaiduMap.addMarker(markerOptions);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onPause() {
		try {
			if (mMapView != null) {
				mMapView.onPause();
			}
			super.onPause();
		} catch (Exception e) {
		}
	}

	@Override
	protected void onResume() {
		try {
			if (mMapView != null) {
				mMapView.onResume();
			}
			super.onResume();
		} catch (Exception e) {
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mLocClient != null)
			mLocClient.stopLocation();// 停止定位
	}

	@Override
	protected void onDestroy() {
		try {
			// 退出时销毁定位
			if (mLocClient != null)
				mLocClient.onDestroy();
			// 关闭定位图层
			mBaiduMap.setMyLocationEnabled(false);
			mBaiduMap.clear();
			mBaiduMap = null;
			mMapView.removeAllViews();
			mMapView.onDestroy();
			mMapView = null;
			mLocationOption = null;
		} catch (Exception e) {
		}
		super.onDestroy();
	}

	private void doBack() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}

}
