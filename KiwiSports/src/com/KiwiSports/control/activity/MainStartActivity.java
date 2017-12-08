package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.UpdateLocationBusiness;
import com.KiwiSports.business.VenuesMyAreaUsersBusiness;
import com.KiwiSports.business.UpdateLocationBusiness.GetUpdateLocationCallback;
import com.KiwiSports.business.VenuesMyAreaUsersBusiness.GetVenuesMyAreaUsersCallback;
import com.KiwiSports.control.adapter.MainPropertyAdapter;
import com.KiwiSports.control.view.LocationUtils;
import com.KiwiSports.control.view.StepCounterService;
import com.KiwiSports.control.view.StepDetector;
import com.KiwiSports.control.view.TrackUploadFragment;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.MyGridView;
import com.KiwiSports.utils.parser.MainsportParser;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.Trace;
import com.baidu.trace.TraceLocation;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述： 首页轨迹
 */
public class MainStartActivity extends FragmentActivity implements OnClickListener {

	private ImageView pagetop_iv_you;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String token;
	private String access_token;
	private LinearLayout layout_disquan;
	private TextView tv_distance;
	private TextView tv_quannum;
	private LinearLayout layout_bottom;
	private ImageView iv_start;
	private ImageView iv_pause;
	private ImageView iv_stop;
	private Intent service;
	private MyGridView mygridview_property;
	public static Context mHomeActivity;

	// -----------参数------------
	private ArrayList<MainSportInfo> mMpropertyList;
	private double longitude_me;
	private double latitude_me;
	private double distance;
	private String sporttype;
	private TextView tv_distancetitle;
	private TextView tv_quannumtitle;
	private TextView tv_quannumunit;
	private ArrayList<MainSportInfo> mpropertytwnList;
	private int sportindex;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_iv_you:
			
			Intent intent = new Intent(this, MainSportActivity.class);
			intent.putExtra("sportindex", sportindex);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivityForResult(intent, 1);
			CommonUtils.getInstance().setPageIntentAnim(intent, this); 
			
			break;
		case R.id.iv_start:
			startservice();
			btnStartStatus = true;
			btnContinueStatus = true;
			iv_start.setVisibility(View.GONE);
			iv_pause.setVisibility(View.VISIBLE);
			iv_stop.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_pause:
			btnStartStatus = true;
			if (btnContinueStatus) {
				setstopService();
				btnContinueStatus = false;
				iv_pause.setBackgroundResource(R.drawable.start);
			} else {
				startservice();
				btnContinueStatus = true;
				iv_pause.setBackgroundResource(R.drawable.pause);
			}
			iv_start.setVisibility(View.GONE);
			iv_pause.setVisibility(View.VISIBLE);
			iv_stop.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_stop:
			
			
			
			initStartView();
			break;
		default:
			break;
		}
	}

	/**
	 * 结束后初始化所有状态
	 */
	private void initStartView() {
		setstopService();
		btnStartStatus = false;
		btnContinueStatus = false;
		iv_start.setVisibility(View.VISIBLE);
		iv_pause.setVisibility(View.GONE);
		iv_stop.setVisibility(View.GONE);
		iv_pause.setBackgroundResource(R.drawable.pause);
		StepDetector.CURRENT_SETP = 0;
		BEFORECURRENT_SETP = -1;
		setSportPropertyList(sportindex);
		
		//清除轨迹
		if (null != mTrackUploadFragment.polylineoverlay) {
			 mTrackUploadFragment.polylineoverlay.remove();
		}
		TrackUploadFragment.showpointList.clear();
		TrackUploadFragment.uploadpointList.clear();
		TrackUploadFragment.allpointList.clear();
		mBaiduMap.clear();
	}
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		// 另外防止屏幕锁屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		loadViewLayout();
		findViewById();
		setListener();
		processLogic();
	}

	protected void loadViewLayout() {
		setContentView(R.layout.main_start);
		mHomeActivity = getApplicationContext();

	}

	protected void findViewById() {
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.mMapView);
		mBaiduMap = mMapView.getMap();

		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
		LinearLayout page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_run_img);
		CommonUtils.getInstance().setViewTopHeigth(mHomeActivity, page_top_layout);

		layout_disquan = (LinearLayout) findViewById(R.id.layout_disquan);
		tv_distance = (TextView) findViewById(R.id.tv_distance);
		tv_quannum = (TextView) findViewById(R.id.tv_quannum);
		tv_distancetitle= (TextView) findViewById(R.id.tv_distancetitle);
		tv_quannumtitle= (TextView) findViewById(R.id.tv_quannumtitle);
		tv_quannumunit= (TextView) findViewById(R.id.tv_quannumunit);
		

		layout_bottom = (LinearLayout) findViewById(R.id.layout_bottom);
		iv_start = (ImageView) findViewById(R.id.iv_start);
		iv_pause = (ImageView) findViewById(R.id.iv_pause);
		iv_stop = (ImageView) findViewById(R.id.iv_stop);
		mygridview_property = (MyGridView) findViewById(R.id.mygridview_property);
		CommonUtils.getInstance().setTextViewTypeface(this, tv_distance);
		CommonUtils.getInstance().setTextViewTypeface(this, tv_quannum);
	}

	protected void setListener() {
		pagetop_iv_you.setOnClickListener(this);
		iv_start.setOnClickListener(this);
		iv_pause.setOnClickListener(this);
		iv_stop.setOnClickListener(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
	}

	protected void processLogic() {
		sportindex=0;
		setSportPropertyList(sportindex);
		initGps();
		initOnEntityListener();
		initLbsClient();
	}

	/**
	 * 切换运动类型初始化显示属性
	 * @param sportindex
	 */
	private void setSportPropertyList(int sportindex) {
		MainsportParser mMainsportParser = new MainsportParser();
		ArrayList<MainSportInfo> mallsportList = mMainsportParser.parseJSON(this);
		MainSportInfo mMainSportInfo = mallsportList.get(sportindex);
		mMpropertyList = mMainSportInfo.getMpropertyList();
		mpropertytwnList= mMainSportInfo.getMpropertytwnList();
		sporttype=mMainSportInfo.getEname();
		MainPropertyAdapter mMainSportAdapter = new MainPropertyAdapter(this, mMpropertyList);
		mygridview_property.setAdapter(mMainSportAdapter);
		
		if(mpropertytwnList!=null&&mpropertytwnList.size()==2){
			tv_distancetitle.setText(mpropertytwnList.get(0).getCname());
			tv_distance.setText(mpropertytwnList.get(0).getValue());
			
			tv_quannum.setText(mpropertytwnList.get(1).getValue());
			tv_quannumtitle.setText(mpropertytwnList.get(1).getCname());
			tv_quannumunit.setText(mpropertytwnList.get(1).getUnit());
		}
		
	}
	/**
	 * 计算当前的属性值并更新
	 */
	private void showCurrentPropertyValue(){
		
	}

	private void startservice() {
		startTimestamp = System.currentTimeMillis();
		service = new Intent(this, StepCounterService.class);
		if (service != null) {
			startService(service);
		}
	}

	private void setstopService() {
		if (service != null)
			stopService(service);
	}

	/**
	 * =================定位======================
	 */
	private MapView mMapView;
	public static BaiduMap mBaiduMap;
	private LocationClient mLocClient;
	private LocationClientOption option;
	private HashMap<String, String> mhashmap;
	protected ArrayList<VenuesUsersInfo> mMapList;
	private BitmapDescriptor mmorenMarker;
	public static LBSTraceClient client;
	/**
	 * 轨迹服务
	 */
	public static Trace trace = null;

	/**
	 * entity标识
	 */
	public static String entityName = null;

	/**
	 * 鹰眼服务ID，开发者创建的鹰眼服务对应的服务ID
	 */
	public static long serviceId = 123776;

	/**
	 * 轨迹服务类型（0 : 不建立socket长连接， 1 : 建立socket长连接但不上传位置数据，2 : 建立socket长连接并上传位置数据）
	 */
	public int traceType = 2;
	private TrackUploadFragment mTrackUploadFragment;
	private LocationManager lm;
	public static OnEntityListener entityListener;
	boolean gpslocationListenerStatus = false;

	boolean firstnetLocationstatus = true;

	/**
	 * 切换暂停 继续结束 两种状态;true为执行状态
	 */
	protected boolean btnContinueStatus = false;
	/**
	 * 是否开始;true为执行状态
	 */
	protected boolean btnStartStatus = false;
	protected long runingTimestamp;
	private long startTimestamp;
	boolean firstUploadLocationstatus = true;
	private Thread thread;
	Handler handler = new Handler();
	int BEFORECURRENT_SETP = -1;
	private BitmapDescriptor realtimeBitmap;

	/**
	 * 初始化定位的SDK
	 */
	private void initLbsClient() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		// 初始化轨迹服务客户端
		client = new LBSTraceClient(this);
		/**
		 * LocationClientOption.LocationMode.Battery_Saving:低功耗定位 不用GPS (wifi
		 * 基站)
		 *
		 * LocationClientOption.LocationMode.Hight_Accuracy:高精度定位 全开GPS wifi 基站
		 *
		 * LocationClientOption.LocationMode.Device_Sensors 仅仅使用设备 GPS 不支持室内
		 */
		client.setLocationMode(com.baidu.trace.LocationMode.Device_Sensors);
		// 初始化entity标识
		entityName = "myTrace";
		// 初始化轨迹服务
		trace = new Trace(getApplicationContext(), serviceId, entityName, traceType);
		// 开启Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 隐藏Fragment
		if (mTrackUploadFragment != null) {
			transaction.hide(mTrackUploadFragment);
			mTrackUploadFragment.setOverlayOptions(null);
			mTrackUploadFragment.setPolyline(null);
		}
		// 清空地图覆盖物
		mBaiduMap.clear();
		if (mTrackUploadFragment == null) {
			mTrackUploadFragment = new TrackUploadFragment();
			transaction.add(R.id.fragment_content, mTrackUploadFragment);
		} else {
			transaction.show(mTrackUploadFragment);
		}
		mTrackUploadFragment.startRefreshThread(true);
		// TrackUploadFragment.addMarker();
		mBaiduMap.setOnMapClickListener(null);
		/**
		 * 使用的
		 * commit方法是在Activity的onSaveInstanceState()之后调用的，这样会出错，因为onSaveInstanceState
		 * 
		 * 方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存玩状态后再给它添加Fragment就会出错。解决办法就
		 * 
		 * 是把commit（）方法替换成 commitAllowingStateLoss()就行了，其效果是一样的。
		 */
		transaction.commitAllowingStateLoss();
		/*
		 * 百度地图 UI 控制器
		 */
		UiSettings mUiSettings = mBaiduMap.getUiSettings();
		mUiSettings.setCompassEnabled(false);// 隐藏指南针
		mUiSettings.setRotateGesturesEnabled(false);// 阻止旋转
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
		mLocClient = new LocationClient(mHomeActivity);
		mLocClient.registerLocationListener(new MyLocationListener());
		new Thread(new Runnable() {

			@Override
			public void run() {
				InitLocation();// 设置定位参数
			}
		}).start();
	}

	private void InitLocation() {
		option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(4000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		if (null == realtimeBitmap) {
			realtimeBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_myposition_map);
		}
	}

	/**
	 * 初始化OnEntityListener
	 */
	private void initOnEntityListener() {
		entityListener = new OnEntityListener() {
			// 请求失败回调接口
			@Override
			public void onRequestFailedCallback(String arg0) {
				System.out.println("entity请求失败回调接口消息 : " + arg0);

			}

			// 添加entity回调接口
			public void onAddEntityCallback(String arg0) {
				System.out.println("添加entity回调接口消息 : " + arg0);

			}

			// 查询entity列表回调接口
			@Override
			public void onQueryEntityListCallback(String message) {
				System.out.println("entityList回调消息 : " + message);
			}

			@Override
			public void onReceiveLocation(TraceLocation location) {
			}
		};
	}

	private void initGps() {

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 绑定监听状态
		lm.addGpsStatusListener(listener);
		/**
		 * 监听状态 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种 参数2，位置信息更新周期，单位毫秒
		 * 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息 参数4，监听
		 * 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
		 * 1秒更新一次，或最小位移变化超过1米更新一次；
		 * 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
		 */
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 3, gpslocationListener);
		// lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 2,
		// netlocationListener);
	}

	// 状态监听
	GpsStatus.Listener listener = new GpsStatus.Listener() {

		public void onGpsStatusChanged(int event) {
			switch (event) {
			// 第一次定位
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				Log.i("LocationMode", "第一次定位");
				break;
			// 卫星状态改变
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				Log.i("LocationMode", "卫星状态改变");
				break;
			// 定位启动
			case GpsStatus.GPS_EVENT_STARTED:
				Log.i("LocationMode", "定位启动");
				break;
			// 定位结束
			case GpsStatus.GPS_EVENT_STOPPED:
				Log.i("LocationMode", "定位结束");
				gpslocationListenerStatus = false;
				break;
			}
		};
	};

	/**
	 * 显示gps精度定位
	 * 
	 * @param location
	 */
	private void showGpsAccuracy(Location location) {
		mTrackUploadFragment.isInUploadFragment = false;
		gpslocationListenerStatus = false;
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			float accuracy = location.getAccuracy();
			System.err.println(accuracy);
			if (accuracy >= 30) {
				gpslocationListenerStatus = false;
			} else if (accuracy > 12 && accuracy < 30) {
				gpslocationListenerStatus = false;
				// userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps1);
				// userwalk_run_tv_gpsinfo.setText("信号较差数据准确度较低");
				// gpslocationListenerStatus = true;
				// firstnetLocationstatus = true;
			} else if (accuracy > 5 && accuracy <= 12) {
				if (btnContinueStatus) {
					mTrackUploadFragment.isInUploadFragment = true;
				}
				// userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps2);
				// userwalk_run_tv_gpsinfo.setText("信号一般");
				gpslocationListenerStatus = true;
				firstnetLocationstatus = true;
			} else {
				if (btnContinueStatus) {
					mTrackUploadFragment.isInUploadFragment = true;
				}
				// userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps3);
				// userwalk_run_tv_gpsinfo.setText("信号良好");
				gpslocationListenerStatus = true;
				firstnetLocationstatus = true;
			}
			// userwalk_run_tv_gpsinfo.setText(userwalk_run_tv_gpsinfo.getText()
			// + "gps" + accuracy);
		}
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			System.err.println("MyLocationListener==" + location.getLatitude() + "     ");
			if (!gpslocationListenerStatus) {
				if (location == null || !ConfigUtils.getInstance().getNetWorkStatus(mHomeActivity)) {
					mTrackUploadFragment.isInUploadFragment = false;
					// userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps0);
					// userwalk_run_tv_gpsinfo.setText("无信号");
					return;
				} else {
					if (location.getLocType() == 167 || location.getLatitude() == 0 || location.getRadius() > 100) {
						mTrackUploadFragment.isInUploadFragment = false;
						// userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps1);
						// userwalk_run_tv_gpsinfo.setText("信号较差数据准确度较低");
					} else {
						if (firstnetLocationstatus) {
							// 第一次不定位，防止漂浮坐标
							firstnetLocationstatus = false;
							return;

						}
						if (location.getLocType() == 62) {
							longitude_me = 116.404269;
							latitude_me = 39.91405;
						} else {
							longitude_me = location.getLongitude();
							latitude_me = location.getLatitude();
						}
						LatLng latLng = new LatLng(latitude_me, longitude_me);
						if (btnStartStatus && btnContinueStatus) {
							mTrackUploadFragment.isInUploadFragment = true;
							if (mTrackUploadFragment != null && mTrackUploadFragment.isInUploadFragment) {
								setline(latLng);
							}
						} else {
							showLocation(latLng);
						}

					}
				}
				System.err.println("location===" + location.getLatitude() + "    " + location.getLocType());
				// userwalk_run_tv_gpsinfo.setText("信号良好net"+"
				// "+location.getRadius()+" "+StepDetector.CURRENT_SETP);
			}
		}
	};

	private void setline(LatLng latLng) {
		boolean sensorAva = (BEFORECURRENT_SETP != StepDetector.CURRENT_SETP) && Constans.getInstance().mSensorState;
		if (sensorAva || !Constans.getInstance().mSensorState) {
			mTrackUploadFragment.showRealtimeTrack(latLng);
			distance = mTrackUploadFragment.sum_distance;
			// if (firstUploadDate) {
			// walking_status = STARTWALKSTAUS;
			// uploadrunedDate();
			// }
		}
	}

	private void showLocation(LatLng latLng) {
		try {
			//显示系统logo
//			MyLocationData locData = new MyLocationData.Builder()
//					// 此处设置开发者获取到的方向信息，顺时针0-360
//					.direction(0).latitude(latitude_me).longitude(longitude_me).build();
//			mBaiduMap.setMyLocationData(locData);
			
			/**
			 * 替换定位logo
			 */
			if (null != mTrackUploadFragment.overlay) {
				 mTrackUploadFragment.overlay.remove();
			}
			MarkerOptions overlayOptions = new MarkerOptions().position(latLng).icon(realtimeBitmap).zIndex(8).draggable(true);
			if (null != overlayOptions) {
				 mTrackUploadFragment.overlay = mBaiduMap.addOverlay(overlayOptions);
			}
			moveToCenter();
			initMyLocationUsers(latLng);
		} catch (Exception e) {
		}
	}

	/**
	 * 更新定位和刷新当前定位周边用户
	 * 
	 * @param latLng
	 */
	protected void initMyLocationUsers(final LatLng latLng) {
		if (thread == null) {
			thread = new Thread() {
				@Override
				public void run() {
					super.run();
					while (true) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Message msg = new Message();
						runingTimestamp = System.currentTimeMillis() - startTimestamp;
						/**
						 * 每十分钟上传一次数据
						 */
						if (firstUploadLocationstatus || (runingTimestamp % (5 * 60 * 1000) == 0)) {
							firstUploadLocationstatus = false;
							startTimestamp = runingTimestamp;
							getVenuesUsers();
							Message message = new Message();
							message.what = UPDATELOCATION;
							message.obj = latLng;
							mHandler.sendMessage(message);
							message = null;
						}
						handler.sendMessage(msg);// 通知主线程
					}
				}
			};
			thread.start();
		}
	}

	private LocationListener gpslocationListener = new LocationListener() {
		/**
		 * 位置信息变化时触发
		 */
		@Override
		public void onLocationChanged(Location location) {
			showGpsAccuracy(location);
			System.err.println("gpslocationListener==" + location.getLatitude() + "     " + location.getAccuracy());
			if (gpslocationListenerStatus && location != null) {
				// userwalk_run_tv_gpsinfo.setText(userwalk_run_tv_gpsinfo.getText()+"
				// gps "+location.getAccuracy());

				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				CoordinateConverter converter = new CoordinateConverter();
				converter.from(CoordType.GPS);
				converter.coord(latLng);
				LatLng sourceLatLng = converter.convert();
				longitude_me = sourceLatLng.longitude;
				latitude_me = sourceLatLng.latitude;

				if (btnStartStatus && btnContinueStatus) {
					setline(sourceLatLng);
				} else {
					showLocation(sourceLatLng);
				}

			}
		}

		/**
		 * GPS状态变化时触发
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
			// GPS状态为可见时
			case LocationProvider.AVAILABLE:
				Log.i("gps", "当前GPS状态为可见状态");
				break;
			// GPS状态为服务区外时
			case LocationProvider.OUT_OF_SERVICE:
				Log.i("gps", "当前GPS状态为服务区外状态");
				break;
			// GPS状态为暂停服务时
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Log.i("gps", "当前GPS状态为暂停服务状态");
				break;
			}
		}

		/**
		 * GPS开启时触发
		 */
		@Override
		public void onProviderEnabled(String provider) {
		}

		/**
		 * GPS禁用时触发
		 */
		@Override
		public void onProviderDisabled(String provider) {
		}

	};
	private final int UPDATELOCATION = 1;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATELOCATION:
				LatLng mypoint = (LatLng) msg.obj;
				double longitude_me = mypoint.longitude;
				double latitude_me = mypoint.latitude;
				updateLocation(longitude_me, latitude_me);
				break;
			}
		}
	};

	protected void updateLocation(double longitude_me, double latitude_me) {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("longitude", longitude_me + "");
		mhashmap.put("latitude", latitude_me + "");
		Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
		new UpdateLocationBusiness(this, mhashmap, new GetUpdateLocationCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	protected void getVenuesUsers() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("longitude", longitude_me + "");
		mhashmap.put("latitude", latitude_me + "");
		Log.e("map", "------------VenuesMyAreaUsersBusiness------------" + mhashmap);
		new VenuesMyAreaUsersBusiness(this, mhashmap, new GetVenuesMyAreaUsersCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mMapList = (ArrayList<VenuesUsersInfo>) dataMap.get("mlist");
						initMyOverlay();
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	/**
	 * 场馆定位
	 */
	private void initMyOverlay() {
		try {
			if (mMapList != null && mMapList.size() != 0) {
				for (int i = 0; i < mMapList.size(); i++) {
					double latitude = mMapList.get(i).getLatitude();
					double longitude = mMapList.get(i).getLongitude();
					String Album_url = mMapList.get(i).getAlbum_url();
					System.err.println(longitude + "      " + longitude);
					if (latitude > 0 && longitude > 0) {
						LatLng stadiumpoint = new LatLng(latitude, longitude);
						if (!TextUtils.isEmpty(Album_url)) {
							loadToBitmap(Album_url, stadiumpoint);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	@SuppressLint("NewApi")
	private void addMarker(LatLng point, Bitmap bitmap) {

		View convertView = LayoutInflater.from(mHomeActivity).inflate(R.layout.venues_map_marker, null);
		CircleImageView iv_head = (CircleImageView) convertView.findViewById(R.id.iv_head);
		iv_head.setImageBitmap(bitmap);
		iv_head.setImageAlpha(0);
		mmorenMarker = BitmapDescriptorFactory.fromView(convertView);
		OverlayOptions ooA = new MarkerOptions().position(point).icon(mmorenMarker).zIndex(5).draggable(false);
		Marker mMarker = (Marker) mBaiduMap.addOverlay(ooA);
	}

	Bitmap bitmap = null;

	public Bitmap loadToBitmap(String Album_url, final LatLng stadiumpoint) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.menutab_location_normal)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.menutab_location_normal)// 设置图片加载或解码过程中发生错误显示的图片
				.bitmapConfig(Bitmap.Config.RGB_565).build();
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
					addMarker(stadiumpoint, bitmap);
				}
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {

			}
		});
		return bitmap;
	}

	/**
	 * 设置中心点的焦点
	 */
	private void moveToCenter() {
		LatLng mypoint = new LatLng(latitude_me, longitude_me);
		MapStatus mMapStatus = new MapStatus.Builder().target(mypoint).zoom(16).build();
		MapStatusUpdate msUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		if (msUpdate != null && mBaiduMap != null) {
			mBaiduMap.animateMapStatus(msUpdate);// 以动画方式更新地图状态，动画耗时 300 ms
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
			handler.removeCallbacks(thread);
			if (mTrackUploadFragment != null) {
				mTrackUploadFragment.stopTrace();
				mTrackUploadFragment.uploadpointList.clear();
				mTrackUploadFragment.allpointList.clear();
			}
			if (lm != null && gpslocationListener != null) {
				lm.removeUpdates(gpslocationListener);
				gpslocationListener = null;
			}
			if (lm != null && listener != null) {
				lm.removeGpsStatusListener(listener);
				listener = null;
			}
			lm = null;
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
			setstopService();
		} catch (Exception e) {
		}
		super.onDestroy();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == 1) {
				sportindex = data.getIntExtra("sportindex", 0);
				setSportPropertyList(sportindex);
			}
		} catch (Exception e) {
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * 退出监听
	 */
	public void onBackPressed() {
		CommonUtils.getInstance().defineBackPressed(this, null, 0, Constans.getInstance().exit);
	}

}
