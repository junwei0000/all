package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.fulist;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.bean.fupackage.FuListBean;
import com.longcheng.lifecareplan.bean.fupackage.MyFuListBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter.MyFuListAdapter;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.adapter.CornucopiaRankAdapter;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankListBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankingBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFuBaoList extends BaseListActivity<FuListContract.View, FuListPresenterImp<FuListContract.View>> implements FuListContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pageTop_layout_num)
    LinearLayout pageTopLayoutNum;
    @BindView(R.id.top_tv_num)
    TextView topTvNum;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.tv_lines)
    TextView tvLines;
    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.tv_tomorrow)
    TextView tvTomorrow;
    @BindView(R.id.tab_layout)
    LinearLayout tabLayout;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_out_num)
    TextView tvOutNum;
    @BindView(R.id.tab_bottom)
    LinearLayout tabBottom;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.lv_data)
    MyListView lvData;
    @BindView(R.id.sv_myredbag)
    PullToRefreshScrollView svMyredbag;

    boolean refreshStatus = false;

    private int type = 1;//type =1 收到    2 发出，
    private int page;
    private int pageSize = 20;

    private List<FuListBean> fuListBeans = null;
    MyFuListAdapter myFuListAdapter = null;
    private String user_id = "0000";

    public MyFuBaoList() {
        page = 1;
    }

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
        return R.layout.list_myfupackage_layout;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOrChangeTranslucentColor(toolbar, null);
        pageTopTvName.setText("我的福包");
//        asyncImageLoader = new ImageLoader(mContext, "headImg");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(1);
    }

    @Override
    public void initDataAfter() {
        int hei = (int) (DensityUtil.screenHigh(mContext));
        LogUtils.v("TAG", "hei:" + hei);
        topLayout.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) (hei * 0.3050)));
        fuListBeans = new ArrayList<>();
        svMyredbag.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                page = 1;
                getList(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                page++;
                getList(page);
            }
        });
    }

    @Override
    public void setListener() {
        ScrowUtil.ScrollViewConfigAll(svMyredbag);
        pagetopIvLeft.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        tvToday.setOnClickListener(this);
        tvTomorrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.pagetop_iv_left:
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_today:
                type = 1;
                setTopView();
                fuListBeans.clear();
                myFuListAdapter = null;
                break;
            case R.id.tv_tomorrow:
                type = 2;
                setTopView();
                fuListBeans.clear();
                myFuListAdapter = null;
                break;

        }
    }

    private void setTopView() {
        tvToday.setTextColor(getResources().getColor(R.color.color_C63F00));
        tvTomorrow.setTextColor(getResources().getColor(R.color.color_C63F00));
        tvToday.setBackgroundColor(getResources().getColor(R.color.transparent));
        tvTomorrow.setBackgroundColor(getResources().getColor(R.color.transparent));
        if (type == 1) {
            tvToday.setTextColor(getResources().getColor(R.color.white));
            tvToday.setBackgroundResource(R.drawable.corners_bg_redshen);
        } else {
            tvTomorrow.setTextColor(getResources().getColor(R.color.white));
            tvTomorrow.setBackgroundResource(R.drawable.corners_bg_redshen);
        }
        getList(1);
    }

    private void getList(int page_) {
        mPresent.getFuList(user_id, type, page_, pageSize);
    }


    @Override
    protected FuListPresenterImp<FuListContract.View> createPresent() {
        return new FuListPresenterImp<>(mContext, this);
    }

    @Override
    public void ListSuccess(MyFuListBean responseBean, int pageback) {
        ListUtils.getInstance().RefreshCompleteS(svMyredbag);
        String status_ = responseBean.getStatus();
        LogUtils.v("TAG", "status_:" + status_ + "page" + page);
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            MyFuListBean.MyFuListAfterBean data = responseBean.getData();
            fuListBeans = data.getList();
            String avatar = data.getAvatar();
            String count = data.getCount();
            int size = fuListBeans == null ? 0 : fuListBeans.size();
            LogUtils.v("TAG", "size:" + size + "page" + page);
            if (size == 0 && page == 1) {
                myFuListAdapter = null;
                showNoMoreData(false, lvData);
            }
            if (myFuListAdapter == null) {
                myFuListAdapter = new MyFuListAdapter(mActivity, fuListBeans,type);
                lvData.setAdapter(myFuListAdapter);
            } else {
                myFuListAdapter.reloadListView(fuListBeans, false);
            }
            page = pageback;
            checkLoadOver(size);
        }
        lvData.setVisibility(View.VISIBLE);
    }

    @Override
    public void ListError() {
        lvData.setVisibility(View.VISIBLE);
        ListUtils.getInstance().RefreshCompleteS(svMyredbag);
    }


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

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.ScrollViewDownConfig(svMyredbag);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, lvData);
            }
        } else {
            ScrowUtil.ScrollViewConfigAll(svMyredbag);
        }
    }
}
