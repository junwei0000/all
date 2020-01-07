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
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.RedEnvelopeKnpActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.EnergyDetailDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
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

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 生活保障/志愿者
 * Created by Burning on 2018/9/11.
 */
@SuppressLint("CheckResult")
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
    private KangNDialogUtils mknpDetailHelpDialogUtils;
    private VolunterDialogUtils mVolunterDialogUtils;
    private VolunterDialogUtils mChangeLFDialogUtils;
    private BaoZhangDialogUtils mLifeBasicPayDialogUtils;
    private BaoZhangDialogUtils mDetailHelpDialogUtils;
    private VolunterDialogUtils mGiveactivityDialogUtils;
    private VolunterDialogUtils mDoctorDialogUtils;

    private List<DetailItemBean> blessings_list;
    private List<DetailItemBean> mutual_help_money_all;
    private String knp_sharetitle, knp_shareurl, knp_sharePic, knp_sharedesc;
    private String life_id, is_share_help, msg_id;
    private String one_order_id = "";
    private int mutual_help_money;
    private DetailItemBean userInfo;
    private String asset_debt;
    private int is_super_ability;//是否是超级生命能量的卷 1 不使用余额支付
    /**
     * 生活保障详情id
     */
    public static String life_repay_id = "0";
    /**
     * 生活保障分享
     */
    public static int SHARETYPE_lifeDetailPay = 1000;
    public static int SHARETYPE_LifeBasicDetailPay = 1001;
    public static int SHARETYPE_KNPDetailPay = 1002;
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
    private String weixinPayBackType = "";

    private String Voluntepay_money = "", ChangLFMoney = "";

    private String volunteer_debt_item_id = "0", type = "1";
    String Givemoney = "", Giveaddress_id = "", __app_pay_token__ = "";

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
                Log.e("registerHandler", "knp_shareurl=" + knp_shareurl);
                if (shareStatus && !TextUtils.isEmpty(knp_shareurl)) {
                    mShareUtils.setShare(knp_sharedesc, knp_sharePic,R.mipmap.share_rqz, knp_shareurl, knp_sharetitle);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("Observable", "onNewINtent执行了");
        setIntent(intent);
        String url = getIntent().getStringExtra("html_url");
        loadUrl(url);
    }

    @Override
    public void initDataAfter() {
        super.initDataAfter();
        String url = getIntent().getStringExtra("html_url");
        loadUrl(url);
        firstComIn = false;
        //详情--获取分享 knp_msg_id
        mBridgeWebView.registerHandler("knp_getKnpMsgId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String url = jsonObject.optString("url", "");
                    life_repay_id = jsonObject.optString("knp_msg_id", "0");
                    if (pagetopIvRigth != null) {
                        pagetopIvRigth.setVisibility(View.VISIBLE);
                        shareStatus = true;
                        if (!TextUtils.isEmpty(url) && url.contains("/knp/info")) {
                            shareBackType = "knpDetail";
                        } else if (!TextUtils.isEmpty(url) && url.contains("/home/life/repayinfo")) {
                            shareBackType = "lifeDetail";
                        } else if (!TextUtils.isEmpty(url) && url.contains("/lifebasic/info")) {
                            shareBackType = "LifeBasicDetail";
                        } else if (!TextUtils.isEmpty(url) && url.contains("/giveactivity/index")) {
                            shareBackType = "GiveactivityApply";
                        } else if (!TextUtils.isEmpty(url) && url.contains("/knpteam/roominfo")) {
                            shareBackType = "knpteamroominfo";
                        } else if (!TextUtils.isEmpty(url) && url.contains("home/mybook/newYearMybook")) {
                            shareBackType = "newYearMybook";
                            knp_sharetitle = UserUtils.getUserName(mContext) + "的2019年度账单";
                            knp_sharedesc = "人生最大的意义，莫过于让生命能量流动起来，祝福更多的人。";
                            knp_sharePic = "";
                            knp_shareurl = url;
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
                        } else if (route.equals("knp_info")) {
                            shareBackType = "knpDetail";
                        }
                    }
                    if (!life_repay_id.equals("0")) {
                        if (shareBackType.equals("knpDetail")) {
                            getKNPMsgDetail(life_repay_id);
                        } else if (shareBackType.equals("lifeDetail")) {
                            getLifeDetail(life_repay_id);
                        } else if (shareBackType.equals("LifeBasicDetail")) {
                            getLifeBasicDetail(life_repay_id);
                        } else if (shareBackType.equals("GiveactivityApply")) {
                            getGiveactivityInfo();
                        } else if (shareBackType.equals("knpteamroominfo")) {
                            getKnpTeamRoomDetail(life_repay_id);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //坐堂医-----押金支付
        mBridgeWebView.registerHandler("toVolunDoctor_pay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                weixinPayBackType = "DoctorPay";
                if (mDoctorDialogUtils == null) {
                    mDoctorDialogUtils = new VolunterDialogUtils(mActivity, mHandler, DoctorSelectPay);
                }
                mDoctorDialogUtils.showPopupWindow(data, "立即支付");
            }
        });
        //康农详情----显示祝福弹层
        mBridgeWebView.registerHandler("knp_showHelpDialog", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                JSONObject jsonObject = null;
                int is_normal_help = 1;
                try {
                    jsonObject = new JSONObject(data);
                    msg_id = jsonObject.optString("knp_msg_id", "0");
                    is_normal_help = jsonObject.optInt("is_normal_help", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                weixinPayBackType = "knpDetailPay";
                Log.e("registerHandler", "data=" + data);
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                    if (mknpDetailHelpDialogUtils == null) {
                        mknpDetailHelpDialogUtils = new KangNDialogUtils(mActivity, mHandler, KNPBLESS);
                    }
                    mknpDetailHelpDialogUtils.initData(userInfo, blessings_list, 0, mutual_help_money, mutual_help_money_all);
                    mknpDetailHelpDialogUtils.setIs_normal_help(is_normal_help);
                    mknpDetailHelpDialogUtils.showPopupWindow();
                } else {
                    ToastUtils.showToast(R.string.net_tishi);
                }
            }
        });
        //天下无债详情--显示祝福支付
        mBridgeWebView.registerHandler("Life_AppPayment", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                life_id = data;
                weixinPayBackType = "lifeDetailPay";
                Log.e("registerHandler", "data=" + data);
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                    if (mDetailHelpDialogUtils == null) {
                        mDetailHelpDialogUtils = new BaoZhangDialogUtils(mActivity, mHandler, LIFEBLESSING);
                    }
                    mDetailHelpDialogUtils.initData(blessings_list, mutual_help_money_all, asset_debt);
                    mDetailHelpDialogUtils.showPopupWindow();
                } else {
                    ToastUtils.showToast(R.string.net_tishi);
                }
            }
        });
        //志愿者互祝---生活保障详情--显示支付
        mBridgeWebView.registerHandler("LifeBasic_AppPayment", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                weixinPayBackType = "LifeBasicDetailPay";
                Log.e("registerHandler", "data=" + data + "  is_super_ability==" + is_super_ability);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                    life_id = jsonObject.optString("life_basic_id", "0");
                    is_share_help = jsonObject.optString("is_share_help", "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                    if (mLifeBasicPayDialogUtils == null) {
                        mLifeBasicPayDialogUtils = new BaoZhangDialogUtils(mActivity, mHandler, LifeBasicAppPayment);
                    }
                    mLifeBasicPayDialogUtils.initData(blessings_list, mutual_help_money_all, asset_debt);
                    mLifeBasicPayDialogUtils.setIs_super_ability(is_super_ability);
                    mLifeBasicPayDialogUtils.showPopupWindow();
                } else {
                    ToastUtils.showToast(R.string.net_tishi);
                }
            }
        });
        //首页--饮品免费领（邮费支付）---显示支付弹层
        mBridgeWebView.registerHandler("GiveactivityApply_applyPay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                weixinPayBackType = "GiveactivityApplyPay";
                Log.e("registerHandler", "GiveactivityApply_applyPay =" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    Givemoney = jsonObject.optString("money", "");
                    Giveaddress_id = jsonObject.optString("address_id", "");
                    __app_pay_token__ = jsonObject.optString("__app_pay_token__", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mGiveactivityDialogUtils == null) {
                    mGiveactivityDialogUtils = new VolunterDialogUtils(mActivity, mHandler, GiveactivityApplyPay);
                }
                mGiveactivityDialogUtils.showPopupWindow(Givemoney, "立即支付");
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

        //志愿者互祝---申请生活保障支付
        mBridgeWebView.registerHandler("LifeBasic_applyPay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "Life_PayChangeLeiFeng=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String payment_channel = jsonObject.optString("payment_channel", "");
                    weixinPayBackType = "LifeBasicApplyPay";
                    LifeBasicApplyPay(data, payment_channel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //天下无债---广告行动申请
        mBridgeWebView.registerHandler("LifeRepayInfo_GG", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "LifeRepayInfo_GG=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String action_id = jsonObject.optString("action_id", "0");
                    String life_ad_main = jsonObject.optString("life_ad_main", "0");
                    String life_ad_minor = jsonObject.optString("life_ad_minor", "0");
                    String life_repay_id = jsonObject.optString("life_repay_id", "0");
                    String life_comment_id = jsonObject.optString("life_comment_id", "0");
                    Intent intent = new Intent(mContext, ApplyHelpActivity.class);
                    intent.putExtra("life_ad_main", "" + life_ad_main);
                    intent.putExtra("life_ad_minor", "" + life_ad_minor);
                    intent.putExtra("life_repay_id", "" + life_repay_id);
                    intent.putExtra("life_comment_id", "" + life_comment_id);
                    intent.putExtra("action_goods_id", "" + action_id);
                    intent.putExtra("skiptype", ConstantManager.skipType_LifeRepayInfo_GG);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //康农工程创建房间---互祝支付
        mBridgeWebView.registerHandler("allNewPay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "allNewPay=" + data);
                weixinPayBackType = "allNewPay";
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String payment_channel = jsonObject.optString("payment_channel", "");
                    allNewPay(data, payment_channel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static final int knpPaySuccessBack = 11;
    public static final int knpPaySucRreDetail = 12;
    private static final int KNPBLESS = 20;
    private static final int LIFEBLESSING = 22;
    private static final int LIFEBLESSINGAsset = 23;
    private static final int VolunterSelectPay = 33;
    private static final int DoctorSelectPay = 34;
    private static final int sendLifeDetailShareNum = 44;
    private static final int ChangeLeiFengPay = 55;
    public static final int LifeBasicAppPayment = 66;
    public static final int LifeBasicAppPaymentAsset = 67;
    private static final int sendLifeBasicDetailShareNum = 77;
    private static final int sendKNPDetailShareNum = 78;
    private static final int GiveactivityApplyPay = 88;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        Bundle bundle;
        String help_comment_content;
        String payType;
        int selectmoney;
        int help_number;

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KNPBLESS:
                    bundle = msg.getData();
                    help_comment_content = bundle.getString("help_comment_content");
                    payType = bundle.getString("payType");
                    selectmoney = bundle.getInt("selectmoney");
                    knpPayHelp(UserUtils.getUserId(mContext), help_comment_content, payType, msg_id, selectmoney);
                    break;
                case LIFEBLESSING:
                    bundle = msg.getData();
                    help_comment_content = bundle.getString("help_comment_content");
                    payType = bundle.getString("payType");
                    selectmoney = bundle.getInt("selectmoney");
                    help_number = bundle.getInt("help_number");
                    if (!TextUtils.isEmpty(payType) && payType.equals("4")) {
                        mDetailHelpDialogUtils.showDialogAsset(help_comment_content, payType, selectmoney, help_number, mHandler, LIFEBLESSINGAsset);
                    } else {
                        lifePayHelp(UserUtils.getUserId(mContext), help_comment_content, payType, life_id, selectmoney, help_number);
                    }
                    break;
                case LIFEBLESSINGAsset:
                    bundle = msg.getData();
                    help_comment_content = bundle.getString("help_comment_content");
                    payType = bundle.getString("payType");
                    selectmoney = bundle.getInt("selectmoney");
                    help_number = bundle.getInt("help_number");
                    lifePayHelp(UserUtils.getUserId(mContext), help_comment_content, payType, life_id, selectmoney, help_number);
                    break;
                case LifeBasicAppPayment:
                    bundle = msg.getData();
                    help_comment_content = bundle.getString("help_comment_content");
                    payType = bundle.getString("payType");
                    selectmoney = bundle.getInt("selectmoney");
                    help_number = bundle.getInt("help_number");
                    if (!TextUtils.isEmpty(payType) && payType.equals("4")) {
                        mLifeBasicPayDialogUtils.showDialogAsset(help_comment_content, payType, selectmoney, help_number, mHandler, LifeBasicAppPaymentAsset);
                    } else {
                        LifeBasicDetailPay(UserUtils.getUserId(mContext), help_comment_content, payType, life_id, selectmoney, help_number, is_share_help);
                    }
                    break;
                case LifeBasicAppPaymentAsset:
                    bundle = msg.getData();
                    help_comment_content = bundle.getString("help_comment_content");
                    payType = bundle.getString("payType");
                    selectmoney = bundle.getInt("selectmoney");
                    help_number = bundle.getInt("help_number");
                    LifeBasicDetailPay(UserUtils.getUserId(mContext), help_comment_content, payType, life_id, selectmoney, help_number, is_share_help);
                    break;
                case VolunterSelectPay:
                    bundle = msg.getData();
                    payType = bundle.getString("payType");
                    VoluntePay(payType, Voluntepay_money, volunteer_debt_item_id);
                    break;
                case DoctorSelectPay:
                    bundle = msg.getData();
                    String payTypes = bundle.getString("payType");
                    if (payTypes.equals("1")) {
                        payTypes = "wxpay";
                    } else if (payTypes.equals("2")) {
                        payTypes = "alipay";
                    }
                    doctorPay(payTypes);
                    break;
                case ChangeLeiFengPay:
                    bundle = msg.getData();
                    payType = bundle.getString("payType");
                    ChangeLeiFengPay(payType, ChangLFMoney);
                    break;
                case GiveactivityApplyPay:
                    bundle = msg.getData();
                    payType = bundle.getString("payType");
                    GiveactivityApplyPay(payType);
                    break;
                case sendLifeDetailShareNum:
                    sendLifeDetailShareNum();
                    break;
                case sendLifeBasicDetailShareNum:
                    sendLifeDetailShareNum();
                    break;
                case sendKNPDetailShareNum:
                    sendLifeDetailShareNum();
                    break;


            }
        }
    };

    /**
     * 康农工程创建房间---互祝支付
     *
     * @param json_datas
     */
    private void allNewPay(String json_datas, String payment_channel) {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.allNewPay(UserUtils.getUserId(mContext),
                json_datas, ExampleApplication.token);
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
                                one_order_id = payWeChatBean.getOne_order_id();
                                if (payment_channel.equals("1")) {
                                    Log.e(TAG, payWeChatBean.toString());
                                    PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                                } else if (payment_channel.equals("2")) {
                                    String payInfo = payWeChatBean.getPayInfo();
                                    PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            allNewPaySuccess();
                                        }

                                        @Override
                                        public void onFailure(String error) {

                                        }
                                    });
                                }
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
                                allNewPayRefresh(status);
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
     * 康农工程创建房间---互祝支付
     */
    private void allNewPaySuccess() {
        if (mBridgeWebView != null) {
            mBridgeWebView.callHandler("allNewPay_SucBack", "" + one_order_id, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        }
    }

    /**
     * 康农工程创建房间---刷新页面 401
     *
     * @param status
     */
    private void allNewPayRefresh(String status) {
        mBridgeWebView.callHandler("allNewPay_refreshPage", "" + status, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * 坐堂医-----押金支付
     *
     * @param payment_channel
     */
    private void doctorPay(String payment_channel) {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.doctorPay(UserUtils.getUserId(mContext),
                payment_channel, ExampleApplication.token);
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
                                if (payment_channel.equals("wxpay")) {
                                    Log.e(TAG, payWeChatBean.toString());
                                    PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                                } else if (payment_channel.equals("alipay")) {
                                    String payInfo = payWeChatBean.getPayInfo();
                                    PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            doctorPaySuccess();
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

    /**
     * 坐堂医-----押金支付成功回调
     */
    private void doctorPaySuccess() {
        MineFragment.showDoctorDialogStatus = true;
        Intent intents = new Intent();
        intents.setAction(ConstantManager.MAINMENU_ACTION);
        intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_CENTER);
        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
        ActivityManager.getScreenManager().popAllActivityOnlyMain();
    }

    boolean firstComIn = true;

    /**
     * 成为坐堂医--回来刷新任务完成进度
     */
    private void setDoctorPageBackRefresh() {
        if (!firstComIn) {
            mBridgeWebView.callHandler("doctor_taskReload", "", new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        }
    }
    //--------------------------------------------------------------------------------------------------------

    /**
     * 康农详情--互祝支付
     *
     * @param user_id
     * @param help_comment_content
     * @param pay_way
     * @param msg_id
     * @param money
     */
    public void knpPayHelp(String user_id, String help_comment_content, String pay_way, String msg_id, int money) {
        Log.e("Observable", "" + ExampleApplication.token);
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.KNPPayHelp(user_id,
                help_comment_content, pay_way, msg_id, money, ExampleApplication.token);
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
                                one_order_id = payWeChatBean.getOne_order_id();
                                if (pay_way.equals("wxpay")) {
                                    Log.e(TAG, payWeChatBean.toString());
                                    PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                                } else if (pay_way.equals("alipay")) {
                                    String payInfo = payWeChatBean.getPayInfo();
                                    PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            knpPayHelpSkipSuccess();
                                        }

                                        @Override
                                        public void onFailure(String error) {

                                        }
                                    });
                                } else {
                                    knpPayHelpSkipSuccess();
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

    /**
     * 跳转红包页
     */
    private void knpPayHelpSkipSuccess() {
        Intent intent = new Intent(mActivity, RedEnvelopeKnpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("one_order_id", one_order_id);
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageLoginIntentAnim(intent, mActivity);
    }

    /**
     * 康农工程-----刷新康农详情页返回列表
     */
    private void knpPaySuccessBack() {
        mBridgeWebView.callHandler("knp_paySuccessBack", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * 康农工程-----刷新康农详情页
     */
    private void knpPaySucRreDetail() {
        mBridgeWebView.callHandler("knp_paySucRreDetail", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * 康农工程---分享成功刷新生活保障详情页
     */
    private void knpDetailShareRefresh() {
        mBridgeWebView.callHandler("knp_DetailRefresh", "" + one_order_id, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }
    //--------------------------------------------------------------------------------------------------------

    /**
     * 首页--饮品免费领（邮费支付）
     */
    private void GiveactivityApplyPay(String payment_channel) {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        String pay_source = "2";
        Observable<PayWXDataBean> observable = Api.getInstance().service.GiveactivityApplyPay(UserUtils.getUserId(mContext),
                payment_channel, pay_source, Giveaddress_id, Givemoney, __app_pay_token__, ExampleApplication.token);
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
                                if (payment_channel.equals("1")) {
                                    Log.e(TAG, payWeChatBean.toString());
                                    PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                                } else if (payment_channel.equals("2")) {
                                    String payInfo = payWeChatBean.getPayInfo();
                                    PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            GiveactivityPaySuccuess();
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

    /**
     * 首页--饮品免费领（邮费支付） 完刷新页面
     */
    private void GiveactivityPaySuccuess() {
        mBridgeWebView.callHandler("GiveactivityApply_applyPaySucBack", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }
//--------------------------------------------------------------------------------------------------------

    /**
     * 志愿者互祝---申请生活保障支付
     */

    private void LifeBasicApplyPay(String json_datas, String payment_channel) {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.LifeBasicApplyPay(UserUtils.getUserId(mContext),
                json_datas, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {

                            String status = responseBean.getStatus();
                            if (status.equals("200")) {
                                PayWXAfterBean payWeChatBean = (PayWXAfterBean) responseBean.getData();
                                one_order_id = payWeChatBean.getOne_order_id();
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
                                } else {
                                    LifeBasicApplyPaySuccuess();
                                    ToastUtils.showToast(responseBean.getMsg());
                                }
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
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
     * 志愿者互祝----申请生活保障支付回调
     */
    private void LifeBasicApplyPaySuccuess() {
        mBridgeWebView.callHandler("LifeBasic_applyPaySucBack", "" + one_order_id, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * 志愿者互祝---变更雷锋号支付
     */
    private void ChangeLeiFengPay(String payment_channel, String price) {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.ChangeLeiFengPay(UserUtils.getUserId(mContext),
                payment_channel, price, ExampleApplication.token);
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
                                } else {
                                    ChangeLeiFengSuccuess();
                                    ToastUtils.showToast(responseBean.getMsg());
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
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.VoluntePay(UserUtils.getUserId(mContext),
                payment_channel, type, pay_money, volunteer_debt_item_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        showDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {

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
                                } else {
                                    volunterPaySuccuess();
                                    ToastUtils.showToast(responseBean.getMsg());
                                }
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

    //--------------------------------------------------------------------------------------------------------


    /**
     * 志愿者互祝--生活保障详情互祝支付
     *
     * @param user_id
     * @param help_comment_content
     * @param pay_way
     * @param life_basic_id
     * @param money
     */
    public void LifeBasicDetailPay(String user_id, String help_comment_content,
                                   String pay_way, String life_basic_id, int money,
                                   int help_number, String is_share_help) {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        String pay_source = "2";//1：微信 2：安卓 3：ios
        Observable<PayWXDataBean> observable = Api.getInstance().service.LifeBasicDetailPay(user_id,
                help_comment_content, pay_way, life_basic_id, money, help_number, is_share_help, pay_source, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {

                            String status = responseBean.getStatus();
                            if (status.equals("200")) {
                                PayWXAfterBean payWeChatBean = (PayWXAfterBean) responseBean.getData();
                                one_order_id = payWeChatBean.getOne_order_id();
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
                                } else {
                                    LifeBasicDetailPaySuccess();
                                    ToastUtils.showToast(responseBean.getMsg());
                                }
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
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
     * 志愿者互祝--生活保障详情支付成功跳转红包页
     */
    private void LifeBasicDetailPaySuccess() {
        mBridgeWebView.callHandler("LifeBasic_paySuccessBack", "" + one_order_id, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }
//--------------------------------------------------------------------------------------------------------

    /**
     * 天下无债---详情互祝支付
     *
     * @param user_id
     * @param help_comment_content
     * @param pay_way
     * @param life_id
     * @param money
     */
    public void lifePayHelp(String user_id, String help_comment_content, String pay_way, String life_id, int money, int help_number) {
        Log.e("Observable", "" + ExampleApplication.token);
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.BaoZhangPayHelp(user_id,
                help_comment_content, pay_way, life_id, money, help_number, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            String status = responseBean.getStatus();
                            if (status.equals("200")) {
                                PayWXAfterBean payWeChatBean = (PayWXAfterBean) responseBean.getData();
                                one_order_id = payWeChatBean.getOne_order_id();
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
                                    ToastUtils.showToast(responseBean.getMsg());
                                }
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
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
     * 天下无债---支付成功跳转红包页
     */
    private void lifeSkipSuccess() {
        if (mBridgeWebView == null) {
            return;
        }
        mBridgeWebView.callHandler("Life_paySuccessBack", "" + one_order_id, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    /**
     * 天下无债/生活保障---分享成功刷新生活保障详情页
     */
    private void lifeDetailShareRefresh() {
        mBridgeWebView.callHandler("Life_DetailRefresh", "" + one_order_id, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }
//--------------------------------------------------------------------------------------------------------

    /**
     * 获取康农详情数据
     *
     * @param knp_msg_id
     */
    public void getKNPMsgDetail(String knp_msg_id) {
        Observable<EnergyDetailDataBean> observable = Api.getInstance().service.getKNPMsgDetail(UserUtils.getUserId(mContext), knp_msg_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EnergyDetailDataBean>() {
                    @Override
                    public void accept(EnergyDetailDataBean responseBean) throws Exception {
                        String status = responseBean.getStatus();
                        if (status.equals("400")) {
                        } else if (status.equals("200")) {
                            //http://test.t.asdyf.com/api/v1_0_0/help/lj_payment?id=1350&user_id=942&token=7e3ddf48a199421e37d17b57c7d66a1c
                            DetailAfterBean mDetailAfterBean = (DetailAfterBean) responseBean.getData();
                            if (mDetailAfterBean != null) {
                                knp_sharetitle = mDetailAfterBean.getKnp_sharetitle();
                                knp_shareurl = mDetailAfterBean.getKnp_shareurl();
                                knp_sharePic = mDetailAfterBean.getKnp_sharePic();
                                knp_sharedesc = mDetailAfterBean.getKnp_sharedesc();
                                blessings_list = mDetailAfterBean.getKnp_blessings_list();
                                DetailItemBean knp_helpmoneydefault = mDetailAfterBean.getKnp_helpmoneydefault();
                                mutual_help_money = knp_helpmoneydefault.getMoney();
                                userInfo = mDetailAfterBean.getUser_info();
                                mutual_help_money_all = mDetailAfterBean.getMutual_help_money_all();
                                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                                } else {
                                    mutual_help_money_all = new ArrayList<>();
                                }
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                        qiMingSkipHelp("0");
                    }
                });

    }

    /**
     * 康农房间详情---获取详情数据
     *
     * @param life_repay_id
     */
    public void getKnpTeamRoomDetail(String life_repay_id) {
        Observable<EnergyDetailDataBean> observable = Api.getInstance().service.getKnpTeamRoomDetail(UserUtils.getUserId(mContext), life_repay_id,
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
     * 天下无债详情---获取天下无债详情数据
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
                                asset_debt = mDetailAfterBean.getUser_info().getAsset_debt();
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
     * 志愿者互祝--生活保障详情---获取生活保障详情数据
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
                                is_super_ability = mDetailAfterBean.getIs_super_ability();
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
                                asset_debt = mDetailAfterBean.getUser_info().getAsset_debt();
                                Log.e("ResponseBody", "____________________" + mutual_help_money_all.size());
                            }
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ResponseBody", "____________________" + throwable.toString());
                    }
                });

    }

    /**
     * 首页---志愿者赠送商品活动分享API
     */
    public void getGiveactivityInfo() {
        Observable<EnergyDetailDataBean> observable = Api.getInstance().service.getGiveactivityInfo(UserUtils.getUserId(mContext),
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
        } else if (shareBackType.equals("knpDetail")) {
            observable = Api.getInstance().service.sendKNPDetailShareNum(UserUtils.getUserId(mContext), life_repay_id,
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
                            if (shareBackType.equals("knpDetail")) {
                                knpDetailShareRefresh();
                            } else {
                                lifeDetailShareRefresh();
                            }

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
    protected void onResume() {
        super.onResume();
        setDoctorPageBackRefresh();
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
            Log.e("registerHandler", "weixinPayBackType=" + weixinPayBackType + "  " + one_order_id);
            if (Code == WXPayEntryActivity.PAY_SUCCESS) {
                if (weixinPayBackType.equals("knpDetailPay")) {
                    knpPayHelpSkipSuccess();
                } else if (weixinPayBackType.equals("lifeDetailPay")) {
                    lifeSkipSuccess();
                } else if (weixinPayBackType.equals("VoluntePay")) {
                    volunterPaySuccuess();
                } else if (weixinPayBackType.equals("ChangeLeiFengPay")) {
                    ChangeLeiFengSuccuess();
                } else if (weixinPayBackType.equals("LifeBasicApplyPay")) {
                    LifeBasicApplyPaySuccuess();
                } else if (weixinPayBackType.equals("LifeBasicDetailPay")) {
                    LifeBasicDetailPaySuccess();
                } else if (weixinPayBackType.equals("GiveactivityApplyPay")) {
                    GiveactivityPaySuccuess();
                } else if (weixinPayBackType.equals("DoctorPay")) {
                    doctorPaySuccess();
                } else if (weixinPayBackType.equals("allNewPay")) {
                    allNewPaySuccess();
                }

            } else if (Code == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (Code == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            } else if (Code == BaoZhangActitvty.SHARETYPE_lifeDetailPay) {
                mHandler.sendEmptyMessage(sendLifeDetailShareNum);
            } else if (Code == BaoZhangActitvty.SHARETYPE_LifeBasicDetailPay) {
                mHandler.sendEmptyMessage(sendLifeBasicDetailShareNum);
            } else if (Code == BaoZhangActitvty.SHARETYPE_KNPDetailPay) {
                mHandler.sendEmptyMessage(sendKNPDetailShareNum);
            } else if (Code == knpPaySuccessBack) {
                knpPaySuccessBack();
            } else if (Code == knpPaySucRreDetail) {
                knpPaySucRreDetail();
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


