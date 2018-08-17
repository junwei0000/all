/**
 * 
 */
package com.bestdo.bestdoStadiums.control.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.business.GetQiNiuTokenBusiness;
import com.bestdo.bestdoStadiums.business.UploadVedioBusiness;
import com.bestdo.bestdoStadiums.business.UploadVedioBusiness.GetUploadVedioCallback;
import com.bestdo.bestdoStadiums.business.UserAccountUpdateAblumBusiness;
import com.bestdo.bestdoStadiums.business.GetQiNiuTokenBusiness.GetQiNiuTokenCallback;
import com.bestdo.bestdoStadiums.business.UserAccountUpdateAblumBusiness.GetAccountUpdateAblumCallback;
import com.bestdo.bestdoStadiums.control.map.BestDoApplication;
import com.bestdo.bestdoStadiums.control.photo.vedio.AblumUtils;
import com.bestdo.bestdoStadiums.control.photo.vedio.FilePathResolver;
import com.bestdo.bestdoStadiums.model.UserCardsBuyInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.MyDialog;
import com.bestdo.bestdoStadiums.utils.PayDialog;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.utils.ShareUtils;
import com.bestdo.bestdoStadiums.R;
import com.example.androidbridge.BridgeHandler;
import com.example.androidbridge.BridgeWebView;
import com.example.androidbridge.CallBackFunction;
import com.example.androidbridge.DefaultHandler;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.Zone;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMShareBoardListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.zhy.view.HorizontalProgressBarWithNumber;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-3-7 下午5:41:07
 * @Description 类描述：
 */
public class MainNavImgActivity extends BaseActivity {
	LinearLayout page_top_layout;
	LinearLayout pagetop_layout_back;
	ImageView pagetop_iv_back;
	TextView pagetop_tv_name;
	private BridgeWebView webview;
	private String html_url;
	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private LinearLayout parent;
	private static final String APP_CACAHE_DIRNAME = "/webcache";
	private CallBackFunction function_, function_2, pay_function;
	private String shopPay_url;
	private boolean showTitleStatus;
	private LinearLayout pagetop_layout_you;
	private String name;
	private boolean showYouStatus;
	private ImageView pagetop_iv_you;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pagetop_layout_back:
			nowFinish();
			break;
		case R.id.pagetop_layout_you:
			String description = "百动-让运行成为中国人的生活方式";
			(new ShareUtils(this)).share(description, html_url, name);
			break;

		default:
			break;
		}
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.main_business_img);
		CommonUtils.getInstance().addActivity(this);
	}

	@Override
	protected void findViewById() {
		parent = (LinearLayout) findViewById(R.id.mainnav_img_lin);
		page_top_layout = (LinearLayout) findViewById(R.id.page_top_layout);
		pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
		pagetop_iv_back = (ImageView) findViewById(R.id.pagetop_iv_back);
		pagetop_layout_you = (LinearLayout) findViewById(R.id.pagetop_layout_you);
		pagetop_iv_you = (ImageView) findViewById(R.id.pagetop_iv_you);
		pagetop_iv_you.setBackgroundResource(R.drawable.stadiumdetail_collect_share);
		pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
		webview = (BridgeWebView) findViewById(R.id.webView_bri);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@SuppressLint("NewApi")
	@Override
	protected void setListener() {
		pagetop_layout_you.setOnClickListener(this);
		pagetop_layout_back.setOnClickListener(this);
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		Intent inentIntent = getIntent();
		html_url = inentIntent.getExtras().getString("html_url", "");
		name = inentIntent.getExtras().getString("name", "");
		showTitleStatus = inentIntent.getExtras().getBoolean("showTitleStatus", true);
		showYouStatus= inentIntent.getExtras().getBoolean("showYouStatus", true);
		String intent_from = inentIntent.getExtras().getString("intent_from", "");
		if (!TextUtils.isEmpty(intent_from) && intent_from.equals("CreateOrderSelectCardsActivity")) {
			CommonUtils.getInstance().addPayPageActivity(MainNavImgActivity.this);
			CommonUtils.getInstance().intntSelectCardsStatus = "CreatOrdersActivity";
		} else {
			CommonUtils.getInstance().intntSelectCardsStatus = "MainActivity";
		}
		shopPay_url = bestDoInfoSharedPrefs.getString("shopPay_url", "");
		String upload_url = inentIntent.getExtras().getString("upload_url", "");
		if (!TextUtils.isEmpty(upload_url)) {
			shopPay_url = upload_url;
		}
		pagetop_tv_name.setText(name);
		if (!showTitleStatus) {
			page_top_layout.setVisibility(View.GONE);
		}
		if (!showYouStatus) {
			pagetop_iv_you.setVisibility(View.GONE);
		}else{
			pagetop_iv_you.setVisibility(View.VISIBLE);
		}
		initDate();
	}

	protected PayDialog payDialog;
	protected String oid;
	protected String payType;
	protected String payMoney;

	/**
	 * 
	 */
	@SuppressLint("NewApi")
	private void initDate() {
		webview.setVisibility(View.VISIBLE);
		// 支持javascript
		webview.getSettings().setJavaScriptEnabled(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
		/**
		 * 第二种方法：
		 * 
		 * //设置加载进来的页面自适应手机屏幕
		 */
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setDisplayZoomControls(false);
		// 适应全屏 39适应竖屏 57适应横屏
		webview.setInitialScale(39);
		webview.getSettings().setRenderPriority(RenderPriority.HIGH);
		// 开启 DOM storage API 功能
		webview.getSettings().setDomStorageEnabled(true);
		// 开启 database storage API 功能
		webview.getSettings().setDatabaseEnabled(true);
		String cacheDirPath = this.getApplicationContext().getCacheDir().getAbsolutePath();
		// 设置数据库缓存路径
		webview.getSettings().setDatabasePath(cacheDirPath);
		// 启用地理定位
		webview.getSettings().setGeolocationEnabled(true);
		// 设置定位的数据库路径
		webview.getSettings().setGeolocationDatabasePath(cacheDirPath);
		webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
		webview.init(this);
		webview.initContext("http://gamevision4img.bs2cdn.yy.com/WebViewJavascriptBridge.min.js", new DefaultHandler());

		webview.setWebChromeClient(new WebChromeClient() {
			public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
				callback.invoke(origin, true, false);
				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
				this.openFileChooser(uploadMsg);
			}

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
				this.openFileChooser(uploadMsg);
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
			}
		});
		// webview.loadUrl("file:///android_asset/Api_index.html");
		// webView.loadUrl("http://www.pekingpiao.com/Mobile/Api/index.shtml");
		// webview.loadUrl("http://test.h5.shop.bestdo.com/");
		// webview.loadUrl("http://192.168.16.154/indexTest");
		// webview.loadUrl("http://yg.h5.shop.bestdo.com/"+ "?t=" +
		// System.currentTimeMillis());
		synCookies(this, html_url, "uid=" + bestDoInfoSharedPrefs.getString("uid", ""));
		webview.loadUrl(html_url);
		Log.e(html_url, "html_url" + html_url);
		webview.registerHandler("login", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i("MainPersonImgActivity", "handler = submitFromWeb, data from web = " + data);
				if (checkLoginStatus(Constans.getInstance().loginskiptoPersonImgActivity)) {
					String uid = bestDoInfoSharedPrefs.getString("uid", "");
					JSONObject oo = new JSONObject();
					try {
						oo.put("uid", uid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String jsonresult = Base64.encode(oo.toString().getBytes());
					Log.e("jsonresult", jsonresult);
					function.onCallBack(jsonresult);
				}
			}

		});

		/**
		 * 精彩时刻详情点赞
		 *
		 */
		webview.registerHandler("showMessage", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i("MainPersonImgActivity", "handler = submitFromWeb, data from web = " + data);
				if (checkLoginStatus(Constans.getInstance().loginskiptoPersonImgActivity)) {
					try {
						JSONObject jsonObject = new JSONObject(data);
						String msg = jsonObject.optString("msg");
						CommonUtils.getInstance().initToast(context, msg);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		/**
		 * 精彩时刻点赞分享数量
		 *
		 */
		webview.registerHandler("updateLandAndShareNum", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				JSONObject oo = new JSONObject();
				try {
					String version = ConfigUtils.getInstance().getVerCode(MainNavImgActivity.this);
					oo.put("version", version);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Log.e("version", oo.toString());
				String jsonresult = Base64.encode(oo.toString().getBytes());
				function.onCallBack(jsonresult);
			}
		});

		/**
		 * 精彩时刻详情分享微信
		 *
		 */
		webview.registerHandler("shareWX", new BridgeHandler() {
			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i("MainPersonImgActivity", "handler = submitFromWeb, data from web = " + data);
				if (checkLoginStatus(Constans.getInstance().loginskiptoPersonImgActivity)) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(data);
						String result = jsonObject.optString("title");
						String url = jsonObject.optString("url");
						String description = jsonObject.optString("description");
						share2weixin(0, url, result, description, function);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});
		/**
		 * 精彩时刻详情分享朋友圈
		 *
		 */
		webview.registerHandler("shareFriends", new BridgeHandler() {
			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i("MainPersonImgActivity", "handler = submitFromWeb, data from web = " + data);
				if (checkLoginStatus(Constans.getInstance().loginskiptoPersonImgActivity)) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(data);
						String result = jsonObject.optString("title");
						String url = jsonObject.optString("url");
						String description = jsonObject.optString("description");
						share2weixin(1, url, result, description, function);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// 尊享卡
		webview.registerHandler("openBuy8888Card", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i("MainPersonImgActivity", "handler = submitFromWeb, data from web = " + data);
				if (checkLoginStatus(Constans.getInstance().loginskiptoPersonImgActivity)) {
					UserCardsBuyInfo mUserCardsBuyInfo = null;
					try {
						JSONObject jsonObject = new JSONObject(data);
						String card_batch_id = jsonObject.optString("card_batch_id");
						String productid = jsonObject.optString("productid");
						String price = jsonObject.optString("price");
						String name = jsonObject.optString("name");
						String detail = jsonObject.optString("detail");
						String card_explain = jsonObject.optString("card_explain");
						mUserCardsBuyInfo = new UserCardsBuyInfo(productid, price, name, detail, card_batch_id);
						mUserCardsBuyInfo.setCard_explain(card_explain);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Intent intent = new Intent(MainNavImgActivity.this, CreatOrdersBuyCardActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("mUserCardsBuyInfo", mUserCardsBuyInfo);
					CommonUtils.getInstance().intntStatus = "ZhunXiangCard";
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, MainNavImgActivity.this);
				}
			}

		});
		webview.registerHandler("openBuyCard", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i("MainPersonImgActivity", "handler = submitFromWeb, data from web = " + data);
				if (checkLoginStatus(Constans.getInstance().loginskiptoPersonImgActivity)) {
					Intent intent = new Intent(MainNavImgActivity.this, UserCardsBuyActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					CommonUtils.getInstance().intntStatus = "UserCenterActivity";
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, MainNavImgActivity.this);
				}
			}

		});

		webview.registerHandler("app_to_pay", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				String mid = "";
				try {
					JSONObject jsonObject = new JSONObject(data);
					oid = jsonObject.optString("order_sn");
					mid = jsonObject.optString("mid");
					payType = jsonObject.optString("payType");
					payMoney = jsonObject.optString("payMoney");
					payMoney = PriceUtils.getInstance().gteDividePrice(payMoney, "100");
					Log.e("app_to_pay  jsonObject", "" + jsonObject);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				payDialog = new PayDialog(MainNavImgActivity.this, mHandler, Constans.showPayDialogByNavImg, oid,
						bestDoInfoSharedPrefs.getString("uid", ""), "", "", payMoney);
				payDialog.mid = mid;
				pay_function = function;
				payDialog.payType = payType;
				Constans.getInstance().function = function;
				payDialog.getPayDialog();

			}

		});

		webview.registerHandler("back", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				finish();
				CommonUtils.getInstance().setPageBackAnim(MainNavImgActivity.this);
			}

		});
		// getAppMethod('operateChoose');//运营首页
		// getAppMethod('operateMemberChoose');//会员出勤页面
		webview.registerHandler("operateChoose", new BridgeHandler() {// 运营首页

			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i("MainPerSonImgActivity", "handler = submitFromWeb, data from web = " + data);
				function_2 = function;
				Intent cityintent = new Intent(context, CashBookSelectDateActivity.class);
				cityintent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				cityintent.putExtra("starttime", starttime);
				cityintent.putExtra("endtime", endtime);
				startActivityForResult(cityintent, Constans.getInstance().ForResult3);
				CommonUtils.getInstance().setPageIntentAnim(cityintent, context);

			}

		});

		webview.registerHandler("pic_upload", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				if (checkLoginStatus(Constans.getInstance().loginskiptoPersonImgActivity)) {
					function_ = function;
					onDateClick();
				}
			}

		});
		webview.registerHandler("mv_upload", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				if (checkLoginStatus(Constans.getInstance().loginskiptoPersonImgActivity)) {
					function_ = function;
					chooseVideo();
				}
			}

		});

		webview.registerHandler("toBuyMember", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				if (checkLoginStatus(Constans.getInstance().loginskiptoPersonImgActivity)) {

					Intent intent = new Intent(context, UserMeberActiyity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);
				}
			}

		});

		webview.registerHandler("share", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i("MainPerSonImgActivity", "handler = submitFromWeb, data from web = ");
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(data);
					String result = jsonObject.optString("title");
					String url = jsonObject.optString("url");
					setShareUM(result, url);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
		// 下载监听
		webview.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
					long contentLength) {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}

		});

	}

	/**
	 * 选择本地视频
	 */
	private void chooseVideo() {
		Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
		// intent.setType("image/*");
		// intent.setType("audio/*"); //选择音频
		// intent.setType("video/*;image/*");//同时选择视频和图片
		intent.setType("video/*"); // 选择视频 （mp4 3gp 是android支持的视频格式）
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
		startActivityForResult(intent, RESULTVEDIO);
	}

	PopupWindow datePopWindow;

	private void onDateClick() {
		if (datePopWindow == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View mMenuView = layoutInflater.inflate(R.layout.user_account_ablum_dialog, null);
			TextView useraccount_ablum_tv_photos = (TextView) mMenuView.findViewById(R.id.useraccount_ablum_tv_photos);
			TextView useraccount_ablum_tv_selects = (TextView) mMenuView
					.findViewById(R.id.useraccount_ablum_tv_selects);
			TextView useraccount_ablum_tv_cancel = (TextView) mMenuView.findViewById(R.id.useraccount_ablum_tv_cancel);
			LinearLayout pop_layout = (LinearLayout) mMenuView.findViewById(R.id.pop_layout);
			datePopWindow = new PopupWindow(mMenuView, WindowManager.LayoutParams.FILL_PARENT,
					WindowManager.LayoutParams.WRAP_CONTENT);
			datePopWindow.setBackgroundDrawable(new BitmapDrawable());
			datePopWindow.setAnimationStyle(R.style.MyDialogStyleBottom);
			useraccount_ablum_tv_cancel.setOnClickListener(mABlumClickListener);
			useraccount_ablum_tv_photos.setOnClickListener(mABlumClickListener);
			useraccount_ablum_tv_selects.setOnClickListener(mABlumClickListener);
			pop_layout.setOnClickListener(mABlumClickListener);
		}
		final Window window = getWindow();
		final WindowManager.LayoutParams lp = window.getAttributes();
		// 设置透明度为0.3
		lp.alpha = 0.4f;
		window.setAttributes(lp);
		datePopWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		datePopWindow.setOnDismissListener(new OnDismissListener() {
			public void onDismiss() {
				lp.alpha = 1.0f;
				window.setAttributes(lp);
			}
		});

	}

	OnClickListener mABlumClickListener = new OnClickListener() {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.pop_layout:
			case R.id.useraccount_ablum_tv_cancel:
				pic_cancel();
				break;
			case R.id.useraccount_ablum_tv_photos:
				camera(v);
				break;
			case R.id.useraccount_ablum_tv_selects:
				gallery(v);
				break;
			default:
				break;
			}
			datePopWindow.dismiss();
		}

	};

	private void share2weixin(int flg, String url, String title, String description, final CallBackFunction function) {// 0是朋友，1是朋友圈
		if (!BestDoApplication.getInstance().msgApi.isWXAppInstalled()) {
			Toast.makeText(context, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
			return;
		}

		String appID = "wx1d30dc3cd2adc80c";
		String appSecret = "2b129f9c81e4a75de4c32ace80f83b9a";
		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.share_icon);

		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(context, thumb));
		/**
		 * 添加微信平台
		 */
		UMWXHandler wxHandler = new UMWXHandler(context, appID, appSecret);
		wxHandler.addToSocialSDK();
		wxHandler.setTargetUrl(url);
		wxHandler.setTitle(title);
		mController.setShareContent(description);

		/**
		 * 添加微信朋友圈
		 */
		UMWXHandler wxCircleHandler = new UMWXHandler(context, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		// 设置微信朋友圈分享内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(description);
		circleMedia.setTitle(title);
		circleMedia.setShareImage(new UMImage(context, thumb));
		circleMedia.setTargetUrl(url);
		mController.setShareMedia(circleMedia);

		// 关闭友盟分享的toast
		mController.getConfig().closeToast();

		if (flg == 0) {
			mController.postShare(context, SHARE_MEDIA.WEIXIN, new SnsPostListener() {

				@Override
				public void onStart() {
				}

				@Override
				public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
					if (arg1 == 200) {
						CommonUtils.getInstance().initToast(context, "分享成功");
						String jsonresult = Base64.encode("1".getBytes());
						function.onCallBack(jsonresult);

					} else {
					}
				}
			});
		} else if (flg == 1) {
			mController.postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

				@Override
				public void onStart() {
				}

				@Override
				public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
					if (arg1 == 200) {
						CommonUtils.getInstance().initToast(context, "分享成功");
						String jsonresult = Base64.encode("1".getBytes());
						function.onCallBack(jsonresult);
					} else {
					}
				}
			});
		}
		mController.setShareBoardListener(new UMShareBoardListener() {

			@Override
			public void onShow() {

			}

			@Override
			public void onDismiss() {
			}
		});

	}

	public void pic_cancel() {
		JSONObject o = new JSONObject();
		try {
			o.put("error", "3");
			String str = Base64.encode(o.toString().getBytes());
			Log.e("str", "encodeToString >>> " + str);
			function_.onCallBack(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private final int UPDATEABLUM = 1;// 上传头像
	private final int UPDATESUCCESS = 2;// 上传头像
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATEABLUM:
				if (tempFile != null) {
					System.out.println("tempFile.getName() = " + tempFile.getName());
					processUpdateInfo();
				} else {

				}
				break;
			case UPDATESUCCESS:
				boolean delete = tempFile.delete();
				System.out.println("delete = " + delete);
				break;
			case 5:
				// 退出支付
				payDialog.selectPayDialog.dismiss();
				JSONObject oo = new JSONObject();
				try {
					oo.put("code", "500");
					oo.put("order_sn", oid);
					oo.put("pay_type", "aliPay");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String jsonresult = Base64.encode(oo.toString().getBytes());
				pay_function.onCallBack(jsonresult);

				break;
			case 0x111:
				int ii = msg.arg1;
				// 设置进度条当前的完成进度
				mProgressBar.setProgress(ii);
				if (ii >= 100) {
					selectDialog.dismiss();
				}
				break;
			}
		}
	};
	/**
	 * 视频
	 */
	private HorizontalProgressBarWithNumber mProgressBar;
	private MyDialog selectDialog;

	public void vedioProcess() {
		selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_put_pengyouquan);// 创建Dialog并设置样式主题
		selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		selectDialog.show();
		TextView put_pengyouquan_text_title = (TextView) selectDialog.findViewById(R.id.put_pengyouquan_text_title);
		put_pengyouquan_text_title.setText("视频处理中...");
		TextView text_off = (TextView) selectDialog.findViewById(R.id.put_pengyouquan_text_off);// 取消
		text_off.setText("取消处理");
		mProgressBar = (HorizontalProgressBarWithNumber) selectDialog.findViewById(R.id.id_progressbar01);
		mProgressBar.setMax(100);
		text_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancell();
				selectDialog.dismiss();
			}
		});
		selectDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
					cancell();
				}
				return false;
			}
		});
	}

	private HashMap<String, String> mhashmap;
	private ProgressDialog mDialog;

	private void processUpdateInfo() {
		if (!ConfigUtils.getInstance().isNetWorkAvaiable(this)) {
			CommonUtils.getInstance().initToast(this, getString(R.string.net_tishi));
			return;
		}
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("suffix", "jpg");
		mhashmap.put("fileName", tempFile.getName());
		System.err.println(shopPay_url);
		new UserAccountUpdateAblumBusiness(this, mhashmap, tempFile, shopPay_url, "imgFile",
				new GetAccountUpdateAblumCallback() {

					@Override
					public void afterDataGet(HashMap<String, Object> dataMap) {
						CommonUtils.getInstance().setOnDismissDialog(mDialog);
						if (dataMap != null) {
							String jsonObject = (String) dataMap.get("jsonObject");
							String enToStr = Base64.encode(jsonObject.getBytes());
							Log.e("enToStr", "encodeToString >>> " + enToStr);
							function_.onCallBack(enToStr);
						} else {
							invalidUpload();
						}
						// 清除缓存
						CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

					}
				});
	}

	private void processVedioUpdateInfo(String vedio_url) {
		showDilag();
		mhashmap = new HashMap<String, String>();
		mhashmap.put("mv_url", vedio_url);
		new UploadVedioBusiness(this, mhashmap, new GetUploadVedioCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {

				CommonUtils.getInstance().setOnDismissDialog(mDialog);
				if (dataMap != null) {
					String jsonObject = (String) dataMap.get("jsonObject");
					String enToStr = Base64.encode(jsonObject.getBytes());
					Log.e("enToStr", "encodeToString >>> " + enToStr);
					function_.onCallBack(enToStr);
				} else {
					invalidUpload();
				}
				// 清除缓存
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);

			}
		});
	}

	/**
	 * 无效上传
	 */
	private void invalidUpload() {
		JSONObject oo = new JSONObject();
		try {
			oo.put("error", "error");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String jsonresult = Base64.encode(oo.toString().getBytes());
		function_.onCallBack(jsonresult);
	}

	private void showDilag() {
		try {
			if (mDialog == null) {
				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
			} else {
				mDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 手机相册
	 * 
	 * @param view
	 */
	public void gallery(View view) {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, RESULTGALLERY);
	}

	private String file_name = "shangcheng_photo.jpg";
	private File tempFile;
	private boolean isCancelled;
	private File select_vedio_file;
	protected int before;
	private Uri pictrueuri;

	/**
	 * 拍照
	 * 
	 * @param view
	 */
	public void camera(View view) {
		file_name = ConfigUtils.getInstance().getRandomNumber(16) + ".jpg";
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), AblumUtils.mkdirsName);
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			pictrueuri = Uri.fromFile(new File(cacheDir, file_name));
			intent.putExtra(MediaStore.EXTRA_OUTPUT, pictrueuri);
		}
		startActivityForResult(intent, RESULTCAMERA);
	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 设置Cookie
	 *
	 * @param context
	 * @param url
	 * @param cookie
	 *            格式：uid=21233 如需设置多个，需要多次调用
	 */
	public void synCookies(Context context, String url, String cookie) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieManager.setCookie(url, cookie);// cookies是在HttpClient中获得的cookie
		CookieSyncManager.getInstance().sync();
	}

	/**
	 * 获取七牛token
	 */
	public void getQiniuToken() {
		// uid 用户ID string 是
		// token 验证token string 是 token生成规则：md5(用户id+加密key)
		showDilag();
		mhashmap = new HashMap<String, String>();
		String uid = bestDoInfoSharedPrefs.getString("uid", "");
		mhashmap.put("uid", "" + uid);
		mhashmap.put("token", ConfigUtils.getInstance().MD5(uid + "zecepErPQ4UxtxK@GN6K48.fiC%R++"));

		new GetQiNiuTokenBusiness(context, mhashmap, new GetQiNiuTokenCallback() {

			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				// TODO Auto-generated method stub
				if (dataMap != null && dataMap.get("code").equals("200")) {
					String token = (String) dataMap.get("token");
					String domain = (String) dataMap.get("domain");
					Log.e("token,domain", "token=" + token + "domain=" + domain);
					vedioProcess();
					qiNiuPut(token, domain);
				} else {
					invalidUpload();
				}
				CommonUtils.getInstance().setOnDismissDialog(mDialog);
			}
		});
	}

	// 点击取消按钮，让UpCancellationSignal##isCancelled()方法返回true，以停止上传
	private void cancell() {
		isCancelled = true;
		if (function_ != null) {
			pic_cancel();
		}
	}

	/**
	 * 上传七牛
	 * 
	 * @param domain
	 * @param token
	 */
	private void qiNiuPut(String token, final String domain) {
		isCancelled = false;
		Configuration config = new Configuration.Builder().chunkSize(256 * 1024) // 分片上传时，每片的大小。
																					// 默认256K
				.putThreshhold(512 * 1024) // 启用分片上传阀值。默认512K
				.connectTimeout(10) // 链接超时。默认10秒
				.responseTimeout(60) // 服务器响应超时。默认60秒
				.recorder(null) // recorder分片上传时，已上传片记录器。默认null
				.zone(Zone.zone1) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
				.build();
		UploadManager uploadManager = new UploadManager(config);
		Map<String, String> params = new HashMap<String, String>();
		params.put("x:a", "test");
		params.put("x:b", "test2");
		// params Map<String, String> 自定义变量，key 必须以 x: 开始
		// mimeType String 指定文件的 mimeType
		// checkCrc boolean 是否验证上传文件
		// progressHandler UpProgressHandler 上传进度回调
		// cancellationSignal UpCancellationSignal 取消上传，当 isCancelled() 返回 true
		// 时，不再执行更多上传
		UploadOptions options = new UploadOptions(params, null, false, new UpProgressHandler() {
			@Override
			public void progress(String key, double percent) {
				// if (percent >= 0.8) {
				// isCancelled = true;
				// }
				double d = percent;
				int ii = (int) (d * 100);
				if (before != ii && ii > before) {
					Message msg = new Message();
					msg.what = 0x111;
					msg.arg1 = ii;
					mHandler.sendMessage(msg);
				}
				before = ii;
			}
		}, new UpCancellationSignal() {
			@Override
			public boolean isCancelled() {
				return isCancelled;
			}
		});
		// @param data 上传的数据
		// * @param key 上传数据保存的文件名
		// * @param token 上传凭证
		// * @param complete 上传完成后续处理动作
		// * @param options 上传数据的可选参数
		uploadManager.put(select_vedio_file, select_vedio_file.getName(), token, new UpCompletionHandler() {

			@Override
			public void complete(String key, ResponseInfo info, JSONObject res) {
				// res包含hash、key等信息，具体字段取决于上传策略的设置
				if (info.isOK() && res != null) {
					Log.i("qiniu", "Upload Success");
					String vedio_name = res.optString("key");
					String vedio_url = domain + "/" + vedio_name;
					Log.e("vedio_url", vedio_url);
					processVedioUpdateInfo(vedio_url);
				} else {
					Log.i("qiniu", "Upload Fail");
					// 如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
					invalidUpload();
				}
				Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
			}
		}, options);
	}

	/**
	 * 不裁剪直接使用，处理图片文件大小
	 */
	private void unCrop() {
		if (tempFile.length() > 0) {
			saveBitmapFile(setFileToBitmap(tempFile));
			mHandler.sendEmptyMessage(UPDATEABLUM);
		}
	}

	/**
	 * Bitmap对象保存味图片文件
	 * 
	 * @param bitmap
	 */
	public File saveBitmapFile(Bitmap bitmap) {
		try {
			File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), AblumUtils.mkdirsName);
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			file_name = ConfigUtils.getInstance().getRandomNumber(16) + ".jpg";
			tempFile = new File(cacheDir, file_name);// 将要保存图片的路径
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempFile;
	}

	/**
	 * decode这个图片并且按比例缩放以减少内存消耗，虚拟机对每张图片的缓存大小也是有限制的
	 * 
	 * @param f
	 * @return
	 */
	public Bitmap setFileToBitmap(File f) {
		Bitmap mBitmap = null;
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			int REQUIRED_SIZE = 550;// 上传大小
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			while (true) {
				if (width_tmp / scale <= REQUIRED_SIZE || height_tmp / scale <= REQUIRED_SIZE)
					break;
				scale += 1;
			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			o2.inPurgeable = true;
			o2.inInputShareable = true;
			mBitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
			return mBitmap;
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	private final int RESULTCAMERA = 1;// 拍照
	private final int RESULTGALLERY = 2;// 相册
	// private final int RESULTCROP = 3;// 剪切
	public final int RESULTVEDIO = 10;// 视频
	private Uri uri;
	private String starttime;
	private String endtime;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
		if (resultCode == Activity.RESULT_OK) {

			if (requestCode == RESULTGALLERY) {
				if (data != null) {
					// 得到图片的全路径
					uri = data.getData();
					System.out.println("uri = " + uri);
					String picturePath = FilePathResolver.getPath(this, uri);
					tempFile = new File(picturePath);
					unCrop();
					// -----------------------------
				}

			} else if (requestCode == RESULTCAMERA) {
				if (hasSdcard()) {
					sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, pictrueuri));
					// ----------不使用裁剪-------------
					File cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),
							AblumUtils.mkdirsName);
					if (!cacheDir.exists()) {
						cacheDir.mkdirs();
					}
					tempFile = new File(cacheDir, file_name);
					unCrop();
				} else {
					Toast.makeText(this, "未找到存储卡，无法存储照片！", 0).show();
				}

			} else if (requestCode == RESULTVEDIO) {
				Uri uri = data.getData();
				String v_path = FilePathResolver.getPath(this, uri);
				System.out.println("文件路径== " + v_path);
				select_vedio_file = new File(v_path);
				long size = select_vedio_file.length();
				if (size <= 1024 * 1024 * 50) {
					// <=50M
					getQiniuToken();
				} else if (size == 0) {
					CommonUtils.getInstance().initToast(context, "文件已损坏无法上传");
				} else {
					CommonUtils.getInstance().initToast(context, "请上传50M以内的视频哦");
				}
			} 
		}else if (resultCode == Constans.getInstance().ForResult3) {

			starttime = data.getStringExtra("starttime");
			endtime = data.getStringExtra("endtime");

			JSONObject oo = new JSONObject();
			try {
				oo.put("begin_time", starttime);
				oo.put("end_time", endtime);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String jsonresult = Base64.encode(oo.toString().getBytes());
			function_2.onCallBack(jsonresult);
		} else if (resultCode == 0) {
			if (function_ != null) {
				pic_cancel();
			}
		} else if (resultCode == Constans.showPayDialogByBuyBalanceResult) {
			if (payDialog != null)
				payDialog.getPayDialog();
		}

	}

	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;

	/**
	 * 判断登录状态
	 * 
	 * @param loginskiptostatus
	 * @return true=登录
	 */
	private boolean checkLoginStatus(String loginskiptostatus) {
		boolean loginstatus = true;
		String loginStatus = bestDoInfoSharedPrefs.getString("loginStatus", "");
		if (!loginStatus.equals(Constans.getInstance().loginStatus)) {
			bestDoInfoEditor.putString("loginskiptostatus", loginskiptostatus);
			bestDoInfoEditor.commit();
			Intent intent2 = new Intent(MainNavImgActivity.this, UserLoginActivity.class);
			intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent2);
			CommonUtils.getInstance().setPageIntentAnim(intent2, MainNavImgActivity.this);
			loginstatus = false;
		}
		return loginstatus;
	}

	int page;

	protected void processLogic() {

	}

	private void nowFinish() {
		if (datePopWindow != null && datePopWindow.isShowing()) {
			datePopWindow.dismiss();
			pic_cancel();
		} else {
			if (webview.canGoBack()) {
				webview.goBack();
			} else {
				clearAppCache();
				finish();
				CommonUtils.getInstance().setPageBackAnim(this);
			}
		}

	}

	private void setShareUM(String result, String url) {
		String appID = "wx1d30dc3cd2adc80c";
		String appSecret = "2b129f9c81e4a75de4c32ace80f83b9a";
		String webpageUrl = url;
		// String webpageUrl = "http://weixin.bestdo.com/item/info?mer_item_id="
		Log.e("webpageUrl", webpageUrl);
		String mer_name = result;
		String description = result;
		// 设置分享内容
		mController.setShareContent(description + webpageUrl.replace("&", "&amp;"));
		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.share_icon);
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(context, thumb));
		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
				SHARE_MEDIA.SINA);
		SinaSsoHandler sinaSsoHandler = new SinaSsoHandler();
		sinaSsoHandler.setTargetUrl(webpageUrl);
		sinaSsoHandler.setShareAfterAuthorize(true);
		sinaSsoHandler.addToSocialSDK();
		// 新浪分享
		SinaShareContent sinaShareContent = new SinaShareContent();
		// 设置分享文字
		sinaShareContent.setShareContent(description);
		// 设置点击消息的跳转URL
		sinaShareContent.setTargetUrl(webpageUrl.replace("&", "&amp;"));
		sinaShareContent.setShareImage(new UMImage(context, thumb));
		mController.setShareMedia(sinaShareContent);
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
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// clearWebViewCache();
		// clearAppCache();
	}

	/**
	 * 清除WebView缓存
	 */
	public void clearWebViewCache() {

		// 清理Webview缓存数据库
		try {
			deleteDatabase("webview.db");
			deleteDatabase("webviewCache.db");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// WebView 缓存文件
		File appCacheDir = new File(getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
		Log.e("MainPersonImgActivity", "appCacheDir path=" + appCacheDir.getAbsolutePath());

		File webviewCacheDir = new File(getCacheDir().getAbsolutePath() + "/webviewCache");
		Log.e("MainPersonImgActivity", "webviewCacheDir path=" + webviewCacheDir.getAbsolutePath());
		removeCookie();
		// 删除webview 缓存目录
		if (webviewCacheDir.exists()) {
			deleteFile(webviewCacheDir);
		}
		// 删除webview 缓存 缓存目录
		if (appCacheDir.exists()) {
			deleteFile(appCacheDir);
		}
	}

	private void removeCookie() {
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		CookieManager.getInstance().removeSessionCookie();
		CookieSyncManager.getInstance().sync();
		CookieSyncManager.getInstance().startSync();
	}

	/**
	 * 递归删除 文件/文件夹
	 * 
	 * @param file
	 */
	public void deleteFile(File file) {
		Log.i("MainPersonImgActivity", "delete file path=" + file.getAbsolutePath());

		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
			Log.e("MainPersonImgActivity", "delete file no exists " + file.getAbsolutePath());
		}
	}

	/**
	 * 清除app缓存
	 */
	public void clearAppCache() {
		// 清除webview缓存
		@SuppressWarnings("deprecation")
		File file = new File(getCacheDir().getAbsolutePath() + "/webviewCache");
		// 先删除WebViewCache目录下的文件

		if (file != null && file.exists() && file.isDirectory()) {
			for (File item : file.listFiles()) {
				item.delete();
			}
			file.delete();
		}
		deleteDatabase("webview.db");
		deleteDatabase("webview.db-shm");
		deleteDatabase("webview.db-wal");
		deleteDatabase("webviewCache.db");
		deleteDatabase("webviewCache.db-shm");
		deleteDatabase("webviewCache.db-wal");
		// 清除数据缓存
		clearCacheFolder(getFilesDir(), System.currentTimeMillis());
		clearCacheFolder(getCacheDir(), System.currentTimeMillis());

	}

	/**
	 * 清除缓存目录
	 * 
	 * @param dir
	 *            目录
	 * @param numDays
	 *            当前系统时间
	 * @return
	 */
	private int clearCacheFolder(File dir, long curTime) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, curTime);
					}
					if (child.lastModified() < curTime) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
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
}
