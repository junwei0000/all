package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshView.OnReflushListener;
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bestdo.bestdoStadiums.business.GetBannerImgBusiness;
import com.bestdo.bestdoStadiums.business.GetMainSportsBusiness;
import com.bestdo.bestdoStadiums.business.LodePersonDateBusiness;
import com.bestdo.bestdoStadiums.business.LodePersonDateBusiness.LodePersonDateCallback;
import com.bestdo.bestdoStadiums.business.SearchCityAreaBusiness;
import com.bestdo.bestdoStadiums.business.SearchCityAreaBusiness.GetCityAreaCallback;
import com.bestdo.bestdoStadiums.business.SearchCityFileBusiness;
import com.bestdo.bestdoStadiums.business.SearchCityFileBusiness.GetCityFileCallback;
import com.bestdo.bestdoStadiums.business.StadiumRecommendBusiness;
import com.bestdo.bestdoStadiums.business.GetBannerImgBusiness.GetBannerImgCallback;
import com.bestdo.bestdoStadiums.business.GetMainSportsBusiness.GetMainSportsCallback;
import com.bestdo.bestdoStadiums.business.StadiumRecommendBusiness.GetStadiumCallback;
import com.bestdo.bestdoStadiums.control.adapter.AdvertImagePagerAdapter;
import com.bestdo.bestdoStadiums.control.adapter.BestDoSportAdapter;
import com.bestdo.bestdoStadiums.control.adapter.BusinessNaviAdapter;
import com.bestdo.bestdoStadiums.control.adapter.PersonFeatureServerAdapter;
import com.bestdo.bestdoStadiums.control.adapter.StadiumAdapter;
import com.bestdo.bestdoStadiums.control.pullable.PullToRefreshLayout;
import com.bestdo.bestdoStadiums.control.pullable.PullableScrollView;
import com.bestdo.bestdoStadiums.control.pullable.PullToRefreshLayout.OnRefreshListener;
import com.bestdo.bestdoStadiums.control.view.AutoScrollViewPager;
import com.bestdo.bestdoStadiums.control.view.CirclePageIndicator;
import com.bestdo.bestdoStadiums.control.view.PageControlView;
import com.bestdo.bestdoStadiums.control.view.PageIndicator;
import com.bestdo.bestdoStadiums.control.view.ScrollLayout;
import com.bestdo.bestdoStadiums.control.view.ScrollLayout.OnScreenChangeListenerDataLoad;
import com.bestdo.bestdoStadiums.model.BannerInfo;
import com.bestdo.bestdoStadiums.model.BusinessBannerInfo;
import com.bestdo.bestdoStadiums.model.ServersInfo;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.AppUpdate;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.utils.MyScrollView;
import com.bestdo.bestdoStadiums.utils.MyScrollView.ScrollViewListener;
import com.bestdo.bestdoStadiums.utils.MyGridView;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.parser.SearchGetSportParser;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-8-16 下午5:07:26
 * @Description 类描述：个人首页
 */
public class MainPersonActivity extends BaseActivity implements OnItemClickListener {

	private AutoScrollViewPager viewPager;
	private CirclePageIndicator mIndicator;
	private MyListView main_myListView;
	// private MyStadiumServiceGridView main_gv_sport;
	private ArrayList<SportTypeInfo> sportTypeList;
	private ArrayList<StadiumInfo> mList;
	private StadiumAdapter mStadiumAdapter;
	// private PullToRefreshLayout pullToRefreshLayout;
	private MyScrollView scrollview;
	private RelativeLayout banner;
	private LinearLayout rl_title;
	private ImageView main_top_view;
	private LinearLayout main_layout_top, main_layout_top2;
	private View main_layout_line;
	private TextView main_search_edittext, main_city_text_select;
	private LinearLayout main_city_select_layout;

	private LinearLayout not_date;
	private ImageView not_date_img;
	private TextView not_date_cont;

	private LocationClient mLocationClient = null;
	private LocationClientOption option;
	private BDLocationListener myListener = new MyLocationListener();

	private HashMap<String, String> mAllMap;

	private ArrayList<BannerInfo> imgList;
	private ProgressDialog mDialog;
	HashMap<String, String> mHashMap;

	private int errorCode;

	private double longitude_current;
	private double latitude_current;
	private String cityid_current;
	private String city_current = "";// 定位到的城市，定位失败时默认北京

	private double longitude_select = 116.404269;
	private double latitude_select = 39.91405;
	private String cityid_select = "52";// 城市id
	private String cityid_selectBefore = "";// 切换之前的城市id
	private String city_select = "";
	private String myLocationokstatus;
	private String myselectcurrentcitystatus;

	private String address;
	private int scrollY;
	private LinearLayout main_layout_tuijian;

	private ScrollLayout mScrollLayout;
	private PageControlView pageControl;
	public int n = 0;
	private DataLoading dataLoad;
	private SharedPreferences bestDoInfoSharedPrefs;
	private GridView person_mygridview;
	/**
	 * 每页显示的商品数量
	 */
	protected int APP_PAGE_SIZE;
	private XRefreshView main_xrefresh;

	@Override
	protected void onPause() {
		super.onPause();
		viewPager.stopAutoScroll();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	protected void onResume() {
		super.onResume();
		viewPager.startAutoScroll();
		MobclickAgent.onPageStart("MainScreen");
		setApha();
		if (CommonUtils.getInstance().refleshScroolStatus) {
			CommonUtils.getInstance().refleshScroolStatus = false;
			scrollview.fullScroll(ScrollView.FOCUS_UP);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.main_search_edittext:
			if (longitude_current == 0 || latitude_current == 0) {
				longitude_current = 116.404269;
				latitude_current = 39.91405;
			}
			Intent in = new Intent(context, SearchKeyWordActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			in.putExtra("longitude_current", "" + longitude_current);
			in.putExtra("latitude_current", "" + latitude_current);
			startActivity(in);
			CommonUtils.getInstance().setPageIntentAnim(in, context);
			break;
		case R.id.main_city_select_layout:
			Intent cityintent = new Intent(context, SearchCityActivity.class);
			cityintent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			cityintent.putExtra("longitude_current", longitude_current);
			cityintent.putExtra("latitude_current", latitude_current);
			cityintent.putExtra("city_current", city_current);
			cityintent.putExtra("city_select", city_select);
			cityintent.putExtra("cityid_select", cityid_select);
			cityintent.putExtra("cityid_current", cityid_current);
			cityintent.putExtra("myLocationokstatus", myLocationokstatus);
			cityintent.putExtra("myselectcurrentcitystatus", myselectcurrentcitystatus);

			startActivityForResult(cityintent, Constans.getInstance().searchForResultByCityPage);
			CommonUtils.getInstance().setPageIntentAnim(cityintent, context);
			break;

		default:
			break;
		}
	}

	/**
	 * 初始化定位
	 * 
	 * @param welcomeEditor
	 */
	private void initSelectLocation() {
		welcomeEditor.putString("cityid_select", cityid_select);
		welcomeEditor.putString("city_select", city_select);
		welcomeEditor.putString("longitude_select", "" + longitude_select);
		welcomeEditor.putString("latitude_select", "" + latitude_select);
		welcomeEditor.commit();
	}

	SharedPreferences welcomeSPF;
	Editor welcomeEditor;
	private TextView main_personal_tese_text;
	private TextView main_personal_yuding_text;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.main_personal);
		CommonUtils.getInstance().addActivity(this);
		String welcomeSPFKey = Constans.getInstance().welcomeSharedPrefsKey;
		welcomeSPF = getSharedPreferences(welcomeSPFKey, 0);
		welcomeEditor = welcomeSPF.edit();

		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		scrollview = (MyScrollView) findViewById(R.id.listview_placemore);
		rl_title = (LinearLayout) findViewById(R.id.toprela);
		rl_title.getBackground().setAlpha(0);
		main_top_view = (ImageView) findViewById(R.id.main_top_view);
		main_layout_line = (View) findViewById(R.id.main_layout_line);
		main_layout_top = (LinearLayout) findViewById(R.id.main_layout_top);
		main_layout_top2 = (LinearLayout) findViewById(R.id.main_layout_top2);
		main_layout_top2.getBackground().setAlpha(0);
		banner = (RelativeLayout) findViewById(R.id.banner);
		viewPager = (AutoScrollViewPager) findViewById(R.id.view_pager_advert);
		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		main_myListView = (MyListView) findViewById(R.id.main_myListView);
		// main_gv_sport = (MyStadiumServiceGridView)
		// findViewById(R.id.main_gv_sport2);
		main_xrefresh = (XRefreshView) findViewById(R.id.main_xrefresh);
		main_layout_tuijian = (LinearLayout) findViewById(R.id.main_layout_tuijian);
		// pullToRefreshLayout = ((PullToRefreshLayout)
		// findViewById(R.id.refresh_view));
		main_city_select_layout = (LinearLayout) findViewById(R.id.main_city_select_layout);
		main_search_edittext = (TextView) findViewById(R.id.main_search_edittext);
		main_search_edittext.setInputType(InputType.TYPE_NULL);
		main_city_text_select = (TextView) findViewById(R.id.main_city_text_select);
		main_personal_tese_text = (TextView) findViewById(R.id.main_personal_tese_text);
		main_personal_yuding_text = (TextView) findViewById(R.id.main_personal_yuding_text);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		not_date_img = (ImageView) findViewById(R.id.not_date_img);
		not_date_cont = (TextView) findViewById(R.id.not_date_cont);

		mScrollLayout = (ScrollLayout) findViewById(R.id.ScrollLayoutTest);
		person_mygridview = (GridView) findViewById(R.id.main_personal_mygridview);
	}

	@Override
	protected void setListener() {
		pagetop_tv_name.setText("个人订场");
		pagetop_layout_back.setOnClickListener(this);
		main_layout_tuijian.setVisibility(View.INVISIBLE);
		main_myListView.setOnItemClickListener(this);
		main_search_edittext.setOnClickListener(this);
		main_city_select_layout.setOnClickListener(this);
		main_xrefresh.setPullLoadEnable(true);
		main_xrefresh.setAutoRefresh(false);
		// XRefreshView下拉刷新时机有了更强大的判断方法，已经不需要再设置view的类型了
		// outView.setRefreshViewType(XRefreshViewType.ABSLISTVIEW);

		main_xrefresh.setXRefreshViewListener(new SimpleXRefreshListener() {

			@Override
			public void onRefresh() {

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mHandler.sendEmptyMessage(NOSHOWING);
						mHandler.sendEmptyMessage(CITYCALLBACK);
						main_xrefresh.stopRefresh();
					}
				}, 1000);
			}

			@Override
			public void onLoadMore() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mHandler.sendEmptyMessage(LOADMORE);
					}
				}, 1000);
			}
		});
		main_xrefresh.setOnReflushListener(new OnReflushListener() {

			@Override
			public void isShow(boolean show) {
				if (show) {
					// rl_title.setVisibility(View.GONE);
					mHandler.sendEmptyMessage(NOSHOWING);
				} else {
					// rl_title.setVisibility(View.VISIBLE);
					mHandler.sendEmptyMessage(SHOWING);
				}
			}
		});

		scrollview.setScrollViewListener(new ScrollViewListener() {
			@Override
			public void onScrollChanged(MyScrollView paramMyScrollView, int paramInt1, int paramInt2, int paramInt3,
					int paramInt4) {
				scrollY = paramInt2;
				setApha();
			}
		});
		person_mygridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (serversList != null && serversList.size() != 0) {
					ServersInfo mBannerInfo = serversList.get(arg2);
					String html_url = mBannerInfo.getUrl();
					if (!TextUtils.isEmpty(html_url) && !html_url.equals("null")) {
						String name= mBannerInfo.getName();
						CommonUtils.getInstance().toH5(context,html_url, name, "",true);
					}
				}
			}
		});
	}

	private void setApha() {
		if (banner != null && banner.getHeight() > 0) {
			// define it for scroll height
			int lHeight = banner.getHeight();
			int lHeight2 = rl_title.getHeight();
			if (scrollY < (lHeight - lHeight2)) {// =
				int progress = (int) (new Float(scrollY) / new Float(lHeight) * 255);// 255
				// 参数a为透明度，取值范围为0-255，数值越小越透明。
				rl_title.getBackground().setAlpha(progress);
				main_layout_top.getBackground().setAlpha(255);
				main_layout_line.getBackground().setAlpha(255);
				main_layout_top2.getBackground().setAlpha(0);
				main_top_view.setVisibility(View.GONE);
			} else {
				rl_title.getBackground().setAlpha(255);
				main_top_view.setVisibility(View.VISIBLE);
				main_layout_top2.getBackground().setAlpha(255);
				main_layout_top.getBackground().setAlpha(0);
				main_layout_line.getBackground().setAlpha(0);
			}
		}
	}

	@Override
	protected void processLogic() {
		city_select = welcomeSPF.getString("city_select", city_select);
		cityid_select = welcomeSPF.getString("cityid_select", cityid_select);
		longitude_select = Double.valueOf(welcomeSPF.getString("longitude_select", "" + longitude_select));
		latitude_select = Double.valueOf(welcomeSPF.getString("latitude_select", "" + latitude_select));
		showDilag();
		// loadBanner();
		dataLoad = new DataLoading();
		mHandler.sendEmptyMessage(CITYCALLBACK);
		mHandler.sendEmptyMessage(UPDATELOCATION);
	}

	/**
	 * 城市选择回调更新数据
	 */
	private final int CITYCALLBACK = 1;
	private final int UPDATELOCATION = 3;
	private final int STARTLOCATION = 0;
	private final int UPDATECITYAREA = 7;
	private final int LOADMORE = 4;
	private final int SHOWING = 5;
	private final int NOSHOWING = 6;
	private ArrayList<ServersInfo> serversList;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CITYCALLBACK:
				mHandler.sendEmptyMessage(UPDATECITYAREA);
				main_city_text_select.setText(city_select);
				// loadBanner();
				loadPersonData();
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					init();
					loadList();
				}
				break;
			case UPDATECITYAREA:
				loadCityAreaData();
				break;
			case UPDATELOCATION:
				// loadSport();
				loadCityData();
				break;
			case STARTLOCATION:
				loadMapLocation();
				break;

			case LOADMORE:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					page++;
					loadList();
				}
				break;
			case SHOWING:
				rl_title.setVisibility(View.VISIBLE);
				break;
			case NOSHOWING:
				rl_title.setVisibility(View.GONE);
				break;
			}
		}
	};

	private void loadPersonData() {
		mHashMap = new HashMap<String, String>();
		new LodePersonDateBusiness(context, mHashMap, new LodePersonDateCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {

						imgList = (ArrayList<BannerInfo>) dataMap.get("imgList");

						if (imgList != null) {
							viewPager.setAdapter(new AdvertImagePagerAdapter(context, imgList, "SkipfromMain",
									cityid_select, longitude_select, latitude_select));
							mIndicator.setViewPager(viewPager);
							mIndicator.setPageColor(getResources().getColor(R.color.text_noclick_color));
							mIndicator.setFillColor(getResources().getColor(R.color.white));
							viewPager.setInterval(2000);// 设置自动滚动的间隔时间，单位为毫秒
						}
						loadSport(dataMap);
						serversList = (ArrayList<ServersInfo>) dataMap.get("serversList");
						if (serversList != null && serversList.size() != 0) {
							PersonFeatureServerAdapter mBusinessNaviAdapter = new PersonFeatureServerAdapter(context,
									serversList);
							person_mygridview.setAdapter(mBusinessNaviAdapter);
							person_mygridview.setVerticalSpacing(10);

						}
					}

				}

			}
		});

	}

	/**
	 * 加载运动类型
	 */
	ArrayList<BestDoSportAdapter> mBestDoSportAdapterList;

	private void loadSport(HashMap<String, Object> dataMap) {
		if (dataMap != null && dataMap.get("status").equals("200")) {
			sportTypeList = (ArrayList<SportTypeInfo>) dataMap.get("mList");
			/**
			 * -----------控制显示个数----------
			 */
			int realPageSize = sportTypeList.size();
			APP_PAGE_SIZE = 10;
			if (APP_PAGE_SIZE > realPageSize) {
				APP_PAGE_SIZE = realPageSize;
			}
			String pageString = PriceUtils.getInstance().gteDividePrice(sportTypeList.size() + "", APP_PAGE_SIZE + "");
			int pageNo = (int) Math.ceil(Double.parseDouble(pageString));
			System.out.println("pageNo=" + pageNo);
			System.out.println("Math.ceil(sportTypeList.size()/ APP_PAGE_SIZE)="
					+ Math.ceil(sportTypeList.size() / APP_PAGE_SIZE));
			if (mScrollLayout.getChildCount() <= 0) {

				mBestDoSportAdapterList = new ArrayList<BestDoSportAdapter>();
				for (int page = 0; page < pageNo; page++) {
					MyGridView appPage = new MyGridView(context);
					appPage.setSelector(R.drawable.list_notselector);
					BestDoSportAdapter mMainSportAdapter2 = new BestDoSportAdapter(MainPersonActivity.this, context,
							sportTypeList, page, APP_PAGE_SIZE);
					mBestDoSportAdapterList.add(mMainSportAdapter2);
					appPage.setAdapter(mMainSportAdapter2);
					String NumColumns = PriceUtils.getInstance().gteDividePrice(APP_PAGE_SIZE + "", 2 + "");
					int NumColumns_ = (int) Math.ceil(Double.parseDouble(NumColumns));
					appPage.setNumColumns(NumColumns_);
					appPage.setPadding(0, getResources().getDimensionPixelSize(R.dimen.jianju), 0,
							getResources().getDimensionPixelSize(R.dimen.jianju));
					appPage.setVerticalSpacing(5);
					mScrollLayout.addView(appPage);

					mHandler.sendEmptyMessage(UPDATECITYAREA);
				}
				// 加载分页
				pageControl = (PageControlView) findViewById(R.id.pageControl);
				pageControl.bindScrollViewGroup(mScrollLayout);
				// 加载分页数据
				dataLoad.bindScrollViewGroup(mScrollLayout);
				main_personal_tese_text.setVisibility(View.VISIBLE);
				main_personal_yuding_text.setVisibility(View.VISIBLE);
				pageControl.setVisibility(View.VISIBLE);
			}
		}

	}

	/**
	 * 加载cityMap
	 */
	private void loadCityData() {

		new SearchCityFileBusiness(context, new GetCityFileCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> mCityMap) {
				if (mCityMap == null) {
					mCityMap = CommonUtils.getInstance().getCityMap(context);
				}
				mAllMap = (HashMap<String, String>) mCityMap.get("mAllMap");
				mHandler.sendEmptyMessage(STARTLOCATION);
			}
		});
	}

	private void loadCityAreaData() {
		mHashMap = new HashMap<String, String>();
		mHashMap.put("city_id", cityid_select);
		new SearchCityAreaBusiness(context, mHashMap, new GetCityAreaCallback() {
			@Override
			public void afterDataGet(String jsonAreaStr) {
				if (mBestDoSportAdapterList != null && mBestDoSportAdapterList.size() > 0) {
					for (BestDoSportAdapter mBestDoSportAdapter : mBestDoSportAdapterList) {
						mBestDoSportAdapter.setInitData(cityid_select, longitude_select, latitude_select,
								myselectcurrentcitystatus, address, city_select, jsonAreaStr);
					}
				}
			}
		});

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

	int page, pagesize;
	int total;

	private void init() {
		mList = new ArrayList<StadiumInfo>();
		page = 0;
		page++;
		pagesize = 5;

	}

	/**
	 * 加载推荐listView
	 */
	StadiumRecommendBusiness mStadiumRecommendBusiness;

	private void loadList() {
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mHashMap = new HashMap<String, String>();
		mHashMap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		mHashMap.put("city", cityid_select);
		mHashMap.put("longitude", "" + longitude_select);
		mHashMap.put("latitude", "" + latitude_select);
		mHashMap.put("radius", "50000");// 距离
		mHashMap.put("page", "" + page);
		mHashMap.put("pagesize", "" + pagesize);// 距离
		System.err.println(mHashMap);
		mStadiumRecommendBusiness = new StadiumRecommendBusiness(context, mHashMap, mList, new GetStadiumCallback() {

			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String data = (String) dataMap.get("msg");
						// CommonUtils.getInstance().initToast(context,
						// data);
					} else if (status.equals("200")) {
						total = (Integer) dataMap.get("total");
						mList = (ArrayList<StadiumInfo>) dataMap.get("mList");
						if (page * pagesize < total) {
							page++;
						}
						if (mList != null && mList.size() != 0) {
						} else {
							mList = new ArrayList<StadiumInfo>();
						}
						main_xrefresh.stopLoadMore();
						mStadiumAdapter = new StadiumAdapter(MainPersonActivity.this, mList, "");
						main_myListView.setAdapter(mStadiumAdapter);
					}
				} else {
					if (cityid_selectBefore.equals(cityid_select)) {
						String responsecache = CommonUtils.getInstance().getCacheFile(context,
								"StadiumRecommendJsonCaChe");
						if (!TextUtils.isEmpty(responsecache)) {
							mStadiumRecommendBusiness.loadCacheData(new ArrayList<StadiumInfo>(), responsecache);
						}
					} else {
						CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
					}
				}
				addNotDateImg();
				cityid_selectBefore = cityid_select;
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
			}
		});
	}

	/**
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (mStadiumAdapter == null || (mStadiumAdapter != null && mStadiumAdapter.getCount() == 0)) {
			main_layout_tuijian.setVisibility(View.INVISIBLE);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.main_ic_none_venues);
			not_date_cont.setText("所选城市下无相关推荐场地");
		} else {
			main_layout_tuijian.setVisibility(View.VISIBLE);
			not_date.setVisibility(View.GONE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (mList != null && mList.size() > 0) {
			Intent intent = new Intent(context, StadiumDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("mer_item_id", mList.get(arg2).getMer_item_id());
			bundle.putString("vip_price_id", mList.get(arg2).getVip_price_id());

			intent.putExtras(bundle);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, context);
		}
	}

	/**
	 * 设置定位
	 */
	private void loadMapLocation() {
		mLocationClient = new LocationClient(this); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		new Thread(new Runnable() {

			@Override
			public void run() {
				InitLocation();// 设置定位参数
			}
		}).start();

	}

	// // 分页数据
	class DataLoading {
		private int count;

		public void bindScrollViewGroup(ScrollLayout scrollViewGroup) {
			this.count = scrollViewGroup.getChildCount();
			scrollViewGroup.setOnScreenChangeListenerDataLoad(new OnScreenChangeListenerDataLoad() {
				public void onScreenChange(int currentIndex) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

	private void InitLocation() {
		option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		// LocationMode mLocationMode=LocationMode.Device_Sensors;
		// option.setLocationMode(mLocationMode);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			try {

				if (TextUtils.isEmpty(cityid_current)) {
					longitude_current = location.getLongitude();// 列表模式时，初始化当前定位
					latitude_current = location.getLatitude();
					city_current = location.getCity();
					address = location.getAddrStr();
					if (!TextUtils.isEmpty(city_current)) {
						city_current = city_current.substring(0, city_current.length() - 1);
					}
					System.err.println(
							"+++++++++++++++" + longitude_current + "  " + latitude_current + "  " + city_current);
					if (!TextUtils.isEmpty(city_current) && mAllMap != null && mAllMap.containsKey(city_current)) {
						cityid_current = (String) mAllMap.get(city_current);
						myLocationokstatus = "have";
						myselectcurrentcitystatus = getString(R.string.search_city_area);
					} else {
						// city_current = "北京";
						// cityid_current = "52";
						// cityid_select = cityid_current;
						// city_select = city_current;
						longitude_current = 116.404269;
						latitude_current = 39.91405;
						myLocationokstatus = "";
						myselectcurrentcitystatus = "";
					}
					cityid_selectBefore = cityid_select;

					if (!TextUtils.isEmpty(myLocationokstatus)) {
						if (!cityid_current.equals(cityid_select)) {
							/**
							 * 定位成功非当前城市
							 */
							showDialogPromptReLocation();
						} else if (longitude_select != longitude_current) {
							/**
							 * 定位成功 当前城市,非当前定位
							 */
							changeLocation();
						}
					}

				}
				// 获取error code：
				// 返回值：
				// 61 ： GPS定位结果，GPS定位成功。
				// 62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
				// 63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
				// 65 ： 定位缓存的结果。
				// 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
				// 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
				// 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
				// 161： 网络定位结果，网络定位定位成功。
				// 162： 请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件。
				// 167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
				// 502： key参数错误，请按照说明文档重新申请KEY。
				// 505： key不存在或者非法，请按照说明文档重新申请KEY。
				// 601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
				// 602： key
				// mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
				// 501～700：key验证失败，请按照说明文档重新申请KEY。
				errorCode = location.getLocType();
				Log.e("errorCode", errorCode + "");
				bestDoInfoSharedPrefs.edit().putString("city_current", city_current);
				bestDoInfoSharedPrefs.edit().commit();
			} catch (Exception e) {
			}
		}

		public void onReceivePoi(BDLocation arg0) {

		}

	}

	public void showDialogPromptReLocation() {
		final MyDialog selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_logout);
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		selectDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				return true;
			}
		});
		TextView myexit_text = (TextView) selectDialog.findViewById(R.id.myexit_text);
		TextView myexit_text_cirt = (TextView) selectDialog.findViewById(R.id.myexit_text_cirt);
		TextView myexit_text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);
		TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);
		myexit_text.setText("您所在城市是" + city_current + "，建议您切换到");
		myexit_text_cirt.setText(city_current);
		myexit_text_cirt.setTextColor(getResources().getColor(R.color.text_contents_color));
		myexit_text_off.setText("取消");
		myexit_text_off.setTextColor(getResources().getColor(R.color.blue));
		text_sure.setText("确定");
		myexit_text_off.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				changeLocation();
			}
		});
	}

	private void changeLocation() {
		cityid_select = cityid_current;
		city_select = city_current;
		longitude_select = longitude_current;
		latitude_select = latitude_current;
		initSelectLocation();
		mHandler.sendEmptyMessage(CITYCALLBACK);
	}

	@Override
	protected void onDestroy() {
		try {
			// 退出时销毁定位
			if (mLocationClient != null)
				mLocationClient.stop();
		} catch (Exception e) {
		}
		if (mAllMap != null) {
			mAllMap.clear();
			mAllMap = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == Constans.getInstance().searchForResultByCityPage) {
				city_select = data.getStringExtra("city_select");
				cityid_select = data.getStringExtra("cityid_select");
				longitude_select = data.getDoubleExtra("longitude_select", 0);
				latitude_select = data.getDoubleExtra("latitude_select", 0);
				myselectcurrentcitystatus = data.getStringExtra("myselectcurrentcitystatus");
				initSelectLocation();
				Message msg = new Message();
				msg.what = CITYCALLBACK;
				mHandler.sendMessage(msg);
				msg = null;
			}
		} catch (Exception e) {
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void doback() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
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
