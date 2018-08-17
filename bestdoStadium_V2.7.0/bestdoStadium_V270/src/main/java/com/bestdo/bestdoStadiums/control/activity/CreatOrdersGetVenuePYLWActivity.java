package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness;
import com.bestdo.bestdoStadiums.business.StadiumDetailOneDayMerItemPriceBisiness;
import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness.GetMemberInfoCallback;
import com.bestdo.bestdoStadiums.business.StadiumDetailOneDayMerItemPriceBisiness.OneDayMerItemPriceCallback;
import com.bestdo.bestdoStadiums.model.CreatOrdersGetVenuePYLWInfo2;
import com.bestdo.bestdoStadiums.model.StadiumDetailMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.UserCenterMemberInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.MyHorizontalScrollView;
import com.bestdo.bestdoStadiums.utils.MyScrollView;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-25 下午6:34:30
 * @Description 类描述：创建订单---乒羽篮网选择时段
 */
public class CreatOrdersGetVenuePYLWActivity extends BaseActivity {
	private ProgressDialog mDialog;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private MyHorizontalScrollView venueWeekScrollView;
	private LinearLayout creat_orders_getvenuepylw_week;

	private LinearLayout creatorders_getvenuepylw_layout_venuePieces;
	private MyHorizontalScrollView venuePriceScrollView;
	private LinearLayout creat_orders_getvenuepylw_venuePieces;
	private MyScrollView venueTimeScrollView;
	private LinearLayout creat_orders_getvenuepylw_venueTime;
	private MyScrollView middleRowScrollView;
	private MyHorizontalScrollView middleColumnScrollView;
	private LinearLayout creat_orders_getvenuepylw_venuemiddle;
	private LinearLayout creat_orders_getvenuepylw_xiehua;

	private TextView createorder_getvenuepylw_tv_allprice;
	private TextView createorder_getvenuepylw_tv_selectnum;
	private TextView createorder_getvenuepylw_tv_book;

	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;
	private String selectday = "";
	private String summoney;
	private int sumNum;
	private String mer_item_id;
	private float pylwScrollViewX;// 乒羽篮网滑动选择week
	private View mPinYuLanBottomView;
	private View mTimesDuanView;
	private View mVenueHaoView;
	private View mpiecesDayView;
	private View mpiecesView;
	private ArrayList<CreatOrdersGetVenuePYLWInfo2> myudingList = new ArrayList<CreatOrdersGetVenuePYLWInfo2>();// 已选中的片数
	private HashMap<String, String> myudingpiecesMap = new HashMap<String, String>();// 为了封装打球时间字段
	private ArrayList<StadiumDetailMerItemPriceInfo> m7DaysPriceList;
	private ArrayList<CreatOrdersGetVenuePYLWInfo2> changdiList;
	private TextView creat_order_items_select, creat_order_items_noselect, creat_order_items_cantselect;
	// protected String identityId;
	// protected String discountBook;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_iv_you2:
			skipToUserCenter();
			break;
		case R.id.createorder_getvenuepylw_tv_book:
			setClickBtnPreparatParameters();
			// 友盟埋点：在场次选择页面选择时间后点击提交进入创建订单页
			CommonUtils.getInstance().umengCount(context, "1002006", null);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.creat_orders_getvenuepylw);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		venueWeekScrollView = (MyHorizontalScrollView) findViewById(R.id.venueWeekScrollView);
		creat_orders_getvenuepylw_week = (LinearLayout) findViewById(R.id.creat_orders_getvenuepylw_week);

		creatorders_getvenuepylw_layout_venuePieces = (LinearLayout) findViewById(
				R.id.creatorders_getvenuepylw_layout_venuePieces);
		venuePriceScrollView = (MyHorizontalScrollView) findViewById(R.id.venuePriceScrollView);
		creat_orders_getvenuepylw_venuePieces = (LinearLayout) findViewById(R.id.creat_orders_getvenuepylw_venuePieces);
		venueTimeScrollView = (MyScrollView) findViewById(R.id.venueTimeScrollView);
		creat_orders_getvenuepylw_venueTime = (LinearLayout) findViewById(R.id.creat_orders_getvenuepylw_venueTime);
		middleRowScrollView = (MyScrollView) findViewById(R.id.middleRowScrollView);
		middleColumnScrollView = (MyHorizontalScrollView) findViewById(R.id.middleColumnScrollView);
		creat_orders_getvenuepylw_venuemiddle = (LinearLayout) findViewById(R.id.creat_orders_getvenuepylw_venuemiddle);
		creat_orders_getvenuepylw_xiehua = (LinearLayout) findViewById(R.id.creat_orders_getvenuepylw_xiehua);

		createorder_getvenuepylw_tv_allprice = (TextView) findViewById(R.id.createorder_getvenuepylw_tv_allprice);
		createorder_getvenuepylw_tv_selectnum = (TextView) findViewById(R.id.createorder_getvenuepylw_tv_selectnum);
		createorder_getvenuepylw_tv_book = (TextView) findViewById(R.id.createorder_getvenuepylw_tv_book);
		creat_order_items_select = (TextView) findViewById(R.id.creat_order_items_select);
		creat_order_items_noselect = (TextView) findViewById(R.id.creat_order_items_noselect);
		creat_order_items_cantselect = (TextView) findViewById(R.id.creat_order_items_cantselect);
	}

	@Override
	protected void setListener() {
		venueWeekScrollView.setVisibility(View.INVISIBLE);
		createorder_getvenuepylw_tv_book.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		// 监听滑动时更新weekScrollView的选中的位置
		venueWeekScrollView.setScrollViewListener(new MyHorizontalScrollView.ScrollViewListener() {
			public void onScrollChanged(MyHorizontalScrollView paramAnonymousMyHorizontalScrollView,
					int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4) {
				pylwScrollViewX = paramAnonymousInt1;
			}
		});
		venueTimeScrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
			public void onScrollChanged(MyScrollView paramAnonymousMyScrollView, int paramAnonymousInt1,
					int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4) {
				middleRowScrollView.scrollTo(0, paramAnonymousInt2);
			}
		});
		venuePriceScrollView.setScrollViewListener(new MyHorizontalScrollView.ScrollViewListener() {
			public void onScrollChanged(MyHorizontalScrollView paramAnonymousMyHorizontalScrollView,
					int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4) {
				middleColumnScrollView.scrollTo(paramAnonymousInt1, 0);
			}
		});

		middleColumnScrollView.setScrollViewListener(new MyHorizontalScrollView.ScrollViewListener() {
			public void onScrollChanged(MyHorizontalScrollView paramAnonymousMyHorizontalScrollView,
					int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4) {
				venuePriceScrollView.scrollTo(paramAnonymousInt1, 0);
			}
		});
		middleRowScrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
			public void onScrollChanged(MyScrollView paramAnonymousMyScrollView, int paramAnonymousInt1,
					int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4) {
				venueTimeScrollView.scrollTo(0, paramAnonymousInt2);
			}
		});
		initDate();
	}

	@SuppressWarnings("unchecked")
	private void initDate() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		String m7DaysPriceListString = bestDoInfoSharedPrefs.getString("m7DaysPriceListString", "");
		m7DaysPriceList = (ArrayList<StadiumDetailMerItemPriceInfo>) CommonUtils.getInstance()
				.String2SceneList(m7DaysPriceListString);

		selectday = bestDoInfoSharedPrefs.getString("book_day", "");
		mer_item_id = bestDoInfoSharedPrefs.getString("mer_item_id", "");
		pylwScrollViewX = bestDoInfoSharedPrefs.getFloat("pylwScrollViewX", 0);
		pagetop_tv_name.setText("选择场次");
		summoney = "0";
		sumNum = myudingList.size();
		setBottomPrice();
	}

	/**
	 * 跳转用户中心
	 */
	private void skipToUserCenter() {
		Intent intents = new Intent(this, UserCenterActivity.class);
		intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intents);
		CommonUtils.getInstance().setPageIntentAnim(intents, this);
	}

	private void setBottomPrice() {
		createorder_getvenuepylw_tv_allprice.setText(getResources().getString(R.string.unit_fuhao_money) + summoney);
		createorder_getvenuepylw_tv_selectnum.setText("" + sumNum);
		if (Double.valueOf(summoney) > 0 && sumNum > 0) {
			createorder_getvenuepylw_tv_book.setBackgroundColor(getResources().getColor(R.color.btn_blue_color));
		} else {
			createorder_getvenuepylw_tv_book.setBackgroundColor(getResources().getColor(R.color.text_noclick_color));
		}
	}

	protected void processLogic() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		new GetMemberInfoBusiness(this, mhashmap, new GetMemberInfoCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					UserCenterMemberInfo muserCenterMemberInfo = (UserCenterMemberInfo) dataMap
							.get("userCenterMemberInfo");
					if (muserCenterMemberInfo != null) {
						// identityId=muserCenterMemberInfo.getIdentityId();
						// if(!TextUtils.isEmpty(identityId)&&!identityId.equals(Constans.getInstance().identityCommomUser)){
						// //会员
						// discountBook
						// =muserCenterMemberInfo.getDiscountBook();
						// }
					}
					mloadHandler.sendEmptyMessage(LOAD);
				}
			}
		});
	}

	private final int LOAD = 5;
	Handler mloadHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOAD:
				processLogicLLL();
				break;
			}
		}
	};
	HashMap<String, String> mhashmap;
	String is_card;

	protected void processLogicLLL() {

		mhashmap = new HashMap<String, String>();
		mhashmap.put("mer_item_id", mer_item_id);
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		mhashmap.put("date", selectday);
		System.err.println(mhashmap);
		showDilag();
		new StadiumDetailOneDayMerItemPriceBisiness(this, mhashmap, "1", new OneDayMerItemPriceCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(CreatOrdersGetVenuePYLWActivity.this, msg);
					} else if (status.equals("200")) {
						initContent();
						is_card = "1";
						changdiList = (ArrayList<CreatOrdersGetVenuePYLWInfo2>) dataMap.get("changdiList");
						showWeekView();
						updateLoadSelectDay();
						/**
						 * scrollBy()直接设置滚动条到该位置 scrollTo()
						 * 是直接指定滚动条的位置，会有一个滑动效果, 但是由于这个动作不是单纯关于 ScrollView 而已,
						 * 还要根据 ScrollView 里面包含的View 的实际信息.
						 * 所以这动作必须在页面加载完成以后才能执行.
						 */
						Handler mHandler = new Handler();
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								venueWeekScrollView.scrollBy((int) pylwScrollViewX, 0);
							}
						});
					}

				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
			}
		});

	}

	/**
	 * 每次重新选择日期时，重新请求时初始化
	 */
	private void initContent() {
		summoney = "0";
		sumNum = 0;
		new_xwidth = 0;
		new_yheigh = 0;
		scollStatus = true;
		setClearCacheView();
		setBottomPrice();
	}

	/**
	 * 显示week view
	 */
	private void showWeekView() {
		venueWeekScrollView.setVisibility(View.VISIBLE);
		creat_orders_getvenuepylw_week.removeAllViews();
		if (m7DaysPriceList != null && m7DaysPriceList.size() != 0) {
			for (int i = 0; i < m7DaysPriceList.size(); i++) {
				mPinYuLanBottomView = LayoutInflater.from(context)
						.inflate(R.layout.stadium_detail_venue_swimfitnes_item, null);
				@SuppressWarnings("deprecation")
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						android.widget.LinearLayout.LayoutParams.FILL_PARENT,
						android.widget.LinearLayout.LayoutParams.FILL_PARENT);
				params.setMargins(5, 0, 10, 5);
				mPinYuLanBottomView.setLayoutParams(params);
				TextView stadium_detail_swimfitnes_item_time = (TextView) mPinYuLanBottomView
						.findViewById(R.id.stadium_detail_swimfitnes_item_time);
				TextView stadium_detail_swimfitnes_item_week = (TextView) mPinYuLanBottomView
						.findViewById(R.id.stadium_detail_swimfitnes_item_week);
				String showday = DatesUtils.getInstance().getTimeStampToDate(m7DaysPriceList.get(i).getDatetamp(),
						"MM月dd日");
				String showEE = DatesUtils.getInstance().getTimeStampToDate(m7DaysPriceList.get(i).getDatetamp(), "EE");
				if (showEE.contains("星期")) {
					showEE = showEE.replace("星期", "周");
				}
				stadium_detail_swimfitnes_item_time.setText(showday);
				stadium_detail_swimfitnes_item_week.setText(showEE);
				boolean is_order = m7DaysPriceList.get(i).getIs_order();
				if (is_order) {
					mPinYuLanBottomView.setTag(i);
					mPinYuLanBottomView.setOnClickListener(onChangeDateClick);
					boolean is_select = m7DaysPriceList.get(i).getIs_select();
					if (is_select) {
						getCurrentSelectDate(m7DaysPriceList.get(i).getDatetamp());
						stadium_detail_swimfitnes_item_time.setTextColor(getResources().getColor(R.color.blue));
						stadium_detail_swimfitnes_item_week.setTextColor(getResources().getColor(R.color.blue));
						mPinYuLanBottomView.setBackgroundResource(R.drawable.stadium_detail_venues_corners_bg);
					} else {
						stadium_detail_swimfitnes_item_time
								.setTextColor(getResources().getColor(R.color.text_contents_color));
						stadium_detail_swimfitnes_item_week
								.setTextColor(getResources().getColor(R.color.text_contents_color));

					}
				} else {
					stadium_detail_swimfitnes_item_time
							.setTextColor(getResources().getColor(R.color.text_noclick_color));
					stadium_detail_swimfitnes_item_week
							.setTextColor(getResources().getColor(R.color.text_noclick_color));
				}
				creat_orders_getvenuepylw_week.addView(mPinYuLanBottomView);
			}
		}

	}

	/**
	 * 获取当前日期
	 * 
	 * @param datetamp
	 */
	private void getCurrentSelectDate(int datetamp) {
		selectday = DatesUtils.getInstance().getTimeStampToDate(datetamp, "yyyy-MM-dd");
	}

	OnClickListener onChangeDateClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int i = (Integer) v.getTag();
			for (int j = 0; j < m7DaysPriceList.size(); j++) {
				if (i == j) {
					m7DaysPriceList.get(j).setIs_select(true);
				} else {
					m7DaysPriceList.get(j).setIs_select(false);
				}
			}
			getCurrentSelectDate(m7DaysPriceList.get(i).getDatetamp());
			mloadHandler.sendEmptyMessage(LOAD);
		}
	};

	/**
	 * 更新选择的day下的数据
	 * 
	 * @param selectday
	 */
	private void updateLoadSelectDay() {

		if (changdiList != null && changdiList.size() != 0) {
			showTimesDuanView();
		} else {
			showNotVenuePricesView();
		}
	}

	/**
	 * 1加载左边时间段
	 */
	private void showTimesDuanView() {
		creat_orders_getvenuepylw_venueTime.removeAllViews();
		creat_orders_getvenuepylw_venuePieces.removeAllViews();
		creat_orders_getvenuepylw_venuemiddle.removeAllViews();
		creat_orders_getvenuepylw_xiehua.removeAllViews();
		if (changdiList != null && changdiList.size() != 0) {
			ArrayList<CreatOrdersGetVenuePYLWInfo2> mDayPricesList = changdiList.get(0).getVenuesList();
			for (int i = 0; i < mDayPricesList.size(); i++) {
				mTimesDuanView = getLayoutInflater().inflate(R.layout.creat_orders_getvenuepylw_timeitem, null);
				TextView tv = (TextView) mTimesDuanView.findViewById(R.id.create_orders_pylw_timeitem_time);
				String start_hour = mDayPricesList.get(i).getStart_hour();
				String end_hour = mDayPricesList.get(i).getEnd_hour();
				tv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
				if (i == mDayPricesList.size() - 1) {
					tv.setText(start_hour);
					int height = (getItemHeight() + ConfigUtils.getInstance().dip2px(context, 20)) / 2;
					tv.setHeight(height);
					System.err.println("height" + height);
					creat_orders_getvenuepylw_venueTime.addView(mTimesDuanView);
					// 显示最后一个
					View mendTimesDuanView = getLayoutInflater().inflate(R.layout.creat_orders_getvenuepylw_timeitem,
							null);
					TextView tvend = (TextView) mendTimesDuanView.findViewById(R.id.create_orders_pylw_timeitem_time);
					tvend.setGravity(Gravity.CENTER);
					tvend.setHeight(height);
					tvend.setText(end_hour);
					creat_orders_getvenuepylw_venueTime.addView(mendTimesDuanView);
				} else {
					tv.setHeight(getItemHeight());
					System.err.println("ConfigUtils.getInstance().dip2px(context, 40)"
							+ ConfigUtils.getInstance().dip2px(context, 40));
					tv.setText(start_hour);
					creat_orders_getvenuepylw_venueTime.addView(mTimesDuanView);
				}
			}
			/**
			 * 2加载横向场地号
			 */
			showVenueHaoView();
			/**
			 * 3加载中间场片
			 */
			showVenuePieces();

		} else {
			showNotVenuePricesView();
		}
	}

	private int getItemWidth() {
		DisplayMetrics dm = ConfigUtils.getInstance().getPhoneWidHeigth(context);
		int screenWidth = dm.widthPixels;
		int itemwidth = (screenWidth - ConfigUtils.getInstance().dip2px(context, 70)) / 5;
		return itemwidth;
	}

	private int getItemHeight() {
		int itemheight = ConfigUtils.getInstance().dip2px(context, 35);
		return itemheight;
	}

	/**
	 * 当没有报价时
	 */
	private void showNotVenuePricesView() {
		creat_orders_getvenuepylw_venueTime.removeAllViews();
		creat_orders_getvenuepylw_venuePieces.removeAllViews();
		creat_orders_getvenuepylw_venuemiddle.removeAllViews();
		creat_orders_getvenuepylw_xiehua.removeAllViews();
	}

	/**
	 * 显示场号view加载横向场地号
	 */
	private void showVenueHaoView() {
		for (int i = 0; i < changdiList.size(); i++) {
			mVenueHaoView = getLayoutInflater().inflate(R.layout.creat_orders_getvenuepylw_timeitem, null);
			TextView tv = (TextView) mVenueHaoView.findViewById(R.id.create_orders_pylw_timeitem_time);
			tv.setTextColor(getResources().getColor(R.color.white));
			int itemwidth = getItemWidth();
			tv.setWidth(itemwidth);
			int itemHeight = getItemHeight();
			tv.setHeight(itemHeight);
			tv.setText(changdiList.get(i).getName());
			creat_orders_getvenuepylw_venuePieces.addView(mVenueHaoView);
		}
		creatorders_getvenuepylw_layout_venuePieces.setVisibility(View.VISIBLE);
	}

	private Boolean scollStatus = true;
	private int new_xwidth = 0;
	private int new_yheigh = 0;

	/**
	 * 加载中间场片
	 */
	private void showVenuePieces() {
		if (changdiList != null && changdiList.size() != 0) {
			// 计算中间片场item布局的宽高
			int item_Width = getItemWidth();
			int item_Heigh = getItemHeight();
			creat_order_items_select.setWidth(item_Width);
			creat_order_items_select.setHeight(item_Heigh);
			creat_order_items_noselect.setWidth(item_Width);
			creat_order_items_noselect.setHeight(item_Heigh);
			creat_order_items_cantselect.setWidth(item_Width);
			creat_order_items_cantselect.setHeight(item_Heigh);
			// 手机屏幕显示的个数
			int item_Widths = 5;
			// 在Y轴所占的高度
			DisplayMetrics dm = ConfigUtils.getInstance().getPhoneWidHeigth(context);
			int screenWidth = dm.widthPixels;
			int screenHeigh = dm.heightPixels;
			int YHeigh = (int) (screenHeigh / 10 * 3.5);

			for (int i = 0; i < changdiList.size(); i++) {
				mpiecesDayView = getLayoutInflater().inflate(R.layout.creat_orders_getvenuepylw_day, null);
				LinearLayout layout = (LinearLayout) mpiecesDayView.findViewById(R.id.create_orders_pylw_day_layout);
				ArrayList<CreatOrdersGetVenuePYLWInfo2> mDayPricesList = changdiList.get(i).getVenuesList();
				if (mDayPricesList != null && mDayPricesList.size() != 0) {
					for (int j = 0; j < mDayPricesList.size(); j++) {
						mpiecesView = getLayoutInflater().inflate(R.layout.creat_orders_getvenuepylw_timeitem, null);
						TextView tv = (TextView) mpiecesView.findViewById(R.id.create_orders_pylw_timeitem_time);
						tv.setWidth(item_Width);
						tv.setHeight(item_Heigh);
						tv.setTextColor(getResources().getColor(R.color.blue));
						/**
						 * 如果有卡价格取prepay_price 有-->piecebookstatus=1
						 */
						String price = mDayPricesList.get(j).getPrepay_price();
						String piecebookstatus = mDayPricesList.get(j).getPiecebookstatus();
						if (!TextUtils.isEmpty(piecebookstatus) && Double.valueOf(piecebookstatus) == 1
								&& !TextUtils.isEmpty(price) && Double.valueOf(price) > 0) {
							tv.setBackgroundColor(getResources().getColor(R.color.white));

							// if(!TextUtils.isEmpty(identityId)&&!identityId.equals(Constans.getInstance().identityCommomUser)){
							// price=PriceUtils.getInstance().gteMultiplySumPrice(price,
							// discountBook);
							// price=PriceUtils.getInstance().getPriceTwoDecimalDown(Double.valueOf(price),
							// 2);
							// price=PriceUtils.getInstance().seePrice(price);
							// }
							tv.setText(getResources().getString(R.string.unit_fuhao_money) + price);
							if (scollStatus) {
								// 可预订的Y轴没有显示出来时
								if ((j + 1) * item_Heigh > YHeigh) {
									// new_yheigh=(j+1)*item_Heigh-sum;
									new_yheigh = YHeigh;
								}
								// 可预订的X轴没有显示出来时
								if ((i + 1) * item_Width + item_Width > (screenWidth - item_Width)) {
									new_xwidth = (item_Widths + 1) * item_Width;
								}
								scollStatus = false;
							}

							tv.setTag(mDayPricesList.get(j));
							tv.setOnClickListener(selectVenuePiecesClick);
						} else {
							tv.setBackgroundColor(getResources().getColor(R.color.text_noclick_color));
						}
						mDayPricesList.get(j).setTvpieces(tv);
						mDayPricesList.get(j).setIs_select(false);// 初始化每片的选中状态
						layout.addView(mpiecesView);
					}
				}
				creat_orders_getvenuepylw_venuemiddle.addView(mpiecesDayView);

			}
			venueTimeScrollView.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					venueTimeScrollView.scrollTo(0, new_yheigh);
				}
			});
			venuePriceScrollView.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					venuePriceScrollView.scrollTo(new_xwidth, 0);
				}
			});
		}

	}

	/**
	 * 点击预订的话，此时应该提醒"您还没有选择场地哦" 补充：用户如选择超过5片，则提醒“亲，一次只能选择5片哦”
	 */
	private OnClickListener selectVenuePiecesClick = new OnClickListener() {
		public void onClick(View v) {

			CreatOrdersGetVenuePYLWInfo2 mvenuepieceinfo = (CreatOrdersGetVenuePYLWInfo2) v.getTag();
			// 判断是否选中
			Boolean is_select = mvenuepieceinfo.getIs_select();
			if (!is_select) {
				// 没选中时
				if (myudingList.size() < 5) {
					myudingList.add(mvenuepieceinfo);
					mvenuepieceinfo.setIs_select(true);
					mvenuepieceinfo.getTvpieces().setBackgroundColor(getResources().getColor(R.color.blue));
					mvenuepieceinfo.getTvpieces().setTextColor(getResources().getColor(R.color.white));
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.createorderpylw_booktip));
				}
			} else {
				/*
				 * 取消选中
				 */
				myudingList.remove(mvenuepieceinfo);
				mvenuepieceinfo.setIs_select(false);
				mvenuepieceinfo.getTvpieces().setTextColor(getResources().getColor(R.color.blue));
				mvenuepieceinfo.getTvpieces().setBackgroundColor(getResources().getColor(R.color.white));
			}
			/**
			 * 初始化计算选中价格
			 */
			if (myudingpiecesMap != null) {
				myudingpiecesMap.clear();
			}
			summoney = "0";
			if (myudingList.size() == 0) {
				myudingpiecesMap.clear();
			} else {
				for (int i = 0; i < myudingList.size(); i++) {
					String price = myudingList.get(i).getPrepay_price();
					// if(!TextUtils.isEmpty(identityId)&&!identityId.equals(Constans.getInstance().identityCommomUser)){
					// price=PriceUtils.getInstance().gteMultiplySumPrice(price,
					// discountBook);
					// price=PriceUtils.getInstance().getPriceTwoDecimalDown(Double.valueOf(price),
					// 2);
					// price=PriceUtils.getInstance().seePrice(price);
					// }
					summoney = PriceUtils.getInstance().gteAddSumPrice(price, summoney);
					summoney = PriceUtils.getInstance().seePrice(summoney);
					String key = myudingList.get(i).getStart_hour() + "-" + myudingList.get(i).getEnd_hour();
					if (myudingpiecesMap.containsKey(key)) {
						String value = PriceUtils.getInstance().gteAddSumPrice(myudingpiecesMap.get(key), "1");
						myudingpiecesMap.put(key, value);
					} else {
						myudingpiecesMap.put(key, "1");
					}
				}

			}
			sumNum = myudingList.size();
			setBottomPrice();
		}
	};

	/**
	 * 点击下一步按钮，准备参数，未选择场片不可点击
	 */
	private void setClickBtnPreparatParameters() {
		if (myudingList.size() > 0 && myudingList.size() < 6) {
			// 排序
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
					myudingpiecesMap.entrySet());
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					// return (o2.getValue() - o1.getValue());
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			StringBuffer shiduan = new StringBuffer();
			for (int i = 0; i < infoIds.size(); i++) {
				String key = infoIds.get(i).getKey();// 返回与此项对应的键
				String value = infoIds.get(i).getValue();// 返回与此项对应的值
				shiduan.append(key + "  " + value + getString(R.string.unit_piece));
				if (i != infoIds.size() - 1) {
					shiduan.append("\n");
				}
			}
			StringBuffer hour = new StringBuffer();
			StringBuffer itemspiece = new StringBuffer();
			// {8,9}
			for (int i = 0; i < myudingList.size(); i++) {
				String starthour = DatesUtils.getInstance().getDateGeShi(myudingList.get(i).getStart_hour(), "HH:mm",
						"H");
				String endhour = DatesUtils.getInstance().getDateGeShi(myudingList.get(i).getEnd_hour(), "HH:mm", "H");
				hour.append(starthour);
				itemspiece.append(starthour + "," + endhour + "," + myudingList.get(i).getPrepay_price() + ","
						+ myudingList.get(i).getPiece_id());
				if (i != myudingList.size() - 1) {
					hour.append(",");
					itemspiece.append("!");
				}
			}

			bestDoInfoEditor.putString("hour", hour.toString());
			bestDoInfoEditor.putString("book_shiduan", "" + shiduan);
			bestDoInfoEditor.putString("book_day", selectday);
			bestDoInfoEditor.putString("select_price", summoney);
			bestDoInfoEditor.putString("itemspiece", "" + itemspiece);
			bestDoInfoEditor.commit();
			Intent intent = new Intent(CreatOrdersGetVenuePYLWActivity.this, CreatOrdersActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, CreatOrdersGetVenuePYLWActivity.this);
			shiduan = null;
			infoIds.clear();
			infoIds = null;
		}
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

	// -----------------add cancel 收藏-------------------------------
	@Override
	protected void onStart() {
		super.onStart();
		// 动态注册广播
		filter = new IntentFilter();
		filter.addAction(context.getString(R.string.action_updateDetailVenueOrPYLWVenuePieces));
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(dynamicReceiver, filter);
	}

	/**
	 * 接收广播,返回乒羽篮网选择场地片数时更新场地信息（只有当点击创建订单后）
	 */
	IntentFilter filter;
	private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("from CreatOrdersActivity", "接收---乒羽篮网选择场地片数时更新场地信息---广播消息");
			if (intent.getAction().equals(context.getString(R.string.action_updateDetailVenueOrPYLWVenuePieces))) {
				mloadHandler.sendEmptyMessage(LOAD);
			}
		}
	};

	private void setClearCacheView() {
		mTimesDuanView = null;
		mVenueHaoView = null;
		mpiecesDayView = null;
		mpiecesView = null;
		if (myudingList != null && myudingList.size() != 0) {
			for (CreatOrdersGetVenuePYLWInfo2 iterable_element : myudingList) {
				iterable_element = null;
			}
			myudingList.clear();
		}
		if (myudingpiecesMap != null) {
			myudingpiecesMap.clear();
		}
	}

	private void setClearCacheData() {
		filter = null;
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		// mHandler.removeCallbacks(mRunnable);
		// mHandler = null;
		// mRunnable = null;
		myudingList = null;
		myudingpiecesMap = null;
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(dynamicReceiver);
		dynamicReceiver = null;
		filter = null;
		setClearCacheData();
		setClearCacheView();
		RequestUtils.cancelAll(this);
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
