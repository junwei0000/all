package com.longcheng.lifecareplan.modular.mine.goodluck.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.goodluck.bean.GoodLuckListDataBean;
import com.longcheng.lifecareplan.utils.myview.MyDialog;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class GoodLuckPresenterImp<T> extends GoodLuckContract.Presenter<GoodLuckContract.View> {

    private Activity mContext;
    private GoodLuckContract.View mView;
    private GoodLuckContract.Model mModel;

    public GoodLuckPresenterImp(Activity mContext, GoodLuckContract.View mView) {
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
    public void getGoodLuckList(String user_id,
                                int page,
                                int pageSize) {
        mView.showDialog();
        Observable<GoodLuckListDataBean> observable = Api.getInstance().service.getGoodLuckList(user_id,
                page, pageSize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<GoodLuckListDataBean>() {
                    @Override
                    public void accept(GoodLuckListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Observable", "" + throwable.toString());
                        mView.ListError();
                        mView.dismissDialog();
                    }
                });

    }

    /**
     * 开红包
     *
     * @param user_id
     * @param mutual_help_user_red_packet_id
     */
    public void openRedEnvelope(String user_id, String mutual_help_user_red_packet_id) {
        Observable<OpenRedDataBean> observable = Api.getInstance().service.GoodLuckopenRedEnvelope(user_id,
                mutual_help_user_red_packet_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<OpenRedDataBean>() {
                    @Override
                    public void accept(OpenRedDataBean responseBean) throws Exception {
                        mView.OpenRedEnvelopeSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onOpenRedEnvelopeError("");
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    /**
     * 一键开红包
     *
     * @param user_id
     */
    public void openRedEnvelopeOneKey(String user_id) {
        showDialog();
        Observable<OpenRedDataBean> observable = Api.getInstance().service.openRedEnvelopeOnekey(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<OpenRedDataBean>() {
                    @Override
                    public void accept(OpenRedDataBean responseBean) throws Exception {
                        dismissDialog();
                        mView.OpenRedEnvelopeOneKeySuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        mView.onOpenRedEnvelopeError("");
                    }
                });
    }

    public void showDialog() {
        showOpenLoadingDialog();
    }

    public void dismissDialog() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openLoadingDialogDismiss();
            }
        }, 500);//秒后执行Runnable中的run方法
    }

    MyDialog selectOpenLoadingDialog;

    private void showOpenLoadingDialog() {
        if (selectOpenLoadingDialog == null) {
            selectOpenLoadingDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_openredload);// 创建Dialog并设置样式主题
            selectOpenLoadingDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectOpenLoadingDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectOpenLoadingDialog.show();
            WindowManager m = mContext.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectOpenLoadingDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectOpenLoadingDialog.getWindow().setAttributes(p); //设置生效
        } else {
            selectOpenLoadingDialog.show();
        }
    }

    private void openLoadingDialogDismiss() {
        if (selectOpenLoadingDialog != null && selectOpenLoadingDialog.isShowing()) {
            selectOpenLoadingDialog.dismiss();
        }
    }
}
