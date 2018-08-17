package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bestdo.bestdoStadiums.business.StadiumBusiness;
import com.bestdo.bestdoStadiums.business.StadiumBusiness.GetStadiumCallback;
import com.bestdo.bestdoStadiums.control.activity.MainPersonActivity.MyLocationListener;
import com.bestdo.bestdoStadiums.control.adapter.StadiumAdapter;
import com.bestdo.bestdoStadiums.control.adapter.StadiumSelectJuliAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.StadiumSelectJuliInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.parser.SearchCityAreaParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class StadiumListActivity extends BaseActivity implements OnRefreshListioner {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_you, pagetop_layout_back, stadiumlist_top_line;
	private ListView stadium_list_date;
	private StadiumAdapter adapter;
	private ArrayList<StadiumInfo> mList;
	private ImageView pagetop_iv_you;
	private TextView stadium_list_area, stadium_list_sort, stadium_list_distance;
	private LinearLayout not_date;
	private FrameLayout stadiummaplist_list;
	private PullDownListView mPullDownView;
	private TextView stadiumlist_tv_location;
	private LinearLayout stadiumlist_layout_refresh;
	private ImageView stadium_list_area_right_img, stadium_list_sort_right_img, stadium_list_distance_right_img;

	private ArrayList<StadiumSelectJuliInfo> distanceList, sortList, mCityAreaList;
	private Handler mHandler = new Handler();
	private ProgressDialog mDialog;

	private String distance = "10000";// 距离
	/**
	 * 价格或距离排序 排序 升序asc,降序desc
	 */
	private String sort = "asc";
	private String pricesort = "";
	private int page, total;// 页数
	private int pagesize;// 页大小
	private String merid, sportname;
	private String defult_price_id;
	private String banlance_price_id;
	//
	private String myselectcurrentcitystatus;
	private String address;
	private String city_select;
	private String cityid_select;
	private double longitude_select;
	private double latitude_select;
	Animation operatingAnim;
	private ImageView stadiumlist_iv_refresh;
	private SharedPreferences bestDoInfoSharedPrefs;

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		// 区域
		case R.id.stadium_list_area:
			changeAreaStatus();
			stadiummaplist_list.getBackground().setAlpha(50);
			break;
		// 排序
		case R.id.stadium_list_sort:
			changeSortStatus();
			stadiummaplist_list.getBackground().setAlpha(50);
			break;
		// 选择距离
		case R.id.stadium_list_distance:
			changeDistanceStatus();
			stadiummaplist_list.getBackground().setAlpha(50);
			break;
		case R.id.pagetop_layout_you:
			Intent intent = new Intent(StadiumListActivity.this, StadiumMapActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Bundle bundle = new Bundle();
			StadiumInfo mStadiumInfo = new StadiumInfo();
			mStadiumInfo.setmMapList(mList);
			bundle.putSerializable("mStadiumInfo", mStadiumInfo);
			bundle.putString("sportname", sportname);
			bundle.putString("vip_price_id", banlance_price_id);
			intent.putExtras(bundle);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, StadiumListActivity.this);
			break;
		case R.id.stadiumlist_layout_refresh:
			// 开始动画
			if (operatingAnim != null) {
				stadiumlist_iv_refresh.startAnimation(operatingAnim);
			}
			reloadMapData();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.stadiumlist);
		CommonUtils.getInstance().addActivity(this);

	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		stadium_list_date = (ListView) findViewById(R.id.stadium_list_date);
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);

		stadium_list_area = (TextView) findViewById(R.id.stadium_list_area);
		stadium_list_area_right_img = (ImageView) findViewById(R.id.stadium_list_area_right_img);
		stadium_list_sort = (TextView) findViewById(R.id.stadium_list_sort);
		stadium_list_sort_right_img = (ImageView) findViewById(R.id.stadium_list_sort_right_img);
		stadium_list_distance = (TextView) findViewById(R.id.stadium_list_distance);
		stadium_list_distance_right_img = (ImageView) findViewById(R.id.stadium_list_distance_right_img);

		stadiumlist_top_line = (LinearLayout) findViewById(R.id.stadiumlist_top_line);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		stadiummaplist_list = (FrameLayout) findViewById(R.id.stadiummaplist_list);

		stadiumlist_tv_location = (TextView) findViewById(R.id.stadiumlist_tv_location);
		stadiumlist_layout_refresh = (LinearLayout) findViewById(R.id.stadiumlist_layout_refresh);
		stadiumlist_iv_refresh = (ImageView) findViewById(R.id.stadiumlist_iv_refresh);
	}

	@Override
	protected void setListener() {
		init();
		intitData();
		loadMapLocation();
		pagetop_iv_you.setVisibility(View.VISIBLE);
		pagetop_iv_you.setBackgroundResource(R.drawable.stadium_img_map);
		stadiummaplist_list.getBackground().setAlpha(0);
		pagetop_tv_name.setText(sportname);
		mPullDownView.setMoreBottom();
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setOrderBottomMoreLine("list");
		pagetop_layout_you.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		stadium_list_distance.setOnClickListener(this);
		stadium_list_sort.setOnClickListener(this);
		stadium_list_area.setOnClickListener(this);
		stadiumlist_layout_refresh.setOnClickListener(this);
		stadium_list_date.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (mList != null & mList.size() > 0 && arg2 <= mList.size()) {
					Intent intent = new Intent(StadiumListActivity.this, StadiumDetailActivity.class);
					Bundle bundle = new Bundle();

					bundle.putString("mer_item_id", mList.get(arg2 - 1).getMer_item_id());

					bundle.putString("vip_price_id", banlance_price_id);

					intent.putExtras(bundle);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, StadiumListActivity.this);
				}
			}
		});
	}

	private void intitData() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		Intent in = getIntent();
		sportname = in.getStringExtra("sportname");
		merid = in.getStringExtra("merid");
		defult_price_id = in.getStringExtra("defult_price_id");
		banlance_price_id = in.getStringExtra("banlance_price_id");
		cityid_select = in.getExtras().getString("cityid_select", "");
		longitude_select = in.getDoubleExtra("longitude_select", 0);
		latitude_select = in.getDoubleExtra("latitude_select", 0);
		myselectcurrentcitystatus = in.getStringExtra("myselectcurrentcitystatus");
		address = in.getStringExtra("address");
		city_select = in.getExtras().getString("city_select", "");
		showAddress();
		String jsonAreaStr = in.getStringExtra("jsonAreaStr");
		if (!TextUtils.isEmpty(jsonAreaStr)) {
			JSONObject jsonObject = RequestUtils.String2JSON(jsonAreaStr);
			SearchCityAreaParser mSearchCityParser = new SearchCityAreaParser();
			HashMap<String, Object> mCityMap = mSearchCityParser.parseJSON(jsonObject);
			if (mCityMap != null) {
				mCityAreaList = (ArrayList<StadiumSelectJuliInfo>) mCityMap.get("mCityAreaList");
			}
		}
		if (mCityAreaList == null) {
			mCityAreaList = new ArrayList<StadiumSelectJuliInfo>();
			mCityAreaList.add(new StadiumSelectJuliInfo("全部", "0", "", true));
		}
		distanceList = new ArrayList<StadiumSelectJuliInfo>();
		distanceList.add(new StadiumSelectJuliInfo("不限距离", true, "10000"));
		distanceList.add(new StadiumSelectJuliInfo("5km以内", false, "5"));
		distanceList.add(new StadiumSelectJuliInfo("10km以内", false, "10"));
		distanceList.add(new StadiumSelectJuliInfo("20km以内", false, "20"));
		distanceList.add(new StadiumSelectJuliInfo("40km以内", false, "40"));
		distanceList.add(new StadiumSelectJuliInfo("60km以内", false, "60"));
		distanceList.add(new StadiumSelectJuliInfo("80km以内", false, "80"));

		sortList = new ArrayList<StadiumSelectJuliInfo>();
		sortList.add(new StadiumSelectJuliInfo("从近到远", true, "asc", ""));
		sortList.add(new StadiumSelectJuliInfo("价格最高", false, "", "desc"));
		sortList.add(new StadiumSelectJuliInfo("价格最低", false, "", "asc"));

		initAnimation();
	}

	private void initAnimation() {
		operatingAnim = AnimationUtils.loadAnimation(this, R.anim.stadiumlist_romatetip);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
	}

	/**
	 * 对于上面的转动在横屏(被设置为了不重绘activity)时会出现问题，即旋转中心偏移，导致动画旋转偏离原旋转中心。解决如下
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

		if (operatingAnim != null && stadiumlist_iv_refresh != null && operatingAnim.hasStarted()) {
			stadiumlist_iv_refresh.clearAnimation();
			stadiumlist_iv_refresh.startAnimation(operatingAnim);
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

	private void init() {
		adapter = null;
		mList = new ArrayList<StadiumInfo>();
		page = 1;
		pagesize = 10;
	}

	HashMap<String, String> mhashmap;
	private String region_id = "0";

	@Override
	protected void processLogic() {
		if (page < 1) {
			setloadPageMoreStatus();
			return;
		}
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		mhashmap.put("mer_id", merid);
		mhashmap.put("price_id_non_default", banlance_price_id);
		mhashmap.put("price_id_default", defult_price_id);
		mhashmap.put("city", cityid_select);
		mhashmap.put("radius", distance + "");
		mhashmap.put("longitude", "" + longitude_select);
		mhashmap.put("latitude", "" + latitude_select);
		mhashmap.put("sort", sort);
		mhashmap.put("price_sort", pricesort);

		mhashmap.put("region_id", "" + region_id);
		mhashmap.put("page", "" + page);
		mhashmap.put("pagesize", "" + pagesize);
		Log.e("mhashmap", mhashmap.toString());
		new StadiumBusiness(this, mhashmap, mList, new GetStadiumCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						if (page == 0) {
							updateList();
						}
					} else if (status.equals("407")) {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					} else if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						total = (Integer) dataMap.get("total");
						mList = (ArrayList<StadiumInfo>) dataMap.get("mList");
						if (page * pagesize < total) {
							page++;
						}
						updateList();
					}
				} else {
					CommonUtils.getInstance().initToast(StadiumListActivity.this, getString(R.string.net_tishi));
				}
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<StadiumInfo>();
		}

		if (adapter == null) {
			adapter = new StadiumAdapter(this, mList, "起");
			stadium_list_date.setAdapter(adapter);
		} else {
			adapter.setList(mList);
			adapter.notifyDataSetChanged();
		}
		addNotDateImg();
	}

	/**
	 * 滑动加载完成后，重置加载的状态
	 */
	private void setloadPageMoreStatus() {
		mPullDownViewHandler.sendEmptyMessage(DATAUPDATEOVER);
	}

	/**
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (adapter == null || (adapter != null && adapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.main_ic_none_venues);
			not_date_cont.setText("暂无相关场地信息！");
		} else {
			not_date.setVisibility(View.GONE);
		}
	}

	/**
	 * 当返回数据小于总数时，不让显示“加载更多”
	 */
	private void nideBottom() {
		if (!checkDataLoadStatus()) {
			mPullDownView.onLoadMoreComplete(getResources().getString(R.string.seen_more), "");// 这里表示加载更多处理完成后把下面的加载更多界面（隐藏或者设置字样更多）
			mPullDownView.setMore(true);// 这里设置true表示还有更多加载，设置为false底部将不显示更多
		} else {
			mPullDownView.onLoadMoreComplete("已无更多场地信息了！", "over");
			mPullDownView.setMore(true);
		}
	}

	/**
	 * 判断数据是否加载完
	 * 
	 * @return true为加载完
	 */
	private boolean checkDataLoadStatus() {
		boolean loadStatus = true;
		if (adapter != null && total > adapter.getCount() && (page * pagesize < total)) {
			loadStatus = false;
		}
		return loadStatus;
	}

	/**
	 * 数据加载完后，判断底部“加载更多”显示状态
	 */
	private final int DATAUPDATEOVER = 0;

	/**
	 * 下拉刷新
	 */
	private final int REFLESH = 1;
	/**
	 * 加载更多
	 */
	private final int LOADMORE = 2;
	Handler mPullDownViewHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DATAUPDATEOVER:
				mPullDownView.onRefreshComplete();
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				nideBottom();
				// 结束动画
				stadiumlist_iv_refresh.clearAnimation();
				break;
			case REFLESH:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					init();
					processLogic();
				}
				break;
			case LOADMORE:
				System.err.println("LOADMORELOADMORELOADMORELOADMORELOADMORE");
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					// if (!checkDataLoadStatus()) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					page++;
					System.err.println("processLogicprocessLogic");
					processLogic();
					// }
				}
				break;

			}
		}
	};

	/**
	 * 方法说明：得到选择附近的PopWindow
	 * 
	 */
	private PopupWindow mPopupWindow;

	String beforeDistanceOrSort = "distance";
	String clickDistanceOrSort = "distance";

	/**
	 * 方法说明：得到选择距离的PopWindow
	 * 
	 */
	private void changeDistanceStatus() {
		initDistancePopuptWindow();
		clickDistanceOrSort = "distance";
		clickChangeSelect(clickDistanceOrSort);
		stadium_list_distance_right_img
				.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_up_blue));
	}

	private void changeSortStatus() {
		initSortPopuptWindow();
		clickDistanceOrSort = "sort";
		clickChangeSelect(clickDistanceOrSort);
		stadium_list_sort_right_img
				.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_up_blue));
	}

	private void changeAreaStatus() {
		initAreaPopuptWindow();
		clickDistanceOrSort = "area";
		clickChangeSelect(clickDistanceOrSort);
		stadium_list_area_right_img
				.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_up_blue));
	}

	private void clickChangeSelect(String DistanceOrFujin) {
		stadium_list_area_right_img
				.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down_gray));
		stadium_list_sort_right_img
				.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down_gray));
		stadium_list_distance_right_img
				.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down_gray));
		stadium_list_area.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
		stadium_list_sort.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
		stadium_list_distance.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
		if (DistanceOrFujin.equals("distance")) {
			stadium_list_distance_right_img
					.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down_blue));
			stadium_list_distance.setTextColor(context.getResources().getColor(R.color.blue));
		} else if (DistanceOrFujin.equals("sort")) {
			stadium_list_sort_right_img
					.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down_blue));
			stadium_list_sort.setTextColor(context.getResources().getColor(R.color.blue));
		} else {
			stadium_list_area_right_img
					.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down_blue));
			stadium_list_area.setTextColor(context.getResources().getColor(R.color.blue));
		}
	}

	private void initAreaPopuptWindow() {

		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.stadiumlist_pup, null);
		ListView select_juli_listview = (ListView) popupWindow.findViewById(R.id.stadiumlist_pup_listview);
		StadiumSelectJuliAdapter stadiumSelectJuliAdapter = new StadiumSelectJuliAdapter(context, mCityAreaList);
		select_juli_listview.setAdapter(stadiumSelectJuliAdapter);

		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT,
				ConfigUtils.getInstance().dip2px(context, 350));
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAsDropDown(stadiumlist_top_line);

		// stadium_list_sort_right_img.setBackgroundDrawable(context
		// .getResources()
		// .getDrawable(R.drawable.pagetop_iv_cityimg_close));
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@SuppressLint("NewApi")
			@Override
			public void onDismiss() {
				stadiummaplist_list.getBackground().setAlpha(0);
				clickChangeSelect(beforeDistanceOrSort);
			}
		});
		select_juli_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				beforeDistanceOrSort = clickDistanceOrSort;
				for (int i = 0; i < mCityAreaList.size(); i++) {
					mCityAreaList.get(i).setCheck(false);
					if (i == position) {
						mCityAreaList.get(position).setCheck(true);
						if (mPopupWindow != null && mPopupWindow.isShowing()) {
							mPopupWindow.dismiss();
						}
						region_id = mCityAreaList.get(position).getRegion_id();
					}

				}
				stadium_list_area.setText(mCityAreaList.get(position).getName());
				if (position == 0) {
					stadium_list_area.setText("区域");
				}
				init();
				processLogic();
			}
		});

	}

	private void initSortPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.stadiumlist_pup, null);
		ListView select_juli_listview = (ListView) popupWindow.findViewById(R.id.stadiumlist_pup_listview);
		StadiumSelectJuliAdapter stadiumSelectJuliAdapter = new StadiumSelectJuliAdapter(context, sortList);
		select_juli_listview.setAdapter(stadiumSelectJuliAdapter);

		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAsDropDown(stadiumlist_top_line);

		// stadium_list_sort_right_img.setBackgroundDrawable(context
		// .getResources()
		// .getDrawable(R.drawable.pagetop_iv_cityimg_close));
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@SuppressLint("NewApi")
			@Override
			public void onDismiss() {
				stadiummaplist_list.getBackground().setAlpha(0);
				clickChangeSelect(beforeDistanceOrSort);
			}
		});
		select_juli_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				beforeDistanceOrSort = clickDistanceOrSort;
				for (int i = 0; i < sortList.size(); i++) {
					sortList.get(i).setCheck(false);
					if (i == position) {
						sortList.get(position).setCheck(true);
						if (mPopupWindow != null && mPopupWindow.isShowing()) {
							mPopupWindow.dismiss();
						}
						sort = sortList.get(position).getSort();
						pricesort = sortList.get(position).getPricesort();
					}

				}
				stadium_list_sort.setText(sortList.get(position).getName());
				if (position == 0) {
					stadium_list_sort.setText("排序");
				}
				init();
				processLogic();
			}
		});
	}

	/**
	 * 方法说明：创建PopWindow 距离
	 */
	private void initDistancePopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.stadiumlist_pup, null);
		ListView select_juli_listview = (ListView) popupWindow.findViewById(R.id.stadiumlist_pup_listview);
		StadiumSelectJuliAdapter stadiumSelectJuliAdapter = new StadiumSelectJuliAdapter(context, distanceList);
		select_juli_listview.setAdapter(stadiumSelectJuliAdapter);

		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAsDropDown(stadiumlist_top_line);

		// stadium_list_distance_right_img.setBackgroundDrawable(context
		// .getResources()
		// .getDrawable(R.drawable.pagetop_iv_cityimg_close));
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@SuppressLint("NewApi")
			@Override
			public void onDismiss() {
				stadiummaplist_list.getBackground().setAlpha(0);
				clickChangeSelect(beforeDistanceOrSort);
			}
		});
		select_juli_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				beforeDistanceOrSort = clickDistanceOrSort;
				for (int i = 0; i < distanceList.size(); i++) {
					distanceList.get(i).setCheck(false);
					if (i == position) {
						distanceList.get(position).setCheck(true);
						if (mPopupWindow != null && mPopupWindow.isShowing()) {
							mPopupWindow.dismiss();
						}
					}
				}
				distance = distanceList.get(position).getRadius();
				stadium_list_distance.setText(distanceList.get(position).getName());
				if (position == 0) {
					stadium_list_distance.setText("距离");
				}
				init();
				processLogic();
			}
		});
	}

	private void nowFinish() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(REFLESH);
			}
		}, 1500);
	}

	@Override
	public void onLoadMore() {
		System.err.println("onLoadMoreonLoadMoreonLoadMoreonLoadMore");
		mHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(LOADMORE);
			}
		}, 1500);
	}

	/**
	 * =================定位======================
	 */
	/**
	 * 城市选择回调更新数据
	 */
	private final int CITYCALLBACK = 1;
	Handler mlocationHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CITYCALLBACK:
				showAddress();
				init();
				processLogic();
				break;
			}
		}
	};

	private void showAddress() {
		if (!TextUtils.isEmpty(myselectcurrentcitystatus)) {
			// 当前
			stadiumlist_tv_location.setText(address);
		} else {
			stadiumlist_tv_location.setText(city_select + "市中心");
		}
	}

	private LocationClientOption option;
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	private HashMap<String, String> mAllMap;
	boolean isFirstLoc = false;// 是否首次定位

	/**
	 * 加载运动类型Map
	 */
	private void loadCidData() {
		HashMap<String, Object> mMap = CommonUtils.getInstance().getCityMap(context);
		if (mMap != null) {
			mAllMap = (HashMap<String, String>) mMap.get("mAllMap");
		} else {
			mAllMap = new HashMap<String, String>();
		}
	}

	/**
	 * 设置定位
	 */
	private void loadMapLocation() {
		loadCidData();
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		new Thread(new Runnable() {

			@Override
			public void run() {
				InitLocation();// 设置定位参数
			}
		}).start();

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

	/**
	 * 重新加载
	 */
	private void reloadMapData() {
		isFirstLoc = true;
		option.setOpenGps(true);
		mLocationClient.requestLocation();
		mLocationClient.start();
	}

	public class MyLocationListener implements BDLocationListener {

		private String cityid_current;
		private double longitude_current;
		private double latitude_current;
		private String city_current;

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			try {
				if (isFirstLoc) {
					longitude_current = location.getLongitude();// 列表模式时，初始化当前定位
					latitude_current = location.getLatitude();
					longitude_select = longitude_current;
					latitude_select = latitude_current;
					city_current = location.getCity();
					address = location.getAddrStr();
					if (!TextUtils.isEmpty(city_current)) {
						city_current = city_current.substring(0, city_current.length() - 1);
					}
					System.err.println(
							"+++++++++++++++" + longitude_current + "  " + latitude_current + "  " + city_current);
					if (!TextUtils.isEmpty(city_current) && mAllMap != null && mAllMap.containsKey(city_current)) {
						cityid_current = (String) mAllMap.get(city_current);
						cityid_select = cityid_current;
						city_select = city_current;
						myselectcurrentcitystatus = getString(R.string.search_city_area);
					} else {
						city_current = "北京";
						cityid_current = "52";
						cityid_select = cityid_current;
						city_select = city_current;
						longitude_current = 116.404269;
						latitude_current = 39.91405;
						myselectcurrentcitystatus = "";
					}

					mlocationHandler.sendEmptyMessage(CITYCALLBACK);
				}
				option.setOpenGps(false);
				isFirstLoc = false;
			} catch (Exception e) {
			}
		}

		public void onReceivePoi(BDLocation arg0) {

		}

	}

	@Override
	protected void onDestroy() {
		try {
			// 退出时销毁定位
			if (mLocationClient != null)
				mLocationClient.stop();
		} catch (Exception e) {
		}
		option = null;
		if (adapter != null) {
			adapter.asyncImageLoader.clearCache();
		}
		super.onDestroy();
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
