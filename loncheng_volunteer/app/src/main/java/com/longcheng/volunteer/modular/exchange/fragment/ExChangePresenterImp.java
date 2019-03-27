package com.longcheng.volunteer.modular.exchange.fragment;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.exchange.bean.JieQiListDataBean;
import com.longcheng.volunteer.modular.exchange.bean.MallGoodsListDataBean;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:23
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ExChangePresenterImp<T> extends ExChangeContract.Present<ExChangeContract.View> {

    private ExChangeContract.View view;
    private ExChangeContract.Model model;

    public ExChangePresenterImp(ExChangeContract.View view) {
        this.view = view;
        model = new ExChangeModelImp();
    }

    @Override
    public void fetch() {

    }

    /**
     * @param user_id
     * @param page
     * @param page_size
     */
    public void getGoodsList(String user_id,
                             int category,
                             int time_sort,
                             int price_sort,
                             int hot_sort,
                             int solar_terms,
                             String search,
                             int page,
                             int page_size) {
        view.showDialog();
        Observable<MallGoodsListDataBean> observable = Api.getInstance().service.getMallGoodsList(user_id, category,
                time_sort, price_sort, hot_sort, solar_terms, search, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MallGoodsListDataBean>() {
                    @Override
                    public void accept(MallGoodsListDataBean responseBean) throws Exception {
                        view.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            view.ListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.dismissDialog();
                        view.ListError();
                    }
                });

    }

    public void getJieQiList(String user_id) {
        view.showDialog();
        Observable<JieQiListDataBean> observable = Api.getInstance().service.getJieQiList(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<JieQiListDataBean>() {
                    @Override
                    public void accept(JieQiListDataBean responseBean) throws Exception {
                        view.dismissDialog();
                        view.JieQiListSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.dismissDialog();
                        view.ListError();
                    }
                });

    }
}
