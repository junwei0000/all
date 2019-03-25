package com.longcheng.lifecareplantv.modular.login.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import cn.jpush.android.api.TagAliasCallback;

/**
 * 登录
 */
public class LoginActivity extends BaseActivityMVP<LoginContract.View, LoginPresenterImp<LoginContract.View>> implements LoginContract.View {


    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.login_tv_tilte)
    TextView loginTvTilte;
    @BindView(R.id.phonetype_tv_tilte)
    TextView phonetypeTvTilte;
    @BindView(R.id.phonetype_tv_num)
    TextView phonetypeTvNum;
    @BindView(R.id.login_rl_phonetype)
    RelativeLayout loginRlPhonetype;
    @BindView(R.id.phonetype_et_phone)
    EditText phonetypeEtPhone;
    @BindView(R.id.phonetype_et_pw)
    EditText phonetypeEtPw;

    @BindView(R.id.login_rl_pw)
    RelativeLayout loginRlPw;
    @BindView(R.id.phonetype_et_code)
    EditText phonetypeEtCode;
    @BindView(R.id.phonetype_tv_getcode)
    TextView phonetypeTvGetcode;
    @BindView(R.id.login_rl_code)
    RelativeLayout loginRlCode;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.login_lt_forgetpw)
    RelativeLayout loginLtForgetpw;
    @BindView(R.id.yuedu_tv_ti)
    TextView yueduTvTi;
    @BindView(R.id.yuedu_tv_tiaojian)
    TextView yueduTvTiaojian;
    @BindView(R.id.login_lt_yuedu)
    RelativeLayout loginLtYuedu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login_btn_forgetpw)
    TextView loginBtnForgetpw;
    @BindView(R.id.login_btn_phone)
    TextView loginBtnPhone;
    @BindView(R.id.login_btn_account)
    TextView loginBtnAccount;


    /**
     * 当前登录类型, 是否快捷登录
     */
    boolean phoneLoginStus = false;



    UserLoginSkipUtils mUserLoginSkipUtils;

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
        setOrChangeTranslucentColor(toolbar, null);
        pageTopTvRigth.setVisibility(View.VISIBLE);
        pageTopTvRigth.setText(getString(R.string.register));
    }

    @Override
    public void initDataAfter() {
        mUserLoginSkipUtils = new UserLoginSkipUtils(this);
        String Alias = ConfigUtils.getINSTANCE().getDeviceId(mActivity);
        PushClient.getINSTANCE(mActivity).setAlias(Alias, mAliasCallback);
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    initDataAfter();
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };

    @Override
    public void setListener() {
        btnLogin.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        loginBtnPhone.setOnClickListener(this);
        loginBtnAccount.setOnClickListener(this);
        loginBtnForgetpw.setOnClickListener(this);
        phonetypeTvGetcode.setOnClickListener(this);
        loginLtYuedu.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPhone, 11);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPw, 20);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtCode, 6);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.login_btn_phone:
                phoneLoginStus = true;
                setLoginStusView();
                break;
            case R.id.login_btn_account:
                phoneLoginStus = false;
                setLoginStusView();
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
                String pw = phonetypeEtPw.getText().toString().trim();
                String code = phonetypeEtCode.getText().toString().trim();


                /**
                 * 点击事件时调用
                 */
                if (phoneLoginStus) {
                    //手机快捷登录
                    if (isCheckLogin(phoneNum, code)) {
                        mPresent.pUsePhoneLogin(phoneNum, code);
                    }

                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置当前登录类型的布局
     */
    private void setLoginStusView() {

        if (phoneLoginStus) {
            //手机号登录
            loginTvTilte.setText(getString(R.string.login_phone));
            loginRlPw.setVisibility(View.GONE);
            loginRlCode.setVisibility(View.VISIBLE);
            loginLtForgetpw.setVisibility(View.GONE);
            loginLtYuedu.setVisibility(View.VISIBLE);
        } else {
            //账号登录
            loginTvTilte.setText(getString(R.string.login_account));
            loginRlPw.setVisibility(View.VISIBLE);
            loginRlCode.setVisibility(View.GONE);
            loginLtForgetpw.setVisibility(View.VISIBLE);
            loginLtYuedu.setVisibility(View.GONE);
        }
    }


    @Override
    protected LoginPresenterImp<LoginContract.View> createPresent() {
        return new LoginPresenterImp<>(mActivity, this);
    }


    @Override
    public void loginSuccess(BasicResponse<LoginAfterBean> responseBean) {
        int status = responseBean.getStatus();
        if (status==200) {
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
        if (status==400) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status==200) {
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
        if (phoneLoginStus) {
            if (password.length() != 6) {
                ToastUtils.showToast(getString(R.string.code_showtishi));
                return false;
            }
        } else {
            if (TextUtils.isEmpty(password)) {
                ToastUtils.showToast(getString(R.string.forget_repeatpw_hint));
                return false;
            }
            if (password.length() < 6 || password.length() > 20) {
                ToastUtils.showToast(getString(R.string.forget_repeatpw_hint));
                return false;
            }
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

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackground(MessageEvent event) {
        if (event != null) {
            String mesg = event.getMessage();
            if (!TextUtils.isEmpty(mesg) && mesg.equals("register")) {
                doFinish();
            }
        }
    }

}
