package com.longcheng.volunteer.modular.helpwith.applyhelp.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.ActionDataListBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.ExplainDataBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.OtherUserInfoDataBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.volunteer.modular.index.login.bean.LoginDataBean;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;

import butterknife.BindView;

/**
 * 互祝说明
 */
public class ExplainActivity extends BaseActivityMVP<ApplyHelpContract.View, ApplyHelpPresenterImp<ApplyHelpContract.View>> implements ApplyHelpContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_cont)
    EditText et_cont;
    @BindView(R.id.btn_save)
    TextView btnSave;
    @BindView(R.id.tv_moban)
    TextView tvMoban;
    private String action_id, user_id;
    private String describe;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_moban:
                intent = new Intent(mContext, ExplainMoBanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("action_id", action_id);
                intent.putExtra("user_id", user_id);
                startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_EXPLAIN);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.btn_save:
                describe = et_cont.getText().toString().trim();
                if (!TextUtils.isEmpty(describe)) {
                    intent = new Intent();
                    intent.putExtra("describe", describe);
                    setResult(ConstantManager.APPLYHELP_FORRESULT_EXPLAIN, intent);
                    doFinish();
                }
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_apply_explain;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvMoban.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("互祝说明");
        Intent intent = getIntent();
        describe = intent.getStringExtra("describe");
        action_id = intent.getStringExtra("action_id");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        et_cont.setText(describe);
    }


    @Override
    protected ApplyHelpPresenterImp<ApplyHelpContract.View> createPresent() {
        return new ApplyHelpPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void getNeedHelpNumberTaskSuccess(ActionDataBean responseBean) {

    }

    @Override
    public void ActionListSuccess(ActionDataListBean responseBean) {

    }

    @Override
    public void ActionDetailSuccess(ActionDataBean responseBean) {

    }

    @Override
    public void PeopleSearchListSuccess(PeopleSearchDataBean responseBean) {

    }

    @Override
    public void PeopleListSuccess(PeopleDataBean responseBean) {

    }

    @Override
    public void getOtherUserInfoSuccess(OtherUserInfoDataBean responseBean) {

    }

    @Override
    public void ExplainListSuccess(ExplainDataBean responseBean) {

    }

    @Override
    public void applyActionSuccess(ActionDataBean responseBean) {

    }

    @Override
    public void actionSafetySuccess(ActionDataBean responseBean) {

    }

    @Override
    public void saveUserInfo(LoginDataBean responseBean) {

    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {

    }

    @Override
    public void ListError() {

    }

    @Override
    public void GetAddressListSuccess(AddressListDataBean responseBean) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.APPLYHELP_FORRESULT_EXPLAIN) {
                describe = data.getStringExtra("describe");
                et_cont.setText(describe);
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
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
