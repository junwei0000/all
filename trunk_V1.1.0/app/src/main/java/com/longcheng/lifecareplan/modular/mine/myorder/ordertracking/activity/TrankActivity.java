package com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.adapter.TrankListAdapter;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.bean.TrankAfterBean;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.bean.TrankItemBean;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.bean.TrankListDataBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.utils.myview.MyListView;

import java.util.List;

import butterknife.BindView;

/**
 * 物流跟踪
 */
public class TrankActivity extends BaseActivityMVP<TrankContract.View, TrankPresenterImp<TrankContract.View>> implements TrankContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.data_lv)
    MyListView dataLv;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_notdate)
    TextView tv_notdate;
    private String user_id, order_id;

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
        return R.layout.my_ordertrank;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("订单跟踪");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        user_id = UserUtils.getUserId(mContext);
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        mPresent.getListViewData(user_id, order_id);
    }

    @Override
    public void initDataAfter() {

    }


    @Override
    protected TrankPresenterImp<TrankContract.View> createPresent() {
        return new TrankPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void ListSuccess(TrankListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            TrankAfterBean mBean = responseBean.getData();
            if (mBean != null) {
                dataLv.setFocusable(false);
                tv_name.setText(Html.fromHtml("物流公司：<font color=\"#464646\">" + mBean.getShipping_name() + "</font>"));
                tv_num.setText(Html.fromHtml("物流单号：<font color=\"#464646\">" + mBean.getShipping_code() + "</font>"));
                List<TrankItemBean> rankings = mBean.getTraces();
                if (rankings != null && rankings.size() > 0) {
                    dataLv.setVisibility(View.VISIBLE);
                } else {
                    tv_notdate.setVisibility(View.VISIBLE);
                }
                if (mThanksListAdapter == null) {
                    mThanksListAdapter = new TrankListAdapter(mContext, rankings);
                    dataLv.setAdapter(mThanksListAdapter);
                } else {
                    mThanksListAdapter.reloadListView(rankings, false);
                }

            }
        }
    }

    TrankListAdapter mThanksListAdapter;

    @Override
    public void ListError() {
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
