package com.KiwiSports.control.activity;

import java.util.HashMap;
import java.util.Map;

import com.KiwiSports.R;
import com.KiwiSports.business.UserLoginBusiness;
import com.KiwiSports.business.UserLoginBusiness.GetLoginCallback;
import com.KiwiSports.control.view.MyApplication;
import com.KiwiSports.model.UserLoginInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.volley.RequestUtils;
import com.KiwiSports.wxapi.WXEntryActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：登录页面
 */
public class UserLoginActivity extends BaseActivity {
	private ProgressDialog mDialog;
	private TextView wxlogin;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.wxlogin:
			if (!wxHandler.isClientInstalled()) {
				CommonUtils.getInstance().initToast(context, getString(R.string.wxlogin_tishi));
			} else {
				login(SHARE_MEDIA.WEIXIN);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_login);
		CommonUtils.getInstance().addActivity(this);

	}

	@Override
	protected void findViewById() {
		wxlogin = (TextView) findViewById(R.id.wxlogin);
	}

	@Override
	protected void setListener() {
		wxlogin.setOnClickListener(this);

	}

	@Override
	protected void processLogic() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		addWXPlatform();
	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private UMWXHandler wxHandler;
	/**
	 * 整个平台的Controller,负责管理整个SDK的配置、操作等处理
	 */
	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	/**
	 * 登陆类型
	 */
	private String access_token;
	private String openid;
	protected String nick_name;
	protected String profile_image_url;
	private HashMap<String, String> mhashmap;
	private String province;
	private String city;
	private int sex;

	private void addWXPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		String appId = Constans.WEIXIN_APP_ID;
		String appSecret = Constans.WEIXIN_APP_SECRET;
		// 添加微信平台
		wxHandler = new UMWXHandler(UserLoginActivity.this, appId, appSecret);
		wxHandler.setRefreshTokenAvailable(false);
		wxHandler.addToSocialSDK();
	}

	/**
	 * 授权。如果授权成功，则获取用户信息
	 * ------------value------------Bundle[{access_token=4_mpMe-lYShZIC-BAOC_RXaSErr0QlqRXpOxWObAfY3zgbbp7gw5jNeNbri247QPRqT-nHW5eazu5fm7vkx0wS6nTXnlhxchlxnIcx3kxybBU,
	 * refresh_token=4_OlgBcOQy03lNpVO2wD7-qD8zReHTIjObUlcb0-rxSS_gGySjn4cosMSIgRdupmRK2LgQS0oqXZmQ6k07VcXZbKAKhaCKsMwt6ZrnWNNcY0s,
	 * openid=o33YMuAiLhY5ggJuG05rk0Xai1sg, expires_in=7200,
	 * refresh_token_expires=604800, unionid=oxPIquDANxen523S4ZlIyACnk0nM,
	 * uid=o33YMuAiLhY5ggJuG05rk0Xai1sg, scope=snsapi_userinfo}]
	 * 
	 * @param platform
	 */
	private void login(SHARE_MEDIA platform) {
		showDilag();
		mController.doOauthVerify(UserLoginActivity.this, platform, new UMAuthListener() {
			@Override
			public void onStart(SHARE_MEDIA platform) {
			}

			@Override
			public void onError(SocializeException e, SHARE_MEDIA platform) {
				showLoginFailureInfo();
			}

			@Override
			public void onComplete(Bundle value, SHARE_MEDIA platform) {
				// 获取uid
				Log.e("TESTLOG", "------------value------------" + value);
				String account = value.getString("uid");
				if (platform.equals(SHARE_MEDIA.WEIXIN)) {
					access_token = value.getString("access_token");
				}
				if (!TextUtils.isEmpty(account)) {
					getUserInfo(platform);
				} else {
					showLoginFailureInfo();
				}

			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
			}
		});
	}

	/**
	 * 获取用户信息
	 * ------------getUserInfo------------{unionid=oxPIquDANxen523S4ZlIyACnk0nM,
	 * country=中国, nickname=段军伟, city=海淀, language=zh_CN, province=北京,
	 * headimgurl=http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJJialkDlK6EenxibchE80J4e1PMUfGYVg9uO9QUU4ALJVvga83QEDXxt0I5FrFdPKA6MrJTMu34Okw/0,
	 * sex=1, openid=o33YMuAiLhY5ggJuG05rk0Xai1sg}
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
					Log.e("TESTLOG", "------------getUserInfo------------" + info.toString());
					if (platform.equals(SHARE_MEDIA.WEIXIN)) {
						nick_name = (String) info.get("nickname");
						profile_image_url = (String) info.get("headimgurl");
						province = (String) info.get("province");
						city = (String) info.get("city");
						sex = (Integer) info.get("sex");
						openid = (String) info.get("openid");
						if (TextUtils.isEmpty(province)) {
							province = getString(R.string.beijing);
						}
						if (TextUtils.isEmpty(city)) {
							city = getString(R.string.beijing);
						}
					}
					loginUMeng();
				} else {
					showLoginFailureInfo();
				}
			}

		});
	}

	private void showLoginFailureInfo() {
		CommonUtils.getInstance().initToast(context,getString(R.string.wxlogin_tishifali));
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

	private void loginUMeng() {
		mhashmap = new HashMap<String, String>();
		mhashmap.put("openid", openid + "");
		mhashmap.put("nickname", nick_name + "");
		mhashmap.put("headimgurl", profile_image_url + "");

		mhashmap.put("province", province + "");
		mhashmap.put("city", city + "");
		mhashmap.put("sex", sex + "");

		mhashmap.put("accesstoken", access_token + "");
		mhashmap.put("type", "android");
		mhashmap.put("token", ConfigUtils.getInstance().getDeviceId(this));
		Log.e("TESTLOG", "------------mhashmap------------" + mhashmap.toString());
		new UserLoginBusiness(this, mhashmap, new GetLoginCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						UserLoginInfo loginInfo = (UserLoginInfo) dataMap.get("loginInfo");
						if (loginInfo != null) {
							saveUserInfo(loginInfo);
							Intent intent = new Intent(context, MainActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(intent);
							CommonUtils.getInstance().setPageIntentAnim(intent, context);
						}
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

	private void saveUserInfo(UserLoginInfo loginInfo) {
		String uid = loginInfo.getUid();
		String nick_name = loginInfo.getNick_name();
		String album_url = loginInfo.getAlbum_url();
		int sex = loginInfo.getSex();
		String access_token = loginInfo.getAccess_token();
		String token = loginInfo.getToken();
		String hobby = loginInfo.getHobby();
		bestDoInfoEditor.putString("loginStatus", Constans.getInstance().loginStatus);
		bestDoInfoEditor.putString("uid", uid + "");
		bestDoInfoEditor.putString("hobby", hobby + "");
		bestDoInfoEditor.putString("nick_name", nick_name);
		bestDoInfoEditor.putInt("sex", sex);
		bestDoInfoEditor.putString("album_url", "" + album_url);
		bestDoInfoEditor.putString("token", token);
		bestDoInfoEditor.putString("access_token", access_token);
		bestDoInfoEditor.commit();
	}

	@Override
	protected void onDestroy() {
		CommonUtils.getInstance().setClearCacheDialog(mDialog);
		RequestUtils.cancelAll(this);
		super.onDestroy();
	}

	/**
	 * 退出监听
	 */
	public void onBackPressed() {
		CommonUtils.getInstance().defineBackPressed(this, null, 0, Constans.getInstance().exit);
	}

}
