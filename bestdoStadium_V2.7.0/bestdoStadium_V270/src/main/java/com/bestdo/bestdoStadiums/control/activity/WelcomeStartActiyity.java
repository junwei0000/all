package com.bestdo.bestdoStadiums.control.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.R;

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
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		Editor bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		bestDoInfoEditor.putString("walkBottomTiShi", "");
		bestDoInfoEditor.commit();
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
				SharedPreferences welcomeSPF = getSharedPreferences(welcomeSPFKey, 0);
				Editor welcomeEditor = welcomeSPF.edit();
				String welcomeSPF_LaunchStatusKey = Constans.getInstance().welcomeSharedPrefs_LaunchStatusKey;
				final int firstSplash = 0;
				final int MoreSplash = 1;

				int firstSplashStatus = welcomeSPF.getInt(welcomeSPF_LaunchStatusKey, firstSplash);
				switch (firstSplashStatus) {
				case firstSplash:
					welcomeEditor.putInt(welcomeSPF_LaunchStatusKey, MoreSplash);
					initSelectLocation(welcomeEditor);

					welcomeEditor.commit();
					Intent it = new Intent(WelcomeStartActiyity.this, WelcomeGuideActivity.class);
					it.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(it);
					finish();
					break;

				case MoreSplash:
					CommonUtils.getInstance().skipMainActivity(WelcomeStartActiyity.this);
					break;
				}
			}

			public void onAnimationRepeat(Animation animation) {

			}
		});
	}

	/**
	 * 初始化定位
	 * 
	 * @param welcomeEditor
	 */
	private void initSelectLocation(Editor welcomeEditor) {
		welcomeEditor.putString("cityid_select", "52");
		welcomeEditor.putString("city_select", "北京");
		welcomeEditor.putString("longitude_select", "116.404269");
		welcomeEditor.putString("latitude_select", "39.91405");
	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
	}
}
