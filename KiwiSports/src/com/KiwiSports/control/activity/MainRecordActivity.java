package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.RecordListBusiness;
import com.KiwiSports.business.RecordListBusiness.GetRecordListCallback;
import com.KiwiSports.control.adapter.RecordListAdapter;
import com.KiwiSports.control.adapter.VenuesListAdapter;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.PullDownListView;
import com.KiwiSports.utils.PullDownListView.OnRefreshListioner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author Administrator 运动记录
 */
public class MainRecordActivity extends BaseActivity implements OnRefreshListioner {

	private Activity mHomeActivity;
	private PullDownListView mPullDownView;
	private ListView mListView;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String token;
	private String access_token;
	private Editor mEdit;

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
		CommonUtils.getInstance().mCurrentActivity = CommonUtils.getInstance().mCurrentActivity;
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.main_record);
		mHomeActivity = CommonUtils.getInstance().mHomeActivity;
	}

	@Override
	protected void findViewById() {
		LinearLayout pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_layout_back.setVisibility(View.INVISIBLE);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		LinearLayout page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		pagetop_tv_name.setText(getString(R.string.record_title));
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
		mListView = (ListView) findViewById(R.id.list_date);
	}

	@Override
	protected void setListener() {
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setOrderBottomMoreLine("list");
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		mEdit = bestDoInfoSharedPrefs.edit();
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (mList != null & mList.size() > 0 && arg2 <= mList.size()) {
					String posid = mList.get(arg2 - 1).getPosid();
					String record_id = mList.get(arg2 - 1).getRecord_id();
					String name = mList.get(arg2 - 1).getPos_name();
					String sporttype = mList.get(arg2 - 1).getSportsType();
					Intent intent = new Intent(mHomeActivity, RecordDetailActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("sporttype", sporttype);
					intent.putExtra("name", name);
					intent.putExtra("posid", posid);
					intent.putExtra("record_id", record_id);
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
				}
			}
		});
		init();
	}

	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	protected ArrayList<RecordInfo> mList;
	protected int total;
	protected int beforetotal = -1;
	private int page;
	private int page_size;
	private RecordListAdapter adapter;

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
		beforetotal = -1;
		mList = new ArrayList<RecordInfo>();
		page = 1;
		page_size = 20;
	}

	private void initResume() {
		mList = new ArrayList<RecordInfo>();
		page = 1;
		page_size = 20;
	}

	@Override
	protected void processLogic() {

		if (page < 1) {
			mPullDownViewHandler.sendEmptyMessage(DATAUPDATEOVER);
			return;
		}
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("page", page + "");
		mhashmap.put("page_size", page_size + "");
		Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
		new RecordListBusiness(mHomeActivity, mEdit, mList, mhashmap, new GetRecordListCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						total = (Integer) dataMap.get("count");
						mList = (ArrayList<RecordInfo>) dataMap.get("mlist");
						if (total != beforetotal) {
							if (beforetotal == -1) {
								adapter = null;
							}
							beforetotal = total;
							if (page * page_size < total) {
								page++;
							}
							updateList();
						}
					} else {
						if (page == 0) {
							updateList();
						}
					}

				} else {
					CommonUtils.getInstance().initToast(mHomeActivity, getString(R.string.net_tishi));
				}
				mPullDownViewHandler.sendEmptyMessage(DATAUPDATEOVER);
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
				firststatus = false;

			}
		});

	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<RecordInfo>();
		}

		if (adapter == null) {
			adapter = new RecordListAdapter(this, mList, false);
			mListView.setAdapter(adapter);
		} else {
			adapter.setList(mList);
			adapter.notifyDataSetChanged();
		}
		addNotDateImg();
	}

	/**
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		LinearLayout not_date = (LinearLayout) findViewById(R.id.not_date);
		if (adapter == null || (adapter != null && adapter.getCount() == 0)) {
			not_date.setVisibility(View.VISIBLE);
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
			mPullDownView.onLoadMoreComplete(getString(R.string.notmore_info), "over");
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
		if (adapter != null && total > adapter.getCount() && (page * page_size < total)) {
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
	private final int REFLESHRESUME = 3;
	Handler mPullDownViewHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DATAUPDATEOVER:
				mPullDownView.onRefreshComplete();
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				nideBottom();
				break;
			case REFLESH:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					init();
					processLogic();
				}
				break;
			case REFLESHRESUME:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					initResume();
					processLogic();
				}
				break;
			case LOADMORE:
				System.err.println("LOADMORELOADMORELOADMORELOADMORELOADMORE");
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					page++;
					System.err.println("processLogicprocessLogic");
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
		System.err.println("onLoadMoreonLoadMoreonLoadMoreonLoadMore");
		mHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(LOADMORE);
			}
		}, 1500);

	}

	Boolean firststatus = true;

	@Override
	protected void onResume() {
		super.onResume();
		if (!firststatus)
			mPullDownViewHandler.sendEmptyMessage(REFLESHRESUME);
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
