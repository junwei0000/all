package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness;
import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness.GetMemberInfoCallback;
import com.bestdo.bestdoStadiums.business.StadiumDetailBusiness;
import com.bestdo.bestdoStadiums.business.StadiumDetailHoursMerItemPriceBisiness;
import com.bestdo.bestdoStadiums.business.StadiumDetailMerItemPriceBisiness;
import com.bestdo.bestdoStadiums.business.StadiumDetailMerItemPriceGolfBisiness;
import com.bestdo.bestdoStadiums.business.StadiumDetailBusiness.GetStadiumDetailCallback;
import com.bestdo.bestdoStadiums.business.StadiumDetailHoursMerItemPriceBisiness.GetHoursMerItemPriceCallback;
import com.bestdo.bestdoStadiums.business.StadiumDetailMerItemPriceBisiness.GetMerItemPriceCallback;
import com.bestdo.bestdoStadiums.business.StadiumDetailMerItemPriceGolfBisiness.GetMerItemPriceGolfCallback;
import com.bestdo.bestdoStadiums.control.adapter.AdvertImagePagerAdapter;
import com.bestdo.bestdoStadiums.control.adapter.StadiumDetailServiceAdapter;
import com.bestdo.bestdoStadiums.control.map.BestDoApplication;
import com.bestdo.bestdoStadiums.control.pullable.PullableScrollView;
import com.bestdo.bestdoStadiums.control.teetime.ArrayWheelAdapter;
import com.bestdo.bestdoStadiums.control.teetime.OnWheelChangedListener;
import com.bestdo.bestdoStadiums.control.teetime.WheelView;
import com.bestdo.bestdoStadiums.control.view.AutoScrollViewPager;
import com.bestdo.bestdoStadiums.control.view.CirclePageIndicator;
import com.bestdo.bestdoStadiums.model.BannerInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailMerItemPriceGolfInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailOneDayHourMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.UserCenterMemberInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.MyHorizontalScrollView;
import com.bestdo.bestdoStadiums.utils.MyGridView;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.ShareUtils;
import com.bestdo.bestdoStadiums.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-22 下午5:05:02
 * @Description 类描述：场馆详情
 */
public class StadiumDetailActivity extends BaseActivity {

	private LinearLayout page_top_layout;
	private LinearLayout pagetop_layout_back;
	private PullableScrollView stadium_detail_scrollview;
	private RelativeLayout stadium_detail_ralat_topbanner;
	private ImageView pagetop_iv_back;
	private TextView pagetop_tv_name, stadium_abstract;
	private ImageView pagetop_iv_line;
	private AutoScrollViewPager viewpager_advert;

	private LinearLayout stadium_detail_layout_properties;// 球场属性
	private LinearLayout stadium_detail_layout_propertieszuo;
	private LinearLayout stadium_detail_layout_propertiesyou;
	private MyGridView servicegridview;
	private LinearLayout stadium_detail_abstract_lin;// 场馆介绍
	private TextView stadium_detailmore_tv_description;
	private LinearLayout stadium_detailmore_layout_address;// 地址
	private TextView stadium_detailmore_tv_address;
	private LinearLayout stadium_detailmore_tel;// 电话
	private LinearLayout stadium_detail_venuces;// 场地

	private TextView stadium_detail_item_tv_vipprice;
	private TextView stadiumdetails_tv_book;
	private ProgressDialog mDialog;
	private String address;
	private String mer_name;
	private String merid;
	private String mer_item_id;
	private String vip_price_id;
	private String cid = "";
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;
	private ArrayList<StadiumDetailMerItemPriceInfo> m7DaysPriceList;
	/**
	 * 当前选中显示的日期 yyyy-MM-dd
	 */
	private String currentSelectDate = "";
	private String hour = "0";
	private String playtime = "0:00";
	private String bd_phone = "";
	/**
	 * 1小时型 2时段型 3日期型
	 */
	private String inventory_type;
	String price_info;
	String mer_price_id;
	String vip_price;
	String origeprice;
	String starthour;
	String endhour;
	String longitude;
	String latitude;
	String min_price;
	public int teetimejiange;// teetime间隔
	private float pylwScrollViewX;// 滑动选择week
	/**
	 * 收藏状态
	 */
	boolean collectStatus = false;
	StadiumCollectUtils mCollectUtils;
	int nowScrollY;
	/**
	 * 登录后自动收藏状态
	 */
	boolean loginAutoCollectStatus = false;
	private CirclePageIndicator indicator;
	private String skipToDetailStatus;
	private LinearLayout stadium_detailmore_layout_sheshi;
	private LinearLayout stadium_detail_layout_bookinfo;
	private TextView stadium_detail_tv_bookinfo;
	private TextView stadium_detailmore_tv_stadiumname;
	// 首先在您的Activity中添加如下成员变量

	private LinearLayout stadium_detailbottom_layout_collect;
	private ImageView stadium_detailbottom_iv_collect;
	private View stadium_detailbottom_layout_share;
	private LinearLayout stadium_detailmore_layout_stadiumname;
	private LinearLayout stadiumdetail_layout_member;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.stadium_detailbottom_layout_collect:
			if (checkLoginStatus(Constans.getInstance().loginskiptoaddfavorite)) {
				mCollectUtils.uid = bestDoInfoSharedPrefs.getString("uid", "");
				if (mCollectUtils.clickloadingoverstatus) {
					mCollectUtils.clickloadingoverstatus = false;
					mCollectUtils.getClickFavorite();
				}
			}
			break;
		case R.id.stadium_detailbottom_layout_share:
			String webpageUrl = "http://weixin.bestdo.com/item/info?mer_item_id=" + mer_item_id;
			(new ShareUtils(this)).share(description, webpageUrl, mer_name);
			break;
		case R.id.stadium_detailmore_tel:
			CommonUtils.getInstance().getPhoneToKey(context, bd_phone);
			break;
		case R.id.stadium_detailmore_layout_address:
			Intent intent = new Intent(this, StadiumDetailMapActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("latitude", latitude);
			intent.putExtra("longitude", longitude);
			intent.putExtra("stadium_name", mer_name);
			intent.putExtra("min_price", min_price);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;

		default:
			break;
		}

	}

	private boolean meberStatus() {
		boolean meberStatus = false;
		if (!TextUtils.isEmpty(identityId) && !identityId.equals(Constans.getInstance().identityCommomUser)) {
			meberStatus = true;
		} else {
			toMemberDialpg();
		}
		return meberStatus;

	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.stadium_detail);
		CommonUtils.getInstance().addActivity(this);

	}

	@Override
	protected void findViewById() {
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		page_top_layout.getBackground().setAlpha(0);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		stadium_detail_scrollview = (PullableScrollView) findViewById(R.id.stadium_detail_scrollview);
		stadium_detail_ralat_topbanner = (RelativeLayout) findViewById(R.id.stadium_detail_ralat_topbanner);
		stadium_detail_ralat_topbanner.getBackground().setAlpha(51);
		pagetop_iv_back = (ImageView) findViewById(R.id.pagetop_iv_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_iv_line = (ImageView) findViewById(R.id.pagetop_iv_line);
		stadium_detailbottom_iv_collect = (ImageView) findViewById(R.id.stadium_detailbottom_iv_collect);
		stadium_detailbottom_layout_share = (LinearLayout) findViewById(R.id.stadium_detailbottom_layout_share);
		stadium_detailbottom_layout_collect = (LinearLayout) findViewById(R.id.stadium_detailbottom_layout_collect);
		stadium_abstract = (TextView) findViewById(R.id.stadium_abstract);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		viewpager_advert = (AutoScrollViewPager) findViewById(R.id.viewpager_advert);

		stadium_detail_layout_properties = (LinearLayout) findViewById(R.id.stadium_detail_layout_properties);
		stadium_detail_layout_propertieszuo = (LinearLayout) findViewById(R.id.stadium_detail_layout_propertieszuo);
		stadium_detail_layout_propertiesyou = (LinearLayout) findViewById(R.id.stadium_detail_layout_propertiesyou);
		stadium_detailmore_layout_sheshi = (LinearLayout) findViewById(R.id.stadium_detailmore_layout_sheshi);
		servicegridview = (MyGridView) findViewById(R.id.stadium_detail_more_service_gridview);
		stadium_detail_abstract_lin = (LinearLayout) findViewById(R.id.stadium_detail_abstract_lin);
		stadium_detailmore_tv_description = (TextView) findViewById(R.id.stadium_detailmore_tv_description);
		stadium_detailmore_layout_address = (LinearLayout) findViewById(R.id.stadium_detailmore_layout_address);
		stadium_detailmore_tv_address = (TextView) findViewById(R.id.stadium_detailmore_tv_address);
		stadium_detailmore_tel = (LinearLayout) findViewById(R.id.stadium_detailmore_tel);
		stadium_detail_venuces = (LinearLayout) findViewById(R.id.stadium_detail_venuces);
		stadium_detail_layout_bookinfo = (LinearLayout) findViewById(R.id.stadium_detail_layout_bookinfo);
		stadium_detail_tv_bookinfo = (TextView) findViewById(R.id.stadium_detail_tv_bookinfo);
		stadium_detailmore_tv_stadiumname = (TextView) findViewById(R.id.stadium_detailmore_tv_stadiumname);
		stadium_detailmore_layout_stadiumname = (LinearLayout) findViewById(R.id.stadium_detailmore_layout_stadiumname);
		// 底部
		stadium_detail_item_tv_vipprice = (TextView) findViewById(R.id.stadium_detail_item_tv_vipprice);
		stadiumdetails_tv_book = (TextView) findViewById(R.id.stadiumdetails_tv_book);
		stadiumdetail_layout_member = (LinearLayout) findViewById(R.id.stadiumdetail_layout_member);

	}

	@Override
	protected void setListener() {
		stadium_detail_ralat_topbanner.setVisibility(View.INVISIBLE);
		pagetop_layout_back.setOnClickListener(this);
		stadium_detailbottom_layout_collect.setOnClickListener(this);
		stadium_detailbottom_layout_share.setOnClickListener(this);
		stadium_detailmore_tel.setOnClickListener(this);
		stadium_detailmore_layout_address.setOnClickListener(this);
		stadium_detail_scrollview.setOnScrollChangedListener(new PullableScrollView.OnScrollChangedListener() {

			public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
				nowScrollY = t;
				setTopApha();
			}
		});
		init();
		setTopApha();
	}

	@SuppressLint("NewApi")
	private void init() {
		Intent in = getIntent();
		// s
		mer_item_id = in.getStringExtra("mer_item_id");
		vip_price_id = in.getStringExtra("vip_price_id");
		skipToDetailStatus = (in.getExtras()).getString("skipToDetailStatus", "");
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();

		String uid = bestDoInfoSharedPrefs.getString("uid", "");
		mCollectUtils = new StadiumCollectUtils(StadiumDetailActivity.this, mer_item_id, uid, mloadHandler,
				UPDATECOLLECTSTATUS);

	}

	private void setCollectStatus() {
		if (collectStatus) {
			stadium_detailbottom_iv_collect.setBackgroundResource(R.drawable.stadiumdetail_collect_img_select);
		} else {
			stadium_detailbottom_iv_collect.setBackgroundResource(R.drawable.stadiumdetail_collect_img);
		}
	}

	@SuppressLint("UseValueOf")
	private void setTopApha() {
		setCollectStatus();
		if (stadium_detail_ralat_topbanner != null) {
			int lHeight = stadium_detail_ralat_topbanner.getHeight();
			int lHeight2 = page_top_layout.getHeight();
			if (nowScrollY == 0) {
				page_top_layout.getBackground().setAlpha(0);
				pagetop_iv_line.setVisibility(View.GONE);
				pagetop_tv_name.setText("");
				pagetop_iv_back.setBackgroundResource(R.drawable.back_moren);
			} else if (nowScrollY < (lHeight - lHeight2)) {// =
				int progress = (int) (new Float(nowScrollY) / new Float(lHeight + 1) * 255);// 255
				// 参数a为透明度，取值范围为0-255，数值越小越透明。
				page_top_layout.getBackground().setAlpha(progress);

				pagetop_iv_line.setVisibility(View.GONE);
				pagetop_tv_name.setText(mer_name);
				pagetop_iv_back.setBackgroundResource(R.drawable.back);
			} else {
				pagetop_iv_line.setVisibility(View.VISIBLE);
				pagetop_tv_name.setText(mer_name);
				page_top_layout.getBackground().setAlpha(255);
				pagetop_iv_back.setBackgroundResource(R.drawable.back);
			}
		}
	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				if (!isFinishing())
					mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	HashMap<String, String> mhashmap;
	private LinearLayout mStadiumPropertiesView;
	private View venuehorizontalscrollsView;
	private View stadium_detail_golf_venues;
	private LinearLayout stadiundetail_layout_horizontal;
	private View mPinYuLanBottomView;
	private boolean isbook;
	/**
	 * 是否人工
	 */
	protected String process_type;
	private String book_info, description;

	@Override
	protected void processLogic() {
		stadium_detail_venuces.setVisibility(View.GONE);
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().setOnDismissDialog(mDialog);
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("mer_item_id", mer_item_id);
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		System.err.println(mhashmap);
		new StadiumDetailBusiness(this, mhashmap, new GetStadiumDetailCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(StadiumDetailActivity.this, msg);
					} else if (status.equals("200")) {

						StadiumDetailInfo stadiumDetailInfo = (StadiumDetailInfo) dataMap.get("mStadiumDetailInfo");
						if (stadiumDetailInfo != null) {
							// 场馆设施
							ArrayList<StadiumDetailInfo> mservicesList = (ArrayList<StadiumDetailInfo>) dataMap
									.get("mservicesList");
							// 场馆属性
							ArrayList<StadiumDetailInfo> mdisplayStadiumPropertiesList = (ArrayList<StadiumDetailInfo>) dataMap
									.get("mdisplayStadiumPropertiesList");
							process_type = stadiumDetailInfo.getProcess_type();
							inventory_type = stadiumDetailInfo.getInventory_type();
							price_info = stadiumDetailInfo.getPrice_info();
							collectStatus = stadiumDetailInfo.isCollectStatus();
							longitude = stadiumDetailInfo.getLongitude();
							latitude = stadiumDetailInfo.getLatitude();
							min_price = stadiumDetailInfo.getMin_price();
							teetimejiange = stadiumDetailInfo.getTeetimejiange();
							// 更新收藏状态
							mCollectUtils.collectStatus = collectStatus;
							mCollectUtils.uid = bestDoInfoSharedPrefs.getString("uid", "");
							stadium_detail_ralat_topbanner.setVisibility(View.VISIBLE);
							showAblum(stadiumDetailInfo);
							showStadiumInfoView(stadiumDetailInfo);
							setTopApha();
							loadDataMservices(mservicesList);
							loadDataMdisplay(mdisplayStadiumPropertiesList);
							setVenucesView();
						}
						mloadHandler.sendEmptyMessage(LOGINAUTOCOLLECT);
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}

		});

	}

	private void showAblum(StadiumDetailInfo stadiumDetailInfo) {
		ArrayList<BannerInfo> mAlbumsList = stadiumDetailInfo.getmAlbumsList();
		if (mAlbumsList != null && mAlbumsList.size() > 0) {
			viewpager_advert.setAdapter(new AdvertImagePagerAdapter(context, mAlbumsList, "", "", 0, 0));
			indicator.setViewPager(viewpager_advert);
			viewpager_advert.setInterval(2000);
			indicator.setCentered(false);
			indicator.setRight(true);
		}

	}

	private void showStadiumInfoView(StadiumDetailInfo stadiumDetailInfo) {
		address = stadiumDetailInfo.getAddress();
		merid = stadiumDetailInfo.getMerid();
		cid = stadiumDetailInfo.getCid();
		mer_name = stadiumDetailInfo.getName();
		isbook = stadiumDetailInfo.isIsbook();
		book_info = stadiumDetailInfo.getBook_info();
		description = stadiumDetailInfo.getDescription();
		if (!TextUtils.isEmpty(book_info)) {
			stadium_detail_layout_bookinfo.setVisibility(View.VISIBLE);
			stadium_detail_tv_bookinfo.setText(book_info);
		}
		stadium_abstract.setText(mer_name);
		stadium_detailmore_tv_stadiumname.setText(stadiumDetailInfo.getStadium_name());
		stadium_detailmore_tv_address.setText(stadiumDetailInfo.getAddress());
		stadium_detailmore_layout_address.setVisibility(View.VISIBLE);
		stadium_detailmore_layout_stadiumname.setVisibility(View.VISIBLE);
		bd_phone = stadiumDetailInfo.getBd_phone();
		if (!TextUtils.isEmpty(description)) {
			stadium_detailmore_tv_description.setText(description);
			stadium_detail_abstract_lin.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 加载场馆设施布局
	 * 
	 * @param mservicesList
	 */
	private void loadDataMservices(ArrayList<StadiumDetailInfo> mservicesList) {
		if (mservicesList != null && mservicesList.size() > 0) {

			servicegridview.setFocusable(false);
			int screenWidth = ConfigUtils.getInstance().getPhoneWidHeigth(context).widthPixels;
			if (screenWidth > 700) {
				servicegridview.setNumColumns(5);
			} else {
				servicegridview.setNumColumns(4);
			}
			servicegridview.setHorizontalSpacing(0);
			servicegridview.setVerticalSpacing(15);
			StadiumDetailServiceAdapter mServiceAdapter = new StadiumDetailServiceAdapter(StadiumDetailActivity.this,
					mservicesList);
			servicegridview.setAdapter(mServiceAdapter);
			mServiceAdapter = null;
			stadium_detailmore_layout_sheshi.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 加载场地属性布局
	 * 
	 * @param mdisplayStadiumPropertiesList
	 */
	private void loadDataMdisplay(ArrayList<StadiumDetailInfo> mdisplayStadiumPropertiesList) {
		stadium_detail_layout_propertieszuo.removeAllViews();
		stadium_detail_layout_propertiesyou.removeAllViews();
		if (mdisplayStadiumPropertiesList != null && mdisplayStadiumPropertiesList.size() != 0) {
			for (int i = 0; i < mdisplayStadiumPropertiesList.size(); i++) {
				String name = mdisplayStadiumPropertiesList.get(i).getDisplayStadiumProperties_name();
				String value = mdisplayStadiumPropertiesList.get(i).getDisplayStadiumProperties_value();
				if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(value)) {
					mStadiumPropertiesView = (LinearLayout) LayoutInflater.from(context)
							.inflate(R.layout.stadium_detail_properties, null);
					TextView textView = (TextView) mStadiumPropertiesView
							.findViewById(R.id.stadium_detail_properties_name);
					TextView stadium_detail_properties_value = (TextView) mStadiumPropertiesView
							.findViewById(R.id.stadium_detail_properties_value);

					textView.setText(name + getString(R.string.unit_fuhao_maohao));
					stadium_detail_properties_value.setText(value);
					if (i % 2 == 0) {
						stadium_detail_layout_propertieszuo.addView(mStadiumPropertiesView);
					} else {
						stadium_detail_layout_propertiesyou.addView(mStadiumPropertiesView);
					}
				}
			}
			stadium_detail_layout_properties.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 加载场地View
	 */
	private void setVenucesView() {
		stadium_detail_venuces.removeAllViews();
		@SuppressWarnings("deprecation")
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.FILL_PARENT,
				android.widget.LinearLayout.LayoutParams.FILL_PARENT);
		params.setMargins(0, 0, 0, 0);
		stadium_detail_venuces.setLayoutParams(params);
		if (cid.equals(Constans.getInstance().sportCidGolf) || cid.equals(Constans.getInstance().sportCidGolfrange)
				|| merid.equals(Constans.getInstance().sportMeridHighswimming)) {
			/**
			 * 直接请求单独的接口和某时段接口
			 */
			mloadHandler.sendEmptyMessage(LOADGOLF7DAY);
		} else {
			mloadHandler.sendEmptyMessage(LOAD7DAY);
		}

	}

	/**
	 * 加载7天的报价数据（高尔夫练习场）
	 */
	private final int LOADGOLF7DAY = 0;
	/**
	 * 加载7天的报价数据（羽毛球网球游泳健身）
	 */
	private final int LOAD7DAY = 1;
	/**
	 * 某天指定小时商品明细的价格（高尔夫练习场游泳健身）
	 */
	private final int LOADONEDAYHOUR = 2;

	/**
	 * 更新收藏状态
	 */
	private final int UPDATECOLLECTSTATUS = 3;
	/**
	 * 登录后自动收藏
	 */
	private final int LOGINAUTOCOLLECT = 4;
	/**
	 * 登录后重新请求场馆数据
	 */
	private final int RELOAD = 5;
	private final int SHOWMEMBERINFO = 22;
	Handler mloadHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOADGOLF7DAY:
				loadGolf7DayPrices();
				break;
			case LOAD7DAY:
				load7DaysPrices();
				break;
			case LOADONEDAYHOUR:
				getOneDayHoursMerItemPrice();
				break;
			case SHOWMEMBERINFO:
				getMemberInfo();
				break;
			case UPDATECOLLECTSTATUS:
				collectStatus = mCollectUtils.collectStatus;
				setTopApha();
				break;
			case LOGINAUTOCOLLECT:
				mCollectUtils.uid = bestDoInfoSharedPrefs.getString("uid", "");
				if (loginAutoCollectStatus && !collectStatus && mCollectUtils.clickloadingoverstatus) {
					mCollectUtils.clickloadingoverstatus = false;
					loginAutoCollectStatus = false;
					mCollectUtils.getClickFavorite();
				}
				break;
			case RELOAD:
				processLogic();
				break;
			}
		}
	};

	/**
	 * 加载7天的报价数据（羽毛球网球游泳健身）
	 */
	private void load7DaysPrices() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("mer_item_id", mer_item_id);
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		System.err.println(mhashmap.toString());
		new StadiumDetailMerItemPriceBisiness(this, mhashmap, new GetMerItemPriceCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(StadiumDetailActivity.this, msg);
					} else if (status.equals("200")) {
						m7DaysPriceList = (ArrayList<StadiumDetailMerItemPriceInfo>) dataMap.get("m7DaysPriceList");
						if (m7DaysPriceList != null && m7DaysPriceList.size() > 0) {
							if (inventory_type.equals("1")) {
								setPinYuLanView();
							} else if (inventory_type.equals("3")) {
								setSwimFitnessView();
							}
						}
					}
				}
				CommonUtils.getInstance().setClearCacheDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	/**
	 * 加载golf 练习场7天报价
	 */
	private void loadGolf7DayPrices() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("mer_item_id", mer_item_id);
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		System.err.println(mhashmap.toString());
		new StadiumDetailMerItemPriceGolfBisiness(this, mhashmap, teetimejiange, cid,
				new GetMerItemPriceGolfCallback() {
					@Override
					public void afterDataGet(HashMap<String, Object> dataMap) {
						if (dataMap != null) {
							String status = (String) dataMap.get("status");
							if (status.equals("400")) {
								String msg = (String) dataMap.get("msg");
								CommonUtils.getInstance().initToast(StadiumDetailActivity.this, msg);
							} else if (status.equals("200")) {
								StadiumDetailMerItemPriceGolfInfo mMerItemPriceGolfInfo = (StadiumDetailMerItemPriceGolfInfo) dataMap
										.get("mMerItemPriceGolfInfo");
								if (mMerItemPriceGolfInfo != null) {
									setGolfView(mMerItemPriceGolfInfo);
								}
							}
						}
						CommonUtils.getInstance().setClearCacheDialog(mDialog);
						CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
					}
				});

	}

	/**
	 * 加载高尔夫和高尔夫练习场View
	 */
	TextView stadium_detail_golf_time_text;

	private void setGolfView(StadiumDetailMerItemPriceGolfInfo mMerItemPriceGolfInfo) {
		stadium_detail_golf_venues = LayoutInflater.from(context).inflate(R.layout.stadium_detail_golf_venues, null);
		stadium_detail_golf_time_text = (TextView) stadium_detail_golf_venues
				.findViewById(R.id.stadium_detail_golf_time_text);
		/*
		 * 初始化信息
		 */
		isorderList = mMerItemPriceGolfInfo.getIsorderList();
		System.err.println(isorderList);
		days = mMerItemPriceGolfInfo.getDays();
		daysshow = mMerItemPriceGolfInfo.getDaysshow();
		hoursgolf = mMerItemPriceGolfInfo.getHoursgolf();
		hoursgolfange = mMerItemPriceGolfInfo.getHoursgolfange();
		System.err.println(days);
		if (days != null && days.length > 0 && hoursgolf[0] != null && hoursgolf[0].length > 0) {
			firstIndex_select = 0;
			secondIndex_select = 0;
			initGolfDate();

			stadium_detail_venuces.addView(stadium_detail_golf_venues);
			stadium_detail_venuces.setVisibility(View.VISIBLE);
			stadium_detail_venuces.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					getDateOrTeetimeDialog();
				}
			});
			mloadHandler.sendEmptyMessage(LOADONEDAYHOUR);
		} else {
			// 无数据价格时
			ShowDayPriceBottomView(false, null);
		}
	}

	private void initGolfDate() {
		currentSelectDate = days[firstIndex_select];
		boolean is_order = isorderList.get(firstIndex_select);
		if (is_order) {
			selectDayBookStatus = true;
		}
		String startplaytime = playtime;
		if (cid.equals(Constans.getInstance().sportCidGolf)) {
			playtime = hoursgolf[firstIndex_select][secondIndex_select];
			hour = DatesUtils.getInstance().getDateGeShi(playtime, "HH:mm", "H");
			startplaytime = playtime;
		} else if (cid.equals(Constans.getInstance().sportCidGolfrange)
				|| merid.equals(Constans.getInstance().sportMeridHighswimming)) {
			playtime = hoursgolfange[firstIndex_select][secondIndex_select];
			startplaytime = playtime.substring(0, playtime.indexOf("-"));
			hour = DatesUtils.getInstance().getDateGeShi(startplaytime, "HH:mm", "H");
		}
		// 过了打球时段的开始时间不能进行预订
		String nowHour = DatesUtils.getInstance().getNowTime("HH:mm");
		String nowday = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");

		boolean timestaus = DatesUtils.getInstance().doCheck2Date(startplaytime, nowHour, "HH:mm");
		boolean doequeday = DatesUtils.getInstance().doDateEqual(currentSelectDate, nowday, "yyyy-MM-dd");
		if (doequeday && timestaus) {
			selectDayBookStatus = true;
		}
		String showDate = DatesUtils.getInstance().getDateGeShi(currentSelectDate, "yyyy-MM-dd", "MM月dd日 EE");
		if (showDate.contains("星期")) {
			showDate = showDate.replace("星期", "周");
		}
		stadium_detail_golf_time_text.setText(showDate + "  " + playtime);
	}

	ArrayList<Boolean> isorderList;
	String[] daysshow;
	String[] days;
	String[][] hoursgolf;
	String[][] hoursgolfange;
	int firstIndex_select;
	int secondIndex_select;
	int firstIndex;
	int secondIndex;
	MyDialog selectDialog;

	public void getDateOrTeetimeDialog() {
		// 防止索引小于0
		selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_teetime);// 创建Dialog并设置样式主题
		Window dialogWindow = selectDialog.getWindow();
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		dialogWindow.setWindowAnimations(R.style.showAnDialog);
		selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		final LinearLayout teetime_lay = (LinearLayout) selectDialog.findViewById(R.id.teetime_lay);
		teetime_lay.setLayoutParams(new FrameLayout.LayoutParams(
				ConfigUtils.getInstance().getPhoneWidHeigth(context).widthPixels, LayoutParams.WRAP_CONTENT));
		TextView teetime_title = (TextView) selectDialog.findViewById(R.id.teetime_title);
		TextView teetime_none = (TextView) selectDialog.findViewById(R.id.teetime_none);// 无teetime
		TextView teetime_sure = (TextView) selectDialog.findViewById(R.id.teetime_sure);// 确定
		WheelView teetime_hour = (WheelView) selectDialog.findViewById(R.id.teetime_hour);
		final WheelView teetime_mins = (WheelView) selectDialog.findViewById(R.id.teetime_mins);

		teetime_hour.setVisibleItems(5);
		teetime_hour.setAdapter(new ArrayWheelAdapter<String>(daysshow, daysshow.length));
		teetime_hour.setCurrentItem(firstIndex_select);
		teetime_mins.setVisibleItems(5);
		if (cid.equals(Constans.getInstance().sportCidGolf)) {
			teetime_title.setText(context.getResources().getString(R.string.stadium_detail_teetime));
			teetime_mins.setAdapter(new ArrayWheelAdapter<String>(hoursgolf[firstIndex_select]));
		} else if (cid.equals(Constans.getInstance().sportCidGolfrange)
				|| merid.equals(Constans.getInstance().sportMeridHighswimming)) {
			teetime_title.setText(context.getResources().getString(R.string.stadium_detail_playteetime));
			teetime_mins.setAdapter(new ArrayWheelAdapter<String>(hoursgolfange[firstIndex_select]));
		}
		teetime_mins.setCurrentItem(secondIndex_select);
		teetime_mins.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				secondIndex = newValue;
			}
		});
		teetime_hour.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (cid.equals(Constans.getInstance().sportCidGolf)) {
					teetime_mins.setAdapter(new ArrayWheelAdapter<String>(hoursgolf[newValue]));
				} else if (cid.equals(Constans.getInstance().sportCidGolfrange)
						|| merid.equals(Constans.getInstance().sportMeridHighswimming)) {
					teetime_mins.setAdapter(new ArrayWheelAdapter<String>(hoursgolfange[newValue]));
				}
				firstIndex = newValue;
				teetime_mins.setCurrentItem(0);
			}
		});

		teetime_none.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				selectDialog = null;
			}
		});
		teetime_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				selectDialog = null;
				firstIndex_select = firstIndex;
				secondIndex_select = secondIndex;
				initGolfDate();
				mloadHandler.sendEmptyMessage(LOADONEDAYHOUR);
			}
		});
	}

	/**
	 * 加载游泳健身View
	 */
	private void setSwimFitnessView() {
		venuehorizontalscrollsView = LayoutInflater.from(context)
				.inflate(R.layout.stadium_detail_venue_horizontalscrolls, null);
		stadiundetail_layout_horizontal = (LinearLayout) venuehorizontalscrollsView
				.findViewById(R.id.stadiundetail_layout_horizontal);
		MyHorizontalScrollView venuesScrollViewBottom = (MyHorizontalScrollView) venuehorizontalscrollsView
				.findViewById(R.id.venuesScrollViewBottom);
		venuesScrollViewBottom.setScrollViewListener(new MyHorizontalScrollView.ScrollViewListener() {
			public void onScrollChanged(MyHorizontalScrollView paramAnonymousMyHorizontalScrollView,
					int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4) {
				pylwScrollViewX = paramAnonymousInt1;
			}
		});
		showSwimFitnesHorizontalScrollView();
		stadium_detail_venuces.addView(venuehorizontalscrollsView);
		stadium_detail_venuces.setVisibility(View.VISIBLE);

	}

	/**
	 * 加载游泳健身横向7天数据
	 */
	@SuppressWarnings("deprecation")
	private void showSwimFitnesHorizontalScrollView() {
		stadiundetail_layout_horizontal.removeAllViews();
		if (m7DaysPriceList != null && m7DaysPriceList.size() != 0) {
			getCurrentSelectDate(m7DaysPriceList.get(0).getDatetamp());
			// 判断默认是否可预定状态
			if (m7DaysPriceList.get(0).getIs_order() && m7DaysPriceList.get(0).getIs_select()) {
				selectDayBookStatus = true;
			}
			for (int i = 0; i < m7DaysPriceList.size(); i++) {
				mPinYuLanBottomView = LayoutInflater.from(context)
						.inflate(R.layout.stadium_detail_venue_swimfitnes_item, null);
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
				if (!isbook || !is_order) {
					stadium_detail_swimfitnes_item_time
							.setTextColor(getResources().getColor(R.color.text_noclick_color));
					stadium_detail_swimfitnes_item_week
							.setTextColor(getResources().getColor(R.color.text_noclick_color));

				} else {
					mPinYuLanBottomView.setTag(i);
					mPinYuLanBottomView.setOnClickListener(onswimClick);
					boolean is_select = m7DaysPriceList.get(i).getIs_select();
					if (is_select) {
						getCurrentSelectDate(m7DaysPriceList.get(i).getDatetamp());
						selectDayBookStatus = true;
						stadium_detail_swimfitnes_item_time.setTextColor(getResources().getColor(R.color.blue));
						stadium_detail_swimfitnes_item_week.setTextColor(getResources().getColor(R.color.blue));
						mPinYuLanBottomView.setBackgroundResource(R.drawable.stadium_detail_venues_corners_bg);
					} else {
						stadium_detail_swimfitnes_item_time
								.setTextColor(getResources().getColor(R.color.text_contents_color));
						stadium_detail_swimfitnes_item_week
								.setTextColor(getResources().getColor(R.color.text_contents_color));

					}
				}
				stadiundetail_layout_horizontal.addView(mPinYuLanBottomView);
			}
		}
		mloadHandler.sendEmptyMessage(LOADONEDAYHOUR);
	}

	/**
	 * 加载乒羽篮网 7天View
	 */
	private void setPinYuLanView() {
		if (m7DaysPriceList != null && m7DaysPriceList.size() != 0) {
			venuehorizontalscrollsView = LayoutInflater.from(context)
					.inflate(R.layout.stadium_detail_venue_horizontalscrolls, null);
			stadiundetail_layout_horizontal = (LinearLayout) venuehorizontalscrollsView
					.findViewById(R.id.stadiundetail_layout_horizontal);
			MyHorizontalScrollView venuesScrollViewBottom = (MyHorizontalScrollView) venuehorizontalscrollsView
					.findViewById(R.id.venuesScrollViewBottom);
			venuesScrollViewBottom.setScrollViewListener(new MyHorizontalScrollView.ScrollViewListener() {
				public void onScrollChanged(MyHorizontalScrollView paramAnonymousMyHorizontalScrollView,
						int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3,
						int paramAnonymousInt4) {
					pylwScrollViewX = paramAnonymousInt1;
				}
			});
			showPYLWHorizontalScrollView();
			stadium_detail_venuces.addView(venuehorizontalscrollsView);
			stadium_detail_venuces.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 加载venuesScrollViewBottom布局
	 */
	private void showPYLWHorizontalScrollView() {
		// 下部场片
		stadiundetail_layout_horizontal.removeAllViews();
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
				if (!isbook || !is_order) {
					stadium_detail_swimfitnes_item_time
							.setTextColor(getResources().getColor(R.color.text_noclick_color));
					stadium_detail_swimfitnes_item_week
							.setTextColor(getResources().getColor(R.color.text_noclick_color));

				} else {
					mPinYuLanBottomView.setTag(i);
					mPinYuLanBottomView.setOnClickListener(onPinYuLanWeekClick);
					boolean is_select = m7DaysPriceList.get(i).getIs_select();
					if (is_select) {
						getCurrentSelectDate(m7DaysPriceList.get(i).getDatetamp());
						mer_price_id = m7DaysPriceList.get(i).getMer_price_id();
						selectDayBookStatus = true;
						stadium_detail_swimfitnes_item_time.setTextColor(getResources().getColor(R.color.blue));
						stadium_detail_swimfitnes_item_week.setTextColor(getResources().getColor(R.color.blue));
						mPinYuLanBottomView.setBackgroundResource(R.drawable.stadium_detail_venues_corners_bg);
						setClickPYLW(i);
					} else {
						stadium_detail_swimfitnes_item_time
								.setTextColor(getResources().getColor(R.color.text_contents_color));
						stadium_detail_swimfitnes_item_week
								.setTextColor(getResources().getColor(R.color.text_contents_color));
					}
				}
				stadiundetail_layout_horizontal.addView(mPinYuLanBottomView);
			}
		}
	}

	private void setClickPYLW(int i) {
		String venue_min_price = m7DaysPriceList.get(i).getMin_price();
		String normal_price = m7DaysPriceList.get(i).getMax_price();
		StadiumDetailOneDayHourMerItemPriceInfo mOneDayHourMerItemPriceInfo = new StadiumDetailOneDayHourMerItemPriceInfo();
		mOneDayHourMerItemPriceInfo.setVip_price(venue_min_price);
		mOneDayHourMerItemPriceInfo.setVisitor_price(normal_price);
		mOneDayHourMerItemPriceInfo.setDayindex(i);
		mOneDayHourMerItemPriceInfo.setMer_price_id(m7DaysPriceList.get(i).getMer_price_id());
		ShowDayPriceBottomView(true, mOneDayHourMerItemPriceInfo);
	}

	/**
	 * 获取当前日期
	 * 
	 * @param datetamp
	 */
	private void getCurrentSelectDate(int datetamp) {
		currentSelectDate = DatesUtils.getInstance().getTimeStampToDate(datetamp, "yyyy-MM-dd");
	}

	/**
	 * 高尔夫练习场游泳健身
	 */
	private void getOneDayHoursMerItemPrice() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		mhashmap.put("mer_item_id", mer_item_id);
		mhashmap.put("vip_price_id", vip_price_id);
		mhashmap.put("merid", merid);
		mhashmap.put("date", currentSelectDate);
		mhashmap.put("hours", hour + "");
		showDilag();
		System.err.println(mhashmap.toString());
		new StadiumDetailHoursMerItemPriceBisiness(this, mhashmap, new GetHoursMerItemPriceCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(StadiumDetailActivity.this, msg);
					} else if (status.equals("200")) {
						StadiumDetailOneDayHourMerItemPriceInfo mOneDayHourMerItemPriceInfo = (StadiumDetailOneDayHourMerItemPriceInfo) dataMap
								.get("mOneDayHourMerItemPriceInfo");
						ShowDayPriceBottomView(false, mOneDayHourMerItemPriceInfo);
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
				if (!isFinishing())
					CommonUtils.getInstance().setClearCacheDialog(mDialog);
			}
		});

	}

	/**
	 * 选中的日期是否可以预定--默认不可以
	 */
	boolean selectDayBookStatus = false;
	/**
	 * 会员状态
	 */
	protected String identityId;

	/**
	 * 
	 * @param mPYLWShowStatus
	 * @param mOneDayHourMerItemPriceInfo
	 */
	private void ShowDayPriceBottomView(Boolean mPYLWShowStatus,
			StadiumDetailOneDayHourMerItemPriceInfo mOneDayHourMerItemPriceInfo) {
		if (mOneDayHourMerItemPriceInfo != null) {
			vip_price = mOneDayHourMerItemPriceInfo.getVip_price();
			mer_price_id = mOneDayHourMerItemPriceInfo.getMer_price_id();
			origeprice = mOneDayHourMerItemPriceInfo.getVisitor_price();
			starthour = mOneDayHourMerItemPriceInfo.getStart_hour();
			endhour = mOneDayHourMerItemPriceInfo.getEnd_hour();

			if (isbook && selectDayBookStatus && Double.valueOf(vip_price) > 0 && Double.valueOf(origeprice) > 0) {
				stadiumdetails_tv_book.setEnabled(true);
				if (mPYLWShowStatus) {
					stadiumdetails_tv_book.setTag(mOneDayHourMerItemPriceInfo.getDayindex());
					stadiumdetails_tv_book.setOnClickListener(onPinYuLanWeekClick);
					stadiumdetails_tv_book.setText("选择场次");
				} else {
					stadiumdetails_tv_book.setTag(mOneDayHourMerItemPriceInfo);
					stadiumdetails_tv_book.setOnClickListener(onGolfOtherClick);
				}
				stadiumdetails_tv_book.setBackgroundColor(getResources().getColor(R.color.btn_blue_color));
			} else {
				stadiumdetails_tv_book.setEnabled(false);
				stadiumdetails_tv_book.setBackgroundColor(getResources().getColor(R.color.text_noclick_color));
			}
		} else {
			stadium_detail_item_tv_vipprice.setText("不可预订");
			stadium_detail_item_tv_vipprice.setTextColor(getResources().getColor(R.color.text_noclick_color));
			stadium_detail_item_tv_vipprice.setBackgroundResource(R.drawable.corners_bg);
			stadiumdetails_tv_book.setEnabled(false);
			stadiumdetails_tv_book.setBackgroundColor(getResources().getColor(R.color.text_noclick_color));
		}
		showMemberPriceView();
	}

	private void showMemberPriceView() {
		String loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
		if (!loginStatus.equals(Constans.getInstance().loginStatus)) {
			showCommomMemberView();
		} else {
			mloadHandler.sendEmptyMessage(SHOWMEMBERINFO);
		}

	}

	/**
	 * 显示普通用户,非会员 的价格样式
	 */
	private void showCommomMemberView() {
		stadium_detail_item_tv_vipprice
				.setText("会员价：" + getResources().getString(R.string.unit_fuhao_money) + origeprice);
		stadiumdetail_layout_member.setVisibility(View.VISIBLE);
		stadiumdetail_layout_member.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkLoginStatus(Constans.getInstance().loginrefreshDetail)) {
					toMemberDialpg();
				}
			}
		});
	}

	private void showMemberView() {
		stadium_detail_item_tv_vipprice
				.setText("会员价：" + getResources().getString(R.string.unit_fuhao_money) + origeprice);
		stadiumdetail_layout_member.setVisibility(View.GONE);
	}

	public void toMemberDialpg() {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_tomember);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView text_off = (TextView) selectDialog.findViewById(R.id.cancel);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.sure);// 确定
		text_off.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}

		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				Intent intent = new Intent(StadiumDetailActivity.this, UserMeberActiyity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, StadiumDetailActivity.this);
			}
		});
	}

	private void getMemberInfo() {
//		showDilag();
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
						identityId = muserCenterMemberInfo.getIdentityId();
						if (!TextUtils.isEmpty(identityId) && !identityId.equals(Constans.getInstance().identityCommomUser)) {
							// 会员
							showMemberView();
						} else {
							showCommomMemberView();
						}
					} else {
						showCommomMemberView();
					}
				} else {
					showCommomMemberView();
				}
			}
		});
	}

	/**
	 * 判断登录状态
	 * 
	 * @param loginskiptostatus
	 * @return true=登录
	 */
	private boolean checkLoginStatus(String loginskiptostatus) {
		boolean loginstatus = true;
		String loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
		if (!loginStatus.equals(Constans.getInstance().loginStatus)) {
			bestDoInfoEditor.putString("loginskiptostatus", loginskiptostatus);
			bestDoInfoEditor.commit();
			Intent intent2 = new Intent(StadiumDetailActivity.this, UserLoginActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, StadiumDetailActivity.this);
			loginstatus = false;
		}
		return loginstatus;
	}

	/**
	 * 乒羽篮网的点击事件
	 */
	private OnClickListener onPinYuLanWeekClick = new OnClickListener() {
		public void onClick(View v) {
			int i = (Integer) v.getTag();
			for (int j = 0; j < m7DaysPriceList.size(); j++) {
				if (i == j) {
					m7DaysPriceList.get(j).setIs_select(true);
				} else {
					m7DaysPriceList.get(j).setIs_select(false);
				}
			}
			showPYLWHorizontalScrollView();

			savePYLWOrderInfoEditor();
			if (checkLoginStatus(Constans.getInstance().loginrefreshDetail)) {
				if (meberStatus()) {
					Intent intent = new Intent(StadiumDetailActivity.this, CreatOrdersGetVenuePYLWActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);

					CommonUtils.getInstance().setPageIntentAnim(intent, StadiumDetailActivity.this);
				}
			}
		}
	};
	private OnClickListener onGolfOtherClick = new OnClickListener() {
		public void onClick(View v) {
			savegolfOrderInfoEditor();
			if (checkLoginStatus(Constans.getInstance().loginrefreshDetail)) {
				if (meberStatus()) {
					Intent intents = new Intent(StadiumDetailActivity.this, CreatOrdersActivity.class);
					intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intents);
					CommonUtils.getInstance().setPageIntentAnim(intents, StadiumDetailActivity.this);
				}
			}
		}
	};
	
	private void savegolfOrderInfoEditor() {
		bestDoInfoEditor.putString("source", "2");// 2 Android
		bestDoInfoEditor.putString("cid", cid);
		bestDoInfoEditor.putString("mer_item_id", mer_item_id);
		bestDoInfoEditor.putString("mer_price_id", mer_price_id);
		bestDoInfoEditor.putString("merid", merid);
		bestDoInfoEditor.putString("book_day", currentSelectDate);
		bestDoInfoEditor.putString("start_hour", starthour);
		bestDoInfoEditor.putString("end_hour", endhour);
		bestDoInfoEditor.putString("select_price", origeprice);

		bestDoInfoEditor.putString("process_type", process_type);
		bestDoInfoEditor.putString("stadium_name", mer_name);
		bestDoInfoEditor.putString("contain", price_info);
		bestDoInfoEditor.putString("address", address);
		bestDoInfoEditor.putString("play_time", playtime);
		bestDoInfoEditor.putString("hour", hour);

		bestDoInfoEditor.commit();
	}

	private void savePYLWOrderInfoEditor() {
		bestDoInfoEditor.putString("source", "2");// 2 Android
		bestDoInfoEditor.putString("cid", cid);
		bestDoInfoEditor.putString("mer_item_id", mer_item_id);
		bestDoInfoEditor.putString("mer_price_id", mer_price_id);
		bestDoInfoEditor.putString("merid", merid);
		bestDoInfoEditor.putString("book_day", currentSelectDate);
		bestDoInfoEditor.putString("play_time", playtime);
		bestDoInfoEditor.putString("process_type", process_type);

		bestDoInfoEditor.putFloat("pylwScrollViewX", pylwScrollViewX);
		bestDoInfoEditor.putString("stadium_name", mer_name);
		bestDoInfoEditor.putString("contain", price_info);
		bestDoInfoEditor.putString("address", address);

		String m7DaysPriceListString = CommonUtils.getInstance().SceneList2String(m7DaysPriceList);
		bestDoInfoEditor.putString("m7DaysPriceListString", m7DaysPriceListString);
		bestDoInfoEditor.commit();
	}

	/**
	 * 游泳健身的点击事件
	 */
	private OnClickListener onswimClick = new OnClickListener() {
		public void onClick(View v) {

			int i = (Integer) v.getTag();
			for (int j = 0; j < m7DaysPriceList.size(); j++) {
				if (i == j) {
					m7DaysPriceList.get(j).setIs_select(true);
				} else {
					m7DaysPriceList.get(j).setIs_select(false);
				}
			}
			showSwimFitnesHorizontalScrollView();
		}
	};

	// -----------------add cancel 收藏-------------------------------
	@Override
	protected void onStart() {
		super.onStart();
		// 动态注册广播
		filter = new IntentFilter();
		filter.addAction(context.getString(R.string.action_StadiumCollectUtils));
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(dynamicReceiver, filter);
	}

	IntentFilter filter;
	private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("from CreatOrdersActivity", "接收---登录注册后同步详情中收藏状态--广播消息");
			if (intent.getAction().equals(context.getString(R.string.action_StadiumCollectUtils))) {
				String type = intent.getStringExtra("type");
				if (!TextUtils.isEmpty(type) && type.equals(Constans.getInstance().loginrefreshDetail)) {
					mloadHandler.sendEmptyMessage(RELOAD);
				} else {
					loginAutoCollectStatus = true;
					mloadHandler.sendEmptyMessage(RELOAD);
				}
			}
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		viewpager_advert.stopAutoScroll();
	}

	@Override
	protected void onResume() {
		super.onResume();
		viewpager_advert.startAutoScroll();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(dynamicReceiver);
		dynamicReceiver = null;
		filter = null;
		super.onDestroy();
	}

	private void nowFinish() {
		if (!TextUtils.isEmpty(skipToDetailStatus)
				&& skipToDetailStatus.equals(Constans.getInstance().skipToDetailByCollectPage) && !collectStatus) {
			Intent intent = new Intent();
			intent.putExtra("collectStatus", collectStatus);
			setResult(Constans.getInstance().collectForResultByStaDetailPage, intent);
		}
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
