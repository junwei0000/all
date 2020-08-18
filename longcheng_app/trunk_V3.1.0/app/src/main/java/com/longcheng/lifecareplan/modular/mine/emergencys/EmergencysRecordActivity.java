package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.widget.CanScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EmergencysRecordActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tl_tabs)
    SlidingTabLayout tlTabs;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    List<String> titles = new ArrayList<>();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
        }
    }

    private void back() {
        doFinish();
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_emergencys_record;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {

        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("救急金转账记录");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(AllRecordFragment.newInstance());
        fragmentList.add(ProgressMoneyFragment.newInstance());
        fragmentList.add(FinishMoneyFragment.newInstance());
        titles.add("全部");
        titles.add("未打款");
        titles.add("已打款");
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return titles.get(position);
            }
        });

        tlTabs.setViewPager(viewPager);

    }

}
