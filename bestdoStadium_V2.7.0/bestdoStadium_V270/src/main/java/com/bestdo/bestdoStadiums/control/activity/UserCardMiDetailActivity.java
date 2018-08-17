package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.bestdo.bestdoStadiums.business.UserCardsDetailBusiness;
import com.bestdo.bestdoStadiums.business.UserCardsDetailLoadShouZhiBusiness;
import com.bestdo.bestdoStadiums.business.UserCardsDetailBusiness.GetUserCardsDetailCallback;
import com.bestdo.bestdoStadiums.business.UserCardsDetailLoadShouZhiBusiness.GetUserCardsDetailLoadShouZhiCallback;
import com.bestdo.bestdoStadiums.control.adapter.UserCardZhiFuDetailAdapter;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.model.UserCardsDetaiShouZhilInfo;
import com.bestdo.bestdoStadiums.model.UserCardsDetailInfo;
import com.bestdo.bestdoStadiums.model.UserCardsMiInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.MyListView;
import com.bestdo.bestdoStadiums.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * @author
 * @date
 * @Description 类描述：卡密详情
 */
public class UserCardMiDetailActivity extends BaseActivity {

	private LinearLayout pagetop_layout_back, user_card_mi_detail_lay;
	private TextView pagetop_tv_name;
	private ImageView club_activity_mi_lay_top_img;
	private UserCardsMiInfo userCardsMiInfo;
	private String thumb;
	private ImageLoader asyncImageLoader;
	private EditText user_card_mi_text;
	private TextView user_card_mi_time_text, user_card_mi_shiyong_text;
	private String backGroundColor = "";
	private String camiNote = "";
	private Button share_friend_btn;
	// 首先在您的Activity中添加如下成员变量
	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private String webpageUrl;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:

			nowFinish();
			break;
		case R.id.user_carddetail_tv_bottom:

			break;
		case R.id.share_friend_btn:
			setShareUM();

			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.user_card_mi_detail);
		CommonUtils.getInstance().addActivity(this);
		asyncImageLoader = new ImageLoader(this, "listdetail");
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		pagetop_tv_name.setText("卡密包");
		club_activity_mi_lay_top_img = (ImageView) findViewById(R.id.club_activity_mi_lay_top_img);
		user_card_mi_text = (EditText) findViewById(R.id.user_card_mi_text);
		user_card_mi_time_text = (TextView) findViewById(R.id.user_card_mi_time_text);
		share_friend_btn = (Button) findViewById(R.id.share_friend_btn);
		user_card_mi_shiyong_text = (TextView) findViewById(R.id.user_card_mi_shiyong_text);
		user_card_mi_detail_lay = (LinearLayout) findViewById(R.id.user_card_mi_detail_lay);
	}

	@Override
	protected void setListener() {
		pagetop_layout_back.setOnClickListener(this);
		share_friend_btn.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		try {
			userCardsMiInfo = (UserCardsMiInfo) getIntent().getSerializableExtra("UserCardsMiInfo");
			thumb = userCardsMiInfo.getCard_img();

			if (!TextUtils.isEmpty(thumb)) {
				asyncImageLoader.DisplayImage(thumb, club_activity_mi_lay_top_img);
			} else {
				club_activity_mi_lay_top_img.setBackgroundResource(R.drawable.club_img_banner);
			}
			user_card_mi_text.setText("卡密:  " + userCardsMiInfo.getCard_pass());
			user_card_mi_time_text.setText("有效期:  " + userCardsMiInfo.getValidNote());// 有效期
			camiNote = userCardsMiInfo.getCamiNote();
			backGroundColor = userCardsMiInfo.getBackGroundColor();
			camiNote = camiNote.replace("|", "\n");
			user_card_mi_shiyong_text.setText(camiNote);
			user_card_mi_detail_lay.setBackgroundColor(Color.parseColor("#" + backGroundColor));
			webpageUrl = userCardsMiInfo.getShare_url();
		} catch (Exception e) {

		}
	}

	private void setShareUM() {
		String appID = "wx1d30dc3cd2adc80c";
		String appSecret = "2b129f9c81e4a75de4c32ace80f83b9a";
		String mer_name = "要运动,找百动";
		String description = "您的好友赠送了一张百动卡,快去领取吧!";
		// 设置分享内容
		mController.setShareContent(description);
		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.share_icon);
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(context, thumb));
		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QQ);

		/**
		 * 添加微信平台
		 */
		UMWXHandler wxHandler = new UMWXHandler(context, appID, appSecret);
		wxHandler.addToSocialSDK();
		wxHandler.setTargetUrl(webpageUrl);
		wxHandler.setTitle(mer_name);
		/**
		 * 添加微信朋友圈
		 */
		UMWXHandler wxCircleHandler = new UMWXHandler(context, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		// 设置微信朋友圈分享内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(description);
		circleMedia.setTitle(description);
		circleMedia.setShareImage(new UMImage(context, thumb));
		circleMedia.setTargetUrl(webpageUrl);
		mController.setShareMedia(circleMedia);
		// qq
		UMQQSsoHandler qq = new UMQQSsoHandler(context, "1105290700", "yKdi86YWzF0ApOz6");
		qq.addToSocialSDK();
		QQShareContent qqShareContent = new QQShareContent();
		qqShareContent.setShareContent(description);
		qqShareContent.setTitle(mer_name);
		qqShareContent.setTargetUrl(webpageUrl);
		qqShareContent.setShareImage(new UMImage(context, thumb));
		mController.setShareMedia(qqShareContent);
		mController.openShare(context, false);
	}

	private void nowFinish() {
		finish();
		CommonUtils.getInstance().setPageBackAnim(this);

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

	@Override
	protected void onRestart() {
		super.onRestart();

	}
}
