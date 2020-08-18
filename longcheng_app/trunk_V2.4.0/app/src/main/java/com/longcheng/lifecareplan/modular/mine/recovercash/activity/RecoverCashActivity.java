package com.longcheng.lifecareplan.modular.mine.recovercash.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountAfterBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;

import butterknife.BindView;

/**
 * 提现
 */
public class RecoverCashActivity extends BaseActivityMVP<RecoverCashContract.View, RecoverCashPresenterImp<RecoverCashContract.View>> implements RecoverCashContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.cash_et_money)
    SupplierEditText cashEtMoney;
    @BindView(R.id.cash_tv_showtishi)
    TextView cashTvShowtishi;
    @BindView(R.id.cash_tv_recovercash)
    TextView cashTvRecovercash;
    @BindView(R.id.btn_next)
    TextView btnNext;

    /**
     * 是否可以点击 下一步
     */
    boolean clcikbtnNextStatus = false;

    private String user_withdraw_star_level_ratio = "0";//对应星级现金比列
    private String withdraw_ability_ratio = "0";//对应生命能量比列
    private String account_balance = "0";//账户金额
    private String service_charge = "0";//手续费
    private String apply_withdrawals_cash = "0";//提现金额（显示在输入框）
    private String money = "0";//提现总额（apply_withdrawals_cash+service_charge）
    private String ketixian_balance = "0";//可提现金额（apply_withdrawals_cash*user_withdraw_star_level_ratio）
    private String engrytixian_balance = "0";//转换能量的金额（apply_withdrawals_cash*/*withdraw_ability_ratio*/）

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                doFinish();
                break;
            case R.id.cash_tv_recovercash:
                allWithdraw();
                break;
            case R.id.btn_next:
                if (clcikbtnNextStatus) {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    Intent intent = new Intent(mContext, RecoverCashCommitActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("service_charge", "" + service_charge);
                    intent.putExtra("apply_withdrawals_cash", "" + apply_withdrawals_cash);
                    intent.putExtra("money", "" + money);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.recovercash;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText(getString(R.string.recovercash_tilte));
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        cashEtMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        btnNext.setBackgroundResource(R.drawable.corners_bg_logingray);
        pagetopLayoutLeft.setOnClickListener(this);
        cashTvRecovercash.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        cashEtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence source, int start, int before, int count) {
//                PriceUtils.getInstance().judge(-1,2,cashEtMoney.getText());

                String inputmoney = cashEtMoney.getText().toString();
                if (!TextUtils.isEmpty(inputmoney)) {
                    if (inputmoney.startsWith(".")) {
                        if (inputmoney.length() > 3) {
                            inputmoney = inputmoney.substring(0, 3);
                        }
                        String cont = "0" + inputmoney;
                        cashEtMoney.setText(cont);
                        cashEtMoney.setSelection(cashEtMoney.getText().length());
                    }
                    inputmoney = cashEtMoney.getText().toString();
                    if (inputmoney.contains(".") && !inputmoney.endsWith(".")) {
                        String sd = inputmoney.substring(inputmoney.indexOf(".") + 1);
                        if (sd.length() > 2) {
                            inputmoney = inputmoney.substring(0, inputmoney.indexOf(".") + 3);
                            cashEtMoney.setText(inputmoney);
                            cashEtMoney.setSelection(cashEtMoney.getText().length());
                        }
                    }
                }
                inputmoney = cashEtMoney.getText().toString();
                initView(inputmoney);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //设置字符过滤
        cashEtMoney.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Log.e("setFilters", "source=" + source + "  ;cashEtMoney=" + cashEtMoney.getText().toString() + "  start=" + start + "  end=" + end + "  dstart=" + dstart + "  dend=" + dend);
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if (mlength == 3) {
                        cashEtMoney.setSelection(dest.length());
                        return "";
                    }
                }
                Log.e("setFilters", "source=" + source + "  ;dest=" + dest);
                return null;
            }
        }});
    }

    /**
     * 标记是否点击 全额提现  ，避免走输入监听
     */
    boolean clickallWithdrawStatus = false;
    String minMoney = "2000";
    String minservice_charge = "5.00";
    String Maxservice_charge = "200.00";

    private void initView(String inputmoney) {
        btnNext.setBackgroundResource(R.drawable.corners_bg_logingray);
        if (!TextUtils.isEmpty(inputmoney)) {
            cashTvRecovercash.setVisibility(View.INVISIBLE);
            cashTvShowtishi.setTextColor(getResources().getColor(R.color.red));
            if (!PriceUtils.getInstance().compareToPrice(minMoney, inputmoney)) {
                cashTvShowtishi.setText("最低提现金额为" + minMoney + "元");
            } else if (!PriceUtils.getInstance().compareToPrice(inputmoney, account_balance)) {
                cashTvShowtishi.setText("输入金额超过账户余额");
            } else {
                enterWithdraw(inputmoney);
            }
        } else {
            cashTvRecovercash.setVisibility(View.VISIBLE);
            cashTvShowtishi.setTextColor(getResources().getColor(R.color.text_noclick_color));
            cashTvShowtishi.setText("账户余额" + getString(R.string.mark_money) + account_balance);
        }
    }


    /**
     * 获取手续费
     *
     * @param money
     */
    private void getServiceCharge(String money) {
        service_charge = PriceUtils.getInstance().gteMultiplySumPrice(money, ConstantManager.RECOVERCASH_FEE);
        service_charge = PriceUtils.getInstance().getStrWeiShuTwo(service_charge);
        if (!PriceUtils.getInstance().compareToPrice(minservice_charge, service_charge)) {
            service_charge = minservice_charge;
        }
        if (!PriceUtils.getInstance().compareToPrice(service_charge, Maxservice_charge)) {
            service_charge = Maxservice_charge;
        }
    }

    /**
     * 全部
     */
    private void allWithdraw() {
        cashTvRecovercash.setVisibility(View.INVISIBLE);
        if (Double.valueOf(account_balance) == 0) {
            cashTvRecovercash.setVisibility(View.VISIBLE);
            cashTvShowtishi.setTextColor(getResources().getColor(R.color.red));
            cashTvShowtishi.setText("最低提现金额为" + minMoney + "元");
            cashEtMoney.setText("" + account_balance);
            cashEtMoney.setSelection(cashEtMoney.getText().length());
            clcikbtnNextStatus = false;
            return;
        }
        getServiceCharge(account_balance);
        if (!PriceUtils.getInstance().compareToPrice(service_charge, account_balance)) {
            cashTvRecovercash.setVisibility(View.VISIBLE);
            cashTvShowtishi.setTextColor(getResources().getColor(R.color.red));
            cashTvShowtishi.setText("剩余金额不足以支付提现手续费");
            clcikbtnNextStatus = false;
            return;
        } else {
            clickallWithdrawStatus = true;
            money = account_balance;
            apply_withdrawals_cash = PriceUtils.getInstance().gteSubtractSumPrice(service_charge, account_balance);
            apply_withdrawals_cash = PriceUtils.getInstance().seePrice(apply_withdrawals_cash);
            cashEtMoney.setText("" + apply_withdrawals_cash);
            cashEtMoney.setSelection(cashEtMoney.getText().length());
            if (!PriceUtils.getInstance().compareToPrice(minMoney, apply_withdrawals_cash)) {
                cashTvShowtishi.setTextColor(getResources().getColor(R.color.red));
                cashTvShowtishi.setText("最低提现金额为" + minMoney + "元");
                clcikbtnNextStatus = false;
                return;
            }
            showKe("全部提现所需手续费");
        }
    }

    private void showKe(String cont) {
        ketixian_balance = PriceUtils.getInstance().gteMultiplySumPrice(apply_withdrawals_cash, user_withdraw_star_level_ratio);
        ketixian_balance = PriceUtils.getInstance().getStrWeiShuTwo(ketixian_balance);
        engrytixian_balance = PriceUtils.getInstance().gteMultiplySumPrice(apply_withdrawals_cash, withdraw_ability_ratio);
        engrytixian_balance = PriceUtils.getInstance().getStrWeiShuTwo(engrytixian_balance);
        //能量向下取整
        String engry = PriceUtils.getInstance().gteMultiplySumPrice(engrytixian_balance, ConstantManager.RECOVERCASH_ENGRY);
        engry = PriceUtils.getInstance().getStrWeiShu0(engry);
        cashTvShowtishi.setText("可提现" + getString(R.string.mark_money) + ketixian_balance + "，"
                + getString(R.string.mark_money) + engrytixian_balance
                + "为您激活" + engry + "生命能量直接发放到您的账户。"
                + "\n" + cont + getString(R.string.mark_money) + service_charge);
        cashTvShowtishi.setTextColor(getResources().getColor(R.color.text_noclick_color));
        btnNext.setBackgroundResource(R.drawable.corners_bg_login);
        clcikbtnNextStatus = true;
    }

    /**
     * 输入
     *
     * @param inputmoney
     */
    private void enterWithdraw(String inputmoney) {
        btnNext.setBackgroundResource(R.drawable.corners_bg_logingray);
        clcikbtnNextStatus = false;
        if (clickallWithdrawStatus) {//防止全额提现时更新Editview，循环计算
            clickallWithdrawStatus = false;
            return;
        }
        cashTvRecovercash.setVisibility(View.INVISIBLE);
        getServiceCharge(inputmoney);
        String afterMoney = PriceUtils.getInstance().gteAddSumPrice(inputmoney, service_charge);
        if (!PriceUtils.getInstance().compareToPrice(afterMoney, account_balance)) {
            cashTvRecovercash.setVisibility(View.VISIBLE);
            cashTvShowtishi.setTextColor(getResources().getColor(R.color.red));
            cashTvShowtishi.setText("剩余金额不足以支付提现手续费");
        } else {
            money = afterMoney;
            apply_withdrawals_cash = inputmoney;
            showKe("额外扣除手续费");
        }

    }

    @Override
    public void initDataAfter() {
        mPresent.getAccountInfo(UserUtils.getUserId(mContext));
    }


    @Override
    protected RecoverCashPresenterImp<RecoverCashContract.View> createPresent() {
        return new RecoverCashPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void getwalletSuccess(AcountInfoDataBean responseBean) {

    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {

    }

    @Override
    public void getAcountSuccess(AcountInfoDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            AcountAfterBean mAcountAfterBean = responseBean.getData();
            if (mAcountAfterBean != null) {
                account_balance = mAcountAfterBean.getAccount_balance();
                user_withdraw_star_level_ratio = mAcountAfterBean.getUser_withdraw_star_level_ratio();
                withdraw_ability_ratio = mAcountAfterBean.getWithdraw_ability_ratio();
            }
            initView("");
        }
    }

    @Override
    public void doWithdrawSuccess(ResponseBean responseBean) {

    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {

    }

    @Override
    public void TiXianSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void ListError() {
    }


    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
            doFinish();
        }
        return false;
    }
}
