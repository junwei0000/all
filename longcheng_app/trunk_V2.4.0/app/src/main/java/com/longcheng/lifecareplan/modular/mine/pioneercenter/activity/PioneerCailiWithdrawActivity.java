package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerCounterDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.UserBankDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 24节气创业中心--财礼提现
 */
public class PioneerCailiWithdrawActivity extends BaseActivityMVP<PioneerContract.View, PioneerPresenterImp<PioneerContract.View>> implements PioneerContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_allmoney)
    TextView tvAllmoney;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_bankname)
    TextView tvBankname;
    @BindView(R.id.tv_change)
    TextView tvChange;
    @BindView(R.id.tv_bankno)
    TextView tvBankno;
    @BindView(R.id.layout_card)
    LinearLayout layoutCard;
    @BindView(R.id.tv_zfsrecord)
    TextView tvZfsrecord;
    @BindView(R.id.tv_jihuo)
    TextView tvJihuo;

    String knp_team_bind_card_id;
    boolean haveCard = true;
    String rebate = "0";

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.layout_card:
                if (!haveCard) {
                    intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" +
                            MineFragment.ka_url);
                    startActivity(intent);
                }
                break;
            case R.id.tv_change:
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" +
                        MineFragment.ka_url);
                startActivity(intent);
                break;
            case R.id.tv_zfsrecord:
                intent = new Intent(mActivity, PionCailiRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.tv_jihuo:
                String money = etMoney.getText().toString();
                if (clickStatus) {
                    if (Double.parseDouble(rebate) < Double.parseDouble(money)) {
                        ToastUtils.showToast("您的财礼余额不足");
                        break;
                    }
                    if (!TextUtils.isEmpty(money) && Double.parseDouble(money) < 1000) {
                        ToastUtils.showToast("提现金额1000起");
                        break;
                    }
                    mPresent.doRebateWithdraw(knp_team_bind_card_id, money);
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
        return R.layout.pioneer_cadiliwithdraw;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBarDark(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("我的财礼");
        pagetopLayoutLeft.setOnClickListener(this);
        layoutCard.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvZfsrecord.setOnClickListener(this);
        tvJihuo.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etMoney, 9);
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //还原数据
                if (etMoney != null) {
                    setBtn();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void initDataAfter() {
        setBtn();
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
            PioneerCounterDataBean.PionCounterItemBean info = responseBean.getData();
            if (info != null) {
                rebate = info.getRebate();
                tvAllmoney.setText(rebate);
            }
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void paySuccess(PayWXDataBean responseBean) {

    }

    @Override
    public void SellFQBSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status.equals("200")) {
            doFinish();
        }
    }

    @Override
    public void applyMoneyListSuccess(PioneerDataListBean responseBean) {

    }

    boolean clickStatus = false;

    private void setBtn() {
        String moneyCont = etMoney.getText().toString();
        if (haveCard && !TextUtils.isEmpty(moneyCont)) {
            tvJihuo.setBackgroundColor(getResources().getColor(R.color.red));
            clickStatus = true;
        } else {
            tvJihuo.setBackgroundColor(getResources().getColor(R.color.gray));
            clickStatus = false;
        }
    }

    @Override
    public void backBankInfoSuccess(UserBankDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            UserBankDataBean.BankItem Knp_team_bind_card = responseBean.getData();
            if (Knp_team_bind_card != null && !TextUtils.isEmpty(Knp_team_bind_card.getBank_no())) {
                haveCard = true;
                knp_team_bind_card_id = Knp_team_bind_card.getKnp_team_bind_card_id();
            } else {
                haveCard = false;
            }
            setBtn();
            if (haveCard) {
                tvChange.setVisibility(View.VISIBLE);
                layoutCard.setBackgroundResource(R.drawable.corners_bg_red);
                ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, layoutCard, R.color.red);
                ivImg.setBackgroundResource(R.mipmap.my_ty_icon);
                tvBankname.setText(Knp_team_bind_card.getBank_name() + " (" + CommonUtil.setName(Knp_team_bind_card.getCardholder_name()) + ")");
                tvBankname.setTextColor(getResources().getColor(R.color.white));
                String showno = CommonUtil.setBankNo(Knp_team_bind_card.getBank_no());
                tvBankno.setText(showno);
                tvBankno.setVisibility(View.VISIBLE);
                layoutCard.setGravity(Gravity.CENTER_HORIZONTAL);
            } else {
                tvChange.setVisibility(View.GONE);
                layoutCard.setGravity(Gravity.CENTER);
                layoutCard.setBackgroundResource(R.drawable.corners_bg_goodgray);
                ivImg.setBackgroundResource(R.mipmap.my_zhufushi_add1);
                tvBankname.setText("未绑定银行卡");
                tvBankname.setTextColor(getResources().getColor(R.color.text_noclick_color));
                tvBankno.setVisibility(View.GONE);
            }
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
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


    @Override
    protected void onResume() {
        super.onResume();
        mPresent.getCailiInfo();
        mPresent.getUserBankInfo();
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
