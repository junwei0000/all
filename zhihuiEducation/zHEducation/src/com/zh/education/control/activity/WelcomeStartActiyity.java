package com.zh.education.control.activity;

import com.zh.education.R;
import com.zh.education.utils.CommonUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2015-12-30 上午10:51:39
 * @Description 类描述：启动页
 */
public class WelcomeStartActiyity extends BaseActivity {
	private LinearLayout welcome_start_img;

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.welcome_start);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		welcome_start_img = (LinearLayout) findViewById(R.id.welcome_start_img);
	}

	@Override
	protected void setListener() {

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
				SharedPreferences bestDoInfoSharedPrefs = CommonUtils
						.getInstance().getBestDoInfoSharedPrefs(context);
				String loginStatus = bestDoInfoSharedPrefs.getString(
						"logining", "");
				Intent its;
				if (loginStatus.equals("logining")) {
					its = new Intent(WelcomeStartActiyity.this,
							MainActivity.class);
				} else {
					its = new Intent(WelcomeStartActiyity.this,
							UserLoginActivity.class);
				}
				its.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(its);
				finish();

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
