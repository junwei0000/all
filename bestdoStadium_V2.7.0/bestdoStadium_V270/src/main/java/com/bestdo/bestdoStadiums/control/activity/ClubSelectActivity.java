/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.ClubGetManageBusiness;
import com.bestdo.bestdoStadiums.business.ClubGetManageBusiness.ClubGetManageCallback;
import com.bestdo.bestdoStadiums.business.LodeCampaignQuartDateBusiness;
import com.bestdo.bestdoStadiums.business.LodeCampaignQuartDateBusiness.LodeCampaignQuartCallback;
import com.bestdo.bestdoStadiums.control.adapter.ClubSelectAdapter;
import com.bestdo.bestdoStadiums.control.photo.util.Bimp;
import com.bestdo.bestdoStadiums.model.ClubMenuInfo;
import com.bestdo.bestdoStadiums.model.ClubOperateInfo;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2017-3-13 下午3:07:06
 * @Description 类描述：运动时刻-选择俱乐部
 */
public class ClubSelectActivity extends BaseActivity {

	private SharedPreferences bestDoInfoSharedPrefs;
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView tv_num;
	private ListView list_date;
	private HashMap<String, String> mhashmap;
	private TextView pagetop_tv_you;
	private String clubidBuffer;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.pagetop_layout_back:
			doBack();
			break;
		case R.id.pagetop_tv_you:
			String clubid_Buffer = "";
			String clubname_Buffer = "";
			if (clubList != null && clubList.size() > 0) {
				for (int i = 0; i < clubList.size(); i++) {
					boolean check = clubList.get(i).isCheck();
					if (check) {
						clubid_Buffer += (clubList.get(i).getClub_id() + ",");
						clubname_Buffer += (clubList.get(i).getClub_name() + ",");
					}
				}
			}
			if (!TextUtils.isEmpty(clubid_Buffer)) {
				clubid_Buffer = clubid_Buffer.substring(0, clubid_Buffer.length() - 1);
			}
			if (!TextUtils.isEmpty(clubname_Buffer)) {
				clubname_Buffer = clubname_Buffer.substring(0, clubname_Buffer.length() - 1);
			}
			System.err.println(clubid_Buffer + "    " + clubname_Buffer);
			Intent intent = new Intent();
			intent.putExtra("clubid_Buffer", clubid_Buffer + "");
			intent.putExtra("clubname_Buffer", clubname_Buffer + "");
			setResult(Constans.getInstance().searchForResultBySearchKWPage, intent);
			doBack();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.clubselect);
		CommonUtils.getInstance().addActivity(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		tv_num = (TextView) findViewById(R.id.tv_num);
		list_date = (ListView) findViewById(R.id.list_date);

	}

	@Override
	protected void setListener() {
		pagetop_tv_name.setText("选择相关俱乐部");
		pagetop_tv_you.setText("完成");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		Intent intent = getIntent();
		clubidBuffer = intent.getStringExtra("clubid_Buffer");
		list_date.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (clubList != null && clubList.size() > 0) {
					boolean check = clubList.get(arg2).isCheck();
					if (check) {
						clubList.get(arg2).setCheck(false);
						clubList.get(arg2).getChoosedbt().setBackgroundResource(R.drawable.check_false);
					} else {
						clubList.get(arg2).setCheck(true);
						clubList.get(arg2).getChoosedbt().setBackgroundResource(R.drawable.check_true);
					}
				}

			}
		});
	}

	private ProgressDialog mDialog;
	protected ArrayList<MyJoinClubmenuInfo> clubList;

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog2(this);
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
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		new ClubGetManageBusiness(this, mhashmap, new ClubGetManageCallback() {

			private String status;

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					status = (String) dataMap.get("code");
					if (status.equals("200")) {
						clubList = (ArrayList<MyJoinClubmenuInfo>) dataMap.get("clubList");
						if (clubList != null && clubList.size() > 0) {
							tv_num.setText("我管理的俱乐部" + "(" + clubList.size() + ")");
							ClubSelectAdapter mClubSelectAdapter = new ClubSelectAdapter(context, clubList,
									clubidBuffer);
							list_date.setAdapter(mClubSelectAdapter);
						}

					}
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
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
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doBack();
		}
		return false;
	}
}
