package com.longcheng.volunteer.modular.index.login.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.modular.index.login.bean.LoginAfterBean;
import com.longcheng.volunteer.modular.index.login.bean.LoginDataBean;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.Utils;
import com.longcheng.volunteer.utils.myview.SupplierEditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class LoginThirdSetPwActivity extends BaseActivityMVP<LoginContract.View, LoginPresenterImp<LoginContract.View>> implements LoginContract.View {


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
    @BindView(R.id.phonetype_et_code)
    SupplierEditText phonetypeEtCode;
    @BindView(R.id.phonetype_tv_getcode)
    TextView phonetypeTvGetcode;
    @BindView(R.id.phonetype_et_pw)
    SupplierEditText phonetypeEtPw;
    @BindView(R.id.phonetype_et_name)
    SupplierEditText phonetypeEtName;
    @BindView(R.id.btn_commit)
    TextView btnCommit;
    private String phone;

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
        return R.layout.user_loginthird_setpw;
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
        phonetypeTvGetcode.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPhone, 11);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPw, 20);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtCode, 6);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtName, 20);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.phonetype_tv_getcode:
                String phoneNum1 = phonetypeEtPhone.getText().toString().trim();
                if (isCheckPhone(phoneNum1) && !codeSendingStatus) {
                    codeSendingStatus = true;
                    mPresent.pUseSendCode(phoneNum1, "5");
                }
                break;
            case R.id.btn_commit:
                String user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
                phone = phonetypeEtPhone.getText().toString().trim();
                String code = phonetypeEtCode.getText().toString().trim();
                String pw = phonetypeEtPw.getText().toString().trim();
                String pwtwo = phonetypeEtName.getText().toString().trim();
                if (isCheck(phone, code, pw, pwtwo)) {
                    mPresent.bindPhone(user_id, code, phone, pw);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected LoginPresenterImp<LoginContract.View> createPresent() {
        return new LoginPresenterImp<>(mContext, this);
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

    @Override
    public void loginSuccess(LoginDataBean responseBean) {
    }

    @Override
    public void bindPhoneSuccess(LoginDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            LoginAfterBean mLoginInfo = (LoginAfterBean) responseBean.getData();
            if (mLoginInfo != null && !TextUtils.isEmpty(mLoginInfo.getUser_id())) {
                SharedPreferencesHelper.put(mActivity, "loginSkipToStatus", "");
                UserLoginSkipUtils mUserLoginSkipUtils = new UserLoginSkipUtils(this);
                mUserLoginSkipUtils.getLoginInfo(mLoginInfo);
            }
            Intent intent = new Intent();
            intent.putExtra("phone", phone);
            setResult(ConstantManager.USERINFO_FORRESULT_PHONE, intent);
            doFinish();
        }
    }

    @Override
    public void updatepwSuccess(EditDataBean responseBean) {

    }

    @Override
    public void loginFail() {
        ToastUtils.showToast(getString(R.string.net_tishi));
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
        }
    }

    /**
     * 获取验证码前判断手机号
     *
     * @param phone
     * @return
     */
    private boolean isCheckPhone(String phone) {
        if (TextUtils.isEmpty(phone) || !Utils.isPhoneNum(phone)) {
            ToastUtils.showToast(getString(R.string.account_showtishi));
            return false;
        }
        return true;
    }

    /**
     * 前验证
     *
     * @param phone
     * @param password
     * @return
     */
    private boolean isCheck(String phone, String code, String password, String newpassword) {
        if (TextUtils.isEmpty(phone) || !Utils.isPhoneNum(phone)) {
            ToastUtils.showToast(getString(R.string.account_showtishi));
            return false;
        }
        if (TextUtils.isEmpty(phone) || (!TextUtils.isEmpty(phone) && code.length() != 6)) {
            ToastUtils.showToast(getString(R.string.code_showtishi));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(getString(R.string.forget_repeatpw_hint));
            return false;
        }
        if (TextUtils.isEmpty(newpassword)) {
            ToastUtils.showToast(getString(R.string.forget_repeatpwtwo_hint));
            return false;
        }
        if (password.length() < 6 || password.length() > 20) {
            ToastUtils.showToast(getString(R.string.forget_repeatpw_hint));
            return false;
        }
        if (!password.equals(newpassword)) {
            ToastUtils.showToast(getString(R.string.forget_repeatpwtwosame_hint));
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
