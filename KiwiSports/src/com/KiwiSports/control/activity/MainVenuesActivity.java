package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.VenuesBusiness;
import com.KiwiSports.business.VenuesBusiness.GetVenuesCallback;
import com.KiwiSports.control.adapter.VenuesListAdapter;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.PullDownListView;
import com.KiwiSports.utils.PullDownListView.OnRefreshListioner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：场地列表
 */
public class MainVenuesActivity extends BaseActivity implements OnRefreshListioner {

	private PullDownListView mPullDownView;
	private ListView mListView;
	private HashMap<String, String> mhashmap;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String token;
	private String access_token;
	private Activity mHomeActivity;
	protected Integer total;
	protected ArrayList<VenuesListInfo> mList;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		CommonUtils.getInstance().mCurrentActivity=CommonUtils.getInstance().mCurrentActivity;
	}
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.main_location);
		mHomeActivity = CommonUtils.getInstance().mHomeActivity;
	}

	@Override
	protected void findViewById() {
		LinearLayout pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_layout_back.setVisibility(View.INVISIBLE);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		LinearLayout page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		pagetop_tv_name.setText(getString(R.string.venues_title));
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
		mListView = (ListView) findViewById(R.id.list_date);

	}

	@Override
	protected void setListener() {
		initFootView();
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setOrderBottomMoreLine("list");
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (mList != null & mList.size() > 0 && arg2 <= mList.size()) {

					String name = mList.get(arg2 - 1).getField_name();
					String posid = mList.get(arg2 - 1).getPosid();
					double top_left_x = mList.get(arg2 - 1).getTop_left_x();
					double top_left_y = mList.get(arg2 - 1).getTop_left_y();
					Intent intent = new Intent(mHomeActivity, VenuesMapActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("posid", posid);
					intent.putExtra("name", name);
					intent.putExtra("top_left_x", top_left_x);
					intent.putExtra("top_left_y", top_left_y);
					intent.putExtra("uid", uid);
					intent.putExtra("token", token);
					intent.putExtra("access_token", access_token);
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
				}
			}
		});
	}

	private void initFootView() {
		convertView = LayoutInflater.from(context).inflate(R.layout.venues_list_add, null);
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mHomeActivity, VenuesAddActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			}
		});

	}

	private ProgressDialog mDialog;
	private View convertView;

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

	@Override
	protected void processLogic() {
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
		new VenuesBusiness(mHomeActivity, mhashmap, new GetVenuesCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				/**
				 * 动态添加的效果主要的做法是：在listview.setadapter之前添加所需要的控件，然后使用removeFooterView()方法移除控件。
				 */
				mListView.removeFooterView(convertView);
				mListView.addFooterView(convertView);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						total = (Integer) dataMap.get("count");
						mList = (ArrayList<VenuesListInfo>) dataMap.get("mlist");
					}
					if (mList != null && mList.size() > 0) {
					} else {
						mList = new ArrayList<VenuesListInfo>();
					}
					VenuesListAdapter adapter = new VenuesListAdapter(mHomeActivity, mList);
					mListView.setAdapter(adapter);
				} else {
					CommonUtils.getInstance().initToast(mHomeActivity, getString(R.string.net_tishi));
				}
				mPullDownViewHandler.sendEmptyMessage(DATAUPDATEOVER);
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	/**
	 * 数据加载完后，判断底部“加载更多”显示状态
	 */
	private final int DATAUPDATEOVER = 0;

	/**
	 * 下拉刷新
	 */
	private final int REFLESH = 1;
	Handler mPullDownViewHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DATAUPDATEOVER:
				mPullDownView.onRefreshComplete();
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				mPullDownView.onLoadMoreComplete(getString(R.string.notmore_info), "over");
				mPullDownView.setMore(false);
				break;
			case REFLESH:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					processLogic();
				}
				break;

			}
		}
	};
	private Handler mHandler = new Handler();

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
		mPullDownViewHandler.sendEmptyMessage(DATAUPDATEOVER);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mPullDownViewHandler.sendEmptyMessage(REFLESH);
	}

	/**
	 * 退出监听
	 */
	public void onBackPressed() {
		Intent intents = new Intent();
		intents.setAction(getString(R.string.action_home));
		intents.putExtra("type", getString(R.string.action_home_type_gotohome));
		sendBroadcast(intents);
	}

}
