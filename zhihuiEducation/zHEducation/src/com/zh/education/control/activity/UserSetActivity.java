package com.zh.education.control.activity;

import com.zh.education.R;
import com.zh.education.utils.CommonUtils;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-13 下午2:47:25
 * @Description 类描述：设置
 */
public class UserSetActivity extends BaseActivity {

	private LinearLayout top_layout_back, top_head_layout;
	private TextView top_tv_name;
	private RelativeLayout userset_relay_setsize;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_layout_back:
			onBackPressed();
			break;
		case R.id.userset_relay_setsize:
			Intent intent_center = new Intent(getApplicationContext(),
					UserSetSizeActivity.class);
			intent_center.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent_center);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.userset);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		top_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		top_head_layout = (LinearLayout) findViewById(R.id.top_head_layout);
		top_tv_name = (TextView) findViewById(R.id.top_tv_name);
		userset_relay_setsize = (RelativeLayout) findViewById(R.id.userset_relay_setsize);
	}

	@Override
	protected void setListener() {
		top_layout_back.setOnClickListener(this);
		userset_relay_setsize.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		top_head_layout.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		top_tv_name.setText(getResources().getString(R.string.user_set));

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
