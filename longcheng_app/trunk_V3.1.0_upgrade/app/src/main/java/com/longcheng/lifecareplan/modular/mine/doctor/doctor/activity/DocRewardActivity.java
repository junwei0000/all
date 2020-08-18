package com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity;

import android.content.Intent;
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
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.adapter.DoclRewardListAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.RewardDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.VolSearchDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 坐堂医奖励
 */
public class DocRewardActivity extends BaseListActivity<DoctorContract.View, DocPresenterImp<DoctorContract.View>> implements DoctorContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_allmoney)
    TextView tvAllmoney;
    @BindView(R.id.tv_reward)
    TextView tvReward;
    @BindView(R.id.tv_tixian)
    TextView tvTixian;
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
    private int page = 0;
    private int pageSize = 20;

    String account_yue = "0";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_tixian:
                Intent intent = new Intent(mContext, DocTiXianActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("source", "1");//1坐堂医， 2志愿者
                intent.putExtra("account_yue", account_yue);
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
        return R.layout.volouteer_reward;
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
        pageTopTvName.setText("坐堂医奖励");
        pagetopLayoutLeft.setOnClickListener(this);
        tvTixian.setOnClickListener(this);
        notDateCont.setText("暂无数据");
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.doctorAsset();
        getList(1);
    }

    private void getList(int page) {
        mPresent.getDocRewardList(page, pageSize);
    }


    @Override
    protected DocPresenterImp<DoctorContract.View> createPresent() {
        return new DocPresenterImp<>(mActivity, this);
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

    DoclRewardListAdapter mDaiFuAdapter;

    @Override
    public void ListSuccess(DocRewardListDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(lvData);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ArrayList<DocRewardListDataBean.DocItemBean> helpList = responseBean.getData();
            int size = helpList == null ? 0 : helpList.size();
            if (backPage == 1) {
                mDaiFuAdapter = null;
                showNoMoreData(false, lvData.getRefreshableView());
            }
            if (size > 0) {
            }
            if (mDaiFuAdapter == null) {
                mDaiFuAdapter = new DoclRewardListAdapter(mContext, helpList);
                lvData.setAdapter(mDaiFuAdapter);
            } else {
                mDaiFuAdapter.reloadListView(helpList, false);
            }
            page = backPage;
            checkLoadOver(size);
        }
        ListUtils.getInstance().setNotDateViewL(mDaiFuAdapter, layoutNotdate);
    }

    @Override
    public void VolSearchSuccess(VolSearchDataBean responseBean) {

    }

    @Override
    public void GetRewardInfoSuccess(RewardDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            RewardDataBean.RewardItemBean data = responseBean.getData();
            account_yue = data.getBalance();
            tvAllmoney.setText(account_yue);
            tvReward.setText(data.getCumulative_balance());

        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void VolAddSuccess(ResponseBean responseBean) {

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
