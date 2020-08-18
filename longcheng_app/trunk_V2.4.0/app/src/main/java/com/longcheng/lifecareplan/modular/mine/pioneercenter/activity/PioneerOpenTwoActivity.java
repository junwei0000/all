package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 24节气创业中心---开通
 */
public class PioneerOpenTwoActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.btn_open)
    TextView btn_open;
    @BindView(R.id.iv_price1)
    LinearLayout ivPrice1;
    @BindView(R.id.iv_price2)
    LinearLayout ivPrice2;
    @BindView(R.id.iv_price3)
    LinearLayout ivPrice3;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.activat_iv_wxselect)
    ImageView activatIvWxselect;
    @BindView(R.id.activat_relat_wx)
    RelativeLayout activatRelatWx;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.activat_iv_cardselect)
    ImageView activatIvCardselect;
    @BindView(R.id.activat_relat_card)
    RelativeLayout activatRelatCard;
    private int payType = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.activat_relat_wx:
                payType = 1;
                selectPayTypeView();
                break;
            case R.id.activat_relat_card:
//                payType = 2;
//                selectPayTypeView();
                ToastUtils.showToast("功能开发中");
                break;
            case R.id.btn_open:
                applyPay();
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_opentwo;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("开通24节气创业中心");
        pagetopLayoutLeft.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        activatRelatWx.setOnClickListener(this);
        activatRelatCard.setOnClickListener(this);
        int width = (DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 55)) / 3;
        ivPrice1.setLayoutParams(new FrameLayout.LayoutParams(width, (int) (width * 1.273)));

        ivPrice2.setLayoutParams(new FrameLayout.LayoutParams(width, (int) (width * 1.273)));
        ivPrice3.setLayoutParams(new FrameLayout.LayoutParams(width, (int) (width * 1.273)));
    }


    @Override
    public void initDataAfter() {
        selectPayTypeView();
    }

    /**
     * 切换充值渠道
     */
    private void selectPayTypeView() {
        tvWx.setTextColor(getResources().getColor(R.color.text_contents_color));
        tvCard.setTextColor(getResources().getColor(R.color.text_contents_color));
        activatRelatWx.setBackgroundResource(R.drawable.corners_bg_graybian);
        activatIvWxselect.setVisibility(View.GONE);
        activatRelatCard.setBackgroundResource(R.drawable.corners_bg_graybian);
        activatIvCardselect.setVisibility(View.GONE);
        if (payType == 1) {
            tvWx.setTextColor(getResources().getColor(R.color.red));
            activatIvWxselect.setVisibility(View.VISIBLE);
            activatRelatWx.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatWx.setPadding(0, 0, 0, 0);
        } else if (payType == 2) {
            tvCard.setTextColor(getResources().getColor(R.color.red));
            activatIvCardselect.setVisibility(View.VISIBLE);
            activatRelatCard.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatCard.setPadding(0, 0, 0, 0);
        }
    }

    public void showDialog() {
        RequestDataStatus = true;
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        RequestDataStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }

    private void applyPay() {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.PionopenPay(UserUtils.getUserId(mContext),
                "10000", payType, "2", ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {

                            String status = responseBean.getStatus();
                            if (status.equals("200")) {
                                PayWXAfterBean payWeChatBean = responseBean.getData();
                                if (payType == 1) {
                                    Log.e(TAG, payWeChatBean.toString());
                                    PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                                } else if (payType == 2) {
                                    String payInfo = payWeChatBean.getPayInfo();
                                    PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            applyPaySuccuess();
                                        }

                                        @Override
                                        public void onFailure(String error) {

                                        }
                                    });
                                } else {
                                    applyPaySuccuess();
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

    private void applyPaySuccuess() {
        ToastUtils.showToast("开通成功");
        Intent intent = new Intent(mContext, PioneerMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        PioneerOpenActivity.mPioneerOpenActivity.doFinish();
        doFinish();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
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
            int Code = intent.getIntExtra("errCode", 100);
            if (Code == WXPayEntryActivity.PAY_SUCCESS) {
                applyPaySuccuess();
            } else if (Code == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (Code == WXPayEntryActivity.PAY_CANCLE) {
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

