package com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldOtherActivity;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldOtherNewActivity;
import com.longcheng.lifecareplan.utils.myview.address.AddressSelectUtils;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.CalendarActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.nanchen.calendarview.LunarSolarConverter;

import butterknife.BindView;

/**
 * 生命能量计算器
 */

public class LifeEnergyCalculatorActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.layout_address)
    LinearLayout layoutAddress;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.layout_date)
    LinearLayout layoutDate;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_calcutar)
    TextView tv_calcutar;

    AddressSelectUtils mAddressSelectUtils;
    private String pid, cid, aid, area;
    String birthday, skipType = "";

    @Override
    public void onClick(View v) {
        Intent intent;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_date:
                intent = new Intent(mContext, CalendarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("showDate", birthday);
                startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_DATE);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                break;
            case R.id.layout_address:
                mAddressSelectUtils.onSelectShiQu();
                break;
            case R.id.tv_calcutar:
                String user_name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                if (TextUtils.isEmpty(user_name)) {
                    ToastUtils.showToast("请输入姓名");
                    break;
                }
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showToast("请输入手机号");
                    break;
                }
                if (TextUtils.isEmpty(area)) {
                    ToastUtils.showToast("请选择出生地址");
                    break;
                }
                if (TextUtils.isEmpty(birthday)) {
                    ToastUtils.showToast("请选择出生日期");
                    break;
                }
                if (!TextUtils.isEmpty(skipType) && skipType.equals("PioneerMenuActivity")) {
                    intent = new Intent(mContext, AWordOfGoldOtherNewActivity.class);
                } else {
                    intent = new Intent(mContext, AWordOfGoldOtherActivity.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("user_name", user_name);
                intent.putExtra("phone", phone);
                intent.putExtra("area_simple", area);
                intent.putExtra("birthday", birthday);
                intent.putExtra("pid", pid);
                intent.putExtra("cid", cid);
                intent.putExtra("aid", aid);
                startActivity(intent);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.doctor_calculator;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("生命能量计算器");
        pagetopLayoutLeft.setOnClickListener(this);
        layoutAddress.setOnClickListener(this);
        layoutDate.setOnClickListener(this);
        tv_calcutar.setOnClickListener(this);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initDataAfter() {
        skipType = getIntent().getStringExtra("skipType");
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etName, 11);
        mAddressSelectUtils = new AddressSelectUtils(mActivity, mHandler, SELECTADDRESS);
        setBtn();
    }

    private void setBtn() {
        if (tv_calcutar == null) {
            return;
        }
        String user_name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        if (!TextUtils.isEmpty(user_name) &&
                !TextUtils.isEmpty(phone) &&
                !TextUtils.isEmpty(area) &&
                !TextUtils.isEmpty(birthday)) {
            tv_calcutar.setBackgroundResource(R.drawable.corners_bg_login);
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_calcutar, R.color.red);
        } else {
            tv_calcutar.setBackgroundResource(R.drawable.corners_bg_logingray);
        }
    }

    private final int SELECTADDRESS = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SELECTADDRESS:
                    //上传出生地
                    Bundle bundle = msg.getData();
                    pid = bundle.getString("pid");
                    cid = bundle.getString("cid");
                    aid = bundle.getString("aid");
                    area = bundle.getString("area");
                    tvAddress.setText(area);
                    setBtn();
                    break;
            }
        }
    };

    private void shoeDateView() {
        try {
            if (!TextUtils.isEmpty(birthday) && !birthday.equals("0000-00-00")) {
                int year = Integer.parseInt(birthday.split("-")[0]);
                int month = Integer.parseInt(birthday.split("-")[1]);
                int day = Integer.parseInt(birthday.split("-")[2]);
                if (year > 1900 && month > 0 && day > 0) {
                    String yinliDateShow = new LunarSolarConverter().LunarBlockLetter(birthday);
                    tvDate.setText(birthday + "\n" + yinliDateShow);
                } else {
                    birthday = "";
                }
                setBtn();
            }
        } catch (Exception e) {
            birthday = "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.USERINFO_FORRESULT_DATE) {
                birthday = data.getStringExtra("birthday");
                shoeDateView();
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
