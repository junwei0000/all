package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bestdo.bestdoStadiums.business.UserOrderDetailsBusiness;
import com.bestdo.bestdoStadiums.business.UserOrderDetailsBusiness.GetUserOrderDetailsCallback;
import com.bestdo.bestdoStadiums.model.UserOrderDetailsInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.Dates_CorrentUtils;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.PayDialog;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.TimeTextView;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-29 下午12:00:08
 * @Description 类描述：7种订单状态和多种运动类型的详情
 */
public class UserOrderDetailsActivity extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;
	private ImageView pagetop_iv_you;
	/*
	 * topstatus
	 */
	private ImageView userorderdetails_topstatus_iv_img;
	private TextView userorderdetails_topstatus_tv_status;
	private TextView userorderdetails_topstatus_tv_statusinfo;

	/*
	 * stadium
	 */
	private TextView userorderdetails_stadiuminfo_tv_stadiumname;
	private TextView userorderdetails_stadiuminfo_tv_date;
	private TextView userorderdetails_stadiuminfo_tv_num, userorderdetails_stadiuminfo_tv_numtitle;
	private TextView userorderdetails_stadiuminfo_tv_server, userorderdetails_stadiuminfo_tv_servertitle;
	private TextView userorderdetails_stadiuminfo_tv_address;

	private LinearLayout userorderdetails_codeitem_layout;
	/*
	 * order
	 */
	private TextView userorderdetails_orderinfo_tv_orderNo;
	private TextView userorderdetails_orderinfo_tv_orderNocontent;
	private TextView userorderdetails_orderinfo_tv_phone;
	private TextView userorderdetails_orderinfo_tv_phonecontent;
	private TextView userorderdetails_orderinfo_tv_palyers;
	private TextView userorderdetails_orderinfo_tv_palyerscontent;
	private LinearLayout userorderdetails_orderinfo_layout_palyers;
	/*
	 * pay
	 */
	private TextView userorderdetails_payinfo_tv_orderallmoney;
	private TextView userorderdetails_payinfo_tv_deduction;
	private TextView userorderdetails_payinfo_tv_paymoney;

	private TextView userorderdetails_tv_bottom;
	private TimeTextView userorderdetails_tv_timers;

	private ProgressDialog mDialog;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String oid;
	private UserOrderDetailsInfo mUserOrderDetailsInfo;

	String stadiumtel;
	private LinearLayout userorderdetails_layout_bottom;
	private TextView userorderdetails_payinfo_tv_membermoney;
	private TextView userorderdetails_payinfo_tv_balance;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;

		case R.id.pagetop_iv_you:
			CommonUtils.getInstance().getPhoneToKey(this);
			break;
		case R.id.userorderdetails_tv_bottom:
			setClickBtn();
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_order_details);
		CommonUtils.getInstance().addActivity(this);
		CommonUtils.getInstance().addOrderDetailPage(this);

	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);

		userorderdetails_topstatus_iv_img = (ImageView) findViewById(R.id.userorderdetails_topstatus_iv_img);
		userorderdetails_topstatus_tv_status = (TextView) findViewById(R.id.userorderdetails_topstatus_tv_status);
		userorderdetails_topstatus_tv_statusinfo = (TextView) findViewById(
				R.id.userorderdetails_topstatus_tv_statusinfo);

		userorderdetails_stadiuminfo_tv_stadiumname = (TextView) findViewById(
				R.id.userorderdetails_stadiuminfo_tv_stadiumname);
		userorderdetails_stadiuminfo_tv_date = (TextView) findViewById(R.id.userorderdetails_stadiuminfo_tv_date);
		userorderdetails_stadiuminfo_tv_numtitle = (TextView) findViewById(
				R.id.userorderdetails_stadiuminfo_tv_numtitle);
		userorderdetails_stadiuminfo_tv_num = (TextView) findViewById(R.id.userorderdetails_stadiuminfo_tv_num);
		userorderdetails_stadiuminfo_tv_servertitle = (TextView) findViewById(
				R.id.userorderdetails_stadiuminfo_tv_servertitle);
		userorderdetails_stadiuminfo_tv_server = (TextView) findViewById(R.id.userorderdetails_stadiuminfo_tv_server);
		userorderdetails_stadiuminfo_tv_address = (TextView) findViewById(R.id.userorderdetails_stadiuminfo_tv_address);

		userorderdetails_codeitem_layout = (LinearLayout) findViewById(R.id.userorderdetails_codeitem_layout);

		userorderdetails_orderinfo_tv_orderNo = (TextView) findViewById(R.id.userorderdetails_orderinfo_tv_orderNo);
		userorderdetails_orderinfo_tv_orderNocontent = (TextView) findViewById(
				R.id.userorderdetails_orderinfo_tv_orderNocontent);
		userorderdetails_orderinfo_tv_phone = (TextView) findViewById(R.id.userorderdetails_orderinfo_tv_phone);
		userorderdetails_orderinfo_tv_phonecontent = (TextView) findViewById(
				R.id.userorderdetails_orderinfo_tv_phonecontent);
		userorderdetails_orderinfo_tv_palyers = (TextView) findViewById(R.id.userorderdetails_orderinfo_tv_palyers);
		userorderdetails_orderinfo_tv_palyerscontent = (TextView) findViewById(
				R.id.userorderdetails_orderinfo_tv_palyerscontent);
		userorderdetails_orderinfo_layout_palyers = (LinearLayout) findViewById(
				R.id.userorderdetails_orderinfo_layout_palyers);

		userorderdetails_payinfo_tv_orderallmoney = (TextView) findViewById(
				R.id.userorderdetails_payinfo_tv_orderallmoney);
		userorderdetails_payinfo_tv_deduction = (TextView) findViewById(R.id.userorderdetails_payinfo_tv_deduction);
		userorderdetails_payinfo_tv_paymoney = (TextView) findViewById(R.id.userorderdetails_payinfo_tv_paymoney);
		userorderdetails_payinfo_tv_membermoney = (TextView) findViewById(R.id.userorderdetails_payinfo_tv_membermoney);
		userorderdetails_payinfo_tv_balance= (TextView) findViewById(R.id.userorderdetails_payinfo_tv_balance);
		userorderdetails_layout_bottom = (LinearLayout) findViewById(R.id.userorderdetails_layout_bottom);
		userorderdetails_tv_timers = (TimeTextView) findViewById(R.id.userorderdetails_tv_timers);
		userorderdetails_tv_bottom = (TextView) findViewById(R.id.userorderdetails_tv_bottom);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		userorderdetails_tv_bottom.setOnClickListener(this);
		pagetop_iv_you.setVisibility(View.VISIBLE);
		userorderdetails_layout_bottom.setVisibility(View.INVISIBLE);
		pagetop_iv_you.setOnClickListener(this);
		pagetop_iv_you.setBackgroundResource(R.drawable.userorderdetail_ic_phone);
		initDate();
	}

	private void initDate() {
		pagetop_tv_name.setText(getResources().getString(R.string.userOrdersDetails_orderinfo));
		oid = getIntent().getStringExtra("oid");
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	HashMap<String, String> mhashmap;
	String uid;
	private PayDialog payDialog;

	protected void processLogic() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		mhashmap.put("uid", uid);
		mhashmap.put("oid", oid);
		new UserOrderDetailsBusiness(this, mhashmap, new GetUserOrderDetailsCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String msg = (String) dataMap.get("data");
						CommonUtils.getInstance().initToast(context, msg);
					} else if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						mUserOrderDetailsInfo = (UserOrderDetailsInfo) dataMap.get("userOrderDetailsInfo");
						if (mUserOrderDetailsInfo != null) {
							userorderdetails_layout_bottom.setVisibility(View.VISIBLE);
							setLoadData();
						}
					}
				} else {
					CommonUtils.getInstance().initToast(UserOrderDetailsActivity.this, getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	private void setLoadData() {
		String cid = mUserOrderDetailsInfo.getCid();
		String orderStatus = mUserOrderDetailsInfo.getStats();
		Log.e("orderStatus", "orderStatus" + orderStatus);
		if (orderStatus.equals(getString(R.string.order_putParameters_waitpay))) {
			showTimerDown();
		}

		orderStatus = mUserOrderDetailsInfo.getStats();
		showTopStatusView(orderStatus);

		showCodeView(orderStatus, cid);

		showStadiumView(cid);

		showOrderView(cid);

		showPayView();

		showBtn(orderStatus);

	}

	/**
	 * 
	 * @param orderStatus
	 */
	private void showCodeView(String orderStatus, String cid) {
		if (orderStatus.equals(getString(R.string.order_putParameters_stayoff))
				&& !cid.equals(Constans.getInstance().sportCidGolf)) {
			userorderdetails_codeitem_layout.setVisibility(View.VISIBLE);
			ArrayList<UserOrderDetailsInfo> itemOrderList = mUserOrderDetailsInfo.getItemOrderList();
			if (itemOrderList != null && itemOrderList.size() != 0) {
				if (cid.equals(Constans.getInstance().sportCidTennis)
						|| cid.equals(Constans.getInstance().sportCidBadminton)) {
					// pylw
					for (int i = 0; i < itemOrderList.size(); i++) {
						View view = LayoutInflater.from(context).inflate(R.layout.user_order_details_codeitem, null);
						TextView codetitle = (TextView) view.findViewById(R.id.userorderdetails_codeitem_tv_codetitle);
						TextView code = (TextView) view.findViewById(R.id.userorderdetails_codeitem_tv_code);
						View line = view.findViewById(R.id.userorderdetails_codeitem_tv_line);
						codetitle.setText("验证码" + (i + 1) + ":");
						String start_time = itemOrderList.get(i).getStart_time();
						String end_time = itemOrderList.get(i).getEnd_time();
						String cod = itemOrderList.get(i).getCode();
						String code_status = itemOrderList.get(i).getCode_status();
						String zuoString = "";
						String youString = "";
						if (code_status.equals("1")) {
							// 使用
							zuoString = "<font color='#A8A8A8'>";
							youString = "</font>";
						}
						code.setText(Html.fromHtml(zuoString + start_time + "-" + end_time + " " + cod + youString));
						if (i == itemOrderList.size() - 1) {
							line.setVisibility(View.GONE);
						}
						userorderdetails_codeitem_layout.addView(view);
					}
				} else {
					//
					for (int i = 0; i < itemOrderList.size(); i++) {
						View view = LayoutInflater.from(context).inflate(R.layout.user_order_details_codeitem, null);
						TextView codetitle = (TextView) view.findViewById(R.id.userorderdetails_codeitem_tv_codetitle);
						TextView code = (TextView) view.findViewById(R.id.userorderdetails_codeitem_tv_code);
						View line = view.findViewById(R.id.userorderdetails_codeitem_tv_line);
						codetitle.setText("验证码" + (i + 1) + ":");
						String cod = itemOrderList.get(i).getCode();
						String code_status = itemOrderList.get(i).getCode_status();
						String zuoString = "";
						String youString = "";
						if (code_status.equals("1")) {
							// 使用
							zuoString = "<font color='#A8A8A8'>";
							youString = "</font>";
						}
						code.setText(Html.fromHtml(zuoString + cod + youString));
						if (i == itemOrderList.size() - 1) {
							line.setVisibility(View.GONE);
						}
						userorderdetails_codeitem_layout.addView(view);
					}
				}
			}
		} else {
			userorderdetails_codeitem_layout.setVisibility(View.GONE);
		}
	}

	private void setClickBtn() {
		String orderStatus = mUserOrderDetailsInfo.getStats();
		if (orderStatus.equals(getString(R.string.order_putParameters_waitpay))) {
			// 去支付
			String remind = mUserOrderDetailsInfo.getPhone();
			payDialog = new PayDialog(UserOrderDetailsActivity.this, mTimerHandler, Constans.showPayDialogByBuyStadium, oid, uid, remind, "",mUserOrderDetailsInfo.getPay_money());
			payDialog.getPayDialog();
		} else if (orderStatus.equals(getString(R.string.order_unsubscribed))
				|| orderStatus.equals(getString(R.string.order_canceled))
				|| orderStatus.equals(getString(R.string.order_completed))) {
			// 重新预订
			Intent intent = new Intent(this, StadiumDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("mer_item_id", mUserOrderDetailsInfo.getMer_item_id());
			bundle.putString("vip_price_id", mUserOrderDetailsInfo.getMer_price_id());
			bundle.putString("mer_name", mUserOrderDetailsInfo.getMer_item_name());
			bundle.putString("merid", mUserOrderDetailsInfo.getMerid());
			bundle.putString("cid", mUserOrderDetailsInfo.getCid());
			intent.putExtras(bundle);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
		} else if (orderStatus.equals(getString(R.string.order_putParameters_confirming))
				|| orderStatus.equals(getString(R.string.order_putParameters_stayoff))) {
			// 退订
			Intent intent = new Intent(this, UserOrderUnsubscribActivity.class);
			intent.putExtra("oid", mUserOrderDetailsInfo.getOid());
			intent.putExtra("uid", uid);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
		}
	}

	private void showTimerDown() {
		/*
		 * 用系统返回的当前时间
		 */
		int servicecurrenttimestamp = mUserOrderDetailsInfo.getCurrent_time();
		int createtimestamp = mUserOrderDetailsInfo.getCreate_time();
		long[] shengYuTime = DatesUtils.getInstance().getCountdownTimesArray(servicecurrenttimestamp, createtimestamp);
		if (shengYuTime[1] >= 0 && shengYuTime[2] >= 0) {
			// 特卖倒计时开始
			userorderdetails_tv_timers.setTimes(shengYuTime, "<pre>支付剩余 </pre>", "", UPDATETIME, mTimerHandler);
			// 已经在倒计时的时候不再开启计时
			if (!userorderdetails_tv_timers.isRun()) {
				userorderdetails_tv_timers.run();
			}
		} else {
			// 设置为已取消
			mUserOrderDetailsInfo.setStats(getString(R.string.order_canceled));
		}
	}

	/**
	 * 
	 * @param orderStatus
	 */
	private void showBtn(String orderStatus) {
		userorderdetails_tv_timers.setVisibility(View.GONE);
		userorderdetails_tv_bottom.setVisibility(View.VISIBLE);
		if (orderStatus.equals(getString(R.string.order_putParameters_waitpay))) {
			userorderdetails_tv_timers.setVisibility(View.VISIBLE);
			userorderdetails_tv_bottom.setText("去支付");
			userorderdetails_tv_bottom.setBackgroundColor(getResources().getColor(R.color.btn_blue_color));
			userorderdetails_tv_bottom.setTextColor(getResources().getColor(R.color.white));
		} else if (orderStatus.equals(getString(R.string.order_unsubscribed))
				|| orderStatus.equals(getString(R.string.order_canceled))
				|| orderStatus.equals(getString(R.string.order_completed))) {
			userorderdetails_tv_bottom.setText("重新预订");
			userorderdetails_tv_bottom.setBackgroundColor(getResources().getColor(R.color.btn_blue_color));
			userorderdetails_tv_bottom.setTextColor(getResources().getColor(R.color.white));
		} else if (orderStatus.equals(getString(R.string.order_putParameters_confirming))
				|| orderStatus.equals(getString(R.string.order_putParameters_stayoff))) {
			userorderdetails_tv_bottom.setText("申请退订");
			userorderdetails_tv_bottom.setTextColor(getResources().getColor(R.color.btn_blue_color));
			userorderdetails_tv_bottom.setBackgroundColor(getResources().getColor(R.color.white));
		} else {
			userorderdetails_tv_bottom.setVisibility(View.GONE);
		}

	}

	private void showPayView() {
		userorderdetails_payinfo_tv_orderallmoney
				.setText(getString(R.string.unit_fuhao_money) + mUserOrderDetailsInfo.getOrder_money());
		userorderdetails_payinfo_tv_deduction
				.setText(getString(R.string.unit_fuhao_money) + mUserOrderDetailsInfo.getReduce_money());
		userorderdetails_payinfo_tv_paymoney
				.setText(getString(R.string.unit_fuhao_money) + mUserOrderDetailsInfo.getPay_money());
		userorderdetails_payinfo_tv_membermoney
				.setText(getString(R.string.unit_fuhao_money) + mUserOrderDetailsInfo.getMember_reduce_money());
		userorderdetails_payinfo_tv_balance.setText(getString(R.string.unit_fuhao_money) + mUserOrderDetailsInfo.getBalance());
	}

	/**
	 * 
	 * @param cid
	 */
	private void showOrderView(String cid) {
		String orderNoTitle;
		String phoneTitle;
		String palyersTitle;
		orderNoTitle = "订单号码:";
		phoneTitle = "联系人手机:";
		palyersTitle = "打球人姓名:";
		if (cid.equals(Constans.getInstance().sportCidGolf)) {
		} else {
			// orderNoTitle = "订单号:";
			// phoneTitle = "手机:";
			// palyersTitle = "姓名:";
			userorderdetails_orderinfo_layout_palyers.setVisibility(View.GONE);
		}
		userorderdetails_orderinfo_tv_orderNo.setText(orderNoTitle);
		userorderdetails_orderinfo_tv_phone.setText(phoneTitle);
		userorderdetails_orderinfo_tv_palyers.setText(palyersTitle);

		userorderdetails_orderinfo_tv_orderNocontent.setText(mUserOrderDetailsInfo.getOid());
		userorderdetails_orderinfo_tv_phonecontent.setText(mUserOrderDetailsInfo.getPhone());
		userorderdetails_orderinfo_tv_palyerscontent.setText(mUserOrderDetailsInfo.getPlayers());
	}

	/**
	 * 
	 * @param cid
	 */
	private void showStadiumView(String cid) {
		userorderdetails_stadiuminfo_tv_stadiumname.setText(mUserOrderDetailsInfo.getMer_item_name());
		String day = mUserOrderDetailsInfo.getDay();
		stadiumtel = mUserOrderDetailsInfo.getStadiumtel();
		day = DatesUtils.getInstance().getDateGeShi(day, "yyyy-MM-dd", "MM月dd日 EE");
		day = DatesUtils.getInstance().getZhuanHuan(day, "星期", "周");
		userorderdetails_stadiuminfo_tv_num.setText(mUserOrderDetailsInfo.getNumber());
		userorderdetails_stadiuminfo_tv_server.setText(mUserOrderDetailsInfo.getContain());
		userorderdetails_stadiuminfo_tv_address.setText(mUserOrderDetailsInfo.getAddress());

		if (cid.equals(Constans.getInstance().sportCidGolf)) {
			String plattime = mUserOrderDetailsInfo.getItemOrderList().get(0).getPlay_time();
			day = day + " " + plattime;
		} else if (cid.equals(Constans.getInstance().sportCidGolfrange)) {
			String starttime = mUserOrderDetailsInfo.getItemOrderList().get(0).getStart_time();
			String endtime = mUserOrderDetailsInfo.getItemOrderList().get(0).getEnd_time();
			day = day + " " + starttime + "-" + endtime;
		}
		userorderdetails_stadiuminfo_tv_date.setText(day);
		showPYLWView(cid);

	}

	/**
	 * 
	 * @param cid
	 */
	private void showPYLWView(String cid) {
		if (cid.equals(Constans.getInstance().sportCidTennis) || cid.equals(Constans.getInstance().sportCidBadminton)) {
			userorderdetails_stadiuminfo_tv_numtitle.setVisibility(View.GONE);
			userorderdetails_stadiuminfo_tv_num.setVisibility(View.GONE);
			userorderdetails_stadiuminfo_tv_servertitle.setText(getString(R.string.userOrdersDetails_times));

			/*
			 * 乒羽篮网--显示时间段
			 */
			ArrayList<UserOrderDetailsInfo> playtimeList = mUserOrderDetailsInfo.getItemOrderList();
			if (playtimeList != null && playtimeList.size() != 0) {
				HashMap<String, String> sameTimeMap = new HashMap<String, String>();
				for (int i = 0; i < playtimeList.size(); i++) {
					String starttime = playtimeList.get(i).getStart_time();
					String endtime = playtimeList.get(i).getEnd_time();
					String key = starttime + "-" + endtime;
					if (!sameTimeMap.containsKey(key)) {
						sameTimeMap.put(key, "1");
					} else {
						String value = PriceUtils.getInstance().gteAddSumPrice(sameTimeMap.get(key), "1");
						sameTimeMap.put(key, value);
					}
				}
				// 排序
				List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
						sameTimeMap.entrySet());
				Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
					public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
						return (o1.getKey()).toString().compareTo(o2.getKey());
					}
				});
				StringBuffer stringBuffer = new StringBuffer();
				if (sameTimeMap.size() == 1) {
					// 1.当只有一个相同time时
					for (Entry<String, String> entry : infoIds) {
						String key = entry.getKey();// 返回与此项对应的键
						String value = entry.getValue();// 返回与此项对应的值
						stringBuffer.append(key + "  " + value + getString(R.string.unit_piece));
					}
				} else {
					for (int i = 0; i < infoIds.size(); i++) {
						Entry<String, String> entry = infoIds.get(i);
						String key = entry.getKey();// 返回与此项对应的键
						String value = entry.getValue();// 返回与此项对应的值
						stringBuffer.append(key + "  " + value + getString(R.string.unit_piece));
						if (i != infoIds.size() - 1) {
							stringBuffer.append("\n");
						}

					}
				}
				userorderdetails_stadiuminfo_tv_server.setText(stringBuffer);
				stringBuffer = null;
				if (sameTimeMap != null) {
					sameTimeMap.clear();
					sameTimeMap = null;
				}
				if (infoIds != null) {
					infoIds.clear();
					infoIds = null;
				}
			}

		}
	}

	/**
	 * 
	 * @param orderStatus
	 */
	private void showTopStatusView(String orderStatus) {
		String statusName;
		String statusInfo;
		int drawableid;
		if (orderStatus.equals(getString(R.string.order_putParameters_waitpay))) {
			statusName = getString(R.string.order_waitpay_name);
			statusInfo = "超时将自动取消";
			drawableid = R.drawable.userorder_detail_waitpay_img;
		} else if (orderStatus.equals(getString(R.string.order_unsubscribing))) {
			statusName = getString(R.string.order_unsubscribing_name);
			statusInfo = "等待百动确认";
			drawableid = R.drawable.userorder_detail_unsubscribing_img;
		} else if (orderStatus.equals(getString(R.string.order_putParameters_stayoff))) {
			statusName = getString(R.string.order_stayoff_name);
			statusInfo = "赶紧去运动吧";
			drawableid = R.drawable.userorder_detail_stayoff_img;
		} else if (orderStatus.equals(getString(R.string.order_unsubscribed))) {
			statusName = getString(R.string.order_unsubscribed_name);
			statusInfo = "已退订成功";
			drawableid = R.drawable.userorder_detail_unsubscribed_img;
		} else if (orderStatus.equals(getString(R.string.order_canceled))) {
			statusName = getString(R.string.order_canceled_name);
			statusInfo = "支付超时";
			drawableid = R.drawable.userorder_detail_canceled_img;
		} else if (orderStatus.equals(getString(R.string.order_completed))) {
			statusName = getString(R.string.order_completed_name);
			statusInfo = "订单已完成";
			drawableid = R.drawable.userorder_detail_completed_img;
		} else {
			statusName = getString(R.string.order_confirming_name);
			statusInfo = "百动正在努力确认中";
			drawableid = R.drawable.userorder_detail_confirming_img;
		}
		userorderdetails_topstatus_iv_img.setBackgroundResource(drawableid);
		userorderdetails_topstatus_tv_status.setText(statusName);
		userorderdetails_topstatus_tv_statusinfo.setText(statusInfo);
	}

	// -----------------add cancel 收藏-------------------------------
	@Override
	protected void onStart() {
		super.onStart();
		// 动态注册广播
		filter = new IntentFilter();
		filter.addAction(context.getString(R.string.action_Success_Faliure));
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(dynamicReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(dynamicReceiver);
		dynamicReceiver = null;
		filter = null;
		mUserOrderDetailsInfo = null;
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		RequestUtils.cancelAll(this);
		userorderdetails_tv_timers.removeCallbacks();
		super.onDestroy();
	}

	IntentFilter filter;
	private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context context, Intent intent) {
			Log.e("UserOrderDetailsActivity", "接收--倒计时/支付成功/点击去付款后在支付页添加时间---广播消息");
			String type = intent.getStringExtra("type");
			if (type.equals("UPDATESTATUS") || type.equals("updatepaytime") || type.equals("payover")) {
				Log.i("接收--倒计时/支付成功/点击去付款后在支付页添加时间---广播消息", type);
				mTimerHandler.sendEmptyMessage(UPDATESTATUS);
			}
		}
	};
	private final int UPDATETIME = 1;
	private final int UPDATESTATUS = 2;
	Handler mTimerHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATETIME:
				mUserOrderDetailsInfo.setStats(getString(R.string.order_canceled));
				setLoadData();
				break;
			case UPDATESTATUS:
				processLogic();
				break;
			case 5:
				payDialog.selectPayDialog.dismiss();
				break;
			}
		}
	};

	private void doBack() {
		// Intent userCancelOrderIntents = new Intent();
		// userCancelOrderIntents.setAction(context
		// .getString(R.string.action_Success_Faliure));
		// userCancelOrderIntents.putExtra("type", "UPDATELISTSTATUS");
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
	}
	/**
	 * activity回调数据
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			  if(resultCode == Constans.showPayDialogByBuyBalanceResult){
				if(payDialog!=null)
				payDialog.getPayDialog();
			}
		} catch (Exception e) {
		}
	};
}
