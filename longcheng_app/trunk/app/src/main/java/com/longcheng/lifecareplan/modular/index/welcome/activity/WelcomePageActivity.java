package com.longcheng.lifecareplan.modular.index.welcome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.index.welcome.adapter.WelcomePageAdapter;
import com.longcheng.lifecareplan.modular.index.welcome.bean.WelcomeBean;
import com.longcheng.lifecareplan.modular.test.MainActivityMVP;
import com.longcheng.lifecareplan.modular.webView.WebProjectActivity;
import com.longcheng.lifecareplan.modular.webView.WebProjectActivityBridge;
import com.longcheng.lifecareplan.utils.MySharedPreferences;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomePageActivity extends BaseActivityMVP<WelcomeContract.View, WelcomePresenterImp<WelcomeContract.View>> implements WelcomeContract.View, ViewPager.OnPageChangeListener {

    //引导页首页
    @BindView(R.id.vp_welcome)
    ViewPager mViewPager;

    //存放小圆点
    @BindView(R.id.ll_dot)
    LinearLayout mLineLayoutDot;

    @BindView(R.id.bt_in)
    Button mButtonIn;

    private List<ImageView> dotList = new ArrayList<>();

    private List<WelcomeBean> welcomeList = new ArrayList<>();

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_welcome_page;
    }

    @Override
    public void initView(View view) {
        boolean isFirstIn = MySharedPreferences.getInstance().getIsFirstIn();
        if (!isFirstIn) {
            intentIndexPage();
        }
        MySharedPreferences.getInstance().saveIsFirstIn(false);
        mButtonIn.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.bt_in:
                intentIndexPage();
                break;
        }
    }

    /**
     * @param
     * @Name 跳转Intent
     * @Data 2018/1/31 18:54
     * @Author :MarkShuai
     */
    private void intentIndexPage() {
        Intent intent = new Intent(mContext, MainActivityMVP.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected WelcomePresenterImp<WelcomeContract.View> createPresent() {
        return new WelcomePresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotList.size(); i++) {
            ImageView img = dotList.get(i);
            if (i == position % dotList.size()) {
                img.setImageResource(R.mipmap.lunbo_chose);
            } else {
                img.setImageResource(R.mipmap.lunbo_nochose);
            }
        }

        if (position == dotList.size() - 1) {
            mButtonIn.setVisibility(View.VISIBLE);
        } else {
            mButtonIn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setPageAdapter(List<WelcomeBean> list) {
        WelcomePageAdapter pageAdapter = new WelcomePageAdapter(mContext, list);
        mViewPager.setAdapter(pageAdapter);
    }

    @Override
    public void addLineLayoutDot(List<ImageView> list, LinearLayout.LayoutParams params) {
        dotList.clear();
        mLineLayoutDot.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            mLineLayoutDot.addView(list.get(i), params);
            dotList.add(list.get(i));
        }
    }

}
