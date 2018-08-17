/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.R.id;
import com.bestdo.bestdoStadiums.business.CashBookClubYearBudgetListBusiness;
import com.bestdo.bestdoStadiums.business.CashBookClubYearBudgetListBusiness.ClubYearBudgetListCallback;
import com.bestdo.bestdoStadiums.business.CashSetYearBudgetBusiness;
import com.bestdo.bestdoStadiums.business.CashSetYearBudgetBusiness.CashSetYearBudgetCallback;
import com.bestdo.bestdoStadiums.control.adapter.CashYearBudgetAdapter;
import com.bestdo.bestdoStadiums.model.CashClubYearbudgetInfo;
import com.bestdo.bestdoStadiums.model.CashMyClubInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.utils.TimeUtils;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-28 下午3:48:09
 * @Description 类描述：记事本--设置年度预算
 */
public class CashBookSetYearlybudgetActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private SupplierEditText setyerlybudget_et_money;
	private Button click_btn;
	private MyListView setyerlybudget_myListView;
	private HashMap<String, String> mHashMap;
	private String club_id;
	protected ArrayList<CashClubYearbudgetInfo> mYearbudgetList;
	private String uid;
	private String yearBudge;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			doback();
			break;
		case R.id.click_btn:
			String budget = setyerlybudget_et_money.getText().toString();
			if (!TextUtils.isEmpty(budget)) {
				double money = Double.parseDouble(budget);
				if (money > 0) {
					setYearBudget(budget);
				} else {
					CommonUtils.getInstance().initToast(context, "预算金额大于0");
				}
			} else {
				CommonUtils.getInstance().initToast(context, "请输入预算金额");
			}

			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.cashbook_setyearlybudget);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);

		setyerlybudget_et_money = (SupplierEditText) findViewById(R.id.setyerlybudget_et_money);
		click_btn = (Button) findViewById(R.id.click_btn);
		setyerlybudget_myListView = (MyListView) findViewById(R.id.setyerlybudget_myListView);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		pagetop_tv_name.setOnClickListener(this);
		click_btn.setOnClickListener(this);
		setyerlybudget_et_money.addTextChangedListener(new TextWatcher() {
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
		pagetop_tv_name.setText("年度预算设置");
		click_btn.setText("确定");
		click_btn.setTextColor(getResources().getColor(R.color.white));
		click_btn.setBackgroundResource(R.drawable.corners_bg_blue);
		Intent intent = getIntent();
		club_id = intent.getStringExtra("club_id");
		uid = intent.getStringExtra("uid");
		yearBudge = intent.getStringExtra("yearBudge");
		setyerlybudget_et_money.setText(yearBudge);
		initSelection();
	}

	/**
	 * 初始化光标的位置
	 */
	private void initSelection() {
		CharSequence text = setyerlybudget_et_money.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}

	@Override
	protected void processLogic() {
		mHashMap = new HashMap<String, String>();
		mHashMap.put("club_id", club_id);
		new CashBookClubYearBudgetListBusiness(this, mHashMap, new ClubYearBudgetListCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String status = (String) dataMap.get("code");
					if (status.equals("200")) {
						mYearbudgetList = (ArrayList<CashClubYearbudgetInfo>) dataMap.get("mList");
						if (mYearbudgetList != null && mYearbudgetList.size() > 0) {
							CashYearBudgetAdapter mCashYearBudgetAdapter = new CashYearBudgetAdapter(context,
									mYearbudgetList);
							setyerlybudget_myListView.setAdapter(mCashYearBudgetAdapter);
						}
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mHashMap, dataMap);
			}
		});

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

	private void setYearBudget(String budget) {
		showDilag();
		String year = DatesUtils.getInstance().getNowTime("yyyy");
		mHashMap = new HashMap<String, String>();
		mHashMap.put("uid", uid);
		mHashMap.put("club_id", club_id);
		mHashMap.put("year", "" + year);
		mHashMap.put("budget", budget);
		new CashSetYearBudgetBusiness(this, mHashMap, new CashSetYearBudgetCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					String msg = (String) dataMap.get("msg");
					CommonUtils.getInstance().initToast(context, msg);
					if (status.equals("200")) {
						Intent intent = new Intent();
						setResult(Constans.getInstance().ForResult4, intent);
						doback();
					}
				}
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
