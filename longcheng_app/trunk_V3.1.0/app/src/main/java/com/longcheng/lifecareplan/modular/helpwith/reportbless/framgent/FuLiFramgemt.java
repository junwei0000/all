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
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuli.ComingFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuli.AllFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuli.GetFrag;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.framgent.fuli.NotGetFrag;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.AllFragment;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.ComingFragment;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.OveredFragment;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.PendingFragment;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.YaJinFragment;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 *
 */
public class FuLiFramgemt extends BaseFragmentMVP<ReportContract.View, ReportPresenterImp<ReportContract.View>> implements ReportContract.View {


    @BindView(R.id.vPager)
    ViewPager vPager;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_allline)
    TextView tvAllline;
    @BindView(R.id.tv_coming)
    TextView tvComing;
    @BindView(R.id.tv_comingline)
    TextView tvComingline;
    @BindView(R.id.tv_get)
    TextView tvGet;
    @BindView(R.id.tv_getline)
    TextView tvGetline;
    @BindView(R.id.tv_notget)
    TextView tvNotget;
    @BindView(R.id.tv_notgetline)
    TextView tvNotgetline;
    @BindView(R.id.fuli_tv_allnum)
    TextView fuli_tv_allnum;


    private List<Fragment> fragmentList = new ArrayList<>();
    public static int position;

    @Override
    public int bindLayout() {
        return R.layout.report_fuli_fram;
    }


    @Override
    public void initView(View view) {
        tvAll.setOnClickListener(this);
        tvComing.setOnClickListener(this);
        tvGet.setOnClickListener(this);
        tvNotget.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                position = 0;
                selectPage();
                break;
            case R.id.tv_coming:
                position = 1;
                selectPage();
                break;
            case R.id.tv_get:
                position = 2;
                selectPage();
                break;
            case R.id.tv_notget:
                position = 3;
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
        AllFrag rewardFrag = new AllFrag(fuli_tv_allnum);
        fragmentList.add(rewardFrag);
        ComingFrag devoteFrag = new ComingFrag(fuli_tv_allnum);
        fragmentList.add(devoteFrag);
        GetFrag getFrag = new GetFrag(fuli_tv_allnum);
        fragmentList.add(getFrag);
        NotGetFrag notGetFrag = new NotGetFrag(fuli_tv_allnum);
        fragmentList.add(notGetFrag);
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
        tabPageAdapter.setOnReloadListener(new FragmentAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragmentList = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(new AllFrag(fuli_tv_allnum));
                list.add(new ComingFrag(fuli_tv_allnum));
                list.add(new GetFrag(fuli_tv_allnum));
                list.add(new NotGetFrag(fuli_tv_allnum));
                tabPageAdapter.setPagerItems(list);
                fragmentList=list;
                Log.e("onReload", "onReload");
            }
        });
        vPager.setOffscreenPageLimit(4);
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position_) {
                Log.e("onPageSelected", "position_=" + position_);
                position = position_ % 4;
                selectPage();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void reLoadList() {
        if (tabPageAdapter != null)
            tabPageAdapter.reLoad();
    }

    /**
     * 选择某页
     */
    private void selectPage() {
        vPager.setCurrentItem(position, false);
        showView();
    }

    private void showView() {
        tvAll.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvAllline.setVisibility(View.INVISIBLE);
        tvComing.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvComingline.setVisibility(View.INVISIBLE);
        tvGet.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvGetline.setVisibility(View.INVISIBLE);
        tvNotget.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvNotgetline.setVisibility(View.INVISIBLE);
        if (position == 0) {
            tvAllline.setVisibility(View.VISIBLE);
            tvAll.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (position == 1) {
            tvComingline.setVisibility(View.VISIBLE);
            tvComing.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (position == 2) {
            tvGetline.setVisibility(View.VISIBLE);
            tvGet.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
        } else if (position == 3) {
            tvNotgetline.setVisibility(View.VISIBLE);
            tvNotget.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
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
    public void DetailSuccess(FQDetailDataBean responseBean) {

    }

    @Override
    public void lingQuSuccess(EditDataBean responseBean) {

    }

    @Override
    public void Error() {
    }

}
