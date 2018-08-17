package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.business.StadiumKeySearchBusiness;
import com.bestdo.bestdoStadiums.business.StadiumKeySearchBusiness.StadiumKeySearchBusinessCallback;
import com.bestdo.bestdoStadiums.control.adapter.SearchKeyWordAdapter;
import com.bestdo.bestdoStadiums.control.adapter.StadiumAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.db.DBHelper;
import com.bestdo.bestdoStadiums.model.db.DBOpenHelper;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.SearchKWClearEditText;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-2 下午4:42:04
 * @Description 类描述：关键字搜索
 */
public class SearchKeyWordActivity extends BaseActivity implements OnRefreshListioner {
	private LinearLayout pagetop_layout_back;
	private SearchKWClearEditText stadium_search_filteredit;
	private TextView pagetop_tv_search;
	private LinearLayout not_date, serach_histoy_linear, page_top_layout;
	private PullDownListView mPullDownView;
	private ListView lv_date;
	private TextView not_date_text;
	private ImageView not_date_img;
	private StadiumAdapter mStadiumAdapter;
	private View historyBottomView;
	private TextView search_history_clear;
	private Handler mListHandler = new Handler();
	private ArrayList<StadiumInfo> mList;
	private String q = "";// 关键字搜索 ---""默认为空
	private int page;// 页码
	private int pagesize;// 页数
	private int total;
	private ProgressDialog mDialog;

	private DBOpenHelper mDB;
	private ArrayList<StadiumInfo> mKeyWordHistoryList;
	private SearchKeyWordAdapter mSearchKeyWordAdapter;
	private ListView serach_histoy_listview;
	private boolean addOrNotHistoryBottomStatus = false;
	HashMap<String, String> mhashmap;
	private String longitude;
	private String latitude;
	private SharedPreferences bestDoInfoSharedPrefs;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_tv_search:
			q = stadium_search_filteredit.getText().toString().trim();
			if (!TextUtils.isEmpty(q)) {
				// ------阻止EditText得到焦点，以防输入法弄丑画面
				pagetop_tv_search.setFocusable(true);
				pagetop_tv_search.setFocusableInTouchMode(true);
				pagetop_tv_search.requestFocus();
				// 初始不让EditText得焦点搜索
				pagetop_tv_search.requestFocusFromTouch();
				// 让焦点指到任一个textView1中
				/*
				 * 2.db--add一条
				 */
				mDB.addTableKeyWordInfo(q);
				/*
				 * 关闭键盘
				 */
				CommonUtils.getInstance().closeSoftInput(SearchKeyWordActivity.this);
				init();
				initListView();
				processLogicStadium();
			}
			break;
		case R.id.search_history_clear:
			/*
			 * 4.db 清除所有历史
			 */
			mDB.deleteTableAllInfo(DBHelper.TABLE_CKEYWORD);
			stadium_search_filteredit.setText("");
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.search_keyword);
		CommonUtils.getInstance().addActivity(this);
		if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
			// 隐藏软键盘
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			getWindow().getAttributes().softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
		}
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		stadium_search_filteredit = (SearchKWClearEditText) findViewById(R.id.stadium_search_filteredit);
		pagetop_tv_search = (TextView) findViewById(R.id.pagetop_tv_search);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
		lv_date = (ListView) findViewById(R.id.lv_date);
		not_date_text = (TextView) findViewById(R.id.not_date_cont);
		not_date_img = (ImageView) findViewById(R.id.not_date_img);
		serach_histoy_listview = (ListView) findViewById(R.id.serach_histoy_listview);
		serach_histoy_linear = (LinearLayout) findViewById(R.id.serach_histoy_linear);
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		/*
		 * bottom
		 */
		historyBottomView = getLayoutInflater().inflate(R.layout.search_history_bottom, null);
		search_history_clear = (TextView) historyBottomView.findViewById(R.id.search_history_clear);
	}

	@Override
	protected void setListener() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_search.setOnClickListener(this);
		search_history_clear.setOnClickListener(this);
		lv_date.setOnItemClickListener(itemClickListener);
		stadium_search_filteredit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				serach_histoy_linear.setVisibility(View.VISIBLE);
				mPullDownView.setVisibility(View.GONE);
			}
		});
		stadium_search_filteredit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String keyword = s.toString().trim();
				if (!TextUtils.isEmpty(keyword)) {
					pagetop_tv_search.setTextColor(getResources().getColor(R.color.white));
					if (!mClickHistory) {
						mMatchingStatus = true;
						processLogicQChange(keyword);
					} else {
						mClickHistory = false;
					}
				} else {
					pagetop_tv_search.setTextColor(getResources().getColor(R.color.btn_noclick_color));
					showAllHistoryList();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}

		});
		stadium_search_filteredit.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					String keyword = stadium_search_filteredit.getText().toString();
					if (!TextUtils.isEmpty(keyword)) {
						showHistoryListByKW(keyword);
					} else {
						showAllHistoryList();
					}
				}
			}
		});

		mPullDownView.setRefreshListioner(this);
		serach_histoy_listview.setOnItemClickListener(mHistoryItemClickListener);
		initDate();
		init();
	}

	private void initDate() {
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		Intent intent = getIntent();
		longitude = intent.getStringExtra("longitude_current");
		latitude = intent.getStringExtra("latitude_current");
		/*
		 * 1.db--显示历史
		 */
		try {
			mDB = new DBOpenHelper(context);
			showAllHistoryList();
		} catch (Exception e) {
		}
	}

	private void init() {
		mStadiumAdapter = null;
		mList = new ArrayList<StadiumInfo>();
		page = 0;
		page++;
		pagesize = 10;
	}

	/**
	 * 重新查询时
	 */
	private void initListView() {
		mStadiumAdapter = new StadiumAdapter(this, mList, "");
		lv_date.setAdapter(mStadiumAdapter);
		mPullDownView.hiddenMore();
	}

	/**
	 * 添加“清除搜索历史”按钮
	 */
	private void addHistoryBottomView() {
		if (!addOrNotHistoryBottomStatus) {
			addOrNotHistoryBottomStatus = true;
			serach_histoy_listview.addFooterView(historyBottomView);
			mSearchKeyWordAdapter = new SearchKeyWordAdapter(context, mKeyWordHistoryList);
			serach_histoy_listview.setAdapter(mSearchKeyWordAdapter);
		}
	}

	/**
	 * 非匹配状态,是否有关键字
	 */
	boolean mMatchingStatus = false;
	/**
	 * 点击历史
	 */
	boolean mClickHistory = false;

	/**
	 * 判断“清除搜索历史”按钮是否显示
	 */
	private void showOrNotHistoryBottomView() {
		if (addOrNotHistoryBottomStatus) {
			if (!mMatchingStatus && mKeyWordHistoryList != null && mKeyWordHistoryList.size() != 0) {
				search_history_clear.setVisibility(View.VISIBLE);
			} else {
				search_history_clear.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * 显示所有搜索历史列表数据
	 */
	private void showAllHistoryList() {
		mKeyWordHistoryList = mDB.getKeyWordList();
		if (mKeyWordHistoryList != null && mKeyWordHistoryList.size() != 0) {
			serach_histoy_linear.setVisibility(View.VISIBLE);
			mPullDownView.setVisibility(View.GONE);
			addHistoryBottomView();
			mMatchingStatus = false;
		} else {
			mKeyWordHistoryList = new ArrayList<StadiumInfo>();
		}
		showOrNotHistoryBottomView();
		if (mSearchKeyWordAdapter != null) {
			mSearchKeyWordAdapter.setaList(mKeyWordHistoryList);
			mSearchKeyWordAdapter.notifyDataSetChanged();
		} else {
			mSearchKeyWordAdapter = new SearchKeyWordAdapter(context, mKeyWordHistoryList);
			serach_histoy_listview.setAdapter(mSearchKeyWordAdapter);
		}
		addNotDateImg("keywordlist", mSearchKeyWordAdapter);
	}

	@Override
	protected void processLogic() {
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

	protected void processLogicQChange(final String keyword) {
		serach_histoy_linear.setVisibility(View.VISIBLE);
		not_date.setVisibility(View.GONE);
		mPullDownView.setVisibility(View.GONE);
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		mhashmap.put("q", keyword);
		mhashmap.put("page", "" + 1);
		mhashmap.put("pagesize", "" + 5);
		mhashmap.put("radius", "10000");
		mhashmap.put("longitude", "" + longitude);
		mhashmap.put("latitude", "" + latitude);
		System.err.println(mhashmap);
		mList = new ArrayList<StadiumInfo>();
		new StadiumKeySearchBusiness(this, mhashmap, mList, new StadiumKeySearchBusinessCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mList = (ArrayList<StadiumInfo>) dataMap.get("mList");
						if (mList != null && mList.size() != 0) {
							mSearchKeyWordAdapter = new SearchKeyWordAdapter(context, mList);
							serach_histoy_listview.setAdapter(mSearchKeyWordAdapter);
							showOrNotHistoryBottomView();
						} else {
							showHistoryListByKW(keyword);
						}
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	protected void processLogicStadium() {

		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			setloadPageMoreStatus();
			return;
		}
		if (page < 1) {
			setloadPageMoreStatus();
			return;
		}
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		serach_histoy_linear.setVisibility(View.GONE);
		not_date.setVisibility(View.GONE);
		mPullDownView.setVisibility(View.VISIBLE);
		mhashmap = new HashMap<String, String>();
		// q 关键字 string
		// radius 距离 float
		// longitude 经度 float
		// latitude 纬度 float
		// page 页数 int
		// pagesize 页大小 int
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		mhashmap.put("q", q);
		mhashmap.put("page", "" + page);
		mhashmap.put("pagesize", "" + pagesize);
		mhashmap.put("radius", "10000");
		mhashmap.put("longitude", "" + longitude);
		mhashmap.put("latitude", "" + latitude);
		System.err.println(mhashmap);
		new StadiumKeySearchBusiness(this, mhashmap, mList, new StadiumKeySearchBusinessCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String data = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(SearchKeyWordActivity.this, data);
					} else if (status.equals("200")) {
						total = (Integer) dataMap.get("total");
						mList = (ArrayList<StadiumInfo>) dataMap.get("mList");
						if (page * pagesize < total) {
							page++;
						}
						updateList();
					}
				} else {
					CommonUtils.getInstance().initToast(SearchKeyWordActivity.this, getString(R.string.net_tishi));
				}
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	/**
	 * 更新显示搜索后的场馆列表数据
	 */
	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<StadiumInfo>();
		}
		if (mStadiumAdapter == null) {
			mStadiumAdapter = new StadiumAdapter(this, mList, "");
			lv_date.setAdapter(mStadiumAdapter);
		} else {
			mStadiumAdapter.setList(mList);
			mStadiumAdapter.notifyDataSetChanged();
		}
		addNotDateImg("", mStadiumAdapter);
	}

	/**
	 * 滑动加载完成后，重置加载的状态
	 */
	private void setloadPageMoreStatus() {
		mPullDownViewHandler.sendEmptyMessage(SETBOTTOMLOADMORESHOWSTA);
	}

	/**
	 * 根据输入的关键字匹配搜索过的历史记录
	 * 
	 * @param keyword
	 */
	private void showHistoryListByKW(String keyword) {
		/*
		 * 3.db--根据输入模糊查询搜索历史
		 */
		mKeyWordHistoryList = mDB.getKeyWordSameList(keyword);
		if (mKeyWordHistoryList != null && mKeyWordHistoryList.size() != 0) {
			Log.e("-------mDB.getKeyWordSameList(keyword)----", "" + mKeyWordHistoryList.get(0));
			serach_histoy_linear.setVisibility(View.VISIBLE);
			mPullDownView.setVisibility(View.GONE);
			addHistoryBottomView();
		}
		mSearchKeyWordAdapter.setaList(mKeyWordHistoryList);
		mSearchKeyWordAdapter.notifyDataSetChanged();
		addNotDateImg("keywordlist", mSearchKeyWordAdapter);
		showOrNotHistoryBottomView();
	}

	private OnItemClickListener mHistoryItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// 友盟埋点：点击搜索历史搜索
			CommonUtils.getInstance().umengCount(context, "1004003", null);
			mPullDownView.initBg(context, mSearchKeyWordAdapter);
			if (!mMatchingStatus && mKeyWordHistoryList != null && mKeyWordHistoryList.size() != 0
					&& mKeyWordHistoryList.size() > arg2) {
				mClickHistory = true;
				q = mKeyWordHistoryList.get(arg2).getName();
				/*
				 * 2.db--add一条
				 */
				mDB.addTableKeyWordInfo(q);
				stadium_search_filteredit.setText(q);
				// ------阻止EditText得到焦点，以防输入法弄丑画面
				pagetop_tv_search.setFocusable(true);
				pagetop_tv_search.setFocusableInTouchMode(true);
				pagetop_tv_search.requestFocus();
				// 初始不让EditText得焦点搜索
				pagetop_tv_search.requestFocusFromTouch();
				// 让焦点指到任一个textView1中

				if (!TextUtils.isEmpty(q)) {
					/*
					 * 关闭键盘
					 */
					CommonUtils.getInstance().closeSoftInput(SearchKeyWordActivity.this);
					init();
					initListView();
					processLogicStadium();
				}
			} else if (mList != null && arg2 < mList.size()) {
				String mer_item_id = mList.get(arg2).getMer_item_id();
				String vip_price_id = mList.get(arg2).getVip_price_id();
				skipDeatil(mer_item_id, vip_price_id);
			}
		}
	};
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// 防止有些机型点击出现黑屏
			// 友盟埋点：由搜索结果列表页进入场馆详情页
			CommonUtils.getInstance().umengCount(context, "1004004", null);
			mPullDownView.initBg(context, mStadiumAdapter);
			if (mList != null && arg2 <= mList.size()) {
				String mer_item_id = mList.get(arg2 - 1).getMer_item_id();
				String vip_price_id = mList.get(arg2 - 1).getVip_price_id();
				skipDeatil(mer_item_id, vip_price_id);
			}
		}
	};

	private void skipDeatil(String mer_item_id, String vip_price_id) {
		Intent intent = new Intent(SearchKeyWordActivity.this, StadiumDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("mer_item_id", mer_item_id);
		bundle.putString("vip_price_id", vip_price_id);

		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		CommonUtils.getInstance().setPageIntentAnim(intent, SearchKeyWordActivity.this);
	}

	/**
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg(String listType, BaseAdapter mAdapter) {
		if (listType.equals("keywordlist")) {
			not_date_text.setText("您的搜索历史会记录在这里哦！");
			not_date_img.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.search_nodistorydadabg));
			serach_histoy_linear.setVisibility(View.VISIBLE);
			mPullDownView.setVisibility(View.GONE);
		} else {
			serach_histoy_linear.setVisibility(View.GONE);
			mPullDownView.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_none_search));
			not_date_text.setText("抱歉，没有找到相关的场地信息！");
		}
		CommonUtils.getInstance().doLoadDateNot(mAdapter, not_date, not_date_text, "loadnot_date");
	}

	/**
	 * 当返回数据小于总数时，不让显示“加载更多”
	 */
	private void nideBottom() {
		if (!checkDataLoadStatus()) {
			mPullDownView.onLoadMoreComplete(getResources().getString(R.string.seen_more), "");
			mPullDownView.setMore(true);
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
		if (mStadiumAdapter != null && total > mStadiumAdapter.getCount() && (page * pagesize < total)) {
			loadStatus = false;
		}
		return loadStatus;
	}

	/**
	 * 设置加载更多显示状态
	 */
	private final int SETBOTTOMLOADMORESHOWSTA = 0;
	/**
	 * 下拉刷新
	 */
	private final int REFRESHLOAD = 1;
	/**
	 * 加载更多
	 */
	private final int LOADMORE = 2;
	Handler mPullDownViewHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SETBOTTOMLOADMORESHOWSTA:
				mPullDownView.onRefreshComplete();
				nideBottom();
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				break;
			case REFRESHLOAD:
				if (!TextUtils.isEmpty(q)) {
					if (!Constans.getInstance().refreshOrLoadMoreLoading) {
						Constans.getInstance().refreshOrLoadMoreLoading = true;
						init();
						processLogicStadium();
					}
				}
				break;
			case LOADMORE:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					// if (!checkDataLoadStatus()) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					page++;
					processLogicStadium();
					// }
				}
				break;

			}
		}
	};

	/**
	 * 刷新，先清空list中数据然后重新加载更新内容
	 */
	public void onRefresh() {
		mListHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(REFRESHLOAD);
			}
		}, 1500);
	}

	/**
	 * 加载更多，在原来基础上在添加新内容
	 */
	public void onLoadMore() {
		mListHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(LOADMORE);
			}
		}, 1500);
	}

	@Override
	protected void onStop() {
		if (mStadiumAdapter != null)
			mStadiumAdapter.clearCache();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		mStadiumAdapter = null;
		mSearchKeyWordAdapter = null;
		historyBottomView = null;
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		if (mKeyWordHistoryList != null) {
			mKeyWordHistoryList.clear();
			mKeyWordHistoryList = null;
		}
		RequestUtils.cancelAll(this);
		super.onDestroy();
	}

	private void nowFinish() {
		CommonUtils.getInstance().closeSoftInput(this);
		mDB.close();
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
