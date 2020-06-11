package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleCommentDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.SKBPayDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class LifeStyleDetailPresenterImp<T> extends LifeStyleDetailContract.Presenter<LifeStyleDetailContract.View> {

    private Context mContext;
    private LifeStyleDetailContract.View mView;
    private LifeStyleDetailContract.Model mModel;

    public LifeStyleDetailPresenterImp(Context mContext, LifeStyleDetailContract.View mView) {
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
    public void getDetailData(String user_id, String help_goods_id) {
        mView.showDialog();
        Log.e("Observable", "" + ExampleApplication.token + "    " + help_goods_id);
        Observable<LifeStyleDetailDataBean> observable = Api.getInstance().service.getLifeStyleDetailWares(user_id,
                help_goods_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LifeStyleDetailDataBean>() {
                    @Override
                    public void accept(LifeStyleDetailDataBean responseBean) throws Exception {
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
     * @param help_goods_id
     * @param page
     * @param page_size
     * @param type          1.生命能量 2.生活方式互助
     */
    public void getCommentList(String user_id, String help_goods_id, int page, int page_size, int type) {
        Log.e("aaa", "getCommentList:help/help_comment_ajax_list pageIndex = " + page + " , size = " + page_size + " , type = " + type);
        Observable<LifeStyleDetailDataBean> observable = Api.getInstance().service.getLifeStyleCommentList(user_id,
                help_goods_id, page, page_size, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LifeStyleDetailDataBean>() {
                    @Override
                    public void accept(LifeStyleDetailDataBean responseBean) throws Exception {
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
        Observable<LifeStyleCommentDataBean> observable = Api.getInstance().service.sendCommentLifeStyle(user_id,
                content, be_comment_id, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LifeStyleCommentDataBean>() {
                    @Override
                    public void accept(LifeStyleCommentDataBean responseBean) throws Exception {
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
     * 支付
     *
     * @param user_id
     * @param help_comment_content
     * @param help_goods_skb_money_id
     * @param help_goods_id
     * @param skb_price
     */
    public void lifeStylePayHelp(String user_id, String help_comment_content,
                                 String help_goods_skb_money_id, String help_goods_id, int skb_price, int help_type) {
        mView.showDialog();
        Observable<SKBPayDataBean> observable = Api.getInstance().service.lifeStylePayHelp(user_id,
                help_comment_content, help_goods_skb_money_id, help_goods_id, skb_price, help_type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<SKBPayDataBean>() {
                    @Override
                    public void accept(SKBPayDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
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

}
