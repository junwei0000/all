package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.dc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.PionDCContract;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.PionDCPresenterImp;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.adapter.PionDaiCAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.bean.PionDaiCItemBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创业中心--代充
 */
public abstract class PionDaiCBaseFrag extends BaseListFrag<PionDCContract.View, PionDCPresenterImp<PionDCContract.View>> implements PionDCContract.View {


    @BindView(R.id.date_listview)
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
    private int page = 0;
    private int pageSize = 20;

    public static final int INDEX_ALL = 0;
    public static final int INDEX_COMING = 1;
    public static final int INDEX_CANCEL = -1;
    public static final int INDEX_OVERED = 2;

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
        notDateCont.setText("暂无敬售数据");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
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
    public void doBusiness(Context mContext) {
        getList(1);
    }

    @Override
    public void widgetClick(View v) {

    }

    public abstract int getType();


    private void getList(int page) {
        mPresent.getDaiCList(page, pageSize, getType());
    }

    MyDialog selectDialog;
    TextView tv_title, tv_title2, tv_title3;

    public void setDialog() {
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
            tv_title = selectDialog.findViewById(R.id.tv_title);
            tv_title2 = selectDialog.findViewById(R.id.tv_title2);
            tv_title3 = selectDialog.findViewById(R.id.tv_title3);
            TextView tv_off = selectDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectDialog.findViewById(R.id.tv_sure);
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
                    if (type == RefuseDaiC) {
                        mPresent.PiondaiCRefuse(user_zhufubao_order_id);
                    } else if (type == RefuseOrder) {
                        mPresent.PionDaiCConsult(user_zhufubao_order_id, -1);
                    } else if (type == ConfirmOrder) {
                        mPresent.PionDaiCConsult(user_zhufubao_order_id, 1);
                    }
                }
            });
        } else {
            selectDialog.show();
        }
        if (type == RefuseDaiC) {
            tv_title.setText("请再次确认您的操作，是否继续？");
            tv_title2.setVisibility(View.GONE);
            tv_title3.setVisibility(View.GONE);
            tv_title.setTextColor(getResources().getColor(R.color.text_healthcontents_color));
        } else if (type == RefuseOrder) {
            tv_title.setText("请与用户协商");
            tv_title2.setText("双方同时撤销订单后");
            tv_title3.setText("本次订单关闭");
            tv_title2.setVisibility(View.VISIBLE);
            tv_title3.setVisibility(View.VISIBLE);
            tv_title.setTextColor(getResources().getColor(R.color.red));
        } else if (type == ConfirmOrder) {
            tv_title.setText("请与用户协商");
            tv_title2.setText("双方同时完成订单后");
            tv_title3.setText("本次订单完成");
            tv_title2.setVisibility(View.VISIBLE);
            tv_title3.setVisibility(View.VISIBLE);
            tv_title.setTextColor(getResources().getColor(R.color.red));
        }
    }

    /**
     * 点击操作功能类型
     */
    private int type;
    int clickIndex;
    String user_zhufubao_order_id;
    public static final int RefuseApply = 1;
    public static final int AgreeApply = 2;
    public static final int RefuseDaiC = 3;
    public static final int AgreeDaiC = 4;
    public static final int RefuseOrder = 5;
    public static final int ConfirmOrder = 6;
    public static final int REFRESH = 7;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RefuseApply:
                    type = RefuseApply;
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    mPresent.RefuseApply(user_zhufubao_order_id);
                    break;
                case AgreeApply:
                    type = AgreeApply;
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    mPresent.AgreeApply(user_zhufubao_order_id);
                    break;
                case RefuseDaiC:
                    type = RefuseDaiC;
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    setDialog();
                    break;
                case AgreeDaiC:
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    Intent intents = new Intent(mContext, PionDaiCConfirmActivity.class);
                    intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intents.putExtra("user_zhufubao_order_id", "" + user_zhufubao_order_id);
                    startActivity(intents);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intents, mActivity);
                    break;
                case RefuseOrder:
                    type = RefuseOrder;
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    setDialog();
                    break;
                case ConfirmOrder:
                    type = ConfirmOrder;
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    setDialog();
                    break;
                case REFRESH:
                    ((PionDaiCActivity) getActivity()).reLoadList();
                    break;
            }
        }
    };

    @Override
    protected PionDCPresenterImp<PionDCContract.View> createPresent() {
        return new PionDCPresenterImp<>(mActivity, this);
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

    PionDaiCAdapter mDaiFuAdapter;
    List<PionDaiCItemBean> allList = new ArrayList<>();

    @Override
    public void ListSuccess(PionDaiCDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(lvData);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            PionDaiCAfterBean mDaiFuAfterBean = responseBean.getData();
            if (mDaiFuAfterBean != null) {
                List<PionDaiCItemBean> helpList = mDaiFuAfterBean.getLists();
                int size = helpList == null ? 0 : helpList.size();
                if (backPage == 1) {
                    if (mDaiFuAdapter != null) {
                        mDaiFuAdapter.clearTimer();
                    }
                    mDaiFuAdapter = null;
                    allList.clear();
                    showNoMoreData(false, lvData.getRefreshableView());
                }
                if (size > 0) {
                    allList.addAll(helpList);
                }
                if (mDaiFuAdapter == null) {
                    mDaiFuAdapter = new PionDaiCAdapter(mContext, helpList, mHandler);
                    lvData.setAdapter(mDaiFuAdapter);
                } else {
                    mDaiFuAdapter.reloadListView(helpList, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
        }
        ListUtils.getInstance().setNotDateViewL(mDaiFuAdapter, layoutNotdate);
    }

    @Override
    public void RefuseSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status.equals("200")) {
            mHandler.sendEmptyMessage(REFRESH);
        }
    }

    @Override
    public void editAvatarSuccess(EditDataBean responseBean) {

    }

    @Override
    public void backBankInfoSuccess(PionDaiCDataBean responseBean) {

    }

    @Override
    public void BuySuccess(PayWXDataBean responseBean) {

    }

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
        ListUtils.getInstance().RefreshCompleteL(lvData);
        ToastUtils.showToast(R.string.net_tishi);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDaiFuAdapter != null) {
            mDaiFuAdapter.clearTimer();
        }
    }
}
