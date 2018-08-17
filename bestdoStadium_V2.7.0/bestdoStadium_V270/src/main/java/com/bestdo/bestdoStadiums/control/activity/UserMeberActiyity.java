package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.business.CreateMemberOrderBusiness;
import com.bestdo.bestdoStadiums.business.CreateMemberOrderBusiness.CreateMemberOrderCallback;
import com.bestdo.bestdoStadiums.business.GetMeMberListBusiness;
import com.bestdo.bestdoStadiums.business.GetMeMberListBusiness.GetMeMberListCallback;
import com.bestdo.bestdoStadiums.control.adapter.UserBuyMberAdapter;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberListInfo;
import com.bestdo.bestdoStadiums.model.UserMemberBuyNoteInfo;
import com.bestdo.bestdoStadiums.model.UserMemberInfo;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.utils.PayUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.umeng.analytics.MobclickAgent;
import com.unionpay.UPPayAssistEx;
import com.unionpay.UPQuerySEPayInfoCallback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午4:28:11
 * @Description 类描述：会员中心
 */
public class UserMeberActiyity extends BaseActivity {
	private LinearLayout pagetop_layout_back;
	private TextView pagetop_tv_name;
	private TextView user_buy_member_btn;
	public static MyJoinClubmenuInfo myJoinClubmenuInfo;
	private ImageLoader asyncImageLoader;
	private TextView stadiumdetails_tv_book;
	private SharedPreferences bestDoInfoSharedPrefs;
	private TextView pagetop_tv_you;
	private MyListView mListView;
	private CheckBox zhifubao_pay_checkbox;
	private CheckBox weixin_pay_checkbox;
	private CheckBox yinlian_pay_checkbox;
	private TextView user_putong_member_note_title_right;
	private TextView user_putong_member_note_title;
	private ImageView user_putong_member_note_img;
	private ImageView user_putong_member_note_img2;
	private TextView user_putong_member_note_detle;
	private TextView user_putong_member_note_detle2;
	private TextView user_putong_member_note;
	private TextView user_putong_member_note2;
	HashMap<String, String> mhashmap;
	private String uid;
	private String price;
	private String member_package_id;
	private String pay_type = "yinlian";
	private String member_level = "0";
	private TextView usrecenter_tv_usermember_info;
	private String levelName;
	private String levelUrl;
	private CheckBox huawei_pay_checkbox;
	private LinearLayout huawei_pay_lin;

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_tv_you:
			Intent intent2 = new Intent(this, UserMeberCashActiyity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, this);
			break;
		case R.id.stadiumdetails_tv_book:
			goBuy();
			break;
		case R.id.yinlian_pay_lin:
		case R.id.yinlian_pay_checkbox:
			yinlian_pay_checkbox.setChecked(true);
			huawei_pay_checkbox.setChecked(false);
			zhifubao_pay_checkbox.setChecked(false);
			weixin_pay_checkbox.setChecked(false);
			pay_type = "yinlian";
			break;
		case R.id.zhifubao_pay_lin:
		case R.id.zhifubao_pay_checkbox:
			yinlian_pay_checkbox.setChecked(false);
			huawei_pay_checkbox.setChecked(false);
			zhifubao_pay_checkbox.setChecked(true);
			weixin_pay_checkbox.setChecked(false);
			pay_type = "zhifubao";
			break;
		case R.id.weixin_pay_lin:
		case R.id.weixin_pay_checkbox:
			yinlian_pay_checkbox.setChecked(false);
			huawei_pay_checkbox.setChecked(false);
			zhifubao_pay_checkbox.setChecked(false);
			weixin_pay_checkbox.setChecked(true);
			pay_type = "weixin";
			break;
		case R.id.huawei_pay_lin:
		case R.id.huawei_pay_checkbox:
			yinlian_pay_checkbox.setChecked(false);
			huawei_pay_checkbox.setChecked(true);
			zhifubao_pay_checkbox.setChecked(false);
			weixin_pay_checkbox.setChecked(false);
			pay_type = "huawei";
			break;
		case R.id.usrecenter_tv_usermember_info:
			CommonUtils.getInstance().toH5(this, levelUrl, "", "", false);
			break;
		default:
			break;
		}
	}

	protected void loadViewLayout() {
		setContentView(R.layout.user_meber);
		CommonUtils.getInstance().addActivity(this);
	}

	protected void findViewById() {
		LinearLayout page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		page_top_layout.setBackgroundColor(getResources().getColor(R.color.btn_blue_color));
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		ImageView pagetop_iv_back = (ImageView) findViewById(R.id.pagetop_iv_back);
		pagetop_iv_back.setBackgroundResource(R.drawable.back_moren);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText("我的会员");
		pagetop_tv_name.setTextColor(getResources().getColor(R.color.white));
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		pagetop_tv_you.setText("会员兑换");
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setTextColor(getResources().getColor(R.color.white));
		user_buy_member_btn = (TextView) findViewById(R.id.user_buy_member_btn);
		stadiumdetails_tv_book = (TextView) findViewById(R.id.stadiumdetails_tv_book);
		stadiumdetails_tv_book.setText("确认支付");
		asyncImageLoader = new ImageLoader(this, "headImg");
	}

	protected void setListener() {
		pagetop_tv_you.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		stadiumdetails_tv_book.setOnClickListener(this);
		onInit();
//		intPay();
	}

	public void onInit() {
		mListView = (MyListView) findViewById(R.id.user_meber_myListView);
		zhifubao_pay_checkbox = (CheckBox) findViewById(R.id.zhifubao_pay_checkbox);
		weixin_pay_checkbox = (CheckBox) findViewById(R.id.weixin_pay_checkbox);
		yinlian_pay_checkbox = (CheckBox) findViewById(R.id.yinlian_pay_checkbox);
		huawei_pay_checkbox = (CheckBox) findViewById(R.id.huawei_pay_checkbox);
		user_putong_member_note_title_right = (TextView) findViewById(R.id.user_putong_member_note_title_right);
		user_putong_member_note_title = (TextView) findViewById(R.id.user_putong_member_note_title);
		user_putong_member_note_img = (ImageView) findViewById(R.id.user_putong_member_note_img);
		user_putong_member_note_img2 = (ImageView) findViewById(R.id.user_putong_member_note_img2);
		user_putong_member_note_detle = (TextView) findViewById(R.id.user_putong_member_note_detle);
		user_putong_member_note_detle2 = (TextView) findViewById(R.id.user_putong_member_note_detle2);
		user_putong_member_note = (TextView) findViewById(R.id.user_putong_member_note);
		user_putong_member_note2 = (TextView) findViewById(R.id.user_putong_member_note2);
		LinearLayout yinlian_pay_lin = (LinearLayout) findViewById(R.id.yinlian_pay_lin);
		LinearLayout zhifubao_pay_lin = (LinearLayout) findViewById(R.id.zhifubao_pay_lin);
		LinearLayout weixin_pay_lin = (LinearLayout) findViewById(R.id.weixin_pay_lin);
		huawei_pay_lin = (LinearLayout) findViewById(R.id.huawei_pay_lin);
		usrecenter_tv_usermember_info = (TextView) findViewById(R.id.usrecenter_tv_usermember_info);
		yinlian_pay_lin.setOnClickListener(this);
		yinlian_pay_checkbox.setOnClickListener(this);
		huawei_pay_lin.setOnClickListener(this);
		huawei_pay_checkbox.setOnClickListener(this);
		zhifubao_pay_lin.setOnClickListener(this);
		zhifubao_pay_checkbox.setOnClickListener(this);
		usrecenter_tv_usermember_info.setOnClickListener(this);
		weixin_pay_lin.setOnClickListener(this);
		weixin_pay_checkbox.setOnClickListener(this);
		Intent intent = getIntent();
		levelName = intent.getStringExtra("levelName");
		levelUrl = intent.getStringExtra("levelUrl");

	}

	private void intPay() {
		UPPayAssistEx.getSEPayInfo(this, new UPQuerySEPayInfoCallback() {
			/**
			 * SEName —— 手机pay名称，如表1 seType —— 与手机pay名称对应的类别，如表1 cardNumbers
			 * ——卡数量
			 */
			@Override
			public void onResult(String SEName, String seType, int cardNumbers, Bundle reserved) {
				System.err.println("onResult  " + seType + " " + cardNumbers);
				if (!TextUtils.isEmpty(seType) && seType.equals("04")) {
					huawei_pay_lin.setVisibility(View.VISIBLE);
				}
				CommonUtils.getInstance().initToast(context, "onResult  " +SEName+"   "+ seType + " " + cardNumbers);
			}

			@Override
			public void onError(String SEName, String seType, String errorCode, String errorDesc) {
				System.err.println("onError  " + errorCode);
				huawei_pay_lin.setVisibility(View.GONE);
				CommonUtils.getInstance().initToast(context, "onError  "+seType+"  " + errorCode);
			}
		});
	}

	protected void processLogic() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		new GetMeMberListBusiness(this, mhashmap, new GetMeMberListCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						UserMemberInfo userMemberInfo = (UserMemberInfo) dataMap.get("UserMemberInfo");
						if (userMemberInfo != null) {
							initTopMeberView(userMemberInfo);
						}
						ArrayList<UserBuyMeberInfo> meberpayList = (ArrayList<UserBuyMeberInfo>) dataMap.get("imgList");
						showMeberpaylistView(meberpayList);
					}
				}
			}
		});

	}

	private void showMeberpaylistView(ArrayList<UserBuyMeberInfo> meberpayList) {
		if (meberpayList != null && meberpayList.size() > 0) {
			final String show_more_url = meberpayList.get(0).getShow_more_url();
			String id_show_more = meberpayList.get(0).getIs_show_more();
			if (Integer.valueOf(id_show_more) > 2) {
				user_putong_member_note_title_right.setVisibility(View.VISIBLE);
				user_putong_member_note_title_right.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						CommonUtils.getInstance().toH5(context, show_more_url, "", "", false);
					}
				});
			}
			final ArrayList<UserBuyMeberListInfo> userBuyMeberListInfoList = meberpayList.get(0)
					.getUserBuyMeberListInfoList();
			final UserBuyMberAdapter userBuyMberAdapter = new UserBuyMberAdapter(context, userBuyMeberListInfoList,
					"1");
			mListView.setAdapter(userBuyMberAdapter);
			price = userBuyMeberListInfoList.get(0).getPriceBase();
			price = PriceUtils.getInstance().gteDividePrice(price, "100");
			price = PriceUtils.getInstance().seePrice(price);
			member_package_id = userBuyMeberListInfoList.get(0).getId();
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
					for (int i = 0; i < userBuyMeberListInfoList.size(); i++) {
						userBuyMeberListInfoList.get(i).setSelect(false);
						if (i == position) {
							userBuyMeberListInfoList.get(position).setSelect(true);
						}
					}
					userBuyMberAdapter.setaList(userBuyMeberListInfoList);
					userBuyMberAdapter.notifyDataSetChanged();
					member_package_id = userBuyMeberListInfoList.get(position).getId();
					price = userBuyMeberListInfoList.get(position).getPriceBase();
					price = PriceUtils.getInstance().gteDividePrice(price, "100");
					price = PriceUtils.getInstance().seePrice(price);
					updateCheckView();
				}
			});

			/**
			 * 会员特权说明
			 */
			ArrayList<UserMemberBuyNoteInfo> userMemberBuyNoteInfoList = meberpayList.get(0)
					.getUserMemberBuyNoteInfoList();
			user_putong_member_note_title.setText("百动会员特权");
			user_putong_member_note.setText(userMemberBuyNoteInfoList.get(0).getNote());
			user_putong_member_note2.setText(userMemberBuyNoteInfoList.get(1).getNote());

			user_putong_member_note_detle.setText(userMemberBuyNoteInfoList.get(0).getDiscount());
			user_putong_member_note_detle2.setText(userMemberBuyNoteInfoList.get(1).getDiscount());
			try {
				if (!TextUtils.isEmpty(userMemberBuyNoteInfoList.get(0).getImg())) {
					asyncImageLoader.DisplayImage(userMemberBuyNoteInfoList.get(0).getImg(),
							user_putong_member_note_img);
					asyncImageLoader.DisplayImage(userMemberBuyNoteInfoList.get(1).getImg(),
							user_putong_member_note_img2);

				}
			} catch (Exception e) {
			}
			updateCheckView();
		}
	}

	public void initTopMeberView(UserMemberInfo userMemberInfo) {
		String thumb = userMemberInfo.getAblumUrl();// 会员头像
		member_level = userMemberInfo.getMember_level();// 会员等级
		String member_note = userMemberInfo.getMember_note();// 有效期
		String note = userMemberInfo.getNote();// 头像右侧话术
		String username = userMemberInfo.getNick_name();
		CircleImageView usrecenter_iv_head = (CircleImageView) findViewById(R.id.usrecenter_iv_head);
		TextView usrecenter_tv_username = (TextView) findViewById(R.id.usrecenter_tv_username);
		TextView usrecenter_tv_phone = (TextView) findViewById(R.id.usrecenter_tv_phone);
		TextView user_member_topay_bottom_tv = (TextView) findViewById(R.id.user_member_topay_bottom_tv);
		try {
			if (!TextUtils.isEmpty(thumb)) {
				asyncImageLoader.DisplayImage(thumb, usrecenter_iv_head);
			} else {
				usrecenter_iv_head.setBackgroundResource(R.drawable.user_default_icon);
			}
		} catch (Exception e) {
		}
		usrecenter_tv_username.setText(username);
		usrecenter_tv_phone.setText(note);
		if (member_level.equals("0")) {
			user_member_topay_bottom_tv.setText("您还不是会员");
			Resources res = getResources();
			Drawable img_off = res.getDrawable(R.drawable.notmember_icon);
			// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
			img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
			usrecenter_tv_username.setCompoundDrawablePadding(5);
			usrecenter_tv_username.setCompoundDrawables(null, null, img_off, null);
			usrecenter_tv_usermember_info.setVisibility(View.GONE);
		} else {
			usrecenter_tv_usermember_info.setVisibility(View.VISIBLE);
			usrecenter_tv_usermember_info.setText(levelName);
			user_member_topay_bottom_tv.setText(member_note);
			Drawable img_off;
			Resources res = getResources();
			img_off = res.getDrawable(R.drawable.putong_icon);
			// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
			img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
			usrecenter_tv_username.setCompoundDrawablePadding(5);
			usrecenter_tv_username.setCompoundDrawables(null, null, img_off, null);
		}

	}

	/**
	 * 切换选中的view颜色
	 * 
	 * @param arg0
	 */
	private void updateCheckView() {
		user_buy_member_btn.setText(price);
		stadiumdetails_tv_book.setBackgroundResource(R.color.btn_blue_color);
		if (member_level.equals("0")) {
			stadiumdetails_tv_book.setText("确认支付");

		}
		if (member_level.equals("1")) {
			stadiumdetails_tv_book.setText("确认续费");
		}
		if (member_level.equals("2")) {
			stadiumdetails_tv_book.setText("确认支付");
			stadiumdetails_tv_book.setEnabled(false);
		}
	}

	private void goBuy() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", uid);
		mhashmap.put("username", bestDoInfoSharedPrefs.getString("loginName", ""));
		mhashmap.put("source", "1");
		mhashmap.put("company_id", bestDoInfoSharedPrefs.getString("bid", ""));
		String corp_id = bestDoInfoSharedPrefs.getString("bid", "");
		String isconpanyuser = "";
		if (!TextUtils.isEmpty(corp_id)) {
			if (corp_id.equals("nobid")) {

				isconpanyuser = "0";
			} else {
				isconpanyuser = "1";
			}

		}
		mhashmap.put("member_package_id", member_package_id);
		Editor bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		bestDoInfoEditor.putString("member_package_id", "" + member_package_id);
		bestDoInfoEditor.commit();
		mhashmap.put("isconpanyuser", isconpanyuser);
		mhashmap.put("pay_money", PriceUtils.getInstance().gteMultiplySumPrice(price, "100"));

		System.err.println(mhashmap);
		new CreateMemberOrderBusiness(this, mhashmap, new CreateMemberOrderCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						String oid = (String) dataMap.get("oid");

						PayUtils payUtils = new PayUtils(UserMeberActiyity.this, oid, uid, "", "", "",
								Constans.showPayDialogByBuyMember);
						if (pay_type.equals("zhifubao")) {
							payUtils.processLogicZFB();
						} else if (pay_type.equals("weixin")) {
							payUtils.processLogicWX();
						} else if (pay_type.equals("yinlian")) {
							payUtils.processLogicYinLian();
						} else if (pay_type.equals("huawei")) {
							payUtils.processLogicHuaWei();
						}
					}
				}
			}
		});

	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		MobclickAgent.onResume(this); // 统计时长
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		RequestUtils.cancelAll(this);
		super.onDestroy();
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
