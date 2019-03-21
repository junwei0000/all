package com.longcheng.lifecareplan.modular.helpwith.energydetail.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpEnergyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.CommentDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.EnergyDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.PayDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;

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

    /**
     * @param
     * @name 获取ViewPager的数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void getDetailData(String user_id, String id) {
        mView.showDialog();
        Log.e("Observable", "" + ExampleApplication.token + "    " + id);
        Observable<EnergyDetailDataBean> observable = Api.getInstance().service.getHelpDetail(user_id,
                id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EnergyDetailDataBean>() {
                    @Override
                    public void accept(EnergyDetailDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
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

    /**
     * 评论列表
     *
     * @param user_id
     * @param mutual_help_id
     * @param page
     * @param page_size
     * @param type           1.生命能量 2.生活方式互助
     */
    public void getCommentList(String user_id, String mutual_help_id, int page, int page_size, int type) {
        Log.e("aaa", "getCommentList:help/help_comment_ajax_list pageIndex = " + page + " , size = " + page_size + " , type = " + type);
        Observable<EnergyDetailDataBean> observable = Api.getInstance().service.getCommentList(user_id,
                mutual_help_id, page, page_size, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EnergyDetailDataBean>() {
                    @Override
                    public void accept(EnergyDetailDataBean responseBean) throws Exception {
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.CommentListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.ListError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    /**
     * 发送评论
     *
     * @param user_id
     * @param content
     * @param be_comment_id
     * @param type          1.生命能量 2.生活方式互助
     */
    public void sendComment(String user_id, String content, int be_comment_id, int type) {
        mView.showDialog();
        Log.e("Observable", "http://test.t.asdyf.com/api/v1_0_0/help/help_comment_ajax_reply?user_id=" + user_id + "&content=" + content
                + "&be_comment_id=" + be_comment_id + "&type=1&token=" + ExampleApplication.token);
        Observable<CommentDataBean> observable = Api.getInstance().service.sendComment(user_id,
                content, be_comment_id, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CommentDataBean>() {
                    @Override
                    public void accept(CommentDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.SendCommentSuccess(responseBean);
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
     * 支付
     *
     * @param user_id
     * @param help_comment_content
     * @param pay_way
     * @param msg_id
     * @param money
     */
    public void payHelp(String user_id, String help_comment_content, String pay_way, String msg_id, int money) {
        mView.showDialog();
        Log.e("Observable", "" + ExampleApplication.token);
        Observable<PayWXDataBean> observable = Api.getInstance().service.payHelp(user_id,
                help_comment_content, pay_way, msg_id, money, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        mView.dismissDialog();
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

    /**
     * 删除评论
     *
     * @param user_id
     * @param mutual_help_comment_id
     */
    public void delComment(String user_id, int mutual_help_comment_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.delComment(user_id,
                mutual_help_comment_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.delCommentSuccess(responseBean);
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
     * 获取红包信息
     *
     * @param user_id
     * @param one_order_id
     */
    public void getRedEnvelopeData(String user_id, String one_order_id) {
        mView.showDialog();
        Observable<PayDataBean> observable = Api.getInstance().service.getRedEnvelopeData(user_id,
                one_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayDataBean>() {
                    @Override
                    public void accept(PayDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.getRedEnvelopeDataSuccess(responseBean);
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
     * 开红包
     *
     * @param user_id
     * @param one_order_id
     */
    public void openRedEnvelope(String user_id, String one_order_id) {
        mView.showDialog();
        Observable<OpenRedDataBean> observable = Api.getInstance().service.openRedEnvelope(user_id,
                one_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<OpenRedDataBean>() {
                    @Override
                    public void accept(OpenRedDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.OpenRedEnvelopeDataSuccess(responseBean);
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
