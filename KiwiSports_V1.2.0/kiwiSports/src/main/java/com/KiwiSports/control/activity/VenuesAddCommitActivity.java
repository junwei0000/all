package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.VenuesAddBusiness;
import com.KiwiSports.business.VenuesTypeBusiness;
import com.KiwiSports.business.VenuesTypeBusiness.GetVenuesTypeCallback;
import com.KiwiSports.control.adapter.VenuesTypeAdapter;
import com.KiwiSports.model.HobbyInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constants;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：
 */
public class VenuesAddCommitActivity extends BaseActivity {

	private ListView mListView;
	private HashMap<String, String> mhashmap;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String token;
	private String access_token;
	private LinearLayout pagetop_layout_back;
	private String sportsType="";
	private TextView tv_commit;

	private String field_name;
	private String address;
	private String top_left_x;
	private String top_left_y;
	private String bottom_right_x;
	private String bottom_right_y;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
			case R.id.tv_commit:
				if (!TextUtils.isEmpty(sportsType)) {
					processUpdateInfo();
				} else {
					CommonUtils.getInstance().initToast(
							getString(R.string.venues_add_typetishi));
				}
				break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.venues_type);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText(getString(R.string.venues_add_type));
		mListView = (ListView) findViewById(R.id.list_date);
		tv_commit= (TextView) findViewById(R.id.tv_commit);
	}

	@SuppressLint("NewApi")
	@Override
	protected void setListener() {
		tv_commit.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		init();
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (mList != null & mList.size() > 0 && arg2 < mList.size()) {
					sportsType = mList.get(arg2).getEhobby();
					adapter.setSelect(sportsType);
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	private void init(){
		bestDoInfoSharedPrefs = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		token = bestDoInfoSharedPrefs.getString("token", "");
		access_token = bestDoInfoSharedPrefs.getString("access_token", "");

		Intent intent = getIntent();
		field_name = intent.getExtras().getString("field_name", "");
		address = intent.getExtras().getString("address", "");
		top_left_x = intent.getExtras().getString("top_left_x", "");
		top_left_y = intent.getExtras().getString("top_left_y", "");
		bottom_right_x = intent.getExtras().getString("bottom_right_x", "");
		bottom_right_y = intent.getExtras().getString("bottom_right_y", "");
	}
	private ProgressDialog mDialog;
	protected ArrayList<HobbyInfo> mList;

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

	protected VenuesTypeAdapter adapter;

	@Override
	protected void processLogic() {
		if (!Constants.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		new VenuesTypeBusiness(this, mhashmap, new GetVenuesTypeCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						mList = (ArrayList<HobbyInfo>) dataMap.get("mList");
						if (mList != null && mList.size() > 0) {
						} else {
							mList = new ArrayList<HobbyInfo>();
						}
						adapter = new VenuesTypeAdapter(context, sportsType,
								mList);
						mListView.setAdapter(adapter);
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


	private void processUpdateInfo() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("token", token);
		mhashmap.put("access_token", access_token);
		mhashmap.put("field_name", field_name);
		mhashmap.put("sportsType", sportsType);
		mhashmap.put("address", address);

		mhashmap.put("top_left_x", top_left_x + "");
		mhashmap.put("top_left_y", top_left_y + "");
		mhashmap.put("bottom_right_x", bottom_right_x + "");
		mhashmap.put("bottom_right_y", bottom_right_y + "");
		Log.e("decrypt----", mhashmap.toString());
		new VenuesAddBusiness(this, mhashmap, new VenuesAddBusiness.GetVenuesAddCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(msg);
					if (status.equals("200")) {
						Intent intent = new Intent(context, MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
						doBack();
					}
				} else {
					CommonUtils.getInstance().initToast(
							getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
						dataMap);

			}
		});
	}
	private void doBack() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}
	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}
}
