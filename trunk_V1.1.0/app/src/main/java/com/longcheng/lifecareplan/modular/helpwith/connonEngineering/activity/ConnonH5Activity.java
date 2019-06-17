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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailHelpDialogUtils;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.RedEnvelopeActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.RedEnvelopeKnpActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.ReplyEditPopupUtils;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.EnergyDetailDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.phosphor.RedirectDataBean;
import com.longcheng.lifecareplan.modular.webView.WebAct;
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

import static com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity.BLESSING;

/**
 * 康农
 */

public class ConnonH5Activity extends WebAct {

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
    private String kn_url;

    //康农祝福
    DetailHelpDialogUtils mDetailHelpDialogUtils;
    List<DetailItemBean> mutual_help_money_all;
    private String knp_sharetitle, knp_shareurl, knp_sharePic, knp_sharedesc;
    private int mutual_help_money;
    private DetailItemBean userInfo;
    private List<DetailItemBean> blessings_list;
    private String one_order_id;
    private String msg_id;
    /**
     * 是否可以分享
     */
    boolean shareStatus = false;

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
        pageTopTvName.setText("康农工程");
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
        kn_url = getIntent().getStringExtra("kn_url");
        Log.e("getIntent", "kn_url=[" + kn_url);
        loadUrl(kn_url);
        //康农工程-----获取分享 knp_msg_id
        mBridgeWebView.registerHandler("knp_getKnpMsgId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String url = jsonObject.optString("url", "");
                    String knp_msg_id = jsonObject.optString("knp_msg_id", "");
                    if (pagetopIvRigth != null) {
                        if (!TextUtils.isEmpty(url) && url.contains("/knp/info")) {
                            pagetopIvRigth.setVisibility(View.VISIBLE);
                            shareStatus = true;
                        } else {
                            pagetopIvRigth.setVisibility(View.GONE);
                            shareStatus = false;
                        }
                    }
                    if (!knp_msg_id.equals("0"))
                        getKNPMsgDetail(knp_msg_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //康农工程-----显示祝福弹层
        mBridgeWebView.registerHandler("knp_showHelpDialog", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                msg_id = data;
                Log.e("registerHandler", "data=" + data);
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                    if (mDetailHelpDialogUtils == null) {
                        mDetailHelpDialogUtils = new DetailHelpDialogUtils(mActivity, mHandler, BLESSING);
                    }
                    mDetailHelpDialogUtils.initData(userInfo, blessings_list, 0, mutual_help_money, mutual_help_money_all);
                    mDetailHelpDialogUtils.showPopupWindow();
                }
            }
        });
    }


    public static final int BLESSING = 22;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BLESSING:
                    Bundle bundle = msg.getData();
                    String help_comment_content = bundle.getString("help_comment_content");
                    String payType = bundle.getString("payType");
                    int selectmoney = bundle.getInt("selectmoney");
                    payHelp(UserUtils.getUserId(mContext), help_comment_content, payType, msg_id, selectmoney);
                    break;
            }
        }
    };

    /**
     * 支付
     *
     * @param user_id
     * @param help_comment_content
     * @param pay_way
     * @param msg_id
     * @param money
     */
    public void payHelp(String user_id, String help_comment_content, String pay_way, String msg_id, int money) {
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
                                            helpSkipSuccess();
                                        }

                                        @Override
                                        public void onFailure(String error) {

                                        }
                                    });
                                } else {
                                    helpSkipSuccess();
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

    private void helpSkipSuccess() {
        Intent intent = new Intent(mActivity, RedEnvelopeKnpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("one_order_id", one_order_id);
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageLoginIntentAnim(intent, mActivity);
    }

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

    private void knpPaySuccessBack() {
        //康农工程-----刷新康农详情页返回列表
        mBridgeWebView.callHandler("knp_paySuccessBack", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    private void knpPaySucRreDetail() {
        //康农工程-----刷新康农详情页
        mBridgeWebView.callHandler("knp_paySucRreDetail", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    public static final int knpPaySuccessBack = 11;
    public static final int knpPaySucRreDetail = 22;
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
            } else if (errCode == knpPaySuccessBack) {
                knpPaySuccessBack();
            } else if (errCode == knpPaySucRreDetail) {
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
