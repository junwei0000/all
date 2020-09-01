package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.activity;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.bean.PionRewardListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class FinanManagePresenterImp<T> extends FinanManageContract.Presenter<FinanManageContract.View> {

    private FinanManageContract.View mView;

    public FinanManagePresenterImp(FinanManageContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }
}
