/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignListBusiness;
import com.bestdo.bestdoStadiums.business.CampaignListBusiness.GetCampaignListCallback;
import com.bestdo.bestdoStadiums.business.CampaignQianDaoBusiness;
import com.bestdo.bestdoStadiums.business.CampaignQianDaoBusiness.CampaignQianDaoCallback;
import com.bestdo.bestdoStadiums.control.adapter.CampaignListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserCollectAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午5:53:39
 * @Description 类描述：活动签到
 */
public class CampaignQianDaoActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private String uid;
	private String activity_id;
	private String time;
	private String name;
	private String longitude;
	private String latitude;
	private String longitude_end;
	private String latitude_end;
	private TextView campaign_qiandao_time;
	private TextView campaign_qiandao_name;
	private TextView campaign_qiandao_add;
	private ImageView campaign_qiandao_img, campaign_qiandao_img2;
	private TextView campaign_qiandao_text;
	boolean isFirstLoc = true;// 是否首次定位
	private LatLng startLatLng;
	boolean canQianDao = true;
	/**
	 * 1是已签到，2未签到
	 */
	private String is_sign;
	private String address;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.campaign_qiandao_img:
			if (canQianDao && is_sign.equals("2")) {
				qianDao();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.campaign_qiandao);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		campaign_qiandao_time = (TextView) findViewById(R.id.campaign_qiandao_time);
		campaign_qiandao_name = (TextView) findViewById(R.id.campaign_qiandao_name);
		campaign_qiandao_add = (TextView) findViewById(R.id.campaign_qiandao_add);
		campaign_qiandao_img = (ImageView) findViewById(R.id.campaign_qiandao_img);
		campaign_qiandao_text = (TextView) findViewById(R.id.campaign_qiandao_text);
		campaign_qiandao_img2 = (ImageView) findViewById(R.id.campaign_qiandao_img2);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		campaign_qiandao_img.setOnClickListener(this);
		initDate();
	}

	private void initDate() {

		pagetop_tv_name.setText("签到");
		// SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance()
		// .getBestDoInfoSharedPrefs(this);
		// uid = bestDoInfoSharedPrefs.getString("uid", "");

		Intent in = getIntent();
		uid = in.getStringExtra("uid");
		activity_id = in.getStringExtra("activity_id");
		time = in.getStringExtra("time");
		name = in.getStringExtra("name");
		longitude = in.getStringExtra("longitude");
		latitude = in.getStringExtra("latitude");
		is_sign = in.getStringExtra("is_sign");
		address = in.getStringExtra("address");
		campaign_qiandao_time.setText(time);
		campaign_qiandao_name.setText("参与活动：" + name);
		campaign_qiandao_add.setText(address);
		if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)) {
			startLatLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
			// startLatLng = new LatLng(Double.parseDouble("39.954781"),
			// Double.parseDouble("116.353455"));

		}
		setSignInShow();
	}

	HashMap<String, String> mhashmap;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private LocationClient mLocationClient;
	private LocationClientOption mOption;
	private String sign_type = "";// 1、签到成功 2、异常签到

	@Override
	protected void processLogic() {
		mMapView = (MapView) findViewById(R.id.qiandao_bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMyLocationEnabled(true);
		/*
		 * 百度地图 UI 控制器
		 */
		UiSettings mUiSettings = mBaiduMap.getUiSettings();
		mUiSettings.setCompassEnabled(false);// 隐藏指南针
		mUiSettings.setRotateGesturesEnabled(false);// 阻止旋转
		// 设置显示缩放比例
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(18.0f);
		mBaiduMap.setMapStatus(msu);
		// 隐藏缩放控件
		int childCount = mMapView.getChildCount();
		if (childCount >= 3) {
			mMapView.removeViewAt(3);
			mMapView.removeViewAt(2);
		}
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.setLocOption(getDefaultLocationClientOption());
		mLocationClient.registerLocationListener(mBDLocationListener);
		mLocationClient.start();
		mMapView.refreshDrawableState();
	}

	private void qianDao() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("sign_type", sign_type);
		mhashmap.put("activity_id", activity_id);
		mhashmap.put("longitude", longitude_end);
		mhashmap.put("latitude", latitude_end);
		new CampaignQianDaoBusiness(CampaignQianDaoActivity.this, mhashmap, new CampaignQianDaoCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
						mLocationClient.stop();
						canQianDao = false;
						is_sign = "1";
						setSignInShow();
						UiSettings settings = mBaiduMap.getUiSettings();
						settings.setAllGesturesEnabled(false);
					}
				}
			}
		});
	}

	private void setSignInShow() {
		if (is_sign.equals("1")) {
			campaign_qiandao_img.setBackground(getResources().getDrawable(R.drawable.campaign_qiandao_btn_nosel));
			campaign_qiandao_img2.setBackground(getResources().getDrawable(R.drawable.campaign_qiandao_success));
		} else {
			campaign_qiandao_img.setBackground(getResources().getDrawable(R.drawable.campaign_qiandao_btn));
		}
	}

	/***
	 * 
	 * @return DefaultLocationClientOption
	 */
	public LocationClientOption getDefaultLocationClientOption() {

		mOption = new LocationClientOption();
		mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		mOption.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
		mOption.setScanSpan(5000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		mOption.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		mOption.setNeedDeviceDirect(false);// 可选，设置是否需要设备方向结果
		mOption.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		mOption.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		// mOption.setIsNeedLocationDescribe(true);
		// //设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
		// mOption.setIsNeedLocationPoiList(true);
		return mOption;
	}

	private BDLocationListener mBDLocationListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100)
					.latitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			LatLng ll = null;
			if (isFirstLoc) {
				isFirstLoc = false;
				ll = new LatLng(location.getLatitude(), location.getLongitude());
				// 定义地图状态
				MapStatus mMapStatus = new MapStatus.Builder().target(ll).zoom(18).build();
				MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
				// 改变地图状态
				mBaiduMap.setMapStatus(mMapStatusUpdate);
				/**
				 * 
				 */
				if (location.getLocType() == 62) {
					MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(new LatLng(39.91405, 116.404269), 18);
					mBaiduMap.animateMapStatus(msu);
					String city_ = location.getCity();
					if (TextUtils.isEmpty(city_)) {
						getDateTiShiDilog();
					}
				}
				// -----------------------------------------------------------------------
				// 地图标注
				BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
				OverlayOptions options = new MarkerOptions().position(ll).icon(bitmapDescriptor);
				mBaiduMap.addOverlay(options);
			}

			// TODO Auto-generated method stub
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				StringBuffer sb = new StringBuffer(256);
				sb.append("time : ");
				/**
				 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
				 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
				 */
				sb.append(location.getTime());
				sb.append("\nerror code : ");
				sb.append(location.getLocType());
				sb.append("\nlatitude : ");
				sb.append(location.getLatitude());
				sb.append("\nlontitude : ");
				sb.append(location.getLongitude());
				sb.append("\nradius : ");
				sb.append(location.getRadius());
				sb.append("\nCountryCode : ");
				sb.append(location.getCityCode());
				sb.append("\ncity : ");
				sb.append(location.getCity());
				sb.append("\nDistrict : ");
				sb.append(location.getDistrict());
				sb.append("\nStreet : ");
				sb.append(location.getStreet());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\nDescribe: ");
				sb.append(location.getDirection());
				sb.append("\nPoi: ");
				if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
					sb.append("\nspeed : ");
					sb.append(location.getSpeed());// 单位：km/h
					sb.append("\nsatellite : ");
					sb.append(location.getSatelliteNumber());
					sb.append("\nheight : ");
					sb.append(location.getAltitude());// 单位：米
					sb.append("\ndescribe : ");
					sb.append("gps定位成功");
				} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
					// 运营商信息
					sb.append("\noperationers : ");
					sb.append(location.getOperators());
					sb.append("\ndescribe : ");
					sb.append("网络定位成功");
				} else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
					sb.append("\ndescribe : ");
					sb.append("离线定位成功，离线定位结果也是有效的");
				} else if (location.getLocType() == BDLocation.TypeServerError) {
					sb.append("\ndescribe : ");
					sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
				} else if (location.getLocType() == BDLocation.TypeNetWorkException) {
					sb.append("\ndescribe : ");
					sb.append("网络不同导致定位失败，请检查网络是否通畅");
				} else if (location.getLocType() == BDLocation.TypeCriteriaException) {
					sb.append("\ndescribe : ");
					sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
				}
				Log.e("msg", sb.toString());
				try {

					Double s = ConfigUtils.getInstance().DistanceOfTwoPoints(startLatLng.latitude,
							startLatLng.longitude, location.getLatitude(), location.getLongitude());
					longitude_end = location.getLongitude() + "";
					latitude_end = location.getLatitude() + "";
					s = s * 1000;
					Log.e("getDistance", s + "");
					if (s > 500) {
						campaign_qiandao_text.setText("未进入有效活动区域内");
						Drawable drawable = getResources().getDrawable(R.drawable.campaign_qiandao_nocan_img);
						drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
						campaign_qiandao_text.setCompoundDrawables(drawable, null, null, null);
						sign_type = "2";
					} else {
						campaign_qiandao_text.setText("已进入有效活动区域内");
						sign_type = "1";

						Drawable drawable = getResources().getDrawable(R.drawable.campaign_qiandao_can_img);
						// / 这一步必须要做,否则不会显示.
						drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
						campaign_qiandao_text.setCompoundDrawables(drawable, null, null, null);
						setSignInShow();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	/**
	 * 导航时无起始点坐标定位提示
	 */
	private void getDateTiShiDilog() {
		final Dialog mDialog = new Dialog(this, R.style.dialog);
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

	/**
	 * 计算两点之间距离
	 * 
	 * @param start
	 * @param end
	 * @return 米
	 */
	public double getDistance(LatLng start, LatLng end) {
		double lat1 = (Math.PI / 180) * start.latitude;
		double lat2 = (Math.PI / 180) * end.latitude;

		double lon1 = (Math.PI / 180) * start.longitude;
		double lon2 = (Math.PI / 180) * end.longitude;

		// double Lat1r = (Math.PI/180)*(gp1.getLatitudeE6()/1E6);
		// double Lat2r = (Math.PI/180)*(gp2.getLatitudeE6()/1E6);
		// double Lon1r = (Math.PI/180)*(gp1.getLongitudeE6()/1E6);
		// double Lon2r = (Math.PI/180)*(gp2.getLongitudeE6()/1E6);

		// 地球半径
		double R = 6371;

		// 两点间距离 km，如果想要米的话，结果*1000就可以了
		double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))
				* R;

		return d * 1000;
	}

	@Override
	protected void onResume() {
		Log.e("onResume-----", "onResumeonResumeonResumeonResume-----");
		super.onResume();
		mMapView.onResume();
		MobclickAgent.onPageStart("MainScreen");
	}

	protected void onPause() {
		Log.e("onPause-----", "onPauseonPauseonPauseonPauseonPauseonPause-----");
		super.onPause();
		mMapView.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		clearCache();
		RequestUtils.cancelAll(this);
		super.onDestroy();
		mLocationClient.unRegisterLocationListener(mBDLocationListener);
		mLocationClient.stop();
		mBaiduMap.setMyLocationEnabled(false);
		super.onDestroy();
		mMapView.onDestroy();
		mMapView = null;
	}

	private void clearCache() {
	}

	private void doback() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doback();
		}
		return false;
	}
}
