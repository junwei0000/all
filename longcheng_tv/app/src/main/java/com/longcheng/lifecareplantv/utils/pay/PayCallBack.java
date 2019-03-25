package com.longcheng.lifecareplantv.utils.pay;

/**
 * 作者：zhangjinqi
 * 时间 2017/10/18 11:28
 * 邮箱：mengchong.55@163.com
 * 类的意图：支付回调接口
 */

public interface PayCallBack {
    void onSuccess();

    void onFailure(String error);
}
