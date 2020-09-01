package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter.ApplyJieQi18Adapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ApplyItemBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.DetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.SendPayUtils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.LongClickButton;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 福券申请
 */
public class FuQApplyActivity extends BaseListActivity<ReportContract.View, ReportPresenterImp<ReportContract.View>> implements ReportContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.item_iv_img)
    ImageView itemIvImg;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieqi)
    TextView itemTvJieqi;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.item_layout_shenfen)
    LinearLayout itemLayoutShenfen;
    @BindView(R.id.vp_jieqi)
    ViewPager vp_jieqi;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.item_layout_sub)
    LongClickButton itemLayoutSub;
    @BindView(R.id.item_tv_num)
    TextView itemTvNum;
    @BindView(R.id.item_tv_add)
    LongClickButton itemTvAdd;
    @BindView(R.id.tv_apply)
    TextView tvApply;
    @BindView(R.id.ll_dot)
    LinearLayout ll_dot;
    @BindView(R.id.tv_showcont)
    TextView tv_showcont;

    int num = 1;
    int maxNum = 24;

    String bless_apply_money;
    String user_super_ability;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.item_layout_sub:
                if (num > 1) {
                    num--;
                }
                itemTvNum.setText("" + num);
                break;
            case R.id.item_tv_add:
                if (num < maxNum) {
                    num++;
                }
                itemTvNum.setText("" + num);
                break;
            case R.id.tv_apply:
                super_ability = PriceUtils.getInstance().gteMultiplySumPrice(bless_apply_money, "" + (num * 9));
                super_ability = PriceUtils.getInstance().seePrice(super_ability);
                showPopupWindow();
                break;
        }
    }

    String super_ability;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fuqrep_apply;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("福券申请");
        pagetopLayoutLeft.setOnClickListener(this);
        itemLayoutSub.setOnClickListener(this);
        itemTvAdd.setOnClickListener(this);
        tvApply.setOnClickListener(this);
        //连续减
        itemLayoutSub.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
            @Override
            public void repeatAction() {
                onClick(itemLayoutSub);
            }
        }, 50);
        //连续加
        itemTvAdd.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
            @Override
            public void repeatAction() {
                onClick(itemTvAdd);
            }
        }, 50);
    }

    @Override
    public void initDataAfter() {
        String shoevon = "";
        if (HelpWithFragmentNew.zangfus != null && HelpWithFragmentNew.zangfus.size() > 0) {
            for (String str : HelpWithFragmentNew.zangfus) {
                shoevon = shoevon + str + "\n";
            }
            tv_showcont.setText(shoevon);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.getBlesscardApply();
    }

    @Override
    protected ReportPresenterImp<ReportContract.View> createPresent() {
        return new ReportPresenterImp<>(mContext, this);
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
    public void ListSuccess(ReportDataBean responseBean, int backPage) {

    }

    @Override
    public void DetailSuccess(FQDetailDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            DetailAfterBean mDataBean = responseBean.getData();
            ArrayList<ApplyItemBean> jieqiByUser = mDataBean.getJieqiByUser();
            int usable_bless_card_count = mDataBean.getUsable_bless_card_count();
            if (usable_bless_card_count <= maxNum) {
                maxNum = usable_bless_card_count;
            }
            bless_apply_money = mDataBean.getBless_apply_money();
            showUserView(mDataBean.getUserInfo());
            showJieQiView(jieqiByUser);
            tvNum.setText("可用" + usable_bless_card_count + "张");
        }
    }

    @Override
    public void lingQuSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            Intent intent = new Intent(mActivity, ApplySuccessActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            doFinish();
        }
    }

    @Override
    public void Error() {
    }


    private void showUserView(ApplyItemBean userInfo) {
        user_super_ability = userInfo.getSuper_ability();
        GlideDownLoadImage.getInstance().loadCircleImage(userInfo.getUser_img(), itemIvImg);
        itemTvName.setText(CommonUtil.setName(userInfo.getUser_name()));
        itemTvJieqi.setText(userInfo.getJieqi());
        tvDate.setText(DatesUtils.getInstance().getNowTime("yyyy-MM-dd"));
        List<String> identity_img = userInfo.getIdentity_img();
        itemLayoutShenfen.removeAllViews();
        if (identity_img != null && identity_img.size() > 0) {
            for (String url : identity_img) {
                LinearLayout linearLayout = new LinearLayout(mContext);
                ImageView imageView = new ImageView(mContext);
                int dit = DensityUtil.dip2px(mContext, 16);
                int jian = DensityUtil.dip2px(mContext, 2);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, url, imageView);
                linearLayout.addView(imageView);
                itemLayoutShenfen.addView(linearLayout);
            }
        }

    }

    int jieqiJindex;
    int jieqiPage = 3;
    private List<ImageView> dotList = new ArrayList<>();

    private void showJieQiView(ArrayList<ApplyItemBean> jieqiByUser) {
        ApplyJieQi18Adapter mDetailJieQiAdapter = new ApplyJieQi18Adapter(mContext, jieqiByUser);
        vp_jieqi.setAdapter(mDetailJieQiAdapter);
        jieqiPage = mDetailJieQiAdapter.getPage();
        vp_jieqi.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                jieqiJindex = position;
                for (int i = 0; i < jieqiPage; i++) {
                    ImageView img = dotList.get(i);
                    if (i == jieqiJindex % jieqiPage) {
                        img.setImageResource(R.drawable.corners_oval_sing_yellow);
                    } else {
                        img.setImageResource(R.drawable.corners_oval_graycommuebian);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ll_dot.removeAllViews();
        dotList.clear();
        for (int i = 0; i < jieqiPage; i++) {
            ImageView img = new ImageView(mContext); // 现在空
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            params.setMargins(10, 0, 10, 0);
            img.setLayoutParams(params);
            if (i == jieqiJindex % jieqiPage) {
                img.setImageResource(R.drawable.corners_oval_sing_yellow);
            } else {
                img.setImageResource(R.drawable.corners_oval_graycommuebian);
            }
            dotList.add(img);
            ll_dot.addView(img);
        }
    }

    MyDialog selectDialog;
    TextView tv_applyablity, detailhelp_tv_superengry;

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_fuq_apply);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = selectDialog.findViewById(R.id.layout_cancel);
            tv_applyablity = selectDialog.findViewById(R.id.tv_applyablity);
            detailhelp_tv_superengry = selectDialog.findViewById(R.id.detailhelp_tv_superengry);
            TextView btn_pay = selectDialog.findViewById(R.id.btn_pay);

            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    if (Double.valueOf(super_ability) > Double.valueOf(user_super_ability)) {
                        if (mSendPayUtils == null) {
                            mSendPayUtils = new SendPayUtils(mActivity);
                        }
                        mSendPayUtils.setAbilityDialog();
                    } else {
                        mPresent.BlesscardDoApply(4, num, super_ability);
                    }
                }
            });
        } else {
            selectDialog.show();
        }
        user_super_ability = PriceUtils.getInstance().seePrice(user_super_ability);
        detailhelp_tv_superengry.setText(user_super_ability);
        tv_applyablity.setText(super_ability + "");
    }

    SendPayUtils mSendPayUtils;

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
