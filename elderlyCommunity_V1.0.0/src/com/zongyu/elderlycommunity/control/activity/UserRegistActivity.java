package com.zongyu.elderlycommunity.control.activity;

import java.util.HashMap;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.business.UserLoginBusiness;
import com.zongyu.elderlycommunity.business.UserLoginBusiness.GetLoginCallback;
import com.zongyu.elderlycommunity.business.UserRegistCheckPhoneBusiness;
import com.zongyu.elderlycommunity.business.UserRegistCheckPhoneBusiness.GetRegistCheckPhoneCallback;
import com.zongyu.elderlycommunity.model.UserLoginInfo;
import com.zongyu.elderlycommunity.utils.CommonUtils;
import com.zongyu.elderlycommunity.utils.ConfigUtils;
import com.zongyu.elderlycommunity.utils.Constans;
import com.zongyu.elderlycommunity.utils.SupplierEditText;
import com.zongyu.elderlycommunity.utils.volley.RequestUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
 * @Description 类描述：注册页面
 */
public class UserRegistActivity extends BaseActivity {

	private SupplierEditText userreg_et_phone, userreg_et_pw;
	private Button click_btn;
	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView pagetop_tv_you;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.click_btn:
			String user_name = userreg_et_phone.getText().toString();
			String password = userreg_et_pw.getText().toString();
			if (isCheckLogin(user_name, password)) {
				checkPhone(user_name, password);
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
		CommonUtils.getInstance().addPayPageActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		userreg_et_phone = (SupplierEditText) findViewById(R.id.userreg_et_phone);
		userreg_et_pw = (SupplierEditText) findViewById(R.id.userreg_et_pw);
		click_btn = (Button) findViewById(R.id.click_btn);
	}

	@Override
	protected void setListener() {

		pagetop_tv_name.setText(getString(R.string.tv_reg));
		pagetop_tv_you.setVisibility(View.GONE);
		click_btn.setText(getString(R.string.tv_next));
		pagetop_layout_back.setOnClickListener(this);
		click_btn.setOnClickListener(this);
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
			CommonUtils.getInstance().setOnkeyBackDialog(mDialog, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkPhone(final String user_name, final String password) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this,
					getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("account", user_name);

		new UserRegistCheckPhoneBusiness(this, mhashmap, new GetRegistCheckPhoneCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						String data = (String) dataMap
								.get("data");
						////"data":"1" ：1可用 0：不可用
						 if(!TextUtils.isEmpty(data)&&data.equals("1")){
							 Intent in = new Intent(context, UserRegistCodeActivity.class);
								in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								in.putExtra("account", user_name);
								in.putExtra("password", password);
								context.startActivity(in);
								CommonUtils.getInstance().setPageIntentAnim(in, context);
						 }else{
							 CommonUtils.getInstance().initToast(
										UserRegistActivity.this, "用户已存在");
						 }
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(
								UserRegistActivity.this, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(UserRegistActivity.this,
							getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
						dataMap);
			}
		});
	}

	/**
	 * 登录前验证
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	private boolean isCheckLogin(String phone, String password) {
		if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
			CommonUtils.getInstance().initToast(UserRegistActivity.this,
					"请输入手机号或密码");
			return false;
		}
		if (!ConfigUtils.getInstance().isMobileNO(phone)) {
			CommonUtils.getInstance().initToast(UserRegistActivity.this,
					getString(R.string.tishi_login_phone));
			return false;
		}
		if (password.length() < 6 || password.length() > 16
				|| !ConfigUtils.getInstance().isPasswordNO(password)) {
			CommonUtils.getInstance().initToast(UserRegistActivity.this,
					getString(R.string.tishi_login_pw));
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
