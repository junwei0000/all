package com.longcheng.lifecareplan.modular.helpwith.myfamily.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.autolayout.utils.L;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.RelationListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MyPresenterImp<T> extends MyContract.Presenter<MyContract.View> {

    private Context mContext;
    private MyContract.View mView;
    private MyContract.Model mModel;

    public MyPresenterImp(Context mContext, MyContract.View mView) {
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
        Observable<MyFamilyListDataBean> observable = Api.getInstance().service.getFamilyList(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MyFamilyListDataBean>() {
                    @Override
                    public void accept(MyFamilyListDataBean responseBean) throws Exception {
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
     * 我的家人-保存家人信息
     *
     * @param user_id
     */
    public void addOrEditFamily(String user_id, String family_user_id, String user_name, String birthday
            , String pid, String cid, String aid, String phone_number, String call_user, String relationship) {
        mView.showDialog();
        Log.e("Observable", user_id + "  " +
                family_user_id + "  " + user_name + "  " + birthday + "  " + pid + "  " + cid + "  " + aid + "  " + phone_number + "  " + call_user + "  " + ExampleApplication.token);
        Observable<EditListDataBean> observable = Api.getInstance().service.addOrEditFamily(user_id,
                family_user_id, user_name, birthday, pid, cid, aid, phone_number, call_user, relationship, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.addOrEditSuccess(responseBean);
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
     * 解除家人
     *
     * @param user_id
     */
    public void delFamily(String user_id, String family_user_id) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.delFamily(user_id,
                family_user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.DelSuccess(responseBean);
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
     * 我的家人-关系列表
     *
     * @param user_id
     */
    public void getFamilyRelation(String user_id) {
        mView.showDialog();
        Observable<RelationListDataBean> observable = Api.getInstance().service.getFamilyRelation(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<RelationListDataBean>() {
                    @Override
                    public void accept(RelationListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.GetFamilyRelationSuccess(responseBean);
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
     * 我的家人-编辑时获取该用户信息
     *
     * @param user_id
     */
    public void getFamilyEditInfo(String user_id, String family_user_id) {
        mView.showDialog();
        Observable<MyFamilyDataBean> observable = Api.getInstance().service.getFamilyEditInfo(user_id, family_user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MyFamilyDataBean>() {
                    @Override
                    public void accept(MyFamilyDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.GetFamilyEditInfoSuccess(responseBean);
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
