package com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.adapter.DoclRewardListAdapter;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.RewardDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.VolSearchDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 志愿者添加
 */
public class VoloutAddActivity extends BaseActivityMVP<DoctorContract.View, DocPresenterImp<DoctorContract.View>> implements DoctorContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_phone)
    SupplierEditText etPhone;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont_title)
    TextView notDateContTitle;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.item_iv_img)
    ImageView itemIvImg;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_phone)
    TextView itemTvPhone;
    @BindView(R.id.item_tv_add)
    TextView itemTvAdd;
    @BindView(R.id.layout_user)
    LinearLayout layout_user;

    String volunteer_user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_search:
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showToast("请输入志愿者手机号");
                    break;
                }
                if (!TextUtils.isEmpty(phone) && phone.length() != 11) {
                    ToastUtils.showToast("请输入正确的志愿者手机号");
                    break;
                }
                mPresent.searchVolunteer(phone);
                break;
            case R.id.item_tv_add:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                if (!TextUtils.isEmpty(volunteer_user_id))
                    mPresent.addVolunteer(volunteer_user_id);
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.doctor_volout_add;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("添加志愿者");
        notDateCont.setText("暂无搜索数据");
        pagetopLayoutLeft.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        itemTvAdd.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
    }


    @Override
    protected DocPresenterImp<DoctorContract.View> createPresent() {
        return new DocPresenterImp<>(mActivity, this);
    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void ListSuccess(DocRewardListDataBean responseBean, int backPage) {

    }

    @Override
    public void VolSearchSuccess(VolSearchDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
            layoutNotdate.setVisibility(View.VISIBLE);
            layout_user.setVisibility(View.GONE);
        } else if (status.equals("200")) {
            layoutNotdate.setVisibility(View.GONE);
            layout_user.setVisibility(View.VISIBLE);
            VolSearchDataBean.DocItemBean data = responseBean.getData();
            if (data != null && !TextUtils.isEmpty(data.getUser_id())) {
                volunteer_user_id = data.getUser_id();
                GlideDownLoadImage.getInstance().loadCircleImage(data.getAvatar(), itemIvImg);
                itemTvName.setText(data.getUser_name());
                itemTvPhone.setText(data.getPhone());
            } else {
                layoutNotdate.setVisibility(View.VISIBLE);
                layout_user.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void GetRewardInfoSuccess(RewardDataBean responseBean) {

    }

    @Override
    public void VolAddSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast("添加成功");
            doFinish();
        }
    }


    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
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
