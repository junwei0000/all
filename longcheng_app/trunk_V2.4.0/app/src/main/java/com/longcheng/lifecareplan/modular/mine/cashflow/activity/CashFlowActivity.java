package com.longcheng.lifecareplan.modular.mine.cashflow.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.mine.cashflow.bean.CashFlowAfterBean;
import com.longcheng.lifecareplan.modular.mine.cashflow.bean.CashFlowDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.CalenderSelectNowUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 流水
 */
public class CashFlowActivity extends BaseActivityMVP<CashFlowContract.View, CashFlowPresenterImp<CashFlowContract.View>> implements CashFlowContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.tv_allgetmoney)
    TextView tvAllgetmoney;
    @BindView(R.id.tv_zfbmoney)
    TextView tvZfbmoney;
    @BindView(R.id.tv_zfbdetail)
    TextView tvZfbdetail;
    @BindView(R.id.tv_rewardmoney)
    TextView tvRewardmoney;
    @BindView(R.id.tv_rewarddetail)
    TextView tvRewarddetail;
    @BindView(R.id.tv_xufeimoney)
    TextView tvXufeimoney;
    @BindView(R.id.tv_xufeidetail)
    TextView tvXufeidetail;
    @BindView(R.id.tv_alloutmoney)
    TextView tvAlloutmoney;
    @BindView(R.id.tv_buyzfbmoney)
    TextView tvBuyzfbmoney;
    @BindView(R.id.tv_buyzfbdetail)
    TextView tvBuyzfbdetail;
    @BindView(R.id.tv_emergencymoney)
    TextView tvEmergencymoney;
    @BindView(R.id.tv_emergencydetail)
    TextView tvEmergencydetail;
    @BindView(R.id.tv_cailimoney)
    TextView tvCailimoney;
    @BindView(R.id.tv_cailidetail)
    TextView tvCailidetail;
    @BindView(R.id.tv_alldaichongmoney)
    TextView tvAlldaichongmoney;
    @BindView(R.id.tv_daichongbmoney)
    TextView tvDaichongbmoney;
    @BindView(R.id.tv_daichongdetail)
    TextView tvDaichongdetail;
    @BindView(R.id.tv_alldaifumoney)
    TextView tvAlldaifumoney;
    @BindView(R.id.tv_daifubmoney)
    TextView tvDaifubmoney;
    @BindView(R.id.tv_daifudetail)
    TextView tvDaifudetail;

    CalenderSelectNowUtils mCalenderSelectUtils;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                if (mCalenderSelectUtils == null) {
                    mCalenderSelectUtils = new CalenderSelectNowUtils(mActivity, mHandler, SELECTDATE);
                }
                mCalenderSelectUtils.initCurrentItem(showDate);
                mCalenderSelectUtils.showDialog();
                break;
            case R.id.tv_zfbdetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "购买祝福宝");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.tv_rewarddetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "打赏");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.tv_xufeidetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "能量中心续费");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.tv_buyzfbdetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "回购祝福宝");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 4);
                startActivity(intent);
                break;
            case R.id.tv_emergencydetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "救急转账");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 5);
                startActivity(intent);
                break;
            case R.id.tv_cailidetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "财礼提现");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 6);
                startActivity(intent);
                break;
            case R.id.tv_daichongdetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "代充");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 7);
                startActivity(intent);
                break;
            case R.id.tv_daifudetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "代付");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 8);
                startActivity(intent);
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.mine_cashflow;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("流水");
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        tvZfbdetail.setOnClickListener(this);
        tvRewarddetail.setOnClickListener(this);
        tvXufeidetail.setOnClickListener(this);
        tvBuyzfbdetail.setOnClickListener(this);
        tvEmergencydetail.setOnClickListener(this);
        tvCailidetail.setOnClickListener(this);
        tvDaichongdetail.setOnClickListener(this);
        tvDaifudetail.setOnClickListener(this);
        pagetopIvRigth.setBackgroundResource(R.mipmap.my_liushui_icon);
    }

    String showDate;
    TimeTaskScroll mTimeTaskScroll;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeTaskScroll != null) {
            mTimeTaskScroll.cancel();
            mHandler.removeCallbacks(mTimeTaskScroll);
        }
    }


    public class TimeTaskScroll extends TimerTask {


        private Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                refreshStatus = true;
                getStatisticsInfo(showDate);
            }
        };

        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
        }
    }

    @Override
    public void initDataAfter() {
        showDate = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
        getStatisticsInfo(showDate);
        //设定任务
        mTimeTaskScroll = new TimeTaskScroll();
        new Timer().schedule(mTimeTaskScroll, 0, 15 * 1000);
    }

    private void getStatisticsInfo(String date) {
        mPresent.userStatisticsInfo(date);
    }

    private final int update = 1;
    private final int SELECTDATE = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case update:
                    if (pageTopTvName != null) {
                        String date = (String) msg.obj;
//                        pageTopTvName.setText(date + "");
                    }
                    break;
                case SELECTDATE:
                    String year = msg.getData().getString("year");
                    String month = msg.getData().getString("month");
                    String day = msg.getData().getString("day");
                    String selectDate = year + month + day;
                    showDate = DatesUtils.getInstance().getDateGeShi(selectDate, "yyyy年MM月dd日", "yyyy-MM-dd");
                    getStatisticsInfo(showDate);
                    Log.e(TAG, "  " + showDate);
                    break;
            }
        }
    };

    @Override
    protected CashFlowPresenterImp<CashFlowContract.View> createPresent() {
        return new CashFlowPresenterImp<>(mActivity, this);
    }

    boolean refreshStatus = false;

    @Override
    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }

    @Override
    public void ListSuccess(CashFlowDataBean responseBean, int backPage) {
    }

    @Override
    public void userStatisticsInfo(CashFlowDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            CashFlowAfterBean info = responseBean.getData();
            tvAllgetmoney.setText("" + info.getInflow());
            tvZfbmoney.setText("" + info.getByZhufubao());
            tvRewardmoney.setText("" + info.getReward());
            tvXufeimoney.setText("" + info.getRenew());
            tvAlloutmoney.setText("" + info.getFlowOut());
            tvBuyzfbmoney.setText("" + info.getRecoveryZhufubao());
            tvEmergencymoney.setText("" + info.getEmergency());
            tvCailimoney.setText("" + info.getBetrothalGifts());
            tvAlldaichongmoney.setText("" + info.getSubCharge());
            tvDaichongbmoney.setText("" + info.getSubCharge());
            tvAlldaifumoney.setText("" + info.getSubpay());
            tvDaifubmoney.setText("" + info.getSubpay());
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

}
