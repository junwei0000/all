package com.longcheng.volunteer.modular.mine.userinfo.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.GetUserSETDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.SupplierEditText;

import butterknife.BindView;

/**
 * 修改姓名
 */
public class UpdateNameActivity extends BaseActivityMVP<UserInfoContract.View, UserInfoPresenterImp<UserInfoContract.View>> implements UserInfoContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvrigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRight;
    @BindView(R.id.updatename_et_name)
    SupplierEditText updatename_et_name;
    private String user_name, user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                user_name = updatename_et_name.getText().toString();
                if (!TextUtils.isEmpty(user_name)) {
                    mPresent.editName(user_id, user_name);
                } else {
                    ToastUtils.showToast("请输入姓名");
                }
                break;
        }
    }

    @Override
    protected UserInfoPresenterImp<UserInfoContract.View> createPresent() {
        return new UserInfoPresenterImp<>(mContext, this);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.user_account_updatename;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("修改姓名");
        pageTopTvrigth.setText("确定");
        pageTopTvrigth.setVisibility(View.VISIBLE);
        setOrChangeTranslucentColor(toolbar, null);
        user_name = getIntent().getStringExtra("user_name");
        user_id = getIntent().getStringExtra("user_id");
        updatename_et_name.setText(user_name);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(updatename_et_name, 16);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRight.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void editSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getData());
            Intent intent = new Intent();
            intent.putExtra("user_name", user_name);
            setResult(ConstantManager.USERINFO_FORRESULT_NAME, intent);
            doFinish();
        }
    }

    @Override
    public void saveInfoSuccess(EditDataBean responseBean) {

    }

    @Override
    public void editAvatarSuccess(EditThumbDataBean responseBean) {

    }

    @Override
    public void editBirthdaySuccess(EditDataBean responseBean) {
    }

    @Override
    public void getUserSetSuccess(GetUserSETDataBean responseBean) {

    }

    @Override
    public void editError() {

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
