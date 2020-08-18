package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.adapter.EngryListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpEnergyAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.GaveQuliRecordAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.adapter.ZFBSelectAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class GaveQualiRecordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.date_listview)
    PullToRefreshListView dateListview;
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

    private int page = 0;
    private int pageSize = 20;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_userzyb_gavequlirecord;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("资格列表");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        notDateImg.setBackgroundResource(R.mipmap.zyb_search_bg);
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
    public void initDataAfter() {
        getList(1);
    }

    private void getList(int page) {
        seniorityUserList(page, pageSize);
    }

    public void seniorityUserList(int page, int pageSize) {
        showDialog();
        Observable<PionZFBSelectDataBean> observable = Api.getInstance().service.seniorityUserList(UserUtils.getUserId(mContext),
                page, pageSize, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PionZFBSelectDataBean>() {
                    @Override
                    public void accept(PionZFBSelectDataBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            showDate(responseBean, page);
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                        ListUtils.getInstance().RefreshCompleteL(dateListview);
                        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                    }
                });

    }

    GaveQuliRecordAdapter mAdapter;

    private void showDate(PionZFBSelectDataBean responseBean, int backPage) {
        ArrayList<PionZFBSelectDataBean.PionZFBSBean> helpList = responseBean.getData();
        int size = helpList == null ? 0 : helpList.size();
        if (backPage == 1) {
            mAdapter = null;
        }
        if (mAdapter == null) {
            mAdapter = new GaveQuliRecordAdapter(mContext, helpList);
            dateListview.setAdapter(mAdapter);
        } else {
            mAdapter.reloadListView(helpList, false);
        }
        page = backPage;
        checkLoadOver(size);
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(dateListview);
            if (size > 0 || (page > 1 && size >= 0)) {
            }
        } else {
            ScrowUtil.listViewConfigAll(dateListview);
        }
    }

    boolean refreshStatus = false;

    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }


    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
            doFinish();
        }
        return false;
    }
}
