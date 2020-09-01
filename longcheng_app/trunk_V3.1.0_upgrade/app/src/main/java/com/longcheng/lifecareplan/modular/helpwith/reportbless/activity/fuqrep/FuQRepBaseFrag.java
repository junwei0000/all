package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.fuqrep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.modular.exchange.jieqiactivities.JieQisUtils;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportContract;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportPresenterImp;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.FuQRepListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ListItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.List;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2018/9/22 15:33
 * 意图：
 */

public abstract class FuQRepBaseFrag extends BaseListFrag<ReportContract.View, ReportPresenterImp<ReportContract.View>> implements ReportContract.View {
    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    private String user_id;
    private int page = 0;
    private int pageSize = 20;
    private FuQRepListAdapter mAdapter;


    public static final int INDEX_COMING = 1;
    public static final int INDEX_DAI = 2;
    public static final int INDEX_OVER = 3;

    @Override
    public int bindLayout() {
        return R.layout.fuqrep_list_refresh;
    }

    @Override
    protected View getFooterView() {
        FooterNoMoreData view = new FooterNoMoreData(mContext);
        view.showContJiJu(true);
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        notDateCont.setText("暂无数据噢~");
        notDateBtn.setVisibility(View.GONE);
        helpListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
    public void doBusiness(Context mContext) {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        getList(1);
    }

    public abstract int getSelect_status();

    public abstract int getType();

    public abstract String getKeyword();

    private void getList(int page) {
        mPresent.getFuQReportList(getType(), getKeyword(), getSelect_status(), page, pageSize);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:

                break;
            default:
                break;
        }
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

    public void refreshView() {
    }


    @Override
    public void ListSuccess(ReportDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ReportAfterBean mDaiFuAfterBean = responseBean.getData();
            if (mDaiFuAfterBean != null) {
                List<ListItemBean> blessCards = mDaiFuAfterBean.getBlessCards();
                int size = blessCards == null ? 0 : blessCards.size();
                if (backPage == 1) {
                    if (mAdapter != null) {
                        mAdapter.clearTimer();
                        mAdapter = null;
                    }
                    showNoMoreData(false, helpListview.getRefreshableView());
                }
                if (size > 0) {
                }
                if (mAdapter == null) {
                    mAdapter = new FuQRepListAdapter(mActivity, blessCards, getType(), mHandler);
                    helpListview.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(blessCards, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
        }
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
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

    @Override
    public void lingQuSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            ((FuQRepListActivity) getActivity()).reLoadList();
            if (mJieQiUtils == null) {
                mJieQiUtils = new JieQisUtils(mActivity);
            }
            mJieQiUtils.showFQBialog();
        }
    }

    JieQisUtils mJieQiUtils;

    @Override
    public void DetailSuccess(FQDetailDataBean responseBean) {

    }

    @Override
    public void Error() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.clearTimer();
            mAdapter = null;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String bless_id = (String) msg.obj;
            switch (msg.what) {
                case 1:
                    mPresent.getLingQu(UserUtils.getUserId(mContext), bless_id);
                    break;
            }
        }
    };
}
