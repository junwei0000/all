package com.zh.education.control.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Text;
import com.zh.education.R;
import com.zh.education.business.BoKeBusiness;
import com.zh.education.business.BoKeBusiness.BoKeCallback;
import com.zh.education.control.activity.BoKeDetailActivity;
import com.zh.education.control.activity.UserCenterActivity;
import com.zh.education.control.activity.UserLoginActivity;
import com.zh.education.control.adapter.BoKeAdapter;
import com.zh.education.control.adapter.NoticesAdapter;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.Constans;
import com.zh.education.utils.ProgressDialogUtils;
import com.zh.education.utils.PullDownListView;
import com.zh.education.utils.PullDownListView.OnRefreshListioner;
import com.zh.education.utils.parser.BoKeParser;
import com.zh.education.model.BoKeInfo;
import com.zh.education.model.NoticesInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BokeFragment extends Fragment implements OnRefreshListioner {
	private final static String TAG = "BokeFragment";
	Activity activity;
	String text;
	int channel_id;
	ImageView detail_loading;
	public final static int SET_NEWSLIST = 0;
	// Toast提示框
	private RelativeLayout notify_view;
	private TextView notify_view_text;
	private ListView bokeListView;
	private ArrayList<BoKeInfo> mList;
	private int page, total;// 页数
	private int pagesize;// 页大小
	private PullDownListView refreshable_view;
	private BoKeAdapter boKeAdapter;

	public BokeFragment() {
		super();
	}

	public BokeFragment(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		channel_id = args != null ? args.getInt("id", 0) : 0;
		Log.e("channel_id", channel_id+"博客");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_boke, null);
		bokeListView = (ListView) view.findViewById(R.id.boke_listview);
		refreshable_view = (PullDownListView) view
				.findViewById(R.id.boke_refreshable_view);
		refreshable_view.setRefreshListioner(this);
		refreshable_view.setOrderBottomMoreLine("order");
		bokeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (mList != null && mList.size() != 0
						&& mList.size() > arg2 - 1) {

					Intent intent_boKeDetail = new Intent(activity,
							BoKeDetailActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("boKeInfo", mList.get(arg2 - 1));
					intent_boKeDetail.putExtras(bundle);
					intent_boKeDetail.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent_boKeDetail);
					activity.overridePendingTransition(R.anim.slide_in_right,
							R.anim.slide_out_left);
				}

			}
		});
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		isAttached = true;
	}

	/** 此方法意思为fragment是否可见 ,可见时候加载数据 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			// fragment可见时加载数据
			init();
			getData();
		} else {
			// fragment不可见时不执行操作

		}

		super.setUserVisibleHint(isVisibleToUser);
	}

	private void init() {
		mList = new ArrayList<BoKeInfo>();
		page = 0;
		page++;
		pagesize = 10;
	}

	private Boolean isAttached = false;

	private void getData() {
		// "schoolUrl": ××××
		// "tokenStr": ××××
		// "page": ××××
		// "pageSize": ××××
		// 显示进度条
		ProgressDialogUtils.showProgressDialog(activity, "数据加载中...","");
		SharedPreferences zhedu_spf = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(activity);

		final HashMap<String, String> mhashmap = new HashMap<String, String>();
		mhashmap.put("blogUrl", zhedu_spf.getString("BlogUrl", ""));
		mhashmap.put("tokenStr", zhedu_spf.getString("tokenStr", ""));
		mhashmap.put("page", page + "");
		mhashmap.put("pageSize", pagesize + "");

		new BoKeBusiness(activity, mhashmap, mList, new BoKeCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				page--;
				ProgressDialogUtils.dismissProgressDialog("");
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						page++;
						total = (Integer) dataMap.get("total");
						mList = (ArrayList<BoKeInfo>) dataMap.get("mList");
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
			mList = new ArrayList<BoKeInfo>();
		}
		boKeAdapter = new BoKeAdapter(mList, activity);
		bokeListView.setAdapter(boKeAdapter);
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
		if (boKeAdapter != null && total > boKeAdapter.getCount()
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

	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.d("onDestroyView", "channel_id = " + channel_id);
		// mAdapter = null;
	}

	/* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "channel_id = " + channel_id);
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

}
