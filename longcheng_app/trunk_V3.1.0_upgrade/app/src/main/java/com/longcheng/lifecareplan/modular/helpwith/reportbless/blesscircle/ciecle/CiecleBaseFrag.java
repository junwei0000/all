package com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.ciecle;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportContract;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportPresenterImp;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.fuqrep.FuQRepListActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.FuQuanListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.MyCircleListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.LoveItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.MyCircleContract;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.MyCirclePresenterImp;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2018/9/22 15:33
 * 意图：
 */

public abstract class CiecleBaseFrag extends BaseFragmentMVP<MyCircleContract.View, MyCirclePresenterImp<MyCircleContract.View>> implements MyCircleContract.View {
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
    private MyCircleListAdapter mAdapter;

    public static final int INDEX_NOBLE = 0;
    public static final int INDEX_BLESS = 1;

    @Override
    public int bindLayout() {
        return R.layout.my_order_fragment;
    }


    @Override
    public void initView(View view) {
        notDateCont.setText("暂无数据噢~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        ScrowUtil.listViewDownConfig(dateListview);
        dateListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshStatus = true;
                getList();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        getList();
    }

    public abstract int getType();

    private void getList() {
        mPresent.getMyCircleList();
    }

    @Override
    public void widgetClick(View v) {
    }

    @Override
    protected MyCirclePresenterImp<MyCircleContract.View> createPresent() {
        return new MyCirclePresenterImp<>(mContext, this);
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
    public void ListSuccess(ReportDataBean responseBean, int back_page) {
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            ReportAfterBean mOrderAfterBean = responseBean.getData();
            if (mOrderAfterBean != null) {
                List<LoveItemBean> Recive = mOrderAfterBean.getRecive();
                List<LoveItemBean> Response = mOrderAfterBean.getResponse();
                if( getType()==0){
                    mAdapter = new MyCircleListAdapter(getActivity(), Recive);
                }else{
                    mAdapter = new MyCircleListAdapter(getActivity(), Response);
                }
                dateListview.setAdapter(mAdapter);

            }
        }
        doRefreshComplete();
    }
    @Override
    public void MessageListSuccess(MessageListDataBean responseBean) {

    }

    @Override
    public void Error() {
        doRefreshComplete();
    }

    private void doRefreshComplete() {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }
}
