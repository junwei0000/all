package com.zh.education.control.activity;

import com.zh.education.utils.CommonUtils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2015-12-31 下午5:16:34
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
		CommonUtils.getInstance().setTranslucentStatus(context);
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

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
