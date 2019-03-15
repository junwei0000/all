package com.longcheng.lifecareplan.modular.webView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;
import com.longcheng.lifecareplan.widget.jswebview.browse.JsWeb.CustomWebViewClient;
import com.longcheng.lifecareplan.widget.jswebview.browse.JsWeb.JavaCallHandler;
import com.longcheng.lifecareplan.widget.jswebview.browse.JsWeb.JsHandler;
import com.longcheng.lifecareplan.widget.jswebview.view.ProgressBarWebView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;

public class WebActivity extends BaseActivity {


    @BindView(R.id.webView)
    ProgressBarWebView mProgressBarWebView;

    private ArrayList<String> mHandlers = new ArrayList<>();

    ValueCallback<Uri> mUploadMessage;
    private static CallBackFunction mfunction;

    int RESULT_CODE = 0;


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
    public void initView(View view) {
        initWebView();
    }

    private void initWebView() {
        mProgressBarWebView.setWebViewClient(new CustomWebViewClient(mProgressBarWebView.getWebView()) {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LoadingDialogAnim.show(mContext);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LoadingDialogAnim.dismiss(mContext);
            }

            @Override
            public String onPageError(String url) {
                //指定网络加载失败时的错误页面
                return "file:///android_asset/error.html";
            }

            @Override
            public Map<String, String> onPageHeaders(String url) {
                // 可以加入header
                return null;
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
                mUploadMessage = uploadMsg;
                pickFile();
            }
        });

        // 打开页面，也可以支持网络url
        mProgressBarWebView.loadUrl("http://t.asdyf.com/");

        mHandlers.add("login");
        mHandlers.add("callNative");
        mHandlers.add("callJs");
        mHandlers.add("open");

        //回调js的方法
        mProgressBarWebView.registerHandlers(mHandlers, new JsHandler() {
            @Override
            public void OnHandler(String handlerName, String responseData, CallBackFunction function) {

                if (handlerName.equals("login")) {

                    ToastUtils.showToast(responseData);

                } else if (handlerName.equals("callNative")) {

                    ToastUtils.showToast(responseData);
                    function.onCallBack("我在上海");

                } else if (handlerName.equals("callJs")) {
                    ToastUtils.showToast(responseData);
                    // 想调用你的方法：
                    function.onCallBack("好的 这是图片地址 ：xxxxxxx");
                }
                if (handlerName.equals("open")) {
                    mfunction = function;
                    pickFile();

                }

            }
        });


        // 调用js
        mProgressBarWebView.callHandler("callNative", "hello H5, 我是java", new JavaCallHandler() {
            @Override
            public void OnHandler(String handlerName, String jsResponseData) {
                ToastUtils.showToast("h5返回的数据：" + jsResponseData);
            }
        });


        //发送消息给js
        mProgressBarWebView.send("哈喽", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

                ToastUtils.showToast(data);
            }
        });
    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressBarWebView != null) {
            if (mProgressBarWebView.getWebView() != null) {
                mProgressBarWebView.getWebView().destroy();
            }
        }
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {
    }

    @Override
    public void widgetClick(View v) {

    }
}
