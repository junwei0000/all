package com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.adapter.DoclRewardListAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.adapter.VolListAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.RewardDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.VolSearchDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 志愿者列表
 */
public class VoloutListActivity extends BaseListActivity<DoctorContract.View, DocPresenterImp<DoctorContract.View>> implements DoctorContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.lv_data)
    PullToRefreshListView lvData;
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
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    private int page = 0;
    private int pageSize = 20;


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.not_date_btn:
            case R.id.tv_bottom:
                intent = new Intent(mContext, VoloutAddActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.doctor_volout_list;
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
        pageTopTvName.setText("志愿者");
        pagetopLayoutLeft.setOnClickListener(this);
        tvBottom.setOnClickListener(this);
        notDateBtn.setOnClickListener(this);
        notDateImg.setBackgroundResource(R.mipmap.not_volout_img);
        notDateCont.setText("您还未添加志愿者~");
        notDateBtn.setVisibility(View.VISIBLE);
        notDateBtn.setText("添加志愿者");
        lvData.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

    @Override
    protected void onResume() {
        super.onResume();
        getList(1);
    }

    private void getList(int page) {
        mPresent.getVolList(page, pageSize);
    }


    @Override
    protected DocPresenterImp<DoctorContract.View> createPresent() {
        return new DocPresenterImp<>(mActivity, this);
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

    VolListAdapter mDaiFuAdapter;

    @Override
    public void ListSuccess(DocRewardListDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(lvData);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ArrayList<DocRewardListDataBean.DocItemBean> helpList = responseBean.getData();
            int size = helpList == null ? 0 : helpList.size();
            if (backPage == 1) {
                mDaiFuAdapter = null;
                showNoMoreData(false, lvData.getRefreshableView());
            }
            if (size > 0) {
            }
            if (mDaiFuAdapter == null) {
                mDaiFuAdapter = new VolListAdapter(mContext, helpList, mHandler);
                lvData.setAdapter(mDaiFuAdapter);
            } else {
                mDaiFuAdapter.reloadListView(helpList, false);
            }
            page = backPage;
            checkLoadOver(size);
        }
        ListUtils.getInstance().setNotDateViewL(mDaiFuAdapter, layoutNotdate);
    }

    @Override
    public void VolSearchSuccess(VolSearchDataBean responseBean) {

    }

    @Override
    public void GetRewardInfoSuccess(RewardDataBean responseBean) {

    }

    @Override
    public void VolAddSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            getList(1);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String doctor_relation_volunteer_id = (String) msg.obj;
                    mPresent.delVolunteer(doctor_relation_volunteer_id);
                    break;
            }
        }
    };

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.listViewDownConfig(lvData);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, lvData.getRefreshableView());
            }
        } else {
            ScrowUtil.listViewConfigAll(lvData);
        }
    }

    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
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
