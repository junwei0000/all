package com.longcheng.lifecareplan.widget.jswebview.browse;


/**
 * @param
 * @author MarkShuai
 * @name
 */
public class DefaultHandler implements BridgeHandler {

    String TAG = "DefaultHandler";

    @Override
    public void handler(String data, CallBackFunction function) {
        if (function != null) {
            function.onCallBack("DefaultHandler response data");
        }
    }

}
