package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

import com.bestdo.bestdoStadiums.business.UserGetCodeBusiness;
import com.bestdo.bestdoStadiums.business.UserLoginBusiness;
import com.bestdo.bestdoStadiums.business.UserLoginUMengBusiness;
import com.bestdo.bestdoStadiums.business.UserGetCodeBusiness.GetGetCodeCallback;
import com.bestdo.bestdoStadiums.business.UserLoginBusiness.GetLoginCallback;
import com.bestdo.bestdoStadiums.business.UserLoginUMengBusiness.GetUserLoginUMengCallback;
import com.bestdo.bestdoStadiums.model.UserLoginInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.SupplierEditText;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：登录页面
 */
public class UserLoginActivity extends BaseActivity {
	private TextView pagetop_tv_name, pagetop_tv_you, userlogin_tv_regist;
	private LinearLayout pagetop_layout_you, pagetop_layout_back;
	private TextView userlogin_tv_fastlogin, userlogin_tv_findpw;
	private TextView userlogin_tv_commonlogin;

	private TextView userlogin_tv_getcode;
	private LinearLayout userlogin_layout_getcode;

	private SupplierEditText userlogin_et_phone, userlogin_et_pw;
	private Button click_btn;
	private TextView findPassword;
	private ProgressDialog mDialog;
	private HashMap<String, String> mhashmap;
	/**
	 * 快捷登录状态 默认true
	 */
	private boolean fastLoginStatus = true;
	private String validId = "";
	private int count;
	UserLoginSkipUtils mUserLoginSkipUtils;
	// 整个平台的Controller,负责管理整个SDK的配置、操作等处理
	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private String mold = "";// 登陆类型
	private SharedPreferences bestDoInfoSharedPrefs;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_iv_back_log:
			CommonUtils.getInstance().closeSoftInput(UserLoginActivity.this);
			nowFinish();
			break;
		case R.id.userlogin_tv_regist:
			CommonUtils.getInstance().closeSoftInput(UserLoginActivity.this);
			Intent intent = new Intent(UserLoginActivity.this, UserRegistActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			CommonUtils.getInstance().setPageIntentAnim(intent, UserLoginActivity.this);
			break;

		case R.id.userlogin_tv_findpw:
			CommonUtils.getInstance().closeSoftInput(UserLoginActivity.this);
			Intent intent2 = new Intent(UserLoginActivity.this, UserFindPWActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, UserLoginActivity.this);
			break;
		case R.id.userlogin_tv_fastlogin:
			fastLoginStatus = true;
			userlogin_tv_fastlogin.setVisibility(View.GONE);
			userlogin_tv_commonlogin.setVisibility(View.VISIBLE);
			userlogin_tv_findpw.setVisibility(View.GONE);
			userlogin_layout_getcode.setVisibility(View.VISIBLE);
			setInitLoginType();
			CommonUtils.getInstance().closeSoftInput(UserLoginActivity.this);
			break;
		case R.id.userlogin_tv_commonlogin:
			fastLoginStatus = false;
			userlogin_tv_commonlogin.setVisibility(View.GONE);
			userlogin_tv_fastlogin.setVisibility(View.VISIBLE);
			userlogin_tv_findpw.setVisibility(View.VISIBLE);
			setInitLoginType();
			CommonUtils.getInstance().closeSoftInput(UserLoginActivity.this);
			break;

		case R.id.userlogin_tv_getcode:
			String phone = userlogin_et_phone.getText().toString();
			if (isCheckGetCode(phone)) {
				fastLoginGetCode(phone);
			}
			break;
		case R.id.click_blue_btn:
			String phones = userlogin_et_phone.getText().toString();
			String password = userlogin_et_pw.getText().toString();
			if (isCheckLogin(phones, password)) {
				CommonUtils.getInstance().closeSoftInput(UserLoginActivity.this);
				if (fastLoginStatus) {
					fastLogin(phones, password, validId);
				} else {
					loginProcessLogic(phones, password);
				}
			}
			break;
		case R.id.btn_sina_login: // 新浪微博登录
			login(SHARE_MEDIA.SINA);
			break;
		case R.id.btn_qq_login: // qq登录
			login(SHARE_MEDIA.QQ);
			break;
		case R.id.btn_wechat_login: // 微信登陆

			if (!wxHandler.isClientInstalled()) {
				CommonUtils.getInstance().initToast(context, "您还没有安装微信客户端");
			} else {
				login(SHARE_MEDIA.WEIXIN);
			}
			break;
		default:
			break;
		}
	}

	private MyHandler mHandler = new MyHandler();
	private TimerTask timerTask;
	private final int Daojishistart = 0;
	private final int Daojishiover = 1;
	private ImageView sinaLoginButton;
	private ImageView qqLoginButton;
	private ImageView wechatLoginButton;
	private String device_token;
	private ImageView pagetop_iv_back_log;

	private void daojishi() {
		final long nowTime = System.currentTimeMillis();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				count--;
				Message msg = new Message();
				msg.what = Daojishiover;
				msg.arg1 = count;
				msg.obj = nowTime;
				mHandler.sendMessage(msg);
				if (count <= 0) {
					this.cancel();
				}
				msg = null;
			}
		};
		new Timer().schedule(timerTask, 0, 1000);
	}

	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Daojishistart:
				userlogin_tv_getcode.setEnabled(false);
				userlogin_tv_getcode.setTextColor(getResources().getColor(R.color.text_noclick_color));
				count = 60;
				daojishi();
				break;
			case Daojishiover:
				if (msg.arg1 < 10) {
					userlogin_tv_getcode.setText("0" + msg.arg1 + getString(R.string.tv_codeunit));
				} else {
					userlogin_tv_getcode.setText(msg.arg1 + getString(R.string.tv_codeunit));
				}
				if (msg.arg1 <= 0) {
					if (TextUtils.isEmpty(userlogin_et_phone.getText())) {
						userlogin_tv_getcode.setEnabled(false);
						userlogin_tv_getcode.setTextColor(getResources().getColor(R.color.text_noclick_color));
					} else {
						userlogin_tv_getcode.setTextColor(getResources().getColor(R.color.blue));
						userlogin_tv_getcode.setEnabled(true);
					}
					userlogin_tv_getcode.setText(getString(R.string.tv_repeatgetcode));
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_login);
		CommonUtils.getInstance().addActivity(this);
		CommonUtils.getInstance().addPayPageActivity(this);// 为了自动注册后登录，在UserRegistSetPwActivity页面关闭
		mUserLoginSkipUtils = new UserLoginSkipUtils(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_you = (TextView) findViewById(R.id.pagetop_tv_you);
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		userlogin_tv_regist = (TextView) findViewById(R.id.userlogin_tv_regist);
		userlogin_tv_fastlogin = (TextView) findViewById(R.id.userlogin_tv_fastlogin);
		userlogin_tv_commonlogin = (TextView) findViewById(R.id.userlogin_tv_commonlogin);
		userlogin_tv_findpw = (TextView) findViewById(R.id.userlogin_tv_findpw);

		userlogin_layout_getcode = (LinearLayout) findViewById(R.id.userlogin_layout_getcode);
		userlogin_tv_getcode = (TextView) findViewById(R.id.userlogin_tv_getcode);

		userlogin_et_phone = (SupplierEditText) findViewById(R.id.userlogin_et_phone);
		userlogin_et_pw = (SupplierEditText) findViewById(R.id.userlogin_et_pw);
		click_btn = (Button) findViewById(R.id.click_blue_btn);
		findPassword = (TextView) findViewById(R.id.userlogin_tv_findpw);

		sinaLoginButton = (ImageView) this.findViewById(R.id.btn_sina_login);
		qqLoginButton = (ImageView) this.findViewById(R.id.btn_qq_login);
		pagetop_iv_back_log = (ImageView) this.findViewById(R.id.pagetop_iv_back_log);

		wechatLoginButton = (ImageView) this.findViewById(R.id.btn_wechat_login);
	}

	@Override
	protected void setListener() {
		pagetop_tv_you.setVisibility(View.VISIBLE);
		pagetop_tv_you.setTextColor(getResources().getColor(R.color.text_contents_color));
		pagetop_tv_you.setText(getResources().getString(R.string.tv_reg));
		click_btn.setText(getResources().getString(R.string.tv_login));
		pagetop_tv_name.setText(getResources().getString(R.string.tv_login));
		userlogin_tv_regist.setOnClickListener(this);
		pagetop_layout_you.setOnClickListener(this);
		pagetop_iv_back_log.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		userlogin_tv_fastlogin.setOnClickListener(this);
		userlogin_tv_commonlogin.setOnClickListener(this);
		userlogin_tv_getcode.setOnClickListener(this);
		findPassword.setOnClickListener(this);
		click_btn.setOnClickListener(this);
		sinaLoginButton.setOnClickListener(this);
		qqLoginButton.setOnClickListener(this);
		wechatLoginButton.setOnClickListener(this);
		setInitLoginType();
		userlogin_et_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (TextUtils.isEmpty(userlogin_et_phone.getText())) {
					userlogin_et_pw.setText("");
					userlogin_tv_getcode.setTextColor(getResources().getColor(R.color.text_noclick_color));
					userlogin_tv_getcode.setEnabled(false);
				} else {
					userlogin_tv_getcode.setTextColor(getResources().getColor(R.color.blue));
					userlogin_tv_getcode.setEnabled(true);
				}
				isClick(userlogin_et_phone.getText().toString(), userlogin_et_pw.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		userlogin_et_pw.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				isClick(userlogin_et_phone.getText().toString(), userlogin_et_pw.getText().toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	/**
	 * 设置登录方式页面初始化
	 */
	private void setInitLoginType() {
		if (fastLoginStatus) {
			userlogin_tv_fastlogin.setTextColor(getResources().getColor(R.color.blue));
			userlogin_tv_commonlogin.setTextColor(getResources().getColor(R.color.text_click_color));
			// userlogin_tv_fastlogin.setVisibility(View.GONE);
		} else {
			userlogin_tv_fastlogin.setTextColor(getResources().getColor(R.color.text_click_color));
			userlogin_tv_commonlogin.setTextColor(getResources().getColor(R.color.blue));

		}
		if (fastLoginStatus) {
			userlogin_tv_getcode.setTextColor(getResources().getColor(R.color.text_noclick_color));
			userlogin_tv_getcode.setEnabled(false);
			userlogin_et_pw.setHint(getResources().getString(R.string.tv_codehint));
			userlogin_et_pw.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
			userlogin_et_pw.setInputType(InputType.TYPE_CLASS_NUMBER);
		} else {
			count = 1;// 结束倒计时
			userlogin_layout_getcode.setVisibility(View.GONE);
			userlogin_et_pw.setHint(getResources().getString(R.string.tv_pwhint));
			userlogin_et_pw.setFilters(new InputFilter[] { new InputFilter.LengthFilter(16) });
			userlogin_et_pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
		userlogin_et_phone.setText("" + userlogin_et_phone.getText());
		userlogin_et_pw.setText("");
	}

	@Override
	protected void processLogic() {
		// 配置需要分享的相关平台
		configPlatforms();
	}

	/**
	 * 配置分享平台参数
	 */
	private void configPlatforms() {
		// 添加新浪sso授权
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 添加QQ、QZone平台
		addQQQZonePlatform();
		// 添加微信、微信朋友圈平台
		addWXPlatform();
	}

	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform() {
		String appId = "1105290700";
		String appKey = "yKdi86YWzF0ApOz6";

		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(UserLoginActivity.this, appId, appKey);
		qqSsoHandler.addToSocialSDK();
	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private UMWXHandler wxHandler;

	private void addWXPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		String appId = "wx1d30dc3cd2adc80c";
		String appSecret = "720146d5c26fc6b15e6c851638398c78";
		// 添加微信平台
		wxHandler = new UMWXHandler(UserLoginActivity.this, appId, appSecret);
		wxHandler.setRefreshTokenAvailable(false);
		wxHandler.addToSocialSDK();
	}

	private Boolean weixinBackStatus = false;
	private String access_token;
	private String openid;
	protected String nick_name;
	protected String profile_image_url;
	protected String unionid;

	/**
	 * 授权。如果授权成功，则获取用户信息
	 * 
	 * @param platform
	 */
	private void login(SHARE_MEDIA platform) {
		showDilag();
		weixinBackStatus = false;
		// 0：qq 1：微博 2：微信
		if (platform.equals(SHARE_MEDIA.SINA)) {
			mold = "SINAWEIBO";
		} else if (platform.equals(SHARE_MEDIA.QQ)) {
			mold = "QQ";

		} else if (platform.equals(SHARE_MEDIA.WEIXIN)) {
			mold = "WEIXIN";
		}
		mController.doOauthVerify(UserLoginActivity.this, platform, new UMAuthListener() {
			@Override
			public void onStart(SHARE_MEDIA platform) {

			}

			@Override
			public void onError(SocializeException e, SHARE_MEDIA platform) {
				weixinBackStatus = true;
				showLoginFailureInfo();

			}

			@Override
			public void onComplete(Bundle value, SHARE_MEDIA platform) {
				// 获取uid
				System.err.println();
				weixinBackStatus = true;
				String account = value.getString("uid");
				Log.e("onComplete------login", value + "");
				if (platform.equals(SHARE_MEDIA.QQ)) {
					access_token = (String) value.get("access_token");
					openid = (String) value.get("uid");
				} else if (platform.equals(SHARE_MEDIA.WEIXIN)) {
					access_token = value.getString("access_token");
					unionid = (String) value.getString("unionid");
					showDilag();
				} else if (platform.equals(SHARE_MEDIA.SINA)) {
					openid = value.getString("uid");
				}

				if (!TextUtils.isEmpty(account)) {

					getUserInfo(platform);

				} else {
					showLoginFailureInfo();
				}

			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				weixinBackStatus = true;
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
			}
		});
	}

	/**
	 * 获取用户信息
	 * 
	 * @param platform
	 */
	private void getUserInfo(final SHARE_MEDIA platform) {
		mController.getPlatformInfo(UserLoginActivity.this, platform, new UMDataListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(int status, Map<String, Object> info) {
				if (status == StatusCode.ST_CODE_SUCCESSED && info != null) {
					Log.e("onComplete------getUserInfo", info.toString());
					if (!platform.equals(SHARE_MEDIA.WEIXIN)) {
						nick_name = (String) info.get("screen_name");
						profile_image_url = (String) info.get("profile_image_url");

					} else {
						nick_name = (String) info.get("nickname");
						profile_image_url = (String) info.get("headimgurl");

						openid = (String) info.get("openid");
					}
					if (platform.equals(SHARE_MEDIA.SINA)) {
						access_token = (String) info.get("access_token");
					}
					loginUMeng();
				} else {
					showLoginFailureInfo();
				}
			}

		});
	}

	private void showLoginFailureInfo() {
		CommonUtils.getInstance().initToast(context, "登录失败，请重新登录");
		CommonUtils.getInstance().setOnDismissDialog(mDialog);
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

	/**
	 * 方法说明：
	 * 
	 * @note 第三方登录
	 * @param nick_name名称
	 * @param mold1
	 *            ：qq 2：微博 3：微信） 登陆类型
	 * @param profile_image_url头像
	 * @param access_token
	 */
	private void loginUMeng() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			CommonUtils.getInstance().setOnDismissDialog(mDialog);
			return;
		}
		mhashmap = new HashMap<String, String>();
		mhashmap.put("nickName", nick_name);
		mhashmap.put("appType", mold);
		mhashmap.put("ablumUrl", profile_image_url);
		mhashmap.put("accessToken", access_token);
		mhashmap.put("openId", openid);
		System.err.println(mhashmap);
		if (mold.equals("WEIXIN")) {

			mhashmap.put("appId", "wx1d30dc3cd2adc80c");
		} else if (mold.equals("QQ")) {
			mhashmap.put("appId", "1103199440");
		} else {
			mhashmap.put("appId", "581480410");
		}
		if (mold.equals("WEIXIN")) {
			mhashmap.put("unionId", unionid);
		}
		mhashmap.put("deviceId", "android_" + bestDoInfoSharedPrefs.getString("device_token", ""));

		SharedPreferences bestDoInfo = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		String mobile = bestDoInfo.getString("mobile", "");
		if (!TextUtils.isEmpty(mobile)) {
			mhashmap.put("mobile", mobile);
			mhashmap.put("token", ConfigUtils.getInstance().MD5(mobile + "hc^w6BPbRG%t0LVi"));
		}
		System.err.println(mhashmap);
		showDilag();
		new UserLoginUMengBusiness(this, mhashmap, new GetUserLoginUMengCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						UserLoginInfo loginInfo = (UserLoginInfo) dataMap.get("loginInfo");
						mUserLoginSkipUtils.getLoginInfo(loginInfo);
						mUserLoginSkipUtils.saveThirdPartyInfo(mold);
					} else {
						// 防止老用户第一次验证成功登录失败后第二次登录直接用缓存
						CommonUtils.getInstance().clearAllBestDoInfoSharedPrefs(UserLoginActivity.this);
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(UserLoginActivity.this, msg);
					}
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	/**
	 * 快速登录获取验证码
	 * 
	 * @param phone
	 */
	private void fastLoginGetCode(String phone) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("account", phone);
		CommonUtils.getInstance().addToken(mhashmap, phone);
		String invokingType = Constans.getInstance().invokingTypeByFastLogin;
		new UserGetCodeBusiness(this, mhashmap, invokingType, new GetGetCodeCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						validId = (String) dataMap.get("validId");
						Message msg = new Message();
						msg.what = Daojishistart;
						mHandler.sendMessage(msg);
						msg = null;
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(UserLoginActivity.this, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(UserLoginActivity.this, getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	/**
	 * 快速登录
	 * 
	 * @param phone
	 * @param code
	 * @param validId
	 */
	private void fastLogin(String phone, String code, String validId) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("telephone", phone);
		mhashmap.put("validId", validId);
		mhashmap.put("validCode", code);
		mhashmap.put("deviceId", "android_" + bestDoInfoSharedPrefs.getString("device_token", ""));
		new UserLoginBusiness(this, fastLoginStatus, mhashmap, new GetLoginCallback() {
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						UserLoginInfo loginInfo = (UserLoginInfo) dataMap.get("loginInfo");
						mUserLoginSkipUtils.getLoginInfo(loginInfo);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(UserLoginActivity.this, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(UserLoginActivity.this, getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	/**
	 * 普通登录
	 * 
	 * @param phone
	 * @param password
	 */
	private void loginProcessLogic(String phone, String password) {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("account", phone);
		mhashmap.put("password", password);
		mhashmap.put("deviceId", "android_" + bestDoInfoSharedPrefs.getString("device_token", ""));
		System.err.println(mhashmap);
		Log.e("deviceId", mhashmap.toString());
		new UserLoginBusiness(this, fastLoginStatus, mhashmap, new GetLoginCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						UserLoginInfo loginInfo = (UserLoginInfo) dataMap.get("loginInfo");
						mUserLoginSkipUtils.getLoginInfo(loginInfo);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(UserLoginActivity.this, msg);
					}
				} else {
					CommonUtils.getInstance().initToast(UserLoginActivity.this, getString(R.string.net_tishi));
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});
	}

	/**
	 * 根据输入框内容判断按钮颜色
	 * 
	 * @param phone
	 * @param password
	 */
	private void isClick(String phone, String password) {
		if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
			click_btn.setTextColor(getResources().getColor(R.color.white));
		} else {
			click_btn.setTextColor(getResources().getColor(R.color.btn_noclick_color));
		}
	}

	/**
	 * 获取验证码前判断手机号
	 * 
	 * @param phone
	 * @return
	 */
	private boolean isCheckGetCode(String phone) {
		if (TextUtils.isEmpty(phone) || !ConfigUtils.getInstance().isMobileNO(phone)) {
			CommonUtils.getInstance().initToast(UserLoginActivity.this, getString(R.string.tishi_login_phone));
			return false;
		}
		return true;
	}

	/**
	 * 登录前验证
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	private boolean isCheckLogin(String phone, String password) {
		if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
			return false;
		}
		if (!ConfigUtils.getInstance().isMobileNO(phone)) {
			CommonUtils.getInstance().initToast(UserLoginActivity.this, getString(R.string.tishi_login_phone));
			return false;
		}
		if (fastLoginStatus) {
			if (password.length() != 6) {
				CommonUtils.getInstance().initToast(UserLoginActivity.this, getString(R.string.tishi_login_code));
				return false;
			}
		} else {
			if (password.length() < 6 || password.length() > 16 || !ConfigUtils.getInstance().isPasswordNO(password)) {
				CommonUtils.getInstance().initToast(UserLoginActivity.this, getString(R.string.tishi_login_pw));
				return false;
			}
		}
		return true;
	}

	// -----------------add cancel 收藏-------------------------------
	@Override
	protected void onStart() {
		super.onStart();
		// 动态注册广播
		filter = new IntentFilter();
		filter.addAction(context.getString(R.string.action_UmengLoginBindMobile));
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(dynamicReceiver, filter);
	}

	IntentFilter filter;
	private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("from CreatOrdersActivity", "接收---登录注册后同步详情中收藏状态--广播消息");
			if (intent.getAction().equals(context.getString(R.string.action_UmengLoginBindMobile))) {
				loginUMeng();
			}
		}
	};

	@Override
	protected void onDestroy() {
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		RequestUtils.cancelAll(this);
		unregisterReceiver(dynamicReceiver);
		dynamicReceiver = null;
		filter = null;
		super.onDestroy();
	}

	private void nowFinish() {
		mUserLoginSkipUtils.doBackCheck();
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	// 如果有使用新浪、人人的SSO授权或者集成了facebook平台, 则必须在对应的activity中实现onActivityResult方法,
	// 并添加如下代码
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);

		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (!weixinBackStatus && mold.equals("WEIXIN")) {
			CommonUtils.getInstance().setClearCacheDialog(mDialog);
		}
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
