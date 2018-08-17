package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.business.CreateOrdersBuyCardBusiness;
import com.bestdo.bestdoStadiums.business.CreateOrdersBuyCardBusiness.GetCreateOrdersCallback;
import com.bestdo.bestdoStadiums.control.adapter.SearchKeyWordAdapter;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCardsBuyInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PayDialog;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-26 下午3:48:57
 * @Description 类描述：创建订单-百动卡购买
 */
public class CreatOrdersBuyCardActivity extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout page_top_layout;
	private LinearLayout pagetop_layout_back;

	private ProgressDialog mDialog;
	private SharedPreferences bestDoInfoSharedPrefs;

	private TextView createorderbuycard_tv_name;
	private TextView createorderbuycard_tv_detail;
	private TextView createorderbuycard_tv_numdes;
	private TextView createorderbuycard_tv_pepolenum;
	private TextView createorderbuycard_tv_numadd;
	private TextView createorderbuycard_tv_orderallmoney;
	private TextView createorderbuycard_tv_bottom;
	private String uid;
	private String price;
	private int selectNum = 1;// 人数
	private String oid;
	private String remind;// 联系人手机号
	private String card_batch_id;
	private TextView createorderbuycard_tv_cardexplain;
	private String allprice;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.createorderbuycard_tv_bottom:
			createOrder();
			break;
		case R.id.createorderbuycard_tv_numadd:
			if (selectNum < 5) {
				selectNum++;
				createorderbuycard_tv_pepolenum.setText("" + selectNum);
				updateShowPayInfo();
			} else {
				CommonUtils.getInstance().initToast(context, "购买数量不能大于5");
			}
			break;
		case R.id.createorderbuycard_tv_numdes:
			if (selectNum > 1) {
				selectNum--;
				createorderbuycard_tv_pepolenum.setText("" + selectNum);
				updateShowPayInfo();
			} else {
				CommonUtils.getInstance().initToast(context, "购买数量不能小于1");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.creat_orders_buycard);
		CommonUtils.getInstance().addActivity(this);
		CommonUtils.getInstance().addPayPageActivity(this);
	}

	@Override
	protected void findViewById() {
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		createorderbuycard_tv_name = (TextView) findViewById(R.id.createorderbuycard_tv_name);
		createorderbuycard_tv_detail = (TextView) findViewById(R.id.createorderbuycard_tv_detail);
		createorderbuycard_tv_numdes = (TextView) findViewById(R.id.createorderbuycard_tv_numdes);
		createorderbuycard_tv_pepolenum = (TextView) findViewById(R.id.createorderbuycard_tv_pepolenum);
		createorderbuycard_tv_numadd = (TextView) findViewById(R.id.createorderbuycard_tv_numadd);
		createorderbuycard_tv_orderallmoney = (TextView) findViewById(R.id.createorderbuycard_tv_orderallmoney);

		createorderbuycard_tv_cardexplain = (TextView) findViewById(R.id.createorderbuycard_tv_cardexplain);
		createorderbuycard_tv_bottom = (TextView) findViewById(R.id.createorderbuycard_tv_bottom);

	}

	@Override
	protected void setListener() {
		CommonUtils.getInstance().setViewTopHeigth(context, page_top_layout);
		pagetop_tv_name.setText(getResources().getString(R.string.creat_orders_title));
		pagetop_layout_back.setOnClickListener(this);
		createorderbuycard_tv_numdes.setOnClickListener(this);
		createorderbuycard_tv_numadd.setOnClickListener(this);
		createorderbuycard_tv_bottom.setOnClickListener(this);
		initDate();
	}

	private void initDate() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");// 用户id
		remind = bestDoInfoSharedPrefs.getString("mobile", "");// 联系人手机号
		Intent intent = getIntent();
		UserCardsBuyInfo mUserCardsBuyInfo = (UserCardsBuyInfo) intent.getSerializableExtra("mUserCardsBuyInfo");

		if (mUserCardsBuyInfo != null) {
			String nameString = mUserCardsBuyInfo.getName();
			String detail = mUserCardsBuyInfo.getDetail();
			card_batch_id = mUserCardsBuyInfo.getCard_batch_id();
			price = mUserCardsBuyInfo.getPrice();
			price = PriceUtils.getInstance().gteDividePrice(price, "100");
			price = PriceUtils.getInstance().seePrice(price);
			if (!TextUtils.isEmpty(CommonUtils.getInstance().intntStatus)
					&& CommonUtils.getInstance().intntStatus.equals("ZhunXiangCard")) {
				String card_explain = mUserCardsBuyInfo.getCard_explain();
				if (card_explain.contains("|")) {
					card_explain = DatesUtils.getInstance().getZhuanHuan(card_explain, "|", "\t\n");
				}
				createorderbuycard_tv_cardexplain.setText(card_explain);
			}
			createorderbuycard_tv_name.setText(nameString);
			createorderbuycard_tv_detail.setText(detail);
			updateShowPayInfo();
		}
	}

	private void updateShowPayInfo() {
		  allprice = PriceUtils.getInstance().gteMultiplySumPrice(price, "" + selectNum);
		createorderbuycard_tv_orderallmoney.setText(getResources().getString(R.string.unit_fuhao_money) + allprice);
	}

	@Override
	protected void processLogic() {

	}

	/**
	 * 确认是否关闭payDialog
	 */
	private final int OFFPAYDIALOG = 5;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case OFFPAYDIALOG:
				payDialog.selectPayDialog.dismiss();
				// Intent intent = new Intent(CreatOrdersActivity.this,
				// UserOrderDetailsActivity.class);
				// intent.putExtra("oid", oid + "");// 订单号
				// intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				// startActivity(intent);
				finish();
				CommonUtils.getInstance().setPageIntentAnim(null, CreatOrdersBuyCardActivity.this);
				break;

			default:
				break;
			}
		}

	};

	String morenOrvip;
	private HashMap<String, String> mhashmap;
	protected PayDialog payDialog;

	private void createOrder() {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("num", selectNum + "");
		mhashmap.put("card_batch_id", card_batch_id + "");
		System.err.println(mhashmap);
		new CreateOrdersBuyCardBusiness(this, mhashmap, new GetCreateOrdersCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						oid = (String) dataMap.get("oid");
						payDialog = new PayDialog(CreatOrdersBuyCardActivity.this, mHandler,
								Constans.showPayDialogByBuyCard, oid, uid, remind, selectNum + "",allprice);
						payDialog.card_batch_id = card_batch_id;
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		RequestUtils.cancelAll(this);
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
			// if (payDialog != null && payDialog.selectPayDialog != null
			// && payDialog.selectPayDialog.isShowing()) {
			// CommonUtils.getInstance().defineBackPressed(
			// CreatOrdersBuyCardActivity.this, mHandler, "exit_pay");
			// } else {
			nowFinish();
			// }
		}
		return false;
	}
}
