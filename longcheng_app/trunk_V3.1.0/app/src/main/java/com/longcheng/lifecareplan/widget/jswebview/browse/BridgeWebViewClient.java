package com.longcheng.lifecareplan.widget.jswebview.browse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * @param
 * @author MarkShuai
 * @name
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    private static final int SHOWDIALOG = -2;
    private static final int DISMISSDIALOG = -3;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOWDIALOG:
                    LoadingDialogAnim.show(webView.getContext());
                    break;
                case DISMISSDIALOG:
                    LoadingDialogAnim.dismiss(webView.getContext());
                    break;
            }
        }
    };

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.contains("alipay.com")) {//从支付宝返回
            webView.addUrlPageBackListItem(url);
        }
        if (parseScheme(url)) {//打开支付宝 第三方打款
            try {
                Intent intent;
                intent = Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                view.getContext().startActivity(intent);
                return true;
            } catch (Exception e) {

            }
        }
        try {
            /**
             * % 在URL中是特殊字符，需要特殊转义一下，
             解决办法：使用%25替换字符串中的%号
             */
            url = url.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setLoadPicInit(view);
        Log.e("URLDecoder", "super  url=" + url);
        if (url.contains("tel:")) {
            Log.e("onPageHeaders", "tel=" + url);
            DensityUtil.getPhoneToKey(webView.getContext(), url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            mHandler.sendEmptyMessage(DISMISSDIALOG);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            mHandler.sendEmptyMessage(SHOWDIALOG);
            //---------------------webview关于返回错乱----修改.1-----------------------------------------------
            if (url.contains("t.asdyf.com")) {
                webView.addUrlPageBackListItem(url);
            }
            //-----------------------------------------------------------------------
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public boolean parseScheme(String url) {
        if (url.contains("platformapi/startapp")) {
            return true;
        } else if (url.contains("web-other")) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 设置webview图片缓存本地----初始化加载设置
     *
     * @param view
     */
    public void setLoadPicInit(WebView view) {
//        view.getSettings().setBlockNetworkImage(true);
//        view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }
        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    /**
     * @param
     * @name 支持https
     * @time 2017/12/13 13:18
     * @author MarkShuai
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        handler.proceed(); // Ignore SSL certificate errors
    }
}