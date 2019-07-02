package com.KiwiSports.control.activity;

import com.KiwiSports.R;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.SupplierEditText;

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
	private UpdateInfoUtils mUpdateInfoUtils;
	private String nick_name;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.pagetop_tv_you:
			String et_text = useraccountupdate_et_phone.getText().toString();
			if (!TextUtils.isEmpty(et_text)) {
				mUpdateInfoUtils = new UpdateInfoUtils(this);
				mUpdateInfoUtils.UpdateInfo("nick_name", et_text);
			} else {
				CommonUtils.getInstance().initToast(
						getString(R.string.user_account_etnick));
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

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		nick_name = getIntent().getStringExtra("nick_name");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText(getString(R.string.save));
		pagetop_tv_you.setTextColor(getResources().getColor(
				R.color.text_click_color));
		pagetop_tv_name.setText(getString(R.string.useraccount_nickname));
		useraccountupdate_et_phone.setText(nick_name);
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
