package com.bestdo.bestdoStadiums.control.fragment;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.control.activity.ClubActivity;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date
 * @Description 类描述：精彩时刻
 */
public class ClubWonderfulFragment extends Fragment {
	private ClubActivity mContext;

	private ProgressDialog mDialog;

	private ImageView club_wonderful_img;

	private TextView club_wonderful_text;

	public ClubWonderfulFragment() {
		super();
	}

	public ClubWonderfulFragment(Activity mContext) {
		super();
		this.mContext = (ClubActivity) mContext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.club_wonderful_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		club_wonderful_img = (ImageView) getView().findViewById(R.id.club_wonderful_img);
		club_wonderful_text = (TextView) getView().findViewById(R.id.club_wonderful_text);
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
