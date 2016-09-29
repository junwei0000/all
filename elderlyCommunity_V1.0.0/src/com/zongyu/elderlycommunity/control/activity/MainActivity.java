package com.zongyu.elderlycommunity.control.activity;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.R.layout;
import com.zongyu.elderlycommunity.model.UserLoginInfo;
import com.zongyu.elderlycommunity.utils.CommonUtils;
import com.zongyu.elderlycommunity.utils.ConfigUtils;
import com.zongyu.elderlycommunity.utils.Constans;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	private LinearLayout home_tab_content;
	private LinearLayout home_layout_line;
	private RadioGroup mTabButtonGroup;
	private RadioButton home_tab_calendar;
	private RadioButton home_tab_campaign;
	private RadioButton home_tab_tixing;
	private RadioButton home_tab_usercenter;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;
	private TabHost mTabHost;

	public static final String TAB_CALENDER = "CALENDER_ACTIVITY";
	public static final String TAB_CAMPAIGN = "CAMPAIGN_ACTIVITY";
	public static final String TAB_TIXING = "TIXING_ACTIVITY";
	public static final String TAB_CENTER = "CENTER_ACTIVITY";
	/**
	 * 点击收藏登录记录之前的tab
	 */
	private String before_tab;
	/**
	 * 点击之后正常要跳转的
	 */
	private String after_tab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Constans.getInstance().mHomeActivity = this;
		setContentView(R.layout.main);
		findViewById();
		initView();
	}

	private void findViewById() {
		home_tab_content = (LinearLayout) findViewById(R.id.home_tab_content);
		home_layout_line = (LinearLayout) findViewById(R.id.home_layout_line);
		mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);

		home_tab_calendar = (RadioButton) findViewById(R.id.home_tab_calendar);
		home_tab_campaign = (RadioButton) findViewById(R.id.home_tab_campaign);
		home_tab_tixing = (RadioButton) findViewById(R.id.home_tab_tixing);
		home_tab_usercenter = (RadioButton) findViewById(R.id.home_tab_usercenter);
		bestDoInfoSharedPrefs = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(this);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
	}

	private void initView() {
		mTabHost = getTabHost();
		Intent i_calendar = new Intent(this, MainCalenderActivity.class);
		Intent i_tixing = new Intent(this, MainTiXingActivity.class);
		Intent i_campaign = new Intent(this, MainCampaignActivity.class);
		Intent i_usercenter = new Intent(this, UserCenterActivity.class);

		mTabHost.addTab(mTabHost.newTabSpec(TAB_CALENDER)
				.setIndicator(TAB_CALENDER).setContent(i_calendar));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_TIXING)
				.setIndicator(TAB_TIXING).setContent(i_tixing));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CAMPAIGN)
				.setIndicator(TAB_CAMPAIGN).setContent(i_campaign));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CENTER)
				.setIndicator(TAB_CENTER).setContent(i_usercenter));
		before_tab = TAB_CALENDER;
		mTabHost.setCurrentTabByTag(TAB_CALENDER);
		mTabButtonGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.home_tab_calendar:
							before_tab = TAB_CALENDER;
							mTabHost.setCurrentTabByTag(TAB_CALENDER);
							break;

						case R.id.home_tab_tixing:
							after_tab = TAB_TIXING;
							chackSkipByLoginStatus();
							break;
						case R.id.home_tab_campaign:
							before_tab = TAB_CAMPAIGN;
							after_tab = TAB_CAMPAIGN;
							mTabHost.setCurrentTabByTag(TAB_CAMPAIGN);
							break;
						case R.id.home_tab_usercenter:
							before_tab = TAB_CENTER;
							after_tab = TAB_CENTER;
							mTabHost.setCurrentTabByTag(TAB_CENTER);
							break;

						default:
							break;
						}
					}
				});
	}

	/**
	 * 跳转用户中心判断
	 */
	private void chackSkipByLoginStatus() {
		try {
			String loginStatus = bestDoInfoSharedPrefs.getString("loginStatus",
					"");
			if (loginStatus.equals(Constans.getInstance().loginStatus)) {
				mTabHost.setCurrentTabByTag(after_tab);
			} else {
				bestDoInfoEditor.putString("loginskiptostatus",
						Constans.getInstance().loginskiptoTiXing);
				bestDoInfoEditor.commit();
				Intent intent = new Intent(this, UserLoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, this);
			}
		} catch (Exception e) {
		}

	}

	/**
	 * 设置选择的tab
	 * 
	 * @param tab
	 */
	private void setTab(String tab) {
		if (tab.equals(TAB_CALENDER)) {
			home_tab_calendar.setChecked(true);
		} else if (tab.equals(TAB_CAMPAIGN)) {
			home_tab_campaign.setChecked(true);
		} else if (tab.equals(TAB_TIXING)) {
			home_tab_tixing.setChecked(true);
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
				// UserLoginBack403Utils.getInstance().showDialogPromptReLogin(
				// CommonUtils.getInstance().mCurrentActivity);
				// <!--登录注册注销时返回首页 -->
			} else if (type
					.equals(getString(R.string.action_home_type_gotohome))) {
				mTabHost.setCurrentTabByTag(TAB_CALENDER);
				setTab(TAB_CALENDER);
			} else if (type
					.equals(getString(R.string.action_home_type_loginregok))) {
				mTabHost.setCurrentTabByTag(after_tab);
				setTab(after_tab);
			} else if (type
					.equals(getString(R.string.action_home_type_loginback))) {
				mTabHost.setCurrentTabByTag(before_tab);
				setTab(before_tab);
			}
		}
	};
}
