package com.longcheng.lifecareplan.modular.mine.record.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.mine.record.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.mine.record.fargment.BaseRecordFrag;
import com.longcheng.lifecareplan.widget.CanScrollViewPager;
import com.longcheng.lifecareplan.widget.TabLayoutView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncreaseRecordActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_lines)
    TextView tvLines;
    @BindView(R.id.enhance_tab_layout)
    TabLayoutView enhanceTabLayout;
    @BindView(R.id.recordvpager)
    CanScrollViewPager recordvpager;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;

    private List<BaseRecordFrag> fragments = new ArrayList<>();
    private ArrayList<String> tabs = new ArrayList<>();
    private BaseRecordFrag baseRecordFrag = null;

    FragmentAdapter fragmentAdapter = null;

    String[] tabname = {"安全信息", "吃亏分享", "咨询", "安全信息", "吃亏分享", "咨询", "安全信息", "吃亏分享", "咨询"};//动态添加？？？


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_iv_left:
            case R.id.pagetop_layout_left:

                doFinish();

                break;

        }
    }

    private void initVPView() {
        fragments.clear();
        int i = 0;
        Log.v("TAG", "name");
        for (String name : tabname) {
            Log.v("TAG", "name" + name);
            enhanceTabLayout.addTab(name);
            baseRecordFrag = BaseRecordFrag.newInstance(tabname[i], i);
            fragments.add(baseRecordFrag);
            i++;
        }

        enhanceTabLayout.setmTabMode(fragments.size()); // 设置是否可以滑动 根据fragments 数量

        if (fragmentAdapter == null) {
            recordvpager.setScanScroll(true);
            recordvpager.setAdapter(fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, tabname));
            enhanceTabLayout.setupWithViewPager(recordvpager);
            recordvpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(enhanceTabLayout.getTabLayout()));
            recordvpager.setOffscreenPageLimit((fragments.size() > 3 ? 3 : fragments.size() - 1));
        } else fragmentAdapter.notifyDataSetChanged();

        Log.v("TAG", "SIZE:" + fragments.size());
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.increase_record_layout;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
        pageTopTvName.setText("增长记录");
        enhanceTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.v("TAG", "onTabSelected:");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.v("TAG", "onTabUnselected:");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.v("TAG", "onTabReselected:");
            }
        });
    }

    @Override
    public void setListener() {
        pagetopIvLeft.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        initVPView();
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
