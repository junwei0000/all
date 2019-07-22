package com.longcheng.lifecareplan.modular.home.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionListDataBean;
import com.longcheng.lifecareplan.modular.home.bean.HomeDataBean;
import com.longcheng.lifecareplan.modular.home.bean.PoActionListDataBean;
import com.longcheng.lifecareplan.modular.home.bean.QuickTeamDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;
import com.longcheng.lifecareplan.utils.LocationUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:23
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HomePresenterImp<T> extends HomeContract.Present<HomeContract.View> {

    private HomeContract.View view;
    private Context mContext;

    public HomePresenterImp(Context mContext, HomeContract.View view) {
        this.view = view;
        this.mContext = mContext;
    }

    @Override
    public void fetch() {

    }

    LocationUtils mLocationUtils;

    /**
     */
    public void setListViewData() {
        if (mLocationUtils == null) {
            mLocationUtils = new LocationUtils();
        }
        double[] mLngAndLat = mLocationUtils.getLngAndLatWithNetwork(mContext);
        double phone_user_latitude = mLngAndLat[0];
        double phone_user_longitude = mLngAndLat[1];
        String phone_user_address = mLocationUtils.getAddress(mContext, mLngAndLat[0], mLngAndLat[1]);
        String user_id = UserUtils.getUserId(mContext);
        Observable<HomeDataBean> observable = Api.getInstance().service.getHomeList(user_id, phone_user_latitude,
                phone_user_longitude, phone_user_address, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<HomeDataBean>() {
                    @Override
                    public void accept(HomeDataBean responseBean) throws Exception {
                        view.ListSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.ListError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });

    }

    public void getReMenActioin() {
        view.showDialog();
        Observable<PoActionListDataBean> observable = Api.getInstance().service.getReMenActioin();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PoActionListDataBean>() {
                    @Override
                    public void accept(PoActionListDataBean responseBean) throws Exception {
                        view.dismissDialog();
                        view.ActionListSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.dismissDialog();
                        view.ListError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });

    }

    public void getQuickTeamUrl() {
        Observable<QuickTeamDataBean> observable = Api.getInstance().service.getQuickTeamUrl(UserUtils.getUserId(mContext),ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<QuickTeamDataBean>() {
                    @Override
                    public void accept(QuickTeamDataBean responseBean) throws Exception {
                        view.QuickTeamUrlSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.ListError();
                        Log.e("Observable", "" + throwable.toString());
                    }
                });

    }
}
