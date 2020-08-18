package com.longcheng.lifecareplan.modular.helpwith.autohelp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.VolunterDialogUtils;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.webView.WebAct;
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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */

public class AutoHelpH5Activity extends WebAct {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;


    private String kn_url;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("智能互祝");
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
        kn_url = getIntent().getStringExtra("html_url");
        if (TextUtils.isEmpty(kn_url)) {
            kn_url = HelpWithFragmentNew.automationHelpUrl;
        }
        loadUrl(kn_url);
        //充值
        mBridgeWebView.registerHandler("CashRecharge_APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "Life_PayMoney=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String type = jsonObject.optString("type", "2");
                    String money = jsonObject.optString("money", "");
                    RechargePay(type, money);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void RechargePay(String payment_channel, String pay_money) {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.RechargePay(UserUtils.getUserId(mContext),
                payment_channel, pay_money, ExampleApplication.token);
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
                                PayWXAfterBean payWeChatBean = (PayWXAfterBean) responseBean.getData();
                                if (payment_channel.equals("2")) {
                                    Log.e(TAG, payWeChatBean.toString());
                                    PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                                } else if (payment_channel.equals("3")) {
                                    String payInfo = payWeChatBean.getPayInfo();
                                    PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            PayRefresh();
                                        }

                                        @Override
                                        public void onFailure(String error) {

                                        }
                                    });
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

    private void autohelpRefresh() {
        //智能互祝----刷新页面数据
        mBridgeWebView.callHandler("autohelp_refresh", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    private void PayRefresh() {
        //智能互祝----刷新页面数据
        mBridgeWebView.callHandler("LifePaySuccess_AppBack", "", new CallBackFunction() {
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
        filter.addAction(ConstantManager.BroadcastReceiver_KNP_ACTION);
        filter.addAction(ConstantManager.BroadcastReceiver_PAY_ACTION);
        registerReceiver(mReceiver, filter);
    }

    public static final int knpPaySuccessBack = 11;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);
            if (errCode == WXPayEntryActivity.PAY_SUCCESS) {
                PayRefresh();
            } else if (errCode == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (errCode == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            } else if (errCode == knpPaySuccessBack) {
                autohelpRefresh();
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
