package com.longcheng.lifecareplan.modular.mine.activatenergy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.MoneyAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.PushQueueAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyAfterBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.modular.mine.bill.activity.EngryRecordActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.youzan.YouZanActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 激活能量---
 */
public class ActivatEnergyActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.activat_tv_num)
    TextView activatTvNum;
    @BindView(R.id.activat_tv_cont)
    TextView activatTvCont;
    @BindView(R.id.activat_gv_money)
    MyGridView activatGvMoney;
    @BindView(R.id.activat_tv_account)
    TextView activatTvAccount;
    @BindView(R.id.activat_iv_accountselect)
    ImageView activatIvAccountselect;
    @BindView(R.id.activat_relat_account)
    RelativeLayout activatRelatAccount;
    @BindView(R.id.btn_jihuo)
    TextView btnJihuo;
    @BindView(R.id.activat_iv_yzselect)
    ImageView activatIvYzselect;
    @BindView(R.id.activat_relat_youzan)
    RelativeLayout activatRelatYouzan;
    @BindView(R.id.tv_zfstitle)
    TextView tv_zfstitle;
    @BindView(R.id.tv_youzan)
    TextView tv_youzan;
    @BindView(R.id.detailhelp_iv_zfsselect)
    ImageView detailhelpIvZfsselect;
    @BindView(R.id.detailhelp_relat_zfs)
    RelativeLayout detailhelpRelatZfs;
    @BindView(R.id.iv_youicon)
    ImageView iv_youicon;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_sxf)
    TextView tv_sxf;
    @BindView(R.id.tv_moneytitle)
    TextView tv_moneytitle;
    @BindView(R.id.btn_zfsrecord)
    TextView btn_zfsrecord;
    @BindView(R.id.et_money)
    EditText et_money;
    @BindView(R.id.tv_money_toengry)
    TextView tv_money_toengry;
    @BindView(R.id.layout_money)
    LinearLayout layout_money;
    @BindView(R.id.layout_showengry)
    LinearLayout layout_showengry;

    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieeqi)
    TextView itemTvJieeqi;
    @BindView(R.id.item_tv_time)
    TextView itemTvTime;
    @BindView(R.id.item_tv_money)
    TextView itemTvMoney;
    @BindView(R.id.layout_dc)
    LinearLayout layout_dc;
    @BindView(R.id.item_tv_cancel)
    TextView item_tv_cancel;
    @BindView(R.id.lv_pushdata)
    MyListView lv_pushdata;

    @BindView(R.id.activat_relat_jqb)
    RelativeLayout activat_relat_jqb;
    @BindView(R.id.activat_tv_jqb)
    TextView activat_tv_jqb;
    @BindView(R.id.activat_iv_jqbselect)
    ImageView activat_iv_jqbselect;
    @BindView(R.id.tv_mted)
    TextView tv_mted;


    /**
     * 充值渠道激活类型 1现金; 2 有赞 4祝福师代充   5 节气宝
     */
    private int payType = 4;
    /**
     * 祝福师-支付方式激活类型 1微信; 2 支付宝 ；3 秒提额度
     */
    private int zfs_payType = 3;
    private MoneyAdapter mMoneyAdapter;

    private String activate_ability_config_id;
    private String user_id;
    private int type;

    private int identityType;//3 祝福师   2普通用户
    private String moneyCont = "0";
    private String userRechargeListUrl;
    private String cookie_key;
    private String cookie_value;
    private String money_select = "0";
    private String First_energy = "0";
    private String Presenter_energy = "0", todayCashMoney = "0", user_zhufubao_order_id;

    int maxRechargeLimit;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.activat_relat_youzan:
                payType = 2;
                selectPayTypeView();
                break;
            case R.id.activat_relat_account:
                payType = 1;
                selectPayTypeView();
                break;
            case R.id.detailhelp_relat_zfs:
                payType = 4;
                selectPayTypeView();
                break;
            case R.id.activat_relat_jqb:
                payType = 5;
                selectPayTypeView();
                break;

            case R.id.btn_jihuo:
                if (payType == 4) {
                    if (Double.parseDouble(moneyCont) > 0) {
                        if (zfs_payType != 3 || (zfs_payType == 3 && Double.parseDouble(todayCashMoney) >= Double.parseDouble(moneyCont))) {
                            userRecharge(user_id, moneyCont, "" + zfs_payType);
                        } else if (Double.parseDouble(todayCashMoney) < Double.parseDouble(moneyCont)) {
                            ToastUtils.showToast("输入秒提额度大于可秒提额度");
                        }
                    } else {
                        ToastUtils.showToast("请输入秒提额度");
                    }
                } else {
                    assetRecharge(user_id, money_select, activate_ability_config_id, type, "" + payType);
                }
                break;
            case R.id.btn_zfsrecord:
                Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", userRechargeListUrl);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.item_tv_cancel:
                cancelPiPei(user_zhufubao_order_id);
                break;

        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activatenergy;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("激活能量");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @SuppressLint("NewApi")
    @Override
    public void setListener() {
        item_tv_cancel.setOnClickListener(this);
        btn_zfsrecord.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        activatRelatYouzan.setOnClickListener(this);
        detailhelpRelatZfs.setOnClickListener(this);
        activatRelatAccount.setOnClickListener(this);
        activat_relat_jqb.setOnClickListener(this);
        btnJihuo.setOnClickListener(this);
        activatGvMoney.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0) {
                    if (payType == 1 || payType == 5) {
                        for (int i = 0; i < mList.size(); i++) {
                            if (i == position) {
                                mList.get(position).setIs_default(1);
                            } else {
                                mList.get(i).setIs_default(0);
                            }
                        }
                        setAssetView(mList);
                        mMoneyAdapter.setList(mList);
                        mMoneyAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < YouzanList.size(); i++) {
                            if (i == position) {
                                YouzanList.get(position).setIs_default(1);
                            } else {
                                YouzanList.get(i).setIs_default(0);
                            }
                        }
                        setAssetView(YouzanList);
                        mMoneyAdapter.setList(YouzanList);
                        mMoneyAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        et_money.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {//搜索按键action
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    moneyCont = v.getText().toString();
                    if (TextUtils.isEmpty(moneyCont)) {
                        if (tv_money_toengry != null) {
                            moneyCont = "0";
                            tv_money_toengry.setText("获得" + moneyCont);
                        }
                        return true;
                    }
                    if (tv_money_toengry != null) {
                        String showEngry = PriceUtils.getInstance().gteMultiplySumPrice(moneyCont, "9");
                        tv_money_toengry.setText("获得" + showEngry);
                    }
                    return true;
                }
                return false;
            }
        });
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_money, 9);
        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //还原数据
                if (et_money != null && payType == 4) {
                    moneyCont = et_money.getText().toString();
                    if (TextUtils.isEmpty(moneyCont)) {
                        moneyCont = "0";
                        tv_money_toengry.setText("获得" + moneyCont);
                    } else {
                        if (zfs_payType == 2 && (Double.parseDouble(moneyCont) > maxRechargeLimit)) {
                            moneyCont = "" + maxRechargeLimit;
                            et_money.setText(moneyCont);
                            et_money.setSelection(moneyCont.length());
                        }
                        if (zfs_payType == 3 && (Double.parseDouble(moneyCont) > Double.parseDouble(todayCashMoney))) {
                            moneyCont = "" + todayCashMoney;
                            et_money.setText(moneyCont);
                            et_money.setSelection(moneyCont.length());
                        }
                        String showEngry = PriceUtils.getInstance().gteMultiplySumPrice(moneyCont, "9");
                        tv_money_toengry.setText("获得" + showEngry);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void initDataAfter() {
        getYouZanCookie();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user_id = UserUtils.getUserId(mContext);
        getRechargeInfo(user_id);
    }

    /**
     * 获取有赞cookie
     */
    public void getYouZanCookie() {
        Observable<GetEnergyListDataBean> observable = Api.getInstance().service.getYouZanCookie(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetEnergyListDataBean>() {
                    @Override
                    public void accept(GetEnergyListDataBean responseBean) throws Exception {
                        cookie_key = responseBean.getData().getCookie_key();
                        cookie_value = responseBean.getData().getCookie_value();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });

    }


    /**
     * 切换充值渠道
     */
    private void selectPayTypeView() {
        if (mList != null && mList.size() > 0) {
            if (payType == 1 || payType == 5) {
                mMoneyAdapter.setList(mList);
                mMoneyAdapter.notifyDataSetChanged();
                setAssetView(mList);
            } else {
                mMoneyAdapter.setList(YouzanList);
                mMoneyAdapter.notifyDataSetChanged();
                setAssetView(YouzanList);
            }
        }
        layout_showengry.setVisibility(View.VISIBLE);
        tv_zfstitle.setTextColor(getResources().getColor(R.color.text_contents_color));
        tv_youzan.setTextColor(getResources().getColor(R.color.text_contents_color));
        activatTvAccount.setTextColor(getResources().getColor(R.color.text_contents_color));
        activat_tv_jqb.setTextColor(getResources().getColor(R.color.text_contents_color));
        activatTvCont.setVisibility(View.GONE);
        activatIvYzselect.setVisibility(View.GONE);
        activatRelatYouzan.setBackgroundResource(R.drawable.corners_bg_graybian);
        activatIvAccountselect.setVisibility(View.GONE);
        activatRelatAccount.setBackgroundResource(R.drawable.corners_bg_graybian);
        detailhelpIvZfsselect.setVisibility(View.GONE);
        detailhelpRelatZfs.setBackgroundResource(R.drawable.corners_bg_graybian);
        activat_relat_jqb.setBackgroundResource(R.drawable.corners_bg_graybian);
        activat_iv_jqbselect.setVisibility(View.GONE);
        iv_youicon.setBackgroundResource(R.mipmap.superactivat_you_icon);
        tv_title.setText("激活后获取超级生命能量");
        tv_sxf.setVisibility(View.VISIBLE);
        tv_moneytitle.setText("请选择金额");
        btn_zfsrecord.setVisibility(View.GONE);
        layout_money.setVisibility(View.GONE);
        activatGvMoney.setVisibility(View.VISIBLE);
        tv_mted.setVisibility(View.GONE);
        if (payType == 2) {
            tv_youzan.setTextColor(getResources().getColor(R.color.red));
            activatIvYzselect.setVisibility(View.VISIBLE);
            activatRelatYouzan.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatYouzan.setPadding(0, 0, 0, 0);
            btnJihuo.setBackgroundColor(getResources().getColor(R.color.cyanbluebg));
            if (identityType == 3) {//可代充的祝福师
                iv_youicon.setBackgroundResource(R.mipmap.zhufubao_you_icon);
                btnJihuo.setText("立即激活 (祝福宝)");
                tv_title.setText("激活后获取祝福宝");
            } else {
                iv_youicon.setBackgroundResource(R.mipmap.superactivat_you_icon);
                btnJihuo.setText("立即激活 (超级生命能量)");
                tv_title.setText("激活后获取超级生命能量");
            }
        } else if (payType == 1) {
            activatTvAccount.setTextColor(getResources().getColor(R.color.red));
            activatIvAccountselect.setVisibility(View.VISIBLE);
            activatRelatAccount.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatAccount.setPadding(0, 0, 0, 0);
            btnJihuo.setText("立即激活 (生命能量)");
            btnJihuo.setBackgroundColor(getResources().getColor(R.color.red));
            iv_youicon.setBackgroundResource(R.mipmap.activat_you_icon);
            tv_title.setText("激活后获取生命能量");
            tv_sxf.setVisibility(View.GONE);
            activatTvCont.setVisibility(View.VISIBLE);
        } else if (payType == 5) {
            activat_tv_jqb.setTextColor(getResources().getColor(R.color.red));
            activat_iv_jqbselect.setVisibility(View.VISIBLE);
            activat_relat_jqb.setBackgroundResource(R.drawable.corners_bg_redbian);
            activat_relat_jqb.setPadding(0, 0, 0, 0);
            btnJihuo.setText("立即激活 (生命能量)");
            btnJihuo.setBackgroundColor(getResources().getColor(R.color.red));
            iv_youicon.setBackgroundResource(R.mipmap.activat_you_icon);
            tv_title.setText("激活后获取生命能量");
            tv_sxf.setVisibility(View.GONE);
            activatTvCont.setVisibility(View.VISIBLE);
        } else if (payType == 4) {
            layout_showengry.setVisibility(View.GONE);
            tv_zfstitle.setTextColor(getResources().getColor(R.color.red));
            detailhelpIvZfsselect.setVisibility(View.VISIBLE);
            detailhelpRelatZfs.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelpRelatZfs.setPadding(0, 0, 0, 0);
            btnJihuo.setText("立即激活 (超级生命能量)");
            btnJihuo.setBackgroundColor(getResources().getColor(R.color.cyanbluebg));
            tv_sxf.setVisibility(View.GONE);
            tv_mted.setVisibility(View.VISIBLE);
            tv_moneytitle.setText("请填写秒提额度金额");
            btn_zfsrecord.setVisibility(View.GONE);
            layout_money.setVisibility(View.VISIBLE);
            activatGvMoney.setVisibility(View.GONE);
            String showEngry = PriceUtils.getInstance().gteMultiplySumPrice(moneyCont, "9");
            tv_money_toengry.setText("获得" + showEngry);
        }
    }

    private void setAssetView(List<EnergyItemBean> list) {
        for (EnergyItemBean mEnergyItemBean : list) {
            if (mEnergyItemBean.getIs_default() == 1) {
                money_select = mEnergyItemBean.getMoney();
                First_energy = mEnergyItemBean.getReward_value();
                Presenter_energy = mEnergyItemBean.getGive_value();
                activate_ability_config_id = mEnergyItemBean.getActivate_ability_config_id();
                type = mEnergyItemBean.getType();
                if (payType == 1 || payType == 5) {
                    activatTvCont.setText("激活" + First_energy + "+赠送" + Presenter_energy);
                    activatTvNum.setText(PriceUtils.getInstance().gteAddSumPrice(First_energy, Presenter_energy));
                } else {
                    activatTvCont.setText("");
                    activatTvNum.setText(First_energy);
                }
                break;
            }
        }
    }


    /**
     * @param
     * @name 激活生命能量数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void getRechargeInfo(String user_id) {
        showDialog();
        Observable<GetEnergyListDataBean> observable = Api.getInstance().service.getRechargeInfo(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GetEnergyListDataBean>() {
                    @Override
                    public void accept(GetEnergyListDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            ListSuccess(responseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    /**
     * 充值取消匹配
     *
     * @param user_zhufubao_order_id
     */
    public void cancelPiPei(String user_zhufubao_order_id) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.cancelPiPeiDaiChong(user_id, user_zhufubao_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            if (responseBean.getStatus().equals("200")) {
                                getRechargeInfo(user_id);
                                ToastUtils.showToast(responseBean.getMsg());
                            } else {
                                ToastUtils.showToast(responseBean.getMsg());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    /**
     * 激活生命能量
     *
     * @param user_id
     */
    public void assetRecharge(String user_id, String money, String activate_ability_config_id, int type, String payment_channel) {
        Log.e("Observable", "money=" + money + "  activate_ability_config_id=" + activate_ability_config_id + "  payment_channel=" + payment_channel);
        showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.assetRecharge(user_id, money,
                activate_ability_config_id, type, "2", payment_channel, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus())) {
                            GetPayWXSuccess(responseBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    /**
     * 激活生命能量---祝福师
     *
     * @param user_id
     */
    public void userRecharge(String user_id, String moneyCont, String pay_method) {
        showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.userRecharge(user_id, moneyCont,
                "2", pay_method, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        dismissDialog();
                        ToastUtils.showToast(responseBean.getMsg());
                        if (responseBean.getStatus().equals("200")) {
                            getRechargeInfo(user_id);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ListError();
                    }
                });

    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    List<EnergyItemBean> mList, YouzanList;

    public void ListSuccess(GetEnergyListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            EnergyAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                maxRechargeLimit = mEnergyAfterBean.getMaxRechargeLimit();
                todayCashMoney = mEnergyAfterBean.getTodayCashMoney();
                String serviceCharge = mEnergyAfterBean.getServiceCharge();
                userRechargeListUrl = mEnergyAfterBean.getUserRechargeListUrl();
                identityType = mEnergyAfterBean.getIdentityType();
                if (identityType == 2) {
                    tv_sxf.setText("*使用有赞充值需要扣除" + serviceCharge + "手续费");
                } else {
                    tv_sxf.setText("*使用有赞充值祝福宝暂免手续费");
                }

                mList = mEnergyAfterBean.getEnergys();
                YouzanList = mEnergyAfterBean.getYouzanConfig();
                if (YouzanList != null && YouzanList.size() > 0) {
                    mMoneyAdapter = new MoneyAdapter(mContext, YouzanList);
                    activatGvMoney.setAdapter(mMoneyAdapter);
                    selectPayTypeView();
                }
                EnergyItemBean chatuser = mEnergyAfterBean.getChatuser();
                String asset = chatuser.getAsset();
                tv_mted.setText("今日剩余秒提额度" + todayCashMoney);
                tv_zfstitle.setText("秒提额度:" + mEnergyAfterBean.getCashMoney());
                activatTvAccount.setText("现金余额:" + asset);
                activat_tv_jqb.setText("节气宝:" + chatuser.getJieqibao());
                EnergyItemBean daichong_order = mEnergyAfterBean.getDaichong_order();
                user_zhufubao_order_id = daichong_order.getUser_zhufubao_order_id();
                if (daichong_order != null && !TextUtils.isEmpty(daichong_order.getCreate_time())) {
                    layout_dc.setVisibility(View.VISIBLE);
                    GlideDownLoadImage.getInstance().loadCircleImage(daichong_order.getAvatar(), itemIvThumb);
                    String name = CommonUtil.setName(daichong_order.getUser_name());
                    itemTvName.setText(name);
                    itemTvJieeqi.setText(daichong_order.getJieqi_name());
                    itemTvTime.setText(daichong_order.getCreate_time());
                    itemTvMoney.setText(daichong_order.getPrice() + "元");

                    List<EnergyItemBean> pushQueueItems = mEnergyAfterBean.getPushQueueItems();
                    if (pushQueueItems != null && pushQueueItems.size() > 0) {
                        if (mPushQueueAdapter == null) {
                            mPushQueueAdapter = new PushQueueAdapter(mContext, pushQueueItems);
                            lv_pushdata.setAdapter(mPushQueueAdapter);
                        } else {
                            mPushQueueAdapter.setList(pushQueueItems);
                            mPushQueueAdapter.notifyDataSetChanged();
                        }
                    }
                    if (mPushQueueAdapter != null) {
                        mPushQueueAdapter.setList(pushQueueItems);
                        mPushQueueAdapter.notifyDataSetChanged();
                    }
                } else {
                    layout_dc.setVisibility(View.GONE);
                }
            }
        }
    }

    PushQueueAdapter mPushQueueAdapter;

    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    public void GetPayWXSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            if (payType == 1 || payType == 5) {
                jihuoSuccess();
            } else if (payType == 2) {
                Intent intent = new Intent(mContext, YouZanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", responseBean.getData());
                intent.putExtra("cookie_key", cookie_key);
                intent.putExtra("cookie_value", cookie_value);

                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            } else if (payType == 4) {

            }
        }
    }


    private void autohelpRefresh() {
        //智能互祝----刷新页面数据
        Intent intent = new Intent();
        intent.setAction(ConstantManager.BroadcastReceiver_KNP_ACTION);
        intent.putExtra("errCode", AutoHelpH5Activity.knpPaySuccessBack);
        sendBroadcast(intent);
    }

    private void jihuoSuccess() {
        autohelpRefresh();
        ToastUtils.showToast("激活成功");
        Intent intent = new Intent(mContext, EngryRecordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        doFinish();
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
