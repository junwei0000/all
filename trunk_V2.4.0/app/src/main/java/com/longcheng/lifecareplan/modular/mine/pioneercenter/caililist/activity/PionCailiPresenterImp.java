package com.longcheng.lifecareplan.modular.mine.pioneercenter.caililist.activity;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.caililist.bean.PionCailiListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class PionCailiPresenterImp<T> extends PionCailiContract.Presenter<PionCailiContract.View> {

    private PionCailiContract.View mView;

    public PionCailiPresenterImp(PionCailiContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * 订单类型 默认 0，全部  1，进行中 2，待发货 3，已完成
     *
     * @param user_id
     * @param page
     * @param page_size
     */
    public void getOrderList(String user_id, int type, int page, int page_size) {
        mView.showDialog();
        Observable<PionCailiListDataBean> observable = Api.getInstance().service.PionRebateList(user_id, type,
                page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PionCailiListDataBean>() {
                    @Override
                    public void accept(PionCailiListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });

    }

    /**
     * 确认打款
     */
    public void customerConfirmPayment(String user_id, String entrepreneurs_apply_rebate_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.PionRebateConfirmPayApplyMoney(user_id, entrepreneurs_apply_rebate_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.editSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });

    }

}
