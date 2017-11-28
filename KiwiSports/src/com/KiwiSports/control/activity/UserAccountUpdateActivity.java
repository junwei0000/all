package com.KiwiSports.control.activity;

import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.UserAccountUpdateBusiness;
import com.KiwiSports.business.UserAccountUpdateBusiness.GetAccountUpdateCallback;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.SupplierEditText;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午2:32:14
 * @Description 类描述：我的账户信息修改
 */
public class UserAccountUpdateActivity extends BaseActivity {
	private TextView pagetop_tv_name, pagetop_tv_you;
	private LinearLayout pagetop_layout_back;
	private SupplierEditText useraccountupdate_et_phone;

	private String uid;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.pagetop_tv_you:
			String et_text = useraccountupdate_et_phone.getText().toString();
			if (!TextUtils.isEmpty(et_text)) {
				processUpdateInfo(et_text);
			} else {
				CommonUtils.getInstance().initToast(context, "请输入昵称");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_account_update);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		useraccountupdate_et_phone = (SupplierEditText) findViewById(R.id.useraccountupdate_et_phone);
	}

	String beforeedString;
	private String nick_name;
	private String token;
	private String access_token;

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		uid = getIntent().getStringExtra("uid");
		nick_name = getIntent().getStringExtra("nick_name");
		token = getIntent().getStringExtra("token");
		access_token = getIntent().getStringExtra("access_token");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText("保存");
		pagetop_tv_you.setTextColor(getResources().getColor(R.color.text_click_color));
		pagetop_tv_name.setText("昵称");
		useraccountupdate_et_phone.setText(nick_name);
	}

	private HashMap<String, String> mhashmap;
	private ProgressDialog mDialog;

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

	private void processUpdateInfo(final String et_text) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("nick_name", et_text);
		System.err.println(mhashmap);
		new UserAccountUpdateBusiness(this, "info",mhashmap, new GetAccountUpdateCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance()
								.getBestDoInfoSharedPrefs(context);
						Editor bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
						bestDoInfoEditor.putString("nick_name", et_text);
						bestDoInfoEditor.commit();
						doBack();
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

	private void doBack() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}
}
