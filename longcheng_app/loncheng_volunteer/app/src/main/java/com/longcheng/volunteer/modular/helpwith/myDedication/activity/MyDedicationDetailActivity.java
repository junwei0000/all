package com.longcheng.volunteer.modular.helpwith.myDedication.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseListActivity;
import com.longcheng.volunteer.modular.helpwith.myDedication.adapter.MyDetailAdapter;
import com.longcheng.volunteer.modular.helpwith.myDedication.bean.ItemBean;
import com.longcheng.volunteer.modular.helpwith.myDedication.bean.MyDedicationAfterBean;
import com.longcheng.volunteer.modular.helpwith.myDedication.bean.MyDedicationListDataBean;
import com.longcheng.volunteer.utils.ListUtils;
import com.longcheng.volunteer.utils.ScrowUtil;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.volunteer.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的奉献明细
 */
public class MyDedicationDetailActivity extends BaseListActivity<MyDedicationContract.View, MyDedicationPresenterImp<MyDedicationContract.View>> implements MyDedicationContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;

    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_sum)
    TextView tv_sum;
    @BindView(R.id.layout_bottom)
    LinearLayout layout_bottom;


    private String user_id;
    private int page = 0;
    private int pageSize = 20;
    MyDetailAdapter mHomeHotPushAdapter;
    List<ItemBean> helpAllList = new ArrayList<>();
    private String h_user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_mydedication;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("我的奉献");
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        layout_bottom.setVisibility(View.GONE);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        helpListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getList(page + 1);
            }
        });
    }


    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        h_user_id = intent.getStringExtra("h_user_id");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        getList(1);
    }


    private void getList(int page) {
        mPresent.getDetail(user_id, h_user_id, page, pageSize);
    }


    @Override
    protected MyDedicationPresenterImp<MyDedicationContract.View> createPresent() {
        return new MyDedicationPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void ListSuccess(MyDedicationListDataBean responseBean, int back_page) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            MyDedicationAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                List<ItemBean> helpList = mEnergyAfterBean.getMutualHelpMyBlessDetails();
                int size = helpList == null ? 0 : helpList.size();
                if (back_page == 1) {
                    helpAllList.clear();
                    mHomeHotPushAdapter = null;
                    showNoMoreData(false, helpListview.getRefreshableView());
                }
                if (size > 0) {
                    helpAllList.addAll(helpList);
                }
                if (mHomeHotPushAdapter == null) {
                    mHomeHotPushAdapter = new MyDetailAdapter(mContext, helpList);
                    helpListview.setAdapter(mHomeHotPushAdapter);
                } else {
                    mHomeHotPushAdapter.reloadListView(helpList, false);
                }
                page = back_page;
                checkLoadOver(size);
                ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
            }
        }
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
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
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
