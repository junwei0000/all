package com.zongyu.elderlycommunity.control.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.business.UserLoginBusiness;
import com.zongyu.elderlycommunity.business.UserLoginBusiness.GetLoginCallback;
import com.zongyu.elderlycommunity.business.UserRegistBusiness;
import com.zongyu.elderlycommunity.business.UserRegistBusiness.GetRegistCallback;
import com.zongyu.elderlycommunity.business.UserRegistCheckCodeBusiness;
import com.zongyu.elderlycommunity.business.UserRegistCheckCodeBusiness.GetRegistCheckCodeCallback;
import com.zongyu.elderlycommunity.business.UserRegistGetCodeBusiness;
import com.zongyu.elderlycommunity.business.UserRegistGetCodeBusiness.GetRegistGetCodeCallback;
import com.zongyu.elderlycommunity.model.UserLoginInfo;
import com.zongyu.elderlycommunity.utils.CommonUtils;
import com.zongyu.elderlycommunity.utils.ConfigUtils;
import com.zongyu.elderlycommunity.utils.Constans;
import com.zongyu.elderlycommunity.utils.HaveThIconClearEditText;
import com.zongyu.elderlycommunity.utils.SupplierEditText;
import com.zongyu.elderlycommunity.utils.volley.RequestUtils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：注册验码页面
 */
public class UserRegistCodeActivity extends BaseActivity {

	private HaveThIconClearEditText userregcode_et_code;
	private Button click_btn;
	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView userregcode_tv_phone;
	private TextView userregcode_tv_getcode;
	private String account;
	private String password;
	UserLoginSkipUtils mUserLoginSkipUtils;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.userregcode_tv_getcode:
			getCode();
			break;
		case R.id.click_btn:
			String code = userregcode_et_code.getText().toString();
			if (isCheckNext(code)) {
				CommonUtils.getInstance().closeSoftInput(
						UserRegistCodeActivity.this);
				checkCode(code);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_regist_code);
		CommonUtils.getInstance().addActivity(this);
		CommonUtils.getInstance().addPayPageActivity(this);
		mUserLoginSkipUtils = new UserLoginSkipUtils(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		userregcode_tv_phone = (TextView) findViewById(R.id.userregcode_tv_phone);
		userregcode_et_code = (HaveThIconClearEditText) findViewById(R.id.userregcode_et_code);
		userregcode_tv_getcode = (TextView) findViewById(R.id.userregcode_tv_getcode);

		click_btn = (Button) findViewById(R.id.click_btn);

	}

	@Override
	protected void setListener() {

		pagetop_tv_name.setText("验证手机号");
		click_btn.setText(getString(R.string.tv_next));
		pagetop_layout_back.setOnClickListener(this);
		click_btn.setOnClickListener(this);
		userregcode_tv_getcode.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	protected void processLogic() {

		Intent intent = getIntent();
		account = intent.getExtras().getString("account", "");
		password = intent.getExtras().getString("password", "");
		userregcode_tv_phone.setText(account);
	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				mDialog.show();
			}
			CommonUtils.getInstance().setOnkeyBackDialog(mDialog, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param mobile
	 */
	private void getCode() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this,
					getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("telephone", account);
		new UserRegistGetCodeBusiness(this, mhashmap,
				new GetRegistGetCodeCallback() {

					@Override
					public void afterDataGet(HashMap<String, Object> dataMap) {
						CommonUtils.getInstance().setOnDismissDialog(mDialog);
						if (dataMap != null) {
							String code = (String) dataMap.get("code");
							if (code.equals("200")) {
								Message msg = new Message();
								msg.what = Daojishistart;
								mHandler.sendMessage(msg);
								msg = null;
							} else {
								String msg = (String) dataMap.get("msg");
								CommonUtils.getInstance().initToast(context,
										msg);
							}
						} else {
							CommonUtils.getInstance().initToast(context,
									getString(R.string.net_tishi));
						}
						// 清除缓存
						CommonUtils.getInstance().setClearCacheBackDate(
								mhashmap, dataMap);
					}
				});
	}

	private int count = 60;
	private MyHandler mHandler = new MyHandler();
	private TimerTask timerTask;
	private final int Daojishistart = 0;
	private final int Daojishiover = 1;
	private final int REGIST = 2;

	private void daojishi() {
		final long nowTime = System.currentTimeMillis();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				count--;
				Message msg = new Message();
				msg.what = Daojishiover;
				msg.arg1 = count;
				msg.obj = nowTime;
				mHandler.sendMessage(msg);
				if (count <= 0) {
					this.cancel();
				}
				msg = null;
			}
		};
		new Timer().schedule(timerTask, 0, 1000);
	}

	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Daojishistart:
				userregcode_tv_getcode.setEnabled(false);
				count = 60;
				daojishi();
				break;
			case Daojishiover:
				if (msg.arg1 < 10) {
					userregcode_tv_getcode.setText("0" + msg.arg1
							+ getString(R.string.tv_codeunit));
				} else {
					userregcode_tv_getcode.setText(msg.arg1
							+ getString(R.string.tv_codeunit));
				}
				if (msg.arg1 <= 0) {
					userregcode_tv_getcode.setEnabled(true);
					userregcode_tv_getcode
							.setText(getString(R.string.tv_repeatgetcode));
				}
				break;
			case REGIST:
				regist();
				break;
			default:
				break;
			}
		}
	}

	private void checkCode(final String code) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this,
					getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("telephone", account);
		mhashmap.put("code", code);

		new UserRegistCheckCodeBusiness(this, mhashmap,
				new GetRegistCheckCodeCallback() {
					public void afterDataGet(HashMap<String, Object> dataMap) {
						CommonUtils.getInstance().setOnDismissDialog(mDialog);
						if (dataMap != null) {
							String code = (String) dataMap.get("code");
							if (code.equals("200")) {
								mHandler.sendEmptyMessage(REGIST);
							} else {
								String msg = (String) dataMap.get("msg");
								CommonUtils.getInstance().initToast(
										UserRegistCodeActivity.this, msg);
							}
						} else {
							CommonUtils.getInstance().initToast(
									UserRegistCodeActivity.this,
									getString(R.string.net_tishi));
						}
						// 清除缓存
						CommonUtils.getInstance().setClearCacheBackDate(
								mhashmap, dataMap);
					}
				});
	}

	private void regist() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		CommonUtils.getInstance().addHashMapToken(mhashmap);
		mhashmap.put("telephone", account);
		password = ConfigUtils.getInstance().MD5(password);
		mhashmap.put("password", password);
		mhashmap.put("regOrigin", "telephone");// 注册来源 0：未知
												// 1：login_name；2：telephone；3：email
												// 4：QQ；5：sinaweibo； 6：weixin'
		mhashmap.put("regType", "2");// 注册类型 0:未知 1 ：Web端；2： 移动端； 3： Wap端'
		new UserRegistBusiness(this, mhashmap, new GetRegistCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						UserLoginInfo loginInfo = (UserLoginInfo) dataMap
								.get("loginInfo");
						if (loginInfo != null) {
							mUserLoginSkipUtils.saveLoginInfo(loginInfo);
							Intent in = new Intent(context,
									UserRegistCommitActivity.class);
							in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							in.putExtra("uid", loginInfo.getUid());
							context.startActivity(in);
							CommonUtils.getInstance().setPageIntentAnim(in,
									context);
						}
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(
								UserRegistCodeActivity.this, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(
							UserRegistCodeActivity.this,
							getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
						dataMap);

			}
		});
	}

	/**
	 * 
	 * @param phone
	 * @param code
	 * @return
	 */
	private boolean isCheckNext(String code) {
		if (TextUtils.isEmpty(code)) {
			CommonUtils.getInstance().initToast(this,
					getString(R.string.tishi_code_null));
			return false;
		}
		if (code.length() != 4) {
			CommonUtils.getInstance().initToast(this,
					getString(R.string.tishi_code_style));
			return false;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		RequestUtils.cancelAll(this);
		super.onDestroy();
	}

	private void nowFinish() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			nowFinish();
		}
		return false;
	}

}
