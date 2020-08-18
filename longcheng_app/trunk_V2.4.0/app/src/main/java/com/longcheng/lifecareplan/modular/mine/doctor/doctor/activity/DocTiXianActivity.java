package com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.RewardDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.VolSearchDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 提现
 */
public class DocTiXianActivity extends BaseActivityMVP<DoctorContract.View, DocPresenterImp<DoctorContract.View>> implements DoctorContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.et_cont)
    EditText etMoneysell;
    @BindView(R.id.tv_lines)
    TextView tvLines;
    @BindView(R.id.tv_yue)
    TextView tvYue;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_tixian)
    TextView tvTixian;

    String account_yue = "0", inputmoney;
    String source;

    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_all:
                etMoneysell.setText(account_yue);
                etMoneysell.setSelection(etMoneysell.getText().length());
                break;
            case R.id.tv_tixian:
                inputmoney = etMoneysell.getText().toString();
                if (!TextUtils.isEmpty(inputmoney) && Double.valueOf(inputmoney) > 0) {
                    mPresent.assetChange(inputmoney, source);
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
        return R.layout.volouteer_tixian;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("提现");
        pagetopLayoutLeft.setOnClickListener(this);
        tvAll.setOnClickListener(this);
        tvTixian.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etMoneysell, 11);
        etMoneysell.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //还原数据
                if (etMoneysell != null) {
                    inputmoney = etMoneysell.getText().toString();
                    if (!TextUtils.isEmpty(inputmoney)) {
                        if (Double.valueOf(inputmoney) > Double.valueOf(account_yue)) {
                            etMoneysell.setText(account_yue);
                            etMoneysell.setSelection(etMoneysell.getText().length());
                        }
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        source = intent.getStringExtra("source");
        account_yue = intent.getStringExtra("account_yue");
        tvYue.setText("账户余额: ¥" + account_yue);
        account_yue = PriceUtils.getInstance().getStrWeiShu0(account_yue);
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

    }

    @Override
    public void GetRewardInfoSuccess(RewardDataBean responseBean) {

    }

    @Override
    public void VolAddSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status.equals("200")) {
            doFinish();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
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
