package com.longcheng.lifecareplanTv.home.dynamic.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplanTv.R;
import com.longcheng.lifecareplanTv.api.BasicResponse;
import com.longcheng.lifecareplanTv.base.BaseActivityMVP;
import com.longcheng.lifecareplanTv.home.dynamic.adapter.DynamicAdapter;
import com.longcheng.lifecareplanTv.home.dynamic.adapter.DynamicsAdapter;
import com.longcheng.lifecareplanTv.home.dynamic.bean.DynamicInfo;
import com.longcheng.lifecareplanTv.home.set.SetActivity;
import com.longcheng.lifecareplanTv.home.vedio.adapter.VediosAdapter;
import com.longcheng.lifecareplanTv.login.bean.LoginAfterBean;
import com.longcheng.lifecareplanTv.utils.ConfigUtils;
import com.longcheng.lifecareplanTv.utils.DatesUtils;
import com.longcheng.lifecareplanTv.utils.ToastUtilsNew;
import com.longcheng.lifecareplanTv.utils.myview.SmoothScrollListView;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.SearchResultModel;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvGridLayoutManager;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerView;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerViewList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 动态
 */
public class DynamicActivity extends BaseActivityMVP<DynamicContract.View, DynamicPresenterImp<DynamicContract.View>> implements DynamicContract.View {

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
    @BindView(R.id.ftf_lv)
    TvRecyclerViewList mRecyclerView;

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
        return R.layout.dynamic;
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
        pagetopLayoutSet.setFocusable(false);
//        ftf_lv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                /**
//                 * 点击时 打开页面先运行这个
//                 */
//                if (!hasFocus) {
//                    mAdapter.setSelectItem(-1);
//                } else {
//                    mAdapter.setSelectItem(0);
//                }
//                pageTopTvTime.setFocusable(false);//防止点击上下键还有焦点
//                Log.e("convertView", "   setMoveStatus=  " + hasFocus + "   ");
//            }
//        });
//        ftf_lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                /**
//                 * 使用遥控器 初始化运行这里
//                 */
//                Log.e("convertView", "  setSelectItem ;parent=  " + parent.getCount() + "   " + id);
//                mAdapter.setSelectItem(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                mAdapter.setSelectItem(-1);
//                Log.e("convertView", " onNothingSelected=  " + "   ");
//            }
//        });
//        ftf_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtilsNew.showToast("点击了  " + position);
//                mAdapter.setSelectItem(position);
//            }
//        });
    }

    @Override
    public void initDataAfter() {
        mRecyclerView.setBtnFouse(pagetopLayoutSet);
        //去掉动画,否则当notify数据的时候,焦点会丢失
        mRecyclerView.setItemAnimator(null);
        TvGridLayoutManager mLayoutManager = new TvGridLayoutManager(this, 1);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setSelectedItemOffset(0, 0);
        mRecyclerView.setSelectedItemAtCentered(true);
        mRecyclerView.setOnLoadMoreListener(new TvRecyclerViewList.OnLoadMoreListener() {
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
        mAdapter = new DynamicsAdapter(this , dataList);
        mRecyclerView.setAdapter(mAdapter);
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
    protected DynamicPresenterImp<DynamicContract.View> createPresent() {
        return new DynamicPresenterImp<>(mActivity, this);
    }


    @Override
    public void getMenuInfoSuccess(BasicResponse<LoginAfterBean> responseBean) {

    }

    DynamicsAdapter mAdapter;
    int pageSize = 20;
    private Handler mHandler = new Handler();
    private List<SearchResultModel> dataList = new ArrayList<>();

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
    public void onError() {
        ToastUtilsNew.showToast(getString(R.string.net_tishi));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
