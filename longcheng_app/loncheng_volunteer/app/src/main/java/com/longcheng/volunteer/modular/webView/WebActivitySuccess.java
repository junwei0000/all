package com.longcheng.volunteer.modular.webView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivity;
import com.longcheng.volunteer.widget.dialog.LoadingDialogAnim;
import com.longcheng.volunteer.widget.jswebview.view.NumberProgressBar;

public class WebActivitySuccess extends BaseActivity {

    private WebView webView;
    private String webUrl;
    private NumberProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web_success;
    }

    @Override
    public void initView(View view) {
        getIntentData();
        webView = findViewById(R.id.webView);
        mProgressBar = view.findViewById(R.id.num_progress);
        initWebView();
    }

    /**
     * @param
     * @Name 获取Intent传值
     * @Data 2018/1/24 19:24
     * @Author :MarkShuai
     */
    private void getIntentData() {
        Intent intent = getIntent();
        webUrl = intent.getStringExtra("url");
    }

    @Override
    public void initDataAfter() {
        webView.loadUrl(webUrl);
        webViewListener();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * @param
     * @Name 初始化WebView
     * @Data 2018/1/18 17:11
     * @Author :MarkShuai
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        if (webView != null) {

            webView.addJavascriptInterface(new WapJSInterface(), "paySuccess");
            webView.setInitialScale(100);

            WebSettings setting = webView.getSettings();
            setting.setJavaScriptEnabled(true);
            setting.setLoadWithOverviewMode(true);
            //对离线应用的支持
            setting.setDomStorageEnabled(true);
            setting.setAppCacheMaxSize(1024 * 1024 * 10);//设置缓冲大小，10M
            setting.setUseWideViewPort(true);
//            setting.setPluginState(WebSettings.PluginState.ON); // 开启插件
//            setting.setCacheMode(WebSettings.LOAD_DEFAULT);
            String appCacheDir = getApplicationContext().getDir("cache", MODE_PRIVATE).getPath();   //缓存
            setting.setSupportZoom(true);
            setting.setUseWideViewPort(true);

            setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            setting.setLoadWithOverviewMode(true);
            setting.setBuiltInZoomControls(false);
            setting.setJavaScriptCanOpenWindowsAutomatically(true);

            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            webView.setVerticalScrollBarEnabled(false); //垂直不显示

        }

    }

    private void webViewListener() {

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 95) {
                    mProgressBar.setVisibility(View.GONE);
                    LoadingDialogAnim.dismiss(mContext);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    LoadingDialogAnim.show(mContext);
                }
                mProgressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.i(TAG, "onJsConfirm: " + url);
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.i(TAG, "onJsAlert  :" + message);
                Log.i(TAG, "url  :" + url);
                if (message.startsWith("pay")) {
                    String backUrl = message.substring(3);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("backUrl", backUrl);
                    intent.putExtras(bundle);
                    WebActivitySuccess.this.setResult(1, intent);
                }
                return super.onJsAlert(view, url, message, result);
            }
        });

        // 点击链接继续在当前browser中响应
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
                view.loadUrl(url);
                Log.i(TAG, "shouldOverrideUrlLoading :" + url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "onPageFinished :" + url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //忽略https验证错误问题
                Log.i(TAG, "onReceivedSslError :" + error);
                handler.proceed();
            }

        });

    }

    /**
     * @param
     * @Name 获取返回的URL
     * @Data 2018/1/24 19:50
     * @Author :MarkShuai
     */
    public void getBackUrl() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: getBackUrl");
                webView.loadUrl("javascript:gsPayForSuccess__Global()");
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            getBackUrl();
        }
        return super.onKeyDown(keyCode, event);
    }


    private class WapJSInterface {
        @JavascriptInterface
        public void jsBackUpPage() {
            getBackUrl();
        }
    }
}
