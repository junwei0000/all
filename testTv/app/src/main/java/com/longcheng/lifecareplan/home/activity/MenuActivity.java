package com.longcheng.lifecareplan.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.BasicResponse;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.home.adapter.MenuAdapter;
import com.longcheng.lifecareplan.home.bean.MenuInfo;
import com.longcheng.lifecareplan.home.set.SetActivity;
import com.longcheng.lifecareplan.login.activity.LoginContract;
import com.longcheng.lifecareplan.login.activity.LoginPresenterImp;
import com.longcheng.lifecareplan.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.test.CardPresenter;
import com.longcheng.lifecareplan.test.MainActivity;
import com.longcheng.lifecareplan.test.Movie;
import com.longcheng.lifecareplan.test.MovieList;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.utils.keyboard.SafeKeyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 菜单首页
 */
public class MenuActivity extends BaseActivityMVP<MenuContract.View, MenuPresenterImp<MenuContract.View>> implements MenuContract.View {

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
    @BindView(R.id.gvbottom)
    GridView gvbottom;

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
        return R.layout.menu;
    }

    @Override
    public void initView(View view) {
        initTimer();
    }

    @Override
    public void setListener() {
        pagetopLayoutRigth.setVisibility(View.VISIBLE);
        pagetopLayoutSet.setOnClickListener(this);
        pageTopTvTime.setFocusable(true);
        ConfigUtils.getINSTANCE().setSelectFouseText(pagetopLayoutSet);
        gvbottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showToast("点击了   " + position);
            }
        });
        gvbottom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mAdapter.setSelectItem(-1);
                }
            }
        });
        gvbottom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setSelectItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mAdapter.setSelectItem(-1);
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
    protected MenuPresenterImp<MenuContract.View> createPresent() {
        return new MenuPresenterImp<>(mActivity, this);
    }


    @Override
    public void getMenuInfoSuccess(BasicResponse<LoginAfterBean> responseBean) {

    }

    MenuAdapter mAdapter;

    private void initD() {
        List<MenuInfo> mBottomList = new ArrayList();
        mBottomList.add(new MenuInfo("春分", "玉兰花", "春分第七天",
                R.mipmap.lichun, R.mipmap.login_icon_phone, "视频"));
        mBottomList.add(new MenuInfo("清明", "杜鸥花", "距离清明第14天",
                R.mipmap.qingming, R.mipmap.login_icon_phone, "图库"));
        mBottomList.add(new MenuInfo("谷雨", "玉兰花", "春分第七天",
                R.mipmap.guyu, R.mipmap.login_icon_phone, "互祝"));
        mBottomList.add(new MenuInfo("立夏", "玉兰花", "春分第七天",
                R.mipmap.lixia, R.mipmap.login_icon_phone, "动态"));
        mBottomList.add(new MenuInfo("", "", "",
                R.mipmap.jieqiyangsheng, R.mipmap.login_icon_phone, ""));
        mAdapter = new MenuAdapter(mContext, mBottomList);
        gvbottom.setAdapter(mAdapter);
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
     * 监听返回键
     */
    public void onBackPressed() {
        exit();
    }
}
