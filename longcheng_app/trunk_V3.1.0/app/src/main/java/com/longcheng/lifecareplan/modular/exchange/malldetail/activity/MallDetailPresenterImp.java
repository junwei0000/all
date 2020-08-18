package com.longcheng.lifecareplan.modular.exchange.malldetail.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.GoodsDetailDataBean;
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

public class MallDetailPresenterImp<T> extends MallDetailContract.Presenter<MallDetailContract.View> {

    private Context mContext;
    private MallDetailContract.View mView;

    public MallDetailPresenterImp(Context mContext, MallDetailContract.View mView) {
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
    public void getDetailData(String user_id, String shop_goods_id) {
        mView.showDialog();
        Log.e("Observable", "" + ExampleApplication.token + "    " + shop_goods_id);
        Observable<GoodsDetailDataBean> observable = Api.getInstance().service.getMallGoodsDetail(user_id,
                shop_goods_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<GoodsDetailDataBean>() {
                    @Override
                    public void accept(GoodsDetailDataBean responseBean) throws Exception {
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

}
