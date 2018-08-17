package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.Dates_CorrentUtils;
import com.bestdo.bestdoStadiums.utils.TimeTextView;
import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 支付前超时提醒页面
 * 
 * @author qyy
 * 
 */
public class Success_outtime extends BaseActivity {
	private TextView pagetop_tv_name;
	private TimeTextView faliure_pay_tv_phone;
	private String orderstatus;
	private LinearLayout pagetop_layout_back;
	private LinearLayout success_faliure_text_left, success_faliure_text_right;
	private String oid;
	private Activity mActivity;
	private TextView success_pay_text;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.success_faliure_text_left:
			CommonUtils.getInstance().exitOrderDetailPage();
			Intent intent = new Intent(this, UserOrderDetailsActivity.class);
			intent.putExtra("oid", oid + "");// 订单号
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			finish();
			break;
		case R.id.success_faliure_text_right:
			CommonUtils.getInstance().skipMainActivity(this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.success_faliure);
		CommonUtils.getInstance().addActivity(this);
		mActivity = Success_outtime.this;
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		success_pay_text = (TextView) findViewById(R.id.success_pay_text);
		success_faliure_text_left = (LinearLayout) findViewById(R.id.success_faliure_text_left);
		success_faliure_text_right = (LinearLayout) findViewById(R.id.success_faliure_text_right);
		faliure_pay_tv_phone = (TimeTextView) findViewById(R.id.faliure_pay_tv_phone);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		success_faliure_text_left.setOnClickListener(this);
		success_faliure_text_right.setOnClickListener(this);
		initDate();
	}

	private void initDate() {
		Intent intent = getIntent();
		if (intent != null) {
			oid = intent.getStringExtra("oid");
			pagetop_tv_name.setText("支付超时");
			success_pay_text.setText("支付超时");
			faliure_pay_tv_phone.setText("该订单因超时未支付已被自动取消，不可进行支付！");
		}
	}

	@Override
	protected void processLogic() {

	}

	@Override
	protected void onDestroy() {
		faliure_pay_tv_phone.removeCallbacks();
		super.onDestroy();
	}

	private void nowFinish() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
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
