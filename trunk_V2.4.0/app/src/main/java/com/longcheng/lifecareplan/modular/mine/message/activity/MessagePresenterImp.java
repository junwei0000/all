package com.longcheng.lifecareplan.modular.mine.message.activity;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.message.bean.MessageDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MessagePresenterImp<T> extends MessageContract.Presenter<MessageContract.View> {

    private Context mContext;
    private MessageContract.View mView;
    private MessageContract.Model mModel;

    public MessagePresenterImp(Context mContext, MessageContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * @param
     * @name 获取ViewPager的数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void getMessageList(String user_id,
                               int page,
                               int pageSize) {
        mView.showDialog();
        Observable<MessageDataBean> observable = Api.getInstance().service.getMessageList(user_id,
                page, pageSize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MessageDataBean>() {
                    @Override
                    public void accept(MessageDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
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
     * 消息已读
     *
     * @param user_id
     * @param app_push_id
     */
    public void setReadPush(String user_id, String app_push_id) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.setReadPush(user_id,
                app_push_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.setReadPushSuccess(responseBean);
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
