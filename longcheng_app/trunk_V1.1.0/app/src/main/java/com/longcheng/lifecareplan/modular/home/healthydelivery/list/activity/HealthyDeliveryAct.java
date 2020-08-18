package com.longcheng.lifecareplan.modular.home.healthydelivery.list.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.frag.AllFrag;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.frag.GuideFrag;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.frag.KnowledgeFrag;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.frag.LiaoYuFrag;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.frag.NoticeFrag;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConstantManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 健康速递列表
 * Created by Burning on 2018/9/13.
 */

public class HealthyDeliveryAct extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout back;
    @BindView(R.id.pageTop_tv_name)
    TextView tvTitle;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_knowledge)
    TextView tvKnowledge;
    @BindView(R.id.tv_guide)
    TextView tvGuide;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_liaoyu)
    TextView tv_liaoyu;

    @BindView(R.id.iv_cursor)
    ImageView ivCursor;
    @BindView(R.id.vPager)
    ViewPager viewPager;

    @BindView(R.id.iv_cursor1)
    ImageView ivCursor1;
    @BindView(R.id.iv_cursor2)
    ImageView ivCursor2;
    @BindView(R.id.iv_cursor3)
    ImageView ivCursor3;
    @BindView(R.id.iv_cursor4)
    ImageView ivCursor4;

    /**
     * 全部
     */
    public static final int INDEX_All = 0;
    /**
     * 疗愈心声
     */
    public static final int INDEX_LiaoYu = 4;
    /**
     * 文以载道
     */
    public static final int INDEX_WenRoad = 5;
    /**
     * 健康速递
     */
    public static final int INDEX_health = 1;
    /**
     * 平台动态
     */
    public static final int INDEX_PlatformDynamics = 3;

    @Override
    public int bindLayout() {
        return R.layout.act_healthy_delivery;
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void initDataBefore() {
        super.initDataBefore();
    }

    @Override
    public void initView(View view) {
        tvTitle.setText("健康速递");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getInt(intent);
    }

    @Override
    public void initDataAfter() {
        setPageAdapter();
        getInt(getIntent());
    }

    private void getInt(Intent intent) {
        int type = intent.getIntExtra("type", 0);

        if (type == INDEX_LiaoYu) {//疗愈心声
            selectPage(1);
        } else if (type == INDEX_WenRoad) {//文以载道
            selectPage(2);
        } else if (type == INDEX_health) {//健康速递
            selectPage(3);
        } else if (type == INDEX_PlatformDynamics) {//平台动态
            selectPage(4);
        } else {
            selectPage(0);
        }

    }

    @Override
    public void setListener() {
        tvAll.setOnClickListener(this);
        tvKnowledge.setOnClickListener(this);
        tvGuide.setOnClickListener(this);
        tvNotice.setOnClickListener(this);
        tv_liaoyu.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.tv_all://全部
                selectPage(0);
                break;
            case R.id.tv_liaoyu://疗愈心声
                selectPage(1);
                break;
            case R.id.tv_guide://文以载道
                selectPage(2);
                break;
            case R.id.tv_knowledge://健康速递
                selectPage(3);
                break;
            case R.id.tv_notice://平台动态
                selectPage(4);
                break;
        }
    }

    /**
     * 初始化Fragment
     */
    private List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new AllFrag());
        list.add(new LiaoYuFrag());
        list.add(new GuideFrag());
        list.add(new KnowledgeFrag());
        list.add(new NoticeFrag());
        return list;

    }

    /**
     * @param
     * @name 设置PagerAdapter
     * @time 2017/11/24 10:44
     * @author MarkShuai
     */
    private void setPageAdapter() {
        FragmentAdapter tabPageAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragments());
        tabPageAdapter.setOnReloadListener(new FragmentAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                tabPageAdapter.setPagerItems(getFragments());
            }
        });
        viewPager.setAdapter(tabPageAdapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 选择某页
     */
    private void selectPage(int index) {
        viewPager.setCurrentItem(index, false);
        changeTextColor(tvAll, R.color.text_biaoTi_color);
        changeTextColor(tvKnowledge, R.color.text_biaoTi_color);
        changeTextColor(tvGuide, R.color.text_biaoTi_color);
        changeTextColor(tvNotice, R.color.text_biaoTi_color);
        changeTextColor(tv_liaoyu, R.color.text_biaoTi_color);
        ivCursor.setVisibility(View.INVISIBLE);
        ivCursor1.setVisibility(View.INVISIBLE);
        ivCursor2.setVisibility(View.INVISIBLE);
        ivCursor3.setVisibility(View.INVISIBLE);
        ivCursor4.setVisibility(View.INVISIBLE);
        switch (index) {
            case 0:
                changeTextColor(tvAll, R.color.blue);
                ivCursor.setVisibility(View.VISIBLE);
                break;
            case 1:
                changeTextColor(tv_liaoyu, R.color.blue);
                ivCursor1.setVisibility(View.VISIBLE);
                break;
            case 2:
                ivCursor4.setVisibility(View.VISIBLE);
                changeTextColor(tvGuide, R.color.blue);
                break;
            case 3:
                ivCursor3.setVisibility(View.VISIBLE);
                changeTextColor(tvKnowledge, R.color.blue);
                break;
            case 4:
                changeTextColor(tvNotice, R.color.blue);
                ivCursor2.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    private void changeTextColor(TextView tv, int color) {
        tv.setTextColor(getResources().getColor(color));
    }

    private void back() {
        String skiptype = getIntent().getStringExtra("skiptype");
        if (!TextUtils.isEmpty(skiptype) && skiptype.equals("about")) {
        } else {
            Intent intents = new Intent();
            intents.setAction(ConstantManager.MAINMENU_ACTION);
            intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_HOME);
            LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
            ActivityManager.getScreenManager().popAllActivityOnlyMain();
        }
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
