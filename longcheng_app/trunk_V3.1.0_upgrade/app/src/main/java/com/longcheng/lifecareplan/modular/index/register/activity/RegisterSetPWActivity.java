package com.longcheng.lifecareplan.modular.index.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.MessageEvent;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.AgreementAct;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterSetPWActivity extends BaseActivityMVP<RegisterContract.View, RegisterPresenterImp<RegisterContract.View>> implements RegisterContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.phonetype_et_pw)
    EditText phonetypeEtPw;
    @BindView(R.id.phonetype_et_pwtwo)
    EditText phonetypeEtPwtwo;
    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.layout_cont)
    LinearLayout layoutCont;
    @BindView(R.id.login_lt_yuedu)
    LinearLayout loginLtYuedu;
    private String phone, code;
    UserLoginSkipUtils mUserLoginSkipUtils;

    boolean cickStatus = false;
    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_register_setpw;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        code = intent.getStringExtra("code");
        mUserLoginSkipUtils = new UserLoginSkipUtils(this);
    }

    @Override
    public void setListener() {
        loginLtYuedu.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPw, 20);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPwtwo, 20);
        int wih = DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 20);
        layoutCont.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (wih * 0.90)));
        phonetypeEtPw.addTextChangedListener(mTextWatcher);
        phonetypeEtPwtwo.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (phonetypeEtPw != null) {
                String phoneNum = phonetypeEtPw.getText().toString().trim();
                String pw = phonetypeEtPwtwo.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(pw)) {
                    btnNext.setBackgroundResource(R.drawable.corners_bg_login_new);
                    cickStatus = true;
                } else {
                    btnNext.setBackgroundResource(R.drawable.corners_bg_logingray_new);
                    cickStatus = false;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.login_lt_yuedu:
                Intent intent = new Intent(mContext, AgreementAct.class);
                intent.putExtra("starturl", "http://t.asdyf.com/home/login/protocol");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.btn_next:
                String pw = phonetypeEtPw.getText().toString().trim();
                String pw2 = phonetypeEtPwtwo.getText().toString().trim();
                if (cickStatus) {
                    mPresent.register(phone, code, pw, pw2);
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

    @Override
    public void CheckPhoneSuccess(ResponseBean responseBean) {

    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {

    }

    @Override
    public void registerSuccess(LoginDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            // 发布事件
            new Thread("posting") {
                @Override
                public void run() {
                    EventBus.getDefault().post(new MessageEvent("register"));
                }
            }.start();
            LoginAfterBean mLoginInfo = responseBean.getData();
            mUserLoginSkipUtils.getLoginInfo(mLoginInfo);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void loginFail() {
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
}
