package com.longcheng.lifecareplan.modular.mine.myorder.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.longcheng.lifecareplan.base.BaseListFrag;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedItemBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity.LifeStyleDetailActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.myorder.adapter.OrderListAdapter;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderAfterBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderItemBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.detail.activity.OrderDetailActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
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

public abstract class BaseOrderFrag extends BaseListFrag<MyOrderContract.View, MyOrderPresenterImp<MyOrderContract.View>> implements MyOrderContract.View {
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
    private int count;
    List<OrderItemBean> mAllList = new ArrayList<>();
    private OrderListAdapter mAdapter;


    public static final int INDEX_ALL = 0;
    public static final int INDEX_COMING = 1;
    public static final int INDEX_PENDING = 2;
    public static final int INDEX_OVERED = 3;
    public static final int INDEX_YAJIN = 4;

    @Override
    public int bindLayout() {
        return R.layout.my_order_fragment;
    }

    @Override
    protected View getFooterView() {
        FooterNoMoreData view = new FooterNoMoreData(mContext);
        view.showDividerTop(true);
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        notDateCont.setText("暂无订单噢~");
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
                if (mAllList != null && mAllList.size() > 0 && (position - 1) < mAllList.size()) {
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("order_id", mAllList.get(position - 1).getOrder_id());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        getList(1);
    }

    public abstract int getType();
//    private void init() {
//        ScrowUtil.listViewConfigAll(dateListview);
//        page = 1;
//        mAdapter = null;
//        mAllList.clear();
//    }

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
    protected MyOrderPresenterImp<MyOrderContract.View> createPresent() {
        return new MyOrderPresenterImp<>(this);
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
    public void ListSuccess(OrderListDataBean responseBean, int back_page) {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            OrderAfterBean mOrderAfterBean = responseBean.getData();
            if (mOrderAfterBean != null) {
                count = mOrderAfterBean.getCount();
                List<OrderItemBean> mList = mOrderAfterBean.getOrders();

                int size = mList == null ? 0 : mList.size();
                if (back_page == 1) {
                    mAllList.clear();
                    mAdapter = null;
                    showNoMoreData(false, dateListview.getRefreshableView());
                }Log.e("checkLoadOver","pageSize="+pageSize+"  size="+size+"  page="+page+"  getType()="+getType());
                if (size > 0) {
                    mAllList.addAll(mList);
                }
                if (mAdapter == null) {
                    mAdapter = new OrderListAdapter(getActivity(), mList, mHandler);
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
    public void getNeedHelpNumberTaskSuccess(ActionDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ActionItemBean mBean = responseBean.getData();
            if (mBean != null) {
                //状态 0：没有要做的任务 ;1， 有要去互祝的任务; 2，有未读的任务
                String apply_status = mBean.getApply_status();
                String need_help_number = mBean.getRemain_number();
                String redirectMsgId = mBean.getMsg_id();
                if (!apply_status.equals("0")) {
                    if (!need_help_number.equals("0")) {
                        //申请成功后做任务跳转msgid   0：跳转到列表页   非0：跳转到行动详情页
                        Intent intent;
                        if (redirectMsgId.equals("0")) {
                            intent = new Intent(mContext, HelpWithEnergyActivity.class);
                            intent.putExtra("skiptype", "Order");
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        } else {
                            intent = new Intent(mContext, DetailActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("msg_id", redirectMsgId);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        }
                    }
                }
            }

        }
    }

    @Override
    public void editSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            getList(1);
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String order_id = (String) msg.obj;
            switch (msg.what) {
                case ConstantManager.ORDER_HANDLE_cancelAction:
                    mPresent.cancelAction(user_id, order_id);
                    break;
                case ConstantManager.ORDER_HANDLE_ConfirmReceipt:
                    mPresent.confirmReceipt(user_id, order_id);
                    break;
                case ConstantManager.ORDER_HANDLE_SendBlessing:
                    mPresent.getNeedHelpNumberTask(user_id);
                    break;
                case ConstantManager.ORDER_HANDLE_SendBlessingLifeStyle:
                    mPresent.getLifeNeedHelpNumberTaskSuccess(user_id, order_id + "");
                    break;
            }
        }
    };
    private int is_read = 1;//是否已读： 1 已读
    private int need_help_number = 1;//数量
    /**
     * 任务ID
     */
    String redirectMsgId;

    @Override
    public void getLifeStyleNeedHelpNumberTaskSuccess(LifeNeedDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            LifeNeedItemBean mPeopleAfterBean = responseBean.getData();
            if (mPeopleAfterBean != null) {
                need_help_number = mPeopleAfterBean.getNeedhelpGoodsnumber();
                is_read = mPeopleAfterBean.getIs_read();
                if (need_help_number > 0) {
                    LifeNeedItemBean appointHelpGoods = mPeopleAfterBean.getAppointHelpGoods();
                    if (appointHelpGoods != null) {
                        redirectMsgId = appointHelpGoods.getRedirectMsgId();//互祝help_goods_id
                    }
                } else if (is_read == 0 && need_help_number == 0) {
                    LifeNeedItemBean appointHelpGoods = mPeopleAfterBean.getApplySuccess();
                    if (appointHelpGoods != null) {
                        redirectMsgId = appointHelpGoods.getRedirectMsgId();//自己help_goods_id
                    }
                }
                Intent intent;
                if (need_help_number > 0) {
                    //申请成功后做任务跳转msgid   0：跳转到列表页   非0：跳转到行动详情页
                    if (redirectMsgId.equals("0")) {
                        intent = new Intent(mContext, LifeStyleActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("skiptype", "LifeApplyHelp");
                        startActivity(intent);
                    } else {
                        intent = new Intent(mContext, LifeStyleDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("help_goods_id", "" + redirectMsgId);
                        startActivity(intent);
                    }
                } else {
                    intent = new Intent(mContext, LifeStyleDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("help_goods_id", "" + redirectMsgId);
                    startActivity(intent);
                }

            }

        }
    }

    @Override
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(dateListview);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }
}
