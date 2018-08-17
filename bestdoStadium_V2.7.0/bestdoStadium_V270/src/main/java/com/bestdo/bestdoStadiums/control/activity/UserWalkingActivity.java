package com.bestdo.bestdoStadiums.control.activity;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.Trace;
import com.baidu.trace.TraceLocation;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.UserWalkingGetTrackBusiness;
import com.bestdo.bestdoStadiums.business.UserWalkingGetTrackBusiness.GetUserWalkingGetTrackCallback;
import com.bestdo.bestdoStadiums.business.UserWalkingPostDataBusiness;
import com.bestdo.bestdoStadiums.business.UserWalkingPostDataBusiness.UserWalkingPostDataCallback;
import com.bestdo.bestdoStadiums.control.map.BestDoApplication;
import com.bestdo.bestdoStadiums.control.map.LocationUtils;
import com.bestdo.bestdoStadiums.control.walking.MyCountTimer;
import com.bestdo.bestdoStadiums.control.walking.StepAllCounterService;
import com.bestdo.bestdoStadiums.control.walking.StepAllDetector;
import com.bestdo.bestdoStadiums.control.walking.StepCounterService;
import com.bestdo.bestdoStadiums.control.walking.StepDetector;
import com.bestdo.bestdoStadiums.control.walking.TrackUploadFragment;
import com.bestdo.bestdoStadiums.model.UserWalkingHistoryInfo;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Config;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.DpUtil;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.ScreenShareUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMShareBoardListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Administrator
 * @date 创建时间：2017年5月22日 上午10:53:48
 * @Description 健步走轨迹
 */
public class UserWalkingActivity extends FragmentActivity implements OnClickListener {

	private TextView userwalk_tv_start;
	private ImageView userwalk_run_iv_gps;
	private LinearLayout userwalk_run_layout_hostory;
	private TextView userwalk_run_tv_gpsinfo;
	private TextView userwalk_run_tv_distance;
	private ImageView userwalk_run_iv_map;
	private TextView userwalk_run_tv_speed;
	private TextView userwalk_run_tv_time;
	private TextView userwalk_run_tv_calories;
	private ImageView userwalk_run_iv_continue;
	private ImageView userwalk_run_iv_pause;
	private ImageView userwalk_run_iv_end;
	private LinearLayout userwalk_layout_running;
	private RelativeLayout userwalk_relat_map;
	private ImageView userwalk_map_iv_back;
	private ImageView userwalk_map_iv_mylocation;
	private LinearLayout userwalk_layout_start;

	private TrackUploadFragment mTrackUploadFragment;
	private RelativeLayout userwalk_relat_map_result;
	private ImageView userwalk_map_iv_resultback;
	private ImageView userwalk_map_iv_resultshare;
	private ImageView userwalk_map_iv_resultposition;
	private TextView userwalk_map_tv_resultdistance;
	private CircleImageView userwalk_map_iv_resultavatar;
	private TextView userwalk_map_tv_resultname;
	private TextView userwalk_map_tv_resulttime;
	private TextView userwalk_map_tv_reslutspeed;
	private TextView userwalk_map_tv_resluttime;
	private TextView userwalk_map_tv_reslutcalories;
	private TextView userwalk_map_tv_resultsure;
	private ImageLoader asyncImageLoader;
	private MapView bmapView;
	public static BaiduMap mBaiduMap;
	private LocationManager lm;
	private Thread thread;
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

	/**
	 * 轨迹服务客户端
	 */
	public static LBSTraceClient client = null;

	/**
	 * Entity监听器
	 */
	public static OnEntityListener entityListener = null;

	/**
	 * 判断地图和run状态,true为地图模式
	 */
	private Boolean mapStatus = false;
	/**
	 * 切换暂停 继续结束 两种状态;true为执行状态
	 */
	private Boolean btnContinueStatus = true;
	/**
	 * 已经执行的时间
	 */
	protected long startTimestamp;
	protected long pauseTimestamp;
	protected long runingTimestamp;
	protected double distance;
	protected int calories;
	protected String speed;
	protected double weight = 70;

	public static Context mContext;
	String uid;
	private String bid;
	private String deptId;
	private String mobile;
	boolean firstUploadDate = true;
	/**
	 * 开始上传轨迹坐标
	 */
	String STARTWALKSTAUS = "0";
	/**
	 * 中间上传轨迹坐标
	 */
	String CONTINUEWALKSTAUS = "1";
	/**
	 * 结束上传轨迹坐标
	 */
	String ENDWALKSTAUS = "2";
	/**
	 * 结果页,防止点击有结束弹窗时 返回问题
	 */
	String ENDRESULTPAGESTAUS = "3";
	private String nickname;
	private String ablum;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userwalk_run_layout_hostory:
			Intent intent = new Intent(this, UserWalkingHistoryActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		case R.id.userwalk_run_iv_map:
			mapStatus = true;
			switchMapOrRunShowstatus();
			break;
		case R.id.userwalk_run_iv_pause:
			btnContinueStatus = false;
			mTrackUploadFragment.isInUploadFragment = false;
			switchPauseOrContinueBtn();
			break;
		case R.id.userwalk_run_iv_continue:
			btnContinueStatus = true;
			mTrackUploadFragment.isInUploadFragment = true;
			pauseTimestamp = System.currentTimeMillis() - runingTimestamp - startTimestamp;
			switchPauseOrContinueBtn();
			break;
		case R.id.userwalk_run_iv_end:
			String dialogType;
			if (distance < 0.05) {
				dialogType = "shortDistance";
			} else if (!ConfigUtils.getInstance().getNetWorkStatus(this)) {
				dialogType = "notHaveNet";
			} else {
				dialogType = "uploadDistance";
			}
			// mTrackUploadFragment.getPointList().add(new LatLng(39.91405,
			// 116.404269));
			walking_status = ENDWALKSTAUS;
			endDialog(dialogType);
			break;
		case R.id.userwalk_map_iv_mylocation:
		case R.id.userwalk_map_iv_resultposition:
			mTrackUploadFragment.setMapupdateStatus(zoom, mTrackUploadFragment.mEndpoint);
			break;
		case R.id.userwalk_map_iv_back:
		case R.id.userwalk_map_iv_resultback:
		case R.id.userwalk_map_tv_resultsure:
			doback();
			break;
		case R.id.userwalk_map_iv_resultshare:
			if (!cutStatus) {
				cutStatus = true;
				minitHandler.sendEmptyMessage(CUTMAP);

			}
			break;
		default:
			break;
		}

	}

	private Boolean cutStatus = false;
	private Intent service;

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		// 另外防止屏幕锁屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		loadViewLayout();
		findViewById();
		setListener();
	}

	protected void loadViewLayout() {
		setContentView(R.layout.userwalk);
		CommonUtils.getInstance().addActivity(this);
		mContext = getApplicationContext();

		startservice();

	}

	protected void findViewById() {
		userwalk_tv_start = (TextView) findViewById(R.id.userwalk_tv_start);
		userwalk_layout_start = (LinearLayout) findViewById(R.id.userwalk_layout_start);
		CommonUtils.getInstance().setTextViewTypeface(this, userwalk_tv_start);

		// *********run**********
		userwalk_layout_running = (LinearLayout) findViewById(R.id.userwalk_layout_running);
		userwalk_run_iv_gps = (ImageView) findViewById(R.id.userwalk_run_iv_gps);
		userwalk_run_layout_hostory = (LinearLayout) findViewById(R.id.userwalk_run_layout_hostory);
		userwalk_run_tv_gpsinfo = (TextView) findViewById(R.id.userwalk_run_tv_gpsinfo);

		userwalk_run_tv_distance = (TextView) findViewById(R.id.userwalk_run_tv_distance);
		CommonUtils.getInstance().setTextViewTypeface(this, userwalk_run_tv_distance);

		userwalk_run_iv_map = (ImageView) findViewById(R.id.userwalk_run_iv_map);

		userwalk_run_tv_speed = (TextView) findViewById(R.id.userwalk_run_tv_speed);
		CommonUtils.getInstance().setTextViewTypeface(this, userwalk_run_tv_speed);
		userwalk_run_tv_time = (TextView) findViewById(R.id.userwalk_run_tv_time);
		CommonUtils.getInstance().setTextViewTypeface(this, userwalk_run_tv_time);
		userwalk_run_tv_calories = (TextView) findViewById(R.id.userwalk_run_tv_calories);
		CommonUtils.getInstance().setTextViewTypeface(this, userwalk_run_tv_calories);

		userwalk_run_iv_continue = (ImageView) findViewById(R.id.userwalk_run_iv_continue);
		userwalk_run_iv_pause = (ImageView) findViewById(R.id.userwalk_run_iv_pause);
		userwalk_run_iv_end = (ImageView) findViewById(R.id.userwalk_run_iv_end);

		// ********map***********
		userwalk_relat_map = (RelativeLayout) findViewById(R.id.userwalk_relat_map);
		userwalk_map_iv_back = (ImageView) findViewById(R.id.userwalk_map_iv_back);
		userwalk_map_iv_mylocation = (ImageView) findViewById(R.id.userwalk_map_iv_mylocation);
		bmapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = bmapView.getMap();
		bmapView.showZoomControls(false);

		userwalk_relat_map_result = (RelativeLayout) findViewById(R.id.userwalk_relat_map_result);
		userwalk_map_iv_resultback = (ImageView) findViewById(R.id.userwalk_map_iv_resultback);
		userwalk_map_iv_resultshare = (ImageView) findViewById(R.id.userwalk_map_iv_resultshare);
		userwalk_map_iv_resultposition = (ImageView) findViewById(R.id.userwalk_map_iv_resultposition);
		userwalk_map_tv_resultdistance = (TextView) findViewById(R.id.userwalk_map_tv_resultdistance);
		CommonUtils.getInstance().setTextViewTypeface(this, userwalk_map_tv_resultdistance);
		userwalk_map_iv_resultavatar = (CircleImageView) findViewById(R.id.userwalk_map_iv_resultavatar);
		userwalk_map_tv_resultname = (TextView) findViewById(R.id.userwalk_map_tv_resultname);
		userwalk_map_tv_resulttime = (TextView) findViewById(R.id.userwalk_map_tv_resulttime);
		userwalk_map_tv_reslutspeed = (TextView) findViewById(R.id.userwalk_map_tv_reslutspeed);
		CommonUtils.getInstance().setTextViewTypeface(this, userwalk_map_tv_reslutspeed);
		userwalk_map_tv_resluttime = (TextView) findViewById(R.id.userwalk_map_tv_resluttime);
		CommonUtils.getInstance().setTextViewTypeface(this, userwalk_map_tv_resluttime);
		userwalk_map_tv_reslutcalories = (TextView) findViewById(R.id.userwalk_map_tv_reslutcalories);
		CommonUtils.getInstance().setTextViewTypeface(this, userwalk_map_tv_reslutcalories);
		userwalk_map_tv_resultsure = (TextView) findViewById(R.id.userwalk_map_tv_resultsure);

	}

	protected void setListener() {
		asyncImageLoader = new ImageLoader(this, "headImg");
		userwalk_run_layout_hostory.setOnClickListener(this);
		userwalk_run_iv_map.setOnClickListener(this);
		userwalk_run_iv_continue.setOnClickListener(this);
		userwalk_run_iv_pause.setOnClickListener(this);
		userwalk_run_iv_end.setOnClickListener(this);
		userwalk_map_iv_back.setOnClickListener(this);
		userwalk_map_iv_mylocation.setOnClickListener(this);

		userwalk_map_iv_resultback.setOnClickListener(this);
		userwalk_map_iv_resultshare.setOnClickListener(this);
		userwalk_map_iv_resultposition.setOnClickListener(this);
		userwalk_map_tv_resultsure.setOnClickListener(this);
		initUserDate();
		setCountTimer();
	}

	private void initUserDate() {
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		bid = bestDoInfoSharedPrefs.getString("bid", "nobid");
		deptId = bestDoInfoSharedPrefs.getString("deptId", "");
		nickname = bestDoInfoSharedPrefs.getString("nickName", "");
		ablum = bestDoInfoSharedPrefs.getString("ablum", "");
		mobile = ConfigUtils.getInstance().getXngHao();
	}

	/**
	 * 倒计时
	 */
	private void setCountTimer() {
		userwalk_layout_start.setVisibility(View.VISIBLE);
		MyCountTimer myCountTimer = new MyCountTimer(5000, 1000, userwalk_tv_start, minitHandler, LOAD);
		myCountTimer.start();
	}

	protected static final int LOAD = 0;
	protected static final int CUTMAP = 1;
	private final int SET = 2;
	Handler minitHandler = new Handler() {
		@SuppressLint("NewApi")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOAD:
				userwalk_layout_start.setVisibility(View.GONE);
				startTimestamp = System.currentTimeMillis();
				switchMapOrRunShowstatus();
				switchPauseOrContinueBtn();
				initGps();
				initLbsClient();
				initOnEntityListener();
				initTimers();
				break;
			case CUTMAP:
				mBaiduMap.snapshot(callback);

				break;
			case SET:
				// bmp = saveCurrentImage();
				// UMshare();
				// imgView.setImageBitmap(bitmap);
				BitmapDrawable bd = new BitmapDrawable(temBitmap_BG);
				userwalk_relat_map_result.setBackgroundDrawable(bd);
				screenShare();
				break;
			}
		}
	};

	protected void initTimers() {
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
						if (btnContinueStatus) {
							Message msg = new Message();
							runingTimestamp = System.currentTimeMillis() - startTimestamp - pauseTimestamp;
							if (runingTimestamp % 600000 == 0) {
								// 每十分钟上传一次数据
								if (!firstUploadDate) {
									walking_status = CONTINUEWALKSTAUS;
									uploadrunedDate();
								}

							}
							handler.sendMessage(msg);// 通知主线程
						}
					}
				}
			};
			thread.start();
		}
	}

	// 配速计算公式 = 时间 / 公里数
	// 跑步热量（kcal）＝体重（kg）×距离（公里）×1.036
	// 假定用户体重为70KG
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (distance >= 0.01) {
				distance = Double.valueOf(PriceUtils.getInstance().getPriceTwoDecimal(distance, 2));
				calories = (int) (weight * distance * 1.036);
				formatspeed(runingTimestamp);
			}
			userwalk_run_tv_distance.setText(PriceUtils.getInstance().getPriceTwoDecimal(distance, 2));
			userwalk_run_tv_speed.setText(speed + "");
			userwalk_run_tv_time.setText(formatTimes(runingTimestamp));
			userwalk_run_tv_calories.setText(calories + "");
			if (calories == 0) {
				userwalk_run_tv_calories.setText("00");
			}
			if (TextUtils.isEmpty(speed)) {
				userwalk_run_tv_speed.setText("00");
			}
		}
	};
	private LocationClient mLocClient;

	private void formatspeed(long time) {
		time = time / 1000;
		long second_ = time % 60;
		long minute_ = (time % 3600) / 60;
		long hour_ = time / 3600;
		long totalsecond = hour_ * 3600 + minute_ * 60 + second_;
		long totalSec = (long) (totalsecond / distance);

		long second = totalSec % 60;
		long minute = (totalSec % 3600) / 60;
		long hour = totalSec / 3600;
		// 秒显示两位
		String strSecond = ("00" + second).substring(("00" + second).length() - 2);
		// 分显示两位
		String strMinute = ("00" + minute).substring(("00" + minute).length() - 2);
		// 时显示两位
		String strHour = ("00" + hour).substring(("00" + hour).length() - 2);
		if (strHour.equals("00")) {
			speed = strMinute + "'" + strSecond + "\"";
		} else {
			speed = "--";
		}

	}

	/**
	 * 得到一个格式化的时间
	 * 
	 * @param time
	 *            时间 毫秒
	 * @return 时：分：秒：毫秒
	 */
	private String formatTimes(long time) {
		time = time / 1000;
		long second = time % 60;
		long minute = (time % 3600) / 60;
		long hour = time / 3600;

		// 毫秒秒显示两位
		// String strMillisecond = "" + (millisecond / 10);
		// 秒显示两位
		String strSecond = ("00" + second).substring(("00" + second).length() - 2);
		// 分显示两位
		String strMinute = ("00" + minute).substring(("00" + minute).length() - 2);
		// 时显示两位
		String strHour = ("00" + hour).substring(("00" + hour).length() - 2);

		return strHour + ":" + strMinute + ":" + strSecond;
		// + strMillisecond;
	}

	/**
	 * LocationClientOption.LocationMode.Battery_Saving:低功耗定位 不用GPS (wifi 基站)
	 *
	 * LocationClientOption.LocationMode.Hight_Accuracy:高精度定位 全开GPS wifi 基站
	 *
	 * LocationClientOption.LocationMode.Device_Sensors 仅仅使用设备 GPS 不支持室内
	 */
	private void initLbsClient() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		// 初始化轨迹服务客户端
		client = new LBSTraceClient(this);
		// 设置定位模式
		client.setLocationMode(LocationMode.Device_Sensors);
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
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(networklocationListener);
		// 创建一个定位客户端的参数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 是否打开GPS
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(4000);// 1000毫秒定位一次
		// com.baidu.location.LocationClientOption.LocationMode mLocationMode =
		// LocationClientOption.LocationMode.Battery_Saving;
		// option.setLocationMode(mLocationMode);
		mLocClient.setLocOption(option);// 设置给定位客户端
		mLocClient.start();// 启动定位客户端
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
				userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps2);
				userwalk_run_tv_gpsinfo.setText("信号一般");
				gpslocationListenerStatus = true;
				firstnetLocationstatus = true;
			} else {
				if (btnContinueStatus) {
					mTrackUploadFragment.isInUploadFragment = true;
				}
				userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps3);
				userwalk_run_tv_gpsinfo.setText("信号良好");
				gpslocationListenerStatus = true;
				firstnetLocationstatus = true;
			}
			// userwalk_run_tv_gpsinfo.setText(userwalk_run_tv_gpsinfo.getText()
			// + "gps" + accuracy);
		}
	}

	int BEFORECURRENT_SETP = -1;
	boolean gpslocationListenerStatus = false;

	boolean firstnetLocationstatus = true;
	private BDLocationListener networklocationListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (!gpslocationListenerStatus && btnContinueStatus) {
				if (location == null || !ConfigUtils.getInstance().getNetWorkStatus(UserWalkingActivity.this)) {
					mTrackUploadFragment.isInUploadFragment = false;
					userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps0);
					userwalk_run_tv_gpsinfo.setText("无信号");
					return;
				} else {
					if (location.getLocType() == 167 || location.getLatitude() == 0 || location.getRadius() > 100) {
						mTrackUploadFragment.isInUploadFragment = false;
						userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps1);
						userwalk_run_tv_gpsinfo.setText("信号较差数据准确度较低");
					} else {
						if (firstnetLocationstatus) {
							// 第一次不定位，防止漂浮坐标
							firstnetLocationstatus = false;
							return;

						}
						mTrackUploadFragment.isInUploadFragment = true;
						userwalk_run_iv_gps.setBackgroundResource(R.drawable.userwalk_run_gps2);
						userwalk_run_tv_gpsinfo.setText("信号良好");
						if (mTrackUploadFragment != null && mTrackUploadFragment.isInUploadFragment) {
							LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
							setline(latLng);
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
		boolean sensorAva = (BEFORECURRENT_SETP != StepDetector.CURRENT_SETP) && Config.config().isSensorAvalible();
		if (sensorAva || !Config.config().isSensorAvalible()) {
			line(latLng);
		} else {
		}
	}

	private void line(LatLng latLng) {
		mTrackUploadFragment.showRealtimeTrack(latLng);
		distance = mTrackUploadFragment.sum_distance;
		BEFORECURRENT_SETP = StepDetector.CURRENT_SETP;
		if (firstUploadDate) {
			walking_status = STARTWALKSTAUS;
			uploadrunedDate();
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
			if (gpslocationListenerStatus && mTrackUploadFragment != null && mTrackUploadFragment.isInUploadFragment
					&& location != null) {
				// userwalk_run_tv_gpsinfo.setText(userwalk_run_tv_gpsinfo.getText()+"
				// gps "+location.getAccuracy());

				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				LatLng sourceLatLng = latLng;
				CoordinateConverter converter = new CoordinateConverter();
				converter.from(CoordType.GPS);
				converter.coord(sourceLatLng);
				setline(converter.convert());
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
	protected String walking_status = "";
	private CharSequence endtime;
	protected String step_time;
	private float zoom = 19;

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
		criteria.setSpeedRequired(false);
		// 设置是否允许运营商收费
		criteria.setCostAllowed(false);
		// 设置是否需要方位信息
		criteria.setBearingRequired(false);
		// 设置是否需要海拔信息
		criteria.setAltitudeRequired(false);
		// 设置对电源的需求
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}

	/**
	 * 切换run或map页面
	 */
	private void switchMapOrRunShowstatus() {
		if (mapStatus) {
			userwalk_layout_running.setVisibility(View.GONE);
			userwalk_relat_map.setVisibility(View.VISIBLE);
		} else {
			userwalk_layout_running.setVisibility(View.VISIBLE);
			userwalk_relat_map.setVisibility(View.GONE);
		}
	}

	/**
	 * 切换暂停 继续结束 两种状态
	 */
	private void switchPauseOrContinueBtn() {
		if (btnContinueStatus) {
			userwalk_run_iv_pause.setVisibility(View.VISIBLE);
			userwalk_run_iv_continue.setVisibility(View.GONE);
			userwalk_run_iv_end.setVisibility(View.GONE);
		} else {
			userwalk_run_iv_pause.setVisibility(View.GONE);
			userwalk_run_iv_continue.setVisibility(View.VISIBLE);
			userwalk_run_iv_end.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 点击结束
	 * 
	 * @param dialogType
	 */
	public void endDialog(final String dialogType) {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_userwalking);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定
		TextView myexit_text_title = (TextView) selectDialog.findViewById(R.id.myexit_text_title);
		myexit_text_title.setGravity(Gravity.CENTER);
		text_sure.setText("确定");
		if (dialogType.equals("shortDistance")) {
			myexit_text_title.setText("此次运动距离太短，无法保存记录");
		} else if (dialogType.equals("notHaveNet")) {
			text_off.setText("暂不");
			text_sure.setText("继续丢弃");
			myexit_text_title.setText("无网络连接，是否丢弃该次记录?");
		} else {
			myexit_text_title.setText("是否结束本次计步?");
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
				if (dialogType.equals("shortDistance") || dialogType.equals("notHaveNet")) {
					finish();
				} else {
					walking_status = ENDRESULTPAGESTAUS;
					uploadrunedDate();
				}

			}
		});
	}

	private void uploadrunedDate() {
		if (mTrackUploadFragment.getuploadPointList() != null && mTrackUploadFragment.getuploadPointList().size() > 0) {
			firstUploadDate = false;
			pullEveneyData();
		}
	}

	/**
	 * 上传每次
	 */
	protected void pullEveneyData() {
		HashMap<String, String> mHashMap;
		mHashMap = new HashMap<String, String>();
		mHashMap.put("uid", uid);
		if (bid.equals("nobid")) {
			mHashMap.put("bid", "" + 0);
		} else {
			mHashMap.put("bid", bid);
		}
		mHashMap.put("deptid", deptId);
		mHashMap.put("step_num", calories + "");
		mHashMap.put("km_num", PriceUtils.getInstance().getPriceTwoDecimal(distance, 2));
		mHashMap.put("step_time", formatTimes(runingTimestamp));
		if (walking_status.equals(ENDRESULTPAGESTAUS)) {
			mHashMap.put("status", "2");
		} else {
			mHashMap.put("status", walking_status + "");
		}

		mHashMap.put("begindate", startTimestamp / 1000 + "");
		mHashMap.put("uploaddate", System.currentTimeMillis() / 1000 + "");
		mHashMap.put("ip", "");
		mHashMap.put("source", Constans.getInstance().source);
		mHashMap.put("m_type", mobile + "");
		mHashMap.put("v_num", speed + "");
		List<LatLng> pointList = mTrackUploadFragment.getuploadPointList();
		mHashMap.put("loc_data", getlocationArrayToJson(pointList));
		System.err.println(mHashMap);
		new UserWalkingPostDataBusiness(mContext, mHashMap, new UserWalkingPostDataCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {

						LatLng mEndpoint = mTrackUploadFragment.getuploadPointList()
								.get(mTrackUploadFragment.getuploadPointList().size() - 1);
						mTrackUploadFragment.getuploadPointList().clear();
						mTrackUploadFragment.getuploadPointList().add(mEndpoint);
					}

				}
				if (walking_status.equals(ENDRESULTPAGESTAUS)) {
					endtime = DatesUtils.getInstance().getNowTime("MM月dd日 HH:mm");
					step_time = formatTimes(runingTimestamp);
					showWalkResult();
				}

			}
		});
	}

	/**
	 * 结果页
	 */
	private void showWalkResult() {
		if (mTrackUploadFragment.allpointList != null && mTrackUploadFragment.allpointList.size() > 0) {
			zoom = ConfigUtils.getInstance().setZoom(mTrackUploadFragment.allpointList);
			LatLng nowpoint = ConfigUtils.getInstance().getCenterpoint(mTrackUploadFragment.allpointList);
			mTrackUploadFragment.setMapupdateStatus(zoom, nowpoint);
		}

		mapStatus = true;
		switchMapOrRunShowstatus();
		userwalk_map_iv_back.setVisibility(View.GONE);
		userwalk_map_iv_mylocation.setVisibility(View.GONE);
		userwalk_relat_map_result.setVisibility(View.VISIBLE);
		userwalk_map_tv_resultdistance.setText(PriceUtils.getInstance().getPriceTwoDecimal(distance, 2));
		userwalk_map_tv_resultname.setText(nickname);
		userwalk_map_tv_resulttime.setText(endtime);
		userwalk_map_tv_reslutspeed.setText(speed + "");
		userwalk_map_tv_resluttime.setText(step_time);
		userwalk_map_tv_reslutcalories.setText(calories + "");

		if (calories == 0) {
			userwalk_map_tv_reslutcalories.setText("00");
		}
		if (TextUtils.isEmpty(speed)) {
			userwalk_map_tv_reslutspeed.setText("00");
		}
		if (!TextUtils.isEmpty(ablum)) {
			asyncImageLoader.DisplayImage(ablum, userwalk_map_iv_resultavatar);
		} else {
			Bitmap mBitmap = asyncImageLoader.readBitMap(this, R.drawable.user_default_icon);
			userwalk_map_iv_resultavatar.setImageBitmap(mBitmap);
		}

	}

	private String getlocationArrayToJson(List<LatLng> list) {

		if (list != null) {
			JSONArray array = new JSONArray();
			int length = list.size();
			double lat;
			double lon;
			try {
				for (int i = 0; i < length; i++) {
					LatLng latLng = list.get(i);
					lat = latLng.latitude;
					lon = latLng.longitude;
					if (latLng != null) {
						JSONObject latObject = new JSONObject();
						latObject.put("latitude", lat);
						latObject.put("longitude", lon);
						array.put(latObject);
					}
				}
				return array.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";

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

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
		bmapView.onDestroy();
		bmapView = null;
		setstopService();
	}

	private void doback() {
		if (mapStatus) {
			if (walking_status.equals(ENDRESULTPAGESTAUS)) {
				finish();
			}
			mapStatus = false;
			switchMapOrRunShowstatus();
		}
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
			if (bmapView != null) {
				bmapView.onPause();
			}
			super.onPause();
		} catch (Exception e) {
		}
	}

	@Override
	protected void onResume() {
		try {
			if (bmapView != null) {
				bmapView.onResume();
			}
			super.onResume();
		} catch (Exception e) {
		}
	}

	Bitmap temBitmap_BG;
	SnapshotReadyCallback callback = new SnapshotReadyCallback() {
		/**
		 * 地图截屏回调接口
		 * 
		 * @param snapshot
		 *            截屏返回的 bitmap 数据
		 */
		public void onSnapshotReady(Bitmap snapshot) {
			temBitmap_BG = snapshot;
			minitHandler.sendEmptyMessage(SET);
		}
	};
	private String file;
	private LinearLayout ll;

	private void screenShare() {
		if (!BestDoApplication.getInstance().msgApi.isWXAppInstalled()) {
			Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
			return;
		}
		file = ScreenShareUtil.shotBitmap(this);
		File f = new File(file);
		if (f != null && f.exists() && f.length() > 1000) {
			ViewGroup.LayoutParams top = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					(int) DpUtil.dpToPx(this, 400));
			ll = new LinearLayout(this);
			ll.setBackground(new ColorDrawable(0x88000000));
			ImageView imgView = new ImageView(this);
			params.setMargins(0, (int) DpUtil.dpToPx(this, 80), 0, 0);
			Bitmap bitmap = BitmapFactory.decodeFile(file);
			imgView.setImageBitmap(bitmap);
			// imgView.setImageBitmap(temBitmap_BG);

			ll.addView(imgView, params);
			addContentView(ll, top);
			UMshare(bitmap);
		}
	}
	// 截屏

	Bitmap bmp;
	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

	private void onShareComplete() {
		refreshView();
		deleteShareFile();
		userwalk_relat_map_result.setBackgroundDrawable(null);
		cutStatus = false;
	}

	private void refreshView() {
		ll.removeAllViews();
		ll.setVisibility(View.GONE);
	};

	private void deleteShareFile() {
		try {
			File f = new File(file);
			f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void UMshare(Bitmap bmp) {
		if (bmp != null) {
			String appID = "wx1d30dc3cd2adc80c";
			String appSecret = "2b129f9c81e4a75de4c32ace80f83b9a";
			mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
					SHARE_MEDIA.SINA);
			// 添加微信平台
			UMWXHandler wxHandler = new UMWXHandler(this, appID, appSecret);
			wxHandler.addToSocialSDK();
			// 设置分享内容,防止被重写
			mController.setShareContent(null);
			mController.setShareMedia(new UMImage(this, bmp));
			// 添加微信朋友圈
			UMWXHandler wxCircleHandler = new UMWXHandler(this, appID, appSecret);
			wxCircleHandler.setToCircle(true);
			wxCircleHandler.addToSocialSDK();
			// qq
			UMQQSsoHandler qq = new UMQQSsoHandler(this, "1105290700", "yKdi86YWzF0ApOz6");
			qq.addToSocialSDK();
			QQShareContent qqShareContent = new QQShareContent();
			qqShareContent.setShareImage(new UMImage(this, bmp));
			qqShareContent.setShareContent(null);
			mController.setShareMedia(qqShareContent);
			// 关闭友盟分享的toast
			mController.getConfig().closeToast();
			mController.setShareBoardListener(new UMShareBoardListener() {

				@Override
				public void onShow() {
				}

				@Override
				public void onDismiss() {
					onShareComplete();
				}
			});
			mController.openShare(this, new SnsPostListener() {

				@Override
				public void onStart() {

				}

				@Override
				public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
					if (arg1 == 200) {
						CommonUtils.getInstance().initToast(UserWalkingActivity.this, "分享成功");
					} else {
						CommonUtils.getInstance().initToast(UserWalkingActivity.this, "分享失败");
					}
					onShareComplete();
				}

			});
		}

	}
}
