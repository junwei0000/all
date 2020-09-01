package com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.PushQueueAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBContract;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBPresenterImp;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设置开通创业中心权限
 */
public class PionOpenSetActivity extends BaseActivityMVP<PionOpenSetContract.View, PionpenSetPresenterImp<PionOpenSetContract.View>> implements PionOpenSetContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_cont)
    EditText etCont;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.tv_jieeqi)
    TextView tv_jieeqi;
    @BindView(R.id.item_tv_phone)
    TextView itemTvPhone;
    @BindView(R.id.realt_info)
    RelativeLayout realtInfo;
    @BindView(R.id.btn_record)
    TextView btnRecord;
    @BindView(R.id.btn_commit)
    TextView btnCommit;

    String add_user_id;

    @Override
    public void onClick(View v) {
        Intent intent;
        String search;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_sure:
                search = etCont.getText().toString();
                if (!TextUtils.isEmpty(search)) {
                    mPresent.getUserByPhone(search);
                } else {
                    ToastUtils.showToast("请输入正确的手机号");
                }
                break;
            case R.id.btn_record:
                intent = new Intent(mActivity, PionOpenSetRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.btn_commit:
                if (!TextUtils.isEmpty(add_user_id)) {
                    mPresent.addEntrepreneursUser(add_user_id);
                } else {
                    ToastUtils.showToast("请输入开通人的手机号");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_openset;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("创业名额设置");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        btnRecord.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etCont, 11);
    }


    @Override
    public void initDataAfter() {

    }

    @Override
    protected PionpenSetPresenterImp<PionOpenSetContract.View> createPresent() {
        return new PionpenSetPresenterImp<>(mContext, this);
    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void getUserByPhoneSuccess(PionOpenSetDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            PionOpenSetDataBean.PionOpenSetItemBean mAfterBean = responseBean.getData();
            if (mAfterBean != null) {
                add_user_id = mAfterBean.getUser_id();
                GlideDownLoadImage.getInstance().loadCircleImage(mAfterBean.getAvatar(), itemIvThumb);
                itemTvName.setText(CommonUtil.setName(mAfterBean.getUser_name()));
                itemTvPhone.setText("手机号：" + mAfterBean.getPhone());
                tv_jieeqi.setText(mAfterBean.getJieqi_branch_name() + "." + mAfterBean.getJieqi_name());
                realtInfo.setVisibility(View.VISIBLE);
            } else {
                ToastUtils.showToast("暂无该用户");
                add_user_id = "";
                realtInfo.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void commitSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            etCont.setText("");
            add_user_id = "";
            realtInfo.setVisibility(View.GONE);
            ToastUtils.showToast(responseBean.getMsg());
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void ListSuccess(PionOpenSetRecordDataBean responseBean, int pageback) {

    }

    @Override
    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }


    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
            doFinish();
        }
        return false;
    }

}
