package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.bestdo.bestdoStadiums.business.StadiumBusiness;
import com.bestdo.bestdoStadiums.business.StadiumBusiness.GetStadiumCallback;
import com.bestdo.bestdoStadiums.control.activity.MainPersonActivity.MyLocationListener;
import com.bestdo.bestdoStadiums.control.adapter.StadiumAdapter;
import com.bestdo.bestdoStadiums.control.adapter.StadiumSelectJuliAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.StadiumSelectJuliInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-14 下午8:14:53
 * @Description 类描述：地图
 */
public class StadiumMapActivity extends BaseActivity implements OnMapClickListener {

	private ImageView stadiummap_iv_myarea;
	private ImageView pagetop_iv_you;
	private LinearLayout pagetop_layout_you;
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;

	private ArrayList<StadiumInfo> mMapList;// 场馆数据
	private String sportname;
	private String vip_price_id;

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_layout_you:
			nowFinish();
			break;
		case R.id.stadiummap_iv_myarea:
			moveToCenter();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.stadiummap);
		CommonUtils.getInstance().addActivity(this);

	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);

		stadiummap_iv_myarea = (ImageView) findViewById(R.id.stadiummap_iv_myarea);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		stadiummap_iv_myarea.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		intitData();
		initMapView();
	}

	private void intitData() {
		Intent in = getIntent();
		StadiumInfo mStadiumInfo = (StadiumInfo) in.getSerializableExtra("mStadiumInfo");
		if (mStadiumInfo != null) {
			mMapList = mStadiumInfo.getmMapList();
			sportname = in.getStringExtra("sportname");
			vip_price_id = in.getStringExtra("vip_price_id");
		}
		pagetop_tv_name.setText(sportname);
	}

	private void skipStadiumDetail(StadiumInfo mStadiumInfo) {
		Intent intent = new Intent(StadiumMapActivity.this, StadiumDetailActivity.class);
		Bundle bundle = new Bundle();
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		bundle.putString("mer_item_id", mStadiumInfo.getMer_item_id());
		bundle.putString("vip_price_id", vip_price_id);
		bundle.putString("mer_name", mStadiumInfo.getName());
		bundle.putString("cid", mStadiumInfo.getCid());
		bundle.putString("merid", mStadiumInfo.getMerid());

		intent.putExtras(bundle);
		startActivity(intent);
		CommonUtils.getInstance().setPageIntentAnim(intent, this);
	}

	/**
	 * =================定位======================
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private LocationClient mLocClient;
	private LocationClientOption option;
	public MyLocationListenner myListener = new MyLocationListenner();
	private BitmapDescriptor bdmin;

	private double longitude_me;
	private double latitude_me;
	private boolean isFirstLoc = true;
	private float STARTZOOM = 12.7f;
	// 每个Marker 对应的StadiumInfo
	HashMap<Marker, StadiumInfo> mBitmapDescriptorHashMap = new HashMap<Marker, StadiumInfo>();

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
		// 隐藏缩放控件
		int childCount = mMapView.getChildCount();
		if (childCount >= 3) {
			// 删除比例尺控件
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
		// option.disableCache(true);// 禁止启用缓存定位
		mLocClient.setLocOption(option);// 设置给定位客户端
		mLocClient.start();// 启动定位客户端
		// 修改为自定义marker
		// BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
		// .fromResource(R.drawable.map_myarea_con);
		BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromAssetWithDpi("bitmapDescriptor.png");
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(LocationMode.NORMAL, false, mCurrentMarker));
		/**
		 * 设置Marker的监听
		 */
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				if (mBitmapDescriptorHashMap.containsKey(marker)) {
					StadiumInfo mStadiumInfo = mBitmapDescriptorHashMap.get(marker);
					View view = showDefinedPopView(mStadiumInfo);
					LatLng ll = marker.getPosition();
					InfoWindow mInfoWindow = new InfoWindow(view, ll,
							ConfigUtils.getInstance().dip2px(StadiumMapActivity.this, -10));
					view.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							StadiumInfo mStadiumInfo = mBitmapDescriptorHashMap.get(marker);
							skipStadiumDetail(mStadiumInfo);
						}
					});
					mBaiduMap.showInfoWindow(mInfoWindow);
				}
				return false;
			}
		});
		// 地图点击事件处理
		mBaiduMap.setOnMapClickListener(this);
		bdmin = BitmapDescriptorFactory.fromResource(R.drawable.stadium_detail_map_con);
	}

	class MyLocationListenner implements BDLocationListener {

		/**
		 * 接收定位信息
		 */
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
						MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
								// 此处设置开发者获取到的方向信息，顺时针0-360
								.direction(0).latitude(latitude_me).longitude(longitude_me).build();
						mBaiduMap.setMyLocationData(locData);
						locData = null;
						// ---------1.首先判断当前城市是否有，没有则默认距离北京坐标，我的位置为当前定位-----------------------------
					} catch (Exception e) {
					}
					initMyOverlay();
					option.setOpenGps(false);
					isFirstLoc = false;
				}
			} catch (Exception e) {
			}
		}

		/**
		 * 接收查找或搜索兴趣点的信息
		 */
		public void onReceivePoi(BDLocation arg0) {

		}
	}

	/**
	 * 场馆定位
	 */
	private void initMyOverlay() {
		try {
			if (mMapList != null && mMapList.size() != 0) {
				for (int i = 0; i < mMapList.size(); i++) {
					String latitude = mMapList.get(i).getLatitude();
					String longitude = mMapList.get(i).getLongitude();
					System.err.println(longitude + "      " + longitude);
					if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)) {
						double longitude_ = Double.parseDouble(longitude);
						double latitude_ = Double.parseDouble(latitude);
						LatLng stadiumpoint = new LatLng(latitude_, longitude_);
						OverlayOptions ooA = new MarkerOptions().position(stadiumpoint).icon(bdmin).zIndex(5)
								.draggable(false);
						Marker mMarker = (Marker) mBaiduMap.addOverlay(ooA);
						mBitmapDescriptorHashMap.put(mMarker, mMapList.get(i));
						stadiumpoint = null;
					}
				}
			}
		} catch (Exception e) {
			nowFinish();
		} finally {
			moveToCenter();
		}
	}

	/**
	 * 自定义弹出层
	 * 
	 * @return
	 */
	View popMarkerView;

	private View showDefinedPopView(StadiumInfo mStadiumInfo) {
		popMarkerView = null;
		popMarkerView = getLayoutInflater().inflate(R.layout.stadiummap_marker_pop, null);
		popMarkerView.setPadding(0, ConfigUtils.getInstance().dip2px(this, 5), 0, 0);
		TextView map_popview_name = (TextView) popMarkerView.findViewById(R.id.map_popview_name);
		TextView map_popview_price = (TextView) popMarkerView.findViewById(R.id.map_popview_price);
		map_popview_name.setText(mStadiumInfo.getName());
		map_popview_price.setText(mStadiumInfo.getAddress());
		return popMarkerView;
	}

	/**
	 * 设置中心点的焦点
	 */
	private void moveToCenter() {
		LatLng mypoint = new LatLng(latitude_me, longitude_me);
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
	public void onMapClick(LatLng arg0) {
		mBaiduMap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		return false;
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
			myListener = null;
			popMarkerView = null;
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
