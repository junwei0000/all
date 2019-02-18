package com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataListBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetUserSETDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;
import retrofit2.http.Query;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ApplyHelpPresenterImp<T> extends ApplyHelpContract.Presenter<ApplyHelpContract.View> {

    private Context mContext;
    private ApplyHelpContract.View mView;
    private ApplyHelpContract.Model mModel;

    public ApplyHelpPresenterImp(Context mContext, ApplyHelpContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
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
     * 申请互助-行动列表
     *
     * @param user_id
     */
    public void getActionList(String user_id) {
        mView.showDialog();
        Observable<ActionDataListBean> observable = Api.getInstance().service.getActionList(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ActionDataListBean>() {
                    @Override
                    public void accept(ActionDataListBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ActionListSuccess(responseBean);
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
     * 申请互助-接福人列表
     *
     * @param user_id
     */
    public void getPeopleList(String user_id) {
        mView.showDialog();
        Observable<PeopleDataBean> observable = Api.getInstance().service.getPeopleList(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PeopleDataBean>() {
                    @Override
                    public void accept(PeopleDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.PeopleListSuccess(responseBean);
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
     * 申请互助-接福人搜索列表
     *
     * @param user_id
     */
    public void getPeopleSearchList(String user_id, String user_name) {
        mView.showDialog();
        Observable<PeopleSearchDataBean> observable = Api.getInstance().service.getPeopleSearchList(user_id, user_name, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PeopleSearchDataBean>() {
                    @Override
                    public void accept(PeopleSearchDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.PeopleSearchListSuccess(responseBean);
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
     * 申请互助
     *
     * @param user_id
     */
    public void applyAction(String user_id,
                            String action_id,
                            String receive_user_id,
                            String address_id,
                            String describe,
                            String action_safety_id,
                            String extend_info,
                            String qiming_user_id) {
        Log.e("applyAction", "qiming_user_id=" + qiming_user_id);
        mView.showDialog();
        Observable<ActionDataBean> observable = Api.getInstance().service.applyAction(user_id, action_id,
                receive_user_id, address_id, describe, action_safety_id, extend_info, qiming_user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ActionDataBean>() {
                    @Override
                    public void accept(ActionDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.applyActionSuccess(responseBean);
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

    /**
     * 申请互助-互助说明
     *
     * @param user_id
     */
    public void getExplainList(String user_id, String action_id) {
        mView.showDialog();
        Observable<ExplainDataBean> observable = Api.getInstance().service.getExplainList(user_id, action_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ExplainDataBean>() {
                    @Override
                    public void accept(ExplainDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ExplainListSuccess(responseBean);
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
     * 设置行动任务数据已读
     *
     * @param user_id
     */
    public void setTaskRead(String user_id, String mutual_help_apply_id) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.setTaskRead(user_id, mutual_help_apply_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
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
     * @param
     * @name 获取地址列表
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void setAddressList(String user_id, String receive_user_id) {
        mView.showDialog();
        Observable<AddressListDataBean> observable = Api.getInstance().service.getAddressList(user_id, receive_user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AddressListDataBean>() {
                    @Override
                    public void accept(AddressListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.GetAddressListSuccess(responseBean);
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
     * 申请互助-平安行动-资料保存
     *
     * @param user_id
     */
    public void actionSafety(String user_id, String user_name, int user_sex,
                             String pid,
                             String cid,
                             String aid,
                             String occupation, String birthday, String identity_number
            , String phone, String email) {
        mView.showDialog();
        Observable<ActionDataBean> observable = Api.getInstance().service.actionSafety(user_id, user_name, user_sex, pid, cid, aid, occupation
                , birthday, identity_number, phone, email, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ActionDataBean>() {
                    @Override
                    public void accept(ActionDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.actionSafetySuccess(responseBean);
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
}
