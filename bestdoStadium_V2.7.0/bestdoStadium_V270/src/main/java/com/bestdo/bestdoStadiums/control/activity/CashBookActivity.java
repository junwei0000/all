/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshView.OnReflushListener;
import com.andview.refreshview.XRefreshView.SimpleXRefreshListener;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CashMyClubBusiness;
import com.bestdo.bestdoStadiums.business.CashMyClubBusiness.CashMyClubCallback;
import com.bestdo.bestdoStadiums.business.CashbookDelBusiness;
import com.bestdo.bestdoStadiums.business.CashbookDelBusiness.GetCashbookDelCallback;
import com.bestdo.bestdoStadiums.business.CashbookListBusiness;
import com.bestdo.bestdoStadiums.business.CashbookListBusiness.GetCashbookListCallback;
import com.bestdo.bestdoStadiums.control.adapter.CashClubAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CashbookListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.StadiumAdapter;
import com.bestdo.bestdoStadiums.control.adapter.StadiumSelectJuliAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderSportAdapter;
import com.bestdo.bestdoStadiums.model.CashMyClubInfo;
import com.bestdo.bestdoStadiums.model.CashbookListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.TimeUtils;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-28 下午3:48:09
 * @Description 类描述：记事本
 */
public class CashBookActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private ImageView pagetop_tv_arrow;
	private XRefreshView main_xrefresh;
	private RelativeLayout cashbook_relat_sum;
	private TextView cashbook_tv_sum;
	private TextView cashbook_tv_pay;
	private TextView cashbook_tv_income;
	private MyListView main_myListView;
	private LinearLayout not_date;
	private Button click_btn;

	private SharedPreferences bestDoInfoSharedPrefs;
	private HashMap<String, String> mHashMap;
	private LinearLayout pagetop_layout_you;
	private TextView cashbook_tv_title;
	private ImageView cashbook_iv_arrow;
	private TimeUtils mTimeUtils;
	private TextView pagetop_tv_you;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.pagetop_tv_name:
			if (mNyClubList != null && mNyClubList.size() > 1) {
				initSelectSportPopuptWindow();
			}
			break;
		case R.id.pagetop_layout_you:
			mTimeUtils.setInit(mHandler, SETYEAR);
			int yearIndex_select = Integer.parseInt(year) - Constans.getInstance().STARTYEAR;
			mTimeUtils.getYDialog(yearIndex_select, 0, 0);
			break;
		case R.id.cashbook_relat_sum:
			String nowyear = DatesUtils.getInstance().getNowTime("yyyy");
			if (nowyear.equals(year)) {
				Intent intent = new Intent(context, CashBookSetYearlybudgetActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("club_id", club_id);
				intent.putExtra("uid", bestDoInfoSharedPrefs.getString("uid", ""));
				intent.putExtra("yearBudge", yearBudge);
				startActivityForResult(intent, Constans.getInstance().ForResult4);
				CommonUtils.getInstance().setPageIntentAnim(intent, context);
			}
			break;
		case R.id.click_btn:
			Intent intent = new Intent(context, CashBookUpdateActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("upudatetype", "add");
			intent.putExtra("club_id", club_id);
			intent.putExtra("uid", bestDoInfoSharedPrefs.getString("uid", ""));
			startActivityForResult(intent, Constans.getInstance().ForResult4);
			CommonUtils.getInstance().setPageIntentAnim(intent, context);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.cashbook);
		CommonUtils.getInstance().addActivity(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		cashbook_tv_title = (TextView) findViewById(R.id.cashbook_tv_title);
		pagetop_tv_arrow = (ImageView) findViewById(R.id.pagetop_tv_arrow);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		cashbook_iv_arrow = (ImageView) findViewById(R.id.cashbook_iv_arrow);
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		main_xrefresh = (XRefreshView) findViewById(R.id.main_xrefresh);
		cashbook_relat_sum = (RelativeLayout) findViewById(R.id.cashbook_relat_sum);
		cashbook_tv_sum = (TextView) findViewById(R.id.cashbook_tv_sum);
		cashbook_tv_pay = (TextView) findViewById(R.id.cashbook_tv_pay);
		cashbook_tv_income = (TextView) findViewById(R.id.cashbook_tv_income);
		main_myListView = (MyListView) findViewById(R.id.main_myListView);
		not_date = (LinearLayout) findViewById(R.id.not_date);
		click_btn = (Button) findViewById(R.id.click_btn);

		FrameLayout frm_notdte = (FrameLayout) findViewById(R.id.frm_notdte);
		frm_notdte.setBackgroundColor(getResources().getColor(R.color.white));
	}

	@Override
	protected void setListener() {
		mTimeUtils = new TimeUtils(this);
		mTimeUtils.initFromToNowDates(Constans.getInstance().STARTYEAR);
		year = DatesUtils.getInstance().getNowTime("yyyy");
		pagetop_tv_you.setText("年份");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_name.setOnClickListener(this);
		pagetop_layout_you.setOnClickListener(this);
		cashbook_relat_sum.setOnClickListener(this);
		click_btn.setOnClickListener(this);
		main_myListView.setOnItemLongClickListener(itemLongClickListener);
		main_myListView.setOnItemClickListener(itemClickListener);
		main_xrefresh.setPullRefreshEnable(true);
		main_xrefresh.setAutoRefresh(false);
		main_xrefresh.setPullLoadEnable(true);
		// XRefreshView下拉刷新时机有了更强大的判断方法，已经不需要再设置view的类型了
		// outView.setRefreshViewType(XRefreshViewType.ABSLISTVIEW);

		main_xrefresh.setXRefreshViewListener(new SimpleXRefreshListener() {

			@Override
			public void onRefresh() {

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if (!Constans.getInstance().refreshOrLoadMoreLoading) {
							Constans.getInstance().refreshOrLoadMoreLoading = true;
							mHandler.sendEmptyMessage(LOADDETAIL);
						}
						main_xrefresh.stopRefresh();
					}
				}, 1000);
			}

			@Override
			public void onLoadMore() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if (!Constans.getInstance().refreshOrLoadMoreLoading) {
							Constans.getInstance().refreshOrLoadMoreLoading = true;
							mHandler.sendEmptyMessage(LOADMORE);
						}
						main_xrefresh.stopLoadMore();
					}
				}, 1000);
			}
		});
		main_xrefresh.setOnReflushListener(new OnReflushListener() {

			@Override
			public void isShow(boolean show) {
			}
		});
	}

	OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (mbookListList != null && mbookListList.size() > 0) {
				cancelEditDialog(arg2);

			}
			return true;
		}
	};
	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (mbookListList != null && mbookListList.size() > 0) {
				Intent intent = new Intent(context, CashBookUpdateActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("upudatetype", "edit");
				intent.putExtra("mCashbookListInfo", mbookListList.get(arg2));
				intent.putExtra("index", arg2);
				intent.putExtra("club_id", club_id);
				intent.putExtra("uid", bestDoInfoSharedPrefs.getString("uid", ""));
				startActivityForResult(intent, Constans.getInstance().ForResult5);
				CommonUtils.getInstance().setPageIntentAnim(intent, context);
			}
		}
	};
	private ProgressDialog mDialog;
	protected ArrayList<CashMyClubInfo> mNyClubList;
	protected String club_name;
	protected String club_id;

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

	private final int LOADDETAIL = 7;
	private final int LOADMORE = 4;
	private final int SETYEAR = 2;

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String selectday;
			switch (msg.what) {
			case LOADDETAIL:
				init();
				loadList();
				break;
			case LOADMORE:
				page++;
				loadList();
				break;
			case SETYEAR:
				selectday = msg.getData().getString("selectday");
				year = DatesUtils.getInstance().getDateGeShi(selectday, "yyyy年", "yyyy");
				mHandler.sendEmptyMessage(LOADDETAIL);
				break;
			}
		}
	};

	@Override
	protected void processLogic() {
		showDilag();
		mHashMap = new HashMap<String, String>();
		mHashMap.put("uid", bestDoInfoSharedPrefs.getString("uid", ""));
		new CashMyClubBusiness(this, mHashMap, new CashMyClubCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("200")) {
						mNyClubList = (ArrayList<CashMyClubInfo>) dataMap.get("mList");
						if (mNyClubList != null && mNyClubList.size() > 0) {
							club_name = mNyClubList.get(0).getClub_name();
							club_id = mNyClubList.get(0).getClub_id();
							if (mNyClubList.size() == 1) {
								pagetop_tv_arrow.setVisibility(View.GONE);
							} else {
								pagetop_tv_arrow.setVisibility(View.VISIBLE);
							}
						}
						clubShowStatus = false;
						setArrowView();
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mHashMap, dataMap);
				mHandler.sendEmptyMessage(LOADDETAIL);
			}
		});

	}

	int page, pagesize;
	int total;
	private ArrayList<CashbookListInfo> mbookListList;
	protected String payMoney;
	protected String incomeMoney;
	protected String budgetSurplus;
	private CashbookListAdapter adapter;
	protected String yearBudge;
	/**
	 * 0为设置预算，1已设置预算
	 */
	protected String yearBudgeStarts;

	private void init() {
		mbookListList = new ArrayList<CashbookListInfo>();
		page = 0;
		page++;
		pagesize = 10;
		cashbook_tv_title.setText(year + "年度预算剩余总额（元）");
		String nowyear = DatesUtils.getInstance().getNowTime("yyyy");
		if (nowyear.equals(year)) {
			cashbook_iv_arrow.setVisibility(View.VISIBLE);
		} else {
			cashbook_iv_arrow.setVisibility(View.INVISIBLE);
		}

	}

	private void loadList() {
		mHashMap = new HashMap<String, String>();
		mHashMap.put("club_id", club_id);
		mHashMap.put("year", year);
		mHashMap.put("page", page + "");
		mHashMap.put("pagesize", pagesize + "");
		if (!Constans.getInstance().refreshOrLoadMoreLoading) {
			showDilag();
		}
		new CashbookListBusiness(this, mHashMap, mbookListList, new GetCashbookListCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				Constans.getInstance().refreshOrLoadMoreLoading = false;
				page--;
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("200")) {
						total = (Integer) dataMap.get("total");
						budgetSurplus = (String) dataMap.get("budgetSurplus");
						payMoney = (String) dataMap.get("payMoney");
						incomeMoney = (String) dataMap.get("incomeMoney");
						yearBudge = (String) dataMap.get("yearBudge");
						yearBudgeStarts = (String) dataMap.get("yearBudgeStarts");
						setDates();

						mbookListList = (ArrayList<CashbookListInfo>) dataMap.get("mList");
						if (page * pagesize < total) {
							page++;
						}
						updateList();
					}
				}
				addNotDateImg();
				CommonUtils.getInstance().setClearCacheBackDate(mHashMap, dataMap);
			}
		});

	}

	private void setDates() {
		if ((!TextUtils.isEmpty(yearBudgeStarts) && yearBudgeStarts.equals("0")) || TextUtils.isEmpty(budgetSurplus)) {
			String nowyear = DatesUtils.getInstance().getNowTime("yyyy");
			if (nowyear.equals(year)) {
				cashbook_tv_sum.setText("点击设置预算");
			} else {
				cashbook_tv_sum.setText("0");
			}
		} else {
			cashbook_tv_sum.setText("" + PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(budgetSurplus)));
		}
		cashbook_tv_pay.setText("" + payMoney);
		cashbook_tv_income.setText("" + incomeMoney);
	}

	public void cancelEditDialog(final int arg2) {
		final MyDialog selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_logout);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text = (TextView) selectDialog.findViewById(R.id.myexit_text);
		TextView myexit_text_cirt = (TextView) selectDialog.findViewById(R.id.myexit_text_cirt);
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定

		myexit_text.setText("");
		myexit_text.setVisibility(View.GONE);
		myexit_text_cirt.setText("是否删除该明细？");
		text_off.setText("取消");
		text_sure.setText("确定");
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
				del(mbookListList.get(arg2).getId(), arg2);
			}
		});
	}

	private void del(String noteid, final int index) {
		showDilag();
		mHashMap = new HashMap<String, String>();
		mHashMap.put("noteid", noteid);
		new CashbookDelBusiness(this, mHashMap, new GetCashbookDelCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {

				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(context, msg);
					if (status.equals("200")) {
						budgetSurplus = (String) dataMap.get("budgetSurplus");
						payMoney = (String) dataMap.get("payMoney");
						incomeMoney = (String) dataMap.get("incomeMoney");
						setDates();
						mbookListList.remove(index);
						updateList();
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mHashMap, dataMap);

			}
		});
	}

	private void updateList() {
		if (mbookListList != null && mbookListList.size() != 0) {
		} else {
			mbookListList = new ArrayList<CashbookListInfo>();
		}

		if (adapter == null) {
			adapter = new CashbookListAdapter(this, mbookListList);
			main_myListView.setAdapter(adapter);
		} else {
			adapter.setList(mbookListList);
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 没有数据时加载默认图
	 */
	private void addNotDateImg() {
		if (adapter == null || (adapter != null && adapter.getCount() == 0)) {
			ImageView not_date_img = (ImageView) findViewById(R.id.not_date_img);

			TextView not_date_cont2 = (TextView) findViewById(R.id.not_date_cont2);
			TextView not_date_cont = (TextView) findViewById(R.id.not_date_cont);
			not_date.setVisibility(View.VISIBLE);
			not_date_img.setVisibility(View.VISIBLE);
			not_date_cont.setVisibility(View.VISIBLE);
			not_date_cont2.setVisibility(View.VISIBLE);
			not_date_img.setBackgroundResource(R.drawable.cashbook_img_notdate);
			not_date_cont2.setText("没有账单明细");
			not_date_cont.setText("记账是一个好习惯，快来记一笔");
			main_xrefresh.setPullLoadEnable(false);
		} else {
			// main_xrefresh.setPullLoadEnable(true);
			not_date.setVisibility(View.GONE);
		}
	}

	boolean clubShowStatus = false;
	private PopupWindow mPopupWindow;
	private String year = "";

	private void setArrowView() {
		if (!TextUtils.isEmpty(club_name) && club_name.length() > 12) {
			club_name = club_name.substring(0, 9) + "...";
		}
		pagetop_tv_name.setText(club_name);
		if (clubShowStatus) {
			clubShowStatus = false;
			pagetop_tv_arrow.setBackgroundResource(R.drawable.ic_arrow_up_gray);
		} else {
			clubShowStatus = true;
			pagetop_tv_arrow.setBackgroundResource(R.drawable.ic_arrow_down_gray);
		}
	}

	private void initSelectSportPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.user_orderall_sport_popupwindow, null);
		ListView listview = (ListView) popupWindow.findViewById(R.id.user_orderall_sport_listview);
		final CashClubAdapter adapter = new CashClubAdapter(this, mNyClubList, club_id);
		listview.setAdapter(adapter);
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		popupWindow.setBackgroundColor(getResources().getColor(R.color.black));
		popupWindow.getBackground().setAlpha(50);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAsDropDown(pagetop_layout_back);
		clubShowStatus = true;
		setArrowView();
		popupWindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				club_id = mNyClubList.get(position).getClub_id();
				club_name = mNyClubList.get(position).getClub_name();
				clubShowStatus = false;
				setArrowView();
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				mHandler.sendEmptyMessage(LOADDETAIL);
			}
		});
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				clubShowStatus = false;
				setArrowView();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			if (resultCode == Constans.getInstance().ForResult4) {
				mHandler.sendEmptyMessage(LOADDETAIL);
			} else if (resultCode == Constans.getInstance().ForResult5) {
				budgetSurplus = (String) data.getStringExtra("budgetSurplus");
				payMoney = (String) data.getStringExtra("payMoney");
				incomeMoney = (String) data.getStringExtra("incomeMoney");
				setDates();
				int index = data.getIntExtra("index", 0);
				CashbookListInfo mCashbookListInfo = (CashbookListInfo) data.getSerializableExtra("mCashbookListInfo");
				mbookListList.get(index).setClass_name(mCashbookListInfo.getClass_name());
				mbookListList.get(index).setDescription(mCashbookListInfo.getDescription());
				mbookListList.get(index).setMoney(mCashbookListInfo.getMoney());
				mbookListList.get(index).setType(mCashbookListInfo.getType());
				mbookListList.get(index).setUse_time(mCashbookListInfo.getUse_time());
				updateList();
			}
		} catch (Exception e) {
		}
		super.onActivityResult(requestCode, resultCode, data);
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
