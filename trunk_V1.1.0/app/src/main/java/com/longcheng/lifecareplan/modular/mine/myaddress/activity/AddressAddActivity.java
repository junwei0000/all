package com.longcheng.lifecareplan.modular.mine.myaddress.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 我的地址-添加
 */
public class AddressAddActivity extends BaseActivityMVP<AddressContract.View, AddressPresenterImp<AddressContract.View>> implements AddressContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.add_tv_nametitle)
    TextView addTvNametitle;
    @BindView(R.id.add_et_name)
    SupplierEditText addEtName;
    @BindView(R.id.add_et_phone)
    SupplierEditText addEtPhone;
    @BindView(R.id.add_tv_shiqu)
    TextView addTvShiqu;
    @BindView(R.id.add_relat_shiqu)
    RelativeLayout addRelatShiqu;
    @BindView(R.id.add_et_address)
    SupplierEditText addEtAddress;
    @BindView(R.id.add_tv_check)
    TextView addCbCheck;
    @BindView(R.id.item_tv_moren)
    TextView item_tv_moren;
    @BindView(R.id.btn_save)
    TextView btnSave;

    String province, city, district, order_id;
    String receive_user_id, consignee, area, address, mobile;
    private String user_id, is_default = "0";//是否默认 0：否 1：是

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.add_relat_shiqu:
                mAddressSelectUtils.onSelectShiQu();
                break;
            case R.id.add_tv_check:
            case R.id.item_tv_moren:
                if (is_default.equals("0")) {
                    is_default = "1";
                    addCbCheck.setBackgroundResource(R.mipmap.check_true_red);
                } else {
                    is_default = "0";
                    addCbCheck.setBackgroundResource(R.mipmap.check_false);
                }
                break;
            case R.id.btn_save:
                consignee = addEtName.getText().toString().trim();
                mobile = addEtPhone.getText().toString().trim();
                address = addEtAddress.getText().toString().trim();
                if (!TextUtils.isEmpty(consignee) && !TextUtils.isEmpty(address) &&
                        !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(area)) {
                    mPresent.addAddress(user_id, order_id, receive_user_id, consignee, province, city, district, address, mobile, is_default);
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
        return R.layout.myaddress_add;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(View view) {
        pageTopTvName.setText("新增地址");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        addRelatShiqu.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        item_tv_moren.setOnClickListener(this);
        addCbCheck.setOnClickListener(this);
        addEtName.addTextChangedListener(mTextWatcher);
        addEtPhone.addTextChangedListener(mTextWatcher);
        addEtAddress.addTextChangedListener(mTextWatcher);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(addEtPhone, 11);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(addEtAddress, 100);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(addEtName, 20);
    }

    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            consignee = addEtName.getText().toString().trim();
            mobile = addEtPhone.getText().toString().trim();
            address = addEtAddress.getText().toString().trim();
            setBtnBg();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    AddressSelectUtils mAddressSelectUtils;

    @Override
    public void initDataAfter() {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        Intent intent = getIntent();
        order_id = intent.getExtras().getString("order_id", "0");
        receive_user_id = intent.getStringExtra("receive_user_id");
        setBtnBg();
        mAddressSelectUtils = new AddressSelectUtils(mActivity, mHandler, SELECTADDRESS);
    }

    private void setBtnBg() {
        if (!TextUtils.isEmpty(consignee) && !TextUtils.isEmpty(address) &&
                !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(area)) {
            btnSave.setBackgroundResource(R.drawable.corners_bg_red);
        } else {
            btnSave.setBackgroundResource(R.drawable.corners_bg_logingray);
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
                    province = bundle.getString("pid");
                    city = bundle.getString("cid");
                    district = bundle.getString("aid");
                    area = bundle.getString("area");
                    addTvShiqu.setText(area);
                    setBtnBg();
                    break;
            }
        }
    };


    @Override
    protected AddressPresenterImp<AddressContract.View> createPresent() {
        return new AddressPresenterImp<>(mContext, this);
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
    public void ListSuccess(AddressListDataBean responseBean) {

    }

    @Override
    public void delSuccess(EditDataBean responseBean) {
    }

    @Override
    public void AddSuccess(AddressListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast("添加成功");
            sendBroadcastsRefreshList();
            doFinish();
        }
    }

    private void sendBroadcastsRefreshList() {
        Intent intent = new Intent();
        intent.setAction(ConstantManager.BroadcastReceiver_ORDER_ACTION);
        intent.putExtra("type", "EDIT");
        sendBroadcast(intent);//发送普通广播
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
