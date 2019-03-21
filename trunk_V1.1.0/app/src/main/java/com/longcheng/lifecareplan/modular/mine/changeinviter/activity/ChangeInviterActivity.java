package com.longcheng.lifecareplan.modular.mine.changeinviter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.changeinviter.bean.InviteAfterBean;
import com.longcheng.lifecareplan.modular.mine.changeinviter.bean.InviteDataBean;
import com.longcheng.lifecareplan.modular.mine.changeinviter.bean.InviteItemBean;
import com.longcheng.lifecareplan.modular.mine.rebirth.activity.GetRECodeActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 变更邀请人
 */
public class ChangeInviterActivity extends BaseActivityMVP<ChangeInviterContract.View, ChangeInviterPresenterImp<ChangeInviterContract.View>> implements ChangeInviterContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.tv_invitername)
    TextView tvInvitername;
    @BindView(R.id.tv_invitercommune)
    TextView tvInvitercommune;
    @BindView(R.id.tv_inviterphone)
    TextView tvInviterphone;
    @BindView(R.id.layout_haveinviter)
    LinearLayout layoutHaveinviter;
    @BindView(R.id.tv_notinviter)
    TextView tvNotinviter;
    @BindView(R.id.et_search)
    SupplierEditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;
    @BindView(R.id.tv_tishi)
    TextView tvTishi;
    @BindView(R.id.iv_searchuserthumb)
    ImageView ivSearchuserthumb;
    @BindView(R.id.tv_searchuserinvitername)
    TextView tvSearchuserinvitername;
    @BindView(R.id.tv_searchuserinvitercommune)
    TextView tvSearchuserinvitercommune;
    @BindView(R.id.tv_searchuserinviterphone)
    TextView tvSearchuserinviterphone;
    @BindView(R.id.layout_searchuser)
    FrameLayout layoutSearchuser;
    @BindView(R.id.tv_change)
    TextView tvChange;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;


    private String user_id, commend_user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_search:
                search();
                break;
            case R.id.tv_change:
                if (!TextUtils.isEmpty(commend_user_id)) {
                    Intent intent = new Intent(mContext, GetCICodeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("commend_user_id", commend_user_id);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
                break;
        }
    }

    private void search() {
        String phone = etSearch.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showMyDialog(false, "请输入变更人的手机号");
            return;
        } else if (!Utils.isPhoneNum(phone)) {
            showMyDialog(false, "请输入正确的手机号");
            return;
        } else {
            ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
            mPresent.SearchInvite(user_id, phone);
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.my_changeinviter;
    }


    @Override
    public void initView(View view) {
        pageTopTvName.setText("变更邀请人");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    search();
                    return true;
                }
                return false;
            }
        });
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etSearch, 11);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.getInviteInfo(user_id);
    }

    @Override
    public void initDataAfter() {
        user_id = UserUtils.getUserId(mContext);
        mPresent.getInviteInfo(user_id);
    }


    @Override
    protected ChangeInviterPresenterImp<ChangeInviterContract.View> createPresent() {
        return new ChangeInviterPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void getInviteInfoSuccess(InviteDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            InviteAfterBean mEnergyAfterBean = responseBean.getData();
            InviteItemBean CommendInvitationInfo = mEnergyAfterBean.getCommendInvitationInfo();
            if (CommendInvitationInfo != null && !TextUtils.isEmpty(CommendInvitationInfo.getUser_id())) {
                layoutHaveinviter.setVisibility(View.VISIBLE);
                tvNotinviter.setVisibility(View.INVISIBLE);
                GlideDownLoadImage.getInstance().loadCircleImage(mContext, CommendInvitationInfo.getAvatar(), ivThumb);
                String name = CommendInvitationInfo.getUser_name();
                if (TextUtils.isEmpty(name)) {
                    name = "暂无";
                }
                String Groupname = CommendInvitationInfo.getGroup_name();
                if (TextUtils.isEmpty(Groupname)) {
                    Groupname = "暂无";
                }
                tvInvitername.setText("姓名：" + name);
                tvInvitercommune.setText("公社：" + Groupname);
                tvInviterphone.setText("手机号：" + CommendInvitationInfo.getPhone());
            } else {
                layoutHaveinviter.setVisibility(View.INVISIBLE);
                tvNotinviter.setVisibility(View.VISIBLE);
            }
            //是否做过邀请人变更 0：否 1：是 ，只能变更联系人一次
            int isChangeInvitation = mEnergyAfterBean.getIsChangeInvitation();
            if (isChangeInvitation == 0) {
                tvTishi.setText("*邀请人变更只可修改一次，请慎重操作");
                layoutSearch.setVisibility(View.VISIBLE);
                layoutBottom.setVisibility(View.VISIBLE);
            } else {
                tvTishi.setText("*邀请人变更只可修改一次，您已执行过此操作");
                layoutSearch.setVisibility(View.GONE);
                layoutSearchuser.setVisibility(View.INVISIBLE);
                layoutBottom.setVisibility(View.INVISIBLE);
                tvChange.setBackgroundResource(R.drawable.corners_bg_logingray);
            }

        }
    }

    @Override
    public void SearchInviteSuccess(InviteDataBean responseBean) {
        tvChange.setBackgroundResource(R.drawable.corners_bg_logingray);
        layoutSearchuser.setVisibility(View.INVISIBLE);
        commend_user_id = "";
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            showMyDialog(false, responseBean.getMsg());
        } else if (status_.equals("200")) {
            InviteAfterBean mEnergyAfterBean = responseBean.getData();
            InviteItemBean searchInfo = mEnergyAfterBean.getUser();
            if (searchInfo != null && !TextUtils.isEmpty(searchInfo.getUser_id())) {
                layoutSearchuser.setVisibility(View.VISIBLE);
                GlideDownLoadImage.getInstance().loadCircleImage(mContext, searchInfo.getAvatar(), ivSearchuserthumb);
                String name = searchInfo.getUser_name();
                if (TextUtils.isEmpty(name)) {
                    name = "暂无";
                }
                String Groupname = searchInfo.getGroup_name();
                if (TextUtils.isEmpty(Groupname)) {
                    Groupname = "暂无";
                }
                tvSearchuserinvitername.setText("姓名：" + name);
                tvSearchuserinvitercommune.setText("公社：" + Groupname);
                tvSearchuserinviterphone.setText("手机号：" + searchInfo.getPhone());
                tvChange.setBackgroundResource(R.drawable.corners_bg_login);
                //变更的邀请人是否是当前登录人本人 0 不是本人登录用户 ，1 是本人 1的时候不能变更
                int isCurrentLoginUser = mEnergyAfterBean.getIsCurrentLoginUser();
                //变更的邀请人是否已经是当前任的邀请人 0 不是当前邀请人，1 是当前邀请人 1的时候不能变更
                int isCurrentInvitationUser = mEnergyAfterBean.getIsCurrentInvitationUser();
                //变更的邀请人是否是我邀请的人 0 不是我邀请的人 , 1 是我邀请的人 1的时候不能变更
                int isMyInvitationUser = mEnergyAfterBean.getIsMyInvitationUser();

                if (isCurrentLoginUser == 1 || isCurrentInvitationUser == 1 || isMyInvitationUser == 1) {
                    tvChange.setBackgroundResource(R.drawable.corners_bg_logingray);
                    if (isMyInvitationUser == 1) {
                        ToastUtils.showToast(name + "是您邀请的人");
                    }
                } else {
                    commend_user_id = searchInfo.getUser_id();
                }
            }


        }
    }

    @Override
    public void getCodeSuccess(ResponseBean responseBean) {

    }

    @Override
    public void ChangeInviteSuccess(EditListDataBean responseBean) {
    }

    @Override
    public void ListError() {
    }

    MyDialog mDialog;
    TextView tv_cont, btn_left, btn_change;

    public void showMyDialog(boolean clickbtn, String msg) {
        if (mDialog == null) {
            mDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_changeinvitertishi);// 创建Dialog并设置样式主题
            mDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            mDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = (LinearLayout) mDialog.findViewById(R.id.layout_cancel);
            tv_cont = (TextView) mDialog.findViewById(R.id.tv_cont);
            btn_left = (TextView) mDialog.findViewById(R.id.btn_left);
            btn_change = (TextView) mDialog.findViewById(R.id.btn_change);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        } else {
            mDialog.show();
        }
        if (clickbtn) {
            btn_left.setText("再想想");
            tv_cont.setText("您正在执行变更邀请人操作，邀请人变更只可修改一次，请慎重。");
            btn_change.setVisibility(View.VISIBLE);
        } else {
            tv_cont.setText(msg);
            btn_left.setText("知道了");
            btn_change.setVisibility(View.GONE);
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
