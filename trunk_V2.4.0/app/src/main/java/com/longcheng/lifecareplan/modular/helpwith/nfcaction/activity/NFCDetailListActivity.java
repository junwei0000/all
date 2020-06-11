package com.longcheng.lifecareplan.modular.helpwith.nfcaction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.adapter.DetailListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.adapter.NFCDetailRecordAdapter;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailListDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * nfc行动-
 */
public class NFCDetailListActivity extends BaseListActivity<NFCDetailContract.View, NFCDetailPresenterImp<NFCDetailContract.View>> implements NFCDetailContract.View {


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
    @BindView(R.id.btn_engry)
    TextView btn_engry;

    String order_id, nfc_sn, msg_id;
    private int page = 0;
    private int page_size = 20;
    boolean refreshStatus = false;
    NFCDetailItemBean userInfo;
    NFCDetailHelpDialogUtils mDetailHelpDialogUtils;
    List<NFCDetailItemBean> mutual_help_money_all = new ArrayList<>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_engry:
                if (mutual_help_money_all != null && mutual_help_money_all.size() > 0) {
                    if (mDetailHelpDialogUtils == null) {
                        mDetailHelpDialogUtils = new NFCDetailHelpDialogUtils(mActivity, mHandler, BLESSING);
                    }
                    mDetailHelpDialogUtils.initData(userInfo, mutual_help_money_all);
                    mDetailHelpDialogUtils.showPopupWindow();
                }
                break;
        }
    }

    public static final int BLESSING = 22;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BLESSING:
                    Bundle bundle = msg.getData();
                    int payType = bundle.getInt("payType");
                    String mutual_help_money_id = bundle.getString("mutual_help_money_id");
                    mPresent.payHelp(UserUtils.getUserId(mContext), msg_id, order_id, payType, mutual_help_money_id, nfc_sn);
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
        return R.layout.nfc_list;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("祝福列表");
        notDateCont.setText("暂无数据");
        notDateBtn.setVisibility(View.GONE);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        btn_engry.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
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

    private void getList(int page) {
        mPresent.getDetailList(order_id, page, page_size);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        nfc_sn = intent.getStringExtra("nfc_sn");
        mPresent.getDetail(order_id, nfc_sn);
        getList(1);
    }

    @Override
    protected NFCDetailPresenterImp<NFCDetailContract.View> createPresent() {
        return new NFCDetailPresenterImp<>(mContext, this);
    }

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
    public void DetailSuccess(NFCDetailDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            NFCDetailAfterBean mNFCDetailAfterBean = responseBean.getData();
            NFCDetailItemBean actioninfo = mNFCDetailAfterBean.getMsg();
            msg_id = actioninfo.getId();
            userInfo = mNFCDetailAfterBean.getCurrentUser();
            mutual_help_money_all = mNFCDetailAfterBean.getMutual_help_money_all();
            Log.e("mutual_help_money_all", "" + mutual_help_money_all.size());
        }
    }

    DetailListAdapter mAdapter;

    @Override
    public void DetailListSuccess(NFCDetailDataBean responseBean, int backpage) {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            NFCDetailAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                ArrayList<NFCDetailItemBean> helpList = mEnergyAfterBean.getHelpAbilityRanking();
                int size = helpList == null ? 0 : helpList.size();
                if (backpage == 1) {
                    mAdapter = null;
                    showNoMoreData(false, dateListview.getRefreshableView());
                }
                if (mAdapter == null) {
                    mAdapter = new DetailListAdapter(mActivity, helpList);
                    dateListview.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(helpList, false);
                }
                page = backpage;
                checkLoadOver(size);
            }
        }
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
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
    public void PayHelpSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            mPresent.getDetail(order_id, nfc_sn);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void DetailRecordSuccess(NFCDetailListDataBean responseBean) {

    }

    @Override
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
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
