package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.PushQueueAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBHistoryDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 激活
 */
public class PionZFBJiHuoActivity extends BaseActivityMVP<PionZFBContract.View, PionZFBPresenterImp<PionZFBContract.View>> implements PionZFBContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_superablity)
    TextView tvSuperablity;
    @BindView(R.id.et_sell)
    EditText etSell;
    @BindView(R.id.tv_jihuo)
    TextView tvJihuo;

    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_jihuo:
                String uzhufubao_number = etSell.getText().toString();
                if (!TextUtils.isEmpty(uzhufubao_number)) {
                    mPresent.activateSuperAbility(uzhufubao_number);
                } else {
                    ToastUtils.showToast("请输入祝佑宝数量");
                }
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
        return R.layout.pioneer_userzyb_jihuo;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("激活超级生命能量");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvJihuo.setOnClickListener(this);
        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvJihuo, R.color.red);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etSell, 9);
        etSell.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etSell.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etSell != null) {
                    String inputmoney = etSell.getText().toString();
                    if (!TextUtils.isEmpty(inputmoney)) {
                        String showEngry = PriceUtils.getInstance().gteMultiplySumPrice(inputmoney, "9");
                        tvSuperablity.setText("获得" + showEngry + "超级生命能量");
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void initDataAfter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
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

    }

    @Override
    public void historySuccess(PionZFBHistoryDataBean responseBean) {

    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            doFinish();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void PaySuccess(PayWXDataBean responseBean) {

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
