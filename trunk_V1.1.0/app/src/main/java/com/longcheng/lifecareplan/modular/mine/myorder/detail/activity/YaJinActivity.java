package com.longcheng.lifecareplan.modular.mine.myorder.detail.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.bill.activity.EngryRecordActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.OrderListActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.bean.DetailDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 押金
 */
public class YaJinActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_moneyunit)
    TextView tv_moneyunit;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.paywx_iv_icon)
    ImageView paywxIvIcon;
    @BindView(R.id.activat_iv_wxselect)
    ImageView activatIvWxselect;
    @BindView(R.id.activat_relat_wx)
    RelativeLayout activatRelatWx;
    @BindView(R.id.activat_tv_account)
    TextView activatTvAccount;
    @BindView(R.id.activat_iv_accountselect)
    ImageView activatIvAccountselect;
    @BindView(R.id.activat_relat_account)
    RelativeLayout activatRelatAccount;
    @BindView(R.id.tv_zfbtitle)
    TextView tvZfbtitle;
    @BindView(R.id.detailhelp_iv_zfbselect)
    ImageView detailhelpIvZfbselect;
    @BindView(R.id.detailhelp_relat_zfb)
    RelativeLayout detailhelpRelatZfb;
    @BindView(R.id.btn_pay)
    TextView btnPay;
    @BindView(R.id.tv_type)
    TextView tv_type;

    /**
     * 订单类型	1商城订单 2 生命能量订单 3 生活方式互祝订单 4 康农
     */
    int type;
    public String order_id;
    String asset;
    int deposit;
    /**
     * 支付方式激活类型
     */
    String payWay = "wxpay";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.activat_relat_wx:
                payWay = "wxpay";
                selectPayTypeView();
                break;
            case R.id.activat_relat_account:
                payWay = "asset";
                selectPayTypeView();
                break;
            case R.id.detailhelp_relat_zfb:
                payWay = "alipay";
                selectPayTypeView();
                break;
            case R.id.btn_pay:
                if (!TextUtils.isEmpty(asset))
                    yaJinPay(UserUtils.getUserId(mContext));
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.my_order_yajin;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("支付押金");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        activatRelatWx.setOnClickListener(this);
        detailhelpRelatZfb.setOnClickListener(this);
        activatRelatAccount.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        type = intent.getIntExtra("type", 0);
        getYaJinPayInfo();
    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    /**
     * 获取支付信息
     */
    public void getYaJinPayInfo() {
        showDialog();
        Observable<DetailDataBean> observable;
        if (type == 3) {
            activatRelatAccount.setVisibility(View.GONE);
            observable = Api.getInstance().service.getYaJinLifeStylePayInfo(UserUtils.getUserId(mContext),
                    order_id, ExampleApplication.token);
        } else {
            activatRelatAccount.setVisibility(View.VISIBLE);
            observable = Api.getInstance().service.getYaJinPayInfo(UserUtils.getUserId(mContext),
                    order_id, ExampleApplication.token);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<DetailDataBean>() {
                    @Override
                    public void accept(DetailDataBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("400")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status.equals("200")) {
                            DetailAfterBean mDetailAfterBean = responseBean.getData();
                            showView(mDetailAfterBean);
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(R.string.net_tishi);
                        dismissDialog();
                    }
                });
    }

    private void showView(DetailAfterBean mDetailAfterBean) {
        tv_moneyunit.setVisibility(View.VISIBLE);
        btnPay.setBackgroundResource(R.drawable.corners_bg_login);
        asset = mDetailAfterBean.getUser_asset();
        deposit = mDetailAfterBean.getDeposit();
        GlideDownLoadImage.getInstance().loadCircleHeadImageCenter(mContext, mDetailAfterBean.getAvatar(), ivHead);
        tvName.setText("" + mDetailAfterBean.getUser_name());
        tvMoney.setText("" + mDetailAfterBean.getDeposit_str());
        activatTvAccount.setText(getString(R.string.mark_money) + asset);
        /**
         * 押金类型  1CHO  2坐堂医 3志愿者
         */
        int deposit_type = mDetailAfterBean.getDeposit_type();
        if (deposit_type == 1) {
            tv_type.setText("CHO");
        } else if (deposit_type == 2) {
            tv_type.setText("坐堂医");
        } else if (deposit_type == 3) {
            tv_type.setText("志愿者");
        }

    }

    private void selectPayTypeView() {
        activatIvWxselect.setVisibility(View.GONE);
        activatRelatWx.setBackgroundResource(R.drawable.corners_bg_black);
        activatIvAccountselect.setVisibility(View.GONE);
        activatRelatAccount.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelpIvZfbselect.setVisibility(View.GONE);
        detailhelpRelatZfb.setBackgroundResource(R.drawable.corners_bg_black);
        if (payWay.equals("wxpay")) {
            activatIvWxselect.setVisibility(View.VISIBLE);
            activatRelatWx.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatWx.setPadding(0, 0, 0, 0);
        } else if (payWay.equals("asset")) {
            activatIvAccountselect.setVisibility(View.VISIBLE);
            activatRelatAccount.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatAccount.setPadding(0, 0, 0, 0);
        } else if (payWay.equals("alipay")) {
            detailhelpIvZfbselect.setVisibility(View.VISIBLE);
            detailhelpRelatZfb.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelpRelatZfb.setPadding(0, 0, 0, 0);
        }
    }


    public void yaJinPay(String user_id) {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable;
        if (type == 3) {
            String pay_source = "2";
            observable = Api.getInstance().service.yaJinPayLifeStyle(user_id, order_id, deposit, payWay, pay_source, ExampleApplication.token);
        } else {
            observable = Api.getInstance().service.yaJinPay(user_id, order_id, deposit, payWay, ExampleApplication.token);
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
                                if (deposit == 0 || payWay.equals("asset")) {
                                    ToastUtils.showToast(responseBean.getMsg());
                                    jihuoSuccess();
                                } else if (payWay.equals("wxpay")) {
                                    PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                                } else if (payWay.equals("alipay")) {
                                    String payInfo = payWeChatBean.getPayInfo();
                                    PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            jihuoSuccess();
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
                        dismissDialog();
                    }
                });

    }

    /**
     * 刷新订单列表
     */
    private void jihuoSuccess() {
        OrderListActivity.editOrderStatus = true;
        doFinish();

    }

    @Override
    protected void onDestroy() {
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

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);

            if (errCode == WXPayEntryActivity.PAY_SUCCESS) {
                jihuoSuccess();
            } else if (errCode == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (errCode == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            }
        }
    };

    /**
     * 重写onkeydown 用于监听返回键
     */

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }
}
