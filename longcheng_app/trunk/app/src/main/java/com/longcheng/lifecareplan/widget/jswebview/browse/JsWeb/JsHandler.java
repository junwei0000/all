package com.longcheng.lifecareplan.widget.jswebview.browse.JsWeb;


import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;

/**
 * @name
 * @param
 * @author MarkShuai
 */
public interface JsHandler {

    public void OnHandler(String handlerName, String responseData, CallBackFunction function);

}
