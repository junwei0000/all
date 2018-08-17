/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignDetailBusiness;
import com.bestdo.bestdoStadiums.business.CampaignDetailBusiness.CampaignDetailCallback;
import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-30 下午5:02:48
 * @Description 类描述：活动管理-详情
 */
public class CampaignManagerDetailActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView campaignmanagerdetail_tv_baomingnum;
	private TextView campaignmanagerdetail_tv_baominglv;
	private TextView campaignmanagerdetail_tv_name;
	private TextView campaignmanagerdetail_tv_starttime;
	private TextView campaignmanagerdetail_tv_endtime;
	private TextView campaignmanagerdetail_tv_address;
	private TextView campaignmanagerdetail_tv_rule;
	private Button campaignmanagerdetail_btn_detail;
	private Button campaignmanagerdetail_btn_edit;
	private Button campaignmanagerdetail_btn_cancel;
	private CampaignListInfo mCampaignListInfo;
	private String uid;
	private HashMap<String, String> mhashmap;

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.campaignmanagerdetail_btn_detail:
			intent = new Intent(context, CampaignDetailActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("uid", uid);
			intent.putExtra("activity_id", mCampaignListInfo.getActivity_id());
			context.startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, context);
			break;
		case R.id.campaignmanagerdetail_btn_edit:
			intent = new Intent(context, CampaignPublishActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("skipFrom", "edit");
			intent.putExtra("mCampaignListInfo", mCampaignListInfo);
			context.startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, context);
			break;
		case R.id.campaignmanagerdetail_btn_cancel:
			intent = new Intent(context, CampaignCancelActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("activity_id", mCampaignListInfo.getActivity_id());
			context.startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, context);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.campaign_manager_detail);
		CommonUtils.getInstance().addActivity(this);
		CommonUtils.getInstance().addPayPageActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		campaignmanagerdetail_tv_baomingnum = (TextView) findViewById(R.id.campaignmanagerdetail_tv_baomingnum);
		campaignmanagerdetail_tv_baominglv = (TextView) findViewById(R.id.campaignmanagerdetail_tv_baominglv);
		campaignmanagerdetail_tv_name = (TextView) findViewById(R.id.campaignmanagerdetail_tv_name);
		campaignmanagerdetail_tv_starttime = (TextView) findViewById(R.id.campaignmanagerdetail_tv_starttime);
		campaignmanagerdetail_tv_endtime = (TextView) findViewById(R.id.campaignmanagerdetail_tv_endtime);
		campaignmanagerdetail_tv_address = (TextView) findViewById(R.id.campaignmanagerdetail_tv_address);
		campaignmanagerdetail_tv_rule = (TextView) findViewById(R.id.campaignmanagerdetail_tv_rule);

		campaignmanagerdetail_btn_detail = (Button) findViewById(R.id.campaignmanagerdetail_btn_detail);
		campaignmanagerdetail_btn_edit = (Button) findViewById(R.id.campaignmanagerdetail_btn_edit);
		campaignmanagerdetail_btn_cancel = (Button) findViewById(R.id.campaignmanagerdetail_btn_cancel);

	}

	@Override
	protected void setListener() {
		pagetop_tv_name.setText("活动管理");
		pagetop_layout_back.setOnClickListener(this);
		campaignmanagerdetail_btn_detail.setOnClickListener(this);
		campaignmanagerdetail_btn_edit.setOnClickListener(this);
		campaignmanagerdetail_btn_cancel.setOnClickListener(this);

		Intent intent = getIntent();
		uid = intent.getStringExtra("uid");
		mCampaignListInfo = (CampaignListInfo) intent.getSerializableExtra("mCampaignListInfo");
		if (mCampaignListInfo != null) {
			campaignmanagerdetail_tv_name.setText(mCampaignListInfo.getName());
			int starttime = mCampaignListInfo.getStart_time();
			String starttime_ = DatesUtils.getInstance().getTimeStampToDate(starttime, "yyyy年MM月dd日  HH:mm");
			campaignmanagerdetail_tv_starttime.setText(starttime_);
			int endtime = mCampaignListInfo.getEnd_time();
			String endtime_ = DatesUtils.getInstance().getTimeStampToDate(endtime, "yyyy年MM月dd日  HH:mm");
			campaignmanagerdetail_tv_endtime.setText(endtime_);
			campaignmanagerdetail_tv_address.setText(mCampaignListInfo.getSitus());
			campaignmanagerdetail_tv_rule.setText(mCampaignListInfo.getRule());

			String act_status = mCampaignListInfo.getAct_status();
			if (act_status.equals("1")) {
				// 已结束
				campaignmanagerdetail_btn_detail.setVisibility(View.GONE);
				campaignmanagerdetail_btn_cancel.setVisibility(View.GONE);
				campaignmanagerdetail_btn_edit.setVisibility(View.GONE);
			} else {
				String is_edit = mCampaignListInfo.getIs_edit();
				if (is_edit.equals("0")) {
					campaignmanagerdetail_btn_cancel.setVisibility(View.GONE);
					campaignmanagerdetail_btn_edit.setVisibility(View.GONE);
				} else {
					campaignmanagerdetail_btn_cancel.setVisibility(View.VISIBLE);
					campaignmanagerdetail_btn_edit.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	@Override
	protected void processLogic() {

		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("activity_id", mCampaignListInfo.getActivity_id());
		new CampaignDetailBusiness(context, mhashmap, new CampaignDetailCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						CampaignDetailInfo mCampaignDetailInfo = (CampaignDetailInfo) dataMap
								.get("mCampaignDetailInfo");
						campaignmanagerdetail_tv_baomingnum.setText(mCampaignDetailInfo.getJoin_num());
						campaignmanagerdetail_tv_baominglv.setText(mCampaignDetailInfo.getJoin_rate());
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg + "");
					}

				}
				firstComeIn = false;
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});

	}

	boolean firstComeIn = true;

	@Override
	protected void onResume() {
		super.onResume();
		if (!firstComeIn)
			processLogic();
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
