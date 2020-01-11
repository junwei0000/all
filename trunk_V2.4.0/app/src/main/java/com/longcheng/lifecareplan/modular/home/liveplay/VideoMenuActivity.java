package com.longcheng.lifecareplan.modular.home.liveplay;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.TabPageAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.home.liveplay.framgent.CodeFramgemt;
import com.longcheng.lifecareplan.modular.home.liveplay.framgent.MineFramgemt;
import com.longcheng.lifecareplan.modular.home.liveplay.framgent.VideoFramgent;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.ShortVideoActivity;
import com.longcheng.lifecareplan.utils.myview.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoMenuActivity extends BaseActivity {
    @BindView(R.id.vp_bt_menu)
    MyViewPager vpBtMenu;
    @BindView(R.id.bottom_tv_home)
    TextView bottomTvHome;
    @BindView(R.id.bottom_tv_scancode)
    TextView bottomTvScancode;
    @BindView(R.id.bottom_layout_publish)
    LinearLayout bottom_layout_publish;
    @BindView(R.id.bottom_tv_follow)
    TextView bottomTvFollow;
    @BindView(R.id.bottom_tv_mine)
    TextView bottomTvMine;


    public static List<Fragment> fragmentList = new ArrayList<>();
    public static int position;

    public static final int tab_position_home = 0;
    public static final int tab_position_Scancode = 1;
    public static final int tab_position_Follow = 2;
    public static final int tab_position_mine = 3;

    public static Activity mMenuContext;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.video_menu;
    }


    @Override
    public void initView(View view) {
        mMenuContext = this;
    }


    String skipType;

    @Override
    public void initDataAfter() {
        skipType = getIntent().getStringExtra("skipType");
        initFragment();
        setPageAdapter();
        selectPage(tab_position_home);
    }

    @Override
    public void setListener() {
        bottomTvHome.setOnClickListener(this);
        bottomTvScancode.setOnClickListener(this);
        bottom_layout_publish.setOnClickListener(this);
        bottomTvFollow.setOnClickListener(this);
        bottomTvMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_tv_home:
                selectPage(tab_position_home);
                break;
            case R.id.bottom_tv_scancode:
                selectPage(tab_position_Scancode);
                break;
            case R.id.bottom_layout_publish:
                Intent intent = new Intent(mActivity, ShortVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.bottom_tv_follow:
                selectPage(tab_position_Follow);
                break;
            case R.id.bottom_tv_mine:
                selectPage(tab_position_mine);
                break;
        }
    }

    private void setBottomBtn() {
        bottomTvHome.setTextColor(getResources().getColor(R.color.text_noclick_color));
        bottomTvScancode.setTextColor(getResources().getColor(R.color.text_noclick_color));
        bottomTvFollow.setTextColor(getResources().getColor(R.color.text_noclick_color));
        bottomTvMine.setTextColor(getResources().getColor(R.color.text_noclick_color));
        if (position == tab_position_home) {
            bottomTvHome.setTextColor(getResources().getColor(R.color.white));
        } else if (position == tab_position_Scancode) {
            bottomTvScancode.setTextColor(getResources().getColor(R.color.white));
        } else if (position == tab_position_Follow) {
            bottomTvFollow.setTextColor(getResources().getColor(R.color.white));
        } else if (position == tab_position_mine) {
            bottomTvMine.setTextColor(getResources().getColor(R.color.white));
        }
    }

    /**
     * @param
     * @name 初始化Fragment
     * @time 2017/11/24 10:23
     * @author MarkShuai
     */
    private void initFragment() {
        VideoFramgent videoFramgent = new VideoFramgent();
        fragmentList.add(videoFramgent);

        CodeFramgemt codeFramgemt = new CodeFramgemt();
        fragmentList.add(codeFramgemt);

        VideoFramgent exChangeFragment = new VideoFramgent();
        fragmentList.add(exChangeFragment);

        MineFramgemt mineFragment = new MineFramgemt();
        fragmentList.add(mineFragment);

    }

    /**
     * @param
     * @name 设置PagerAdapter
     * @time 2017/11/24 10:44
     * @author MarkShuai
     */
    private void setPageAdapter() {
        TabPageAdapter tabPageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragmentList);
        vpBtMenu.setAdapter(tabPageAdapter);
        vpBtMenu.setOffscreenPageLimit(0);
//        mViewPager.addOnPageChangeListener(mOnPageChange);
        /**
         * 设置是否可以滑动
         */
        vpBtMenu.setScroll(false);
    }

    ViewPager.OnPageChangeListener mOnPageChange = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.e("BottomMenuActivity", "onPageScrolled=" + position);
        }

        @Override
        public void onPageSelected(int position) {
            selectPage(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * @param position
     */
    private void selectPage(int position) {
        this.position = position;
        // 切换页面
        vpBtMenu.setCurrentItem(position, false);
        setBottomBtn();
    }

    private void back() {
        if(!TextUtils.isEmpty(skipType)&&skipType.equals("auto")){
            Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("html_url", Config.BASE_HEAD_URL + "home/knpteam/allroomlist");
            startActivity(intent);
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
