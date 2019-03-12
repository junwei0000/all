package com.longcheng.lifecareplan.modular.mine.awordofgold.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.activity.EditFamilyActivity;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldBean;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldResponseBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialog;

import butterknife.BindView;

/**
 * 一字千金
 * Created by Burning on 2018/9/3.
 */

public class AWordOfGoldAct extends BaseActivityMVP<AWordOfGoldContract.View, AWordOfGoldImp<AWordOfGoldContract.View>> implements AWordOfGoldContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout llBack;
    @BindView(R.id.pageTop_tv_name)
    TextView tvTitle;
    @BindView(R.id.tvcontent)
    TextView tvContent;
    @BindView(R.id.tvuser)
    TextView tvName;
    @BindView(R.id.userinfo_tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_time)
    TextView tv_time;
    String otherId;

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.layout_notdate)
    LinearLayout llNodata;
    @BindView(R.id.not_date_img)
    ImageView ivNodata;
    @BindView(R.id.not_date_cont)
    TextView tvNoDataContent;
    @BindView(R.id.not_date_cont_title)
    TextView tvNoDataTitle;
    @BindView(R.id.not_date_btn)
    TextView btnNoData;

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void onSuccess(AWordOfGoldResponseBean responseBean) {

        if (responseBean == null || responseBean.getData() == null) {
            showNoDataView(true);
            return;
        }
        showNoDataView(false);
        AWordOfGoldBean bean = responseBean.getData();
        tvName.setText(getString(R.string.text_yzqj_hello, bean.getUserName()));
        StringBuffer sbs = new StringBuffer();
        int size = bean.getInfo().size();
        for (int i = 0; i < size; i++) {
            sbs.append(bean.getInfo().get(i));
            if (i != size - 1) {
                sbs.append("\n");
            }
        }
        tvContent.setText(sbs.toString());

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
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#e60c0c"));
        style.setSpan(foregroundColorSpan, length - 10, length - 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvBirthday.setMovementMethod(LinkMovementMethod.getInstance());
        tvBirthday.setHighlightColor(getResources().getColor(android.R.color.transparent));
        tvBirthday.setText(style);
        tv_time.setText(DatesUtils.getInstance().getNowTime("yyyy.MM.dd"));
    }

    @Override
    public void onError(String code) {
        showNoDataView(true);
    }

    private void showNoDataView(boolean flag) {
        int showNodata = flag ? View.VISIBLE : View.GONE;
        int showData = flag ? View.GONE : View.VISIBLE;
        flContent.setVisibility(showData);
        llNodata.setVisibility(showNodata);
        tvNoDataContent.setVisibility(showNodata);
        tvNoDataTitle.setVisibility(showNodata);
        btnNoData.setVisibility(showNodata);
        ivNodata.setVisibility(showNodata);
        ivNodata.setBackgroundResource(R.mipmap.my_network_icon);
        tvNoDataContent.setText("请刷新或检查网络");
        tvNoDataTitle.setText("网络加载失败");
        btnNoData.setText("刷新");
        btnNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataAfter();
            }
        });
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_awordofgold;
    }

    @Override
    public void initView(View view) {
        otherId = getIntent().getStringExtra("otherId");
        tvTitle.setText(R.string.awordofgold);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        mPresent.doRefresh(UserUtils.getUserId(mContext), UserUtils.getToken(), otherId);
    }

    @Override
    public void setListener() {
        llBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pagetop_layout_left) {
            doFinish();
        }
    }

    @Override
    protected AWordOfGoldImp<AWordOfGoldContract.View> createPresent() {
        return new AWordOfGoldImp<>(mContext, this);
    }

    private void goEditBirthDay() {
        if (otherId.equals(UserUtils.getUserId(mContext))) {
            Intent intent = new Intent(AWordOfGoldAct.this, UserInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, 123);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, (Activity) mContext);
        } else {
            Intent intent = new Intent(mContext, EditFamilyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("family_user_id", "" + otherId);
            startActivityForResult(intent, 123);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 123 && data != null) {
            boolean haschange = data.getBooleanExtra("birthdayflag", false);
            if (haschange) {
                mPresent.doRefresh(UserUtils.getUserId(mContext), UserUtils.getToken(), otherId);
            }
        }
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
