package com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.adapter.ThanksListAdapter;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean.ThanksAfterBean;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean.ThanksItemBean;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean.ThanksListDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.view.FooterNoMoreData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 感恩录
 */
public class ThanksActivity extends BaseListActivity<ThanksContract.View, ThanksPresenterImp<ThanksContract.View>> implements ThanksContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.data_lv)
    MyListView dataLv;
    @BindView(R.id.main_sv)
    PullToRefreshScrollView mainSv;
    @BindView(R.id.tv_cn)
    TextView tvCn;
    @BindView(R.id.tv_cn_line)
    TextView tvCnLine;
    @BindView(R.id.layout_cn)
    LinearLayout layoutCn;
    @BindView(R.id.tv_skb)
    TextView tvSkb;
    @BindView(R.id.tv_skb_line)
    TextView tvSkbLine;
    @BindView(R.id.layout_skb)
    LinearLayout layoutSkb;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;


    private int page = 0;
    private int pageSize = 20;
    private String user_id, order_id;
    private int type;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_cn:
                skbStatus = false;
                changeData();
                break;
            case R.id.layout_skb:
                skbStatus = true;
                changeData();
                break;
        }
    }
    boolean skbStatus = false;
    int help_type=2;
    private void changeData() {
        tvCnLine.setVisibility(View.INVISIBLE);
        tvSkbLine.setVisibility(View.INVISIBLE);
        tvCnLine.setBackgroundResource(R.drawable.corners_bg_blackxingji);
        tvCn.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvSkbLine.setBackgroundResource(R.drawable.corners_bg_blackxingji);
        tvSkb.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        if (skbStatus) {
            help_type=1;
            tvSkbLine.setVisibility(View.VISIBLE);
            tvSkbLine.setBackgroundResource(R.drawable.corners_bg_red);
            tvSkb.setTextColor(getResources().getColor(R.color.red));
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvSkbLine, R.color.red);
        } else {
            help_type=2;
            tvCnLine.setVisibility(View.VISIBLE);
            tvCnLine.setBackgroundResource(R.drawable.corners_bg_red);
            tvCn.setTextColor(getResources().getColor(R.color.red));
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvCnLine, R.color.red);
        }
        getList(1);
    }

    private void getList(int page_) {
        mPresent.getListViewData(user_id, order_id, type, page_, pageSize,help_type);
    }
    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.my_thanks;
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
        pageTopTvName.setText("感恩录");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        layoutCn.setOnClickListener(this);
        layoutSkb.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        ScrowUtil.ScrollViewUpConfig(mainSv);
        mainSv.setBottomTextColor(getResources().getColor(R.color.white));
        mainSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getList(page+1);
            }
        });
        dataLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ResponseBody", "onItemClick==" + position);
                if (rankingsAll != null && rankingsAll.size() > 0 && position < rankingsAll.size()) {
                    if (type == 2) {
                        Intent intent = new Intent(mContext, HelpWithEnergyActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("name", rankingsAll.get(position).getUser_name());
                        intent.putExtra("skiptype", "Thanks");
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, LifeStyleActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("name", rankingsAll.get(position).getUser_name());
                        intent.putExtra("skiptype", "Thanks");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        type = intent.getIntExtra("type", 0);
        if (type == 2||type ==4) {
            layoutTop.setVisibility(View.GONE);
        } else {
            layoutTop.setVisibility(View.VISIBLE);
        }
        getList(1);
    }


    @Override
    protected ThanksPresenterImp<ThanksContract.View> createPresent() {
        return new ThanksPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    List<ThanksItemBean> rankingsAll = new ArrayList<>();

    @Override
    public void ListSuccess(ThanksListDataBean responseBean, int back_page) {
        ListUtils.getInstance().RefreshCompleteS(mainSv);
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ThanksAfterBean mBean = responseBean.getData();
            if (mBean != null) {
                tvName.setText("亲爱的CHO" + mBean.getUser_name() + "：");
                tvSum.setText("" + mBean.getTotal());
                List<ThanksItemBean> rankings = mBean.getRankings();
                int size = rankings == null ? 0 : rankings.size();
                if (back_page == 1) {
                    rankingsAll.clear();
                    mThanksListAdapter = null;
                }
                if (size > 0) {
                    rankingsAll.addAll(rankings);
                }
                if (mThanksListAdapter == null) {
                    mThanksListAdapter = new ThanksListAdapter(mContext, rankings, type);
                    dataLv.setAdapter(mThanksListAdapter);
                } else {
                    mThanksListAdapter.reloadListView(rankings, false);
                }
                page = back_page;
                checkLoadOver(size);
            }
        }
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.ScrollViewConfigDismiss(mainSv, false);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, dataLv);
            }
        } else {
            ScrowUtil.ScrollViewUpConfig(mainSv, false);
        }
    }

    ThanksListAdapter mThanksListAdapter;

    @Override
    public void ListError() {
        ListUtils.getInstance().RefreshCompleteS(mainSv);
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
