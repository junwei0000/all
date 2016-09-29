package com.zongyu.elderlycommunity.control.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.utils.CommonUtils;
import com.zongyu.elderlycommunity.utils.ConfigUtils;
import com.zongyu.elderlycommunity.utils.SupplierEditText;
import com.zongyu.elderlycommunity.utils.volley.RequestUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：个人中心
 */
public class UserCenterActivity extends BaseActivity {

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.pagetop_layout_back:
		// nowFinish();
		// break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		// setContentView(R.layout.user_login);
		CommonUtils.getInstance().addActivity(this);
		// CommonUtils.getInstance().addPayPageActivity(this);//
		// 为了自动注册后登录，在UserRegistSetPwActivity页面关闭
		// mUserLoginSkipUtils = new UserLoginSkipUtils(this);

	}

	@Override
	protected void findViewById() {
		// pagetop_layout_back = (LinearLayout)
		// findViewById(R.id.pagetop_layout_back);
		// pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		// pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		// pagetop_layout_you = (LinearLayout)
		// findViewById(R.id.pagetop_layout_you);
		//
		// userlogin_tv_fastlogin = (TextView)
		// findViewById(R.id.userlogin_tv_fastlogin);
		// userlogin_fastlogin_bottomline = (TextView)
		// findViewById(R.id.userlogin_fastlogin_bottomline);
		// userlogin_tv_commonlogin = (TextView)
		// findViewById(R.id.userlogin_tv_commonlogin);
		// userlogin_commonlogin_bottomline = (TextView)
		// findViewById(R.id.userlogin_commonlogin_bottomline);

	}

	@Override
	protected void setListener() {
	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub

	}

	/**
	 * 退出监听
	 */
	public void onBackPressed() {
		Intent intents = new Intent();
		intents.setAction(getString(R.string.action_home));
		intents.putExtra("type", getString(R.string.action_home_type_gotohome));
		sendBroadcast(intents);
	}

}
