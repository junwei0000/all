package com.longcheng.lifecareplan.modular.index.login.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.MessageEvent;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.index.register.activity.RegisterContract;
import com.longcheng.lifecareplan.modular.index.register.activity.RegisterPresenterImp;
import com.longcheng.lifecareplan.modular.index.register.activity.RegisterSetPWActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class GetLogCodeActivity extends BaseActivityMVP<LoginContract.View, LoginPresenterImp<LoginContract.View>> implements LoginContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.phonetype_et_code)
    SupplierEditText phonetypeEtCode;
    @BindView(R.id.phonetype_tv_getcode)
    TextView phonetypeTvGetcode;
    @BindView(R.id.btn_next)
    TextView btnNext;
    private String phone;

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_logincode;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        mUserLoginSkipUtils = new UserLoginSkipUtils(this);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        phonetypeTvGetcode.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtCode, 6);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.phonetype_tv_getcode:
                if (!codeSendingStatus) {
                    codeSendingStatus = true;
                    mPresent.pUseSendCode(phone, "4");
                }
                break;
            case R.id.btn_next:
                String code = phonetypeEtCode.getText().toString().trim();
                //手机快捷登录
                if (isCheckLogin(code)) {
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    mPresent.pUsePhoneLogin(phone, code);
                }
                break;
            default:
                break;
        }
    }

    private boolean isCheckLogin(String password) {
        if (password.length() != 6) {
            ToastUtils.showToast(getString(R.string.code_showtishi));
            return false;
        }
        return true;
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
    public void bindPhoneSuccess(LoginDataBean responseBean) {

    }

    @Override
    public void updatepwSuccess(EditDataBean responseBean) {

    }

    @Override
    public void loginSuccess(LoginDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            new Thread("posting") {
                @Override
                public void run() {
                    EventBus.getDefault().post(new MessageEvent("register"));
                }
            }.start();
            LoginAfterBean mLoginInfo = (LoginAfterBean) responseBean.getData();
            mUserLoginSkipUtils.getLoginInfo(mLoginInfo);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
        Log.e(TAG, "*******************************" + responseBean.toString());
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
    UserLoginSkipUtils mUserLoginSkipUtils;

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
                    phonetypeTvGetcode.setEnabled(false);
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
                            phonetypeTvGetcode.setTextColor(getResources().getColor(R.color.blue));
                            phonetypeTvGetcode.setEnabled(true);
                            phonetypeTvGetcode.setText(getString(R.string.code_get));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected LoginPresenterImp<LoginContract.View> createPresent() {
        return new LoginPresenterImp<>(mContext, this);
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
