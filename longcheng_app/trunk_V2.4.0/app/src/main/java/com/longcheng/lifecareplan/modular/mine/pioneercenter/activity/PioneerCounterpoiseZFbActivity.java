package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerCounterDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.UserBankDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import butterknife.BindView;

/**
 * 24节气创业中心--账户平衡 购买祝佑宝
 */
public class PioneerCounterpoiseZFbActivity extends BaseActivityMVP<PioneerContract.View, PioneerPresenterImp<PioneerContract.View>> implements PioneerContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_zfballmoney)
    TextView tvZfballmoney;
    @BindView(R.id.btn_buy)
    TextView btnBuy;
    private String zhufubao_balance_num = "0";
    private int payType = 1;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.btn_buy:
                mPresent.counterpoiseBuyZFb(zhufubao_balance_num, payType);
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_counterpoise_buyzfb;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBarDark(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("请购祝佑宝");
        pagetopLayoutLeft.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        zhufubao_balance_num = getIntent().getStringExtra("zhufubao_balance_num");
        tvZfballmoney.setText(zhufubao_balance_num);
    }


    @Override
    protected PioneerPresenterImp<PioneerContract.View> createPresent() {
        return new PioneerPresenterImp<>(mActivity, this);
    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void MenuInfoSuccess(PioneerDataBean responseBean) {

    }

    @Override
    public void CounterInfoSuccess(PioneerCounterDataBean responseBean) {
    }

    @Override
    public void paySuccess(PayWXDataBean responseBean) {
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

    @Override
    public void SellFQBSuccess(ResponseBean responseBean) {

    }

    @Override
    public void applyMoneyListSuccess(PioneerDataListBean responseBean) {

    }

    @Override
    public void backBankInfoSuccess(UserBankDataBean responseBean) {

    }

    @Override
    public void ListSuccess(PionOpenSetRecordDataBean responseBean, int pageback) {

    }

    @Override
    public void CZListSuccess(PioneerDataBean responseBean, int pageback) {

    }

    private void applyPaySuccuess() {
        ToastUtils.showToast("请购祝佑宝成功");
        doFinish();
    }

    @Override
    public void Error() {
        ToastUtils.showToast(R.string.net_tishi);
    }


    @Override
    protected void onResume() {
        super.onResume();
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

    private void back() {
        doFinish();
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
