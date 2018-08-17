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
 * 购买卡支付成功页面
 * 
 * @author qyy
 * 
 */
public class CardSuccess_Pay extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back, success_pay_text_right;
	private String oid, cid;
	private Activity mActivity;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			// nowFinish();
			if (CommonUtils.getInstance().intntSelectCardsStatus.equals("CreatOrdersActivity")) {
				CommonUtils.getInstance().exitPayPage();
			} else {
				CommonUtils.getInstance().skipMainActivity(this);
			}
			break;
		case R.id.card_success_pay_text:
			String intentStatus = CommonUtils.getInstance().intntSelectCardsStatus;

			if (!TextUtils.isEmpty(intentStatus) && intentStatus.equals("CreatOrdersActivity")) {
				CommonUtils.getInstance().exitPayPage();
			} else {
				CommonUtils.getInstance().skipCenterActivityFromMain(this);
			}
			skipIntent();
			// CommonUtils.getInstance().skipMainActivity(this);
			nowFinish();
			break;
		default:

			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.card_success_pay);
		CommonUtils.getInstance().addActivity(this);
		mActivity = CardSuccess_Pay.this;
		Intent in = getIntent();
		oid = in.getStringExtra("oid");
		cid = in.getStringExtra("cid");
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		success_pay_text_right = (LinearLayout) findViewById(R.id.card_success_pay_text);
	}

	@Override
	protected void setListener() {
		pagetop_tv_name.setText(getResources().getString(R.string.success_card_pay_title));
		pagetop_layout_back.setOnClickListener(this);
		success_pay_text_right.setOnClickListener(this);
	}

	private void skipIntent() {
		String s = CommonUtils.getInstance().intntStatus;
		if (!TextUtils.isEmpty(s) && s.equals("UserCenterActivity")) {
			Intent intent = new Intent(this, CreateOrderSelectCardsActivity.class);

			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
		}
		finish();

	}

	@Override
	protected void processLogic() {
	}

	private void nowFinish() {
		CommonUtils.getInstance().exitPayPage();
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
