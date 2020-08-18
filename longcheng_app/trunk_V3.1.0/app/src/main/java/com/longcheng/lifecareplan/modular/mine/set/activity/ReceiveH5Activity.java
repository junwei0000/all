package com.longcheng.lifecareplan.modular.mine.set.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeHandler;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;
import com.longcheng.lifecareplan.zxing.activity.MipcaCaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 收付款
 */
public class ReceiveH5Activity extends WebAct {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    private String one_order_id;


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

        //付款码-收款支付
        mBridgeWebView.registerHandler("Ticket_AppMerchantPayment", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "Ticket_AppMerchantPayment=" + data);
                PaymentStatus = "MerchantPayment";
                Payment(data);
            }
        });
        //收款码-付款支付
        mBridgeWebView.registerHandler("Ticket_AppReceiptPayment", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "Ticket_AppReceiptPayment=" + data);
                PaymentStatus = "ReceiptPayment";
                Payment(data);

            }
        });
        //收款码-用户付款失败重新扫一扫
        mBridgeWebView.registerHandler("Ticket_AppRenewQR", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, MipcaCaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                doFinish();
            }
        });

    }

    private String PaymentStatus = "";
    private String payment_channel = "";

    /**
     * 支付
     *
     * @param data
     */
    public void Payment(String data) {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Log.e("Observable", "" + ExampleApplication.token);
        //商家收款地址
        Observable<PayWXDataBean> observable = null;
        if (PaymentStatus.equals("ReceiptPayment")) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                String receive_user_id = jsonObject.optString("receive_user_id");
                payment_channel = jsonObject.optString("payment_channel");
                String user_ticket_id = jsonObject.optString("user_ticket_id");
                String pay_check_code = jsonObject.optString("pay_check_code");
                String pay_money = jsonObject.optString("pay_money");
                observable = Api.getInstance().service.receivePay(UserUtils.getUserId(mContext),
                        receive_user_id, payment_channel, user_ticket_id, pay_check_code, pay_money, ExampleApplication.token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject(data);
                String payment_order_id = jsonObject.optString("payment_order_id");
                payment_channel = jsonObject.optString("payment_channel");
                String pay_check_code = jsonObject.optString("pay_check_code");
                String current_time = jsonObject.optString("current_time");
                observable = Api.getInstance().service.MerchantPay(UserUtils.getUserId(mContext),
                        payment_order_id, payment_channel, pay_check_code, current_time, ExampleApplication.token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {

                            String status = responseBean.getStatus();
                            if (status.equals("400")) {
                                ToastUtils.showToast(responseBean.getMsg());
                            } else if (status.equals("200")) {
                                PayWXAfterBean payWeChatBean = responseBean.getData();
                                one_order_id = payWeChatBean.getOne_order_id();
                                if (payment_channel.equals("3")) {//weixin
                                    Log.e(TAG, payWeChatBean.toString());
                                    PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                                } else if (payment_channel.equals("4")) {//zhifubao
                                    String payInfo = payWeChatBean.getPayInfo();
                                    PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            PaySuccess(WXPayEntryActivity.PAY_SUCCESS);
                                        }

                                        @Override
                                        public void onFailure(String error) {
                                            PaySuccess(WXPayEntryActivity.PAY_FAILE);
                                        }
                                    });
                                } else {
                                    PaySuccess(WXPayEntryActivity.PAY_SUCCESS);
                                }
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(R.string.net_tishi);
                        Log.e("Observable", "" + throwable.toString());
                        dismissDialog();
                    }
                });
    }

    private void PaySuccess(int code) {
        Log.e("registerHandler", "PaySuccess=" + code);
        if (PaymentStatus.equals("ReceiptPayment")) {
            mBridgeWebView.callHandler("Ticket_AppReceiptPayment_Callback", "" + code, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        } else {
            mBridgeWebView.callHandler("Ticket_AppMerchantPayment_Callback", "" + code, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        }
    }

    @Override

    protected void onResume() {
        super.onResume();
        //套餐刷新页面
        mBridgeWebView.callHandler("ticket_RefreshList", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
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
                PaySuccess(WXPayEntryActivity.PAY_SUCCESS);
            } else if (errCode == WXPayEntryActivity.PAY_FAILE) {
//                ToastUtils.showToast("支付失败");
                PaySuccess(WXPayEntryActivity.PAY_FAILE);
            } else if (errCode == WXPayEntryActivity.PAY_CANCLE) {
//                ToastUtils.showToast("支付取消");
                PaySuccess(WXPayEntryActivity.PAY_CANCLE);
            }
        }
    };


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
