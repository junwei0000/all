package com.longcheng.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;


import com.longcheng.web.browse.BridgeHandler;
import com.longcheng.web.browse.BridgeWebView;
import com.longcheng.web.browse.CallBackFunction;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;


/**
 * Created by Burning on 2018/9/11.
 */

public abstract class WebAct extends BaseActivity {
    @BindView(R.id.webView)
    public BridgeWebView mBridgeWebView;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;


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
        return R.layout.activity_web;
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {
    }

    @Override
    public void onClick(View view) {

    }

    String url;

    public void loadUrl(String url) {
        this.url = url;
        sewtCookie(url);
        // 打开页面，也可以支持网络url
        mBridgeWebView.loadUrl(url);
    }

    private void sewtCookie(String url) {
        /**
         * 缓存  Cookie
         */
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
//        String name = UserUtils.getUserName(mContext);
//        String phone = UserUtils.getUserPhone(mContext);
//        String userId = UserUtils.getUserId(mContext);
//        String avatar = UserUtils.getUserAvatar(mContext);
//        String token = UserUtils.getWXToken(mContext);
//        cookieManager.setCookie(url, "phone_user_name=" + name + Config.WEB_DOMAIN);
//        cookieManager.setCookie(url, "phone_user_phone=" + phone + Config.WEB_DOMAIN);
//        cookieManager.setCookie(url, "phone_user_id=" + userId + Config.WEB_DOMAIN);
//        cookieManager.setCookie(url, "phone_user_avatar=" + avatar + Config.WEB_DOMAIN);
//        cookieManager.setCookie(url, "phone_user_token=" + token + Config.WEB_DOMAIN);
//        cookieManager.setCookie(url, "isApp_Storage=1" + Config.WEB_DOMAIN);
//        Log.e("aaa", "name : " + name + " , " + phone + " , userId : " + userId + avatar + " , token : " + token);
        cookieManager.getCookie(url);
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public void initView(View view) {
        initWebView();
    }

    private void initWebView() {
        updateWebPic();
        //刷新头部标题
        mBridgeWebView.registerHandler("public_TitleChange", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
            }
        });

        //地图定位获取坐标----康农  坐堂医
        mBridgeWebView.registerHandler("user_getLongitudeLatitude", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                double[] mLngAndLat = LocationUtils.getInstance().getLngAndLat(mContext);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("phone_user_latitude", "" + mLngAndLat[0]);
                    jsonObject.put("phone_user_longitude", "" + mLngAndLat[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                function.onCallBack(jsonObject.toString());
            }
        });
        //返回按钮
        mBridgeWebView.registerHandler("about_back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                doFinish();
            }
        });
    }

    /**
     * ***********************上传图片*************************
     */
    private void updateWebPic() {
        mBridgeWebView.setWebChromeClient(new WebChromeClient() {
            // 配置权限（同样在WebChromeClient中实现）
            @SuppressLint("NewApi")
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            // For Android 5.0+
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                choseSinglePic();
                return true;
            }
            // For Android 3.0+

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                choseSinglePic();
            }
            //3.0--版本

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                choseSinglePic();
            }
            // For Android 4.1

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                choseSinglePic();
            }
        });
    }

    AblumWebUtils mAblumPicUtils;

    private void choseSinglePic() {
        if (mAblumPicUtils == null) {
            mAblumPicUtils = new AblumWebUtils(mActivity, mUploadMessage, mUploadCallbackAboveL);
        }
        mAblumPicUtils.setmUploadMessage(mUploadMessage);
        mAblumPicUtils.setmUploadCallbackAboveL(mUploadCallbackAboveL);
        mAblumPicUtils.onAddAblumClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAblumPicUtils != null && mAblumPicUtils.selectDialog != null && mAblumPicUtils.selectDialog.isShowing()) {
            mAblumPicUtils.selectDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == mAblumPicUtils.RESULTCAMERA
                    || requestCode == mAblumPicUtils.RESULTGALLERY) {
                mAblumPicUtils.setResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ******************end****************
     */

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBridgeWebView != null) {
            mBridgeWebView.destroy();
        }
    }


}
