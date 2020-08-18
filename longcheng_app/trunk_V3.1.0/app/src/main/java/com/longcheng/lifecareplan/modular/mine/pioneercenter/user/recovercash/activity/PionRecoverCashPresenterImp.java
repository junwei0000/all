package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.emergencys.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

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

public class PionRecoverCashPresenterImp<T> extends PionRecoverCashContract.Presenter<PionRecoverCashContract.View> {

    private Context mContext;
    private PionRecoverCashContract.View mView;
    private PionRecoverCashContract.Model mModel;

    public PionRecoverCashPresenterImp(Context mContext, PionRecoverCashContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void isCertify(String use_id) {
        mView.showDialog();
        Observable<CertifyBean> observable = Api.getInstance().service.isCertify(use_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CertifyBean>() {
                    @Override
                    public void accept(CertifyBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.getCardInfoSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }

    /**
     * 获取钱包信息
     *
     * @param user_id
     */
    public void getwalletInfo(String user_id) {
        mView.showDialog();
        Observable<AcountInfoDataBean> observable = Api.getInstance().service.PiongetwalletInfo(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AcountInfoDataBean>() {
                    @Override
                    public void accept(AcountInfoDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.getwalletSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });
    }
    /**
     * 获取匹配信息
     *
     * @param user_id
     */
    public void getwalletOrderInfo(String user_id) {
        mView.showDialog();
        Observable<AcountInfoDataBean> observable = Api.getInstance().service.getwalletOrderInfo(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AcountInfoDataBean>() {
                    @Override
                    public void accept(AcountInfoDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.getwalletSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });
    }
    /**
     * 充值取消匹配
     *
     * @param user_zhufubao_order_id
     */
    public void cancelPiPei(String user_zhufubao_order_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.PioncancelPiPeiTiXian(UserUtils.getUserId(mContext), user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            mView.cancelPiPeiSuccess(responseBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });

    }

    /**
     * 获取节气宝信息
     *
     * @param user_id
     */
    public void getUserJQBInfo(String user_id) {
        mView.showDialog();
        Observable<AcountInfoDataBean> observable = Api.getInstance().service.PiongetUserJQBInfo(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AcountInfoDataBean>() {
                    @Override
                    public void accept(AcountInfoDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.getwalletSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });
    }

    /**
     * 转换节气宝
     *
     * @param jieqibao
     */
    public void ConvertJQB(String jieqibao) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.ConvertJQB(UserUtils.getUserId(mContext), jieqibao, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.cancelPiPeiSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });
    }

    /**
     * 用户福祺宝出售
     */
    public void UserFQBSell(String total_money, String cloud_service_charge, String money, int is_face_pay) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.UserFQBSell(UserUtils.getUserId(mContext),
                total_money, cloud_service_charge, money, is_face_pay, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.cancelPiPeiSuccess(responseBean);
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
