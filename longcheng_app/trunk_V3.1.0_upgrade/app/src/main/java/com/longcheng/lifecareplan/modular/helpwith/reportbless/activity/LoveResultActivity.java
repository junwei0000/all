package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.LoveResultAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.LoveItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 爱的成就
 */
public class LoveResultActivity extends BaseListActivity<ReportContract.View, ReportPresenterImp<ReportContract.View>> implements ReportContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.layout_engry)
    LinearLayout layoutEngry;
    @BindView(R.id.tv_noble)
    TextView tvNoble;
    @BindView(R.id.tv_fushen)
    TextView tv_fushen;
    @BindView(R.id.detail_lv_rank)
    MyListView detailLvRank;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_noble:
                guiren = true;
                changeTab();
                break;
            case R.id.tv_fushen:
                guiren = false;
                changeTab();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fuqrep_loveresult;
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
        pageTopTvName.setText("爱的成就");
        pagetopLayoutLeft.setOnClickListener(this);
        tvNoble.setOnClickListener(this);
        tv_fushen.setOnClickListener(this);
        pagetopLayoutLeft.setFocusable(true);
        pagetopLayoutLeft.setFocusableInTouchMode(true);
        pagetopLayoutLeft.requestFocus();
    }

    String bless_id;

    @Override
    public void initDataAfter() {
        bless_id = getIntent().getStringExtra("bless_id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    private void getList() {
        mPresent.getLoveResultList();
    }

    @Override
    protected ReportPresenterImp<ReportContract.View> createPresent() {
        return new ReportPresenterImp<>(mContext, this);
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

    List<LoveItemBean> response = new ArrayList<>();
    List<LoveItemBean> recive = new ArrayList<>();

    @Override
    public void ListSuccess(ReportDataBean responseBean, int backPage) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ReportAfterBean mDaiFuAfterBean = responseBean.getData();
            String complete_money = mDaiFuAfterBean.getComplete_money();
            response = mDaiFuAfterBean.getResponse();
            recive = mDaiFuAfterBean.getRecive();
            showNum(complete_money);
            changeTab();
        }
    }

    @Override
    public void DetailSuccess(FQDetailDataBean responseBean) {

    }

    @Override
    public void lingQuSuccess(EditDataBean responseBean) {


    }

    @Override
    public void Error() {
    }

    private void showNum(String complete_money) {
        if (!TextUtils.isEmpty(complete_money)) {
            layoutEngry.removeAllViews();
            for (int i = 0; i < complete_money.length(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.fuqrep_loveresult_topnum, null);
                TextView tv_num = view.findViewById(R.id.tv_num);
                tv_num.setText("" + complete_money.charAt(i));
                layoutEngry.addView(view);
            }
        }
    }

    boolean guiren = true;

    private void changeTab() {
        if (guiren) {
            shoeList(recive);
            tvNoble.setTextColor(getResources().getColor(R.color.white));
            tvNoble.setBackgroundResource(R.drawable.corners_bg_yellow25);
            tv_fushen.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            tv_fushen.setBackgroundResource(R.color.transparent);
        } else {
            shoeList(response);
            tvNoble.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            tvNoble.setBackgroundResource(R.color.transparent);
            tv_fushen.setTextColor(getResources().getColor(R.color.white));
            tv_fushen.setBackgroundResource(R.drawable.corners_bg_yellow25);
        }
    }

    private void shoeList(List<LoveItemBean> list) {
        LoveResultAdapter mLoveResultAdapter = new LoveResultAdapter(mContext, list,guiren);
        detailLvRank.setAdapter(mLoveResultAdapter);
        if (list != null && list.size() > 0) {
            layoutNotdate.setVisibility(View.GONE);
            detailLvRank.setVisibility(View.VISIBLE);
        } else {
            layoutNotdate.setVisibility(View.VISIBLE);
        }
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
