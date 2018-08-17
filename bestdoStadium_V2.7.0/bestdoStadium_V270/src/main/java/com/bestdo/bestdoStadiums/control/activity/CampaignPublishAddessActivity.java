/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignListBusiness;
import com.bestdo.bestdoStadiums.business.CampaignListBusiness.GetCampaignListCallback;
import com.bestdo.bestdoStadiums.business.CampaignQianDaoBusiness;
import com.bestdo.bestdoStadiums.business.CampaignQianDaoBusiness.CampaignQianDaoCallback;
import com.bestdo.bestdoStadiums.control.adapter.CampaignListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CampaingQuartAdapter;
import com.bestdo.bestdoStadiums.control.adapter.PoiAdapter;
import com.bestdo.bestdoStadiums.control.adapter.PoiSearchAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserCollectAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午5:53:39
 * @Description 类描述：活动签到
 */
public class CampaignPublishAddessActivity extends Activity implements BDLocationListener, OnGetGeoCoderResultListener,
		BaiduMap.OnMapStatusChangeListener, TextWatcher, OnClickListener {

	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private ListView poisLL;
	/**
	 * 定位模式
	 */
	private MyLocationConfiguration.LocationMode mCurrentMode;
	/**
	 * 定位端
	 */
	private LocationClient mLocClient;
	/**
	 * 是否是第一次定位
	 */
	private boolean isFirstLoc = true;
	/**
	 * 定位坐标
	 */
	private LatLng locationLatLng;
	/**
	 * 定位城市
	 */
	private String city;
	/**
	 * 反地理编码
	 */
	private GeoCoder geoCoder;
	/**
	 * 界面上方布局
	 */
	private RelativeLayout topRL;
	/**
	 * 搜索地址输入框
	 */
	private EditText searchAddress;
	/**
	 * 搜索输入框对应的ListView
	 */
	private ListView searchPois;
	protected List<PoiInfo> poiInfos;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_you;
	private TextView pagetop_tv_name;
	protected LatLng select_LatLng;
	protected String address;
	private ImageView my_location;
	private LatLng mypoint;
	private PoiAdapter poiAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		// 另外防止屏幕锁屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.campaign_publish_address);
		initView();

	}

	@Override
	public void onClick(View arg0) {
		CommonUtils.getInstance().closeSoftInput(this);
		switch (arg0.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.pagetop_tv_you:
			if (select_LatLng != null) {
				// select_LatLng
				Intent intent = new Intent();
				intent.putExtra("longitude_select", select_LatLng.longitude);
				intent.putExtra("latitude_select", select_LatLng.latitude);
				intent.putExtra("address", address);
				setResult(1, intent);
				doback();
			} else if (!TextUtils.isEmpty(searchAddress.getText().toString())) {
				Intent intent = new Intent();
				intent.putExtra("longitude_select", 0);
				intent.putExtra("latitude_select", 0);
				intent.putExtra("address", searchAddress.getText().toString());
				setResult(1, intent);
				doback();
			}
		case R.id.my_location:
			reloadMapData();
			break;
		default:
			break;
		}
	}

	private void initView() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		my_location = (ImageView) findViewById(R.id.my_location);
		pagetop_tv_name.setText("活动地点");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText("确定");
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		my_location.setOnClickListener(this);
		mMapView = (MapView) findViewById(R.id.main_bdmap);
		mBaiduMap = mMapView.getMap();

		poisLL = (ListView) findViewById(R.id.main_pois);

		topRL = (RelativeLayout) findViewById(R.id.main_top_RL);

		searchAddress = (EditText) findViewById(R.id.main_search_address);

		searchPois = (ListView) findViewById(R.id.main_search_pois);

		// 定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder().zoom(18).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);

		// 地图状态改变相关监听
		mBaiduMap.setOnMapStatusChangeListener(this);

		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 隐藏缩放控件
		int childCount = mMapView.getChildCount();
		if (childCount >= 3) {
			mMapView.removeViewAt(3);
			mMapView.removeViewAt(2);
		}
		// 定位图层显示方式
		mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

		/**
		 * 设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效 customMarker用户自定义定位图标
		 * enableDirection是否允许显示方向信息 locationMode定位图层显示方式
		 */
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));

		// 初始化定位
		mLocClient = new LocationClient(this);
		// 注册定位监听
		mLocClient.registerLocationListener(this);

		// 定位选项
		LocationClientOption option = new LocationClientOption();
		/**
		 * coorType - 取值有3个： 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系
		 * ：bd09ll
		 */
		option.setCoorType("bd09ll");
		// 设置是否需要地址信息，默认为无地址
		option.setIsNeedAddress(true);
		// 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
		// 可以用作地址信息的补充
		// option.setIsNeedLocationDescribe(true);
		// //设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
		// option.setIsNeedLocationPoiList(true);
		/**
		 * 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps)模式 Hight_Accuracy
		 * 高精度模式
		 */
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		// 设置是否打开gps进行定位
		option.setOpenGps(true);
		// 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
		option.setScanSpan(600 * 1000);

		// 设置 LocationClientOption
		mLocClient.setLocOption(option);

		// 开始定位
		mLocClient.start();

	}

	/**
	 * 更新当前位置
	 */
	private void reloadMapData() {
		// MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(mypoint);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(mypoint, 18);
		if (u != null && mBaiduMap != null) {
			// mBaiduMap.setMapStatus(u);// 改变地图状态
			mBaiduMap.animateMapStatus(u);
		}
	}

	private void doback() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 定位监听
	 * 
	 * @param bdLocation
	 */
	@Override
	public void onReceiveLocation(BDLocation bdLocation) {

		// 如果bdLocation为空或mapView销毁后不再处理新数据接收的位置
		if (bdLocation == null || mBaiduMap == null) {
			return;
		}
		if (bdLocation.getLocType() == 62) {
			bdLocation.setLatitude(39.91405);
			bdLocation.setLongitude(116.404269);
		}
		// 定位数据
		MyLocationData data = new MyLocationData.Builder()
				// 定位精度bdLocation.getRadius()
				.accuracy(bdLocation.getRadius())
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(bdLocation.getDirection())
				// 经度
				.latitude(bdLocation.getLatitude())
				// 纬度
				.longitude(bdLocation.getLongitude())
				// 构建
				.build();

		// 设置定位数据
		mBaiduMap.setMyLocationData(data);

		// 是否是第一次定位
		if (isFirstLoc) {
			isFirstLoc = false;
			LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
			mypoint = ll;
			MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);
			mBaiduMap.animateMapStatus(msu);
		}

		// 获取坐标，待会用于POI信息点与定位的距离
		locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
		// 获取城市，待会用于POISearch
		city = bdLocation.getCity();

		if (TextUtils.isEmpty(city)) {
			city = "北京";
		}
		// 文本输入框改变监听，必须在定位完成之后
		searchAddress.addTextChangedListener(this);

		// 创建GeoCoder实例对象
		geoCoder = GeoCoder.newInstance();
		// 发起反地理编码请求(经纬度->地址信息)
		ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
		// 设置反地理编码位置坐标
		reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
		geoCoder.reverseGeoCode(reverseGeoCodeOption);

		// 设置查询结果监听者
		geoCoder.setOnGetGeoCodeResultListener(this);
	}

	// 地理编码查询结果回调函数
	@Override
	public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
	}

	// 反地理编码查询结果回调函数
	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
		if (!isSelect) {
			final List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
			if (poiInfos != null && poiInfos.size() > 0) {
				/**
				 * 赋值选择的位置信息，防止反地理编码查询结果回调不同的问题
				 */
				if (slectPoiInfo != null) {
					poiInfos.get(0).location = slectPoiInfo.location;
					poiInfos.get(0).address = slectPoiInfo.address;
					poiInfos.get(0).name = slectPoiInfo.name;
				}
				select_LatLng = new LatLng(poiInfos.get(0).location.latitude, poiInfos.get(0).location.longitude);
				address = poiInfos.get(0).address + "-" + poiInfos.get(0).name;
				poiAdapter = new PoiAdapter(CampaignPublishAddessActivity.this, poiInfos);

				poiAdapter.setSelectIndex(0);
				poisLL.setAdapter(poiAdapter);
				poisLL.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						isSelect = true;
						poiAdapter.setSelectIndex(arg2);
						poiAdapter.notifyDataSetChanged();
						PoiInfo p = poiInfos.get(arg2);
						select_LatLng = new LatLng(p.location.latitude, p.location.longitude);
						address = p.address + "-" + p.name;
						if (TextUtils.isEmpty(address)) {
							address = p.name;
						}
						MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(select_LatLng, 18);
						mBaiduMap.animateMapStatus(msu);
					}
				});

			}
		}
		isSelect = false;

	}

	Boolean isSelect = false;
	protected PoiInfo slectPoiInfo;

	/**
	 * 手势操作地图，设置地图状态等操作导致地图状态开始改变
	 * 
	 * @param mapStatus
	 *            地图状态改变开始时的地图状态
	 */
	@Override
	public void onMapStatusChangeStart(MapStatus mapStatus) {
	}

	/**
	 * 地图状态变化中
	 * 
	 * @param mapStatus
	 *            当前地图状态
	 */
	@Override
	public void onMapStatusChange(MapStatus mapStatus) {
	}

	/**
	 * 地图状态改变结束
	 * 
	 * @param mapStatus
	 *            地图状态改变结束后的地图状态
	 */
	@Override
	public void onMapStatusChangeFinish(MapStatus mapStatus) {
		// 地图操作的中心点
		LatLng cenpt = mapStatus.target;
		if (select_LatLng != null) {
			cenpt = select_LatLng;
		}
		if (geoCoder != null && cenpt != null)
			geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
	}

	/**
	 * 输入框监听---输入之前
	 * 
	 * @param s
	 *            字符序列
	 * @param start
	 *            开始
	 * @param count
	 *            总计
	 * @param after
	 *            之后
	 */
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	/**
	 * 输入框监听---正在输入
	 * 
	 * @param s
	 *            字符序列
	 * @param start
	 *            开始
	 * @param before
	 *            之前
	 * @param count
	 *            总计
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	/**
	 * 输入框监听---输入完毕
	 * 
	 * @param s
	 */
	@Override
	public void afterTextChanged(Editable s) {
		if (s.length() == 0 || "".equals(s.toString())) {
			searchPois.setVisibility(View.GONE);
		} else {
			// 创建PoiSearch实例
			PoiSearch poiSearch = PoiSearch.newInstance();
			// 城市内检索
			PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption();
			// 关键字
			poiCitySearchOption.keyword(s.toString());
			// 城市
			poiCitySearchOption.city(city);
			// 设置每页容量，默认为每页10条
			poiCitySearchOption.pageCapacity(15);
			// 分页编号从0开始
			poiCitySearchOption.pageNum(0);
			poiSearch.searchInCity(poiCitySearchOption);
			// 设置<p>检索监听者
			poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {

				// poi 查询结果回调
				@Override
				public void onGetPoiResult(PoiResult poiResult) {
					poiInfos = poiResult.getAllPoi();
					if (poiInfos != null && poiInfos.size() > 0) {
					} else {
						poiInfos = new ArrayList<PoiInfo>();
					}
					PoiSearchAdapter poiSearchAdapter = new PoiSearchAdapter(CampaignPublishAddessActivity.this,
							poiInfos, locationLatLng);
					searchPois.setVisibility(View.VISIBLE);
					searchPois.setAdapter(poiSearchAdapter);

				}

				// poi 详情查询结果回调
				@Override
				public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
				}

			});
			searchPois.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					CommonUtils.getInstance().closeSoftInput(CampaignPublishAddessActivity.this);
					slectPoiInfo = poiInfos.get(arg2);
					select_LatLng = new LatLng(slectPoiInfo.location.latitude, slectPoiInfo.location.longitude);
					address = slectPoiInfo.address;
					MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(select_LatLng, 18);
					mBaiduMap.animateMapStatus(msu);
					searchPois.setVisibility(View.GONE);
				}
			});
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		if (mMapView != null)
			mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		if (mMapView != null)
			mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 退出时停止定位
		mLocClient.stop();
		// 退出时关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);

		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();

		// 释放资源
		if (geoCoder != null) {
			geoCoder.destroy();
		}

		mMapView = null;
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
