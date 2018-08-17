package com.bestdo.bestdoStadiums.control.activity;

import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.GetBuySuccessMemberBusiness;
import com.bestdo.bestdoStadiums.business.GetBuySuccessMemberBusiness.GetBuySuccessMemberCallback;
import com.bestdo.bestdoStadiums.control.view.HuitanCustomView;
import com.bestdo.bestdoStadiums.model.UserMemberSuccessInfo;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午4:26:41
 * @Description 类描述：会员支付结果页
 */
public class UserMeberResultActiyity extends BaseActivity {
	private TextView pagetop_tv_name;
	private LinearLayout pagetop_layout_back, user_meber_lin;
	// 首先在您的Activity中添加如下成员变量
	// private RelativeLayout usrecenter_relay_meber;
	private HuitanCustomView h;
	private TextView user_getMember_success_note;
	private TextView user_getMember_success_validDays;
	private TextView user_getMember_success_endTimeMsg;
	private CircleImageView usrecenter_iv_head;
	private TextView success_member_username;
	private TextView success_member_note;
	private TextView success_member_bottom_tv;
	protected ImageLoader asyncImageLoader;
	private TextView success_member_bottom_goto_stadium_tv;
	private TextView success_member_bottom_goto_shangcheng_tv;
	int viewBottom_meber;
	int viewBottom_;
	private SharedPreferences bestDoInfoSharedPrefs;
	protected LinearLayout usrecenter_relay_head;
	protected String shopUrl;
	private float mDensity;
	private int mHiddenViewMeasuredHeight;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			toFinish();
			break;
		case R.id.userabout_tv_tel:
			CommonUtils.getInstance().getPhoneToKey(UserMeberResultActiyity.this);
			break;
		case R.id.success_member_bottom_goto_stadium_tv:
			Intent intent2 = new Intent(context, MainPersonActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			context.startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, context);
			finish();
			break;
		case R.id.success_member_bottom_goto_shangcheng_tv:
			CommonUtils.getInstance().toH5(context,shopUrl, "", "",false);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {

		setContentView(R.layout.user_meberresult);
		CommonUtils.getInstance().addActivity(UserMeberResultActiyity.this);

	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);

		usrecenter_iv_head = (CircleImageView) findViewById(R.id.success_member_iv_head);

		usrecenter_relay_head = (LinearLayout) findViewById(R.id.success_relay_head);

		success_member_username = (TextView) findViewById(R.id.success_member_username);
		success_member_note = (TextView) findViewById(R.id.success_member_note);
		success_member_bottom_tv = (TextView) findViewById(R.id.success_member_bottom_tv);

		user_getMember_success_note = (TextView) findViewById(R.id.user_getMember_success_note);
		user_getMember_success_validDays = (TextView) findViewById(R.id.user_getMember_success_validDays);
		user_getMember_success_endTimeMsg = (TextView) findViewById(R.id.user_getMember_success_endTimeMsg);

		success_member_bottom_goto_stadium_tv = (TextView) findViewById(R.id.success_member_bottom_goto_stadium_tv);
		success_member_bottom_goto_shangcheng_tv = (TextView) findViewById(
				R.id.success_member_bottom_goto_shangcheng_tv);

		asyncImageLoader = new ImageLoader(context, "headImg");

		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText("我的会员");
		user_meber_lin = (LinearLayout) findViewById(R.id.user_meber_lin);

		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		user_meber_lin.measure(w, h);
		viewBottom_ = user_meber_lin.getMeasuredHeight();
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		success_member_bottom_goto_stadium_tv.setOnClickListener(this);
		success_member_bottom_goto_shangcheng_tv.setOnClickListener(this);
	}

	private void animateOpen(View v) {
		v.setVisibility(View.VISIBLE);
		ValueAnimator animator = createDropAnimator(v, 0, mHiddenViewMeasuredHeight);
		animator.start();
	}

	private ValueAnimator createDropAnimator(final View v, int start, int end) {
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.setDuration(1800);
		animator.setInterpolator(new BounceInterpolator());
		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				int value = (Integer) arg0.getAnimatedValue();
				ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
				layoutParams.height = value;
				v.setLayoutParams(layoutParams);
			}
		});
		return animator;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void processLogic() {
		pagetop_tv_name.setText(getResources().getString(R.string.usercenter_member));

		mDensity = getResources().getDisplayMetrics().density;
		mHiddenViewMeasuredHeight = (int) (mDensity * viewBottom_ + 0.5);

		HashMap<String, String> mhashmap;
		mhashmap = new HashMap<String, String>();
		mhashmap.put("uid", "" + bestDoInfoSharedPrefs.getString("uid", ""));
		String member_package_id = bestDoInfoSharedPrefs.getString("member_package_id", "");
		mhashmap.put("packageId", "" + member_package_id);

		Log.e("packageId", "member_package_id" + member_package_id);
		new GetBuySuccessMemberBusiness(this, mhashmap, new GetBuySuccessMemberCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String status = (String) dataMap.get("status");
					if (status.equals("200")) {
						UserMemberSuccessInfo userMemberSuccessInfo = (UserMemberSuccessInfo) dataMap
								.get("UserMemberSuccessInfo");
						String nickName = userMemberSuccessInfo.getNickName();
						String iconUrl = userMemberSuccessInfo.getIconUrl();
						String note = userMemberSuccessInfo.getNote();
						String identityName = userMemberSuccessInfo.getIdentityName();
						String successMsg = userMemberSuccessInfo.getSuccessMsg();
						String validDays = userMemberSuccessInfo.getValidDays();
						String endTimeMsg = userMemberSuccessInfo.getEndTimeMsg();
						String identityId = userMemberSuccessInfo.getIdentityId();
						shopUrl = userMemberSuccessInfo.getShopUrl();
						if (!TextUtils.isEmpty(identityId) && identityId.equals("2")) {
							usrecenter_relay_head
									.setBackgroundResource(R.drawable.usercenter_background_baijinmeber_img2_yuan);
							user_meber_lin.setBackgroundResource(R.drawable.user_meber_bottom_baijin_img);
							success_member_bottom_goto_stadium_tv
									.setBackgroundResource(R.drawable.corners_buymeber_baijin_success_btnbg);
							success_member_bottom_goto_stadium_tv.setTextColor(R.color.huang_light);
							success_member_bottom_goto_shangcheng_tv.setTextColor(R.color.huang_light);
							success_member_bottom_goto_shangcheng_tv
									.setBackgroundResource(R.drawable.corners_buymeber_baijin_success_btnbg);

							Drawable img_off;
							Resources res = getResources();
							img_off = res.getDrawable(R.drawable.baijin_icon);
							// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
							img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
							success_member_username.setCompoundDrawablePadding(5);
							success_member_username.setCompoundDrawables(null, null, img_off, null); // 设置左图标
						} else if (!TextUtils.isEmpty(identityId) && identityId.equals("1")) {
							usrecenter_relay_head
									.setBackgroundResource(R.drawable.usercenter_background_baijinmeber_img_yuan);
							user_meber_lin.setBackgroundResource(R.drawable.user_meber_bottom_img);
							success_member_bottom_goto_stadium_tv
									.setBackgroundResource(R.drawable.corners_buymeber_success_btnbg_blue);
							success_member_bottom_goto_shangcheng_tv
									.setBackgroundResource(R.drawable.corners_buymeber_success_btnbg_blue);

							Drawable img_off;
							Resources res = getResources();
							img_off = res.getDrawable(R.drawable.putong_icon);
							// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
							img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
							success_member_username.setCompoundDrawablePadding(5);
							success_member_username.setCompoundDrawables(null, null, img_off, null); // 设置左图标

						}

						try {
							if (!TextUtils.isEmpty(iconUrl)) {
								String ablum = bestDoInfoSharedPrefs.getString("ablum", "");
								asyncImageLoader.DisplayImage(ablum, usrecenter_iv_head);
								// asyncImageLoader.DisplayImage(iconUrl,
								// usrecenter_iv_head);
							} else {
								usrecenter_iv_head.setBackgroundResource(R.drawable.user_default_icon);
							}
						} catch (Exception e) {
						}

						success_member_username.setText(nickName);
						success_member_note.setText(note);
						success_member_bottom_tv.setText(identityName);
						user_getMember_success_note.setText(successMsg);
						user_getMember_success_validDays.setText("有效期" + validDays + "天");
						user_getMember_success_endTimeMsg.setText(endTimeMsg);
						animateOpen(user_meber_lin);
					} else {
						String msg = (String) dataMap.get("msg");
						CommonUtils.getInstance().initToast(context, msg);
					}
				}
			}
		});
	}

	private void toFinish() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);
	}

	/**
	 * 重写onkeydown 用于监听返回键
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			toFinish();
		}
		return false;
	}
}