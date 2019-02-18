package com.longcheng.lifecareplan.modular.index.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.MessageEvent;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.index.forgetPw.activity.GetFPWCodeActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.AgreementAct;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivityMVP<RegisterContract.View, RegisterPresenterImp<RegisterContract.View>> implements RegisterContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.phonetype_tv_tilte)
    TextView phonetypeTvTilte;
    @BindView(R.id.phonetype_tv_num)
    TextView phonetypeTvNum;
    @BindView(R.id.login_rl_phonetype)
    RelativeLayout loginRlPhonetype;
    @BindView(R.id.phonetype_et_phone)
    SupplierEditText phonetypeEtPhone;
    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.yuedu_cb_check)
    CheckBox yueduCbCheck;
    @BindView(R.id.yuedu_tv_tiaojian)
    TextView yueduTvTiaojian;
    @BindView(R.id.login_lt_yuedu)
    RelativeLayout loginLtYuedu;
    private String phoneNum;

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }


    @Override
    public void CheckPhoneSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            Intent intent = new Intent(mContext, GetRCodeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("phone", phoneNum);
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        }
    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {

    }

    @Override
    public void registerSuccess(LoginDataBean responseBean) {

    }

    @Override
    public void loginFail() {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_register;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        yueduTvTiaojian.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPhone, 11);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.yuedu_tv_tiaojian:
                intent = new Intent(mContext, AgreementAct.class);
                intent.putExtra("starturl", "http://t.asdyf.com/home/login/protocol");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.btn_next:
                phoneNum = phonetypeEtPhone.getText().toString().trim();
                if (Utils.isCheckPhone(phoneNum)) {
                    if (yueduCbCheck.isChecked()) {
                        mPresent.checkphone(phoneNum);
                    } else {
                        ToastUtils.showToast("请阅读并同意《平台使用条件》");
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected RegisterPresenterImp<RegisterContract.View> createPresent() {
        return new RegisterPresenterImp<>(mContext, this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackground(MessageEvent event) {
        if (event != null) {
            String mesg = event.getMessage();
            if (!TextUtils.isEmpty(mesg) && mesg.equals("register")) {
                doFinish();
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
