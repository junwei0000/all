package com.longcheng.lifecareplan.modular.mine.rewardcenters.activity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.longcheng.lifecareplan.modular.mine.rewardcenters.adapter.AdapterRewardCenters;
import com.longcheng.lifecareplan.modular.mine.rewardcenters.bean.RewardCentersResultBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * Created by Burning on 2018/9/4.
 */

public class RewardCentersActivity extends BaseListActivity<RewardCentersContract.View, RewardCentersImp<RewardCentersContract.View>> implements RewardCentersContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout llBack;
    @BindView(R.id.pageTop_tv_name)
    TextView tvTitle;
    @BindView(R.id.ptf_reward)
    PullToRefreshListView ptrView;
    @BindView(R.id.tv_total_skb)
    TextView tvTotalSkb;
    @BindView(R.id.tv_invitation_count)
    TextView tvInvitationCount;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;


    private AdapterRewardCenters adapter;

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_rewardcenters;
    }

    @Override
    public void initDataBefore() {
        super.initDataBefore();
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        tvTitle.setText(R.string.reward_centers);
        setOrChangeTranslucentOtherColor(toolbar, null, getResources().getColor(R.color.color_fb4c76));
    }

    @Override
    public void initDataAfter() {
        adapter = new AdapterRewardCenters(mContext);
        ptrView.setAdapter(adapter);
        refresh(0);
    }

    @Override
    public void setListener() {
        llBack.setOnClickListener(this);
        ScrowUtil.listViewConfigAll(ptrView);
        ptrView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                refresh(0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                refresh(pageIndex);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
        }
    }

    @Override
    protected RewardCentersImp<RewardCentersContract.View> createPresent() {
        return new RewardCentersImp<>(mContext, this);
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
    public void onSuccess(RewardCentersResultBean responseBean, int pageIndex_) {
        ListUtils.getInstance().RefreshCompleteL(ptrView);
        //获取到数据了
        if (responseBean != null && responseBean.getData() != null) {
            int listSize = responseBean.getData().getList() == null ? 0 : responseBean.getData().getList().size();
            tvInvitationCount.setText("" + responseBean.getData().getCho_count());
            tvTotalSkb.setText("" + responseBean.getData().getCho_reward());
            boolean isRefresh = pageIndex_ == 1;
            if (listSize == 0) {
                ScrowUtil.listViewDownConfig(ptrView);
                if (isRefresh) {
                    showEmptyView(true);
                } else {
                    showNoMoreData(true, ptrView.getRefreshableView());
                }
                return;
            }
            if (listSize < pageSize) {
                ScrowUtil.listViewDownConfig(ptrView);
                //当前加载的数据不足一页时候，提示footer
                showNoMoreData(true, ptrView.getRefreshableView());
            }

            if (pageIndex_ == 1) {//刷新
                pageIndex = pageIndex_;
                adapter.refresh(responseBean.getData().getList(), true);
                showEmptyView(false);
                if (listSize == pageSize) {
                    ScrowUtil.listViewConfigAll(ptrView);
                    //刷新成功，可能还有下一页的时候，需要先隐藏这个提示footer
                    //非刷新操作的时候，不做任何操作
                    showNoMoreData(false, ptrView.getRefreshableView());
                }
            } else {
                pageIndex++;
                adapter.refresh(responseBean.getData().getList(), false);
            }
        } else if (pageIndex_ == 1) {
            ScrowUtil.listViewDownConfig(ptrView);
            showEmptyView(true);
        }
        Log.e("aaa", "onSuccess pageIndex_ = " + pageIndex_ + " , pageIndex = " + pageIndex);
    }

    private void showEmptyView(boolean empty) {
        ptrView.setVisibility(empty ? View.GONE : View.VISIBLE);
        layoutNotdate.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onError(String code) {
        ListUtils.getInstance().RefreshCompleteL(ptrView);
    }

    private void refresh(int pageIndex_) {
        mPresent.doRefresh(pageIndex_ + 1, pageSize, UserUtils.getUserId(mContext), UserUtils.getToken());
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
