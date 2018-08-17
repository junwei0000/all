package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.UserWalkingHistoryBusiness;
import com.bestdo.bestdoStadiums.business.UserWalkingLoadTotalStepBusiness;
import com.bestdo.bestdoStadiums.business.UserWalkingLoadTotalStepBusiness.GetUserWalkingLoadTotalStepCallback;
import com.bestdo.bestdoStadiums.business.UserWalkingRankBusiness;
import com.bestdo.bestdoStadiums.business.UserWalkingHistoryBusiness.GetUserWalkingHistoryCallback;
import com.bestdo.bestdoStadiums.business.UserWalkingRankBusiness.GetUserWalkingRankCallback;
import com.bestdo.bestdoStadiums.control.adapter.StadiumAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserWalkingHistoryAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserWalkingRankBestdoAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserWalkingHistoryInfo;
import com.bestdo.bestdoStadiums.model.UserWalkingRankInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UserWalkingRankActivity extends BaseActivity implements OnRefreshListioner {
	LinearLayout page_top_layout;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView pagetop_iv_back;
	private TextView rank_tv_bestdo;
	private TextView rank_tv_bestdoline;
	private TextView rank_tv_business;
	private TextView rank_tv_businessline;
	private ListView rank_baidong_listdate;
	private LinearLayout not_date;
	private String uid;
	private String bid;
	private String bname;
	private String deptId;
	private String company_title;
	private String department_title;
	private LinearLayout rank_layout_business;
	/**
	 * //true 为企业,false部门
	 */
	boolean beatdoSelectStatus = true;
	private HashMap<String, String> mHashMap;
	protected UserWalkingRankBestdoAdapter mBestdoAdapter;
	private int step_num;
	protected int total;
	private PullDownListView mPullDownView;
	private String ranked_show;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.rank_tv_bestdo:
			beatdoSelectStatus = true;
			setClickSelectBtn();
			processLogicDate();
			break;
		case R.id.rank_tv_business:
			beatdoSelectStatus = false;
			setClickSelectBtn();
			if (!bid.equals("0")) {
				processLogicDate();
			} else {
				ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
				TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
				not_date.setVisibility(View.VISIBLE);
				not_date_img.setVisibility(View.VISIBLE);
				not_date_cont.setVisibility(View.VISIBLE);
				not_date_img.setBackgroundResource(R.drawable.user_walking_nohistory_img);
				not_date_cont.setText("暂无相关计步信息");
				not_date.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_walking_rank);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_iv_back = (ImageView) findViewById(R.id.pagetop_iv_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		rank_tv_bestdo = (TextView) findViewById(R.id.rank_tv_bestdo);
		rank_tv_bestdoline = (TextView) findViewById(R.id.rank_tv_bestdoline);
		rank_tv_business = (TextView) findViewById(R.id.rank_tv_business);
		rank_tv_businessline = (TextView) findViewById(R.id.rank_tv_businessline);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		rank_layout_business = (LinearLayout) findViewById(R.id.rank_layout_business);
		rank_baidong_listdate = (ListView) findViewById(R.id.rank_baidong_listdate);
		mPullDownView = (PullDownListView) findViewById(R.id.refreshable_view);
	}

	@Override
	protected void setListener() {
		mPullDownView.setMoreBottom();
		mPullDownView.setMore(false);
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setOrderBottomMoreLine("list");
		pagetop_layout_back.setOnClickListener(this);
		rank_tv_bestdo.setOnClickListener(this);
		rank_tv_business.setOnClickListener(this);
		initDate();
	}

	@SuppressLint("NewApi")
	private void initDate() {
		Intent intent = getIntent();
		uid = intent.getExtras().getString("uid", "");
		bid = intent.getExtras().getString("bid", "");
		bname = intent.getExtras().getString("bname", "");
		company_title = intent.getExtras().getString("company_title", "");
		department_title = intent.getExtras().getString("department_title", "");
		deptId = intent.getExtras().getString("deptId", "");
		ranked_show = intent.getExtras().getString("ranked_show", "");
		step_num = intent.getExtras().getInt("step_num", 0);
		pagetop_tv_name.setText("今日步数排行榜");
		pagetop_tv_name.setTextColor(getResources().getColor(R.color.white));
		pagetop_iv_back.setBackgroundResource(R.drawable.back_moren);
		page_top_layout.setBackgroundColor(getResources().getColor(R.color.blue));
		rank_tv_bestdo.setText(company_title);
		rank_tv_business.setText(department_title);
		if (!TextUtils.isEmpty(ranked_show) && !ranked_show.equals("2")) {
			rank_layout_business.setVisibility(View.GONE);
			rank_tv_bestdoline.setVisibility(View.GONE);
		}
		setClickSelectBtn();
	}

	private void setClickSelectBtn() {
		init();
		if (beatdoSelectStatus) {
			rank_tv_bestdo.setTextColor(getResources().getColor(R.color.blue));
			rank_tv_bestdoline.setBackgroundColor(getResources().getColor(R.color.blue));
			rank_tv_business.setTextColor(getResources().getColor(R.color.text_click_color));
			rank_tv_businessline.setBackgroundColor(getResources().getColor(R.color.white));
		} else {
			rank_tv_bestdo.setTextColor(getResources().getColor(R.color.text_click_color));
			rank_tv_bestdoline.setBackgroundColor(getResources().getColor(R.color.white));
			rank_tv_business.setTextColor(getResources().getColor(R.color.blue));
			rank_tv_businessline.setBackgroundColor(getResources().getColor(R.color.blue));
		}
	}

	@Override
	protected void processLogic() {
		showDilag();
		processLogicDate();
	}

	private int page;
	private int pagesize;
	protected ArrayList<UserWalkingRankInfo> mList;
	protected String company_name;
	protected String dept_name;

	private void init() {
		mBestdoAdapter = null;
		mList = new ArrayList<UserWalkingRankInfo>();
		page = 1;
		pagesize = 100;
	}

	protected void processLogicDate() {
		// uid 用户ID string 是
		// bid 企业ID string 是
		// deptid 部门ID string 是
		// page 当前页数 string 是
		// pagesize 每页条数 string 是

		mHashMap = new HashMap<String, String>();
		mHashMap.put("uid", uid);
		if (bid.equals("nobid")) {
			mHashMap.put("bid", "" + 0);
		} else {
			mHashMap.put("bid", bid);
		}
		if (!beatdoSelectStatus) {
			mHashMap.put("deptid", deptId);
		}
		mHashMap.put("page", "" + page);
		mHashMap.put("pagesize", "" + pagesize);
		System.err.println("--------" + mHashMap);
		new UserWalkingRankBusiness(this, beatdoSelectStatus, mHashMap, mList, new GetUserWalkingRankCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
					} else if (status.equals("407")) {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					} else if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						total = (Integer) dataMap.get("total") + 1;
						company_name = (String) dataMap.get("company_name");
						dept_name = (String) dataMap.get("dept_name");
						UserWalkingRankInfo mUserWalkingRankInfo = (UserWalkingRankInfo) dataMap
								.get("mUserWalkingRankInfo");
						mList = (ArrayList<UserWalkingRankInfo>) dataMap.get("mList");
						if (page * pagesize < total) {
							page++;
						}
						updateList(mUserWalkingRankInfo);

					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				addNotDateImg();
				setloadPageMoreStatus();
				CommonUtils.getInstance().setClearCacheBackDate(mHashMap, dataMap);
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
			}
		});
	}

	private void updateList(UserWalkingRankInfo mUserWalkingRankInfo) {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<UserWalkingRankInfo>();
		}
		ArrayList<UserWalkingRankInfo> mbestdoList = new ArrayList<UserWalkingRankInfo>();
		if (mUserWalkingRankInfo != null) {
			mbestdoList.addAll(mList);
			mbestdoList.add(0, mUserWalkingRankInfo);
		}
		String companyname = "";
		if (beatdoSelectStatus) {
			companyname = company_name;
		} else {
			companyname = dept_name;
		}

		if (mBestdoAdapter == null) {
			mBestdoAdapter = new UserWalkingRankBestdoAdapter(context, mbestdoList, companyname, ranked_show);
			rank_baidong_listdate.setAdapter(mBestdoAdapter);
		} else {
			mBestdoAdapter.setList(mbestdoList);
			mBestdoAdapter.notifyDataSetChanged();
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
		if (mBestdoAdapter == null || (mBestdoAdapter != null && mBestdoAdapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.main_ic_none_venues);
			not_date_cont.setText("暂无相关计步信息");
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
			mPullDownView.onLoadMoreComplete("已无更多信息！", "over");
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
		if (mBestdoAdapter != null && total > mBestdoAdapter.getCount() && (page * pagesize < total)) {
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
					processLogicDate();
				}
				break;
			case LOADMORE:
				System.err.println("LOADMORELOADMORELOADMORELOADMORELOADMORE");
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					// if (!checkDataLoadStatus()) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					page++;
					System.err.println("processLogicprocessLogic");
					processLogicDate();
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
