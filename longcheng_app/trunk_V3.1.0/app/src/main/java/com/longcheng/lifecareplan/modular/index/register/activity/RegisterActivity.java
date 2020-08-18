package com.longcheng.lifecareplan.modular.index.register.activity;

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
import com.longcheng.lifecareplan.bean.MessageEvent;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.AgreementAct;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivityMVP<RegisterContract.View, RegisterPresenterImp<RegisterContract.View>> implements RegisterContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
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
    @BindView(R.id.login_lt_yuedu)
    LinearLayout loginLtYuedu;

    private String phoneNum;

    boolean cickStatus = false;

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
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {
        loginLtYuedu.setOnClickListener(this);
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
        Intent intent;
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.phonetype_tv_getcode:
                phoneNum = phonetypeEtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNum)) {
                    mPresent.checkphone(phoneNum);
                }
                break;
            case R.id.login_lt_yuedu:
                intent = new Intent(mContext, AgreementAct.class);
                intent.putExtra("starturl", "http://t.asdyf.com/home/login/protocol");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.btn_next:
                phoneNum = phonetypeEtPhone.getText().toString().trim();
                String code = phonetypeEtCode.getText().toString().trim();
                if (cickStatus) {
                    intent = new Intent(mContext, RegisterSetPWActivity.class);
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
                msg = null;
                if (count <= 0) {
                    this.cancel();
                }
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
                    if (phonetypeTvGetcode != null) {
                        phonetypeTvGetcode.setEnabled(false);
                    }
                    count = 60;
                    daojishi();
                    break;
                case Daojishiover:
                    int count_ = msg.arg1;
                    if (phonetypeTvGetcode != null) {
                        if (count_ < 10) {
                            phonetypeTvGetcode.setText("0" + count_ + getString(R.string.tv_codeunit));
                        } else {
                            phonetypeTvGetcode.setText(count_ + getString(R.string.tv_codeunit));
                        }
                        if (count_ <= 0) {
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

    @Override
    protected RegisterPresenterImp<RegisterContract.View> createPresent() {
        return new RegisterPresenterImp<>(mContext, this);
    }

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
            if (!codeSendingStatus) {
                codeSendingStatus = true;
                mPresent.pUseSendCode(phoneNum, "1");
            }
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {
        codeSendingStatus = false;
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            Message msg = new Message();
            msg.what = Daojishistart;
            mHandler.sendMessage(msg);
            msg = null;
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void registerSuccess(LoginDataBean responseBean) {

    }

    @Override
    public void loginFail() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
            mHandler.removeCallbacks(timerTask);
        }
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
