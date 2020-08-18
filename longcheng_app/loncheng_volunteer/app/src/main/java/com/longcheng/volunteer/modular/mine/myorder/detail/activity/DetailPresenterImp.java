package com.longcheng.volunteer.modular.mine.myorder.detail.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.volunteer.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.volunteer.modular.mine.myorder.detail.bean.DetailDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class DetailPresenterImp<T> extends DetailContract.Presenter<DetailContract.View> {

    private Context mContext;
    private DetailContract.View mView;
    private DetailContract.Model mModel;

    public DetailPresenterImp(Context mContext, DetailContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void getDetail(String user_id, String order_id) {
        mView.showDialog();
        Observable<DetailDataBean> observable;
        observable = Api.getInstance().service.getOrderDetail(user_id, order_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<DetailDataBean>() {
                    @Override
                    public void accept(DetailDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean);
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
     * 申请行动完成做任务
     *
     * @param user_id
     */
    public void getNeedHelpNumberTask(String user_id) {
        mView.showDialog();
        Observable<ActionDataBean> observable = Api.getInstance().service.getNeedHelpNumberTask(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ActionDataBean>() {
                    @Override
                    public void accept(ActionDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.getNeedHelpNumberTaskSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
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
     * 生活方式获取申请跳转-送祝福
     *
     * @param user_id
     * @param good_id
     */
    public void getLifeNeedHelpNumberTaskSuccess(String user_id, String good_id) {
        mView.showDialog();
        Observable<LifeNeedDataBean> observable = Api.getInstance().service.getLifeNeedHelpNumberTask(user_id, good_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LifeNeedDataBean>() {
                    @Override
                    public void accept(LifeNeedDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.getLifeStyleNeedHelpNumberTaskSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
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
     * 取消行动
     *
     * @param user_id
     * @param order_id
     */
    public void cancelAction(String user_id, String order_id) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.cancelAction(user_id, order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        Log.e("Observable", "" + throwable.toString());
                        mView.ListError();
                    }
                });

    }

    /**
     * 确认收货
     *
     * @param user_id
     * @param order_id
     */
    public void confirmReceipt(String user_id, String order_id) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.confirmReceipt(user_id, order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
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
