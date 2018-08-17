package com.bestdo.bestdoStadiums.control.fragment;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.control.activity.CampaignBaoMingUsersListActivity;
import com.bestdo.bestdoStadiums.control.activity.ClubActivity;
import com.bestdo.bestdoStadiums.control.activity.ClubUsersListActivity;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-28 下午2:54:57
 * @Description 类描述：全部
 */
public class ClubMainlFragment extends Fragment {
	private ClubActivity mContext;

	private ProgressDialog mDialog;
	private TextView club_main_text_description, club_main_text_member_count;
	private String club_description;
	private String member_count;
	private String club_id;
	private RelativeLayout club_main_member_rel;

	public ClubMainlFragment() {
		super();
	}

	@SuppressLint("ValidFragment")
	public ClubMainlFragment(Activity mContext, String club_description, String member_count, String club_id) {
		super();
		this.mContext = (ClubActivity) mContext;
		this.club_description = club_description;
		this.member_count = member_count;
		this.club_id = club_id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.club_main_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		club_main_text_description = (TextView) getView().findViewById(R.id.club_main_text_description);
		club_main_text_member_count = (TextView) getView().findViewById(R.id.club_main_text_member_count);
		if (mContext == null) {
			this.mContext = (ClubActivity) Constans.getInstance().mClubActivity;
		}
		club_main_member_rel = (RelativeLayout) getView().findViewById(R.id.club_main_member_rel);
		club_main_text_description.setText(club_description);
		club_main_text_member_count.setText(member_count + "人");
		club_main_member_rel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intents = new Intent(mContext, ClubUsersListActivity.class);
				intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intents.putExtra("club_id", club_id);
				intents.putExtra("member_count", member_count);

				startActivity(intents);
				CommonUtils.getInstance().setPageIntentAnim(intents, mContext);
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
			showDilag();
			getData();
		} else {
			// 不可见时不执行操作
		}
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

	private void getData() {
		// if (isAttached&& mContext !=
		// null&&mContext.selectArg0==Integer.valueOf(mContext.getResources().getString(
		// R.string.order_index_all))) {
		// if (!ConfigUtils.getInstance().isNetWorkAvaiable(mContext)) {
		// CommonUtils.getInstance().initToast(mContext,
		// getString(R.string.net_tishi));
		// setloadPageMoreStatus();
		// return;
		// }
		// if (page < 1) {
		// setloadPageMoreStatus();
		// return;
		// }
		// if (!Constans.getInstance().refreshOrLoadMoreLoading) {
		// showDilag();
		// }
		// //
		// http://test.bd.app.bestdo.com/2.0.0/order/list?uid=260151124164836ak2
		// mhashmap = new HashMap<String, String>();
		// mhashmap.put("merid", "" + mContext.getMerid());
		// mhashmap.put("cid", "" + mContext.getCid());
		// mhashmap.put("uid", uid);
		// mhashmap.put("page", "" + page);
		// mhashmap.put("pageSize", "" + pagesize);
		// new UserOrdersBusiness(mContext, mhashmap, orderlist,
		// new GetUserOrdersCallback() {
		//
		// @Override
		// public void afterDataGet(HashMap<String, Object> dataMap) {
		// page--;
		// if (dataMap != null) {
		// String status = (String) dataMap.get("status");
		// if (status.equals("400")) {
		// if (page == 0) {
		// updateList();
		// }
		// } else if (status.equals("407")) {
		// String msg = (String) dataMap.get("data");
		// CommonUtils.getInstance().initToast(
		// mContext, msg);
		// } else if (status.equals("403")) {
		// UserLoginBack403Utils.getInstance()
		// .showDialogPromptReLogin(mContext);
		// } else if (status.equals("200")) {
		// mPullDownView.setVisibility(View.VISIBLE);
		// total = (Integer) dataMap.get("total");
		// orderlist = (ArrayList<UserOrdersInfo>) dataMap
		// .get("mList");
		// if (page * pagesize < total) {
		// page++;
		// }
		// updateList();
		// updateOrderNum(dataMap);
		// }
		// } else {
		// CommonUtils.getInstance().initToast(mContext,
		// getString(R.string.net_tishi));
		// }
		// setloadPageMoreStatus();
		// CommonUtils.getInstance().setOnDismissDialog(
		// mDialog);
		// CommonUtils.getInstance().setClearCacheBackDate(
		// mhashmap, dataMap);
		// }
		// });
		// }else{
		CommonUtils.getInstance().setOnDismissDialog(mDialog);
		// }
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
