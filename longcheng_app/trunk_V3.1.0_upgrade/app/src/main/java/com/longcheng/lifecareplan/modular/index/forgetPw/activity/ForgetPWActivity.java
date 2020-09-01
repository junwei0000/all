package com.longcheng.lifecareplan.modular.index.forgetPw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPWActivity extends BaseActivityMVP<ForgetPWContract.View, ForgetPWPresenterImp<ForgetPWContract.View>> implements ForgetPWContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.phonetype_et_phone)
    EditText phonetypeEtPhone;
    @BindView(R.id.phonetype_et_code)
    EditText phonetypeEtCode;
    @BindView(R.id.phonetype_tv_getcode)
    TextView phonetypeTvGetcode;
    @BindView(R.id.btn_next)
    TextView btnNext;
    @BindView(R.id.layout_cont)
    LinearLayout layoutCont;
    private String phoneNum;

    boolean cickStatus = false;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_forget;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {
        btnNext.setOnClickListener(this);
        phonetypeTvGetcode.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPhone, 11);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtCode, 6);
        int wih = DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 20);
        layoutCont.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (wih * 0.90)));
        phonetypeEtPhone.addTextChangedListener(mTextWatcher);
        phonetypeEtCode.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (phonetypeEtPhone != null) {
                String phoneNum = phonetypeEtPhone.getText().toString().trim();
                String pw = phonetypeEtCode.getText().toString().trim();
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
            case R.id.phonetype_tv_getcode:
                phoneNum = phonetypeEtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNum)) {
                    mPresent.ForgetPWcheckphone(phoneNum);
                }
                break;
            case R.id.btn_next:
                phoneNum = phonetypeEtPhone.getText().toString().trim();
                String code = phonetypeEtCode.getText().toString().trim();
                if (cickStatus) {
                    Intent intent = new Intent(mContext, ForgetSetPWActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("phone", phoneNum);
                    intent.putExtra("code", code);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
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
            if (!codeSendingStatus) {
                codeSendingStatus = true;
                mPresent.pUseSendCode(phoneNum, "2");
            }
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {
        codeSendingStatus = false;
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            Message msg = new Message();
            msg.what = Daojishistart;
            mHandler.sendMessage(msg);
            msg = null;
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void loginFail() {

    }

    /**
     * 是否正在请求发送验证码，防止多次重发
     */
    boolean codeSendingStatus = false;
    private MyHandler mHandler = new MyHandler();
    private TimerTask timerTask;
    private final int Daojishistart = 0;
    private final int Daojishiover = 1;
    private int count;

    private void daojishi() {
        final long nowTime = System.currentTimeMillis();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                count--;
                Message msg = new Message();
                msg.what = Daojishiover;
                msg.arg1 = count;
                msg.obj = nowTime;
                mHandler.sendMessage(msg);
                if (count <= 0) {
                    this.cancel();
                }
                msg = null;
            }
        };
        new Timer().schedule(timerTask, 0, 1000);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Daojishistart:
                    phonetypeTvGetcode.setEnabled(false);
                    count = 60;
                    daojishi();
                    break;
                case Daojishiover:
                    if (phonetypeTvGetcode != null) {
                        int arg1 = msg.arg1;
                        if (arg1 < 10) {
                            phonetypeTvGetcode.setText("0" + arg1 + getString(R.string.tv_codeunit));
                        } else {
                            phonetypeTvGetcode.setText(arg1 + getString(R.string.tv_codeunit));
                        }
                        if (arg1 <= 0) {
                            phonetypeTvGetcode.setBackgroundResource(R.drawable.corners_bg_yellowbian2);
                            phonetypeTvGetcode.setTextColor(getResources().getColor(R.color.yellow_E95D1B));
                            phonetypeTvGetcode.setEnabled(true);
                            phonetypeTvGetcode.setText("重新获取");
                        }
                    }
                    break;
                default:
                    break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
            mHandler.removeCallbacks(timerTask);
        }
    }
}
