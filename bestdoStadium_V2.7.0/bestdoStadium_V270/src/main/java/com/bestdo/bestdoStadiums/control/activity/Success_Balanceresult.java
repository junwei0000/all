package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness;
import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness.GetMemberInfoCallback;
import com.bestdo.bestdoStadiums.model.UserCenterMemberInfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 充值余额结果页
 * 
 * @author jun
 * 
 */
public class Success_Balanceresult extends BaseActivity {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView pay_iv_result;
	private TextView pay_tv_result;

	private String resultStatus;
	private TextView pay_tv_left;
	private TextView pay_tv_right;
	private TextView pay_tv_content1;
	private TextView pay_tv_content2;
	private String payMoney;
	private String balancemoney;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pay_tv_right:
			Intent intent = new Intent(context, UserBalanceActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("balance", balancemoney);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, context);
			finish();
			break;
		case R.id.pay_tv_left:
			CommonUtils.getInstance().skipMainActivity(this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.success_balanceresult);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pay_iv_result = (ImageView) findViewById(R.id.pay_iv_result);
		pay_tv_result = (TextView) findViewById(R.id.pay_tv_result);

		pay_tv_content1 = (TextView) findViewById(R.id.pay_tv_content1);
		pay_tv_content2 = (TextView) findViewById(R.id.pay_tv_content2);
		pay_tv_left = (TextView) findViewById(R.id.pay_tv_left);
		pay_tv_right = (TextView) findViewById(R.id.pay_tv_right);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pay_tv_left.setOnClickListener(this);
		pay_tv_right.setOnClickListener(this);
		initDate();
	}

	private void initDate() {
		pagetop_tv_name.setText("支付结果");
		Intent intent = getIntent();
		if (intent != null) {
			resultStatus = intent.getExtras().getString("resultStatus", "success");
			payMoney= intent.getExtras().getString("payMoney", "0");
		}
		if (resultStatus.equals("success")) {
			pay_tv_result.setText("支付成功");
			pay_tv_result.setTextColor(getResources().getColor(R.color.blue));
			pay_iv_result.setBackgroundResource(R.drawable.pay_balanceresult_success);
			pay_tv_left.setBackgroundResource(R.drawable.user_login_btn_blue);
			pay_tv_right.setBackgroundResource(R.drawable.user_login_btn_blue);
			pay_tv_right.setText("继续储值");
			String title = "您已成功充值：<font color='#F40A0A'>¥" + payMoney + "</font>";
			pay_tv_content1.setText(Html.fromHtml(title));
			String title2 = "当前账户余额：<font color='#F40A0A'>¥" + balancemoney + "</font>";
			pay_tv_content2.setText(Html.fromHtml(title2));
		} else {
			pay_tv_result.setText("支付失败");
			pay_tv_result.setTextColor(getResources().getColor(R.color.red_level));
			pay_iv_result.setBackgroundResource(R.drawable.pay_balanceresult_failure);
			pay_tv_left.setBackgroundResource(R.drawable.corners_rectangle_red);
			pay_tv_right.setBackgroundResource(R.drawable.corners_rectangle_red);
			pay_tv_right.setText("重新支付");
			String title = "请<font color='#F40A0A'>重试" + "</font>或<font color='#F40A0A'>选择其他支付方式" + "</font>支付";
			pay_tv_content1.setText(Html.fromHtml(title));
		}

	}

	@Override
	protected void processLogic() {
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		HashMap<String, String> mhashmap2 = new HashMap<String, String>();
		mhashmap2.put("uid", "" + bestDoInfoSharedPrefs.getString("uid", ""));
		new GetMemberInfoBusiness(this, mhashmap2, new GetMemberInfoCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						UserCenterMemberInfo userCenterBalanceInfo = (UserCenterMemberInfo) dataMap
								.get("userCenterBalanceInfo");
						if (userCenterBalanceInfo != null) {
							balancemoney = userCenterBalanceInfo.getBalance();
							balancemoney = PriceUtils.getInstance().gteDividePrice(balancemoney, "100");
							balancemoney = PriceUtils.getInstance().seePrice(balancemoney);
							if (resultStatus.equals("success")) {
								String title2 = "当前账户余额：<font color='#F40A0A'>¥" + balancemoney + "</font>";
								pay_tv_content2.setText(Html.fromHtml(title2));
							}
						}

					}
				}

			}
		});
	}

	@Override
	protected void onDestroy() {
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
