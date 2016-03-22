package com.zh.education.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import com.zh.education.R;
import com.zh.education.business.BoKeCommentsBusiness;
import com.zh.education.control.adapter.BoKeAdapter;
import com.zh.education.control.adapter.BoKeCommentsAdapter;
import com.zh.education.model.BoKeInfo;
import com.zh.education.model.PingLunInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.Constans;
import com.zh.education.utils.ProgressDialogUtils;
import com.zh.education.utils.PullDownListView;
import com.zh.education.utils.PullDownListView.OnRefreshListioner;
import com.zh.education.business.BoKeCommentsBusiness.PingLunCallback;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author qyy 评论列表页
 */
public class BoKeCommentsActivity extends BaseActivity implements
		OnRefreshListioner {
	private LinearLayout top_layout_back;
	private TextView top_tv_name;
	private int page, total;// 页数
	private int pagesize;// 页大小
	private PullDownListView refreshable_view;
	private ListView pinglunListView;
	private ArrayList<PingLunInfo> mList;
	private Activity activity;
	private BoKeCommentsAdapter commentsAdapter;
	private BoKeInfo boKeInfo;
	private LinearLayout comments_pinglun_layout;
	private BroadcastReceiver broadcastReceiver;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.top_layout_back:
			onBackPressed();
			break;
		case R.id.comments_pinglun_layout:
			if(!CommonUtils.getInstance().LoginTokenReLog(this)){
			intentToSendComments();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.fragment_boke_comments);
		CommonUtils.getInstance().addActivity(this);
		Intent in = getIntent();
		boKeInfo = (BoKeInfo) in.getSerializableExtra("boKeInfo");
		activity = BoKeCommentsActivity.this;
		broadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				if (arg1.getAction().equals("commentsRefresh")) {
					init();
					getData();
				}
			}
		};
		IntentFilter filter = new IntentFilter("commentsRefresh");
		registerReceiver(broadcastReceiver, filter);
	}

	@Override
	protected void findViewById() {
		top_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		top_tv_name = (TextView) findViewById(R.id.top_tv_name);
		top_tv_name.setText("全部评论");
		pinglunListView = (ListView) findViewById(R.id.comments_listview);
		refreshable_view = (PullDownListView) findViewById(R.id.boke_comments_refreshable_view);
		refreshable_view.setRefreshListioner(this);
		refreshable_view.setOrderBottomMoreLine("order");
		comments_pinglun_layout = (LinearLayout) findViewById(R.id.comments_pinglun_layout);
	}

	@Override
	protected void setListener() {
		top_layout_back.setOnClickListener(this);
		comments_pinglun_layout.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		init();
		getData();
	}

	private void init() {
		mList = new ArrayList<PingLunInfo>();
		page = 0;
		page++;
		pagesize = 10;
	}

	private void getData() {
		// "schoolUrl": ××××
		// "tokenStr": ××××
		// "postID": ××××
		// "page": ××××
		// "pageSize":

		// 显示进度条
		ProgressDialogUtils.showProgressDialog(activity, "数据加载中...","");
		SharedPreferences zhedu_spf = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(activity);

		final HashMap<String, String> mhashmap = new HashMap<String, String>();
		mhashmap.put("blogUrl", zhedu_spf.getString("BlogUrl", ""));
		mhashmap.put("tokenStr", zhedu_spf.getString("tokenStr", ""));
		mhashmap.put("postID", boKeInfo.getId() + "");
		mhashmap.put("page", page + "");
		mhashmap.put("pageSize", pagesize + "");

		new BoKeCommentsBusiness(this, mhashmap, mList, new PingLunCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				ProgressDialogUtils.dismissProgressDialog("");
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						page++;
						total = (Integer) dataMap.get("total");
						mList = (ArrayList<PingLunInfo>) dataMap.get("mList");
						updateList();
					} else {
						Toast.makeText(activity, (String) dataMap.get("msg"),
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(activity, "网络不稳，请稍后重试！", Toast.LENGTH_SHORT)
							.show();
				}
				setloadPageMoreStatus();
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
						dataMap);
			}
		});

		// pinglunListView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		//
		// if (mList != null && mList.size() != 0
		// && mList.size() > arg2-1) {
		//
		// Intent intent_boKeDetail = new Intent(activity,
		// BoKeDetailActivity.class);
		// Bundle bundle = new Bundle();
		// bundle.putSerializable("boKeInfo", mList.get(arg2-1));
		// intent_boKeDetail.putExtras(bundle);
		// intent_boKeDetail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		// startActivity(intent_boKeDetail);
		// activity.overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);
		// }
		//
		// }
		// });
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
	 * 滑动加载完成后，重置加载的状态
	 */
	private void setloadPageMoreStatus() {
		refreshable_viewHandler.sendEmptyMessage(DATAUPDATEOVER);
	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<PingLunInfo>();
		}

		if (commentsAdapter == null) {
			commentsAdapter = new BoKeCommentsAdapter(mList, activity);
			pinglunListView.setAdapter(commentsAdapter);
		} else {
			commentsAdapter.setList(mList);
			commentsAdapter.notifyDataSetChanged();
			if (page == 1) {
				pinglunListView.setSelection(0);
			}
		}

	}

	Handler refreshable_viewHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DATAUPDATEOVER:
				refreshable_view.onRefreshComplete();
				nideBottom();
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				break;
			case REFLESH:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					init();
					getData();
				}
				break;
			case LOADMORE:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					if (!checkDataLoadStatus()) {
						Constans.getInstance().refreshOrLoadMoreLoading = true;
						page++;
						getData();
					}
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
		if (commentsAdapter != null && total > commentsAdapter.getCount()
				&& (page * pagesize < total)) {
			loadStatus = false;
		}
		return loadStatus;
	}

	/**
	 * 当返回数据小于总数时，不让显示“加载更多”
	 */
	private void nideBottom() {
		if (!checkDataLoadStatus()) {
			refreshable_view.onLoadMoreComplete(
					getResources().getString(R.string.seen_more), "");
			refreshable_view.setMore(true);
		} else {
			refreshable_view.onLoadMoreComplete(
					getResources().getString(R.string.seen_more), "over");
			refreshable_view.setMore(false);
		}
	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	private void intentToSendComments() {
		Intent intent_boSendComments = new Intent(activity,
				BoKeSendCommentsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("boKeInfo", boKeInfo);
		intent_boSendComments.putExtras(bundle);
		intent_boSendComments.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent_boSendComments);
		activity.overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}

	private Handler mListHandler = new Handler();

	@Override
	public void onRefresh() {
		mListHandler.postDelayed(new Runnable() {
			public void run() {
				refreshable_viewHandler.sendEmptyMessage(REFLESH);
			}
		}, 1500);
	}

	@Override
	public void onLoadMore() {
		mListHandler.postDelayed(new Runnable() {
			public void run() {
				refreshable_viewHandler.sendEmptyMessage(LOADMORE);
			}
		}, 1500);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}
}
