package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.UserAccountUpdateBusiness;
import com.bestdo.bestdoStadiums.business.UserAccountUpdateBusiness.GetAccountUpdateCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
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
public class UserAccountUpdateActivity extends BaseActivity {
	private TextView pagetop_tv_name, pagetop_tv_you;
	private LinearLayout pagetop_layout_back;
	private SupplierEditText useraccountupdate_et_phone;

	private String uid, updatetypekey, value;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.pagetop_tv_you:
			String et_text = useraccountupdate_et_phone.getText().toString();
			if (checkCommit(et_text)) {
				if (updatetypekey.equals(Constans.getInstance().UPDATE_LOGINNAME)) {
					showDialogProcessType(et_text);
				} else {
					processUpdateInfo(et_text, updatetypekey);
				}
			}
			break;
		default:
			break;
		}
	}

	public void showDialogProcessType(final String et_text) {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_pylwmangetishi);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView stadiumshishi_tishi = (TextView) selectDialog.findViewById(R.id.myexit_text_title);
		TextView myexit_text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);
		TextView myexit_text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);
		stadiumshishi_tishi.setText("用户名只可修改一次是否立即修改?");
		myexit_text_off.setText("再想想");
		myexit_text_sure.setText("确认");
		myexit_text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		myexit_text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				processUpdateInfo(et_text, updatetypekey);
			}
		});
	}

	private boolean checkCommit(String et_text) {
		boolean status = true;
		if (TextUtils.isEmpty(et_text)) {
			et_text = "";
		}
		if (updatetypekey.equals(Constans.getInstance().UPDATE_LOGINNAME)) {
			if (!TextUtils.isEmpty(et_text)) {
				boolean drt = ConfigUtils.getInstance().isLoginName(et_text);
				if (et_text.length() < 3) {
					CommonUtils.getInstance().initToast(context, "输入用户名的位数不能小于3位！");
					status = false;
				} else if (!drt) {
					CommonUtils.getInstance().initToast(context, "用户名格式不正确！");
					status = false;
				}
			}
		}
		return status;
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

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		useraccountupdate_et_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (updatetypekey.equals(Constans.getInstance().UPDATE_LOGINNAME)) {
					String editable = useraccountupdate_et_phone.getText().toString();
					String str = ConfigUtils.getInstance().stringFilter(editable);
					while (!TextUtils.isEmpty(str) && !ConfigUtils.getInstance().isLoginName(str)) {
						str = str.substring(1);
					}
					if (!editable.equals(str)) {
						useraccountupdate_et_phone.setText(str);
						// 设置新的光标所在位置
						useraccountupdate_et_phone.setSelection(str.length());
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void processLogic() {
		uid = getIntent().getStringExtra("uid");
		updatetypekey = getIntent().getStringExtra("updatetypekey");
		value = getIntent().getStringExtra("value");
		String title = null;
		if (updatetypekey.equals(Constans.getInstance().UPDATE_LOGINNAME)) {
			beforeedString = value;
			title = getResources().getString(R.string.user_account_username);
			useraccountupdate_et_phone.setFilters(new InputFilter[] { new InputFilter.LengthFilter(29) });
			useraccountupdate_et_phone.setHint("请输入字母或数字，不能以“bd”开头");
		} else if (updatetypekey.equals(Constans.getInstance().UPDATE_NICKNAME)) {
			title = getResources().getString(R.string.user_account_nickname);
			useraccountupdate_et_phone.setHint("请输入" + title);
		} else if (updatetypekey.equals(Constans.getInstance().UPDATE_REALNAME)) {
			title = getResources().getString(R.string.user_account_name);
			useraccountupdate_et_phone.setHint("请输入" + title);
		}
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText("保存");
		pagetop_tv_you.setTextColor(getResources().getColor(R.color.text_click_color));
		pagetop_tv_name.setText(title);
		useraccountupdate_et_phone.setText(value);
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

	private void processUpdateInfo(final String et_text, String update_type) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put(updatetypekey, et_text);
		System.err.println(mhashmap);
		new UserAccountUpdateBusiness(this, updatetypekey, mhashmap, new GetAccountUpdateCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						boolean result = (Boolean) dataMap.get("updateResult");
						if (result) {
							SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance()
									.getBestDoInfoSharedPrefs(context);
							Editor bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
							bestDoInfoEditor.putString(updatetypekey, et_text);
							bestDoInfoEditor.putString("nameChangeTimes", "1");
							bestDoInfoEditor.commit();
							doBack();
						}
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
