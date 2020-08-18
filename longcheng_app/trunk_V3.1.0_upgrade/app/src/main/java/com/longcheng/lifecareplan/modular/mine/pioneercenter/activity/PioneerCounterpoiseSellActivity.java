package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.bean.PionDaiFuDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
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
import butterknife.ButterKnife;

/**
 * 24节气创业中心--账户平衡 出售福祺宝
 */
public class PioneerCounterpoiseSellActivity extends BaseActivityMVP<PioneerContract.View, PioneerPresenterImp<PioneerContract.View>> implements PioneerContract.View {


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
    @BindView(R.id.tv_usrename)
    EditText tvUsrename;
    @BindView(R.id.tv_backname)
    EditText tvBackname;
    @BindView(R.id.tv_zhiname)
    EditText tvZhiname;
    @BindView(R.id.tv_cardnum)
    EditText tvCardnum;
    private String fuqibao_balance_num = "0";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.btn_buy:
                String bank_user_name = tvUsrename.getText().toString();
                String bank_name = tvBackname.getText().toString();
                String bank_branch = tvZhiname.getText().toString();
                String bank_account = tvCardnum.getText().toString();
                if (TextUtils.isEmpty(bank_user_name)) {
                    ToastUtils.showToast("请输入开户名");
                    break;
                }
                if (TextUtils.isEmpty(bank_name)) {
                    ToastUtils.showToast("请输入开户行");
                    break;
                }
                if (TextUtils.isEmpty(bank_branch)) {
                    ToastUtils.showToast("请输入开户行支行");
                    break;
                }
                if (TextUtils.isEmpty(bank_account)) {
                    ToastUtils.showToast("请输入银行卡号");
                    break;
                }
                mPresent.counterpoiseSellFQB(fuqibao_balance_num, bank_user_name, bank_name, bank_branch, bank_account);
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_counterpoise_sell;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("敬售福祺宝");
        pagetopLayoutLeft.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(tvUsrename, 20);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(tvBackname, 20);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(tvZhiname, 20);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(tvCardnum, 40);
    }


    @Override
    public void initDataAfter() {
        fuqibao_balance_num = getIntent().getStringExtra("fuqibao_balance_num");
        tvZfballmoney.setText(fuqibao_balance_num);
        mPresent.getSellBankInfo();
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
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            PioneerCounterDataBean.PionCounterItemBean mPionCounterItemBean = responseBean.getData();
            if (mPionCounterItemBean != null && !TextUtils.isEmpty(mPionCounterItemBean.getBank_account())) {
                tvUsrename.setText(mPionCounterItemBean.getBank_user_name());
                tvBackname.setText(mPionCounterItemBean.getBank_name());
                tvZhiname.setText(mPionCounterItemBean.getBank_branch());
                tvCardnum.setText(mPionCounterItemBean.getBank_account());
            }

        }
    }

    @Override
    public void paySuccess(PayWXDataBean responseBean) {

    }

    @Override
    public void SellFQBSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            doFinish();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
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


    @Override
    public void Error() {
        ToastUtils.showToast(R.string.net_tishi);
    }


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
