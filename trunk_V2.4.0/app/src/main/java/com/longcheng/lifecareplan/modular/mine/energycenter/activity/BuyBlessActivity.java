package com.longcheng.lifecareplan.modular.mine.energycenter.activity;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuItemBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import butterknife.BindView;

/**
 * 能量中心--购买祝福宝
 */
public class BuyBlessActivity extends BaseListActivity<EnergyCenterContract.View, EnergyCenterPresenterImp<EnergyCenterContract.View>> implements EnergyCenterContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_wxtitle)
    TextView tvWxtitle;
    @BindView(R.id.iv_wxselect)
    ImageView ivWxselect;
    @BindView(R.id.relat_wx)
    RelativeLayout relatWx;
    @BindView(R.id.tv_bmtitle)
    TextView tvBmtitle;
    @BindView(R.id.activat_iv_bmselect)
    ImageView activatIvBmselect;
    @BindView(R.id.relat_bm)
    RelativeLayout relatBm;
    @BindView(R.id.tv_cardtitle)
    TextView tvCardtitle;
    @BindView(R.id.iv_cardselect)
    ImageView ivCardselect;
    @BindView(R.id.relat_card)
    RelativeLayout relatCard;
    @BindView(R.id.tv_moneytitle)
    TextView tvMoneytitle;
    @BindView(R.id.tv_blessmoney)
    TextView tvBlessmoney;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.et_acount)
    TextView etAcount;
    @BindView(R.id.tv_acountcopy)
    TextView tvAcountcopy;
    @BindView(R.id.et_cardnum)
    TextView etCardnum;
    @BindView(R.id.tv_cardnumcopy)
    TextView tvCardnumcopy;
    @BindView(R.id.et_bank)
    TextView etBank;
    @BindView(R.id.tv_bankcopy)
    TextView tvBankcopy;
    @BindView(R.id.layout_card)
    LinearLayout layoutCard;
    @BindView(R.id.btn_buy)
    TextView btnBuy;


    private int pay_source = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.relat_wx:
                pay_source = 1;
                selectPayType();
                break;
            case R.id.relat_bm:
                pay_source = 2;
                selectPayType();
                break;
            case R.id.relat_card:
                pay_source = 3;
                selectPayType();
                break;
            case R.id.tv_acountcopy:
                PriceUtils.getInstance().copy(mActivity, etAcount.getText().toString());
                break;
            case R.id.tv_cardnumcopy:
                PriceUtils.getInstance().copy(mActivity, etCardnum.getText().toString());
                break;
            case R.id.tv_bankcopy:
                PriceUtils.getInstance().copy(mActivity, etBank.getText().toString());
                break;
            case R.id.btn_buy:
                String price = etMoney.getText().toString();
                if (!TextUtils.isEmpty(price)) {
                    if (pay_source == 1) {
                        mPresent.buyZhufubaoByWeixin(pay_source, price);
                    } else {
                        mPresent.buyZhufubaoByCash(pay_source, price);
                    }

                } else {
                    ToastUtils.showToast("请输入祝福宝数量");
                }
                break;

        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.energycenter_buybless;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("购买祝福宝");
        pagetopLayoutLeft.setOnClickListener(this);
        relatWx.setOnClickListener(this);
        relatBm.setOnClickListener(this);
        relatCard.setOnClickListener(this);
        tvAcountcopy.setOnClickListener(this);
        tvCardnumcopy.setOnClickListener(this);
        tvBankcopy.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etMoney, 9);
    }


    @Override
    public void initDataAfter() {
        selectPayType();
        mPresent.componyBankInfo();
    }

    private void selectPayType() {
        tvWxtitle.setTextColor(getResources().getColor(R.color.text_contents_color));
        tvBmtitle.setTextColor(getResources().getColor(R.color.text_contents_color));
        tvCardtitle.setTextColor(getResources().getColor(R.color.text_contents_color));
        ivWxselect.setVisibility(View.GONE);
        activatIvBmselect.setVisibility(View.GONE);
        ivCardselect.setVisibility(View.GONE);
        relatWx.setBackgroundResource(R.drawable.corners_bg_graybian);
        relatBm.setBackgroundResource(R.drawable.corners_bg_graybian);
        relatCard.setBackgroundResource(R.drawable.corners_bg_graybian);
        layoutCard.setVisibility(View.GONE);
        tvBlessmoney.setVisibility(View.GONE);
        etMoney.setVisibility(View.VISIBLE);
        btnBuy.setVisibility(View.VISIBLE);
        tvMoneytitle.setText("请输入购买数量");
        if (pay_source == 1) {
            tvWxtitle.setTextColor(getResources().getColor(R.color.red));
            ivWxselect.setVisibility(View.VISIBLE);
            relatWx.setBackgroundResource(R.drawable.corners_bg_redbian);
        } else if (pay_source == 2) {
            tvBmtitle.setTextColor(getResources().getColor(R.color.red));
            activatIvBmselect.setVisibility(View.VISIBLE);
            relatBm.setBackgroundResource(R.drawable.corners_bg_redbian);
            tvBlessmoney.setVisibility(View.VISIBLE);
        } else if (pay_source == 3) {
            tvCardtitle.setTextColor(getResources().getColor(R.color.red));
            ivCardselect.setVisibility(View.VISIBLE);
            relatCard.setBackgroundResource(R.drawable.corners_bg_redbian);
            tvMoneytitle.setText("请复制下方银行信息并进行转账");
            layoutCard.setVisibility(View.VISIBLE);
            etMoney.setVisibility(View.GONE);
            btnBuy.setVisibility(View.GONE);
        }
    }


    @Override
    protected EnergyCenterPresenterImp<EnergyCenterContract.View> createPresent() {
        return new EnergyCenterPresenterImp<>(mActivity, this);
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
    public void ListSuccess(DaiFuDataBean responseBean, int backPage) {
    }

    @Override
    public void RefuseSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void editAvatarSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getData());
            doFinish();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void backBankInfoSuccess(DaiFuDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            DaiFuItemBean bankInfo = responseBean.getData().getBankInfo();
            etAcount.setText(bankInfo.getAccount_name());
            etBank.setText(bankInfo.getBank_name() + " " + bankInfo.getBranch_name());
            etCardnum.setText(bankInfo.getBank_no());
            String zhufubao_monry = responseBean.getData().getZhufubao_monry();
            tvBlessmoney.setText("祝福金余额：" + zhufubao_monry);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void BuySuccess(PayWXDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            PayWXAfterBean payWeChatBean = responseBean.getData();
            PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }


    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }


    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
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

    /**
     * 微信支付回调广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);
            if (errCode == WXPayEntryActivity.PAY_SUCCESS) {
                doFinish();
            } else if (errCode == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (errCode == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            }
        }
    };
}
