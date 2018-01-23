package com.KiwiSports.control.activity;

import com.KiwiSports.R;
import com.KiwiSports.model.UserLoginInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constans;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-15 下午7:18:03
 * @Description 类描述：启动页
 */
public class WelcomeStartActiyity extends BaseActivity {
	private ImageView welcome_start_img;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.welcome_start);
		CommonUtils.getInstance().addActivity(WelcomeStartActiyity.this);
	}

	@Override
	protected void findViewById() {
		welcome_start_img = (ImageView) findViewById(R.id.welcome_start_img);
	}

	@Override
	protected void setListener() {
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
	}

	/**
	 * 设置启动页淡出动画
	 */
	@Override
	protected void processLogic() {
		AlphaAnimation mImgAlphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		mImgAlphaAnimation.setDuration(800);
		welcome_start_img.startAnimation(mImgAlphaAnimation);
		mImgAlphaAnimation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {

				if (checkLoginStatus()) {
					Intent intent = new Intent(context, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);
					finish();
				}
			}

			public void onAnimationRepeat(Animation animation) {

			}
		});
	}

	private boolean checkLoginStatus() {
		boolean loginstatus = true;
//		 monisaveUserInfo();
		String loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
		if (!loginStatus.equals(Constans.getInstance().loginStatus)) {
			Intent intent2 = new Intent(this, UserLoginActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, this);
			loginstatus = false;
		}
		return loginstatus;
	}

	/**
	 * 模拟登陆
	 * 
	 * @param loginInfo
	 */
	private void monisaveUserInfo() {
		String uid = "10008";
		String nick_name = "段军伟";
		String album_url = "5_z7KBsDJRzpjG1lrCvQH_-46hJWZB1Ajt07VuFNLW9HWMPs2VSRsUFKQTVp5haqKWDy9H3y116uV_TqHQkMWEAqKYM_EQhYb72U93vE1zmkI";
		int sex = 1;
		String token = ConfigUtils.getInstance().getDeviceId(this);
		String access_token = "6_EIqeIfYyGcpPglpZ-H8-6zSamTcqKs2jJIWMZJKpF1f6I-PgKcMpw-s_KKZGnH55YBUJJV4z6WiLrSY8rVp7S6oIw9U5HpFT96z0OH6cIGs";
		bestDoInfoEditor.putString("loginStatus", Constans.getInstance().loginStatus);
		bestDoInfoEditor.putString("uid", uid + "");
		bestDoInfoEditor.putString("nick_name", nick_name);
		bestDoInfoEditor.putInt("sex", sex);
		bestDoInfoEditor.putString("album_url", "" + album_url);
		bestDoInfoEditor.putString("token", token);
		bestDoInfoEditor.putString("access_token", access_token);
		bestDoInfoEditor.commit();
	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
	}
}
