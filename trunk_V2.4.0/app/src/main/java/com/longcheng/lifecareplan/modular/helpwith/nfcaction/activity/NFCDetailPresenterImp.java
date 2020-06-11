package com.longcheng.lifecareplan.modular.helpwith.nfcaction.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
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

public class NFCDetailPresenterImp<T> extends NFCDetailContract.Presenter<NFCDetailContract.View> {

    private Context mContext;
    private NFCDetailContract.View mView;
    private NFCDetailContract.Model mModel;

    public NFCDetailPresenterImp(Context mContext, NFCDetailContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void addRecord(String order_id,
                          double phone_user_longitude,
                          double phone_user_latitude,
                          String city,
                          String nfc_sn) {
        Log.e("Observable", "" + ExampleApplication.token);
        Observable<EditDataBean> observable = Api.getInstance().service.addRecord(UserUtils.getUserId(mContext),
                order_id, phone_user_longitude, phone_user_latitude, city, nfc_sn, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    public void getDetail(String order_id, String nfc_sn) {
        mView.showDialog();
        Log.e("Observable", "" + ExampleApplication.token);
        Observable<NFCDetailDataBean> observable = Api.getInstance().service.getNFCDetail(
                UserUtils.getUserId(mContext),
                order_id, nfc_sn, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<NFCDetailDataBean>() {
                    @Override
                    public void accept(NFCDetailDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.DetailSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    public void getDetailList(String order_id, int page, int page_size) {
        mView.showDialog();
        Log.e("Observable", "" + ExampleApplication.token);
        Observable<NFCDetailDataBean> observable = Api.getInstance().service.getNFCDetailList(UserUtils.getUserId(mContext),
                order_id, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<NFCDetailDataBean>() {
                    @Override
                    public void accept(NFCDetailDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.DetailListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    /**
     * 支付
     *
     * @param user_id
     * @param msg_id
     */
    public void payHelp(String user_id, String msg_id, String order_id,
                        int payment_channel, String mutual_help_money_id, String nfc_sn) {
        mView.showDialog();
        Log.e("Observable", "" + ExampleApplication.token);
        Observable<ResponseBean> observable = Api.getInstance().service.payNFCHelp(user_id,
                msg_id, order_id, "2", payment_channel, mutual_help_money_id, nfc_sn, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.PayHelpSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    public void getNFCRecord(String order_id, String nfc_sn) {
        mView.showDialog();
        Log.e("Observable", "" + ExampleApplication.token);
        Observable<NFCDetailListDataBean> observable = Api.getInstance().service.getNFCRecord(
                UserUtils.getUserId(mContext),
                order_id, nfc_sn, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<NFCDetailListDataBean>() {
                    @Override
                    public void accept(NFCDetailListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.DetailRecordSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    public void getNFCEnergyImport(String order_id) {
        mView.showDialog();
        Log.e("Observable", "" + ExampleApplication.token);
        Observable<NFCDetailDataBean> observable = Api.getInstance().service.getNFCEnergyImport(UserUtils.getUserId(mContext),
                order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<NFCDetailDataBean>() {
                    @Override
                    public void accept(NFCDetailDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.DetailSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }
}
