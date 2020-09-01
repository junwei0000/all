package com.longcheng.lifecareplan.modular.mine.awordofgold.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.framgent.UpLoadDialogUtils;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldBean;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldResponseBean;
import com.longcheng.lifecareplan.modular.mine.invitation.activity.InvitationAct;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.apkupload.utils.InstallUtil;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.zxing.CreateQRImage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 一字千金
 * Created by Burning on 2018/9/3.
 */

public class AWordOfGoldOtherNewActivity extends BaseActivityMVP<AWordOfGoldContract.View, AWordOfGoldImp<AWordOfGoldContract.View>> implements AWordOfGoldContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_yaoqing)
    TextView tvYaoqing;
    @BindView(R.id.tvuser)
    TextView tvuser;
    @BindView(R.id.tvcontent)
    TextView tvcontent;
    @BindView(R.id.userinfo_tv_birthday)
    TextView userinfoTvBirthday;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.layout_result)
    LinearLayout layoutResult;
    @BindView(R.id.iv_qr)
    ImageView ivQr;
    @BindView(R.id.iv_userimg)
    ImageView ivUserimg;
    @BindView(R.id.tv_yqrecord)
    TextView tvYqrecord;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.layout_yaoqing)
    LinearLayout layoutYaoqing;

    private String pid, cid, aid;
    private String user_name, phone, area_simple, birthday;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_result:
                resultstatus = true;
                setChangeTabs();
                break;
            case R.id.tv_yaoqing:
                resultstatus = false;
                setChangeTabs();
                break;
            case R.id.tv_yqrecord:
                Intent intent = new Intent(mContext, InvitationAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.tv_save:
                if(yqShareDialogUtils==null){
                    yqShareDialogUtils=new YQShareDialogUtils(mActivity);
                }
                yqShareDialogUtils.setShareDialog();
                break;
        }
    }
    YQShareDialogUtils yqShareDialogUtils;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_awordofgoldnew;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvResult.setOnClickListener(this);
        tvYaoqing.setOnClickListener(this);
        tvYqrecord.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        int hei = (int) (DensityUtil.screenWith(mContext) * 0.59);
        layoutTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, hei));
        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvYqrecord, R.color.yellow_E95D1B);
        setQRImage();
        setChangeTabs();
    }

    @Override
    public void initDataAfter() {
        user_name = getIntent().getStringExtra("user_name");
        phone = getIntent().getStringExtra("phone");
        area_simple = getIntent().getStringExtra("area_simple");
        birthday = getIntent().getStringExtra("birthday");
        pid = getIntent().getStringExtra("pid");
        cid = getIntent().getStringExtra("cid");
        aid = getIntent().getStringExtra("aid");
        mPresent.newGoldWord(UserUtils.getUserId(mContext), UserUtils.getToken(),
                user_name, phone, area_simple, birthday, pid, cid, aid);
    }

    boolean resultstatus = true;

    private void setChangeTabs() {
        if (resultstatus) {
            tvResult.setBackgroundResource(R.mipmap.my_jisuan_bai);
            tvYaoqing.setBackgroundResource(R.mipmap.my_jisuan_hui);
            tvResult.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            tvYaoqing.setTextColor(getResources().getColor(R.color.text_healthcontents_color));
            layoutYaoqing.setVisibility(View.GONE);
            layoutResult.setVisibility(View.VISIBLE);
        } else {
            tvResult.setBackgroundResource(R.mipmap.my_jisuan_hui);
            tvYaoqing.setBackgroundResource(R.mipmap.my_jisuan_bai);
            tvResult.setTextColor(getResources().getColor(R.color.text_healthcontents_color));
            tvYaoqing.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
            layoutYaoqing.setVisibility(View.VISIBLE);
            layoutResult.setVisibility(View.GONE);
        }

    }

    private void setQRImage() {
        String invite_user_url = Config.BASE_HEAD_URL + "home/wxuser/index/shareuid/";
        invite_user_url = invite_user_url + UserUtils.getUserId(mContext);
        Log.e("invite_user_url", "invite_user_url=" + invite_user_url);
        CreateQRImage.createQRImage(invite_user_url, ivQr);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void onSuccess(AWordOfGoldResponseBean responseBean) {
        if (responseBean == null || responseBean.getData() == null) {
            return;
        }
        AWordOfGoldBean bean = responseBean.getData();
        tvuser.setText(getString(R.string.text_yzqj_hello, bean.getUserName()));
        StringBuffer sbs = new StringBuffer();
        int size = bean.getInfo().size();
        for (int i = 0; i < size; i++) {
            sbs.append(bean.getInfo().get(i));
            if (i != size - 1) {
                sbs.append("\n");
            }
        }
        tvcontent.setText(sbs.toString());

        ClickableSpan cs = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                goEditBirthDay();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(android.R.color.transparent));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        final SpannableStringBuilder style = new SpannableStringBuilder();
        style.append(getString(R.string.text_yzqj_birthinfo, bean.getBirthday(), bean.getArea()));
        int length = style.length();
        style.setSpan(cs, length - 10, length - 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置部分文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#E95D1B"));
        style.setSpan(foregroundColorSpan, length - 10, length - 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        userinfoTvBirthday.setMovementMethod(LinkMovementMethod.getInstance());
        userinfoTvBirthday.setHighlightColor(getResources().getColor(android.R.color.transparent));
        userinfoTvBirthday.setText(style);
        tvTime.setText(DatesUtils.getInstance().getNowTime("yyyy.MM.dd"));
    }

    @Override
    public void onError(String code) {
    }

    @Override
    protected AWordOfGoldImp<AWordOfGoldContract.View> createPresent() {
        return new AWordOfGoldImp<>(mContext, this);
    }

    private void goEditBirthDay() {
        doFinish();
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
