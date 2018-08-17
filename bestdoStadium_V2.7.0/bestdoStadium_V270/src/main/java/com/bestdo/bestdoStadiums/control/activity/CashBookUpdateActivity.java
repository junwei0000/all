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
import com.bestdo.bestdoStadiums.business.CashMyClubTypeListBusiness.CashMyClubTypeListCallback;
import com.bestdo.bestdoStadiums.business.CashMyClubTypeListBusiness;
import com.bestdo.bestdoStadiums.business.CashbookDelBusiness;
import com.bestdo.bestdoStadiums.business.CashbookDelBusiness.GetCashbookDelCallback;
import com.bestdo.bestdoStadiums.business.CashbookListBusiness;
import com.bestdo.bestdoStadiums.business.CashbookListBusiness.GetCashbookListCallback;
import com.bestdo.bestdoStadiums.business.CashbookUpdateBusiness;
import com.bestdo.bestdoStadiums.business.CashbookUpdateBusiness.GetCashbookUpdateCallback;
import com.bestdo.bestdoStadiums.control.activity.CampaignQuartMainActivity.DataLoading;
import com.bestdo.bestdoStadiums.control.adapter.AdvertImagePagerAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CampaingQuartAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CashClubAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CashTypeAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CashUpdateTypeAdapter;
import com.bestdo.bestdoStadiums.control.adapter.CashbookListAdapter;
import com.bestdo.bestdoStadiums.control.adapter.StadiumAdapter;
import com.bestdo.bestdoStadiums.control.adapter.StadiumSelectJuliAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderSportAdapter;
import com.bestdo.bestdoStadiums.control.view.PageControlView;
import com.bestdo.bestdoStadiums.control.view.ScrollLayout;
import com.bestdo.bestdoStadiums.control.view.ScrollLayout.OnScreenChangeListenerDataLoad;
import com.bestdo.bestdoStadiums.model.CashMyClubInfo;
import com.bestdo.bestdoStadiums.model.CashMyClubTypeInfo;
import com.bestdo.bestdoStadiums.model.CashbookListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.utils.MyGridView;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.utils.TimeUtils;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
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
 * @Description 类描述：记事本--添加、编辑
 */
public class CashBookUpdateActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;

	private HashMap<String, String> mHashMap;
	private ImageView pagetop_tv_arrow;
	private TextView pagetop_tv_you;
	private EditText update_et_money;
	private EditText update_et_description;
	private LinearLayout update_layout_date;
	private TextView update_tv_date;
	private ScrollLayout mScrollLayout;
	private PageControlView pageControl;
	private DataLoading dataLoad;
	private ArrayList<CashMyClubInfo> mNyClubTypeList;
	private TimeUtils mTimeUtils;
	private String use_time;
	private String year;
	private String money;
	private String club_id;
	private String uid;
	private String upudatetype;
	private int index;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.pagetop_tv_name:
			CommonUtils.getInstance().closeSoftInput(context);
			mHandler.sendEmptyMessage(SHOWPOPU);
			break;
		case R.id.update_layout_date:
			CommonUtils.getInstance().closeSoftInput(context);
			mTimeUtils.setInit(mHandler, SELECTDATE);
			setIndex(use_time);
			break;
		case R.id.pagetop_tv_you:
			money = update_et_money.getText().toString();
			if (!TextUtils.isEmpty(money)) {
				description = update_et_description.getText().toString();
				update();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.cashbook_update);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_arrow = (ImageView) findViewById(R.id.pagetop_tv_arrow);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		pagetop_tv_you.setVisibility(View.VISIBLE);

		update_et_money = (EditText) findViewById(R.id.update_et_money);
		update_et_description = (EditText) findViewById(R.id.update_et_description);
		update_layout_date = (LinearLayout) findViewById(R.id.update_layout_date);
		update_tv_date = (TextView) findViewById(R.id.update_tv_date);

		mScrollLayout = (ScrollLayout) findViewById(R.id.scroll_type);
		pageControl = (PageControlView) findViewById(R.id.mpageControl);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_name.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		update_layout_date.setOnClickListener(this);
		pagetop_tv_you.setOnClickListener(this);
		update_et_money.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				PriceUtils.getInstance().judge(8, 2, arg0);
			}

		});
		init();
	}

	private void init() {
		dataLoad = new DataLoading();
		pagetop_tv_you.setText("确定");
		mNyClubTypeList = new ArrayList<CashMyClubInfo>();
		mNyClubTypeList.add(new CashMyClubInfo("1", "支出", "", ""));
		mNyClubTypeList.add(new CashMyClubInfo("2", "收入", "", ""));
		club_name = mNyClubTypeList.get(0).getClub_name();
		selecttype = mNyClubTypeList.get(0).getClub_id();
		mTimeUtils = new TimeUtils(this);
		mTimeUtils.initFromToNowDates(Constans.getInstance().STARTYEAR);
		use_time = DatesUtils.getInstance().getNowTime("yyyy年MM月dd日");
		update_tv_date.setText(use_time);

		Intent intent = getIntent();
		club_id = intent.getStringExtra("club_id");
		uid = intent.getStringExtra("uid");
		upudatetype = intent.getStringExtra("upudatetype");
		if (upudatetype.equals("edit")) {
			index = intent.getIntExtra("index", 0);
			CashbookListInfo mCashbookListInfo = (CashbookListInfo) intent.getSerializableExtra("mCashbookListInfo");
			noteid = mCashbookListInfo.getId();
			use_time = mCashbookListInfo.getUse_time();
			use_time = DatesUtils.getInstance().getDateGeShi(use_time, "yyyy-MM-dd", "yyyy年MM月dd日");
			year = DatesUtils.getInstance().getDateGeShi(use_time, "yyyy年MM月dd日", "yyyy");
			money = mCashbookListInfo.getMoney();
			money = PriceUtils.getInstance().seePrice(money);
			selecttype = mCashbookListInfo.getType();
			class_name = mCashbookListInfo.getClass_name();
			description = mCashbookListInfo.getDescription();
			update_et_description.setText(description);
			update_et_money.setText(money);
			update_tv_date.setText(use_time);
			if (selecttype.equals("1")) {
				club_name = mNyClubTypeList.get(0).getClub_name();
			} else if (selecttype.equals("2")) {
				club_name = mNyClubTypeList.get(1).getClub_name();
			}
		}
		initSelection();
		setArrowView(false);
	}

	/**
	 * 初始化光标的位置
	 */
	private void initSelection() {
		CharSequence text = update_et_money.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
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

	private ProgressDialog mDialog;
	protected ArrayList<CashMyClubTypeInfo> mNyClubtypeList;
	/**
	 * 1是支出，2是收入
	 */
	private String selecttype;

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
		mHashMap = new HashMap<String, String>();
		mHashMap.put("type", "" + selecttype);
		new CashMyClubTypeListBusiness(this, mHashMap, new CashMyClubTypeListCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("200")) {
						mNyClubtypeList = (ArrayList<CashMyClubTypeInfo>) dataMap.get("mList");
						loadeDate();
					}
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mHashMap, dataMap);
			}
		});

	}

	private int APP_PAGE_SIZE;
	private ArrayList<CashTypeAdapter> mBestDoSportAdapterList;
	private String selectid;
	private String class_name;

	/**
	 * 加载运动圈菜单类型
	 */
	private void loadeDate() {
		if (mNyClubtypeList != null && mNyClubtypeList.size() > 0) {
			if (upudatetype.equals("edit")) {
				boolean haveSelected = false;
				for (int i = 0; i < mNyClubtypeList.size(); i++) {
					if (class_name.equals(mNyClubtypeList.get(i).getName())) {
						selectid = mNyClubtypeList.get(i).getId();
						haveSelected = true;
						break;
					}
				}
				if (!haveSelected) {
					selectid = mNyClubtypeList.get(0).getId();
					class_name = mNyClubtypeList.get(0).getName();
				}
			} else {
				selectid = mNyClubtypeList.get(0).getId();
				class_name = mNyClubtypeList.get(0).getName();
			}
			/**
			 * -----------控制显示个数----------
			 */
			int realPageSize = mNyClubtypeList.size();
			APP_PAGE_SIZE = 4;
			if (APP_PAGE_SIZE > realPageSize) {
				APP_PAGE_SIZE = realPageSize;
				pageControl.setVisibility(View.GONE);
			} else {
				pageControl.setVisibility(View.VISIBLE);
			}
			mScrollLayout.removeAllViews();
			String pageString = PriceUtils.getInstance().gteDividePrice(mNyClubtypeList.size() + "",
					APP_PAGE_SIZE + "");
			int pageNo = (int) Math.ceil(Double.parseDouble(pageString));
			System.out.println("pageNo=" + pageNo);
			System.out.println("Math.ceil(sportTypeList.size()/ APP_PAGE_SIZE)="
					+ Math.ceil(mNyClubtypeList.size() / APP_PAGE_SIZE));

			mBestDoSportAdapterList = new ArrayList<CashTypeAdapter>();
			for (int page = 0; page < pageNo; page++) {
				MyGridView appPage = new MyGridView(context);
				appPage.setSelector(R.drawable.list_notselector);

				ArrayList<CashMyClubTypeInfo> mList = new ArrayList<CashMyClubTypeInfo>();
				int i = page * APP_PAGE_SIZE;
				int iEnd = i + APP_PAGE_SIZE;
				while ((i < mNyClubtypeList.size()) && (i < iEnd)) {
					mList.add(mNyClubtypeList.get(i));
					i++;
				}

				CashTypeAdapter mMainSportAdapter2 = new CashTypeAdapter(this, mList, page, mHandler, SELECTTYPE,
						selectid);
				mBestDoSportAdapterList.add(mMainSportAdapter2);
				appPage.setAdapter(mMainSportAdapter2);
				appPage.setNumColumns(APP_PAGE_SIZE);
				appPage.setPadding(0, getResources().getDimensionPixelSize(R.dimen.jianju), 0,
						getResources().getDimensionPixelSize(R.dimen.jianju));
				appPage.setVerticalSpacing(5);
				mScrollLayout.addView(appPage);
			}
			// 加载分页
			pageControl.bindScrollViewGroup(mScrollLayout);
			// 加载分页数据
			dataLoad.bindScrollViewGroup(mScrollLayout);

		}

	}

	private final int SELECTTYPE = 1;
	private final int LOAD = 2;
	private final int SELECTDATE = 3;
	private final int SHOWPOPU = 4;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SELECTTYPE:
				CashMyClubTypeInfo mCashMyClubTypeInfo = (CashMyClubTypeInfo) msg.obj;
				selectid = mCashMyClubTypeInfo.getId();
				class_name = mCashMyClubTypeInfo.getName();
				for (CashTypeAdapter iterable_element : mBestDoSportAdapterList) {
					iterable_element.setSelectPosition(selectid);
					iterable_element.notifyDataSetChanged();
				}
				break;
			case LOAD:
				processLogic();
				break;
			case SELECTDATE:
				use_time = msg.getData().getString("selectday");
				update_tv_date.setText(use_time);
				break;
			case SHOWPOPU:
				initSelectSportPopuptWindow();
				break;
			}
		}
	};

	private void setIndex(String time) {
		String sy = DatesUtils.getInstance().getDateGeShi(time, "yyyy年MM月dd日", "yyyy");
		String sm = DatesUtils.getInstance().getDateGeShi(time, "yyyy年MM月dd日", "MM");
		String sd = DatesUtils.getInstance().getDateGeShi(time, "yyyy年MM月dd日", "dd");
		int yearIndex_select = Integer.parseInt(sy) - Constans.getInstance().STARTYEAR;
		int monthsIndex_select = Integer.parseInt(sm) - 1;
		int dayIndex_select = Integer.parseInt(sd) - 1;
		mTimeUtils.getYMDDialog(yearIndex_select, monthsIndex_select, dayIndex_select);
	}

	private PopupWindow mPopupWindow;
	protected String club_name;
	private String description;
	private String noteid;

	private void initSelectSportPopuptWindow() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.user_orderall_sport_popupwindow, null);
		ListView listview = (ListView) popupWindow.findViewById(R.id.user_orderall_sport_listview);
		final CashUpdateTypeAdapter adapter = new CashUpdateTypeAdapter(this, mNyClubTypeList, selecttype);
		listview.setAdapter(adapter);
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		popupWindow.setBackgroundColor(getResources().getColor(R.color.black));
		popupWindow.getBackground().setAlpha(50);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.showAsDropDown(pagetop_layout_back);
		setArrowView(true);
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
				selecttype = mNyClubTypeList.get(position).getClub_id();
				club_name = mNyClubTypeList.get(position).getClub_name();
				setArrowView(false);
				if (mPopupWindow != null && mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				mHandler.sendEmptyMessage(LOAD);
			}
		});
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				setArrowView(false);
			}
		});
	}

	private void setArrowView(boolean clubShowStatus) {
		pagetop_tv_name.setText(club_name);
		if (clubShowStatus) {
			clubShowStatus = false;
			pagetop_tv_arrow.setBackgroundResource(R.drawable.ic_arrow_up_gray);
		} else {
			clubShowStatus = true;
			pagetop_tv_arrow.setBackgroundResource(R.drawable.ic_arrow_down_gray);
		}
	}

	private void update() {
		use_time = DatesUtils.getInstance().getDateGeShi(use_time, "yyyy年MM月dd日", "yyyy-MM-dd");
		year = DatesUtils.getInstance().getDateGeShi(use_time, "yyyy-MM-dd", "yyyy");
		showDilag();
		mHashMap = new HashMap<String, String>();
		String path;
		if (upudatetype.equals("edit")) {
			mHashMap.put("noteid", "" + noteid);
			path = Constans.CLUBEDIT;
		} else {
			path = Constans.CLUBADD;
		}
		mHashMap.put("uid", "" + uid);
		mHashMap.put("club_id", "" + club_id);
		mHashMap.put("year", "" + year);
		mHashMap.put("money", "" + money);
		mHashMap.put("use_time", "" + use_time);
		mHashMap.put("type", "" + selecttype);
		mHashMap.put("class", "" + selectid);
		mHashMap.put("description", "" + description);
		new CashbookUpdateBusiness(this, mHashMap, path, new GetCashbookUpdateCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(context, msg);
					if (status.equals("200")) {
						Intent intent = new Intent();
						if (upudatetype.equals("edit")) {
							String budgetSurplus = (String) dataMap.get("budgetSurplus");
							String payMoney = (String) dataMap.get("payMoney");
							String incomeMoney = (String) dataMap.get("incomeMoney");
							intent.putExtra("budgetSurplus", budgetSurplus);
							intent.putExtra("payMoney", payMoney);
							intent.putExtra("incomeMoney", incomeMoney);

							CashbookListInfo mCashbookListInfo = new CashbookListInfo(noteid, money, use_time,
									selecttype, class_name, description, "", "");
							intent.putExtra("index", index);
							intent.putExtra("mCashbookListInfo", mCashbookListInfo);
							setResult(Constans.getInstance().ForResult5, intent);
						} else {
							setResult(Constans.getInstance().ForResult4, intent);
						}

						doback();
					}
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mHashMap, dataMap);

			}
		});

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
