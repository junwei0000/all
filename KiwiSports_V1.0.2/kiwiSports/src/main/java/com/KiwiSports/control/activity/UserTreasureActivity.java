package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.UserTreasureBusiness;
import com.KiwiSports.business.UserTreasureBusiness.GetTreasureCallback;
import com.KiwiSports.control.adapter.UserTreasureAdapter;
import com.KiwiSports.model.VenuesTreasInfo;
import com.KiwiSports.utils.CommonUtils;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：宝贝list
 */
public class UserTreasureActivity extends BaseActivity {

	private ListView mListView;
	private HashMap<String, String> mhashmap;
	private String uid;
	private String token;
	private String access_token;
	private LinearLayout pagetop_layout_back;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_treasure);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText(getString(R.string.usrecenter_tv_hongbao));
		mListView = (ListView) findViewById(R.id.list_date);

	}

	@SuppressLint("NewApi")
	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		Intent intent = getIntent();
		uid = intent.getExtras().getString("uid", "");
		token = intent.getExtras().getString("token", "");
		access_token = intent.getExtras().getString("access_token", "");
	}

	private ProgressDialog mDialog;
	protected UserTreasureAdapter adapter;

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

	@Override
	protected void processLogic() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		new UserTreasureBusiness(this, mhashmap, new GetTreasureCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						@SuppressWarnings("unchecked")
						ArrayList<VenuesTreasInfo> mList = (ArrayList<VenuesTreasInfo>) dataMap
								.get("mlist");
						if (mList != null && mList.size() > 0) {
						} else {
							mList = new ArrayList<VenuesTreasInfo>();
						}
						adapter = new UserTreasureAdapter(context, mList);
						mListView.setAdapter(adapter);
						addNotDateImg();
					}
				} else {
					CommonUtils.getInstance().initToast(
							getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
						dataMap);

			}
		});

	}

	/**
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		LinearLayout not_date = (LinearLayout) findViewById(R.id.not_date);
		if (adapter == null || (adapter != null && adapter.getCount() == 0)) {
			not_date.setVisibility(View.VISIBLE);
		} else {
			not_date.setVisibility(View.GONE);
		}
	}

	private void doBack() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}
}
