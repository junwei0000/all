package com.KiwiSports.control.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.KiwiSports.R;
import com.KiwiSports.business.GrabTreasureBusiness;
import com.KiwiSports.business.GrabTreasureBusiness.GetGrabTreasureCallback;
import com.KiwiSports.business.InitRecordBusiness;
import com.KiwiSports.business.RecordDatesloadBusiness;
import com.KiwiSports.business.RecordDatesloadBusiness.GetRecordDatesloadCallback;
import com.KiwiSports.business.UpdateLocationBusiness;
import com.KiwiSports.business.UpdateLocationBusiness.GetUpdateLocationCallback;
import com.KiwiSports.business.VenuesInfoBylicationBusiness;
import com.KiwiSports.business.VenuesInfoBylicationBusiness.GetInfoBylicationCallback;
import com.KiwiSports.business.VenuesMyAreaUsersBusiness;
import com.KiwiSports.business.VenuesMyAreaUsersBusiness.GetVenuesMyAreaUsersCallback;
import com.KiwiSports.business.VenuesTreasureBusiness;
import com.KiwiSports.business.VenuesTreasureBusiness.GetVenuesTreasureCallback;
import com.KiwiSports.control.adapter.MainPropertyAdapter;
import com.KiwiSports.control.adapter.MapListAdapter;
import com.KiwiSports.control.adapter.VenuesListAdapter;
import com.KiwiSports.control.locationService.LocationService;
import com.KiwiSports.control.locationService.LocationStatusManager;
import com.KiwiSports.control.locationService.Utils;
import com.KiwiSports.control.step.StepService;
import com.KiwiSports.control.step.StepUtils;
import com.KiwiSports.control.view.MyApplication;
import com.KiwiSports.control.view.TrackUploadFragment;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesTreasInfo;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.model.db.BaseDBHelper;
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.model.db.TrackListDBOpenHelper;
import com.KiwiSports.utils.App;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.GPSUtil;
import com.KiwiSports.utils.ImageLoader;
import com.KiwiSports.utils.LanguageUtil;
import com.KiwiSports.utils.MobileInfoUtils;
import com.KiwiSports.utils.MyDialog;
import com.KiwiSports.utils.MyGridView;
import com.KiwiSports.utils.PriceUtils;
import com.KiwiSports.utils.PullDownListView;
import com.KiwiSports.utils.parser.MainsportParser;
import com.KiwiSports.utils.volley.RequestUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.icu.text.AlphabeticIndex;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述： 首页轨迹
 */
public class MainStartActivity extends FragmentActivity implements
        OnClickListener {

    private ImageView pagetop_iv_you;
    private LinearLayout page_top_layout, layout_start;

    private FrameLayout frame_map, frame_list;
    private ImageView iv_start;
    private ImageView map_iv_mylocation;
    private TextView tv_distancetitle, tv_distance;
    private LinearLayout layout_chat;
    private ImageView map_iv_selectcollect, map_iv_selectchat;
    private CircleImageView map_iv_selectablum;
    private TextView tv_map, tv_list, tv_mapline, tv_listline;
    private PullDownListView mPullDownView;
    private ListView mListView;
    private LinearLayout not_date;

    private Activity mHomeActivity;
    private Intent service;
    private SharedPreferences bestDoInfoSharedPrefs;
    public static Context mActivity;
    MediaPlayer mHongBaoPlayer;
    MediaPlayer mediaPlayer;
    // -----------参数------------
    /**
     * 点击开始，每条轨迹的标记时间，保存数据库时使用
     */
    private String startTrackTime = "";
    private String uid = "";
    private String token = "";
    private String access_token = "";
    private String recordDatas = "";
    private double longitude_me;
    private double latitude_me;
    /**
     * 是否第一次定位---进行上传定位和加载周边用户
     */
    boolean firstUploadLocationstatus = true;
    private long runingTimestamp;// 运动时间
    private long startTimestamp;// 开始时间
    private long pauseTimestamp;// 暂停时间，单位，毫秒
    private long sleepDuration;//休息时间，速度为0时自动统计，单位，秒
    private long initTimestamp;// 初始化时间
    private int sportindex;
    private int sportFieldIndex;
    // --------------
    private double distanceTraveled;// 总距离
    private double distanceOfBeforeLat;// 上一个坐标点的距离
    private int nSteps;// 步数
    public static String duration;// 运动时间
    private long freezeDuration;// 休息时间
    protected String address;

    private String minMatchSpeed = "--";// 最慢配速
    private String matchSpeed = "--";// 配速
    private String maxMatchSpeed = "--";// 最快配速 / 最大速度
    private String averageMatchSpeed = "--";// 平均配速

    // ----计算最大配速的参数------
    int beforemaxMatchSpeedDis = 0;// 上一个计算距离点
    long previousLapTimestamp = 0;// 上一公里时间点

    private long minmatchSpeedTimestamp;// 最慢配速时间戳
    private long matchSpeedTimestamp;// 配速时间戳
    private long maxMatchSpeedTimestamp;// 最快配速 / 最大速度时间戳
    private int minAltidue;// 每次最高海拔
    private int maxAltitude;// 每次最高海拔
    private int minAltidueall;// 最低海拔
    private int maxAltidueall;// 最高海拔
    private int currentAltitude;// 当前海拔
    private int initAltitude;// 初始海拔
    private int beforeAltitude;// 前一个海拔
    private int currentAccuracy;// 精度
    public static double Speed;// 速度
    private double averageSpeed;// 平均速度
    private double topSpeed;// 最高速度
    private double minSpeed;// 最小速度
    private double calorie;// 热量
    private String posid = "";// 场地id
    private String pos_name = "";
    private String record_data_id = "";


    private String latitudeOffset = "";
    private String longitudeOffset = "";
    private String cableCarQueuingDuration = "";// 缆车排队时间
    private String wrestlingCount = "";// 摔跤次数
    private String totalHoverDuration = "";// 总滞空时间
    private String maxHoverDuration = "";// 最大滞空时间
    private int dropTraveled = 0;// 滑行落差
    private int hopCount = 0;// 跳跃次数

    // 滑雪
    private int lapCount = 0;// 趟数（上升和下降的次数）
    private int upHillDistance = 0;// 上坡距离 （距离坐标点的累加） m
    private int downHillDistance = 0;// 下坡距离/滑行距离 m
    private int verticalDistance = 0;// 滑行落差/垂直距离 m
    private int _nMaxSlopeAngle = 0;// 最大坡度
    /**
     * 上升状态（1：上升状态； 2：下降状态）
     */
    private int nskiStatus = 0;

    /**
     * 当前是否是GPS定位
     */
    boolean gpslocationListenerStatus = false;

    /**
     * 第一次定位过滤
     */
    boolean firstLocationstatus = true;

    /**
     * 切换暂停 继续结束 两种状态;true为执行状态
     */
    protected boolean btnContinueStatus = false;
    /**
     * 是否开始;true为执行状态
     */
    protected boolean btnStartStatus = false;

    private Thread thread;
    /**
     * 是否开启语音
     */
    private boolean cb_voicestatus;
    private SharedPreferences welcomeSharedPreferences;
    private LinearLayout pagetop_layoutv_you;
    private Bundle paramBundle;
    private String nick_name;

    private RecordListDBOpenHelper mRecordListDB;
    private TrackListDBOpenHelper mTrackListDBOpenHelper;
    private RecordListUtils mRecordListUtils;


    int speakTime = 60 * 5;
    /**
     * 是否运动500米之前手动选择运动类型.如果选择了运动类型，那么系统不再根据场地条件 或 者是自动判断运动类型
     */
    boolean selectSportTypeByManuallyStatus = false;
    /**
     * 是否5分钟时当自动切换运动类型
     */
    boolean autoChangeSportStus = false;
    ArrayList<MainSportInfo> mallsportList;
    private MainsportParser mMainsportParser;
    SharedPreferences mset;
    /**
     * 是否正在加载缓存
     */
    Boolean updateTrackHistoryStatus = false;
    /**
     * 所有轨迹点
     */
    private List<MainLocationItemInfo> allpointList = new ArrayList<MainLocationItemInfo>();
    /**
     * 当前坐标
     */
    private LatLng currentlatLng;
    /**
     * 定位时的前一个坐标
     */
    private LatLng beforelatLng;
    /**
     * 显示当前位置时的前一个坐标
     */
    private LatLng showLatLng;
    /**
     * 是否初始化进来
     */
    boolean FirstComeIn = true;
    /**
     * 是否要加载未完成的缓存
     */
    private boolean loadCancerStartSttus = false;

    /**
     * 地图模式 列表模式
     */
    private boolean mapStatus = true;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layoutv_you:
                Intent intent = new Intent(this, MainSportActivity.class);
                intent.putExtra("sportindex", sportindex);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 1);
                CommonUtils.getInstance().setPageIntentAnim(intent, this);
                break;
            case R.id.layout_start:
                if (btnStartStatus) {
                    Intent intents = new Intent(this, MainStarPropertytActivity.class);
                    intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    updateProperty(intents);
                    startActivity(intents);
                    CommonUtils.getInstance().setPageIntentAnim(intents, this);
                } else {
                    btnStart();
                }
                break;
            case R.id.map_iv_mylocation:
                moveMyLocationStaus = true;
                moveToCenter();
                setToFollowStatus();
                break;
            case R.id.tv_map:
                mapStatus = true;
                setMoShi();
                break;
            case R.id.tv_list:
                mapStatus = false;
                setMoShi();
                break;
            default:
                break;
        }
    }


    private void btnStart() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            btnStartStatus = true;
            btnContinueStatus = true;
            mUserListHideMap.clear();
            startTimestamp = System.currentTimeMillis();
            startTrackTime = DatesUtils.getInstance().getNowTime(
                    "yyyy-MM-dd HH:mm:ss");
            initGps();
            startLocationServices();
            setToFollowStatus();
            sportindex = -1;
            sportFieldIndex = -1;
            setSportPropertyList();
            startSpeak();
            startservice();
            initRecord();
            iv_start.setVisibility(View.GONE);
            tv_distancetitle.setVisibility(View.VISIBLE);
            tv_distance.setVisibility(View.VISIBLE);
        } else {
            endDialog("GPSNOTSTART");
        }
    }

    /**
     * 点击继续按钮
     */
    private void setClickContiue() {
        setToFollowStatus();
        btnContinueStatus = true;
        pauseTimestamp = System.currentTimeMillis() - runingTimestamp
                - startTimestamp;
        contiueSpeak();
        startservice();
    }

    private void setMoShi() {
        if (mapStatus) {
            tv_map.setTextColor(getResources().getColor(R.color.ching));
            tv_list.setTextColor(getResources().getColor(R.color.white));
            tv_mapline.setVisibility(View.VISIBLE);
            tv_listline.setVisibility(View.INVISIBLE);
            frame_map.setVisibility(View.VISIBLE);
            frame_list.setVisibility(View.INVISIBLE);
        } else {
            tv_map.setTextColor(getResources().getColor(R.color.white));
            tv_list.setTextColor(getResources().getColor(R.color.ching));
            tv_mapline.setVisibility(View.INVISIBLE);
            tv_listline.setVisibility(View.VISIBLE);
            frame_map.setVisibility(View.INVISIBLE);
            frame_list.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 点击暂停按钮
     */
    private void setClickpause() {
        pauseSpeak();
        setstopService("pause");
        btnContinueStatus = false;
    }

    /**
     * 点击结束按钮
     */
    private void setClickStop() {
        endSpeak();
        String dialogType;
        if (distanceTraveled < 0.05) {
            dialogType = "shortDistance";
            endDialog(dialogType);
        } else {
            if (determineSportType(true)) {
                dialogType = "AvoidCheating";
                endDialog(dialogType);
            }
            mHandler.sendEmptyMessage(UPDATELOCATIONCLAER);
            recordDatas = setRecordInfoArrayToJson(true);
            addRecordListDB(record_data_id, RecordListDBOpenHelper.currentTrackNotUpLoad);
            Constants.getInstance().addTrackStatus = true;
            if (ConfigUtils.getInstance().isNetWorkAvaiable(mActivity)) {
                mHandler.sendEmptyMessage(loadRecordDates);
            } else {
                initStartView();
                Log.e("track", "mDB.add");
            }
            skipDetail();

        }
    }

    /**
     * 点击结束，跳转详情
     */
    private void skipDetail() {
        Intent intent1;
        intent1 = new Intent(mHomeActivity,
                RecordDetailActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent1.putExtra("sporttype", "" + sportindex);
        intent1.putExtra("posid", posid);
        intent1.putExtra("record_id", record_data_id);
        intent1.putExtra("runStartTime", startTrackTime);
        intent1.putExtra("distanceTraveled", distanceTraveled * 1000);
        startActivity(intent1);
        CommonUtils.getInstance().setPageIntentAnim(intent1,
                mHomeActivity);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.paramBundle = arg0;
        Log.e("TESTLOG", "***********onCreate************");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
        // 另外防止屏幕锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        loadViewLayout();
        findViewById();
        setListener();
        processLogic();

        setLocationServiceBroadcast();
        initLbsClient();
        startLocationServices();
        initTimer();
        mHandler.sendEmptyMessage(showSettingsApp);
        setMoShi();
    }


    /**
     * 第一次显示app设置
     */
    private void showSettingsApp() {
        String welcomeSPFKey = Constants.getInstance().welcomeSharedPrefsKey;
        mset = getSharedPreferences(welcomeSPFKey,
                0);
        String showSettingsApp = mset.getString("showSettingsApp", "");
        if (TextUtils.isEmpty(showSettingsApp)) {
            String dialogType = "showSettingsApp";
            endDialog(dialogType);
        }

    }

    protected void loadViewLayout() {
        setContentView(R.layout.main_start);
        mActivity = getApplicationContext();
        mHomeActivity = CommonUtils.getInstance().mHomeActivity;
    }

    protected void findViewById() {
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.mMapView);
        mMapView.onCreate(paramBundle);
        mBaiduMap = mMapView.getMap();
        pagetop_layoutv_you = (LinearLayout) findViewById(R.id.pagetop_layoutv_you);
        pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
        page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
        CommonUtils.getInstance().setViewTopHeigth(mActivity, page_top_layout);

        tv_map = (TextView) findViewById(R.id.tv_map);
        tv_list = (TextView) findViewById(R.id.tv_list);
        tv_mapline = (TextView) findViewById(R.id.tv_mapline);
        tv_listline = (TextView) findViewById(R.id.tv_listline);
        map_iv_mylocation = (ImageView) findViewById(R.id.map_iv_mylocation);
        layout_chat = (LinearLayout) findViewById(R.id.layout_chat);
        map_iv_selectablum = (CircleImageView) findViewById(R.id.map_iv_selectablum);
        map_iv_selectcollect = (ImageView) findViewById(R.id.map_iv_selectcollect);
        map_iv_selectchat = (ImageView) findViewById(R.id.map_iv_selectchat);

        frame_map = (FrameLayout) findViewById(R.id.frame_map);
        frame_list = (FrameLayout) findViewById(R.id.frame_list);

        mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
        mListView = (ListView) findViewById(R.id.list_date);
        not_date = (LinearLayout) findViewById(R.id.not_date);
        not_date.setBackgroundColor(getResources().getColor(R.color.main_page_bg));
        TextView not_tv_cont = (TextView) findViewById(R.id.not_tv_cont);
        not_tv_cont.setText("亲，这里什么也没有呢~");
        not_tv_cont.setTextColor(getResources().getColor(R.color.white));
        iv_start = (ImageView) findViewById(R.id.iv_start);
        tv_distancetitle = (TextView) findViewById(R.id.tv_distancetitle);
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        layout_start = (LinearLayout) findViewById(R.id.layout_start);
        CommonUtils.getInstance().setTextViewTypeface(this, tv_distance);
        CommonUtils.getInstance().setTextViewTypeface(this, tv_distancetitle);
    }

    protected void setListener() {
        map_iv_mylocation.setOnClickListener(this);
        pagetop_layoutv_you.setOnClickListener(this);
        layout_start.setOnClickListener(this);
        tv_map.setOnClickListener(this);
        tv_list.setOnClickListener(this);

        mPullDownView.setRefreshListioner(new PullDownListView.OnRefreshListioner() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (!Constants.getInstance().refreshOrLoadMoreLoading) {
                            mPullDownViewHandler.sendEmptyMessage(REFLESH);
                        }
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                mPullDownViewHandler.sendEmptyMessage(DATAUPDATEOVER);
            }
        });
        mPullDownView.setOrderBottomMoreLine("list");
    }

    /**
     * 数据加载完后，判断底部“加载更多”显示状态
     */
    private final int DATAUPDATEOVER = 0;

    /**
     * 下拉刷新
     */
    private final int REFLESH = 1;
    @SuppressLint("HandlerLeak")
    Handler mPullDownViewHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DATAUPDATEOVER:
                    mPullDownView.onRefreshComplete();
                    Constants.getInstance().refreshOrLoadMoreLoading = false;
                    mPullDownView.onLoadMoreComplete(
                            getString(R.string.notmore_info), "over");
                    mPullDownView.setMore(false);
                    setNotDateImg();
                    break;
                case REFLESH:
                    if (!Constants.getInstance().refreshOrLoadMoreLoading) {
                        Constants.getInstance().refreshOrLoadMoreLoading = true;
                        mHandler.sendEmptyMessage(GETUSETLIST);
                    }
                    break;
            }
        }
    };

    protected void processLogic() {
        String welcomeSPFKey = Constants.getInstance().welcomeSharedPrefsKey;
        welcomeSharedPreferences = getSharedPreferences(welcomeSPFKey, 0);

        bestDoInfoSharedPrefs = CommonUtils.getInstance()
                .getBestDoInfoSharedPrefs(this);
        uid = bestDoInfoSharedPrefs.getString("uid", "");
        nick_name = bestDoInfoSharedPrefs.getString("nick_name", "");
        token = bestDoInfoSharedPrefs.getString("token", "");
        access_token = bestDoInfoSharedPrefs.getString("access_token", "");
        Log.e("TESTLOG", "***********access_token*********" + access_token);
        sportindex = -1;
        initTimestamp = System.currentTimeMillis();
        mRecordListDB = new RecordListDBOpenHelper(this);
        mTrackListDBOpenHelper = new TrackListDBOpenHelper(this);
        initPlayer();
        setSportPropertyList();
        saveDefaultLocationStatus();
        getRecordCalendarDataToDB();
        getRecordListDataToDB();
    }

    /**
     * 获取记录列表数据-缓存到db,按用户缓存，每个用户都缓存一次
     */
    private void getRecordListDataToDB() {
        mRecordListUtils = new RecordListUtils(this, mRecordListDB, uid, token, access_token);
        String welcomeSPFKey = Constants.getInstance().welcomeSharedPrefsKey;
        SharedPreferences mset = getSharedPreferences(welcomeSPFKey,
                0);
        boolean showSettingsApp = mset.getBoolean("firstGetRecordListDataToDB_" + uid, false);
        if ((!showSettingsApp || !mRecordListDB.hasRecordListInfo(uid)) && ConfigUtils.getInstance().getNetWorkStatus(this)) {
            SharedPreferences.Editor edit = mset.edit();
            edit.putBoolean("firstGetRecordListDataToDB_" + uid, true);
            edit.commit();
            mRecordListUtils.getRecordListDataToDB();
        }
    }

    /**
     * 初始化声音播报
     */
    private void initPlayer() {
        mHongBaoPlayer = MediaPlayer.create(this, R.raw.bonus);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new CompletionListener());
    }

    /**
     * 初始页面 刷新同步定位开关给后台
     */
    private void saveDefaultLocationStatus() {
        boolean cb_mylocationstatus = welcomeSharedPreferences.getBoolean(
                "cb_mylocationstatus", true);
        boolean cb_myanonlocationstatus = welcomeSharedPreferences.getBoolean(
                "cb_myanonlocationstatus", false);
        UpdateInfoUtils mUpdateInfoUtils = new UpdateInfoUtils(this);
        if (cb_mylocationstatus && cb_myanonlocationstatus) {
            mUpdateInfoUtils.UpdateInfo("is_anonymous", "1");
        } else {
            mUpdateInfoUtils.UpdateInfo("is_anonymous", "0");
        }
    }

    /**
     * 从后台缓存记录日历运动类型数据--缓存到本地
     */
    private void getRecordCalendarDataToDB() {
        RecordCalendarUtils mRecordCalendarUtils = new RecordCalendarUtils(mActivity, null, uid, token, access_token);
        mRecordCalendarUtils.getAllCadenrdats();
    }


    /**
     * 切换运动类型初始化显示属性
     */
    private void setSportPropertyList() {
        mMainsportParser = new MainsportParser();
        HashMap<Integer, MainSportInfo> mallsportList = mMainsportParser.parseJSONHash(App.getInstance().getContext());
        if (mallsportList.containsKey(sportindex)) {
            showSportPic();
            getCurrentPropertyValue();
        }
    }


    /**
     * //非跑步模式，场地权重最高 自动判断运动类型。A）如果正好在雪场，运动类型就是双板滑雪 B）如果正好在跑步场地，运动类型就是跑步
     * C）如果不在任何场地，0.3公里后自动判断运动类型，判断方法如下
     */
    private Boolean determineSportType(Boolean bAvoidCheating) {
        if (!bAvoidCheating) {
            /**
             * 200米 时没有手动切换运动类型时，自动取运动场地类型
             */

            if (btnStartStatus
                    && btnContinueStatus
                    && !selectSportTypeByManuallyStatus
                    && TextUtils.isEmpty(posid)
                    && distanceTraveled >= 0
                    && runingTimestamp > 0
                    && sportFieldIndex == -1
                    && distanceTraveled > 0.2) {

                getPosid();
            }
            // 未在任何运动场地内：300米时，自动判断运动类型
            // 若位于某运动场，则500米后再判断运动类型, 非跑步模式，场地权重最高。跑步模式，忽略场地
            if (btnStartStatus
                    && btnContinueStatus
                    && !selectSportTypeByManuallyStatus
                    && distanceTraveled >= 0
                    && runingTimestamp > 0
                    && sportindex == -1
                    && (distanceTraveled > 0.3 && sportFieldIndex == -1 || distanceTraveled > 0.5 && sportFieldIndex != -1)) {

                if (upHillDistance > 100 && downHillDistance > 100) {
                    sportindex = Constants.BGSportsTypeSki;// 双板滑雪
                } else {
                    if ((topSpeed < 20 && averageSpeed < 8 && nSteps
                            / (distanceTraveled * 1000) > 1.1)
                            || (topSpeed < 15 && averageSpeed < 6 && nSteps
                            / (distanceTraveled * 1000) > 0.7)
                            || (topSpeed < 8 && averageSpeed < 5 && nSteps
                            / (distanceTraveled * 1000) > 0.5)) {
                        sportindex = Constants.BGSportsTypeWalk;// 走路
                    } else if (topSpeed < 40
                            && averageSpeed < 15
                            && (nSteps / (distanceTraveled * 1000) > 0.6)) {
                        sportindex = Constants.BGSportsTypeRun;// 跑步
                    } else if (distanceTraveled > 0.8) {
                        if (topSpeed < 30
                                && averageSpeed < 20) {
                            sportindex = Constants.BGSportsTypeBike;// 骑行
                        } else if (topSpeed < 260 && averageSpeed < 150) {
                            sportindex = Constants.BGSportsTypeDrive;// 开车
                        } else {
                            sportindex = Constants.BGSportsTypeUnknown;// 其他
                        }
                    }

                }
                if (sportindex != Constants.BGSportsTypeUnknown) {
                    if (sportindex != 1 && sportFieldIndex != -1 || (sportFieldIndex == Constants.BGSportsTypeKiteSurfing
                            || sportFieldIndex == Constants.BGSportsTypeWindSurfing
                            || sportFieldIndex == Constants.BGSportsTypeSki)) { //非跑步模式，场地权重最高
                        sportindex = sportFieldIndex;
                    }

                    autoChangeSportStus = true;

                    mBaiduMap.setMapType(Constants.BaiduMapTYPE_NORMAL);// 交通图

                    setSportPropertyList();
                    autoDetectNotification();
                }
            }
        } else if (sportindex != Constants.BGSportsTypeDrive && sportindex != Constants.BGSportsTypeMotor &&
                sportindex != Constants.BGSportsTypeSki &&
                sportindex != Constants.BGSportsTypeSnowboard &&
                sportindex != Constants.BGSportsTypeWindSurfing &&
                sportindex != Constants.BGSportsTypeKiteSurfing) {//Avoid cheating 防止作弊
            int l_sportsType = sportindex;
            if (distanceTraveled > 0 && runingTimestamp > 0
                    && distanceTraveled > 0.5) {
                if ((topSpeed < 20 && averageSpeed < 8 && nSteps
                        / (distanceTraveled * 1000) > 1.1)
                        || (topSpeed < 15 && averageSpeed < 6 && nSteps
                        / (distanceTraveled * 1000) > 0.7)
                        || (topSpeed < 8 && averageSpeed < 5 && nSteps
                        / (distanceTraveled * 1000) > 0.5)) {
                    l_sportsType = Constants.BGSportsTypeWalk;// 走路
                } else if (topSpeed < 40
                        && averageSpeed < 15
                        && (nSteps / (distanceTraveled * 1000) > 0.6)) {
                    l_sportsType = Constants.BGSportsTypeRun;// 跑步
                } else if (topSpeed < 30
                        && averageSpeed < 20) {
                    l_sportsType = Constants.BGSportsTypeBike;// 骑行
                } else if (topSpeed < 260 && averageSpeed < 150) {
                    l_sportsType = Constants.BGSportsTypeDrive;// 开车
                } else if (topSpeed < 380) {
                    l_sportsType = Constants.BGSportsTypeHighSpeedTrain;//高铁
                } else {
                    l_sportsType = Constants.BGSportsTypePlane; //飞机
                }

            }
            if (l_sportsType == Constants.BGSportsTypeDrive) {
                sportindex = l_sportsType;
                setSportPropertyList();
                return true;
            }
        }
        return false;
    }

    /**
     * 根据运动类型更换相应的图标
     */
    private void showSportPic() {
        if (sportindex == Constants.BGSportsTypeWalk) {// 走路
            pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_walk_img);
        } else if (sportindex == Constants.BGSportsTypeRun) {// 跑步
            pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_run_img);
        } else if (sportindex == Constants.BGSportsTypeBike) {// 骑行
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_bike_img);
        } else if (sportindex == Constants.BGSportsTypeSnowboard) {// 单板滑雪
            pagetop_iv_you.setBackgroundResource(R.drawable.mainstart_snowboard_img);
        } else if (sportindex == Constants.BGSportsTypeSki) {// 双板滑雪
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_ski_img);
        } else if (sportindex == Constants.BGSportsTypeCrossCountryRun) {// 越野跑
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_cross_country_run_img);
        } else if (sportindex == Constants.BGSportsTypeMountainBike) {// 山地车越野
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_mountainbike_img);
        } else if (sportindex == Constants.BGSportsTypeSkate) {// 轮滑
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_skate_img);
        } else if (sportindex == Constants.BGSportsTypeMotor) {// 摩托车
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_motorbike_img);
        } else if (sportindex == Constants.BGSportsTypeLongboard) {// 长板
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_longboard_img);
        } else if (sportindex == Constants.BGSportsTypeRideHorse) {// 骑马
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_horseride_img);
        } else if (sportindex == Constants.BGSportsTypeWindSurfing) {// 帆板
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_windsurfing_img);
        } else if (sportindex == Constants.BGSportsTypeKiteSurfing) {// 风筝冲浪
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_kitesurfing_img);
        } else if (sportindex == Constants.BGSportsTypeDrive) {// 开车
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_drive_img);
        } else {
            pagetop_iv_you
                    .setBackgroundResource(R.drawable.mainstart_other_img);
        }
    }


    /**
     * 计算当前的属性值
     */
    private void getCurrentPropertyValue() {
        if (mTrackUploadFragment != null) {
            distanceTraveled = mTrackUploadFragment.sum_distance;
        }
        distanceTraveled = Double.valueOf(PriceUtils.getInstance()
                .formatFloatNumber(distanceTraveled));
        calorie = (int) (70 * distanceTraveled * 1.036);
        duration = DatesUtils.getInstance().formatTimes(runingTimestamp);
        freezeDuration = pauseTimestamp / 1000 + sleepDuration;
        Log.e("getCurrentPropertyValue", "duration=" + duration + "  ;freezeDuration==" + freezeDuration + "  ;pauseTimestamp=" + pauseTimestamp / 1000 + "  ;sleepDuration==" + sleepDuration);
        long time = runingTimestamp / 1000;
        if (time > 0) {
            averageSpeed = distanceTraveled * 1000 * 3.6 / time;// 单位：公里每小时
            averageSpeed = Double.valueOf(PriceUtils.getInstance()
                    .getPriceTwoDecimal(averageSpeed, 2));
            Speed = Double.valueOf(PriceUtils.getInstance().getPriceTwoDecimal(
                    Speed, 2));
            if (Speed < minSpeed) {
                minSpeed = Speed;
            }
            if (Speed > topSpeed && mTrackUploadFragment.isInUploadFragment) {
                topSpeed = Speed;
            }
            if (averageSpeed > topSpeed) {
                topSpeed = averageSpeed;
            }
            topSpeed = Double.valueOf(PriceUtils.getInstance().getPriceTwoDecimal(
                    topSpeed, 2));
        }
        if (distanceTraveled > 0) {
            matchSpeedTimestamp = DatesUtils.getInstance().computeMatchspeed(
                    runingTimestamp, distanceTraveled);
            averageMatchSpeed = DatesUtils.getInstance().formatMatchspeed(
                    matchSpeedTimestamp);
            matchSpeed = DatesUtils.getInstance().formatMatchspeed(
                    matchSpeedTimestamp);
            if (Speed > 0) {
                matchSpeedTimestamp = (long) (1 * 3600 / Speed);
                matchSpeed = DatesUtils.getInstance().formatMatchspeed(
                        matchSpeedTimestamp);
            }


            int juliSt = (int) distanceTraveled;
            // 最快配速取 每公里配速最快值
            if (juliSt - beforemaxMatchSpeedDis >= 1 && runingTimestamp > 1 &&
                    distanceTraveled >= 1 && distanceTraveled < 1000) {

                long erverymatchSpeedTimestamp = DatesUtils.getInstance()
                        .computeMatchspeed(runingTimestamp - previousLapTimestamp,
                                juliSt - beforemaxMatchSpeedDis);
                //每公里 最快配速
                if (maxMatchSpeedTimestamp == 0 || erverymatchSpeedTimestamp <= maxMatchSpeedTimestamp) {
                    maxMatchSpeedTimestamp = erverymatchSpeedTimestamp;
                    maxMatchSpeed = DatesUtils.getInstance().formatMatchspeed(
                            maxMatchSpeedTimestamp);
                }
                //每公里 最慢配速
                if (minmatchSpeedTimestamp == 0 || minmatchSpeedTimestamp <= erverymatchSpeedTimestamp) {
                    minmatchSpeedTimestamp = erverymatchSpeedTimestamp;
                    minMatchSpeed = DatesUtils.getInstance().formatMatchspeed(
                            minmatchSpeedTimestamp);
                }
                if ((minmatchSpeedTimestamp / 3600) >= 1) {
                    minmatchSpeedTimestamp = erverymatchSpeedTimestamp;
                    minMatchSpeed = DatesUtils.getInstance().formatMatchspeed(
                            minmatchSpeedTimestamp);
                }

                cb_voicestatus = welcomeSharedPreferences.getBoolean("cb_voicestatus",
                        true);
                if (cb_voicestatus && btnStartStatus && btnContinueStatus
                        && (sportindex == 0 || sportindex == 1 || sportindex == 2)) {
                    // 跑步模式语音播报每公里报一次
                    setSpeakContent();

                }
                previousLapTimestamp = runingTimestamp;
                beforemaxMatchSpeedDis = juliSt;
            }

            if (cb_voicestatus && btnStartStatus && btnContinueStatus
                    && sportindex == 3 && (runingTimestamp / 1000) % (speakTime) == 0) {
                // 开车模式，每5,10,20,40分钟报一次，就是间隔时间每次加倍
                setSpeakContent();
                speakTime = speakTime * 2;
            }
        }
        tv_distance.setText(PriceUtils.getInstance().formatFloatNumber(distanceTraveled)
                + "");
        Intent intent = new Intent(Constants.RECEIVER_ACTION_REFRESHPROPERTY);
        updateProperty(intent);
        sendBroadcast(intent);
    }

    /**
     * 更新属性页面数据
     */
    private void updateProperty(Intent intent) {
        intent.putExtra("distanceTraveled", distanceTraveled);
        intent.putExtra("duration", duration);
        intent.putExtra("freezeDuration", freezeDuration);
        Log.e("getCurrentPropertyValue", "duration=" + duration + "  ;freezeDuration==" + freezeDuration);
        intent.putExtra("averageSpeed", averageSpeed);
        intent.putExtra("nSteps", nSteps);
        intent.putExtra("currentAltitude", currentAltitude);
        intent.putExtra("averageMatchSpeed", averageMatchSpeed);
        intent.putExtra("maxMatchSpeed", maxMatchSpeed);
        intent.putExtra("minMatchSpeed", minMatchSpeed);
        intent.putExtra("topSpeed", topSpeed);
        intent.putExtra("downHillDistance", downHillDistance);
        intent.putExtra("verticalDistance", verticalDistance);
        intent.putExtra("_nMaxSlopeAngle", _nMaxSlopeAngle);
        intent.putExtra("Speed", Speed);
        intent.putExtra("lapCount", lapCount);
        intent.putExtra("sportindex", sportindex);
        intent.putExtra("btnContinueStatus", btnContinueStatus);
    }

    /**
     * start   pause   end
     */
    private void savePrefence(String stepStatus) {
        SharedPreferences.Editor medit = bestDoInfoSharedPrefs.edit();
        medit.putString("stepStatus", stepStatus);
        medit.commit();
        Log.e("stepssteps", "savePrefence-----=" + bestDoInfoSharedPrefs.getString("stepStatus", ""));
    }

    /**
     * 有缓存时保持步数
     *
     * @param step
     */
    private void haveCachingDBAndLoadstep(int step) {
        SharedPreferences.Editor medit = bestDoInfoSharedPrefs.edit();
        medit.putInt("step", step);
        medit.putBoolean("haveCachingDBAndLoad", true);
        medit.commit();
    }

    private void startservice() {
        savePrefence("start");
        service = new Intent(this, StepService.class);
        if (service != null) {
            startService(service);
        }
    }

    private void setstopService(String stepstatus) {
        savePrefence(stepstatus);
        sendBroadcast(StepUtils.getCloseBrodecastIntent());
    }

    private void setEndService() {
        savePrefence("end");
        haveCachingDBAndLoadstep(nSteps);
        sendBroadcast(StepUtils.getCloseBrodecastIntent());
    }

    /**
     * =================定位======================
     */
    private MapView mMapView;
    public static AMap mBaiduMap;
    private HashMap<String, String> mhashmap;
    protected ArrayList<VenuesUsersInfo> mMapList;
    /**
     * 隐藏的用户
     */
    private HashMap<String, String> mUserListHideMap = new HashMap<>();
    private TrackUploadFragment mTrackUploadFragment;
    private LocationManager lm;
    private BitmapDescriptor realtimeBitmap;

    /**
     * 初始化定位的SDK
     */
    private void initLbsClient() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 隐藏Fragment
        if (mTrackUploadFragment != null) {
            transaction.hide(mTrackUploadFragment);
        }
        // 清空地图覆盖物
        mBaiduMap.clear();
        if (mTrackUploadFragment == null) {
            mTrackUploadFragment = new TrackUploadFragment();
            transaction.add(R.id.fragment_content, mTrackUploadFragment);
        } else {
            transaction.show(mTrackUploadFragment);
        }
        mBaiduMap.setOnMapClickListener(null);
        /**
         * 使用的 commit方法是在Activity的onSaveInstanceState()之后调用的，这样会出错，
         * 因为onSaveInstanceState
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
        mUiSettings.setZoomControlsEnabled(false);// 隐藏的缩放按钮
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(Constants.BaiduMapTYPE_NORMAL);// 交通图
        if (null == realtimeBitmap) {
            realtimeBitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_myposition_map);
        }
    }

    /**
     * 监听状态 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种 参数2，位置信息更新周期，单位毫秒
     * 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息 参数4，监听
     * 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
     * 1秒更新一次，或最小位移变化超过1米更新一次； 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep
     * (10000);然后执行handler.sendMessage(),更新位置
     */
    private void initGps() {
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 绑定监听状态
        lm.addGpsStatusListener(listener);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1,
                gpslocationListener);
    }

    // 状态监听
    GpsStatus.Listener listener = new GpsStatus.Listener() {

        public void onGpsStatusChanged(int event) {
            switch (event) {
                // 第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    Log.i("gps", "第一次定位");
                    savaInfoToSD("第一次定位");
                    break;
                // 卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    Log.i("gps", "卫星状态改变");
                    savaInfoToSD("卫星状态改变");
                    break;
                // 定位启动
                case GpsStatus.GPS_EVENT_STARTED:
                    Log.i("gps", "定位启动");
                    savaInfoToSD("定位启动");
                    break;
                // 定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
                    Log.i("gps", "定位结束");
                    gpslocationListenerStatus = false;
                    savaInfoToSD("定位结束");
                    break;
            }
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
                    savaInfoToSD("当前GPS状态为可见状态");
                    break;
                // GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i("gps", "当前GPS状态为服务区外状态");
                    savaInfoToSD("当前GPS状态为服务区外状态");
                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i("gps", "当前GPS状态为暂停服务状态");
                    savaInfoToSD("当前GPS状态为暂停服务状态");
                    break;

            }
        }

        /**
         * GPS开启时触发
         */
        @Override
        public void onProviderEnabled(String provider) {
            Log.i("gps", "GPS开启时触发" + provider);
            savaInfoToSD("GPS开启时触发" + provider);
        }

        /**
         * GPS禁用时触发
         */
        @Override
        public void onProviderDisabled(String provider) {
            Log.i("gps", "GPS禁用时触发" + provider);
            savaInfoToSD("GPS禁用时触发" + provider);
        }

    };

    /**
     * 显示gps精度定位
     *
     * @param Accuracy
     */
    private void showGpsAccuracy(int Accuracy) {
        Speed = 0;
        mTrackUploadFragment.isInUploadFragment = false;
        gpslocationListenerStatus = false;
        if (Accuracy > 32) {
            gpslocationListenerStatus = false;
        } else if (Accuracy > 12 && Accuracy <= 32) {
            if (btnContinueStatus) {
                mTrackUploadFragment.isInUploadFragment = true;
            }
            // 信号较差数据准确度较低
            gpslocationListenerStatus = true;
        } else if (Accuracy > 5 && Accuracy <= 12) {
            if (btnContinueStatus) {
                mTrackUploadFragment.isInUploadFragment = true;
            }
            // 信号一般
            gpslocationListenerStatus = true;
        } else {
            if (btnContinueStatus) {
                mTrackUploadFragment.isInUploadFragment = true;
            }
            // 信号良好
            gpslocationListenerStatus = true;
        }
    }

    private void netLocation(int mLocType) {

        if (!gpslocationListenerStatus) {
            if (!ConfigUtils.getInstance().getNetWorkStatus(mActivity)) {
                // 无信号
                mTrackUploadFragment.isInUploadFragment = false;
                return;
            } else {
                dayin("MylocationListener==" + latitude_me + "     "
                        + currentAccuracy);
                if (mLocType != 0) {
                    // 信号较差数据准确度较低
                    mTrackUploadFragment.isInUploadFragment = false;
                } else {
                    /**
                     * **************防止gps没信号时，记录轨迹************
                     */
                    Speed = 0;
                    mTrackUploadFragment.isInUploadFragment = false;
                    currentlatLng = new LatLng(latitude_me, longitude_me);
                    setLineStatus();
                }
            }
        }

    }

    private void gpsLocation(Location location) {
        showGpsAccuracy((int) location.getAccuracy());
        Log.e("gps", "gpslocationListener==" + location.getLatitude() + "     "
                + location.getAccuracy());
        dayin("gpslocationListener==" + location.getLatitude() + "     "
                + location.getAccuracy() + "   " + location.getSpeed());
        // CommonUtils.getInstance().initToast("gps==" + location.getLatitude()
        // + " ;精度="
        // + location.getAccuracy() + " ;速度=" + location.getSpeed());
        if (gpslocationListenerStatus && location != null) {
            double[] latLng = GPSUtil.gps84_To_Gcj02(location.getLatitude(),
                    location.getLongitude());
            AMapLocation aMapLocation = new AMapLocation(location);
            aMapLocation.setLatitude(latLng[0]);
            aMapLocation.setLongitude(latLng[1]);
            address = location.getProvider();
            currentAltitude = (int) location.getAltitude(); // 获取海拔高度信息，单位米
            currentAccuracy = (int) location.getAccuracy();
            if (firstLocationstatus) {
                // 第一次不定位，防止漂浮坐标
                firstLocationstatus = false;
                initAltitude = currentAltitude;
                return;
            }
            // 第一次定位海拔变化大于ski_height 时，重新定位
            if (Math.abs(initAltitude - currentAltitude) > ski_height) {
                initAltitude = currentAltitude;
                return;
            }

            if (maxAltidueall == 0 && minAltidueall == 0) {
                maxAltidueall = minAltidueall = currentAltitude;
            }
            if (currentAltitude > 0 && currentAltitude < minAltidueall) {
                minAltidueall = currentAltitude;
            }
            if (currentAltitude > maxAltidueall) {
                maxAltidueall = currentAltitude;
            }
            //过滤非正常坐标速度
            if (btnStartStatus && btnContinueStatus && mTrackUploadFragment != null
                    && mTrackUploadFragment.isInUploadFragment) {
                Speed = location.getSpeed() * 3.6;// m/s --> Km/h
                if (Speed < 0) {
                    Speed = 0;
                }
            }
            currentlatLng = new LatLng(latLng[0], latLng[1]);
            longitude_me = currentlatLng.longitude;
            latitude_me = currentlatLng.latitude;
            setLineStatus();
        }

    }


    /**
     * 设置定位成功后设置haveGetMyLocationStauts状态，防止刚进来数据请求错乱
     */
    private void initLocSuccess() {
        Constants.getInstance().haveGetMyLocationStauts = true;
        Constants.getInstance().longitude_me = longitude_me;
        Constants.getInstance().latitude_me = latitude_me;
        Log.e("TESTLOG", "haveGetMyLocationStauts))"
                + Constants.getInstance().haveGetMyLocationStauts);
    }

    private void setLineStatus() {
        initLocSuccess();
        mHandler.sendEmptyMessage(COMEINGETLASTNOWDATAFROMDB);
        if (btnStartStatus && btnContinueStatus && mTrackUploadFragment != null
                && mTrackUploadFragment.isInUploadFragment) {
            // 如果开始按钮显示着但是btnStartStatus状态为true，则没有点击开始
            if (iv_start != null && iv_start.getVisibility() == View.VISIBLE) {
                initStartView();
            }
            mHandler.sendEmptyMessage(SETLINE);
        } else {
            showLocation();
        }
    }


    private void setline() {
        Log.e("Test", "setline***" + allpointList.size());
        if (allpointList.size() == 0 || Speed > 0) {
            String latLngDashedStatus = "latLngDashedStatus_false";
            /**
             * 加载缓存后第一次定位-要虚线链接
             */
            if (loadCancerStartSttus) {
                loadCancerStartSttus = false;
                latLngDashedStatus = "latLngDashedStatus_true";
            }

            if (beforelatLng == null
                    || beforelatLng.latitude != currentlatLng.latitude) {
                addRecordLatLngInfo(currentlatLng, latLngDashedStatus);
                addRecordListDB(record_data_id, RecordListDBOpenHelper.currentTrackComing);
                if (allpointList != null && allpointList.size() > 0) {
                    mTrackUploadFragment.sportindex = sportindex;
                    mTrackUploadFragment.showRealtimeTrack(currentlatLng,
                            latLngDashedStatus, Speed);
                    insmaxSlope();
                    inskyHillDis();
                    beforelatLng = currentlatLng;
                }
            }
            Log.e("TESTLOG", "beforelatLng==" + beforelatLng + ";;;latLng=="
                    + currentlatLng);
            getCurrentPropertyValue();
        }
    }

    /**
     * 每次定位：计算最大坡度
     */
    private void insmaxSlope() {
        if (allpointList != null && allpointList.size() > 0) {
            int tempDistance = 0;
            for (int index = allpointList.size() - 1; index > -1; index--) {
                tempDistance = (int) (allpointList.get(allpointList.size() - 1)
                        .getDistance() - allpointList.get(index).getDistance());
                if (tempDistance >= 200) {
                    MainLocationItemInfo lastPoint = allpointList.get(index);
                    MainLocationItemInfo currentPoint = allpointList
                            .get(allpointList.size() - 1);
                    if (lastPoint.getAltitude() > currentPoint.getAltitude()) {
                        // 此种为下滑
                        int tempVerticalDistance = (int) (lastPoint
                                .getAltitude() - currentPoint.getAltitude());
                        double tempAngle = Math.asin(tempVerticalDistance
                                / (tempDistance * 1.0));
                        if (((tempAngle * 180) / Math.PI) > _nMaxSlopeAngle) {
                            _nMaxSlopeAngle = (int) ((tempAngle * 180) / Math.PI);
                            if (_nMaxSlopeAngle > 45) {
                                _nMaxSlopeAngle = 45;
                            }
                        }
                    }

                    break;

                }
            }

        }
    }

    int ski_height = 30;// 起伏高度
    private int _tmpAltitude;

    /**
     * 计算趟数
     */
    private void inskyHillDis() {
        // 计算落差
        if (nskiStatus == 2 && beforeAltitude - currentAltitude > 0) {
            int tempVerticalDistance = (beforeAltitude - currentAltitude);
            verticalDistance += tempVerticalDistance;
        }
        if (maxAltitude == 0 && minAltidue == 0) {
            maxAltitude = minAltidue = _tmpAltitude = currentAltitude;
        }
        if (nskiStatus == 0) {
            if ((currentAltitude - maxAltitude) > 0) {
                maxAltitude = currentAltitude;
            }
            if ((currentAltitude - minAltidue) < 0) {
                minAltidue = currentAltitude;
            }

            if (_tmpAltitude - minAltidue > ski_height) {
                nskiStatus = 2;
                setStatusDownWithDistance();
                maxAltitude = minAltidue = currentAltitude;
            }
            if (maxAltitude - _tmpAltitude > ski_height) {
                nskiStatus = 1;
                setStatusUpWithDistance();
                maxAltitude = minAltidue = currentAltitude;
            }
        }

        if (nskiStatus == 1) {
            if ((currentAltitude - maxAltitude) > 0) {
                minAltidue = maxAltitude = currentAltitude;
            }
            if ((currentAltitude - minAltidue) < 0) {
                minAltidue = currentAltitude;
            }

            if (maxAltitude - minAltidue > ski_height) {
                nskiStatus = 2;
                setStatusDownWithDistance();
                maxAltitude = minAltidue = currentAltitude;
            }
        }
        if (nskiStatus == 2) {
            if ((currentAltitude - maxAltitude) > 0) {
                maxAltitude = currentAltitude;
            }
            if ((currentAltitude - minAltidue) < 0) {
                minAltidue = maxAltitude = currentAltitude;
            }

            if (maxAltitude - minAltidue > ski_height) {
                nskiStatus = 1;
                setStatusUpWithDistance();
                minAltidue = maxAltitude = currentAltitude;
            }
        }
        beforeAltitude = currentAltitude;
    }

    private void setStatusDownWithDistance() {
        int nHighestPointIndex = allpointList.size() - 1; // 全局轨迹点记录数组最后一个的索引值
        double nHighestAltitude = allpointList.get(nHighestPointIndex)
                .getAltitude(); // 声明最高点海拔局部变量
        for (int i = nHighestPointIndex; i >= 0; i--) {
            MainLocationItemInfo myLocation = allpointList.get(i);

            if ((myLocation.getnLapPoint() == lapCount)
                    && ((lapCount > 0 && myLocation.getnStatus() == 1) || lapCount == 0)) { // 当前点的滑行次数
                // 等于
                // 总的滑行次数
                if (myLocation.getAltitude() >= nHighestAltitude) { // 当前点海拔高于最高点海拔，更新最高点海拔值
                    nHighestAltitude = myLocation.getAltitude();
                    // 保存最高点索引
                    nHighestPointIndex = i;
                }
            } else {
                break;
            }
        }
        int skiDis = (int) (allpointList.get(allpointList.size() - 1)
                .getDistance()
                - allpointList.get(nHighestPointIndex).getDistance() + mTrackUploadFragment.temdistance);
        verticalDistance += (maxAltidueall - minAltidueall);
        dropTraveled = verticalDistance;
        downHillDistance += (skiDis + verticalDistance);

        ++lapCount;
        valueSpeakSki();
    }

    public void setStatusUpWithDistance() {
        int nLowestPointIndex = allpointList.size() - 1; // 全局轨迹点记录数组最后一个的索引值
        double nLowestAltitude = allpointList.get(nLowestPointIndex)
                .getAltitude();// 声明最低点海拔局部变量
        for (int i = nLowestPointIndex; i >= 0; i--) {
            MainLocationItemInfo myModel = allpointList.get(i);
            if ((myModel.getnLapPoint() == lapCount)
                    && ((myModel.getnLapPoint() > 0 && myModel.getnStatus() == 2) || myModel
                    .getnLapPoint() == 0)) { // 当前点的滑行次数
                // 等于
                // 总的滑行次数
                if (myModel.getAltitude() <= nLowestAltitude) { // 当前点海拔低于最高点海拔，更新最高点海拔值
                    nLowestAltitude = myModel.getAltitude();
                    // 保存最低点索引
                    nLowestPointIndex = i;
                }
            } else {
                break;
            }
        }
        // 计算上升ski_height米后的扣除ski_height米的移动距离（前提：之前有滑行趟数）
        int upDistance = (int) (allpointList.get(allpointList.size() - 1)
                .getDistance()
                - allpointList.get(nLowestPointIndex).getDistance() + mTrackUploadFragment.temdistance);
        if (downHillDistance > upDistance) {
            downHillDistance -= upDistance;
        }
        upHillDistance = verticalDistance(nLowestPointIndex, nskiStatus);
        // if (verticalDistance > upHillDistance) {
        // verticalDistance -= upHillDistance; // 总滑降 - 上升过程中的滑降 （扣除上升40米的海拔差
        // }
    }

    // 计算上升/下降垂直距离
    public int verticalDistance(int point, int status) {

        int verDistance = 0;
        int count = allpointList.size();
        for (int i = point; i < count; i++) {
            MainLocationItemInfo preMyLocation = allpointList.get(i);
            preMyLocation.setnStatus(status);

            for (int j = i; j < count; j++) {
                MainLocationItemInfo nextMyLocation = allpointList.get(j);

                if (nextMyLocation.getAltitude() < preMyLocation.getAltitude()) {
                    verDistance += preMyLocation.getAltitude()
                            - nextMyLocation.getAltitude();
                }
            }
        }
        return verDistance;
    }

    /**
     * 保存坐标明细集合,
     */
    private void addRecordLatLngInfo(LatLng latLng, String latLngDashedStatus_) {
        if ((allpointList != null && allpointList.size() == 0) || (distanceTraveled > 0 && distanceTraveled != distanceOfBeforeLat)) {
            LatLng mLatLng = latLng;// 经纬度
            double speed = Speed;// 速度
            int altitude = currentAltitude;// 海拔
            int accuracy = currentAccuracy;// 精度
            long duration = runingTimestamp / 1000;// 用时
            double distance = distanceTraveled * 1000;// 距离
            int nStatus = nskiStatus;// 运动状态
            int nLapPoint = lapCount;// 没圈线路中间点
            String nLapTime = matchSpeed;// 单圈用时
            String latLngDashedStatus = latLngDashedStatus_;
            String latitudeOffset = "";// 精度偏移
            String longitudeOffset = "";// 维度偏移
            MainLocationItemInfo mMainLocationItemInfo = new MainLocationItemInfo(
                    mLatLng.latitude, mLatLng.longitude, speed, altitude, accuracy,
                    nStatus, nLapPoint, nLapTime, latLngDashedStatus, duration, distance,
                    latitudeOffset, longitudeOffset, latLng);
            addRecordLatLngToDB(mMainLocationItemInfo);
            allpointList.add(mMainLocationItemInfo);
            mMainLocationItemInfo = null;
            distanceOfBeforeLat = distanceTraveled;
        }
    }

    /**
     * 缓存坐标点信息到数据表
     *
     * @param mMainLocationItemInfo
     */
    private void addRecordLatLngToDB(MainLocationItemInfo mMainLocationItemInfo) {
        mTrackListDBOpenHelper.addTableInfo(uid, startTrackTime, mMainLocationItemInfo);
    }

    /**
     * 显示系统logo
     * <p>
     * MyLocationData locData = new
     * MyLocationData.Builder().direction(0).latitude
     * (latitude_me).longitude(longitude_me).build();
     * <p>
     * mBaiduMap.setMyLocationData(locData);
     */
    private void showLocation() {
        try {
            if (showLatLng == null
                    || showLatLng.latitude != showLatLng.latitude) {
                // 自定义图标
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(currentlatLng);
                markerOptions.icon(realtimeBitmap);
                if (null != mTrackUploadFragment.overlay) {
                    mTrackUploadFragment.overlay.remove();
                }
                if (null != markerOptions) {
                    mTrackUploadFragment.overlay = mBaiduMap
                            .addMarker(markerOptions);
                }
                showLatLng = currentlatLng;
                moveToCenter();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 更新定位和刷新当前定位周边用户
     */
    protected void initMyLocationUsers() {

        /**
         * 每1s更新一下场地用户列表信息
         */
        mHandler.sendEmptyMessage(GETUSETLIST);

        if (firstUploadLocationstatus
                || (btnStartStatus && btnContinueStatus && runingTimestamp > 0 && (runingTimestamp / 1000) % 3 == 0)) {
            Log.e("TESTLOG", "UPDATEPOI)))" + firstUploadLocationstatus + "   "
                    + btnStartStatus + "  " + btnContinueStatus);
            mHandler.sendEmptyMessage(UPDATEPOI);
        }
        /**
         * 每1s上传一次当前坐标
         */
        if ((btnStartStatus && btnContinueStatus && runingTimestamp > 0 && (runingTimestamp / 1000) % 1 == 0)) {
            Log.e("TESTLOG", "UPDATELOCATION)))" + firstUploadLocationstatus
                    + "   " + btnStartStatus + "  " + btnContinueStatus);
            mHandler.sendEmptyMessage(UPDATELOCATION);
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
                        mUpdateTime();
                        initMyLocationUsers();
                        mHaveNetAutoUploadDBTrack();
                    }
                }
            };
            thread.start();
        }
    }

    /**
     * 点击开始后开启计时,并刷新属性数据
     */
    private void mUpdateTime() {
        // 防止加载缓存轨迹时先初始化页面导致轨迹显示落后，距离无法加载问题
        if (loadCancerStartSttus && btnStartStatus && distanceTraveled == 0) {
            mHandler.sendEmptyMessage(UPDATETIME);
        }
        if (btnStartStatus && btnContinueStatus) {
            runingTimestamp = System.currentTimeMillis() - startTimestamp
                    - pauseTimestamp;
            //防止开始时间为0时sleepDuration就累加
            if (Speed <= 0 && runingTimestamp / 1000 > 0) {
                sleepDuration += 1;
            }
            mHandler.sendEmptyMessage(UPDATETIME);// 通知主线程更新UIs
        }
        if (btnStartStatus) {
            initSpeed();
        }
    }

    /**
     * 不再运动的时间，大于10秒 速度归0
     */
    double stillTime;
    double stilldistanceTraveled;

    /**
     * 不再运动的时间，持续大于10秒 速度归0,防止无GPS信号时速度无法获取
     */
    private void initSpeed() {
        if (stillTime == 0) {
            stilldistanceTraveled = distanceTraveled;
        }
        if (distanceTraveled == stilldistanceTraveled) {
            stillTime++;
        } else {
            stillTime = 0;
        }
        if (stillTime > 10) {
            Speed = 0;
            gpslocationListenerStatus = false;
            stillTime = 11;
        }
        Log.e("initSpeed", stillTime + "  " + Speed + "  " + gpslocationListenerStatus + " " + distanceTraveled + " " + stilldistanceTraveled);
//        CommonUtils.getInstance().initToast(stillTime+"  "+Speed+"  "+gpslocationListenerStatus+" "+distanceTraveled+" "+stilldistanceTraveled);
    }


    /**
     * 是否正在更新位置
     */
    boolean UPDATELOCATIONSTAUS = false;
    /**
     * 是否手动移动当前位置
     */
    boolean moveMyLocationStaus = true;
    private final int UPDATEPOI = 0;
    private final int UPDATELOCATION = 1;
    private final int UPDATETIME = 2;
    private final int SETLINE = 3;
    private final int NETLOCATION = 4;
    private final int GPSLOCATION = 5;
    private final int UPDATETRACKHISTORYDATA = 6;
    /**
     * 更新 暂停 结束  继续 状态
     */
    private final int UPDATEBTNSTATUS = 7;
    /**
     * 初始化时获取上次未完成的轨迹，提示是否继续
     */
    private final int COMEINGETLASTNOWDATAFROMDB = 9;
    private final int HUIJI = 10;
    /**
     * 加载用户头像地图
     */
    private final int GETUSERTHUMB = 11;
    /**
     * 红包头像
     */
    private final int GETTREATURETHUMB = 12;
    private final int showSettingsApp = 13;
    private final int loadRecordDates = 14;
    private final int UPDATELOCATIONCLAER = 15;
    private final int GETUSETLIST = 16;//获取用户列表
    private final int MARKERCLCIK = 17;//点击用户头像
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case showSettingsApp:
                    showSettingsApp();
                    break;
                case UPDATELOCATION:
                    boolean cb_mylocationstatus = welcomeSharedPreferences
                            .getBoolean("cb_mylocationstatus", true);
                    if (!UPDATELOCATIONSTAUS && cb_mylocationstatus
                            && Constants.getInstance().haveGetMyLocationStauts) {
                        UPDATELOCATIONSTAUS = true;
                        double longitude_me = currentlatLng.longitude;
                        double latitude_me = currentlatLng.latitude;
                        updateLocation(longitude_me, latitude_me, false);
                        receiveTreasure();
                        Log.e("TESTLOG", pos_name + " pos_name   posid    " + posid);
                    }
                    break;
                case UPDATELOCATIONCLAER:
                    updateLocation(longitude_me, latitude_me, true);
                    break;
                case UPDATEPOI:
                    if (Constants.getInstance().haveGetMyLocationStauts) {
                        firstUploadLocationstatus = false;
                        Log.e("TESTLOG", "**UPDATEPOI***");
                        if (distanceTraveled > 0.6) {
                            getPosid();
                        }
                    }
                    break;
                case GETUSETLIST:
                    if (Constants.getInstance().haveGetMyLocationStauts) {
                        Log.e("getVenuesUsers", "**getVenuesUsers***" + backgroundStus);
                        getAroundTreasure();
                        if (!backgroundStus) {
                            getVenuesUsers();
                        }
                    }
                    break;
                case UPDATETIME:
                    determineSportType(false);
                    getCurrentPropertyValue();
                    break;
                case SETLINE:
                    if (currentlatLng != null)
                        setline();
                    break;
                case NETLOCATION:
                    int mLocType = msg.arg1;
                    netLocation(mLocType);
                    break;
                case GPSLOCATION:
                    Location location = (Location) msg.obj;
                    gpsLocation(location);
                    break;
                case UPDATETRACKHISTORYDATA:
                    updateTrackHistoryData();
                    break;
                case UPDATEBTNSTATUS:
                    String type = (String) msg.obj;
                    if (type.equals("stop")) {
                        setClickStop();
                    } else if (type.equals("pause")) {
                        setClickpause();
                    } else if (type.equals("continue")) {
                        setClickContiue();
                    }
                    break;
                case COMEINGETLASTNOWDATAFROMDB:
                    if (FirstComeIn) {
                        FirstComeIn = false;
                        getLastNowDataFromDB();
                    }
                    break;
                case HUIJI:
                    String latLngDashedStatus;
                    if (msg.arg1 == 1) {
                        latLngDashedStatus = "latLngDashedStatus_true";
                    } else {
                        latLngDashedStatus = "latLngDashedStatus_false";
                    }
                    LatLng mLatLng = (LatLng) msg.obj;
                    double speed = (double) msg.arg2;
                    mTrackUploadFragment.isInUploadFragment = true;
                    mTrackUploadFragment.showRealtimeTrack(mLatLng,
                            latLngDashedStatus, speed);
                    break;

                case GETUSERTHUMB:
                    if (!backgroundStus && mBaiduMap != null) {
                        if (muserThumbShoaUtils == null) {
                            muserThumbShoaUtils = new userThumbShoaUtils(mActivity,
                                    mBaiduMap, mHandler, MARKERCLCIK);
                        }
                        muserThumbShoaUtils.setmUserListHideMap(mUserListHideMap);
                        muserThumbShoaUtils.initMyOverlay(mMapList);
                    }
                    break;
                case GETTREATURETHUMB:
                    // 后台运行时不再更新mapview
                    if (!backgroundStus && mBaiduMap != null) {
                        if (muserTreasureShoaUtils == null) {
                            muserTreasureShoaUtils = new userTreasureShoaUtils(
                                    mActivity, mBaiduMap);
                        }
                        muserTreasureShoaUtils
                                .initMyOverlayTreasure(mMapTreasureList);
                    }
                    break;
                case loadRecordDates:
                    loadRecordDates();
                    break;
                case MARKERCLCIK:
                    String uid = msg.getData().getString("uid");
                    String album_url = (String) msg.obj;
                    if (TextUtils.isEmpty(uid)) {
                        layout_chat.setVisibility(View.GONE);
                    } else {
                        if (asyncImageLoader == null) {
                            asyncImageLoader = new ImageLoader(mActivity, "headImg");
                        }
                        asyncImageLoader.DisplayImage(album_url, map_iv_selectablum);
                        layout_chat.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };
    ImageLoader asyncImageLoader;

    /**
     * ******获取未完成的轨迹,把之前”自动保存“的数据恢复到首页，并显示”继续“按钮************
     */
    private void getLastNowDataFromDB() {
        if (mRecordListDB != null) {
            ArrayList<RecordInfo> mNowTrackList = mRecordListDB
                    .getTrackDBList(uid, mRecordListDB.currentTrackComing);
            if (mNowTrackList != null && mNowTrackList.size() > 0) {
                firstUploadLocationstatus = false;// 防止注销后重新登录加载场地用户接口报401问题
                Log.e("TESTLOG", "**getLastNowDataFromDB***");
                if (mNowTrackList.size() > 1) {
                    for (int i = 1; i < mNowTrackList.size(); i++) {
                        // 当前用户的缓存才上传
                        String hisUid = mNowTrackList.get(i).getUid();
                        if (hisUid.equals(uid)) {
                            mRecordListUtils.delDB(mNowTrackList.get(i));
                        }
                    }
                }
                Log.e("track", "getLastNowDataFromDB=" + mNowTrackList.size());
                RecordInfo mTrackSaveInfo = mNowTrackList.get(0);
                if (mTrackSaveInfo.getDistanceTraveled() < 0.05) {
                    mRecordListUtils.delDB(mTrackSaveInfo);
                } else {
                    String hisUid = mTrackSaveInfo.getUid();
                    if (hisUid.equals(uid)) {
                        String startTrackTime_ = mTrackSaveInfo.getRunStartTime();
                        String nottime = DatesUtils.getInstance().getNowTime(
                                "yyyy-MM-dd");
                        String dday = DatesUtils.getInstance().getDateGeShi(
                                startTrackTime_, "yyyy-MM-dd HH:mm:ss",
                                "yyyy-MM-dd");
                        if (DatesUtils.getInstance().doDateEqual(nottime, dday,
                                "yyyy-MM-dd")) {
                            loadCancerStartSttus = true;
                            startTrackTime = startTrackTime_;
                            record_data_id = mTrackSaveInfo.getRecord_id();
                            recordDatas = setRecordInfoArrayToJson(mTrackSaveInfo);
                            analyzeNowData(recordDatas);
                        } else {
                            updateNowDataForOver(mTrackSaveInfo);
                        }
                    }
                }
            }
        }
    }

    /**
     * 不是同一天的状态设置为完成
     */
    private void updateNowDataForOver(RecordInfo mTrackSaveInfo) {
        mTrackSaveInfo.setCurrentTrackStatus(mRecordListDB.currentTrackNotUpLoad);
        mRecordListDB.updateTableInfo(mTrackSaveInfo);
    }

    private void analyzeNowData(String recordDates) {
        Log.e("track", "analyzeNowData=" + recordDates);
        if (!TextUtils.isEmpty(recordDates)) {
            showDilag();
            JSONObject jsonObject = RequestUtils.String2JSON(recordDates);
            JSONArray infoArray = jsonObject.optJSONArray("recordInfo");
            for (int i = 0; i < infoArray.length(); i++) {
                JSONObject infoOb = infoArray.optJSONObject(i);
                double latitude = infoOb.optDouble("latitude", 0);
                double longitude = infoOb.optDouble("longitude", 0);
                double speed = infoOb.optDouble("speed", 0);
                int altitude = infoOb.optInt("altitude", 0);
                int accuracy = infoOb.optInt("accuracy", 0);
                int nStatus = infoOb.optInt("nStatus", 0);
                int nLapPoint = infoOb.optInt("nLapPoint", 1);
                String nLapTime = infoOb.optString("nLapTime", "");
                String latLngDashedStatus = infoOb.optString("latLngDashedStatus", "false");
                long durations = infoOb.optLong("duration", 0);
                double distance = infoOb.optDouble("distance", 0);
                String latitudeOffsets = infoOb.optString("latitudeOffset", "");
                String longitudeOffsets = infoOb.optString("longitudeOffset",
                        "");
                LatLng mLatLng = new LatLng(latitude, longitude);
                MainLocationItemInfo mMainLocationItemInfo = new MainLocationItemInfo(
                        latitude, longitude, speed, altitude, accuracy,
                        nStatus, nLapPoint, nLapTime, latLngDashedStatus, durations, distance,
                        latitudeOffsets, longitudeOffsets, mLatLng);
                allpointList.add(mMainLocationItemInfo);
                mMainLocationItemInfo = null;
            }
            if (allpointList != null && allpointList.size() > 0) {
                mTrackUploadFragment.settoFollowStatus(false);
                for (int j = 0; j < allpointList.size(); j++) {
                    double latitude = allpointList.get(j).getLatitude();
                    double longitude = allpointList.get(j).getLongitude();
                    double speed = allpointList.get(j).getSpeed();
                    String latLngDashedStatus = allpointList.get(j)
                            .getLatLngDashedStatus();
                    Message msg = new Message();
                    if (latLngDashedStatus.equals("latLngDashedStatus_true")) {
                        msg.arg1 = 1;
                    } else {
                        msg.arg1 = 0;
                    }
                    msg.arg2 = (int) Math.ceil(speed);
                    msg.obj = new LatLng(latitude, longitude);
                    msg.what = HUIJI;
                    mHandler.sendMessage(msg);
                    msg = null;
                }
                initCancDAT(jsonObject);
            }
            CommonUtils.getInstance().setOnDismissDialog(mDialog);
        }
    }

    private void initCancDAT(JSONObject jsonObject) {

        Log.e("track", "allpointLngList***" + allpointList.size());
        btnStartStatus = true;
        btnContinueStatus = true;
        mUserListHideMap.clear();
        iv_start.setVisibility(View.GONE);
        tv_distancetitle.setVisibility(View.VISIBLE);
        tv_distance.setVisibility(View.VISIBLE);

        posid = jsonObject.optString("posid", "");
        if (!TextUtils.isEmpty(posid) && posid.equals("null")) {
            posid = "";
        }
        pos_name = jsonObject.optString("pos_name", "");
        runingTimestamp = jsonObject.optLong("duration", 0);
        runingTimestamp = runingTimestamp * 1000;
        verticalDistance = jsonObject.optInt("verticalDistance", 0);
        topSpeed = jsonObject.optDouble("topSpeed", 0.00);
        dropTraveled = jsonObject.optInt("dropTraveled", 0);
        nSteps = jsonObject.optInt("nSteps", 0);
        matchSpeed = jsonObject.optString("matchSpeed", "0");
        maxMatchSpeed = jsonObject.optString("maxMatchSpeed", "0");
        minMatchSpeed = jsonObject.optString("minMatchSpeed", "0");
        _nMaxSlopeAngle = jsonObject.optInt("maxSlope", 0);
        maxAltidueall = jsonObject.optInt("maxAltitude", 0);
        currentAltitude = jsonObject.optInt("currentAltitude", 0);
        averageMatchSpeed = jsonObject.optString("averageMatchSpeed", "0");
        averageSpeed = jsonObject.optDouble("averageSpeed", 0.00);
        freezeDuration = jsonObject.optLong("freezeDuration", 0);
        pauseTimestamp = jsonObject.optLong("pauseTimestamp", 0);
        pauseTimestamp = pauseTimestamp * 1000;
        if (pauseTimestamp < 0) {
            pauseTimestamp = 0;
        }
        sleepDuration = freezeDuration - pauseTimestamp;
        if (sleepDuration < 0) {
            sleepDuration = 0;
        }
        maxHoverDuration = jsonObject.optString("maxHoverDuration", "");
        totalHoverDuration = jsonObject.optString("totalHoverDuration", "");
        hopCount = jsonObject.optInt("hopCount", 0);
        lapCount = jsonObject.optInt("lapCount", 0);
        wrestlingCount = jsonObject.optString("wrestlingCount", "");
        cableCarQueuingDuration = jsonObject.optString(
                "cableCarQueuingDuration", "");
        minAltidueall = jsonObject.optInt("minAltidue", 0);
        calorie = jsonObject.optDouble("calorie", 0.00);
        sportindex = jsonObject.optInt("sportsType", 0);
        upHillDistance = jsonObject.optInt("upHillDistance", 0);
        downHillDistance = jsonObject.optInt("downHillDistance", 0);

        startTimestamp = System.currentTimeMillis() - runingTimestamp
                - pauseTimestamp;
        if (startTimestamp < 0) {
            startTimestamp = 0;
        }
        haveCachingDBAndLoadstep(nSteps);
        initGps();
        startLocationServices();
        setToFollowStatus();
        startSpeak();
        startservice();
        setSportPropertyList();
    }

    // ******************************************************************

    /**
     * 有网时自动上传数据库中未上传的轨迹记录，30s检查一次
     */
    private void mHaveNetAutoUploadDBTrack() {
        long useTimestamp = System.currentTimeMillis() - initTimestamp;
        if ((useTimestamp / 1000) % 30 == 0)
            mHandler.sendEmptyMessage(UPDATETRACKHISTORYDATA);
    }

    /**
     * 有网时，自动从数据库查询缓存的轨迹数据
     */
    private void updateTrackHistoryData() {
        if (ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
            if (mRecordListDB != null) {
                ArrayList<RecordInfo> mTrackList = mRecordListDB
                        .getTrackDBList(uid, mRecordListDB.currentTrackNotUpLoad);
                if (mTrackList != null && mTrackList.size() > 0) {
                    for (int i = 0; i < mTrackList.size(); i++) {
                        // 当前用户的缓存才上传
                        String hisUid = mTrackList.get(i).getUid();
                        if (hisUid.equals(uid) && !updateTrackHistoryStatus) {
                            updateTrackHistoryStatus = true;
                            loadTrackHistoryDates(mTrackList.get(i));
                        }
                    }
                }
            }

        }
    }

    /**
     * 有网时，上传当前用户的所有缓存的轨迹数据，成功并清除数据表数据
     *
     * @param mRecordInfo
     */
    private void loadTrackHistoryDates(final RecordInfo mRecordInfo) {

        recordDatas = setRecordInfoArrayToJson(mRecordInfo);
        mhashmap = new HashMap<String, String>();
        // 上传记录时传递最新access_token，防止报401
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        mhashmap.put("record_data_id", mRecordInfo.getRecord_id());
        mhashmap.put("recordDatas", "" + recordDatas);
        Log.e("track", "------------loadTrackHistoryDates------------"
                + mhashmap);
        new RecordDatesloadBusiness(this, mhashmap,
                new GetRecordDatesloadCallback() {
                    @Override
                    public void afterDataGet(HashMap<String, Object> dataMap) {
                        if (dataMap != null) {
                            String status = (String) dataMap.get("status");
                            if (status.equals("200")) {
                                Constants.getInstance().addTrackStatus = true;
                                mRecordInfo.setCurrentTrackStatus(RecordListDBOpenHelper.currentTrackBOVER);
                                mRecordListUtils.addDB(mRecordInfo);
                            }
                        }
                        updateTrackHistoryStatus = false;
                        CommonUtils.getInstance().setClearCacheBackDate(
                                mhashmap, dataMap);
                    }
                });

    }

    /**
     * 添加运动记录到数据库
     */
    private void addRecordListDB(String record_id, String currentTrackStatus) {
        String createtime = DatesUtils.getInstance().getNowTime(
                "yyyy-MM-dd HH:mm:ss");
        long startTrackTimeTamp = DatesUtils.getInstance().getDateToTimeStamp(startTrackTime, "yyyy-MM-dd HH:mm:ss");
        String date_time = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
        RecordInfo mRecordInfo = new RecordInfo(record_id, uid,
                posid, date_time, distanceTraveled * 1000, runingTimestamp / 1000,
                String.valueOf(verticalDistance), String.valueOf(topSpeed), String.valueOf(dropTraveled), String.valueOf(nSteps),
                matchSpeed, maxMatchSpeed, String.valueOf(_nMaxSlopeAngle), String.valueOf(maxAltitude),
                String.valueOf(currentAltitude), averageMatchSpeed, String.valueOf(averageSpeed),
                pauseTimestamp / 1000 + sleepDuration, pauseTimestamp / 1000, maxHoverDuration,
                totalHoverDuration, String.valueOf(lapCount), wrestlingCount,
                cableCarQueuingDuration, address, String.valueOf(minAltidue),
                String.valueOf(calorie), String.valueOf(sportindex), latitudeOffset,
                longitudeOffset, String.valueOf(upHillDistance), String.valueOf(downHillDistance),
                "", "", "",
                createtime, "1", pos_name, startTrackTime, String.valueOf(startTrackTimeTamp), minMatchSpeed, currentTrackStatus);
        mRecordListUtils.addDB(mRecordInfo);
    }

    /**
     * 转换轨迹集合数据为json字符串
     *
     * @param uploadStatus 是否是点击上传
     * @return
     */
    private String setRecordInfoArrayToJson(boolean uploadStatus) {
        JSONObject recordDatas = new JSONObject();
        JSONArray recordInfoArray = new JSONArray();
        try {
            try {
                if (allpointList != null && allpointList.size() > 0) {
                    int length = allpointList.size();
                    double lat;
                    double lon;
                    topSpeed = 0.0;
                    for (int i = 0; i < length; i++) {
                        lat = allpointList.get(i).getLatitude();
                        lon = allpointList.get(i).getLongitude();
                        JSONObject latObject = new JSONObject();

                        latObject.put("longitude", lon + "");
                        latObject.put("latitude", lat + "");

                        double speed_ = allpointList.get(i).getSpeed();
                        if (speed_ > topSpeed) {
                            topSpeed = speed_;
                        }
                        latObject.put("speed", "" + speed_);
                        latObject.put("altitude", ""
                                + allpointList.get(i).getAltitude());
                        latObject.put("accuracy", ""
                                + allpointList.get(i).getAccuracy());
                        latObject.put("nStatus", ""
                                + allpointList.get(i).getnStatus());
                        latObject.put("nLapPoint", ""
                                + allpointList.get(i).getnLapPoint());
                        latObject.put("nLapTime", ""
                                + allpointList.get(i).getnLapTime());
                        if (!uploadStatus) {
                            latObject.put("latLngDashedStatus", ""
                                    + allpointList.get(i).getLatLngDashedStatus());
                        }
                        latObject.put("duration", ""
                                + allpointList.get(i).getDuration());
                        latObject.put("distance", ""
                                + allpointList.get(i).getDistance());
                        latObject.put("latitudeOffset", ""
                                + allpointList.get(i).getLatitudeOffset());
                        latObject.put("longitudeOffset",
                                "" + allpointList.get(i).getLongitudeOffset());
                        recordInfoArray.put(latObject);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            recordDatas.put("recordInfo", recordInfoArray);

            recordDatas.put("user_id", uid);
            recordDatas.put("posid", posid + "");
            recordDatas.put("pos_name", pos_name + "");
            recordDatas.put("distanceTraveled", "" + distanceTraveled * 1000);
            recordDatas.put("duration", "" + (runingTimestamp / 1000));
            recordDatas.put("verticalDistance", "" + verticalDistance);
            recordDatas.put("topSpeed", "" + topSpeed);

            recordDatas.put("dropTraveled", "" + dropTraveled);
            recordDatas.put("nSteps", "" + nSteps);
            recordDatas.put("matchSpeed", "" + matchSpeed);
            recordDatas.put("maxMatchSpeed", "" + maxMatchSpeed);
            recordDatas.put("maxSlope", "" + _nMaxSlopeAngle);
            recordDatas.put("maxAltitude", "" + maxAltidueall);
            recordDatas.put("currentAltitude", "" + currentAltitude);
            recordDatas.put("minMatchSpeed", "" + minMatchSpeed);
            recordDatas.put("runStartTime", "" + startTrackTime);
            recordDatas.put("averageMatchSpeed", "" + averageMatchSpeed);
            recordDatas.put("averageSpeed", "" + averageSpeed);
            recordDatas.put("freezeDuration", pauseTimestamp / 1000 + sleepDuration);
            recordDatas.put("pauseTimestamp", pauseTimestamp / 1000);
            recordDatas.put("maxHoverDuration", "" + maxHoverDuration);
            recordDatas.put("totalHoverDuration", "" + totalHoverDuration);
            recordDatas.put("hopCount", "" + hopCount);
            recordDatas.put("lapCount", "" + lapCount);
            recordDatas.put("wrestlingCount", "" + wrestlingCount);
            recordDatas.put("cableCarQueuingDuration", ""
                    + cableCarQueuingDuration);
            recordDatas.put("address", "" + address);
            recordDatas.put("minAltidue", "" + minAltidueall);
            recordDatas.put("calorie", "" + calorie);
            recordDatas.put("sportsType", "" + sportindex);
            recordDatas.put("latitudeOffset", "" + latitudeOffset);
            recordDatas.put("longitudeOffset", "" + longitudeOffset);
            recordDatas.put("upHillDistance", "" + upHillDistance);
            recordDatas.put("downHillDistance", "" + downHillDistance);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recordDatas.toString();

    }

    /**
     * db数据转换json
     *
     * @param mRecordInfo
     * @return
     */
    private String setRecordInfoArrayToJson(RecordInfo mRecordInfo) {
        JSONObject recordDatas = new JSONObject();
        JSONArray recordInfoArray = new JSONArray();
        try {
            recordDatas.put("user_id", mRecordInfo.getUid());
            recordDatas.put("posid", mRecordInfo.getPosid() + "");
            recordDatas.put("pos_name", mRecordInfo.getPos_name() + "");
            recordDatas.put("distanceTraveled", "" + mRecordInfo.getDistanceTraveled());
            recordDatas.put("duration", "" + mRecordInfo.getDuration());
            recordDatas.put("verticalDistance", "" + mRecordInfo.getVerticalDistance());
            recordDatas.put("topSpeed", "" + mRecordInfo.getTopSpeed());

            recordDatas.put("dropTraveled", "" + mRecordInfo.getDropTraveled());
            recordDatas.put("nSteps", "" + mRecordInfo.getnSteps());
            recordDatas.put("matchSpeed", "" + mRecordInfo.getMatchSpeed());
            recordDatas.put("maxMatchSpeed", "" + mRecordInfo.getMinMatchSpeed());
            recordDatas.put("maxSlope", "" + mRecordInfo.getMaxSlope());
            recordDatas.put("maxAltitude", "" + mRecordInfo.getMaxAltitude());
            recordDatas.put("currentAltitude", "" + mRecordInfo.getCurrentAltitude());
            recordDatas.put("minMatchSpeed", "" + mRecordInfo.getMinMatchSpeed());
            recordDatas.put("runStartTime", "" + mRecordInfo.getRunStartTime());
            recordDatas.put("averageMatchSpeed", "" + mRecordInfo.getAverageMatchSpeed());
            recordDatas.put("averageSpeed", "" + mRecordInfo.getAverageSpeed());
            recordDatas.put("freezeDuration", "" + mRecordInfo.getFreezeDuration());
            recordDatas.put("pauseTimestamp", "" + mRecordInfo.getPauseTimestamp());
            recordDatas.put("maxHoverDuration", "" + mRecordInfo.getMaxHoverDuration());
            recordDatas.put("totalHoverDuration", "" + mRecordInfo.getTotalHoverDuration());
            recordDatas.put("hopCount", "" + hopCount);
            recordDatas.put("lapCount", "" + mRecordInfo.getLapCount());
            recordDatas.put("wrestlingCount", "" + mRecordInfo.getWrestlingCount());
            recordDatas.put("cableCarQueuingDuration", ""
                    + mRecordInfo.getCableCarQueuingDuration());
            recordDatas.put("address", "" + mRecordInfo.getAddress());
            recordDatas.put("minAltidue", "" + mRecordInfo.getMinAltidue());
            recordDatas.put("calorie", "" + mRecordInfo.getCalorie());
            recordDatas.put("sportsType", "" + mRecordInfo.getSportsType());
            recordDatas.put("latitudeOffset", "" + mRecordInfo.getLatitudeOffset());
            recordDatas.put("longitudeOffset", "" + mRecordInfo.getLongitudeOffset());
            recordDatas.put("upHillDistance", "" + mRecordInfo.getUpHillDistance());
            recordDatas.put("downHillDistance", "" + mRecordInfo.getDownHillDistance());
            try {
                HashMap<String, Object> mHashMap = mTrackListDBOpenHelper.getHistoryDBList(mRecordInfo.getUid(), mRecordInfo.getRunStartTime());
                List<MainLocationItemInfo> allpointList = (List<MainLocationItemInfo>) mHashMap.get("allpointList");
                if (allpointList != null && allpointList.size() > 0) {
                    int length = allpointList.size();
                    double lat;
                    double lon;
                    for (int i = 0; i < length; i++) {
                        lat = allpointList.get(i).getLatitude();
                        lon = allpointList.get(i).getLongitude();
                        JSONObject latObject = new JSONObject();

                        latObject.put("longitude", lon + "");
                        latObject.put("latitude", lat + "");

                        latObject.put("speed", ""
                                + allpointList.get(i).getSpeed());
                        latObject.put("altitude", ""
                                + allpointList.get(i).getAltitude());
                        latObject.put("accuracy", ""
                                + allpointList.get(i).getAccuracy());
                        latObject.put("nStatus", ""
                                + allpointList.get(i).getnStatus());
                        latObject.put("nLapPoint", ""
                                + allpointList.get(i).getnLapPoint());
                        latObject.put("nLapTime", ""
                                + allpointList.get(i).getnLapTime());
                        latObject.put("latLngDashedStatus", ""
                                + allpointList.get(i).getLatLngDashedStatus());
                        latObject.put("duration", ""
                                + allpointList.get(i).getDuration());
                        latObject.put("distance", ""
                                + allpointList.get(i).getDistance());
                        latObject.put("latitudeOffset", ""
                                + allpointList.get(i).getLatitudeOffset());
                        latObject.put("longitudeOffset",
                                "" + allpointList.get(i).getLongitudeOffset());
                        recordInfoArray.put(latObject);
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
     * 当没有场地ID并且没有手动切换运动类型时--- 获取场地id
     */
    private void getPosid() {
        if (btnStartStatus && !selectSportTypeByManuallyStatus
                && !autoChangeSportStus && (TextUtils.isEmpty(posid) || TextUtils.isEmpty(pos_name))) {
            mhashmap = new HashMap<String, String>();
            mhashmap.put("uid", uid);
            mhashmap.put("token", token);
            mhashmap.put("access_token", access_token);

            mhashmap.put("longitude", longitude_me + "");
            mhashmap.put("latitude", latitude_me + "");
            Log.e("TESTLOG", "------------getPosid------------" + mhashmap);
            new VenuesInfoBylicationBusiness(this, mhashmap,
                    new GetInfoBylicationCallback() {
                        @Override
                        public void afterDataGet(HashMap<String, Object> dataMap) {

                            if (dataMap != null) {
                                posid = (String) dataMap.get("posid");
                                pos_name = (String) dataMap.get("field_name");
                                String sportsType_ = (String) dataMap
                                        .get("sportsType");
                                if (!TextUtils.isEmpty(sportsType_)) {
                                    sportFieldIndex = getsportIndexByType(sportsType_);
                                }
                            }
                            CommonUtils.getInstance().setClearCacheBackDate(
                                    mhashmap, dataMap);

                        }
                    });
        }

    }

    private int getsportIndexByType(String sportsType_) {
        int matchNum = 0;
        int l_sportIndex = -1;
        mMainsportParser = new MainsportParser();
        mallsportList = mMainsportParser.parseJSON(this);
        for (int i = 0; i < mallsportList.size(); i++) {
            MainSportInfo mainSportInfo = mallsportList.get(i);
            if (sportsType_.equals(mainSportInfo.getEname())) {
                l_sportIndex = mainSportInfo.getIndex();
                matchNum++;
                break;
            }
        }
        if (matchNum == 0) {
            l_sportIndex = mallsportList.size() - 1;
        }
        return l_sportIndex;
    }

    private ProgressDialog mDialog;
    protected ArrayList<VenuesTreasInfo> mMapTreasureList;
    protected userTreasureShoaUtils muserTreasureShoaUtils;

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
     * 用户本地开始记录轨迹时,必须向服务器申请record_data_id,
     * 后期在上传轨迹数据时需要上传record_data_id
     */
    private void initRecord() {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        new InitRecordBusiness(this, mhashmap, new InitRecordBusiness.GetInitRecordCallback() {
            @Override
            public void afterDataGet(HashMap<String, Object> dataMap) {
                if (dataMap != null) {
                    String status = (String) dataMap.get("status");
                    if (status.equals("200")) {
                        record_data_id = (String) dataMap.get("record_data_id");
                    }
                }
                CommonUtils.getInstance().setClearCacheBackDate(
                        mhashmap, dataMap);
            }
        });
    }


    /**
     * 点击结束
     *
     * @param dialogType
     */
    public void endDialog(final String dialogType) {
        final MyDialog selectDialog = new MyDialog(this, R.style.dialog,
                R.layout.dialog_myexit);// 创建Dialog并设置样式主题
        selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        selectDialog.show();
        TextView text_off = (TextView) selectDialog
                .findViewById(R.id.myexit_text_off);// 取消
        final TextView text_sure = (TextView) selectDialog
                .findViewById(R.id.myexit_text_sure);// 确定
        TextView myexit_text_title = (TextView) selectDialog
                .findViewById(R.id.myexit_text_title);
        if (dialogType.equals("shortDistance")) {
            myexit_text_title.setText(getString(R.string.endlocationcancel));
        } else if (dialogType.equals("AvoidCheating")) {
            text_off.setVisibility(View.GONE);
            myexit_text_title.setText("系统自动识别您的运动模式为开车");
        } else if (dialogType.equals("GPSNOTSTART")) {
            myexit_text_title.setText(getString(R.string.endlocationgpsstart));
        } else if (dialogType.equals("showSettingsApp")) {
            text_off.setVisibility(View.GONE);
            myexit_text_title.setTextSize(15);
            myexit_text_title.setText("KIWI运动需要手动设置\"允许后台活动\"和\"锁屏后不清理\"，即将跳转至设置页面");
        } else {
            if (ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
                myexit_text_title
                        .setText(getString(R.string.endlocationcommit));
            } else {
                myexit_text_title
                        .setText(getString(R.string.endlocationcommitNotnet));
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
                    RecordInfo mTrackSaveInfo = new RecordInfo();
                    long startTrackTimeTamp = DatesUtils.getInstance().getDateToTimeStamp(startTrackTime, "yyyy-MM-dd HH:mm:ss");
                    mTrackSaveInfo.setUid(uid);
                    mTrackSaveInfo.setRunStartTime(startTrackTime);
                    mTrackSaveInfo.setRunStartTimeTamp(String.valueOf(startTrackTimeTamp));
                    mRecordListUtils.delDB(mTrackSaveInfo);
                    initStartView();
                    mHandler.sendEmptyMessage(UPDATELOCATIONCLAER);
                } else if (dialogType.equals("AvoidCheating")) {
                    selectDialog.dismiss();

                } else if (dialogType.equals("GPSNOTSTART")) {
                    // 返回开启GPS导航设置界面
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 0);
                } else if (dialogType.equals("showSettingsApp")) {
                    SharedPreferences.Editor edit = mset.edit();
                    edit.putString("showSettingsApp", "showSettingsApp");
                    edit.commit();
                    MobileInfoUtils.getInstance().jumpStartInterface(mActivity);
                }

            }
        });
    }

    /**
     * 点击结束，上传轨迹
     */
    private void loadRecordDates() {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("record_data_id", record_data_id);
        mhashmap.put("access_token", access_token);
        mhashmap.put("recordDatas", "" + recordDatas);
        Log.e("TESTLOG", "------------loadRecordDates------------" + mhashmap);
        new RecordDatesloadBusiness(this, mhashmap,
                new GetRecordDatesloadCallback() {
                    @Override
                    public void afterDataGet(HashMap<String, Object> dataMap) {

                        if (dataMap != null) {
                            String status = (String) dataMap.get("status");
                            if (status.equals("200")) {
                                record_data_id = (String) dataMap.get("msg");
                            }
                            addRecordListDB(record_data_id, RecordListDBOpenHelper.currentTrackBOVER);
                        }
                        initStartView();
                        CommonUtils.getInstance().setClearCacheBackDate(
                                mhashmap, dataMap);

                    }
                });

    }

    /**
     * 上传坐标
     *
     * @param longitude_me
     * @param latitude_me
     * @param clearLoc     是否清除坐标
     */
    protected void updateLocation(double longitude_me, double latitude_me,
                                  boolean clearLoc) {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        mhashmap.put("current_sports_type", sportindex + "");
        mhashmap.put("speed", Speed + "");
        mhashmap.put("accuracy", currentAccuracy + "");
        mhashmap.put("altitude", currentAltitude + "");
        if (clearLoc) {
            mhashmap.put("longitude", "");
            mhashmap.put("latitude", "");
        } else {
            mhashmap.put("longitude", longitude_me + "");
            mhashmap.put("latitude", latitude_me + "");
        }
        savaInfoToSD("updateLocation");
        Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
        new UpdateLocationBusiness(this, mhashmap,
                new GetUpdateLocationCallback() {
                    @Override
                    public void afterDataGet(HashMap<String, Object> dataMap) {
                        UPDATELOCATIONSTAUS = false;
                        if (dataMap != null) {
                            String status = (String) dataMap.get("status");
                            if (status.equals("401") || status.equals("402")) {
                                initStartView();
                                firstUploadLocationstatus = false;
                                UserLoginBack403Utils
                                        .getInstance()
                                        .sendBroadcastLoginBack403(
                                                CommonUtils.getInstance().mHomeActivity);
                            }
                        }
                        CommonUtils.getInstance().setClearCacheBackDate(
                                mhashmap, dataMap);
                    }
                });

    }

    /**
     * 每次定位更新，点击开始并且 开启共享位置---抢红包
     */
    private void receiveTreasure() {
        if (mMapTreasureList != null && mMapTreasureList.size() != 0) {
            for (int i = 0; i < mMapTreasureList.size(); i++) {
                VenuesTreasInfo mVenuesTreasInfo = mMapTreasureList.get(i);

                String is_receive = mVenuesTreasInfo.getIs_receive();
                String type = mVenuesTreasInfo.getType();
                if (is_receive.equals("0") && !type.equals("4")) {// 抢红包--未领取并且类型不属于航标
                    double Latitude = mVenuesTreasInfo.getLatitude();
                    double Longitude = mVenuesTreasInfo.getLongitude();
                    double juliString = GPSUtil.DistanceOfTwoPoints(
                            latitude_me, longitude_me, Latitude, Longitude);
                    juliString = juliString * 1000;
                    Log.e("----receiveTreasure----", i + "----------"
                            + juliString);
                    if (juliString < 50 && !grabTreasureStauts) {//
                        grabTreasureStauts = true;
                        String reward_item_id = mVenuesTreasInfo
                                .getReward_item_id();
                        grabTreasure(reward_item_id, i);
                        Log.e("----receiveTreasure----", i + "----------"
                                + reward_item_id);
                    }
                }

            }
        }
    }

    /**
     * 是否正在领取
     */
    Boolean grabTreasureStauts = false;

    /**
     * 抢红包
     */
    protected void grabTreasure(String reward_item_id, final int index) {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("reward_item_id", reward_item_id + "");
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        new GrabTreasureBusiness(this, mhashmap, new GetGrabTreasureCallback() {
            @Override
            public void afterDataGet(HashMap<String, Object> dataMap) {
                if (dataMap != null) {
                    String status = (String) dataMap.get("status");
                    if (status.equals("200")) {
                        // 抢成功时本地更新状态
                        mHongBaoPlayer.start();
                        mMapTreasureList.get(index).setIs_receive("1");
                        String msg = (String) dataMap.get("msg");
                        CommonUtils.getInstance().initToast(msg);
                    }
                }
                grabTreasureStauts = false;
                CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
                        dataMap);

            }
        });

    }

    /**
     * 获取周围红包
     */
    protected void getAroundTreasure() {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("longitude", longitude_me + "");
        mhashmap.put("latitude", latitude_me + "");
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        Log.e("TESTLOG", "------------getAroundTreasure------------" + mhashmap);
        new VenuesTreasureBusiness(this, "Around", mhashmap,
                new GetVenuesTreasureCallback() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void afterDataGet(HashMap<String, Object> dataMap) {
                        if (dataMap != null) {
                            String status = (String) dataMap.get("status");
                            if (status.equals("200")) {
                                mMapTreasureList = (ArrayList<VenuesTreasInfo>) dataMap
                                        .get("mlist");
                                mHandler.sendEmptyMessage(GETTREATURETHUMB);
                            }
                        }
                        CommonUtils.getInstance().setClearCacheBackDate(
                                mhashmap, dataMap);

                    }
                });

    }

    /**
     * 获取所有用户坐标
     */
    protected void getVenuesUsers() {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        mhashmap.put("longitude", longitude_me + "");
        mhashmap.put("latitude", latitude_me + "");
        savaInfoToSD("getVenuesUsers");
        Log.e("TESTLOG", "------------getVenuesUsers------------" + mhashmap);
        new VenuesMyAreaUsersBusiness(this, mhashmap,
                new GetVenuesMyAreaUsersCallback() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void afterDataGet(HashMap<String, Object> dataMap) {
                        if (dataMap != null) {
                            String status = (String) dataMap.get("status");
                            if (status.equals("200")) {
                                mMapList = (ArrayList<VenuesUsersInfo>) dataMap
                                        .get("mlist");
                                if (adapter == null) {
                                    adapter = new MapListAdapter(mHomeActivity, mMapList);
                                    adapter.setmUserListHideMap(mUserListHideMap);
                                    mListView.setAdapter(adapter);
                                } else {
                                    adapter.setmUserListHideMap(mUserListHideMap);
                                    adapter.setList(mMapList);
                                    adapter.notifyDataSetChanged();
                                }
                                mHandler.sendEmptyMessage(GETUSERTHUMB);
                            } else if (status.equals("401")
                                    || status.equals("402")) {
                                initStartView();
                                firstUploadLocationstatus = false;// 防止报401后初始化循环加载401问题
                                UserLoginBack403Utils
                                        .getInstance()
                                        .sendBroadcastLoginBack403(
                                                CommonUtils.getInstance().mHomeActivity);
                            }
                        }
                        mPullDownViewHandler.sendEmptyMessage(DATAUPDATEOVER);
                        CommonUtils.getInstance().setClearCacheBackDate(
                                mhashmap, dataMap);

                    }
                });

    }

    private void setNotDateImg() {
        if (not_date != null) {
            if (adapter == null || mMapList == null || (mMapList != null && mMapList.size() == 0)) {
                not_date.setVisibility(View.VISIBLE);
                mPullDownView.setVisibility(View.GONE);
            } else {
                not_date.setVisibility(View.GONE);
                mPullDownView.setVisibility(View.VISIBLE);
            }
        }
    }

    MapListAdapter adapter;
    userThumbShoaUtils muserThumbShoaUtils;

    /**
     * 设置中心点的焦点
     */
    private void moveToCenter() {
        if (moveMyLocationStaus) {
            moveMyLocationStaus = false;
            CameraUpdate u = CameraUpdateFactory.newLatLngZoom(currentlatLng,
                    mTrackUploadFragment.STARTZOOM);
            if (u != null && mBaiduMap != null) {
                mBaiduMap.moveCamera(u);// 以动画方式更新地图状态，动画耗时 300 ms
            }

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

    /**
     * 是否跟随一下轨迹
     */
    private void setToFollowStatus() {
        if (mTrackUploadFragment != null) {
            mTrackUploadFragment.settoFollowStatus(true);
        }
    }

    private void autoDetectNotification() {
        cb_voicestatus = welcomeSharedPreferences.getBoolean("cb_voicestatus",
                true);
        if (cb_voicestatus) {
            initSpeak();
            songArrayList.add("Yizidongshibie.mp3");
            switch (sportindex) {
                case Constants.BGSportsTypeWalk:
                    songArrayList.add("Zoulu.mp3");
                    break;
                case Constants.BGSportsTypeRun:
                    songArrayList.add("Paobu.mp3");
                    break;
                case Constants.BGSportsTypeBike:
                    songArrayList.add("Qixing.mp3");
                    break;
                case Constants.BGSportsTypeDrive:
                    songArrayList.add("Kaiche.mp3");
                    break;
                case Constants.BGSportsTypeWindSurfing:
                    songArrayList.add("Fanban.mp3");
                    break;
                case Constants.BGSportsTypeKiteSurfing:
                    songArrayList.add("Fengzhengchonglang.mp3");
                    break;
                default:
                    break;
            }
            songplay(songArrayList.get(songIndex));
        }
    }

    private void startSpeak() {
        cb_voicestatus = welcomeSharedPreferences.getBoolean("cb_voicestatus",
                true);
        if (cb_voicestatus) {
            initSpeak();
            songArrayList.add("Kaishiyundong.mp3");
            songplay(songArrayList.get(songIndex));
        }
    }

    private void pauseSpeak() {
        cb_voicestatus =
                welcomeSharedPreferences.getBoolean("cb_voicestatus",
                        true);
        if (cb_voicestatus && sportindex == 1) {
            songArrayList.add("Zantingpaobu.mp3");
            songplay(songArrayList.get(songIndex));
        }

    }

    private void contiueSpeak() {
        cb_voicestatus =
                welcomeSharedPreferences.getBoolean("cb_voicestatus",
                        true);
        if (cb_voicestatus && sportindex == 1) {
            songArrayList.add("Jixupaobu.mp3");
            songplay(songArrayList.get(songIndex));
        }

    }

    private void endSpeak() {
    }


    private void setSpeakContent() {
        int[] time = DatesUtils.getInstance().companyTimeNoSecond(
                runingTimestamp);
        initSpeak();
        if (sportindex == 1 || sportindex == 0) {//跑步 or 走路
            songArrayList.add("Attention.mp3");
            songArrayList.add("Paole.mp3");
        } else if (sportindex == 2) {
            songArrayList.add("Attention.mp3");
            songArrayList.add("Qixing.mp3");
        } else if (sportindex == 3) {
            songArrayList.add("Kaichexingshile.mp3");
        }
        int showdisd = (int) distanceTraveled;
        if (showdisd <= 100) {
            songArrayList.add(showdisd + ".mp3");
            int decimalPart = (int) ((distanceTraveled - (int) distanceTraveled) * 10.0);
            if (decimalPart > 0) {
                songArrayList.add("Dian.mp3");
                songArrayList.add(decimalPart + ".mp3");
            }
        } else {
            songArrayList.add(showdisd / 100 + "00.mp3");
            if (showdisd % 100 > 0) {
                songArrayList.add(showdisd % 100 + ".mp3");
            }
        }
        songArrayList.add("Gongli.mp3");// 公里
        //songArrayList.add("Blank.m4a");// 停顿
        songArrayList.add("Yongshi.mp3");// 用时

        if (time[0] > 0) {
            songArrayList.add(time[0] + ".mp3");
            songArrayList.add("Xiaoshi.mp3");// 小时
        }
        if (time[1] > 0) {
            songArrayList.add(time[1] + ".mp3");
            songArrayList.add("Fen.mp3");// 分
        }
        if (time[2] > 0) {
            songArrayList.add(time[2] + ".mp3");
            songArrayList.add("Miao.mp3");// 秒
        }

        if (sportindex == 1 && runingTimestamp - previousLapTimestamp > 0 && distanceTraveled >= 2) {// 跑步

            long second = (runingTimestamp - previousLapTimestamp) / 1000 % 60;
            long minute = ((runingTimestamp - previousLapTimestamp) / 1000 % 3600) / 60;
            long hour = (runingTimestamp - previousLapTimestamp) / 1000 / 3600;

            songArrayList.add("Zuijinyigonglipeisu.mp3");// 最近一公里配速
            if (hour > 0) {
                songArrayList.add(hour + ".mp3");
                songArrayList.add("Xiaoshi.mp3");// 小时
            }
            if (minute > 0) {
                songArrayList.add(minute + ".mp3");
                songArrayList.add("Fen.mp3");// 分
            }
            if (second > 0) {
                songArrayList.add(second + ".mp3");
                songArrayList.add("Miao.mp3");// 秒
            }

        }
        songplay(songArrayList.get(songIndex));
        // mSpeechSynthesizer.speak("您已经运动" + distanceTraveled +
        // "公里，当前海拔"
        // + currentAltitude + "米，用时" + time);

    }

    /**
     * 每次使用时初始化数据。防止数组越界
     */
    private void initSpeak() {
        songIndex = 0;
        songArrayList.clear();
    }

    /**
     * 滑雪每趟播报一次
     */
    private void valueSpeakSki() {
        if (sportindex == Constants.BGSportsTypeSnowboard || sportindex == Constants.BGSportsTypeSki) {
            cb_voicestatus = welcomeSharedPreferences.getBoolean(
                    "cb_voicestatus", true);
            int[] time = DatesUtils.getInstance().companyTimeNoSecond(
                    runingTimestamp);
            if (cb_voicestatus && btnStartStatus && btnContinueStatus
                    && distanceTraveled >= 1 && distanceTraveled < 1000) {
                initSpeak();
                songArrayList.add("Huale.mp3");// 您已经滑了
                int showdisd = (int) distanceTraveled;
                if (showdisd <= 100) {
                    songArrayList.add(showdisd + ".mp3");
                } else {
                    songArrayList.add(showdisd / 100 + "00.mp3");
                    if (showdisd % 100 > 0) {
                        songArrayList.add(showdisd % 100 + ".mp3");
                    }
                }
                songArrayList.add("Gongli.mp3");// 公里
                //songArrayList.add("Blank.m4a");// 停顿
                songArrayList.add("Huaxingjuli.mp3");// 滑行距离
                int showdownHillDistance = (int) downHillDistance;
                if (showdownHillDistance <= 100) {
                    songArrayList.add(showdownHillDistance + ".mp3");
                } else {
                    songArrayList.add(showdownHillDistance / 100
                            + "00.mp3");
                    if (showdownHillDistance % 100 > 0) {
                        songArrayList.add(showdownHillDistance % 100
                                + ".mp3");
                    }
                }
                songArrayList.add("Mi.mp3");// 米
                //songArrayList.add("Blank.mp3");// 停顿
                songArrayList.add("Yundong.mp3");// 您已经运动
                if (lapCount <= 100) {
                    songArrayList.add(lapCount + ".mp3");
                } else {
                    songArrayList.add(lapCount / 100 + "00.mp3");
                    if (showdownHillDistance % 100 > 0) {
                        songArrayList.add(lapCount % 100 + ".mp3");
                    }
                }
                songArrayList.add("Tang.mp3");// 趟
                //songArrayList.add("Blank.m4a");// 停顿
                songArrayList.add("Yongshi.mp3");// 用时

                if (time[0] > 0) {
                    songArrayList.add(time[0] + ".mp3");
                    songArrayList.add("Xiaoshi.mp3");// 小时
                }
                if (time[1] > 0) {
                    songArrayList.add(time[1] + ".mp3");
                    songArrayList.add("Fen.mp3");// 分
                }
                if (time[2] > 0) {
                    songArrayList.add(time[2] + ".mp3");
                    songArrayList.add("Miao.mp3");// 秒
                }
                songplay(songArrayList.get(songIndex));
            }
        }

    }

    /**
     * 是否在播放value
     */
    ArrayList<String> songArrayList = new ArrayList<String>();
    ;
    private int songIndex;

    private final class CompletionListener implements OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            nextsong();
        }

    }

    private void nextsong() {

        if (songIndex < songArrayList.size() - 1) {
            songIndex = songIndex + 1;
            songplay(songArrayList.get(songIndex));
        } else {
            songArrayList.clear();
            songIndex = 0;

        }

    }

    private void songplay(String path) {
        try {
            mediaPlayer.reset();
            AssetFileDescriptor afd = getAssets().openFd("play/" + path);
            mediaPlayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 结束后初始化所有状态
     */
    private void initStartView() {
        loadCancerStartSttus = false;
        moveMyLocationStaus = true;
        btnStartStatus = false;
        btnContinueStatus = false;
        firstLocationstatus = false;
        Constants.getInstance().haveGetMyLocationStauts = false;
        gpslocationListenerStatus = false;
        firstUploadLocationstatus = true;
        selectSportTypeByManuallyStatus = false;
        autoChangeSportStus = false;
        runingTimestamp = 0;// 运动时间
        startTimestamp = 0;// 开始时间
        pauseTimestamp = 0;// 休息暂停时间
        sleepDuration = 0;
        _nMaxSlopeAngle = 0;
        if (iv_start != null) {
            iv_start.setVisibility(View.VISIBLE);
            tv_distancetitle.setVisibility(View.GONE);
            tv_distance.setVisibility(View.GONE);
            layout_chat.setVisibility(View.GONE);
        }
        // 清除轨迹
        if (null != mTrackUploadFragment) {
            mTrackUploadFragment.initDates();
        }
        if (allpointList != null && allpointList.size() > 0) {
            for (MainLocationItemInfo iterable_element : allpointList) {
                iterable_element = null;
            }
            allpointList.clear();
        }
        if (mBaiduMap != null) {
            mBaiduMap.clear();
        }
        mUserListHideMap.clear();
        if (muserThumbShoaUtils != null) {
            muserThumbShoaUtils.clearMap();
        }
        showLatLng = null;
        beforelatLng = null;
        Speed = 0;
        stillTime = 0;
        distanceOfBeforeLat = 0.0;
        averageSpeed = 0;
        topSpeed = 0;
        matchSpeed = "--";
        minMatchSpeed = "--";
        maxMatchSpeed = "--";
        averageMatchSpeed = "--";
        minmatchSpeedTimestamp = 0;
        maxMatchSpeedTimestamp = 0;
        beforemaxMatchSpeedDis = 0;// 上一个计算距离点
        previousLapTimestamp = 0;
        recordDatas = "";
        nskiStatus = 0;
        _tmpAltitude = 0;
        minAltidue = 0;
        maxAltitude = 0;
        dropTraveled = 0;// 滑行落差
        hopCount = 0;// 跳跃次数
        minAltidueall = 0;// 最低海拔
        maxAltidueall = 0;// 最高海拔
        // 滑雪
        lapCount = 0;// 趟数（上升和下降的次数）
        upHillDistance = 0;// 上坡距离 （距离坐标点的累加） m
        downHillDistance = 0;// 下坡距离/滑行距离 m
        verticalDistance = 0;// 滑行落差/垂直距离 m
        _nMaxSlopeAngle = 0;// 最大坡度
        speakTime = 60 * 5;
        posid = "";
        pos_name = "";
        record_data_id = "";
        setGpsClose();
        nSteps = 0;
        setEndService();
        setSportPropertyList();
        startLocationServices();
    }

    /**
     * 是否进入后台运行
     */
    boolean backgroundStus = false;

    @Override
    protected void onPause() {
        try {
            /**
             * ***********************Android
             * 8.0版本以上定位问题********************************* 1.写后台服务server
             * 2.PowerManager Android 8.0 锁屏后地图无法定位 解决
             * 3.充电时可以一直记录,有toast,读写交互等也会一直显示 4.退到后台后gps定位会结束 Keep My GPS Alive
             * ；； Ulysse Gizmos 定位；；；oruxmaps
             */
            if (mMapView != null) {
                mMapView.onPause();
            }
            backgroundStus = true;
            Log.e("LocationMide", "onPauseonPauseonPause");
            super.onPause();
        } catch (Exception e) {
        }
    }

    protected void dayin(String sbd) {
        App.getInstance().savaInfoToSD("loc", sbd, false);
    }

    protected void savaInfoToSD(String sbd) {
        App.getInstance().savaInfoToSD(sbd, sbd, false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        try {
            if (mMapView != null) {
                mMapView.onResume();
            }
            startLocationServices();
            backgroundStus = false;
            setToFollowStatus();
            super.onResume();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        try {
            Log.e("TESTLOG", "onDestroy");
            if (mHongBaoPlayer != null) {
                mHongBaoPlayer.release();
                mHongBaoPlayer = null;
            }
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (thread != null) {
                mHandler.removeCallbacks(thread);
            }
            if (mTrackUploadFragment != null) {
                mTrackUploadFragment.initDates();
            }
            btnStartStatus = false;
            btnContinueStatus = false;
            /**
             * 防止定位运行状态时退出重新登录，初始化定位状态
             */
            Constants.getInstance().haveGetMyLocationStauts = false;
            if (allpointList != null && allpointList.size() > 0) {
                for (MainLocationItemInfo iterable_element : allpointList) {
                    iterable_element = null;
                }
                allpointList.clear();
            }
            // 关闭定位图层
            if (mBaiduMap != null) {
                mBaiduMap.setMyLocationEnabled(false);
                mBaiduMap.clear();
                mBaiduMap = null;
            }
            mUserListHideMap.clear();
            if (mMapView != null) {
                mMapView.removeAllViews();
                mMapView.onDestroy();
                mMapView = null;
            }
//            if (mDB != null) {
//                mDB.close();
//            }
            if (mRecordListDB != null) {
                mRecordListDB.close();
            }
            if (mTrackListDBOpenHelper != null) {
                mTrackListDBOpenHelper.close();
            }
            if (muserThumbShoaUtils != null) {
                muserThumbShoaUtils.clearMap();
            }
            nSteps = 0;
            setEndService();
            setGpsClose();
            stopLocationServices();
            if (locationChangeBroadcastReceiver != null)
                unregisterReceiver(locationChangeBroadcastReceiver);
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    /**
     * 关闭使用GPS
     */
    private void setGpsClose() {
        if (lm != null && gpslocationListener != null) {
            lm.removeUpdates(gpslocationListener);
        }
        if (lm != null && listener != null) {
            lm.removeGpsStatusListener(listener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == 1) {
                selectSportTypeByManuallyStatus = true;
                sportindex = data.getIntExtra("sportindex", 0);
                CommonUtils.getInstance().mCurrentActivity = mHomeActivity;
                mBaiduMap.setMapType(Constants.BaiduMapTYPE_NORMAL);// 交通图
                setSportPropertyList();
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 退出监听
     */
    public void onBackPressed() {
        //返回键返回桌面不退出程序
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
//        CommonUtils.getInstance().defineBackPressed(this, null, 0,
//                Constants.getInstance().exit);
    }

    // **********************后台定位监听***********************************

    /**
     * 开始定位服务
     */
    private void startLocationServices() {
        startService(new Intent(this, LocationService.class));
        LocationStatusManager.getInstance()
                .resetToInit(getApplicationContext());
    }

    /**
     * 关闭服务 先关闭守护进程，再关闭定位服务
     */
    private void stopLocationServices() {
        sendBroadcast(Utils.getCloseBrodecastIntent());
        LocationStatusManager.getInstance()
                .resetToInit(getApplicationContext());
    }

    /**
     * 注册广播
     */
    private void setLocationServiceBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.RECEIVER_ACTION);
        intentFilter.addAction(Constants.RECEIVER_ACTION_STEP);
        intentFilter.addAction(Constants.RECEIVER_ACTION_REFRESHMAIN);
        registerReceiver(locationChangeBroadcastReceiver, intentFilter);
    }

    private BroadcastReceiver locationChangeBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.RECEIVER_ACTION)) {
                Log.e("BroadcastReceiver", action);
                if (!gpslocationListenerStatus) {
                    /**
                     * **************防止gps没信号时，记录轨迹************
                     */
                    address = intent.getStringExtra("address");
                    currentAccuracy = (int) intent.getIntExtra(
                            "currentAccuracy", currentAccuracy);
                    longitude_me = intent.getDoubleExtra("longitude_me",
                            longitude_me);
                    latitude_me = intent.getDoubleExtra("latitude_me",
                            latitude_me);
                    int mLocType = (int) intent.getIntExtra("mLocType", 1);
                    Log.e("TESTLOG", address);
                    Log.e("TESTLOG", currentAccuracy + ";longitude_me="
                            + longitude_me + ";mLocType=" + mLocType);
                    Message msg = new Message();
                    msg.what = NETLOCATION;
                    msg.arg1 = mLocType;
                    mHandler.sendMessage(msg);
                    msg = null;
                    if (!btnStartStatus) {
                        stopLocationServices();
                    }
                }
            } else if (action.equals(Constants.RECEIVER_ACTION_STEP)) {
                nSteps = intent.getIntExtra("CURRENT_STEP_NOW", nSteps);
                Log.e("stepssteps", ";nStepsnStepsnStepsnStepsnSteps="
                        + nSteps);
            } else if (action.equals(Constants.RECEIVER_ACTION_REFRESHMAIN)) {
                String type = intent.getStringExtra("type");
                Log.e("REFRESHMAIN", ";type="
                        + type);
                Message msg = new Message();
                msg.what = UPDATEBTNSTATUS;
                msg.obj = type;
                mHandler.sendMessage(msg);
                msg = null;
            }
        }
    };
}
