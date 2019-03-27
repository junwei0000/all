package com.longcheng.volunteer.modular.index.forgetPw.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.Utils;
import com.longcheng.volunteer.utils.myview.SupplierEditText;

import butterknife.BindView;

public class ForgetPWActivity extends BaseActivityMVP<ForgetPWContract.View, ForgetPWPresenterImp<ForgetPWContract.View>> implements ForgetPWContract.View {


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
    @BindView(R.id.btn_next)
    TextView btnNext;
    private String phoneNum;


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_forget;
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
        btnNext.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(phonetypeEtPhone, 11);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_next:
                phoneNum = phonetypeEtPhone.getText().toString().trim();
                if (Utils.isCheckPhone(phoneNum)) {
                    mPresent.ForgetPWcheckphone(phoneNum);
                }
                break;
            default:
                break;
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
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            Intent intent = new Intent(mContext, GetFPWCodeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("phone", phoneNum);
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {

    }

    @Override
    public void loginFail() {

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
