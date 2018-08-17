package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.UserCardsDetailBusiness;
import com.bestdo.bestdoStadiums.business.UserCardsDetailLoadShouZhiBusiness;
import com.bestdo.bestdoStadiums.business.UserCardsDetailBusiness.GetUserCardsDetailCallback;
import com.bestdo.bestdoStadiums.business.UserCardsDetailLoadShouZhiBusiness.GetUserCardsDetailLoadShouZhiCallback;
import com.bestdo.bestdoStadiums.control.adapter.UserCardZhiFuDetailAdapter;
import com.bestdo.bestdoStadiums.model.UserCardsDetaiShouZhilInfo;
import com.bestdo.bestdoStadiums.model.UserCardsDetailInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-8 下午3:36:37
 * @Description 类描述：卡详情
 */
public class UserCardDetailActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back, user_card_detail_top_gone_lin, user_card_detail_top_lin;
	private TextView pagetop_tv_name, user_card_detail_top_description;
	private TextView user_card_detail_name2, user_card_detail_num2, user_card_detail_youxiaoqi2,
			user_card_detail_status2, user_card_detail_activation_time2, user_card_detail_category2,
			user_card_detail_amount2;
	private ImageView user_card_detail_top_img;
	private TextView user_card_detail_top_status;
	private TextView user_carddetail_tv_bottom;
	private LinearLayout user_carddetail_layout_shouzhi;
	private LinearLayout user_carddetail_layout_bottom;
	private MyListView user_card_detail_lv_shouzhi;

	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid;
	private String cardNo;
	private String user_card_ticket_id;
	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	private String cardStatus;
	private String skipToCardDetailStatus;
	private String card_status;
	private TextView user_card_detail_banlace;
	private ScrollView user_card_detail_top_scrollView;
	private TextView user_card_detail_category;
	private TextView user_card_detail_banlace2;
	protected String isShowTel = "", showTel = "";
	private String intent_from;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:

			nowFinish();
			break;
		case R.id.user_carddetail_tv_bottom:
			if (!TextUtils.isEmpty(isShowTel) && isShowTel.equals("1")) {
				defineBackPressed(this);

			}
			if (intent_from.equals("CreateOrderSelectCardsActivity")) {

				// 入口3个人创建订单页
				CommonUtils.getInstance().exitBuyCardsList();
				nowFinish();
			} else if (intent_from.equals("UserCenterActivity")) {
				// 用户中心 入口1卡密、 入口2卡券中心跳转首页
				CommonUtils.getInstance().skipMainActivity(this);
				nowFinish();
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_card_detail);
		CommonUtils.getInstance().addActivity(this);
		CommonUtils.getInstance().addBuyCardsList(this);
		Intent intent = getIntent();
		intent_from = intent.getStringExtra("intent_from");
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		user_card_detail_top_scrollView = (ScrollView) findViewById(R.id.user_card_detail_top_scrollView);
		user_card_detail_top_description = (TextView) findViewById(R.id.user_card_detail_top_description);
		user_card_detail_name2 = (TextView) findViewById(R.id.user_card_detail_name2);
		user_card_detail_num2 = (TextView) findViewById(R.id.user_card_detail_num2);
		user_card_detail_youxiaoqi2 = (TextView) findViewById(R.id.user_card_detail_youxiaoqi2);
		user_card_detail_status2 = (TextView) findViewById(R.id.user_card_detail_status2);
		user_card_detail_activation_time2 = (TextView) findViewById(R.id.user_card_detail_activation_time2);
		user_card_detail_category2 = (TextView) findViewById(R.id.user_card_detail_category2);
		user_card_detail_category = (TextView) findViewById(R.id.user_card_detail_category);
		user_card_detail_banlace2 = (TextView) findViewById(R.id.user_card_detail_banlace2);
		user_card_detail_amount2 = (TextView) findViewById(R.id.user_card_detail_amount2);
		user_card_detail_banlace = (TextView) findViewById(R.id.user_card_detail_banlace);
		user_card_detail_top_img = (ImageView) findViewById(R.id.user_card_detail_top_img);
		user_card_detail_top_gone_lin = (LinearLayout) findViewById(R.id.user_card_detail_top_gone_lin);
		user_card_detail_top_lin = (LinearLayout) findViewById(R.id.user_card_detail_top_lin);

		user_card_detail_lv_shouzhi = (MyListView) findViewById(R.id.user_card_detail_lv_shouzhi);
		user_carddetail_layout_shouzhi = (LinearLayout) findViewById(R.id.user_carddetail_layout_shouzhi);
		user_carddetail_layout_bottom = (LinearLayout) findViewById(R.id.user_carddetail_layout_bottom);

		user_card_detail_top_status = (TextView) findViewById(R.id.user_card_detail_top_status);
		user_carddetail_tv_bottom = (TextView) findViewById(R.id.user_carddetail_tv_bottom);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		user_carddetail_tv_bottom.setOnClickListener(this);
		initData();
	}

	private void initData() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		Intent intent = getIntent();
		if (intent != null) {
			user_card_ticket_id = intent.getStringExtra("user_card_ticket_id");
			skipToCardDetailStatus = intent.getStringExtra("skipToCardDetailStatus");
			card_status = intent.getStringExtra("status");
		}
		if (skipToCardDetailStatus.equals(Constans.getInstance().skipToCardDetailByListPage)) {
			user_card_detail_top_lin.setVisibility(View.GONE);
			pagetop_tv_name.setText("卡券详情");
			user_card_detail_top_description.setText("已激活卡信息");
			user_carddetail_tv_bottom.setText("立即使用卡");
		} else {
			pagetop_tv_name.setText("激活卡");
			user_card_detail_top_description.setText("已激活卡信息");
			user_carddetail_tv_bottom.setText("立即使用卡");
		}

	}

	protected void processLogic() {

		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("cardId", user_card_ticket_id);
		new UserCardsDetailBusiness(UserCardDetailActivity.this, mhashmap,
				Constans.getInstance().getCardDetailInfoByCardDePage, new GetUserCardsDetailCallback() {
					@Override
					public void afterDataGet(HashMap<String, Object> dataMap) {
						if (dataMap != null) {
							String status = (String) dataMap.get("status");
							if (status.equals("200")) {
								UserCardsDetailInfo u = (UserCardsDetailInfo) dataMap.get("UserCardsDetailInfo");
								isShowTel = (String) dataMap.get("isShowTel");
								showTel = (String) dataMap.get("showTel");
								if (u != null) {
									showCardInfo(u);
									cardNo = u.getCardNo();
									mloadHandler.sendEmptyMessage(LOADSHOUZHI);
								}
							} else if (status.equals("403")) {
								UserLoginBack403Utils.getInstance().showDialogPromptReLogin(context);
							} else {
								CommonUtils.getInstance().initToast(UserCardDetailActivity.this,
										(String) dataMap.get("data"));
							}
						} else {
							CommonUtils.getInstance().initToast(UserCardDetailActivity.this, "获取卡信息失败！");
						}
						CommonUtils.getInstance().setOnDismissDialog(mDialog);
						// 清除缓存
						CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
					}

				});

	}

	/**
	 * 加载收支记录
	 */
	private final int LOADSHOUZHI = 1;
	Handler mloadHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOADSHOUZHI:
				loadShouZhi();
				break;
			}
		}
	};
	private ArrayList<UserCardsDetaiShouZhilInfo> mList;

	private void loadShouZhi() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("page", "1");
		mhashmap.put("page_size", "150");
		mhashmap.put("cardNo", cardNo);
		System.err.println(mhashmap);
		mList = new ArrayList<UserCardsDetaiShouZhilInfo>();

		new UserCardsDetailLoadShouZhiBusiness(this, mhashmap, mList, new GetUserCardsDetailLoadShouZhiCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null && dataMap.get("status").equals("200")) {

					int totalRows = (Integer) dataMap.get("totalRows");
					if (totalRows == 0) {
						user_carddetail_layout_shouzhi.setVisibility(View.GONE);
					} else {
						mList = (ArrayList<UserCardsDetaiShouZhilInfo>) dataMap.get("mList");
						UserCardZhiFuDetailAdapter adapter = new UserCardZhiFuDetailAdapter(context, mList);
						user_card_detail_lv_shouzhi.setAdapter(adapter);
					}

				} else {
					user_carddetail_layout_shouzhi.setVisibility(View.GONE);
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
				user_card_detail_top_scrollView.fullScroll(ScrollView.FOCUS_UP);
				user_card_detail_top_scrollView.setVisibility(View.VISIBLE);
			}
		});
	}

	protected void showCardInfo(UserCardsDetailInfo u) {
		cardStatus = u.getStatus2();
		user_card_detail_name2.setText(u.getCardTypeName());
		user_card_detail_num2.setText(u.getCardNo());
		user_card_detail_youxiaoqi2.setText(u.getUseEndTime());
		user_card_detail_activation_time2.setText(u.getActiveTime());
		String accountTyp = u.getAccountType();
		if (!TextUtils.isEmpty(accountTyp) && accountTyp.equals("TIMES")) {
			user_card_detail_category.setText("卡券总次：");
			user_card_detail_banlace2.setText("卡券余次：");
			if (!TextUtils.isEmpty(u.getAmount())) {
				user_card_detail_category2.setText(u.getAmount() + "次");
			}
			if (!TextUtils.isEmpty(u.getBalance())) {
				user_card_detail_banlace.setText(u.getBalance() + "次");
			}

		} else {

			if (!TextUtils.isEmpty(u.getAmount())) {
				user_card_detail_category2.setText(getString(R.string.unit_fuhao_money) + u.getAmount());
			}
			if (!TextUtils.isEmpty(u.getBalance())) {
				user_card_detail_banlace.setText(getString(R.string.unit_fuhao_money) + u.getBalance());
			}
		}
		String mBuyableMer = u.getBuyableMerList();
		if (!TextUtils.isEmpty(mBuyableMer) && !mBuyableMer.equals("null")) {
			user_card_detail_amount2.setText(mBuyableMer);
		} else {
			user_card_detail_amount2.setText("暂无");
		}
		// if (cardStatus.equals("NOTDISABLE")) {
		// user_card_detail_status2.setText("正常");
		// } else {
		// user_card_detail_status2.setText("已过期");
		// }
		user_card_detail_status2.setText(cardStatus);
		setActivateStatusView();
		user_card_detail_top_gone_lin.setVisibility(View.VISIBLE);
		user_carddetail_tv_bottom.setVisibility(View.VISIBLE);
		if (skipToCardDetailStatus.equals(Constans.getInstance().skipToCardDetailByQiYEPage)) {
			user_carddetail_tv_bottom.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置激活状态
	 */
	private void setActivateStatusView() {

		if (skipToCardDetailStatus.equals(Constans.getInstance().skipToCardDetailByAbstractPage)
				&& card_status != null) {
			user_card_detail_top_lin.setVisibility(View.VISIBLE);
			if (card_status.equals("401")) {
				user_card_detail_top_img
						.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.user_card_jiho_fail));
				user_card_detail_top_status.setText("该卡已被激活！");
			} else if (card_status.equals("200")) {
				user_card_detail_top_status.setText("激活成功！");
				user_card_detail_top_img
						.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.user_card_jiho_success));
			} else if (card_status.equals("402")) {
				user_card_detail_top_status.setText("激活期限已过");
				user_card_detail_top_img
						.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.user_card_jiho_fail));
			} else if (card_status.equals("403")) {
				user_card_detail_top_status.setText("未到激活期限");
				user_card_detail_top_img
						.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.user_card_jiho_fail));
			} else if (card_status.equals("404")) {
				user_card_detail_top_status.setText("卡已被禁用");
				user_card_detail_top_img
						.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.user_card_jiho_fail));
			}
		}
	}

	/**
	 * 弹出电话提示
	 * 
	 * @param content
	 * @param mHandler
	 * @param activitypath
	 *            exit退出,cancel注销
	 */
	public void defineBackPressed(final Activity content) {
		final MyDialog selectDialog = new MyDialog(content, R.style.dialog, R.layout.dialog_myexit);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView myexit_text_title = (TextView) selectDialog.findViewById(R.id.myexit_text_title);
		TextView text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);// 取消
		final TextView text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);// 确定
		final String exit = Constans.getInstance().exit;
		final String cancel = Constans.getInstance().cancel;
		final String exit_pay = Constans.getInstance().exit_pay;
		myexit_text_title.setText("该卡对应的场馆为人工预订,是否拨打百动客服电话" + showTel + "?");
		text_sure.setText("呼叫");
		text_off.setText("取消");
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectDialog.dismiss();
			}
		});
		text_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// text_sure.setBackgroundColor(R.drawable.myexit_sureclick_bg);
				selectDialog.dismiss();
				CommonUtils.getInstance().getPhoneToKey(content, showTel);
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

	@Override
	protected void onRestart() {
		super.onRestart();

	}
}
