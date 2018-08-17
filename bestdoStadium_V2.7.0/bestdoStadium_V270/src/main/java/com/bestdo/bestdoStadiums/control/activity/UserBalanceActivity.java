package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CreateOrdersBuyBalanceBusiness;
import com.bestdo.bestdoStadiums.business.CreateOrdersBuyBalanceBusiness.GetCreateOrdersCallback;
import com.bestdo.bestdoStadiums.business.UserBalanceListBusiness;
import com.bestdo.bestdoStadiums.business.UserBalanceListBusiness.BalanceListCallback;
import com.bestdo.bestdoStadiums.control.adapter.UserBalanceListAdapter;
import com.bestdo.bestdoStadiums.model.UserBalanceListInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.utils.PayDialog;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserBalanceActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView pagetop_tv_you;
	private TextView balance_tv_mymoney;
	private TextView balance_tv_book;
	private MyListView balance_myListView;
	private SharedPreferences bestDoInfoSharedPrefs;
	private HashMap<String, String> mhashmap;
	protected ArrayList<UserBalanceListInfo> mList;
	protected String agreementurl;
	private LinearLayout layout_xieyi;
	protected UserBalanceListAdapter mUserBalanceListAdapter;
	private String balance;
	private String skip;
	private CheckBox xieyi_cb;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.pagetop_tv_you:

			Intent intent = new Intent(context, UserBalanceDetailActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, context);

			break;
		case R.id.balance_tv_book:
			if(xieyi_cb.isChecked()){
			if (!TextUtils.isEmpty(payMoney)) {
				String money=PriceUtils.getInstance().gteMultiplySumPrice(payMoney, "100");
				money=PriceUtils.getInstance().seePrice(money);
				payMoney=PriceUtils.getInstance().gteDividePrice(money, "100");
				System.err.println(payMoney);
				create(money);
			} else {
				CommonUtils.getInstance().initToast(context, "请输入要充值的金额");
			}
			}
			break;
		case R.id.layout_xieyi:
			CommonUtils.getInstance().toH5(this,agreementurl, "服务协议", "","",true,false);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_balance);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		LinearLayout page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		balance_tv_mymoney = (TextView) findViewById(R.id.balance_tv_mymoney);

		balance_myListView = (MyListView) findViewById(R.id.balance_myListView);
		layout_xieyi = (LinearLayout) findViewById(R.id.layout_xieyi);
		balance_tv_book = (TextView) findViewById(R.id.balance_tv_book);
		
		xieyi_cb= (CheckBox) findViewById(R.id.xieyi_cb);
	}

	@Override
	protected void setListener() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		balance_tv_book.setOnClickListener(this);
		layout_xieyi.setOnClickListener(this);
		pagetop_tv_name.setText("我的余额");
		pagetop_tv_you.setText("余额明细");
		pagetop_tv_you.setTextColor(getResources().getColor(R.color.text_contents_color));
		Intent intent = getIntent();
		balance = intent.getStringExtra("balance");
		skip= intent.getStringExtra("skip");
		balance_tv_mymoney.setText(balance);
		xieyi_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					balance_tv_book.setBackgroundColor(getResources().getColor(R.color.gray_bg));
				} else {
					balance_tv_book.setBackgroundColor(getResources().getColor(R.color.btn_blue_color));
				}

			}
		});
	}

	String payMoney;
	private final int SHOW = 2;
	public final static int INPUT = 1;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW:
				int position = msg.arg1;
				for (int i = 0; i < mList.size(); i++) {
					mList.get(i).setSelect(false);
					if (i == position) {
						payMoney = mList.get(position).getInputMoney();
						mList.get(position).setSelect(true);
					}
				}
				mUserBalanceListAdapter.setaList(mList);
				mUserBalanceListAdapter.notifyDataSetChanged();
				break;
			case INPUT:
				int positio = msg.arg1;
				Editable cont = (Editable) msg.obj;
				PriceUtils.getInstance().judge(7, 2,cont);
				mList.get(positio).setInputMoney(cont.toString());
				payMoney = mList.get(positio).getInputMoney();
				break;
			case 5:
				if (payDialog != null) {
					payDialog.selectPayDialog.dismiss();
				}
				break;
			}
		}
	};
	private String uid;
	private ProgressDialog mDialog;
	protected PayDialog payDialog;

	@Override
	protected void processLogic() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		new UserBalanceListBusiness(this, mhashmap, new BalanceListCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						mList = (ArrayList<UserBalanceListInfo>) dataMap.get("mList");
						agreementurl = (String) dataMap.get("agreementurl");
						balance = (String) dataMap.get("balance");
						balance = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(balance));
						balance_tv_mymoney.setText(balance);
						if (mList != null && mList.size() > 0) {
							payMoney = mList.get(0).getRechargeMoney();
							mUserBalanceListAdapter = new UserBalanceListAdapter(context, mList, mHandler, SHOW);
							balance_myListView.setAdapter(mUserBalanceListAdapter);
						}

					}
				}
				firstComIn = false;
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	protected void create(String money) {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("payMoney", money);
		System.err.println(mhashmap);
		new CreateOrdersBuyBalanceBusiness(this, mhashmap, new GetCreateOrdersCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String code = (String) dataMap.get("code");
					if (code.equals("200")) {
						String oid = (String) dataMap.get("oid");
						payDialog = new PayDialog(context, mHandler, Constans.showPayDialogByBuyBalance, oid, uid, "",
								"", payMoney);
						payDialog.getPayDialog();

					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(context, getString(R.string.net_tishi));
				}
				CommonUtils.getInstance().setClearCacheDialog(mDialog);
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

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

	boolean firstComIn = true;

	@Override
	protected void onResume() {
		if (!firstComIn) {
			processLogic();
		}
		super.onResume();
	}

	private void doback() {
		if(!TextUtils.isEmpty(skip)&&skip.equals("DIALOG")){
			Intent intent = new Intent();
			setResult(Constans.showPayDialogByBuyBalanceResult, intent);
		}
		finish();
	}

	public void onBackPressed() {
		doback();
	}
}
