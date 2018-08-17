package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.UserSendIdearBusiness;
import com.bestdo.bestdoStadiums.business.UserSendIdearBusiness.GetUserSendIdearCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午5:38:05
 * @Description 类描述：意见反馈
 */
public class UserSendIdearActivity extends BaseActivity {

	private EditText user_sendidea_tel, editTextContext;
	private Button click_btn;
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;
	private ProgressDialog mDialog;
	private String tel;
	private String idea_content;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.click_btn:
			tel = user_sendidea_tel.getText().toString();
			idea_content = editTextContext.getText().toString();
			CommonUtils.getInstance().closeSoftInput(UserSendIdearActivity.this);
			if (!TextUtils.isEmpty(tel) || !TextUtils.isEmpty(idea_content)) {
				sendId();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_sendidears);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		user_sendidea_tel = (EditText) findViewById(R.id.user_sendidea_tel);
		editTextContext = (EditText) findViewById(R.id.user_sendidea_content);
		click_btn = (Button) findViewById(R.id.click_btn);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
	}

	@Override
	protected void setListener() {
		click_btn.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		editTextContext.addTextChangedListener(mTextWatcher);
		user_sendidea_tel.addTextChangedListener(mTextWatcher);
	}

	TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			tel = user_sendidea_tel.getText().toString();
			idea_content = editTextContext.getText().toString();
			isClick(tel, idea_content);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	@Override
	protected void processLogic() {
		pagetop_tv_name.setText(getResources().getString(R.string.usercenter_sendmsg));
		click_btn.setText(getResources().getString(R.string.tv_commit));
	}

	/**
	 * 根据输入框内容判断按钮颜色
	 * 
	 * @param phone
	 * @param password
	 */
	private void isClick(String tel, String idea_content) {
		if (TextUtils.isEmpty(tel) && TextUtils.isEmpty(idea_content)) {
			click_btn.setTextColor(getResources().getColor(R.color.btn_noclick_color));
		} else {
			click_btn.setTextColor(getResources().getColor(R.color.white));
		}
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

	private void sendId() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		mhashmap = new HashMap<String, String>();
		mhashmap.put("content", idea_content);
		mhashmap.put("tel", tel);
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		new UserSendIdearBusiness(this, mhashmap, new GetUserSendIdearCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					String data = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(context, data);
					if (status.equals("200")) {
						user_sendidea_tel.setText("");
						editTextContext.setText("");
					} else if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					}
				} else {
					CommonUtils.getInstance().initToast(context, "提交成功！");
				}
				doBack();
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	/**
	 * 退出页面前执行操作
	 */
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
	};

	@Override
	protected void onDestroy() {

		RequestUtils.cancelAll(this);
		super.onDestroy();
	}
}
