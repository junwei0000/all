package com.longcheng.lifecareplan.home.vedio.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.BasicResponse;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.home.dynamic.adapter.DynamicAdapter;
import com.longcheng.lifecareplan.home.dynamic.bean.DynamicInfo;
import com.longcheng.lifecareplan.home.set.SetActivity;
import com.longcheng.lifecareplan.home.vedio.adapter.VediosAdapter;
import com.longcheng.lifecareplan.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SmoothScrollListView;
import com.longcheng.lifecareplan.utils.tvrecyclerview.GuessYouLikeAdapter;
import com.longcheng.lifecareplan.utils.tvrecyclerview.SearchResultModel;
import com.longcheng.lifecareplan.utils.tvrecyclerview.TvGridLayoutManager;
import com.longcheng.lifecareplan.utils.tvrecyclerview.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 视频
 */
public class VediosActivity extends BaseActivityMVP<VediosContract.View, VediosPresenterImp<VediosContract.View>> implements VediosContract.View {

    @BindView(R.id.pageTop_tv_time)
    TextView pageTopTvTime;
    @BindView(R.id.pageTop_tv_date)
    TextView pageTopTvDate;
    @BindView(R.id.pageTop_tv_week)
    TextView pageTopTvWeek;
    @BindView(R.id.pagetop_layout_set)
    LinearLayout pagetopLayoutSet;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pageTop_tv_phone)
    TextView pageTopTvPhone;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.pageTop_iv_thumb)
    ImageView pageTopIvThumb;
    @BindView(R.id.search_result_recyclerView)
    TvRecyclerView mRecyclerView;
    private TvGridLayoutManager mLayoutManager;
    private List<SearchResultModel> dataList = new ArrayList<>();
    private VediosAdapter mAdapter;
    private Handler mHandler = new Handler();

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.vedio;
    }

    @Override
    public void initView(View view) {
        initTimer();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setListener() {
        pagetopLayoutRigth.setVisibility(View.VISIBLE);
        pagetopLayoutSet.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setSelectFouseText(pagetopLayoutSet);
        pageTopTvTime.setFocusable(true);//设置无用的view焦点，其他可点击view默认无焦点
        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pageTopTvTime.setFocusable(false);//防止点击上下键还有焦点
            }
        });
    }

    @Override
    public void initDataAfter() {
        mRecyclerView.setFocusable(false);
        //去掉动画,否则当notify数据的时候,焦点会丢失
        mRecyclerView.setItemAnimator(null);
        mLayoutManager = new TvGridLayoutManager(this, 4);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setSelectedItemOffset(180, 180);

        mRecyclerView.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //Toast.makeText(VedioActivity.this,"翻页加载更多中...",Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addDataList(getMoreData());
                        mAdapter.notifyDataSetChanged();
                    }
                }, 200);
            }
        });
        initData();
        mAdapter = new VediosAdapter(this, mRecyclerView, mLayoutManager, dataList);
        mRecyclerView.setAdapter(mAdapter);
    }

    int pageSize = 8;

    public void initData() {
        for (int i = 0; i < pageSize; i++) {
            dataList.add(new SearchResultModel(i + 1, "title : " + (i + 1)));
        }
    }

    public List<SearchResultModel> getMoreData() {
        List<SearchResultModel> list = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            list.add(new SearchResultModel(i + 1, "title : " + (i + 1)));
        }
        return list;
    }

    @Override
    public void setDateInfo() {
        String date = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
        pageTopTvDate.setText(date);
        String week = DatesUtils.getInstance().getNowTime("EE");
        pageTopTvWeek.setText(week);
        String time = DatesUtils.getInstance().getNowTime("HH:mm");
        pageTopTvTime.setText(time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_set:
                Intent intent = new Intent(this, SetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected VediosPresenterImp<VediosContract.View> createPresent() {
        return new VediosPresenterImp<>(mActivity, this);
    }


    @Override
    public void getMenuInfoSuccess(BasicResponse<LoginAfterBean> responseBean) {

    }


    @Override
    public void onError() {
        ToastUtils.showToast(getString(R.string.net_tishi));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return false;
    }
}
