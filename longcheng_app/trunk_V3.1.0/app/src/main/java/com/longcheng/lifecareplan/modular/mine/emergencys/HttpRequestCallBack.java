package com.longcheng.lifecareplan.modular.mine.emergencys;

/**
 * Created by zhangwenjun on 2018/6/29.
 */

public interface HttpRequestCallBack {
    void onSuccess(String responseBody);

    void onFailure(int statusCode, byte[] responseBody);
}
