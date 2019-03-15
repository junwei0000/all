package com.longcheng.lifecareplan.modular.webView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.http.SslError;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayBeforeBean;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

public class WebProjectActivity extends BaseActivity {

    private WebView webView;
    private NumberProgressBar mProgressBar;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case WXPayEntryActivity.BROAD_CAST_PAY_SUCCESS:
                    int code1 = intent.getIntExtra("errCode", 100);
                    notifyHtmlPatState(code1);
                    Log.i(TAG, "onReceive: BROAD_CAST_PAY_SUCCESS" + code1);
                    break;
                case WXPayEntryActivity.BROAD_CAST_PAY_FAILE:
                    int code2 = intent.getIntExtra("errCode", 100);
                    notifyHtmlPatState(code2);
                    Log.i(TAG, "onReceive: BROAD_CAST_PAY_FAILE" + code2);
                    break;
                case WXPayEntryActivity.BROAD_CAST_PAY_CANCLE:
                    int code3 = intent.getIntExtra("errCode", 100);
                    Log.i(TAG, "onReceive: BROAD_CAST_PAY_CANCLE" + code3);
                    notifyHtmlPatState(code3);
                    break;

            }
        }
    };

    private String webViewUrl = "";
    private static final int WEB_REQUEST_CODE = 0;

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
        return R.layout.activity_web_project;
    }

    @Override
    public void initView(View view) {
        webView = view.findViewById(R.id.webview);
        mProgressBar = view.findViewById(R.id.num_progress);
//        Button jump = view.findViewById(R.id.jump);
//        jump.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PackageManager packageManager = getPackageManager();
//                Intent intent = new Intent();
//                intent = packageManager.getLaunchIntentForPackage("com.markshuai.demojump");
//                startActivity(intent);
//            }
//        });
        getIntentData();
        initWebView();
        initRegister();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if ("recharge".equals(name)) {
            webViewUrl = "http://t.asdyf.com/home/testc/recharge";
        } else if ("activation".equals(name)) {
            webViewUrl = "http://t.asdyf.com/home/testc/index";
        } else if ("help".equals(name)) {
            webViewUrl = "http://t.asdyf.com/home/testc/lj_payment/goods_id/5/h_user_id/1035/id/178";
        } else if ("asd".equals(name)) {
            webViewUrl = "http://t.asdyf.com/";
        }
    }

    /**
     * @param
     * @name 注册广播
     * @auhtor MarkMingShuai
     * @Data 2017-10-25 16:48
     */
    private void initRegister() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WXPayEntryActivity.BROAD_CAST_PAY_SUCCESS);
        filter.addAction(WXPayEntryActivity.BROAD_CAST_PAY_FAILE);
        filter.addAction(WXPayEntryActivity.BROAD_CAST_PAY_CANCLE);
        mContext.registerReceiver(mReceiver, filter);
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
            webView.addJavascriptInterface(new WapJSInterface(), "jsAndroidPay");
            webView.setInitialScale(100);//100表示不缩放
            WebSettings setting = webView.getSettings();
            //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
            setting.setJavaScriptEnabled(true);
            //对离线应用的支持
            setting.setDomStorageEnabled(true);//对localStorage的支持
            setting.setAppCacheMaxSize(1024 * 1024 * 10);//设置缓冲大小，10M
            setting.setUseWideViewPort(true);//自适应屏幕大小
            setting.setPluginState(WebSettings.PluginState.ON); // 开启插件
            setting.setCacheMode(WebSettings.LOAD_DEFAULT);
            setting.setSupportZoom(true);// 设置可以支持缩放

            setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//自适应屏幕
            setting.setLoadWithOverviewMode(true);//设置默认加载的可视范围是大视野范围
            setting.setBuiltInZoomControls(false);// 设置出现缩放工具
//            setting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口

            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            webView.setVerticalScrollBarEnabled(false); //垂直不显示
        }
    }

    @Override
    public void initDataAfter() {
//        webView.loadUrl("http://t.asdyf.com/");
//        webView.loadUrl("http://t.asdyf.com/home/testc/recharge");
//        webView.loadUrl("file:///android_asset/index.html");
        Log.i(TAG, "initDataAfter: " + webViewUrl);
        webViewListener();
        webView.loadUrl(webViewUrl);
    }


    /**
     * @param
     * @Name 判断是不是ANdroid
     * @Data 2018/1/22 20:03
     * @Author :MarkShuai
     */
    public void htmlIsAndroid() {
        // 必须另开线程进行JS方法调用(否则无法调用)
        Log.i(TAG, "htmlIsAndroid");
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 注意调用的JS方法名要对应上
                // 调用javascript的callJS()方法
                webView.loadUrl("javascript:android()");
            }
        }, 500);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void widgetClick(View v) {
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
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.i(TAG, "onJsAlert  :" + message);
                Log.i(TAG, "url  :" + url);
                if (message.startsWith("{")) {
                    Log.i(TAG, "onJsAlertMessage  :" + message);
                    JSONObject jsonObject = JSON.parseObject(message);
                    PayAfterBean payBean = new PayAfterBean();
                    payBean.setAppid(jsonObject.getString("appid"));
                    payBean.setNoncestr(jsonObject.getString("noncestr"));
                    payBean.setPrepayid(jsonObject.getString("prepayid"));
                    payBean.setPartnerid(jsonObject.getString("partnerid"));
                    payBean.setSign(jsonObject.getString("sign"));
                    payBean.setPackages(jsonObject.getString("package"));
                    payBean.setTimestamp(jsonObject.getString("timestamp"));
                    PayUtils.getWeChatPayHtml(mContext, payBean);
                    return true;
                } else if (message.startsWith("intent")) {
                    String messUrl = message.substring(6);
                    Intent intent = new Intent(mContext, WebActivitySuccess.class);
                    intent.putExtra("url", messUrl);
                    Log.i(TAG, "onJsAlert:intent +" + messUrl);
                    startActivityForResult(intent, WEB_REQUEST_CODE);
                    return true;
                }
                return super.onJsAlert(view, url, message, result);
            }
        });


        // 点击链接继续在当前browser中响应
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.i(TAG, "shouldOverrideUrlLoading :" + url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "onPageFinished :" + url);
                htmlIsAndroid();
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

    @Override
    protected void onResume() {
        super.onResume();
        webView.requestFocus();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case WEB_REQUEST_CODE:
//                if (data != null) {
//                    Bundle bundle = data.getExtras();
//                    String backUrl = bundle.getString("backUrl");
//                    webView.loadUrl(backUrl);
//                }
//                break;
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param
     * @Name 通知html微信支付的状态
     * @Data 2018/1/23 18:53
     * @Author :MarkShuai
     */
    public void notifyHtmlPatState(int code) {
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: code" + code);
                webView.loadUrl("javascript:State_Of_Payment_F('" + code + "')");
            }
        }, 300);
    }

    //微信支付
    public void payJsMethod() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "payJsMethod1");
                // 必须另开线程进行JS方法调用(否则无法调用)
                webView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 注意调用的JS方法名要对应上
                        // 调用javascript的callJS()方法
                        Log.i(TAG, "payJsMethod2");
                        webView.loadUrl("javascript:alert(get_WXJson())");
//                        PackageManager packageManager = getPackageManager();
//                        Intent intent = new Intent();
//                        intent = packageManager.getLaunchIntentForPackage("com.markshuai.demojump");
//                        startActivity(intent);
                    }
                }, 500);
            }
        });
    }

    private class WapJSInterface {

        @JavascriptInterface
        public void weChatPayHtml() {
            Log.i(TAG, "weChatPayHtml()");
            ConstantManager.isHtmlPayMethod = 1;
            payJsMethod();
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                Log.i(TAG, "onKeyDown: ");
                webView.goBack();
                // 返回上一页面
                return true;
            } else {
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    ToastUtils.showToast("再按一次退出程序--->onKeyUp");
                    firstTime=secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        if (webView != null) {
            webView.clearHistory();
            webView.clearSslPreferences();
            webView.clearHistory();
            webView.clearCache(true);
            webView.destroy();
            Log.i(TAG, "onDestroy: clear");
        }
    }
}
