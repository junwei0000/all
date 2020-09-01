package com.longcheng.lifecareplan.modular.index.login.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.MessageEvent;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.forgetPw.activity.ForgetPWActivity;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.index.register.activity.RegisterActivity;
import com.longcheng.lifecareplan.modular.mine.message.activity.MessageActivity;
import com.longcheng.lifecareplan.modular.mine.starinstruction.StarInstructionAct;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.push.PushClient;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
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


    @BindView(R.id.login_btn_register)
    TextView login_btn_register;
    @BindView(R.id.phonetype_et_phone)
    EditText phonetypeEtPhone;
    @BindView(R.id.phonetype_et_pw)
    EditText phonetypeEtPw;
    @BindView(R.id.phonetype_iv_pwsee)
    ImageView phonetypeIvPwsee;

    @BindView(R.id.phonetype_layout_pwsee)
    LinearLayout phonetype_layout_pwsee;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.loginthird_iv_wechat)
    ImageView loginthirdIvWechat;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login_btn_forgetpw)
    TextView loginBtnForgetpw;
    @BindView(R.id.layout_cont)
    LinearLayout layout_cont;

    /**
     * 是否显示密码
     */
    boolean showpwStatus = false;


    UserLoginSkipUtils mUserLoginSkipUtils;

    boolean cickStatus = false;

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
        return R.layout.user_login;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBar(mActivity, toolbar);
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
        login_btn_register.setOnClickListener(this);
        loginBtnForgetpw.setOnClickListener(this);
        loginthirdIvWechat.setOnClickListener(this);
        phonetype_layout_pwsee.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPhone, 11);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPw, 20);
        int wih = DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 20);
        layout_cont.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (wih * 0.90)));
        phonetypeEtPhone.addTextChangedListener(mTextWatcher);
        phonetypeEtPw.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (phonetypeEtPhone != null) {
                String phoneNum = phonetypeEtPhone.getText().toString().trim();
                String pw = phonetypeEtPw.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(pw)) {
                    btnLogin.setBackgroundResource(R.drawable.corners_bg_login_new);
                    cickStatus = true;
                } else {
                    btnLogin.setBackgroundResource(R.drawable.corners_bg_logingray_new);
                    cickStatus = false;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login_btn_register:
                //注册
                intent = new Intent(mContext, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
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
            case R.id.btn_login:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                String phoneNum = phonetypeEtPhone.getText().toString().trim();
                String pw = phonetypeEtPw.getText().toString().trim();
                //账号登录
                if (cickStatus) {
                    mPresent.pUserAccountLogin(phoneNum, pw);
                }
                break;
            case R.id.loginthird_iv_wechat:
                weiXinLogin(v);
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
                String openid = map.get("openid");
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
            LoginAfterBean mLoginInfo = responseBean.getData();
            mUserLoginSkipUtils.getLoginInfo(mLoginInfo);
        } else if (status.equals("401")) {
            String phoneNum1 = phonetypeEtPhone.getText().toString().trim();
            Intent intents = new Intent(mActivity, GetLogCodeActivity.class);
            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intents.putExtra("phone", "" + phoneNum1);
            mActivity.startActivity(intents);
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
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(getString(R.string.forget_repeatpw_hint));
            return false;
        }
        if (password.length() < 6 || password.length() > 20) {
            ToastUtils.showToast(getString(R.string.forget_repeatpw_hint));
            return false;
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
