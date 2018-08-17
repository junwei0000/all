/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.MessageBusiness;
import com.bestdo.bestdoStadiums.business.MessageBusiness.MessageCallback;
import com.bestdo.bestdoStadiums.control.adapter.MessageAdapter;
import com.bestdo.bestdoStadiums.model.MessageInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.umeng.analytics.MobclickAgent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-2 上午10:51:16
 * @Description 类描述：消息首页列表API
 */
public class MessageActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private LinearLayout not_date;
	private ListView lv_date;
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
		setContentView(R.layout.message);
		CommonUtils.getInstance().addActivity(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		lv_date = (ListView) findViewById(R.id.list_date);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		lv_date.setOnItemClickListener(itemListener);
		pagetop_tv_name.setText("消息中心");
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
	}

	private OnItemClickListener itemListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (mList != null && arg2 < mList.size()) {
				String type = mList.get(arg2).getType();
				Intent intent = new Intent(context, MessageListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("uid", uid);
				intent.putExtra("type", type);
				context.startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, context);
			}
		}
	};
	private ProgressDialog mDialog;

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

	HashMap<String, String> mhashmap;
	protected ArrayList<MessageInfo> mList;
	private MessageAdapter mAdapter;
	private SharedPreferences bestDoInfoSharedPrefs;

	@Override
	protected void processLogic() {

		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("crop_id", bestDoInfoSharedPrefs.getString("corp_id", ""));
		new MessageBusiness(this, mhashmap, new MessageCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("403")) {
						UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
					} else if (status.equals("200")) {
						mList = (ArrayList<MessageInfo>) dataMap.get("mList");
						updateList();
					} else {
						String data = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, data);
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				firstComeIn = false;
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	private void updateList() {
		if (mList != null && mList.size() != 0) {
		} else {
			mList = new ArrayList<MessageInfo>();
		}
		if (mAdapter == null) {
			mAdapter = new MessageAdapter(context, mList);
			lv_date.setAdapter(mAdapter);
		} else {
			mAdapter.setList(mList);
			mAdapter.notifyDataSetChanged();
		}
		addNotDateImg();
	}

	/**
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (mAdapter == null || (mAdapter != null && mAdapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.not_date);
			not_date_cont.setText("暂无信息");
		} else {
			not_date.setVisibility(View.GONE);
		}
	}

	boolean firstComeIn = true;

	@Override
	protected void onResume() {
		Log.e("onResume-----", "onResumeonResumeonResumeonResume-----");
		super.onResume();
		MobclickAgent.onPageStart("MainScreen");
		if (!firstComeIn)
			processLogic();
	}

	private void doback() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
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
