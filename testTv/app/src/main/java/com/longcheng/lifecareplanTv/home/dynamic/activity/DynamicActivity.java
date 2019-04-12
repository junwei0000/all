package com.longcheng.lifecareplanTv.home.dynamic.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
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
import com.longcheng.lifecareplanTv.home.dynamic.bean.DynamicInfo;
import com.longcheng.lifecareplanTv.home.set.SetActivity;
import com.longcheng.lifecareplanTv.login.bean.LoginAfterBean;
import com.longcheng.lifecareplanTv.utils.ConfigUtils;
import com.longcheng.lifecareplanTv.utils.DatesUtils;
import com.longcheng.lifecareplanTv.utils.ToastUtils;
import com.longcheng.lifecareplanTv.utils.myview.SmoothScrollListView;

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
    SmoothScrollListView ftf_lv;

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
        pageTopTvTime.setFocusable(true);//设置无用的view焦点，其他可点击view默认无焦点
        ftf_lv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /**
                 * 点击时 打开页面先运行这个
                 */
                if (!hasFocus) {
                    mAdapter.setSelectItem(-1);
                } else {
                    mAdapter.setSelectItem(0);
                }
                pageTopTvTime.setFocusable(false);//防止点击上下键还有焦点
                Log.e("convertView", "   setMoveStatus=  " + hasFocus + "   ");
            }
        });
        ftf_lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 使用遥控器 初始化运行这里
                 */
                Log.e("convertView", "  setSelectItem ;parent=  " + parent.getCount() + "   " + id);
                mAdapter.setSelectItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mAdapter.setSelectItem(-1);
                Log.e("convertView", " onNothingSelected=  " + "   ");
            }
        });
        ftf_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showToast("点击了  " + position);
                mAdapter.setSelectItem(position);
            }
        });
    }

    @Override
    public void initDataAfter() {
        initD();
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

    DynamicAdapter mAdapter;

    private void initD() {
        List<DynamicInfo> mBottomList = new ArrayList();
        for (int i = 0; i < 30; i++) {
            mBottomList.add(new DynamicInfo());
        }
        mAdapter = new DynamicAdapter(mContext, mBottomList);
        ftf_lv.setAdapter(mAdapter);
        myHandler.sendEmptyMessageDelayed(11, 40);
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:
                    mAdapter.setSelectItem(-1);
                    break;
            }
        }
    };

    @Override
    public void onError() {
        ToastUtils.showToast(getString(R.string.net_tishi));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
