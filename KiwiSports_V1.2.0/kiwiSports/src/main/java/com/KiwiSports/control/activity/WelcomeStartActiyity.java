package com.KiwiSports.control.activity;

import com.KiwiSports.R;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constants;

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
		bestDoInfoSharedPrefs = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(this);
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
					CommonUtils.getInstance()
							.setPageIntentAnim(intent, context);
					finish();
				}
			}

			public void onAnimationRepeat(Animation animation) {

			}
		});
	}

	private boolean checkLoginStatus() {
		boolean loginstatus = true;
		simulateWeChatUserInfo();
		String loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
		if (!loginStatus.equals(Constants.getInstance().loginStatus)) {
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
	 */
	@SuppressWarnings("unused")
	private void simulateWeChatUserInfo() {
		String uid = "21752";
		String nick_name = "djw";
		String album_url = "http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/vi_32\\/Q0j4TwGTfTJdZAyrDL1DmossDNqYBbhwvjj09TyGNicIYY7nL3XxL17zo1MmJFLeDcu5XHL8PzcaXZ4NIQzSZSA\\/132";
		String token = ConfigUtils.getInstance().getDeviceId(this);
		String access_token = "23_3KGnEJWfJUQiR_jkzA5ryTqtRAajxrgQwW6xesBMXobbUiDgD6f48wHQSIB_1JqIabaINxlNy82HGqwCb08LtdBPSRLv5bmCmwcUBD73MiE";
		bestDoInfoEditor.putString("loginStatus",
				Constants.getInstance().loginStatus);
		bestDoInfoEditor.putString("uid", uid + "");
		bestDoInfoEditor.putString("nick_name", nick_name);
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
