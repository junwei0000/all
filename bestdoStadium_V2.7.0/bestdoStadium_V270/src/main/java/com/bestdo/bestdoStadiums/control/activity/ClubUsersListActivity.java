/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignBaomingListBusiness;
import com.bestdo.bestdoStadiums.business.CampaignBaomingListBusiness.CampaignBaomingListCallback;
import com.bestdo.bestdoStadiums.business.ClubUserListBusiness;
import com.bestdo.bestdoStadiums.business.ClubUserListBusiness.ClubUserListCallback;
import com.bestdo.bestdoStadiums.control.adapter.CampaignBaominglistAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CampaignListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.ClubUserlistAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserCollectAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午5:53:39
 * @Description 类描述：活动报名成员列表
 */
public class ClubUsersListActivity extends BaseActivity implements OnRefreshListioner {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private LinearLayout not_date;
	private ListView lv_date;
	private String club_id = "";
	private PullDownListView mPullDownView;
	private int page;
	private int pagesize;
	private Handler mHandler = new Handler();
	private int total = 0;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.club_userlist);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		lv_date = (ListView) findViewById(R.id.list_date);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setLoadingtishiString("俱乐部成员正在加载中");
		mPullDownView.setOrderBottomMoreLine("list");
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		initDate();
		init();
	}

	private void initDate() {
		Intent intent = getIntent();
		club_id = intent.getStringExtra("club_id");
		pagetop_tv_name.setText("成员列表");
	}

	private void init() {
		mList = new ArrayList<CampaignDetailInfo>();
		page = 0;
		page++;
		pagesize = 20;
	}

	private ArrayList<CampaignDetailInfo> mList;
	private ArrayList<CampaignDetailInfo> master_list;

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
	private ClubUserlistAdapter mAdapter;

	@Override
	protected void processLogic() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("club_id", club_id);
		mhashmap.put("page", "" + page);
		mhashmap.put("pagesize", "" + pagesize);
		new ClubUserListBusiness(this, mhashmap, mList, new ClubUserListCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("200")) {

						mPullDownView.setVisibility(View.VISIBLE);
						mList = (ArrayList<CampaignDetailInfo>) dataMap.get("mList");
						master_list = (ArrayList<CampaignDetailInfo>) dataMap.get("master_list");
						total = (Integer) dataMap.get("member_count");
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
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	private void updateList() {
		ArrayList<CampaignDetailInfo> mList_ = new ArrayList<CampaignDetailInfo>();

		if (master_list != null && master_list.size() != 0) {

		} else {
			master_list = new ArrayList<CampaignDetailInfo>();
		}
		mList_.addAll(master_list);

		if (mList != null && mList.size() != 0) {

		} else {
			mList = new ArrayList<CampaignDetailInfo>();
		}
		mList_.addAll(mList);

		if (mAdapter == null) {
			mAdapter = new ClubUserlistAdapter(context, mList_);
			lv_date.setAdapter(mAdapter);
		} else {
			mAdapter.setList(mList_);
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
			not_date_cont.setText("暂无报名成员");
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
			mPullDownView.onLoadMoreComplete("已无更多成员信息了！", "over");
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
		if (mAdapter != null && total > mAdapter.getCount() && (page * pagesize < total)) {
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
	/**
	 * 更新view
	 */
	private final int UPDATEVIEW = 3;
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
					// if (!checkDataLoadStatus()) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					page++;
					processLogic();
					// }
				}
				break;
			case UPDATEVIEW:
				if (mAdapter != null) {
					mAdapter.notifyDataSetChanged();
				}
				addNotDateImg();
				break;

			}
		}
	};

	/**
	 * 刷新，先清空list中数据然后重新加载更新内容
	 */
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(REFLESH);
			}
		}, 1500);
	}

	/**
	 * 加载更多，在原来基础上在添加新内容
	 */
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(LOADMORE);
			}
		}, 1500);
	}

	@Override
	protected void onResume() {
		Log.e("onResume-----", "onResumeonResumeonResumeonResume-----");
		super.onResume();
		MobclickAgent.onPageStart("MainScreen");
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
		super.onDestroy();
	}

	private void clearCache() {
		mAdapter = null;
		if (mList != null && mList.size() != 0) {
			for (CampaignDetailInfo iterable_element : mList) {
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
