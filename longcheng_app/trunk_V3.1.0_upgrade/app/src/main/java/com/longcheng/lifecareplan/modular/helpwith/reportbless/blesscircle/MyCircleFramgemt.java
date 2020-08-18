package com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.ciecle.BlessFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle.ciecle.NobleFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class MyCircleFramgemt extends BaseFragmentMVP<MyCircleContract.View, MyCirclePresenterImp<MyCircleContract.View>> implements MyCircleContract.View {


    @BindView(R.id.vPager)
    ViewPager vPager;
    @BindView(R.id.fuquan_tv_noblecircle)
    TextView fuquanTvNoblecircle;
    @BindView(R.id.fuquan_tv_noblecircleline)
    TextView fuquanTvNoblecircleline;
    @BindView(R.id.fuquan_tv_blessingcircle)
    TextView fuquanTvBlessingcircle;
    @BindView(R.id.fuquan_tv_blessingcircleline)
    TextView fuquanTvBlessingcircleline;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;


    private List<Fragment> fragmentList = new ArrayList<>();
    public static int position;

    @Override
    public int bindLayout() {
        return R.layout.fuqrep_mycircle_fram;
    }


    @Override
    public void initView(View view) {
        fuquanTvNoblecircle.setOnClickListener(this);
        fuquanTvBlessingcircle.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.fuquan_tv_noblecircle:
                position = 0;
                selectPage();
                break;
            case R.id.fuquan_tv_blessingcircle:
                position = 1;
                selectPage();
                break;
            default:
                break;
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        if (isAdded()) {
            position = 0;
            initFragment();
            setPageAdapter();
        }
    }

    /**
     * @param
     * @name 初始化Fragment
     * @time 2017/11/24 10:23
     * @author MarkShuai
     */
    private void initFragment() {
        fragmentList.clear();
        NobleFrag nobleFrag = new NobleFrag();
        fragmentList.add(nobleFrag);

        BlessFrag blessFrag = new BlessFrag();
        fragmentList.add(blessFrag);
    }

    /**
     * @param
     * @name 设置PagerAdapter
     * @time 2017/11/24 10:44
     * @author MarkShuai
     */
    FragmentAdapter tabPageAdapter;

    private void setPageAdapter() {
        Log.e("onPageSelected", "fragmentList=" + fragmentList.size());
        tabPageAdapter = new FragmentAdapter(getChildFragmentManager(), fragmentList);
        vPager.setAdapter(tabPageAdapter);
        selectPage();
        vPager.setOffscreenPageLimit(2);
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position_) {
                Log.e("onPageSelected", "position_=" + position_);
                position = position_ % 2;
                selectPage();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 选择某页
     */
    private void selectPage() {
        vPager.setCurrentItem(position, false);
        showView();
    }

    private void showView() {
        fuquanTvNoblecircle.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        fuquanTvBlessingcircle.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        fuquanTvNoblecircleline.setVisibility(View.INVISIBLE);
        fuquanTvBlessingcircleline.setVisibility(View.INVISIBLE);
        if (position == 0) {
            fuquanTvNoblecircleline.setVisibility(View.VISIBLE);
            fuquanTvNoblecircle.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (position == 1) {
            fuquanTvBlessingcircleline.setVisibility(View.VISIBLE);
            fuquanTvBlessingcircle.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        }
    }

    @Override
    protected MyCirclePresenterImp<MyCircleContract.View> createPresent() {
        return new MyCirclePresenterImp<>(mContext, this);
    }


    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void ListSuccess(ReportDataBean responseBean, int backPage) {

    }

    @Override
    public void MessageListSuccess(MessageListDataBean responseBean) {

    }


    @Override
    public void Error() {
    }
}
