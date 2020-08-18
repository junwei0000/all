package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.dc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.activity.PioneerMenuActivity;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创业中心--代充
 */
public class PionDaiCActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.userorder_tv_all)
    TextView userorderTvAll;
    @BindView(R.id.userorder_tv_coming)
    TextView userorderTvComing;
    @BindView(R.id.userorder_tv_overed)
    TextView userorderTvOvered;
    @BindView(R.id.userorder_iv_cursor)
    ImageView userorderIvCursor;
    @BindView(R.id.userorder_vPager)
    ViewPager userorderVPager;
    @BindView(R.id.userorder_tv_cancel)
    TextView userorderTvCancel;


    private List<Fragment> fragmentList = new ArrayList<>();
    private int position;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.userorder_tv_all:
                position = 0;
                selectPage();
                break;
            case R.id.userorder_tv_coming:
                position = 1;
                selectPage();
                break;
            case R.id.userorder_tv_cancel:
                position = 2;
                selectPage();
                break;
            case R.id.userorder_tv_overed:
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
        return R.layout.pionner_order;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("敬售祝佑宝");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        userorderTvAll.setOnClickListener(this);
        userorderTvComing.setOnClickListener(this);
        userorderTvCancel.setOnClickListener(this);
        userorderTvOvered.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        bmpW = DensityUtil.dip2px(mContext, 30);
        userorderIvCursor.setLayoutParams(new LinearLayout.LayoutParams(bmpW, DensityUtil.dip2px(mContext, 2)));
        position = 1;
        initFragment();
        setPageAdapter();
    }

    /**
     * 选择某页
     */
    private void selectPage() {
        userorderVPager.setCurrentItem(position, false);
        userorderTvAll.setTextColor(getResources().getColor(R.color.text_contents_color));
        userorderTvComing.setTextColor(getResources().getColor(R.color.text_contents_color));
        userorderTvCancel.setTextColor(getResources().getColor(R.color.text_contents_color));
        userorderTvOvered.setTextColor(getResources().getColor(R.color.text_contents_color));
        if (position == 0) {
            userorderTvAll.setTextColor(getResources().getColor(R.color.blue));
        } else if (position == 1) {
            userorderTvComing.setTextColor(getResources().getColor(R.color.blue));
        } else if (position == 2) {
            userorderTvCancel.setTextColor(getResources().getColor(R.color.blue));
        } else if (position == 2) {
            userorderTvOvered.setTextColor(getResources().getColor(R.color.blue));
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
        ComingFragment mComingFragment = new ComingFragment();
        fragmentList.add(mComingFragment);
        CancelFragment cancelFragment = new CancelFragment();
        fragmentList.add(cancelFragment);
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
                list.add(new ComingFragment());
                list.add(new CancelFragment());
                list.add(new OveredFragment());
                tabPageAdapter.setPagerItems(list);
                Log.e("onReload", "onReload");
            }
        });
        userorderVPager.setAdapter(tabPageAdapter);
        selectPage();
        userorderVPager.setOffscreenPageLimit(4);
        userorderVPager.addOnPageChangeListener(this);
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
     * 是否有编辑订单处理
     */
    public static boolean editOrderStatus = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (editOrderStatus) {
            editOrderStatus = false;
            reLoadList();
        }
    }

    public void reLoadList() {
        if (tabPageAdapter != null)
            tabPageAdapter.reLoad();
    }

    public void back() {
        Intent intent = getIntent();
        if (intent != null) {
            String skiptype = intent.getStringExtra("skiptype");
            if (!TextUtils.isEmpty(skiptype) && skiptype.equals("push")) {
                Intent intents = new Intent(mActivity, PioneerMenuActivity.class);
                intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intents);
            }
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