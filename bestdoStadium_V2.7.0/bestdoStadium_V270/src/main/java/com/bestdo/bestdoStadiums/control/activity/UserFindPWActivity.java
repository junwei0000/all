package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.bestdo.bestdoStadiums.business.UserFindPWBusiness;
import com.bestdo.bestdoStadiums.business.UserGetCodeBusiness;
import com.bestdo.bestdoStadiums.business.UserFindPWBusiness.GetUserFindPWCallback;
import com.bestdo.bestdoStadiums.business.UserGetCodeBusiness.GetGetCodeCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
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
 * @date 创建时间：2016-1-15 下午8:33:16
 * @Description 类描述：忘记密码
 */
public class UserFindPWActivity extends BaseActivity {
	private TextView pagetop_tv_name, userfindpw_tv_getcode;
	private ImageView pagetop_iv_back_log;
	private LinearLayout pagetop_layout_back;
	private SupplierEditText userfindpw_et_phone, userfindpw_et_code, userfindpw_et_pw;
	private Button click_blue_btn;

	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	private String validId = "";
	private int count;

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
		case R.id.userfindpw_tv_getcode:
			String phone = userfindpw_et_phone.getText().toString();
			if (isCheckGetCode(phone)) {
				findPWGetCode(phone);
			}
			break;
		case R.id.click_blue_btn:
			String phones = userfindpw_et_phone.getText().toString();
			String code = userfindpw_et_code.getText().toString();
			String pw = userfindpw_et_pw.getText().toString();
			if (isCheckLogin(phones, code, pw)) {
				CommonUtils.getInstance().closeSoftInput(this);
				findPW(phones, code, pw, validId);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 登录前验证
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	private boolean isCheckLogin(String phone, String code, String password) {
		if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) || TextUtils.isEmpty(password)) {
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
		if (password.length() < 6 || password.length() > 16 || !ConfigUtils.getInstance().isPasswordNO(password)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.tishi_login_pw));
			return false;
		}
		return true;
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
				userfindpw_tv_getcode.setEnabled(false);
				userfindpw_tv_getcode.setTextColor(getResources().getColor(R.color.text_noclick_color));
				count = 60;
				daojishi();
				break;
			case Daojishiover:
				if (msg.arg1 < 10) {
					userfindpw_tv_getcode.setText("0" + msg.arg1 + getString(R.string.tv_codeunit));
				} else {
					userfindpw_tv_getcode.setText(msg.arg1 + getString(R.string.tv_codeunit));
				}
				if (msg.arg1 <= 0) {
					userfindpw_tv_getcode.setTextColor(getResources().getColor(R.color.blue));
					userfindpw_tv_getcode.setEnabled(true);
					userfindpw_tv_getcode.setText(getString(R.string.tv_repeatgetcode));
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_findpw);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		userfindpw_et_phone = (SupplierEditText) findViewById(R.id.userfindpw_et_phone);
		userfindpw_et_code = (SupplierEditText) findViewById(R.id.userfindpw_et_code);
		userfindpw_et_pw = (SupplierEditText) findViewById(R.id.userfindpw_et_pw);
		userfindpw_tv_getcode = (TextView) findViewById(R.id.userfindpw_tv_getcode);
		pagetop_iv_back_log = (ImageView) findViewById(R.id.pagetop_iv_back_log);
		click_blue_btn = (Button) findViewById(R.id.click_blue_btn);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		userfindpw_tv_getcode.setOnClickListener(this);
		click_blue_btn.setOnClickListener(this);
		pagetop_iv_back_log.setOnClickListener(this);
		userfindpw_et_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (TextUtils.isEmpty(userfindpw_et_phone.getText())) {
					userfindpw_tv_getcode.setTextColor(getResources().getColor(R.color.text_noclick_color));
					userfindpw_tv_getcode.setEnabled(false);
				} else {
					userfindpw_tv_getcode.setTextColor(getResources().getColor(R.color.blue));
					userfindpw_tv_getcode.setEnabled(true);
				}
				isClick(userfindpw_et_phone.getText().toString(), userfindpw_et_code.getText().toString(),
						userfindpw_et_pw.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		userfindpw_et_code.addTextChangedListener(mTextWatcher);
		userfindpw_et_pw.addTextChangedListener(mTextWatcher);
	}

	@Override
	protected void processLogic() {
		pagetop_tv_name.setText(getResources().getString(R.string.userfindPw_title));
		click_blue_btn.setText(getResources().getString(R.string.userfindPw_ok));
		userfindpw_tv_getcode.setEnabled(false);
	}

	TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			isClick(userfindpw_et_phone.getText().toString(), userfindpw_et_code.getText().toString(),
					userfindpw_et_pw.getText().toString());
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

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

	/**
	 * 获取验证码
	 * 
	 * @param phone
	 */
	private void findPWGetCode(String phone) {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("account", phone);
		mhashmap.put("flag", "find");
		CommonUtils.getInstance().addToken(mhashmap, phone);
		String invokingType = Constans.getInstance().invokingTypeByFindphone;
		new UserGetCodeBusiness(this, mhashmap, invokingType, new GetGetCodeCallback() {
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
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	private void findPW(String phones, String code, String pw, String validId) {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("telephone", phones);
		mhashmap.put("validId", validId);
		mhashmap.put("validCode", code);
		mhashmap.put("newPassword", pw);
		new UserFindPWBusiness(this, mhashmap, new GetUserFindPWCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						Intent intent2 = new Intent(context, UserLoginActivity.class);
						intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent2);
						CommonUtils.getInstance().setPageIntentAnim(intent2, context);
						finish();
						CommonUtils.getInstance().initToast(context, "密码修改成功，请重新登录！");
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	/**
	 * 根据输入框内容判断按钮颜色
	 * 
	 * @param phone
	 * @param password
	 */
	private void isClick(String phone, String code, String password) {
		if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code) && !TextUtils.isEmpty(password)) {
			click_blue_btn.setTextColor(getResources().getColor(R.color.white));
		} else {
			click_blue_btn.setTextColor(getResources().getColor(R.color.btn_noclick_color));
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
