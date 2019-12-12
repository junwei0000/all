package com.longcheng.lifecareplan.modular.mine.myaddress.activity;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class AddressPresenterImp<T> extends AddressContract.Presenter<AddressContract.View> {

    private Context mContext;
    private AddressContract.View mView;
    private AddressContract.Model mModel;

    public AddressPresenterImp(Context mContext, AddressContract.View mView) {
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
    public void setListViewData(String user_id, String receive_user_id) {
        mView.showDialog();
        Observable<AddressListDataBean> observable = Api.getInstance().service.getAddressList(user_id, receive_user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AddressListDataBean>() {
                    @Override
                    public void accept(AddressListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean);
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
     * @name 添加
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void addAddress(String user_id, String order_id, String receive_user_id, String consignee, String province,
                           String city, String district, String address, String mobile, String is_default) {
        mView.showDialog();
        Observable<AddressListDataBean> observable = Api.getInstance().service.addAddress(user_id, order_id, receive_user_id, consignee, province, city, district, address, mobile, is_default, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AddressListDataBean>() {
                    @Override
                    public void accept(AddressListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.AddSuccess(responseBean);
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
     * @name 编辑地址
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void editAddress(String user_id, String address_id, String consignee, String province,
                            String city, String district, String address, String mobile, String is_default) {
        mView.showDialog();
        Observable<AddressListDataBean> observable = Api.getInstance().service.editAddress(user_id, address_id, consignee, province, city, district, address, mobile, is_default, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AddressListDataBean>() {
                    @Override
                    public void accept(AddressListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.AddSuccess(responseBean);
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
     * @name 设置默认地址
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void setDefaultAddress(String user_id, String address_id) {
        mView.showDialog();
        Observable<AddressListDataBean> observable = Api.getInstance().service.setDefaultAddress(user_id, address_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AddressListDataBean>() {
                    @Override
                    public void accept(AddressListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.AddSuccess(responseBean);
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
     * @name 删除地址
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void delAddress(String user_id, String address_id) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.delAddress(user_id, address_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.delSuccess(responseBean);
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
