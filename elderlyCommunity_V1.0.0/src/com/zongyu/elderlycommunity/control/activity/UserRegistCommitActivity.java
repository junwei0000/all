package com.zongyu.elderlycommunity.control.activity;

import java.util.HashMap;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.business.UserLoginBusiness;
import com.zongyu.elderlycommunity.business.UserRegistCheckCodeBusiness;
import com.zongyu.elderlycommunity.business.UserLoginBusiness.GetLoginCallback;
import com.zongyu.elderlycommunity.business.UserRegistCheckCodeBusiness.GetRegistCheckCodeCallback;
import com.zongyu.elderlycommunity.business.UserRegistSaveInfoBusiness;
import com.zongyu.elderlycommunity.business.UserRegistSaveInfoBusiness.GetRegistSaveInfoCallback;
import com.zongyu.elderlycommunity.model.UserLoginInfo;
import com.zongyu.elderlycommunity.utils.CommonUtils;
import com.zongyu.elderlycommunity.utils.ConfigUtils;
import com.zongyu.elderlycommunity.utils.Constans;
import com.zongyu.elderlycommunity.utils.SupplierEditText;
import com.zongyu.elderlycommunity.utils.volley.RequestUtils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
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
 * @Description 类描述：注册信息页面
 */
public class UserRegistCommitActivity extends BaseActivity {

	private SupplierEditText userreginfo_et_nickname;
	private Button click_btn;
	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView userreginfo_iv_thumb;
	private SupplierEditText userreginfo_et_sex;
	private SupplierEditText userreginfo_et_area;
	private String uid;
	private String sex;
	private String nick_name;
	private UserLoginSkipUtils mUserLoginSkipUtils;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.click_btn:
			String nickname = userreginfo_et_nickname.getText().toString();
			if (!TextUtils.isEmpty(nickname)) {
				CommonUtils.getInstance().closeSoftInput(
						UserRegistCommitActivity.this);
				saveInfo();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_regist_info);
		CommonUtils.getInstance().addActivity(this);
		mUserLoginSkipUtils=new UserLoginSkipUtils(context);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		userreginfo_iv_thumb = (ImageView) findViewById(R.id.userreginfo_iv_thumb);
		userreginfo_et_nickname = (SupplierEditText) findViewById(R.id.userreginfo_et_nickname);
		userreginfo_et_sex = (SupplierEditText) findViewById(R.id.userreginfo_et_sex);
		userreginfo_et_area = (SupplierEditText) findViewById(R.id.userreginfo_et_area);
		click_btn = (Button) findViewById(R.id.click_btn);

	}

	@Override
	protected void setListener() {

		pagetop_tv_name.setText("填写资料");
		click_btn.setText(getString(R.string.tv_reg));
		pagetop_layout_back.setOnClickListener(this);
		click_btn.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	protected void processLogic() {

		Intent intent = getIntent();
		uid = intent.getExtras().getString("uid", "");
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

	private void saveInfo() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this,
					getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		//性别 0：未知 1：男 2：女
		mhashmap.put("sex", sex);
		mhashmap.put("nick_name", nick_name);
		new UserRegistSaveInfoBusiness(this, mhashmap, new GetRegistSaveInfoCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						UserLoginInfo loginInfo = (UserLoginInfo) dataMap
								.get("loginInfo");
						mUserLoginSkipUtils.cacheLoginInfo(loginInfo);
						CommonUtils.getInstance().exitPayPage();
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(
								context, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(context,
							getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
						dataMap);
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
	 * 退出时跳转下一个页面
	 */
	private void nowFinish() {
		CommonUtils.getInstance().exitPayPage();
		mUserLoginSkipUtils.skipToPage();
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
