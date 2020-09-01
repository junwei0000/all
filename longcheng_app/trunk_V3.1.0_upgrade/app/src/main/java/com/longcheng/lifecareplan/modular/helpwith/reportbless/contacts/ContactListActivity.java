package com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.gson.Gson;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.bean.contactbean.PhoneBean;
import com.longcheng.lifecareplan.bean.contactbean.PhoneBeans;
import com.longcheng.lifecareplan.bean.contactbean.SelectPhone;
import com.longcheng.lifecareplan.bean.fupackage.FuBaoDetailsListBean;
import com.longcheng.lifecareplan.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.adapter.ContactAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.adapter.PyAdapter;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.pinyinutil.GetPhoneUtils;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.pinyinutil.PyEntity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.SendPayUtils;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.FuPacakageDetails;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.IntoBagFuActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter.FuBaoDetailsAdapter;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.utils.sidebarutils.SideBar;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.mylhyl.circledialog.CircleDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ContactListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.contact_recycle)
    RecyclerView contactRecycle;
    @BindView(R.id.contact_side)
    SideBar contactSide;
    @BindView(R.id.contact_letter)
    TextView contactLetter;
    @BindView(R.id.contact_name)
    TextView contactName;
    @BindView(R.id.contact_num)
    TextView contactNum;
    @BindView(R.id.btn_saveuser)
    TextView btnSaveuser;
    @BindView(R.id.contact_numshow)
    TextView contentNumshow;

    private List<PhoneBean> phoneBeans = new ArrayList<>();


    private ArrayList<Map<String, String>> selectphone = new ArrayList<>();

    private GetPhoneUtils getPhoneUtils = null;

    private ContactAdapter contactAdapter = null;

    private boolean isadd = false;
    private boolean isshow = false;

    private static final int REQUEST_CODE_SETTING = 1;

    int super_ability;
    int isHaveAddress;
    SendPayUtils mSendPayUtils;
    private int index = 0; // 选中个数

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
            case R.id.pagetop_iv_left:
                doFinish();
                break;
            case R.id.btn_saveuser:
                ///  确定按钮
                if (index > 0) {
                    if (mSendPayUtils == null) {
                        mSendPayUtils = new SendPayUtils(mActivity);
                    }
                    mSendPayUtils.init(super_ability * index, phoneBeans);
                    mSendPayUtils.setPayDialog();

                } else {
                    ToastUtils.showToast("请选择送福人");
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
        return R.layout.activity_contact_list;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("通讯录");
        setOrChangeTranslucentColor(toolbar, null);
    }


    @Override
    public void setListener() {
        pagetopIvLeft.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        btnSaveuser.setOnClickListener(this);
        contactName.setOnClickListener(this);
        pagetopIvLeft.setOnClickListener(this);
        contactSide.addIndex("#", contactSide.indexes.size());
    }

    @Override
    public void initDataAfter() {

        getPhoneUtils = new GetPhoneUtils();

        super_ability = HelpWithFragmentNew.blessBagVal;
        isHaveAddress = HelpWithFragmentNew.isHaveAddress;
        contentNumshow.setText(new StringBuffer().append("" + super_ability).append("X").append(index).toString());

        if (isHaveAddress == 1) {
            // 读取服务器数据
            getContactListList();
        } else {
            // 读取本地数据
            PermissionCheck(Permission.READ_CONTACTS);
        }


    }

    LinearLayoutManager manager;

    private void setDateView() {
        contactAdapter = new ContactAdapter(ContactListActivity.this, phoneBeans);
        manager = new LinearLayoutManager(ContactListActivity.this);
        contactRecycle.setLayoutManager(manager);
        contactRecycle.setAdapter(contactAdapter);
        contactSide.setOnLetterChangeListener(new SideBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                contactLetter.setVisibility(View.VISIBLE);
                contactLetter.setText(letter);
                int position = contactAdapter.getLetterPosition(letter);
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }

            @Override
            public void onReset() {
                contactLetter.setVisibility(View.GONE);
            }
        });
        contactAdapter.setOnItemClickListener(new PyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PyEntity entity, int position) {
                final PhoneBean phoneBean = (PhoneBean) entity;
                int is_new = phoneBean.getIs_new();//1没有注册平台
                if (is_new == 1) {
                    itemClick(phoneBean);
                } else {
                    ToastUtils.showToast("已是平台用户");
                }
            }
        });
    }

    private void PermissionCheck(@PermissionDef String... permissions) {

        List<String> permissionNames = Permission.transformText(mContext, permissions);

        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        initData();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(mActivity, permissions)) {
                            setPermissionDialog(0);
                        }
                    }
                })
                .start();

    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this).runtime().setting().start(REQUEST_CODE_SETTING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                boolean isget = AndPermission.hasPermissions(mActivity, Permission.READ_CONTACTS);
                LogUtils.v("TAG", "是否获取权限" + isget);
                if (isget) {
                    // 获取权限 刷新通讯录数据
                    initData();
                } else {
                    // 为获取权限 关闭页面
                    LogUtils.v("TAG", "是否获取权限" + isget);
                    doFinish();
                }
                break;
            }
        }
    }

    private void initData() {
        try {
            phoneBeans = getPhoneUtils.getPhoneNumberFromMobile(ContactListActivity.this, isadd, isshow);
            if (phoneBeans != null && phoneBeans.size() > 0) {
                SaveList();
                setDateView();
            }
        } catch (Exception e) {
            new CircleDialog.Builder(this).setText(getString(R.string.contact_promission))
                    .setNegative(getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setPositive(getString(R.string.confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    }).show();
        }
    }

    private List<PhoneBean> getSearchList(String searchText) {
        List<PhoneBean> searchList = new ArrayList<>();
        PhoneBean phoneBean = null;
        for (int i = 0; i < phoneBeans.size(); i++) {
            phoneBean = phoneBeans.get(i);
            if (phoneBean.getName().contains(searchText)) {
                searchList.add(phoneBean);
            } else if (phoneBean.getPhone().contains(searchText)) {
                searchList.add(phoneBean);
            } else if (phoneBean.getPinyin().toLowerCase().contains(searchText.toLowerCase())) {
                searchList.add(phoneBean);
            }
        }
        return searchList;
    }


    private void itemClick(PhoneBean phoneBean) {
        if (phoneBean != null) {
            boolean isselect = phoneBean.isIsadd();
            if (!isselect) {
                index++;
                phoneBean.setIsadd(true);
            } else {
                phoneBean.setIsadd(false);
                index--;
            }
            contactNum.setText("" + index);
            contentNumshow.setText(new StringBuffer().append("" + super_ability).append("X").append(index).toString());
            setBtnBackground(index);
            contactAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showToast("");
        }
    }
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    public void getContactListList() {
        showDialog();
        Observable<PhoneBeans> observable = Api.getInstance().service.getContentUser(
                UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PhoneBeans>() {
                    @Override
                    public void accept(PhoneBeans responseBean) throws Exception {
                        dismissDialog();
                        String status_ = responseBean.getStatus();
                        if (status_.equals("400")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status_.equals("200")) {
                            phoneBeans.clear();
                            phoneBeans = responseBean.getData();
                            int size = phoneBeans == null ? 0 : phoneBeans.size();
                            if (size == 0) {
                                PermissionCheck(Permission.READ_CONTACTS);
                            } else {
                                setDateView();
                            }

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(R.string.net_tishi);
                        dismissDialog();
                    }
                });
    }

    public void SaveList() {
        showDialog();
        String phone_string = getPhoneMsg(phoneBeans);
        Observable<ResponseBean> observable = Api.getInstance().service.saveContentUser(
                UserUtils.getUserId(mContext), phone_string, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        String status_ = responseBean.getStatus();
                        if (status_.equals("400")) {
                            ToastUtils.showToast(responseBean.getMsg());
                        } else if (status_.equals("200")) {
                            // 发送成功，获取数据
                            getContactListList();
                            HelpWithFragmentNew.isHaveAddress = 1;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast(R.string.net_tishi);
                        dismissDialog();
                    }
                });
    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            btnSaveuser.setEnabled(true);
        } else {
            btnSaveuser.setEnabled(false);
        }
    }

    MyDialog permissionDialog;

    TextView tv_sure, tv_title;

    public void setPermissionDialog(int type) {
        if (permissionDialog == null) {
            permissionDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_tixiandel);// 创建Dialog并设置样式主题
            permissionDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = permissionDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            permissionDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = permissionDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            permissionDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_off = permissionDialog.findViewById(R.id.tv_off);
            tv_title = permissionDialog.findViewById(R.id.tv_title);
            tv_sure = permissionDialog.findViewById(R.id.tv_sure);

            tv_title.setText(getResources().getString(R.string.permission_diloag_need));

            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    permissionDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    permissionDialog.dismiss();
                    setPermission();
                }
            });
        } else {
            permissionDialog.show();
        }
    }

    /***
     * 获取选中用户手机号用户名
     * @return
     */
    private String getPhoneMsg(List<PhoneBean> phoneBeans) {
        String usephone = "";
        selectphone.clear();
        for (PhoneBean phoneBean : phoneBeans) {
            String phone = phoneBean.getPhone().replace(" ", "");
            if (!TextUtils.isEmpty(phone)) {
                Map<String, String> maps = new HashMap<>();
                maps.put("name", phoneBean.getName());
                maps.put("phone", phone);
                selectphone.add(maps);
            }
        }
        usephone = new Gson().toJson(selectphone).toString();
        Log.v("getPhoneMsg", usephone);
        return usephone;

    }


}
