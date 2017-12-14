package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.RecordDetailBusiness;
import com.KiwiSports.business.VenuesUsersBusiness;
import com.KiwiSports.business.RecordDetailBusiness.GetRecordDetailCallback;
import com.KiwiSports.business.VenuesUsersBusiness.GetVenuesUsersCallback;
import com.KiwiSports.control.adapter.MainPropertyAdapter;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.MyGridView;
import com.KiwiSports.utils.PriceUtils;
import com.KiwiSports.utils.parser.MainsportParser;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ZoomControls;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：
 */
public class RecordDetailActivity extends BaseActivity implements BDLocationListener {

	private HashMap<String, String> mhashmap;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String token;
	private String access_token;
	private LinearLayout pagetop_layout_back;
	private String sporttype;
	private TextView pagetop_tv_name;
	private TextView pagetop_tv_you;
	private String posid;
	private TextView tv_map;
	private TextView tv_mapline;
	private TextView tv_date;
	private TextView tv_dateline;
	boolean mapStatus = true;
	private String record_id;
	private FrameLayout layout_map;
	private LinearLayout layout_date;
	private MyGridView map_property;
	private LinearLayout layoutmap_property;
	private MyGridView date_property;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.pagetop_tv_you:
			Intent intent = new Intent(this, RecordDetailYouActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("posid", posid);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		case R.id.tv_map:
			mapStatus = true;
			initBottom();
			break;
		case R.id.tv_date:
			mapStatus = false;
			initBottom();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.record_detail);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);

		layout_map = (FrameLayout) findViewById(R.id.layout_map);
		map_property = (MyGridView) findViewById(R.id.map_property);
		layoutmap_property = (LinearLayout) findViewById(R.id.layoutmap_property);
		layout_date = (LinearLayout) findViewById(R.id.layout_date);
		date_property = (MyGridView) findViewById(R.id.date_property);
		tv_map = (TextView) findViewById(R.id.tv_map);
		tv_mapline = (TextView) findViewById(R.id.tv_mapline);
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_dateline = (TextView) findViewById(R.id.tv_dateline);
		initBottom();
	}

	@SuppressLint("NewApi")
	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		tv_map.setOnClickListener(this);
		tv_date.setOnClickListener(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText(getString(R.string.record_details));
		Intent intent = getIntent();
		posid = intent.getExtras().getString("posid", "");
		record_id = intent.getExtras().getString("record_id", "");
		String name = intent.getExtras().getString("name", "");
		sporttype = intent.getExtras().getString("sporttype", "");
		pagetop_tv_name.setText(name);
		if (sporttype.equals("run")) {
			pagetop_tv_you.setVisibility(View.GONE);
		}
	}

	private void initBottom() {
		tv_map.setTextColor(getResources().getColor(R.color.white_light));
		tv_date.setTextColor(getResources().getColor(R.color.white_light));
		tv_mapline.setVisibility(View.INVISIBLE);
		tv_dateline.setVisibility(View.INVISIBLE);
		if (mapStatus) {
			tv_map.setTextColor(getResources().getColor(R.color.white));
			tv_mapline.setVisibility(View.VISIBLE);
			layout_date.setVisibility(View.GONE);
			layout_map.setVisibility(View.VISIBLE);
		} else {
			tv_date.setTextColor(getResources().getColor(R.color.white));
			tv_dateline.setVisibility(View.VISIBLE);
			layout_date.setVisibility(View.VISIBLE);
			layout_map.setVisibility(View.GONE);
		}
	}

	private ProgressDialog mDialog;
	protected RecordInfo mRecordInfo;
	protected ArrayList<MainLocationItemInfo> allpointList;
	protected ArrayList<LatLng> allpointLngMapList;
	private ArrayList<MainSportInfo> mMpropertyList;
	private String sportsType;
	private ArrayList<MainSportInfo> mpropertytwnList;
	private String duration;
	private String distanceTraveled;
	private String matchSpeed;
	private String freezeDuration;
	private String nSteps;
	private String maxMatchSpeed;
	private String averageMatchSpeed;
	private String currentAltitude;
	private String averageSpeed;
	private String lapCount;
	private String topSpeed;
	private String downHillDistance;
	private String verticalDistance;
	private String maxSlope;

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void processLogic() {
		initMapView();
	}

	protected void processLogicLLLL() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("record_id", record_id);
		new RecordDetailBusiness(this, mhashmap, new GetRecordDetailCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mRecordInfo = (RecordInfo) dataMap.get("mRecordInfo");
						allpointList = (ArrayList<MainLocationItemInfo>) dataMap.get("allpointList");
						allpointLngMapList = (ArrayList<LatLng>) dataMap.get("allpointLngMapList");
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				if (mRecordInfo != null) {
					layoutmap_property.setVisibility(View.VISIBLE);
					setSportPropertyList();
				}
				addMarker();

				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	private void setSportPropertyList() {
		MainsportParser mMainsportParser = new MainsportParser();
		ArrayList<MainSportInfo> mallsportList = mMainsportParser.parseJSON(this);
		sportsType = mRecordInfo.getSportsType();
		for (int i = 0; i < mallsportList.size(); i++) {
			MainSportInfo mMainSportInfo = mallsportList.get(i);
			if (sportsType.equals(mMainSportInfo.getEname())) {
				mMpropertyList = mMainSportInfo.getMpropertyList();
				mpropertytwnList = mMainSportInfo.getMpropertytwnList();
				break;
			}
		}
		showCurrentPropertyValue();
	}

	/**
	 * 显示属性值
	 */
	private void showCurrentPropertyValue() {
		distanceTraveled = mRecordInfo.getDistanceTraveled();
		long runingTimestamp = mRecordInfo.getDuration();
		duration = DatesUtils.getInstance().formatTimes(runingTimestamp * 1000);
		matchSpeed = mRecordInfo.getMatchSpeed();
		long freezeTimestamp = mRecordInfo.getFreezeDuration();
		freezeDuration = DatesUtils.getInstance().formatTimes(freezeTimestamp * 1000);
		nSteps = mRecordInfo.getnSteps();
		maxMatchSpeed = mRecordInfo.getMaxMatchSpeed();
		averageMatchSpeed = mRecordInfo.getAverageMatchSpeed();
		currentAltitude = mRecordInfo.getCurrentAltitude();
		averageSpeed = mRecordInfo.getAverageSpeed();
		averageSpeed = PriceUtils.getInstance().getPriceTwoDecimal(Double.valueOf(averageSpeed), 2);
		lapCount = mRecordInfo.getLopCount();
		topSpeed = mRecordInfo.getTopSpeed();
		topSpeed = PriceUtils.getInstance().getPriceTwoDecimal(Double.valueOf(topSpeed), 2);
		downHillDistance = mRecordInfo.getDownHillDistance();
		verticalDistance = mRecordInfo.getVerticalDistance();
		maxSlope = mRecordInfo.getMaxSlope();

		mpropertytwnList.get(0).setValue(distanceTraveled + "");
		mpropertytwnList.get(1).setValue(duration);
		if (sportsType.equals("run")) {
			for (int i = 0; i < mMpropertyList.size(); i++) {
				switch (i) {
				case 0:
					mMpropertyList.get(i).setValue(matchSpeed);
					break;
				case 1:
					mMpropertyList.get(i).setValue(freezeDuration);
					break;
				case 2:
					mMpropertyList.get(i).setValue(nSteps + "");
					break;
				case 3:
					mMpropertyList.get(i).setValue(maxMatchSpeed);
					break;
				case 4:
					mMpropertyList.get(i).setValue(averageMatchSpeed);
					break;
				case 5:
					mMpropertyList.get(i).setValue(currentAltitude + "");
					break;
				default:
					break;
				}
			}

		} else if (sportsType.equals("riding")) {
			for (int i = 0; i < mMpropertyList.size(); i++) {
				switch (i) {
				case 0:
					mMpropertyList.get(i).setValue(matchSpeed);
					break;
				case 1:
					mMpropertyList.get(i).setValue(freezeDuration);
					break;
				case 2:
					mMpropertyList.get(i).setValue(maxMatchSpeed + "");
					break;
				case 3:
					mMpropertyList.get(i).setValue(averageMatchSpeed);
					break;
				case 4:
					mMpropertyList.get(i).setValue(currentAltitude + "");
					break;
				default:
					break;
				}
			}
		} else if (sportsType.equals("walk")) {
			for (int i = 0; i < mMpropertyList.size(); i++) {
				switch (i) {
				case 0:
					mMpropertyList.get(i).setValue(averageSpeed + "");
					break;
				case 1:
					mMpropertyList.get(i).setValue(freezeDuration);
					break;
				case 2:
					mMpropertyList.get(i).setValue(nSteps + "");
					break;
				case 3:
					mMpropertyList.get(i).setValue(currentAltitude + "");
					break;
				default:
					break;
				}
			}
		} else if (sportsType.equals("sky")) {
			mpropertytwnList.get(1).setValue(lapCount);
			for (int i = 0; i < mMpropertyList.size(); i++) {
				switch (i) {
				case 0:
					mMpropertyList.get(i).setValue(duration);
					break;
				case 1:
					mMpropertyList.get(i).setValue(downHillDistance);
					break;
				case 2:
					mMpropertyList.get(i).setValue(verticalDistance + "");
					break;
				case 3:
					mMpropertyList.get(i).setValue(topSpeed + "");
					break;
				case 4:
					mMpropertyList.get(i).setValue(maxSlope);
					break;
				case 5:
					mMpropertyList.get(i).setValue(currentAltitude + "");
					break;
				default:
					break;
				}
			}
		} else {
			for (int i = 0; i < mMpropertyList.size(); i++) {
				switch (i) {
				case 0:
					mMpropertyList.get(i).setValue(averageSpeed + "");
					break;
				case 1:
					mMpropertyList.get(i).setValue(freezeDuration);
					break;
				case 2:
					mMpropertyList.get(i).setValue(topSpeed + "");
					break;
				case 3:
					mMpropertyList.get(i).setValue(averageSpeed + "");
					break;
				case 4:
					mMpropertyList.get(i).setValue(currentAltitude + "");
					break;
				default:
					break;
				}
			}
		}
		mpropertytwnList.addAll(mMpropertyList);
		MainPropertyAdapter mMainSportAdapter = new MainPropertyAdapter(this, mpropertytwnList);
		map_property.setAdapter(mMainSportAdapter);

		MainPropertyAdapter mdate_propertyAdapter = new MainPropertyAdapter(this, mpropertytwnList);
		date_property.setAdapter(mdate_propertyAdapter);
	}

	public void addMarker() {
		if (allpointLngMapList != null && allpointLngMapList.size() >= 2) {
			// 添加路线（轨迹）
			PolylineOptions polyline = new PolylineOptions().width(10)
					.color(MainStartActivity.mHomeActivity.getResources().getColor(R.color.blue))
					.points(allpointLngMapList);
			mBaiduMap.addOverlay(polyline);
		}
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
	private BitmapDescriptor realtimeBitmap;
	private Overlay overlay;
	private BitmapDescriptor mmorenMarker;

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
		option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
		mLocClient.setLocOption(option);// 设置给定位客户端
		mLocClient.start();// 启动定位客户端
		mmorenMarker = BitmapDescriptorFactory.fromResource(R.drawable.menutab_location_normal);
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
					processLogicLLLL();
					getVenuesUsers();
				} catch (Exception e) {
				}
				option.setOpenGps(false);
				isFirstLoc = false;
			}
		} catch (Exception e) {
		}

	}

	/**
	 * 设置中心点的焦点
	 */
	private void moveToCenter() {
		if (latitude_me > 0) {
			if (null != overlay) {
				overlay.remove();
			}

			if (null == realtimeBitmap) {
				realtimeBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_myposition_map);
			}
			LatLng mypoint = new LatLng(latitude_me, longitude_me);
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
						ArrayList<VenuesUsersInfo> mMapList = (ArrayList<VenuesUsersInfo>) dataMap.get("mlist");
						initMyOverlay(mMapList);
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	/**
	 * 场馆定位
	 */
	private void initMyOverlay(ArrayList<VenuesUsersInfo> mMapList) {
		try {
			if (mMapList != null && mMapList.size() != 0) {
				for (int i = 0; i < mMapList.size(); i++) {
					double latitude = mMapList.get(i).getLatitude();
					double longitude = mMapList.get(i).getLongitude();
					String Album_url = mMapList.get(i).getAlbum_url();
					String userid = mMapList.get(i).getUid();
					System.err.println(longitude + "      " + longitude);
					if (latitude > 0 && longitude > 0 && !userid.equals(uid)) {
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

		View convertView = LayoutInflater.from(context).inflate(R.layout.venues_map_marker, null);
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
