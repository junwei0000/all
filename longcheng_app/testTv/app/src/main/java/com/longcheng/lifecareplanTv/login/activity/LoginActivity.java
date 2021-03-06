package com.longcheng.lifecareplanTv.login.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.longcheng.lifecareplanTv.R;
import com.longcheng.lifecareplanTv.api.BasicResponse;
import com.longcheng.lifecareplanTv.base.BaseActivityMVP;
import com.longcheng.lifecareplanTv.home.menu.activity.MenuActivity;
import com.longcheng.lifecareplanTv.login.bean.LoginAfterBean;
import com.longcheng.lifecareplanTv.utils.ConfigUtils;
import com.longcheng.lifecareplanTv.utils.DatesUtils;
import com.longcheng.lifecareplanTv.utils.DensityUtil;
import com.longcheng.lifecareplanTv.utils.ToastUtilsNew;
import com.longcheng.lifecareplanTv.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 登录
 */
public class LoginActivity extends BaseActivityMVP<LoginContract.View, LoginPresenterImp<LoginContract.View>> implements LoginContract.View {

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
    Button phonetypeTvGetcode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.layout_login)
    LinearLayout layout_login;
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
        initTimer();
    }

    @Override
    public void setListener() {
        phonetypeTvGetcode.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPhone, 11);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtCode, 6);
        ConfigUtils.getINSTANCE().setSelectFouseBtn(phonetypeTvGetcode);
        ConfigUtils.getINSTANCE().setSelectFouseBtn(btnLogin);
        int width = DensityUtil.screenWith(mContext);
        layout_login.setLayoutParams(new LinearLayout.LayoutParams(width * 2 / 5, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void initDataAfter() {
        mUserLoginSkipUtils = new UserLoginSkipUtils(this);
        initSafeKeyboard();
    }

    @Override
    public void setDateInfo() {
        String date = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
        pageTopTvDate.setText(date);
        String week = DatesUtils.getInstance().getNowTime("EE");
        pageTopTvWeek.setText(week);
        String time = DatesUtils.getInstance().getNowTime("HH:mm");
        pageTopTvTime.setText(time);
    }

    private void initSafeKeyboard() {
//        safeKeyboard = new SafeKeyboard(mContext, keyboardViewPlace);
//        safeKeyboard.setmEditText(phonetypeEtPhone);
//        safeKeyboard.setmEditText(phonetypeEtCode);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phonetype_tv_getcode:
                String phoneNum1 = phonetypeEtPhone.getText().toString().trim();
                if (Utils.isCheckPhone(phoneNum1) && !codeSendingStatus) {
                    codeSendingStatus = true;
                    mPresent.pUseSendCode(phoneNum1, "4");
                }
                break;
            case R.id.btn_login:
                String phoneNum = phonetypeEtPhone.getText().toString().trim();
                String code = phonetypeEtCode.getText().toString().trim();
                if (isCheckLogin(phoneNum, code)) {
                    mPresent.pUsePhoneLogin(phoneNum, code);
                }
                Intent intent = new Intent(mContext, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
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
            ToastUtilsNew.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void getCodeSuccess(BasicResponse<LoginAfterBean> responseBean) {
        codeSendingStatus = false;
        int status = responseBean.getStatus();
        if (status == 400) {
            ToastUtilsNew.showToast(responseBean.getMsg());
        } else if (status == 200) {
            Message msg = new Message();
            msg.what = Daojishistart;
            mHandler.sendMessage(msg);
            msg = null;
        }
    }

    @Override
    public void loginFail() {
        ToastUtilsNew.showToast(getString(R.string.net_tishi));
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
            ToastUtilsNew.showToast(getString(R.string.account_hint));
            return false;
        }
        if (password.length() != 6) {
            ToastUtilsNew.showToast(getString(R.string.code_showtishi));
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (timerTask != null) {
            timerTask.cancel();
            mHandler.removeCallbacks(timerTask);
        }
        super.onDestroy();
    }

    // 当点击返回键时, 如果软键盘正在显示, 则隐藏软键盘并是此次返回无效
    public void onBackPressed() {
        exit();
    }

}
