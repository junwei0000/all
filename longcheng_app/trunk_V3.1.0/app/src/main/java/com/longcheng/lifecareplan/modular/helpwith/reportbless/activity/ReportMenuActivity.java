package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.TabPageAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.FuLiFramgemt;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.FuQuanFramgemt;
import com.longcheng.lifecareplan.utils.myview.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 福券报表
 */
public class ReportMenuActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.vp_bt_menu)
    MyViewPager vpBtMenu;
    @BindView(R.id.tv_fuquan)
    TextView tvFuquan;
    @BindView(R.id.tv_fuli)
    TextView tvFuli;
    @BindView(R.id.fuquan_iv_cursor)
    ImageView fuquan_iv_cursor;
    @BindView(R.id.fuli_iv_cursor)
    ImageView fuli_iv_cursor;


    public static List<Fragment> fragmentList = new ArrayList<>();
    public static int position;
    public static final int tab_position_fuquan = 0;
    public static final int tab_position_fuli = 1;
    public static Activity mMenuContext;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_fuquan:
                selectPage(tab_position_fuquan);
                break;
            case R.id.tv_fuli:
                selectPage(tab_position_fuli);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.report_menu;
    }


    @Override
    public void initView(View view) {
        mMenuContext = this;
        pageTopTvName.setText("福券报表");
        setOrChangeTranslucentColor(toolbar, null);
    }


    @Override
    public void initDataAfter() {
        initFragment();
        setPageAdapter();
        selectPage(tab_position_fuquan);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvFuquan.setOnClickListener(this);
        tvFuli.setOnClickListener(this);
    }


    private void setMenuBtn() {
        tvFuquan.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvFuli.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        fuquan_iv_cursor.setVisibility(View.INVISIBLE);
        fuli_iv_cursor.setVisibility(View.INVISIBLE);
        if (position == tab_position_fuquan) {
            fuquan_iv_cursor.setVisibility(View.VISIBLE);
            tvFuquan.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (position == tab_position_fuli) {
            fuli_iv_cursor.setVisibility(View.VISIBLE);
            tvFuli.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        }
    }

    /**
     * @name 初始化Fragment
     */
    private void initFragment() {
        FuQuanFramgemt fuQuanFramgemt = new FuQuanFramgemt();
        fragmentList.add(fuQuanFramgemt);

        FuLiFramgemt fuLiFramgemt = new FuLiFramgemt();
        fragmentList.add(fuLiFramgemt);

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
        vpBtMenu.setOffscreenPageLimit(2);
        /**
         * 设置是否可以滑动
         */
        vpBtMenu.setScroll(true);
        vpBtMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
    }


    /**
     * @param position
     */
    private void selectPage(int position) {
        ReportMenuActivity.position = position;
        // 切换页面
        vpBtMenu.setCurrentItem(position, false);
        setMenuBtn();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }

}
