package com.KiwiSports.control.activity;

import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.UserAccountUpdateBusiness;
import com.KiwiSports.business.UserAccountUpdateBusiness.GetAccountUpdateCallback;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constans;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午2:32:14
 * @Description 类描述：我的账户信息修改
 */
public class UserAccountUpdateSexActivity extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;
	private TextView useraccount_sex_tv_male;
	private ImageView useraccount_sex_iv_male;
	private TextView useraccount_sex_tv_famale;
	private ImageView useraccount_sex_iv_famale;

	private String uid;
	private int sex;
	private String token;
	private String access_token;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.useraccount_sex_tv_male:
			sex = Constans.getInstance().SEX_MALE;
			changeCheck();
			processUpdateInfo();
			break;
		case R.id.useraccount_sex_tv_famale:
			sex = Constans.getInstance().SEX_FAMALE;
			changeCheck();
			processUpdateInfo();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_account_update_sex);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		useraccount_sex_tv_male = (TextView) findViewById(R.id.useraccount_sex_tv_male);
		useraccount_sex_iv_male = (ImageView) findViewById(R.id.useraccount_sex_iv_male);
		useraccount_sex_tv_famale = (TextView) findViewById(R.id.useraccount_sex_tv_famale);
		useraccount_sex_iv_famale = (ImageView) findViewById(R.id.useraccount_sex_iv_famale);

	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		useraccount_sex_tv_male.setOnClickListener(this);
		useraccount_sex_tv_famale.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		uid = getIntent().getStringExtra("uid");
		token = getIntent().getStringExtra("token");
		access_token = getIntent().getStringExtra("access_token");
		sex = getIntent().getIntExtra("sex", 1);
		pagetop_tv_name.setText("性别");
		changeCheck();
	}

	private void changeCheck() {
		if (sex==Constans.getInstance().SEX_MALE) {
			useraccount_sex_iv_male.setVisibility(View.VISIBLE);
			useraccount_sex_iv_famale.setVisibility(View.GONE);
		} else {
			useraccount_sex_iv_male.setVisibility(View.GONE);
			useraccount_sex_iv_famale.setVisibility(View.VISIBLE);
		}
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

	private void processUpdateInfo() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("sex", sex+"");
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
						bestDoInfoEditor.putInt("sex", sex);
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
