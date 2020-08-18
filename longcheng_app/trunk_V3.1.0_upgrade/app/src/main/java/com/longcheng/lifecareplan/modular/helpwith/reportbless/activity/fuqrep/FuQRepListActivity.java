package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.fuqrep;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.utils.myview.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class FuQRepListActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.vp_bt_menu)
    ViewPager vpBtMenu;
    @BindView(R.id.tv_coming)
    TextView tvComing;
    @BindView(R.id.iv_coming_cursor)
    ImageView ivComingCursor;
    @BindView(R.id.tv_dai)
    TextView tvDai;
    @BindView(R.id.iv_dai_cursor)
    ImageView ivDaiCursor;
    @BindView(R.id.tv_over)
    TextView tvOver;
    @BindView(R.id.iv_over_cursor)
    ImageView ivOverCursor;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_coming:
                selectPage(0);
                break;
            case R.id.tv_dai:
                selectPage(1);
                break;
            case R.id.tv_over:
                selectPage(2);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fuqrep_report_menu;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    int type;//1请祈福 2领福利  3节气  4日期
    String keyword;

    @Override
    public void initDataAfter() {
        type = getIntent().getIntExtra("type", 1);
        keyword = getIntent().getStringExtra("keyword");
        String keywordname = getIntent().getStringExtra("keywordname");
        if (type == 3) {
            pageTopTvName.setText(keywordname);
        } else {
            pageTopTvName.setText(keyword);
        }
        initFragment();
        setPageAdapter();
        selectPage(0);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvComing.setOnClickListener(this);
        tvDai.setOnClickListener(this);
        tvOver.setOnClickListener(this);
    }


    private void setMenuBtn(int position) {
        tvComing.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvDai.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvOver.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        ivComingCursor.setVisibility(View.INVISIBLE);
        ivDaiCursor.setVisibility(View.INVISIBLE);
        ivOverCursor.setVisibility(View.INVISIBLE);
        if (position == 0) {
            ivComingCursor.setVisibility(View.VISIBLE);
            tvComing.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (position == 1) {
            ivDaiCursor.setVisibility(View.VISIBLE);
            tvDai.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (position == 2) {
            ivOverCursor.setVisibility(View.VISIBLE);
            tvOver.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        }
    }

    /**
     * @name 初始化Fragment
     */
    private void initFragment() {
        fragmentList.clear();
        FuQRepComingFrag fuQRepComingFrag = new FuQRepComingFrag(type, keyword);
        fragmentList.add(fuQRepComingFrag);
        FuQRepDaiFrag fuQRepDaiFrag = new FuQRepDaiFrag(type, keyword);
        fragmentList.add(fuQRepDaiFrag);
        FuQRepOverFrag fuQRepOverFrag = new FuQRepOverFrag(type, keyword);
        fragmentList.add(fuQRepOverFrag);
    }

    FragmentAdapter tabPageAdapter;

    /**
     * @param
     * @name 设置PagerAdapter
     * @time 2017/11/24 10:44
     * @author MarkShuai
     */
    private void setPageAdapter() {
        tabPageAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        tabPageAdapter.setOnReloadListener(new FragmentAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragmentList = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(new FuQRepComingFrag(type, keyword));
                list.add(new FuQRepDaiFrag(type, keyword));
                list.add(new FuQRepOverFrag(type, keyword));
                tabPageAdapter.setPagerItems(list);
                Log.e("onReload", "onReload");
                fragmentList = list;
            }
        });
        vpBtMenu.setAdapter(tabPageAdapter);
        vpBtMenu.setOffscreenPageLimit(3);
        vpBtMenu.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPage(position % 4);
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
        // 切换页面
        vpBtMenu.setCurrentItem(position, false);
        setMenuBtn(position);
    }

    public void reLoadList() {
        if (tabPageAdapter != null)
            tabPageAdapter.reLoad();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }
}
