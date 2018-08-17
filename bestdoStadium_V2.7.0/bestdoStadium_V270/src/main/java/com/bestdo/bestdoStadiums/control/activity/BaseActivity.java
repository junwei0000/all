package com.bestdo.bestdoStadiums.control.activity;

import cn.jpush.android.api.JPushInterface;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Config;
import com.umeng.analytics.MobclickAgent;
//import com.umeng.message.PushAgent;
//import com.umeng.message.UmengRegistrar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * base
 * 
 * @author jun
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
	protected Activity context;
	private String clazzName = getClass().getSimpleName();
	public SharedPreferences bestDoSharedPrefs;
	Editor bestDoEditor;

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
		// 另外防止屏幕锁屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		context = this;
		bestDoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		bestDoEditor = bestDoSharedPrefs.edit();
		initView();
		// PushAgent.getInstance(context).onAppStart();
		Config.d(clazzName + " onCreate");

	}

	/**
	 * 退出监听
	 */
	public void suBack() {
		Intent intents = new Intent();
		intents.setAction(getString(R.string.action_home));
		intents.putExtra("type", getString(R.string.action_home_type_gotohome));
		sendBroadcast(intents);
	}


	/**
	 * 初始化
	 */
	private void initView() {
		loadViewLayout();
		findViewById();
		setListener();
		processLogic();
	}

	/**
	 * 初始化layout文件
	 */
	protected abstract void loadViewLayout();

	/**
	 * 初始化资源控件属性
	 */
	protected abstract void findViewById();

	/**
	 * 设置监听事件
	 */
	protected abstract void setListener();

	/**
	 * 事务请求处理
	 */
	protected abstract void processLogic();

	@Override
	protected void onStart() {
		Config.d(clazzName + " onStart");
		super.onStart();
	}

	/**
	 * 友盟session统计
	 */
	protected void onResume() {
		Config.d(clazzName + " onResume");
		super.onResume();
		MobclickAgent.onPageStart("SplashScreen");
		MobclickAgent.onResume(this);
		JPushInterface.onResume(this);
	}

	protected void onPause() {
		Config.d(clazzName + " onPause");
		super.onPause();
		MobclickAgent.onPageEnd("SplashScreen");
		MobclickAgent.onPause(this);
		JPushInterface.onPause(this);
	}

	@Override
	protected void onStop() {
		Config.d(clazzName + " onStop");
		super.onStop();
	}

	@Override
	protected void onRestart() {
		Config.d(clazzName + " onRestart");
		super.onRestart();
		CommonUtils.getInstance().mCurrentActivity = context;// 返回时更新当前最新页面
	}

	@Override
	protected void onDestroy() {
		Config.d(clazzName + " onDestroy");
		super.onDestroy();
	}
}
