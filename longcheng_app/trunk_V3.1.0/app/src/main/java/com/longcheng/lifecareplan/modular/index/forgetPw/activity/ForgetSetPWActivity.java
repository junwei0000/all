package com.longcheng.lifecareplan.modular.index.forgetPw.activity;

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
import com.longcheng.lifecareplan.modular.index.login.activity.LoginActivity;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetSetPWActivity extends BaseActivityMVP<ForgetPWContract.View, ForgetPWPresenterImp<ForgetPWContract.View>> implements ForgetPWContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.phonetype_et_pw)
    EditText phonetypeEtPw;
    @BindView(R.id.phonetype_et_pwtwo)
    EditText phonetypeEtPwtwo;
    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.layout_cont)
    LinearLayout layoutCont;
    private String phone, code;

    boolean cickStatus = false;
    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_forget_setpw;
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
    }

    @Override
    public void setListener() {
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
            case R.id.btn_next:
                String pw = phonetypeEtPw.getText().toString().trim();
                String pw2 = phonetypeEtPwtwo.getText().toString().trim();
                if (cickStatus) {
                    mPresent.findPassword(phone, code, pw, pw2);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected ForgetPWPresenterImp<ForgetPWContract.View> createPresent() {
        return new ForgetPWPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }


    @Override
    public void checkPhoneSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getData());
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            finish();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {

    }

    @Override
    public void loginFail() {

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
