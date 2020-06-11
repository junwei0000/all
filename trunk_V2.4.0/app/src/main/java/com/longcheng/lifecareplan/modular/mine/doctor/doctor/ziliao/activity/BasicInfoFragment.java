package com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity.VoloutAddActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.adapter.BasicInfoListAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.AYApplyListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.BasicInfoListDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class BasicInfoFragment extends BaseListFrag<BasicContract.View, BasicPresenterImp<BasicContract.View>> implements BasicContract.View {
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

    @BindView(R.id.tv_bottom)
    TextView tv_bottom;

    private String user_id;
    private int page = 0;
    private int pageSize = 20;
    List<BasicInfoListDataBean.BasicItemBean> mAllList = new ArrayList<>();
    private BasicInfoListAdapter mAdapter;


    @Override
    public int bindLayout() {
        return R.layout.doctor_basic_fragment;
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
        tv_bottom.setOnClickListener(this);
        tv_bottom.setVisibility(View.VISIBLE);
        notDateCont.setText("暂无记录噢~");
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
        dateListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAllList != null && mAllList.size() > 0&& position <= mAllList.size()) {
                    String knp_msg_patient_id = mAllList.get(position - 1).getKnp_msg_patient_id();
                    BasicListActivity.editOrderStatus = true;
                    Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + Config.BASE_HEAD_URL + "home/knp/patientedit/knp_msg_patient_id/" + knp_msg_patient_id);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        getList(1);
    }


    private void getList(int page) {
        mPresent.getBasicInfoList(user_id, page, pageSize);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bottom:
                BasicListActivity.editOrderStatus = true;
                Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + Config.BASE_HEAD_URL + "home/knp/patientadd");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected BasicPresenterImp<BasicContract.View> createPresent() {
        return new BasicPresenterImp<>(this);
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
    public void ListSuccess(BasicInfoListDataBean responseBean, int back_page) {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            List<BasicInfoListDataBean.BasicItemBean> mList = responseBean.getData();

            int size = mList == null ? 0 : mList.size();
            if (back_page == 1) {
                mAllList.clear();
                mAdapter = null;
                showNoMoreData(false, dateListview.getRefreshableView());
            }
            if (size > 0) {
                mAllList.addAll(mList);
            }
            if (mAdapter == null) {
                mAdapter = new BasicInfoListAdapter(getActivity(), mList);
                dateListview.setAdapter(mAdapter);
            } else {
                mAdapter.reloadListView(mList, false);
            }
            page = back_page;
            checkLoadOver(size);
            ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
        }
    }

    @Override
    public void ayapplySuccess(AYApplyListDataBean responseBean, int back_page) {

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
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }
}
