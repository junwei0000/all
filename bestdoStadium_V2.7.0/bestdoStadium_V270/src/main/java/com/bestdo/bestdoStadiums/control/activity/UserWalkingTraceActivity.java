package com.bestdo.bestdoStadiums.control.activity;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
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
import com.bestdo.bestdoStadiums.control.walking.StepCounterService;
import com.bestdo.bestdoStadiums.control.walking.StepDetector;
import com.bestdo.bestdoStadiums.control.walking.TrackUploadFragment;
import com.bestdo.bestdoStadiums.control.walking.TrackresultFragment;
import com.bestdo.bestdoStadiums.model.UserWalkingHistoryInfo;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
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
import android.graphics.Bitmap.Config;
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
 * @Description 轨迹结果页
 */
public class UserWalkingTraceActivity extends FragmentActivity implements OnClickListener {

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

	private TrackresultFragment mTrackUploadFragment;
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
	private String nickname;
	private String ablum;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userwalk_map_iv_resultposition:
			mTrackUploadFragment.setMapupdateStatus(zoom, mTrackUploadFragment.nowpoint);
			break;
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
		setHistoryResultView();
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
				initLbsClient();
				initOnEntityListener();
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
			mTrackUploadFragment = new TrackresultFragment();
			transaction.add(R.id.fragment_content, mTrackUploadFragment);
		} else {
			transaction.show(mTrackUploadFragment);
		}
		mTrackUploadFragment.startRefreshThread(true);
		// TrackUploadFragment.addMarker();
		mBaiduMap.setOnMapClickListener(null);
		// 事务提交
		transaction.commit();
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

	// 状态监听
	protected String walking_status = "";
	private CharSequence endtime;

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
	 * 结果页
	 */
	private void showWalkResult() {
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mTrackUploadFragment != null) {
			mTrackUploadFragment.stopTrace();
		}
		if (mBaiduMap != null)
			mBaiduMap.clear();

	}

	private void doback() {
		if (walking_status.equals(ENDWALKSTAUS) && mapStatus) {
			finish();
		} else if (mapStatus) {
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

	// ****************健步走轨迹结果页*************************
	String log_id;
	private HashMap<String, String> mhashmap;
	protected String step_time;
	protected float zoom = 17;

	private void setHistoryResultView() {
		Intent intent = getIntent();
		String skipTypeActivity = intent.getStringExtra("skipTypeActivity");
		if (skipTypeActivity.equals("History")) {
			log_id = intent.getStringExtra("log_id");
			mapStatus = true;
			btnContinueStatus = false;
			walking_status = ENDWALKSTAUS;
			switchMapOrRunShowstatus();
			switchPauseOrContinueBtn();
			initLbsClient();
			initOnEntityListener();
			mhashmap = new HashMap<String, String>();
			mhashmap.put("uid", uid);
			mhashmap.put("log_id", "" + log_id);
			System.err.println(mhashmap);
			new UserWalkingGetTrackBusiness(mContext, mhashmap, new GetUserWalkingGetTrackCallback() {
				@Override
				public void afterDataGet(HashMap<String, Object> dataMap) {
					if (dataMap != null) {
						String status = (String) dataMap.get("status");
						if (status.equals("200")) {
							calories = (Integer) dataMap.get("step_num");
							distance = (Double) dataMap.get("km_num");
							speed = (String) dataMap.get("v_num");
							step_time = (String) dataMap.get("step_time");
							endtime = (String) dataMap.get("endtime");
							ArrayList<LatLng> mList = (ArrayList<LatLng>) dataMap.get("mList");
							if (mTrackUploadFragment != null && mList != null && mList.size() > 0) {
								zoom = ConfigUtils.getInstance().setZoom(mList);
								LatLng nowpoint = ConfigUtils.getInstance().getCenterpoint(mList);
								mTrackUploadFragment.showpointList = mList;
								mTrackUploadFragment.drawRealtimePoint(mList.get(mList.size() - 1));
								mTrackUploadFragment.setMapupdateStatus(zoom, nowpoint);
							}
							showWalkResult();
						}
					}
					CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
				}
			});

		} else {
			setCountTimer();
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
						CommonUtils.getInstance().initToast(UserWalkingTraceActivity.this, "分享成功");
					} else {
						CommonUtils.getInstance().initToast(UserWalkingTraceActivity.this, "分享失败");
					}
					onShareComplete();
				}

			});
		}

	}
}
