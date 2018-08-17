package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness;
import com.bestdo.bestdoStadiums.business.GetMemberInfoBusiness.GetMemberInfoCallback;
import com.bestdo.bestdoStadiums.business.UserOrderGetNumBusiness;
import com.bestdo.bestdoStadiums.business.UserOrderGetNumBusiness.GetUserOrderGetNumCallback;
import com.bestdo.bestdoStadiums.model.UserCenterMemberInfo;
import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.AppUpdate;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午7:06:50
 * @Description 类描述：个人中心
 */
public class UserCenterActivity extends BaseActivity {

	private RelativeLayout usrecenter_relay_head;
	private CircleImageView usrecenter_iv_head;
	private TextView usrecenter_tv_username, usrecenter_tv_phone;

	private RelativeLayout usercenter_relay_allorder;
	private RelativeLayout usercenter_relay_kaquan;
	private RelativeLayout usercenter_relay_set;
	private RelativeLayout usercenter_relay_about;
	private RelativeLayout usercenter_relay_sendmsg, usercenter_relay_update;
	private TextView usercenter_tv_tel;
	private ScrollView usrecenter_scrollview;
	private SharedPreferences bestDoInfoSharedPrefs;
	private String uid, loginStatus, mobile, ablum;

	Activity mHomeActivity;
	private LinearLayout usercenter_relay_ka;
	private RelativeLayout usercenter_relay_buykaquan;
	private HashMap<String, String> mhashmap;
	private TextView usrecenter_tv_usermember_info;
	private LinearLayout usercenter_layout_collect;
	private String order_list, address_list;
	private LinearLayout usercenter_layout_address;
	private RelativeLayout usercenter_relay_shangcheng_allorder;
	private RelativeLayout usercenter_relay_kami, usercenter_relay_member, user_center_top_info_edit;
	private ImageView usercenter_arrowright_edit, usercenter_member_icon;
	private LinearLayout usercenter_layout_banlance;
	private TextView usercenter_tv_banlance;
	private TextView usercenter_tv_kami;
	private TextView usercenter_tv_kaquan;

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.usercenter_relay_member:
			intent = new Intent(mHomeActivity, UserMeberActiyity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("levelName", levelName);
			intent.putExtra("levelUrl", levelUrl);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			break;
		case R.id.usrecenter_tv_usermember_info:
			if (!TextUtils.isEmpty(identityId) && identityId.equals("0")) {
				intent = new Intent(mHomeActivity, UserMeberActiyity.class);
				intent.putExtra("levelName", levelName);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			} else {
				CommonUtils.getInstance().toH5(this,levelUrl, "", "",false);
			}
			break;
		case R.id.user_center_top_info_edit:
		case R.id.usercenter_arrowright_edit:
		case R.id.usrecenter_relay_head:
			if (CommonUtils.getInstance().chackLoginStatus(loginStatus, mHomeActivity)) {
				intent = new Intent(mHomeActivity, UserAccountActivity.class);
				intent.putExtra("uid", uid);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivityForResult(intent, 1);
				CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			}
			break;
		case R.id.usercenter_relay_allorder:
			if (CommonUtils.getInstance().chackLoginStatus(loginStatus, mHomeActivity)) {
				processLogic();
				intent = new Intent(mHomeActivity, UserOrderActivity.class);
				intent.putExtra("uid", uid);// "260151124164836ak2"
				intent.putExtra("status", getResources().getString(R.string.order_all));
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			}
			break;
		case R.id.usercenter_relay_kaquan:
			intentKaQuan(v);
			break;
		case R.id.usercenter_relay_kami:
			intentKaMi(v);
			break;
		case R.id.usercenter_relay_buykaquan:
			// 购买百动卡
			if (CommonUtils.getInstance().chackLoginStatus(loginStatus, mHomeActivity)) {
				intent = new Intent(mHomeActivity, UserCardsBuyActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				CommonUtils.getInstance().intntStatus = "UserCenterActivity";

				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			}
			break;
		case R.id.usercenter_layout_collect:
			if (CommonUtils.getInstance().chackLoginStatus(loginStatus, mHomeActivity)) {
				intent = new Intent(mHomeActivity, UserCollectActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			}
			break;
		case R.id.usrecenter_tv_phone:
		case R.id.usercenter_relay_set:
			// 设置
			if (CommonUtils.getInstance().chackLoginStatus(loginStatus, mHomeActivity)) {
				intent = new Intent(mHomeActivity, UserSetActivity.class);
				intent.putExtra("mobile", mobile);
				intent.putExtra("ThirdPartyLoginMode", bestDoInfoSharedPrefs.getString("ThirdPartyLoginMode", ""));
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			}
			break;
		case R.id.usercenter_relay_about:
			intent = new Intent(mHomeActivity, UserAboutActiyity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			break;
		case R.id.usercenter_relay_sendmsg:
			intent = new Intent(mHomeActivity, UserSendIdearActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
			break;
		case R.id.usercenter_relay_update:
			AppUpdate appUpdate = new AppUpdate(this);
			appUpdate.startUpdateAsy(Constans.getInstance().updateVersionFromSetPage);
			break;
		case R.id.usercenter_tv_tel:
			CommonUtils.getInstance().getPhoneToKey(mHomeActivity);
			break;
		case R.id.usercenter_layout_address:
			if (CommonUtils.getInstance().chackLoginStatus(loginStatus, mHomeActivity)) {
				if (!TextUtils.isEmpty(address_list) && !address_list.equals("null")) {
					String html_url=address_list + "&uid=" + bestDoInfoSharedPrefs.getString("uid", "")
					+ "&device_type=android";
					CommonUtils.getInstance().toH5(context,html_url, "", "",false);
				}
			}
			break;
		case R.id.usercenter_relay_shangcheng_allorder:
			if (CommonUtils.getInstance().chackLoginStatus(loginStatus, mHomeActivity)) {
				if (!TextUtils.isEmpty(order_list) && !order_list.equals("null")) {
					String url = order_list + "&uid=" + bestDoInfoSharedPrefs.getString("uid", "")
							+ "&device_type=android";
					CommonUtils.getInstance().toH5(this,url, "", "",false);
				}
			}
			break;
		default:
			break;
		}
	}

	public void intentMyBanlance(View v) {
		Intent intent = new Intent(mHomeActivity, UserBalanceActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("balance", balance);
		startActivity(intent);
		CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
	}

	public void intentKaQuan(View v) {
		if (CommonUtils.getInstance().chackLoginStatus(loginStatus, mHomeActivity)) {
			Intent intent = new Intent(mHomeActivity, UserCardsTabActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
		}
	}

	public void intentKaMi(View v) {
		if (CommonUtils.getInstance().chackLoginStatus(loginStatus, mHomeActivity)) {
			Intent intent = new Intent(mHomeActivity, UserCardsMiActivity.class);
			intent.putExtra("intent_from", "UserCenterActivity");
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_center);
		mHomeActivity = CommonUtils.getInstance().nHomeActivity;
	}

	@Override
	protected void findViewById() {
		usrecenter_scrollview = (ScrollView) findViewById(R.id.usrecenter_scrollview);
		usrecenter_relay_head = (RelativeLayout) findViewById(R.id.usrecenter_relay_head);
		usrecenter_iv_head = (CircleImageView) findViewById(R.id.usrecenter_iv_head);
		usrecenter_tv_username = (TextView) findViewById(R.id.usrecenter_tv_username);
		usrecenter_tv_phone = (TextView) findViewById(R.id.usrecenter_tv_phone);
		usercenter_relay_allorder = (RelativeLayout) findViewById(R.id.usercenter_relay_allorder);
		usercenter_layout_collect = (LinearLayout) findViewById(R.id.usercenter_layout_collect);
		usercenter_relay_ka = (LinearLayout) findViewById(R.id.usercenter_relay_ka);
		usercenter_relay_kaquan = (RelativeLayout) findViewById(R.id.usercenter_relay_kaquan);
		usercenter_relay_buykaquan = (RelativeLayout) findViewById(R.id.usercenter_relay_buykaquan);
		usercenter_relay_set = (RelativeLayout) findViewById(R.id.usercenter_relay_set);
		usercenter_relay_about = (RelativeLayout) findViewById(R.id.usercenter_relay_about);
		usercenter_relay_sendmsg = (RelativeLayout) findViewById(R.id.usercenter_relay_sendmsg);
		usercenter_tv_tel = (TextView) findViewById(R.id.usercenter_tv_tel);
		usercenter_relay_update = (RelativeLayout) findViewById(R.id.usercenter_relay_update);
		usercenter_layout_address = (LinearLayout) findViewById(R.id.usercenter_layout_address);
		usercenter_relay_kami = (RelativeLayout) findViewById(R.id.usercenter_relay_kami);
		usercenter_relay_member = (RelativeLayout) findViewById(R.id.usercenter_relay_member);
		usrecenter_tv_usermember_info = (TextView) findViewById(R.id.usrecenter_tv_usermember_info);
		usercenter_relay_shangcheng_allorder = (RelativeLayout) findViewById(R.id.usercenter_relay_shangcheng_allorder);
		usercenter_arrowright_edit = (ImageView) findViewById(R.id.usercenter_arrowright_edit);
		usercenter_member_icon = (ImageView) findViewById(R.id.usercenter_member_icon);
		user_center_top_info_edit = (RelativeLayout) findViewById(R.id.user_center_top_info_edit);

		usercenter_layout_banlance = (LinearLayout) findViewById(R.id.usercenter_layout_banlance);
		usercenter_tv_banlance = (TextView) findViewById(R.id.usercenter_tv_banlance);
		usercenter_tv_kami = (TextView) findViewById(R.id.usercenter_tv_kami);
		usercenter_tv_kaquan = (TextView) findViewById(R.id.usercenter_tv_kaquan);
	}

	@Override
	protected void setListener() {
		usrecenter_tv_usermember_info.setOnClickListener(this);
		usrecenter_relay_head.setOnClickListener(this);
		usercenter_relay_allorder.setOnClickListener(this);
		usercenter_relay_member.setOnClickListener(this);
		usercenter_relay_kaquan.setOnClickListener(this);
		usercenter_relay_buykaquan.setOnClickListener(this);
		usercenter_layout_collect.setOnClickListener(this);
		usercenter_relay_set.setOnClickListener(this);
		usercenter_relay_about.setOnClickListener(this);
		usercenter_relay_sendmsg.setOnClickListener(this);
		usercenter_tv_tel.setOnClickListener(this);
		usercenter_relay_update.setOnClickListener(this);
		usercenter_layout_address.setOnClickListener(this);
		usercenter_relay_shangcheng_allorder.setOnClickListener(this);
		usercenter_relay_kami.setOnClickListener(this);
		usercenter_arrowright_edit.setOnClickListener(this);
		user_center_top_info_edit.setOnClickListener(this);
		firstComeIn = false;
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		asyncImageLoader = new ImageLoader(context, "headImg");
		loadLoginInfo();
	}

	ImageLoader asyncImageLoader;
	protected String levelUrl;
	protected String levelName;
	protected String identityId;
	protected String balance = "0";

	private void loadLoginInfo() {
		loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
		if (loginStatus.equals(Constans.getInstance().loginStatus)) {
			uid = bestDoInfoSharedPrefs.getString("uid", "");
			mobile = bestDoInfoSharedPrefs.getString("mobile", "");
			ablum = bestDoInfoSharedPrefs.getString("ablum", "");
			String nickName = bestDoInfoSharedPrefs.getString("nickName", "");
			String realName = bestDoInfoSharedPrefs.getString("realName", "");
			if (!TextUtils.isEmpty(nickName)) {
				usrecenter_tv_username.setText(nickName);
			} else {
				if (!TextUtils.isEmpty(realName)) {
					usrecenter_tv_username.setText(realName);
				} else {
					usrecenter_tv_username.setText(mobile);
				}
			}
			usercenter_layout_banlance.setVisibility(View.VISIBLE);
			usercenter_layout_collect.setVisibility(View.VISIBLE);
			usercenter_relay_ka.setVisibility(View.VISIBLE);
			usrecenter_tv_phone.setVisibility(View.VISIBLE);
			usercenter_relay_member.setVisibility(View.VISIBLE);
			usrecenter_tv_usermember_info.setVisibility(View.VISIBLE);
			usercenter_arrowright_edit.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(mobile)) {
				usrecenter_tv_phone.setText(mobile);
				usrecenter_tv_phone.setClickable(false);
			} else {
				usrecenter_tv_phone.setText("设置手机号");
				usrecenter_tv_phone.setOnClickListener(this);
			}
			if (!TextUtils.isEmpty(ablum)) {
				asyncImageLoader.DisplayImage(ablum, usrecenter_iv_head);
			} else {
				Bitmap mBitmap = asyncImageLoader.readBitMap(mHomeActivity, R.drawable.user_default_icon);
				usrecenter_iv_head.setImageBitmap(mBitmap);
			}
		} else {
			Bitmap mBitmap = asyncImageLoader.readBitMap(mHomeActivity, R.drawable.user_default_icon);
			usercenter_member_icon.setVisibility(View.GONE);
			usrecenter_iv_head.setImageBitmap(mBitmap);
			usrecenter_tv_username.setText(getString(R.string.usercenter_namemoren));
			usercenter_layout_collect.setVisibility(View.GONE);
			usrecenter_tv_phone.setText("快速登录,享受更多服务");
			usercenter_relay_ka.setVisibility(View.GONE);
			usercenter_arrowright_edit.setVisibility(View.GONE);
			usercenter_relay_member.setVisibility(View.GONE);
			usercenter_layout_banlance.setVisibility(View.GONE);
			usrecenter_tv_usermember_info.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void processLogic() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", "" + uid);
		new UserOrderGetNumBusiness(this, mhashmap, new GetUserOrderGetNumCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						UserOrdersInfo mOrdersNumInfo = (UserOrdersInfo) dataMap.get("mOrdersNumInfo");
						if (mOrdersNumInfo != null) {
							order_list = mOrdersNumInfo.getOrder_list();
							address_list = mOrdersNumInfo.getAddress_list();
						}
					}
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
		getUserMemberBalanceInfo();
	}

	private void getUserMemberBalanceInfo() {
		loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
		if (loginStatus.equals(Constans.getInstance().loginStatus)) {
			HashMap<String, String> mhashmap2 = new HashMap<String, String>();
			mhashmap2.put("uid", "" + uid);
			new GetMemberInfoBusiness(this, mhashmap2, new GetMemberInfoCallback() {

				@Override
				public void afterDataGet(HashMap<String, Object> dataMap) {
					if (dataMap != null) {
						String status = (String) dataMap.get("status");
						if (status.equals("200")) {
							UserCenterMemberInfo userCenterMemberInfo = (UserCenterMemberInfo) dataMap
									.get("userCenterMemberInfo");
							if (userCenterMemberInfo != null) {
								levelName = userCenterMemberInfo.getLevelName();
								levelUrl = userCenterMemberInfo.getLevelUrl();
								identityId = userCenterMemberInfo.getIdentityId();
								if (identityId.equals("0")) {
									usercenter_member_icon.setVisibility(View.GONE);
									usrecenter_tv_usermember_info.setVisibility(View.VISIBLE);
									usrecenter_tv_usermember_info.setText("您还不是会员");
									usrecenter_tv_usermember_info
											.setBackgroundResource(R.drawable.usercenter_img_notmenberbg);
									usrecenter_tv_usermember_info
											.setTextColor(getResources().getColor(R.color.btn_blue_color));
								} else {
									usercenter_member_icon.setVisibility(View.VISIBLE);
									usrecenter_tv_usermember_info.setVisibility(View.VISIBLE);
									usercenter_member_icon.setBackgroundResource(R.drawable.putong_icon);
									usrecenter_tv_usermember_info.setText(levelName);
									usrecenter_tv_usermember_info
											.setBackgroundResource(R.drawable.corners_rectangle_red);
									usrecenter_tv_usermember_info.setTextColor(getResources().getColor(R.color.white));

								}
							}

							UserCenterMemberInfo userCenterBalanceInfo = (UserCenterMemberInfo) dataMap
									.get("userCenterBalanceInfo");
							if (userCenterBalanceInfo != null) {
								balance = userCenterBalanceInfo.getBalance();
								String cardNumber = userCenterBalanceInfo.getCardNumber();
								String kmNumber = userCenterBalanceInfo.getKmNumber();
								balance = PriceUtils.getInstance().gteDividePrice(balance, "100");
								balance = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(balance));
								usercenter_tv_banlance.setText(balance + "元");
								usercenter_tv_kami.setText(kmNumber);
								usercenter_tv_kaquan.setText(cardNumber);
							}

						}
					}

				}
			});
		}
	}

	private boolean firstComeIn = true;

	@Override
	protected void onRestart() {
		Log.e("onRestart", "--------------onRestart-----------");
		super.onResume();
	}

	@Override
	protected void onResume() {
		Log.e("onResume", "--------------onResume-----------" + firstComeIn);
		if (CommonUtils.getInstance().refleshScroolStatus) {
			CommonUtils.getInstance().refleshScroolStatus = false;
			usrecenter_scrollview.fullScroll(ScrollView.FOCUS_UP);

		}
		if (!firstComeIn) {

			loadLoginInfo();
			processLogic();
		}
		super.onResume();
		MobclickAgent.onPageEnd("MainScreen");
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		bestDoInfoSharedPrefs = null;
		RequestUtils.cancelAll(this);
		super.onDestroy();
	}

	Bitmap updatebitmap;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (data != null) {
				updatebitmap = data.getParcelableExtra("updatebitmap");
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
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
