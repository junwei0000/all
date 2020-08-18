package com.longcheng.lifecareplan.modular.mine.cashflow.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.longcheng.lifecareplan.modular.mine.cashflow.adapter.CashFlowAdapter;
import com.longcheng.lifecareplan.modular.mine.cashflow.bean.CashFlowAfterBean;
import com.longcheng.lifecareplan.modular.mine.cashflow.bean.CashFlowDataBean;
import com.longcheng.lifecareplan.modular.mine.cashflow.bean.CashFlowItemBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.CalenderSelectNowUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.List;

import butterknife.BindView;

/**
 * 福气榜
 */
public class FlowDetailActivity extends BaseListActivity<CashFlowContract.View, CashFlowPresenterImp<CashFlowContract.View>> implements CashFlowContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_allmoney)
    TextView tvAllmoney;
    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;
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
    private String user_id;
    private int page;
    private int pageSize = 20;
    CalenderSelectNowUtils mCalenderSelectUtils;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.tv_date:
                if (mCalenderSelectUtils == null) {
                    mCalenderSelectUtils = new CalenderSelectNowUtils(mActivity, mHandler, SELECTDATE);
                }
                mCalenderSelectUtils.initCurrentItem(date);
                mCalenderSelectUtils.showDialog();
                break;
        }
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
                    tvDate.setText(selectDate);
                    date = DatesUtils.getInstance().getDateGeShi(selectDate, "yyyy年MM月dd日", "yyyy-MM-dd");
                    getList(1);
                    break;
            }
        }
    };

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.mine_cashflow_detail;
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
        tvDate.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        notDateCont.setText("暂无数据");
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
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
    public void initDataAfter() {
        Intent intent = getIntent();
        getData(intent);
    }

    int type;
    String date;

    private void getData(Intent intent) {
        String title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        type = intent.getIntExtra("type", 1);
        user_id = UserUtils.getUserId(mContext);
        pageTopTvName.setText(title);
        tvDate.setText(DatesUtils.getInstance().getDateGeShi(date, "yyyy-MM-dd", "yyyy年MM月dd日"));
        getList(1);
    }

    private void getList(int page) {
        mPresent.getFlowDetail(user_id, type, date, page, pageSize);
    }


    @Override
    protected CashFlowPresenterImp<CashFlowContract.View> createPresent() {
        return new CashFlowPresenterImp<>(mActivity, this);
    }

    boolean refreshStatus = false;
    CashFlowAdapter mAdapter;

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
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            CashFlowAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                tvAllmoney.setText(mEnergyAfterBean.getMoney());
                List<CashFlowItemBean> helpList = mEnergyAfterBean.getData();
                int size = helpList == null ? 0 : helpList.size();
                if (backPage == 1) {
                    mAdapter = null;
                    showNoMoreData(false, helpListview.getRefreshableView());
                }
                if (size > 0) {
                }
                if (mAdapter == null) {
                    mAdapter = new CashFlowAdapter(mActivity, helpList);
                    helpListview.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(helpList, false);
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
    public void userStatisticsInfo(CashFlowDataBean responseBean) {

    }

    @Override
    public void ListError() {
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
        ListUtils.getInstance().RefreshCompleteL(helpListview);
    }


    private void back() {
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
