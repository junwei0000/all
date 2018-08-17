package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.navisdk.BNaviPoint;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.bestdo.bestdoStadiums.control.map.BNavigatorActivity;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-7 下午4:03:48
 * @Description 类描述：场馆详情定位
 */
public class StadiumDetailMapActivity extends BaseActivity implements OnMapClickListener, OnGetRoutePlanResultListener {
	/**
	 * ------------------------------------------
	 */
	// 定位相关
	LocationClient mLocClient;
	LocationClientOption option;
	public MyLocationListenner myListener = new MyLocationListenner();
	MapView mMapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// 是否首次定位
	// --------------
	boolean useDefaultIcon = false;
	// 搜索相关
	RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	DrivingRoutePlanOption mDrivingRoutePlanOption;
	/**
	 * ------------------------------------------
	 */
	ArrayList<View> mMapViewList = new ArrayList<View>();
	BitmapDescriptor bdA;
	LinearLayout pagetop_layout_back;
	TextView pagetop_tv_name;
	LinearLayout map_search_myarea;
	LinearLayout map_search_luxian;

	LatLng stadiumpoint;
	LatLng mypoint;
	String myaddress;
	String stadium_name;
	double latitude;
	double longitude;
	String min_price;
	boolean luxiainstatus = false;
	Handler handler;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.map_search_myarea:
			reloadMapData();
			break;
		case R.id.map_search_luxian:
			handler.sendEmptyMessage(LUXIAN);
			break;
		default:
			break;
		}
	}

	protected void loadViewLayout() {
		setContentView(R.layout.stadium_detail_map);
		CommonUtils.getInstance().addActivity(this);
	}

	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		map_search_myarea = (LinearLayout) findViewById(R.id.map_search_myarea);
		map_search_luxian = (LinearLayout) findViewById(R.id.map_search_luxian);
	}

	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		map_search_myarea.setOnClickListener(this);
		map_search_luxian.setOnClickListener(this);
		initHandler();
		initNaviMap();
		initDate();
		initMapView();
	}

	private void initNaviMap() {
		// 初始化导航引擎
		// BaiduNaviManager.getInstance().initEngine(this, getSdcardDir(),
		// mNaviEngineInitListener, ACCESS_KEY, mKeyVerifyListener);
		BaiduNaviManager.getInstance().initEngine(StadiumDetailMapActivity.this, getSdcardDir(),
				mNaviEngineInitListener, new LBSAuthManagerListener() {
					@Override
					public void onAuthResult(int status, String msg) {
					}
				});
	}

	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {
		public void engineInitSuccess() {
		}

		public void engineInitStart() {
		}

		public void engineInitFail() {
		}
	};

	protected void processLogic() {
	}

	/**
	 * 对搜索到的结果进行处理 3公交 2驾车
	 */
	private final int LUXIAN = 1;
	private final int GPSNAVIGATION = 2;

	public void initHandler() {
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case LUXIAN:
					if (mypoint == null || stadiumpoint == null) {
						getDateTiShiDilog();
						break;
					}
					launchNavigator();
					break;
				case GPSNAVIGATION:
					if (!ConfigUtils.getInstance().isNetWorkAvaiable(StadiumDetailMapActivity.this)) {
						CommonUtils.getInstance().initToast(StadiumDetailMapActivity.this,
								getString(R.string.net_tishi));
						break;
					}
					if (mypoint == null || stadiumpoint == null) {
						getDateTiShiDilog();
						break;
					}
					// 设置起终点信息，对于tranist search 来说，城市名无意义
					PlanNode stNode = PlanNode.withLocation(mypoint);
					PlanNode enNode = PlanNode.withLocation(stadiumpoint);
					if (enNode != null && stNode != null)
						mSearch.drivingSearch((mDrivingRoutePlanOption).from(stNode).to(enNode));
					break;
				}
			}
		};
	}

	/**
	 * 指定导航起终点启动GPS导航.起终点可为多种类型坐标系的地理坐标。 前置条件：导航引擎初始化成功
	 */
	private void launchNavigator() {
		// 这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标
		BNaviPoint startPoint = new BNaviPoint(mypoint.longitude, mypoint.latitude, myaddress,
				BNaviPoint.CoordinateType.BD09_MC);
		BNaviPoint endPoint = new BNaviPoint(stadiumpoint.longitude, stadiumpoint.latitude, stadium_name,
				BNaviPoint.CoordinateType.BD09_MC);
		// BNaviPoint startPoint = new BNaviPoint(116.30142, 40.05087,
		// "百度大厦", BNaviPoint.CoordinateType.GCJ02);
		// BNaviPoint endPoint = new BNaviPoint(116.39750, 39.90882,
		// "北京天安门", BNaviPoint.CoordinateType.GCJ02);

		BaiduNaviManager.getInstance().launchNavigator(this, startPoint, // 起点（可指定坐标系）
				endPoint, // 终点（可指定坐标系）
				NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
				true, // 真实导航
				BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
				new OnStartNavigationListener() { // 跳转监听

					@Override
					public void onJumpToNavigator(Bundle configParams) {
						Intent intent = new Intent(StadiumDetailMapActivity.this, BNavigatorActivity.class);
						intent.putExtras(configParams);
						startActivity(intent);
					}

					@Override
					public void onJumpToDownloader() {
					}
				});
	}

	/**
	 * 初始化
	 */
	private void initDate() {
		Intent intent = getIntent();
		if (intent != null) {
			latitude = Double.parseDouble(intent.getStringExtra("latitude"));
			longitude = Double.parseDouble(intent.getStringExtra("longitude"));
			min_price = intent.getStringExtra("min_price");
			stadium_name = intent.getStringExtra("stadium_name");
		}
		String name = stadium_name;
		if (!TextUtils.isEmpty(name) && name.length() > 12) {
			name = name.substring(0, 8) + "...";
		}
		pagetop_tv_name.setText(name);
	}

	/**
	 * 导航时无起始点坐标定位提示
	 */
	private void getDateTiShiDilog() {
		try {
			final Dialog mDialog = new Dialog(this, R.style.dialog);
			if (mDialog != null) {
				mDialog.show();
				mDialog.setCanceledOnTouchOutside(true);
				Window window = mDialog.getWindow();
				WindowManager.LayoutParams wl = window.getAttributes();
				window.setAttributes(wl);
				window.setGravity(Gravity.CENTER);
				mDialog.setContentView(R.layout.dialog_stadiumdatetishi);
				TextView tv = (TextView) mDialog.findViewById(R.id.stadiumshishi_tishi);
				TextView tvsure = (TextView) mDialog.findViewById(R.id.stadiumshishi_tv_sure);
				tv.setText(getString(R.string.stadium_detail_notcity_tishi));
				tvsure.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						mDialog.dismiss();
					}
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

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
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);
		mBaiduMap.setMapStatus(msu);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 隐藏缩放控件
		int childCount = mMapView.getChildCount();
		if (childCount >= 3) {
			mMapView.removeViewAt(3);
			mMapView.removeViewAt(2);
			// View child = mMapView.getChildAt(2);
			// child.setVisibility(View.GONE);
		}
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		// 创建一个定位客户端的参数
		option = new LocationClientOption();
		option.setOpenGps(true);// 是否打开GPS
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
		mLocClient.setLocOption(option);// 设置给定位客户端
		mLocClient.start();// 启动定位客户端
		// 修改为自定义marker
		BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromAssetWithDpi("bitmapDescriptor.png");
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(LocationMode.NORMAL, true, mCurrentMarker));
		// 地图点击事件处理
		mBaiduMap.setOnMapClickListener(this);
		// 初始化搜索模块，注册事件监听
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);
		mDrivingRoutePlanOption = new DrivingRoutePlanOption();
	}

	/**
	 * 更新当前位置
	 */
	private void reloadMapData() {
		if (mypoint != null) {

			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(mypoint);
			if (u != null && mBaiduMap != null) {
				// mBaiduMap.animateMapStatus(u);//以动画方式更新地图状态，动画耗时 300 ms
				mBaiduMap.setMapStatus(u);// 改变地图状态
			}
		} else {
			getDateTiShiDilog();
		}

	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {
		/**
		 * 接收定位信息
		 */
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (isFirstLoc && location != null) {
				String city_ = location.getCity();
				int c = location.getLocType();
				if (mBaiduMap != null && !TextUtils.isEmpty(city_) && c != 62) {
					double longitude_me = location.getLongitude();
					double latitude_me = location.getLatitude();
					MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
							// 此处设置开发者获取到的方向信息，顺时针0-360
							.direction(0).latitude(latitude_me).longitude(longitude_me).build();
					mBaiduMap.setMyLocationData(locData);
					locData = null;
					// 转换经纬度
					mypoint = new LatLng(latitude_me, longitude_me);
					myaddress = location.getAddrStr();
					// 设置中心点
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(mypoint);
					mBaiduMap.animateMapStatus(u);
				}
				initMyOverlay();
			}
			option.setOpenGps(false);
			isFirstLoc = false;
		}

		/**
		 * 接收查找或搜索兴趣点的信息
		 */
		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	/**
	 * 添加场馆坐标点
	 */
	private void initMyOverlay() {
		stadiumpoint = new LatLng(latitude, longitude);

		if (mBaiduMap != null) {
			mBaiduMap.clear();
			mBaiduMap.setMyLocationEnabled(false);
		}
		luxiainstatus = true;
		Message msg = handler.obtainMessage();
		msg.what = GPSNAVIGATION;
		handler.handleMessage(msg);

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
	public void onGetDrivingRouteResult(DrivingRouteResult result) {

		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			if (mBaiduMap != null) {
				DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
				if (overlay != null) {
					mBaiduMap.setOnMarkerClickListener(overlay);
					overlay.setData(result.getRouteLines().get(0));
					overlay.addToMap();
					overlay.zoomToSpan();
				}
			}
		}

	}

	// 定制RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
			if (baiduMap != null) {
				MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);
				baiduMap.setMapStatus(msu);
			}
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.map_icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.map_icon_en);
			}
			return null;
		}
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult arg0) {

	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult arg0) {

	}

	@Override
	public void onMapClick(LatLng arg0) {
		// mBaiduMap.hideInfoWindow();

	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		return false;
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
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
			mMapView.onDestroy();
			mMapView = null;
			myListener = null;
			option = null;
			mSearch = null;
			mDrivingRoutePlanOption = null;
			// 回收 bitmap 资源
			bdA.recycle();
			if (mMapViewList != null && mMapViewList.size() != 0) {
				for (View iterable_element : mMapViewList) {
					iterable_element = null;
				}
				mMapViewList.clear();
				mMapViewList = null;
			}
			stadiumpoint = null;
			mypoint = null;
		} catch (Exception e) {
		}
		super.onDestroy();
	}

	private void nowFinish() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			nowFinish();
		}
		return false;
	}

}
