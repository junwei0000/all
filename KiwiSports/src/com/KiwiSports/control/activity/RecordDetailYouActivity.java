package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.RecordDetailYouBusiness;
import com.KiwiSports.business.RecordDetailYouBusiness.GetRecordDetailYouCallback;
import com.KiwiSports.business.RecordListBusiness;
import com.KiwiSports.business.RecordListBusiness.GetRecordListCallback;
import com.KiwiSports.control.adapter.RecordListAdapter;
import com.KiwiSports.control.adapter.VenuesListAdapter;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.PullDownListView;
import com.KiwiSports.utils.PullDownListView.OnRefreshListioner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
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
 * 
 * @author Administrator 运动记录
 */
public class RecordDetailYouActivity extends BaseActivity {

	private ListView mListView;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String token;
	private String access_token;
	private String posid;
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
		setContentView(R.layout.record_detailyou);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText(getString(R.string.record_title));
		mListView = (ListView) findViewById(R.id.list_date);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");
		Intent intent = getIntent();
		posid = intent.getExtras().getString("posid", "");
	}

	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	protected ArrayList<RecordInfo> mList;
	private RecordListAdapter adapter;

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
		mhashmap.put("posid", posid + "");
		Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
		new RecordDetailYouBusiness(this, mhashmap, new GetRecordDetailYouCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mList = (ArrayList<RecordInfo>) dataMap.get("mlist");
						if (mList != null && mList.size() != 0) {
						} else {
							mList = new ArrayList<RecordInfo>();
						}
						boolean lopCointStatus = true;
						adapter = new RecordListAdapter(context, mList, lopCointStatus);
						mListView.setAdapter(adapter);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}

					addNotDateImg();
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

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

	@Override
	protected void onResume() {
		super.onResume();
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
