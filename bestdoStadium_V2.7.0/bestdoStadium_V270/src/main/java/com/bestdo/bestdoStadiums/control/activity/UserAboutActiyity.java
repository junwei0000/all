package com.bestdo.bestdoStadiums.control.activity;

import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ScoreUtils;
import com.bestdo.bestdoStadiums.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午4:26:41
 * @Description 类描述：关于
 */
public class UserAboutActiyity extends BaseActivity {
	private TextView pagetop_tv_name, userabout_tv_tel;
	private LinearLayout pagetop_layout_back;
	private TextView userabout_tv_versionName;
	private TextView userabout_tv_cont2;
	private TextView userabout_tv_cont1;
	private TextView userabout_tv_share;
	// 首先在您的Activity中添加如下成员变量
	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private LinearLayout userabout_layout_score;

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			toFinish();
			break;
		case R.id.userabout_tv_tel:
			CommonUtils.getInstance().getPhoneToKey(UserAboutActiyity.this);
			break;
		case R.id.userabout_tv_share:
			setShareUM();
			break;
		case R.id.userabout_layout_score:
			ScoreUtils.getInstance().skipAppScore(context);
			break;
		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {

		setContentView(R.layout.user_about);
		CommonUtils.getInstance().addActivity(UserAboutActiyity.this);
	}

	@Override
	protected void findViewById() {
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		userabout_tv_tel = (TextView) findViewById(R.id.userabout_tv_tel);
		userabout_tv_versionName = (TextView) findViewById(R.id.userabout_tv_versionName);
		userabout_tv_cont1 = (TextView) findViewById(R.id.userabout_tv_cont1);
		userabout_tv_cont2 = (TextView) findViewById(R.id.userabout_tv_cont2);
		userabout_tv_share = (TextView) findViewById(R.id.userabout_tv_share);
		userabout_layout_score = (LinearLayout) findViewById(R.id.userabout_layout_score);
		if (!ScoreUtils.getInstance().haveUserApp(context)) {
			userabout_layout_score.setVisibility(View.GONE);
		}
	}

	@Override
	protected void setListener() {
		userabout_tv_tel.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		userabout_tv_share.setOnClickListener(this);
		userabout_layout_score.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		pagetop_tv_name.setText(getResources().getString(R.string.usercenter_about));
		String verName = ConfigUtils.getInstance().getVerName(context);
		userabout_tv_versionName.setText(verName);
		userabout_tv_cont1.setText(Html.fromHtml(
				"我们只是很厉害而已<br/><br/>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;率先登陆新三板挂牌上市的国内体育服务平台类公司，具备全国运营能力的体育服务公司， 国内领先的体育运动服务公司！我们不是在刷数据，我们只是真的很厉害而已！2007年成立至今的北京新赛点体育投资股份有限公司（NEEQ：834425），一直坚持用运动的魅力，打造现代中国人新的生活方式。"));
		userabout_tv_cont2.setText(Html.fromHtml(
				"<br/>成为更好的我们<br/><br/>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;我们全情投入，绽放光芒。新赛点旗下“百动体育互联网平台”应运而生。作为目前国内领先的体育运动服务平台，通过整合覆盖全国的线下场馆资源，基于移动互联网技术和理念，为企业客户和个人用户提供全运动、多场景的服务。我们希望更多的人可以动起来，因为你收获的不仅是强健的体魄，更多的是自我的成长。<br/><br/>要让运动改变千万人<br/><br/>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;自2007年成立至今，十年光阴，我们迅速成长，把总部设在北京，并在上海、广州、深圳、盐城等地均设有分支机构。我们希望让运动，这个充满“快乐因子”的生活方式，席卷全国。现在平均每年服务人次近千万，范围覆盖国内100多个城市，十多种运动大项，直营或合作场馆已超过3000家。<br/><br/>重新定义体育+互联网<br/><br/>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;有人说，运动的本质是对自我的挑战。在体育产业“黄金十年”到来之时，我们也将对更好的自己说声Hi。百动体育将以“体育+互联网”的创新姿态，依托强大的整合和执行能力，围绕“大用户，大健康，大数据”， 倾力打造体育服务平台生态，为全民健身提供全运动的服务产品和优质的服务体验，让每个人都能在运动中找寻到全新的自己。"));
	}

	private void setShareUM() {
		String appID = "wx1d30dc3cd2adc80c";
		String appSecret = "2b129f9c81e4a75de4c32ace80f83b9a";
		String webpageUrl = "http://new.bd.app.bestdo.com/2.3.0/user/share";
		// String webpageUrl = "http://weixin.bestdo.com/item/info?mer_item_id="
		String mer_name = "百动运动管家";
		String description = "让运动成为中国人的生活方式";
		// 设置分享内容
		mController.setShareContent(description);
		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.share_icon);
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(context, thumb));
		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
				SHARE_MEDIA.SINA);

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