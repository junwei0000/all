package com.zongyu.elderlycommunity.control.activity;

import com.zongyu.elderlycommunity.utils.Constans;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @author      作者：zoc
 * @date        创建时间：2016-9-27 上午10:15:50
 * @Description 类描述：base
 */
public abstract class BaseActivity extends Activity implements
		View.OnClickListener {
	protected Activity context;

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
		initView();

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
		super.onStart();
	}

	/**
	 * 友盟session统计
	 */
	protected void onResume() {
		super.onResume();
	}

	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Constans.getInstance().mCurrentActivity = context;// 返回时更新当前最新页面
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
