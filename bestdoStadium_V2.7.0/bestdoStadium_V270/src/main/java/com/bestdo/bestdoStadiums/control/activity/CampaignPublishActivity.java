/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CampaignGetClubBusiness;
import com.bestdo.bestdoStadiums.business.CampaignGetClubBusiness.CampaignGetClubCallback;
import com.bestdo.bestdoStadiums.business.CampaignPublishBusiness;
import com.bestdo.bestdoStadiums.business.CampaignPublishBusiness.CampaignPublishCallback;
import com.bestdo.bestdoStadiums.control.adapter.CampaignClubAdapter;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.utils.TimeUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 上午11:30:28
 * @Description 类描述：活动发布
 */
public class CampaignPublishActivity extends BaseActivity {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView pagetop_tv_you;
	private SupplierEditText campaignpublish_et_title;
	private EditText campaignpublish_et_titlestated;
	private RelativeLayout campaignpublish_relat_club;
	private TextView campaignpublish_tv_club;
	private RelativeLayout campaignpublish_relat_address;
	private TextView campaignpublish_tv_address;
	private RelativeLayout campaignpublish_relat_starttime;
	private TextView campaignpublish_tv_starttime;
	private RelativeLayout campaignpublish_relat_endtime;
	private TextView campaignpublish_tv_endtime;
	private EditText campaignpublish_et_quota;
	private Button campaignpublish_btn_activities;
	private RelativeLayout campaignpublish_relat_setauto;
	private TextView campaignpublish_tv_setauto;
	private HashMap<String, String> mhashmap;
	private SharedPreferences bestDoInfoSharedPrefs;
	protected ArrayList<CampaignGetClubInfo> mClubList;
	private MyDialog tClubDialog;
	private CampaignClubAdapter mCampaignClubAdapter;
	private MyDialog tSetAutoDialog;
	private CampaignClubAdapter mtSetAutoDialogAdapter;
	protected ArrayList<CampaignGetClubInfo> mSetAutoList;

	protected String club_id;
	protected String advance_time = "3";
	/**
	 * 1固定活动 0普通活动
	 */
	private int is_fixed = 0;
	private String uid;
	private LinearLayout campaignpublish_layout_bottom;
	private String skipFrom;
	private String activity_id;
	private String address_;
	private String starttime_;
	private String endtime_;
	private ImageView campaignpublish_iv_club;

	@Override
	public void onClick(View v) {
		CommonUtils.getInstance().closeSoftInput(context);
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			if (!skipFrom.equals("edit")) {
				doback();
			} else {
				cancelEditDialog();
			}

			break;
		case R.id.pagetop_tv_you:
			if (check()) {
				if (!skipFrom.equals("edit")) {
					clickBtn();
				} else {
					if (idEditStatusAdress) {
						saveEditDialog();
					} else {
						clickBtn();
					}
				}
			}
			break;
		case R.id.campaignpublish_relat_club:
			if (mClubList != null && mClubList.size() > 0) {
				if (tClubDialog != null && !tClubDialog.isShowing()) {
					tClubDialog.show();
					mCampaignClubAdapter.setaList(mClubList);
				} else {
					initClubPopuptWindow();
				}
			} else {
				processLogic();
			}
			break;
		case R.id.campaignpublish_relat_address:
			Intent intent = new Intent(context, CampaignPublishAddessActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivityForResult(intent, 1);
			CommonUtils.getInstance().setPageIntentAnim(intent, context);
			break;
		case R.id.campaignpublish_relat_starttime:
			if (!TextUtils.isEmpty(skipFrom) && skipFrom.equals("edit")) {
				Intent intent2 = getIntent();
				CampaignListInfo mCampaignListInfo = (CampaignListInfo) intent2
						.getSerializableExtra("mCampaignListInfo");
				if (mCampaignListInfo != null) {
					int starttime_int = mCampaignListInfo.getStart_time();
					String yyy = DatesUtils.getInstance().getTimeStampToDate(starttime_int, "yyyy");
					String MM = DatesUtils.getInstance().getTimeStampToDate(starttime_int, "MM");
					String dd = DatesUtils.getInstance().getTimeStampToDate(starttime_int, "dd");
					String hh = DatesUtils.getInstance().getTimeStampToDate(starttime_int, "HH");
					String mm = DatesUtils.getInstance().getTimeStampToDate(starttime_int, "mm");

					initSelectDates(yyy, MM, dd, hh, mm);
				}
			}
			mTimeUtils.setInit(mHandler, STARTTIME);
			mTimeUtils.getDateOrTeetimeDialog();
			break;
		case R.id.campaignpublish_relat_endtime:
			if (!TextUtils.isEmpty(skipFrom) && skipFrom.equals("edit")) {
				Intent intent2 = getIntent();
				CampaignListInfo mCampaignListInfo = (CampaignListInfo) intent2
						.getSerializableExtra("mCampaignListInfo");
				if (mCampaignListInfo != null) {
					int endtime_int = mCampaignListInfo.getEnd_time();
					String yyy = DatesUtils.getInstance().getTimeStampToDate(endtime_int, "yyyy");
					String MM = DatesUtils.getInstance().getTimeStampToDate(endtime_int, "MM");
					String dd = DatesUtils.getInstance().getTimeStampToDate(endtime_int, "dd");
					String hh = DatesUtils.getInstance().getTimeStampToDate(endtime_int, "HH");
					String mm = DatesUtils.getInstance().getTimeStampToDate(endtime_int, "mm");
					initSelectDates(yyy, MM, dd, hh, mm);
				}
			}
			mTimeUtils.setInit(mHandler, ENDTIME);
			mTimeUtils.getDateOrTeetimeDialog();
			break;
		case R.id.campaignpublish_btn_activities:
			if (is_fixed == 1) {
				// 设置为普通活动
				is_fixed = 0;
				campaignpublish_btn_activities.setBackgroundResource(R.drawable.img_off);
			} else {
				is_fixed = 1;
				campaignpublish_btn_activities.setBackgroundResource(R.drawable.img_on);
			}
			break;
		case R.id.campaignpublish_relat_setauto:
			if (mSetAutoList != null) {
				if (tSetAutoDialog != null && !tSetAutoDialog.isShowing()) {
					tSetAutoDialog.show();
					mtSetAutoDialogAdapter.setaList(mSetAutoList);
				} else {
					initSetAutoPopuptWindow();
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.campaign_publish);
		CommonUtils.getInstance().addActivity(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(context);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);

		campaignpublish_et_title = (SupplierEditText) findViewById(R.id.campaignpublish_et_title);
		campaignpublish_et_titlestated = (EditText) findViewById(R.id.campaignpublish_et_titlestated);

		campaignpublish_relat_club = (RelativeLayout) findViewById(R.id.campaignpublish_relat_club);
		campaignpublish_tv_club = (TextView) findViewById(R.id.campaignpublish_tv_club);
		campaignpublish_iv_club = (ImageView) findViewById(R.id.campaignpublish_iv_club);

		campaignpublish_relat_address = (RelativeLayout) findViewById(R.id.campaignpublish_relat_address);
		campaignpublish_tv_address = (TextView) findViewById(R.id.campaignpublish_tv_address);

		campaignpublish_relat_starttime = (RelativeLayout) findViewById(R.id.campaignpublish_relat_starttime);
		campaignpublish_tv_starttime = (TextView) findViewById(R.id.campaignpublish_tv_starttime);

		campaignpublish_relat_endtime = (RelativeLayout) findViewById(R.id.campaignpublish_relat_endtime);
		campaignpublish_tv_endtime = (TextView) findViewById(R.id.campaignpublish_tv_endtime);

		campaignpublish_et_quota = (EditText) findViewById(R.id.campaignpublish_et_quota);

		campaignpublish_btn_activities = (Button) findViewById(R.id.campaignpublish_btn_activities);
		campaignpublish_layout_bottom = (LinearLayout) findViewById(R.id.campaignpublish_layout_bottom);
		campaignpublish_relat_setauto = (RelativeLayout) findViewById(R.id.campaignpublish_relat_setauto);
		campaignpublish_tv_setauto = (TextView) findViewById(R.id.campaignpublish_tv_setauto);
	}

	@Override
	protected void setListener() {
		pagetop_tv_name.setText("活动发布");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setText("发布");
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		campaignpublish_relat_address.setOnClickListener(this);
		campaignpublish_relat_starttime.setOnClickListener(this);
		campaignpublish_relat_endtime.setOnClickListener(this);
		campaignpublish_btn_activities.setOnClickListener(this);
		campaignpublish_relat_setauto.setOnClickListener(this);
		mSetAutoList = new ArrayList<CampaignGetClubInfo>();
		mSetAutoList.add(new CampaignGetClubInfo("2", "提前 2 天"));
		mSetAutoList.add(new CampaignGetClubInfo("3", "提前 3 天"));
		mSetAutoList.add(new CampaignGetClubInfo("4", "提前 4 天"));
		initDates();
		setEditView();
	}

	private void setEditView() {
		Intent intent = getIntent();
		skipFrom = intent.getStringExtra("skipFrom");
		if (!TextUtils.isEmpty(skipFrom) && skipFrom.equals("edit")) {
			campaignpublish_iv_club.setVisibility(View.INVISIBLE);
			pagetop_tv_name.setText("活动编辑");
			pagetop_tv_you.setText("完成");
			CampaignListInfo mCampaignListInfo = (CampaignListInfo) intent.getSerializableExtra("mCampaignListInfo");
			if (mCampaignListInfo != null) {
				activity_id = mCampaignListInfo.getActivity_id();
				title = mCampaignListInfo.getName();
				campaignpublish_et_title.setText(title);
				titlestated = mCampaignListInfo.getRule();
				campaignpublish_et_titlestated.setText(titlestated);
				club_id = mCampaignListInfo.getClub_id();
				campaignpublish_tv_club.setText(mCampaignListInfo.getClub_name());
				address = mCampaignListInfo.getSitus();
				latitude = mCampaignListInfo.getLatitude();
				longitude = mCampaignListInfo.getLongitude();
				campaignpublish_tv_address.setText(address);
				int starttime_int = mCampaignListInfo.getStart_time();
				starttime = DatesUtils.getInstance().getTimeStampToDate(starttime_int, "yyyy-MM-dd  HH:mm");
				int endtime_int = mCampaignListInfo.getEnd_time();
				endtime = DatesUtils.getInstance().getTimeStampToDate(endtime_int, "yyyy-MM-dd  HH:mm");
				address_ = address;
				starttime_ = starttime;
				endtime_ = endtime;
				campaignpublish_tv_starttime.setText(starttime);
				campaignpublish_tv_endtime.setText(endtime);
				minge = mCampaignListInfo.getMax_peo();
				if (minge.equals("0")) {
					campaignpublish_et_quota.setText("");
				} else {
					campaignpublish_et_quota.setText(minge);
				}
				campaignpublish_layout_bottom.setVisibility(View.GONE);
			}

		} else {
			campaignpublish_relat_club.setOnClickListener(this);
		}
	}

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

	@Override
	protected void processLogic() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		new CampaignGetClubBusiness(context, mhashmap, new CampaignGetClubCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						mClubList = (ArrayList<CampaignGetClubInfo>) dataMap.get("mClubList");
						if (mClubList != null && mClubList.size() == 1) {
							campaignpublish_tv_club.setText(mClubList.get(0).getClub_name());
							club_id = mClubList.get(0).getClub_id();
						}
					}

				}
				CommonUtils.getInstance().setClearCacheDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	/**
	 * 方法说明：俱乐部的PopWindow
	 * 
	 */
	private void initClubPopuptWindow() {

		tClubDialog = new MyDialog(this, R.style.dialog, R.layout.camgaign_clublist_pup);// 创建Dialog并设置样式主题
		Window dialogWindow = tClubDialog.getWindow();
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		dialogWindow.setWindowAnimations(R.style.showAnDialog);
		tClubDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		tClubDialog.show();
		ListView select_juli_listview = (ListView) tClubDialog.findViewById(R.id.addtixing_pup_listview);
		mCampaignClubAdapter = new CampaignClubAdapter(context, mClubList);
		select_juli_listview.setAdapter(mCampaignClubAdapter);
		select_juli_listview.setLayoutParams(new LinearLayout.LayoutParams(
				ConfigUtils.getInstance().getPhoneWidHeigth(this).widthPixels, LayoutParams.WRAP_CONTENT));
		select_juli_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				campaignpublish_tv_club.setText(mClubList.get(position).getClub_name());
				club_id = mClubList.get(position).getClub_id();
				tClubDialog.dismiss();

			}

		});
	}

	/**
	 * 方法说明：自动发布设置的PopWindow
	 * 
	 */
	private void initSetAutoPopuptWindow() {

		tSetAutoDialog = new MyDialog(this, R.style.dialog, R.layout.camgaign_clublist_pup);// 创建Dialog并设置样式主题
		Window dialogWindow = tSetAutoDialog.getWindow();
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		dialogWindow.setWindowAnimations(R.style.showAnDialog);
		tSetAutoDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		tSetAutoDialog.show();
		ListView select_juli_listview = (ListView) tSetAutoDialog.findViewById(R.id.addtixing_pup_listview);
		mtSetAutoDialogAdapter = new CampaignClubAdapter(context, mSetAutoList);
		select_juli_listview.setAdapter(mtSetAutoDialogAdapter);
		select_juli_listview.setLayoutParams(new LinearLayout.LayoutParams(
				ConfigUtils.getInstance().getPhoneWidHeigth(this).widthPixels, LayoutParams.WRAP_CONTENT));
		select_juli_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				campaignpublish_tv_setauto.setText(mSetAutoList.get(position).getClub_name());
				advance_time = mSetAutoList.get(position).getClub_id();
				tSetAutoDialog.dismiss();

			}

		});
	}

	TimeUtils mTimeUtils;

	private void initDates() {
		mTimeUtils = new TimeUtils(this);
		mTimeUtils.hourShowStatus = true;
		String[] years = new String[6];
		String nowyear = DatesUtils.getInstance().getNowTime("yyyy");
		mTimeUtils.yearIndex_select = 0;
		for (int i = 0; i < 6; i++) {
			years[i] = Integer.parseInt(nowyear) + i + "年";
		}
		mTimeUtils.monthsIndex_select = Integer.parseInt(DatesUtils.getInstance().getNowTime("MM")) - 1;
		mTimeUtils.dayIndex_select = Integer.parseInt(DatesUtils.getInstance().getNowTime("dd")) - 1;
		String[] months = new String[12];
		String[][] days = new String[12][];
		for (int i = 0; i < 12; i++) {
			String qianzuiString = "0";
			if (i < 9) {
				qianzuiString = "0";
			} else {
				qianzuiString = "";
			}
			months[i] = qianzuiString + (1 + i) + "月";
			int lastday = DatesUtils.getInstance().getMonthLastDay(1 + i);
			days[i] = new String[lastday];
			for (int j = 0; j < lastday; j++) {
				String dayqianzuiString = "0";
				if (j < 9) {
					dayqianzuiString = "0";
				} else {
					dayqianzuiString = "";
				}
				days[i][j] = dayqianzuiString + (1 + j) + "日";
			}
		}
		mTimeUtils.years = years;
		mTimeUtils.months = months;
		mTimeUtils.days = days;
		System.err.println(years.toString());
		System.err.println(months.toString());
		System.err.println(days.toString());
		// ----------
		String[] hours = new String[24];
		for (int h = 0; h < 24; h++) {
			String qianzuiString = "0";
			if (h < 10) {
				qianzuiString = "0";
			} else {
				qianzuiString = "";
			}
			hours[h] = qianzuiString + h + "  :";
		}
		String[] mins = new String[60];
		mTimeUtils.hoursIndex_select = Integer.parseInt(DatesUtils.getInstance().getNowTime("H"));
		mTimeUtils.minsIndex_select = Integer.parseInt(DatesUtils.getInstance().getNowTime("m"));
		for (int m = 0; m < 60; m++) {
			String qianzuiString = "0";
			if (m < 10) {
				qianzuiString = "0";
			} else {
				qianzuiString = "";
			}
			mins[m] = qianzuiString + m + "";
		}
		mTimeUtils.hours = hours;
		mTimeUtils.mins = mins;
	}

	private void initSelectDates(String selectyear, String selectMM, String selectDD, String H, String M) {
		mTimeUtils = new TimeUtils(this);
		mTimeUtils.hourShowStatus = true;
		String[] years = new String[6];
		String nowyear = selectyear;
		mTimeUtils.yearIndex_select = 0;
		for (int i = 0; i < 6; i++) {
			years[i] = Integer.parseInt(nowyear) + i + "年";
		}
		mTimeUtils.monthsIndex_select = Integer.parseInt(selectMM) - 1;
		mTimeUtils.dayIndex_select = Integer.parseInt(selectDD) - 1;
		String[] months = new String[12];
		String[][] days = new String[12][];
		for (int i = 0; i < 12; i++) {
			String qianzuiString = "0";
			if (i < 9) {
				qianzuiString = "0";
			} else {
				qianzuiString = "";
			}
			months[i] = qianzuiString + (1 + i) + "月";
			int lastday = DatesUtils.getInstance().getMonthLastDay(1 + i);
			days[i] = new String[lastday];
			for (int j = 0; j < lastday; j++) {
				String dayqianzuiString = "0";
				if (j < 9) {
					dayqianzuiString = "0";
				} else {
					dayqianzuiString = "";
				}
				days[i][j] = dayqianzuiString + (1 + j) + "日";
			}
		}
		mTimeUtils.years = years;
		mTimeUtils.months = months;
		mTimeUtils.days = days;
		System.err.println(years.toString());
		System.err.println(months.toString());
		System.err.println(days.toString());
		// ----------
		String[] hours = new String[24];
		for (int h = 0; h < 24; h++) {
			String qianzuiString = "0";
			if (h < 10) {
				qianzuiString = "0";
			} else {
				qianzuiString = "";
			}
			hours[h] = qianzuiString + h + "  :";
		}
		String[] mins = new String[60];
		mTimeUtils.hoursIndex_select = Integer.parseInt(H);
		mTimeUtils.minsIndex_select = Integer.parseInt(M);
		for (int m = 0; m < 60; m++) {
			String qianzuiString = "0";
			if (m < 10) {
				qianzuiString = "0";
			} else {
				qianzuiString = "";
			}
			mins[m] = qianzuiString + m + "";
		}
		mTimeUtils.hours = hours;
		mTimeUtils.mins = mins;
	}

	protected static final int STARTTIME = 0;
	protected static final int ENDTIME = 1;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String selectday;
			switch (msg.what) {
			case STARTTIME:
				selectday = msg.getData().getString("selectday");
				selectday = selectday.replace("  :", ":");
				selectday = DatesUtils.getInstance().getDateGeShi(selectday, "yyyy年MM月dd日  HH:mm", "yyyy-MM-dd  HH:mm");
				System.err.println("selectday=====" + selectday);
				campaignpublish_tv_starttime.setText(selectday);
				break;
			case ENDTIME:
				selectday = msg.getData().getString("selectday");
				selectday = selectday.replace("  :", ":");
				selectday = DatesUtils.getInstance().getDateGeShi(selectday, "yyyy年MM月dd日  HH:mm", "yyyy-MM-dd  HH:mm");
				System.err.println("selectday=====" + selectday);
				campaignpublish_tv_endtime.setText(selectday);
				break;
			}
		}
	};
	private String title;
	private String titlestated;
	private String address;
	private String starttime;
	private String endtime;
	private String minge = "0";
	private double longitude;
	private double latitude;

	boolean idEditStatusAdress = false;

	private boolean check() {
		boolean status = true;

		title = campaignpublish_et_title.getText().toString();
		titlestated = campaignpublish_et_titlestated.getText().toString();
		address = campaignpublish_tv_address.getText().toString();
		starttime = campaignpublish_tv_starttime.getText().toString();
		endtime = campaignpublish_tv_endtime.getText().toString();
		minge = campaignpublish_et_quota.getText().toString();
		String nowtimeString = DatesUtils.getInstance().getNowTime("yyyy-MM-dd  HH:mm");
		if (skipFrom.equals("edit")) {
			/**
			 * 编辑时判断是否修改地点时间
			 */
			if (!address_.equals(address)
					|| !DatesUtils.getInstance().doDateEqual(starttime_, starttime, "yyyy-MM-dd  HH:mm")
					|| !DatesUtils.getInstance().doDateEqual(endtime_, endtime, "yyyy-MM-dd  HH:mm")) {
				idEditStatusAdress = true;
			}

		}
		if (TextUtils.isEmpty(title)) {
			CommonUtils.getInstance().initToast(context, "请输入活动主题");
			status = false;
		} else if (TextUtils.isEmpty(address)) {
			CommonUtils.getInstance().initToast(context, "请输入活动地址");
			status = false;
		} else if (TextUtils.isEmpty(starttime)) {
			CommonUtils.getInstance().initToast(context, "请输入活动开始时间");
			status = false;
		} else if (TextUtils.isEmpty(endtime)) {
			CommonUtils.getInstance().initToast(context, "请输入活动结束时间");
			status = false;
		} else if (DatesUtils.getInstance().doCheck2Date(starttime, nowtimeString, "yyyy-MM-dd  HH:mm")) {
			CommonUtils.getInstance().initToast(context, "活动开始时间要大于当前时间");
			status = false;
		} else if (DatesUtils.getInstance().doCheck2Date(endtime, starttime, "yyyy-MM-dd  HH:mm")) {
			CommonUtils.getInstance().initToast(context, "活动结束时间要大于活动开始时间");
			status = false;
		}

		return status;
	}

	public void cancelEditDialog() {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_logout);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text = (TextView) selectDialog.findViewById(R.id.myexit_text);
		TextView myexit_text_cirt = (TextView) selectDialog.findViewById(R.id.myexit_text_cirt);
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定

		myexit_text.setText("活动编辑");
		myexit_text_cirt.setText("是否放弃编辑？");
		text_off.setText("继续");
		text_sure.setText("放弃");
		text_sure.setTextColor(getResources().getColor(R.color.btn_bg));
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				doback();
			}
		});
	}

	/**
	 * 编辑时判断是否修改地点时间
	 */
	public void saveEditDialog() {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_logout);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text = (TextView) selectDialog.findViewById(R.id.myexit_text);
		TextView myexit_text_cirt = (TextView) selectDialog.findViewById(R.id.myexit_text_cirt);
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定

		myexit_text.setText("活动编辑");
		myexit_text_cirt.setText("活动变更了地点和时间，将通过短信方式通知已报名的会员。");
		text_off.setText("取消");
		text_sure.setText("确定编辑");
		text_sure.setTextColor(getResources().getColor(R.color.btn_bg));
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
				clickBtn();
			}
		});
	}

	/**
	 * 发布
	 */
	private void clickBtn() {
		showDilag();
		// int starttime_ =
		// DatesUtils.getInstance().getDateToTimeStamp(starttime,
		// "yyyy-MM-dd HH:mm");
		// int endtime_ = DatesUtils.getInstance().getDateToTimeStamp(endtime,
		// "yyyy-MM-dd HH:mm");
		mhashmap = new HashMap<String, String>();
		mhashmap.put("bestdo_uid", uid);
		mhashmap.put("name", title);
		mhashmap.put("club_id", "" + club_id);
		mhashmap.put("longitude", "" + longitude);
		mhashmap.put("latitude", "" + latitude);
		mhashmap.put("situs", "" + address);
		mhashmap.put("start_time", "" + starttime);
		mhashmap.put("end_time", "" + endtime);
		mhashmap.put("max_peo", "" + minge);
		mhashmap.put("rule", titlestated);
		if (!skipFrom.equals("edit")) {
			mhashmap.put("is_fixed", "" + is_fixed);
			mhashmap.put("advance_time", "" + advance_time);
		} else {
			mhashmap.put("activity_id", activity_id);
		}
		System.err.println(mhashmap);
		new CampaignPublishBusiness(context, skipFrom, mhashmap, new CampaignPublishCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(context, msg + "");
					if (code.equals("200")) {
						if (!skipFrom.equals("edit")) {
							String activity_id = (String) dataMap.get("activity_id");
							Intent intent = new Intent(context, CampaignDetailActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							intent.putExtra("uid", uid);
							intent.putExtra("activity_id", activity_id);
							context.startActivity(intent);
							CommonUtils.getInstance().setPageIntentAnim(intent, context);
							finish();
						} else {
							doback();
						}
					}

				}
				CommonUtils.getInstance().setClearCacheDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	private void doback() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == 1) {

				address = data.getStringExtra("address");
				campaignpublish_tv_address.setText(address);
				longitude = data.getDoubleExtra("longitude_select", 0);
				latitude = data.getDoubleExtra("latitude_select", 0);
			}
		} catch (Exception e) {
		}
		super.onActivityResult(requestCode, resultCode, data);
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
