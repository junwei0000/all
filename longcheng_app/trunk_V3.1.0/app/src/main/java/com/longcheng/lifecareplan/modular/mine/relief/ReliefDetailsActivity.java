package com.longcheng.lifecareplan.modular.mine.relief;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencyDetaiBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencyDetailActivity;
import com.longcheng.lifecareplan.modular.mine.emergencys.GridAdapter;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReliefDetailsActivity extends BaseListActivity<ReliefDetailsContract.View, ReliefDetailsPresenterImp<ReliefDetailsContract.View>> implements ReliefDetailsContract.View {


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
    @BindView(R.id.tv_lines)
    TextView tvLines;
    @BindView(R.id.iv_top_bg)
    ImageView ivTopBg;
    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieeqi)
    TextView itemTvJieeqi;
    @BindView(R.id.item_layout_shenfen)
    LinearLayout itemLayoutShenfen;
    @BindView(R.id.ll_usertab_info)
    LinearLayout llUsertabInfo;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.tv_engry)
    TextView tvEngry;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.tv_aimsengry)
    TextView tvAimsengry;
    @BindView(R.id.prl_gain)
    MyListView myListView;
    @BindView(R.id.tv_send_bless)
    TextView tvSendBless;
    @BindView(R.id.plsv)
    PullToRefreshScrollView plsv;

    private String checkUid;
    int page = 1;
    private MyDialog mUpdaDialog;
    private PopupWindow popupWindow;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_relief_details;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("救济详情");
        setOrChangeTranslucentColor(toolbar, null);
        if (mDetailAdapter == null) mDetailAdapter = new ReliefDetailAdapter(mContext);
        myListView.setAdapter(mDetailAdapter);
    }

    @Override
    public void initDataAfter() {
        checkUid = getIntent().getStringExtra("userid");

        mPresent.getDetailsTop(checkUid);
        getListData(1);

    }


    @Override
    public void setListener() {
        ScrowUtil.ScrollViewConfigAll(plsv);
        plsv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Log.d(TAG, "onPullDownToRefresh: page" + page);
                getListData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page++;
                Log.d(TAG, "onPullUpToRefresh: page" + page);
                getListData(page);

            }
        });
    }


    private void getListData(int page) {

        mPresent.getDetailsList(checkUid, String.valueOf(page));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected ReliefDetailsPresenterImp<ReliefDetailsContract.View> createPresent() {
        return new ReliefDetailsPresenterImp<>(mContext, this);
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
    public void getReliefDetailSuccess(ReliefDetailsBean.DataBean data) {
        if (data == null) return;
        ReliefDetailsBean.DataBean info = data;
        //祝福您的
        tvEngry.setText((TextUtils.isEmpty(info.bless_me) ? "" : info.bless_me));
        //已收能量
        textView6.setText((TextUtils.isEmpty(info.number) ? "" : info.number));
        //目标
        tvAimsengry.setText((TextUtils.isEmpty(info.airm) ? "" : info.airm));
        //姓名
        itemTvName.setText((TextUtils.isEmpty(info.user_name) ? "" : info.user_name));
        itemTvJieeqi.setText((TextUtils.isEmpty(info.user_jieqi_branch_name) ? "" : info.user_jieqi_branch_name) + "·" +
                (TextUtils.isEmpty(info.user_jieqi_name) ? "" : info.user_jieqi_name)
        );
        List<String> images = data.images;
        itemLayoutShenfen.removeAllViews();
        for (int i = 0; i < images.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            ImageView imageView = new ImageView(mContext);
            int dit = DensityUtil.dip2px(mContext, 16);
            int jian = DensityUtil.dip2px(mContext, 3);
            linearLayout.setPadding(0, 2, jian, 2);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
            GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, images.get(i), imageView);
            linearLayout.addView(imageView);

            itemLayoutShenfen.addView(linearLayout);

        }
        //用户头像
        String user_avatar = TextUtils.isEmpty(info.user_avatar) ? "" : info.user_avatar;
        GlideDownLoadImage.getInstance().loadCircleImageCommune(this, user_avatar, itemIvThumb);

        String time = TextUtils.isEmpty(info.now_jieqi.time) ? "" : info.now_jieqi.time;
        String cn = TextUtils.isEmpty(info.now_jieqi.cn) ? "" : info.now_jieqi.cn;
        //当前节气时间
        tvDate.setText(cn + ":" + time);


        String now_jieqi_img = TextUtils.isEmpty(info.now_jieqi_img) ? "" : info.now_jieqi_img;
        GlideDownLoadImage.getInstance().loadCircleImageCommune(this, now_jieqi_img, ivTopBg, 0);


    }

    ReliefDetailAdapter mDetailAdapter;
    List<ReliefDetailsListBean.DataBean> allList = new ArrayList<>();

    @Override
    public void getListSuccess(List<ReliefDetailsListBean.DataBean> data, String sPage) {
        ListUtils.getInstance().RefreshCompleteS(plsv);
        if (data == null) return;
        int backPage = Integer.valueOf(sPage);

        int size = data == null ? 0 : data.size();
        if (backPage == 1) {
            mDetailAdapter = null;
            allList.clear();
            showNoMoreData(false, myListView);
            mDetailAdapter.setData(allList);
        }
        if (size > 0) {
            allList.addAll(data);
            mDetailAdapter.setData(allList);
        }

        if (size < pageSize) {
            ScrowUtil.ScrollViewDownConfig(plsv);
            showNoMoreData(true, myListView);
        } else {
            ScrowUtil.ScrollViewConfigAll(plsv);
        }

    }


    @OnClick({R.id.pagetop_iv_left, R.id.tv_send_bless})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.pagetop_iv_left:
                doFinish();
                break;

            case R.id.tv_send_bless:
                changeIcon(view);
                break;
        }
    }

    private void changeIcon(View v) {
        View popupView = null;
        if (popupWindow == null) {
            popupView = View.inflate(this, R.layout.popup_relief_detail, null);
            ImageView iv_close = popupView.findViewById(R.id.iv_close);
            TextView tv_help_pay = popupView.findViewById(R.id.tv_help_pay);


            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null) {
                        lighton();
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
            });
            tv_help_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSureDialog();
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            });

            //指明popupwindow的宽度和高度
            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);


            ColorDrawable dw = new ColorDrawable(0x0000000);
            // 设置背景图片， 必须设置，不然动画没作用
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.setFocusable(true);
//            lightoff();
            // 设置点击popupwindow外屏幕其它地方消失
            popupWindow.setOutsideTouchable(true);

            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(200);

            popupView.startAnimation(animation);
            popupWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popupWindow != null) {
                    lighton();
                    popupWindow.dismiss();
                    popupWindow = null;
                }

            }
        });


    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }


    public void showSureDialog() {
        if (mContext == null) {
            return;
        }

        mUpdaDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_pay);// 创建Dialog并设置样式主题
        mUpdaDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = mUpdaDialog.getWindow();
        window.setGravity(Gravity.CENTER);

        WindowManager m = getWindowManager();

        mUpdaDialog.show();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = mUpdaDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 3 / 4;
        mUpdaDialog.getWindow().setAttributes(p); //设置生效
        TextView tv_sure = mUpdaDialog.findViewById(R.id.tv_sure);
        TextView tv_title = mUpdaDialog.findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        TextView tv_cancel = mUpdaDialog.findViewById(R.id.tv_cancel);
        TextView tv_power = mUpdaDialog.findViewById(R.id.tv_power);
        tv_power.setText("365");


        tv_sure.setOnClickListener(v -> {
            mPresent.helpneed_pay(Integer.valueOf(checkUid), 365);
            mUpdaDialog.dismiss();
            mUpdaDialog = null;
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUpdaDialog.dismiss();
                mUpdaDialog = null;
            }
        });
    }


}
