package com.longcheng.lifecareplan.modular.webView;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.Permission.MPermissionUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.share.ShareHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialog;
import com.longcheng.lifecareplan.widget.dialog.SharePopWindow;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeHandler;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebView;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebViewClient;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;
import com.longcheng.lifecareplan.widget.jswebview.browse.DefaultHandler;
import com.longcheng.lifecareplan.widget.jswebview.view.NumberProgressBar;
import com.longcheng.lifecareplan.zxing.activity.MipcaCaptureActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebProjectActivityBridge extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.num_progress)
    NumberProgressBar numProgress;
    @BindView(R.id.js_webView)
    BridgeWebView jsWebView;
    @BindView(R.id.rl_web_main)
    RelativeLayout rlWebMain;

    private String webViewUrl;
    private static final int QR_QEQUEST_CODE = 1;
    private String shareUrl = "";
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
//                case WXPayEntryActivity.BROAD_CAST_PAY_SUCCESS:
//                    int code1 = intent.getIntExtra("errCode", 100);
//                    Log.i(TAG, "onReceive: BROAD_CAST_PAY_SUCCESS" + code1);
//                    notifyHtmlPatState(code1);
//                    break;
//                case WXPayEntryActivity.BROAD_CAST_PAY_FAILE:
//                    int code2 = intent.getIntExtra("errCode", 100);
//                    Log.i(TAG, "onReceive: BROAD_CAST_PAY_FAILE" + code2);
//                    notifyHtmlPatState(code2);
//                    break;
//                case WXPayEntryActivity.BROAD_CAST_PAY_CANCLE:
//                    int code3 = intent.getIntExtra("errCode", 100);
//                    Log.i(TAG, "onReceive: BROAD_CAST_PAY_CANCLE" + code3);
//                    notifyHtmlPatState(code3);
//                    break;
            }
        }
    };

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web_project_bridg;
    }

    @Override
    public void initView(View view) {
        getIntentData();
        initRegister();
        setOrChangeTranslucentColor(toolbar, null);
        initWebView();
    }

    /**
     * @param
     * @Name 获取Intent传来的值
     * @Data 2018/1/25 16:59
     * @Author :MarkShuai
     */
    private void getIntentData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        //测试start
        if ("recharge".equals(name)) {
            webViewUrl = "http://t.asdyf.com/home/testc/recharge";
        } else if ("activation".equals(name)) {
            webViewUrl = "http://t.asdyf.com/home/testc/index";
        } else if ("help".equals(name)) {
            webViewUrl = "http://t.asdyf.com/home/testc/lj_payment/goods_id/5/h_user_id/1035/id/178";
        } else if ("asd".equals(name)) {
            webViewUrl = "http://t.asdyf.com/";
        }
        //测试end

        webViewUrl = "http://t.asdyf.com/";

    }

    /**
     * @param
     * @name 注册广播
     * @auhtor MarkMingShuai
     * @Data 2017-10-25 16:48
     */
    private void initRegister() {
        IntentFilter filter = new IntentFilter();
//        filter.addAction(WXPayEntryActivity.BROAD_CAST_PAY_SUCCESS);
//        filter.addAction(WXPayEntryActivity.BROAD_CAST_PAY_FAILE);
//        filter.addAction(WXPayEntryActivity.BROAD_CAST_PAY_CANCLE);
        mContext.registerReceiver(mReceiver, filter);
    }

    /**
     * @param
     * @Name 初始化WebView
     * @Data 2018/1/25 17:32
     * @Author :MarkShuai
     */
    private void initWebView() {
        jsWebView.setDefaultHandler(new DefaultHandler());
        jsWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                numProgress.setProgress(newProgress);
                if (newProgress >= 95) {
                    numProgress.setVisibility(View.GONE);
                    LoadingDialog.dismiss(mContext);
                } else {
                    numProgress.setVisibility(View.VISIBLE);
                    LoadingDialog.show(mContext);
                }
            }
        });

        jsWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        jsWebView.setWebViewClient(new MyWebViewClient(jsWebView));

        jsWebView.loadUrl(webViewUrl);
    }

    /**
     * @param
     * @Name 注册支付
     * @Data 2018/1/25 17:22
     * @Author :MarkShuai
     */
    public void htmlPay() {
        /**
         * js发送给Android消息   submitFromWeb 是js调用的方法名    安卓\返回给js
         */
        Log.i(TAG, "htmlPay: 初始化一下吧");
        jsWebView.registerHandler("htmlPay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                Log.i(TAG, "htmlPay handler: " + data);
                JSONObject jsonObject = JSON.parseObject(data).getJSONObject("param");
                Log.i(TAG, "htmlPay json: " + jsonObject.toString());
                PayWXAfterBean payBean = new PayWXAfterBean();
                payBean.setAppid(jsonObject.getString("appid"));
                payBean.setNoncestr(jsonObject.getString("noncestr"));
                payBean.setPrepayid(jsonObject.getString("prepayid"));
                payBean.setPartnerid(jsonObject.getString("partnerid"));
                payBean.setSign(jsonObject.getString("sign"));
                payBean.setPackages(jsonObject.getString("package"));
                payBean.setTimestamp(jsonObject.getString("timestamp"));
                PayUtils.getWeChatPayHtml(mContext, payBean);

            }
        });
    }

    /**
     * @param
     * @Name 分享弹框
     * @Data 2018/2/5 18:45
     * @Author :MarkShuai
     */
    private void showSharePopupWindows() {
        Log.i(TAG, "showSharePopupWindows: 初始化一下");
        jsWebView.registerHandler("showSharePopupWindows", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "showSharePopupWindows: " + data);
                SharePopWindow sharePopWindow = new SharePopWindow(mContext, v -> {

                    JSONObject param = JSON.parseObject(data).getJSONObject("param");
                    String title = param.getString("title");
                    String content = param.getString("content");
                    String imgUrl = param.getString("img");
                    String httpContent = param.getString("http");
                    switch (v.getId()) {
                        case R.id.rl_wechat:
                            ShareHelper.getINSTANCE().shareActionAll((Activity) mContext, SHARE_MEDIA.WEIXIN, imgUrl,
                                    content, httpContent, title);
                            break;
                        case R.id.rl_friends:
                            ShareHelper.getINSTANCE().shareActionAll((Activity) mContext, SHARE_MEDIA.WEIXIN_CIRCLE, imgUrl,
                                    content, httpContent, title);
                            break;
                    }
                });

                sharePopWindow.showAtLocation(rlWebMain, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    /**
     * @param
     * @Name 扫描二维码
     * @Data 2018/1/30 10:38
     * @Author :MarkShuai
     */
    public void scanQRCodeAndroid() {
        Log.i(TAG, "scanQRCodeAndroid: 初始化一下吧");
        Log.i(TAG, "scanQRCodeAndroid: 初始化一下吧");
        jsWebView.registerHandler("scanQRCodeAndroid", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                Log.i(TAG, "scanQRCodeAndroid handler: " + data);
                MPermissionUtils.requestPermissionsResult((Activity) mContext, 0, new String[]{Manifest.permission.CAMERA}, new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(mContext, MipcaCaptureActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", "webView");
                        intent.putExtras(bundle);
                        startActivityForResult(intent, QR_QEQUEST_CODE);
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(mContext);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (requestCode) {
                case QR_QEQUEST_CODE:
                    Bundle bundle = data.getExtras();
                    String resultString = bundle.getString("resultString");
                    Log.i(TAG, "onActivityResult: resultString  :  " + resultString);
                    scanQRCodeResult(resultString);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param
     * @Name 将扫描结果返回给Html
     * @Data 2018/1/30 10:47
     * @Author :MarkShuai
     */
    private void scanQRCodeResult(String result) {
        Log.i(TAG, "scanQRCodeResult: result  : " + result);
        jsWebView.callHandler("scanQRCodeResult", result, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i(TAG, "onCallBack: 调用结束" + data);
            }
        });
    }

    /**
     * @param
     * @Name 判断是不是Android
     * @Data 2018/1/25 17:15
     * @Author :MarkShuai
     */
    private void htmlIsAndroid() {
        Log.i(TAG, "htmlIsAndroid: start");
        jsWebView.callHandler("isAndroid", "1", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i(TAG, "onCallBack  htmlIsAndroid: " + data);
            }
        });
    }

    /**
     * @param
     * @Name 通知html微信支付的状态
     * @Data 2018/1/23 18:53
     * @Author :MarkShuai
     */
    public void notifyHtmlPatState(int code) {
        jsWebView.callHandler("notifyHtmlPatState", code + "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
            }
        });
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        if (jsWebView != null) {
            jsWebView.destroy();
        }

        ShareHelper.getINSTANCE().release(this);
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (jsWebView.canGoBack()) {
                Log.i(TAG, "onKeyDown: ");
                jsWebView.goBack();
                // 返回上一页面
                return true;
            } else {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    ToastUtils.showToast("再按一次退出程序");
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
            Log.i(TAG, "MyWebViewClient: ");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            LogUtils.i(TAG, "shouldOverrideUrlLoading: ");
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            LogUtils.i(TAG, "onPageFinished: " + url);
            htmlIsAndroid();
            htmlPay();
            scanQRCodeAndroid();
            showSharePopupWindows();
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            LogUtils.i(TAG, "onReceivedSslError: ");
            handler.proceed();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

}
