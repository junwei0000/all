package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.Dates_CorrentUtils;
import com.bestdo.bestdoStadiums.utils.TimeTextView;
import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 支付失败页面
 * 
 * @author jun
 * 
 */
public class Success_Faliure extends BaseActivity {
	private TextView pagetop_tv_name;
	private TimeTextView faliure_pay_tv_phone;
	private String orderstatus;
	private LinearLayout pagetop_layout_back;
	private LinearLayout success_faliure_text_left, success_faliure_text_right;
	private String oid;
	private Activity mActivity;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			mHandler.sendEmptyMessage(BACK);
			break;
		case R.id.success_faliure_text_left:
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
		mActivity = Success_Faliure.this;
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
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

	/**
	 * 更新订单状态
	 */
	private final int RENEWORDERSTA = 1;
	private final int BACK = 2;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RENEWORDERSTA:
				orderstatus = getResources().getString(R.string.order_canceled);
				break;
			case BACK:
				/**
				 * 倒计时内返回到支付方式页，订单已取消时，关闭支付方式页
				 */
				if (!TextUtils.isEmpty(orderstatus)
						&& orderstatus.equals(getResources().getString(R.string.order_canceled))) {
				}
				nowFinish();
				break;
			}
		}
	};
	private int servicecurrenttimestamp;
	private int createtimestamp;

	private void initDate() {
		Intent intent = getIntent();
		if (intent != null) {
			orderstatus = intent.getStringExtra("orderstatus");
			oid = intent.getStringExtra("oid");
			servicecurrenttimestamp = intent.getIntExtra("servicecurrenttimestamp", 0);
			createtimestamp = intent.getIntExtra("createtimestamp", 0);
		}
		pagetop_tv_name.setText("支付失败");
		if (!TextUtils.isEmpty(orderstatus) && orderstatus.equals(getResources().getString(R.string.order_canceled))) {
			faliure_pay_tv_phone.setText(getString(R.string.faliure_pay_content));
		} else {

			showTimerDown();
		}

	}

	private void showTimerDown() {
		/*
		 * 用系统返回的当前时间
		 */
		long[] shengYuTime = DatesUtils.getInstance().getCountdownTimesArray(servicecurrenttimestamp, createtimestamp);
		// 特卖倒计时开始
		faliure_pay_tv_phone.setComeFromFaliueString("true");
		faliure_pay_tv_phone.setTimes(shengYuTime, "<pre>请去该订单详情页对订单进行支付，</pre>", "后订单将因超时未支付被自动取消！", RENEWORDERSTA,
				mHandler);
		// 已经在倒计时的时候不再开启计时
		if (!faliure_pay_tv_phone.isRun()) {
			faliure_pay_tv_phone.run();
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
			mHandler.sendEmptyMessage(BACK);
		}
		return false;
	}
}
