package com.zh.education.control.activity;

import com.zh.education.R;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.ConfigUtils;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午4:26:41
 * @Description 类描述：关于
 */
public class UserAboutActiyity extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;
	private TextView userabout_tv_versionName;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.top_layout_back:
			onBackPressed();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {

		setContentView(R.layout.user_about);
		CommonUtils.getInstance().addActivity(UserAboutActiyity.this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.top_tv_name);
		userabout_tv_versionName = (TextView) findViewById(R.id.userabout_tv_versionName);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		pagetop_tv_name.setText(getResources().getString(R.string.user_about));
		String verName = ConfigUtils.getInstance().getVerName(context);
		userabout_tv_versionName.setText(verName);
	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}
}