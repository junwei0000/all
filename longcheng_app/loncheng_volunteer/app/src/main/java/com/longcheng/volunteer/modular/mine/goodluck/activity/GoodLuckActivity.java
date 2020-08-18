package com.longcheng.volunteer.modular.mine.goodluck.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseListActivity;
import com.longcheng.volunteer.modular.helpwith.energydetail.bean.OpenRedAfterBean;
import com.longcheng.volunteer.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.volunteer.modular.mine.goodluck.adapter.GoodLuckAdapter;
import com.longcheng.volunteer.modular.mine.goodluck.bean.GoodLuckBean;
import com.longcheng.volunteer.modular.mine.goodluck.bean.GoodLuckAfterBean;
import com.longcheng.volunteer.modular.mine.goodluck.bean.GoodLuckListDataBean;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.ListUtils;
import com.longcheng.volunteer.utils.PriceUtils;
import com.longcheng.volunteer.utils.ScrowUtil;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.MyDialog;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 好运来
 */
public class GoodLuckActivity extends BaseListActivity<GoodLuckContract.View, GoodLuckPresenterImp<GoodLuckContract.View>> implements GoodLuckContract.View {

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
    @BindView(R.id.tv_summoney)
    TextView tv_summoney;
    @BindView(R.id.tv_sumskb)
    TextView tv_sumskb;

    @BindView(R.id.iv_onekeyopen)
    ImageView iv_onekeyopen;

    private String user_id;
    private int page = 0;
    private int pageSize = 20;
    private String mutualHelpBlessMeCount;
    GoodLuckAdapter mHomeHotPushAdapter;
    List<GoodLuckBean> helpAllList = new ArrayList<>();
    private int count;
    private double money, skb;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.iv_onekeyopen:
                mPresent.openRedEnvelopeOneKey(user_id);
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.my_goodluck;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("好运来");
        notDateCont.setText("暂无信息噢~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.not_searched_img);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        iv_onekeyopen.setOnClickListener(this);
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
        user_id = UserUtils.getUserId(mContext);
        getList(1);
    }


    private void getList(int page) {
        Log.e("ResponseBody", "user_id=" + user_id + "  ;page=" + page + "  ;pageSize=" + pageSize);
        mPresent.getGoodLuckList(user_id, page, pageSize);
    }


    @Override
    protected GoodLuckPresenterImp<GoodLuckContract.View> createPresent() {
        return new GoodLuckPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        showOpenLoadingDialog();
    }

    @Override
    public void dismissDialog() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openLoadingDialogDismiss();
            }
        }, 500);//秒后执行Runnable中的run方法
    }


    @Override
    public void ListSuccess(GoodLuckListDataBean responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            GoodLuckAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                count = mEnergyAfterBean.getCount();
                money = mEnergyAfterBean.getMoney();
                skb = mEnergyAfterBean.getSkb();
                showBottomView();
                List<GoodLuckBean> starList = mEnergyAfterBean.getSolar_terms_endorsement_star();
                List<GoodLuckBean> helpList = mEnergyAfterBean.getList();
                int size = helpList == null ? 0 : helpList.size();
                if (backPage == 1) {
                    helpAllList.clear();
                    mHomeHotPushAdapter = null;
                    showNoMoreData(false, helpListview.getRefreshableView());
                }
                if (size > 0) {
                    helpAllList.addAll(helpList);
                }
                if (mHomeHotPushAdapter == null) {
                    mHomeHotPushAdapter = new GoodLuckAdapter(mActivity, helpList, starList);
                    helpListview.getRefreshableView().setAdapter(mHomeHotPushAdapter);
                } else {
                    mHomeHotPushAdapter.reloadListView(helpList, false);
                }
                page = backPage;
                ListUtils.getInstance().setNotDateViewL(mHomeHotPushAdapter, layoutNotdate);
                checkLoadOver(size);
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

    private void showBottomView() {
        String showT = "共获得红包<font color=\"#ff0000\">" + count + "</font>个";
        tv_num.setText(Html.fromHtml(showT));
        String showmoney = "已拆现金<font color=\"#ff0000\">" + PriceUtils.getInstance().getPriceTwoDecimalDown(money, 2) + "</font>元";
        tv_summoney.setText(Html.fromHtml(showmoney));
        String showskb = "已拆寿康宝<font color=\"#ff0000\">" + PriceUtils.getInstance().getPriceTwoDecimalDown(skb, 2) + "</font>个";
        tv_sumskb.setText(Html.fromHtml(showskb));
    }

    @Override
    public void OpenRedEnvelopeSuccess(OpenRedDataBean responseBean) {

    }

    @Override
    public void OpenRedEnvelopeOneKeySuccess(OpenRedDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            initDataAfter();
            showEndDialog(responseBean.getData());
        }
    }


    @Override
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
    }

    @Override
    public void onOpenRedEnvelopeError(String msg) {

    }

    MyDialog selectOpenLoadingDialog;

    private void showOpenLoadingDialog() {
        if (selectOpenLoadingDialog == null) {
            selectOpenLoadingDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_openredload);// 创建Dialog并设置样式主题
            selectOpenLoadingDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectOpenLoadingDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectOpenLoadingDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectOpenLoadingDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            selectOpenLoadingDialog.getWindow().setAttributes(p); //设置生效
        } else {
            selectOpenLoadingDialog.show();
        }
    }

    private void openLoadingDialogDismiss() {
        if (selectOpenLoadingDialog != null && selectOpenLoadingDialog.isShowing()) {
            selectOpenLoadingDialog.dismiss();
        }
    }

    MyDialog selectendDialog;

    private void showEndDialog(OpenRedAfterBean responseBean) {
        if (selectendDialog == null) {
            selectendDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_openredend);// 创建Dialog并设置样式主题
            selectendDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectendDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectendDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectendDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            selectendDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_bg = selectendDialog.findViewById(R.id.layout_bg);
            int hei = (int) (p.width * 1.296);
            layout_bg.setLayoutParams(new FrameLayout.LayoutParams(p.width, hei));
            TextView tv_money = selectendDialog.findViewById(R.id.tv_money);
            TextView tv_skb = selectendDialog.findViewById(R.id.tv_skb);
            TextView tv_rednum = selectendDialog.findViewById(R.id.tv_rednum);
            TextView tv_know = selectendDialog.findViewById(R.id.tv_know);
            tv_money.setText(responseBean.getTotalMoney());
            tv_skb.setText(responseBean.getTotalSkb());
            tv_rednum.setText("本次开启红包" + responseBean.getTotalNumber() + "个");
            tv_know.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectendDialog.dismiss();
                }
            });
        } else {
            selectendDialog.show();
        }
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
        filter.addAction(ConstantManager.BroadcastReceiver_GOODLUCK_ACTION);
        mContext.registerReceiver(mReceiver, filter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra("position", 0);
            Log.e("BroadcastReceiver", "position=" + position);
            if (helpAllList != null && helpAllList.size() > 0 && helpAllList.size() >= position && position >= 1) {
                helpAllList.get(position - 1).setRed_packet_status(1);//红包状态: 0 未领取 , 1 已领取
                int type = helpAllList.get(position - 1).getType();
                if (type == 1) {
                    money += helpAllList.get(position - 1).getRed_packet_money();
                } else {
                    skb += helpAllList.get(position - 1).getRed_packet_money();
                }
                showBottomView();
            }
            mHomeHotPushAdapter.refreshListView(helpAllList);
        }
    };

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
