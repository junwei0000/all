package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.VenuesRankAllBusiness;
import com.KiwiSports.business.VenuesRankAllBusiness.GetVenuesRankAllCallback;
import com.KiwiSports.business.VenuesRankTodayBusiness;
import com.KiwiSports.business.VenuesRankTodayBusiness.GetVenuesRankTodayCallback;
import com.KiwiSports.business.VenuesTypeBusiness;
import com.KiwiSports.business.VenuesTypeBusiness.GetVenuesTypeCallback;
import com.KiwiSports.control.adapter.VenuesHobbyAdapter;
import com.KiwiSports.model.HobbyInfo;
import com.KiwiSports.model.VenuesRankTodayInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constans;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：
 */
public class VenuesRankActivity extends BaseActivity {

	private ListView mListView;
	private HashMap<String, String> mhashmap;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String token;
	private String access_token;
	private LinearLayout pagetop_layout_back;
	private String posid;
	private TextView pagetop_tv_name;
	private TextView tv_today;
	private TextView tv_todayline;
	private TextView tv_all;
	private TextView tv_allline;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.tv_today:
			todayStatus = true;
			initBottom();
			processLogic();
			break;
		case R.id.tv_all:
			todayStatus = false;
			initBottom();
			getRankAll();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.venues_rank);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		mListView = (ListView) findViewById(R.id.list_date);

		tv_today = (TextView) findViewById(R.id.tv_today);
		tv_todayline = (TextView) findViewById(R.id.tv_todayline);
		tv_all = (TextView) findViewById(R.id.tv_all);
		tv_allline = (TextView) findViewById(R.id.tv_allline);
		initBottom();
	}

	@SuppressLint("NewApi")
	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		tv_today.setOnClickListener(this);
		tv_all.setOnClickListener(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");

		Intent intent = getIntent();
		String name = intent.getExtras().getString("name", "");
		posid = intent.getExtras().getString("posid", "");
		pagetop_tv_name.setText(name);
	}

	boolean todayStatus = true;

	private void initBottom() {
		tv_today.setTextColor(getResources().getColor(R.color.white_light));
		tv_all.setTextColor(getResources().getColor(R.color.white_light));
		tv_todayline.setVisibility(View.INVISIBLE);
		tv_allline.setVisibility(View.INVISIBLE);
		if (todayStatus) {
			tv_today.setTextColor(getResources().getColor(R.color.white));
			tv_todayline.setVisibility(View.VISIBLE);
		} else {
			tv_all.setTextColor(getResources().getColor(R.color.white));
			tv_allline.setVisibility(View.VISIBLE);
		}
	}

	private ProgressDialog mDialog;
	protected ArrayList<VenuesRankTodayInfo> mList;

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

	protected VenuesHobbyAdapter adapter;

	@Override
	protected void processLogic() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("posid", posid);
		new VenuesRankTodayBusiness(this, mhashmap, new GetVenuesRankTodayCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mList = (ArrayList<VenuesRankTodayInfo>) dataMap.get("mList");
						if (mList != null && mList.size() > 0) {
						} else {
							mList = new ArrayList<VenuesRankTodayInfo>();
						}
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	protected void getRankAll() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("posid", posid);
		new VenuesRankAllBusiness(this, mhashmap, new GetVenuesRankAllCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						dataMap.get("mList");
						mList = (ArrayList<VenuesRankTodayInfo>) dataMap.get("mList");
						if (mList != null && mList.size() > 0) {
						} else {
							mList = new ArrayList<VenuesRankTodayInfo>();
						}
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	private void doBack() {
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
}
