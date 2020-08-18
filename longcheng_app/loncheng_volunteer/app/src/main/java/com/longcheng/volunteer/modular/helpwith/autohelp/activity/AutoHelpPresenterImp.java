package com.longcheng.volunteer.modular.helpwith.autohelp.activity;

import android.content.Context;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.helpwith.autohelp.bean.AutoHelpDataBean;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class AutoHelpPresenterImp<T> extends AutoHelpContract.Presenter<AutoHelpContract.View> {

    private Context mContext;
    private AutoHelpContract.View mView;
    private AutoHelpContract.Model mModel;

    public AutoHelpPresenterImp(Context mContext, AutoHelpContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * @param user_id
     */
    public void getList(String user_id) {
        mView.showDialog();
        Observable<AutoHelpDataBean> observable = Api.getInstance().service.getAutoInfo(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AutoHelpDataBean>() {
                    @Override
                    public void accept(AutoHelpDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean);
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
     * 修改智能互祝状态
     *
     * @param user_id
     * @param is_automation_help
     * @param automation_help_type
     * @param mutual_help_money_id
     * @param everyday_help_number
     */
    public void saveUpdateAutoHelp(String user_id, int is_automation_help, int automation_help_type, int mutual_help_money_id, int everyday_help_number) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.saveUpdateAutoHelp(user_id,
                is_automation_help, automation_help_type, mutual_help_money_id, everyday_help_number, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.saveUpdateSuccess(responseBean);
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
