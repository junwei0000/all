package com.longcheng.lifecareplan.modular.helpwith.myfamily.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ValueSelectUtils;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.ItemBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.MyFamilyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.bean.RelationListDataBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressSelectUtils;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.CalendarActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.nanchen.calendarview.LunarSolarConverter;

import java.util.List;

import butterknife.BindView;

/**
 * 添加
 */
public class AddFamilyActivity extends BaseActivityMVP<MyContract.View, MyPresenterImp<MyContract.View>> implements MyContract.View {


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
    @BindView(R.id.add_tv_foottitle)
    TextView addTvFoottitle;
    @BindView(R.id.add_tv_foot)
    TextView addTvFoot;
    @BindView(R.id.add_relat_foot)
    RelativeLayout addRelatFoot;
    @BindView(R.id.add_tv_datetitle)
    TextView addTvDatetitle;
    @BindView(R.id.add_tv_date)
    TextView addTvDate;
    @BindView(R.id.add_relat_date)
    RelativeLayout addRelatDate;
    @BindView(R.id.add_et_phone)
    EditText addEtPhone;
    @BindView(R.id.add_tv_addresstitle)
    TextView addTvAddresstitle;
    @BindView(R.id.add_tv_address)
    TextView addTvAddress;
    @BindView(R.id.add_relat_address)
    RelativeLayout addRelatAddress;
    @BindView(R.id.btn_save)
    TextView btnSave;

    private String user_id;
    private String pid, cid, aid, area;
    private String user_name, call_user, relationship, birthday, phone;
    private List<ItemBean> relationList;
    String[] values;
    AddressSelectUtils mAddressSelectUtils;
    ValueSelectUtils mValueSelectUtils;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.add_relat_foot:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                if (mValueSelectUtils == null) {
                    mValueSelectUtils = new ValueSelectUtils(mActivity, mHandler, VALUE);
                }
                if (values != null && values.length > 0) {
                    mValueSelectUtils.showDialog(values, "选择关系");
                }
                break;
            case R.id.add_relat_date:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                Intent intent = new Intent(mContext, CalendarActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("showDate", birthday);
                startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_DATE);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, this);
                break;
            case R.id.add_relat_address:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                mAddressSelectUtils.onSelectShiQu();
                break;

            case R.id.btn_save:
                user_name = addEtName.getText().toString().trim();
                phone = addEtPhone.getText().toString().trim();
                if (checkStatus()) {
                    mPresent.addOrEditFamily(user_id,
                            "0", user_name, birthday, pid, cid, aid, phone, call_user, relationship);
                }
//                TestAdd();
                break;
        }
    }

    private void TestAdd() {
        String[] xing = {"段", "杨", "张", "吴", "李", "刘", "武", "齐","高","陈","何","贺","郑"};
        String[] ming = {"鸿", "辰", "运", "宇", "朗", "平", "鹏", "淼", "明", "皓", "栋", "昂","","芳","文"};
        String[] ming3 = {"运", "龙", "珹", "振", "高", "景", "鑫", "昌", "炫", "昆", "曜", "文","","芳"};
        for (int i = 0; i < 20; i++) {
            Handler handler = new Handler();
            int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    phone = "175000000" + (50 + finalI);
//                    phone = "183100000" + (15 + finalI);
                    int random = ConfigUtils.getINSTANCE().setRandom(xing.length);
                    int randomming = ConfigUtils.getINSTANCE().setRandom(ming.length);
                    int randomming3 = ConfigUtils.getINSTANCE().setRandom(ming3.length);
                    user_name = xing[random] + ming[randomming] + ming3[randomming3];
                    birthday = "2019-09-08";
                    pid = "330000";
                    cid = "330100";
                    aid = "330102";
                    call_user = "其他";
                    relationship = "10";
                    mPresent.addOrEditFamily(user_id,
                            "0", user_name, birthday, pid, cid, aid, phone, call_user, relationship);
                }
            }, 0);

        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.myfamily_add;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView(View view) {
        pageTopTvName.setText("添加家人");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        addRelatFoot.setOnClickListener(this);
        addRelatDate.setOnClickListener(this);
        addRelatAddress.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(addEtName, 20);
    }


    @Override
    public void initDataAfter() {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mAddressSelectUtils = new AddressSelectUtils(mActivity, mHandler, SELECTADDRESS);
        mPresent.getFamilyRelation(user_id);
    }

    private final int VALUE = 2;
    private final int SELECTADDRESS = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case SELECTADDRESS:
                    //上传出生地
                    pid = bundle.getString("pid");
                    cid = bundle.getString("cid");
                    aid = bundle.getString("aid");
                    area = bundle.getString("area");
                    addTvAddress.setText(area);
                    break;
                case VALUE:
                    int selectindex = bundle.getInt("selectindex");
                    if (relationList != null && relationList.size() > 0) {
                        relationship = relationList.get(selectindex).getId();
                    }
                    call_user = bundle.getString("cont");
                    addTvFoot.setText(call_user);
                    break;
            }
        }
    };


    @Override
    protected MyPresenterImp<MyContract.View> createPresent() {
        return new MyPresenterImp<>(mContext, this);
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
    public void ListSuccess(MyFamilyListDataBean responseBean) {

    }

    @Override
    public void DelSuccess(EditDataBean responseBean) {

    }

    @Override
    public void GetFamilyRelationSuccess(RelationListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            relationList = responseBean.getData();
            if (relationList != null && relationList.size() > 0) {
                values = new String[relationList.size()];
                for (int i = 0; i < relationList.size(); i++) {
                    values[i] = relationList.get(i).getRelation();
                }
            }
        }
    }

    @Override
    public void GetFamilyEditInfoSuccess(MyFamilyDataBean responseBean) {

    }

    @Override
    public void addOrEditSuccess(EditListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
            doFinish();
        }
    }

    @Override
    public void ListError() {

    }

    private boolean checkStatus() {
        if (TextUtils.isEmpty(user_name)) {
            ToastUtils.showToast("请输入家人的姓名");
            return false;
        }
        if (TextUtils.isEmpty(call_user)) {
            ToastUtils.showToast("请选择您与家人的关系");
            return false;
        }
        if (TextUtils.isEmpty(birthday)) {
            ToastUtils.showToast("请选择家人的出生日期");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请输入家人的电话号码");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请输入手机号码");
            return false;
        }
        if (TextUtils.isEmpty(pid)) {
            ToastUtils.showToast("请选择家人的出生地");
            return false;
        }
        return true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.USERINFO_FORRESULT_DATE) {
                birthday = data.getStringExtra("birthday");
                if (!TextUtils.isEmpty(birthday)) {
                    String yinliDateShow = new LunarSolarConverter().LunarBlockLetter(birthday);
                    addTvDate.setText(birthday + "\n" + yinliDateShow);
                }
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
