/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.StadiumRecommendBusiness;
import com.bestdo.bestdoStadiums.business.StadiumRecommendBusiness.GetStadiumCallback;
import com.bestdo.bestdoStadiums.control.adapter.StadiumAdapter;
import com.bestdo.bestdoStadiums.control.pullable.PullableScrollView;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.R;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-3-7 下午5:41:07
 * @Description 类描述：
 */
public class MainBannerActivity extends BaseActivity {
	LinearLayout page_top_layout;
	LinearLayout pagetop_layout_back;
	ImageView pagetop_iv_back;
	TextView pagetop_tv_name;
	private PullableScrollView mainbanner_scrollview;
	private ImageView mainbanner_iv_thumb;
	private MyListView banner_date;
	private LinearLayout not_date;
	private int nowScrollY;
	private ImageView not_date_img;
	private TextView not_date_cont;
	private String cityid_select;
	private double longitude_select;
	private double latitude_select;
	private LinearLayout mainbanner_layout_thumb;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String loginStatus;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.main_banner);
		CommonUtils.getInstance().addActivity(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
	}

	@Override
	protected void findViewById() {
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_iv_back = (ImageView) findViewById(R.id.pagetop_iv_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		mainbanner_scrollview = (PullableScrollView) findViewById(R.id.mainbanner_scrollview);
		mainbanner_layout_thumb = (LinearLayout) findViewById(R.id.mainbanner_layout_thumb);
		mainbanner_iv_thumb = (ImageView) findViewById(R.id.mainbanner_iv_thumb);
		banner_date = (MyListView) findViewById(R.id.banner_date);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		not_date_img = (ImageView) findViewById(R.id.not_date_img);
		not_date_cont = (TextView) findViewById(R.id.not_date_cont);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		initDate();
	}

	@SuppressLint("NewApi")
	private void initDate() {
		Intent inentIntent = getIntent();
		pagetop_tv_name.setText("百动");
		page_top_layout.setVisibility(View.VISIBLE);
		mainbanner_scrollview.setVisibility(View.VISIBLE);
		banner_date.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (mList != null && mList.size() > 0) {
					Intent intent = new Intent(MainBannerActivity.this, StadiumDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("mer_item_id", mList.get(position).getMer_item_id());
					bundle.putString("vip_price_id", mList.get(position).getVip_price_id());

					intent.putExtras(bundle);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, MainBannerActivity.this);
				}
			}
		});
		pagetop_tv_name.setText("推荐场地");
		String thumb = inentIntent.getStringExtra("thumb");
		cityid_select = inentIntent.getExtras().getString("cityid_select", "52");
		longitude_select = inentIntent.getExtras().getDouble("longitude_select", 116.404269);
		latitude_select = inentIntent.getExtras().getDouble("latitude_select", 39.91405);
		ImageLoader asyncImageLoader = new ImageLoader(context, "listdetail");
		asyncImageLoader.DisplayImage(thumb, mainbanner_iv_thumb);
		init();
		getList();
	}

	/**
	 * 加载推荐listView
	 */
	StadiumRecommendBusiness mStadiumRecommendBusiness;
	int page, pagesize;
	int total;
	private ArrayList<StadiumInfo> mList;
	private HashMap<String, String> mHashMap;
	private ProgressDialog mDialog;
	private StadiumAdapter mStadiumAdapter;

	private void init() {
		mList = new ArrayList<StadiumInfo>();
		page = 0;
		page++;
		pagesize = 20;

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

	protected void processLogic() {

	}

	protected void getList() {

		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mHashMap = new HashMap<String, String>();
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
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
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
						mStadiumAdapter = new StadiumAdapter(MainBannerActivity.this, mList, "");
						banner_date.setAdapter(mStadiumAdapter);
						addNotDateImg();
					}
				} else {
					String responsecache = CommonUtils.getInstance().getCacheFile(MainBannerActivity.this,
							"StadiumRecommendJsonCaChe");
					if (!TextUtils.isEmpty(responsecache)) {
						mStadiumRecommendBusiness.loadCacheData(new ArrayList<StadiumInfo>(), responsecache);
					}
				}
				Constans.getInstance().refreshOrLoadMoreLoading = false;
			}
		});
	}

	/**
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (mStadiumAdapter == null || (mStadiumAdapter != null && mStadiumAdapter.getCount() == 0)) {
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.main_ic_none_venues);
			not_date_cont.setText("所选城市下无相关推荐场地");
		} else {
			not_date.setVisibility(View.GONE);
		}
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
