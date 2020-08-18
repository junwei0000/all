package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.activity;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.pionopen.bean.PionOpenListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class PionOpenListPresenterImp<T> extends PionOpenListContract.Presenter<PionOpenListContract.View> {

    private PionOpenListContract.View mView;

    public PionOpenListPresenterImp(PionOpenListContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     *
     * @param user_id
     * @param type     1：开通 2：续费 3:升级
     * @param search_type  search_type  0未确认 1已确认 100：全部
     * @param page
     * @param page_size
     */
    public void getOrderList(String user_id, int type, int search_type, int page, int page_size) {
        mView.showDialog();
        Observable<PionOpenListDataBean> observable = Api.getInstance().service.PionOpenXuFeiList(user_id, type, search_type,
                page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PionOpenListDataBean>() {
                    @Override
                    public void accept(PionOpenListDataBean responseBean) throws Exception {
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
    public void customerConfirmPayment(String user_id, String entrepreneurs_order_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.PionOpenXuFeiConfirmGetMoney(user_id, entrepreneurs_order_id, ExampleApplication.token);
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
