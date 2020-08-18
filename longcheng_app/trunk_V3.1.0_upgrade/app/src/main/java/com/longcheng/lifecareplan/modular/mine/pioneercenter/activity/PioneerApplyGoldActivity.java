package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
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
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 24节气创业中心---申请创业金
 */
public class PioneerApplyGoldActivity extends BaseActivityMVP<PioneerContract.View, PioneerPresenterImp<PioneerContract.View>> implements PioneerContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_bankname)
    TextView tvBankname;
    @BindView(R.id.tv_bankno)
    TextView tvBankno;
    @BindView(R.id.layout_card)
    LinearLayout layoutCard;
    @BindView(R.id.btn_open)
    TextView btnOpen;
    @BindView(R.id.tv_change)
    TextView tv_change;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;

    String knp_team_bind_card_id;
    boolean haveCard = true;
    String applyMoney = "0.00";


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                intent = new Intent(mActivity, PioneerApplyGoldRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
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
            case R.id.btn_open:
                if (haveCard && (!TextUtils.isEmpty(applyMoney) && Double.valueOf(applyMoney) > 0)) {
                    mPresent.saveApplyEntrepreneursMoney(knp_team_bind_card_id, applyMoney);
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
        return R.layout.pioneer_applygold;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBarDark(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("申请创业金");
        pageTopTvRigth.setText("历史记录");
        pageTopTvRigth.setVisibility(View.VISIBLE);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        layoutCard.setOnClickListener(this);
        tv_change.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.applyMoneyInfo();
        mPresent.getUserBankInfo();
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
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            applyMoney = responseBean.getData().getApplyMoney();
            tv_money.setText(applyMoney);
            changeBtn();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void CounterInfoSuccess(PioneerCounterDataBean responseBean) {

    }

    @Override
    public void paySuccess(PayWXDataBean responseBean) {

    }

    @Override
    public void SellFQBSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            Intent intent = new Intent(mActivity, PioneerApplyGoldverifyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
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
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            UserBankDataBean.BankItem Knp_team_bind_card = responseBean.getData();
            if (Knp_team_bind_card != null && !TextUtils.isEmpty(Knp_team_bind_card.getBank_no())) {
                haveCard = true;
                knp_team_bind_card_id = Knp_team_bind_card.getKnp_team_bind_card_id();
            } else {
                haveCard = false;
            }
            if (haveCard) {
                tv_change.setVisibility(View.VISIBLE);
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
                tv_change.setVisibility(View.GONE);
                layoutCard.setGravity(Gravity.CENTER);
                layoutCard.setBackgroundResource(R.drawable.corners_bg_goodgray);
                ivImg.setBackgroundResource(R.mipmap.my_zhufushi_add1);
                tvBankname.setText("绑定银行卡");
                tvBankname.setTextColor(getResources().getColor(R.color.text_noclick_color));
                tvBankno.setVisibility(View.GONE);
            }
            changeBtn();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    private void changeBtn() {
        if (haveCard && (!TextUtils.isEmpty(applyMoney) && Double.valueOf(applyMoney) > 0)) {
            btnOpen.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            btnOpen.setBackgroundColor(getResources().getColor(R.color.gray));
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
