package com.longcheng.volunteer.modular.helpwith.lifestyledetail.lifestylerank.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseListActivity;
import com.longcheng.volunteer.modular.helpwith.lifestyledetail.lifestylerank.adapter.LifeRankAdapter;
import com.longcheng.volunteer.modular.helpwith.lifestyledetail.lifestylerank.bean.LifeRankAfterBean;
import com.longcheng.volunteer.modular.helpwith.lifestyledetail.lifestylerank.bean.LifeRankItemBean;
import com.longcheng.volunteer.modular.helpwith.lifestyledetail.lifestylerank.bean.LifeRankListDataBean;
import com.longcheng.volunteer.utils.ListUtils;
import com.longcheng.volunteer.utils.ScrowUtil;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 互祝-排行
 */
public class LifeRankActivity extends BaseListActivity<LifeRankContract.View, LifeRankPresenterImp<LifeRankContract.View>> implements LifeRankContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;

    private int page = 0;
    private int pageSize = 20;
    private String user_id, help_goods_id;
    LifeRankAdapter mRankAdapter;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_engry_rank;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("爱心榜");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        helpListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(page + 1);
            }
        });
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        help_goods_id = intent.getStringExtra("help_goods_id");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        getList(1);
    }


    private void getList(int page_) {
        mPresent.setListViewData(user_id, help_goods_id, page_, pageSize);
    }

    @Override
    protected LifeRankPresenterImp<LifeRankContract.View> createPresent() {
        return new LifeRankPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void ListSuccess(LifeRankListDataBean responseBean, int backpage) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            LifeRankAfterBean mDetailAfterBean = (LifeRankAfterBean) responseBean.getData();
            if (mDetailAfterBean != null) {
                List<LifeRankItemBean> rankings = mDetailAfterBean.getHelpGoodsRanking();
                int size = rankings == null ? 0 : rankings.size();
                if (backpage == 1) {
                    mHotPushList.clear();
                    mRankAdapter = null;
                    rankingsTop.clear();
                    mHotPushList.clear();
                    showNoMoreData(false, helpListview.getRefreshableView());
                }
                if (size == 0) {
                    rankings = new ArrayList<>();
                }
                page = backpage;
                showData(rankings, backpage);
                checkLoadOver(size);
            }
        }
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(helpListview);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, helpListview.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(helpListview);
        }
    }

    List<LifeRankItemBean> rankingsTop = new ArrayList<>();
    List<LifeRankItemBean> mHotPushList = new ArrayList<>();

    private void showData(List<LifeRankItemBean> rankings, int backpage) {
        if (backpage == 1) {
            if (rankings.size() == 0) {
                rankings.add(new LifeRankItemBean());
                rankings.add(new LifeRankItemBean());
                rankings.add(new LifeRankItemBean());
            } else if (rankings.size() == 1) {
                rankings.add(new LifeRankItemBean());
                rankings.add(new LifeRankItemBean());
            } else if (rankings.size() == 2) {
                rankings.add(new LifeRankItemBean());
            }
            if (rankings.size() >= 3) {
                mHotPushList.add(new LifeRankItemBean());//_____________________
                for (int i = 0; i < rankings.size(); i++) {
                    if (i < 3) {
                        rankingsTop.add(rankings.get(i));
                    } else {
                        mHotPushList.add(rankings.get(i));
                    }
                }
            } else {
                rankingsTop = rankings;
            }
        } else {
            mHotPushList.addAll(rankings);
        }
        if (mRankAdapter == null) {
            mRankAdapter = new LifeRankAdapter(mActivity, mHotPushList, rankingsTop);
            helpListview.setAdapter(mRankAdapter);
        } else {
            mRankAdapter.refreshListView(mHotPushList);
        }
    }

    @Override
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
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
