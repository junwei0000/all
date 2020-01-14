package com.longcheng.lifecareplan.modular.home.liveplay.framgent;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.VideoMenuActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyContract;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MineItemInfo;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class HomFramgemt extends BaseFragmentMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {


    @BindView(R.id.vPager)
    ViewPager vPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.tv_playlist_video)
    TextView tvPlaylistVideo;
    @BindView(R.id.tv_playlist_video_line)
    TextView tvPlaylistVideoLine;
    @BindView(R.id.layout_playlist_video)
    LinearLayout layoutPlaylistVideo;
    @BindView(R.id.tv_playlist_live)
    TextView tvPlaylistLive;
    @BindView(R.id.tv_playlist_live_line)
    TextView tvPlaylistLiveLine;
    @BindView(R.id.layout_playlist_live)
    LinearLayout layoutPlaylistLive;
    @BindView(R.id.layout_rigth)
    LinearLayout layoutRigth;


    private List<Fragment> fragmentList = new ArrayList<>();
    public static int position;

    @Override
    public int bindLayout() {
        return R.layout.live_hom_fram;
    }


    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
        layoutLeft.setOnClickListener(this);
        layoutRigth.setVisibility(View.VISIBLE);
        layoutPlaylistVideo.setVisibility(View.VISIBLE);
        layoutPlaylistLive.setVisibility(View.VISIBLE);
        layoutPlaylistVideo.setOnClickListener(this);
        layoutPlaylistLive.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                ((VideoMenuActivity) getActivity()).back();
                break;
            case R.id.layout_playlist_video:
                position = 0;
                selectPage();
                break;
            case R.id.layout_playlist_live:
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
        VideoFramgent videoFramgent = new VideoFramgent();
        videoFramgent.showPageType=videoFramgent.showPageType_video;
        fragmentList.add(videoFramgent);

        LiveFramgent mMyLiveFrag = new LiveFramgent();
        fragmentList.add(mMyLiveFrag);
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
        if (position == 0) {
            tvPlaylistVideoLine.setVisibility(View.VISIBLE);
            tvPlaylistLiveLine.setVisibility(View.INVISIBLE);
            tvPlaylistVideoLine.setBackgroundResource(R.drawable.corners_bg_write);
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvPlaylistVideoLine, R.color.white);
            onResumeVideo();
        } else if (position == 1) {
            tvPlaylistVideoLine.setVisibility(View.INVISIBLE);
            tvPlaylistLiveLine.setVisibility(View.VISIBLE);
            tvPlaylistLiveLine.setBackgroundResource(R.drawable.corners_bg_write);
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvPlaylistLiveLine, R.color.white);
            onPauseVideo();
        }
    }
    public void onResumeVideo() {
        ((VideoFramgent) fragmentList.get(0)).onResumeVideo();
    }

    public void onPauseVideo() {
        ((VideoFramgent) fragmentList.get(0)).onPauseVideo();
    }

    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(mContext, this);
    }


    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void getMineInfoSuccess(BasicResponse<MineItemInfo> responseBean) {

    }

    @Override
    public void updateShowTitleSuccess(BasicResponse responseBean) {
    }

    @Override
    public void cancelFollowSuccess(BasicResponse responseBean) {

    }

    @Override
    public void BackVideoListSuccess(BasicResponse<MVideoDataInfo> responseBean, int back_page) {

    }

    @Override
    public void Error() {
    }

}
