package com.longcheng.lifecareplan.modular.home.liveplay.framgent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyContract;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyFouseActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MyPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.UpdateInfoActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MineItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.fragment.MyLiveFrag;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.fragment.MyLoveFrag;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.fragment.MyVideoFrag;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的
 */
public class MineFramgemt extends BaseFragmentMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layout_left;
    @BindView(R.id.tv_likenum)
    TextView tvLikenum;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_skbnum)
    TextView tvSkbnum;
    @BindView(R.id.tv_showtitle)
    TextView tv_showtitle;
    @BindView(R.id.tv_myvideo)
    TextView tvMyvideo;
    @BindView(R.id.tv_mylive)
    TextView tvMylive;
    @BindView(R.id.tv_myfollow)
    TextView tv_myfollow;
    @BindView(R.id.vPager)
    ViewPager vPager;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.tv_mylove)
    TextView tvMylove;
    @BindView(R.id.tv_video_line)
    TextView tv_video_line;
    @BindView(R.id.tv_live_line)
    TextView tv_live_line;
    @BindView(R.id.tv_love_line)
    TextView tv_love_line;

    private List<Fragment> fragmentList = new ArrayList<>();
    private int position;
    private String name = "";
    String show_title;

    @Override
    public int bindLayout() {
        return R.layout.live_mine;
    }


    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
        layout_left.setVisibility(View.INVISIBLE);
        tvMyvideo.setOnClickListener(this);
        tvMylive.setOnClickListener(this);
        tvMylove.setOnClickListener(this);
        tv_showtitle.setOnClickListener(this);
        tv_myfollow.setOnClickListener(this);
        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_video_line, R.color.white);
        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_live_line, R.color.white);
        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_love_line, R.color.white);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.tv_myfollow:
                Intent intent = new Intent(mActivity, MyFouseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("video_user_id", video_user_id);
                intent.putExtra("user_name", name);
                startActivity(intent);
                break;
            case R.id.tv_myvideo:
                position = 0;
                selectPage();
                break;
            case R.id.tv_mylive:
                position = 1;
                selectPage();
                break;
            case R.id.tv_mylove:
                position = 2;
                selectPage();
                break;
            case R.id.tv_showtitle:
                if (!TextUtils.isEmpty(video_user_id) && video_user_id.equals(UserUtils.getUserId(mContext))) {
                    Intent intents = new Intent(mActivity, UpdateInfoActivity.class);
                    intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intents.putExtra("show_title", show_title);
                    startActivity(intents);
                }
                break;
            default:
                break;
        }
    }


    String video_user_id;

    @Override
    public void doBusiness(Context mContext) {
        if (isAdded()) {
            video_user_id = UserUtils.getUserId(mContext);
            if (UserUtils.getUserId(mContext).equals(video_user_id)) {
                tvFollow.setVisibility(View.INVISIBLE);
            } else {
                tvFollow.setVisibility(View.VISIBLE);
                tvFollow.setOnClickListener(this);
            }
            position = 0;
            initFragment();
            setPageAdapter();
        }
    }

    public void getMineInfo() {
        if (isAdded() && mPresent != null)
            mPresent.getMineInfo(video_user_id);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMineInfo();
    }

    /**
     * @param
     * @name 初始化Fragment
     * @time 2017/11/24 10:23
     * @author MarkShuai
     */
    private void initFragment() {
        fragmentList.clear();
        MyVideoFrag mMyVideoFrag = new MyVideoFrag();
        mMyVideoFrag.setVideo_user_id(video_user_id);
        fragmentList.add(mMyVideoFrag);

        MyLiveFrag mMyLiveFrag = new MyLiveFrag();
        mMyLiveFrag.setVideo_user_id(video_user_id);
        fragmentList.add(mMyLiveFrag);

        MyLoveFrag myLoveFrag = new MyLoveFrag();
        myLoveFrag.setVideo_user_id(video_user_id);
        fragmentList.add(myLoveFrag);
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
        vPager.setOffscreenPageLimit(3);
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position_) {
                Log.e("onPageSelected", "position_=" + position_);
                position = position_ % 3;
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
        tv_video_line.setVisibility(View.INVISIBLE);
        tv_live_line.setVisibility(View.INVISIBLE);
        tv_love_line.setVisibility(View.INVISIBLE);
        tvMyvideo.setTextColor(getResources().getColor(R.color.text_noclick_color));
        tvMylive.setTextColor(getResources().getColor(R.color.text_noclick_color));
        tvMylove.setTextColor(getResources().getColor(R.color.text_noclick_color));
        tvMyvideo.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvMylive.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvMylove.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        if (position == 0) {
            tvMyvideo.setTextColor(getResources().getColor(R.color.white));
            tvMyvideo.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_video_line.setVisibility(View.VISIBLE);
        } else if (position == 1) {
            tvMylive.setTextColor(getResources().getColor(R.color.white));
            tvMylive.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_live_line.setVisibility(View.VISIBLE);
        } else if (position == 2) {
            tvMylove.setTextColor(getResources().getColor(R.color.white));
            tvMylove.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_love_line.setVisibility(View.VISIBLE);
        }
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
        MineItemInfo mMineItemInfo = responseBean.getData();
        if (mMineItemInfo != null) {
            MineItemInfo userExtra = mMineItemInfo.getUserExtra();
            if (userExtra != null && tvLikenum != null) {
                tvLikenum.setText("获赞 " + userExtra.getLike_number());
                tvSkbnum.setText("寿康宝 " + userExtra.getSkb());
                show_title = userExtra.getShow_title();
                tv_showtitle.setText("" + show_title);
                tv_myfollow.setText("关注 " + mMineItemInfo.getUserFollowCount());
                int isFollow = mMineItemInfo.getIsFollow();
                if (isFollow == 0) {
                    ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvFollow, R.color.red);
                    tvFollow.setText("关注");
                } else {
                    ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvFollow, R.color.follow_color);
                    tvFollow.setText("取消关注");
                }
                tvMyvideo.setText("视频 " + mMineItemInfo.getShortVideoCount());
                tvMylive.setText("直播 " + mMineItemInfo.getLiveRoomCount());
                tvMylove.setText("喜欢 " + mMineItemInfo.getShortVideoFollowUserCount());

                name = userExtra.getUser_name();
                tvName.setText(name);
                String avatar = userExtra.getAvatar();
                GlideDownLoadImage.getInstance().loadCircleHeadImageCenter(mActivity, avatar, ivHead);
            }
        }
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
