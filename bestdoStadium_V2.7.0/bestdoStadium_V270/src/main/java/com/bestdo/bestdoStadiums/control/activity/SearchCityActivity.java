package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.bestdo.bestdoStadiums.business.SearchCityFileBusiness;
import com.bestdo.bestdoStadiums.business.SearchCityFileBusiness.GetCityFileCallback;
import com.bestdo.bestdoStadiums.control.city.PinnedHeaderListView;
import com.bestdo.bestdoStadiums.control.city.PinyinComparator;
import com.bestdo.bestdoStadiums.control.city.SectionListAdapter;
import com.bestdo.bestdoStadiums.control.city.SideBar;
import com.bestdo.bestdoStadiums.control.city.StandardArrayAdapter;
import com.bestdo.bestdoStadiums.control.city.SideBar.OnTouchingLetterChangedListener;
import com.bestdo.bestdoStadiums.model.SearchCityInfo;
import com.bestdo.bestdoStadiums.model.db.DBOpenHelper;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 城市选择
 * 
 * @author jun
 * 
 */
public class SearchCityActivity extends BaseActivity {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private PinnedHeaderListView stadiumsearch_city_lvcountry;
	private SideBar stadiumsearch_city_sidrbar;

	private StandardArrayAdapter arrayAdapter;
	private SectionListAdapter sectionAdapter;
	private ArrayList<SearchCityInfo> SourceDateList = new ArrayList<SearchCityInfo>();
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	/**
	 * 是否选择城市
	 */
	private boolean selectcitystatus = false;

	private String cityid_select;
	private String myLocationokstatus = "";
	/**
	 * 判断是否当前定位，是，=-在列表显示当前具体地址
	 */
	private String myselectcurrentcitystatus;
	private String cityid_current;
	private String city_current;
	private double longitude_current, latitude_current;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.pagetop_layout_back:
			backCityData();
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.search_city);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		stadiumsearch_city_sidrbar = (SideBar) findViewById(R.id.stadiumsearch_city_sidrbar);
		stadiumsearch_city_lvcountry = (PinnedHeaderListView) findViewById(R.id.stadiumsearch_city_lvcountry);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		stadiumsearch_city_lvcountry.setOnItemClickListener(itemclick);
		// 设置右侧触摸监听
		stadiumsearch_city_sidrbar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = sectionAdapter.getPositionForSectionSide(s.charAt(0));
				if (position != -1) {
					stadiumsearch_city_lvcountry.setSelection(position);
				}

			}
		});
		initDate();
	}

	HashMap<String, Object> mMap;
	HashMap<String, String> mAllMap;
	List<SearchCityInfo> mhotCityList;
	List<SearchCityInfo> mordinaryCityList;
	ArrayList<String> sidrbarList;

	@SuppressLint("NewApi")
	private void initDate() {
		Intent intent = getIntent();
		// 显示为附近
		cityid_current = intent.getExtras().getString("cityid_current", "");
		city_current = intent.getExtras().getString("city_current", "");
		longitude_current = intent.getDoubleExtra("longitude_current", 0);
		latitude_current = intent.getDoubleExtra("latitude_current", 0);

		cityid_select = intent.getExtras().getString("cityid_select", "");

		myLocationokstatus = intent.getExtras().getString("myLocationokstatus", "");
		myselectcurrentcitystatus = intent.getExtras().getString("myselectcurrentcitystatus", "");
		pagetop_tv_name.setText(getString(R.string.search_city_pagetop_name));
		pinyinComparator = new PinyinComparator(this);
		getCityList();
	}

	/**
	 * 获取城市列表
	 */
	private void getCityList() {
		new SearchCityFileBusiness(context, new GetCityFileCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> mCityMap) {
				if (mCityMap == null) {
					mCityMap = CommonUtils.getInstance().getCityMap(context);
				}
				mAllMap = (HashMap<String, String>) mCityMap.get("mAllMap");
				mhotCityList = (List<SearchCityInfo>) mCityMap.get("mhotCityList");
				mordinaryCityList = (List<SearchCityInfo>) mCityMap.get("mordinaryCityList");
				setData();
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void setData() {
		/*
		 * 我的附近
		 */
		System.err.println("===========" + latitude_current + "  " + longitude_current + "  " + city_current);
		if (myLocationokstatus.equals("have")) {
			SearchCityInfo mInfo = new SearchCityInfo(cityid_current, city_current, city_current, longitude_current,
					latitude_current, getString(R.string.search_city_area), getString(R.string.search_city_area));
			SourceDateList.add(mInfo);
			mInfo = null;
		}
		/*
		 * 热门
		 */
		if (mhotCityList != null && mhotCityList.size() != 0) {
			SourceDateList.add(mhotCityList.get(0));
		}
		// 根据a-z进行排序源数据
		Collections.sort(mordinaryCityList, pinyinComparator);
		sidrbarList = new ArrayList<String>();
		if (mordinaryCityList != null && mordinaryCityList.size() != 0) {
			for (SearchCityInfo stadiumSearchCityInfo : mordinaryCityList) {
				if (!sidrbarList.contains(stadiumSearchCityInfo.getSortLetters())) {
					sidrbarList.add(stadiumSearchCityInfo.getSortLetters());
				}
				SourceDateList.add(stadiumSearchCityInfo);
			}
		}
		String[] b = new String[sidrbarList.size() + 1];
		Collections.sort(sidrbarList);
		b[0] = "*";
		for (int j = 0; j < sidrbarList.size(); j++) {
			String key = sidrbarList.get(j);// 返回与此项对应的键
			b[j + 1] = key;
		}
		stadiumsearch_city_sidrbar
				.setLayoutParams(new LinearLayout.LayoutParams(ConfigUtils.getInstance().dip2px(context, 25),
						ConfigUtils.getInstance().dip2px(context, 20 * b.length)));
		stadiumsearch_city_sidrbar.initDate(b);

		arrayAdapter = new StandardArrayAdapter(this, R.id.stadiumsearchcity_item_tv_name, SourceDateList, mhotCityList,
				cityid_select, cityid_current, myselectcurrentcitystatus, myLocationokstatus);
		sectionAdapter = new SectionListAdapter(getLayoutInflater(), arrayAdapter, mReMenHandler, REMENITEMCLICK);
		stadiumsearch_city_lvcountry.setAdapter(sectionAdapter);
		stadiumsearch_city_lvcountry.setOnScrollListener(sectionAdapter);
		stadiumsearch_city_lvcountry.setPinnedHeaderView(
				getLayoutInflater().inflate(R.layout.search_city_listsection, stadiumsearch_city_lvcountry, false));
		b = null;
	}

	String city_select;
	double longitude_select, latitude_select;
	private OnItemClickListener itemclick = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			selectcitystatus = true;
			city_select = SourceDateList.get(arg2).getCityname();
			cityid_select = SourceDateList.get(arg2).getCityid();
			longitude_select = SourceDateList.get(arg2).getLongitude();
			latitude_select = SourceDateList.get(arg2).getLatitude();
			String sort = SourceDateList.get(arg2).getSortLettersShow();
			if (sort.equals(getString(R.string.search_city_area))) {
				myselectcurrentcitystatus = getString(R.string.search_city_area);
			} else {
				myselectcurrentcitystatus = "";
			}
			backCityData();
		}
	};
	private final int REMENITEMCLICK = 1;
	Handler mReMenHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REMENITEMCLICK:
				int position = msg.arg1;
				selectcitystatus = true;
				city_select = mhotCityList.get(position).getCityname();
				cityid_select = mhotCityList.get(position).getCityid();
				longitude_select = mhotCityList.get(position).getLongitude();
				latitude_select = mhotCityList.get(position).getLatitude();
				String sort = mhotCityList.get(position).getSortLettersShow();
				if (sort.equals(getString(R.string.search_city_area))) {
					myselectcurrentcitystatus = getString(R.string.search_city_area);
				} else {
					myselectcurrentcitystatus = "";
				}
				backCityData();
				break;
			}
		}
	};

	/**
	 * 根据城市得到区域 先出本地判断读取，如没有则从数据库得到
	 */
	@Override
	protected void processLogic() {
	}

	@Override
	protected void onDestroy() {
		pinyinComparator = null;
		if (SourceDateList != null && SourceDateList.size() != 0) {
			for (SearchCityInfo iterable_element : SourceDateList) {
				iterable_element = null;
			}
			SourceDateList.clear();
			SourceDateList = null;
		}
		if (mhotCityList != null && mhotCityList.size() != 0) {
			for (SearchCityInfo iterable_element : mhotCityList) {
				iterable_element = null;
			}
			mhotCityList.clear();
			mhotCityList = null;
		}
		if (mordinaryCityList != null && mordinaryCityList.size() != 0) {
			for (SearchCityInfo iterable_element : mordinaryCityList) {
				iterable_element = null;
			}
			mordinaryCityList.clear();
			mordinaryCityList = null;
		}
		if (sidrbarList != null && sidrbarList.size() != 0) {
			sidrbarList.clear();
			sidrbarList = null;
		}
		if (mAllMap != null) {
			mAllMap.clear();
			mAllMap = null;
		}
		if (mMap != null) {
			mMap.clear();
			mMap = null;
		}
		arrayAdapter = null;
		if (sectionAdapter != null) {
			sectionAdapter.clearCache();
			sectionAdapter = null;

		}
		super.onDestroy();
	}

	private void backCityData() {
		if (selectcitystatus) {
			Intent intent = new Intent();
			intent.putExtra("cityid_select", cityid_select);
			intent.putExtra("city_select", city_select);
			intent.putExtra("latitude_select", latitude_select);
			intent.putExtra("longitude_select", longitude_select);
			intent.putExtra("myselectcurrentcitystatus", myselectcurrentcitystatus);
			setResult(Constans.getInstance().searchForResultByCityPage, intent);
		}
		System.err.println(latitude_select + "  " + longitude_select + "  " + city_select);
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			backCityData();
		}
		return false;
	}

}
