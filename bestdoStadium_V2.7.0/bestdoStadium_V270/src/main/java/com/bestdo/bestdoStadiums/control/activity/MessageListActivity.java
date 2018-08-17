/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignListBusiness;
import com.bestdo.bestdoStadiums.business.MessageListBusiness.MessageListCallback;
import com.bestdo.bestdoStadiums.business.MessageListBusiness;
import com.bestdo.bestdoStadiums.business.MessageSetReadBusiness;
import com.bestdo.bestdoStadiums.business.MessageSetReadBusiness.MessageSetReadCallback;
import com.bestdo.bestdoStadiums.control.adapter.CampaignListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.MessageListAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.MessageListInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.umeng.analytics.MobclickAgent;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-2 上午10:51:16
 * @Description 类描述：
 */
public class MessageListActivity extends BaseActivity implements OnRefreshListioner {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private LinearLayout not_date;
	private PullDownListView mPullDownView;
	private ListView lv_date;
	private String uid;
	private String type;
	private TextView pagetop_tv_you;
	private SharedPreferences bestDoInfoSharedPrefs;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.pagetop_tv_you:
			setAllRead();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.message_list);
		CommonUtils.getInstance().addActivity(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
		lv_date = (ListView) findViewById(R.id.list_date);
	}

	@Override
	protected void setListener() {
		mPullDownView.setRefreshListioner(this);
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		lv_date.setOnItemClickListener(itemListener);
		initDate();
		init();
	}

	private void initDate() {
		Intent intent = getIntent();
		uid = intent.getStringExtra("uid");
		type = intent.getStringExtra("type");
		pagetop_tv_name.setText("活动通知消息");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText("全部已读");
		pagetop_tv_you.setTextColor(getResources().getColor(R.color.text_noclick_color));
	}

	private OnItemClickListener itemListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			mPullDownView.initBg(context, mAdapter);
			if (mList != null && arg2 <= mList.size()) {
				String is_read = mList.get(arg2 - 1).getIs_read();
				if (is_read.equals("0")) {
					String message_id = mList.get(arg2 - 1).getId();
					serRead(message_id, arg2 - 1);
				}

				String act_status = mList.get(arg2 - 1).getActivity_status();
				if (act_status.equals("0")) {
					String activity_id = mList.get(arg2 - 1).getActivity_id();
					Intent intent = new Intent(context, CampaignDetailActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("uid", uid);
					intent.putExtra("activity_id", activity_id);
					context.startActivity(intent);
				} else if (act_status.equals("1")) {
					CommonUtils.getInstance().initToast(context, "活动已结束");
				} else if (act_status.equals("2")) {
					CommonUtils.getInstance().initToast(context, "活动已取消");
				}
			}
		}
	};
	private ArrayList<MessageListInfo> mList;
	private int page;
	private int pagesize;

	private void init() {
		mList = new ArrayList<MessageListInfo>();
		page = 0;
		page++;
		pagesize = 20;
	}

	private ProgressDialog mDialog;

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

	HashMap<String, String> mhashmap;
	protected Integer total;
	private MessageListAdapter mAdapter;

	private void setAllRead() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("type", "" + type);
		new MessageSetReadBusiness(this, "all", mhashmap, new MessageSetReadCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(context, msg);
					if (status.equals("200")) {
						for (MessageListInfo iterable_element : mList) {
							iterable_element.setIs_read("1");
						}
						updateList();
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	private void serRead(String message_id, final int index) {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("message_id", message_id);
		new MessageSetReadBusiness(this, "one", mhashmap, new MessageSetReadCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("200")) {
						mList.get(index).setIs_read("1");
						updateList();
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	@Override
	protected void processLogic() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			setloadPageMoreStatus();
			return;
		}
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mPullDownView.setVisibility(View.VISIBLE);
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("type", "" + type);
		mhashmap.put("page", "" + page);
		mhashmap.put("pagesize", "" + pagesize);
		mhashmap.put("crop_id", bestDoInfoSharedPrefs.getString("corp_id", ""));
		new MessageListBusiness(this, mList, mhashmap, new MessageListCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						mList = (ArrayList<MessageListInfo>) dataMap.get("mList");
						total = (Integer) dataMap.get("total");
						if (page * pagesize < total) {
							page++;
						}
						updateList();
					} else {
						String data = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, data);
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				firstComeIn = false;
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<MessageListInfo>();
		}
		if (mAdapter == null) {
			mAdapter = new MessageListAdapter(context, mList);
			lv_date.setAdapter(mAdapter);
		} else {
			mAdapter.setList(mList);
			mAdapter.notifyDataSetChanged();
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
	 ** 
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (mAdapter == null || (mAdapter != null && mAdapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.not_date);
			not_date_cont.setText("暂无信息");
		} else {
			not_date.setVisibility(View.GONE);
		}
	}

	/**
	 * 当返回数据小于总数时，不让显示“加载更多”
	 */
	private void nideBottom() {
		if (!checkDataLoadStatus()) {
			mPullDownView.onLoadMoreComplete(getResources().getString(R.string.seen_more), "");
			mPullDownView.setMore(true);
		} else {
			// mPullDownView.onLoadMoreComplete(
			// getResources().getString(R.string.load_all), "over");
			// mPullDownView.setMore(false);
			mPullDownView.onLoadMoreComplete("已无更多信息了！", "over");
			mPullDownView.setMore(true);
		}
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
				nideBottom();
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				break;
			case REFLESH:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					init();
					processLogic();
				}
				break;
			case LOADMORE:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					mPullDownView.showMore();
					// if (!checkDataLoadStatus()) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					page++;
					processLogic();
					// }
				}
				break;
			}
		}
	};

	/**
	 * 判断数据是否加载完
	 * 
	 * @return true为加载完
	 */
	private boolean checkDataLoadStatus() {
		boolean loadStatus = true;
		if (mAdapter != null && total > mAdapter.getCount() && (page * pagesize < total)) {
			loadStatus = false;
		}
		return loadStatus;
	}

	/**
	 * 刷新，先清空list中数据然后重新加载更新内容
	 */
	private Handler mListHandler = new Handler();

	public void onRefresh() {
		mListHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(REFLESH);
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

	boolean firstComeIn = true;

	@Override
	protected void onResume() {
		Log.e("onResume-----", "onResumeonResumeonResumeonResume-----");
		super.onResume();
		MobclickAgent.onPageStart("MainScreen");
		if (!firstComeIn)
			mPullDownViewHandler.sendEmptyMessage(REFLESH);
	}

	protected void onPause() {
		Log.e("onPause-----", "onPauseonPauseonPauseonPauseonPauseonPause-----");
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		clearCache();
		RequestUtils.cancelAll(this);
		if (mAdapter != null)
			mAdapter.clearCache();
		super.onDestroy();
	}

	private void clearCache() {
		mAdapter = null;
		if (mList != null && mList.size() != 0) {
			for (MessageListInfo iterable_element : mList) {
				iterable_element = null;
			}
			mList.clear();
			mList = null;
		}
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
