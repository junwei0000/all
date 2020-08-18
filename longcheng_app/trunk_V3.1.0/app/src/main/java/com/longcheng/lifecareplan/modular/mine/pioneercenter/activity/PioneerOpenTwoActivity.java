package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 24节气创业中心---开通
 */
public class PioneerOpenTwoActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.btn_open)
    TextView btn_open;
    @BindView(R.id.iv_price1)
    LinearLayout ivPrice1;
    @BindView(R.id.iv_price2)
    LinearLayout ivPrice2;
    @BindView(R.id.iv_price3)
    LinearLayout ivPrice3;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.activat_iv_wxselect)
    ImageView activatIvWxselect;
    @BindView(R.id.activat_relat_wx)
    RelativeLayout activatRelatWx;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.activat_iv_cardselect)
    ImageView activatIvCardselect;
    @BindView(R.id.activat_relat_card)
    RelativeLayout activatRelatCard;
    private int payType = 3;
    public static PioneerOpenTwoActivity mPioneerOpenTwoActivity;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.activat_relat_card:
                payType = 3;
                selectPayTypeView();
                break;
            case R.id.btn_open:
                Intent intent = new Intent(mContext, PioneerOpenThreeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_open_two;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        mPioneerOpenTwoActivity=this;
        pageTopTvName.setText("开通24节气创业中心");
        pagetopLayoutLeft.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        activatRelatCard.setOnClickListener(this);
        int width = (DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 55)) / 3;
        ivPrice1.setLayoutParams(new FrameLayout.LayoutParams(width, (int) (width * 1.273)));

        ivPrice2.setLayoutParams(new FrameLayout.LayoutParams(width, (int) (width * 1.273)));
        ivPrice3.setLayoutParams(new FrameLayout.LayoutParams(width, (int) (width * 1.273)));
    }


    @Override
    public void initDataAfter() {
        selectPayTypeView();
    }

    /**
     * 切换充值渠道
     */
    private void selectPayTypeView() {
        tvWx.setTextColor(getResources().getColor(R.color.text_contents_color));
        tvCard.setTextColor(getResources().getColor(R.color.text_contents_color));
        activatRelatWx.setBackgroundResource(R.drawable.corners_bg_graybian);
        activatIvWxselect.setVisibility(View.GONE);
        activatRelatCard.setBackgroundResource(R.drawable.corners_bg_graybian);
        activatIvCardselect.setVisibility(View.GONE);
        if (payType == 1) {
            tvWx.setTextColor(getResources().getColor(R.color.red));
            activatIvWxselect.setVisibility(View.VISIBLE);
            activatRelatWx.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatWx.setPadding(0, 0, 0, 0);
        } else if (payType == 3) {
            tvCard.setTextColor(getResources().getColor(R.color.red));
            activatIvCardselect.setVisibility(View.VISIBLE);
            activatRelatCard.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatCard.setPadding(0, 0, 0, 0);
        }
    }

    public void showDialog() {
        RequestDataStatus = true;
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        RequestDataStatus = false;
        LoadingDialogAnim.dismiss(mContext);
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

