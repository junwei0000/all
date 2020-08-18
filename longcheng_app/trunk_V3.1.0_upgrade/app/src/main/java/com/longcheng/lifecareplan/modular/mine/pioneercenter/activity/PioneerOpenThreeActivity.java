package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.ZYBBDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.AblumUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 24节气创业中心---开通3
 */
public class PioneerOpenThreeActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_bankname)
    TextView tvBankname;
    @BindView(R.id.tv_banknamecopy)
    TextView tvBanknamecopy;
    @BindView(R.id.tv_cardnum)
    TextView tvCardnum;
    @BindView(R.id.tv_cardnumcopy)
    TextView tvCardnumcopy;
    @BindView(R.id.tv_branch)
    TextView tvBranch;
    @BindView(R.id.tv_branchcopy)
    TextView tvBranchcopy;
    @BindView(R.id.iv_jietu)
    ImageView ivJietu;
    @BindView(R.id.btn_buy)
    TextView btnBuy;

    private int payType = 3;
    int is_ing_order;//0 审核中 ；1可以提交
    String payment_img;
    String account_name, entrepreneurs_bank_id;
    String bank_name;
    String branch_name;
    String bank_no;
    String new_bank_no;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_banknamecopy:
                PriceUtils.getInstance().copy(mActivity, account_name);
                break;
            case R.id.tv_cardnumcopy:
                PriceUtils.getInstance().copy(mActivity, bank_no);
                break;
            case R.id.tv_branchcopy:
                PriceUtils.getInstance().copy(mActivity, bank_name + branch_name);
                break;
            case R.id.iv_jietu:
                if (is_ing_order == 1) {
                    mAblumUtils.onAddAblumClick();
                }
                break;
            case R.id.btn_buy:
                if (is_ing_order == 1 && !TextUtils.isEmpty(payment_img))
                    applyPay();
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_open_three;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("开通24节气创业中心");
        pagetopLayoutLeft.setOnClickListener(this);
        tvBanknamecopy.setOnClickListener(this);
        tvCardnumcopy.setOnClickListener(this);
        tvBranchcopy.setOnClickListener(this);
        ivJietu.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
    }

    AblumUtils mAblumUtils;

    @Override
    public void initDataAfter() {
        mAblumUtils = new AblumUtils(this, mHandler, UPDATEABLUM);
        mAblumUtils.setCropStaus(false);
        getZYBBalanceInfo();
    }


    public void showDialog() {
        RequestDataStatus = true;
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        RequestDataStatus = false;
        LoadingDialogAnim.dismiss(mContext);
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
                    Bitmap mBitmap = (Bitmap) msg.obj;
                    ivJietu.setImageBitmap(mBitmap);
                    String file = mAblumUtils.Bitmap2StrByBase64(mBitmap);
                    uploadImg(file);
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

    public void getZYBBalanceInfo() {
        showDialog();
        Observable<ZYBBDataBean> observable = Api.getInstance().service.getopenBankInfo(
                UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ZYBBDataBean>() {
                    @Override
                    public void accept(ZYBBDataBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            ZYBBDataBean.AfterBean data = responseBean.getData();
                            ZYBBDataBean.AfterBean bank = data.getBank();
                            entrepreneurs_bank_id = bank.getEntrepreneurs_bank_id();
                            account_name = bank.getAccount_name();
                            bank_name = bank.getBank_name();
                            branch_name = bank.getBranch_name();
                            bank_no = bank.getBank_no();
                            new_bank_no = bank.getNew_bank_no();
                            tvBankname.setText(account_name);
                            tvCardnum.setText(new_bank_no);
                            tvBranch.setText(bank_name + branch_name);
                            is_ing_order = data.getIs_ing_order();
                            if (is_ing_order == 1) {
                                btnBuy.setText("提交");
                            } else {
                                btnBuy.setText("审核中");
                                payment_img = data.getPayment_img();
                                GlideDownLoadImage.getInstance().loadCircleImageCommune(mContext, payment_img, ivJietu, 0);
                            }
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        Log.e("Observable", throwable.toString());
                    }
                });
    }

    public void uploadImg(String file) {
        showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.uploadCertificateImg(
                UserUtils.getUserId(mContext), file, 3, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        dismissDialog();
                        String status = responseBean.getStatus();
                        if (status.equals("200")) {
                            payment_img = responseBean.getData();
                            btnBuy.setBackgroundColor(getResources().getColor(R.color.red));
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        Log.e("Observable", throwable.toString());
                    }
                });
    }

    private void applyPay() {
        if (RequestDataStatus) {
            return;
        }
        showDialog();
        Observable<PayWXDataBean> observable = Api.getInstance().service.PionopenPay(UserUtils.getUserId(mContext),
                "1", payType, "2", payment_img, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PayWXDataBean>() {
                    @Override
                    public void accept(PayWXDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {

                            String status = responseBean.getStatus();
                            if (status.equals("200")) {
                                PayWXAfterBean payWeChatBean = responseBean.getData();
                                if (payType == 1) {
                                    Log.e(TAG, payWeChatBean.toString());
                                    PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
                                } else if (payType == 2) {
                                    String payInfo = payWeChatBean.getPayInfo();
                                    PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                                        @Override
                                        public void onSuccess() {
                                            applyPaySuccuess();
                                        }

                                        @Override
                                        public void onFailure(String error) {

                                        }
                                    });
                                } else {
                                    applyPaySuccuess();
                                    ToastUtils.showToast(responseBean.getMsg());
                                }
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(R.string.net_tishi);
                        Log.e("Observable", "" + throwable.toString());
                        dismissDialog();
                    }
                });
    }

    private void applyPaySuccuess() {
        ToastUtils.showToast("提交成功");
        PioneerOpenTwoActivity.mPioneerOpenTwoActivity.doFinish();
        PioneerOpenActivity.mPioneerOpenActivity.doFinish();
        doFinish();
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

