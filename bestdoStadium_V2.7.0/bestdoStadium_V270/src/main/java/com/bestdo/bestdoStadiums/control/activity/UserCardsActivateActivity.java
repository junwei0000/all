package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.UserCardsDetailBusiness;
import com.bestdo.bestdoStadiums.business.UserCardsDetailBusiness.GetUserCardsDetailCallback;
import com.bestdo.bestdoStadiums.model.UserCardsDetailInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-8 下午3:29:04
 * @Description 类描述：绑定卡,卡激活
 */
public class UserCardsActivateActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private Button click_btn;
	private SupplierEditText user_card_jihuo_edit;
	private HashMap<String, String> mhashmap;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String intent_from = "";

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.click_btn:
			String password = user_card_jihuo_edit.getText().toString();
			CommonUtils.getInstance().closeSoftInput(UserCardsActivateActivity.this);
			if (!TextUtils.isEmpty(password)) {
				jihuoProcessLogic(password);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_card_activate);
		CommonUtils.getInstance().addActivity(this);
		Intent in = getIntent();
		intent_from = in.getStringExtra("intent_from");
		if (!TextUtils.isEmpty(intent_from) && intent_from.equals("CreateOrderSelectCardsActivity")) {
			CommonUtils.getInstance().addBuyCardsList(UserCardsActivateActivity.this);
		}
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		click_btn = (Button) findViewById(R.id.click_btn);
		user_card_jihuo_edit = (SupplierEditText) findViewById(R.id.user_card_jihuo_edit);
		pagetop_tv_name.setText("激活卡");
		click_btn.setText("立即激活");
	}

	@Override
	protected void setListener() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		pagetop_layout_back.setOnClickListener(this);
		click_btn.setOnClickListener(this);
		user_card_jihuo_edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(user_card_jihuo_edit.getText())) {
					click_btn.setTextColor(getResources().getColor(R.color.white));
				} else {
					click_btn.setTextColor(getResources().getColor(R.color.btn_noclick_color));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	protected void processLogic() {

	}

	private void jihuoProcessLogic(String password) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("password", password.trim());
		new UserCardsDetailBusiness(UserCardsActivateActivity.this, mhashmap,
				Constans.getInstance().getCardDetailInfoByAbstractPage, new GetUserCardsDetailCallback() {
					@Override
					public void afterDataGet(HashMap<String, Object> dataMap) {
						if (dataMap != null) {
							String status = (String) dataMap.get("status");
							// 激活期限已过：402
							// 未到激活期限：403
							// 卡已被禁用：404
							if (status.equals("200") || status.equals("") || status.equals("401")) {
								String jihuouid = (String) dataMap.get("jihuouid");
								if (TextUtils.isEmpty(jihuouid)) {
									jihuouid = uid;
								}
								if (status.equals("401") && !jihuouid.equals(uid)) {
									// 用户不同，被其他人激活提醒,是自己跳详情页，不是自己提示错误信息
									CommonUtils.getInstance().initToast(UserCardsActivateActivity.this,
											(String) dataMap.get("data"));

								} else {

									String cardId = (String) dataMap.get("cardId");
									Intent intent = new Intent(UserCardsActivateActivity.this,
											UserCardDetailActivity.class);

									intent.putExtra("skipToCardDetailStatus",
											Constans.getInstance().skipToCardDetailByAbstractPage);
									intent.putExtra("status", status);
									intent.putExtra("user_card_ticket_id", cardId);
									intent.putExtra("intent_from", intent_from);
									intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
									startActivity(intent);
									finish();
									CommonUtils.getInstance().setPageIntentAnim(intent, UserCardsActivateActivity.this);
								}
							} else {
								CommonUtils.getInstance().initToast(UserCardsActivateActivity.this,
										(String) dataMap.get("data"));
							}

						}
						CommonUtils.getInstance().setOnDismissDialog(mDialog);
						// 清除缓存
						CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
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
