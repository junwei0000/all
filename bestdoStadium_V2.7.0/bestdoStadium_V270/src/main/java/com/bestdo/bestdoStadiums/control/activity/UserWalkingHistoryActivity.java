package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.UserWalkingHistoryBusiness;
import com.bestdo.bestdoStadiums.business.UserWalkingHistoryBusiness.GetUserWalkingHistoryCallback;
import com.bestdo.bestdoStadiums.control.adapter.UserWalkingHistoryAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.UserWalkingHistoryInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UserWalkingHistoryActivity extends BaseActivity implements OnRefreshListioner {
	LinearLayout page_top_layout;
	private LinearLayout pagetop_layout_back;
	private ImageView pagetop_iv_back;
	private TextView pagetop_tv_name;
	private PullDownListView mPullDownView;
	private ListView lv_date;
	private LinearLayout not_date;
	private HashMap<String, String> mhashmap;
	private ArrayList<UserWalkingHistoryInfo> list;
	private int page;
	private int pagesize;
	private SharedPreferences bestDoInfoSharedPrefs;
	protected int total;
	private UserWalkingHistoryAdapter adapter;
	protected UserWalkingHistoryInfo mMaxHistoryInfo;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_walking_history);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_iv_back = (ImageView) findViewById(R.id.pagetop_iv_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
		lv_date = (ListView) findViewById(R.id.lv_date);
		not_date = (LinearLayout) findViewById(R.id.not_date);
	}

	@Override
	protected void setListener() {
		mPullDownView.setMoreBottom();
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setOrderBottomMoreLine("list");
		pagetop_layout_back.setOnClickListener(this);
		page_top_layout.setBackgroundColor(getResources().getColor(R.color.btn_blue_color));
		pagetop_iv_back.setBackgroundResource(R.drawable.back_moren);
		pagetop_tv_name.setTextColor(getResources().getColor(R.color.white));
		pagetop_tv_name.setText("跑步记录");
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		lv_date.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					if (list != null & list.size() > 0 && arg2 > 1 && arg2 <= list.size()) {
						Intent intent = new Intent(context, UserWalkingTraceActivity.class);
						intent.putExtra("skipTypeActivity", "History");
						intent.putExtra("log_id", list.get(arg2 - 1).getId());
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, context);
					}
				}
			}
		});
		// log_id uid
		// appservice/getHistoryLog

		init();
	}

	private void init() {
		adapter = null;
		list = new ArrayList<UserWalkingHistoryInfo>();
		page = 1;
		pagesize = 10;
	}

	@Override
	protected void processLogic() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		mhashmap.put("page", "" + page);
		mhashmap.put("pagesize", "" + pagesize);
		System.err.println("--------" + mhashmap);
		new UserWalkingHistoryBusiness(this, mhashmap, list, new GetUserWalkingHistoryCallback() {

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
						if (dataMap.containsKey("total")) {
							total = (Integer) dataMap.get("total");
						}
						mMaxHistoryInfo = (UserWalkingHistoryInfo) dataMap.get("mMaxHistoryInfo");
						list = (ArrayList<UserWalkingHistoryInfo>) dataMap.get("mList");

						if (list != null && list.size() != 0) {
						} else {
							list = new ArrayList<UserWalkingHistoryInfo>();
						}

						if (mMaxHistoryInfo != null && list.size() > 0) {
							list.add(0, mMaxHistoryInfo);
							total = total + 1;

						}
						if (page * pagesize < total) {
							page++;
						}
						updateList();
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				addNotDateImg();
				setloadPageMoreStatus();
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});
	}

	private void updateList() {

		if (adapter == null) {
			adapter = new UserWalkingHistoryAdapter(this, list);
			lv_date.setAdapter(adapter);
		} else {
			adapter.setList(list);
			adapter.notifyDataSetChanged();
		}
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
			not_date_img.setBackgroundResource(R.drawable.user_walking_nohistory_img);
			not_date_cont.setText("目前还没有运动记录");
		} else {
			not_date.setVisibility(View.GONE);
		}
	}

	/**
	 * 当返回数据小于总数时，不让显示“加载更多”
	 */
	private void nideBottom() {
		if (!checkDataLoadStatus()) {
			mPullDownView.onLoadMoreComplete("运动信息加载中...", "");// 这里表示加载更多处理完成后把下面的加载更多界面（隐藏或者设置字样更多）
			mPullDownView.setMore(true);// 这里设置true表示还有更多加载，设置为false底部将不显示更多
		} else {
			mPullDownView.onLoadMoreComplete("已无更多运动信息！", "over");
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
					list.remove(0);
					processLogic();
					// }
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

	/**
	 * 退出页面前执行操作
	 */
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
