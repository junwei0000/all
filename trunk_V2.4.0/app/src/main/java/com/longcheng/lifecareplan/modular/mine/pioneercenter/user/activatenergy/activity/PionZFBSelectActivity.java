package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.PushQueueAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class PionZFBSelectActivity extends BaseActivityMVP<PionZFBContract.View, PionZFBPresenterImp<PionZFBContract.View>> implements PionZFBContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_phone)
    SupplierEditText etPhone;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.item_iv_img)
    ImageView itemIvImg;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieqi)
    TextView itemTvJieqi;
    @BindView(R.id.item_tv_phone)
    TextView itemTvPhone;
    @BindView(R.id.item_tv_add)
    TextView itemTvAdd;
    @BindView(R.id.layout_user)
    RelativeLayout layoutUser;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;

    String inputmoney;
    int pay_method;
    String phone;
    String entrepreneurs_id;

    @Override
    public void onClick(View v) {
        Intent intent;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.item_tv_phone:
                DensityUtil.getPhoneToKey(mContext, phone);
                break;
            case R.id.tv_search:
                String phone = etPhone.getText().toString();
                if (!TextUtils.isEmpty(phone)) {
                    mPresent.getRechargeEntrepreneurs(phone);
                } else {
                    ToastUtils.showToast("请输入手机号选择创业师");
                }
                break;
            case R.id.tv_bottom:
                if (click)
                    mPresent.entrepreneursuserRecharge(inputmoney, pay_method, entrepreneurs_id);
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_userzyb_select;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("查找创业者");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvBottom.setOnClickListener(this);
        itemTvPhone.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etPhone, 11);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPhone != null) {
                    String phone = etPhone.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        click = false;
                        layoutNotdate.setVisibility(View.GONE);
                        layoutUser.setVisibility(View.GONE);
                        tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void initDataAfter() {
        inputmoney = getIntent().getStringExtra("inputmoney");
        pay_method = getIntent().getIntExtra("pay_method", 4);
    }


    @Override
    protected PionZFBPresenterImp<PionZFBContract.View> createPresent() {
        return new PionZFBPresenterImp<>(mContext, this);
    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void getwalletSuccess(PionZFBDataBean responseBean) {

    }

    @Override
    public void SelectSuccess(PionZFBSelectDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            setBtn(responseBean);
        } else if (status.equals("200")) {
            setBtn(responseBean);
            PionZFBSelectDataBean.PionZFBSBean mEnergyAfterBean = responseBean.getData();
            GlideDownLoadImage.getInstance().loadCircleImage(mEnergyAfterBean.getAvatar(), itemIvImg);
            itemTvJieqi.setText(mEnergyAfterBean.getJieqi_name());
            itemTvName.setText(CommonUtil.setName(mEnergyAfterBean.getUser_name()));
            phone = mEnergyAfterBean.getPhone();
            itemTvPhone.setText(phone);
            entrepreneurs_id = mEnergyAfterBean.getEntrepreneurs_id();
        }
    }

    boolean click = false;

    private void setBtn(PionZFBSelectDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            click = true;
            layoutNotdate.setVisibility(View.GONE);
            layoutUser.setVisibility(View.VISIBLE);
            tvBottom.setBackgroundColor(getResources().getColor(R.color.red));
        } else {
            click = false;
            notDateCont.setText(responseBean.getMsg());
            layoutNotdate.setVisibility(View.VISIBLE);
            layoutUser.setVisibility(View.GONE);
            tvBottom.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {

    }

    @Override
    public void PaySuccess(PayWXDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            showPayTypeDialog();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    MyDialog mPayTypeDialog;

    /**
     * 显示填写支付方式提示
     */
    public void showPayTypeDialog() {
        if (mPayTypeDialog == null) {
            mPayTypeDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_ordertishi);// 创建Dialog并设置样式主题
            mPayTypeDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mPayTypeDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mPayTypeDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mPayTypeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            mPayTypeDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_ok = mPayTypeDialog.findViewById(R.id.tv_ok);
            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayTypeDialog.dismiss();
                }
            });
            mPayTypeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    doFinish();
                }
            });
        } else {
            mPayTypeDialog.show();
        }
    }


    @Override
    public void ListError() {
    }


    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
            doFinish();
        }
        return false;
    }
}
