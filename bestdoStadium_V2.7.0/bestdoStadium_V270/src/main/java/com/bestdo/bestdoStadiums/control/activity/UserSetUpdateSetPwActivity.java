package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

import com.bestdo.bestdoStadiums.business.UserFindPWBusiness;
import com.bestdo.bestdoStadiums.business.UserRegistSetPWBusiness;
import com.bestdo.bestdoStadiums.business.UserSetUpdatePwBusiness;
import com.bestdo.bestdoStadiums.business.UserFindPWBusiness.GetUserFindPWCallback;
import com.bestdo.bestdoStadiums.business.UserRegistSetPWBusiness.GetUserRegistSetPWCallback;
import com.bestdo.bestdoStadiums.business.UserSetUpdatePwBusiness.GetUserSetUpdatePwCallback;
import com.bestdo.bestdoStadiums.model.UserLoginInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
 * @date 创建时间：2016-1-16 下午2:30:53
 * @Description 类描述：用户设置-修改密码设置密码
 */
public class UserSetUpdateSetPwActivity extends BaseActivity {
	private TextView pagetop_tv_name, user_regist_top_text;
	private LinearLayout pagetop_layout_back;
	private SupplierEditText userregistset_et_pw, userregistset_et_surepw;
	private Button click_blue_btn;
	private ImageView pagetop_iv_back_regist_setpw;
	private ProgressDialog mDialog;
	private String mobile = "", validCode = "", validId = "";

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_iv_back_regist_setpw:
			nowFinish();
			break;
		case R.id.click_blue_btn:
			String pw = userregistset_et_pw.getText().toString();
			String surepw = userregistset_et_surepw.getText().toString();
			if (isCheckInputSuccess(pw, surepw)) {
				CommonUtils.getInstance().closeSoftInput(this);
				toUpdate(surepw);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_regist_setpw);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		userregistset_et_pw = (SupplierEditText) findViewById(R.id.userregistset_et_pw);
		userregistset_et_surepw = (SupplierEditText) findViewById(R.id.userregistset_et_surepw);
		click_blue_btn = (Button) findViewById(R.id.click_blue_btn);
		user_regist_top_text = (TextView) findViewById(R.id.user_regist_top_text);
		pagetop_iv_back_regist_setpw = (ImageView) findViewById(R.id.pagetop_iv_back_regist_setpw);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		click_blue_btn.setOnClickListener(this);
		pagetop_iv_back_regist_setpw.setOnClickListener(this);
		userregistset_et_pw.addTextChangedListener(mTextWatcher);
		userregistset_et_surepw.addTextChangedListener(mTextWatcher);
	}

	TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			isClick(userregistset_et_pw.getText().toString(), userregistset_et_surepw.getText().toString());
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

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
		mobile = getIntent().getExtras().getString("account");
		validCode = getIntent().getExtras().getString("validCode");
		validId = getIntent().getExtras().getString("validId");

		click_blue_btn.setText(getResources().getString(R.string.userfindPw_ok));
		pagetop_tv_name.setText(getResources().getString(R.string.usercenter_setpw));
		user_regist_top_text.setText(getResources().getString(R.string.usercenter_setpw));
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

	private void toUpdate(String password) {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("telephone", mobile);
		mhashmap.put("validId", validId);
		mhashmap.put("validCode", validCode);
		mhashmap.put("newPassword", password);
		new UserFindPWBusiness(this, mhashmap, new GetUserFindPWCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						CommonUtils.getInstance().skipCenterActivity(context);
						CommonUtils.getInstance().initToast(context, "密码修改成功");
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

	@Override
	protected void onDestroy() {
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		RequestUtils.cancelAll(this);
		super.onDestroy();
	}

	/**
	 * 验证
	 * 
	 * @param password
	 * @param password2
	 * @return
	 */
	private boolean isCheckInputSuccess(String pw, String surepw) {
		if (TextUtils.isEmpty(pw) || TextUtils.isEmpty(pw)) {
			return false;
		}
		if (pw.length() < 6 || pw.length() > 16 || !ConfigUtils.getInstance().isPasswordNO(pw)) {
			CommonUtils.getInstance().initToast(UserSetUpdateSetPwActivity.this, getString(R.string.tishi_login_pw));
			return false;
		}
		if (surepw.length() < 6 || surepw.length() > 16 || !ConfigUtils.getInstance().isPasswordNO(surepw)) {
			CommonUtils.getInstance().initToast(UserSetUpdateSetPwActivity.this, getString(R.string.tishi_login_pw));
			return false;
		}
		if (!pw.equals(surepw)) {
			CommonUtils.getInstance().initToast(UserSetUpdateSetPwActivity.this,
					getString(R.string.tishi_regist_surepw));
			return false;
		}

		return true;
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
