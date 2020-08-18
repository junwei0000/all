package com.longcheng.volunteer.modular.helpwith.energydetail.rank.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.helpwith.energydetail.rank.bean.RankListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class RankPresenterImp<T> extends RankContract.Presenter<RankContract.View> {

    private Context mContext;
    private RankContract.View mView;
    private RankContract.Model mModel;

    public RankPresenterImp(Context mContext, RankContract.View mView) {
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
    public void setListViewData(String user_id, String msg_id, int page, int pageSize) {
        Observable<RankListDataBean> observable = Api.getInstance().service.getRankList(user_id, msg_id, page, pageSize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<RankListDataBean>() {
                    @Override
                    public void accept(RankListDataBean responseBean) throws Exception {
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
