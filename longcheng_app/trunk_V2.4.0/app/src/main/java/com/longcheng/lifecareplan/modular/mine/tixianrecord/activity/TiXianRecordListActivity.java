package com.longcheng.lifecareplan.modular.mine.tixianrecord.activity;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 提现记录
 */
public class TiXianRecordListActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_matching)
    TextView tvMatching;
    @BindView(R.id.tv_coming)
    TextView tvComing;
    @BindView(R.id.tv_overed)
    TextView tvOvered;
    @BindView(R.id.userorder_iv_cursor)
    ImageView userorderIvCursor;
    @BindView(R.id.vPager)
    ViewPager vPager;


    private List<Fragment> fragmentList = new ArrayList<>();
    private int position;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_all:
                position = 0;
                selectPage();
                break;
            case R.id.tv_matching:
                position = 1;
                selectPage();
                break;
            case R.id.tv_coming:
                position = 2;
                selectPage();
                break;
            case R.id.tv_overed:
                position = 3;
                selectPage();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.my_tixianrecord;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("平台提现记录");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvAll.setOnClickListener(this);
        tvMatching.setOnClickListener(this);
        tvComing.setOnClickListener(this);
        tvOvered.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        bmpW = DensityUtil.dip2px(mContext, 30);// 获取图片宽度
        userorderIvCursor.setLayoutParams(new LinearLayout.LayoutParams(bmpW, DensityUtil.dip2px(mContext, 2)));
        position = 0;
        initFragment();
        setPageAdapter();
    }

    /**
     * 选择某页
     */
    private void selectPage() {
        vPager.setCurrentItem(position, false);
        tvAll.setTextColor(getResources().getColor(R.color.text_contents_color));
        tvComing.setTextColor(getResources().getColor(R.color.text_contents_color));
        tvMatching.setTextColor(getResources().getColor(R.color.text_contents_color));
        tvOvered.setTextColor(getResources().getColor(R.color.text_contents_color));
        if (position == 0) {
            tvAll.setTextColor(getResources().getColor(R.color.red));
        } else if (position == 1) {
            tvMatching.setTextColor(getResources().getColor(R.color.red));
        } else if (position == 2) {
            tvComing.setTextColor(getResources().getColor(R.color.red));
        } else if (position == 3) {
            tvOvered.setTextColor(getResources().getColor(R.color.red));
        }
        InitImageView(position);
    }

    /**
     * @param
     * @name 初始化Fragment
     * @time 2017/11/24 10:23
     * @author MarkShuai
     */
    private void initFragment() {
        AllFragment mAllFragment = new AllFragment();
        fragmentList.add(mAllFragment);

        MatchingFragment mComingFragment = new MatchingFragment();
        fragmentList.add(mComingFragment);

        ComingFragment mPendingFragment = new ComingFragment();
        fragmentList.add(mPendingFragment);

        OveredFragment mOveredFragment = new OveredFragment();
        fragmentList.add(mOveredFragment);
    }

    /**
     * @param
     * @name 设置PagerAdapter
     * @time 2017/11/24 10:44
     * @author MarkShuai
     */
    FragmentAdapter tabPageAdapter;

    private void setPageAdapter() {
        tabPageAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        tabPageAdapter.setOnReloadListener(new FragmentAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragmentList = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(new AllFragment());
                list.add(new MatchingFragment());
                list.add(new ComingFragment());
                list.add(new OveredFragment());
                tabPageAdapter.setPagerItems(list);
                Log.e("onReload", "onReload");
            }
        });
        vPager.setAdapter(tabPageAdapter);
        selectPage();
        vPager.setOffscreenPageLimit(4);
        vPager.addOnPageChangeListener(this);
    }


    /**
     * 初始化动画
     */
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度

    private void InitImageView(int selectArg0) {
        int screenW = DensityUtil.getPhoneWidHeigth(this).widthPixels;
        offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        userorderIvCursor.setImageMatrix(matrix);// 设置动画初始位置
        setLineFollowSlide(selectArg0);
    }

    /**
     * 设置跟随滑动
     */
    private void setLineFollowSlide(int selectArg0) {
        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        Animation animation = new TranslateAnimation(offset + one * currIndex, offset + one * selectArg0, 0, 0);// 显然这个比较简洁，只有一行代码。
        currIndex = selectArg0;
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(300);
        userorderIvCursor.startAnimation(animation);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        selectPage();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 提现 --接单
     */
    public static boolean editTiXianStatus = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (editTiXianStatus) {
            editTiXianStatus = false;
            reLoadList();
        }
    }

    public void reLoadList() {
        if (tabPageAdapter != null)
            tabPageAdapter.reLoad();
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
