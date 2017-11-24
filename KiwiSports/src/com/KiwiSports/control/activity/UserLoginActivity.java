package com.KiwiSports.control.activity;

import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.SupplierEditText;
import com.KiwiSports.utils.volley.RequestUtils;

import android.app.ProgressDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：登录页面
 */
public class UserLoginActivity extends BaseActivity {
	private TextView pagetop_tv_name, pagetop_tv_you;
	private LinearLayout pagetop_layout_you, pagetop_layout_back;
	private TextView userlogin_tv_fastlogin;
	private TextView userlogin_fastlogin_bottomline;
	private TextView userlogin_tv_commonlogin;
	private TextView userlogin_commonlogin_bottomline;

	private TextView userlogin_tv_code;
	private TextView userlogin_tv_getcode;
	private LinearLayout userlogin_layout_getcode;

	private SupplierEditText userlogin_et_phone, userlogin_et_pw;
	private Button click_btn;
	private TextView findPassword;
	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;

	// UserLoginSkipUtils mUserLoginSkipUtils;
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
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setTextColor(getResources().getColor(
				R.color.text_contents_color));
		pagetop_layout_you.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		userlogin_tv_fastlogin.setOnClickListener(this);
		userlogin_tv_commonlogin.setOnClickListener(this);
		userlogin_tv_getcode.setOnClickListener(this);
		findPassword.setOnClickListener(this);
		click_btn.setOnClickListener(this);

		userlogin_et_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isEmpty(userlogin_et_phone.getText())) {
					userlogin_et_pw.setText("");
					userlogin_tv_getcode.setTextColor(getResources().getColor(
							R.color.text_noclick_color));
					userlogin_tv_getcode.setEnabled(false);
				} else {
					userlogin_tv_getcode.setTextColor(getResources().getColor(
							R.color.blue));
					userlogin_tv_getcode.setEnabled(true);
				}
				isClick(userlogin_et_phone.getText().toString(),
						userlogin_et_pw.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		userlogin_et_pw.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isClick(userlogin_et_phone.getText().toString(),
						userlogin_et_pw.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub

	}

	/**
	 * 根据输入框内容判断按钮颜色
	 * 
	 * @param phone
	 * @param password
	 */
	private void isClick(String phone, String password) {
		if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
			click_btn.setTextColor(getResources().getColor(R.color.white));
		} else {
			click_btn.setTextColor(getResources().getColor(
					R.color.btn_noclick_color));
		}
	}

	/**
	 * 获取验证码前判断手机号
	 * 
	 * @param phone
	 * @return
	 */
	private boolean isCheckGetCode(String phone) {
		if (TextUtils.isEmpty(phone)
				|| !ConfigUtils.getInstance().isMobileNO(phone)) {
			// CommonUtils.getInstance().initToast(UserLoginActivity.this,
			// getString(R.string.tishi_login_phone));
			return false;
		}
		return true;
	}

	/**
	 * 登录前验证
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	// private boolean isCheckLogin(String phone, String password) {
	// if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
	// return false;
	// }
	// if (!ConfigUtils.getInstance().isMobileNO(phone)) {
	// CommonUtils.getInstance().initToast(UserLoginActivity.this,
	// getString(R.string.tishi_login_phone));
	// return false;
	// }
	// if (fastLoginStatus) {
	// if (password.length() != 6) {
	// CommonUtils.getInstance().initToast(UserLoginActivity.this,
	// getString(R.string.tishi_login_code));
	// return false;
	// }
	// } else {
	// if (password.length() < 6 || password.length() > 16
	// || !ConfigUtils.getInstance().isPasswordNO(password)) {
	// CommonUtils.getInstance().initToast(UserLoginActivity.this,
	// getString(R.string.tishi_login_pw));
	// return false;
	// }
	// }
	// return true;
	// }

	@Override
	protected void onDestroy() {
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		RequestUtils.cancelAll(this);
		super.onDestroy();
	}

	private void nowFinish() {
		// mUserLoginSkipUtils.doBackCheck();
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
