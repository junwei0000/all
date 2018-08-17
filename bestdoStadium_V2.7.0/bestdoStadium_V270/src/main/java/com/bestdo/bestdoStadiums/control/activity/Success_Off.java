package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 超时支付成功
 * 
 * @author qyy
 * 
 */
public class Success_Off extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;
	private Button click_btn;
	private String oid;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			// 订单超时点击返回，返回到订单详情或订单列表，（关闭操作3）
			nowFinish();
			break;
		case R.id.click_btn:
			// 点击跳转详情，从详情返回时，直接返回订单列表（关闭操作3）
			CommonUtils.getInstance().getPhoneToKey(Success_Off.this);

			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.success_off);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		click_btn = (Button) findViewById(R.id.click_btn);
		click_btn.setText("百动客服热线  400-684-1808");

	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		click_btn.setOnClickListener(this);
		initDate();
	}

	private void skipIntent() {
		CommonUtils.getInstance().exitOrderDetailPage();
		Intent intent = new Intent(this, UserOrderDetailsActivity.class);
		intent.putExtra("oid", oid + "");// 订单号
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	private void initDate() {
		pagetop_tv_name.setText("超时支付成功");
		Intent in = getIntent();
		oid = in.getStringExtra("oid");
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
