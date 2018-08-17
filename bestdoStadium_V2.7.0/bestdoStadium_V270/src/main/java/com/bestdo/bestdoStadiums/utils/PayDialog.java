package com.bestdo.bestdoStadiums.utils;

import java.util.HashMap;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness;
import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness.GetMemberInfoCallback;
import com.bestdo.bestdoStadiums.business.UserOrderDetailsBusiness;
import com.bestdo.bestdoStadiums.business.UserOrderDetailsBusiness.GetUserOrderDetailsCallback;
import com.bestdo.bestdoStadiums.control.activity.CreatOrdersActivity;
import com.bestdo.bestdoStadiums.control.activity.Success_Pay;
import com.bestdo.bestdoStadiums.control.activity.Success_YuDing;
import com.bestdo.bestdoStadiums.control.activity.Success_outtime;
import com.bestdo.bestdoStadiums.control.activity.UserBalanceActivity;
import com.bestdo.bestdoStadiums.control.activity.UserOrderDetailsActivity;
import com.bestdo.bestdoStadiums.model.UserCenterMemberInfo;
import com.bestdo.bestdoStadiums.model.UserOrderDetailsInfo;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;
import com.example.androidbridge.CallBackFunction;

public class PayDialog {
	private Activity activity;
	public MyDialog selectPayDialog;
	private String oid;
	private String uid;
	private String remind;
	/**
	 * 区别跳转渠道（购买卡、场馆下单、商城购买、会员购买）
	 */
	public String skiptIntent;
	private String num;
	private String payMoney;
	private String payShowMoney;
	private Handler mHandler;
	/**
	 * 银联支付-渠道类型（android ios web等） 首页银联活动
	 */
	public String mid;
	public String payType = "";
	public PayDialog(Activity activity, Handler mHandler, String skiptIntent, String oid, String uid, String remind,
			String num, String payMoney) {
		super();
		this.activity = activity;
		this.skiptIntent = skiptIntent;
		this.mHandler = mHandler;
		this.oid = oid;
		this.uid = uid;
		this.remind = remind;
		this.num = num;
		this.payMoney = payMoney;
	}

	CheckBox zhifubao_pay_checkbox;
	CheckBox weixin_pay_checkbox;
	public String card_batch_id = "";
	private CheckBox yinlian_pay_checkbox;
	protected PayUtils payUtils;
	private LinearLayout yue_pay_lin;
	private CheckBox yue_pay_checkbox;
	private LinearLayout weixin_pay_lin;
	private LinearLayout zhifubao_pay_lin;
	private LinearLayout yinlian_pay_lin;
	protected String balance = "0";
	protected String tip;

	public void getPayDialog() {
		getUserMemberBalanceInfo();

	}

	private void getUserMemberBalanceInfo() {
		HashMap<String, String> mhashmap2 = new HashMap<String, String>();
		mhashmap2.put("uid", "" + uid);
		System.err.println();
		new GetMemberInfoBusiness(activity, mhashmap2, new GetMemberInfoCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						UserCenterMemberInfo userCenterBalanceInfo = (UserCenterMemberInfo) dataMap
								.get("userCenterBalanceInfo");
						if (userCenterBalanceInfo != null) {
							tip = userCenterBalanceInfo.getTip();
							balance = userCenterBalanceInfo.getBalance();
							balance = PriceUtils.getInstance().gteDividePrice(balance, "100");
							balance = PriceUtils.getInstance().seePrice(balance);
						}

					}
				}
				mDialogHandler.sendEmptyMessage(SHOWDIALOG);
			}
		});
	}

	private final int SHOWDIALOG = 2;
	Handler mDialogHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOWDIALOG:
				setShowDialog();
				break;
			}
		}
	};
	private TextView paytop_tv_balanceinfo;
	private TextView paytop_tv_money;
	private String dikouMoney;

	private void setShowDialog() {
		if (selectPayDialog == null) {
			selectPayDialog = new MyDialog(activity, R.style.dialog, R.layout.pay_dialog);
			Window dialogWindow = selectPayDialog.getWindow();
			dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
			dialogWindow.setWindowAnimations(R.style.showAnDialog);
			selectPayDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
			selectPayDialog.show();
			// 将对话框的大小按屏幕大小的百分比设置
			WindowManager m = activity.getWindowManager();
			Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
			WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
			p.width = (int) (d.getWidth()); // 宽度设置为屏幕
			dialogWindow.setAttributes(p);

			LinearLayout select_pry_dialog_off_lin = (LinearLayout) selectPayDialog
					.findViewById(R.id.select_pry_dialog_off_lin);

			paytop_tv_money = (TextView) selectPayDialog.findViewById(R.id.paytop_tv_money);
			paytop_tv_balanceinfo = (TextView) selectPayDialog.findViewById(R.id.paytop_tv_balanceinfo);
			yue_pay_lin = (LinearLayout) selectPayDialog.findViewById(R.id.yue_pay_lin);
			yue_pay_checkbox = (CheckBox) selectPayDialog.findViewById(R.id.yue_pay_checkbox);

			TextView sure_pay_lin = (TextView) selectPayDialog.findViewById(R.id.sure_pay_lin);
			yinlian_pay_lin = (LinearLayout) selectPayDialog.findViewById(R.id.yinlian_pay_lin);
			yinlian_pay_checkbox = (CheckBox) selectPayDialog.findViewById(R.id.yinlian_pay_checkbox);
			zhifubao_pay_lin = (LinearLayout) selectPayDialog.findViewById(R.id.zhifubao_pay_lin);
			zhifubao_pay_checkbox = (CheckBox) selectPayDialog.findViewById(R.id.zhifubao_pay_checkbox);
			weixin_pay_lin = (LinearLayout) selectPayDialog.findViewById(R.id.weixin_pay_lin);
			weixin_pay_checkbox = (CheckBox) selectPayDialog.findViewById(R.id.weixin_pay_checkbox);

			sure_pay_lin.setOnClickListener(mClickListener);
			select_pry_dialog_off_lin.setOnClickListener(mClickListener);
			yue_pay_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (!isChecked) {
						yue_pay_checkbox.setChecked(false);
						payShowMoney = payMoney;
						paytop_tv_money.setText("支付金额：¥" + payShowMoney + "元");
						paytop_tv_balanceinfo.setText("我的余额：¥" + balance + "元   本次抵扣：¥" + 0 + "元");
						setPayTypeUsersStatus(true);
					} else {
						setContents();
					}

				}
			});
			yinlian_pay_lin.setOnClickListener(mClickListener);
			yinlian_pay_checkbox.setOnClickListener(mClickListener);
			zhifubao_pay_lin.setOnClickListener(mClickListener);
			zhifubao_pay_checkbox.setOnClickListener(mClickListener);
			weixin_pay_checkbox.setOnClickListener(mClickListener);
			weixin_pay_lin.setOnClickListener(mClickListener);
			selectPayDialog.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
					// 取消dialog返回监听，走页面监听返回
					setBackDialog();
					return true;
				}
			});
		} else {
			selectPayDialog.show();
		}
		setPayTypeShow();
		setContents();
	}

	private void setContents() {
		payShowMoney = payMoney;
		setPayTypeUsersStatus(true);
		if (skiptIntent.equals(Constans.showPayDialogByBuyStadium)
				|| (skiptIntent.equals(Constans.showPayDialogByNavImg) && payType.contains("balancePay"))) {
			yue_pay_lin.setVisibility(View.VISIBLE);
			paytop_tv_balanceinfo.setVisibility(View.VISIBLE);
			paytop_tv_balanceinfo.setOnClickListener(mClickListener);
			yue_pay_checkbox.setChecked(true);
			yue_pay_checkbox.setClickable(true);
			if (Double.parseDouble(balance) == 0) {
				paytop_tv_balanceinfo.setText(tip + "");
				yue_pay_checkbox.setChecked(false);
				yue_pay_checkbox.setClickable(false);
			} else if (Double.parseDouble(balance) > 0 && Double.parseDouble(balance) >= Double.parseDouble(payMoney)) {
				dikouMoney = payMoney;
				paytop_tv_balanceinfo.setText("我的余额：¥" + balance + "元   本次抵扣：¥" + payMoney + "元");
				payShowMoney = "0";
				setPayTypeUsersStatus(false);
			} else if (Double.parseDouble(balance) > 0 && Double.parseDouble(balance) < Double.parseDouble(payMoney)) {
				payShowMoney = PriceUtils.getInstance().gteSubtractSumPrice(balance, payMoney);
				dikouMoney = balance;
				paytop_tv_balanceinfo.setText("我的余额：¥" + balance + "元   本次抵扣：¥" + balance + "元");
			}

		} else {
			paytop_tv_balanceinfo.setVisibility(View.GONE);
			yue_pay_lin.setVisibility(View.GONE);
			yue_pay_checkbox.setChecked(false);
		}
		paytop_tv_money.setText("支付金额：¥" + payShowMoney + "元");

	}

	private void setPayTypeShow() {
		/**
		 * 支付宝 = "aliPay";
		 * 
		 * 微信支付 = "weChatPay";
		 * 
		 * 银联支付 = "unionPay";
		 * 
		 * ApplePay = "applePay";
		 * 
		 * 余额=balancePay
		 */
		if (!TextUtils.isEmpty(payType)) {
			if (payType.contains("unionPay")) {
				yinlian_pay_lin.setVisibility(View.VISIBLE);
				setYinLianSelect();
			} else {
				yinlian_pay_lin.setVisibility(View.GONE);
				setaliPaySelect();
			}
			if (payType.contains("aliPay")) {
				zhifubao_pay_lin.setVisibility(View.VISIBLE);
			} else {
				zhifubao_pay_lin.setVisibility(View.GONE);
				if (yinlian_pay_lin.getVisibility() == View.GONE){
					setweixinSelect();
				}
			}
			if (payType.contains("weChatPay")) {
				weixin_pay_lin.setVisibility(View.VISIBLE);
			} else {
				weixin_pay_lin.setVisibility(View.GONE);
			}
		}
	}

	OnClickListener mClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.paytop_tv_balanceinfo:
				Intent intent = new Intent(activity, UserBalanceActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("balance", balance);
				intent.putExtra("skip", "DIALOG");
				activity.startActivityForResult(intent, Constans.showPayDialogByBuyBalanceResult);;
				CommonUtils.getInstance().setPageIntentAnim(intent, activity);
				break;
			case R.id.select_pry_dialog_off_lin:
				setBackDialog();
				break;
			case R.id.sure_pay_lin:
				setClickSureBtn();
				break;
			case R.id.yinlian_pay_lin:
			case R.id.yinlian_pay_checkbox:
				setYinLianSelect();
				break;
			case R.id.zhifubao_pay_lin:
			case R.id.zhifubao_pay_checkbox:
				setaliPaySelect();
				break;
			case R.id.weixin_pay_lin:
			case R.id.weixin_pay_checkbox:
				setweixinSelect();
				break;

			default:
				break;
			}
		}
	};

	private void setPayTypeUsersStatus(boolean userstatus) {

		if (userstatus) {
			yinlian_pay_lin.setClickable(true);
			zhifubao_pay_lin.setClickable(true);
			weixin_pay_lin.setClickable(true);
			yinlian_pay_checkbox.setClickable(true);
			zhifubao_pay_checkbox.setClickable(true);
			weixin_pay_checkbox.setClickable(true);
			yinlian_pay_checkbox.setBackgroundResource(R.drawable.pay_zhifubao_select);
			zhifubao_pay_checkbox.setBackgroundResource(R.drawable.pay_zhifubao_select);
			weixin_pay_checkbox.setBackgroundResource(R.drawable.pay_zhifubao_select);
		} else {
			yinlian_pay_lin.setClickable(false);
			zhifubao_pay_lin.setClickable(false);
			weixin_pay_lin.setClickable(false);
			yinlian_pay_checkbox.setClickable(false);
			zhifubao_pay_checkbox.setClickable(false);
			weixin_pay_checkbox.setClickable(false);
			yinlian_pay_checkbox.setBackgroundResource(R.drawable.check_no);
			zhifubao_pay_checkbox.setBackgroundResource(R.drawable.check_no);
			weixin_pay_checkbox.setBackgroundResource(R.drawable.check_no);
		}
	}

	private void setYinLianSelect() {
		yinlian_pay_checkbox.setChecked(true);
		zhifubao_pay_checkbox.setChecked(false);
		weixin_pay_checkbox.setChecked(false);
	}

	private void setaliPaySelect() {
		yinlian_pay_checkbox.setChecked(false);
		zhifubao_pay_checkbox.setChecked(true);
		weixin_pay_checkbox.setChecked(false);
	}

	private void setweixinSelect() {
		yinlian_pay_checkbox.setChecked(false);
		zhifubao_pay_checkbox.setChecked(false);
		weixin_pay_checkbox.setChecked(true);
	}

	private void setClickSureBtn() {
		payUtils = new PayUtils(activity, oid, uid, remind, num, mid, skiptIntent);
		payUtils.card_batch_id = card_batch_id;
		clickChecked();
	}

	private void clickChecked() {
		if (yue_pay_checkbox.isChecked()) {
			payUtils.isBalance = "1";
			payUtils.pay_money = PriceUtils.getInstance().gteMultiplySumPrice(payShowMoney, "100");
			payUtils.balance_reduce_money = PriceUtils.getInstance().gteMultiplySumPrice(dikouMoney, "100");
		}
		payUtils.ordermoney = payMoney;
		
		/**
		 * 使用余额-零元支付
		 */
		if(Double.parseDouble(payShowMoney) == 0){
			payUtils.userBalance0Pay();
		}else{
		
		if (zhifubao_pay_checkbox.isChecked()) {
			payUtils.processLogicZFB();
		} else if (weixin_pay_checkbox.isChecked()) {
			payUtils.processLogicWX();
		} else if (yinlian_pay_checkbox.isChecked()) {
			payUtils.processLogicYinLian();
		}
		}
		selectPayDialog.dismiss();
	}

	MyDialog selectBackDialog;

	/**
	 * 返回时是否取消支付
	 */
	private void setBackDialog() {
		if (selectBackDialog == null) {
			selectBackDialog = CommonUtils.getInstance().defineBackPressed(activity, mHandler, "exit_pay");
		} else if (selectBackDialog != null && !selectBackDialog.isShowing()) {
			selectBackDialog.show();
		} else {
			selectBackDialog.dismiss();
		}
	}

}
