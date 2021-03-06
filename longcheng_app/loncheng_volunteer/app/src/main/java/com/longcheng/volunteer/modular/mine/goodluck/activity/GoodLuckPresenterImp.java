package com.longcheng.volunteer.modular.mine.goodluck.activity;

import android.content.Context;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.volunteer.modular.mine.goodluck.bean.GoodLuckListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class GoodLuckPresenterImp<T> extends GoodLuckContract.Presenter<GoodLuckContract.View> {

    private Context mContext;
    private GoodLuckContract.View mView;
    private GoodLuckContract.Model mModel;

    public GoodLuckPresenterImp(Context mContext, GoodLuckContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * @param
     * @name 获取ViewPager的数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void getGoodLuckList(String user_id,
                                int page,
                                int pageSize) {
        Observable<GoodLuckListDataBean> observable = Api.getInstance().service.getGoodLuckList(user_id,
                page, pageSize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<GoodLuckListDataBean>() {
                    @Override
                    public void accept(GoodLuckListDataBean responseBean) throws Exception {
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.ListError();
                    }
                });

    }

    /**
     * 开红包
     *
     * @param user_id
     * @param one_order_id
     */
    public void openRedEnvelope(String user_id, String one_order_id) {
        Observable<OpenRedDataBean> observable = Api.getInstance().service.openRedEnvelope(user_id,
                one_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<OpenRedDataBean>() {
                    @Override
                    public void accept(OpenRedDataBean responseBean) throws Exception {
                        mView.OpenRedEnvelopeSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onOpenRedEnvelopeError("");
                    }
                });
    }

    /**
     * 一键开红包
     *
     * @param user_id
     */
    public void openRedEnvelopeOneKey(String user_id) {
        mView.showDialog();
        Observable<OpenRedDataBean> observable = Api.getInstance().service.openRedEnvelopeOnekey(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<OpenRedDataBean>() {
                    @Override
                    public void accept(OpenRedDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.OpenRedEnvelopeOneKeySuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.onOpenRedEnvelopeError("");
                    }
                });
    }
}
