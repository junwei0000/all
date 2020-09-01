package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldContract;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldImp;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.YQShareDialogUtils;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldBean;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldResponseBean;
import com.longcheng.lifecareplan.modular.mine.invitation.activity.InvitationAct;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.zxing.CreateQRImage;

import butterknife.BindView;

/**
 * 分享福券
 * Created by Burning on 2018/9/3.
 */

public class ReceiveSuccessActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.iv_qr)
    ImageView ivQr;
    @BindView(R.id.iv_userimg)
    ImageView ivUserimg;
    @BindView(R.id.tv_yqrecord)
    TextView tvYqrecord;
    @BindView(R.id.top_title_name)
    TextView topTitleName;

    @BindView(R.id.tv_fu_hinit)
    TextView tvFuHinit;
    @BindView(R.id.layout_yaoqing)
    LinearLayout layoutYaoqing;

    private int pagetype = 1; // 1 分享领福包 2、分享炫耀
    FBShareDialogUtils fbShareDialogUtils;

    String invite_user_url = Config.BASE_HEAD_URL + "home/app/index";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_yqrecord:
                if (fbShareDialogUtils == null) {
                    fbShareDialogUtils = new FBShareDialogUtils(this);
                }
                fbShareDialogUtils.setPagetype(pagetype);
                fbShareDialogUtils.setInvite_user_url(invite_user_url);
                fbShareDialogUtils.setShareDialog();
                break;

        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.receivesuccess_layout;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvYqrecord.setOnClickListener(this);
        setQRImage();
    }

    @Override
    public void initDataAfter() {
        pagetype = getIntent().getIntExtra("pagetype", 1);
        invite_user_url = getIntent().getStringExtra("dataurl");
        int hei = (int) (DensityUtil.screenWith(mContext) * 1.0467);
        layoutTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hei));
        if (pagetype == 1) {
            layoutTop.setBackgroundResource(R.mipmap.top_bg_fu_shared);
            tvFuHinit.setVisibility(View.VISIBLE);
            tvFuHinit.setText("识别二维码接福包");
        } else if (pagetype == 2) {
            tvFuHinit.setVisibility(View.GONE);
            tvYqrecord.setText("分享福运");
            layoutTop.setBackgroundResource(R.mipmap.diloag_fu_bg_top);
        }
    }
    private void setQRImage() {
        if (invite_user_url != null ) {
            CreateQRImage.createQRImage(invite_user_url, ivQr);
        }
    }
    public void onError(String code) {

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
