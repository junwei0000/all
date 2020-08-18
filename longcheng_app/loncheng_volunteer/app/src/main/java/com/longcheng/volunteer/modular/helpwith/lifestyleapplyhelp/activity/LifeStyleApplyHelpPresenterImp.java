package com.longcheng.volunteer.modular.helpwith.lifestyleapplyhelp.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.volunteer.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class LifeStyleApplyHelpPresenterImp<T> extends LifeStyleApplyHelpContract.Presenter<LifeStyleApplyHelpContract.View> {

    private Context mContext;
    private LifeStyleApplyHelpContract.View mView;
    private LifeStyleApplyHelpContract.Model mModel;

    public LifeStyleApplyHelpPresenterImp(Context mContext, LifeStyleApplyHelpContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }


    public void getLifeNeedHelpNumberTaskSuccess(String user_id, String good_id) {
        mView.showDialog();
        Observable<LifeNeedDataBean> observable = Api.getInstance().service.getLifeNeedHelpNumberTask(user_id, good_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LifeNeedDataBean>() {
                    @Override
                    public void accept(LifeNeedDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.getNeedHelpNumberTaskSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
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
     * 申请互助-接福人列表
     *
     * @param user_id
     */
    public void getPeopleList(String user_id) {
        mView.showDialog();
        Observable<PeopleDataBean> observable = Api.getInstance().service.getPeopleList(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PeopleDataBean>() {
                    @Override
                    public void accept(PeopleDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.PeopleListSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
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
     * 申请互助
     *
     * @param user_id
     */
    public void applyAction(String user_id,
                            String shop_goods_price_id,
                            String remark,
                            int purpose,
                            String address_id,
                            int apply_goods_number,
                            String receive_user_id,
                            String goods_id,
                            String purpose_remark) {
        Log.e("applyAction", "shop_goods_price_id=" + shop_goods_price_id);
        Log.e("applyAction", "address_id=" + address_id);
        Log.e("applyAction", "goods_id=" + goods_id);
        Log.e("applyAction", "receive_user_id=" + receive_user_id);

        mView.showDialog();
        Observable<LifeNeedDataBean> observable = Api.getInstance().service.lifeStyleApplyAction(user_id,
                shop_goods_price_id,
                remark, purpose, address_id, apply_goods_number, receive_user_id, goods_id, purpose_remark, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LifeNeedDataBean>() {
                    @Override
                    public void accept(LifeNeedDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.applyActionSuccess(responseBean);
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

    /**
     * 设置行动任务数据已读
     *
     * @param user_id
     */
    public void setTaskRead(String user_id, String help_goods_id) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.setLifeTaskRead(user_id, help_goods_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        Log.e("Observable", "" + responseBean.toString());
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
     * @param
     * @name 获取地址列表
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void setAddressList(String user_id, String receive_user_id) {
        mView.showDialog();
        Observable<AddressListDataBean> observable = Api.getInstance().service.getAddressList(user_id, receive_user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AddressListDataBean>() {
                    @Override
                    public void accept(AddressListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.GetAddressListSuccess(responseBean);
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
