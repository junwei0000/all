package com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.adapter.ActionListAdapter;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataListBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionItemBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.OtherUserInfoDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressSelectUtils;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.CalendarActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 行动详情
 */
public class ActionDetailActivity extends BaseActivityMVP<ApplyHelpContract.View, ApplyHelpPresenterImp<ApplyHelpContract.View>> implements ApplyHelpContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_engry)
    TextView tvEngry;
    @BindView(R.id.tv_apply)
    TextView tvApply;
    @BindView(R.id.mBridgeWebView)
    BridgeWebView mBridgeWebView;
    private String user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_apply:
                //扩展类型 0：通用（无特殊扩展） 1：平安行动-资料填写 2：能量配-属性选择
                String extend_type = mActionItemBean.getExtend_type();
                if (!TextUtils.isEmpty(extend_type) && extend_type.equals("1")) {
                    showSafeActionWindow();
                } else if (!TextUtils.isEmpty(extend_type) && extend_type.equals("2")) {
                    showEnergyWindow();
                } else {
                    applyHelp("", "");
                }
                break;
        }
    }

    private void applyHelp(String action_safety_id, String extend_info) {
        Intent intent = new Intent(mContext, ApplyHelpActivity.class);
        intent.putExtra("skipType", ConstantManager.skipType_OPENREDACTION);
        intent.putExtra("action_id", mActionItemBean.getAction_id());
        intent.putExtra("actionname", mActionItemBean.getName2());
        intent.putExtra("activity_id", mActionItemBean.getActivity_id());
        intent.putExtra("extend_info", extend_info);
        intent.putExtra("action_safety_id", action_safety_id);

        intent.putExtra("type", mActionItemBean.getType());
        intent.putExtra("action_abilityprice", mActionItemBean.getAbility_price());
        intent.putExtra("actionname1", mActionItemBean.getName1());
        intent.putExtra("actionnimg", mActionItemBean.getImg());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_apply_actiondetail;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvApply.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        laod();
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("行动详情");
        user_id = UserUtils.getUserId(mContext);
        laod();
    }

    private void laod() {
        String goods_id = getIntent().getStringExtra("goods_id");
        mPresent.getActionDetail(user_id, goods_id);
    }

    @Override
    protected ApplyHelpPresenterImp<ApplyHelpContract.View> createPresent() {
        return new ApplyHelpPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void ActionListSuccess(ActionDataListBean responseBean) {

    }

    ActionItemBean mActionItemBean;

    @Override
    public void ActionDetailSuccess(ActionDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            mActionItemBean = responseBean.getData();
            String showT = "生命能量：<font color=\"#b58c1a\">" + mActionItemBean.getAbility_price() + "</font>";
            tvEngry.setText(Html.fromHtml(showT));
            ConfigUtils.getINSTANCE().showBridgeWebView(mBridgeWebView, mActionItemBean.getContent());
        }
    }

    @Override
    public void getNeedHelpNumberTaskSuccess(ActionDataBean responseBean) {

    }

    @Override
    public void PeopleListSuccess(PeopleDataBean responseBean) {

    }

    @Override
    public void getOtherUserInfoSuccess(OtherUserInfoDataBean responseBean) {

    }

    @Override
    public void PeopleSearchListSuccess(PeopleSearchDataBean responseBean) {

    }

    @Override
    public void ExplainListSuccess(ExplainDataBean responseBean) {

    }

    @Override
    public void applyActionSuccess(ActionDataBean responseBean) {

    }

    @Override
    public void ListError() {

    }

    @Override
    public void GetAddressListSuccess(AddressListDataBean responseBean) {

    }

    @Override
    public void actionSafetySuccess(ActionDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ActionItemBean mActionItemBean = responseBean.getData();
            if (mActionItemBean != null) {
                String action_safety_id = mActionItemBean.getAction_safety_id();
                applyHelp(action_safety_id, "");
            }
        }
    }

    @Override
    public void saveUserInfo(LoginDataBean responseBean) {

    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {

    }

    //*******************************平安行动*******************************************
    private MyDialog mSafeActionDialog;
    private TextView cb_man, cb_feman, tv_jiguan, tv_date;
    private LinearLayout layout_jiguan;
    SupplierEditText et_name, et_job, et_idcardnum, et_phone, et_mailbox;

    private AddressSelectUtils mAddressSelectUtils;
    private int user_sex = 1;//性别：0 女 1：男
    private String pid, cid, aid;
    String birthday;

    /**
     * 平安行动
     */
    private void showSafeActionWindow() {
        if (mSafeActionDialog == null) {
            mSafeActionDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_actionlist_safeaction);// 创建Dialog并设置样式主题
            mSafeActionDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mSafeActionDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            final EditText et = new EditText(mActivity);
            et.setHint("请输入");
            mSafeActionDialog.setView(et);//给对话框添加一个EditText输入文本框
            mSafeActionDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            });
            mSafeActionDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mSafeActionDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            mSafeActionDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = (LinearLayout) mSafeActionDialog.findViewById(R.id.layout_cancel);
            et_name = (SupplierEditText) mSafeActionDialog.findViewById(R.id.et_name);
            cb_man = (TextView) mSafeActionDialog.findViewById(R.id.cb_man);
            cb_feman = (TextView) mSafeActionDialog.findViewById(R.id.cb_feman);
            layout_jiguan = (LinearLayout) mSafeActionDialog.findViewById(R.id.layout_jiguan);
            tv_jiguan = (TextView) mSafeActionDialog.findViewById(R.id.tv_jiguan);
            et_job = (SupplierEditText) mSafeActionDialog.findViewById(R.id.et_job);

            LinearLayout layout_date = (LinearLayout) mSafeActionDialog.findViewById(R.id.layout_date);
            tv_date = (TextView) mSafeActionDialog.findViewById(R.id.tv_date);
            et_idcardnum = (SupplierEditText) mSafeActionDialog.findViewById(R.id.et_idcardnum);
            et_phone = (SupplierEditText) mSafeActionDialog.findViewById(R.id.et_phone);
            et_mailbox = (SupplierEditText) mSafeActionDialog.findViewById(R.id.et_mailbox);
            TextView btn_ok = (TextView) mSafeActionDialog.findViewById(R.id.btn_ok);
            cb_man.setOnClickListener(actionClickListener);
            cb_feman.setOnClickListener(actionClickListener);
            layout_jiguan.setOnClickListener(actionClickListener);
            layout_date.setOnClickListener(actionClickListener);
            layout_cancel.setOnClickListener(actionClickListener);
            btn_ok.setOnClickListener(actionClickListener);

            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_name, 20);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_job, 20);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_idcardnum, 20);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_phone, 11);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_mailbox, 40);
        } else {
            mSafeActionDialog.show();
        }
    }

    View.OnClickListener actionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_cancel:
                    mSafeActionDialog.dismiss();
                    break;
                case R.id.cb_man:
                    cb_man.setBackgroundResource(R.mipmap.check_true_red);
                    cb_feman.setBackgroundResource(R.mipmap.check_false);
                    user_sex = 1;
                    break;
                case R.id.cb_feman:
                    cb_man.setBackgroundResource(R.mipmap.check_false);
                    cb_feman.setBackgroundResource(R.mipmap.check_true_red);
                    user_sex = 0;
                    break;
                case R.id.layout_jiguan:
                    if (mAddressSelectUtils == null) {
                        mAddressSelectUtils = new AddressSelectUtils(mActivity, mHandler, SELECTADDRESS);
                    }
                    mAddressSelectUtils.onSelectShiQu();
                    break;
                case R.id.layout_date:
                    Intent intent = new Intent(mContext, CalendarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("showDate", birthday);
                    startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_DATE);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    break;
                case R.id.btn_ok:
                    String user_name = et_name.getText().toString().trim();
                    String occupation = et_job.getText().toString().trim();
                    String identity_number = et_idcardnum.getText().toString().trim();
                    String phone = et_phone.getText().toString().trim();
                    String email = et_mailbox.getText().toString().trim();
                    if (checkActionStatus(user_name, occupation, identity_number, phone, email, birthday)) {
                        mPresent.actionSafety(user_id, user_name, user_sex, pid, cid, aid, occupation
                                , birthday, identity_number, phone, email);
                        mSafeActionDialog.dismiss();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.USERINFO_FORRESULT_DATE) {
                birthday = data.getStringExtra("birthday");
                tv_date.setText(birthday);
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean checkActionStatus(String user_name, String occupation, String identity_number
            , String phone, String email, String birthday) {

        if (TextUtils.isEmpty(user_name)) {
            ToastUtils.showToast("请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(pid)) {
            ToastUtils.showToast("请选择籍贯");
            return false;
        }
        if (TextUtils.isEmpty(occupation)) {
            ToastUtils.showToast("请输入职业");
            return false;
        }
        if (TextUtils.isEmpty(birthday)) {
            ToastUtils.showToast("请选择出生日期");
            return false;
        }
        if (TextUtils.isEmpty(identity_number)) {
            ToastUtils.showToast("请输入身份证号");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请输入手机号");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            ToastUtils.showToast("请输入邮箱");
            return false;
        }
        return true;
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
                    String area = bundle.getString("area");
                    tv_jiguan.setText(area);
                    break;
                case VALUE:
                    twelve_chinese_zodiac = bundle.getInt("selectindex") + 1;
                    String cont = bundle.getString("cont");
                    tv_shengxiao.setText(cont);
                    break;
            }
        }
    };
    //*******************************能量配********************************************
    MyDialog selectDialog;
    String colour = "1";//colour：颜色  1 蓝色 ;2 粉色
    int twelve_chinese_zodiac = 1;
    TextView tv_shengxiao;
    TextView cb_blue, cb_fense, tv_blue, tv_fense;
    ValueSelectUtils mValueSelectUtils;

    /**
     * 能量配
     */
    private void showEnergyWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_actionlist_energy);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = (LinearLayout) selectDialog.findViewById(R.id.layout_cancel);
            cb_blue = (TextView) selectDialog.findViewById(R.id.cb_blue);
            cb_fense = (TextView) selectDialog.findViewById(R.id.cb_fense);
            tv_blue = (TextView) selectDialog.findViewById(R.id.tv_blue);
            tv_fense = (TextView) selectDialog.findViewById(R.id.tv_fense);
            LinearLayout layout_shengxiao = (LinearLayout) selectDialog.findViewById(R.id.layout_shengxiao);
            tv_shengxiao = (TextView) selectDialog.findViewById(R.id.tv_shengxiao);
            TextView btn_ok = (TextView) selectDialog.findViewById(R.id.btn_ok);
            layout_cancel.setOnClickListener(peiClickListener);
            cb_blue.setOnClickListener(peiClickListener);
            tv_blue.setOnClickListener(peiClickListener);
            cb_fense.setOnClickListener(peiClickListener);
            tv_fense.setOnClickListener(peiClickListener);
            layout_shengxiao.setOnClickListener(peiClickListener);
            btn_ok.setOnClickListener(peiClickListener);
        } else {
            selectDialog.show();
        }
    }

    View.OnClickListener peiClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.layout_cancel:
                    selectDialog.dismiss();
                    break;
                case R.id.cb_blue:
                case R.id.tv_blue:
                    colour = "1";
                    cb_blue.setBackgroundResource(R.mipmap.check_true_red);
                    cb_fense.setBackgroundResource(R.mipmap.check_false);
                    break;
                case R.id.cb_fense:
                case R.id.tv_fense:
                    colour = "2";
                    cb_blue.setBackgroundResource(R.mipmap.check_false);
                    cb_fense.setBackgroundResource(R.mipmap.check_true_red);
                    break;

                case R.id.layout_shengxiao:
                    if (mValueSelectUtils == null) {
                        mValueSelectUtils = new ValueSelectUtils(mActivity, mHandler, VALUE);
                    }
                    final String[] hours = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
                    mValueSelectUtils.showDialog(hours, "");
                    break;
                case R.id.btn_ok:
                    selectDialog.dismiss();
                    String extend_info = "";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("colour", "" + colour);
                        jsonObject.put("twelve_chinese_zodiac", "" + twelve_chinese_zodiac);
                        extend_info = jsonObject.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    doFinish();
                    applyHelp("", extend_info);
                    break;

            }

        }
    };

    //***************************************************************************

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
