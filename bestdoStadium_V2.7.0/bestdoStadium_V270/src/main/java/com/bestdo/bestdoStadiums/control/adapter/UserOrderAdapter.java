package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import java.util.Timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.control.activity.UserOrderActivity;
import com.bestdo.bestdoStadiums.control.activity.UserOrderShengHuoActivity;
import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.Dates_CorrentUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.ProgressTimer;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-26 下午3:25:51
 * @Description 类描述：全部订单列表适配
 */
public class UserOrderAdapter extends BaseAdapter {
	private ArrayList<UserOrdersInfo> list;
	private Activity context;
	ViewHolder viewHolder;
	ImageLoader mImageLoader;
	Handler mHandler;
	String orderFromListStatus;
	int UPDATEVIEW;

	public UserOrderAdapter(Activity context, ArrayList<UserOrdersInfo> list, Handler mHandler, int UPDATEVIEW,
			String orderFromListStatus) {
		super();
		this.context = context;
		this.list = list;
		this.mHandler = mHandler;
		this.UPDATEVIEW = UPDATEVIEW;
		this.orderFromListStatus = orderFromListStatus;
		mImageLoader = new ImageLoader(context, "listitem");
	}

	public void setList(ArrayList<UserOrdersInfo> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.user_order_items, null);
			viewHolder = new ViewHolder();
			viewHolder.userorder_item_img = (ImageView) view.findViewById(R.id.userorder_item_img);
			viewHolder.userorder_item_tv_mername = (TextView) view.findViewById(R.id.userorder_item_tv_mername);
			viewHolder.userorder_item_tv_orderstatus = (TextView) view.findViewById(R.id.userorder_item_tv_orderstatus);
			viewHolder.userorder_item_layout_waipaytimer = (LinearLayout) view
					.findViewById(R.id.userorder_item_layout_waipaytimer);
			viewHolder.userorder_item_tv_waipaytimer = (TextView) view.findViewById(R.id.userorder_item_tv_waipaytimer);
			viewHolder.userorder_item_tv_waitpaybtn = (TextView) view.findViewById(R.id.userorder_item_tv_waitpaybtn);
			viewHolder.userorder_item_layout_code = (LinearLayout) view.findViewById(R.id.userorder_item_layout_code);
			viewHolder.userorder_item_tv_code = (TextView) view.findViewById(R.id.userorder_item_tv_code);
			viewHolder.userorder_item_tv_codebiaoti = (TextView) view.findViewById(R.id.userorder_item_tv_codebiaoti);
			viewHolder.userorder_item_tv_time = (TextView) view.findViewById(R.id.userorder_item_tv_time);
			viewHolder.userorder_item_tv_num = (TextView) view.findViewById(R.id.userorder_item_tv_num);
			viewHolder.userorder_item_tv_price = (TextView) view.findViewById(R.id.userorder_item_tv_price);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		UserOrdersInfo mUserOrdersInfo = list.get(position);
		// ***************测试*****************************
		// if (position == 0) {
		// mUserOrdersInfo.setStatus(context.getResources().getString(
		// R.string.order_stayoff));
		// }
		// if (position == 1) {
		// mUserOrdersInfo.setStatus(context.getResources().getString(
		// R.string.order_stayoff));
		// }
		// if (position == 2) {
		// mUserOrdersInfo.setStatus(context.getResources().getString(
		// R.string.order_completed));
		// }
		// ********************************************
		initShowInfo(mUserOrdersInfo);
		String orderStatus = mUserOrdersInfo.getStatus();
		// 待付款
		if (orderStatus.equals(context.getResources().getString(R.string.order_putParameters_waitpay))) {
			setStartTimer(viewHolder, view, mUserOrdersInfo);
		}
		setWaitpayTvTimerShow(mUserOrdersInfo);
		orderStatus = mUserOrdersInfo.getStatus();
		changeOrderStatusNameColor(orderStatus);
		setOrderStatusName(orderStatus);
		setShowStayoffCode(mUserOrdersInfo);
		return view;
	}

	/**
	 * 初始化--显示数据
	 * 
	 * @code play_time---高尔夫、练习场显示
	 * @param mUserOrdersInfo
	 */
	private void initShowInfo(UserOrdersInfo mUserOrdersInfo) {
		String mer_item_name = mUserOrdersInfo.getMer_item_name();
		viewHolder.userorder_item_tv_mername.setText(mer_item_name);

		String day = mUserOrdersInfo.getBook_day();
		String showday = DatesUtils.getInstance().getDateGeShi(day, "yyyy-MM-dd", "MM月dd日 EE");
		showday = DatesUtils.getInstance().getZhuanHuan(showday, "星期", "周");

		String cid = mUserOrdersInfo.getCid();
		String time = mUserOrdersInfo.getItemList().get(0).getPlay_time();
		/**
		 * 高尔夫、练习场显示Play_time
		 */
		if (TextUtils.isEmpty(time)) {
			time = "";
		}
		if (cid.equals(Constans.getInstance().sportCidGolf)) {
			viewHolder.userorder_item_tv_time.setText(showday + " " + time);
		} else if (cid.equals(Constans.getInstance().sportCidGolfrange)) {
			String stime = mUserOrdersInfo.getItemList().get(0).getStart_hour();
			String etime = mUserOrdersInfo.getItemList().get(0).getEnd_hour();
			viewHolder.userorder_item_tv_time.setText(showday + " " + stime + "-" + etime);
		} else {
			viewHolder.userorder_item_tv_time.setText(showday);
		}

		String num = mUserOrdersInfo.getBook_number();
		viewHolder.userorder_item_tv_num.setText("数量" + num);

		String price = mUserOrdersInfo.getOrder_money();
		price = PriceUtils.getInstance().gteDividePrice(price, "100");
		viewHolder.userorder_item_tv_price.setText(context.getResources().getString(R.string.unit_fuhao_money) + price);

		// int he = ConfigUtils.getInstance().dip2px(context, 60);
		// int wh = ConfigUtils.getInstance().dip2px(context, 80);
		// viewHolder.userorder_item_img.setLayoutParams(new LayoutParams(wh,
		// he));
		String thumb = mUserOrdersInfo.getStadium_thumb();
		if (!TextUtils.isEmpty(thumb)) {
			mImageLoader.DisplayImage(thumb, viewHolder.userorder_item_img);
		} else {
			viewHolder.userorder_item_img.setBackgroundResource(R.drawable.listitem_none);
		}
	}

	/**
	 * 订单--设置状态ID对应的名称
	 * 
	 * @param orderStatus
	 * @return
	 */
	private String setOrderStatusName(String orderStatus) {
		String orderStatusName = orderStatus;
		if (orderStatus.equals(context.getResources().getString(R.string.order_unsubscribed))) {
			orderStatusName = context.getResources().getString(R.string.order_unsubscribed_name);
		} else if (orderStatus.equals(context.getResources().getString(R.string.order_canceled))) {
			orderStatusName = context.getResources().getString(R.string.order_canceled_name);
		} else if (orderStatus.equals(context.getResources().getString(R.string.order_putParameters_waitpay))) {
			orderStatusName = context.getResources().getString(R.string.order_waitpay_name);
		} else if (orderStatus.equals(context.getResources().getString(R.string.order_completed))) {
			orderStatusName = context.getResources().getString(R.string.order_completed_name);
		} else if (orderStatus.equals(context.getResources().getString(R.string.order_putParameters_stayoff))) {
			orderStatusName = context.getResources().getString(R.string.order_stayoff_name);
		} else if (orderStatus.equals(context.getResources().getString(R.string.order_unsubscribing))) {
			orderStatusName = context.getResources().getString(R.string.order_unsubscribing_name);
		} else if (orderStatus.equals(context.getResources().getString(R.string.order_putParameters_confirming))) {
			orderStatusName = context.getResources().getString(R.string.order_confirming_name);
		}
		viewHolder.userorder_item_tv_orderstatus.setText(orderStatusName);
		return orderStatusName;
	}

	/**
	 * 订单--根据状态改变字体颜色
	 * 
	 * @param orderStatus
	 */
	private void changeOrderStatusNameColor(String orderStatus) {
		if (orderStatus.equals(context.getResources().getString(R.string.order_unsubscribed))
				|| orderStatus.equals(context.getResources().getString(R.string.order_canceled))
				|| orderStatus.equals(context.getResources().getString(R.string.order_completed))) {
			viewHolder.userorder_item_tv_orderstatus
					.setTextColor(context.getResources().getColor(R.color.text_hint_color));
			viewHolder.userorder_item_tv_price
					.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
		} else {
			viewHolder.userorder_item_tv_orderstatus.setTextColor(context.getResources().getColor(R.color.blue));
			viewHolder.userorder_item_tv_price.setTextColor(context.getResources().getColor(R.color.btn_bg));
		}
	}

	/**
	 * 验码---待下场中非golf都显示
	 * 
	 * @param mUserOrdersInfo
	 */
	private void setShowStayoffCode(UserOrdersInfo mUserOrdersInfo) {
		String orderStatus = mUserOrdersInfo.getStatus();
		String cid = mUserOrdersInfo.getCid();
		String merid = mUserOrdersInfo.getMerid();
		if (orderStatus.equals(context.getResources().getString(R.string.order_putParameters_stayoff))
				&& !cid.equals(Constans.getInstance().sportCidGolf)) {
			ArrayList<UserOrdersInfo> itemList = mUserOrdersInfo.getItemList();
			if (itemList != null && itemList.size() > 0) {
				viewHolder.userorder_item_layout_code.setVisibility(View.VISIBLE);
				viewHolder.userorder_item_tv_codebiaoti.setVisibility(View.VISIBLE);
				StringBuffer codes = new StringBuffer();
				for (int i = 1; i <= itemList.size(); i++) {
					String cod = itemList.get(i - 1).getCode();
					String code_status = itemList.get(i - 1).getCode_status();
					String zuoString = "";
					String youString = "";
					if (code_status.equals("1")) {
						// 使用
						zuoString = "<font color='#A8A8A8'>";
						youString = "</font>";
					}
					if (cid.equals(Constans.getInstance().sportCidTennis)
							|| cid.equals(Constans.getInstance().sportCidBadminton)
							|| cid.equals(Constans.getInstance().sportTableTennis)
							|| cid.equals(Constans.getInstance().sportBasketball)) {
						// pylw 12:00-14:00 1234566u3u
						String starthour = itemList.get(i - 1).getStart_hour();
						String endhour = itemList.get(i - 1).getEnd_hour();
						if (i != itemList.size()) {
							codes.append(zuoString + starthour + "-" + endhour + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
									+ cod + "<br/><br/>" + youString);
						} else {
							codes.append(zuoString + starthour + "-" + endhour + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
									+ cod + youString);
						}
					} else {
						if (i % 2 == 0) {
							codes.append(zuoString + cod + "<br/><br/>" + youString);
						} else if (i % 2 == 0 && i == itemList.size()) {
							codes.append(zuoString + cod + youString);
						} else {
							codes.append(zuoString + cod + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + youString);
						}
					}
				}
				viewHolder.userorder_item_tv_code.setText(Html.fromHtml(codes + ""));
			}
		} else if (orderStatus.equals(context.getResources().getString(R.string.order_completed))
				&& merid.equals(Constans.getInstance().shenghuo)) {
			// 已完成 生活
			ArrayList<UserOrdersInfo> itemList = mUserOrdersInfo.getItemList();
			if (itemList != null && itemList.size() > 0) {
				viewHolder.userorder_item_layout_code.setVisibility(View.VISIBLE);
				viewHolder.userorder_item_tv_codebiaoti.setVisibility(View.INVISIBLE);
				StringBuffer codes = new StringBuffer();
				for (int i = 1; i <= itemList.size(); i++) {
					String accounttext = itemList.get(i - 1).getAccount_text();
					if (i == itemList.size()) {
						codes.append(accounttext);
					} else {
						codes.append(accounttext + "<br/><br/>");
					}

				}
				viewHolder.userorder_item_tv_code.setText(Html.fromHtml(codes + ""));
			}
		} else {
			viewHolder.userorder_item_layout_code.setVisibility(View.GONE);
		}
	}

	/**
	 * 待付款--倒计时textview显示状态
	 */
	private void setWaitpayTvTimerShow(UserOrdersInfo mUserOrdersInfo) {
		String orderStatus = mUserOrdersInfo.getStatus();
		String merid = mUserOrdersInfo.getMerid();
		if (orderStatus.equals(context.getResources().getString(R.string.order_putParameters_waitpay))) {
			viewHolder.userorder_item_tv_orderstatus.setVisibility(View.GONE);
			viewHolder.userorder_item_layout_waipaytimer.setVisibility(View.VISIBLE);
			viewHolder.userorder_item_tv_waitpaybtn.setVisibility(View.VISIBLE);
			viewHolder.userorder_item_tv_waitpaybtn.setText("支付");
			viewHolder.userorder_item_tv_waitpaybtn.setTextColor(context.getResources().getColor(R.color.btn_bg));
			viewHolder.userorder_item_tv_waitpaybtn.setBackgroundResource(R.drawable.user_order_waitpay_corners_bg);
		} else if (orderStatus.equals(context.getResources().getString(R.string.order_completed))
				&& merid.equals(Constans.getInstance().shenghuo)) {
			viewHolder.userorder_item_tv_waitpaybtn.setVisibility(View.VISIBLE);
			viewHolder.userorder_item_layout_waipaytimer.setVisibility(View.GONE);
			viewHolder.userorder_item_tv_waitpaybtn.setText("立即使用");
			viewHolder.userorder_item_tv_waitpaybtn.setTextColor(context.getResources().getColor(R.color.white));
			viewHolder.userorder_item_tv_waitpaybtn.setBackgroundResource(R.drawable.user_order_waitpay_corners_bgshi);
			viewHolder.userorder_item_tv_waitpaybtn.setTag(mUserOrdersInfo);
			viewHolder.userorder_item_tv_waitpaybtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					UserOrdersInfo mUserOrdersInfo = (UserOrdersInfo) v.getTag();
					String orderStatus = mUserOrdersInfo.getStatus();
					String merid = mUserOrdersInfo.getMerid();
					if (orderStatus.equals(context.getResources().getString(R.string.order_completed))
							&& merid.equals(Constans.getInstance().shenghuo)) {
						Intent intent = new Intent(context, UserOrderShengHuoActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("account", mUserOrdersInfo.getItemList().get(0).getAccount());
						intent.putExtra("life_type", mUserOrdersInfo.getLife_type());
						context.startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, context);
					}
				}
			});
		} else {
			viewHolder.userorder_item_tv_orderstatus.setVisibility(View.VISIBLE);
			viewHolder.userorder_item_layout_waipaytimer.setVisibility(View.GONE);
			viewHolder.userorder_item_tv_waitpaybtn.setVisibility(View.GONE);
		}
	}

	/**
	 * 待付款--开始倒计时
	 */
	private void setStartTimer(final ViewHolder viewHolder, View view, UserOrdersInfo mUserOrdersInfo) {
		int servicecurrenttimestamp = mUserOrdersInfo.getCurrent_time();
		int createtimestamp = mUserOrdersInfo.getCreate_time();
		long[] shengYuTime = DatesUtils.getInstance().getCountdownTimesArray(servicecurrenttimestamp, createtimestamp);
		// *****************************
		// shengYuTime[0] = 00;
		// shengYuTime[1] = 50;
		// *********************************
		viewHolder.shengYuTime = shengYuTime;
		if (shengYuTime[1] >= 0 && shengYuTime[2] >= 0) {
			String remainingTime = DatesUtils.getInstance().getCountdownTimes(shengYuTime);
			viewHolder.userorder_item_tv_waipaytimer.setText(remainingTime);
			viewHolder.mUserOrdersInfo = mUserOrdersInfo;
			Timer timer;
			if (viewHolder.timer != null) {
				timer = viewHolder.timer;
			} else {
				timer = new Timer();
				viewHolder.timer = timer;
				/**
				 * 防止每次进来开的倒计时都会后台计时-------------1、重新进来时关闭所有-------------------
				 */
				CommonUtils.getInstance().addTimer(viewHolder);
				viewHolder.userorder_item_tv_waipaytimer.addTextChangedListener(new TextWatcher() {
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						if (viewHolder.time == 1) {
							// CommonUtils.getInstance().initToast(context,
							// "删除");
							// viewHolder.temai_timeTextView.setVisibility(View.GONE);
							viewHolder.stateChange = false;
							viewHolder.time = -1;
							if (!TextUtils.isEmpty(orderFromListStatus) && orderFromListStatus
									.equals(context.getResources().getString(R.string.order_putParameters_waitpay))) {
								/**
								 * 待付款列表--直接改变状态
								 */
								viewHolder.mUserOrdersInfo
										.setStatus(context.getResources().getString(R.string.order_canceled));
								viewHolder.userorder_item_layout_waipaytimer.setVisibility(View.INVISIBLE);
								if (list.contains(viewHolder.mUserOrdersInfo)) {
									list.remove(viewHolder.mUserOrdersInfo);
								}
							} else {
								/**
								 * 全部订单列表--直接改变状态
								 */
								viewHolder.userorder_item_layout_waipaytimer.setVisibility(View.INVISIBLE);
								viewHolder.mUserOrdersInfo
										.setStatus(context.getResources().getString(R.string.order_canceled));
							}

							// ---------------倒计时结束，更新view---------------------------
							mHandler.sendEmptyMessage(UPDATEVIEW);
						}
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});
			}
			new ProgressTimer(view, timer);
		} else {
			viewHolder.userorder_item_layout_waipaytimer.setVisibility(View.INVISIBLE);
			mUserOrdersInfo.setStatus(context.getResources().getString(R.string.order_canceled));
		}
	}

	public class ViewHolder {
		public ImageView userorder_item_img;
		public TextView userorder_item_tv_mername;
		public TextView userorder_item_tv_orderstatus;
		public TextView userorder_item_tv_time;
		public TextView userorder_item_tv_num;
		public TextView userorder_item_tv_price;

		public LinearLayout userorder_item_layout_code;
		public TextView userorder_item_tv_code;
		public TextView userorder_item_tv_codebiaoti;

		public LinearLayout userorder_item_layout_waipaytimer;
		public TextView userorder_item_tv_waipaytimer;
		public TextView userorder_item_tv_waitpaybtn;
		public long[] shengYuTime;
		public UserOrdersInfo mUserOrdersInfo;
		public Timer timer;
		public int time;
		public boolean stateChange = true;
	}

}
