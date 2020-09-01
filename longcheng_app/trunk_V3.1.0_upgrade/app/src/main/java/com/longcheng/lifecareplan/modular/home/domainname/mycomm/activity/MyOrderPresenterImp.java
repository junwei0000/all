package com.longcheng.lifecareplan.modular.home.domainname.mycomm.activity;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.home.domainname.bean.HotListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MyOrderPresenterImp<T> extends MyOrderContract.Presenter<MyOrderContract.View> {

    private MyOrderContract.View mView;
    private MyOrderContract.Model mModel;

    public MyOrderPresenterImp(MyOrderContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * @param user_id
     * @param page
     * @param page_size
     */
    public void getOrderList(String user_id, int level, int page, int page_size) {
        mView.showDialog();
        Observable<HotListDataBean> observable = Api.getInstance().service.getMyCommuList(user_id, level,
                page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<HotListDataBean>() {
                    @Override
                    public void accept(HotListDataBean responseBean) throws Exception {
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

}
