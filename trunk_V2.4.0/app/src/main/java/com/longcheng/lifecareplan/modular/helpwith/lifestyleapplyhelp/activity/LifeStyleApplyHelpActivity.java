package com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.PeopleActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.PeopleItemBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedItemBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity.LifeStyleDetailActivity;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressSelectUtils;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressAfterBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressItemBean;
import com.longcheng.lifecareplan.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.List;

import butterknife.BindView;

/**
 * 生活方式-申请互祝
 */
public class LifeStyleApplyHelpActivity extends BaseActivityMVP<LifeStyleApplyHelpContract.View, LifeStyleApplyHelpPresenterImp<LifeStyleApplyHelpContract.View>> implements LifeStyleApplyHelpContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    @BindView(R.id.tv_action)
    TextView tvAction;
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
    @BindView(R.id.layout_address)
    LinearLayout layout_address;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.btn_save)
    TextView btnSave;

    private String user_id;
    private String shop_goods_price_id;
    private String address_id;
    private int apply_goods_number = 1;//数量
    private String peopleid, peoplename;
    private String describe;
    private String goods_id;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
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
                    intent.putExtra("skiptype", "LifeStyleApplyHelpActivity");
                    startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_ADDRESS);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                } else {
                    ToastUtils.showToast("请选择接福人");
                }
                break;
            case R.id.relat_explain:
                intent = new Intent(mContext, LifeExplainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("describe", describe);
                startActivityForResult(intent, ConstantManager.APPLYHELP_FORRESULT_EXPLAIN);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.btn_save:
                Log.e("Observable", "address_id = " + address_id);
                if (!TextUtils.isEmpty(goods_id) && !TextUtils.isEmpty(peopleid) &&
                        !TextUtils.isEmpty(address_id) && !TextUtils.isEmpty(describe)) {
                    showApplyDialog();
                }
                break;
        }
    }


    private void setBtnBg() {
        if (!TextUtils.isEmpty(goods_id) && !TextUtils.isEmpty(peopleid) &&
                !TextUtils.isEmpty(address_id) && !TextUtils.isEmpty(describe)) {
            btnSave.setBackgroundResource(R.drawable.corners_bg_login);
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
        return R.layout.helpwith_lifestyle_applynew;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        relatPeople.setOnClickListener(this);
        relatAddress.setOnClickListener(this);
        relatExplain.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("申请互祝");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");

        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        shop_goods_price_id = intent.getStringExtra("shop_goods_price_id");
        setBtnBg();

        mPresent.getLifeApplyInfo(user_id, goods_id, shop_goods_price_id);
        mPresent.getPeopleList(user_id);
    }


    @Override
    protected LifeStyleApplyHelpPresenterImp<LifeStyleApplyHelpContract.View> createPresent() {
        return new LifeStyleApplyHelpPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void getNeedHelpNumberTaskSuccess(LifeNeedDataBean responseBean) {
        firstComIn = false;
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            LifeNeedItemBean mPeopleAfterBean = responseBean.getData();
            if (mPeopleAfterBean != null) {
                LifeNeedItemBean info = mPeopleAfterBean.getSkbGoodsInfo();
                if (info != null) {
                    skb_price = mPeopleAfterBean.getSkb_price();
                    apply_help_price = mPeopleAfterBean.getApply_help_price();
                    tvAction.setText(mPeopleAfterBean.getName()+"   "+mPeopleAfterBean.getPrice_name());
                    super_ability = mPeopleAfterBean.getChatuser().getSuper_ability();
                    shoukangyuan = mPeopleAfterBean.getChatuser().getShoukangyuan();
                }
                List<LifeNeedItemBean> mList = mPeopleAfterBean.getHelpGoodsTemp();
                if (mList != null && mList.size() > 0 && TextUtils.isEmpty(describe)) {
                    int random = ConfigUtils.getINSTANCE().setRandom(mList.size() - 1);
                    describe = mList.get(random).getContent();
                    tvExplain.setText(describe);
                    setBtnBg();
                }

            }

        }
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
                    tvAddress.setText(area + " " + address_address);
                    if (!TextUtils.isEmpty(address_name) && address_name.length() >= 11) {
                        address_name = address_name.substring(0, 10) + "...";
                    }
                    if (!TextUtils.isEmpty(phone) && phone.length() >= 11) {
                        phone = phone.substring(0, 3) + "****" + phone.substring(7);
                    }
                    tv_addressname.setText(address_name + "     " + phone);
                    tvAddress.setText(area + " " + address_address);
                    layout_address.setVisibility(View.VISIBLE);
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
    public void applyActionSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            String redirectMsgId = responseBean.getData();
            showPopupWindow(redirectMsgId);
        } else {
            String mag = responseBean.getMsg();
            ToastUtils.showToast(mag);
        }
    }

    @Override
    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    MyDialog applyDialog;
    TextView tv_showcn, tv_showskb, tv_mycn, tv_myskb;

    /**
     * 申请弹层
     */
    private void showApplyDialog() {
        if (applyDialog == null) {
            applyDialog = new MyDialog(this, R.style.dialog, R.layout.dialog_lifestyleapplyhelp);// 创建Dialog并设置样式主题
            applyDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = applyDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            applyDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = applyDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            applyDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = (LinearLayout) applyDialog.findViewById(R.id.layout_cancel);
            tv_showcn = (TextView) applyDialog.findViewById(R.id.tv_showcn);
            tv_showskb = (TextView) applyDialog.findViewById(R.id.tv_showskb);
            tv_mycn = (TextView) applyDialog.findViewById(R.id.tv_mycn);
            tv_myskb = (TextView) applyDialog.findViewById(R.id.tv_myskb);
            TextView btn_helpsure = (TextView) applyDialog.findViewById(R.id.btn_helpsure);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    applyDialog.dismiss();
                }
            });
            btn_helpsure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresent.applyAction(user_id, goods_id, shop_goods_price_id, peopleid,
                            address_id, describe);
                    applyDialog.dismiss();
                }
            });
        } else {
            applyDialog.show();
        }
        tv_showcn.setText(apply_help_price);
        tv_showskb.setText(skb_price);
        tv_mycn.setText(super_ability);
        tv_myskb.setText(shoukangyuan);

    }

    String apply_help_price = "", skb_price = "", super_ability = "", shoukangyuan = "";
    MyDialog selectDialog;
    TextView tv_cont1, tv_cont2, tv_cont3, btn_ok;

    /**
     * 成功弹出
     */
    private void showPopupWindow(String redirectMsgId) {
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
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LifeStyleDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("help_goods_id", "" + redirectMsgId);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    doFinish();
                    selectDialog.dismiss();
                }
            });
        } else {
            initView();
            selectDialog.show();
        }
    }

    private void initView() {
        tv_cont1.setVisibility(View.VISIBLE);
        tv_cont2.setVisibility(View.GONE);
        tv_cont3.setVisibility(View.VISIBLE);
        tv_cont1.setText("感恩您的奉献！");
        tv_cont3.setText("恭喜您申请成功！");
        tv_cont1.setTextColor(getResources().getColor(R.color.color_db4065));
        tv_cont3.setTextColor(getResources().getColor(R.color.color_db4065));
        btn_ok.setText("知道了，点击查看");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.APPLYHELP_FORRESULT_PEOPLE) {
                peopleid = data.getStringExtra("peopleid");
                peoplename = data.getStringExtra("peoplename");
                tvPeople.setText(peoplename);
                String avatar = data.getStringExtra("avatar");
                GlideDownLoadImage.getInstance().loadCircleImage(mContext, avatar, ivThumb);
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

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstComIn)
            mPresent.getLifeApplyInfo(user_id, goods_id, shop_goods_price_id);
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
