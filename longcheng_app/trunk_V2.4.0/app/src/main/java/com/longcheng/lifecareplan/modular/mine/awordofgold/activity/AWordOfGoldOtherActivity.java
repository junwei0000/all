package com.longcheng.lifecareplan.modular.mine.awordofgold.activity;

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
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldBean;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldResponseBean;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import butterknife.BindView;

/**
 * 一字千金
 * Created by Burning on 2018/9/3.
 */

public class AWordOfGoldOtherActivity extends BaseActivityMVP<AWordOfGoldContract.View, AWordOfGoldImp<AWordOfGoldContract.View>> implements AWordOfGoldContract.View {
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

    String user_name, phone, area_simple, birthday;
    private String pid, cid, aid;
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
        if (flContent == null) {
            return;
        }
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
        user_name = getIntent().getStringExtra("user_name");
        phone = getIntent().getStringExtra("phone");
        area_simple = getIntent().getStringExtra("area_simple");
        birthday = getIntent().getStringExtra("birthday");
        pid = getIntent().getStringExtra("pid");
        cid = getIntent().getStringExtra("cid");
        aid = getIntent().getStringExtra("aid");
        tvTitle.setText(R.string.awordofgold);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        mPresent.newGoldWord(UserUtils.getUserId(mContext), UserUtils.getToken(),
                user_name, phone, area_simple, birthday,pid,cid,aid);
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
