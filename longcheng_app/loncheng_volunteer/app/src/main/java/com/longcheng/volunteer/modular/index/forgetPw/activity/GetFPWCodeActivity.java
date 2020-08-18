package com.longcheng.volunteer.modular.index.forgetPw.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.SupplierEditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class GetFPWCodeActivity extends BaseActivityMVP<ForgetPWContract.View, ForgetPWPresenterImp<ForgetPWContract.View>> implements ForgetPWContract.View {


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
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_code;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
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
                    mPresent.pUseSendCode(phone, "2");
                }
                break;
            case R.id.btn_next:
                String code = phonetypeEtCode.getText().toString().trim();
                if (!TextUtils.isEmpty(code)) {
                    Intent intent = new Intent(mContext, ForgetSetPWActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("phone", phone);
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
