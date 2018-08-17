package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.business.CreateOrdersBusiness;
import com.bestdo.bestdoStadiums.business.CreateOrdersGetPriceBusiness;
import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness;
import com.bestdo.bestdoStadiums.business.CreateOrdersGetPriceBusiness.CreateOrdersGetPriceCallback;
import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness.GetMemberInfoCallback;
import com.bestdo.bestdoStadiums.business.UserOrderDetailsBusiness;
import com.bestdo.bestdoStadiums.business.CreateOrdersBusiness.GetCreateOrdersCallback;
import com.bestdo.bestdoStadiums.business.UserOrderDetailsBusiness.GetUserOrderDetailsCallback;
import com.bestdo.bestdoStadiums.model.CreatOrderGetDefautCardInfo;
import com.bestdo.bestdoStadiums.model.CreatOrderGetMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.CreateOrderItemInfo;
import com.bestdo.bestdoStadiums.model.UserCenterMemberInfo;
import com.bestdo.bestdoStadiums.model.UserOrderDetailsInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.PayDialog;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-26 下午3:48:57
 * @Description 类描述：创建订单
 */
/**
 * @author qyy
 * 
 */
public class CreatOrdersActivity extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout page_top_layout;
	private LinearLayout pagetop_layout_back;

	private TextView userorderdetails_stadiuminfo_tv_stadiumname;
	private TextView userorderdetails_stadiuminfo_tv_date;
	private TextView userorderdetails_stadiuminfo_tv_numtitle;
	private TextView userorderdetails_stadiuminfo_tv_num;
	private TextView userorderdetails_stadiuminfo_tv_servertitle;
	private TextView userorderdetails_stadiuminfo_tv_server;
	private TextView userorderdetails_stadiuminfo_tv_address;

	private RelativeLayout createorder_relay_pepolenum;
	private LinearLayout createorder_layout_name;
	private TextView createorder_tv_pepolenumtitle;
	private TextView createorder_tv_pepolenum;
	private SupplierEditText createorder_edt_phone;
	private SupplierEditText createorder_edt_name;

	private RelativeLayout createorder_relay_usekaquan;
	private TextView createorder_tv_kaquanNum;

	private TextView payinfo_tv_orderallmoney;
	private TextView payinfo_tv_zhdeduction;
	private TextView payinfo_tv_paymoney;

	private TextView createorder_tv_bottom;

	/**
	 * 参数
	 */
	private String source;
	private String uid;// 用户id
	private String card_id = "";
	private String account_no = "";
	private String cid;
	private String mer_item_id;
	private String book_day;// 预定日期

	private String hour;
	private String price_id;
	private String mer_price_id;
	private String price_id_type;
	private String priceidtype_menshijia = "price_menshijia";
	private String priceidtype_banlance = "price_banlance";
	private String priceidtype_nobanlance = "price_nobanlance";
	private String merid;
	private String stadium_name;
	private String stadium_id = "";
	private String contain;
	private String address;
	private String remind;// 联系人手机号
	private String[] nameString;
	private String starthour;
	private String endhour;
	private String itemspiece;

	private String cardName;
	private String balance = "0.0";

	private String allorder_money;
	private String allvip_money;
	private String allpay_money;

	private String order_money = "";// 默认价
	private String vip_money;
	private String reduce_money;

	private String play_time;// golf意向打球时间
	private String book_shiduan;// pylw

	ArrayList<CreatOrderGetMerItemPriceInfo> mMerItemPriceInfoList;
	HashMap<String, String> mhashmap;

	ArrayList<CreatOrderGetDefautCardInfo> mList;
	private int selectcardindex;
	private ProgressDialog mDialog;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Intent updatevenuePiecesIntents;
	private int selectNum = 1;// 人数
	private String oid;
	private TextView createorder_tv_numdes;
	private TextView createorder_tv_numadd;
	private String default_price_id;
	private String accountType;
	private String process_type;
	/**
	 * 减免类型 ：1 减免到reducedAfterprice；0 减去reducedAfterprice
	 */
	private String isReduced;
	/**
	 * 卡有效次数
	 */
	private String main_deduct_time;
	private String reducedAfterprice;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.createorder_relay_usekaquan:
			Intent intent = new Intent(CreatOrdersActivity.this, CreateOrderSelectCardsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("uid", uid);
			intent.putExtra("merid", merid);
			intent.putExtra("book_day", book_day);
			intent.putExtra("stadium_id", stadium_id);
			intent.putExtra("main_deduct_time", main_deduct_time);
			intent.putExtra("intent_from", "CreateOrderSelectCardsActivity");
			startActivityForResult(intent, 1);
			CommonUtils.getInstance().setPageIntentAnim(intent, CreatOrdersActivity.this);
			break;
		case R.id.createorder_tv_bottom:
			mHandler.sendEmptyMessage(CREATEORDER);
			break;
		case R.id.createorder_tv_numadd:
			if (selectNum < 5) {
				CommonUtils.getInstance().closeSoftInput(this);
				setLoseFocusable();
				selectNum++;
				itemsLenght = selectNum;
				createorder_tv_pepolenum.setText("" + selectNum);
				updateShowPayInfo();
			} else {
				if (cid.equals(Constans.getInstance().sportCidGolf)) {
					CommonUtils.getInstance().initToast(context, "打球人数不能大于5");
				} else {
					CommonUtils.getInstance().initToast(context, "购买数量不能大于5");
				}
			}
			break;
		case R.id.createorder_tv_numdes:
			if (selectNum > 1) {
				CommonUtils.getInstance().closeSoftInput(this);
				setLoseFocusable();
				selectNum--;
				itemsLenght = selectNum;
				createorder_tv_pepolenum.setText("" + selectNum);
				updateShowPayInfo();
			} else {
				if (cid.equals(Constans.getInstance().sportCidGolf)) {
					CommonUtils.getInstance().initToast(context, "打球人数不能小于1");
				} else {
					CommonUtils.getInstance().initToast(context, "购买数量不能小于1");
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.creat_orders);
		CommonUtils.getInstance().addActivity(this);
		// 返回乒羽篮网选择场地片数时更新场地信息（只有当点击创建订单后）
		updatevenuePiecesIntents = new Intent();
		updatevenuePiecesIntents.setAction(getString(R.string.action_updateDetailVenueOrPYLWVenuePieces));
	}

	@Override
	protected void findViewById() {
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

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

		createorder_tv_pepolenumtitle = (TextView) findViewById(R.id.createorder_tv_pepolenumtitle);
		createorder_layout_name = (LinearLayout) findViewById(R.id.createorder_layout_name);
		createorder_relay_pepolenum = (RelativeLayout) findViewById(R.id.createorder_relay_pepolenum);
		createorder_tv_pepolenum = (TextView) findViewById(R.id.createorder_tv_pepolenum);
		createorder_edt_phone = (SupplierEditText) findViewById(R.id.createorder_edt_phone);
		createorder_edt_name = (SupplierEditText) findViewById(R.id.createorder_edt_name);

		createorder_tv_numdes = (TextView) findViewById(R.id.createorder_tv_numdes);
		createorder_tv_numadd = (TextView) findViewById(R.id.createorder_tv_numadd);

		createorder_relay_usekaquan = (RelativeLayout) findViewById(R.id.createorder_relay_usekaquan);
		createorder_tv_kaquanNum = (TextView) findViewById(R.id.createorder_tv_kaquanNum);

		payinfo_tv_memberrelief = (TextView) findViewById(R.id.payinfo_tv_memberrelief);
		payinfo_tv_orderallmoney = (TextView) findViewById(R.id.payinfo_tv_orderallmoney);
		payinfo_tv_zhdeduction = (TextView) findViewById(R.id.payinfo_tv_zhdeduction);
		payinfo_tv_paymoney = (TextView) findViewById(R.id.payinfo_tv_paymoney);

		createorder_tv_bottom = (TextView) findViewById(R.id.createorder_tv_bottom);

	}

	@Override
	protected void setListener() {
		showDilag();
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		pagetop_tv_name.setText(getResources().getString(R.string.creat_orders_title));
		pagetop_layout_back.setOnClickListener(this);
		createorder_relay_usekaquan.setOnClickListener(this);
		createorder_tv_bottom.setOnClickListener(this);
		initDate();

	}

	private void initDate() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		process_type = bestDoInfoSharedPrefs.getString("process_type", "");
		uid = bestDoInfoSharedPrefs.getString("uid", "");// 用户id
		remind = bestDoInfoSharedPrefs.getString("mobile", "");// 联系人手机号
		cid = bestDoInfoSharedPrefs.getString("cid", "");// 运动类型
		hour = bestDoInfoSharedPrefs.getString("hour", "");
		book_day = bestDoInfoSharedPrefs.getString("book_day", "");// 预定日期
		source = bestDoInfoSharedPrefs.getString("source", "");
		merid = bestDoInfoSharedPrefs.getString("merid", "");
		mer_item_id = bestDoInfoSharedPrefs.getString("mer_item_id", "");
		mer_price_id = bestDoInfoSharedPrefs.getString("mer_price_id", "");
		price_id_type = priceidtype_menshijia;
		stadium_name = bestDoInfoSharedPrefs.getString("stadium_name", "");
		contain = bestDoInfoSharedPrefs.getString("contain", "");
		address = bestDoInfoSharedPrefs.getString("address", "");
		reduce_money = "0";
		play_time = bestDoInfoSharedPrefs.getString("play_time", "");// 意向打球时间
		if (cid.equals(Constans.getInstance().sportCidTennis) || cid.equals(Constans.getInstance().sportCidBadminton)
				|| cid.equals(Constans.getInstance().sportTableTennis)
				|| cid.equals(Constans.getInstance().sportBasketball)) {
			book_shiduan = bestDoInfoSharedPrefs.getString("book_shiduan", "");
			itemspiece = bestDoInfoSharedPrefs.getString("itemspiece", "");
		} else {
			starthour = bestDoInfoSharedPrefs.getString("start_hour", "");
			endhour = bestDoInfoSharedPrefs.getString("end_hour", "");
		}

		/**
		 * 
		 */
		userorderdetails_stadiuminfo_tv_stadiumname.setText(stadium_name);
		userorderdetails_stadiuminfo_tv_numtitle.setVisibility(View.GONE);
		userorderdetails_stadiuminfo_tv_num.setVisibility(View.GONE);
		userorderdetails_stadiuminfo_tv_address.setText(address);
		String showday = DatesUtils.getInstance().getDateGeShi(book_day, "yyyy-MM-dd", "MM月dd日 EE");
		if (showday.contains("星期")) {
			showday = showday.replace("星期", "周");
		}
		userorderdetails_stadiuminfo_tv_date.setText(showday);
		userorderdetails_stadiuminfo_tv_server.setText(contain);
		if (cid.equals(Constans.getInstance().sportCidGolf)) {
			userorderdetails_stadiuminfo_tv_date.setText(showday + "  " + play_time);
			createorder_tv_numadd.setOnClickListener(this);
			createorder_tv_numdes.setOnClickListener(this);
			createorder_tv_pepolenum.setText("" + selectNum);
		} else if (cid.equals(Constans.getInstance().sportCidTennis)
				|| cid.equals(Constans.getInstance().sportCidBadminton)
				|| cid.equals(Constans.getInstance().sportTableTennis)
				|| cid.equals(Constans.getInstance().sportBasketball)) {
			userorderdetails_stadiuminfo_tv_servertitle
					.setText(getResources().getString(R.string.userOrdersDetails_times));
			userorderdetails_stadiuminfo_tv_server.setText(book_shiduan);
			createorder_relay_pepolenum.setVisibility(View.GONE);
			createorder_layout_name.setVisibility(View.GONE);
		} else {
			if (cid.equals(Constans.getInstance().sportCidGolfrange)
					|| merid.equals(Constans.getInstance().sportMeridHighswimming)) {
				userorderdetails_stadiuminfo_tv_date.setText(showday + "  " + play_time);
			}
			createorder_layout_name.setVisibility(View.GONE);
			createorder_tv_pepolenumtitle.setText("购买数量");
			// ---------------
			createorder_tv_numadd.setOnClickListener(this);
			createorder_tv_numdes.setOnClickListener(this);
			createorder_tv_pepolenum.setText("" + selectNum);
		}
		createorder_edt_phone.setText(remind);
		setLoseFocusable();

	}

	int cardsNumber = 0;
	/**
	 * 场片个数 或 场地数量
	 */
	protected int itemsLenght;
	protected String[] itemspieceinfo;
	private TextView payinfo_tv_memberrelief;
	protected String identityId;
	protected String discountBook;
	protected String defaultCount = "1";

	protected void processLogic() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		new GetMemberInfoBusiness(this, mhashmap, new GetMemberInfoCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					UserCenterMemberInfo muserCenterMemberInfo = (UserCenterMemberInfo) dataMap
							.get("userCenterMemberInfo");
					if (muserCenterMemberInfo != null) {
						identityId = muserCenterMemberInfo.getIdentityId();
						if (!TextUtils.isEmpty(identityId)
								&& !identityId.equals(Constans.getInstance().identityCommomUser)) {
							// 会员
							discountBook = muserCenterMemberInfo.getDiscountBook();

						}
						defaultCount = muserCenterMemberInfo.getDefaultCount();
					}
					mHandler.sendEmptyMessage(CREATEORDERMERTEMPRICE);
				} else {
					CommonUtils.getInstance().setOnDismissDialog(mDialog);
				}

			}
		});
	}

	protected void processLogicLLL() {
		// 用户ID：uid（必填）
		// 商品ID：merid（必填）
		// 商品明细ID：mer_item_id（必填）
		// 预定日期：date 例：2016-07-26（必填）
		// 预定时段：hours（必填）
		// 权益卡下标：card_index（非必填）
		member_reduce_money = "0";// 每次请求初始化会员减免
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("merid", merid);
		mhashmap.put("mer_item_id", mer_item_id);
		mhashmap.put("date", book_day);
		mhashmap.put("hours", hour);
		if (useCardStatus) {
			mhashmap.put("card_index", selectcardindex + "");
		} else {

		}
		System.err.println(mhashmap);
		new CreateOrdersGetPriceBusiness(this, mhashmap, new CreateOrdersGetPriceCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null && dataMap.get("status").equals("200")) {
					int timestamp = (Integer) dataMap.get("timestamp");
					showDialogProcessType(timestamp);
					/**
					 * 注释：1.先取默认价格id，直接取默认价
					 */
					mMerItemPriceInfoList = (ArrayList<CreatOrderGetMerItemPriceInfo>) dataMap.get("mList");
					if (mMerItemPriceInfoList == null) {
						mMerItemPriceInfoList = new ArrayList<CreatOrderGetMerItemPriceInfo>();
					}
					for (int i = 0; i < mMerItemPriceInfoList.size(); i++) {
						default_price_id = mMerItemPriceInfoList.get(i).getMer_price_id();
						accountType = mMerItemPriceInfoList.get(i).getAccountType();
						isReduced = mMerItemPriceInfoList.get(i).getIsReduced();
						main_deduct_time = mMerItemPriceInfoList.get(i).getMain_deduct_time();
						reducedAfterprice = mMerItemPriceInfoList.get(i).getReducedAfterprice();// 减免到价格
						stadium_id = mMerItemPriceInfoList.get(i).getStadium_id();

						// 原价乘以系数
						String prepay_price = mMerItemPriceInfoList.get(i).getPrepay_price();
						String balance_price = mMerItemPriceInfoList.get(i).getBalance_price();
						String nobalance_price = mMerItemPriceInfoList.get(i).getNobalance_price();

						/**
						 * 现金卡乘以默认价系数
						 */

						if (!accountType.equals("TIMES")) {
							prepay_price = PriceUtils.getInstance().gteMultiplySumPrice(prepay_price, defaultCount);
							balance_price = PriceUtils.getInstance().gteMultiplySumPrice(balance_price, defaultCount);
							nobalance_price = PriceUtils.getInstance().gteMultiplySumPrice(nobalance_price,
									defaultCount);
							mMerItemPriceInfoList.get(i).setPrepay_price(prepay_price);
							mMerItemPriceInfoList.get(i).setNobalance_price(nobalance_price);
							mMerItemPriceInfoList.get(i).setBalance_price(balance_price);
						}

					}
					/**
					 * 注释：2.没有卡券使用默认价，有重新调用有无余额价格
					 */
					order_money = getBackPrice(mMerItemPriceInfoList, price_id_type);
					/**
					 * 价格ID初始为默认价格id
					 */
					price_id = default_price_id;

					String cardsNumber_ = (String) dataMap.get("cardsNumber");
					cardsNumber = Integer.valueOf(cardsNumber_);
					cardName = (String) dataMap.get("accountName");// 权益卡名称

					balance = (String) dataMap.get("balance");// 权益卡余额

					account_no = (String) dataMap.get("accountNo");// 权益卡好
					card_id = (String) dataMap.get("cardId");// 权益卡id

					if (cid.equals(Constans.getInstance().sportCidTennis)
							|| cid.equals(Constans.getInstance().sportCidBadminton)
							|| cid.equals(Constans.getInstance().sportTableTennis)
							|| cid.equals(Constans.getInstance().sportBasketball)) {

						/**
						 * 乒羽篮网
						 */
						itemspieceinfo = itemspiece.split("!");
						itemsLenght = itemspieceinfo.length;
					} else {
						/**
						 * 高尔夫练习场游泳健身
						 */
						itemsLenght = selectNum;
					}
					if (cardsNumber > 0) {// 有卡时
						if (accountType.equals("TIMES")) {// 是次卡
							balance = PriceUtils.getInstance().gteMultiplySumPrice("10", balance);
							balance = PriceUtils.getInstance().seePrice(balance);

						}
						getCreateOrderMerItemPrice2();
					} else {
						useCardStatus = false;

						updateShowPayInfo();
					}

				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	private void getCreateOrderMerItemPrice2() {

		if (!accountType.equals("TIMES")) {
			// 现金卡
			if (Double.valueOf(balance) > 0) {

				for (int i = 0; i < mMerItemPriceInfoList.size(); i++) {
					price_id = mMerItemPriceInfoList.get(i).getBalance_mer_price_id();
				}
				price_id_type = priceidtype_banlance;

			} else {
				for (int i = 0; i < mMerItemPriceInfoList.size(); i++) {
					price_id = mMerItemPriceInfoList.get(i).getNobalance_mer_price_id();
				}
				price_id_type = priceidtype_nobanlance;
			}
		}
		updateShowPayInfo();
	}

	private void showDialogProcessType(int timestamp) {
		if (!process_type.equals(Constans.getInstance().stadiumUsedManage)) {
			String hour = DatesUtils.getInstance().getTimeStampToDate(timestamp, "H");
			int hours = Integer.parseInt(hour);
			String title = "";
			if (hours <= 9) {
				title = "该场地需人工预订，预计<font color='#FC821B'>上午10:00</font>前反馈预订结果。";
				showDialogProcessType(title);
			} else if (hours >= 17) {
				title = "该场地需人工预订，预计次日<font color='#FC821B'>上午10:00</font>前反馈预订结果。";
				showDialogProcessType(title);
			}

		}
	}

	public void showDialogProcessType(String title) {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_stadiumdatetishi);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView stadiumshishi_tishi = (TextView) selectDialog.findViewById(R.id.stadiumshishi_tishi);
		TextView stadiumshishi_tv_sure = (TextView) selectDialog.findViewById(R.id.stadiumshishi_tv_sure);
		stadiumshishi_tishi.setText(Html.fromHtml(title));
		stadiumshishi_tishi.setPadding(ConfigUtils.getInstance().dip2px(context, 20),
				ConfigUtils.getInstance().dip2px(context, 20), ConfigUtils.getInstance().dip2px(context, 20), 0);
		stadiumshishi_tv_sure.setText("我知道了");
		stadiumshishi_tv_sure.setTextColor(getResources().getColor(R.color.text_contents_color));
		stadiumshishi_tv_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
	}

	/**
	 * 计算会员减免
	 * 
	 * @param price
	 */
	private void setmemberPrice(String price) {
		if (!accountType.equals("TIMES") && !TextUtils.isEmpty(identityId)
				&& !identityId.equals(Constans.getInstance().identityCommomUser)) {
			// 会员
			if (cid.equals(Constans.getInstance().sportCidTennis)
					|| cid.equals(Constans.getInstance().sportCidBadminton)
					|| cid.equals(Constans.getInstance().sportTableTennis)
					|| cid.equals(Constans.getInstance().sportBasketball)) {
				for (int i = 0; i < itemsLenght; i++) {
					String ordermoney;
					if (price_id_type.equals(priceidtype_banlance)) {
						ordermoney = mMerItemPriceInfoList.get(i).getBalance_price();
					} else if (price_id_type.equals(priceidtype_nobanlance)) {
						ordermoney = mMerItemPriceInfoList.get(i).getNobalance_price();
					} else {
						ordermoney = mMerItemPriceInfoList.get(i).getPrepay_price();
					}

					String member_reduce_moneyone = getMemberreducemoneyone(ordermoney);
					member_reduce_money = PriceUtils.getInstance().gteAddSumPrice(member_reduce_money,
							member_reduce_moneyone);
				}
			} else {
				String menberordermoney = PriceUtils.getInstance().gteDividePrice(price, defaultCount);
				String memberreducemoneyone = PriceUtils.getInstance().gteSubtractSumPrice(menberordermoney, price);
				memberreducemoneyone = PriceUtils.getInstance()
						.getPriceTwoDecimalDown(Double.valueOf(memberreducemoneyone), 2);
				memberreducemoneyone = PriceUtils.getInstance().seePrice(memberreducemoneyone);
				member_reduce_money = PriceUtils.getInstance().gteMultiplySumPrice("" + selectNum,
						memberreducemoneyone);
				member_reduce_money = PriceUtils.getInstance().seePrice(member_reduce_money);
			}

		} else {
			member_reduce_money = "0";
		}
		payinfo_tv_memberrelief.setText(getResources().getString(R.string.unit_fuhao_money) + member_reduce_money);
	}

	/**
	 * 会员减免
	 */
	String member_reduce_money = "0";

	private void initPrice() {
		if (cardsNumber > 0 && useCardStatus) {

			// 次卡
			if (accountType.equals("TIMES")) {

				timesIninPrice();

				setmemberPrice("");
			} else {

				cashInitPrice();
				setmemberPrice(vip_money);
			}

		} else {
			setmemberPrice(order_money);
			price_id_type = priceidtype_menshijia;
			card_id = "";
			account_no = "";
			vip_money = "0";
			reduce_money = "0";
			if (cardsNumber > 0) {
				createorder_tv_kaquanNum.setText("不使用卡券");
			} else {
				createorder_tv_kaquanNum

						.setText(getString(R.string.createorder_setcard_tv));
			}
		}

	}

	private Double useBalance = 0.0;
	Double balance_ = 0.0;

	/**
	 * 次卡计算价格
	 */
	private void timesIninPrice() {

		balance_ = Double.valueOf(balance).doubleValue();

		Double main_deduct_time_ = Double.valueOf(main_deduct_time);
		Double allpay_money_ = 0.0;
		Double allvip_money_ = 0.0;
		int index = 0;
		for (int i = 0; i < itemsLenght; i++) {
			if (cid.equals(Constans.getInstance().sportCidTennis)
					|| cid.equals(Constans.getInstance().sportCidBadminton)
					|| cid.equals(Constans.getInstance().sportTableTennis)
					|| cid.equals(Constans.getInstance().sportBasketball)) {
				/**
				 * 乒羽篮网
				 */
				index = i;
			} else {
				/**
				 * 高尔夫练习场游泳健身
				 */
				index = 0;
			}
			if (isReduced.equals("1")) {// 减免到
				if (balance_ >= Double.valueOf(main_deduct_time)) {
					mer_price_id = mMerItemPriceInfoList.get(index).getMer_price_id();
					vip_money = mMerItemPriceInfoList.get(index).getReducedAfterprice();
					allvip_money = mMerItemPriceInfoList.get(index).getReducedAfterprice();
					allpay_money = mMerItemPriceInfoList.get(index).getReducedAfterprice();
					useBalance += main_deduct_time_;
					balance_ -= main_deduct_time_;
				} else {
					vip_money = mMerItemPriceInfoList.get(index).getPrepay_price();
					allvip_money = mMerItemPriceInfoList.get(index).getPrepay_price();
					allpay_money = allvip_money;
				}

				allpay_money_ += Double.valueOf(allpay_money);
				allpay_money = allpay_money_ + "";

				allvip_money_ += Double.valueOf(allvip_money);
				allvip_money = allpay_money_ + "";
			} else {// 减免
				if (balance_ >= Double.valueOf(main_deduct_time)) {
					mer_price_id = mMerItemPriceInfoList.get(index).getMer_price_id();
					vip_money = mMerItemPriceInfoList.get(index).getBalance_price();
					allvip_money = mMerItemPriceInfoList.get(index).getBalance_price();
					allpay_money = allvip_money;
					useBalance += main_deduct_time_;
					balance_ -= main_deduct_time_;
				} else {
					vip_money = mMerItemPriceInfoList.get(index).getPrepay_price();
					allvip_money = mMerItemPriceInfoList.get(index).getPrepay_price();
					allpay_money = allvip_money;
				}

				allpay_money_ += Double.valueOf(allpay_money);
				allpay_money = allpay_money_ + "";

				allvip_money_ += Double.valueOf(allvip_money);
				allvip_money = allpay_money_ + "";
			}
		}
		createorder_tv_kaquanNum.setText(cardName);
	}

	/**
	 * 现金卡计算价格
	 */
	private void cashInitPrice() {
		mer_price_id = mMerItemPriceInfoList.get(0).getMer_price_id();
		if (price_id_type.equals(priceidtype_menshijia)) {
			reduce_money = "0";
		} else if (price_id_type.equals(priceidtype_banlance)) {
			vip_money = getBackPrice(mMerItemPriceInfoList, price_id_type);
			/**
			 * 计算余额
			 */
			allvip_money = PriceUtils.getInstance().gteMultiplySumPrice("" + selectNum, vip_money);
			calculateDiscountInit();
		} else if (price_id_type.equals(priceidtype_nobanlance)) {
			reduce_money = "0";
			vip_money = getBackPrice(mMerItemPriceInfoList, price_id_type);
			allvip_money = PriceUtils.getInstance().gteMultiplySumPrice("" + selectNum, vip_money);
			createorder_tv_kaquanNum.setText(cardName);
		}
	}

	/**
	 * 计算折扣
	 */
	private void calculateDiscountInit() {
		String allvipmoney = PriceUtils.getInstance().gteSubtractSumPrice(member_reduce_money, allvip_money);
		allvipmoney = PriceUtils.getInstance().seePrice(allvipmoney);
		String subtract = PriceUtils.getInstance().gteSubtractSumPrice(allvipmoney, balance);
		if (Double.valueOf(subtract) >= 0) {
			reduce_money = PriceUtils.getInstance().gteSubtractSumPrice(member_reduce_money, allvip_money);
			reduce_money = PriceUtils.getInstance().seePrice(reduce_money);
		} else {
			reduce_money = balance;
			subtract = "0";
		}
		if (!price_id_type.equals(priceidtype_menshijia)) {
			createorder_tv_kaquanNum.setText(cardName);
		}
	}

	private String getBackPrice(ArrayList<CreatOrderGetMerItemPriceInfo> mMerItemPriceInfoList, String price_id_type) {
		String order_money = "0";
		String pay_price = "";

		for (int i = 0; i < mMerItemPriceInfoList.size(); i++) {
			if (price_id_type.equals(priceidtype_menshijia)) {
				pay_price = mMerItemPriceInfoList.get(i).getPrepay_price();
			} else if (price_id_type.equals(priceidtype_banlance)) {
				pay_price = mMerItemPriceInfoList.get(i).getBalance_price();
			} else if (price_id_type.equals(priceidtype_nobanlance)) {
				pay_price = mMerItemPriceInfoList.get(i).getNobalance_price();
			}

			order_money = PriceUtils.getInstance().gteAddSumPrice(pay_price, order_money);
		}
		return order_money;
	}

	private void updateShowPayInfo() {

		initPrice();
		/**
		 * 根据人数计算总价ss
		 */
		allorder_money = PriceUtils.getInstance().gteMultiplySumPrice("" + selectNum, order_money);
		allorder_money = PriceUtils.getInstance().seePrice(allorder_money);
		payinfo_tv_orderallmoney.setText(getResources().getString(R.string.unit_fuhao_money) + allorder_money);

		if (cardsNumber > 0 && useCardStatus) {
			if (accountType.equals("TIMES")) {// 次卡

				reduce_money = PriceUtils.getInstance().gteSubtractSumPrice(allvip_money, allorder_money);
				payinfo_tv_zhdeduction.setText(getResources().getString(R.string.unit_fuhao_money)
						+ PriceUtils.getInstance().seePrice(reduce_money) + "(" + "使用"
						+ PriceUtils.getInstance().seePrice("" + useBalance) + "次权益)");
				useBalance = 0.0;

			} else {// 现金卡

				calculateDiscountInit();
				if (price_id_type.equals(priceidtype_menshijia)) {
					allpay_money = PriceUtils.getInstance().gteSubtractSumPrice(reduce_money, allorder_money);
				} else {
					allpay_money = PriceUtils.getInstance().gteSubtractSumPrice(reduce_money, allvip_money);
				}
				allpay_money = PriceUtils.getInstance().seePrice(allpay_money);
				payinfo_tv_zhdeduction.setText(getResources().getString(R.string.unit_fuhao_money) + reduce_money);

			}

		} else {
			// 不用卡或没卡
			reduce_money = "0";
			payinfo_tv_zhdeduction.setText(getResources().getString(R.string.unit_fuhao_money) + reduce_money);
			if (price_id_type.equals(priceidtype_menshijia)) {
				allpay_money = PriceUtils.getInstance().gteSubtractSumPrice(reduce_money, allorder_money);
			}

		}

		allpay_money = PriceUtils.getInstance().gteSubtractSumPrice(member_reduce_money, allpay_money);
		allpay_money = PriceUtils.getInstance().seePrice(allpay_money);
		payinfo_tv_paymoney.setText("实付款：" + getResources().getString(R.string.unit_fuhao_money) + allpay_money);
	}

	/**
	 * 设置输入框失去焦点
	 */
	private void setLoseFocusable() {
		// ------阻止EditText得到焦点，以防输入法弄丑画面
		userorderdetails_stadiuminfo_tv_address.setFocusable(true);
		userorderdetails_stadiuminfo_tv_address.setFocusableInTouchMode(true);
		userorderdetails_stadiuminfo_tv_address.requestFocus();
		// 初始不让EditText得焦点搜索,让焦点指到任一个textView1中
		userorderdetails_stadiuminfo_tv_address.requestFocusFromTouch();
	}

	/**
	 * 选择卡券
	 */
	private final int SELECTCARD = 1;
	/**
	 * 创建订单
	 */
	private final int CREATEORDER = 2;
	/**
	 * 某天指定小时商品明细的价格
	 */
	private final int CREATEORDERMERTEMPRICE = 3;

	/**
	 * 加载Dialog
	 */
	private final int SHOWPAYDIALOG = 4;
	/**
	 * 确认是否关闭payDialog
	 */
	private final int OFFPAYDIALOG = 5;
	/**
	 * 0元支付--跳转前判断订单状体
	 */

	private final int GETORDERSTATUS = 6;

	/**
	 * 切换卡券
	 */

	private final int CHANGECARD = 7;
	protected PayDialog payDialog;

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SELECTCARD:
				break;
			case CREATEORDER:
				remind = createorder_edt_phone.getText().toString();
				String name = createorder_edt_name.getText().toString();
				if (checkInputInfo(remind, name)) {
					setBtnDateBeforeCommit();

				}
				break;
			case CREATEORDERMERTEMPRICE:
				processLogicLLL();
				break;
			case SHOWPAYDIALOG:
				payDialog = new PayDialog(CreatOrdersActivity.this, mHandler, Constans.showPayDialogByBuyStadium, oid, uid, remind, "",allpay_money);
				payDialog.getPayDialog();
				break;
			case OFFPAYDIALOG:
				payDialog.selectPayDialog.dismiss();
				Intent intent = new Intent(CreatOrdersActivity.this, UserOrderDetailsActivity.class);
				intent.putExtra("oid", oid + "");// 订单号
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				finish();
				CommonUtils.getInstance().setPageIntentAnim(null, CreatOrdersActivity.this);
				break;
			case GETORDERSTATUS:
				getOrderStatus();
				break;
			case CHANGECARD:
				showDilag();
				if (selectcardindex >= 0) {
					useCardStatus = true;
					price_id_type = priceidtype_menshijia;
					mHandler.sendEmptyMessage(CREATEORDERMERTEMPRICE);
				} else {
					// 小于0为不使用卡券
					useCardStatus = false;
					price_id = default_price_id;
					price_id_type = priceidtype_menshijia;
					Log.e("price_id", "price_id" + price_id);
					mHandler.sendEmptyMessage(CREATEORDERMERTEMPRICE);
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				break;
			}
		}
	};

	/**
	 * 判断方法
	 * 
	 * @param remind
	 * @param name
	 * @return
	 */
	private boolean checkInputInfo(String remind, String name) {
		boolean backstatus = true;
		if (!ConfigUtils.getInstance().isMobileNO(remind)) {
			CommonUtils.getInstance().initToast(context, "请输入正确格式的手机号！");
			return backstatus = false;
		}
		if (cid.equals(Constans.getInstance().sportCidGolf)) {
			if (TextUtils.isEmpty(name)) {
				CommonUtils.getInstance().initToast(context, "请输入正确格式的打球人姓名！");
				return backstatus = false;
			}
			name = name.replace("，", ",");
			nameString = name.split(",");
			if (nameString.length != selectNum) {
				CommonUtils.getInstance().initToast(context, "打球人姓名的数量和打球人数不匹配！");
				return backstatus = false;
			} else {
				for (String string : nameString) {
					if (TextUtils.isEmpty(string)) {
						CommonUtils.getInstance().initToast(context, "每个姓名不能为空");
						return backstatus = false;
					}
				}
			}
		}
		return backstatus;
	}

	private String setItemsDate() {
		ArrayList<CreateOrderItemInfo> list = new ArrayList<CreateOrderItemInfo>();
		String shengyubalance = balance;
		String loginName = bestDoInfoSharedPrefs.getString("loginName", "");
		Double shengyuBalance = Double.valueOf(balance);
		int index = 0;
		for (int i = 0; i < itemsLenght; i++) {
			String playtime;
			String play_person_name;
			if (cid.equals(Constans.getInstance().sportCidGolf)) {
				play_person_name = nameString[i];
				playtime = play_time;
			} else {
				playtime = "10:00";
				play_person_name = loginName;
				if (cid.equals(Constans.getInstance().sportCidGolfrange)
						|| merid.equals(Constans.getInstance().sportMeridHighswimming)) {
					playtime = DatesUtils.getInstance().getDateGeShi("" + starthour, "HH", "HH:mm");
				}
			}
			String start_hour;
			String end_hour;
			String ordermoney = "";
			String reducemoney = "";
			String pay_money = "";
			String is_rights = "";
			String piece_id = "0";
			String member_reduce_moneyone = "0";

			// 次卡
			if (accountType.equals("TIMES") && useCardStatus) {

				if (cid.equals(Constans.getInstance().sportCidTennis)
						|| cid.equals(Constans.getInstance().sportCidBadminton)
						|| cid.equals(Constans.getInstance().sportTableTennis)
						|| cid.equals(Constans.getInstance().sportBasketball)) {
					/**
					 * 乒羽篮网
					 */
					start_hour = mMerItemPriceInfoList.get(i).getStart_hour();
					playtime = DatesUtils.getInstance().getDateGeShi(start_hour, "H", "HH:mm");
					end_hour = mMerItemPriceInfoList.get(i).getEnd_hour();
					String[] everyitemvalue = itemspieceinfo[i].split(",");
					piece_id = everyitemvalue[3];
					index = i;
				} else {
					start_hour = mMerItemPriceInfoList.get(0).getStart_hour();
					end_hour = mMerItemPriceInfoList.get(0).getEnd_hour();
					index = 0;
				}

				if (isReduced.equals("1")) {
					// 减免到
					if (shengyuBalance >= Double.valueOf(main_deduct_time)) {// 次数足够扣
						mer_price_id = mMerItemPriceInfoList.get(index).getMer_price_id();
						ordermoney = mMerItemPriceInfoList.get(index).getPrepay_price();
						pay_money = mMerItemPriceInfoList.get(index).getReducedAfterprice();
						reducemoney = PriceUtils.getInstance().gteSubtractSumPrice(pay_money, ordermoney);
						is_rights = "1";
						shengyuBalance -= Double.valueOf(main_deduct_time);
					} else {
						mer_price_id = mMerItemPriceInfoList.get(index).getMer_price_id();
						ordermoney = mMerItemPriceInfoList.get(index).getPrepay_price();
						pay_money = ordermoney;
						reducemoney = "0";
						is_rights = "0";
					}

				} else {
					// 减免
					if (shengyuBalance >= Double.valueOf(main_deduct_time)) {// 次数足够扣
						mer_price_id = mMerItemPriceInfoList.get(index).getBalance_mer_price_id();
						ordermoney = mMerItemPriceInfoList.get(index).getPrepay_price();
						pay_money = mMerItemPriceInfoList.get(index).getBalance_price();
						reducemoney = PriceUtils.getInstance().gteSubtractSumPrice(pay_money, ordermoney);
						is_rights = "1";
						shengyuBalance -= Double.valueOf(main_deduct_time);
					} else {
						mer_price_id = mMerItemPriceInfoList.get(index).getMer_price_id();
						ordermoney = mMerItemPriceInfoList.get(index).getPrepay_price();
						pay_money = ordermoney;
						reducemoney = "0";
						is_rights = "0";
					}
				}
			} else {
				// 不使用卡 或 没卡 现金卡
				if (price_id_type.equals(priceidtype_menshijia) || mMerItemPriceInfoList == null
						|| (mMerItemPriceInfoList != null && mMerItemPriceInfoList.size() == 0)) {
					if (cid.equals(Constans.getInstance().sportCidTennis)
							|| cid.equals(Constans.getInstance().sportCidBadminton)
							|| cid.equals(Constans.getInstance().sportTableTennis)
							|| cid.equals(Constans.getInstance().sportBasketball)) {
						/**
						 * 乒羽篮网
						 */
						String[] everyitemvalue = itemspieceinfo[i].split(",");
						start_hour = everyitemvalue[0];
						playtime = DatesUtils.getInstance().getDateGeShi(start_hour, "H", "HH:mm");
						end_hour = everyitemvalue[1];
						ordermoney = mMerItemPriceInfoList.get(i).getPrepay_price();
						piece_id = everyitemvalue[3];
					} else {
						/**
						 * 高尔夫练习场游泳健身
						 */
						start_hour = starthour;
						end_hour = endhour;
						ordermoney = order_money;
					}
					reducemoney = "0";
					member_reduce_moneyone = getMemberreducemoneyone(ordermoney);
					String memberordermoney = PriceUtils.getInstance().gteSubtractSumPrice(member_reduce_moneyone,
							ordermoney);
					pay_money = memberordermoney;
					is_rights = "0";// 没使用权益账户
					mer_price_id = default_price_id;
				} else {
					// 现金卡
					if (cid.equals(Constans.getInstance().sportCidTennis)
							|| cid.equals(Constans.getInstance().sportCidBadminton)
							|| cid.equals(Constans.getInstance().sportTableTennis)
							|| cid.equals(Constans.getInstance().sportBasketball)) {
						/**
						 * 乒羽篮网
						 */
						if (price_id_type.equals(priceidtype_banlance)) {
							mer_price_id = mMerItemPriceInfoList.get(i).getBalance_mer_price_id();
							ordermoney = mMerItemPriceInfoList.get(i).getBalance_price();
						} else if (price_id_type.equals(priceidtype_nobanlance)) {
							mer_price_id = mMerItemPriceInfoList.get(i).getNobalance_mer_price_id();
							ordermoney = mMerItemPriceInfoList.get(i).getNobalance_price();
						} else {
							mer_price_id = mMerItemPriceInfoList.get(i).getMer_price_id();
							ordermoney = mMerItemPriceInfoList.get(i).getPrepay_price();
						}
						start_hour = mMerItemPriceInfoList.get(i).getStart_hour();
						playtime = DatesUtils.getInstance().getDateGeShi(start_hour, "H", "HH:mm");
						end_hour = mMerItemPriceInfoList.get(i).getEnd_hour();
						String[] everyitemvalue = itemspieceinfo[i].split(",");
						piece_id = everyitemvalue[3];
					} else {
						if (price_id_type.equals(priceidtype_banlance)) {
							mer_price_id = mMerItemPriceInfoList.get(0).getBalance_mer_price_id();
							ordermoney = mMerItemPriceInfoList.get(0).getBalance_price();
						} else if (price_id_type.equals(priceidtype_nobanlance)) {
							mer_price_id = mMerItemPriceInfoList.get(0).getNobalance_mer_price_id();
							ordermoney = mMerItemPriceInfoList.get(0).getNobalance_price();
						} else {
							mer_price_id = mMerItemPriceInfoList.get(i).getMer_price_id();
							ordermoney = mMerItemPriceInfoList.get(0).getPrepay_price();
						}
						start_hour = mMerItemPriceInfoList.get(0).getStart_hour();
						end_hour = mMerItemPriceInfoList.get(0).getEnd_hour();
					}
					member_reduce_moneyone = getMemberreducemoneyone(ordermoney);
					String memberordermoney = PriceUtils.getInstance().gteSubtractSumPrice(member_reduce_moneyone,
							ordermoney);
					if (price_id_type.equals(priceidtype_nobanlance)) {
						reducemoney = "0";
						pay_money = memberordermoney;
						is_rights = "0";// balance为0
					} else {
						String shengyu = PriceUtils.getInstance().gteSubtractSumPrice(memberordermoney, shengyubalance);
						if (Double.valueOf(shengyu) >= 0) {
							// 足够抵扣
							reducemoney = memberordermoney;
							pay_money = "0";
							is_rights = "1";

							shengyubalance = shengyu;
						} else {
							// 半足够抵扣
							reducemoney = shengyubalance;
							pay_money = PriceUtils.getInstance().gteSubtractSumPrice(shengyubalance, memberordermoney);
							is_rights = "1";
							price_id_type = priceidtype_nobanlance;
						}
					}
				}
			}
			member_reduce_moneyone = PriceUtils.getInstance().seePrice(member_reduce_moneyone);
			ordermoney = PriceUtils.getInstance().gteMultiplySumPrice(ordermoney, "100");
			reducemoney = PriceUtils.getInstance().gteMultiplySumPrice(reducemoney, "100");
			pay_money = PriceUtils.getInstance().gteMultiplySumPrice(pay_money, "100");
			CreateOrderItemInfo mCreateOrderItemInfo = new CreateOrderItemInfo();
			mCreateOrderItemInfo.setMer_price_id(mer_price_id);
			mCreateOrderItemInfo.setPlay_time(playtime);
			mCreateOrderItemInfo.setPlay_person_name(play_person_name);
			mCreateOrderItemInfo.setStart_hour(start_hour);
			mCreateOrderItemInfo.setEnd_hour(end_hour);

			mCreateOrderItemInfo.setOrder_money(ordermoney);
			mCreateOrderItemInfo.setReduce_money(reducemoney);
			mCreateOrderItemInfo.setPay_money(pay_money);
			mCreateOrderItemInfo.setIs_rights(is_rights);
			mCreateOrderItemInfo.setMember_reduce_money(member_reduce_moneyone);
			mCreateOrderItemInfo.setPiece_id(piece_id);
			list.add(mCreateOrderItemInfo);
			mCreateOrderItemInfo = null;
		}
		return changeArrayDateToJson(list);
	}

	/**
	 * item中获取每个的会员减免
	 * 
	 * @param ordermoney
	 * @return
	 */
	private String getMemberreducemoneyone(String ordermoney) {
		String memberreducemoneyone;
		if (!TextUtils.isEmpty(identityId) && !identityId.equals(Constans.getInstance().identityCommomUser)) {
			String menberordermoney = PriceUtils.getInstance().gteDividePrice(ordermoney, defaultCount);
			memberreducemoneyone = PriceUtils.getInstance().gteSubtractSumPrice(menberordermoney, ordermoney);
			memberreducemoneyone = PriceUtils.getInstance().getPriceTwoDecimalDown(Double.valueOf(memberreducemoneyone),
					2);
			memberreducemoneyone = PriceUtils.getInstance().seePrice(memberreducemoneyone);
		} else {
			memberreducemoneyone = "0";
		}

		return memberreducemoneyone;
	}

	/**
	 * 将数组转换为JSON格式的数据。
	 * 
	 * @param stoneList
	 *            数据源
	 * @return JSON格式的数据
	 */
	public String changeArrayDateToJson(ArrayList<CreateOrderItemInfo> stoneList) {
		try {
			JSONArray array = new JSONArray();
			int length = stoneList.size();
			for (int i = 0; i < length; i++) {
				CreateOrderItemInfo stone = stoneList.get(i);
				String mer_price_id = stone.getMer_price_id();
				String playtime = stone.getPlay_time();
				String play_person_name = stone.getPlay_person_name();
				String start_hour = stone.getStart_hour();
				String end_hour = stone.getEnd_hour();
				String ordermoney = stone.getOrder_money();
				String reducemoney = stone.getReduce_money();
				String pay_money = stone.getPay_money();
				String is_rights = stone.getIs_rights();
				String member_reduce_money = stone.getMember_reduce_money();
				String piece_id = stone.getPiece_id();
				JSONObject stoneObject = new JSONObject();
				stoneObject.put("mer_price_id", mer_price_id);
				stoneObject.put("play_time", playtime);
				stoneObject.put("play_person_name", play_person_name);
				stoneObject.put("start_hour", start_hour);
				stoneObject.put("end_hour", end_hour);
				stoneObject.put("member_reduce_money",
						PriceUtils.getInstance().gteMultiplySumPrice(member_reduce_money, "100"));
				stoneObject.put("order_money", ordermoney);
				stoneObject.put("reduce_money", reducemoney);
				stoneObject.put("pay_money", pay_money);
				stoneObject.put("is_rights", is_rights);
				stoneObject.put("piece_id", piece_id);
				array.put(stoneObject);
			}
			return array.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 提交订单--准备数据
	 */
	private void setBtnDateBeforeCommit() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("source", source);
		mhashmap.put("uid", uid);
		mhashmap.put("card_id", card_id);
		mhashmap.put("account_no", account_no);
		mhashmap.put("cid", cid);
		mhashmap.put("mer_item_id", mer_item_id);
		mhashmap.put("book_day", book_day);

		// 次卡
		if (accountType.equals("TIMES")) {

			mhashmap.put("order_money", PriceUtils.getInstance().gteMultiplySumPrice(allorder_money, "100"));
		} else {

			if (price_id_type.equals(priceidtype_menshijia)) {
				mhashmap.put("order_money", PriceUtils.getInstance().gteMultiplySumPrice(allorder_money, "100"));
			} else {
				mhashmap.put("order_money", PriceUtils.getInstance().gteMultiplySumPrice(allvip_money, "100"));
			}
		}

		mhashmap.put("reduce_money", PriceUtils.getInstance().gteMultiplySumPrice(reduce_money, "100"));

		mhashmap.put("pay_money", PriceUtils.getInstance().gteMultiplySumPrice(allpay_money, "100"));

		mhashmap.put("book_phone", remind);
		mhashmap.put("member_reduce_money", PriceUtils.getInstance().gteMultiplySumPrice(member_reduce_money, "100"));

		String items = setItemsDate();
		mhashmap.put("items", items);
		Log.e("map", mhashmap.toString());
		System.err.println("mhashmap====" + mhashmap);
		showDilag();
		new CreateOrdersBusiness(this, cid, merid, mhashmap, new GetCreateOrdersCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						sendBroadcast(updatevenuePiecesIntents);
						oid = (String) dataMap.get("oid");
						if (0 != Double.valueOf(allpay_money)) {
							mHandler.sendEmptyMessage(SHOWPAYDIALOG);

						} else {

							mHandler.sendEmptyMessage(GETORDERSTATUS);
						}

					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(context, getResources().getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	/**
	 * 获取订单状体
	 */
	protected void getOrderStatus() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("oid", oid);
		new UserOrderDetailsBusiness(this, mhashmap, new GetUserOrderDetailsCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null && dataMap.get("status").equals("200")) {
					UserOrderDetailsInfo userOrderDetailsInfo = (UserOrderDetailsInfo) dataMap
							.get("userOrderDetailsInfo");
					if (userOrderDetailsInfo != null) {
						String is_set_supplier = userOrderDetailsInfo.getIs_set_supplier();
						String process_type = userOrderDetailsInfo.getProcess_type();
						// 0元时
						Intent intent;
						if (toYuding(userOrderDetailsInfo.getStats(), is_set_supplier, process_type)) {
							intent = new Intent(CreatOrdersActivity.this, Success_YuDing.class);
							intent.putExtra("oid", oid);
							intent.putExtra("mername", stadium_name);
							intent.putExtra("play_time", play_time);
							intent.putExtra("cid", cid);
							intent.putExtra("selectNum", "" + selectNum);
							intent.putExtra("remind", "" + remind);
							intent.putExtra("book_day", "" + book_day);
						} else {
							intent = new Intent(CreatOrdersActivity.this, Success_Pay.class);
							intent.putExtra("cid", cid);
							intent.putExtra("oid", oid);
						}
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						context.startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, context);
						finish();
					}
				} else {
					Intent intent = new Intent(context, Success_YuDing.class);
					intent.putExtra("oid", oid);
					intent.putExtra("mername", stadium_name);
					intent.putExtra("play_time", play_time);
					intent.putExtra("cid", cid);
					intent.putExtra("selectNum", "" + selectNum);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					context.startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);
					finish();
				}
				// 清除缓存
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	/**
	 * status-人工状态-供应商匹配状态 ; 1-0-1预订成功； 1-0-0支付成功； 1-1-0支付成功； 3-0-1预订成功；
	 * 3-0-0支付成功； 3-1-0支付成功；
	 * 
	 * @param orderstatus
	 * @param is_set_supplier
	 * @param process_type
	 *            人工
	 * 
	 * @return
	 */
	private boolean toYuding(String orderstatus, String is_set_supplier, String process_type) {
		boolean toYudingStatus = true;
		System.err.println(orderstatus + "   " + is_set_supplier + "     " + process_type);
		if (orderstatus.equals(getString(R.string.order_putParameters_confirming))
				&& process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 1-0-1预订成功；
			toYudingStatus = true;
		} else if (orderstatus.equals(getString(R.string.order_putParameters_confirming))
				&& process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& !is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 1-0-0支付成功；
			toYudingStatus = false;
		} else if (orderstatus.equals(getString(R.string.order_putParameters_confirming))
				&& !process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& !is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 1-1-0支付成功；
			toYudingStatus = false;
		} else if (orderstatus.equals(getString(R.string.order_putParameters_stayoff))
				&& process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 3-0-1预订成功；
			toYudingStatus = true;
		} else if (orderstatus.equals(getString(R.string.order_putParameters_stayoff))
				&& process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& !is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 3-0-0支付成功；
			toYudingStatus = false;
		} else if (orderstatus.equals(getString(R.string.order_putParameters_stayoff))
				&& !process_type.equals(Constans.getInstance().stadiumUsedManage)
				&& !is_set_supplier.equals(Constans.getInstance().stadiumStockType)) {
			// 3-1-0支付成功；
			toYudingStatus = false;
		}
		return toYudingStatus;
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

	/**
	 * 是否使用卡券
	 */
	boolean useCardStatus = true;

	/**
	 * activity回调数据
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == 1) {
				selectcardindex = data.getIntExtra("selectcardindex", 0);
				Message msgMessage = new Message();
				msgMessage.what = CHANGECARD;
				mHandler.sendMessage(msgMessage);
				msgMessage = null;
			}else if(resultCode == Constans.showPayDialogByBuyBalanceResult){
				if(payDialog!=null)
				payDialog.getPayDialog();
			}
		} catch (Exception e) {
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		RequestUtils.cancelAll(this);
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
			if (payDialog != null && payDialog.selectPayDialog != null && payDialog.selectPayDialog.isShowing()) {
				CommonUtils.getInstance().defineBackPressed(CreatOrdersActivity.this, mHandler, "exit_pay");
			} else {
				nowFinish();
			}
		}
		return false;
	}
}
