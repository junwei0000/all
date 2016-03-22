package com.zh.education.control.activity;

import com.zh.education.R;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.ImageLoader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：qyy
 * @date 创建时间：2016-1-13 下午2:47:07
 * @Description 类描述：我的
 */
public class UserCenterActivity extends BaseActivity {

	private LinearLayout top_layout_back, top_head_layout;
	private TextView top_tv_name, usercenter_tv_name, usercenter_tv_note;
	private RelativeLayout uesrcenter_relay_cancel, usercenter_relay_set,
			usercenter_relay_about;
	private ImageView usercenter_iv_thumb;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_layout_back:
			onBackPressed();
			break;
		case R.id.usercenter_relay_set:
			Intent intent_center = new Intent(getApplicationContext(),
					UserSetActivity.class);
			intent_center.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent_center);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
			break;
		case R.id.usercenter_relay_about:
			// Intent intent_centers = new Intent(getApplicationContext(),
			// UserAboutActiyity.class);
			// startActivity(intent_centers);
			// overridePendingTransition(R.anim.slide_in_right,
			// R.anim.slide_out_left);
			break;
		case R.id.uesrcenter_relay_cancel:
			CommonUtils.getInstance().defineBackPressed(this,
					CommonUtils.getInstance().cancel);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.usercenter);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		top_layout_back = (LinearLayout) findViewById(R.id.top_layout_back);
		top_head_layout = (LinearLayout) findViewById(R.id.top_head_layout);
		top_tv_name = (TextView) findViewById(R.id.top_tv_name);
		usercenter_relay_set = (RelativeLayout) findViewById(R.id.usercenter_relay_set);
		usercenter_relay_about = (RelativeLayout) findViewById(R.id.usercenter_relay_about);
		uesrcenter_relay_cancel = (RelativeLayout) findViewById(R.id.uesrcenter_relay_cancel);
		usercenter_tv_name = (TextView) findViewById(R.id.usercenter_tv_name);
		usercenter_tv_note = (TextView) findViewById(R.id.usercenter_tv_note);
		usercenter_iv_thumb = (ImageView) findViewById(R.id.usercenter_iv_thumb);
	}

	@Override
	protected void setListener() {
		top_layout_back.setOnClickListener(this);
		usercenter_relay_set.setOnClickListener(this);
		usercenter_relay_about.setOnClickListener(this);
		uesrcenter_relay_cancel.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		top_head_layout.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		top_tv_name.setText(getResources().getString(R.string.user_me));
		SharedPreferences zhedu_spf = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(context);
		String name = zhedu_spf.getString("name", "");
		String note = zhedu_spf.getString("note", "");
		String pictureUrl = zhedu_spf.getString("pictureUrl", "");
		usercenter_tv_name.setText(name);
		usercenter_tv_note.setText(note);
		if (!TextUtils.isEmpty(pictureUrl)) {
			// ImageLoader mImageLoader=new ImageLoader(context, "");
			// mImageLoader.DisplayImage(pictureUrl, usercenter_iv_thumb);
		}
	}

	/**
	 * 监听返回键
	 */
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}
}
