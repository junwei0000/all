package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.FuQRepListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ListItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.MyCircleActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class FuQListActivity extends BaseListActivity<ReportContract.View, ReportPresenterImp<ReportContract.View>> implements ReportContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.help_listview)
    PullToRefreshListView helpListview;
    @BindView(R.id.vp)
    ViewFlipper vp;
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
    @BindView(R.id.tv_myquan)
    TextView tv_myquan;



    private int page = 0;
    private int pageSize = 20;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_myquan:
                Intent intent = new Intent(mActivity, MyCircleActivity.class);
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
        return R.layout.fuqrep_list;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        notDateCont.setText("暂无数据~");
        notDateBtn.setVisibility(View.GONE);
        notDateImg.setBackgroundResource(R.mipmap.my_nodata_icon);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tv_myquan.setOnClickListener(this);
        helpListview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

    int type;//1请祈福 2领福利  3节气  4日期
    String keyword;

    @Override
    public void initDataAfter() {
        type = getIntent().getIntExtra("type", 1);
        keyword = getIntent().getStringExtra("keyword");
        String keywordname = getIntent().getStringExtra("keywordname");
        if (type == 3) {
            pageTopTvName.setText(keywordname);
        } else {
            pageTopTvName.setText(keyword);
        }
        if (type == 1 || type == 2) {
            vp.setVisibility(View.VISIBLE);
        } else {
            vp.setVisibility(View.GONE);
        }
        if(type==2){
            tv_myquan.setVisibility(View.VISIBLE);
        }else{
            tv_myquan.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(1);
    }

    private void getList(int page) {
        mPresent.getFuQRepList(type, keyword, page, pageSize);
    }


    @Override
    protected ReportPresenterImp<ReportContract.View> createPresent() {
        return new ReportPresenterImp<>(mContext, this);
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

    private void RefreshComplete() {
        ListUtils.getInstance().RefreshCompleteL(helpListview);
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

    FuQRepListAdapter mAdapter;

    @Override
    public void ListSuccess(ReportDataBean responseBean, int backPage) {
        RefreshComplete();
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ReportAfterBean mDaiFuAfterBean = responseBean.getData();
            if (mDaiFuAfterBean != null) {
                List<ListItemBean> blessCards = mDaiFuAfterBean.getBlessCards();
                if (type == 1 || type == 2) {
                    fillView(mDaiFuAfterBean.getBroadcastLists());
                }
                int size = blessCards == null ? 0 : blessCards.size();
                if (backPage == 1) {
                    if (mAdapter != null) {
                        mAdapter.clearTimer();
                        mAdapter = null;
                    }
                    showNoMoreData(false, helpListview.getRefreshableView());
                }
                if (size > 0) {
                }
                if (mAdapter == null) {
                    mAdapter = new FuQRepListAdapter(mActivity, blessCards, type, mHandler);
                    helpListview.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(blessCards, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
        }
        ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
    }

    @Override
    public void DetailSuccess(FQDetailDataBean responseBean) {

    }

    @Override
    public void lingQuSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            refreshStatus = true;
            getList(1);
            if(mJieQiUtils==null){
                mJieQiUtils=new JieQisUtils(mActivity);
            }
            mJieQiUtils.showFQBialog();
        }
    }

    JieQisUtils mJieQiUtils;

    @Override
    public void Error() {
        RefreshComplete();
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String bless_id = (String) msg.obj;
            switch (msg.what) {
                case 1:
                    mPresent.getLingQu(UserUtils.getUserId(mContext), bless_id);
                    break;
            }
        }
    };

    private void fillView(List<ListItemBean> broadcastLists) {
        vp.removeAllViews();
        for (int i = 0; i < broadcastLists.size(); i++) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.text_item3, null);
            TextView textView = view.findViewById(R.id.tv_cont);
            TextView tv_cont2 = view.findViewById(R.id.tv_cont2);
            ImageView iv_head = view.findViewById(R.id.iv_head);
            GlideDownLoadImage.getInstance().loadCircleImage(broadcastLists.get(i).getAvatar(), iv_head);
            textView.setTextColor(mActivity.getResources().getColor(R.color.white));
            tv_cont2.setTextColor(mActivity.getResources().getColor(R.color.white));
            if (type == 1) {
                textView.setText(broadcastLists.get(i).getUser_name() + "申请了福券" + broadcastLists.get(i).getApply_number() + "张");
            } else if (type == 2) {
                textView.setText(broadcastLists.get(i).getUser_name() + "领取福券" + broadcastLists.get(i).getApply_number() + "张");
            }
            tv_cont2.setText("");
            vp.addView(view);
        }
        //是否自动开始滚动
        vp.setAutoStart(true);
        //滚动时间
        vp.setFlipInterval(2600);
        //开始滚动
        vp.startFlipping();
        //出入动画
        vp.setOutAnimation(mActivity, R.anim.push_bottom_outvp);
        vp.setInAnimation(mActivity, R.anim.push_bottom_in);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.clearTimer();
            mAdapter = null;
        }
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
