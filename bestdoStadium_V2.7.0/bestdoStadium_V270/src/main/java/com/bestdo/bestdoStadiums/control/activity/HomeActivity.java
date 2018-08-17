package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.App;
import com.bestdo.bestdoStadiums.utils.AppUpdate;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Config;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HomeActivity extends TabActivity {

	public static final String TAG = HomeActivity.class.getSimpleName();

	public RadioGroup mTabButtonGroup;
	private TabHost mTabHost;
	private RadioButton home_tab_main, home_tab_walk, home_tab_usercenter;

	public static final String TAB_MAIN = "MAIN_ACTIVITY";
	public static final String TAB_SPORTQURAT = "SPORTQURAT_ACTIVITY";
	public static final String TAB_WALK = "WALK_ACTIVITY";
	public static final String TAB_CENTER = "CENTER_ACTIVITY";

	/**
	 * 点击收藏登录记录之前的tab
	 */
	private String before_tab = "";
	/**
	 * 点击之后正常要跳转的
	 */
	private String after_tab = "";

	public LinearLayout home_layout_line;

	public LinearLayout home_tab_content;

	private RadioButton home_tab_sportqurat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CommonUtils.getInstance().nHomeActivity = this;
		setContentView(R.layout.activity_home);
		findViewById();
		initView();
		AppUpdate appUpdate = new AppUpdate(this);
		appUpdate.startUpdateAsy(Constans.getInstance().updateVersionFromMainPage);
	}

	private void findViewById() {
		home_tab_content = (LinearLayout) findViewById(R.id.home_tab_content);
		home_layout_line = (LinearLayout) findViewById(R.id.home_layout_line);
		mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
		home_tab_main = (RadioButton) findViewById(R.id.home_tab_main);
		home_tab_sportqurat = (RadioButton) findViewById(R.id.home_tab_sportqurat);
		home_tab_walk = (RadioButton) findViewById(R.id.home_tab_walk);
		home_tab_usercenter = (RadioButton) findViewById(R.id.home_tab_usercenter);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
	}

	private void initView() {
		mTabHost = getTabHost();
		Intent i_main = new Intent(this, MainActivity.class);
		Intent i_sportqurat = new Intent(this, CampaignQuartMainActivity.class);
		Intent i_walking = new Intent(this, WalkActivity.class);
		Intent i_category = new Intent(this, UserCenterActivity.class);

		mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN).setContent(i_main));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_SPORTQURAT).setIndicator(TAB_SPORTQURAT).setContent(i_sportqurat));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_WALK).setIndicator(TAB_WALK).setContent(i_walking));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_CENTER).setIndicator(TAB_CENTER).setContent(i_category));

		mTabHost.setCurrentTabByTag(TAB_MAIN);
		before_tab = TAB_MAIN;
		mTabButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.home_tab_main:
					before_tab = TAB_MAIN;
					mTabHost.setCurrentTabByTag(TAB_MAIN);
					break;
				case R.id.home_tab_sportqurat:
					after_tab = TAB_SPORTQURAT;
					chackSkipByLoginStatus();
					break;
				case R.id.home_tab_walk:
					after_tab = TAB_WALK;
					chackSkipByLoginStatus();
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

	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;

	/**
	 * 跳转用户中心判断
	 */
	private void chackSkipByLoginStatus() {
		try {
			String loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
			if (loginStatus.equals(Constans.getInstance().loginStatus)) {
				mTabHost.setCurrentTabByTag(after_tab);
			} else {
				bestDoInfoEditor.putString("loginskiptostatus", Constans.getInstance().loginskiptoinmain);
				bestDoInfoEditor.commit();
				Intent intent = new Intent(this, UserLoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, this);
			}
		} catch (Exception e) {
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		// 动态注册广播
		filter = new IntentFilter();
		filter.addAction(getString(R.string.action_home));
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
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
			if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) { // 开屏
				onScreenOn();
			} else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) { // 锁屏
				onScreenOff();
			}
			Log.e("all of page", "接收---下线通知---广播消息");
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				String type = bundle.getString("type", "");
				if (type.equals(getString(R.string.action_home_type_login403))) {
					UserLoginBack403Utils.getInstance()
							.showDialogPromptReLogin(CommonUtils.getInstance().mCurrentActivity);
					// <!--登录注册注销时返回首页 -->
				} else if (type.equals(getString(R.string.action_home_type_loginregok))) {
					mTabHost.setCurrentTabByTag(after_tab);
					setTab(after_tab);
				} else if (type.equals(getString(R.string.action_home_type_gotohome))) {
					mTabHost.setCurrentTabByTag(TAB_MAIN);
					setTab(TAB_MAIN);
				} else if (type.equals(getString(R.string.action_home_type_centerfrommain))) {
					mTabHost.setCurrentTabByTag(TAB_CENTER);
					setTab(TAB_CENTER);
				} else if (type.equals(getString(R.string.action_home_type_back))) {
					mTabHost.setCurrentTabByTag(before_tab);
					setTab(before_tab);
				} else if (type.equals(getString(R.string.actionhome_bussinesspersonal))) {
					String selectStatus = intent.getExtras().getString("bussinessOrPersonalSelectStatus");
					Message msg = new Message();
					msg.what = NOSHOWING;
					msg.obj = selectStatus;
					mHandler.sendMessage(msg);
				}
			}
		}
	};

	private void onScreenOn() {

	}

	private void onScreenOff() {
		App.runInMainThread(new Runnable() {

			@Override
			public void run() {
				Config.config().setSensorState(false);
			}
		}, 1000);

	}

	private final int NOSHOWING = 6;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case NOSHOWING:
				String selectStatus = (String) msg.obj;
				if (selectStatus.equals("business")) {
					home_layout_line.setVisibility(View.GONE);
					mTabButtonGroup.setVisibility(View.GONE);
				} else {
					home_layout_line.setVisibility(View.VISIBLE);
					mTabButtonGroup.setVisibility(View.VISIBLE);
				}
				break;

			}
		}
	};

	/**
	 * 设置选择的tab
	 * 
	 * @param tab
	 */
	private void setTab(String tab) {
		if (tab.equals(TAB_MAIN)) {
			home_tab_main.setChecked(true);
		} else if (tab.equals(TAB_CENTER)) {
			home_tab_usercenter.setChecked(true);
		} else if (tab.equals(TAB_WALK)) {
			home_tab_walk.setChecked(true);
		} else if (tab.equals(TAB_SPORTQURAT)) {
			home_tab_sportqurat.setChecked(true);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		CommonUtils.getInstance().nHomeActivityShowStatus = true;
		MobclickAgent.onPageStart("MainScreen");
	}

	@Override
	protected void onPause() {
		super.onPause();
		CommonUtils.getInstance().nHomeActivityShowStatus = false;
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(dynamicReceiver);
		dynamicReceiver = null;
		filter = null;
	}
}
