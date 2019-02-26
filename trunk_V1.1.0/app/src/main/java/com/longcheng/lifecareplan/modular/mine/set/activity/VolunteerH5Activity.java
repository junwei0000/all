package com.longcheng.lifecareplan.modular.mine.set.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailHelpDialogUtils;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.RedEnvelopeKnpActivity;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeHandler;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 志愿者/坐堂医
 */
public class VolunteerH5Activity extends WebAct {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        super.setListener();
        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        super.initDataAfter();
        String about_me_url = getIntent().getStringExtra("html_url");
        loadUrl(about_me_url);
        firstComIn = false;
        //坐堂医-----押金支付
        mBridgeWebView.registerHandler("toVolunDoctor_pay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                showpayDialog();
            }
        });
    }

    MyDialog payDialog;


    public void showpayDialog() {
        if (payDialog == null) {
            payDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_doctor_pay);// 创建Dialog并设置样式主题
            payDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = payDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            payDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = payDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth();
            payDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_cancel = (TextView) payDialog.findViewById(R.id.tv_cancel);
            TextView tv_weixin = (TextView) payDialog.findViewById(R.id.tv_weixin);
            TextView tv_alipay = (TextView) payDialog.findViewById(R.id.tv_alipay);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payDialog.dismiss();
                }
            });
            tv_weixin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payDialog.dismiss();
                    doctorPay("wxpay");
                }
            });
            tv_alipay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payDialog.dismiss();
                    doctorPay("alipay");
                }
            });
        } else {
            payDialog.show();
        }
    }

    private void doctorPay(String payment_channel) {
        Observable<PayWXDataBean> observable = Api.getInstance().service.doctorPay(UserUtils.getUserId(mContext),
                payment_channel, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        if (status.equals("400")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status.equals("200")) {
                            PayWXAfterBean payWeChatBean = (PayWXAfterBean) responseBean.getData();
                            if (payment_channel.equals("wxpay")) {
                                Log.e(TAG, payWeChatBean.toString());
                                PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                            } else if (payment_channel.equals("alipay")) {
                                String payInfo = payWeChatBean.getPayInfo();
                                PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        helpSkipSuccess();
                                    }

                                    @Override
                                    public void onFailure(String error) {

                                    }
                                });
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(R.string.net_tishi);
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    private void helpSkipSuccess() {
        MineFragment.showDoctorDialogStatus = true;
        Intent intents = new Intent();
        intents.setAction(ConstantManager.MAINMENU_ACTION);
        intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_CENTER);
        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
        ActivityManager.getScreenManager().popAllActivityOnlyMain();
    }

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        //坐堂医 回来刷新
        if (!firstComIn) {
            Log.e("callHandler", "doctor_taskReload");
            mBridgeWebView.callHandler("doctor_taskReload", "", new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantManager.BroadcastReceiver_PAY_ACTION);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 微信支付回调广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);
            if (errCode == WXPayEntryActivity.PAY_SUCCESS) {
                helpSkipSuccess();
            } else if (errCode == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (errCode == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            }
        }
    };

    private void back() {
        if (mBridgeWebView != null && mBridgeWebView.canGoBack()) {
            mBridgeWebView.goBack();
        } else {
            doFinish();
        }
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
