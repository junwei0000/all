package com.longcheng.lifecareplan.modular.exchange.fragment;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.exchange.bean.JieQiListDataBean;
import com.longcheng.lifecareplan.modular.exchange.bean.MallGoodsListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;

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
