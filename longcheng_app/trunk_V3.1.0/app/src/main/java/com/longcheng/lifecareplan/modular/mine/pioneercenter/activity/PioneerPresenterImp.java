package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerCounterDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.UserBankDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
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

public class PioneerPresenterImp<T> extends PioneerContract.Presenter<PioneerContract.View> {

    private Context mContext;
    private PioneerContract.View mView;

    public PioneerPresenterImp(Context mContext, PioneerContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void getMenuInfo() {
        mView.showDialog();
        Observable<PioneerDataBean> observable = Api.getInstance().service.PionGetMenuInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerDataBean>() {
                    @Override
                    public void accept(PioneerDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.MenuInfoSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }

    public void getCounterpoiseInfo() {
        mView.showDialog();
        Observable<PioneerCounterDataBean> observable = Api.getInstance().service.getCounterpoiseInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerCounterDataBean>() {
                    @Override
                    public void accept(PioneerCounterDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.CounterInfoSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    /**
     * 账户平衡 购买祝福宝
     */
    public void counterpoiseBuyZFb(String money, int payType, String payment_img) {
        mView.showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.counterpoiseBuyZFb(UserUtils.getUserId(mContext),
                money, payType, "2", payment_img, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            mView.paySuccess(responseBean);
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    /**
     * 账户平衡 出售福祺宝
     */
    public void counterpoiseSellFQB(String money
            , String bank_user_name
            , String bank_name
            , String bank_branch
            , String bank_account) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.counterpoiseSellFQB(UserUtils.getUserId(mContext),
                money, bank_user_name, bank_name, bank_branch, bank_account, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            mView.SellFQBSuccess(responseBean);
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    public void applyMoneyInfo() {
        mView.showDialog();
        Observable<PioneerDataBean> observable = Api.getInstance().service.applyMoneyInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerDataBean>() {
                    @Override
                    public void accept(PioneerDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.MenuInfoSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }

    public void applyMoneyList() {
        mView.showDialog();
        Observable<PioneerDataListBean> observable = Api.getInstance().service.applyMoneyList(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerDataListBean>() {
                    @Override
                    public void accept(PioneerDataListBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.applyMoneyListSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }

    /**
     * 祝福宝奖励页面信息
     */
    public void getUserBankInfo() {
        mView.showDialog();
        Observable<UserBankDataBean> observable = Api.getInstance().service.getUserBankInfo(
                UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<UserBankDataBean>() {
                    @Override
                    public void accept(UserBankDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.backBankInfoSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    /**
     *
     */
    public void saveApplyEntrepreneursMoney(String knp_team_bind_card_id, String money) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.saveApplyEntrepreneursMoney(
                UserUtils.getUserId(mContext), knp_team_bind_card_id, money, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.SellFQBSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    public void getSellBankInfo() {
        mView.showDialog();
        Observable<PioneerCounterDataBean> observable = Api.getInstance().service.getSellBankInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerCounterDataBean>() {
                    @Override
                    public void accept(PioneerCounterDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.CounterInfoSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }


    public void getCailiInfo() {
        mView.showDialog();
        Observable<PioneerCounterDataBean> observable = Api.getInstance().service.getCailiInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerCounterDataBean>() {
                    @Override
                    public void accept(PioneerCounterDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.CounterInfoSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    /**
     * 财礼提现
     */
    public void doRebateWithdraw(String knp_team_bind_card_id, String money) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.doRebateWithdraw(
                UserUtils.getUserId(mContext), knp_team_bind_card_id, money, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.SellFQBSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    public void getCailiRecordList(int page, int page_size) {
        mView.showDialog();
        Observable<PionOpenSetRecordDataBean> observable = Api.getInstance().service.getCailiRecordList(UserUtils.getUserId(mContext), page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PionOpenSetRecordDataBean>() {
                    @Override
                    public void accept(PionOpenSetRecordDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }

    public void rewardRechargeMoneyRecordList(int page, int page_size) {
        mView.showDialog();
        Observable<PioneerDataBean> observable = Api.getInstance().service.rewardRechargeMoneyRecordList(UserUtils.getUserId(mContext), page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerDataBean>() {
                    @Override
                    public void accept(PioneerDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.CZListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }

    public void receviceOrderStatusSave(int receive_order_status) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.receviceOrderStatusSave(
                UserUtils.getUserId(mContext), receive_order_status, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.SellFQBSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }


    public void getFQBRankList(int page, int page_size) {
        mView.showDialog();
        Observable<PioneerDataBean> observable = Api.getInstance().service.getFQBRankList(UserUtils.getUserId(mContext), page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerDataBean>() {
                    @Override
                    public void accept(PioneerDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.CZListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }

    public void getZYBRankList(int type,int page, int page_size) {
        mView.showDialog();
        Observable<PioneerDataBean> observable = Api.getInstance().service.getZYBRankList(UserUtils.getUserId(mContext),type, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PioneerDataBean>() {
                    @Override
                    public void accept(PioneerDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.CZListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }
}
