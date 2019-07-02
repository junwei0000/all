package com.KiwiSports.control.activity;

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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.business.GrabTreasureBusiness;
import com.KiwiSports.business.GrabTreasureBusiness.GetGrabTreasureCallback;
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
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.model.db.TrackListDBOpenHelper;
import com.KiwiSports.utils.App;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.GPSUtil;
import com.KiwiSports.utils.LanguageUtil;
import com.KiwiSports.utils.MobileInfoUtils;
import com.KiwiSports.utils.MyDialog;
import com.KiwiSports.utils.MyGridView;
import com.KiwiSports.utils.PriceUtils;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述： 首页轨迹属性显示
 */
public class MainStarPropertytActivity extends BaseActivity implements
        OnClickListener {
    private LinearLayout pagetop_layout_back;
    private TextView top_tv_title;
    private TextView tv_distance;
    private TextView tv_quannum;
    private MyGridView mygridview_property;
    private ImageView iv_share;
    private TextView iv_continue;
    private TextView iv_pause;
    private ImageView iv_stop;

    private SharedPreferences bestDoInfoSharedPrefs;
    private String uid = "";
    private String nick_name = "";

    private ArrayList<MainSportInfo> mMpropertyList;
    private ArrayList<MainSportInfo> mpropertytwnList;
    private MainPropertyAdapter mMainSportAdapter;
    private int sportindex;
    // --------------
    private double distanceTraveled;// 总距离
    public static String duration;// 运动时间 00:00:00
    private long freezeDuration;// 休息时间 s
    private double averageSpeed;// 平均速度
    private int nSteps;// 步数
    private int currentAltitude;// 当前海拔


    private double topSpeed;// 最高速度
    public double Speed;// 速度

    private int downHillDistance = 0;// 下坡距离/滑行距离 m
    private int verticalDistance = 0;// 滑行落差/垂直距离 m
    private int _nMaxSlopeAngle = 0;// 最大坡度
    private String minMatchSpeed = "--";// 最慢配速
    private String maxMatchSpeed = "--";// 最快配速 / 最大速度
    private String averageMatchSpeed = "--";// 平均配速

    private int lapCount = 0;

    boolean btnContinueStatus = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_back:
                doback();
                break;
            case R.id.iv_share:
                sendMiniApps();
                break;
            case R.id.iv_continue:
                btnContinueStatus = true;
                setBtn();
                setContinue();
                break;
            case R.id.iv_pause:
                btnContinueStatus = false;
                setBtn();
                setPause();
                break;
            case R.id.iv_stop:
                //结束
                setStop();
                break;
            default:
                break;
        }
    }


    protected void loadViewLayout() {
        setContentView(R.layout.main_start_property);
        setLocationServiceBroadcast();
    }

    protected void findViewById() {
        pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
        top_tv_title = (TextView) findViewById(R.id.top_tv_title);

        tv_distance = (TextView) findViewById(R.id.tv_distance);
        tv_quannum = (TextView) findViewById(R.id.tv_quannum);

        mygridview_property = (MyGridView) findViewById(R.id.mygridview_property);

        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_continue = (TextView) findViewById(R.id.iv_continue);
        iv_pause = (TextView) findViewById(R.id.iv_pause);
        iv_stop = (ImageView) findViewById(R.id.iv_stop);
        CommonUtils.getInstance().setTextViewTypeface(this, tv_distance);
        CommonUtils.getInstance().setTextViewTypeface(this, tv_quannum);
        CommonUtils.getInstance().setTextViewTypeface(this, iv_continue);
        CommonUtils.getInstance().setTextViewTypeface(this, iv_pause);
    }

    protected void setListener() {
        pagetop_layout_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_continue.setOnClickListener(this);
        iv_pause.setOnClickListener(this);
        iv_stop.setOnClickListener(this);
    }

    protected void processLogic() {
        bestDoInfoSharedPrefs = CommonUtils.getInstance()
                .getBestDoInfoSharedPrefs(this);
        uid = bestDoInfoSharedPrefs.getString("uid", "");
        nick_name = bestDoInfoSharedPrefs.getString("nick_name", "");
        Intent intent = getIntent();
        getCurrentPropertyValue(intent);
    }


    /**
     * 分享小程序
     * https://blog.csdn.net/snow12342234/article/details/76640820
     */
    private void sendMiniApps() {
        // 添加微信平台,设置回调监听
        UMWXHandler wxHandler = new UMWXHandler(this, Constants.WEIXIN_APP_ID, Constants.WEIXIN_APP_SECRET);
        wxHandler.setRefreshTokenAvailable(false);
        wxHandler.addToSocialSDK();

        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        // 低版本微信打开 URL
        miniProgram.webpageUrl = "http://www.qq.com";
        // 跳转的小程序的原始 ID
        miniProgram.userName = "gh_91040b089b00";
        // 小程序的 Path
        miniProgram.path = "pages/login/login?uid=" + uid;

        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.share_bg);
//		WXImageObject miniProgram = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage(miniProgram);
        msg.title = "“" + nick_name + "”的实时位置";
        msg.description = "“" + nick_name + "”的实时位置";
        msg.setThumbImage(bmp);

        /**
         * 分享或收藏的目标场景，通过修改scene场景值实现。
         *
         * 发送到聊天界面——WXSceneSession
         *
         * 发送到朋友圈——WXSceneTimeline
         *
         * 添加到微信收藏——WXSceneFavorite
         */
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;// 发送到聊天界面
        MyApplication.getInstance().msgApi.sendReq(req);
    }


    /**
     * 获取当前的属性值
     */
    private void getCurrentPropertyValue(Intent intent) {
        distanceTraveled = intent.getDoubleExtra("distanceTraveled", 0);
        duration = intent.getStringExtra("duration");
        freezeDuration = intent.getLongExtra("freezeDuration", 0);
        averageSpeed = intent.getDoubleExtra("averageSpeed", 0);
        nSteps = intent.getIntExtra("nSteps", 0);
        currentAltitude = intent.getIntExtra("currentAltitude", 0);
        averageMatchSpeed = intent.getStringExtra("averageMatchSpeed");
        maxMatchSpeed = intent.getStringExtra("maxMatchSpeed");
        minMatchSpeed = intent.getStringExtra("minMatchSpeed");
        topSpeed = intent.getDoubleExtra("topSpeed", 0);
        downHillDistance = intent.getIntExtra("downHillDistance", 0);
        verticalDistance = intent.getIntExtra("verticalDistance", 0);
        _nMaxSlopeAngle = intent.getIntExtra("_nMaxSlopeAngle", 0);
        Speed = intent.getDoubleExtra("Speed", 0);
        lapCount = intent.getIntExtra("lapCount", 0);
        sportindex = intent.getIntExtra("sportindex", 0);
        btnContinueStatus = intent.getBooleanExtra("btnContinueStatus", false);
        setBtn();
        setSportPropertyList();
        showCurrentPropertyValue();
    }


    private void setBtn() {
        if (btnContinueStatus) {
            iv_pause.setVisibility(View.VISIBLE);
            iv_continue.setVisibility(View.GONE);
            top_tv_title.setText("运动已开始");
        } else {
            iv_pause.setVisibility(View.GONE);
            iv_continue.setVisibility(View.VISIBLE);
            top_tv_title.setText("运动已暂停");
        }
    }

    /**
     * 切换运动类型初始化显示属性
     */

    private void setSportPropertyList() {
        MainsportParser mMainsportParser = new MainsportParser();
        HashMap<Integer, MainSportInfo> mallsportList = mMainsportParser.parseJSONHash(App.getInstance().getContext());
        if (mallsportList.containsKey(sportindex)) {
            MainSportInfo mMainSportInfo = mallsportList.get(sportindex);
            mMpropertyList = mMainSportInfo.getMpropertyList();
            mpropertytwnList = mMainSportInfo.getMpropertytwnList();
            mMainSportAdapter = new MainPropertyAdapter(this, mMpropertyList, sportindex);
            mygridview_property.setAdapter(mMainSportAdapter);
            if (mpropertytwnList != null && mpropertytwnList.size() == 2) {
                tv_distance.setText(mpropertytwnList.get(0).getValue());
                tv_quannum.setText(mpropertytwnList.get(1).getValue());
            }
        }
    }

    /**
     * 显示属性值
     */
    private void showCurrentPropertyValue() {
        mpropertytwnList.get(0).setValue(
                PriceUtils.getInstance().formatFloatNumber(distanceTraveled)
                        + "");
        mpropertytwnList.get(1).setValue(duration);
        if (sportindex == 0) {// 走路
            for (int i = 0; i < mMpropertyList.size(); i++) {
                switch (i) {
                    case 0:
                        mMpropertyList.get(i).setValue(averageSpeed + "");
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(freezeDuration / 60 + "");
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
        } else if (sportindex == 1 || sportindex == 5) {// 跑步
            for (int i = 0; i < mMpropertyList.size(); i++) {
                switch (i) {
                    case 0:
                        mMpropertyList.get(i).setValue(averageMatchSpeed + "");
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(maxMatchSpeed);
                        break;

                    case 2:
                        mMpropertyList.get(i).setValue(nSteps + "");
                        break;
                    case 3:
                        mMpropertyList.get(i).setValue(freezeDuration / 60 + "");
                        break;
                    case 4:
                        mMpropertyList.get(i).setValue(minMatchSpeed);
                        break;
                    default:
                        break;
                }
            }

        } else if (sportindex == 2 || sportindex == 6) {// 骑行
            for (int i = 0; i < mMpropertyList.size(); i++) {
                switch (i) {
                    case 0:
                        mMpropertyList.get(i).setValue(averageMatchSpeed);
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(maxMatchSpeed + "");
                        break;
                    case 2:
                        mMpropertyList.get(i).setValue(freezeDuration / 60 + "");
                        break;
                    case 3:
                        mMpropertyList.get(i).setValue(currentAltitude + "");
                        break;
                    default:
                        break;
                }
            }
        } else if (sportindex == 13 || sportindex == 14) {// 滑雪
            for (int i = 0; i < mMpropertyList.size(); i++) {
                switch (i) {
                    case 0:
                        mMpropertyList.get(i).setValue(topSpeed + "");
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(
                                String.valueOf(downHillDistance));
                        break;
                    case 2:
                        mMpropertyList.get(i).setValue("" + lapCount);
                        break;
                    case 3:
                        mMpropertyList.get(i).setValue(verticalDistance + "");
                        break;
                    case 4:
                        mMpropertyList.get(i).setValue(
                                String.valueOf(_nMaxSlopeAngle));
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
                        mMpropertyList.get(i).setValue(
                                PriceUtils.getInstance().formatFloatNumber(
                                        averageSpeed)
                                        + "");
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(freezeDuration / 60 + "");
                        break;
                    case 2:
                        mMpropertyList.get(i).setValue(topSpeed + "");
                        break;
                    case 3:
                        mMpropertyList.get(i).setValue(
                                PriceUtils.getInstance().formatFloatNumber(Speed)
                                        + "");
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


    /**
     * 点击暂停
     */
    private void setPause() {
        Intent intent = new Intent(Constants.RECEIVER_ACTION_REFRESHMAIN);
        intent.putExtra("type", "pause");
        sendBroadcast(intent);
    }

    /**
     * 点击继续
     */
    private void setContinue() {
        Intent intent = new Intent(Constants.RECEIVER_ACTION_REFRESHMAIN);
        intent.putExtra("type", "continue");
        sendBroadcast(intent);
    }

    /**
     * 点击结束
     */
    private void setStop() {
        Intent intent = new Intent(Constants.RECEIVER_ACTION_REFRESHMAIN);
        intent.putExtra("type", "stop");
        sendBroadcast(intent);
        finish();
    }

    /**
     * 注册广播
     */
    private void setLocationServiceBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.RECEIVER_ACTION_REFRESHPROPERTY);
        registerReceiver(locationChangeBroadcastReceiver, intentFilter);
    }

    private BroadcastReceiver locationChangeBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.RECEIVER_ACTION_REFRESHPROPERTY)) {
                getCurrentPropertyValue(intent);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationChangeBroadcastReceiver != null)
            unregisterReceiver(locationChangeBroadcastReceiver);
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
