package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.business.VenuesHobbyBusiness;
import com.KiwiSports.business.VenuesHobbyBusiness.GetVenuesHobbyCallback;
import com.KiwiSports.control.adapter.VenuesHobbyAdapter;
import com.KiwiSports.model.HobbyInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
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
public class UserAccountHobbyActivity extends BaseActivity {

	private ListView mListView;
	private HashMap<String, String> mhashmap;
	private LinearLayout pagetop_layout_back;
	private String hobby;
	private HashMap<String, String> selecthobbyMap;
	private UpdateInfoUtils mUpdateInfoUtils;

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
		setContentView(R.layout.user_account_update_hobby);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		TextView pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText(getString(R.string.sporttypelike));
		mListView = (ListView) findViewById(R.id.list_date);

	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		mUpdateInfoUtils = new UpdateInfoUtils(this);
		hobby = getIntent().getStringExtra("hobby");
		selecthobbyMap = new HashMap<String, String>();
		if (!TextUtils.isEmpty(hobby)) {
			String[] ss = hobby.split(",");
			for (int i = 0; i < ss.length; i++) {
				selecthobbyMap.put(ss[i], ss[i]);
			}
		}

		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (mList != null & mList.size() > 0 && arg2 < mList.size()) {
					String Ehobby = mList.get(arg2).getEhobby();
					if (selecthobbyMap.containsKey(Ehobby)) {
						selecthobbyMap.remove(Ehobby);
					} else {
						selecthobbyMap.put(Ehobby, Ehobby);
					}
					if (selecthobbyMap.size() > 0) {
						StringBuffer showhobby = new StringBuffer();
						for (String key : selecthobbyMap.keySet()) {
							showhobby.append(key + ",");
						}
						hobby = showhobby.substring(0, showhobby.length() - 1);
					} else {
						hobby = "";
					}
					Log.e("TESTLOG", "------------showhobby------------"
							+ hobby);
					adapter.setSelecthobbyMap(selecthobbyMap);
					adapter.notifyDataSetChanged();
				}
			}
		});
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

	private final int UPDATE = 1;
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE:
				mUpdateInfoUtils.UpdateInfo("hobby", hobby);
				break;
			}
		}
	};
	protected VenuesHobbyAdapter adapter;

	@Override
	protected void processLogic() {
		if (!Constants.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		mhashmap = new HashMap<String, String>();
		new VenuesHobbyBusiness(this, mhashmap, new GetVenuesHobbyCallback() {
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
						adapter = new VenuesHobbyAdapter(context,
								selecthobbyMap, mList);
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

	private void doBack() {
		mHandler.sendEmptyMessage(UPDATE);
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
