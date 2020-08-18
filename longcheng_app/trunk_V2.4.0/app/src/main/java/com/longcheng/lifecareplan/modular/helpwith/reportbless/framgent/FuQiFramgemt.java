package com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportContract;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportPresenterImp;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuli.ComingFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuli.AllFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuqi.DevoteFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuqi.RewardFrag;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class FuQiFramgemt extends BaseFragmentMVP<ReportContract.View, ReportPresenterImp<ReportContract.View>> implements ReportContract.View {


    @BindView(R.id.vPager)
    ViewPager vPager;
    @BindView(R.id.fuquan_tv_rankjieqi)
    TextView fuquanTvRankjieqi;
    @BindView(R.id.fuquan_tv_rankjieqiline)
    TextView fuquanTvRankjieqiline;
    @BindView(R.id.fuquan_tv_rankday)
    TextView fuquanTvRankday;
    @BindView(R.id.fuquan_tv_rankdayline)
    TextView fuquanTvRankdayline;


    private List<Fragment> fragmentList = new ArrayList<>();
    public static int position;

    @Override
    public int bindLayout() {
        return R.layout.report_fuquan_fram;
    }


    @Override
    public void initView(View view) {
        fuquanTvRankjieqi.setOnClickListener(this);
        fuquanTvRankday.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.fuquan_tv_rankjieqi:
                position = 0;
                selectPage();
                break;
            case R.id.fuquan_tv_rankday:
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
        RewardFrag rewardFrag = new RewardFrag();
        fragmentList.add(rewardFrag);

        DevoteFrag devoteFrag = new DevoteFrag();
        fragmentList.add(devoteFrag);
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
        fuquanTvRankjieqi.setText("收获");
        fuquanTvRankday.setText("奉献");
        fuquanTvRankjieqi.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        fuquanTvRankday.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        fuquanTvRankjieqiline.setVisibility(View.INVISIBLE);
        fuquanTvRankdayline.setVisibility(View.INVISIBLE);
        if (position == 0) {
            fuquanTvRankjieqiline.setVisibility(View.VISIBLE);
            fuquanTvRankjieqi.setTextColor(getResources().getColor(R.color.red));
        } else if (position == 1) {
            fuquanTvRankdayline.setVisibility(View.VISIBLE);
            fuquanTvRankday.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    protected ReportPresenterImp<ReportContract.View> createPresent() {
        return new ReportPresenterImp<>(mContext, this);
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
    public void lingQuSuccess(EditDataBean responseBean) {

    }

    @Override
    public void Error() {
    }

}
