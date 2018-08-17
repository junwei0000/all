package com.bestdo.bestdoStadiums.control.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.CampaignListBusiness;
import com.bestdo.bestdoStadiums.business.CampaignListBusiness.GetCampaignListCallback;
import com.bestdo.bestdoStadiums.control.activity.CampaignDetailActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignManagementActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignManagerDetailActivity;
import com.bestdo.bestdoStadiums.control.activity.UserLoginBack403Utils;
import com.bestdo.bestdoStadiums.control.adapter.CampaignListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CampaignManagerListAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
 * @Description 类描述：活动管理-我组织的
 */
public class CampaignManagerzuzhiFragment extends Fragment implements OnRefreshListioner {
	private CampaignManagementActivity mContext;
	private ListView listview;
	private LinearLayout not_date;
	private PullDownListView mPullDownView;

	private ArrayList<CampaignListInfo> mList;
	private int page;
	private int pagesize;
	private Handler mHandler = new Handler();
	private int total;
	private ProgressDialog mDialog;
	private String uid;

	public CampaignManagerzuzhiFragment() {
		super();
	}

	public CampaignManagerzuzhiFragment(Activity mContext, String uid) {
		super();
		this.mContext = (CampaignManagementActivity) mContext;
		this.uid = uid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.campaign_manager_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		listview = (ListView) getView().findViewById(R.id.lv_listview);
		not_date = (LinearLayout) getView().findViewById(R.id.not_date);
		mPullDownView = (PullDownListView) getView().findViewById(R.id.refreshable_view);
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setLoadingtishiString("活动正在加载中");
		mPullDownView.setOrderBottomMoreLine("list");
		moretitle = getResources().getString(R.string.seen_more);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mPullDownView.initBg(mContext, mAdapter);
				if (mList != null && position <= mList.size()) {
					String act_status = mList.get(position - 1).getAct_status();
					if (act_status.equals("0") || act_status.equals("3") || act_status.equals("1")) {
						// 0进行中1已结束2取消3报名中
						// 进行中才跳转
						String activity_id = mList.get(position - 1).getActivity_id();
						Intent intent = new Intent(mContext, CampaignManagerDetailActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("uid", uid);
						intent.putExtra("mCampaignListInfo", mList.get(position - 1));
						mContext.startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, mContext);
					} else if (act_status.equals("2")) {
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

	/**
	 * Fragment的懒加载 当切换到这个fragment的时候，它才去初始化 ------------取消预加载---------------
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			if (mContext == null) {
				this.mContext = (CampaignManagementActivity) Constans.getInstance().mCampaignManagementActivity;
			}
			showDilag();
			init();
			getData();
		} else {
			// 不可见时不执行操作
		}
	}

	private void init() {
		mList = new ArrayList<CampaignListInfo>();
		page = 0;
		page++;
		pagesize = 20;
	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog2(mContext);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, String> mhashmap;

	private Boolean isAttached = false;
	private CampaignManagerListAdapter mAdapter;
	private String moretitle;

	private void getData() {
		if (isAttached && mContext != null && mContext.selectArg0 == Integer
				.valueOf(mContext.getResources().getString(R.string.order_index_all))) {

			if (!ConfigUtils.getInstance().isNetWorkAvaiable(mContext)) {
				CommonUtils.getInstance().initToast(mContext, getString(R.string.net_tishi));
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				return;
			}
			if (!Constans.getInstance().refreshOrLoadMoreLoading) {
				showDilag();
			}
			mhashmap = new HashMap<String, String>();
			mhashmap.put("uid", uid);
			// // 1、我参加的活动 2、我组织的活动 3、待报名的活动 4、签到活动列表
			mhashmap.put("type", "2");
			mhashmap.put("page", "" + page);
			mhashmap.put("pagesize", "" + pagesize);
			new CampaignListBusiness(mContext, mList, mhashmap, new GetCampaignListCallback() {
				public void afterDataGet(HashMap<String, Object> dataMap) {
					page--;
					if (dataMap != null) {
						String status = (String) dataMap.get("code");
						if (status.equals("403")) {
							UserLoginBack403Utils.getInstance().showDialogPromptReLogin(mContext);
						} else if (status.equals("200")) {
							mPullDownView.setVisibility(View.VISIBLE);
							mList = (ArrayList<CampaignListInfo>) dataMap.get("mList");
							total = (Integer) dataMap.get("total");
							if (page * pagesize < total) {
								page++;
							}
							updateList();
						} else {
							String data = (String) dataMap.get("msg");
							CommonUtils.getInstance().initToast(mContext, data);
						}
					} else {
						CommonUtils.getInstance().initToast(mContext, getString(R.string.net_tishi));
					}
					setloadPageMoreStatus();
					CommonUtils.getInstance().setOnDismissDialog(mDialog);
					CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
				}
			});

		} else {
			CommonUtils.getInstance().setOnDismissDialog(mDialog);
		}
	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<CampaignListInfo>();
		}
		if (mAdapter == null) {
			mAdapter = new CampaignManagerListAdapter(mContext, mList, "zuzhi");
			listview.setAdapter(mAdapter);
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
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (mAdapter == null || (mAdapter != null && mAdapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) getView().findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) getView().findViewById(R.id.not_date_cont);
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
			mPullDownView.onLoadMoreComplete(moretitle, "");// 这里表示加载更多处理完成后把下面的加载更多界面（隐藏或者设置字样更多）
			mPullDownView.setMore(true);// 这里设置true表示还有更多加载，设置为false底部将不显示更多
		} else {
			mPullDownView.onLoadMoreComplete("已无更多活动信息了！", "over");
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
					getData();
				}
				break;
			case LOADMORE:
				if (!Constans.getInstance().refreshOrLoadMoreLoading) {
					// if (!checkDataLoadStatus()) {
					Constans.getInstance().refreshOrLoadMoreLoading = true;
					page++;
					getData();
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

	/**
	 * 包含的 Fragment 中统计页面：
	 */
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("UserOrderScreen"); // 统计页面
		if (mAdapter != null)
			mPullDownViewHandler.sendEmptyMessage(REFLESH);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("UserOrderScreen");
	}
}
