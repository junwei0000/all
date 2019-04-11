package com.longcheng.lifecareplan.modular.index.login.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 修改密码
 */
public class UpdatePwActivity extends BaseActivityMVP<LoginContract.View, LoginPresenterImp<LoginContract.View>> implements LoginContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.tv_tishi)
    TextView tv_tishi;
    private String phone;
    private String type;

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
        return R.layout.user_updattpw;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }


    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        phonetypeTvGetcode.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPw, 20);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtCode, 6);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtName, 20);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getIn(intent);
    }

    private void getIn(Intent intent) {
        type = intent.getStringExtra("type");
        if (!TextUtils.isEmpty(type) && type.equals(ConstantManager.MAIN_ACTION_TYPE_UPDATEPW500)) {
            tv_tishi.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("修改密码");
        phone = (String) SharedPreferencesHelper.get(mContext, "phone", "");
        getIn(getIntent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.phonetype_tv_getcode:
                if (!codeSendingStatus) {
                    codeSendingStatus = true;
                    mPresent.pUseSendCode(phone, "6");
                }
                break;
            case R.id.btn_commit:
                String user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
                String code = phonetypeEtCode.getText().toString().trim();
                String new_pwd = phonetypeEtPw.getText().toString().trim();
                String conf_pwd = phonetypeEtName.getText().toString().trim();
                if (isCheck(phone, code, new_pwd, conf_pwd)) {
                    mPresent.updatePW(user_id, code, phone, new_pwd, conf_pwd);
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
    public void updatepwSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getData());
            doFinish();
        }
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

    @Override
    public void bindPhoneSuccess(LoginDataBean responseBean) {

    }

    /**
     * 前验证
     *
     * @param phone
     * @param password
     * @return
     */
    private boolean isCheck(String phone, String code, String password, String newpassword) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(getString(R.string.account_hint));
            return false;
        }
        if (TextUtils.isEmpty(code) || (!TextUtils.isEmpty(code) && code.length() != 6)) {
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

    private void back() {
        if (!TextUtils.isEmpty(type) && type.equals(ConstantManager.MAIN_ACTION_TYPE_UPDATEPW500)) {
            Log.e("typetype", "" + type);
            ToastUtils.showToast("系统检测您的密码过于简单，为了您的账户安全，请您修改密码。");
        } else {
            doFinish();
        }
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
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
