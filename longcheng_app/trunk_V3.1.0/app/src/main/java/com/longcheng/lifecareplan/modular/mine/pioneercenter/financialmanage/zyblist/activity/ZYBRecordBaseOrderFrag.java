package com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.zyblist.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.zyblist.adapter.ZYBRecordListAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.zyblist.bean.ZYBRecordItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.zyblist.bean.ZYBRecordListDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
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

public abstract class ZYBRecordBaseOrderFrag extends BaseListFrag<ZYBRecordContract.View, ZYBRecordPresenterImp<ZYBRecordContract.View>> implements ZYBRecordContract.View {
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
    List<ZYBRecordItemBean> mAllList = new ArrayList<>();
    private ZYBRecordListAdapter mAdapter;


    public static final int INDEX_ALL = 100;
    public static final int INDEX_COMING = 0;
    public static final int INDEX_OVERED = 1;

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
    }

    @Override
    public void doBusiness(Context mContext) {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        getList(1);
    }

    public abstract int getType();

    private void getList(int page) {
        mPresent.getOrderList(user_id, getType(), page, pageSize);
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
    protected ZYBRecordPresenterImp<ZYBRecordContract.View> createPresent() {
        return new ZYBRecordPresenterImp<>(this);
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
    public void ListSuccess(ZYBRecordListDataBean responseBean, int back_page) {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            List<ZYBRecordItemBean> mList = responseBean.getData();

            int size = mList == null ? 0 : mList.size();
            if (back_page == 1) {
                mAllList.clear();
                mAdapter = null;
                showNoMoreData(false, dateListview.getRefreshableView());
            }
            Log.e("checkLoadOver", "pageSize=" + pageSize + "  size=" + size + "  page=" + page + "  getType()=" + getType());
            if (size > 0) {
                mAllList.addAll(mList);
            }
            if (mAdapter == null) {
                mAdapter = new ZYBRecordListAdapter(getActivity(), mList, mHandler);
                dateListview.setAdapter(mAdapter);
            } else {
                mAdapter.reloadListView(mList, false);
            }
            page = back_page;
            checkLoadOver(size);
            ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
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
    public void editSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            ((ZYBRecordListActivity) getActivity()).reLoadList();
        }
    }

    public static final int SUREPAY = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            entrepreneurs_zhufubao_buy_order_id = (String) msg.obj;
            switch (msg.what) {
                case SUREPAY:
                    setDialog(SUREPAY);
                    break;
            }
        }
    };
    String entrepreneurs_zhufubao_buy_order_id;
    MyDialog selectDialog;
    TextView tv_sure;

    public void setDialog(int type) {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_tixiandel);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_off = selectDialog.findViewById(R.id.tv_off);
            tv_sure = selectDialog.findViewById(R.id.tv_sure);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    if (type == SUREPAY) {
                        mPresent.customerConfirmPayment(user_id, entrepreneurs_zhufubao_buy_order_id);
                    }
                }
            });
        } else {
            selectDialog.show();
        }
    }

    @Override
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }
}
