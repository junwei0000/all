package com.longcheng.lifecareplan.modular.mine.fragment;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 17:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MinePresenterImp<T> extends MineContract.Present<MineContract.View> {

    private MineContract.View view;
    private MineContract.Model model;

    public MinePresenterImp(MineContract.View view) {
        this.view = view;
        model =new MineModelImp();
    }

    @Override
    public void fetch() {

    }
}
