package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshView.OnReflushListener;
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener;
import com.bestdo.bestdoStadiums.business.LodeCampaignQuartDateBusiness;
import com.bestdo.bestdoStadiums.business.LodeCampaignQuartDateBusiness.LodeCampaignQuartCallback;
import com.bestdo.bestdoStadiums.business.UserCollectBusiness;
import com.bestdo.bestdoStadiums.business.UserCollectBusiness.GetUserCollectCallback;
import com.bestdo.bestdoStadiums.control.adapter.BestDoSportAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CampaingQuartAdapter;
import com.bestdo.bestdoStadiums.control.adapter.MyJoinCampaignAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserCollectAdapter;
import com.bestdo.bestdoStadiums.control.view.PageControlView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.control.view.ScrollLayout.OnScreenChangeListenerDataLoad;
import com.bestdo.bestdoStadiums.control.view.ScrollLayout;
import com.bestdo.bestdoStadiums.model.ClubMenuInfo;
import com.bestdo.bestdoStadiums.model.ClubOperateInfo;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.utils.MyScrollView;
import com.bestdo.bestdoStadiums.utils.MyGridView;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.StringFormatUtil;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author qyy 运动圈
 */
public class CampaignQuartMainActivity extends BaseActivity {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ListView lv_date;
	private LinearLayout not_date;
	private String uid;
	private ProgressDialog mDialog;

	Activity mHomeActivity;
	private XRefreshView main_campaign_xrefresh;
	private ScrollLayout mScrollLayout;
	/**
	 * 每页显示的商品数量
	 */
	protected int APP_PAGE_SIZE;
	private SharedPreferences bestDoInfoSharedPrefs;
	private ArrayList<ClubMenuInfo> clubMenuInfoList;
	private ArrayList<MyJoinClubmenuInfo> myJoinClubmenuInfoList;
	private ArrayList<MyJoinClubmenuInfo> myNoJoinClubmenuInfoList;
	private ArrayList<ClubOperateInfo> arrayClubOperateInfo;
	private MyListView myjoin_ListView;
	private MyListView myjoin_ListView2;
	private View campaign_quart_top_lin;
	private LinearLayout myjoin_lin;
	private LinearLayout mynojoin_lin;
	String myClubNum = "";
	String notJoinNum = "";
	private TextView myjoin_text;
	private TextView mynojoin_text;
	private TextView myjoin_tv_tishi;
	private MyScrollView main_campaign_top_scroll;
	private LinearLayout pagetop_layout_you;
	private String privilege_id;
	private LinearLayout campaingn_quartmain;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.campaign_quart);
		mHomeActivity = CommonUtils.getInstance().nHomeActivity;
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		pagetop_layout_you.setVisibility(View.GONE);
		pagetop_layout_back.setVisibility(View.GONE);
		pagetop_layout_back.setFocusable(true);
		pagetop_layout_back.setFocusableInTouchMode(true);
		pagetop_layout_back.requestFocus();
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		lv_date = (ListView) findViewById(R.id.lv_date);
		myjoin_text = (TextView) findViewById(R.id.myjoin_text);
		mynojoin_text = (TextView) findViewById(R.id.mynojoin_text);
		myjoin_tv_tishi = (TextView) findViewById(R.id.myjoin_tv_tishi);
		main_campaign_xrefresh = (XRefreshView) findViewById(R.id.main_campaign_xrefresh);
		mScrollLayout = (ScrollLayout) findViewById(R.id.campaign_sport_scroll_type);
		myjoin_ListView = (MyListView) findViewById(R.id.myjoin_ListView);
		myjoin_ListView2 = (MyListView) findViewById(R.id.myjoin_ListView2);
		campaign_quart_top_lin = findViewById(R.id.campaign_quart_top_lin);
		myjoin_lin = (LinearLayout) findViewById(R.id.myjoin_lin);
		mynojoin_lin = (LinearLayout) findViewById(R.id.mynojoin_lin);
		main_campaign_top_scroll = (MyScrollView) findViewById(R.id.main_campaign_top_scroll);
		campaingn_quartmain = (LinearLayout) findViewById(R.id.campaingn_quartmain);
		CommonUtils.getInstance().setViewTopHeigth(mHomeActivity, campaign_quart_top_lin);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);

		main_campaign_xrefresh.setAutoRefresh(false);
		main_campaign_xrefresh.setPullLoadEnable(false);

		main_campaign_xrefresh.setXRefreshViewListener(new SimpleXRefreshListener() {

			@Override
			public void onRefresh() {

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						processLogic();
						main_campaign_xrefresh.stopRefresh();
					}
				}, 1000);
			}

			@Override
			public void onLoadMore() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
					}
				}, 1000);
			}
		});

		main_campaign_xrefresh.setOnReflushListener(new OnReflushListener() {

			@Override
			public void isShow(boolean show) {
			}
		});

		myjoin_ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (myJoinClubmenuInfoList != null && myJoinClubmenuInfoList.size() > 0) {
					Intent intent = new Intent(mHomeActivity, ClubActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

					intent.putExtra("MyJoinClubmenuInfo", myJoinClubmenuInfoList.get(arg2));

					mHomeActivity.startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
				}
			}
		});
		// myjoin_ListView2.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// if(myJoinClubmenuInfoList!=null&&myJoinClubmenuInfoList.size()>0){
		// Intent intent = new Intent(mHomeActivity, ClubActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//
		// intent.putExtra("MyJoinClubmenuInfo",
		// myNoJoinClubmenuInfoList.get(arg2));
		//
		// mHomeActivity.startActivity(intent);
		// CommonUtils.getInstance().setPageIntentAnim(intent,
		// mHomeActivity);
		// }
		// }
		// });
	}

	private void initDate() {

		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		String corp_head_title = bestDoInfoSharedPrefs.getString("corp_head_title", "");
		privilege_id = bestDoInfoSharedPrefs.getString("privilege_id", "");
		if (!TextUtils.isEmpty(corp_head_title)) {

			pagetop_tv_name.setText(corp_head_title);
		} else {
			pagetop_tv_name.setText("运动圈");
		}
		dataLoad = new DataLoading();
	}

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

	HashMap<String, String> mhashmap;
	private ArrayList<CampaingQuartAdapter> mBestDoSportAdapterList;
	private String roleid;
	protected String status = "";

	@Override
	protected void processLogic() {
		if (firstComeIn) {
			showDilag();
		}
		initDate();
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			addNotDateImg("数据加载失败");
			CommonUtils.getInstance().setOnDismissDialog(mDialog);
			return;
		}
		roleid = bestDoInfoSharedPrefs.getString("privilege_id", "");
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));// "522151230142308Qlz"
		clubMenuInfoList = null;
		myJoinClubmenuInfoList = null;
		myNoJoinClubmenuInfoList = null;
		System.err.println(mhashmap);
		new LodeCampaignQuartDateBusiness(this, mhashmap, new LodeCampaignQuartCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					status = (String) dataMap.get("code");
					if (status.equals("200")) {
						myClubNum = (String) dataMap.get("myClubNum");
						notJoinNum = (String) dataMap.get("notJoinNum");
						clubMenuInfoList = (ArrayList<ClubMenuInfo>) dataMap.get("clubMenuInfoList");
						myJoinClubmenuInfoList = (ArrayList<MyJoinClubmenuInfo>) dataMap.get("myJoinClubmenuInfoList");
						myNoJoinClubmenuInfoList = (ArrayList<MyJoinClubmenuInfo>) dataMap
								.get("myNoJoinClubmenuInfoList");
						myjoin_text.setText("我加入的俱乐部" + "(" + myClubNum + ")");
						mynojoin_text.setText("更多俱乐部" + "(" + notJoinNum + ")");
						myjoin_ListView.setFocusable(false);
						myjoin_ListView2.setFocusable(false);
						loadeMyJoinClubmenu();
						loadeMoreClubmenu();
						loadeDate();
						campaingn_quartmain.removeAllViews();
						if (privilege_id.equals("1")) {// 1为管理员
							arrayClubOperateInfo = (ArrayList<ClubOperateInfo>) dataMap.get("arrayClubOperateInfo");
							String footerDescription = (String) dataMap.get("footerDescription");
							String moreUrl = (String) dataMap.get("moreUrl");
							loadeOperateData(footerDescription, moreUrl);// 加载数据统计
						}
						myjoin_lin.setVisibility(View.VISIBLE);
						mynojoin_lin.setVisibility(View.VISIBLE);

					}
				}
				firstComeIn = false;
				addNotDateImg("暂无数据");
				main_campaign_top_scroll.scrollBy(0, 0);
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
			}
		});

	}

	/**
	 * 加载数据统计
	 * 
	 * @param footerDescription
	 * @param moreUrl
	 */
	private void loadeOperateData(String footerDescription, final String moreUrl) {
		LayoutInflater inflater = LayoutInflater.from(CampaignQuartMainActivity.this);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.campaingn_quartmain_includ_lay, null);
		campaingn_quartmain.addView(view);
		LinearLayout campaingn_quartmain_nocontent_lay = (LinearLayout) view
				.findViewById(R.id.campaingn_quartmain_nocontent_lay);
		LinearLayout campaingn_quartmain_content_lay = (LinearLayout) view
				.findViewById(R.id.campaingn_quartmain_content_lay);
		LinearLayout campaingn_quartmain_mroe_lay = (LinearLayout) view.findViewById(R.id.campaingn_quartmain_mroe_lay);
		campaingn_quartmain_mroe_lay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CommonUtils.getInstance().toH5(context,moreUrl, "", "",false);
			}
		});
		if (arrayClubOperateInfo != null && arrayClubOperateInfo.size() > 0) {
			TextView text_description = (TextView) view.findViewById(R.id.campaingn_quartmain_includ_text_description);
			TextView text_description2 = (TextView) view
					.findViewById(R.id.campaingn_quartmain_includ_text_description2);
			TextView text_description3 = (TextView) view
					.findViewById(R.id.campaingn_quartmain_includ_text_description3);
			text_description.setText(arrayClubOperateInfo.get(0).getDescription());
			text_description2.setText(arrayClubOperateInfo.get(1).getDescription());
			text_description3.setText(arrayClubOperateInfo.get(2).getDescription());
			TextView text_num1 = (TextView) view.findViewById(R.id.campaingn_quartmain_includ_text_num1);
			TextView text_num2 = (TextView) view.findViewById(R.id.campaingn_quartmain_includ_text_num2);
			TextView text_num3 = (TextView) view.findViewById(R.id.campaingn_quartmain_includ_text_num3);
			TextView text_footerDescription = (TextView) view
					.findViewById(R.id.campaingn_quartmain_includ_text_footerDescription);

			StringFormatUtil spanStr = new StringFormatUtil(this,
					arrayClubOperateInfo.get(0).getNum() + arrayClubOperateInfo.get(0).getUnit(),
					arrayClubOperateInfo.get(0).getNum(), R.color.red).fillColor();
			text_num1.setText(spanStr.getResult());

			StringFormatUtil spanStr2 = new StringFormatUtil(this,
					arrayClubOperateInfo.get(1).getNum() + arrayClubOperateInfo.get(1).getUnit(),
					arrayClubOperateInfo.get(1).getNum(), R.color.red).fillColor();
			text_num2.setText(spanStr2.getResult());

			StringFormatUtil spanStr3 = new StringFormatUtil(this,
					arrayClubOperateInfo.get(2).getNum() + arrayClubOperateInfo.get(2).getUnit(),
					arrayClubOperateInfo.get(2).getNum(), R.color.red).fillColor();
			text_num3.setText(spanStr3.getResult());

			text_footerDescription.setText(footerDescription);
			campaingn_quartmain_nocontent_lay.setVisibility(View.GONE);
			campaingn_quartmain_content_lay.setVisibility(View.VISIBLE);
		} else {
			campaingn_quartmain_nocontent_lay.setVisibility(View.VISIBLE);
			campaingn_quartmain_content_lay.setVisibility(View.GONE);
		}
		campaingn_quartmain.setVisibility(View.VISIBLE);
	}

	private DataLoading dataLoad;
	private String type;
	private MyJoinCampaignAdapter myJoinCampaignAdapter;

	/**
	 * 加载运动圈菜单类型
	 */
	private void loadeDate() {
		if (clubMenuInfoList != null && clubMenuInfoList.size() > 0) {
			/**
			 * -----------控制显示个数----------
			 */
			int realPageSize = clubMenuInfoList.size();
			APP_PAGE_SIZE = 8;
			if (APP_PAGE_SIZE > realPageSize) {
				APP_PAGE_SIZE = realPageSize;
			}
			mScrollLayout.removeAllViews();
			String pageString = PriceUtils.getInstance().gteDividePrice(clubMenuInfoList.size() + "",
					APP_PAGE_SIZE + "");
			int pageNo = (int) Math.ceil(Double.parseDouble(pageString));
			System.out.println("pageNo=" + pageNo);
			System.out.println("Math.ceil(sportTypeList.size()/ APP_PAGE_SIZE)="
					+ Math.ceil(clubMenuInfoList.size() / APP_PAGE_SIZE));

			mBestDoSportAdapterList = new ArrayList<CampaingQuartAdapter>();
			for (int page = 0; page < pageNo; page++) {
				MyGridView appPage = new MyGridView(context);
				appPage.setSelector(R.drawable.list_notselector);
				CampaingQuartAdapter mMainSportAdapter2 = new CampaingQuartAdapter(this, context, clubMenuInfoList,
						page, APP_PAGE_SIZE, roleid);
				mBestDoSportAdapterList.add(mMainSportAdapter2);
				appPage.setAdapter(mMainSportAdapter2);
				appPage.setNumColumns(4);
				appPage.setPadding(0, getResources().getDimensionPixelSize(R.dimen.jianju), 0,
						getResources().getDimensionPixelSize(R.dimen.jianju));
				appPage.setVerticalSpacing(2);
				mScrollLayout.addView(appPage);
			}
			// 加载分页数据
			dataLoad.bindScrollViewGroup(mScrollLayout);
		}

	}

	/**
	 * 我加入的俱乐部
	 */
	private void loadeMyJoinClubmenu() {
		if (myJoinClubmenuInfoList != null && myJoinClubmenuInfoList.size() > 0) {
			type = "loadeMyJoinClubmenu";
			MyJoinCampaignAdapter myJoinCampaignAdapter = new MyJoinCampaignAdapter(mHomeActivity, type,
					myJoinClubmenuInfoList);
			myjoin_ListView.setAdapter(myJoinCampaignAdapter);
			myjoin_ListView.setVisibility(View.VISIBLE);
			myjoin_tv_tishi.setVisibility(View.GONE);
		} else {
			myjoin_ListView.setVisibility(View.GONE);
			myjoin_tv_tishi.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 更多俱乐部
	 */
	private void loadeMoreClubmenu() {
		if (myNoJoinClubmenuInfoList != null && myNoJoinClubmenuInfoList.size() > 0) {
			type = "loadeMoreClubmenu";
			myJoinCampaignAdapter = new MyJoinCampaignAdapter(mHomeActivity, type, myNoJoinClubmenuInfoList);
			myjoin_ListView2.setAdapter(myJoinCampaignAdapter);
		}
	}

	/**
	 ** 
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg(String content) {
		if ((clubMenuInfoList == null || clubMenuInfoList.size() == 0)
				&& (myNoJoinClubmenuInfoList == null || myNoJoinClubmenuInfoList.size() == 0)
				&& (myJoinClubmenuInfoList == null || myJoinClubmenuInfoList.size() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			TextView not_date_jihuo_btn = (TextView) findViewById(R.id.not_date_jihuo_btn);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			myjoin_lin.setVisibility(View.GONE);
			mynojoin_lin.setVisibility(View.GONE);
			not_date_img.setBackgroundResource(R.drawable.not_date);
			if (status.equals("400")) {
				not_date_cont.setText("尚未开通企业账号");
			} else {
				not_date_cont.setText(content);
				not_date_jihuo_btn.setText("重新加载");
				not_date_jihuo_btn.setVisibility(View.VISIBLE);
				not_date_jihuo_btn.setBackgroundResource(R.drawable.corners_selectbtnbg);
				not_date_jihuo_btn.setTextColor(getResources().getColor(R.color.blue));
				not_date_jihuo_btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						firstComeIn = true;
						processLogic();
					}
				});
			}

		} else {
			not_date.setVisibility(View.GONE);
		}
	}

	// 分页数据
	class DataLoading {
		private int count;

		public void bindScrollViewGroup(ScrollLayout scrollViewGroup) {
			this.count = scrollViewGroup.getChildCount();
			scrollViewGroup.setOnScreenChangeListenerDataLoad(new OnScreenChangeListenerDataLoad() {
				public void onScreenChange(int currentIndex) {
					// TODO Auto-generated method stub
				}
			});
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

	protected void onPause() {
		Log.e("onPause-----", "onPauseonPauseonPauseonPauseonPauseonPause-----");
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		RequestUtils.cancelAll(this);
		super.onDestroy();
	}

	/**
	 * 退出监听
	 */
	public void onBackPressed() {
		Intent intents = new Intent();
		intents.setAction(getString(R.string.action_home));
		intents.putExtra("type", getString(R.string.action_home_type_gotohome));
		sendBroadcast(intents);
	}
}
