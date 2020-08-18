package com.longcheng.lifecareplan.modular.helpwith.connon.activity;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.CHelpSelectAllAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.adapter.MyCHelpListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpCreatDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpGroupRoomDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class MyHelpListActivity extends BaseActivityMVP<CHelpContract.View, CHelpPresenterImp<CHelpContract.View>> implements CHelpContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.my_tv_gowith)
    TextView my_tv_gowith;
    @BindView(R.id.my_tv_marry)
    TextView my_tv_marry;
    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;


    private int page = 0;
    private int page_size = 10;
    int type = 2;//1.结缘 2.结伴

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.my_tv_marry:
                type = 1;
                getList(1);
                break;
            case R.id.my_tv_gowith:
                type = 2;
                getList(1);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.connon_my_helplist;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("我的组队");
        pagetopLayoutLeft.setOnClickListener(this);
        my_tv_marry.setOnClickListener(this);
        my_tv_gowith.setOnClickListener(this);
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
    }

    private void getList(int page) {
        if (type == 1) {
            my_tv_gowith.setTextColor(getResources().getColor(R.color.color_333333));
            my_tv_marry.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else {
            my_tv_gowith.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            my_tv_marry.setTextColor(getResources().getColor(R.color.color_333333));
        }
        mPresent.getMyHelpList(type, page, page_size);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(1);
    }

    @Override
    protected CHelpPresenterImp<CHelpContract.View> createPresent() {
        return new CHelpPresenterImp<>(mContext, this);
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

    MyCHelpListAdapter mAdapter;

    @Override
    public void ListSuccess(CHelpListDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("421")) {
            doFinish();
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            ArrayList<CHelpListDataBean.CHelpListItemBean> helpList = responseBean.getData();
            int size = helpList == null ? 0 : helpList.size();
            if (backPage == 1) {
                mAdapter = null;
            }
            if (size > 0) {
            }
            if (mAdapter == null) {
                mAdapter = new MyCHelpListAdapter(mActivity, helpList);
                helpListview.setAdapter(mAdapter);
            } else {
                mAdapter.reloadListView(helpList, false);
            }
            page = backPage;
            checkLoadOver(size);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }

    private void checkLoadOver(int size) {
        if (size < page_size) {
            ScrowUtil.listViewDownConfig(helpListview);
            if (size > 0 || (page > 1 && size >= 0)) {
            }
        } else {
            ScrowUtil.listViewConfigAll(helpListview);
        }
    }

    @Override
    public void DataSuccess(CHelpDataBean responseBean) {
    }

    @Override
    public void DetailDataSuccess(CHelpDetailDataBean responseBean) {

    }

    @Override
    public void CreatePageDataSuccess(CHelpCreatDataBean responseBean) {

    }

    @Override
    public void GroupRoomDataSuccess(CHelpGroupRoomDataBean responseBean) {

    }

    @Override
    public void editSuccess(ResponseBean responseBean) {

    }

    @Override
    public void CreateTableSuccess(ResponseBean responseBean) {

    }

    @Override
    public void CreateRoomSuccess(EditDataBean responseBean) {

    }


    @Override
    public void ListError() {
    }

}
