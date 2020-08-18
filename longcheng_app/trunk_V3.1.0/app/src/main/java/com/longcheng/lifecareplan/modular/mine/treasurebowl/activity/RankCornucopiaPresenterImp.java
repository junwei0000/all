package com.longcheng.lifecareplan.modular.mine.treasurebowl.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseView;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencyBangDanBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencysBangContract;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankListBean;
import com.longcheng.lifecareplan.utils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RankCornucopiaPresenterImp<V>  extends RankCornucopiaContract.Presenter<RankCornucopiaContract.View>{

    private Context mContext;
    private RankCornucopiaContract.View mView;

    @Override
    public void fetch() {

    }

    public RankCornucopiaPresenterImp(Context mContext, RankCornucopiaContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void blessCardRanking(String user_id,
                                 int type,
                                 int page,
                                 int page_size) {
        LogUtils.e("page" + page);
        mView.showDialog();
        Observable<RankListBean> observable = Api.getInstance().service.getRankingUser(user_id,type,
                page, page_size,  ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<RankListBean>() {
                    @Override
                    public void accept(RankListBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ListSuccess(responseBean,page);

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
