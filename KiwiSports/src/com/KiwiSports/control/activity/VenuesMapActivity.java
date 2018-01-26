package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.VenuesUsersBusiness.GetVenuesUsersCallback;
import com.KiwiSports.business.VenuesUsersBusiness;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.GPSUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

public class VenuesMapActivity extends BaseActivity implements BDLocationListener {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView map_iv_mylocation;
	private TextView pagetop_tv_you;
	private String posid;
	private double top_left_x;
	private double top_left_y;
	private String token;
	private String uid;
	private String access_token;
	private HashMap<String, String> mhashmap;
	protected ArrayList<VenuesUsersInfo> mMapList;
	private String name;
	private userThumbShoaUtils muserThumbShoaUtils;
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
		top_left_x = intent.getExtras().getDouble("top_left_x", 0);
		top_left_y = intent.getExtras().getDouble("top_left_y", 0);
		token = intent.getExtras().getString("token", "");
		uid = intent.getExtras().getString("uid", "");
		access_token = intent.getExtras().getString("access_token", "");
		pagetop_tv_name.setText(name);
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
			

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mMapList = (ArrayList<VenuesUsersInfo>) dataMap.get("mlist");
						if(muserThumbShoaUtils==null){
							muserThumbShoaUtils=new userThumbShoaUtils(context, mBaiduMap);
						}
						muserThumbShoaUtils.initMyOverlay(mMapList);
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	/**
	 * =================定位======================
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private LocationClient mLocClient;
	private LocationClientOption option;
	private float STARTZOOM = 15.0f;
	private boolean isFirstLoc = true;

	private double longitude_me;
	private double latitude_me;

	/**
	 * 初始化定位的SDK
	 */
	private void initMapView() {
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.mMapView);
		mBaiduMap = mMapView.getMap();
		/*
		 * 百度地图 UI 控制器
		 */
		UiSettings mUiSettings = mBaiduMap.getUiSettings();
		mUiSettings.setCompassEnabled(false);// 隐藏指南针
		mUiSettings.setRotateGesturesEnabled(false);// 阻止旋转
		// 设置显示缩放比例
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(STARTZOOM);
		mBaiduMap.setMapStatus(msu);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 隐藏缩放控件
		int childCount = mMapView.getChildCount();
		if (childCount >= 3) {
			// 删除比例尺控件
			mMapView.removeViewAt(3);
			mMapView.removeViewAt(2);
			View child = mMapView.getChildAt(1);
			if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
				child.setVisibility(View.INVISIBLE);
			}
			mMapView.showZoomControls(false);
			mMapView.showScaleControl(false);
		}

		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(this);
		// 创建一个定位客户端的参数
		option = new LocationClientOption();
		option.setOpenGps(true);// 是否打开GPS
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType(GPSUtil.CoorType); // 返回的定位结果是百度经纬度,默认值gcj02
		mLocClient.setLocOption(option);// 设置给定位客户端
		mLocClient.start();// 启动定位客户端
	}

	@Override
	public void onReceiveLocation(BDLocation location) {

		try {
			System.err.println("*********************************");
			if (isFirstLoc) {
				// --------------------*定自己的位置信息-----------------------------------------------
				try {
					if (location.getLocType() == 62) {
						longitude_me = 116.404269;
						latitude_me = 39.91405;
					} else {
						longitude_me = location.getLongitude();
						latitude_me = location.getLatitude();
					}
					moveToCenter();
					getVenuesUsers();
					LatLng mypoint = new LatLng(top_left_y, top_left_x);
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(mypoint);
					if (u != null && mBaiduMap != null) {
						// mBaiduMap.animateMapStatus(u);//以动画方式更新地图状态，动画耗时 300 ms
						mBaiduMap.setMapStatus(u);// 改变地图状态
					}
				} catch (Exception e) {
				}
				option.setOpenGps(false);
				isFirstLoc = false;
			}
		} catch (Exception e) {
		}

	}

	private Overlay overlay;
	private BitmapDescriptor realtimeBitmap;

	/**
	 * 设置中心点的焦点
	 */
	private void moveToCenter() {
			LatLng mypoint = new LatLng(latitude_me, longitude_me);
			if (null != overlay) {
				overlay.remove();
			}

			if (null == realtimeBitmap) {
				realtimeBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_myposition_map);
			}
			MarkerOptions overlayOptions = new MarkerOptions().position(mypoint).icon(realtimeBitmap).zIndex(8)
					.draggable(true);
			// 实时点覆盖物
			overlay = mBaiduMap.addOverlay(overlayOptions);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(mypoint);
			if (u != null && mBaiduMap != null) {
				// mBaiduMap.animateMapStatus(u);//以动画方式更新地图状态，动画耗时 300 ms
				mBaiduMap.setMapStatus(u);// 改变地图状态
			}
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
	protected void onDestroy() {
		try {
			// 退出时销毁定位
			if (mLocClient != null)
				mLocClient.stop();
			// 关闭定位图层
			mBaiduMap.setMyLocationEnabled(false);
			mBaiduMap.clear();
			mBaiduMap = null;
			mMapView.removeAllViews();
			mMapView.onDestroy();
			mMapView = null;
			option = null;
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
