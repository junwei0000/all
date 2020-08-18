package com.longcheng.volunteer.widget.jswebview.browse;


/**
 * @param
 * @author MarkShuai
 * @name
 */
public interface WebViewJavascriptBridge {

    void send(String data);

    void send(String data, CallBackFunction responseCallback);

}
