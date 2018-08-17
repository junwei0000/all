package com.bestdo.bestdoStadiums.control.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.ClubEventsBusiness;
import com.bestdo.bestdoStadiums.business.ClubEventsBusiness.ClubEventsListCallback;
import com.bestdo.bestdoStadiums.business.UserOrdersBusiness;
import com.bestdo.bestdoStadiums.business.UserOrdersBusiness.GetUserOrdersCallback;
import com.bestdo.bestdoStadiums.control.activity.CampaignDetailActivity;
import com.bestdo.bestdoStadiums.control.activity.ClubActivity;
import com.bestdo.bestdoStadiums.control.activity.UserLoginBack403Utils;
import com.bestdo.bestdoStadiums.control.activity.UserOrderActivity;
import com.bestdo.bestdoStadiums.control.activity.UserOrderDetailsActivity;
import com.bestdo.bestdoStadiums.control.adapter.CampaignManagerListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.ClubEventsAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.ClubEventsInfo;
import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-28 下午2:54:57
 * @Description 类描述：活动
 */
public class ClubEventsFragment extends Fragment implements OnRefreshListioner {
	private ClubActivity mContext;
	private ListView listview;
	private LinearLayout not_date;

	private ArrayList<CampaignListInfo> orderlist;
	private CampaignManagerListAdapter adapter;
	private Handler mHandler = new Handler();
	private ProgressDialog mDialog;
	private String uid;
	private String club_id = "";

	private String clubname;

	public ClubEventsFragment() {
		super();
	}

	public ClubEventsFragment(Activity mContext, String club_id, String clubname) {
		super();
		this.mContext = (ClubActivity) mContext;
		this.club_id = club_id;
		this.clubname = clubname;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.club_events_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		listview = (ListView) getView().findViewById(R.id.club_events_list);
		if (mContext == null) {
			this.mContext = (ClubActivity) Constans.getInstance().mClubActivity;
		}
		not_date = (LinearLayout) getView().findViewById(R.id.not_date);
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(mContext);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int arg2, long id) {
				if (orderlist != null && arg2 < orderlist.size()) {
					String act_status = orderlist.get(arg2).getAct_status();
					if (act_status.equals("0") || act_status.equals("3")) {
						String activity_id = orderlist.get(arg2).getActivity_id();
						Intent intent = new Intent(mContext, CampaignDetailActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("uid", uid);
						intent.putExtra("activity_id", activity_id);
						mContext.startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, mContext);
					} else if (orderlist.get(arg2).getAct_status().equals("1")) {
						CommonUtils.getInstance().initToast(mContext, "活动已结束");
					} else if (orderlist.get(arg2).getAct_status().equals("2")) {
						CommonUtils.getInstance().initToast(mContext, "活动已取消");
					}
				}
			}
		});
	}

	@Override
	public void onAttach(Activity activity) {
		isAttached = true;
		super.onAttach(activity);
	}

	public void updateUserVisibleHint() {
		setUserVisibleHint(true);
	}

	/**
	 * Fragment的懒加载 当切换到这个fragment的时候，它才去初始化 ------------取消预加载---------------
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			if (mContext == null) {
				this.mContext = (ClubActivity) Constans.getInstance().mClubActivity;
			}
			getData();
		} else {
			// 不可见时不执行操作
		}
	}

	private HashMap<String, String> mhashmap;

	private Boolean isAttached = false;

	private void getData() {
		if (isAttached && mContext != null && mContext.selectArg0 == 1) {
			if (!ConfigUtils.getInstance().isNetWorkAvaiable(mContext)) {
				CommonUtils.getInstance().initToast(mContext, getString(R.string.net_tishi));
			}
			// http://test.bd.app.bestdo.com/2.0.0/order/list?uid=260151124164836ak2
			mhashmap = new HashMap<String, String>();
			mhashmap.put("club_id", club_id);
			new ClubEventsBusiness(mContext, mhashmap, new ClubEventsListCallback() {
				@Override
				public void afterDataGet(HashMap<String, Object> dataMap) {
					if (dataMap != null) {
						String status = (String) dataMap.get("code");
						if (status.equals("400")) {
							CommonUtils.getInstance().setOnDismissDialog(mDialog);
						} else if (status.equals("200")) {
							orderlist = (ArrayList<CampaignListInfo>) dataMap.get("mList");
							updateList();
						}
					}
				}
			});
		} else {
		}
	}

	private void updateList() {
		if (orderlist != null && orderlist.size() != 0) {
			for (CampaignListInfo iterable_element : orderlist) {
				iterable_element.setClub_name(clubname);
			}
		} else {
			orderlist = new ArrayList<CampaignListInfo>();
		}
		adapter = new CampaignManagerListAdapter(mContext, orderlist, "club");
		listview.setAdapter(adapter);
		addNotDateImg();
	}

	/**
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (adapter == null || (adapter != null && adapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) getView().findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) getView().findViewById(R.id.not_date_cont);
			TextView not_date_jihuo_btn = (TextView) getView().findViewById(R.id.not_date_jihuo_btn);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.not_date);
			not_date_cont.setText("暂无数据");
			not_date_jihuo_btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					CommonUtils.getInstance().skipMainActivity(mContext);
				}
			});
		} else {
			not_date.setVisibility(View.GONE);
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
	/**
	 * 更新view
	 */
	private final int UPDATEVIEW = 3;
	Handler mPullDownViewHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DATAUPDATEOVER:
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				break;
			case REFLESH:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					getData();
				}
				break;
			case LOADMORE:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					// if (!checkDataLoadStatus()) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					getData();
					// }
				}
				break;
			case UPDATEVIEW:
				if (adapter != null) {
					adapter.notifyDataSetChanged();
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

	/**
	 * 包含的 Fragment 中统计页面：
	 */
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("UserOrderScreen"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("UserOrderScreen");
	}
}
