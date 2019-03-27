package com.longcheng.volunteer.widget.jswebview.browse.JsWeb;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebView;


import com.longcheng.volunteer.utils.DensityUtil;
import com.longcheng.volunteer.widget.jswebview.browse.BridgeWebView;
import com.longcheng.volunteer.widget.jswebview.browse.BridgeWebViewClient;

import java.util.Map;

/**
 * @param
 * @author MarkShuai
 * @name
 */
public abstract class CustomWebViewClient extends BridgeWebViewClient {

    Context mContext;

    public CustomWebViewClient(Context mContext, BridgeWebView webView) {
        super(webView);
        this.mContext = mContext;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.e("onPageHeaders", "url=" + url);
        if (url.contains("tel:")) {
            Log.e("onPageHeaders", "tel=" + url);
            DensityUtil.getPhoneToKey(mContext, url);
            return true;
        } else if (onPageHeaders(url) != null) {
            view.loadUrl(url, onPageHeaders(url));
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        view.loadUrl(onPageError(failingUrl));
    }

    /**
     * return errorUrl
     *
     * @param url
     * @return
     */
    public abstract String onPageError(String url);

    /**
     * HttpHeaders
     * return
     *
     * @return
     */
    @NonNull
    public abstract Map<String, String> onPageHeaders(String url);

}
