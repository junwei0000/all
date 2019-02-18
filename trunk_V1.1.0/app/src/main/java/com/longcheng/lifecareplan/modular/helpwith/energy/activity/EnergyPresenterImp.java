package com.longcheng.lifecareplan.modular.helpwith.energy.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpEnergyListDataBean;
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

public class EnergyPresenterImp<T> extends EnergyContract.Presenter<EnergyContract.View> {

    private Context mContext;
    private EnergyContract.View mView;
    private EnergyContract.Model mModel;

    public EnergyPresenterImp(Context mContext, EnergyContract.View mView) {
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
                                int id,
                                String Search,
                                int progress,
                                int status,
                                int help_status,
                                int goods_id,
                                int page,
                                int page_size) {
        Log.e("Observable", "user_id=" + user_id + " ;id= " +
                id + ";Search= " + Search + ";progress= " + progress + " ;status=" + status + "  ;help_status=" + help_status
                + ";goods_id= " + goods_id + " ;page=" + page + " ;page_size=" + page_size);
        mView.showDialog();
        Observable<HelpEnergyListDataBean> observable = Api.getInstance().service.getHelpList(user_id,
                id, Search, progress, status, help_status, goods_id, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<HelpEnergyListDataBean>() {
                    @Override
                    public void accept(HelpEnergyListDataBean responseBean) throws Exception {
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
     * @param
     * @name 获取ViewPager的数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void setActionList(String user_id) {
        mView.showDialog();
        Observable<ActionListDataBean> observable = Api.getInstance().service.getHelpActionList(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ActionListDataBean>() {
                    @Override
                    public void accept(ActionListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ActionSuccess(responseBean);
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
