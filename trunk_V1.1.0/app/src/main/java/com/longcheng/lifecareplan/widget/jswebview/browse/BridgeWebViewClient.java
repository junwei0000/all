package com.longcheng.lifecareplan.widget.jswebview.browse;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.util.Log;
import android.webkit.SslErrorHandler;
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

    public static final int SHOWDIALOG = -2;
    public static final int DISMISSDIALOG = -3;
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
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
            if (url.contains("t.asdyf.com")) {
                webView.addUrlPageBackListItem(url);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
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

        //
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