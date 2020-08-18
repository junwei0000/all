package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpCreatDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpGroupRoomDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
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

public class CHelpPresenterImp<T> extends CHelpContract.Presenter<CHelpContract.View> {

    private Context mContext;
    private CHelpContract.View mView;

    public CHelpPresenterImp(Context mContext, CHelpContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void getListIndexConfig() {
        mView.showDialog();
        Observable<CHelpDataBean> observable = Api.getInstance().service.getListIndexConfig(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CHelpDataBean>() {
                    @Override
                    public void accept(CHelpDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.DataSuccess(responseBean);
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

    public void getHelpList(String keyword, String knp_team_number_id, String jieqi_en, int page, int pageSize) {
        mView.showDialog();
        Observable<CHelpListDataBean> observable = Api.getInstance().service.getHelpList(
                UserUtils.getUserId(mContext), keyword, knp_team_number_id, jieqi_en, page, pageSize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CHelpListDataBean>() {
                    @Override
                    public void accept(CHelpListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
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
     * 创建页面信息
     */
    public void createRoomPage() {
        mView.showDialog();
        Observable<CHelpCreatDataBean> observable = Api.getInstance().service.createRoomPage(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CHelpCreatDataBean>() {
                    @Override
                    public void accept(CHelpCreatDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.CreatePageDataSuccess(responseBean);
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

    public void groupRoomInfo(String knp_group_room_id) {
        mView.showDialog();
        Observable<CHelpGroupRoomDataBean> observable = Api.getInstance().service.groupRoomInfo(UserUtils.getUserId(mContext),
                knp_group_room_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CHelpGroupRoomDataBean>() {
                    @Override
                    public void accept(CHelpGroupRoomDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.GroupRoomDataSuccess(responseBean);
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

    public void knpAiYouMsgList() {
        mView.showDialog();
        Observable<CHelpDataBean> observable = Api.getInstance().service.knpAiYouMsgList(UserUtils.getUserId(mContext),
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CHelpDataBean>() {
                    @Override
                    public void accept(CHelpDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.DataSuccess(responseBean);
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

    public void getMyHelpList(int type, int page, int pageSize) {
        mView.showDialog();
        Observable<CHelpListDataBean> observable = Api.getInstance().service.getMyHelpList(
                UserUtils.getUserId(mContext), type, page, pageSize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CHelpListDataBean>() {
                    @Override
                    public void accept(CHelpListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
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

    public void CreateRoom(int type, int is_super_ability, String knp_team_number_id, int table_number, int ability) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.CreateRoom(
                UserUtils.getUserId(mContext), type, is_super_ability, knp_team_number_id, table_number, ability, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.CreateRoomSuccess(responseBean);
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
     * 结缘 结伴创建桌子
     *
     * @param knp_group_item_id
     */
    public void wuwaiOpenTable(String knp_group_item_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.wuwaiOpenTable(
                UserUtils.getUserId(mContext), knp_group_item_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.CreateTableSuccess(responseBean);
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

    public void wuwaiBindKnpAiYouMsg(String knp_group_item_id, String knp_msg_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.wuwaiBindKnpAiYouMsg(
                UserUtils.getUserId(mContext), knp_group_item_id, knp_msg_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
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

    public void getRoomInfo(String knp_group_room_id, String knp_group_item_id) {
        mView.showDialog();
        Observable<CHelpDetailDataBean> observable = Api.getInstance().service.getRoomInfo(UserUtils.getUserId(mContext), knp_group_room_id
                , knp_group_item_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CHelpDetailDataBean>() {
                    @Override
                    public void accept(CHelpDetailDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.DetailDataSuccess(responseBean);
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

    public void JoinHelpRoom(String knp_group_room_id, int ability, int number, String knp_group_table_ids) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.JoinHelpRoom(
                UserUtils.getUserId(mContext), knp_group_room_id, ability, number, knp_group_table_ids, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.editSuccess(responseBean);
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
     * 天下无癌解散结伴互祝组
     *
     * @param knp_group_room_id
     */
    public void wuaiDissolution(String knp_group_room_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.wuaiDissolution(UserUtils.getUserId(mContext), knp_group_room_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.editSuccess(responseBean);
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
     * 天下无癌解散结伴互祝组
     *
     * @param knp_group_room_id
     */
    public void wuaiSignOut(String knp_group_room_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.wuaiSignOut(UserUtils.getUserId(mContext), knp_group_room_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.editSuccess(responseBean);
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
