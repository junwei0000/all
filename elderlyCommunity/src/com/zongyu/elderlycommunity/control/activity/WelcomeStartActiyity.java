package com.zongyu.elderlycommunity.control.activity;

import com.zongyu.elderlycommunity.R;
import com.zongyu.elderlycommunity.model.UserLoginInfo;
import com.zongyu.elderlycommunity.utils.CommonUtils;
import com.zongyu.elderlycommunity.utils.ConfigUtils;
import com.zongyu.elderlycommunity.utils.Constans;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
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
		getToken();
	}
	/**
	 * 得到设备唯一值
	 * 
	 * @return
	 */
	private String getToken() {
		String token = UserLoginInfo.getInstance().getTokenUse();
		if (TextUtils.isEmpty(token)) {
			token = ConfigUtils.getInstance().getDeviceId(this);
			UserLoginInfo.getInstance().setTokenUse(token);
		}
		return token;
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
				String welcomeSPFKey = Constans.getInstance().welcomeSharedPrefsKey;
				SharedPreferences welcomeSPF = getSharedPreferences(
						welcomeSPFKey, 0);
				Editor welcomeEditor = welcomeSPF.edit();
				String welcomeSPF_LaunchStatusKey = Constans.getInstance().welcomeSharedPrefs_LaunchStatusKey;
				final int firstSplash = 0;
				final int MoreSplash = 1;

				int firstSplashStatus = welcomeSPF.getInt(
						welcomeSPF_LaunchStatusKey, firstSplash);
				switch (firstSplashStatus) {
				case firstSplash:
					welcomeEditor
							.putInt(welcomeSPF_LaunchStatusKey, MoreSplash);
					welcomeEditor.commit();
					Intent it = new Intent(WelcomeStartActiyity.this,
							WelcomeGuideActivity.class);
					it.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(it);
					finish();
					break;

				case MoreSplash:
					CommonUtils.getInstance().SkipMain(context);
					break;
				}
			}

			public void onAnimationRepeat(Animation animation) {

			}
		});
	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
	}
}
