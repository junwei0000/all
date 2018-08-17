package com.bestdo.bestdoStadiums.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bestdo.bestdoStadiums.business.GetSportsListBusiness;
import com.bestdo.bestdoStadiums.business.GetSportsListBusiness.GetSportsListBusinessCallback;
import com.bestdo.bestdoStadiums.business.UserOrderShengHuoBusiness;
import com.bestdo.bestdoStadiums.business.UserOrderShengHuoBusiness.GetUserOrderShengHuoCallback;
import com.bestdo.bestdoStadiums.business.UserOrdersBusiness;
import com.bestdo.bestdoStadiums.business.UserOrdersBusiness.GetUserOrdersCallback;
import com.bestdo.bestdoStadiums.control.adapter.FragmentAdapter;
import com.bestdo.bestdoStadiums.control.adapter.FragmentAdapter.OnReloadListener;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderAdapter;
import com.bestdo.bestdoStadiums.control.adapter.UserOrderSportAdapter;
import com.bestdo.bestdoStadiums.control.fragment.UserAllFragment;
import com.bestdo.bestdoStadiums.control.fragment.UserConfirmingFragment;
import com.bestdo.bestdoStadiums.control.fragment.UserFinishFragment;
import com.bestdo.bestdoStadiums.control.fragment.UserWaitPayFragment;
import com.bestdo.bestdoStadiums.control.fragment.UsersStayoffFragment;
import com.bestdo.bestdoStadiums.control.view.PullDownListView;
import com.bestdo.bestdoStadiums.control.view.PullDownListView.OnRefreshListioner;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.SearchGetSportParser;
import com.bestdo.bestdoStadiums.utils.parser.UserOrderGetSportParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.bestdo.bestdoStadiums.R;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebSettings.TextSize;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午4:28:11
 * @Description 类描述：
 */
public class UserOrderShengHuoActivity extends BaseActivity {
	private WebView webview;
	private String mobile;
	private String account;
	private String life_type;
	private String uid;

	public void onClick(View v) {
		switch (v.getId()) {
		}
	}

	protected void loadViewLayout() {
		setContentView(R.layout.user_order_shenghuo);
		CommonUtils.getInstance().addActivity(this);
	}

	protected void findViewById() {
		webview = (WebView) findViewById(R.id.webView_bri);
	}

	protected void setListener() {
		SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		mobile = bestDoInfoSharedPrefs.getString("mobile", "");
		uid = bestDoInfoSharedPrefs.getString("uid", "");
		Intent intent = getIntent();
		account = intent.getExtras().getString("account", "");
		life_type = intent.getExtras().getString("life_type", "");
	}

	HashMap<String, String> mhashmap;

	protected void processLogic() {
		mhashmap = new HashMap<String, String>();
		if (!TextUtils.isEmpty(life_type) && life_type.equals("2")) {
			mhashmap.put("uid", uid);
			mhashmap.put("card_code", account);
			mhashmap.put("life_type", life_type);
		} else {
			mhashmap.put("life_type", life_type);
			mhashmap.put("mobile", mobile);
		}
		new UserOrderShengHuoBusiness(this, mhashmap, new GetUserOrderShengHuoCallback() {
			@Override
			public void afterDataGet(HashMap<String, Object> dataMap) {
				if (dataMap != null) {
					String codeString = (String) dataMap.get("code");
					if (codeString.equals("200")) {
						// String html_url=(String)
						// dataMap.get("html_url");
						String date = (String) dataMap.get("data");
						if (!TextUtils.isEmpty(date))
							initDate(date);
						// CommonUtils.getInstance().initToast(context,date);
					} else {
						String date = (String) dataMap.get("data");
						CommonUtils.getInstance().initToast(context, date);
					}
				} else {
					CommonUtils.getInstance().initToast(context, "数据加载失败，请重新加载");
				}
				CommonUtils.getInstance().setClearCacheBackDate(mhashmap, dataMap);
			}
		});

	}

	@SuppressLint("NewApi")
	private void initDate(String html_url) {
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
		webview.setVisibility(View.VISIBLE);
		// 支持javascript
		webview.getSettings().setJavaScriptEnabled(true);
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
		webview.loadUrl(html_url);
		// webview.loadUrl("http://120.26.125.201:8080/WebApp/bespeak/toBespeak");
		webview.setWebChromeClient(new WebChromeClient() {
			public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
				callback.invoke(origin, true, false);
				super.onGeolocationPermissionsShowPrompt(origin, callback);
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
		webview.setWebViewClient(new WebViewClient() {

			@Override
			public void onLoadResource(WebView view, String url) {
				Log.e("PayZFBActivity.this", "onLoadResource：：：：：：" + url);
				super.onLoadResource(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.e("PayZFBActivity.this", "onPageStarted：：：：：：" + favicon);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Log.e("PayZFBActivity.this", "onReceivedError：：：：：：" + failingUrl);
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			@Override
			public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
				Log.e("PayZFBActivity.this", "onReceivedHttpAuthRequest：：：：：：" + realm);
				super.onReceivedHttpAuthRequest(view, handler, host, realm);
			}

			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				Log.e("PayZFBActivity.this", "onReceivedSslError：：：：：：" + error);
				super.onReceivedSslError(view, handler, error);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.e("PayZFBActivity.this", "onPageFinished：：：：：：" + url);
				super.onPageFinished(view, url);
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				return false;
			}

		});

	}

	/**
	 * 在 FragmentActivity 中统计时长：
	 */
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		MobclickAgent.onResume(this); // 统计时长
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		RequestUtils.cancelAll(this);
		super.onDestroy();
	}

	private void nowFinish() {
		if (webview != null && webview.canGoBack()) {
			webview.goBack();
		} else {
			finish();
			CommonUtils.getInstance().setPageBackAnim(this);
		}
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
