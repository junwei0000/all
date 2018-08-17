package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.UserCardsBusiness;
import com.bestdo.bestdoStadiums.business.UserCardsBusiness.GetUserCardsCallback;
import com.bestdo.bestdoStadiums.control.adapter.UserCardAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
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

public class UserQiYeCardsActivity extends BaseActivity implements OnRefreshListioner {
	private TextView pagetop_tv_name, pagetop_tv_you;
	private LinearLayout pagetop_layout_back;
	private LinearLayout not_date;
	private HashMap<String, String> mhashmap;
	private ArrayList<UserCardsInfo> mList;
	private int total;
	private int page;
	private int pagesize;
	private SharedPreferences bestDoInfoSharedPrefs;
	private ProgressDialog mDialog;
	private String uid;
	private String bid;
	private TextView not_date_tv_cardinstructions;
	private PullDownListView mPullDownView;
	private ListView listview1;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_tv_you:
			Intent intent = new Intent(UserQiYeCardsActivity.this, UserCardsActivateActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, UserQiYeCardsActivity.this);
			break;
		case R.id.not_date_tv_cardinstructions:
			Intent intent2 = new Intent(this, UserCardAbstractActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_qiye_cards);
		CommonUtils.getInstance().addActivity(this);
	}

	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);

		not_date_tv_cardinstructions = (TextView) findViewById(R.id.not_date_tv_cardinstructions);
		mPullDownView = ((PullDownListView) findViewById(R.id.user_cards_refreshable_view));
		listview1 = (ListView) findViewById(R.id.user_card_listview);
		not_date = (LinearLayout) findViewById(R.id.user_card_not_date);
	}

	protected void setListener() {
		pagetop_tv_name.setText("卡券中心");
		pagetop_tv_you.setText("激活");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setOrderBottomMoreLine("list");
		listview1.setOnItemClickListener(new listItemClick());
		not_date_tv_cardinstructions.setOnClickListener(this);

		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		bid = bestDoInfoSharedPrefs.getString("bid", "");
		init();
	}

	private UserCardAdapter mUserCardsListAdapter;

	private void init() {
		mUserCardsListAdapter = null;
		mList = new ArrayList<UserCardsInfo>();
		page = 0;
		page++;
		pagesize = 20;
	}

	protected void processLogic() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		if (page < 1) {
			return;
		}
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("page", "" + page);
		mhashmap.put("page_size", "" + pagesize);
		mhashmap.put("uid", uid);
		mhashmap.put("bid", bid);
		System.err.println(mhashmap);
		String url = Constans.GETCOMPANYCARDS;
		new UserCardsBusiness(this, mhashmap, mList, url, new GetUserCardsCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				firstComeIn = false;
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String data = (String) dataMap.get("data");
						// CommonUtils.getInstance().initToast(
						// UserCardsActivity.this, data);
					} else if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(UserQiYeCardsActivity.this);
					} else if (status.equals("200")) {
						total = (Integer) dataMap.get("totalRows");
						mList = (ArrayList<UserCardsInfo>) dataMap.get("mList");
						if (page * pagesize < total) {
							page++;
						}
						updateList();
					}
				} else {
					CommonUtils.getInstance().initToast(UserQiYeCardsActivity.this, getString(R.string.net_tishi));
				}
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(UserQiYeCardsActivity.this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<UserCardsInfo>();
		}
		if (mUserCardsListAdapter == null) {
			mUserCardsListAdapter = new UserCardAdapter(UserQiYeCardsActivity.this, mList);
			listview1.setAdapter(mUserCardsListAdapter);
		} else {
			mUserCardsListAdapter.setList(mList);
			mUserCardsListAdapter.notifyDataSetChanged();
		}
		addNotDateImg();
	}

	/**
	 ** 
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (mUserCardsListAdapter == null || (mUserCardsListAdapter != null && mUserCardsListAdapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			TextView not_date_cont2 = (TextView) findViewById(R.id.not_date_cont2);
			TextView not_date_jihuo_btn = (TextView) findViewById(R.id.not_date_jihuo_btn);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont2.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_jihuo_btn.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.no_cards_img);
			not_date_cont2.setText("暂无卡券");
			not_date_cont.setText("使用百动卡、立减n元");
			not_date_jihuo_btn.setText("立即前往购买");
			not_date_jihuo_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Intent intent = new Intent(UserCardsActivity.this,
					// UserCardsActivateActivity.class);
					// intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					// startActivity(intent);
					// CommonUtils.getInstance().setPageIntentAnim(intent,
					// UserCardsActivity.this);

					Intent intent = new Intent(UserQiYeCardsActivity.this, UserCardsBuyActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					CommonUtils.getInstance().intntStatus = "UserCenterActivity";
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, UserQiYeCardsActivity.this);

				}
			});
			not_date_tv_cardinstructions.setVisibility(View.INVISIBLE);
		} else {
			not_date.setVisibility(View.GONE);
			not_date_tv_cardinstructions.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return super.onCreateView(name, context, attrs);

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		System.err.println("onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	boolean firstComeIn = true;

	@Override
	protected void onResume() {
		if (!firstComeIn) {
			System.err.println("onResume");
			// init();
			// processLogic();
			mPullDownViewHandler.sendEmptyMessage(REFRESHLOAD);

		}
		super.onResume();
	}

	class listItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (mList != null && position <= mList.size()) {
				String user_card_ticket_id = mList.get(position - 1).getCardId();
				Intent intent = new Intent(UserQiYeCardsActivity.this, UserCardDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("user_card_ticket_id", user_card_ticket_id);
				intent.putExtra("uid", uid);
				intent.putExtra("skipToCardDetailStatus", Constans.getInstance().skipToCardDetailByQiYEPage);
				intent.putExtra("intent_from", "UserCenterActivity");

				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, UserQiYeCardsActivity.this);
			}
		}

	}

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

	/**
	 * 滑动加载完成后，重置加载的状态
	 */
	private void setloadPageMoreStatus() {
		mPullDownViewHandler.sendEmptyMessage(SETBOTTOMLOADMORESHOWSTA);
	}

	/**
	 * 当返回数据小于总数时，不让显示“加载更多”
	 */
	private void nideBottom() {
		if (!checkDataLoadStatus()) {
			mPullDownView.onLoadMoreComplete(getResources().getString(R.string.seen_more), "");
			mPullDownView.setMore(true);
		} else {
			mPullDownView.onLoadMoreComplete("已无更多卡券信息了！", "over");
			mPullDownView.setMore(true);
		}
	}

	/**
	 * 设置加载更多显示状态
	 */
	private final int SETBOTTOMLOADMORESHOWSTA = 0;
	/**
	 * 下拉刷新
	 */
	private final int REFRESHLOAD = 1;
	/**
	 * 加载更多
	 */
	private final int LOADMORE = 2;
	Handler mPullDownViewHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SETBOTTOMLOADMORESHOWSTA:
				mPullDownView.onRefreshComplete();
				nideBottom();
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				break;
			case REFRESHLOAD:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					System.err.println("REFRESHLOADREFRESHLOAD");
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
		if (mUserCardsListAdapter != null && total > mUserCardsListAdapter.getCount() && (page * pagesize < total)) {
			loadStatus = false;
		}
		return loadStatus;
	}

	private Handler mListHandler = new Handler();

	@Override
	public void onRefresh() {
		mListHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(REFRESHLOAD);
			}
		}, 1500);
	}

	@Override
	public void onLoadMore() {
		mListHandler.postDelayed(new Runnable() {
			public void run() {
				mPullDownViewHandler.sendEmptyMessage(LOADMORE);
			}
		}, 1500);
	}

}
