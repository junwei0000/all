package com.longcheng.lifecareplantv.modular.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplantv.R;
import com.longcheng.lifecareplantv.base.BaseActivityMVP;
import com.longcheng.lifecareplantv.bean.Bean;
import com.longcheng.lifecareplantv.bean.MessageEvent;
import com.longcheng.lifecareplantv.http.basebean.BasicResponse;
import com.longcheng.lifecareplantv.modular.login.bean.LoginAfterBean;
import com.longcheng.lifecareplantv.push.PushClient;
import com.longcheng.lifecareplantv.utils.ConfigUtils;
import com.longcheng.lifecareplantv.utils.ToastUtils;
import com.longcheng.lifecareplantv.utils.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登录
 */
public class LoginActivity extends BaseActivityMVP<LoginContract.View, LoginPresenterImp<LoginContract.View>> implements LoginContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_time)
    TextView pageTopTvTime;
    @BindView(R.id.pageTop_tv_date)
    TextView pageTopTvDate;
    @BindView(R.id.pageTop_tv_week)
    TextView pageTopTvWeek;
    @BindView(R.id.pagetop_layout_set)
    LinearLayout pagetopLayoutSet;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pageTop_tv_phone)
    TextView pageTopTvPhone;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.phonetype_et_phone)
    EditText phonetypeEtPhone;
    @BindView(R.id.phonetype_et_code)
    EditText phonetypeEtCode;
    @BindView(R.id.phonetype_tv_getcode)
    TextView phonetypeTvGetcode;
    @BindView(R.id.btn_login)
    TextView btnLogin;


    public UserLoginSkipUtils mUserLoginSkipUtils;

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
        return R.layout.user_login;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void initDataAfter() {
        mUserLoginSkipUtils = new UserLoginSkipUtils(this);
    }


    @Override
    public void setListener() {
        pagetopLayoutSet.setOnClickListener(this);
        phonetypeTvGetcode.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPhone, 11);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtCode, 6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_set:

                break;
            case R.id.phonetype_tv_getcode:
                String phoneNum1 = phonetypeEtPhone.getText().toString().trim();
                if (Utils.isCheckPhone(phoneNum1) && !codeSendingStatus) {
                    codeSendingStatus = true;
                    mPresent.pUseSendCode(phoneNum1, "4");
                }
                break;
            case R.id.btn_login:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                String phoneNum = phonetypeEtPhone.getText().toString().trim();
                String code = phonetypeEtCode.getText().toString().trim();
                if (isCheckLogin(phoneNum, code)) {
                    mPresent.pUsePhoneLogin(phoneNum, code);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected LoginPresenterImp<LoginContract.View> createPresent() {
        return new LoginPresenterImp<>(mActivity, this);
    }


    @Override
    public void loginSuccess(BasicResponse<LoginAfterBean> responseBean) {
        int status = responseBean.getStatus();
        if (status == 200) {
            LoginAfterBean mLoginInfo = (LoginAfterBean) responseBean.getData();
            mUserLoginSkipUtils.getLoginInfo(mLoginInfo);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void getCodeSuccess(BasicResponse<Bean> responseBean) {
        codeSendingStatus = false;
        int status = responseBean.getStatus();
        if (status == 400) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status == 200) {
            Message msg = new Message();
            msg.what = Daojishistart;
            mHandler.sendMessage(msg);
            msg = null;
        }
    }

    @Override
    public void loginFail() {
        ToastUtils.showToast(getString(R.string.net_tishi));
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
                    if (msg.arg1 < 10) {
                        phonetypeTvGetcode.setText("0" + msg.arg1 + getString(R.string.tv_codeunit));
                    } else {
                        phonetypeTvGetcode.setText(msg.arg1 + getString(R.string.tv_codeunit));
                    }
                    if (msg.arg1 <= 0) {
                        phonetypeTvGetcode.setTextColor(getResources().getColor(R.color.blue));
                        phonetypeTvGetcode.setEnabled(true);
                        phonetypeTvGetcode.setText(getString(R.string.code_get));
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 登录前验证
     *
     * @param phone
     * @param password
     * @return
     */
    private boolean isCheckLogin(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(getString(R.string.account_hint));
            return false;
        }
        if (password.length() != 6) {
            ToastUtils.showToast(getString(R.string.code_showtishi));
            return false;
        }
        return true;
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
