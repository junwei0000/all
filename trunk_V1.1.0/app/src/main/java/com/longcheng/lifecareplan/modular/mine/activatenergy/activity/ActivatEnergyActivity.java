package com.longcheng.lifecareplan.modular.mine.activatenergy.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.ConnonH5Activity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.MoneyAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyAfterBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;
import com.longcheng.lifecareplan.modular.mine.bill.activity.EngryRecordActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 激活能量
 */
public class ActivatEnergyActivity extends BaseActivityMVP<ActivatEnergyContract.View, ActivatEnergyPresenterImp<ActivatEnergyContract.View>> implements ActivatEnergyContract.View {


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
    @BindView(R.id.activat_iv_wxselect)
    ImageView activatIvWxselect;
    @BindView(R.id.activat_relat_wx)
    RelativeLayout activatRelatWx;
    @BindView(R.id.activat_tv_account)
    TextView activatTvAccount;
    @BindView(R.id.activat_iv_accountselect)
    ImageView activatIvAccountselect;
    @BindView(R.id.activat_relat_account)
    RelativeLayout activatRelatAccount;
    @BindView(R.id.activat_sv)
    MyScrollView activatSv;
    @BindView(R.id.btn_jihuo)
    TextView btnJihuo;

    @BindView(R.id.detailhelp_iv_zfbselect)
    ImageView detailhelpIvZfbselect;
    @BindView(R.id.detailhelp_relat_zfb)
    RelativeLayout detailhelpRelatZfb;

    /**
     * 支付方式激活类型 1现金; 2 微信 ; 3  现金微信混合; 4  支付宝； 5 现金支付宝混合    （默认1
     */
    String payType = "";
    MoneyAdapter mMoneyAdapter;

    private String asset = "0";
    private String user_id;

    /**
     * 混合状态下是否选中微信
     */
    boolean hunheWXStatus = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.activat_relat_wx:
                if (Double.valueOf(asset) >= Double.valueOf(money_select) || Double.valueOf(asset) == 0) {
                    payType = "2";
                    selectPayTypeView();
                } else {
                    if (!payType.equals("3")) {
                        payType = "3";
                        selectPayTypeView();
                    }
                }
                break;
            case R.id.activat_relat_account:
                if (Double.valueOf(asset) >= Double.valueOf(money_select)) {
                    payType = "1";
                    selectPayTypeView();
                } else if (Double.valueOf(asset) > 0) {
//                    if (hunheWXStatus) {
//                        payType = "2";
//                        selectPayTypeView();
//                    } else {
//                        payType = "4";
//                        selectPayTypeView();
//                    }
//                } else {
                    if (hunheWXStatus) {
                        if (payType.equals("3")) {//当选中时并且payType = "3";时取消选中
                            payType = "2";
                            selectPayTypeView();
                        } else {
                            //优化：两个都选
                            payType = "3";
                            selectPayTypeView();
                        }
                    } else {
                        if (!payType.equals("5")) {
                            payType = "5";
                            selectPayTypeView();
                        } else {
                            payType = "4";
                            selectPayTypeView();
                        }
                    }
                }
                break;
            case R.id.detailhelp_relat_zfb:
                if (Double.valueOf(asset) >= Double.valueOf(money_select) || Double.valueOf(asset) == 0) {
                    payType = "4";
                    selectPayTypeView();
                } else {
                    if (!payType.equals("5")) {
                        payType = "5";
                        selectPayTypeView();
                    }
                }
                break;
            case R.id.btn_jihuo:
                mPresent.assetRecharge(user_id, money_select, asset, payType);
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
        pageTopTvName.setText("激活生命能量");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        activatRelatWx.setOnClickListener(this);
        detailhelpRelatZfb.setOnClickListener(this);
        activatRelatAccount.setOnClickListener(this);
        btnJihuo.setOnClickListener(this);
        activatGvMoney.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList != null && mList.size() > 0) {
                    for (int i = 0; i < mList.size(); i++) {
                        if (i == position) {
                            mList.get(position).setIs_default(1);
                        } else {
                            mList.get(i).setIs_default(0);
                        }
                    }
                    setAssetView(mList.get(position), true);
                    mMoneyAdapter.setList(mList);
                    mMoneyAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getRechargeInfo(user_id);
    }

    private void selectPayTypeView() {
        activatIvWxselect.setVisibility(View.GONE);
        activatRelatWx.setBackgroundResource(R.drawable.corners_bg_blackhealth);
        activatIvAccountselect.setVisibility(View.GONE);
        activatRelatAccount.setBackgroundResource(R.drawable.corners_bg_blackhealth);
        detailhelpIvZfbselect.setVisibility(View.GONE);
        detailhelpRelatZfb.setBackgroundResource(R.drawable.corners_bg_blackhealth);
        if (payType.equals("2")) {
            activatIvWxselect.setVisibility(View.VISIBLE);
            activatRelatWx.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatWx.setPadding(0, 0, 0, 0);
        } else if (payType.equals("1")) {
            activatIvAccountselect.setVisibility(View.VISIBLE);
            activatRelatAccount.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatAccount.setPadding(0, 0, 0, 0);
        } else if (payType.equals("3")) {
            hunheWXStatus = true;
            activatIvWxselect.setVisibility(View.VISIBLE);
            activatRelatWx.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatWx.setPadding(0, 0, 0, 0);
            activatIvAccountselect.setVisibility(View.VISIBLE);
            activatRelatAccount.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatAccount.setPadding(0, 0, 0, 0);
        } else if (payType.equals("4")) {
            detailhelpIvZfbselect.setVisibility(View.VISIBLE);
            detailhelpRelatZfb.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelpRelatZfb.setPadding(0, 0, 0, 0);
        } else if (payType.equals("5")) {
            hunheWXStatus = false;
            detailhelpIvZfbselect.setVisibility(View.VISIBLE);
            detailhelpRelatZfb.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelpRelatZfb.setPadding(0, 0, 0, 0);
            activatIvAccountselect.setVisibility(View.VISIBLE);
            activatRelatAccount.setBackgroundResource(R.drawable.corners_bg_redbian);
            activatRelatAccount.setPadding(0, 0, 0, 0);
        }
    }

    String money_select = "0";

    /**
     * 显示选中的生命能量
     *
     * @param mEnergyItemBean
     */
    private void setAssetView(EnergyItemBean mEnergyItemBean, boolean selectstatus) {
        activatTvNum.setText(mEnergyItemBean.getTotal_energy());
        activatTvCont.setText("激活" + mEnergyItemBean.getFirst_energy() + "+赠送" + mEnergyItemBean.getPresenter_energy());
        money_select = mEnergyItemBean.getMoney();
        if (Double.valueOf(asset) >= Double.valueOf(money_select)) {
            payType = "1";
            selectPayTypeView();
        } else if (Double.valueOf(asset) == 0) {
            if (!selectstatus) {
                payType = "2";
                selectPayTypeView();
            }
        } else {
            if (hunheWXStatus) {
                payType = "3";
                selectPayTypeView();
            } else {
                payType = "5";
                selectPayTypeView();
            }
        }
    }

    @Override
    protected ActivatEnergyPresenterImp<ActivatEnergyContract.View> createPresent() {
        return new ActivatEnergyPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    List<EnergyItemBean> mList;

    @Override
    public void ListSuccess(GetEnergyListDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            EnergyAfterBean mEnergyAfterBean = responseBean.getData();
            if (mEnergyAfterBean != null) {
                asset = mEnergyAfterBean.getAsset();
                activatTvAccount.setText("现金余额:" + asset);
                mList = mEnergyAfterBean.getEnergys();
                if (mList != null && mList.size() > 0) {
                    mMoneyAdapter = new MoneyAdapter(mContext, mList);
                    activatGvMoney.setAdapter(mMoneyAdapter);
                    for (EnergyItemBean mEnergyItemBean : mList) {
                        if (mEnergyItemBean.getIs_default() == 1) {
                            setAssetView(mEnergyItemBean, false);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void ListError() {

    }

    @Override
    public void GetPayWXSuccess(PayWXDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            PayWXAfterBean payWeChatBean = (PayWXAfterBean) responseBean.getData();
            if (payType.equals("1")) {
                jihuoSuccess();
            } else if (payType.equals("2") || payType.equals("3")) {
                Log.e(TAG, payWeChatBean.toString());
                PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
            } else if (payType.equals("4") || payType.equals("5")) {
                String payInfo = payWeChatBean.getPayInfo();
                payZfb(payInfo);
            }
        }
    }

    /**
     * 支付宝2.0sdk集成时的难点在于订单参数顺序的一致性
     *
     * @param payInfo
     */
    public void payZfb(String payInfo) {
        PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
            @Override
            public void onSuccess() {
                jihuoSuccess();
            }

            @Override
            public void onFailure(String error) {

            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantManager.BroadcastReceiver_PAY_ACTION);
        registerReceiver(mReceiver, filter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);

            if (errCode == WXPayEntryActivity.PAY_SUCCESS) {
                jihuoSuccess();
            } else if (errCode == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (errCode == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            }
        }
    };
}
