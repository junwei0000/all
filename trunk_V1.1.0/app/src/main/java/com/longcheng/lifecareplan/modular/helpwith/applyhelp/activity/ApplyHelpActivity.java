package com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleItemBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleSearchDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressSelectUtils;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressAfterBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressItemBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.List;

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
    private String extend_info;
    private String mutual_help_apply_id;
    private String qiming_user_id = "0";

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.relat_action:
                intent = new Intent(mContext, ActionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_ACTION);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.relat_people:
                intent = new Intent(mContext, PeopleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_PEOPLE);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
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
                Log.e("Observable", "address_id = " + address_id);
                if (!TextUtils.isEmpty(action_id) && !TextUtils.isEmpty(peopleid) &&
                        !TextUtils.isEmpty(address_id) && !TextUtils.isEmpty(describe)) {
                    mPresent.applyAction(user_id, action_id,
                            peopleid, address_id, describe, action_safety_id, extend_info, qiming_user_id);
                }
                break;
        }
    }

    private void setBtnBg() {
        if (!TextUtils.isEmpty(action_id) && !TextUtils.isEmpty(peopleid) &&
                !TextUtils.isEmpty(address_id) && !TextUtils.isEmpty(describe)) {
            btnSave.setBackgroundResource(R.drawable.corners_bg_helpbtn);
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
    public void initDataAfter() {
        pageTopTvName.setText("申请互祝");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getNeedHelpNumberTask(user_id);
        mPresent.getPeopleList(user_id);
        setBtnBg();
        showRedSkipData();
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

                        extend_info = "";
                        mPresent.getExplainList(user_id, action_id);
                        break;
                    }

                }
            }

        }
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
                    back();
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
    private void showRedSkipData() {
        Intent intent = getIntent();
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

    private void back() {
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
