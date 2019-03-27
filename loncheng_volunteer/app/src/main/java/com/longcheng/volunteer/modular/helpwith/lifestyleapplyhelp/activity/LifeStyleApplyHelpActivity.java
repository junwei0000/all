package com.longcheng.volunteer.modular.helpwith.lifestyleapplyhelp.activity;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseActivityMVP;
import com.longcheng.volunteer.modular.helpwith.applyhelp.activity.PeopleActivity;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleAfterBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleDataBean;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleItemBean;
import com.longcheng.volunteer.modular.helpwith.lifestyle.activity.LifeStyleActivity;
import com.longcheng.volunteer.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedDataBean;
import com.longcheng.volunteer.modular.helpwith.lifestyleapplyhelp.bean.LifeNeedItemBean;
import com.longcheng.volunteer.modular.helpwith.lifestyledetail.activity.LifeStyleDetailActivity;
import com.longcheng.volunteer.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.volunteer.modular.mine.myaddress.activity.AddressSelectUtils;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressAfterBean;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressItemBean;
import com.longcheng.volunteer.modular.mine.myaddress.bean.AddressListDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.MyDialog;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;

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
    @BindView(R.id.tv_goodsname)
    TextView tvGoodsname;
    @BindView(R.id.item_layout_sub)
    LinearLayout itemLayoutSub;
    @BindView(R.id.item_tv_goodsnum)
    TextView itemTvGoodsnum;
    @BindView(R.id.item_tv_add)
    TextView itemTvAdd;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.relat_people)
    RelativeLayout relatPeople;
    @BindView(R.id.tv_addressname)
    TextView tvAddressname;
    @BindView(R.id.tv_addressmobile)
    TextView tvAddressmobile;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.relat_address)
    RelativeLayout relatAddress;
    @BindView(R.id.cb_give)
    TextView cbGive;
    @BindView(R.id.tv_give)
    TextView tvGive;
    @BindView(R.id.cb_ziyong)
    TextView cbZiyong;
    @BindView(R.id.tv_ziyong)
    TextView tvZiyong;
    @BindView(R.id.et_yongtu)
    EditText etYongtu;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.relat_explain)
    RelativeLayout relatExplain;
    @BindView(R.id.btn_save)
    TextView btnSave;

    private String user_id;
    private String shop_goods_price_id;
    private String remark;
    private int purpose = 1;//是否送礼 1 送礼
    private String address_id;
    private int apply_goods_number = 1;//数量
    private String peopleid, peoplename;
    private String describe;
    private String goods_id;

    private int apply_help_max_limit;//申请数量限制
    private int is_read = 1;//是否已读： 1 已读

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.item_layout_sub:
                if (apply_goods_number > 1) {
                    apply_goods_number--;
                }
                itemTvGoodsnum.setText("" + apply_goods_number);
                break;
            case R.id.item_tv_add:
                if (apply_goods_number < apply_help_max_limit) {
                    apply_goods_number++;
                } else {
                    ToastUtils.showToast("申请数量超额");
                }
                itemTvGoodsnum.setText("" + apply_goods_number);
                break;
            case R.id.cb_give:
            case R.id.tv_give:
                purpose = 1;
                cbGive.setBackgroundResource(R.mipmap.check_true_red);
                cbZiyong.setBackgroundResource(R.mipmap.check_false);
                etYongtu.setVisibility(View.VISIBLE);
                break;
            case R.id.cb_ziyong:
            case R.id.tv_ziyong:
                purpose = 2;
                cbGive.setBackgroundResource(R.mipmap.check_false);
                cbZiyong.setBackgroundResource(R.mipmap.check_true_red);
                etYongtu.setVisibility(View.GONE);
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
                    remark = etYongtu.getText().toString().trim();
                    mPresent.applyAction(user_id, shop_goods_price_id,
                            remark, purpose, address_id, apply_goods_number, peopleid, goods_id, describe);
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
        return R.layout.helpwith_lifestyle_apply;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        itemLayoutSub.setOnClickListener(this);
        itemTvAdd.setOnClickListener(this);
        cbGive.setOnClickListener(this);
        tvGive.setOnClickListener(this);
        cbZiyong.setOnClickListener(this);
        tvZiyong.setOnClickListener(this);
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

        mPresent.getLifeNeedHelpNumberTaskSuccess(user_id, goods_id);
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
                    tvGoodsname.setText(info.getName());
                    apply_help_max_limit = info.getApply_help_max_limit();
                }
                List<LifeNeedItemBean> mList = mPeopleAfterBean.getHelpGoodsTemp();
                if (mList != null && mList.size() > 0 && TextUtils.isEmpty(describe)) {
                    int random = ConfigUtils.getINSTANCE().setRandom(mList.size() - 1);
                    describe = mList.get(random).getContent();
                    tvExplain.setText(describe);
                    setBtnBg();
                }

                need_help_number = mPeopleAfterBean.getNeedhelpGoodsnumber();
                is_read = mPeopleAfterBean.getIs_read();
                if (need_help_number > 0) {
                    LifeNeedItemBean appointHelpGoods = mPeopleAfterBean.getAppointHelpGoods();
                    if (appointHelpGoods != null) {
                        redirectMsgId = appointHelpGoods.getRedirectMsgId();//互祝help_goods_id
                    }
                    showPopupWindow();
                } else if (is_read == 0 && need_help_number == 0) {
                    LifeNeedItemBean appointHelpGoods = mPeopleAfterBean.getApplySuccess();
                    if (appointHelpGoods != null) {
                        redirectMsgId = appointHelpGoods.getRedirectMsgId();//自己help_goods_id
                    }
                    showPopupWindow();
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
                    String address_mobile = mAddressBean.getMobile();
                    String address_address = mAddressBean.getAddress();
                    tvAddressname.setText(address_name);
                    tvAddressmobile.setText(address_mobile);
                    tvAddress.setText(area + " " + address_address);
                } else {
                    address_id = "";
                    tvAddressname.setText("");
                    tvAddressmobile.setText("");
                    tvAddress.setText("");
                }
                setBtnBg();
            }
        }
    }

    @Override
    public void applyActionSuccess(LifeNeedDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            String mag = responseBean.getMsg();
            Log.e("applyActionSuccess", "applyActionSuccess=" + mag);
            ToastUtils.showToast(mag);
        } else if (status.equals("200")) {
            LifeNeedItemBean mActionItemBean = responseBean.getData();
            if (mActionItemBean != null) {
                need_help_number = mActionItemBean.getNeed_help_number();
                redirectMsgId = mActionItemBean.getRedirectMsgId();
                showPopupWindow();
            }

        }
    }

    @Override
    public void ListError() {

    }


    MyDialog selectDialog;
    int need_help_number;
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
                    if (need_help_number > 0) {
                        //申请成功后做任务跳转msgid   0：跳转到列表页   非0：跳转到行动详情页
                        if (redirectMsgId.equals("0")) {
                            intent = new Intent(mContext, LifeStyleActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("skiptype", "LifeApplyHelp");
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                        } else {
                            intent = new Intent(mContext, LifeStyleDetailActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("help_goods_id", "" + redirectMsgId);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                        }
                    } else {
                        //查看任务详情页面,并给后台传值已读
                        mPresent.setTaskRead(user_id, redirectMsgId);
                        intent = new Intent(mContext, LifeStyleDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("help_goods_id", "" + redirectMsgId);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                        doFinish();
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
        if (need_help_number > 0) {
            tv_cont1.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            tv_cont2.setTextColor(getResources().getColor(R.color.text_noclick_color));
            String showT = "请您送出<font color=\"#db4065\">" + need_help_number + "</font>次祝福";
            tv_cont1.setText(Html.fromHtml(showT));
            tv_cont2.setText("即可获得更多人的祝福");
            tv_cont2.setVisibility(View.VISIBLE);
            tv_cont3.setVisibility(View.GONE);
            btn_ok.setText("点击送出祝福");
        } else {
            tv_cont2.setVisibility(View.GONE);
            tv_cont3.setVisibility(View.VISIBLE);
            tv_cont1.setText("感恩您的奉献！");
            tv_cont3.setText("恭喜您申请成功！");
            tv_cont1.setTextColor(getResources().getColor(R.color.color_db4065));
            tv_cont3.setTextColor(getResources().getColor(R.color.color_db4065));
            btn_ok.setText("知道了，点击查看");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ConstantManager.APPLYHELP_FORRESULT_PEOPLE) {
                peopleid = data.getStringExtra("peopleid");
                peoplename = data.getStringExtra("peoplename");
                tvPeople.setText(peoplename);
                mPresent.setAddressList(user_id, peopleid);
            } else if (resultCode == ConstantManager.APPLYHELP_FORRESULT_ADDRESS) {
                address_id = data.getStringExtra("address_id");
                String address_name = data.getStringExtra("address_name");
                String address_mobile = data.getStringExtra("address_mobile");
                String address_address = data.getStringExtra("address_address");
                tvAddressname.setText(address_name);
                tvAddressmobile.setText(address_mobile);
                tvAddress.setText(address_address);
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
            mPresent.getLifeNeedHelpNumberTaskSuccess(user_id, goods_id);
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
