package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

import com.bestdo.bestdoStadiums.business.UserCollectBusiness;
import com.bestdo.bestdoStadiums.business.UserCollectBusiness.GetUserCollectCallback;
import com.bestdo.bestdoStadiums.control.adapter.UserCollectAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午3:58:31
 * @Description 类描述：用户收藏列表
 */
public class UserCollectActivity extends BaseActivity implements OnRefreshListioner {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ListView lv_date;
	private LinearLayout not_date;
	private ArrayList<UserCollectInfo> mList;
	private UserCollectAdapter mUserCollectAdapter;
	private String uid;
	private ProgressDialog mDialog;
	private PullDownListView mPullDownView;

	private int page;
	private int pagesize;
	private int total;

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
		setContentView(R.layout.user_collect);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
		lv_date = (ListView) findViewById(R.id.lv_date);
	}

	@Override
	protected void setListener() {
		mPullDownView.setRefreshListioner(this);
		pagetop_layout_back.setOnClickListener(this);
		lv_date.setOnItemClickListener(itemListener);
		initDate();
		init();
	}

	private OnItemClickListener itemListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			mPullDownView.initBg(context, mUserCollectAdapter);
			if (mList != null && arg2 <= mList.size()) {
				String RelationNo = mList.get(arg2 - 1).getRelationNo();
				Intent intent = new Intent(UserCollectActivity.this, StadiumDetailActivity.class);
				intent.putExtra("mer_item_id", RelationNo);
				intent.putExtra("vip_price_id", mList.get(arg2 - 1).getVip_price_id());
				intent.putExtra("skipToDetailStatus", Constans.getInstance().skipToDetailByCollectPage);
				startActivityForResult(intent, Constans.getInstance().collectForResultByStaDetailPage);
				CommonUtils.getInstance().setPageIntentAnim(intent, context);
			}
		}
	};

	private void initDate() {
		pagetop_tv_name.setText(getResources().getString(R.string.usercollect_title));
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
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

	private void init() {
		mUserCollectAdapter = null;
		mList = new ArrayList<UserCollectInfo>();
		page = 0;
		page++;
		pagesize = 20;
	}

	HashMap<String, String> mhashmap;

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
		mhashmap.put("uid", uid);// "522151230142308Qlz"
		mhashmap.put("page", "" + page);
		mhashmap.put("pagesize", "" + pagesize);
		Log.e("UserCollectActivity.this", mhashmap.toString());
		new UserCollectBusiness(this, mList, mhashmap, new GetUserCollectCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						mList = (ArrayList<UserCollectInfo>) dataMap.get("mList");
						total = (Integer) dataMap.get("total");
						if (page * pagesize < total) {
							page++;
						}
						updateList();
					} else {
						String data = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(UserCollectActivity.this, data);
					}
				} else {
					CommonUtils.getInstance().initToast(UserCollectActivity.this, getString(R.string.net_tishi));
				}
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<UserCollectInfo>();
		}
		if (mUserCollectAdapter == null) {
			mUserCollectAdapter = new UserCollectAdapter(UserCollectActivity.this, mList);
			lv_date.setAdapter(mUserCollectAdapter);
		} else {
			mUserCollectAdapter.setList(mList);
			mUserCollectAdapter.notifyDataSetChanged();
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
		if (mUserCollectAdapter == null || (mUserCollectAdapter != null && mUserCollectAdapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			TextView not_date_jihuo_btn = (TextView) findViewById(R.id.not_date_jihuo_btn);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_jihuo_btn.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.main_ic_none_collect);
			not_date_cont.setText("暂无收藏场地信息");
			not_date_jihuo_btn.setText("立即去收藏");
			not_date_jihuo_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CommonUtils.getInstance().skipMainActivity(context);
				}
			});
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
			mPullDownView.onLoadMoreComplete("已无更多收藏场地信息了！", "over");
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
		if (mUserCollectAdapter != null && total > mUserCollectAdapter.getCount() && (page * pagesize < total)) {
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
		if (mUserCollectAdapter != null)
			mUserCollectAdapter.clearCache();
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
		mUserCollectAdapter = null;
		if (mList != null && mList.size() != 0) {
			for (UserCollectInfo iterable_element : mList) {
				iterable_element = null;
			}
			mList.clear();
			mList = null;
		}
	}

	/**
	 * activity回调数据
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == Constans.getInstance().collectForResultByStaDetailPage) {
				boolean collectStatus = data.getBooleanExtra("collectStatus", true);
				if (!collectStatus) {
					mPullDownViewHandler.sendEmptyMessageDelayed(REFLESH, 1000);
				}
			}
		} catch (Exception e) {
		}
	};

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
