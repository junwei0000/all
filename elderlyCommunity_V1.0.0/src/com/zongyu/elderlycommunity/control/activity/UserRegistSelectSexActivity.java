package com.zongyu.elderlycommunity.control.activity;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.utils.CommonUtils;
import com.zongyu.elderlycommunity.utils.Constans;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午2:32:14
 * @Description 类描述：用户注册-选择性别
 */
public class UserRegistSelectSexActivity extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;
	private String sex;
	private ImageView userrreginfo_sex_iv_male;
	private TextView userrreginfo_sex_tv_famale;
	private ImageView userrreginfo_sex_iv_famale;
	private TextView userreginfo_sex_tv_male;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.userreginfo_sex_tv_male:
			sex = Constans.getInstance().SEX_MALE;
			changeCheck();
			break;
		case R.id.userrreginfo_sex_tv_famale:
			sex = Constans.getInstance().SEX_FAMALE;
			changeCheck();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_regist_info_sex);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		userreginfo_sex_tv_male = (TextView) findViewById(R.id.userreginfo_sex_tv_male);
		userrreginfo_sex_iv_male = (ImageView) findViewById(R.id.userrreginfo_sex_iv_male);
		userrreginfo_sex_tv_famale = (TextView) findViewById(R.id.userrreginfo_sex_tv_famale);
		userrreginfo_sex_iv_famale = (ImageView) findViewById(R.id.userrreginfo_sex_iv_famale);

	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		userreginfo_sex_tv_male.setOnClickListener(this);
		userrreginfo_sex_tv_famale.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		sex = getIntent().getStringExtra("sex");
		pagetop_tv_name.setText("选择性别");
		changeCheck();
	}

	private void changeCheck() {
		if (sex.equals(Constans.getInstance().SEX_UNKNOW)
				|| sex.equals(Constans.getInstance().SEX_MALE)) {
			userrreginfo_sex_iv_male.setVisibility(View.VISIBLE);
			userrreginfo_sex_iv_famale.setVisibility(View.GONE);
		} else {
			userrreginfo_sex_iv_male.setVisibility(View.GONE);
			userrreginfo_sex_iv_famale.setVisibility(View.VISIBLE);
		}
		Intent intent = getIntent();
		intent.putExtra("sex", sex + "");
		setResult(1, intent);
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
