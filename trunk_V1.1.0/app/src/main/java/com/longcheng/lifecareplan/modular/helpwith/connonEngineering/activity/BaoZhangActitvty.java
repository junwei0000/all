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
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.EnergyDetailDataBean;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
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
import java.util.Map;

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
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;


    private ShareUtils mShareUtils;
    private List<DetailItemBean> blessings_list;
    private VolunterDialogUtils mVolunterDialogUtils;
    private VolunterDialogUtils mChangeLFDialogUtils;
    private BaoZhangDialogUtils mLifeBasicPayDialogUtils;
    private BaoZhangDialogUtils mDetailHelpDialogUtils;

    List<DetailItemBean> mutual_help_money_all;
    private String knp_sharetitle, knp_shareurl, knp_sharePic, knp_sharedesc;
    private String life_id;
    String life_order_id = "";
    /**
     * 生活保障详情id
     */
    public static String life_repay_id = "0";
    /**
     * 生活保障分享
     */
    public static int SHARETYPE_lifeDetailPay = 1000;
    public static int SHARETYPE_LifeBasicDetailPay = 1001;
    /**
     * 是否可以分享
     */
    boolean shareStatus = false;


    /**
     * 分享回调路径
     */
    public static String shareBackType = "";
    /**
     * 微信支付类型回调类型
     */
    String weixinPayBackType = "";

    String Voluntepay_money = "", ChangLFMoney = "";

    String volunteer_debt_item_id = "0", type = "1";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.pagetop_layout_rigth:
                //分享
                if (mShareUtils == null) {
                    mShareUtils = new ShareUtils(mActivity);
                }
                if (shareStatus && !TextUtils.isEmpty(knp_shareurl)) {
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
        pagetopLayoutRigth.setOnClickListener(this);
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
        //生活保障详情--获取分享 knp_msg_id
        mBridgeWebView.registerHandler("knp_getKnpMsgId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String url = jsonObject.optString("url", "");
                    life_repay_id = jsonObject.optString("knp_msg_id", "0");
                    if (pagetopIvRigth != null) {
                        if (!TextUtils.isEmpty(url) && url.contains("/home/life/repayinfo")) {
                            pagetopIvRigth.setVisibility(View.VISIBLE);
                            shareStatus = true;
                            shareBackType = "lifeDetail";
                        } else if (!TextUtils.isEmpty(url) && url.contains("/lifebasic/info")) {
                            pagetopIvRigth.setVisibility(View.VISIBLE);
                            shareStatus = true;
                            shareBackType = "LifeBasicDetail";
                        } else {
                            //非详情页、和自己的详情不让分享
                            shareBackType = "";
                            shareStatus = false;
                            pagetopIvRigth.setVisibility(View.GONE);
                        }
                    }
                    String route = jsonObject.optString("route", "");
                    if (!TextUtils.isEmpty(route)) {//自己的详情判断
                        if (route.equals("life_basic_info")) {
                            shareBackType = "LifeBasicDetail";
                        } else if (route.equals("life_info")) {
                            shareBackType = "lifeDetail";
                        }
                    }
                    if (!life_repay_id.equals("0")) {
                        if (shareBackType.equals("lifeDetail")) {
                            getLifeDetail(life_repay_id);
                        } else if (shareBackType.equals("LifeBasicDetail")) {
                            getLifeBasicDetail(life_repay_id);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //生活保障详情--显示祝福支付
        mBridgeWebView.registerHandler("Life_AppPayment", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                life_id = data;
                weixinPayBackType = "lifeDetailPay";
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
        //志愿者互祝---基础保障详情--显示支付
        mBridgeWebView.registerHandler("LifeBasic_AppPayment", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                life_id = data;
                weixinPayBackType = "LifeBasicDetailPay";
                Log.e("registerHandler", "data=" + data);
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                    if (mLifeBasicPayDialogUtils == null) {
                        mLifeBasicPayDialogUtils = new BaoZhangDialogUtils(mActivity, mHandler, LifeBasicAppPayment);
                    }
                    mLifeBasicPayDialogUtils.initData(blessings_list, mutual_help_money_all);
                    mLifeBasicPayDialogUtils.showPopupWindow();
                }
            }
        });
        //志愿者互祝---开启债务支付单个 批量
        mBridgeWebView.registerHandler("Life_PayMoney", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "Life_PayMoney=" + data);
                weixinPayBackType = "VoluntePay";
                try {
                    if (data.startsWith("{")) {
                        JSONObject jsonObject = new JSONObject(data);
                        Voluntepay_money = jsonObject.optString("money", "365");
                        volunteer_debt_item_id = jsonObject.optString("volunteer_debt_item_id", "0");
                        type = jsonObject.optString("type", "1");//1单个  2批量
                    } else {
                        Voluntepay_money = data;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mVolunterDialogUtils == null) {
                    mVolunterDialogUtils = new VolunterDialogUtils(mActivity, mHandler, VolunterSelectPay);
                }
                mVolunterDialogUtils.showPopupWindow(Voluntepay_money, "立即开启");
            }
        });

        //志愿者互祝---变更雷锋号支付
        mBridgeWebView.registerHandler("Life_PayChangeLeiFeng", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "Life_PayChangeLeiFeng=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    ChangLFMoney = jsonObject.optString("price", "100");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                weixinPayBackType = "ChangeLeiFengPay";
                if (mChangeLFDialogUtils == null) {
                    mChangeLFDialogUtils = new VolunterDialogUtils(mActivity, mHandler, ChangeLeiFengPay);
                }
                mChangeLFDialogUtils.showPopupWindow(ChangLFMoney, "立即变更");
            }
        });

        //志愿者互祝---申请基础保障支付
        mBridgeWebView.registerHandler("LifeBasic_applyPay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "Life_PayChangeLeiFeng=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String des_content = jsonObject.optString("des_content", "");
                    String payment_channel = jsonObject.optString("payment_channel", "");
                    weixinPayBackType = "LifeBasicApplyPay";
                    LifeBasicApplyPay(des_content, payment_channel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static final int BLESSING = 22;
    public static final int VolunterSelectPay = 33;
    public static final int sendLifeDetailShareNum = 44;
    public static final int ChangeLeiFengPay = 55;
    public static final int LifeBasicAppPayment = 66;
    public static final int sendLifeBasicDetailShareNum = 77;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        Bundle bundle;
        String help_comment_content;
        String payType;
        int selectmoney;
        int help_number;

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BLESSING:
                    bundle = msg.getData();
                    help_comment_content = bundle.getString("help_comment_content");
                    payType = bundle.getString("payType");
                    selectmoney = bundle.getInt("selectmoney");
                    help_number = bundle.getInt("help_number");
                    Log.e("lifepayHelp", "help_number=" + help_number);
                    lifepayHelp(UserUtils.getUserId(mContext), help_comment_content, payType, life_id, selectmoney, help_number);
                    break;
                case LifeBasicAppPayment:
                    bundle = msg.getData();
                    help_comment_content = bundle.getString("help_comment_content");
                    payType = bundle.getString("payType");
                    selectmoney = bundle.getInt("selectmoney");
                    help_number = bundle.getInt("help_number");
                    Log.e("lifepayHelp", "help_number=" + help_number);
                    LifeBasicDetailPay(UserUtils.getUserId(mContext), help_comment_content, payType, life_id, selectmoney, help_number);
                    break;
                case VolunterSelectPay:
                    bundle = msg.getData();
                    String payment_channel = bundle.getString("payType");
                    VoluntePay(payment_channel, Voluntepay_money, volunteer_debt_item_id);
                    break;
                case ChangeLeiFengPay:
                    bundle = msg.getData();
                    String paymentchannel = bundle.getString("payType");
                    ChangeLeiFengPay(paymentchannel, ChangLFMoney);
                    break;
                case sendLifeDetailShareNum:
                    sendLifeDetailShareNum();
                    break;
                case sendLifeBasicDetailShareNum:
                    sendLifeDetailShareNum();
                    break;

            }
        }
    };



    /**
     * 志愿者互祝---申请基础保障支付
     */
    private void LifeBasicApplyPay(String des_content, String payment_channel) {
        showDialog();
        String pay_source = "2";//1：微信 2：安卓 3：ios
        Observable<PayWXDataBean> observable = Api.getInstance().service.LifeBasicApplyPay(UserUtils.getUserId(mContext),
                des_content, payment_channel, pay_source, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
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
                                        LifeBasicApplyPaySuccuess();
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
                        dismissDialog();
                    }
                });
    }


    /**
     * 志愿者互祝----申请基础保障支付回调
     */
    private void LifeBasicApplyPaySuccuess() {
        mBridgeWebView.callHandler("LifeBasic_applyPaySucBack", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * 志愿者互祝---变更雷锋号支付
     */
    private void ChangeLeiFengPay(String payment_channel, String price) {
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.ChangeLeiFengPay(UserUtils.getUserId(mContext),
                payment_channel, price, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
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
                                        ChangeLeiFengSuccuess();
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
                        dismissDialog();
                    }
                });
    }

    /**
     * 志愿者互祝---变更雷锋号支付完刷新页面
     */
    private void ChangeLeiFengSuccuess() {
        mBridgeWebView.callHandler("Life_ChangeLeiFengBack", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }
/**
 * _____________________志愿者_开启债务支付________________________________
 */


    /**
     * 志愿者互祝-开启债务支付
     *
     * @param payment_channel
     * @param pay_money
     * @param volunteer_debt_item_id
     */
    private void VoluntePay(String payment_channel, String pay_money, String volunteer_debt_item_id) {
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.VoluntePay(UserUtils.getUserId(mContext),
                payment_channel, type, pay_money, volunteer_debt_item_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        showDialog();
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
                        showDialog();
                    }
                });
    }

    /**
     * 志愿者互祝---开启债务支付完刷新页面
     */
    private void volunterPaySuccuess() {
        mBridgeWebView.callHandler("Life_pay365SuccessBack", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * _____________________end_________________________________
     */


    /**
     * 志愿者互祝--基础保障详情互祝支付
     *
     * @param user_id
     * @param help_comment_content
     * @param pay_way
     * @param life_basic_id
     * @param money
     */
    public void LifeBasicDetailPay(String user_id, String help_comment_content, String pay_way, String life_basic_id, int money, int help_number) {
        showDialog();
        String pay_source = "2";//1：微信 2：安卓 3：ios
        Observable<PayWXDataBean> observable = Api.getInstance().service.LifeBasicDetailPay(user_id,
                help_comment_content, pay_way, life_basic_id, money, help_number, pay_source, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
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
                                        LifeBasicDetailPaySuccess();
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
                        dismissDialog();
                    }
                });
    }

    /**
     * 志愿者互祝--基础保障详情支付成功跳转红包页
     */
    private void LifeBasicDetailPaySuccess() {
        mBridgeWebView.callHandler("LifeBasic_paySuccessBack", "" + life_order_id, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }


    /**
     * 生活保障---详情互祝支付
     *
     * @param user_id
     * @param help_comment_content
     * @param pay_way
     * @param life_id
     * @param money
     */
    public void lifepayHelp(String user_id, String help_comment_content, String pay_way, String life_id, int money, int help_number) {
        Log.e("Observable", "" + ExampleApplication.token);
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.BaoZhangPayHelp(user_id,
                help_comment_content, pay_way, life_id, money, help_number, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
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
                        dismissDialog();
                    }
                });
    }

    /**
     * 生活保障---支付成功跳转红包页
     */
    private void lifeSkipSuccess() {
        mBridgeWebView.callHandler("Life_paySuccessBack", "" + life_order_id, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * 生活保障/基础保障---分享成功刷新生活保障详情页
     */
    private void lifeDetailShareRefresh() {
        mBridgeWebView.callHandler("Life_DetailRefresh", "" + life_order_id, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * 生活保障详情---获取生活保障详情数据
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

    /**
     * 志愿者互祝--基础保障详情---获取基础保障详情数据
     *
     * @param life_basic_id
     */
    public void getLifeBasicDetail(String life_basic_id) {
        Observable<EnergyDetailDataBean> observable = Api.getInstance().service.getLifeBasicDetail(UserUtils.getUserId(mContext), life_basic_id,
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

    /**
     * 生活保障详情---互祝分享转发保存次数
     */
    public void sendLifeDetailShareNum() {
        Observable<ResponseBean> observable = null;
        if (shareBackType.equals("lifeDetail")) {
            observable = Api.getInstance().service.sendLifeDetailShareNum(UserUtils.getUserId(mContext), life_repay_id,
                    ExampleApplication.token);
        } else if (shareBackType.equals("LifeBasicDetail")) {
            observable = Api.getInstance().service.sendLifeBasicDetailShareNum(UserUtils.getUserId(mContext), life_repay_id,
                    ExampleApplication.token);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        if (status.equals("400")) {
                        } else if (status.equals("200")) {
                            lifeDetailShareRefresh();
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ResponseBody", "____________________" + throwable.toString());
                    }
                });

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        life_repay_id = "0";
        shareBackType = "";
        super.onDestroy();
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
            int Code = intent.getIntExtra("errCode", 100);
            if (Code == WXPayEntryActivity.PAY_SUCCESS) {
                if (weixinPayBackType.equals("lifeDetailPay")) {
                    lifeSkipSuccess();
                } else if (weixinPayBackType.equals("VoluntePay")) {
                    volunterPaySuccuess();
                } else if (weixinPayBackType.equals("ChangeLeiFengPay")) {
                    ChangeLeiFengSuccuess();
                } else if (weixinPayBackType.equals("LifeBasicApplyPay")) {
                    LifeBasicApplyPaySuccuess();
                } else if (weixinPayBackType.equals("LifeBasicDetailPay")) {
                    LifeBasicDetailPaySuccess();
                }

            } else if (Code == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (Code == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            } else if (Code == BaoZhangActitvty.SHARETYPE_lifeDetailPay) {
                mHandler.sendEmptyMessage(sendLifeDetailShareNum);
            } else if (Code == BaoZhangActitvty.SHARETYPE_LifeBasicDetailPay) {
                mHandler.sendEmptyMessage(sendLifeBasicDetailShareNum);
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


