package com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.bean.LifeStyleListDataBean;
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

public class LifeStylePresenterImp<T> extends LifeStyleContract.Presenter<LifeStyleContract.View> {

    private Context mContext;
    private LifeStyleContract.View mView;
    private LifeStyleContract.Model mModel;

    public LifeStylePresenterImp(Context mContext, LifeStyleContract.View mView) {
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
    public void setListViewData(String user_id,
                                int time_sort,
                                String Search,
                                String h_user_id,
                                int status,
                                int help_status,
                                int page,
                                int page_size) {
        Log.e("Observable", "user_id=" + user_id + " ;time_sort= " +
                time_sort + ";Search= " + Search + " ;status=" + status + "  ;help_status=" + help_status
                + " ;page=" + page + " ;page_size=" + page_size);
        mView.showDialog();
        Observable<LifeStyleListDataBean> observable = Api.getInstance().service.getLifeStyleList(user_id,
                time_sort, Search, h_user_id, status, help_status, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LifeStyleListDataBean>() {
                    @Override
                    public void accept(LifeStyleListDataBean responseBean) throws Exception {
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
}
