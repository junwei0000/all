package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.UserCardsBuyBusiness.GetUserCardsCallback;
import com.bestdo.bestdoStadiums.business.UserCardsBuyBusiness;
import com.bestdo.bestdoStadiums.control.adapter.UserCardAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserCardBuyAdapter;
import com.bestdo.bestdoStadiums.model.UserCardsBuyInfo;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-5-5 下午4:45:23
 * @Description 类描述：购买卡
 */
public class UserCardsBuyActivity extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back;
	private MyListView usercard_listview;

	private HashMap<String, String> mhashmap;
	private ArrayList<UserCardsBuyInfo> mList;
	private ProgressDialog mDialog;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_cardsbuy);
		CommonUtils.getInstance().addActivity(this);
		CommonUtils.getInstance().addPayPageActivity(this);
	}

	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		usercard_listview = (MyListView) findViewById(R.id.usercard_listview);
		usercard_listview.setFocusable(false);
	}

	protected void setListener() {
		pagetop_tv_name.setText("购买百动卡");
		pagetop_layout_back.setOnClickListener(this);
		init();
	}

	private void init() {
		mList = new ArrayList<UserCardsBuyInfo>();
	}

	protected void processLogic() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		new UserCardsBuyBusiness(this, mhashmap, mList, new GetUserCardsCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("400")) {
						String data = (String) dataMap.get("data");
						// CommonUtils.getInstance().initToast(
						// UserCardsActivity.this, data);
					} else if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(UserCardsBuyActivity.this);
					} else if (status.equals("200")) {
						mList = (ArrayList<UserCardsBuyInfo>) dataMap.get("mList");
						if (mList != null && mList.size() != 0) {
						} else {
							mList = new ArrayList<UserCardsBuyInfo>();
						}
						System.err.println(mList.size());
						UserCardBuyAdapter mUserCardsListAdapter = new UserCardBuyAdapter(UserCardsBuyActivity.this,
								mList);
						usercard_listview.setAdapter(mUserCardsListAdapter);
					}
				} else {
					CommonUtils.getInstance().initToast(UserCardsBuyActivity.this, getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(UserCardsBuyActivity.this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
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

}
