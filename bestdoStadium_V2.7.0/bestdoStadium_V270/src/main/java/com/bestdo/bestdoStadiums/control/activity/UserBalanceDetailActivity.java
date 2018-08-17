package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CreateOrdersBuyBalanceBusiness;
import com.bestdo.bestdoStadiums.business.CreateOrdersBuyBalanceBusiness.GetCreateOrdersCallback;
import com.bestdo.bestdoStadiums.business.UserBalanceDetailBusiness;
import com.bestdo.bestdoStadiums.business.UserBalanceDetailBusiness.GetDataCallback;
import com.bestdo.bestdoStadiums.business.UserBalanceListBusiness;
import com.bestdo.bestdoStadiums.business.UserBalanceListBusiness.BalanceListCallback;
import com.bestdo.bestdoStadiums.control.adapter.UserBalanceDetailAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserBalanceListAdapter;
import com.bestdo.bestdoStadiums.model.UserBalanceDetailInfo;
import com.bestdo.bestdoStadiums.model.UserBalanceListInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.utils.PayDialog;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UserBalanceDetailActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ListView balance_myListView;
	private SharedPreferences bestDoInfoSharedPrefs;
	private HashMap<String, String> mhashmap;
	private String uid;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_balance_detail);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		balance_myListView = (ListView) findViewById(R.id.balance_myListView);
	}

	@Override
	protected void setListener() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_name.setText("余额明细");
	}

	private ProgressDialog mDialog;
	protected ArrayList<UserBalanceDetailInfo> mList;

	@Override
	protected void processLogic() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		showDilag();
		new UserBalanceDetailBusiness(this, mhashmap, new GetDataCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						String consumeTotal = (String) dataMap.get("consumeTotal");
						mList = (ArrayList<UserBalanceDetailInfo>) dataMap.get("mList");
						if (mList != null && mList.size() > 0) {
							mList.add(0, new UserBalanceDetailInfo());
							UserBalanceDetailAdapter mUserBalanceListAdapter = new UserBalanceDetailAdapter(context,consumeTotal, mList);
							balance_myListView.setAdapter(mUserBalanceListAdapter);
						}

					}
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			
			}
		});
	}


	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doback() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(context);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doback();
		}
		return false;
	}
}
