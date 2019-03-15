package com.longcheng.lifecareplan.modular.exchange.fragment;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:23
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ExChangePresenterImp<T> extends ExChangeContract.Present<ExChangeContract.View> {

    private ExChangeContract.View view;
    private ExChangeContract.Model model;

    public ExChangePresenterImp(ExChangeContract.View view) {
        this.view = view;
        model = new ExChangeModelImp();
    }

    @Override
    public void fetch() {

    }
}
