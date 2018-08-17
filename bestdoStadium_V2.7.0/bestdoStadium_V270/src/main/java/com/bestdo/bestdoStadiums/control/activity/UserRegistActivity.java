package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.bestdo.bestdoStadiums.business.UserGetCodeBusiness;
import com.bestdo.bestdoStadiums.business.UserRegistCheckCodeBusiness;
import com.bestdo.bestdoStadiums.business.UserGetCodeBusiness.GetGetCodeCallback;
import com.bestdo.bestdoStadiums.business.UserRegistCheckCodeBusiness.GetUserRegistCheckCodeCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-16 下午1:53:00
 * @Description 类描述：用户注册页面
 */

public class UserRegistActivity extends BaseActivity {
	private TextView pagetop_tv_name, userregist_tv_getcode;
	private LinearLayout pagetop_layout_back;
	private SupplierEditText userregist_et_phone, userregist_et_code;
	private Button click_blue_btn;
	private TextView userlogin_tv_reg_login;
	private ProgressDialog mDialog;
	private int count = 60;
	private String validId = "";
	private ImageView pagetop_iv_back_log;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			finish();
			CommonUtils.getInstance().setPageBackAnim(this);
			break;
		case R.id.pagetop_iv_back_log:
			finish();
			CommonUtils.getInstance().setPageBackAnim(this);
			break;

		case R.id.userlogin_tv_reg_login:
			finish();
			CommonUtils.getInstance().setPageBackAnim(this);
			break;
		case R.id.userregist_tv_getcode:
			String phone = userregist_et_phone.getText().toString();
			if (isCheckGetCode(phone)) {
				getCode(phone);
			}
			break;
		case R.id.click_blue_btn:
			String mobiles = userregist_et_phone.getText().toString();
			String code = userregist_et_code.getText().toString();
			if (isCheckNext(mobiles, code)) {
				CommonUtils.getInstance().closeSoftInput(UserRegistActivity.this);
				checkCode(mobiles, code);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_regist);
		CommonUtils.getInstance().addActivity(this);
		CommonUtils.getInstance().addPayPageActivity(this);// 为了自动注册后登录，在UserRegistSetPwActivity页面关闭
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_iv_back_log = (ImageView) findViewById(R.id.pagetop_iv_back_log);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		userregist_et_phone = (SupplierEditText) findViewById(R.id.userregist_et_phone);
		userregist_et_code = (SupplierEditText) findViewById(R.id.userregist_et_code);
		userregist_tv_getcode = (TextView) findViewById(R.id.userregist_tv_getcode);
		userlogin_tv_reg_login = (TextView) findViewById(R.id.userlogin_tv_reg_login);
		click_blue_btn = (Button) findViewById(R.id.click_blue_btn);
	}

	@Override
	protected void setListener() {
		click_blue_btn.setText("下一步");
		pagetop_tv_name.setText(getResources().getString(R.string.tv_reg2));
		pagetop_layout_back.setOnClickListener(this);
		pagetop_iv_back_log.setOnClickListener(this);
		userregist_tv_getcode.setOnClickListener(this);
		userlogin_tv_reg_login.setOnClickListener(this);
		click_blue_btn.setOnClickListener(this);
		userregist_et_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (TextUtils.isEmpty(userregist_et_phone.getText())) {
					userregist_tv_getcode.setTextColor(getResources().getColor(R.color.text_noclick_color));
					userregist_tv_getcode.setEnabled(false);
				} else {
					userregist_tv_getcode.setTextColor(getResources().getColor(R.color.blue));
					userregist_tv_getcode.setEnabled(true);
				}
				isClick(userregist_et_phone.getText().toString(), userregist_et_code.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		userregist_et_code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				isClick(userregist_et_phone.getText().toString(), userregist_et_code.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	/**
	 * 根据输入框内容判断按钮颜色
	 * 
	 * @param phone
	 * @param password
	 */
	private void isClick(String phone, String code) {
		if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code)) {
			click_blue_btn.setTextColor(getResources().getColor(R.color.white));
		} else {
			click_blue_btn.setTextColor(getResources().getColor(R.color.btn_noclick_color));
		}
	}

	@Override
	protected void processLogic() {

	}

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

	HashMap<String, String> mhashmap;

	private void checkCode(final String mobile, final String code) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("account", mobile);
		mhashmap.put("validCode", code);
		mhashmap.put("validId", validId);
		new UserRegistCheckCodeBusiness(this, mhashmap, new GetUserRegistCheckCodeCallback() {

			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						Intent intent2 = new Intent(context, UserRegistSetPwActivity.class);
						intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent2.putExtra("account", mobile);
						intent2.putExtra("validCode", code);
						intent2.putExtra("validId", validId);
						startActivity(intent2);
						CommonUtils.getInstance().setPageIntentAnim(intent2, context);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(UserRegistActivity.this, getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	/**
	 * 
	 * @param mobile
	 */
	private void getCode(String mobile) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("account", mobile);
		CommonUtils.getInstance().addToken(mhashmap, mobile);
		new UserGetCodeBusiness(this, mhashmap, Constans.getInstance().invokingTypeByRegist, new GetGetCodeCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						validId = (String) dataMap.get("validId");
						Message msg = new Message();
						msg.what = Daojishistart;
						mHandler.sendMessage(msg);
						msg = null;
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(UserRegistActivity.this, getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	private MyHandler mHandler = new MyHandler();
	private TimerTask timerTask;
	private final int Daojishistart = 0;
	private final int Daojishiover = 1;

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
				userregist_tv_getcode.setEnabled(false);
				userregist_tv_getcode.setTextColor(getResources().getColor(R.color.text_noclick_color));
				count = 60;
				daojishi();
				break;
			case Daojishiover:
				if (msg.arg1 < 10) {
					userregist_tv_getcode.setText("0" + msg.arg1 + getString(R.string.tv_codeunit));
				} else {
					userregist_tv_getcode.setText(msg.arg1 + getString(R.string.tv_codeunit));
				}
				if (msg.arg1 <= 0) {
					userregist_tv_getcode.setTextColor(getResources().getColor(R.color.blue));
					userregist_tv_getcode.setEnabled(true);
					userregist_tv_getcode.setText(getString(R.string.tv_repeatgetcode));
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 获取验证码前判断手机号
	 * 
	 * @param phone
	 * @return
	 */
	private boolean isCheckGetCode(String phone) {
		if (TextUtils.isEmpty(phone) || !ConfigUtils.getInstance().isMobileNO(phone)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.tishi_login_phone));
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param phone
	 * @param code
	 * @return
	 */
	private boolean isCheckNext(String phone, String code) {
		if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code)) {
			return false;
		}
		if (!ConfigUtils.getInstance().isMobileNO(phone)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.tishi_login_phone));
			return false;
		}
		if (code.length() != 6) {
			CommonUtils.getInstance().initToast(this, getString(R.string.tishi_login_code));
			return false;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}

		RequestUtils.cancelAll(this);
		super.onDestroy();
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			CommonUtils.getInstance().setPageBackAnim(this);
		}
		return false;
	}
}
