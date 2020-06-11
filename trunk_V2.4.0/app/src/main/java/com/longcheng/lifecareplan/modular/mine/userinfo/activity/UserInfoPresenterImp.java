package com.longcheng.lifecareplan.modular.mine.userinfo.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetUserSETDataBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class UserInfoPresenterImp<T> extends UserInfoContract.Presenter<UserInfoContract.View> {

    private Context mContext;
    private UserInfoContract.View mView;
    private UserInfoContract.Model mModel;

    public UserInfoPresenterImp(Context mContext, UserInfoContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }


    public void uploadAblum(String user_id, String file) {
        mView.showDialog();
        Log.e("Observable", "file:  " + file);
        Observable<EditThumbDataBean> observable = Api.getInstance().service.editAvatar(user_id, file, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditThumbDataBean>() {
                    @Override
                    public void accept(EditThumbDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editAvatarSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.editError();
                        Log.e("Observable", throwable.toString());
                    }
                });

    }

    /**
     * 修改姓名
     *
     * @param user_id
     * @param user_name
     */
    public void editName(String user_id, String user_name) {
        mView.showDialog();
        Log.e("editName", "user_name=" + user_name);
        Observable<EditDataBean> observable = Api.getInstance().service.editUserName(user_id, user_name, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.editError();
                    }
                });

    }

    /**
     * 意见反馈
     *
     * @param user_id
     * @param content
     */
    public void sendIdea(String user_id, String content) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.sendIdea(user_id, content, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.editError();
                    }
                });

    }

    /**
     * 修改政治面貌
     *
     * @param user_id
     * @param political_status
     */
    public void editPolitical(String user_id, String political_status) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.editPolitical(user_id, political_status, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.editError();
                    }
                });

    }

    /**
     * 修改用户服兵役
     *
     * @param user_id
     * @param is_military_service
     */
    public void editMilitaryService(String user_id, String is_military_service) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.editMilitaryService(user_id, is_military_service, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.editError();
                    }
                });

    }

    /**
     * 获取用户配置，政治面貌  是否团员列表
     *
     * @param user_id
     */
    public void getUserSET(String user_id) {
        mView.showDialog();
        Observable<GetUserSETDataBean> observable = Api.getInstance().service.getUserSET(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<GetUserSETDataBean>() {
                    @Override
                    public void accept(GetUserSETDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.getUserSetSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.editError();
                    }
                });

    }

    /**
     * 设置出生日期
     *
     * @param user_id
     */
    public void editBirthday(String user_id, String birthday) {
        mView.showDialog();
        Log.e("Observable", "" + birthday);
        Observable<EditDataBean> observable = Api.getInstance().service.editBirthday(user_id, birthday, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.editBirthdaySuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.editError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });

    }

    /**
     * 设置出生地
     *
     * @param user_id
     */
    public void editHomeplace(String user_id, String pid, String cid, String aid) {
        mView.showDialog();
        Log.e("Observable", "pid  " + pid + "  cid " + cid + " aid " + aid);
        Observable<EditDataBean> observable = Api.getInstance().service.editHomeplace(user_id, pid, cid, aid, ExampleApplication.token);
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
                        mView.editError();
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
                        mView.saveInfoSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.editError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });

    }
}
