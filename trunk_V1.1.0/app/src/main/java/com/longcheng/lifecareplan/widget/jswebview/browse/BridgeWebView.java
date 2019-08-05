package com.longcheng.lifecareplan.widget.jswebview.browse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.longcheng.lifecareplan.widget.jswebview.browse.JsWeb.CustomWebChromeClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @param
 * @author MarkShuai
 * @name
 */
@SuppressLint("SetJavaScriptEnabled")
public class BridgeWebView extends WebView implements WebViewJavascriptBridge {

    private final String TAG = "BridgeWebView";

    public static String toLoadJs = "WebViewJavascriptBridge.js";
    Map<String, CallBackFunction> responseCallbacks = new HashMap<String, CallBackFunction>();
    Map<String, BridgeHandler> messageHandlers = new HashMap<String, BridgeHandler>();
    BridgeHandler defaultHandler = new DefaultHandler();

    private List<Message> startupMessage = new ArrayList<Message>();
    private long uniqueId = 0;

    public List<Message> getStartupMessage() {
        return startupMessage;
    }

    public void setStartupMessage(List<Message> startupMessage) {
        this.startupMessage = startupMessage;
    }


    public BridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public BridgeWebView(Context context) {
        super(context);
        init();
    }
    //-----------------------webview关于返回错乱--修改.1-----------------------------------------------
    /**
     * 记录加载的H5页面顺序，返回时用
     */
    public List<String> urlPageBackList = new ArrayList<String>();
    /**
     * 临时存储有效的路径
     */
    private List<String> urlPageBackList__ = new ArrayList<String>();
    /**
     * 上一个页面的索引
     */
    public int beforepageIndex = -1;

    /**
     * 是否正在返回操作
     */
    public boolean clickPageBacking = false;

    /**
     * 添加h5页面集合
     *
     * @param url
     */
    public void addUrlPageBackListItem(String url) {
        if (clickPageBacking) {//返回
            clickPageBacking = false;
        } else {//跳转页面初始化页面索引
            beforepageIndex = -1;
        }
        if (urlPageBackList.contains(url)) {
            int dex = urlPageBackList.indexOf(url);
            if (dex < urlPageBackList.size()) {
                urlPageBackList__.clear();
                for (int dex_ = 0; dex_ <= dex; dex_++) {
                    urlPageBackList__.add(urlPageBackList.get(dex_));
                }
                urlPageBackList.clear();
                urlPageBackList.addAll(urlPageBackList__);
            }
        } else {
            urlPageBackList.add(url);
        }
        Log.e("goBack", "url=" + url);
//        Log.e("goBack", "urlPageBackList=\n" + urlPageBackList.toString());
        Log.e("goBack", "********************************************************************************");
    }

    //--------------------------------------------------------------------

    /**
     * @param handler default handler,handle messages send by js without assigned handler name,
     *                if js message has handler name, it will be handled by named handlers registered by native
     */
    public void setDefaultHandler(BridgeHandler handler) {
        this.defaultHandler = handler;
    }

    private void init() {
        WebSettings webviewSettings = this.getSettings();
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        webviewSettings.setJavaScriptEnabled(true);
        /**
         * webview js 点击事件失效 localStorage  缓存问题
         */
        webviewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        this.requestFocus();
        webviewSettings.setAllowFileAccess(true);
        webviewSettings.setDatabaseEnabled(true);
        webviewSettings.setDomStorageEnabled(true);//打开本地缓存提供JS调用,至关重要
        webviewSettings.setGeolocationEnabled(true);
        String dbPath = getContext().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webviewSettings.setDatabasePath(dbPath);
        webviewSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
        webviewSettings.setAppCacheEnabled(true);
        String appCaceDir = getContext().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webviewSettings.setAppCachePath(appCaceDir);
        webviewSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        //-----------------------------------------------
        this.setWebViewClient(generateBridgeWebViewClient());
        //Api Level 17 关键性代码，这里要给webview添加这行代码，才可以点击之后正常播放音频。记录一下。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webviewSettings.setMediaPlaybackRequiresUserGesture(false);
        }
        /**
         * ************************标注：设置webview 字体，防止使用手机字体 导致数据显示变型***************************
         */
        webviewSettings.setTextSize(WebSettings.TextSize.NORMAL);
        // 判断系统版本是不是5.0或之上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //让系统不屏蔽混合内容和第三方Cookie
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
            webviewSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 不支持缩放
        webviewSettings.setSupportZoom(false);

        // 自适应屏幕大小
        webviewSettings.setUseWideViewPort(true);
        webviewSettings.setLoadWithOverviewMode(true);
        this.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    protected BridgeWebViewClient generateBridgeWebViewClient() {
        return new BridgeWebViewClient(this);
    }

    void handlerReturnData(String url) {
        String functionName = BridgeUtil.getFunctionFromReturnUrl(url);
        CallBackFunction f = responseCallbacks.get(functionName);
        String data = BridgeUtil.getDataFromReturnUrl(url);
        if (f != null) {
            f.onCallBack(data);
            responseCallbacks.remove(functionName);
            return;
        }
    }

    @Override
    public void send(String data) {
        send(data, null);
    }

    @Override
    public void send(String data, CallBackFunction responseCallback) {
        doSend(null, data, responseCallback);
    }

    private void doSend(String handlerName, String data, CallBackFunction responseCallback) {
        Message m = new Message();
        if (!TextUtils.isEmpty(data)) {
            m.setData(data);
        }
        if (responseCallback != null) {
            String callbackStr = String.format(BridgeUtil.CALLBACK_ID_FORMAT, ++uniqueId + (BridgeUtil.UNDERLINE_STR + SystemClock.currentThreadTimeMillis()));
            responseCallbacks.put(callbackStr, responseCallback);
            m.setCallbackId(callbackStr);
        }
        if (!TextUtils.isEmpty(handlerName)) {
            m.setHandlerName(handlerName);
        }
        queueMessage(m);
    }

    private void queueMessage(Message m) {
        if (startupMessage != null) {
            startupMessage.add(m);
        } else {
            dispatchMessage(m);
        }
    }

    void dispatchMessage(Message m) {
        String messageJson = m.toJson();
        //escape special characters for json string
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        String javascriptCommand = String.format(BridgeUtil.JS_HANDLE_MESSAGE_FROM_JAVA, messageJson);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            this.loadUrl(javascriptCommand);
        }
    }

    void flushMessageQueue() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            loadUrl(BridgeUtil.JS_FETCH_QUEUE_FROM_JAVA, new CallBackFunction() {

                @Override
                public void onCallBack(String data) {
                    // deserializeMessage
                    List<Message> list = null;
                    try {
                        list = Message.toArrayList(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    if (list == null || list.size() == 0) {
                        return;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        Message m = list.get(i);
                        String responseId = m.getResponseId();
                        // 是否是response
                        if (!TextUtils.isEmpty(responseId)) {
                            CallBackFunction function = responseCallbacks.get(responseId);
                            String responseData = m.getResponseData();
                            function.onCallBack(responseData);
                            responseCallbacks.remove(responseId);
                        } else {
                            CallBackFunction responseFunction = null;
                            // if had callbackId
                            final String callbackId = m.getCallbackId();
                            if (!TextUtils.isEmpty(callbackId)) {
                                responseFunction = new CallBackFunction() {
                                    @Override
                                    public void onCallBack(String data) {
                                        Message responseMsg = new Message();
                                        responseMsg.setResponseId(callbackId);
                                        responseMsg.setResponseData(data);
                                        queueMessage(responseMsg);
                                    }
                                };
                            } else {
                                responseFunction = new CallBackFunction() {
                                    @Override
                                    public void onCallBack(String data) {
                                        // do nothing
                                    }
                                };
                            }
                            BridgeHandler handler;
                            if (!TextUtils.isEmpty(m.getHandlerName())) {
                                handler = messageHandlers.get(m.getHandlerName());
                            } else {
                                handler = defaultHandler;
                            }
                            if (handler != null) {
                                handler.handler(m.getData(), responseFunction);
                            }
                        }
                    }
                }
            });
        }
    }

    public void loadUrl(String jsUrl, Map<String, String> additionalHttpHeaders, CallBackFunction returnCallback) {
        if (jsUrl.startsWith("file:") || additionalHttpHeaders == null) {
            this.loadUrl(jsUrl);
        } else {
            this.loadUrl(jsUrl, additionalHttpHeaders);
        }
        if (returnCallback == null) {
            return;
        }
        responseCallbacks.put(BridgeUtil.parseFunctionName(jsUrl), returnCallback);
    }

    public void loadUrl(String jsUrl, CallBackFunction returnCallback) {
        loadUrl(jsUrl, null, returnCallback);
    }

    /**
     * register handler,so that javascript can call it
     *
     * @param handlerName
     * @param handler
     */
    public void registerHandler(String handlerName, BridgeHandler handler) {
        if (handler != null) {
            messageHandlers.put(handlerName, handler);
        }
    }

    /**
     * call javascript registered handler
     *
     * @param handlerName
     * @param data
     * @param callBack
     */
    public void callHandler(String handlerName, String data, CallBackFunction callBack) {
        doSend(handlerName, data, callBack);
    }
}
