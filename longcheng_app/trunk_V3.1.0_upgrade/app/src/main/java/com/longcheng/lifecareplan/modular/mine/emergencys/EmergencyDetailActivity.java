package com.longcheng.lifecareplan.modular.mine.emergencys;


import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailHelpDialogUtils;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.EmergencyDetaiBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.EmergencysPay2Bean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.EmergencysPayBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HarvestBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HavestList;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.Img;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class EmergencyDetailActivity extends BaseListActivity<EmergencyDetailContract.View, EmergencyDetailImp<EmergencyDetailContract.View>> implements EmergencyDetailContract.View {

    private int Help_need_id;
    private String user_id;
    private ImageLoader asyncImageLoader;
    @BindView(R.id.iv_top_bg)
    ImageView iv_top_bg;
    @BindView(R.id.item_tv_name)
    TextView item_tv_name;
    @BindView(R.id.item_tv_jieeqi)
    TextView item_tv_jieeqi;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.item_iv_thumb)
    ImageView item_iv_thumb;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_engry)
    TextView tv_engry;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.tv_help)
    TextView tv_help;
    @BindView(R.id.tv_target)
    TextView tv_target;
    @BindView(R.id.item_layout_shenfen)
    LinearLayout item_layout_shenfen;
    @BindView(R.id.prl_gain)
    MyListView prl_gain;
    @BindView(R.id.exchange)
    PullToRefreshScrollView exchange;
    private DetailHelpDialogUtils mDetailHelpDialogUtils;
    private PopupWindow popupWindow;
    private EmergencyDetaiBean.DetailBean bean;
    private MyDialog mUpdaDialog;
    private int pay;
    private int[] pa;
    private int page = 1;
    private String where = "1";
    private boolean refreshStatus = false;
    private String status = "";
    private String head = "";
    private String jieqi = "";
    private ArrayList<Img> img = new ArrayList<>();

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_emergency_detail;
    }

    @Override
    public void initDataAfter() {
        bean = new EmergencyDetaiBean.DetailBean();
        setOrChangeTranslucentColor(toolbar, null);
        pageTopTvName.setText("救急详情");
        user_id = UserUtils.getUserId(mContext);
        if (getIntent().hasExtra("Help_need_id")) {
            Help_need_id = getIntent().getIntExtra("Help_need_id", 0);
            LogUtils.e("Help_need_id" + Help_need_id);
        }
        if (getIntent().hasExtra("jieqi")) {
            jieqi = getIntent().getStringExtra("jieqi");
            item_tv_jieeqi.setText(jieqi);
        }
        if (getIntent().hasExtra("head")) {
            head = getIntent().getStringExtra("head");

        }
        if (getIntent().hasExtra("where")) {
            where = getIntent().getStringExtra("where");
        }
        if (getIntent().hasExtra("img")) {
            img = (ArrayList<Img>) getIntent().getSerializableExtra("img");
            LogUtils.e(img.toString());
            showImg(img);
        }
        if (where.equals("1")) {
            tv_help.setVisibility(View.VISIBLE);
        } else {
            if (getIntent().hasExtra("status")) {
                status = getIntent().getStringExtra("status");
            }
            if (status.equals("1")) {
                tv_help.setVisibility(View.VISIBLE);
            } else {
                tv_help.setVisibility(View.GONE);
            }
        }

        asyncImageLoader = new ImageLoader(mContext, "headImg");
        mPresent.helpneed_info(user_id, Help_need_id);
        mPresent.helpneed_order(user_id, Help_need_id, page, pageSize);
        ScrowUtil.ScrollViewConfigAll(exchange);
        exchange.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                mPresent.helpneed_info(user_id, Help_need_id);
                mPresent.helpneed_order(user_id, Help_need_id, page, pageSize);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshStatus = true;
                page++;
                mPresent.helpneed_order(user_id, Help_need_id, page, pageSize);
            }
        });
    }

    @Override
    public void setListener() {
        tv_help.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
    }

    private void back() {
        doFinish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_help:
                changeIcon(tv_help);
                break;
            case R.id.pagetop_layout_left:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                back();
                break;
        }
    }

    @Override
    protected EmergencyDetailImp<EmergencyDetailContract.View> createPresent() {
        return new EmergencyDetailImp<>(mContext, this);
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void ListSuccess(EmergencyDetaiBean responseBean) {
        ListUtils.getInstance().RefreshCompleteS(exchange);
        if (responseBean.getData() != null) {
            bean = responseBean.getData();
            pay = bean.getPayAbility()[0];
            if (bean.getHelpNeed().getStatus() >= 2) {
                tv_help.setVisibility(View.GONE);
            } else {
                tv_help.setVisibility(View.VISIBLE);
            }
            LogUtils.e("EmergencyDetaiBean", bean.getCurrent_jieqi().getCn());
            item_tv_name.setText(bean.getHelpNeed().getUser_name());

            asyncImageLoader.DisplayImage(head, item_iv_thumb);
            GlideDownLoadImage.getInstance().loadCircleImageCommune(this, bean.getSolar_term_img(), iv_top_bg, 0);

            tv_date.setText(bean.getCurrent_jieqi().getCn() + ":" + bean.getCurrent_jieqi().getTime());
            tv_engry.setText(CommonUtil.removeTrim(bean.getHelpNeed().getCumulative_number() + ""));
            tv_target.setText(CommonUtil.removeTrim(bean.getHelpNeed().getSuper_ability() + ""));
            textView6.setText(CommonUtil.removeTrim(bean.getHelpNeed().getObtain_super_ability() + ""));

        }
    }

    @Override
    public void pay(EmergencysPayBean responseBean) {
        EmergencysPay2Bean bean = responseBean.getData();
        //bean.getHelp_need_id()
        ToastUtils.showToast(responseBean.getMsg());
        mPresent.helpneed_info(user_id, Help_need_id);
        mPresent.helpneed_order(user_id, Help_need_id, 1, pageSize);
        if (where.equals("1")) {
            tv_help.setVisibility(View.VISIBLE);
        } else {
            tv_help.setVisibility(View.GONE);

        }

    }


    private MyHarvestAdapter mAdapter;

    @Override
    public void order(HarvestBean responseBean, int backPage) {
        LogUtils.e("HarvestBean", responseBean.getData().toString());

        ListUtils.getInstance().RefreshCompleteS(exchange);
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
        } else if (status_.equals("200")) {
            HavestList bean = responseBean.getData();
            if (bean != null) {


                List<HavestList.Order> helpList = bean.getOrder();
                int size = helpList == null ? 0 : helpList.size();
                if (page == 1) {
                    mAdapter = null;
                    showNoMoreData(false, prl_gain);
                }
                LogUtils.e(bean.getOrder().size() + "getOrder");
                if (mAdapter == null) {
                    mAdapter = new MyHarvestAdapter(mActivity, helpList);
                    prl_gain.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(helpList, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
        }
        prl_gain.setVisibility(View.VISIBLE);
    }

    private void showImg(List<Img> identity_img) {


        // List<EmergencyDetaiBean.DetailBean.CurrentUserIdentitys.Img> identity_img = cuis.getImg();
        item_layout_shenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (int i = 0; i < identity_img.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(this);
                ImageView imageView = new ImageView(this);
                int dit = DensityUtil.dip2px(this, 16);
                int jian = DensityUtil.dip2px(this, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(this, identity_img.get(i).image, imageView);
                linearLayout.addView(imageView);
                item_layout_shenfen.addView(linearLayout);
            }
        }
    }

    @Override
    public void ListError() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    private void changeIcon(View v) {
        View popupView = null;
        if (popupWindow == null) {
            popupView = View.inflate(this, R.layout.pop_emergency_detail, null);
            LinearLayout ll_close = popupView.findViewById(R.id.ll_close);
            RecyclerView rc_power = popupView.findViewById(R.id.rc_power);
            TextView tv_help_pay = popupView.findViewById(R.id.tv_help_pay);

            pa = bean.getPayAbility();
            pay = pa[0];
            GridAdapter gd = new GridAdapter(pa, EmergencyDetailActivity.this);
            rc_power.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            rc_power.setAdapter(gd);
            ll_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow != null) {
                        lighton();
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
            });
            gd.setOnItemClickListener(new GridAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    gd.setData(position);
                    pay = pa[position];
                }


            });
            tv_help_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lightoff();
                    popupWindow.dismiss();
                    popupWindow = null;
                    showSureDialog();
                }
            });
            // 参数2,3：指明popupwindow的宽度和高度
            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);


            ColorDrawable dw = new ColorDrawable(0x0000000);
            // 设置背景图片， 必须设置，不然动画没作用
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.setFocusable(true);
            lightoff();
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

    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
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
        TextView tv_cancel = mUpdaDialog.findViewById(R.id.tv_cancel);
        TextView tv_power = mUpdaDialog.findViewById(R.id.tv_power);
        tv_power.setText(pay + "");


        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresent.helpneed_pay(user_id, Help_need_id, pay);
                mUpdaDialog.dismiss();
                mUpdaDialog = null;
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUpdaDialog.dismiss();
                mUpdaDialog = null;
            }
        });
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.ScrollViewDownConfig(exchange);
            if (size > 0 || (page > 1 && size >= 0)) {
                showNoMoreData(true, prl_gain);
            }
        } else {
            ScrowUtil.ScrollViewConfigAll(exchange);
        }
    }
}
