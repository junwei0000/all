package com.longcheng.lifecareplan.modular.home.commune.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneDataBean;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoContract;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoPresenterImp;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditThumbDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetUserSETDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import butterknife.BindView;

/**
 * 发布公告
 */
public class PublishActivity extends BaseListActivity<CommuneContract.View, CommunePresenterImp<CommuneContract.View>> implements CommuneContract.View {


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
    private int group_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_commit:
                content = user_sendidea_content.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    mPresent.publish(user_id, group_id, content);
                } else {
                    ToastUtils.showToast("请输入您的意见或建议");
                }
                break;
        }
    }

    @Override
    protected CommunePresenterImp<CommuneContract.View> createPresent() {
        return new CommunePresenterImp<>(mContext, this);
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
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        Intent intent = getIntent();
        group_id = intent.getIntExtra("group_id", 0);
        pageTopTvName.setText("发布公告");
        btn_commit.setText("发布");
        btn_commit.setBackgroundColor(getResources().getColor(R.color.red));
        user_sendidea_content.setHint("请输入需要发布的公告内容(140字内)");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(user_sendidea_content, 140);
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
    public void JoinListSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void MineCommuneInfoSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void MineRankListSuccess(CommuneListDataBean responseBean, int backpage) {

    }

    @Override
    public void GetTeamListSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void GetMemberListSuccess(CommuneListDataBean responseBean, int backpage) {

    }

    @Override
    public void JoinCommuneSuccess(EditListDataBean responseBean) {
        String status_ = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status_.equals("400")) {
        } else if (status_.equals("200")) {
            doFinish();
        }
    }

    @Override
    public void ClickLikeSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void editAvatarSuccess(EditThumbDataBean responseBean) {

    }

    @Override
    public void GetCreateTeamInfoSuccess(CommuneListDataBean responseBean) {

    }

    @Override
    public void CreateTeamSuccess(EditListDataBean responseBean) {

    }

    @Override
    public void CreateTeamSearchSuccess(CommuneDataBean responseBean) {

    }

    @Override
    public void ListError() {

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
