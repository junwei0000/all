package com.longcheng.lifecareplan.modular.home.healthydelivery.list.frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.modular.home.healthydelivery.detail.activity.HealthyDeliveryContract;
import com.longcheng.lifecareplan.modular.home.healthydelivery.detail.activity.HealthyDeliveryDetailAct;
import com.longcheng.lifecareplan.modular.home.healthydelivery.detail.activity.HealthyDeliveryImp;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.adapter.AdapterHealthyDeliveryList;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.bean.HealthyDeliveryBean;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.bean.HealthyDeliveryResultBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Burning on 2018/9/13.
 */

public abstract class BaseFrag extends BaseListFrag<HealthyDeliveryContract.View, HealthyDeliveryImp<HealthyDeliveryContract.View>> implements HealthyDeliveryContract.View {

    @BindView(R.id.ftf_lv)
    PullToRefreshListView ptrView;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;

    AdapterHealthyDeliveryList adapter;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public int bindLayout() {
        return R.layout.frag_healthy_delivery_base;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        adapter = new AdapterHealthyDeliveryList(mContext);
        ptrView.setAdapter(adapter);
        setListeners();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //没有数据的时候，重新加载一次，初次载入加载成功后，切换页面不再自动请求数据
        if (pageIndex == 0) {
            refresh(0);
        }
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    protected HealthyDeliveryImp<HealthyDeliveryContract.View> createPresent() {
        return new HealthyDeliveryImp<>(getActivity(), this);
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
    public void onSuccess(HealthyDeliveryResultBean responseBean, int pageIndex_) {
        ListUtils.getInstance().RefreshCompleteL(ptrView);
        //获取到数据了
        if (responseBean != null && responseBean.getData() != null) {
            int listSize = responseBean.getData().getList() == null ? 0 : responseBean.getData().getList().size();
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
            } else {
                ScrowUtil.listViewConfigAll(ptrView);
            }
            if (pageIndex_ == 1) {//刷新
                pageIndex = pageIndex_;
                adapter.refresh(responseBean.getData().getList(), true);
                showEmptyView(false);
                if (listSize == pageSize) {
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
        Log.e("aaa", "pageIndex = " + pageIndex);
    }

    private void showEmptyView(boolean empty) {
        ptrView.setVisibility(empty ? View.GONE : View.VISIBLE);
        layoutNotdate.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onError(String msg) {
        ListUtils.getInstance().RefreshCompleteL(ptrView);
    }

    public abstract int getType();

    private void setListeners() {
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
        ptrView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - 1;
                if (index >= 0 && index < adapter.getCount()) {
                    HealthyDeliveryBean bean = adapter.getItem(index);
                    if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToHealthyDeli)) {
                        Intent intent = new Intent(getActivity(), HealthyDeliveryDetailAct.class);
                        intent.putExtra("detail_url", bean.getUrlDetail());
                        intent.putExtra("cont", bean.getDes());
                        intent.putExtra("title", bean.getTitle());
                        intent.putExtra("img", bean.getPic());
                        getActivity().startActivity(intent);
                    } else {
                        SharedPreferencesHelper.put(mContext, "health_detail_url", bean.getUrlDetail());
                        SharedPreferencesHelper.put(mContext, "health_cont", bean.getDes());
                        SharedPreferencesHelper.put(mContext, "health_title", bean.getTitle());
                        SharedPreferencesHelper.put(mContext, "health_newimg", bean.getPic());
                    }
                }

            }
        });
    }

    private void refresh(int pageIndex_) {
        mPresent.doRefresh(pageIndex_ + 1, pageSize, getType());
    }
}
