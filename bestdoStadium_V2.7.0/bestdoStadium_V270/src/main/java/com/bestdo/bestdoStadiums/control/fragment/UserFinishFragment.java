package com.bestdo.bestdoStadiums.control.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.business.UserOrdersBusiness;
import com.bestdo.bestdoStadiums.business.UserOrdersBusiness.GetUserOrdersCallback;
import com.bestdo.bestdoStadiums.control.activity.HomeActivity;
import com.bestdo.bestdoStadiums.control.activity.UserLoginBack403Utils;
import com.bestdo.bestdoStadiums.control.activity.UserOrderActivity;
import com.bestdo.bestdoStadiums.control.activity.UserOrderDetailsActivity;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderAdapter;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-28 下午2:55:38
 * @Description 类描述：已结束
 */
public class UserFinishFragment extends Fragment implements OnRefreshListioner {
	private UserOrderActivity mContext;
	private ListView listview;
	private LinearLayout not_date;
	private PullDownListView mPullDownView;

	private ArrayList<UserOrdersInfo> orderlist;
	private UserOrderAdapter adapter;
	private int page;
	private int pagesize;
	private Handler mHandler = new Handler();
	private int total;
	private ProgressDialog mDialog;
	private String ordrStatuas;
	private String uid;

	public UserFinishFragment() {
		super();
	}
	@SuppressLint("ValidFragment")
	public UserFinishFragment(Activity mContext, String uid) {
		super();
		this.uid = uid;
		this.mContext = (UserOrderActivity) mContext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.user_order_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		listview = (ListView) getView().findViewById(R.id.lv_listview);
		not_date = (LinearLayout) getView().findViewById(R.id.not_date);
		mPullDownView = (PullDownListView) getView().findViewById(R.id.refreshable_view);
		mPullDownView.setRefreshListioner(this);
		mPullDownView.setLoadingtishiString("订单信息正在加载中");
		mPullDownView.setOrderBottomMoreLine("list");
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mPullDownView.initBg(mContext, adapter);
				if (orderlist != null && position <= orderlist.size()) {
					String oid = orderlist.get(position - 1).getOid();
					Intent intent2 = new Intent(mContext, UserOrderDetailsActivity.class);
					intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent2.putExtra("oid", oid);
					startActivity(intent2);
					CommonUtils.getInstance().setPageIntentAnim(intent2, mContext);
				}
			}
		});
	}

	public void updateUserVisibleHint() {
		setUserVisibleHint(true);
	}

	/**
	 * Fragment的懒加载 当切换到这个fragment的时候，它才去初始化
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			if (mContext == null) {
				this.mContext = (UserOrderActivity) Constans.getInstance().mCampaingActivity;
			}
			showDilag();
			init();
			getData();
		} else {
			// 不可见时不执行操作
		}
	}

	private void init() {
		orderlist = new ArrayList<UserOrdersInfo>();
		page = 0;
		page++;
		pagesize = 20;
		/**
		 * 防止每次进来开的倒计时都会后台计时-------------重新加载时关闭所有-------------------
		 */
		CommonUtils.getInstance().exitTimer();
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

	@Override
	public void onAttach(Activity activity) {
		isAttached = true;
		super.onAttach(activity);
	}

	private Boolean isAttached = false;

	private void getData() {
		if (isAttached && mContext != null && mContext.selectArg0 == Integer
				.valueOf(mContext.getResources().getString(R.string.order_index_canceled))) {
			if (!ConfigUtils.getInstance().isNetWorkAvaiable(mContext)) {
				CommonUtils.getInstance().initToast(mContext, getString(R.string.net_tishi));
				setloadPageMoreStatus();
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				return;
			}
			if (page < 1) {
				setloadPageMoreStatus();
				return;
			}
			if (!Constans.getInstance().refreshOrLoadMoreLoading) {
				showDilag();
			}
			// http://test.bd.app.bestdo.com/2.0.0/order/list?uid=260151124164836ak2
			mhashmap = new HashMap<String, String>();
			ordrStatuas = mContext.getResources().getString(R.string.order_putParameters_finished);
			mhashmap.put("status", ordrStatuas);
			mhashmap.put("merid", "" + mContext.getMerid());
			mhashmap.put("cid", "" + mContext.getCid());
			mhashmap.put("uid", uid);
			mhashmap.put("page", "" + page);
			mhashmap.put("pageSize", "" + pagesize);
			new UserOrdersBusiness(mContext, mhashmap, orderlist, new GetUserOrdersCallback() {
				@Override
				public void afterDataGet(HashMap<String, Object> dataMap) {
					page--;
					if (dataMap != null) {
						String status = (String) dataMap.get("status");
						if (status.equals("400")) {
							if (page == 0) {
								updateList();
							}
						} else if (status.equals("407")) {
							String msg = (String) dataMap.get("data");
							CommonUtils.getInstance().initToast(mContext, msg);
						} else if (status.equals("403")) {
							UserLoginBack403Utils.getInstance().showDialogPromptReLogin(mContext);
						} else if (status.equals("200")) {
							mPullDownView.setVisibility(View.VISIBLE);
							total = (Integer) dataMap.get("total");
							orderlist = (ArrayList<UserOrdersInfo>) dataMap.get("mList");
							if (page * pagesize < total) {
								page++;
							}
							updateList();
							updateOrderNum(dataMap);
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

	private void updateOrderNum(HashMap<String, Object> dataMap) {
		UserOrdersInfo mOrdersNumInfo = (UserOrdersInfo) dataMap.get("mOrdersNumInfo");
		Message msg = new Message();
		msg.obj = mOrdersNumInfo;
		msg.what = mContext.UPDATEORDERNUM;
		mContext.mUpdateOrderNumHandler.sendMessage(msg);
		msg = null;
	}

	private void updateList() {
		if (orderlist != null && orderlist.size() != 0) {
		} else {
			orderlist = new ArrayList<UserOrdersInfo>();
		}

		adapter = new UserOrderAdapter(mContext, orderlist, null, 0, ordrStatuas);
		listview.setAdapter(adapter);
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
		if (adapter == null || (adapter != null && adapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) getView().findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) getView().findViewById(R.id.not_date_cont);
			TextView not_date_jihuo_btn = (TextView) getView().findViewById(R.id.not_date_jihuo_btn);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_jihuo_btn.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.main_ic_none_orderlist);
			not_date_cont.setText("暂无订单信息");
			not_date_jihuo_btn.setText("立即去下单");
			not_date_jihuo_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CommonUtils.getInstance().skipMainActivity(mContext);
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
			mPullDownView.onLoadMoreComplete(getResources().getString(R.string.seen_more), "");// 这里表示加载更多处理完成后把下面的加载更多界面（隐藏或者设置字样更多）
			mPullDownView.setMore(true);// 这里设置true表示还有更多加载，设置为false底部将不显示更多
		} else {
			mPullDownView.onLoadMoreComplete("已无更多订单信息了！", "over");
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
		if (adapter != null && total > adapter.getCount() && (page * pagesize < total)) {
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
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("UserOrderScreen");
	}
}
