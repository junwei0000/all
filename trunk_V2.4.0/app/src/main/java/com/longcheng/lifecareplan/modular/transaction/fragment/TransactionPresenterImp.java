package com.longcheng.lifecareplan.modular.transaction.fragment;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 17:25
 * 邮箱：MarkShuai@163.com
 * 意图：交易的P层
 */

public class TransactionPresenterImp<T> extends TransactionContract.Present<TransactionContract.View> {

    private TransactionContract.View view;
    private TransactionContract.Model model;

    public TransactionPresenterImp(TransactionContract.View view) {
        this.view = view;
        model = new TransactionModelImp();
    }

    @Override
    public void fetch() {

    }
}
