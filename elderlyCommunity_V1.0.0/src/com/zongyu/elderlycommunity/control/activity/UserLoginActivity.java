package com.zongyu.elderlycommunity.control.activity;

import java.util.HashMap;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.business.UserLoginBusiness;
import com.zongyu.elderlycommunity.business.UserLoginBusiness.GetLoginCallback;
import com.zongyu.elderlycommunity.model.UserLoginInfo;
import com.zongyu.elderlycommunity.utils.CircleImageView;
import com.zongyu.elderlycommunity.utils.CommonUtils;
import com.zongyu.elderlycommunity.utils.ConfigUtils;
import com.zongyu.elderlycommunity.utils.Constans;
import com.zongyu.elderlycommunity.utils.HaveThIconClearEditText;
import com.zongyu.elderlycommunity.utils.ImageLoader;
import com.zongyu.elderlycommunity.utils.SupplierEditText;
import com.zongyu.elderlycommunity.utils.volley.RequestUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
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
 * @Description 类描述：登录页面
 */
public class UserLoginActivity extends BaseActivity {

	private HaveThIconClearEditText userlogin_et_phone, userlogin_et_pw;
	private Button click_btn;
	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	private TextView userlogin_tv_regist;
	private TextView userlogin_tv_findpw;
	private ImageLoader asyncImageLoader;
	private UserLoginSkipUtils mUserLoginSkipUtils;
	private CircleImageView usereg_iv_head;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.userlogin_tv_regist:
			Intent in = new Intent(context, UserRegistActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(in);
			CommonUtils.getInstance().setPageIntentAnim(in, context);
			break;
		case R.id.userlogin_tv_findpw:

			break;
		case R.id.click_btn:

			String user_name = userlogin_et_phone.getText().toString();
			String password = userlogin_et_pw.getText().toString();
			if (isCheckLogin(user_name, password)) {
				loginProcessLogic(user_name, password);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_login);
		CommonUtils.getInstance().addActivity(this);
		CommonUtils.getInstance().addPayPageActivity(this);

	}

	@Override
	protected void findViewById() {

		usereg_iv_head = (CircleImageView) findViewById(R.id.usereg_iv_head);

		userlogin_tv_regist = (TextView) findViewById(R.id.userlogin_tv_regist);
		userlogin_et_phone = (HaveThIconClearEditText) findViewById(R.id.userlogin_et_phone);
		userlogin_et_pw = (HaveThIconClearEditText) findViewById(R.id.userlogin_et_pw);
		click_btn = (Button) findViewById(R.id.click_btn);
		userlogin_tv_findpw = (TextView) findViewById(R.id.userlogin_tv_findpw);
	}

	@Override
	protected void setListener() {
		click_btn.setText(getString(R.string.tv_login));
		userlogin_tv_regist.setOnClickListener(this);
		click_btn.setOnClickListener(this);
		userlogin_tv_findpw.setOnClickListener(this);
		mUserLoginSkipUtils = new UserLoginSkipUtils(this);
	}

	@Override
	protected void processLogic() {
		asyncImageLoader = new ImageLoader(context, "headImg");
		Bitmap mBitmap = asyncImageLoader.readBitMap(this,
				R.drawable.userlogin_thumb_bg);
		usereg_iv_head.setImageBitmap(mBitmap);
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

	private void loginProcessLogic(final String user_name, final String password) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this,
					getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		CommonUtils.getInstance().addHashMapToken(mhashmap);
		mhashmap.put("account", user_name);
		String password_ = ConfigUtils.getInstance().MD5(password);
		mhashmap.put("password", password_);

		new UserLoginBusiness(this, mhashmap, new GetLoginCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						UserLoginInfo loginInfo = (UserLoginInfo) dataMap
								.get("loginInfo");
						mUserLoginSkipUtils.cacheLoginInfo(loginInfo);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(
								UserLoginActivity.this, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(UserLoginActivity.this,
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
			CommonUtils.getInstance().initToast(UserLoginActivity.this,
					"请输入手机号或密码");
			return false;
		}
		if (!ConfigUtils.getInstance().isMobileNO(phone)) {
			CommonUtils.getInstance().initToast(UserLoginActivity.this,
					getString(R.string.tishi_login_phone));
			return false;
		}
		if (password.length() < 6 || password.length() > 16
				|| !ConfigUtils.getInstance().isPasswordNO(password)) {
			CommonUtils.getInstance().initToast(UserLoginActivity.this,
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
		mUserLoginSkipUtils.doBackCheck();
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
