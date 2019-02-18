package com.longcheng.lifecareplan.modular.home.commune.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class CommunePresenterImp<T> extends CommuneContract.Presenter<CommuneContract.View> {

    private Context mContext;
    private CommuneContract.View mView;

    public CommunePresenterImp(Context mContext, CommuneContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * 获取加入公社列表
     */
    public void getJoinList(String user_id,
                            int member,
                            int like,
                            int engry) {
        mView.showDialog();
        Log.e("Observable", "user_id=" + user_id);
        Observable<CommuneListDataBean> observable = Api.getInstance().service.getCommuneList(user_id,
                member, like, engry, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CommuneListDataBean>() {
                    @Override
                    public void accept(CommuneListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.JoinListSuccess(responseBean);
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
     * 获取大队列表
     */
    public void getTeamList(String user_id, int group_id) {
        mView.showDialog();
        Observable<CommuneListDataBean> observable = Api.getInstance().service.getTeamList(user_id,
                group_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CommuneListDataBean>() {
                    @Override
                    public void accept(CommuneListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.GetTeamListSuccess(responseBean);
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
     * 获取成员列表
     */
    public void getMemberList(String user_id, int team_id, String search, int page, int pageSize) {
        mView.showDialog();
        Observable<CommuneListDataBean> observable = Api.getInstance().service.getMemberList(user_id,
                team_id, search, page, pageSize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CommuneListDataBean>() {
                    @Override
                    public void accept(CommuneListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.GetMemberListSuccess(responseBean, page);
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
     * 获取我的公社信息
     */
    public void getMineCommuneInfo(String user_id) {
        mView.showDialog();
        Log.e("Observable", "user_id=" + user_id);
        Observable<CommuneListDataBean> observable = Api.getInstance().service.getCommuneInfo(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CommuneListDataBean>() {
                    @Override
                    public void accept(CommuneListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.MineCommuneInfoSuccess(responseBean);
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
     * 加入公社
     */
    public void JoinCommune(String user_id, int group_id, int team_id) {
        mView.showDialog();
        Log.e("Observable", "user_id=" + user_id);
        Observable<EditListDataBean> observable = Api.getInstance().service.JoinCommune(user_id,
                group_id, team_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.JoinCommuneSuccess(responseBean);
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
     * 获取我的公社-今日互祝
     */
    public void getMineCommuneTodayList(String user_id, int group_id, int page, int page_size) {
        mView.showDialog();
        Observable<CommuneListDataBean> observable = Api.getInstance().service.getMineCommuneTodayList(user_id,
                group_id, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CommuneListDataBean>() {
                    @Override
                    public void accept(CommuneListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.MineRankListSuccess(responseBean, page);
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
     * 获取我的公社-累计互祝
     */
    public void getMineCommuneLeiJiList(String user_id, int group_id, int page, int page_size) {
        mView.showDialog();
        Observable<CommuneListDataBean> observable = Api.getInstance().service.getMineCommuneLeiJiList(user_id,
                group_id, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CommuneListDataBean>() {
                    @Override
                    public void accept(CommuneListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.MineRankListSuccess(responseBean, page);
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
     * 更换公社头像
     *
     * @param user_id
     * @param file
     */
    public void uploadCommuneAblum(String user_id, int group_id, String file) {
        mView.showDialog();
        Log.e("Observable", "file:  " + file);
        Observable<EditThumbDataBean> observable = Api.getInstance().service.uploadCommuneAblum(user_id, group_id, file, ExampleApplication.token);
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
                        mView.ListError();
                        Log.e("Observable", throwable.toString());
                    }
                });

    }

    /**
     * 发布公告
     *
     * @param user_id
     * @param content
     */
    public void publish(String user_id, int group_id, String content) {
        mView.showDialog();
        Log.e("Observable", "content:  " + content);
        Observable<EditListDataBean> observable = Api.getInstance().service.publish(user_id, group_id, content, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.JoinCommuneSuccess(responseBean);
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
     * 点赞
     *
     * @param user_id
     */
    public void ClickLike(String user_id, int group_id) {
        mView.showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.ClickLike(user_id, group_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ClickLikeSuccess(responseBean);
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
     * 获取大队名称和大队信息
     *
     * @param user_id
     */
    public void getCreateTeamInfo(String user_id, int team_id) {
        mView.showDialog();
        Observable<CommuneListDataBean> observable = Api.getInstance().service.getCreateTeamInfo(user_id, team_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CommuneListDataBean>() {
                    @Override
                    public void accept(CommuneListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.GetCreateTeamInfoSuccess(responseBean);
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
     * 搜索大队长
     *
     * @param user_id
     */
    public void CreateTeamSearch(String user_id, String phone) {
        mView.showDialog();
        Observable<CommuneDataBean> observable = Api.getInstance().service.CreateTeamSearch(user_id, phone, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CommuneDataBean>() {
                    @Override
                    public void accept(CommuneDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.CreateTeamSearchSuccess(responseBean);
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
     * 创建/编辑大队-保存
     *
     * @param user_id
     */
    public void CreateTeamSaveInfo(String user_id, int team_id, String solar_terms_name, String solar_terms_en, String team_user_id) {
        mView.showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.CreateTeamSaveInfo(user_id, team_id,
                solar_terms_name, solar_terms_en, team_user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.CreateTeamSuccess(responseBean);
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
}
