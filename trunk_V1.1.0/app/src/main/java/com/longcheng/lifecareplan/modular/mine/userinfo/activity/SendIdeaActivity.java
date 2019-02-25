package com.longcheng.lifecareplan.modular.mine.userinfo.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetUserSETDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;

import butterknife.BindView;

/**
 * 意见反馈
 */
public class SendIdeaActivity extends BaseActivityMVP<UserInfoContract.View, UserInfoPresenterImp<UserInfoContract.View>> implements UserInfoContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.btn_commit)
    TextView btn_commit;
    @BindView(R.id.user_sendidea_content)
    EditText user_sendidea_content;
    private String content, user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_commit:
                content = user_sendidea_content.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    mPresent.sendIdea(user_id, content);
                } else {
                    ToastUtils.showToast("请输入您的意见或建议");
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
        return R.layout.user_sendidea;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("意见反馈");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(user_sendidea_content, 200);
    }

    @Override
    public void initDataAfter() {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
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
