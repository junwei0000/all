package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.lifestylerank.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.lifestylerank.bean.LifeRankListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class LifeRankPresenterImp<T> extends LifeRankContract.Presenter<LifeRankContract.View> {

    private Context mContext;
    private LifeRankContract.View mView;
    private LifeRankContract.Model mModel;

    public LifeRankPresenterImp(Context mContext, LifeRankContract.View mView) {
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
    public void setListViewData(String user_id, String help_goods_id, int page, int pageSize) {
        Observable<LifeRankListDataBean> observable = Api.getInstance().service.getLifeRankList(user_id, help_goods_id, page, pageSize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LifeRankListDataBean>() {
                    @Override
                    public void accept(LifeRankListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ListSuccess(responseBean, page);
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
}
