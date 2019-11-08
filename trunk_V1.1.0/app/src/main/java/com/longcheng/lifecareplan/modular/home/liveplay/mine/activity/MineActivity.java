package com.longcheng.lifecareplan.modular.home.liveplay.mine.activity;

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
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePushDataInfo;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.AllFragment;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.ComingFragment;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.PendingFragment;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的
 */
public class MineActivity extends BaseActivityMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.tv_likenum)
    TextView tvLikenum;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_skbnum)
    TextView tvSkbnum;
    @BindView(R.id.tv_myvideo)
    TextView tvMyvideo;
    @BindView(R.id.tv_mylive)
    TextView tvMylive;
    @BindView(R.id.tv_mylike)
    TextView tvMylike;
    @BindView(R.id.vPager)
    ViewPager vPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private int position;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.tv_myvideo:
                position = 0;
                selectPage();
                break;
            case R.id.tv_mylive:
                position = 1;
                selectPage();
                break;
            case R.id.tv_mylike:
                position = 2;
                selectPage();
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_mine;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }


    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvMyvideo.setOnClickListener(this);
        tvMylive.setOnClickListener(this);
        tvMylike.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        pageTopTvName.setText("我的");
        String avatar = (String) SharedPreferencesHelper.get(mActivity, "avatar", "");
        GlideDownLoadImage.getInstance().loadCircleHeadImageCenter(mActivity, avatar, ivHead);
        position = 0;
        initFragment();
        setPageAdapter();
    }

    /**
     * @param
     * @name 初始化Fragment
     * @time 2017/11/24 10:23
     * @author MarkShuai
     */
    private void initFragment() {
        MyVideoFrag mAllFragment = new MyVideoFrag();
        fragmentList.add(mAllFragment);

        MyVideoFrag mComingFragment = new MyVideoFrag();
        fragmentList.add(mComingFragment);

        MyVideoFrag mPendingFragment = new MyVideoFrag();
        fragmentList.add(mPendingFragment);
    }

    /**
     * @param
     * @name 设置PagerAdapter
     * @time 2017/11/24 10:44
     * @author MarkShuai
     */
    FragmentAdapter tabPageAdapter;

    private void setPageAdapter() {
        tabPageAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        tabPageAdapter.setOnReloadListener(new FragmentAdapter.OnReloadListener() {
            @Override
            public void onReload() {
                fragmentList = null;
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(new AllFragment());
                list.add(new ComingFragment());
                list.add(new PendingFragment());
                tabPageAdapter.setPagerItems(list);
                Log.e("onReload", "onReload");
            }
        });
        vPager.setAdapter(tabPageAdapter);
        selectPage();
        vPager.setOffscreenPageLimit(3);
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position_) {
                position = position_;
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
        tvMyvideo.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvMylive.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvMylike.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        if (position == 0) {
            tvMyvideo.setTextColor(getResources().getColor(R.color.red));
        } else if (position == 1) {
            tvMylive.setTextColor(getResources().getColor(R.color.red));
        } else if (position == 2) {
            tvMylike.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(mContext, this);
    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void BackPlayListSuccess(LivePushDataInfo responseBean) {

    }

    @Override
    public void BackVideoListSuccess(LivePushDataInfo responseBean) {

    }

    @Override
    public void Error() {
    }


    private void back() {
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
