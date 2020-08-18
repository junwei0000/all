package com.longcheng.lifecareplan.modular.mine.energycenter.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.adapter.RewardRecordAdapter;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuAfterBean;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuItemBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 奖励记录
 */
public class RewardRecordActivity extends BaseListActivity<EnergyCenterContract.View, EnergyCenterPresenterImp<EnergyCenterContract.View>> implements EnergyCenterContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.lv_data)
    PullToRefreshListView lvData;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.tv_coming)
    TextView tvComing;
    @BindView(R.id.tv_comingline)
    TextView tvComingline;
    @BindView(R.id.tv_over)
    TextView tvOver;
    @BindView(R.id.tv_overline)
    TextView tvOverline;
    private int page = 0;
    private int pageSize = 20;
    int type = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_coming:
                type = 0;
                changeView();
                break;
            case R.id.tv_over:
                type = 1;
                changeView();
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.energycenter_reward;
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
        pageTopTvName.setText("奖励记录");
        notDateCont.setText("暂无奖励记录");
        tvComing.setOnClickListener(this);
        tvOver.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        lvData.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getList(page + 1);
            }
        });
    }


    @Override
    public void initDataAfter() {
        changeView();
    }

    private void getList(int page) {
        mPresent.getRewardList(page, pageSize, type);
    }

    private void changeView() {
        tvComing.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvOver.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvComingline.setVisibility(View.INVISIBLE);
        tvOverline.setVisibility(View.INVISIBLE);
        if (type == 0) {
            tvComing.setTextColor(getResources().getColor(R.color.red));
            tvComingline.setVisibility(View.VISIBLE);
        } else {
            tvOver.setTextColor(getResources().getColor(R.color.red));
            tvOverline.setVisibility(View.VISIBLE);
        }
        getList(1);
    }

    @Override
    protected EnergyCenterPresenterImp<EnergyCenterContract.View> createPresent() {
        return new EnergyCenterPresenterImp<>(mActivity, this);
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

    RewardRecordAdapter mDaiFuAdapter;

    @Override
    public void ListSuccess(DaiFuDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(lvData);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            DaiFuAfterBean mDaiFuAfterBean = responseBean.getData();
            if (mDaiFuAfterBean != null) {
                List<DaiFuItemBean> helpList = mDaiFuAfterBean.getRewardList();
                int size = helpList == null ? 0 : helpList.size();
                if (backPage == 1) {
                    mDaiFuAdapter = null;
                    showNoMoreData(false, lvData.getRefreshableView());
                }
                if (size > 0) {
                }
                if (mDaiFuAdapter == null) {
                    mDaiFuAdapter = new RewardRecordAdapter(mContext, helpList, type);
                    lvData.setAdapter(mDaiFuAdapter);
                } else {
                    mDaiFuAdapter.reloadListView(helpList, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
        }
        ListUtils.getInstance().setNotDateViewL(mDaiFuAdapter, layoutNotdate);
    }

    @Override
    public void RefuseSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void editAvatarSuccess(EditDataBean responseBean) {

    }

    @Override
    public void backBankInfoSuccess(DaiFuDataBean responseBean) {

    }

    @Override
    public void BuySuccess(PayWXDataBean responseBean) {

    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(lvData);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, lvData.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(lvData);
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
