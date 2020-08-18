package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.df;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.adapter.FragmentAdapter;
import com.longcheng.lifecareplan.modular.helpwith.connon.activity.GrupUtils;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 能量中心--代付
 */
public class PionDaiFuActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.userorder_tv_all)
    TextView userorderTvAll;
    @BindView(R.id.userorder_tv_coming)
    TextView userorderTvComing;
    @BindView(R.id.userorder_tv_overed)
    TextView userorderTvOvered;
    @BindView(R.id.userorder_iv_cursor)
    ImageView userorderIvCursor;
    @BindView(R.id.userorder_vPager)
    ViewPager userorderVPager;
    @BindView(R.id.userorder_tv_cancel)
    TextView userorderTvCancel;

    @BindView(R.id.layout_order)
    LinearLayout layoutOrder;
    @BindView(R.id.layout_inner)
    LinearLayout layoutInner;
    @BindView(R.id.absolut_chair)
    AbsoluteLayout absolutChair;
    @BindView(R.id.layout_group_center)
    LinearLayout layoutGroupCenter;
    @BindView(R.id.relat_group)
    RelativeLayout relatGroup;
    @BindView(R.id.layout_zudui)
    RelativeLayout layoutZudui;
    @BindView(R.id.bottom_iv_order)
    ImageView bottomIvOrder;
    @BindView(R.id.bottom_tv_order)
    TextView bottomTvOrder;
    @BindView(R.id.bottom_layout_order)
    LinearLayout bottomLayoutOrder;
    @BindView(R.id.bottom_iv_zudui)
    ImageView bottomIvZudui;
    @BindView(R.id.bottom_tv_zudui)
    TextView bottomTvZudui;
    @BindView(R.id.bottom_layout_zudui)
    LinearLayout bottomLayoutZudui;
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    @BindView(R.id.layout_notdate)
    LinearLayout layout_notdate;
    @BindView(R.id.not_date_img)
    ImageView not_date_img;
    @BindView(R.id.not_date_cont)
    TextView not_date_cont;
    @BindView(R.id.not_date_btn)
    TextView not_date_btn;


    private List<Fragment> fragmentList = new ArrayList<>();
    private int position;
    /**
     * 自己是否是队长 1 是
     */
    int myrole;
    String entrepreneurs_team_item_id;
    HashMap<Integer, CHelpListDataBean.CHelpListItemBean> mHashmap = new HashMap<>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.userorder_tv_all:
                position = 0;
                selectPage();
                break;
            case R.id.userorder_tv_coming:
                position = 1;
                selectPage();
                break;
            case R.id.userorder_tv_cancel:
                position = 2;
                selectPage();
                break;
            case R.id.userorder_tv_overed:
                position = 3;
                selectPage();
                break;
            case R.id.bottom_layout_order:
                orderShowStatus = true;
                showBottomView();
                break;
            case R.id.bottom_layout_zudui:
                orderShowStatus = false;
                showBottomView();
                break;
            case R.id.tv_edit:
                if (myrole == 1) {
                    setDialog();
                } else {
                    setDialog();
                }
                break;
            case R.id.layout_notdate:
                break;
            case R.id.not_date_btn:
                createOrderTeam();
                break;

        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pionner_zudui_order;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        not_date_btn.setText("创建我的组队");
        not_date_btn.setBackgroundResource(R.drawable.corners_bg_redbiantran);
        not_date_btn.setTextColor(getResources().getColor(R.color.red));
        not_date_cont.setText("您还未加入组队");
        not_date_btn.setVisibility(View.VISIBLE);
        not_date_btn.setOnClickListener(this);
        layout_notdate.setOnClickListener(this);
        not_date_img.setBackgroundResource(R.mipmap.order_group_create);
        pagetopLayoutLeft.setOnClickListener(this);
        userorderTvAll.setOnClickListener(this);
        userorderTvComing.setOnClickListener(this);
        userorderTvCancel.setOnClickListener(this);
        userorderTvOvered.setOnClickListener(this);

        bottomLayoutOrder.setOnClickListener(this);
        bottomLayoutZudui.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        bmpW = DensityUtil.dip2px(mContext, 30);
        userorderIvCursor.setLayoutParams(new LinearLayout.LayoutParams(bmpW, DensityUtil.dip2px(mContext, 2)));
        position = 1;
        initFragment();
        setPageAdapter();
        getData(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("tag", "onNewINtent执行了");
        getData(intent);
    }

    private void getData(Intent intent) {
        orderShowStatus = intent.getBooleanExtra("orderShowStatus", true);
    }


    boolean orderShowStatus = true;

    private void showBottomView() {
        layoutInner.setVisibility(View.GONE);
        bottomIvOrder.setBackgroundResource(R.mipmap.pion_order_gray);
        bottomTvOrder.setTextColor(getResources().getColor(R.color.main_tab_text_color_select));
        bottomIvZudui.setBackgroundResource(R.mipmap.pion_order_zudui_gray);
        bottomTvZudui.setTextColor(getResources().getColor(R.color.main_tab_text_color_select));
        if (orderShowStatus) {
            bottomIvOrder.setBackgroundResource(R.mipmap.pion_order_red);
            bottomTvOrder.setTextColor(getResources().getColor(R.color.red));
            layoutOrder.setVisibility(View.VISIBLE);
            layoutZudui.setVisibility(View.GONE);
            pageTopTvName.setText("请购福祺宝");
        } else {
            bottomIvZudui.setBackgroundResource(R.mipmap.pion_order_zudui_red);
            bottomTvZudui.setTextColor(getResources().getColor(R.color.red));
            layoutOrder.setVisibility(View.GONE);
            layoutZudui.setVisibility(View.VISIBLE);
            pageTopTvName.setText("我的组队");
        }
        getOrderTeamInfo();
    }


    public void getOrderTeamInfo() {
        Observable<CHelpListDataBean> observable = Api.getInstance().service.getOrderTeamInfo(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CHelpListDataBean>() {
                    @Override
                    public void accept(CHelpListDataBean responseBean) throws Exception {
                        if (responseBean.getStatus().equals("200")) {
                            ArrayList<CHelpListDataBean.CHelpListItemBean> data = responseBean.getData();
                            mHashmap.clear();
                            if (data != null && data.size() > 0) {
                                layout_notdate.setVisibility(View.GONE);
                                for (int i = 0; i < data.size(); i++) {
                                    mHashmap.put(i + 1, data.get(i));
                                    if (UserUtils.getUserId(mContext).equals(data.get(i).getUser_id())) {
                                        myrole = data.get(i).getRole();
                                        entrepreneurs_team_item_id = data.get(i).getEntrepreneurs_team_item_id();
                                    }
                                }
                                if (myrole == 1) {
                                    tv_edit.setText("解散当前组队");
                                } else {
                                    tv_edit.setText("退出当前组队");
                                }
                                tv_edit.setVisibility(View.VISIBLE);
                            } else {
                                layout_notdate.setVisibility(View.VISIBLE);
                            }
                            if (mGrupUtils == null) {
                                mGrupUtils = new GrupUtils(mActivity);
                            }
                            mGrupUtils.showChairAllViewFQBZuDui(groupnum,
                                    mHashmap, relatGroup, absolutChair, layoutGroupCenter);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void cancelOrderTeam() {
        Observable<ResponseBean> observable = Api.getInstance().service.cancelOrderTeam(UserUtils.getUserId(mContext), entrepreneurs_team_item_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        ToastUtils.showToast(responseBean.getMsg());
                        if (responseBean.getStatus().equals("200")) {
                            showBottomView();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }

    public void createOrderTeam() {
        Observable<ResponseBean> observable = Api.getInstance().service.createOrderTeam(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        ToastUtils.showToast(responseBean.getMsg());
                        if (responseBean.getStatus().equals("200")) {
                            showBottomView();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }


    int groupnum = 5;


    GrupUtils mGrupUtils;
    MyDialog selectDialog;
    TextView tv_title, tv_title2, tv_title3;

    public void setDialog() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_tixiandel);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            tv_title = selectDialog.findViewById(R.id.tv_title);
            tv_title2 = selectDialog.findViewById(R.id.tv_title2);
            tv_title3 = selectDialog.findViewById(R.id.tv_title3);
            TextView tv_off = selectDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectDialog.findViewById(R.id.tv_sure);
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    cancelOrderTeam();
                }
            });
        } else {
            selectDialog.show();
        }
        if (myrole == 1) {
            tv_title2.setText("解散组队将不再匹配大额订单");
            tv_title2.setVisibility(View.VISIBLE);
        } else {
            tv_title2.setText("退出组队将不再匹配大额订单");
            tv_title2.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 选择某页
     */
    private void selectPage() {
        userorderVPager.setCurrentItem(position, false);
        userorderTvAll.setTextColor(getResources().getColor(R.color.text_contents_color));
        userorderTvComing.setTextColor(getResources().getColor(R.color.text_contents_color));
        userorderTvCancel.setTextColor(getResources().getColor(R.color.text_contents_color));
        userorderTvOvered.setTextColor(getResources().getColor(R.color.text_contents_color));
        if (position == 0) {
            userorderTvAll.setTextColor(getResources().getColor(R.color.blue));
        } else if (position == 1) {
            userorderTvComing.setTextColor(getResources().getColor(R.color.blue));
        } else if (position == 2) {
            userorderTvCancel.setTextColor(getResources().getColor(R.color.blue));
        } else if (position == 2) {
            userorderTvOvered.setTextColor(getResources().getColor(R.color.blue));
        }
        InitImageView(position);
    }

    /**
     * @param
     * @name 初始化Fragment
     * @time 2017/11/24 10:23
     * @author MarkShuai
     */
    private void initFragment() {
        AllFragment mAllFragment = new AllFragment();
        fragmentList.add(mAllFragment);
        ComingFragment mComingFragment = new ComingFragment();
        fragmentList.add(mComingFragment);
        CancelFragment cancelFragment = new CancelFragment();
        fragmentList.add(cancelFragment);
        OveredFragment mOveredFragment = new OveredFragment();
        fragmentList.add(mOveredFragment);

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
                list.add(new CancelFragment());
                list.add(new OveredFragment());
                tabPageAdapter.setPagerItems(list);
                Log.e("onReload", "onReload");
            }
        });
        userorderVPager.setAdapter(tabPageAdapter);
        selectPage();
        userorderVPager.setOffscreenPageLimit(4);
        userorderVPager.addOnPageChangeListener(this);
    }


    /**
     * 初始化动画
     */
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度

    private void InitImageView(int selectArg0) {
        int screenW = DensityUtil.getPhoneWidHeigth(this).widthPixels;
        offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        userorderIvCursor.setImageMatrix(matrix);// 设置动画初始位置
        setLineFollowSlide(selectArg0);
    }

    /**
     * 设置跟随滑动
     */
    private void setLineFollowSlide(int selectArg0) {
        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        Animation animation = new TranslateAnimation(offset + one * currIndex, offset + one * selectArg0, 0, 0);// 显然这个比较简洁，只有一行代码。
        currIndex = selectArg0;
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(300);
        userorderIvCursor.startAnimation(animation);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        selectPage();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 是否有编辑订单处理
     */
    public static boolean editOrderStatus = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (editOrderStatus) {
            editOrderStatus = false;
            reLoadList();
        }
        showBottomView();
    }

    public void reLoadList() {
        if (tabPageAdapter != null)
            tabPageAdapter.reLoad();
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