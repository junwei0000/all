package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
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

public class PionZFBPresenterImp<T> extends PionZFBContract.Presenter<PionZFBContract.View> {

    private Context mContext;
    private PionZFBContract.View mView;
    private PionZFBContract.Model mModel;

    public PionZFBPresenterImp(Context mContext, PionZFBContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * 获取钱包信息
     *
     * @param user_id
     */
    public void getwalletInfo(String user_id) {
        mView.showDialog();
        Observable<PionZFBDataBean> observable = Api.getInstance().service.PiongetRechargeInfoNew(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionZFBDataBean>() {
                    @Override
                    public void accept(PionZFBDataBean responseBean) throws Exception {
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
        Observable<ResponseBean> observable = Api.getInstance().service.PioncancelPiPeiDaiChong(UserUtils.getUserId(mContext), user_zhufubao_order_id, ExampleApplication.token);
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
     * 充值保存
     */
    public void entrepreneursuserRecharge(String recharge_money, int pay_method,String entrepreneurs_id) {
        mView.showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.entrepreneursuserRecharge(
                UserUtils.getUserId(mContext), recharge_money, pay_method, "2",entrepreneurs_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            mView.PaySuccess(responseBean);
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
     * 激活超能。
     */
    public void activateSuperAbility(String uzhufubao_number) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.activateSuperAbility(UserUtils.getUserId(mContext), uzhufubao_number, ExampleApplication.token);
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

    public void getRechargeEntrepreneurs(String phone) {
        mView.showDialog();
        Observable<PionZFBSelectDataBean> observable = Api.getInstance().service.getRechargeEntrepreneurs(UserUtils.getUserId(mContext), phone, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionZFBSelectDataBean>() {
                    @Override
                    public void accept(PionZFBSelectDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            mView.SelectSuccess(responseBean);
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
}
