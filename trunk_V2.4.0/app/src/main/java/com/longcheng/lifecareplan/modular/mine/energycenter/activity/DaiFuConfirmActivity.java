package com.longcheng.lifecareplan.modular.mine.energycenter.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.AblumUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 能量中心--已打款上传凭证
 */
public class DaiFuConfirmActivity extends BaseListActivity<EnergyCenterContract.View, EnergyCenterPresenterImp<EnergyCenterContract.View>> implements EnergyCenterContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_wxtitle)
    TextView tvWxtitle;
    @BindView(R.id.iv_wxselect)
    ImageView ivWxselect;
    @BindView(R.id.relat_wx)
    RelativeLayout relatWx;
    @BindView(R.id.tv_zfbtitle)
    TextView tvZfbtitle;
    @BindView(R.id.iv_zfbselect)
    ImageView ivZfbselect;
    @BindView(R.id.relat_zfb)
    RelativeLayout relatZfb;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.iv_cardselect)
    ImageView ivCardselect;
    @BindView(R.id.relat_card)
    RelativeLayout relatCard;
    @BindView(R.id.iv_code)
    ImageView iv_code;
    @BindView(R.id.tv_tishi_title)
    TextView tvTishiTitle;
    @BindView(R.id.tv_tishi)
    TextView tvTishi;
    @BindView(R.id.layout_code)
    LinearLayout layoutCode;
    @BindView(R.id.btn_givemoney)
    TextView btnGivemoney;
    String user_zhufubao_order_id, bless_payment_photo;
    private AblumUtils mAblumUtils;

    int bless_payment_channel = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.relat_wx:
                bless_payment_channel = 1;
                zfbSelectPayType();
                break;
            case R.id.relat_zfb:
                bless_payment_channel = 2;
                zfbSelectPayType();
                break;
            case R.id.relat_card:
                bless_payment_channel = 3;
                zfbSelectPayType();
                break;
            case R.id.layout_code:
                mAblumUtils.onAddAblumClick();
                break;
            case R.id.btn_givemoney:
                if (!TextUtils.isEmpty(bless_payment_photo)) {
                    mPresent.blessPaymentConfirm(user_zhufubao_order_id, bless_payment_channel, bless_payment_photo);
                } else {
                    ToastUtils.showToast("请上传付款凭证");
                }
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.energycenter_daifu_confirm;
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
        pageTopTvName.setText("上传付款凭证");
        pagetopLayoutLeft.setOnClickListener(this);
        layoutCode.setOnClickListener(this);
        btnGivemoney.setOnClickListener(this);
        relatWx.setOnClickListener(this);
        relatZfb.setOnClickListener(this);
        relatCard.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        int hei = DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 40);
        layoutCode.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, hei));
        user_zhufubao_order_id = getIntent().getStringExtra("user_zhufubao_order_id");
        mAblumUtils = new AblumUtils(this, mHandler, UPDATEABLUM);
        mAblumUtils.setCropStaus(false);
        zfbSelectPayType();
    }

    private void zfbSelectPayType() {
        bless_payment_photo = "";
        iv_code.setVisibility(View.GONE);
        tvWxtitle.setTextColor(getResources().getColor(R.color.text_contents_color));
        tvZfbtitle.setTextColor(getResources().getColor(R.color.text_contents_color));
        tvCard.setTextColor(getResources().getColor(R.color.text_contents_color));
        ivWxselect.setVisibility(View.GONE);
        ivZfbselect.setVisibility(View.GONE);
        ivCardselect.setVisibility(View.GONE);
        relatWx.setBackgroundResource(R.drawable.corners_bg_graybian);
        relatZfb.setBackgroundResource(R.drawable.corners_bg_graybian);
        relatCard.setBackgroundResource(R.drawable.corners_bg_graybian);
        if (bless_payment_channel == 1) {
            tvWxtitle.setTextColor(getResources().getColor(R.color.red));
            ivWxselect.setVisibility(View.VISIBLE);
            relatWx.setBackgroundResource(R.drawable.corners_bg_redbian);
            tvTishiTitle.setText("上传微信付款凭证");
            tvTishi.setText("微信支付账单：我~支付~钱包~账单~点击详情~截图上传");
        } else if (bless_payment_channel == 2) {
            tvZfbtitle.setTextColor(getResources().getColor(R.color.red));
            ivZfbselect.setVisibility(View.VISIBLE);
            relatZfb.setBackgroundResource(R.drawable.corners_bg_redbian);
            tvTishiTitle.setText("上传支付宝付款凭证");
            tvTishi.setText("支付宝账单：我的~账单~点击详情~截图上传");
        } else if (bless_payment_channel == 3) {
            tvCard.setTextColor(getResources().getColor(R.color.red));
            ivCardselect.setVisibility(View.VISIBLE);
            relatCard.setBackgroundResource(R.drawable.corners_bg_redbian);
            tvTishiTitle.setText("上传银行卡付款凭证");
            tvTishi.setText("银行卡账单：银行电子回执单");
        }
    }

    /*
     * 调用相册
     */
    protected static final int UPDATEABLUM = 3;
    protected static final int SETRESULT = 4;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATEABLUM:
                    iv_code.setVisibility(View.VISIBLE);
                    Bitmap mBitmap = (Bitmap) msg.obj;
                    iv_code.setImageBitmap(mBitmap);
                    String file = mAblumUtils.Bitmap2StrByBase64(mBitmap);
                    mPresent.uploadImg(file);
                    break;
                case SETRESULT:
                    int requestCode = msg.arg1;
                    int resultCode = msg.arg2;
                    Intent data = (Intent) msg.obj;
                    mAblumUtils.setResult(requestCode, resultCode, data);
                    break;
            }
        }
    };

    @Override
    protected EnergyCenterPresenterImp<EnergyCenterContract.View> createPresent() {
        return new EnergyCenterPresenterImp<>(mActivity, this);
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
    public void ListSuccess(DaiFuDataBean responseBean, int backPage) {
    }

    @Override
    public void RefuseSuccess(EditListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            Intent intent = new Intent();
            setResult(2, intent);
            doFinish();
        }
        ToastUtils.showToast(responseBean.getMsg());
    }

    @Override
    public void editAvatarSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            bless_payment_photo = responseBean.getData();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }


    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == mAblumUtils.RESULTCAMERA || requestCode == mAblumUtils.RESULTGALLERY
                    || requestCode == mAblumUtils.RESULTCROP) {
                Message msgMessage = new Message();
                msgMessage.arg1 = requestCode;
                msgMessage.arg2 = resultCode;
                msgMessage.obj = data;
                msgMessage.what = SETRESULT;
                mHandler.sendMessage(msgMessage);
                msgMessage = null;
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
