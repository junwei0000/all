package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
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

public class PionDCPresenterImp<T> extends PionDCContract.Presenter<PionDCContract.View> {

    private Context mContext;
    private PionDCContract.View mView;

    public PionDCPresenterImp(Context mContext, PionDCContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * 用户代充列表
     *
     * @param page
     * @param page_size
     */
    public void PiongetCZList(int page, int page_size, int type) {
        mView.showDialog();
        Observable<PionDaiCDataBean> observable = Api.getInstance().service.PiongetCZList(
                UserUtils.getUserId(mContext), page, page_size, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PionDaiCDataBean>() {
                    @Override
                    public void accept(PionDaiCDataBean responseBean) throws Exception {
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
     * 用户确认代充打款与 取消
     * 1 确认， -1 取消
     *
     * @param order_id
     */
    public void updateOrderStatus(String order_id, int status, int user_payment_channel, String user_payment_photo) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.PionCZUpdateOrderStatus(
                UserUtils.getUserId(mContext), order_id, status, user_payment_channel, user_payment_photo, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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
     * 用户协商
     * 1 完成 2 撤销
     *
     * @param entrepreneurs_zhufubao_order_id
     */
    public void PionCZuserConsult(String entrepreneurs_zhufubao_order_id, int user_consult_status) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.PionCZuserConsult(
                UserUtils.getUserId(mContext), entrepreneurs_zhufubao_order_id, user_consult_status, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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


    /**
     * 祝福师代充
     *
     * @param page
     * @param page_size
     */
    public void getDaiCList(int page, int page_size, int type) {
        mView.showDialog();
        Observable<PionDaiCDataBean> observable = Api.getInstance().service.PiongetDaiCList(
                UserUtils.getUserId(mContext), page, page_size, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PionDaiCDataBean>() {
                    @Override
                    public void accept(PionDaiCDataBean responseBean) throws Exception {
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
     * 拒绝申请
     *
     * @param user_zhufubao_order_id
     */
    public void RefuseApply(String user_zhufubao_order_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.RefuseApply(
                UserUtils.getUserId(mContext), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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
     * 同意申请
     *
     * @param user_zhufubao_order_id
     */
    public void AgreeApply(String user_zhufubao_order_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.AgreeApply(
                UserUtils.getUserId(mContext), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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
     * 祝福师 取消
     *
     * @param user_zhufubao_order_id
     */
    public void PiondaiCRefuse(String user_zhufubao_order_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.PiondaiCRefuse(
                UserUtils.getUserId(mContext), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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


    public void PiondaiCAgree(String entrepreneurs_zhufubao_order_id, int bless_payment_channel, String bless_payment_photo) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.PiondaiCAgree(
                UserUtils.getUserId(mContext), entrepreneurs_zhufubao_order_id, bless_payment_channel, bless_payment_photo, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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
     * 祝福师协商
     *
     * @param user_zhufubao_order_id
     */
    public void PionDaiCConsult(String user_zhufubao_order_id, int bless_consult_status) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.PionCZBlessConsult(
                UserUtils.getUserId(mContext), user_zhufubao_order_id, bless_consult_status, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
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
