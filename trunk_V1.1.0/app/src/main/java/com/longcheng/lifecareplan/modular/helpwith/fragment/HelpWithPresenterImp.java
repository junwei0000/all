package com.longcheng.lifecareplan.modular.helpwith.fragment;


/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:23
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HelpWithPresenterImp<T> extends HelpWithContract.Present<HelpWithContract.View> {

    private HelpWithContract.View mView;
    private HelpWithContract.Model model;

    public HelpWithPresenterImp(HelpWithContract.View view) {
        this.mView = view;
        model = new HelpWithModelImp();
    }

    @Override
    public void fetch() {

    }

}
