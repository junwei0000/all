package com.longcheng.lifecareplan.modular.mine.fragment;

import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 17:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MinePresenterImp<T> extends MineContract.Present<MineContract.View> {

    private MineContract.View mView;
    private MineContract.Model model;

    public MinePresenterImp(MineContract.View view) {
        this.mView = view;
        model = new MineModelImp();
    }

    @Override
    public void fetch() {

    }

    /**
     * 用户首页信息
     *
     * @param user_id
     */
    public void getUserHomeInfo(String user_id) {
        Observable<GetHomeInfoDataBean> observable = Api.getInstance().service.getUserHomeInfo(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<GetHomeInfoDataBean>() {
                    @Override
                    public void accept(GetHomeInfoDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.getHomeInfoSuccess(responseBean);
                        UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus());
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.error();
                    }
                });

    }

    /**
     * 检查用户信息完善
     *
     * @param user_id
     */
    public void checkUserInfo(String user_id) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.checkUserInfo(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.checkUserInfoSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.error();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });

    }

    /**
     * 检查用户信息完善
     *
     * @param user_id
     */
    public void doStarLevelRemind(String user_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.doStarLevelRemind(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.doStarLevelRemindSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.error();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });

    }
}
