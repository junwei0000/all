package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;

import android.content.Intent;
import android.content.SharedPreferences;
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
 * @date 创建时间：2016-1-20 下午7:39:48
 * @Description 类描述：设置
 */
public class UserSetActivity extends BaseActivity {
	private TextView pagetop_tv_name, userset_tv_cancel, userset_tv_tel;
	private LinearLayout pagetop_layout_back;
	private RelativeLayout userset_relay_pw, userset_relay_tel;
	private ImageView usercenter_iv_allorderarrow;
	private String mobile;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.userset_relay_tel:
			Intent intent2 = new Intent(this, UserSetUpdatePwActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent2.putExtra("intentStatus", "bindMobile");
			intent2.putExtra("mobile", mobile);
			startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, this);

			break;
		case R.id.userset_relay_pw:
			Intent intent = new Intent(this, UserSetUpdatePwActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

			intent.putExtra("mobile", mobile);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		case R.id.userset_tv_cancel:
			CommonUtils.getInstance().defineBackPressed(this, null, Constans.getInstance().cancel);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_set);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		userset_tv_cancel = (TextView) findViewById(R.id.userset_tv_cancel);
		userset_relay_pw = (RelativeLayout) findViewById(R.id.userset_relay_pw);
		userset_relay_tel = (RelativeLayout) findViewById(R.id.userset_relay_tel);
		userset_tv_tel = (TextView) findViewById(R.id.userset_tv_tel);
		usercenter_iv_allorderarrow = (ImageView) findViewById(R.id.usercenter_iv_allorderarrow);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		userset_tv_cancel.setOnClickListener(this);
		userset_relay_pw.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		pagetop_tv_name.setText(getResources().getString(R.string.usercenter_set));
		Intent intent = getIntent();
		mobile = intent.getStringExtra("mobile");
		String ThirdPartyLoginMode = intent.getStringExtra("ThirdPartyLoginMode");

		userset_tv_tel.setText(mobile);

		if (TextUtils.isEmpty(mobile)) {
			userset_tv_tel.setText("点击绑定");
			userset_relay_tel.setOnClickListener(this);
			userset_relay_pw.setVisibility(View.GONE);
		} else {
			usercenter_iv_allorderarrow.setVisibility(View.GONE);

		}
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
}
