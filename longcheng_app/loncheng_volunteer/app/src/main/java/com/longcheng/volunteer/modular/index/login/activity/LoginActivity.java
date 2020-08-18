package com.longcheng.volunteer.modular.index.login.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.bean.MessageEvent;
import com.longcheng.volunteer.modular.index.forgetPw.activity.ForgetPWActivity;
import com.longcheng.volunteer.modular.index.login.bean.LoginAfterBean;
import com.longcheng.volunteer.modular.index.login.bean.LoginDataBean;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.index.register.activity.RegisterActivity;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.volunteer.push.PushClient;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.Utils;
import com.longcheng.volunteer.utils.myview.SupplierEditText;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;
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
    @BindView(R.id.phonetype_iv)
    ImageView phonetypeIv;
    @BindView(R.id.login_rl_phonetype)
    RelativeLayout loginRlPhonetype;
    @BindView(R.id.phonetype_et_phone)
    SupplierEditText phonetypeEtPhone;
    @BindView(R.id.phonetype_et_pw)
    SupplierEditText phonetypeEtPw;
    @BindView(R.id.phonetype_iv_pwsee)
    ImageView phonetypeIvPwsee;

    @BindView(R.id.phonetype_layout_pwsee)
    LinearLayout phonetype_layout_pwsee;
    @BindView(R.id.login_rl_pw)
    RelativeLayout loginRlPw;
    @BindView(R.id.phonetype_et_code)
    SupplierEditText phonetypeEtCode;
    @BindView(R.id.phonetype_tv_getcode)
    TextView phonetypeTvGetcode;
    @BindView(R.id.login_rl_code)
    RelativeLayout loginRlCode;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.login_lt_forgetpw)
    RelativeLayout loginLtForgetpw;
    @BindView(R.id.yuedu_cb_check)
    CheckBox yueduCbCheck;
    @BindView(R.id.yuedu_tv_ti)
    TextView yueduTvTi;
    @BindView(R.id.yuedu_tv_tiaojian)
    TextView yueduTvTiaojian;
    @BindView(R.id.login_lt_yuedu)
    RelativeLayout loginLtYuedu;
    @BindView(R.id.loginthird_iv_wechat)
    ImageView loginthirdIvWechat;
    @BindView(R.id.loginthird_iv_qq)
    ImageView loginthirdIvQq;
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

    /**
     * 是否显示密码
     */
    boolean showpwStatus = false;


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
        loginthirdIvWechat.setOnClickListener(this);
        loginthirdIvQq.setOnClickListener(this);
        phonetype_layout_pwsee.setOnClickListener(this);
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
                back();
                break;
            case R.id.pagetop_layout_rigth:
                //注册
                intent = new Intent(mContext, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.login_btn_phone:
                phoneLoginStus = true;
                setLoginStusView();
                break;
            case R.id.login_btn_account:
                phoneLoginStus = false;
                setLoginStusView();
                break;
            case R.id.phonetype_layout_pwsee:
                setPwShowView();
                break;
            case R.id.login_btn_forgetpw:
                //忘记密码
                intent = new Intent(mContext, ForgetPWActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
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

                } else {
                    //账号登录
                    if (isCheckLogin(phoneNum, pw)) {
                        mPresent.pUserAccountLogin(phoneNum, pw);
                    }
                }
                break;
            case R.id.loginthird_iv_wechat:
                weiXinLogin(v);
                break;
            case R.id.loginthird_iv_qq:
                qqLogin(v);
                break;
            case R.id.login_lt_yuedu:
                intent = new Intent(mContext, AgreementAct.class);
                intent.putExtra("starturl", "http://t.asdyf.com/home/login/protocol");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            default:
                break;
        }
    }

    private void setPwShowView() {
        if (showpwStatus) {
            showpwStatus = false;
            phonetypeIvPwsee.setBackgroundResource(R.mipmap.userlogin_closepw_icon);
            phonetypeEtPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            showpwStatus = true;
            phonetypeIvPwsee.setBackgroundResource(R.mipmap.userlogin_showpw_icon);
            phonetypeEtPw.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        if (!TextUtils.isEmpty(phonetypeEtPw.getText())) {
            phonetypeEtPw.requestFocus();
            phonetypeEtPw.setSelection(phonetypeEtPw.getText().length());//将光标移至文字末尾
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

    public void qqLogin(View view) {
        if (ExampleApplication.mUMShareAPI.isInstall(this, SHARE_MEDIA.QQ)) {
            authorization(SHARE_MEDIA.QQ);
        } else {
            ToastUtils.showToast("请先安装QQ客户端");
        }
    }

    public void weiXinLogin(View view) {
        if (ExampleApplication.mUMShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN)) {
            authorization(SHARE_MEDIA.WEIXIN);
        } else {
            ToastUtils.showToast(getString(R.string.LoginWx_nottishi));
        }
    }

    /**
     * 登录授权
     * https://www.cnblogs.com/changyiqiang/p/5871926.html
     */

    private void authorization(SHARE_MEDIA share_media) {
        ExampleApplication.mUMShareAPI.getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String openid = map.get("uid");
                String unionid = map.get("unionid");//微博没有
                String nick_name = map.get("name");
                String headimgurl = map.get("iconurl");
                String sex = map.get("gender");
                if (!TextUtils.isEmpty(sex) && sex.equals("男")) {
                    sex = "1";//1男2女
                } else {
                    sex = "2";
                }
                String province = map.get("province");
                String city = map.get("city");
                String access_token = map.get("access_token");
                Log.e(TAG, "*******************************" + map.toString());
                //拿到信息去请求登录接口。。。
                mPresent.pUseWXLogin(openid, unionid, nick_name, headimgurl, sex, province, city, access_token);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }

    @Override
    protected LoginPresenterImp<LoginContract.View> createPresent() {
        return new LoginPresenterImp<>(mContext, this);
    }

    @Override
    public void loginSuccess(LoginDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            LoginAfterBean mLoginInfo = (LoginAfterBean) responseBean.getData();
            mUserLoginSkipUtils.getLoginInfo(mLoginInfo);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
        Log.e(TAG, "*******************************" + responseBean.toString());
    }

    @Override
    public void loginFail() {
        ToastUtils.showToast(getString(R.string.net_tishi));
    }

    @Override
    public void bindPhoneSuccess(LoginDataBean responseBean) {

    }

    @Override
    public void updatepwSuccess(EditDataBean responseBean) {

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

    //QQ与新浪微博的回调：
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ExampleApplication.mUMShareAPI.onActivityResult(requestCode, resultCode, data);
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

    private void back() {
        mUserLoginSkipUtils.doBackCheck();
        doFinish();
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

}
