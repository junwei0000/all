/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignListBusiness;
import com.bestdo.bestdoStadiums.business.CampaignRegularEditBusiness;
import com.bestdo.bestdoStadiums.business.CampaignRegularEditBusiness.CampaignRegularEditCallback;
import com.bestdo.bestdoStadiums.business.CampaignRegularListBusiness.GetCampaignRegularListCallback;
import com.bestdo.bestdoStadiums.business.CampaignRegularListBusiness;
import com.bestdo.bestdoStadiums.control.adapter.CampaignListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CampaignRegularListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserCollectAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.CampaignRegularListInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.MyDialog;
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
 * @Description 类描述：活动- 固定活动列表
 */
public class CampaignRegularListActivity extends BaseActivity implements OnRefreshListioner {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private LinearLayout not_date;
	private PullDownListView mPullDownView;
	private ListView lv_date;
	private String uid;

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
		setContentView(R.layout.campaign_list);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
		lv_date = (ListView) findViewById(R.id.list_date);
	}

	@Override
	protected void setListener() {
		mPullDownView.setRefreshListioner(this);
		pagetop_layout_back.setOnClickListener(this);
		initDate();
		init();
	}

	private void initDate() {
		Intent intent = getIntent();
		uid = intent.getStringExtra("uid");
		pagetop_tv_name.setText("固定活动管理");
	}

	private ArrayList<CampaignRegularListInfo> mList;
	private int page;
	private int pagesize;

	private void init() {
		mAdapter = null;
		mList = new ArrayList<CampaignRegularListInfo>();
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
	private CampaignRegularListAdapter mAdapter;

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
		mhashmap.put("page", "" + page);
		mhashmap.put("pagesize", "" + pagesize);
		new CampaignRegularListBusiness(this, mList, mhashmap, new GetCampaignRegularListCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						mList = (ArrayList<CampaignRegularListInfo>) dataMap.get("mList");
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

	protected static final int EDIT = 1;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case EDIT:
				int status = msg.arg1;
				String activity_id = (String) msg.obj;
				if (status == 3 || status == 2) {
					editDialog(status, activity_id);
				} else {
					edit(status, activity_id);
				}
				break;
			}
		}
	};

	public void editDialog(final int status, final String activity_id) {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_logout);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text = (TextView) selectDialog.findViewById(R.id.myexit_text);
		TextView myexit_text_cirt = (TextView) selectDialog.findViewById(R.id.myexit_text_cirt);
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定
		// myexit_text.setText("固定活动编辑");
		myexit_text.setVisibility(View.GONE);
		if (status == 3) {
			myexit_text_cirt.setText("确定删除固定活动发布规则？");
		} else if (status == 2) {
			myexit_text_cirt.setText("确定停用该活动固定发布？");
		}

		text_off.setText("取消");
		text_sure.setText("确定");
		text_sure.setTextColor(getResources().getColor(R.color.btn_bg));
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				edit(status, activity_id);
			}
		});
	}

	private void edit(int status, String activity_id) {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("status", "" + status);
		mhashmap.put("activity_id", "" + activity_id);
		mhashmap.put("uid", "" + uid);
		new CampaignRegularEditBusiness(this, mhashmap, new CampaignRegularEditCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(context, msg + "");
					if (code.equals("200")) {
						mPullDownViewHandler.sendEmptyMessage(REFLESH);
					}

				}
				CommonUtils.getInstance().setClearCacheDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<CampaignRegularListInfo>();
		}
		if (mAdapter == null) {
			mAdapter = new CampaignRegularListAdapter(context, mList, mHandler, EDIT);
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
			not_date_cont.setText("暂无数据");
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
		if (mAdapter != null)
			mAdapter.clearCache();
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
			for (CampaignRegularListInfo iterable_element : mList) {
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
