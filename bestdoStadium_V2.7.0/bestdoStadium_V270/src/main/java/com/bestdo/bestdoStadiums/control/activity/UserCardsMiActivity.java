package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.UserCardsBusiness;
import com.bestdo.bestdoStadiums.business.UserCardsBusiness.GetUserCardsCallback;
import com.bestdo.bestdoStadiums.business.UserCardsMiBusiness;
import com.bestdo.bestdoStadiums.business.UserCardsMiBusiness.UserCardsMiCallback;
import com.bestdo.bestdoStadiums.control.activity.UserCardsActivity.listItemClick;
import com.bestdo.bestdoStadiums.control.adapter.UserCardAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserCardMiAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.model.UserCardsMiInfo;
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
import android.text.TextUtils;
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

/**
 * @author qyy 卡密列表页
 */
public class UserCardsMiActivity extends BaseActivity implements OnRefreshListioner {
	private LinearLayout not_date;
	private HashMap<String, String> mhashmap;
	private ArrayList<UserCardsMiInfo> mList;
	private int total;
	private int page;
	private int pagesize;
	private String useNoteUrl;
	private SharedPreferences bestDoInfoSharedPrefs;
	private ProgressDialog mDialog;
	private String uid;
	private TextView not_date_tv_cardinstructions_mi, pagetop_tv_you, pagetop_tv_name;
	private PullDownListView mPullDownView;
	private ListView listview1;
	private LinearLayout pagetop_layout_back;
	private String intent_from = "";

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_tv_you:
			Intent intent = new Intent(UserCardsMiActivity.this, UserCardsActivateActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("intent_from", intent_from);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, UserCardsMiActivity.this);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_cards_mi);
		CommonUtils.getInstance().addActivity(this);
		Intent intent = getIntent();
		intent_from = intent.getStringExtra("intent_from");
		if (!TextUtils.isEmpty(intent_from) && intent_from.equals("CreateOrderSelectCardsActivity")) {
			CommonUtils.getInstance().addBuyCardsList(UserCardsMiActivity.this);
		}
	}

	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText("激活");
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText("卡密包");
		not_date_tv_cardinstructions_mi = (TextView) findViewById(R.id.not_date_tv_cardinstructions_mi);
		mPullDownView = ((PullDownListView) findViewById(R.id.user_cards_mi_refreshable_view));
		listview1 = (ListView) findViewById(R.id.user_card_mi_listview);
		not_date = (LinearLayout) findViewById(R.id.user_card_mi_not_date);
	}

	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setOrderBottomMoreLine("list");
		mPullDownView.setLoadingtishiString("卡密信息正在加载中");
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		init();
	}

	private UserCardMiAdapter mUserCardsListAdapter;

	private void init() {
		mUserCardsListAdapter = null;
		mList = new ArrayList<UserCardsMiInfo>();
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
		System.err.println(mhashmap);
		new UserCardsMiBusiness(this, mhashmap, mList, Constans.USERCARDSMILIST, new UserCardsMiCallback() {
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
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(UserCardsMiActivity.this);
					} else if (status.equals("200")) {
						total = (Integer) dataMap.get("totalRows");
						useNoteUrl = (String) dataMap.get("useNoteUrl");
						mList = (ArrayList<UserCardsMiInfo>) dataMap.get("mList");
						// if (page * pagesize < total) {
						page++;
						// }
						updateList();
						listview1.setOnItemClickListener(new listItemClick());
					}
				} else {
					CommonUtils.getInstance().initToast(UserCardsMiActivity.this, getString(R.string.net_tishi));
				}
				addNotDateImg();
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	class listItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (mList != null && position <= mList.size() && mList.get(position - 1).getCard_status().equals("未激活")) {
				Intent intent = new Intent(UserCardsMiActivity.this, UserCardMiDetailActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("UserCardsMiInfo", mList.get(position - 1));
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, UserCardsMiActivity.this);
			}
		}

	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(UserCardsMiActivity.this);
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
			mList = new ArrayList<UserCardsMiInfo>();
		}
		if (mUserCardsListAdapter == null) {
			mUserCardsListAdapter = new UserCardMiAdapter(UserCardsMiActivity.this, mList);
			listview1.setAdapter(mUserCardsListAdapter);
		} else {
			mUserCardsListAdapter.setList(mList);
			mUserCardsListAdapter.notifyDataSetChanged();
		}
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
			not_date_jihuo_btn.setVisibility(View.GONE);
			not_date_tv_cardinstructions_mi.setVisibility(View.GONE);
		} else {
			not_date.setVisibility(View.GONE);
			not_date_tv_cardinstructions_mi.setVisibility(View.VISIBLE);
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
			mPullDownView.onLoadMoreComplete("无更多卡密信息", "over");
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
