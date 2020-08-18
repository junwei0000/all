package com.longcheng.volunteer.base;

/**
 * Created by 10755 on 2017/11/18.
 */

public interface BaseView<T> {

    //显示进度框
    void showDialog();

    //隐藏进度框
    void dismissDialog();
}
