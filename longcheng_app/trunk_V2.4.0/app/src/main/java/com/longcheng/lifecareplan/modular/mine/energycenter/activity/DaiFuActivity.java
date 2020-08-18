package com.longcheng.lifecareplan.modular.mine.energycenter.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.adapter.DaiFusAdapter;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuAfterBean;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuItemBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 能量中心--代付
 */
public class DaiFuActivity extends BaseListActivity<EnergyCenterContract.View, EnergyCenterPresenterImp<EnergyCenterContract.View>> implements EnergyCenterContract.View {


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
    private int page = 0;
    private int pageSize = 20;

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
        return R.layout.energycenter_daifu;
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
        pageTopTvName.setText("代付");
        notDateCont.setText("暂无代付数据");
        pagetopLayoutLeft.setOnClickListener(this);
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
        getList(1);
    }

    private void getList(int page) {
        mPresent.getDaiFuList(page, pageSize);
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
                    if (type == Refuse) {
                        mPresent.refuse(user_zhufubao_order_id);
                    } else if (type == RefuseTiXian) {
                        mPresent.cashOrderConsultStatusBless(user_zhufubao_order_id, -1);
                    } else if (type == ConfirmTiXian) {
                        mPresent.cashOrderConsultStatusBless(user_zhufubao_order_id, 1);
                    }
                }
            });
        } else {
            selectDialog.show();
        }
        if (type == Refuse) {
            tv_title.setText("请再次确认您的操作，是否继续？");
            tv_title2.setVisibility(View.GONE);
            tv_title3.setVisibility(View.GONE);
            tv_title.setTextColor(getResources().getColor(R.color.text_healthcontents_color));
        } else if (type == RefuseTiXian) {
            tv_title.setText("请与用户协商");
            tv_title2.setText("双方同时撤销提现后");
            tv_title3.setText("本次代付订单关闭");
            tv_title2.setVisibility(View.VISIBLE);
            tv_title3.setVisibility(View.VISIBLE);
            tv_title.setTextColor(getResources().getColor(R.color.red));
        } else if (type == ConfirmTiXian) {
            tv_title.setText("请与用户协商");
            tv_title2.setText("双方同时完成提现后");
            tv_title3.setText("本次代付订单关闭");
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
    public static final int Refuse = 1;
    public static final int blessPaymentConfirm = 2;
    public static final int RefuseTiXian = 3;
    public static final int ConfirmTiXian = 4;
    public static final int REFRESH = 5;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Refuse:
                    type = Refuse;
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    setDialog();
                    break;
                case blessPaymentConfirm:
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    Intent intents = new Intent(mContext, DaiFuConfirmActivity.class);
                    intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intents.putExtra("user_zhufubao_order_id", "" + user_zhufubao_order_id);
                    startActivity(intents);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intents, mActivity);
                    break;
                case RefuseTiXian:
                    type = RefuseTiXian;
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    setDialog();
                    break;
                case ConfirmTiXian:
                    type = ConfirmTiXian;
                    user_zhufubao_order_id = (String) msg.obj;
                    clickIndex = msg.arg1;
                    setDialog();
                    break;
                case REFRESH:
                    getList(1);
                    break;
            }
        }
    };

    @Override
    protected EnergyCenterPresenterImp<EnergyCenterContract.View> createPresent() {
        return new EnergyCenterPresenterImp<>(mActivity, this);
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

    DaiFusAdapter mDaiFuAdapter;
    List<DaiFuItemBean> allList = new ArrayList<>();

    @Override
    public void ListSuccess(DaiFuDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(lvData);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            DaiFuAfterBean mDaiFuAfterBean = responseBean.getData();
            if (mDaiFuAfterBean != null) {
                List<DaiFuItemBean> helpList = mDaiFuAfterBean.getLists();
                int size = helpList == null ? 0 : helpList.size();
                if (backPage == 1) {
                    mDaiFuAdapter = null;
                    allList.clear();
                    showNoMoreData(false, lvData.getRefreshableView());
                }
                if (size > 0) {
                    allList.addAll(helpList);
                }
                if (mDaiFuAdapter == null) {
                    mDaiFuAdapter = new DaiFusAdapter(mContext, helpList, mHandler);
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
    public void RefuseSuccess(EditListDataBean responseBean) {
        String status = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status.equals("200")) {
            if (allList != null && allList.size() > 0) {
                if (type == Refuse) {
                    allList.get(clickIndex).setBless_user_status(-1);
                } else if (type == RefuseTiXian) {
                    allList.get(clickIndex).setConsult_status(-1);
                } else if (type == ConfirmTiXian) {
                    allList.get(clickIndex).setConsult_status(1);
                }
                mDaiFuAdapter.refreshListView(allList);
            }
        }
    }

    @Override
    public void editAvatarSuccess(EditDataBean responseBean) {

    }

    @Override
    public void backBankInfoSuccess(DaiFuDataBean responseBean) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("AiYouRecordListActivity");
        mContext.registerReceiver(mReceiver, filter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("onActivityResult", "resultCode=");
            mHandler.sendEmptyMessage(REFRESH);
        }
    };
}
