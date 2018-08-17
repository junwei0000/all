package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-8 上午11:14:35
 * @Description 类描述：订单退订成功页面
 */
public class Success_OrderUnsubscrib extends BaseActivity {
	private TextView pagetop_tv_name, success_pay_text;
	private LinearLayout pagetop_layout_back, success_pay_text_left, success_pay_text_right;
	private String oid;
	private TextView success_pay_tishi_text;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.success_pay_text_left:
			skipIntent();
			finish();
			CommonUtils.getInstance().setPageIntentAnim(null, this);
			break;
		case R.id.success_pay_text_right:
			CommonUtils.getInstance().skipMainActivity(this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.success_pay);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		success_pay_text = (TextView) findViewById(R.id.success_pay_text);
		success_pay_tishi_text = (TextView) findViewById(R.id.success_pay_tishi_text);
		success_pay_text_left = (LinearLayout) findViewById(R.id.success_pay_text_left);
		success_pay_text_right = (LinearLayout) findViewById(R.id.success_pay_text_right);
	}

	@Override
	protected void setListener() {
		Intent in = getIntent();
		oid = in.getStringExtra("oid");
		pagetop_tv_name.setText("退订申请提交成功");
		success_pay_text.setText(" 退订申请提交成功");
		success_pay_tishi_text.setText("");
		pagetop_layout_back.setOnClickListener(this);

		success_pay_text_left.setOnClickListener(this);
		success_pay_text_right.setOnClickListener(this);
	}

	private void skipIntent() {
		CommonUtils.getInstance().exitOrderDetailPage();
		Intent intent = new Intent(this, UserOrderDetailsActivity.class);
		intent.putExtra("oid", oid + "");// 订单号
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	@Override
	protected void processLogic() {
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
