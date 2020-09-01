package com.longcheng.lifecareplan.modular.mine.myaddress.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.myaddress.adapter.AddressListAdapter;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressAfterBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressItemBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.myview.address.AddressSelectUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的地址
 */
public class AddressListActivity extends BaseActivityMVP<AddressContract.View, AddressPresenterImp<AddressContract.View>> implements AddressContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.date_listview)
    ListView dateListview;
    @BindView(R.id.not_date_img)
    ImageView notDateImg;
    @BindView(R.id.not_date_cont)
    TextView notDateCont;
    @BindView(R.id.not_date_btn)
    TextView notDateBtn;
    @BindView(R.id.layout_notdate)
    LinearLayout layoutNotdate;
    @BindView(R.id.btn_add)
    TextView btnAdd;
    private String user_id;
    private String receive_user_id;
    private String skiptype;
    /**
     * 是否可以编辑和删除地址 0：否 1：是 （当接福人是自己或者自己家人的时候课编辑）
     */
    private String isCanEdit;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.btn_add:
                Intent intent = new Intent(mContext, AddressAddActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("receive_user_id", receive_user_id);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.myaddress;
    }

    @Override
    public void initView(View view) {
        notDateBtn.setVisibility(View.GONE);
        notDateCont.setText("您还没有收货地址～");
        notDateImg.setBackgroundResource(R.mipmap.my_shippingaddress_icon);
        setOrChangeTranslucentColor(toolbar, null);

        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity,btnAdd,R.color.red);
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        Intent intent = getIntent();
        receive_user_id = intent.getStringExtra("receive_user_id");
        skiptype = intent.getStringExtra("skiptype");
        if (!TextUtils.isEmpty(skiptype) && skiptype.equals("MineFragment")) {
            pageTopTvName.setText("地址管理");
        } else {
            pageTopTvName.setText("选择地址");
        }
        mPresent.setListViewData(user_id, receive_user_id);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        dateListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!TextUtils.isEmpty(skiptype) && !skiptype.equals("MineFragment")) {

                    if (mList != null && mList.size() > 0) {
                        AddressItemBean mAddressBean = mList.get(position);
                        String area = AddressSelectUtils.initData(mContext, mAddressBean.getProvince(), mAddressBean.getCity(), mAddressBean.getDistrict());
                        Intent intent = new Intent();
                        intent.putExtra("address_id", mAddressBean.getAddress_id());
                        intent.putExtra("address_name", mAddressBean.getConsignee());
                        intent.putExtra("address_mobile", mAddressBean.getMobile());
                        intent.putExtra("address_address", area + " " + mAddressBean.getAddress());
                        setResult(ConstantManager.APPLYHELP_FORRESULT_ADDRESS, intent);
                        doFinish();
                    }
                }
            }
        });
    }

    @Override
    public void initDataAfter() {

    }


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

    List<AddressItemBean> mList;
    AddressListAdapter mAdapter;

    @Override
    public void ListSuccess(AddressListDataBean responseBean) {

        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            AddressAfterBean mAddressAfterBean = responseBean.getData();
            if (mAddressAfterBean != null) {
                isCanEdit = mAddressAfterBean.getIsCanEdit();
                mList = mAddressAfterBean.getAddressList();
                if (mList == null) {
                    mList = new ArrayList<>();
                }
                Log.e(TAG, "mList.size  " + mList.size());
                mAdapter = new AddressListAdapter(mContext, mList, mHandler, isCanEdit);
                dateListview.setAdapter(mAdapter);
            }
            ListUtils.getInstance().setNotDateViewL(mAdapter, layoutNotdate);
        }
    }

    @Override
    public void AddSuccess(AddressListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast("修改成功");
            mPresent.setListViewData(user_id, receive_user_id);
        }
    }

    @Override
    public void delSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getData());
            mPresent.setListViewData(user_id, receive_user_id);
        }
    }

    @Override
    public void ListError() {
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int position = msg.arg1;
            String address_id = (String) msg.obj;
            switch (msg.what) {
                case ConstantManager.ADDRESS_HANDLE_SETMOREN:
                    mPresent.setDefaultAddress(user_id, address_id);
                    break;
                case ConstantManager.ADDRESS_HANDLE_DEL:
                    mPresent.delAddress(user_id, address_id);
                    break;
                case ConstantManager.ADDRESS_HANDLE_EDIT:
                    Intent intent = new Intent(mContext, AddressEditActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("province", mList.get(position).getProvince());
                    intent.putExtra("city", mList.get(position).getCity());
                    intent.putExtra("district", mList.get(position).getDistrict());
                    intent.putExtra("address_id", mList.get(position).getAddress_id());
                    intent.putExtra("consignee", mList.get(position).getConsignee());
                    intent.putExtra("address", mList.get(position).getAddress());
                    intent.putExtra("mobile", mList.get(position).getMobile());
                    intent.putExtra("is_default", mList.get(position).getIs_default());

                    Log.e("ResponseBody", "" + mList.get(position).toString());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        mPresent.setListViewData(user_id, receive_user_id);
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
