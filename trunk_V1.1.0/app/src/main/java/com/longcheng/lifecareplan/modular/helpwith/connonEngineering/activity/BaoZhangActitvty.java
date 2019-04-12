package com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailHelpDialogUtils;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.EnergyDetailDataBean;
import com.longcheng.lifecareplan.modular.mine.fragment.genius.ActionlHelpDialogUtils;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeHandler;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 生活保障/志愿者
 * Created by Burning on 2018/9/11.
 */

public class BaoZhangActitvty extends WebAct {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;

    private ShareUtils mShareUtils;

    BaoZhangDialogUtils mDetailHelpDialogUtils;
    List<DetailItemBean> mutual_help_money_all;
    private String knp_sharetitle, knp_shareurl, knp_sharePic, knp_sharedesc;
    private String life_id;
    private List<DetailItemBean> blessings_list;
    private VolunterDialogUtils mVolunterDialogUtils;

    String life_order_id = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.pagetop_iv_rigth:
                //分享
                if (mShareUtils == null) {
                    mShareUtils = new ShareUtils(mActivity);
                }
                if (!TextUtils.isEmpty(knp_shareurl)) {
                    mShareUtils.setShare(knp_sharedesc, knp_sharePic, knp_shareurl, knp_sharetitle);
                }
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
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        super.setListener();
        pagetopIvRigth.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopIvRigth.setVisibility(View.GONE);
        pagetopIvRigth.setBackgroundResource(R.mipmap.wisheachdetails_share);
    }

    @Override
    public void initDataAfter() {
        super.initDataAfter();
        String title = getIntent().getStringExtra("title");
        pageTopTvName.setText(title);
        String url = getIntent().getStringExtra("html_url");
        loadUrl(url);
        //生活保障--获取分享 knp_msg_id
        mBridgeWebView.registerHandler("knp_getKnpMsgId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String url = jsonObject.optString("url", "");
                    String life_repay_id = jsonObject.optString("knp_msg_id", "");
                    if (pagetopIvRigth != null) {
                        if (!TextUtils.isEmpty(url) && url.contains("/home/life/repayinfo")) {
                            pagetopIvRigth.setVisibility(View.VISIBLE);
                        } else {
                            pagetopIvRigth.setVisibility(View.GONE);
                        }
                    }
                    if (!life_repay_id.equals("0"))
                        getLifeDetail(life_repay_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //生活保障--显示祝福弹层
        mBridgeWebView.registerHandler("Life_AppPayment", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                life_id = data;
                weixinPayBackType = "lifePay";
                Log.e("registerHandler", "data=" + data);
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                    if (mDetailHelpDialogUtils == null) {
                        mDetailHelpDialogUtils = new BaoZhangDialogUtils(mActivity, mHandler, BLESSING);
                    }
                    mDetailHelpDialogUtils.initData(blessings_list, mutual_help_money_all);
                    mDetailHelpDialogUtils.showPopupWindow();
                }
            }
        });

        //志愿者互祝---显示祝福弹层
        mBridgeWebView.registerHandler("Life_PayMoney", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                weixinPayBackType = "VoluntePay";
                Voluntepay_money = data;
                if (mVolunterDialogUtils == null) {
                    mVolunterDialogUtils = new VolunterDialogUtils(mActivity, mHandler, VolunterSelectPay);
                }
                mVolunterDialogUtils.showPopupWindow(data);
            }
        });
    }

    /**
     * ____________________________________________________________
     */
    /**
     * 微信回调类型
     */
    String weixinPayBackType = "";

    String Voluntepay_money = "";

    private void VoluntePay(String payment_channel, String pay_money) {
        Observable<PayWXDataBean> observable = Api.getInstance().service.VoluntePay(UserUtils.getUserId(mContext),
                payment_channel, pay_money, ExampleApplication.token);
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
                            if (payment_channel.equals("1")) {
                                Log.e(TAG, payWeChatBean.toString());
                                PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                            } else if (payment_channel.equals("2")) {
                                String payInfo = payWeChatBean.getPayInfo();
                                PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        volunterPaySuccuess();
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

    private void volunterPaySuccuess() {
        mBridgeWebView.callHandler("Life_pay365SuccessBack", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * ____________________________________________________________
     */

    public static final int BLESSING = 22;
    public static final int VolunterSelectPay = 33;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        Bundle bundle;

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BLESSING:
                    bundle = msg.getData();
                    String help_comment_content = bundle.getString("help_comment_content");
                    String payType = bundle.getString("payType");
                    int selectmoney = bundle.getInt("selectmoney");
                    int help_number = bundle.getInt("help_number");
                    Log.e("lifepayHelp", "help_number=" + help_number);
                    lifepayHelp(UserUtils.getUserId(mContext), help_comment_content, payType, life_id, selectmoney, help_number);
                    break;
                case VolunterSelectPay:
                    bundle = msg.getData();
                    String payTypes = bundle.getString("payType");
                    VoluntePay(payTypes, Voluntepay_money);
                    break;
            }
        }
    };

    /**
     * 生活保障 互祝支付
     *
     * @param user_id
     * @param help_comment_content
     * @param pay_way
     * @param life_id
     * @param money
     */
    public void lifepayHelp(String user_id, String help_comment_content, String pay_way, String life_id, int money, int help_number) {
        Log.e("Observable", "" + ExampleApplication.token);
        Observable<PayWXDataBean> observable = Api.getInstance().service.BaoZhangPayHelp(user_id,
                help_comment_content, pay_way, life_id, money, help_number, ExampleApplication.token);
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
                            life_order_id = payWeChatBean.getOne_order_id();
                            if (pay_way.equals("1")) {
                                Log.e(TAG, payWeChatBean.toString());
                                PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                            } else if (pay_way.equals("2")) {
                                String payInfo = payWeChatBean.getPayInfo();
                                PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        lifeSkipSuccess();
                                    }

                                    @Override
                                    public void onFailure(String error) {

                                    }
                                });
                            } else {
                                lifeSkipSuccess();
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        mView.ListError();
                        ToastUtils.showToast(R.string.net_tishi);
                        Log.e("Observable", "" + throwable.toString());
                    }
                });
    }

    private void lifeSkipSuccess() {
        mBridgeWebView.callHandler("Life_paySuccessBack", "" + life_order_id, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * 获取生活保障详情数据
     *
     * @param life_repay_id
     */
    public void getLifeDetail(String life_repay_id) {
        Observable<EnergyDetailDataBean> observable = Api.getInstance().service.getBaoZMsgDetail(UserUtils.getUserId(mContext), life_repay_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EnergyDetailDataBean>() {
                    @Override
                    public void accept(EnergyDetailDataBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        if (status.equals("400")) {
                        } else if (status.equals("200")) {
                            DetailAfterBean mDetailAfterBean = (DetailAfterBean) responseBean.getData();
                            if (mDetailAfterBean != null) {
                                knp_sharetitle = mDetailAfterBean.getKnp_sharetitle();
                                knp_shareurl = mDetailAfterBean.getKnp_shareurl();
                                knp_sharePic = mDetailAfterBean.getKnp_sharePic();
                                knp_sharedesc = mDetailAfterBean.getKnp_sharedesc();
                                blessings_list = mDetailAfterBean.getKnp_blessings_list();
                                mutual_help_money_all = mDetailAfterBean.getMutual_help_money_all();
                                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                                } else {
                                    mutual_help_money_all = new ArrayList<>();
                                }
                                Log.e("ResponseBody", "____________________" + mutual_help_money_all.size());
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        qiMingSkipHelp("0");
                        Log.e("ResponseBody", "____________________" + throwable.toString());
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
        filter.addAction(ConstantManager.BroadcastReceiver_KNP_ACTION);
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
                if (weixinPayBackType.equals("lifePay")) {
                    lifeSkipSuccess();
                } else if (weixinPayBackType.equals("VoluntePay")) {
                    volunterPaySuccuess();
                }
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


