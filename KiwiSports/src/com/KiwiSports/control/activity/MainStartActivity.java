package com.KiwiSports.control.activity;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.KiwiSports.R;
import com.KiwiSports.business.RecordDatesloadBusiness;
import com.KiwiSports.business.RecordDatesloadBusiness.GetRecordDatesloadCallback;
import com.KiwiSports.business.UpdateLocationBusiness;
import com.KiwiSports.business.VenuesMyAreaUsersBusiness;
import com.KiwiSports.business.UpdateLocationBusiness.GetUpdateLocationCallback;
import com.KiwiSports.business.VenuesInfoBylicationBusiness;
import com.KiwiSports.business.VenuesInfoBylicationBusiness.GetInfoBylicationCallback;
import com.KiwiSports.business.VenuesMyAreaUsersBusiness.GetVenuesMyAreaUsersCallback;
import com.KiwiSports.control.adapter.MainPropertyAdapter;
import com.KiwiSports.control.view.StepCounterService;
import com.KiwiSports.control.view.StepDetector;
import com.KiwiSports.control.view.TrackUploadFragment;
import com.KiwiSports.control.view.mapsynth.MySpeechSynthesizer;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.model.TrackSaveInfo;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.model.db.TrackDBHelper;
import com.KiwiSports.model.db.TrackDBOpenHelper;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.GPSUtil;
import com.KiwiSports.utils.LanguageUtil;
import com.KiwiSports.utils.MyDialog;
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
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
	private LinearLayout layout_disquan;
	private TextView tv_distance;
	private TextView tv_quannum;
	private LinearLayout layout_bottom;
	private ImageView iv_start;
	private ImageView iv_pause;
	private ImageView iv_stop;

	private MyGridView mygridview_property;
	private TextView tv_distancetitle;
	private TextView tv_quannumtitle;
	private TextView tv_quannumunit;
	private ImageView iv_continue;
	private ImageView map_iv_zoom;
	private ImageView map_iv_shrink;
	private ImageView map_iv_mylocation;
	private LinearLayout layout_property;
	private LinearLayout page_top_layout;
	private ImageView pagetop_iv_center;
	private LinearLayout layoutall;
	private FrameLayout relat_map;
	private Activity mHomeActivity;

	private Intent service;
	private SharedPreferences bestDoInfoSharedPrefs;
	private MainPropertyAdapter mMainSportAdapter;
	public static Context mActivity;
	// -----------参数------------
	protected TrackDBOpenHelper mDB;
	private String uid;
	private String token;
	private String access_token;
	private String recordDatas;
	private double longitude_me;
	private double latitude_me;
	private ArrayList<MainSportInfo> mMpropertyList;
	private ArrayList<MainSportInfo> mpropertytwnList;
	/**
	 * 第一次定位，之后每5分钟一次
	 */
	boolean firstUploadLocationstatus = true;
	private long runingTimestamp;// 运动时间
	private long startTimestamp;// 开始时间
	private long pauseTimestamp;// 休息暂停时间
	private long initTimestamp;// 初始化时间
	private String sportsType;
	private int sportindex;
	// --------------
	private double distanceTraveled;// 总距离
	private int nSteps;// 步数
	private String duration;// 运动时间
	private String freezeDuration;// 休息时间
	protected String address;

	private String matchSpeed = "0";// 配速
	private String maxMatchSpeed = "0";// 最快配速 / 最大速度
	private String averageMatchSpeed = "0";// 平均配速
	private long matchSpeedTimestamp;// 配速时间戳
	private long maxMatchSpeedTimestamp;// 最快配速 / 最大速度时间戳
	private double minAltidue;// 最低海拔
	private double maxAltitude;// 最高海拔
	private int currentAltitude;// 当前海拔
	private int startAltitude;// 开始海拔
	private int beforeAltitude;// 前一个海拔
	private double currentAccuracy;// 精度
	private double Speed;// 速度
	private double averageSpeed;// 平均速度
	private double topSpeed;// 最高速度
	private double minSpeed;// 最小速度
	private double calorie;// 热量
	private String posid = "";// 场地id

	private String latitudeOffset = "";
	private String longitudeOffset = "";
	private String cableCarQueuingDuration = "";// 缆车排队时间
	private String wrestlingCount = "";// 摔跤次数
	private String totalHoverDuration = "";// 总滞空时间
	private String maxHoverDuration = "";// 最大滞空时间
	private int dropTraveled = 0;// 滑行落差
	private int hopCount = 0;// 跳跃次数

	// 滑雪
	private int lapCount = 1;// 趟数（上升和下降的次数）
	private int upHillDistance = 0;// 上坡距离 （距离坐标点的累加） m
	private int downHillDistance = 0;// 下坡距离/滑行距离 m
	private int verticalDistance = 0;// 滑行落差/垂直距离 m
	private int _nMaxSlopeAngle = 0;// 最大坡度
	private String nskiStatus = "0";// 上升状态（1：上升状态； 2：下降状态）

	/**
	 * 是否全屏
	 */
	boolean MapFullscreenStatus = false;
	private MySpeechSynthesizer mSpeechSynthesizer;

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
			LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				startSpeak();
				startservice();
				startTimestamp = System.currentTimeMillis();
				initTimer();
				btnStartStatus = true;
				btnContinueStatus = true;
				iv_start.setVisibility(View.GONE);
				iv_pause.setVisibility(View.VISIBLE);
				iv_continue.setVisibility(View.GONE);
				iv_stop.setVisibility(View.GONE);
			} else {
				endDialog("GPSNOTSTART");
			}
			break;
		case R.id.iv_continue:
			// 继续
			pauseTimestamp = System.currentTimeMillis() - runingTimestamp - startTimestamp;
			contiueSpeak();
			startservice();
			btnContinueStatus = true;
			iv_start.setVisibility(View.GONE);
			iv_pause.setVisibility(View.VISIBLE);
			iv_continue.setVisibility(View.GONE);
			iv_stop.setVisibility(View.GONE);
			break;
		case R.id.iv_pause:
			pauseSpeak();
			setstopService();
			btnContinueStatus = false;
			iv_start.setVisibility(View.GONE);
			iv_pause.setVisibility(View.GONE);
			iv_continue.setVisibility(View.VISIBLE);
			iv_stop.setVisibility(View.VISIBLE);
			break;
		case R.id.map_iv_zoom:
			MapFullscreenStatus = true;
			setMapFullscreen();
			break;
		case R.id.map_iv_shrink:
			MapFullscreenStatus = false;
			setMapFullscreen();

			break;
		case R.id.map_iv_mylocation:
			moveToCenter();
			break;
		case R.id.iv_stop:
			endSpeak();
			String dialogType;
			if (distanceTraveled < 0.05) {
				dialogType = "shortDistance";
			} else {
				dialogType = "uploadDistance";
			}
			endDialog(dialogType);
			break;
		default:
			break;
		}
	}

	private void startSpeak() {
		mSpeechSynthesizer.speak("开始记录您的运动数据");
	}

	private void pauseSpeak() {
		mSpeechSynthesizer.speak("记录已暂停，请及时停止并保存数据");
	}

	private void contiueSpeak() {
		mSpeechSynthesizer.speak("欢迎回来，继续您的运动");
	}

	private void endSpeak() {
		mSpeechSynthesizer.speak("已经结束记录您的运动数据");
	}

	private void valueSpeak() {

		if (btnStartStatus && verticalDistance*1000/500> 0&&verticalDistance*1000%500<50) {
			String time = DatesUtils.getInstance().companyTimeNoSecond((int) runingTimestamp / 1000);

			if (sportsType.equals("sky")) {
				mSpeechSynthesizer.speak(
						"您已经运动" + distanceTraveled + "公里，滑行落差" + verticalDistance + "米，滑行" + lapCount + "趟，用时" + time);

			} else {
				mSpeechSynthesizer.speak("您已经运动" + distanceTraveled + "公里，当前海拔" + currentAltitude + "米，用时" + time);

			}
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	/**
	 * 结束后初始化所有状态
	 */
	private void initStartView() {
		setstopService();
		gpslocationListenerStatus = false;
		btnStartStatus = false;
		btnContinueStatus = false;
		firstUploadLocationstatus = true;
		StepDetector.CURRENT_SETP = 0;
		BEFORECURRENT_SETP = -1;
		runingTimestamp = 0;// 运动时间
		startTimestamp = 0;// 开始时间
		pauseTimestamp = 0;// 休息暂停时间
		_nMaxSlopeAngle = 0;
		iv_start.setVisibility(View.VISIBLE);
		iv_continue.setVisibility(View.GONE);
		iv_pause.setVisibility(View.GONE);
		iv_stop.setVisibility(View.GONE);

		// 清除轨迹
		if (null != mTrackUploadFragment) {
			mTrackUploadFragment.initDates();
		}
		allpointList.clear();
		mBaiduMap.clear();
		beforelatLng = null;
		Speed = 0;
		averageSpeed = 0;
		topSpeed = 0;
		matchSpeed = "0";
		averageMatchSpeed = "0";
		maxMatchSpeed = "0";
		recordDatas = "";
		setSportPropertyList(sportindex);
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
		mActivity = getApplicationContext();
		mHomeActivity = CommonUtils.getInstance().mHomeActivity;
	}

	protected void findViewById() {
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.mMapView);
		mBaiduMap = mMapView.getMap();
		layoutall = (LinearLayout) findViewById(R.id.layoutall);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		CommonUtils.getInstance().setViewTopHeigth(mActivity, page_top_layout);
		pagetop_iv_center = (ImageView) findViewById(R.id.pagetop_iv_center);
		map_iv_zoom = (ImageView) findViewById(R.id.map_iv_zoom);
		map_iv_shrink = (ImageView) findViewById(R.id.map_iv_shrink);
		map_iv_mylocation = (ImageView) findViewById(R.id.map_iv_mylocation);

		relat_map = (FrameLayout) findViewById(R.id.relat_map);
		layout_property = (LinearLayout) findViewById(R.id.layout_property);
		layout_disquan = (LinearLayout) findViewById(R.id.layout_disquan);
		tv_distance = (TextView) findViewById(R.id.tv_distance);
		tv_quannum = (TextView) findViewById(R.id.tv_quannum);
		tv_distancetitle = (TextView) findViewById(R.id.tv_distancetitle);
		tv_quannumtitle = (TextView) findViewById(R.id.tv_quannumtitle);
		tv_quannumunit = (TextView) findViewById(R.id.tv_quannumunit);

		layout_bottom = (LinearLayout) findViewById(R.id.layout_bottom);
		iv_start = (ImageView) findViewById(R.id.iv_start);
		iv_continue = (ImageView) findViewById(R.id.iv_continue);
		iv_pause = (ImageView) findViewById(R.id.iv_pause);
		iv_stop = (ImageView) findViewById(R.id.iv_stop);
		mygridview_property = (MyGridView) findViewById(R.id.mygridview_property);
		CommonUtils.getInstance().setTextViewTypeface(this, tv_distance);
		CommonUtils.getInstance().setTextViewTypeface(this, tv_quannum);
	}

	protected void setListener() {
		map_iv_zoom.setOnClickListener(this);
		map_iv_shrink.setOnClickListener(this);
		map_iv_mylocation.setOnClickListener(this);
		pagetop_iv_you.setOnClickListener(this);
		iv_start.setOnClickListener(this);
		iv_continue.setOnClickListener(this);
		iv_pause.setOnClickListener(this);
		iv_stop.setOnClickListener(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
		if (mDB == null) {
			mDB = new TrackDBOpenHelper(this);
		}
	}

	protected void processLogic() {
		mSpeechSynthesizer = new MySpeechSynthesizer(mHomeActivity);
		initTimestamp = System.currentTimeMillis();
		sportindex = 0;
		setSportPropertyList(sportindex);
		initGps();
		initOnEntityListener();
		initLbsClient();
	}

	private void setMapFullscreen() {
		if (MapFullscreenStatus) {
			map_iv_zoom.setVisibility(View.GONE);
			map_iv_shrink.setVisibility(View.VISIBLE);
			map_iv_mylocation.setVisibility(View.VISIBLE);
			layout_disquan.setVisibility(View.GONE);
			layout_property.setVisibility(View.GONE);
			layout_bottom.setVisibility(View.GONE);
			pagetop_iv_center.setBackgroundResource(R.drawable.mainstart_kiwi_imgblo);
			page_top_layout.setBackgroundColor(getResources().getColor(R.color.white));
			layoutall.setPadding(0, 0, 0, 0);
			relat_map.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		} else {
			map_iv_zoom.setVisibility(View.VISIBLE);
			map_iv_shrink.setVisibility(View.GONE);
			map_iv_mylocation.setVisibility(View.GONE);
			layout_disquan.setVisibility(View.VISIBLE);
			layout_property.setVisibility(View.VISIBLE);
			layout_bottom.setVisibility(View.VISIBLE);
			pagetop_iv_center.setBackgroundResource(R.drawable.mainstart_kiwi_img);
			page_top_layout.setBackgroundColor(getResources().getColor(R.color.main_page_bg));
			layoutall.setPadding(getResources().getDimensionPixelSize(R.dimen.padd_leftright), 0,
					getResources().getDimensionPixelSize(R.dimen.padd_leftright), 0);
			relat_map.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					ConfigUtils.getInstance().dip2px(this, 140)));
		}
		showSportPic();
	}

	/**
	 * 开始后 ：5分钟的时候判断运动类型
	 */
	private void initSportType() {
		if (upHillDistance > 100 && downHillDistance > 100) {
			sportsType = "sky"; // 滑雪
		} else {
			if ((topSpeed < 20 && averageSpeed < 8 && (float) nSteps / (float) distanceTraveled > 1.1)
					|| (topSpeed < 15 && averageSpeed < 6 && (float) nSteps / (float) distanceTraveled > 0.7)
					|| (topSpeed < 8 && averageSpeed < 5 && (float) nSteps / (float) distanceTraveled > 0.5)) {
				// if (upHillDistance > 100 || downHillDistance > 100)
				// sportsType = 4; // climbing
				// else
				sportsType = "walk";// 走路
			} else if (topSpeed < 40 && averageSpeed < 15
					&& ((float) nSteps / (float) distanceTraveled > 0.6 || nSteps == 0)) {
				// if (upHillDistance > 100 || downHillDistance > 100)
				// sportsType = 5; // cross country run
				// else
				sportsType = "run";// 跑步
			} else if (topSpeed < 70 && averageSpeed < 40
					&& ((float) nSteps / (float) distanceTraveled > 0.05 || nSteps == 0)) {
				// if (upHillDistance > 100 || downHillDistance > 100)
				// sportsType = 6; // cross country biking
				// else
				sportsType = "riding";// 骑行
			} else if (topSpeed < 260 && averageSpeed < 150)
				sportsType = "drive";// 开车
			else {
				sportsType = "drive"; // 高铁
			}

		}
		getsportindexByType();
		setSportPropertyList(sportindex);
		getCurrentPropertyValue();
	}

	/**
	 * 根据运动类型获取索引
	 */
	private void getsportindexByType() {
		int matchNum = 0;
		MainsportParser mMainsportParser = new MainsportParser();
		ArrayList<MainSportInfo> mallsportList = mMainsportParser.parseJSON(this);
		for (int i = 0; i < mallsportList.size(); i++) {
			MainSportInfo mMainSportInfo = mallsportList.get(i);
			if (sportsType.equals(mMainSportInfo.getEname())) {
				sportindex = i;
				matchNum++;
				break;
			}
		}
		if (matchNum == 0) {
			sportsType = "drive";
			sportindex = mallsportList.size() - 1;
		}
	}

	/**
	 * 切换运动类型初始化显示属性
	 * 
	 * @param sportindex
	 */
	private void setSportPropertyList(int sportindex) {
		MainsportParser mMainsportParser = new MainsportParser();
		ArrayList<MainSportInfo> mallsportList = mMainsportParser.parseJSON(this);
		MainSportInfo mMainSportInfo = mallsportList.get(sportindex);
		mMpropertyList = mMainSportInfo.getMpropertyList();
		mpropertytwnList = mMainSportInfo.getMpropertytwnList();
		sportsType = mMainSportInfo.getEname();
		mMainSportAdapter = new MainPropertyAdapter(this, mMpropertyList);
		mygridview_property.setAdapter(mMainSportAdapter);
		showSportPic();
		if (mpropertytwnList != null && mpropertytwnList.size() == 2) {
			if (!LanguageUtil.idChLanguage(this)) {
				tv_distancetitle.setText(mpropertytwnList.get(0).getEname());
				tv_quannumtitle.setText(mpropertytwnList.get(1).getEname());
			} else {
				tv_distancetitle.setText(mpropertytwnList.get(0).getCname());
				tv_quannumtitle.setText(mpropertytwnList.get(1).getCname());
			}
			tv_distance.setText(mpropertytwnList.get(0).getValue());

			tv_quannum.setText(mpropertytwnList.get(1).getValue());
			tv_quannumunit.setText(mpropertytwnList.get(1).getUnit());
		}
	}

	/**
	 * 根据运动类型更换相应的图标
	 */
	private void showSportPic() {
		if (!MapFullscreenStatus) {
			if (sportsType.equals("run")) {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_run_img);
			} else if (sportsType.equals("riding")) {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_riding_img);
			} else if (sportsType.equals("walk")) {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_walk_img);
			} else if (sportsType.equals("sky")) {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_sky_img);
			} else {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_drive_img);
			}
		} else {
			if (sportsType.equals("run")) {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_run_imgblo);
			} else if (sportsType.equals("riding")) {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_riding_imgblo);
			} else if (sportsType.equals("walk")) {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_walk_imgblo);
			} else if (sportsType.equals("sky")) {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_sky_imgblo);
			} else {
				pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_drive_imgblo);
			}
		}
	}

	/**
	 * 计算当前的属性值
	 */
	private void getCurrentPropertyValue() {
		distanceTraveled = mTrackUploadFragment.sum_distance;
		distanceTraveled = Double.valueOf(PriceUtils.getInstance().getPriceTwoDecimal(distanceTraveled, 2));
		calorie = (int) (70 * distanceTraveled * 1.036);
		nSteps = StepDetector.CURRENT_SETP;
		duration = DatesUtils.getInstance().formatTimes(runingTimestamp);
		freezeDuration = DatesUtils.getInstance().formatTimes(pauseTimestamp);
		if (currentAltitude >= maxAltitude) {
			maxAltitude = currentAltitude;
		}
		if (currentAltitude <= minAltidue) {
			minAltidue = currentAltitude;
		}
		long time = runingTimestamp / 1000;
		if (time > 0) {
			averageSpeed = distanceTraveled * 1000 * 3.6 / time;// 单位：公里每小时
			averageSpeed = Double.valueOf(PriceUtils.getInstance().getPriceTwoDecimal(averageSpeed, 2));
			Speed = Double.valueOf(PriceUtils.getInstance().getPriceTwoDecimal(Speed, 2));
			if (Speed < minSpeed) {
				minSpeed = Speed;
			}
			if (Speed > topSpeed) {
				topSpeed = Speed;
			}
			if (averageSpeed > topSpeed) {
				topSpeed = averageSpeed;
			}
		}
		// distanceTraveled=0.06;
		if (distanceTraveled > 0) {
			matchSpeedTimestamp = DatesUtils.getInstance().computeMatchspeed(runingTimestamp, distanceTraveled);
			averageMatchSpeed = DatesUtils.getInstance().formatMatchspeed(matchSpeedTimestamp);
			matchSpeed = DatesUtils.getInstance().formatMatchspeed(matchSpeedTimestamp);
			if (matchSpeedTimestamp <= maxMatchSpeedTimestamp || maxMatchSpeedTimestamp == 0) {
				maxMatchSpeedTimestamp = matchSpeedTimestamp;
				maxMatchSpeed = DatesUtils.getInstance().formatMatchspeed(maxMatchSpeedTimestamp);
			}
			if (Speed > 0) {
				matchSpeedTimestamp = (long) (1 * 3600 / Speed);
				matchSpeed = DatesUtils.getInstance().formatMatchspeed(matchSpeedTimestamp);
				if (matchSpeedTimestamp <= maxMatchSpeedTimestamp || maxMatchSpeedTimestamp == 0) {
					maxMatchSpeedTimestamp = matchSpeedTimestamp;
					maxMatchSpeed = DatesUtils.getInstance().formatMatchspeed(maxMatchSpeedTimestamp);
				}
			}

		}
		valueSpeak();
		showCurrentPropertyValue();
	}

	/**
	 * 显示属性值
	 */
	private void showCurrentPropertyValue() {
		mpropertytwnList.get(0).setValue(distanceTraveled + "");
		mpropertytwnList.get(1).setValue(duration);
		if (sportsType.equals("run")) {
			for (int i = 0; i < mMpropertyList.size(); i++) {
				switch (i) {
				case 0:
					mMpropertyList.get(i).setValue(matchSpeed + "");
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
			mpropertytwnList.get(1).setValue(String.valueOf(lapCount));
			for (int i = 0; i < mMpropertyList.size(); i++) {
				switch (i) {
				case 0:
					mMpropertyList.get(i).setValue(duration);
					break;
				case 1:
					mMpropertyList.get(i).setValue(String.valueOf(downHillDistance));
					break;
				case 2:
					mMpropertyList.get(i).setValue(verticalDistance + "");
					break;
				case 3:
					mMpropertyList.get(i).setValue(topSpeed + "");
					break;
				case 4:
					mMpropertyList.get(i).setValue(String.valueOf(_nMaxSlopeAngle));
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
					mMpropertyList.get(i).setValue(Speed + "");
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

		mMainSportAdapter.setList(mMpropertyList);
		mMainSportAdapter.notifyDataSetChanged();
		if (mpropertytwnList != null && mpropertytwnList.size() == 2) {
			tv_distance.setText(mpropertytwnList.get(0).getValue());
			tv_quannum.setText(mpropertytwnList.get(1).getValue());
		}
	}

	private void startservice() {
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

	private Thread thread;
	// Handler handler = new Handler();
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
		mLocClient = new LocationClient(mActivity);
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
		option.setCoorType(GPSUtil.CoorType);// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(2000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		if (null == realtimeBitmap) {
			realtimeBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_myposition_map);
		}
		getCriteria();
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
		// if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		// Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		// startActivity(intent);
		// }
		// 绑定监听状态
		lm.addGpsStatusListener(listener);
		/**
		 * 监听状态 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种 参数2，位置信息更新周期，单位毫秒
		 * 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息 参数4，监听
		 * 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
		 * 1秒更新一次，或最小位移变化超过1米更新一次；
		 * 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
		 */
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 3, gpslocationListener);
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
	 * 返回查询条件
	 * 
	 * @return
	 */
	private Criteria getCriteria() {
		Criteria criteria = new Criteria();
		// 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 设置是否要求速度
		criteria.setSpeedRequired(true);
		// 设置是否允许运营商收费
		criteria.setCostAllowed(false);
		// 设置是否需要方位信息
		criteria.setBearingRequired(true);
		// 设置是否需要海拔信息
		criteria.setAltitudeRequired(true);
		// 设置对电源的需求
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			Message msg = new Message();
			msg.what = NETLOCATION;
			msg.obj = location;
			mHandler.sendMessage(msg);
			msg = null;
		}
	};

	private LocationListener gpslocationListener = new LocationListener() {
		/**
		 * 位置信息变化时触发
		 */
		@Override
		public void onLocationChanged(Location location) {
			Message msg = new Message();
			msg.what = GPSLOCATION;
			msg.obj = location;
			mHandler.sendMessage(msg);
			msg = null;
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
				// 信号较差数据准确度较低
			} else if (accuracy > 5 && accuracy <= 12) {
				if (btnContinueStatus) {
					mTrackUploadFragment.isInUploadFragment = true;
				}
				// 信号一般
				gpslocationListenerStatus = true;
				firstnetLocationstatus = true;
			} else {
				if (btnContinueStatus) {
					mTrackUploadFragment.isInUploadFragment = true;
				}
				// 信号良好
				gpslocationListenerStatus = true;
				firstnetLocationstatus = true;
			}
		}
	}

	private void netLocation(BDLocation location) {

		System.err.println("MyLocationListener==" + location.getLatitude() + "     ");
		if (!gpslocationListenerStatus) {
			updateTrackHistoryData();
			if (location == null || !ConfigUtils.getInstance().getNetWorkStatus(mActivity)) {
				mTrackUploadFragment.isInUploadFragment = false;
				// 无信号
				return;
			} else {
				if (location.getLocType() == 167 || location.getLatitude() == 0 || location.getRadius() > 100) {
					mTrackUploadFragment.isInUploadFragment = false;
					// 信号较差数据准确度较低
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
					Speed = location.getSpeed();// Km/h
					if (Speed < 0) {
						Speed = 0;
					}
					address = location.getAddrStr();
					currentAccuracy = location.getRadius();
					LatLng latLng = new LatLng(latitude_me, longitude_me);
					if (btnStartStatus && btnContinueStatus) {
						mTrackUploadFragment.isInUploadFragment = true;
						if (mTrackUploadFragment != null && mTrackUploadFragment.isInUploadFragment) {
							Message msg = new Message();
							msg.what = SETLINE;
							msg.obj = latLng;
							mHandler.sendMessage(msg);
							msg = null;
						}
					} else {
						showLocation(latLng);
					}

				}
			}
			System.err.println("location===" + location.getLatitude() + "    " + location.getLocType());
		}

	}

	private void gpsLocation(Location location) {

		showGpsAccuracy(location);
		System.err.println("gpslocationListener==" + location.getLatitude() + "     " + location.getAccuracy());
		if (gpslocationListenerStatus && location != null) {
			updateTrackHistoryData();
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			// 将GPS设备采集的原始GPS坐标转换成百度坐标
			CoordinateConverter converter = new CoordinateConverter();
			converter.from(CoordType.GPS);
			converter.coord(latLng);
			LatLng sourceLatLng = converter.convert();
			longitude_me = sourceLatLng.longitude;
			latitude_me = sourceLatLng.latitude;
			address = location.getProvider();
			currentAccuracy = location.getAccuracy();
			currentAltitude = (int) location.getAltitude(); // 获取海拔高度信息，单位米
			if (startAltitude == 0 || mTrackUploadFragment.isFirstLoc) {
				startAltitude = currentAltitude;
			}
			if (beforeAltitude == 0) {
				beforeAltitude = currentAltitude;
			}
			Speed = location.getSpeed() * 3.6;// m/s --> Km/h
			if (Speed < 0) {
				Speed = 0;
			}
			if (btnStartStatus && btnContinueStatus) {
				Message msg = new Message();
				msg.what = SETLINE;
				msg.obj = sourceLatLng;
				mHandler.sendMessage(msg);
				msg = null;
			} else {
				showLocation(sourceLatLng);
			}

		}

	}

	/**
	 * 前一个坐标点
	 */
	private LatLng beforelatLng;
	/**
	 * 所有轨迹点
	 */
	private List<MainLocationItemInfo> allpointList = new ArrayList<MainLocationItemInfo>();

	private void setline(LatLng latLng) {
		boolean sensorAva = (BEFORECURRENT_SETP != StepDetector.CURRENT_SETP) && Constans.getInstance().mSensorState;
		if (allpointList.size() == 0 || sensorAva || !Constans.getInstance().mSensorState) {
			mTrackUploadFragment.showRealtimeTrack(latLng);
			Log.e("map", "beforelatLng==" + beforelatLng + ";;;latLng==" + latLng);
			if (beforelatLng == null || beforelatLng.latitude != latLng.latitude) {
				recordInfo(latLng);
				if (beforelatLng != null) {
					insmaxSlope(latLng);
					inskyHillDis(latLng);
				}
				beforelatLng = latLng;
				Log.e("track", "addddd-----" + allpointList.size());
			}
			getCurrentPropertyValue();
		}
	}

	/**
	 * 每次定位：计算最大坡度 private String lapCount = "1";// 趟数（上升和下降的次数） private int
	 * upHillDistance = 0;// 上坡距离 （距离坐标点的累加） m private int downHillDistance =
	 * 0;// 下坡距离 m private int verticalDistance = 0;// 滑行落差/垂直距离 m private int
	 * _nMaxSlopeAngle = 0;// 最大坡度 private String nskiStatus = "0";//
	 * 上升状态（1：上升状态； 2：下降状态）
	 */
	private void insmaxSlope(LatLng nowlatLng) {
		LatLng startlatLng_ = mTrackUploadFragment.startlatLng;
		double tempAllDistance_ = ConfigUtils.DistanceOfTwoPoints(startlatLng_.latitude, startlatLng_.longitude,
				nowlatLng.latitude, nowlatLng.longitude);
		int tempAllDistance = (int) (tempAllDistance_ * 1000);
		if (startAltitude > currentAltitude) {
			/**
			 * 下滑 坡度
			 */
			int tempAllVerticalDistance = (startAltitude - currentAltitude);
			if (tempAllDistance > 200) {
				double tempAngle = Math.asin(tempAllVerticalDistance / tempAllDistance);
				if (((tempAngle * 180) / Math.PI) > _nMaxSlopeAngle) {
					_nMaxSlopeAngle = (int) ((tempAngle * 180) / Math.PI);
					if (_nMaxSlopeAngle > 45) {
						_nMaxSlopeAngle = 45;
					}
				}
			}
		}
	}

	/**
	 * 计算
	 * 
	 * @param nowlatLng
	 */
	private void inskyHillDis(LatLng nowlatLng) {
		double tempDistance_ = ConfigUtils.DistanceOfTwoPoints(beforelatLng.latitude, beforelatLng.longitude,
				nowlatLng.latitude, nowlatLng.longitude);
		int tempDistance = (int) (tempDistance_ * 1000);
		int tempVerticalDistance;
		if (tempDistance == 0) {
			return;
		}
		if (beforeAltitude > currentAltitude) {
			/**
			 * 下滑距离
			 */
			tempVerticalDistance = (beforeAltitude - currentAltitude);
			verticalDistance += tempVerticalDistance;
			dropTraveled += tempVerticalDistance;
			downHillDistance += (int) sqrt(pow(tempVerticalDistance, 2) + pow(tempDistance, 2));
			if (nskiStatus.equals("1")) {
				lapCount++;
			}
			nskiStatus = "2";
		} else {
			/**
			 * 上升
			 */
			tempVerticalDistance = (currentAltitude - beforeAltitude);
			upHillDistance += (int) sqrt(pow(tempVerticalDistance, 2) + pow(tempDistance, 2));
			nskiStatus = "1";
		}
		beforeAltitude = currentAltitude;

	}

	/**
	 * 保存坐标明细
	 */
	private void recordInfo(LatLng latLng) {
		LatLng mLatLng = latLng;// 经纬度
		double speed = Speed;// 速度
		double altitude = currentAltitude;// 海拔
		double accuracy = currentAccuracy;// 精度
		long duration = runingTimestamp / 1000;// 用时
		double distance = distanceTraveled * 1000;// 距离
		String nStatus = "";// 运动状态
		String nLapPoint = "";// 没圈线路中间点
		String nLapTime = "";// 单圈用时
		String latitudeOffset = "";// 精度偏移
		String longitudeOffset = "";// 维度偏移

		MainLocationItemInfo mMainLocationItemInfo = new MainLocationItemInfo(mLatLng.latitude, mLatLng.longitude,
				speed, altitude, accuracy, nStatus, nLapPoint, nLapTime, duration, distance, latitudeOffset,
				longitudeOffset);
		allpointList.add(mMainLocationItemInfo);
		mMainLocationItemInfo = null;
	}

	private void showLocation(LatLng latLng) {
		try {
			// 显示系统logo
			// MyLocationData locData = new MyLocationData.Builder()
			// // 此处设置开发者获取到的方向信息，顺时针0-360
			// .direction(0).latitude(latitude_me).longitude(longitude_me).build();
			// mBaiduMap.setMyLocationData(locData);

			/**
			 * 替换定位logo
			 */
			if (null != mTrackUploadFragment.overlay) {
				mTrackUploadFragment.overlay.remove();
			}
			MarkerOptions overlayOptions = new MarkerOptions().position(latLng).icon(realtimeBitmap).zIndex(8)
					.draggable(true);
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
		/**
		 * 每1分钟上传一次数据
		 */
		if (firstUploadLocationstatus || ((System.currentTimeMillis() - initTimestamp) % (1 * 60 * 1000) == 0)) {
			if (beforelatLng == null || latLng.latitude != beforelatLng.latitude) {
				Message message = new Message();
				message.what = UPDATELOCATION;
				message.obj = latLng;
				mHandler.sendMessage(message);
				message = null;
			}
		}

		/**
		 * 每5分钟更新一下场地用户列表信息
		 */

		if (firstUploadLocationstatus || ((System.currentTimeMillis() - initTimestamp) % (10 * 60 * 1000) == 0)) {
			firstUploadLocationstatus = false;
			getVenuesUsers();
			getPosid();
		}
	}

	/**
	 * 开始计时
	 */
	private void initTimer() {
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
						if (btnStartStatus && btnContinueStatus) {
							runingTimestamp = System.currentTimeMillis() - startTimestamp - pauseTimestamp;
							Message msg = new Message();
							msg.what = UPDATETIME;
							mHandler.sendMessage(msg);// 通知主线程
							if (firstComeIn && runingTimestamp - 5 * 60 * 1000 == 0 && distanceTraveled > 0) {
								initSportType();
								firstComeIn = false;
							}
						}
					}
				}
			};
			thread.start();
		}
	}

	boolean firstComeIn = true;
	private final int UPDATELOCATION = 1;
	private final int UPDATETIME = 2;
	private final int SETLINE = 3;
	private final int NETLOCATION = 4;
	private final int GPSLOCATION = 5;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATELOCATION:
				LatLng mypoint = (LatLng) msg.obj;
				double longitude_me = mypoint.longitude;
				double latitude_me = mypoint.latitude;
				updateLocation(longitude_me, latitude_me);
				break;
			case UPDATETIME:
				getCurrentPropertyValue();
				break;
			case SETLINE:
				LatLng latLng = (LatLng) msg.obj;
				initMyLocationUsers(latLng);
				setline(latLng);
				break;
			case NETLOCATION:
				BDLocation mBDLocation = (BDLocation) msg.obj;
				netLocation(mBDLocation);
				break;
			case GPSLOCATION:
				Location location = (Location) msg.obj;
				gpsLocation(location);
				break;
			}
		}
	};

	private void updateTrackHistoryData() {
		if (ConfigUtils.getInstance().isNetWorkAvaiable(this)) {

			ArrayList<TrackSaveInfo> mTrackList = mDB.getHistoryTrackList();
			if (mTrackList != null && mTrackList.size() > 0) {
				Log.e("track", "------------mTrackList.size()------------" + mTrackList.size());
				for (int i = 0; i < mTrackList.size(); i++) {
					loadTrackHistoryDates(i, mTrackList);
				}
			}

		}
	}

	/**
	 * 上传所有缓存的轨迹数据，成功并清除数据表数据
	 * 
	 * @param index
	 * @param mTrackList
	 */
	private void loadTrackHistoryDates(final int index, final ArrayList<TrackSaveInfo> mTrackList) {
		TrackSaveInfo mTrackSaveInfo = mTrackList.get(index);
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", mTrackSaveInfo.getUid());
		mhashmap.put("token", mTrackSaveInfo.getToken());
		mhashmap.put("access_token", mTrackSaveInfo.getAccess_token());
		mhashmap.put("recordDatas", "" + mTrackSaveInfo.getRecordDatas());
		Log.e("track", "------------loadTrackHistoryDates------------" + mhashmap);
		new RecordDatesloadBusiness(this, mhashmap, new GetRecordDatesloadCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200") && index == mTrackList.size() - 1) {
						mDB.deleteTableAllInfo(TrackDBHelper.TABLE_TRACK);
					}
				}
			}
		});

	}

	private String setRecordInfoArrayToJson() {
		JSONArray recordInfoArray = new JSONArray();
		JSONObject recordDatas = new JSONObject();
		try {
			recordDatas.put("user_id", uid);
			recordDatas.put("posid", "" + posid);
			recordDatas.put("distanceTraveled", "" + distanceTraveled * 1000);
			recordDatas.put("duration", "" + (runingTimestamp / 1000));
			recordDatas.put("verticalDistance", "" + verticalDistance);
			recordDatas.put("topSpeed", "" + topSpeed);

			recordDatas.put("dropTraveled", "" + dropTraveled);
			recordDatas.put("nSteps", "" + nSteps);
			recordDatas.put("matchSpeed", "" + matchSpeed);
			recordDatas.put("maxMatchSpeed", "" + maxMatchSpeed);
			recordDatas.put("maxSlope", "" + _nMaxSlopeAngle);
			recordDatas.put("maxAltitude", "" + maxAltitude);
			recordDatas.put("currentAltitude", "" + currentAltitude);
			recordDatas.put("averageMatchSpeed", "" + averageMatchSpeed);
			recordDatas.put("averageSpeed", "" + averageSpeed);
			recordDatas.put("freezeDuration", "" + pauseTimestamp / 1000);
			recordDatas.put("maxHoverDuration", "" + maxHoverDuration);
			recordDatas.put("totalHoverDuration", "" + totalHoverDuration);
			recordDatas.put("hopCount", "" + hopCount);
			recordDatas.put("lapCount", "" + lapCount);
			recordDatas.put("wrestlingCount", "" + wrestlingCount);
			recordDatas.put("cableCarQueuingDuration", "" + cableCarQueuingDuration);
			recordDatas.put("address", "" + address);
			recordDatas.put("minAltidue", "" + minAltidue);
			recordDatas.put("calorie", "" + calorie);
			recordDatas.put("sportsType", "" + sportsType);
			recordDatas.put("latitudeOffset", "" + latitudeOffset);
			recordDatas.put("longitudeOffset", "" + longitudeOffset);
			recordDatas.put("upHillDistance", "" + upHillDistance);
			recordDatas.put("downHillDistance", "" + downHillDistance);

			try {
				if (allpointList != null && allpointList.size() > 0) {
					int length = allpointList.size();
					double lat;
					double lon;
					for (int i = 0; i < length; i++) {
						lat = allpointList.get(i).getLatitude();
						lon = allpointList.get(i).getLongitude();
						if (lat > 0) {
							JSONObject latObject = new JSONObject();
							double[] latlng = GPSUtil.bd09_To_Gcj02(lat, lon);

							latObject.put("longitude", latlng[1] + "");
							latObject.put("latitude", latlng[0] + "");

							latObject.put("speed", "" + allpointList.get(i).getSpeed());
							latObject.put("altitude", "" + allpointList.get(i).getAltitude());
							latObject.put("accuracy", "" + allpointList.get(i).getAccuracy());
							latObject.put("nStatus", "" + allpointList.get(i).getnStatus());
							latObject.put("nLapPoint", "" + allpointList.get(i).getnLapPoint());
							latObject.put("nLapTime", "" + allpointList.get(i).getnLapTime());
							latObject.put("duration", "" + allpointList.get(i).getDuration());
							latObject.put("distance", "" + allpointList.get(i).getDistance());
							latObject.put("latitudeOffset", "" + allpointList.get(i).getLatitudeOffset());
							latObject.put("longitudeOffset", "" + allpointList.get(i).getLongitudeOffset());
							recordInfoArray.put(latObject);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			recordDatas.put("recordInfo", recordInfoArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return recordDatas.toString();

	}

	/**
	 * 获取场地id
	 */
	private void getPosid() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		double[] latlng = GPSUtil.bd09_To_Gcj02(latitude_me, longitude_me);

		mhashmap.put("longitude", latlng[1] + "");
		mhashmap.put("latitude", latlng[0] + "");
		Log.e("map", "------------loadRecordDates------------" + mhashmap);
		new VenuesInfoBylicationBusiness(this, mhashmap, new GetInfoBylicationCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (TextUtils.isEmpty(posid)) {
						posid = (String) dataMap.get("posid");
						String sportsType_ = (String) dataMap.get("sportsType");
						if (!TextUtils.isEmpty(sportsType_)) {
							sportsType = sportsType_;
							getsportindexByType();
							showSportPic();
							setSportPropertyList(sportindex);
							getCurrentPropertyValue();
						}
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	private ProgressDialog mDialog;

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

	/**
	 * 点击结束
	 * 
	 * @param dialogType
	 */
	public void endDialog(final String dialogType) {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_myexit);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定
		TextView myexit_text_title = (TextView) selectDialog.findViewById(R.id.myexit_text_title);
		if (dialogType.equals("shortDistance")) {
			myexit_text_title.setText(getString(R.string.endlocationcancel));
		} else if (dialogType.equals("GPSNOTSTART")) {
			myexit_text_title.setText(getString(R.string.endlocationgpsstart));
		} else {
			if (ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
				myexit_text_title.setText(getString(R.string.endlocationcommit));
			} else {
				myexit_text_title.setText(getString(R.string.endlocationcommitNotnet));
			}
		}
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				if (dialogType.equals("shortDistance")) {
					initStartView();
				} else if (dialogType.equals("GPSNOTSTART")) {
					// 返回开启GPS导航设置界面
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivityForResult(intent, 0);
				} else {
					recordDatas = setRecordInfoArrayToJson();
					if (ConfigUtils.getInstance().isNetWorkAvaiable(mActivity)) {
						loadRecordDates();
					} else {
						// 无网络时保存轨迹到数据库
						if (!TextUtils.isEmpty(recordDatas)) {
							mDB.addTableTrackInfo(uid, token, access_token, recordDatas);
						}
						initStartView();
						Log.e("track", "mDB.add");
					}

				}

			}
		});
	}

	/**
	 * 上传轨迹
	 */
	private void loadRecordDates() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("recordDatas", "" + recordDatas);
		Log.e("map", "------------loadRecordDates------------" + mhashmap);
		new RecordDatesloadBusiness(this, mhashmap, new GetRecordDatesloadCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(mActivity, msg);
				}
				initStartView();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	protected void updateLocation(double longitude_me, double latitude_me) {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);

		double[] latlng = GPSUtil.bd09_To_Gcj02(latitude_me, longitude_me);

		mhashmap.put("longitude", latlng[1] + "");
		mhashmap.put("latitude", latlng[0] + "");
		Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
		new UpdateLocationBusiness(this, mhashmap, new GetUpdateLocationCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("401") || status.equals("402")) {
						UserLoginBack403Utils.getInstance()
								.sendBroadcastLoginBack403(CommonUtils.getInstance().mHomeActivity);
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	protected void getVenuesUsers() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		double[] latlng = GPSUtil.bd09_To_Gcj02(latitude_me, longitude_me);
		mhashmap.put("longitude", latlng[1] + "");
		mhashmap.put("latitude", latlng[0] + "");
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

		View convertView = LayoutInflater.from(mActivity).inflate(R.layout.venues_map_marker, null);
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
		MapStatus mMapStatus = new MapStatus.Builder().target(mypoint).zoom(TrackUploadFragment.STARTZOOM).build();
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
			Log.e("LocationMide", "onPauseonPauseonPause");
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
			mHandler.removeCallbacks(thread);
			if (mTrackUploadFragment != null) {
				mTrackUploadFragment.stopTrace();
				mTrackUploadFragment.showpointList.clear();
				allpointList.clear();
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
			mDB.close();
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
				CommonUtils.getInstance().mCurrentActivity = mHomeActivity;
				setSportPropertyList(sportindex);
				getCurrentPropertyValue();
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
