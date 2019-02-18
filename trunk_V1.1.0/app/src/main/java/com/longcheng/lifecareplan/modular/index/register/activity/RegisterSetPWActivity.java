package com.longcheng.lifecareplan.modular.index.register.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.MessageEvent;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.LoginActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class RegisterSetPWActivity extends BaseActivityMVP<RegisterContract.View, RegisterPresenterImp<RegisterContract.View>> implements RegisterContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.phonetype_et_pw)
    SupplierEditText phonetypeEtPw;
    @BindView(R.id.phonetype_et_name)
    SupplierEditText phonetypeEtName;
    @BindView(R.id.btn_commit)
    TextView btnCommit;
    private String phone, code;
    UserLoginSkipUtils mUserLoginSkipUtils;

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void CheckPhoneSuccess(ResponseBean responseBean) {

    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {

    }

    @Override
    public void registerSuccess(LoginDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            LoginAfterBean mLoginInfo = (LoginAfterBean) responseBean.getData();
            mUserLoginSkipUtils.getLoginInfo(mLoginInfo);
            // 发布事件
            new Thread("posting") {
                @Override
                public void run() {
                    EventBus.getDefault().post(new MessageEvent("register"));
                }
            }.start();
        }
    }

    @Override
    public void loginFail() {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_register_setpw;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        code = intent.getStringExtra("code");
        mUserLoginSkipUtils = new UserLoginSkipUtils(this);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPw, 20);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtName, 20);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_commit:
                String pw = phonetypeEtPw.getText().toString().trim();
                String pw2 = phonetypeEtName.getText().toString().trim();
                if (Utils.isCheckPW(pw, pw2)) {
                    mPresent.register(phone, code, pw, pw2);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected RegisterPresenterImp<RegisterContract.View> createPresent() {
        return new RegisterPresenterImp<>(mContext, this);
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
