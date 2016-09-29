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
import com.zongyu.elderlycommunity.utils.HaveThIconClearEditText;
import com.zongyu.elderlycommunity.utils.SupplierEditText;
import com.zongyu.elderlycommunity.utils.volley.RequestUtils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Message;
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

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView usreginfo_iv_head;
	private HaveThIconClearEditText userreginfo_et_nickname;
	private LinearLayout userreginfo_layout_sex;
	private TextView userreginfo_tv_sex;
	private LinearLayout userreginfo_layout_area;
	private TextView userreginfo_tv_area;
	private Button click_btn;
	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	private String uid;
	private String sex;
	private String nick_name;
	private UserLoginSkipUtils mUserLoginSkipUtils;
	private String nickname;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.usreginfo_iv_head:

			break;
		case R.id.userreginfo_layout_sex:
			Intent intent = new Intent(this, UserRegistSelectSexActivity.class);
			intent.putExtra("sex", "" + sex);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivityForResult(intent, 1);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		case R.id.userreginfo_layout_area:

			break;
		case R.id.click_btn:

			if (check()) {
				CommonUtils.getInstance().closeSoftInput(
						UserRegistCommitActivity.this);
				saveInfo();
			}
			break;
		default:
			break;
		}
	}

	private boolean check() {
		nickname = userreginfo_et_nickname.getText().toString();
		boolean status = true;
		if (TextUtils.isEmpty(nickname)) {
			status = false;
			CommonUtils.getInstance().initToast(context,
					getString(R.string.tv_reginfo_nacknamehint));
		}
		return status;
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_regist_info);
		CommonUtils.getInstance().addActivity(this);
		mUserLoginSkipUtils = new UserLoginSkipUtils(context);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		usreginfo_iv_head = (ImageView) findViewById(R.id.usreginfo_iv_head);
		userreginfo_et_nickname = (HaveThIconClearEditText) findViewById(R.id.userreginfo_et_nickname);

		userreginfo_layout_sex = (LinearLayout) findViewById(R.id.userreginfo_layout_sex);
		userreginfo_tv_sex = (TextView) findViewById(R.id.userreginfo_tv_sex);
		userreginfo_layout_area = (LinearLayout) findViewById(R.id.userreginfo_layout_area);
		userreginfo_tv_area = (TextView) findViewById(R.id.userreginfo_tv_area);
		click_btn = (Button) findViewById(R.id.click_btn);

	}

	@Override
	protected void setListener() {

		pagetop_tv_name.setText("填写资料");
		click_btn.setText(getString(R.string.tv_reg));
		pagetop_layout_back.setOnClickListener(this);
		usreginfo_iv_head.setOnClickListener(this);
		userreginfo_layout_sex.setOnClickListener(this);
		userreginfo_layout_area.setOnClickListener(this);
		click_btn.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	protected void processLogic() {
		Intent intent = getIntent();
		uid = intent.getExtras().getString("uid", "");
		sex = Constans.getInstance().SEX_UNKNOW;
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
		mhashmap.put("nick_name", nick_name);
		// 性别 0：未知 1：男 2：女
		mhashmap.put("sex", "" + sex);
		new UserRegistSaveInfoBusiness(this, mhashmap,
				new GetRegistSaveInfoCallback() {
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (resultCode == 1) {
				sex = data.getStringExtra("sex");
				if (sex.equals(Constans.getInstance().SEX_UNKNOW)) {

				} else if (sex.equals(Constans.getInstance().SEX_MALE)) {
					userreginfo_tv_sex.setText("男");
				} else if (sex.equals(Constans.getInstance().SEX_FAMALE)) {
					userreginfo_tv_sex.setText("女");
				}
			}
		} catch (Exception e) {
		}
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
		if (check()) {
			CommonUtils.getInstance().exitPayPage();
			mUserLoginSkipUtils.skipToPage();
		}
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
