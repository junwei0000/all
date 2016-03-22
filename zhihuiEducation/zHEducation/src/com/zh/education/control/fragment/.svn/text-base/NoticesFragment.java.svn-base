package com.zh.education.control.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.Text;

import com.zh.education.R;
import com.zh.education.business.NoticesBusiness;
import com.zh.education.business.NoticesBusiness.GetNoticesCallback;
import com.zh.education.control.activity.BoKeDetailActivity;
import com.zh.education.control.activity.NoticesDetailActivity;
import com.zh.education.control.activity.UserLoginActivity;
import com.zh.education.control.adapter.NoticesAdapter;
import com.zh.education.model.NoticesInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.Constans;
import com.zh.education.utils.DatesUtils;
import com.zh.education.utils.ProgressDialogUtils;
import com.zh.education.utils.PullDownListView;
import com.zh.education.utils.PullDownListView.OnRefreshListioner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-24 下午1:44:17
 * @Description 类描述：消息列表view
 */
public class NoticesFragment extends Fragment implements OnClickListener,
		OnRefreshListioner {
	private final static String TAG = "XiaoxiFragment";
	private LinearLayout notices_layout_noticeType;
	private TextView notices_tv_school;
	private TextView notices_tv_class;
	private PullDownListView refreshable_view;
	private ListView listview;

	private Activity activity;
	private String text;
	private int channel_id;
	public final static int SET_NEWSLIST = 0;

	private SharedPreferences zhedu_spf;
	private HashMap<String, String> mhashmap;
	private ArrayList<NoticesInfo> mList;
	private int page;
	private int pagesize;
	private int total;
	private String noticeType = "school";

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.notices_tv_school:
			noticeType = "school";
			setChangeNoticeType(noticeType);
			break;
		case R.id.notices_tv_class:
			noticeType = "class";
			setChangeNoticeType(noticeType);
			break;
		default:
			break;
		}
	}

	public NoticesFragment() {
		super();
	}

	public NoticesFragment(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		text = args != null ? args.getString("text") : "";
		channel_id = args != null ? args.getInt("id", 0) : 0;
		Log.e("channel_id", args.getInt("id", 0)+"消息");

	}

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_notices, null);
		notices_layout_noticeType = (LinearLayout) view
				.findViewById(R.id.notices_layout_noticeType);
		notices_tv_school = (TextView) view
				.findViewById(R.id.notices_tv_school);
		notices_tv_class = (TextView) view.findViewById(R.id.notices_tv_class);
		refreshable_view = (PullDownListView) view
				.findViewById(R.id.refreshable_view);
		listview = (ListView) view.findViewById(R.id.listview);
		notices_tv_school.setOnClickListener(this);
		notices_tv_class.setOnClickListener(this);
		refreshable_view.setRefreshListioner(this);
		refreshable_view.setOrderBottomMoreLine("order");
		listview.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("NewApi")
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mList != null && mList.size() != 0
						&& mList.size() > position - 1) {

					SharedPreferences zhedu_spf = CommonUtils.getInstance()
							.getTextSizeSharedPrefs(activity);
					Set<String> set = zhedu_spf.getStringSet("noticeSet",
							new HashSet<String>());
					Iterator<String> it = set.iterator();
					//判断是否存入
					boolean haveAdd=false;
					String keyString=mList.get(position-1).getId()+"";
					while (it.hasNext()) {
						String str = it.next();
						if (str.contains(keyString)) {
							haveAdd=true;
							break;
						}
					}
					if(!haveAdd){
						set.add(keyString);
						Editor editor = zhedu_spf.edit();
						editor.putStringSet("noticeSet", set);
						editor.commit();
					}
					mNoticesAdapter.notifyDataSetChanged();
					
					
					
					Intent intent = new Intent(activity,
							NoticesDetailActivity.class);
					intent.putExtra("title", mList.get(position - 1).getTitle());
					intent.putExtra("time", mList.get(position - 1)
							.getCreateTime());
					intent.putExtra("content", mList.get(position - 1)
							.getContent());
					intent.putExtra("noticetype", mList.get(position - 1)
							.getNoticeType());
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
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
		setUserVisibleHint(true);
	}

	/**
	 * 
	 * @param noticeType
	 */
	private void setChangeNoticeType(String noticeType) {
		if (noticeType.equals("school")) {
			notices_layout_noticeType
					.setBackgroundResource(R.drawable.notices_school_img);
		} else {
			notices_layout_noticeType
					.setBackgroundResource(R.drawable.notices_class_img);
		}
		init();
		getData();
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
		mList = new ArrayList<NoticesInfo>();
		page = 0;
		page++;
		pagesize = 10;
	}

	private Boolean isAttached = false;

	private void getData() {
		if (isAttached) {
			// if (!CommonUtils.getInstance().isNetWorkAvaiable(activity)) {
			// Toast.makeText(activity,activity.getResources().getString(R.string.net_tishi),0).show();
			// return;
			// }
			zhedu_spf = CommonUtils.getInstance().getBestDoInfoSharedPrefs(
					activity);
			ProgressDialogUtils.showProgressDialog(activity, "数据加载中...","");
			mhashmap = new HashMap<String, String>();
			mhashmap.put("schoolUrl", zhedu_spf.getString("schoolUrl", ""));
			mhashmap.put("tokenStr", zhedu_spf.getString("tokenStr", ""));
			mhashmap.put("noticeType", noticeType);// 校级:school/班级:class
			mhashmap.put("page", "" + page);
			mhashmap.put("pageSize", "" + pagesize);
			new NoticesBusiness(activity, mList, mhashmap,
					new GetNoticesCallback() {
						public void afterDataGet(HashMap<String, Object> dataMap) {
							page--;
							ProgressDialogUtils.dismissProgressDialog("");
							if (dataMap != null) {
								String status = (String) dataMap.get("status");
								if (status.equals("200")) {
									total = (Integer) dataMap.get("total");
									page++;
									mList = (ArrayList<NoticesInfo>) dataMap
											.get("mList");
									updateList();
								} else {
									String msg = (String) dataMap.get("msg");
									Toast.makeText(activity, msg, 0).show();
								}
							} else {
								Toast.makeText(
										activity,
										activity.getResources().getString(
												R.string.net_tishi), 0).show();
							}
							setloadPageMoreStatus();
							CommonUtils.getInstance().setClearCacheBackDate(
									mhashmap, dataMap);

						}
					});

		}
	}

	/**
	 * 滑动加载完成后，重置加载的状态
	 */
	private void setloadPageMoreStatus() {
		refreshable_viewHandler.sendEmptyMessage(DATAUPDATEOVER);
	}

	NoticesAdapter mNoticesAdapter;

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<NoticesInfo>();
		}
		mNoticesAdapter = new NoticesAdapter(activity, mList);
		listview.setAdapter(mNoticesAdapter);
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

	private Handler mListHandler = new Handler();

	/**
	 * 刷新，先清空list中数据然后重新加载更新内容
	 */
	public void onRefresh() {
		mListHandler.postDelayed(new Runnable() {
			public void run() {
				refreshable_viewHandler.sendEmptyMessage(REFLESH);
			}
		}, 1500);
	}

	/**
	 * 加载更多，在原来基础上在添加新内容
	 */
	public void onLoadMore() {
		mListHandler.postDelayed(new Runnable() {
			public void run() {
				refreshable_viewHandler.sendEmptyMessage(LOADMORE);
			}
		}, 1500);
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
		if (mNoticesAdapter != null && total > mNoticesAdapter.getCount()
				&& (page * pagesize < total)) {
			loadStatus = false;
		}
		return loadStatus;
	}

	/* 摧毁视图 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d("onDestroyView", "channel_id = " + channel_id);
		// mAdapter = null;
	}

	/* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "channel_id = " + channel_id);
	}

}
