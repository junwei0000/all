package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import butterknife.BindView;

public class ProgressMoneyFragment extends BaseListFrag<CSRecordContract.View, CSRecordImp<CSRecordContract.View>> implements CSRecordContract.View, IActivityUpData {
    public static ProgressMoneyFragment newInstance() {
        ProgressMoneyFragment fragment = new ProgressMoneyFragment();
        return fragment;
    }


    @BindView(R.id.lv_progress)
    PullToRefreshListView lv_progress;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    private boolean refreshStatus = false;
    private CSRecordAdapter mAdapter;
    private String user_id;
    private int page = 1;

    @Override
    protected View getFooterView() {
        FooterNoMoreData view = new FooterNoMoreData(mContext);
        view.showContJiJu(true);
        return view;
    }
    @Override
    public void initView(View view) {
        super.initView(view);
        user_id = UserUtils.getUserId(mContext);
        getData(1);
        lv_progress.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getData(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                page++;
                getData(page);
            }
        });
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_prgress_money;
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    protected CSRecordImp<CSRecordContract.View> createPresent() {
        return new CSRecordImp<>(mContext, this);
    }

    @Override
    public void ListSuccess(CSRecordBean responseBean, int pages) {
        LogUtils.e(responseBean.getData().toString());
        ListUtils.getInstance().RefreshCompleteL(lv_progress);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {

            if (responseBean != null) {


                int size = responseBean.getData() == null ? 0 : responseBean.getData().size();

                if (pages == 1) {
                    // mAllList.clear();
                    mAdapter = null;
                    showNoMoreData(false, lv_progress.getRefreshableView());
                }
//                if (size > 0) {
//                    mAllList.addAll(mEnergyAfterBean.getList());
//                }
                if (mAdapter == null) {
                    mAdapter = new CSRecordAdapter(mActivity, responseBean.getData(), getActivity(), 2);
                    lv_progress.setAdapter(mAdapter);

                } else {
                    mAdapter.reloadListView(responseBean.getData(), false);
                }
                page = pages;
                  checkLoadOver(size);
                mAdapter.setUpdateData(this);
                ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
            }
        }

    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(lv_progress);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, lv_progress.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(lv_progress);
        }
    }

    @Override
    public void ListError() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    private void getData(int page) {
        mPresent.helpneed_customerList(user_id, 1, page, pageSize);
    }

    @Override
    public void upDataUi() {
        getData(1);
    }
}
