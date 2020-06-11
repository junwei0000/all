package com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuquan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.ReportMenuActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportContract;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportPresenterImp;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.FuQuanListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportItemBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.List;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2018/9/22 15:33
 * 意图：
 */

public abstract class FuQuanBaseFrag extends BaseListFrag<ReportContract.View, ReportPresenterImp<ReportContract.View>> implements ReportContract.View {
    @BindView(R.id.date_listview)
    PullToRefreshListView dateListview;
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
    private FuQuanListAdapter mAdapter;

    String exponent;

    public static final int INDEX_JIEQI = 0;
    public static final int INDEX_DAY = 1;

    @Override
    public int bindLayout() {
        return R.layout.my_order_fragment;
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
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        dateListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

    public abstract TextView getTextView();

    public abstract int getType();

    private void getList(int page) {
        mPresent.getFuQuanList(user_id, getType(), page, pageSize);
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
        if(getTextView()!=null)
        getTextView().setText(exponent);
    }

    @Override
    public void ListSuccess(ReportDataBean responseBean, int back_page) {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            ReportAfterBean mOrderAfterBean = responseBean.getData();
            if (mOrderAfterBean != null) {
                exponent = mOrderAfterBean.getExponent();
                refreshView();
                List<ReportItemBean> mList = mOrderAfterBean.getLists();
                int size = mList == null ? 0 : mList.size();
                if (back_page == 1) {
                    mAdapter = null;
                    showNoMoreData(false, dateListview.getRefreshableView());
                }
                Log.e("checkLoadOver", "pageSize=" + pageSize + "  size=" + size + "  page=" + page + "  getType()=" + getType());
                if (size > 0) {
                }
                if (mAdapter == null) {
                    mAdapter = new FuQuanListAdapter(getActivity(), mList);
                    dateListview.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(mList, false);
                }
                page = back_page;
                checkLoadOver(size);
                ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
            }
        }
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(dateListview);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, dateListview.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(dateListview);
        }
    }

    @Override
    public void lingQuSuccess(EditDataBean responseBean) {

    }

    @Override
    public void Error() {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }
}
