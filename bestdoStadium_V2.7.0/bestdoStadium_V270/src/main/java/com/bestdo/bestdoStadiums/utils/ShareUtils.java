package com.bestdo.bestdoStadiums.utils;

import com.bestdo.bestdoStadiums.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ShareUtils {
	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	Activity context;

	public ShareUtils(Activity context) {
		super();
		this.context = context;
	}

	public void share(String description, String webpageUrl, String mer_name) {
		// 0是朋友，1是朋友圈
		// share2weixin(0);
		String appID = "wx1d30dc3cd2adc80c";
		String appSecret = "2b129f9c81e4a75de4c32ace80f83b9a";

		// 设置分享内容
		mController.setShareContent(description);
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.share_icon);
		// 设置分享图片, 参数2为图片的url地址

		mController.setShareMedia(new UMImage(context, thumb));
		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE);

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
		circleMedia.setTitle(mer_name);
		circleMedia.setShareImage(new UMImage(context, thumb));
		circleMedia.setTargetUrl(webpageUrl);
		mController.setShareMedia(circleMedia);
		mController.openShare(context, false);
	}

}
