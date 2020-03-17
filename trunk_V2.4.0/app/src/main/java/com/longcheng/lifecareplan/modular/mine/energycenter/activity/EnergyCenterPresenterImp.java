package com.longcheng.lifecareplan.modular.mine.energycenter.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class EnergyCenterPresenterImp<T> extends EnergyCenterContract.Presenter<EnergyCenterContract.View> {

    private Context mContext;
    private EnergyCenterContract.View mView;

    public EnergyCenterPresenterImp(Context mContext, EnergyCenterContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void getDaiFuList(int page, int page_size) {
        mView.showDialog();
        Observable<DaiFuDataBean> observable = Api.getInstance().service.getDaiFuList(UserUtils.getUserId(mContext), page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<DaiFuDataBean>() {
                    @Override
                    public void accept(DaiFuDataBean responseBean) throws Exception {
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
     * 驳回
     *
     * @param user_zhufubao_order_id
     */
    public void refuse(String user_zhufubao_order_id) {
        mView.showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.blessPaymentRefuse(
                UserUtils.getUserId(mContext), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.RefuseSuccess(responseBean);
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
     * 确认付款
     *
     * @param user_zhufubao_order_id
     */
    public void blessPaymentConfirm(String user_zhufubao_order_id, int bless_payment_channel, String bless_payment_photo) {
        mView.showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.blessPaymentConfirm(
                UserUtils.getUserId(mContext), user_zhufubao_order_id, bless_payment_channel, bless_payment_photo, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.RefuseSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });
    }

    public void uploadImg(String file) {
        mView.showDialog();
        Log.e("Observable", "file:  " + file);
        Observable<EditDataBean> observable = Api.getInstance().service.uploadCertificateImg(
                UserUtils.getUserId(mContext), file, 3, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editAvatarSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", throwable.toString());
                    }
                });
    }


    public void getTiXianList(int page, int page_size) {
        mView.showDialog();
        Observable<DaiFuDataBean> observable = Api.getInstance().service.getTiXianList(UserUtils.getUserId(mContext), page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<DaiFuDataBean>() {
                    @Override
                    public void accept(DaiFuDataBean responseBean) throws Exception {
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
     * 提现记录-撤回
     *
     * @param user_zhufubao_order_id
     */
    public void tixianrefuse(String user_zhufubao_order_id) {
        mView.showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.userPaymentRefuse(
                UserUtils.getUserId(mContext), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.RefuseSuccess(responseBean);
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
     * 确认收款
     *
     * @param user_zhufubao_order_id
     */
    public void userConfirmCash(String user_zhufubao_order_id, int bless_payment_channel, String bless_payment_photo) {
        mView.showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.userConfirmCash(
                UserUtils.getUserId(mContext), user_zhufubao_order_id, bless_payment_channel, bless_payment_photo, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.RefuseSuccess(responseBean);
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
