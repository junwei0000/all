package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.PayUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.unionpay.UPPayAssistEx;
import com.unionpay.UPQuerySEPayInfoCallback;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PayUnionActivity extends BaseActivity {

	private PayUtils payUtils;
	private String tn;
	private String mMode;
	private String PAYTYPE;

	@Override
	public void onClick(View v) {
		doback();
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.pay_union);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
	}

	@Override
	protected void setListener() {
	}

	@Override
	protected void processLogic() {
		PAYTYPE = getIntent().getExtras().getString("PAYTYPE", "");
		String oid = getIntent().getExtras().getString("oid", "");
		String remind = getIntent().getExtras().getString("remind", "");
		String uid = getIntent().getExtras().getString("uid", "");
		tn = getIntent().getExtras().getString("tn", "");
		String mid = getIntent().getExtras().getString("mid", "");
		String num = getIntent().getExtras().getString("num", "");
		String skiptIntent = getIntent().getExtras().getString("skiptIntent", "");

		String ordermoney = getIntent().getExtras().getString("ordermoney", "");
		String isBalance = getIntent().getExtras().getString("isBalance", "");
		String pay_money = getIntent().getExtras().getString("pay_money", "");
		String balance_reduce_money = getIntent().getExtras().getString("balance_reduce_money", "");
		payUtils = new PayUtils(this, oid, uid, remind, num, mid, skiptIntent);
		payUtils.isBalance = isBalance;
		payUtils.pay_money = pay_money;
		payUtils.balance_reduce_money = balance_reduce_money;
		payUtils.ordermoney = ordermoney;
		/*****************************************************************
		 * mMode参数解释： "00" - 启动银联正式环境; "01" - 连接银联测试环境
		 *****************************************************************/
		mMode = "00";
		if (PAYTYPE.equals("UNIONPAY")) {
			UPPayAssistEx.startPay(context, null, null, tn, mMode);
		} else {

			UPPayAssistEx.getSEPayInfo(this, new UPQuerySEPayInfoCallback() {
				/**
				 * SEName —— 手机pay名称，如表1 seType —— 与手机pay名称对应的类别，如表1 cardNumbers
				 * ——卡数量
				 */
				@Override
				public void onResult(String SEName, String seType, int cardNumbers, Bundle reserved) {
					System.err.println("onResult  " + seType + " " + cardNumbers);
					UPPayAssistEx.startSEPay(context, null, null, tn, mMode, seType);
				}

				@Override
				public void onError(String SEName, String seType, String errorCode, String errorDesc) {
					System.err.println("onError  " + errorCode);
				}
			});

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*************************************************
		 * 步骤3：处理银联手机支付控件返回的支付结果
		 ************************************************/
		if (data == null) {
			doback();
			return;
		}
		String msg = "";
		/**
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			payUtils.seccessSkip();
		} else if (str.equalsIgnoreCase("fail")) {
			payUtils.failureSkip();
		} else if (str.equalsIgnoreCase("cancel")) {
			payUtils.failureSkip();
		}
		doback();
	}

	private void doback() {
		finish();
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doback();
		}
		return false;
	}
}
