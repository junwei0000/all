package com.KiwiSports.control.activity;

import com.KiwiSports.R;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

	private String sex;
	private UpdateInfoUtils mUpdateInfoUtils;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.useraccount_sex_tv_male:
			sex = Constants.getInstance().SEX_MALE;
			changeCheck();
			mUpdateInfoUtils.UpdateInfo("sex", String.valueOf(sex));
			break;
		case R.id.useraccount_sex_tv_famale:
			sex = Constants.getInstance().SEX_FAMALE;
			changeCheck();
			mUpdateInfoUtils.UpdateInfo("sex", String.valueOf(sex));
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
		mUpdateInfoUtils = new UpdateInfoUtils(this);
		sex = getIntent().getStringExtra("sex");
		pagetop_tv_name.setText(getString(R.string.useraccount_sex));
		changeCheck();
	}

	private void changeCheck() {
		if (sex.equals(Constants.getInstance().SEX_MALE) ) {
			useraccount_sex_iv_male.setVisibility(View.VISIBLE);
			useraccount_sex_iv_famale.setVisibility(View.GONE);
		} else {
			useraccount_sex_iv_male.setVisibility(View.GONE);
			useraccount_sex_iv_famale.setVisibility(View.VISIBLE);
		}
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
