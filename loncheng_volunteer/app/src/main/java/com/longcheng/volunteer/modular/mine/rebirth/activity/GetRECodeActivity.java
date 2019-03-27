package com.longcheng.volunteer.modular.mine.rebirth.activity;

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
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.mine.rebirth.bean.RebirthDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.SupplierEditText;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class GetRECodeActivity extends BaseActivityMVP<RebirthContract.View, RebirthPresenterImp<RebirthContract.View>> implements RebirthContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTop_tv_name;
    @BindView(R.id.et_code)
    SupplierEditText EtCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.btn_next)
    TextView btnNext;
    private String phone;
    private String user_id;


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.my_rebirthcode;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        phone = (String) SharedPreferencesHelper.get(mContext, "phone", "");
    }

    @Override
    public void setListener() {
        pageTop_tv_name.setText("短信验证");
        pagetopLayoutLeft.setOnClickListener(this);
        tvGetcode.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(EtCode, 6);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_getcode:
                if (!TextUtils.isEmpty(phone)) {
                    if (!codeSendingStatus) {
                        codeSendingStatus = true;
                        mPresent.pUseSendCode(phone, "7");
                    }
                } else {
                    ToastUtils.showToast("请完善个人信息并绑定手机号");
                }
                break;
            case R.id.btn_next:
                String code = EtCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showToast("验证码不能为空");
                } else {
                    mPresent.setRenascence(user_id, code);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getRebirthInfoSuccess(RebirthDataBean responseBean) {

    }

    @Override
    public void getCodeSuccess(ResponseBean responseBean) {
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
    public void RebirthSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status.equals("200")) {
            doFinish();
        }
    }

    @Override
    public void ListError() {

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
                    tvGetcode.setEnabled(false);
                    count = 60;
                    daojishi();
                    break;
                case Daojishiover:
                    if (msg.arg1 < 10) {
                        tvGetcode.setText("0" + msg.arg1 + getString(R.string.tv_codeunit));
                    } else {
                        tvGetcode.setText(msg.arg1 + getString(R.string.tv_codeunit));
                    }
                    if (msg.arg1 <= 0) {
                        tvGetcode.setTextColor(getResources().getColor(R.color.blue));
                        tvGetcode.setEnabled(true);
                        tvGetcode.setText(getString(R.string.code_get));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected RebirthPresenterImp<RebirthContract.View> createPresent() {
        return new RebirthPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

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
