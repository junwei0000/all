package com.longcheng.lifecareplan.modular.mine.cashflow.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.tv_pionxufeititle)
    TextView tvPionxufeititle;
    @BindView(R.id.tv_pionxufeimoney)
    TextView tvPionxufeimoney;
    @BindView(R.id.tv_pionxufeidetail)
    TextView tvPionxufeidetail;
    @BindView(R.id.tv_zybtitle)
    TextView tvZybtitle;
    @BindView(R.id.tv_zybmoney)
    TextView tvZybmoney;
    @BindView(R.id.tv_zybdetail)
    TextView tvZybdetail;
    @BindView(R.id.tv_pionopentitle)
    TextView tvPionopentitle;
    @BindView(R.id.tv_pionopenmoney)
    TextView tvPionopenmoney;
    @BindView(R.id.tv_pionopendetail)
    TextView tvPionopendetail;
    @BindView(R.id.tv_pionupgradetitle)
    TextView tvPionupgradetitle;
    @BindView(R.id.tv_pionupgrademoney)
    TextView tvPionupgrademoney;
    @BindView(R.id.tv_pionupgradedetail)
    TextView tvPionupgradedetail;
    @BindView(R.id.tv_alloutmoney)
    TextView tvAlloutmoney;
    @BindView(R.id.tv_pionfqbtitle)
    TextView tvPionfqbtitle;
    @BindView(R.id.tv_pionfqbmoney)
    TextView tvPionfqbmoney;
    @BindView(R.id.tv_pionfqbdetail)
    TextView tvPionfqbdetail;
    @BindView(R.id.tv_emergencytitle)
    TextView tvEmergencytitle;
    @BindView(R.id.tv_emergencymoney)
    TextView tvEmergencymoney;
    @BindView(R.id.tv_emergencydetail)
    TextView tvEmergencydetail;
    @BindView(R.id.tv_cailititle)
    TextView tvCailititle;
    @BindView(R.id.tv_cailimoney)
    TextView tvCailimoney;
    @BindView(R.id.tv_cailidetail)
    TextView tvCailidetail;
    @BindView(R.id.tv_pionbackmoneytitle)
    TextView tvPionbackmoneytitle;
    @BindView(R.id.tv_pionbackmoneymoney)
    TextView tvPionbackmoneymoney;
    @BindView(R.id.tv_pionbackmoneydetail)
    TextView tvPionbackmoneydetail;

    private CalenderSelectNowUtils mCalenderSelectUtils;

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
            case R.id.tv_pionxufeidetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "创业中心续费");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.tv_zybdetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "祝佑宝平衡");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.tv_pionopendetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "开通创业中心");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.tv_pionupgradedetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "创业中心升级");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 4);
                startActivity(intent);
                break;
            case R.id.tv_pionfqbdetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "回购福祺宝");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 5);
                startActivity(intent);
                break;
            case R.id.tv_emergencydetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "救急金转账");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 6);
                startActivity(intent);
                break;
            case R.id.tv_cailidetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "财礼提现");
                intent.putExtra("date", showDate);
                intent.putExtra("type", 7);
                startActivity(intent);
                break;
            case R.id.tv_pionbackmoneydetail:
                intent = new Intent(mActivity, FlowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "创业金打款");
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
        tvPionxufeidetail.setOnClickListener(this);
        tvZybdetail.setOnClickListener(this);
        tvPionopendetail.setOnClickListener(this);
        tvPionupgradedetail.setOnClickListener(this);
        tvPionfqbdetail.setOnClickListener(this);
        tvEmergencydetail.setOnClickListener(this);
        tvCailidetail.setOnClickListener(this);
        tvPionbackmoneydetail.setOnClickListener(this);
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
            tvPionxufeimoney.setText("" + info.getEntreRenew());
            tvZybmoney.setText("" + info.getZhuyoubaoBalance());
            tvPionopenmoney.setText("" + info.getOpenEntre());
            tvPionupgrademoney.setText("" + info.getUpgradeEntre());

            tvAlloutmoney.setText("" + info.getFlowOut());
            tvPionfqbmoney.setText("" + info.getBuyBackFuqibao());
            tvEmergencymoney.setText("" + info.getEmergency());
            tvCailimoney.setText("" + info.getBetrothalGifts());
            tvPionbackmoneymoney.setText("" + info.getEntrePayment());
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
