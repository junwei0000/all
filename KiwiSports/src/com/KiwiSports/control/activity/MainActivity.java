package com.KiwiSports.control.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.UpdateLocationBusiness;
import com.KiwiSports.business.UpdateLocationBusiness.GetUpdateLocationCallback;
import com.KiwiSports.control.view.LocationUtils;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constans;
import com.baidu.mapapi.model.LatLng;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	private RadioGroup mTabButtonGroup;
	private RadioButton home_tab_main;
	private RadioButton home_tab_location;
	private RadioButton home_tab_record;
	private RadioButton home_tab_usercenter;
	private TabHost mTabHost;
	private String uid;
	private String token;
	private String access_token;
	private LocationUtils mLocationUtils;

	public static final String TAB_CALENDER = "CALENDER_ACTIVITY";
	public static final String TAB_CAMPAIGN = "CAMPAIGN_ACTIVITY";
	public static final String TAB_TIXING = "TIXING_ACTIVITY";
	public static final String TAB_CENTER = "CENTER_ACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Constans.getInstance().mHomeActivity = this;
		findViewById();
		initView();
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
	mLocationUtils=	new LocationUtils(this, mHandler, UPDATELOCATION);
	}

	/**
	 * 下拉刷新
	 */
	private final int UPDATELOCATION = 1;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATELOCATION:
				LatLng mypoint = (LatLng) msg.obj;
				double longitude_me = mypoint.longitude;
				double latitude_me = mypoint.latitude;
//				  longitude_me = 39.958416;
//				  latitude_me = 116.348349;
//				左上角经度 x:39.958416 左上角纬度 y:116.348349
				updateLocation(longitude_me, latitude_me);
				break;
			}
		}
	};
	private HashMap<String, String> mhashmap;

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

	private void findViewById() {
		mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);

		home_tab_main = (RadioButton) findViewById(R.id.home_tab_main);
		home_tab_location = (RadioButton) findViewById(R.id.home_tab_location);
		home_tab_record = (RadioButton) findViewById(R.id.home_tab_record);
		home_tab_usercenter = (RadioButton) findViewById(R.id.home_tab_usercenter);
	}

	private void initView() {
		mTabHost = getTabHost();
		Intent i_calendar = new Intent(this, MainStartActivity.class);
		Intent i_campaign = new Intent(this, MainLocationActivity.class);
		Intent i_tixing = new Intent(this, MainRecordActivity.class);
		Intent i_usercenter = new Intent(this, UserCenterActivity.class);

		mTabHost.addTab(mTabHost.newTabSpec(TAB_CALENDER).setIndicator(TAB_CALENDER).setContent(i_calendar));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CAMPAIGN).setIndicator(TAB_CAMPAIGN).setContent(i_campaign));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_TIXING).setIndicator(TAB_TIXING).setContent(i_tixing));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CENTER).setIndicator(TAB_CENTER).setContent(i_usercenter));

		mTabHost.setCurrentTabByTag(TAB_CALENDER);
		mTabButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.home_tab_main:
					mTabHost.setCurrentTabByTag(TAB_CALENDER);
					break;

				case R.id.home_tab_location:
					mTabHost.setCurrentTabByTag(TAB_CAMPAIGN);
					break;
				case R.id.home_tab_record:
					mTabHost.setCurrentTabByTag(TAB_TIXING);
					break;
				case R.id.home_tab_usercenter:
					mTabHost.setCurrentTabByTag(TAB_CENTER);
					break;

				default:
					break;
				}
			}
		});
	}

	/**
	 * 设置选择的tab
	 * 
	 * @param tab
	 */
	private void setTab(String tab) {
		if (tab.equals(TAB_CALENDER)) {
			home_tab_main.setChecked(true);
		} else if (tab.equals(TAB_CAMPAIGN)) {
			home_tab_location.setChecked(true);
		} else if (tab.equals(TAB_TIXING)) {
			home_tab_record.setChecked(true);
		} else if (tab.equals(TAB_CENTER)) {
			home_tab_usercenter.setChecked(true);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(dynamicReceiver);
		dynamicReceiver = null;
		filter = null;
		mLocationUtils.onDestory();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// 动态注册广播
		filter = new IntentFilter();
		filter.addAction(getString(R.string.action_home));
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(dynamicReceiver, filter);
	}

	/**
	 * 多端登录时接收下线通知提示广播
	 */
	IntentFilter filter;
	private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context contexts, Intent intent) {
			Log.e("all of page", "接收---下线通知---广播消息");
			String type = intent.getExtras().getString("type");
			if (type.equals(getString(R.string.action_home_type_login403))) {
				UserLoginBack403Utils.getInstance().showDialogPromptReLogin(CommonUtils.getInstance().mCurrentActivity);
			} else if (type.equals(getString(R.string.action_home_type_gotohome))) {
				mTabHost.setCurrentTabByTag(TAB_CALENDER);
				setTab(TAB_CALENDER);
			}
		}
	};
}
