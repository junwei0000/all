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
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionDataListBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ActionItemBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.OtherUserInfoDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleItemBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.LoginThirdSetPwActivity;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressSelectUtils;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressAfterBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressItemBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.CalendarActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.Utils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 申请互祝
 */
public class ApplyHelpActivity extends BaseActivityMVP<ApplyHelpContract.View, ApplyHelpPresenterImp<ApplyHelpContract.View>> implements ApplyHelpContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.relat_action)
    LinearLayout relatAction;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.relat_people)
    LinearLayout relatPeople;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_addressname)
    TextView tv_addressname;
    @BindView(R.id.relat_address)
    LinearLayout relatAddress;
    @BindView(R.id.tv_explaintitle)
    TextView tv_explaintitle;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.relat_explain)
    LinearLayout relatExplain;
    @BindView(R.id.btn_save)
    TextView btnSave;
    @BindView(R.id.layout_address)
    LinearLayout layout_address;
    @BindView(R.id.iv_actionimg)
    ImageView ivActionimg;
    @BindView(R.id.tv_action1)
    TextView tvAction1;
    @BindView(R.id.tv_actionengry)
    TextView tvActionengry;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    private String action_id, actionname, activity_id;
    private String peopleid, peoplename;
    private String describe;
    private String user_id;
    private String address_id;
    private String action_safety_id;
    private String extend_info = "";
    ;
    private String mutual_help_apply_id;
    private String qiming_user_id = "0";

    /**
     *
     */
    private String skiptype = "";


    int type;//2 虚拟

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.relat_action:
                intent = new Intent(mContext, ActionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_ACTION);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.relat_people:
                if (!TextUtils.isEmpty(skiptype) && skiptype.equals("Doctor_applyHelp")) {
                    doFinish();
                } else {
                    intent = new Intent(mContext, PeopleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_PEOPLE);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
                break;
            case R.id.relat_address:
                if (!TextUtils.isEmpty(peopleid)) {
                    intent = new Intent(mContext, AddressListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("receive_user_id", peopleid);
                    intent.putExtra("skipType", "LifeStyleApplyHelpActivity");
                    startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_ADDRESS);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                } else {
                    ToastUtils.showToast("请选择接福人");
                }
                break;
            case R.id.relat_explain:
                if (!TextUtils.isEmpty(action_id)) {
                    intent = new Intent(mContext, ExplainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("action_id", action_id);
                    intent.putExtra("describe", describe);
                    startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_EXPLAIN);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                } else {
                    ToastUtils.showToast("请选择互祝行动");
                }
                break;
            case R.id.btn_save:
                if (!showNotCHODialog()) {
                    if (btnClickStatus) {
                        mPresent.applyAction(user_id, action_id,
                                peopleid, address_id, describe, action_safety_id, extend_info, qiming_user_id);
                    }
                }
                break;
        }
    }

    boolean btnClickStatus = false;

    private void setBtnBg() {
        showNotCHODialog();
        btnClickStatus = false;
        if (!TextUtils.isEmpty(action_id) && !TextUtils.isEmpty(peopleid) &&
                !TextUtils.isEmpty(describe)) {
            if (type == 2 || (!TextUtils.isEmpty(address_id) && type != 2)) {
                btnClickStatus = true;
                btnSave.setBackgroundResource(R.drawable.corners_bg_helpbtn);
            } else {
                btnSave.setBackgroundResource(R.drawable.corners_bg_logingray);
            }
        } else {
            btnSave.setBackgroundResource(R.drawable.corners_bg_logingray);
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_applynew;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        relatAction.setOnClickListener(this);
        relatPeople.setOnClickListener(this);
        relatAddress.setOnClickListener(this);
        relatExplain.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        showRedSkipData(intent);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("申请互祝");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getNeedHelpNumberTask(user_id);
        skiptype = getIntent().getStringExtra("skiptype");
        if (!TextUtils.isEmpty(skiptype) && skiptype.equals("Doctor_applyHelp")) {
            peopleid = getIntent().getStringExtra("other_user_id");
            mPresent.getOtherUserInfo(user_id, peopleid);
        } else {
            mPresent.getPeopleList(user_id);
        }
        setBtnBg();
        showRedSkipData(getIntent());
    }

    private boolean showNotCHODialog() {
        boolean stauts = false;
        String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
        //1:是  ；0：不是
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
        } else {
            stauts = true;
            showNotCHOWindow();
        }
        return stauts;
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
    public void getNeedHelpNumberTaskSuccess(ActionDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ActionItemBean mBean = responseBean.getData();
            if (mBean != null) {
                //状态 0：没有要做的任务 ;1， 有要去互祝的任务; 2，有未读的任务
                String apply_status = mBean.getApply_status();
                need_help_number = mBean.getRemain_number();
                redirectMsgId = mBean.getMsg_id();
                mutual_help_apply_id = mBean.getMutual_help_apply_id();
                if (!apply_status.equals("0")) {
                    showPopupWindow();
                }
            }

        }
    }

    @Override
    public void ActionListSuccess(ActionDataListBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            List<ActionItemBean> mList = (List<ActionItemBean>) responseBean.getData();
            if (mList != null && mList.size() > 0) {
                for (ActionItemBean mActionItemBean : mList) {
                    if (action_goods_id.equals(mActionItemBean.getId())) {
                        int type = mActionItemBean.getType();
                        if (type == 2) {//虚拟的
                            relatAddress.setVisibility(View.GONE);
                        } else {
                            relatAddress.setVisibility(View.VISIBLE);
                        }
                        action_id = mActionItemBean.getId();
                        actionname = mActionItemBean.getName2();
                        activity_id = mActionItemBean.getActivity_id();
                        tvAction.setText(actionname);

                        String price = mActionItemBean.getAbility_price();
                        price = PriceUtils.getInstance().seePrice(price);
                        tvActionengry.setText(price + "生命能量");
                        tvAction1.setText(mActionItemBean.getName1());
                        GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, mActionItemBean.getImg(), ivActionimg, ConstantManager.image_angle);
                        ivActionimg.setVisibility(View.VISIBLE);
                        tvAction1.setVisibility(View.VISIBLE);
                        tvAction.setVisibility(View.VISIBLE);
                        tvActionengry.setVisibility(View.VISIBLE);

                        mPresent.getExplainList(user_id, action_id);
                        break;
                    }

                }
            }

        }
    }

    @Override
    public void ActionDetailSuccess(ActionDataBean responseBean) {

    }

    @Override
    public void PeopleSearchListSuccess(PeopleSearchDataBean responseBean) {

    }

    @Override
    public void PeopleListSuccess(PeopleDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            PeopleAfterBean mPeopleAfterBean = responseBean.getData();
            if (mPeopleAfterBean != null) {
                List<PeopleItemBean> mList = mPeopleAfterBean.getFamilyList();
                if (mList != null && mList.size() > 0) {
                    for (PeopleItemBean mPeopleItemBean : mList) {
                        String allow_help = mPeopleItemBean.getAllow_help();
                        //是否可以互助0：不允许 1：允许（非cho不允许）
                        if (!TextUtils.isEmpty(allow_help) && allow_help.equals("1")) {
                            peopleid = mPeopleItemBean.getUser_id();
                            peoplename = mPeopleItemBean.getUser_name();
                            tvPeople.setText(peoplename);
                            String avatar = mPeopleItemBean.getAvatar();
                            GlideDownLoadImage.getInstance().loadCircleImage(mContext, avatar, ivThumb);
                            mPresent.setAddressList(user_id, peopleid);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void getOtherUserInfoSuccess(OtherUserInfoDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            PeopleItemBean mPeopleItemBean = responseBean.getData();
            peopleid = mPeopleItemBean.getUser_id();
            peoplename = mPeopleItemBean.getUser_name();
            tvPeople.setText(peoplename);
            String avatar = mPeopleItemBean.getAvatar();
            GlideDownLoadImage.getInstance().loadCircleImage(mContext, avatar, ivThumb);
            mPresent.setAddressList(user_id, peopleid);
        }
    }

    @Override
    public void GetAddressListSuccess(AddressListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            AddressAfterBean mAddressAfterBean = responseBean.getData();
            if (mAddressAfterBean != null) {
                List<AddressItemBean> mList = mAddressAfterBean.getAddressList();
                if (mList != null && mList.size() > 0) {
                    int defalutindex = 0;
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).getIs_default().equals("1")) {
                            defalutindex = i;
                            break;
                        }
                    }
                    AddressItemBean mAddressBean = mList.get(defalutindex);
                    String area = AddressSelectUtils.initData(mContext, mAddressBean.getProvince(), mAddressBean.getCity(), mAddressBean.getDistrict());
                    address_id = mAddressBean.getAddress_id();
                    String address_name = mAddressBean.getConsignee();
                    String phone = mAddressBean.getMobile();
                    String address_address = mAddressBean.getAddress();
                    layout_address.setVisibility(View.VISIBLE);
                    tv_addressname.setText(address_name);
                    if (!TextUtils.isEmpty(address_name) && address_name.length() >= 11) {
                        address_name = address_name.substring(0, 10) + "...";
                    }
                    if (!TextUtils.isEmpty(phone) && phone.length() >= 11) {
                        phone = phone.substring(0, 3) + "****" + phone.substring(7);
                    }
                    tv_addressname.setText(address_name + "     " + phone);
                    tvAddress.setText(area + " " + address_address);
                } else {
                    address_id = "";
                    tv_addressname.setText("");
                    tvAddress.setText("");
                    layout_address.setVisibility(View.GONE);
                }
                setBtnBg();
            }
        }
    }

    @Override
    public void ExplainListSuccess(ExplainDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ExplainAfterBean mPeopleAfterBean = responseBean.getData();
            if (mPeopleAfterBean != null) {
                List<String> mList = mPeopleAfterBean.getManifesto();
                if (mList != null && mList.size() > 0) {
                    int random = ConfigUtils.getINSTANCE().setRandom(mList.size() - 1);
                    describe = mList.get(random);
                    tvExplain.setText(describe);
                    if (activity_id.equals("0")) {
                        tv_explaintitle.setText("互祝说明");
                        tv_explaintitle.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
                        tvExplain.setTextColor(getResources().getColor(R.color.text_noclick_color));
                    } else {
                        tv_explaintitle.setText("我代言\u3000");
                        tv_explaintitle.setTextColor(getResources().getColor(R.color.red));
                        tvExplain.setTextColor(getResources().getColor(R.color.red));
                    }
                    setBtnBg();
                }
            }

        }
    }

    @Override
    public void applyActionSuccess(ActionDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            String mag = responseBean.getMsg();
            Log.e("applyActionSuccess", "applyActionSuccess=" + mag);
            ToastUtils.showToast(mag);
        } else if (status.equals("200")) {
            ActionItemBean mActionItemBean = responseBean.getData();
            if (mActionItemBean != null) {
                qiming_user_id = "0";
                need_help_number = mActionItemBean.getNeed_help_number();
                redirectMsgId = mActionItemBean.getRedirectMsgId();
                showPopupWindow();
            }

        }
    }

    @Override
    public void actionSafetySuccess(ActionDataBean responseBean) {

    }

    @Override
    public void saveUserInfo(LoginDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            mNotCHODialog.dismiss();
        }
    }

    @Override
    public void getCodeSuccess(SendCodeBean responseBean) {
        codeSendingStatus = false;
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            Message msg = new Message();
            msg.what = Daojishistart;
            mcodeHandler.sendMessage(msg);
            msg = null;
        }
    }

    @Override
    public void ListError() {

    }


    MyDialog selectDialog;
    String need_help_number;
    /**
     * 任务ID
     */
    String redirectMsgId;
    TextView tv_cont1, tv_cont2, tv_cont3, btn_ok;

    /**
     * 申请弹层
     */
    private void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_applyhelp);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            tv_cont1 = (TextView) selectDialog.findViewById(R.id.tv_cont1);
            tv_cont2 = (TextView) selectDialog.findViewById(R.id.tv_cont2);
            tv_cont3 = (TextView) selectDialog.findViewById(R.id.tv_cont3);
            btn_ok = (TextView) selectDialog.findViewById(R.id.btn_ok);
            initView();
            selectDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    doFinish();
                    return true;
                }
            });
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (!need_help_number.equals("0")) {
                        //申请成功后做任务跳转msgid   0：跳转到列表页   非0：跳转到行动详情页
                        if (redirectMsgId.equals("0")) {
                            intent = new Intent(mContext, HelpWithEnergyActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                        } else {
                            intent = new Intent(mContext, DetailActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("msg_id", redirectMsgId);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                        }
                    } else {
                        //查看任务详情页面,并给后台传值已读
                        mPresent.setTaskRead(user_id, mutual_help_apply_id);
                        intent = new Intent(mContext, DetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("msg_id", redirectMsgId);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    }
                    selectDialog.dismiss();
                }
            });
        } else {
            initView();
            selectDialog.show();
        }
    }

    private void initView() {
        if (!TextUtils.isEmpty(need_help_number) && !need_help_number.equals("0")) {
            tv_cont1.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            tv_cont2.setTextColor(getResources().getColor(R.color.text_noclick_color));
            String showT = "请您送出<font color=\"#db4065\">" + need_help_number + "</font>次祝福";
            tv_cont1.setText(Html.fromHtml(showT));
            tv_cont2.setVisibility(View.VISIBLE);
            tv_cont3.setVisibility(View.GONE);
            tv_cont2.setText("即可获得更多人的祝福");
            btn_ok.setText("点击送出祝福");
        } else {
            tv_cont2.setVisibility(View.GONE);
            tv_cont3.setVisibility(View.VISIBLE);
            tv_cont1.setText("感恩您的奉献");
            tv_cont3.setText("恭喜您，申请成功！");
            tv_cont1.setTextColor(getResources().getColor(R.color.color_db4065));
            tv_cont3.setTextColor(getResources().getColor(R.color.color_db4065));
            btn_ok.setText("知道了，点击查看");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.USERINFO_FORRESULT_DATE) {
                birthday = data.getStringExtra("birthday");
                tv_date.setText(birthday);
            } else {
                if (resultCode == ConstantManager.APPLYHELP_FORRESULT_ACTION) {
                    actionListBackData(data);
                } else if (resultCode == ConstantManager.APPLYHELP_FORRESULT_PEOPLE) {
                    peopleid = data.getStringExtra("peopleid");
                    peoplename = data.getStringExtra("peoplename");
                    String avatar = data.getStringExtra("avatar");
                    GlideDownLoadImage.getInstance().loadCircleImage(mContext, avatar, ivThumb);
                    tvPeople.setText(peoplename);
                    mPresent.setAddressList(user_id, peopleid);
                } else if (resultCode == ConstantManager.APPLYHELP_FORRESULT_ADDRESS) {
                    address_id = data.getStringExtra("address_id");
                    String address_name = data.getStringExtra("address_name");
                    String phone = data.getStringExtra("address_mobile");
                    String address_address = data.getStringExtra("address_address");
                    if (!TextUtils.isEmpty(address_name) && address_name.length() >= 11) {
                        address_name = address_name.substring(0, 10) + "...";
                    }
                    if (!TextUtils.isEmpty(phone) && phone.length() >= 11) {
                        phone = phone.substring(0, 3) + "****" + phone.substring(7);
                    }
                    tv_addressname.setText(address_name + "     " + phone);
                    tvAddress.setText(address_address);
                    layout_address.setVisibility(View.VISIBLE);
                } else if (resultCode == ConstantManager.APPLYHELP_FORRESULT_EXPLAIN) {
                    describe = data.getStringExtra("describe");
                    tvExplain.setText(describe);
                }
                setBtnBg();
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.getNeedHelpNumberTask(user_id);
    }

    String action_goods_id = "";

    /**
     * 红包跳转过来显示行动
     */
    private void showRedSkipData(Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            qiming_user_id = "0";
            String skipType = intent.getExtras().getString("skipType", "");
            if (skipType.equals(ConstantManager.skipType_OPENRED)) {
                action_goods_id = intent.getExtras().getString("action_goods_id", "");
                qiming_user_id = intent.getExtras().getString("qiming_user_id", "");
                mPresent.getActionList(user_id);
            } else if (skipType.equals(ConstantManager.skipType_OPENREDACTION)) {
                actionListBackData(intent);
            }
        }
    }

    private void actionListBackData(Intent data) {
        action_id = data.getStringExtra("action_id");
        actionname = data.getStringExtra("actionname");
        activity_id = data.getStringExtra("activity_id");
        extend_info = data.getStringExtra("extend_info");
        action_safety_id = data.getStringExtra("action_safety_id");
        tvAction.setText(actionname);

        type = data.getIntExtra("type", 0);
        if (type == 2) {//虚拟的
            relatAddress.setVisibility(View.GONE);
        } else {
            relatAddress.setVisibility(View.VISIBLE);
        }


        String Ability_price = data.getStringExtra("action_abilityprice");
        Ability_price = PriceUtils.getInstance().seePrice(Ability_price);
        String name1 = data.getStringExtra("actionname1");
        String img = data.getStringExtra("actionnimg");
        tvActionengry.setText(Ability_price + "生命能量");
        tvAction1.setText(name1);
        GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, img, ivActionimg, ConstantManager.image_angle);
        ivActionimg.setVisibility(View.VISIBLE);
        tvAction1.setVisibility(View.VISIBLE);
        tvAction.setVisibility(View.VISIBLE);
        tvActionengry.setVisibility(View.VISIBLE);


        describe = "";
        tvExplain.setText(describe);
        mPresent.getExplainList(user_id, action_id);
    }


    /**
     * ***********************************************
     */
    private MyDialog mNotCHODialog;
    private SupplierEditText et_name, et_phone, et_code, et_pw, et_pwnew;
    private TextView tv_getcode, tv_date, tv_address;
    private AddressSelectUtils mAddressSelectUtils;
    private String pid, cid, aid;
    private String birthday;

    /**
     * 不是cho
     */
    private void showNotCHOWindow() {
        if (mNotCHODialog == null) {
            mNotCHODialog = new MyDialog(this, R.style.dialog, R.layout.dialog_notcho_apply);// 创建Dialog并设置样式主题
            mNotCHODialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mNotCHODialog.getWindow();
            window.setGravity(Gravity.CENTER);
            final EditText et = new EditText(mActivity);
            et.setHint("请输入");
            mNotCHODialog.setView(et);//给对话框添加一个EditText输入文本框
            mNotCHODialog.setOnShowListener(new DialogInterface.OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            });
            mNotCHODialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mNotCHODialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            mNotCHODialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = (LinearLayout) mNotCHODialog.findViewById(R.id.layout_cancel);
            et_name = (SupplierEditText) mNotCHODialog.findViewById(R.id.et_name);
            et_phone = (SupplierEditText) mNotCHODialog.findViewById(R.id.et_phone);
            tv_getcode = (TextView) mNotCHODialog.findViewById(R.id.tv_getcode);
            et_code = (SupplierEditText) mNotCHODialog.findViewById(R.id.et_code);
            et_pw = (SupplierEditText) mNotCHODialog.findViewById(R.id.et_pw);
            et_pwnew = (SupplierEditText) mNotCHODialog.findViewById(R.id.et_pwnew);

            LinearLayout layout_date = (LinearLayout) mNotCHODialog.findViewById(R.id.layout_date);
            tv_date = (TextView) mNotCHODialog.findViewById(R.id.tv_date);

            LinearLayout layout_address = (LinearLayout) mNotCHODialog.findViewById(R.id.layout_address);
            tv_address = (TextView) mNotCHODialog.findViewById(R.id.tv_address);

            TextView btn_ok = (TextView) mNotCHODialog.findViewById(R.id.btn_ok);

            layout_cancel.setOnClickListener(actionClickListener);
            layout_date.setOnClickListener(actionClickListener);
            layout_address.setOnClickListener(actionClickListener);
            btn_ok.setOnClickListener(actionClickListener);
            tv_getcode.setOnClickListener(actionClickListener);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_name, 20);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_phone, 11);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_code, 6);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_pw, 20);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_pwnew, 20);
        } else {
            mNotCHODialog.show();
        }
        showInfo();
    }

    private void showInfo() {
        String user_name = (String) SharedPreferencesHelper.get(mContext, "user_name", "");
        String phone = (String) SharedPreferencesHelper.get(mContext, "phone", "");
        pid = (String) SharedPreferencesHelper.get(mContext, "pid", "");
        cid = (String) SharedPreferencesHelper.get(mContext, "cid", "");
        aid = (String) SharedPreferencesHelper.get(mContext, "aid", "");
        String area = (String) SharedPreferencesHelper.get(mContext, "area", "");
        birthday = (String) SharedPreferencesHelper.get(mContext, "birthday", "");

        et_name.setText(user_name);
        et_phone.setText(phone);
        if (!TextUtils.isEmpty(birthday) && !birthday.equals("0000-00-00")) {
            tv_date.setText(birthday);
        }
        tv_address.setText(area);
    }

    View.OnClickListener actionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layout_cancel:
                    mNotCHODialog.dismiss();
                    doFinish();
                    break;
                case R.id.tv_getcode:
                    String phoneNum = et_phone.getText().toString().trim();
                    if (Utils.isCheckPhone(phoneNum) && !codeSendingStatus) {
                        codeSendingStatus = true;
                        mPresent.pUseSendCode(phoneNum, "9");
                    }
                    break;

                case R.id.layout_address:
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
                    String phone = et_phone.getText().toString().trim();
                    String code = et_code.getText().toString().trim();
                    String pw = et_pw.getText().toString().trim();
                    String pwnew = et_pwnew.getText().toString().trim();
                    if (checkActionStatus(user_name, phone, code, pw, pwnew, birthday)) {
                        mPresent.saveUserInfo(user_id, user_name, phone, code, pw, pwnew, pid, cid, aid
                                , birthday);
                    }
                    break;
            }
        }
    };
    /**
     * 是否正在请求发送验证码，防止多次重发
     */
    boolean codeSendingStatus = false;
    private MyHandler mcodeHandler = new MyHandler();
    private TimerTask timerTask;
    private final int Daojishistart = 0;
    private final int Daojishiover = 1;
    private int count;

    private void daojishi() {
        final long nowTime = System.currentTimeMillis();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                count--;
                Message msg = new Message();
                msg.what = Daojishiover;
                msg.arg1 = count;
                msg.obj = nowTime;
                mcodeHandler.sendMessage(msg);
                if (count <= 0) {
                    this.cancel();
                }
                msg = null;
            }
        };
        new Timer().schedule(timerTask, 0, 1000);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Daojishistart:
                    tv_getcode.setEnabled(false);
                    count = 60;
                    daojishi();
                    break;
                case Daojishiover:
                    if (msg.arg1 < 10) {
                        tv_getcode.setText("0" + msg.arg1 + getString(R.string.tv_codeunit));
                    } else {
                        tv_getcode.setText(msg.arg1 + getString(R.string.tv_codeunit));
                    }
                    if (msg.arg1 <= 0) {
                        tv_getcode.setTextColor(getResources().getColor(R.color.blue));
                        tv_getcode.setEnabled(true);
                        tv_getcode.setText(getString(R.string.code_get));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private boolean checkActionStatus(String user_name, String phone, String code
            , String pw, String pwnew, String birthday) {

        if (TextUtils.isEmpty(user_name)) {
            ToastUtils.showToast("请输入您的姓名");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请输入您的手机号");
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showToast("请输入您的验证码");
            return false;
        }
        if (!Utils.isCheckPW(pw, pwnew)) {
            return false;
        }
        if (TextUtils.isEmpty(birthday)) {
            ToastUtils.showToast("请选择您的出生日期");
            return false;
        }
        if (TextUtils.isEmpty(pid)) {
            ToastUtils.showToast("请选择出生地");
            return false;
        }
        return true;
    }

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
                    tv_address.setText(area);
                    break;
            }
        }
    };

    /**
     * **********************************************************
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
            mHandler.removeCallbacks(timerTask);
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
