package com.longcheng.lifecareplan.modular.mine.recovercash.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.PushQueueAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.TiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.recovercash.adapter.ApplyCashAdapter;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountAfterBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountItemBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提现
 */
public class RecoverCashNewActivity extends BaseActivityMVP<RecoverCashContract.View, RecoverCashPresenterImp<RecoverCashContract.View>> implements RecoverCashContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_mywallet)
    TextView tvMywallet;
    @BindView(R.id.tv_mywalletline)
    TextView tvMywalletline;
    @BindView(R.id.tv_chobalance)
    TextView tvChobalance;
    @BindView(R.id.tv_chobalanceline)
    TextView tvChobalanceline;
    @BindView(R.id.tv_allmoney)
    TextView tvAllmoney;
    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieeqi)
    TextView itemTvJieeqi;
    @BindView(R.id.item_tv_time)
    TextView itemTvTime;
    @BindView(R.id.item_tv_money)
    TextView itemTvMoney;
    @BindView(R.id.item_tv_typetitle)
    TextView itemTvTypetitle;
    @BindView(R.id.item_layout_rank)
    LinearLayout itemLayoutRank;
    @BindView(R.id.item_tv_cancel)
    TextView itemTvCancel;
    @BindView(R.id.lv_pushdata)
    MyListView lvPushdata;
    @BindView(R.id.layout_dc)
    LinearLayout layoutDc;
    @BindView(R.id.btn_payforrecord)
    TextView btnPayforrecord;
    @BindView(R.id.btn_withdraw)
    TextView btnWithdraw;
    @BindView(R.id.lv_applycash)
    ListView lv_applycash;

    @BindView(R.id.layout_cho)
    LinearLayout layout_cho;
    @BindView(R.id.relat_wallet)
    RelativeLayout relat_wallet;

    @BindView(R.id.cash_et_money)
    SupplierEditText cashEtMoney;
    @BindView(R.id.cash_tv_showtishi)
    TextView cashTvShowtishi;
    @BindView(R.id.cash_tv_recovercash)
    TextView cashTvRecovercash;
    @BindView(R.id.btn_next)
    TextView btnNext;
    /**
     * 是否允许提现
     */
    private int is_apply_cash;
    private String user_zhufubao_order_id;

    private String user_withdraw_star_level_ratio = "0";//对应星级现金比列
    private String withdraw_ability_ratio = "0";//对应生命能量比列
    private String account_balance = "0";//账户金额
    private String apply_withdrawals_cash = "0";//提现金额（显示在输入框）
    private String ketixian_balance = "0";//可提现金额（apply_withdrawals_cash*user_withdraw_star_level_ratio）
    private String engrytixian_balance = "0";//转换能量的金额（apply_withdrawals_cash*/*withdraw_ability_ratio*/）
    /**
     * 标记是否点击 全额提现  ，避免走输入监听
     */
    private boolean clickallWithdrawStatus = false;
    private String minMoney = "2000";
    /**
     * 是否可以点击 下一步
     */
    boolean clcikbtnNextStatus = false;
    boolean choStauts = false;

    @Override
    public void onClick(View v) {
        Intent intent;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_mywallet:
                choStauts = false;
                changeMenuView();
                break;
            case R.id.tv_chobalance:
                choStauts = true;
                changeMenuView();
                break;
            case R.id.btn_payforrecord:
                intent = new Intent(mActivity, TiXianRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.btn_withdraw:
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", Config.BASE_HEAD_URL + "/home/zhufubao/usercash");
                startActivity(intent);
                break;
            case R.id.item_tv_cancel:
                mPresent.cancelPiPei(user_zhufubao_order_id);
                break;

            case R.id.cash_tv_recovercash:
                allWithdraw();
                break;
            case R.id.btn_next:
                if (clcikbtnNextStatus) {
                    mPresent.doWithdraw(apply_withdrawals_cash);
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
        return R.layout.recovercashnew;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText(getString(R.string.recovercash_tilte));
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvChobalance.setOnClickListener(this);
        itemTvCancel.setOnClickListener(this);
        tvMywallet.setOnClickListener(this);
        btnPayforrecord.setOnClickListener(this);
        btnWithdraw.setOnClickListener(this);
        cashTvRecovercash.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        cashEtMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        btnNext.setBackgroundResource(R.drawable.corners_bg_logingray);
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


    @Override
    public void initDataAfter() {
        changeMenuView();
        mTimeTaskScroll = new TimeTaskScroll(this, lv_applycash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        mPresent.getwalletInfo(UserUtils.getUserId(mContext));
        mPresent.getAccountInfo(UserUtils.getUserId(mContext));
    }

    private void changeMenuView() {
        tvMywallet.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvChobalance.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvMywalletline.setVisibility(View.INVISIBLE);
        tvChobalanceline.setVisibility(View.INVISIBLE);
        if (!choStauts) {
            tvMywalletline.setVisibility(View.VISIBLE);
            tvMywallet.setTextColor(getResources().getColor(R.color.red));
            layout_cho.setVisibility(View.GONE);
            relat_wallet.setVisibility(View.VISIBLE);
        } else {
            tvChobalanceline.setVisibility(View.VISIBLE);
            tvChobalance.setTextColor(getResources().getColor(R.color.red));
            layout_cho.setVisibility(View.VISIBLE);
            relat_wallet.setVisibility(View.GONE);
        }
    }

    @Override
    protected RecoverCashPresenterImp<RecoverCashContract.View> createPresent() {
        return new RecoverCashPresenterImp<>(mContext, this);
    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    TimeTaskScroll mTimeTaskScroll;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeTaskScroll != null) {
            mTimeTaskScroll.cancel();
            mHandler.removeCallbacks(mTimeTaskScroll);
        }
    }

    private Handler mHandler = new Handler();

    public class TimeTaskScroll extends TimerTask {
        private ListView listView;

        public TimeTaskScroll(Context context, ListView listView) {
            this.listView = listView;
        }

        private Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                //  控制速度
                listView.smoothScrollBy(2, 0);
                Log.e("getwalletSuccess", "Message-----");
            }
        };

        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
        }
    }

    ArrayList<AcountItemBean> userApplyCashList;

    @Override
    public void getwalletSuccess(AcountInfoDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            AcountAfterBean mEnergyAfterBean = responseBean.getData();
            tvAllmoney.setText(mEnergyAfterBean.getAllowAsset());
            is_apply_cash = mEnergyAfterBean.getIs_apply_cash();

            showApplyCashView(mEnergyAfterBean);

            showPushView(mEnergyAfterBean);
        }
    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            refreshData();
            ToastUtils.showToast(responseBean.getMsg());
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    private void showApplyCashView(AcountAfterBean mEnergyAfterBean) {
        if (userApplyCashList == null) {
            userApplyCashList = mEnergyAfterBean.getUserApplyCashList();
            if (userApplyCashList != null && userApplyCashList.size() > 0) {
                ApplyCashAdapter mPushQueueAdapter = new ApplyCashAdapter(mContext, userApplyCashList);
                lv_applycash.setAdapter(mPushQueueAdapter);
                //设定任务
                new Timer().schedule(mTimeTaskScroll, 0, 50);
            }
        } else {
            lv_applycash.setSelection(0);
        }
    }

    private void showPushView(AcountAfterBean mEnergyAfterBean) {
        AcountItemBean daichong_order = mEnergyAfterBean.getUserMatchInfo();
        if (daichong_order != null && !TextUtils.isEmpty(daichong_order.getCreate_time())) {
            layoutDc.setVisibility(View.VISIBLE);
            user_zhufubao_order_id = daichong_order.getUser_zhufubao_order_id();
            GlideDownLoadImage.getInstance().loadCircleImage(daichong_order.getCurrent_user_avatar(), itemIvThumb);
            String name = CommonUtil.setName(daichong_order.getCurrent_user_name());
            itemTvName.setText(name);
            itemTvJieeqi.setText(daichong_order.getCurrent_user_jieqi_name());
            itemTvTime.setText(daichong_order.getCreate_time());
            itemTvMoney.setText(daichong_order.getPrice() + "元");

            ArrayList<EnergyItemBean> pushQueueItems = mEnergyAfterBean.getPushQueueItems();
            if (pushQueueItems != null && pushQueueItems.size() > 0) {
                if (mPushQueueAdapter == null) {
                    mPushQueueAdapter = new PushQueueAdapter(mContext, pushQueueItems);
                    lvPushdata.setAdapter(mPushQueueAdapter);
                } else {
                    mPushQueueAdapter.setList(pushQueueItems);
                    mPushQueueAdapter.notifyDataSetChanged();
                }
            } else {
                if (mPushQueueAdapter != null) {
                    mPushQueueAdapter.setList(pushQueueItems);
                    mPushQueueAdapter.notifyDataSetChanged();
                }
            }
        } else {
            layoutDc.setVisibility(View.GONE);
        }
    }

    PushQueueAdapter mPushQueueAdapter;

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
            initView(cashEtMoney.getText().toString());
        }
    }

    @Override
    public void doWithdrawSuccess(ResponseBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            refreshData();
            choStauts = false;
            changeMenuView();
            ToastUtils.showToast(responseBean.getMsg());
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
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
//        getServiceCharge(inputmoney);
//        String afterMoney = PriceUtils.getInstance().gteAddSumPrice(inputmoney, service_charge);
//        if (!PriceUtils.getInstance().compareToPrice(afterMoney, account_balance)) {
//            cashTvRecovercash.setVisibility(View.VISIBLE);
//            cashTvShowtishi.setTextColor(getResources().getColor(R.color.red));
//            cashTvShowtishi.setText("剩余金额不足以支付提现手续费");
//        } else {
        apply_withdrawals_cash = inputmoney;
        showKe();
//        }
    }

    private void showKe() {
        ketixian_balance = PriceUtils.getInstance().gteMultiplySumPrice(apply_withdrawals_cash, user_withdraw_star_level_ratio);
        ketixian_balance = PriceUtils.getInstance().getStrWeiShuTwo(ketixian_balance);
        engrytixian_balance = PriceUtils.getInstance().gteMultiplySumPrice(apply_withdrawals_cash, withdraw_ability_ratio);
        engrytixian_balance = PriceUtils.getInstance().getStrWeiShuTwo(engrytixian_balance);
        //能量向下取整
        String engry = PriceUtils.getInstance().gteMultiplySumPrice(engrytixian_balance, ConstantManager.RECOVERCASH_ENGRY);
        engry = PriceUtils.getInstance().getStrWeiShu0(engry);
        cashTvShowtishi.setText("可转入" + getString(R.string.mark_money) + ketixian_balance + "，"
                + getString(R.string.mark_money) + engrytixian_balance
                + "为您激活" + engry + "生命能量直接发放到您的账户。");
        cashTvShowtishi.setTextColor(getResources().getColor(R.color.text_noclick_color));
        btnNext.setBackgroundResource(R.drawable.corners_bg_login);
        clcikbtnNextStatus = true;
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
//        getServiceCharge(account_balance);
//        if (!PriceUtils.getInstance().compareToPrice(service_charge, account_balance)) {
//            cashTvRecovercash.setVisibility(View.VISIBLE);
//            cashTvShowtishi.setTextColor(getResources().getColor(R.color.red));
//            cashTvShowtishi.setText("剩余金额不足以支付提现手续费");
//            clcikbtnNextStatus = false;
//            return;
//        } else {
        clickallWithdrawStatus = true;
        apply_withdrawals_cash = account_balance;
        cashEtMoney.setText("" + apply_withdrawals_cash);
        cashEtMoney.setSelection(cashEtMoney.getText().length());
        if (!PriceUtils.getInstance().compareToPrice(minMoney, apply_withdrawals_cash)) {
            cashTvShowtishi.setTextColor(getResources().getColor(R.color.red));
            cashTvShowtishi.setText("最低提现金额为" + minMoney + "元");
            clcikbtnNextStatus = false;
            return;
        }
        showKe();
//        }
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
