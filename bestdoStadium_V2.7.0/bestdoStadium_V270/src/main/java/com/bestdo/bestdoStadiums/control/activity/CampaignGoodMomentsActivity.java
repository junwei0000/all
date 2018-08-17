/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshView.OnReflushListener;
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignGoodMonmentBusiness;
import com.bestdo.bestdoStadiums.business.CampaignGoodMonmentBusiness.CampaignGoodMonmentCallback;
import com.bestdo.bestdoStadiums.business.UserCardsBusiness;
import com.bestdo.bestdoStadiums.business.UserCardsBusiness.GetUserCardsCallback;
import com.bestdo.bestdoStadiums.control.adapter.CircleAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserCardAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.CircleBean;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午5:53:39
 * @Description 类描述：精彩时刻
 */
public class CampaignGoodMomentsActivity extends BaseActivity implements OnRefreshListioner {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private LinearLayout not_date;
	private ListView mListView;
	private ImageView pagetop_iv_you;
	private PullDownListView mPullDownView;

	private HashMap<String, String> mhashmap;
	int page, pagesize;
	int total;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String bid;
	private ArrayList<CircleBean> mList;
	private CircleAdapter circleAdapter;
	/**
	 * 1.管理员 才可以发布删除
	 */
	private String privilege_id;
	private String corp_id;
	private String last_won_id = "";

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.pagetop_iv_you:
			Intent intent = new Intent(this, CampaignGMPublicActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.campaign_good_moments);
		CommonUtils.getInstance().addActivity(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		bid = bestDoInfoSharedPrefs.getString("bid", "");
		corp_id = bestDoInfoSharedPrefs.getString("corp_id", "");
		privilege_id = bestDoInfoSharedPrefs.getString("privilege_id", "");

	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
		pagetop_iv_you.setOnClickListener(this);
		pagetop_iv_you.setBackgroundResource(R.drawable.campaign_gmimg_public);
		if (!TextUtils.isEmpty(privilege_id) && privilege_id.equals("1")) {
			pagetop_iv_you.setVisibility(View.VISIBLE);
		}
		not_date = (LinearLayout) findViewById(R.id.not_date);
		mPullDownView = (PullDownListView) findViewById(R.id.good_moment_xrefresh);
		mListView = (ListView) findViewById(R.id.listview_goodmoment);

	}

	@Override
	protected void setListener() {
		pagetop_tv_name.setText("精彩时刻");
		pagetop_layout_back.setOnClickListener(this);
		init();
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setLoadingtishiString("正在加载中");
		mPullDownView.setOrderBottomMoreLine("list");
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (mList != null && mList.size() > arg2) {
					CommonUtils.getInstance().toH5(context,mList.get(arg2 - 1).getWondful_show_url(), "精彩时刻", "",true);
				}
			}
		});
	}

	private void init() {
		mList = new ArrayList<CircleBean>();
		page = 0;
		page++;
		pagesize = 20;
		last_won_id = "";
	}

	@Override
	protected void processLogic() {
		if (TextUtils.isEmpty(privilege_id)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.img_enjoytime);
			not_date_cont.setText("尚未开通企业账号");
			Constans.getInstance().refreshOrLoadMoreLoading = false;
			return;
		}
		if (page < 1) {
			setloadPageMoreStatus();
			return;
		}
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("page", "" + page);
		mhashmap.put("pagesize", "" + pagesize);
		mhashmap.put("corp_id", corp_id);
		mhashmap.put("uid", uid);
		mhashmap.put("last_won_id", "" + last_won_id);

		System.err.println(mhashmap);
		String url = Constans.GETWONDERFUL;
		new CampaignGoodMonmentBusiness(this, mhashmap, mList, url, new CampaignGoodMonmentCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String data = (String) dataMap.get("data");
						// CommonUtils.getInstance().initToast(
						// UserCardsActivity.this, data);
					} else if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(CampaignGoodMomentsActivity.this);
					} else if (status.equals("200")) {
						// total = (Integer) dataMap.get("totalRows");
						mList = (ArrayList<CircleBean>) dataMap.get("mList");
						// // if (page * pagesize < total) {
						page++;
						// }
						updateList();
					}
				} else {
					CommonUtils.getInstance().initToast(CampaignGoodMomentsActivity.this,
							getString(R.string.net_tishi));
				}
				addNotDateImg();
				firstComeIn = false;
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
			last_won_id = mList.get(mList.size() - 1).getWonderful_id();
		} else {
			last_won_id = "";
			mList = new ArrayList<CircleBean>();
		}
		if (circleAdapter == null) {
			circleAdapter = new CircleAdapter(mList, this, privilege_id, mPullDownViewHandler, DEL, uid);
			mListView.setAdapter(circleAdapter);
		} else {
			circleAdapter.setList(mList);
			circleAdapter.notifyDataSetChanged();
		}

	}

	private ProgressDialog mDialog;

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(CampaignGoodMomentsActivity.this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		if (circleAdapter == null || (circleAdapter != null && circleAdapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.img_enjoytime);
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
	 * 判断数据是否加载完
	 * 
	 * @return true为加载完
	 */
	private boolean checkDataLoadStatus() {
		boolean loadStatus = true;
		if (circleAdapter != null && (page * pagesize > circleAdapter.getCount())) {

		} else {
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
	protected final int DEL = 3;
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
					if (!ConfigUtils.getInstance().isNetWorkAvaiable(context)) {
						CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
						setloadPageMoreStatus();
						return;
					}
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
			case DEL:
				mList.remove(msg.arg1);
				circleAdapter.notifyDataSetChanged();
				mPullDownViewHandler.sendEmptyMessage(REFLESH);
				break;
			}
		}
	};

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
		super.onResume();
		if (!firstComeIn)
			mPullDownViewHandler.sendEmptyMessage(REFLESH);
	}

	@Override
	protected void onRestart() {
		// if (!firstComeIn) {
		// System.err.println("onResume");
		// mHandler.sendEmptyMessage(REFRESH);
		// }
		super.onRestart();
	}

	protected void onPause() {
		Log.e("onPause-----", "onPauseonPauseonPauseonPauseonPauseonPause-----");
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
