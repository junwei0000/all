package com.longcheng.lifecareplan.modular.mine.tixianrecord.activity;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.tixianrecord.bean.TXRecordDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class TXRecorrdPresenterImp<T> extends TXRecorrdContract.Presenter<TXRecorrdContract.View> {

    private TXRecorrdContract.View mView;
    private TXRecorrdContract.Model mModel;

    public TXRecorrdPresenterImp(TXRecorrdContract.View mView) {
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
    public void getOrderList(String user_id, int select_type, int page, int page_size) {
        mView.showDialog();
        Observable<TXRecordDataBean> observable = Api.getInstance().service.getTXRecordList(user_id, select_type,
                page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<TXRecordDataBean>() {
                    @Override
                    public void accept(TXRecordDataBean responseBean) throws Exception {
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
     * 同意 接收代付订单
     *
     * @param user_zhufubao_order_id
     */
    public void agreeePairsU(String user_id, String user_zhufubao_order_id) {
        mView.showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.teacherReceiveCashOrder(
                user_id, user_zhufubao_order_id, 1, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });
    }

}
