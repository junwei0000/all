package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.PushQueueAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.bill.activity.PionUserZYBBillActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBHistoryDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.bean.PionZFBSelectDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.cz.PionCZRecordActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui.ZYBZuDuiActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.zudui.ZYBZuDuiSelectMoneyActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.pay.PayCallBack;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 祝佑宝
 */
public class PionZFBActivity extends BaseActivityMVP<PionZFBContract.View, PionZFBPresenterImp<PionZFBContract.View>> implements PionZFBContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.tv_allmoney)
    TextView tvAllmoney;
    @BindView(R.id.et_moneysell)
    EditText etMoneysell;
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
    @BindView(R.id.item_tv_typetitle)
    TextView itemTvTypetitle;
    @BindView(R.id.item_layout_rank)
    LinearLayout itemLayoutRank;
    @BindView(R.id.item_tv_cancel)
    TextView itemTvCancel;
    @BindView(R.id.lv_pushdata)
    MyListView lvPushdata;
    @BindView(R.id.layout_dc)
    LinearLayout layoutDc;
    @BindView(R.id.tv_zfsrecord)
    TextView tvZfsrecord;
    @BindView(R.id.tv_jihuo)
    TextView tvJihuo;
    @BindView(R.id.layout_jihuo)
    LinearLayout layout_jihuo;
    @BindView(R.id.tv_zudui)
    TextView tv_zudui;

    private String user_zhufubao_order_id, userRechargeListUrl;

    String inputmoney = "0";
    String uzhufubao = "0", serviceCharge = "0.005", fuqibao = "0";
    int zhuyoubaoTeamRoomId;//0时，代表没有房间

    double minMoney = 50;

    @Override
    public void onClick(View v) {
        Intent intent;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                intent = new Intent(mActivity, PionUserZYBBillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.layout_jihuo:
                intent = new Intent(mActivity, PionZFBJiHuoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.tv_zfsrecord:
                intent = new Intent(mActivity, PionCZRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.tv_jihuo:
                inputmoney = etMoneysell.getText().toString();
                if (!TextUtils.isEmpty(inputmoney) && Double.valueOf(inputmoney) >= minMoney) {
                    showPopupWindow();
                } else {
                    ToastUtils.showToast("请输入请购数量 " + minMoney + "元起");
                }
                break;
            case R.id.item_tv_cancel:
                mPresent.cancelPiPei(user_zhufubao_order_id);
                break;
            case R.id.tv_zudui:
                intent = new Intent(mActivity, ZYBZuDuiSelectMoneyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_userzyb;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("祝佑宝");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        itemTvCancel.setOnClickListener(this);
        tvZfsrecord.setOnClickListener(this);
        tvJihuo.setOnClickListener(this);
        layout_jihuo.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        tv_zudui.setOnClickListener(this);
        pagetopIvRigth.setVisibility(View.GONE);
        pageTopTvRigth.setVisibility(View.VISIBLE);
        pageTopTvRigth.setText("账单");
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etMoneysell, 9);
        etMoneysell.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //还原数据
                if (etMoneysell != null) {
                    inputmoney = etMoneysell.getText().toString();
                    if (!TextUtils.isEmpty(inputmoney) && Double.parseDouble(inputmoney) > 0) {
                        tvJihuo.setBackgroundResource(R.mipmap.my_fuqibao_commit);
                    } else {
                        tvJihuo.setBackgroundResource(R.mipmap.my_fuqibao_notcommit);
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        mPresent.getwalletInfo(UserUtils.getUserId(mContext));
    }

    @Override
    protected PionZFBPresenterImp<PionZFBContract.View> createPresent() {
        return new PionZFBPresenterImp<>(mContext, this);
    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void getwalletSuccess(PionZFBDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            PionZFBAfterBean mEnergyAfterBean = responseBean.getData();
            userRechargeListUrl = mEnergyAfterBean.getUserRechargeListUrl();
            uzhufubao = mEnergyAfterBean.getUzhufubao();
            fuqibao = mEnergyAfterBean.getFuqibao();
            serviceCharge = mEnergyAfterBean.getServiceCharge();
            zhuyoubaoTeamRoomId = mEnergyAfterBean.getZhuyoubaoTeamRoomId();
            tvAllmoney.setText(uzhufubao);
            showPushView(mEnergyAfterBean);
        }
    }

    @Override
    public void SelectSuccess(PionZFBSelectDataBean responseBean) {

    }

    @Override
    public void historySuccess(PionZFBHistoryDataBean responseBean) {

    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            refreshData();
            ToastUtils.showToast(responseBean.getMsg());
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void PaySuccess(PayWXDataBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            PayWXAfterBean payWeChatBean = responseBean.getData();
            if (pay_method == 2) {
                PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
            } else if (pay_method == 5) {//zhifubao
                String payInfo = payWeChatBean.getPayInfo();
                PayUtils.Alipay(mActivity, payInfo, new PayCallBack() {
                    @Override
                    public void onSuccess() {
                        refreshData();
                        ToastUtils.showToast("请购祝佑宝成功");
                    }

                    @Override
                    public void onFailure(String error) {
                    }
                });
            } else {
                refreshData();
                ToastUtils.showToast(responseBean.getMsg());
            }
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    private void showPushView(PionZFBAfterBean mEnergyAfterBean) {
        PionZFBItemBean daichong_order = mEnergyAfterBean.getDaichong_order();
        if (daichong_order != null && !TextUtils.isEmpty(daichong_order.getCreate_time())) {
            layoutDc.setVisibility(View.VISIBLE);
            user_zhufubao_order_id = daichong_order.getEntrepreneurs_zhufubao_order_id();
            GlideDownLoadImage.getInstance().loadCircleImage(daichong_order.getAvatar(), itemIvThumb);
            String name = CommonUtil.setName(daichong_order.getUser_name());
            itemTvName.setText(name);
            itemTvTypetitle.setText("匹配中…");
            itemTvJieeqi.setText(daichong_order.getJieqi_name());
            itemTvTime.setText(daichong_order.getCreate_time());
            itemTvMoney.setText(daichong_order.getPrice() + "祝佑宝");

            ArrayList<EnergyItemBean> pushQueueItems = mEnergyAfterBean.getPushQueueItems();
            if (pushQueueItems != null && pushQueueItems.size() > 0) {
                if (mPushQueueAdapter == null) {
                    mPushQueueAdapter = new PushQueueAdapter(mContext, pushQueueItems);
                    lvPushdata.setAdapter(mPushQueueAdapter);
                } else {
                    mPushQueueAdapter.setList(pushQueueItems);
                    mPushQueueAdapter.notifyDataSetChanged();
                }
            } else {
                if (mPushQueueAdapter != null) {
                    mPushQueueAdapter.setList(pushQueueItems);
                    mPushQueueAdapter.notifyDataSetChanged();
                }
            }
        } else {
            layoutDc.setVisibility(View.GONE);
        }
    }

    PushQueueAdapter mPushQueueAdapter;


    @Override
    public void ListError() {
    }

    MyDialog selectDialog;
    TextView tv_server, tv_zfb, tv_fqb, btn_helpsure, tv_select;

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_pion_userzyb);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = selectDialog.findViewById(R.id.layout_cancel);
            tv_server = selectDialog.findViewById(R.id.tv_server);
            tv_zfb = selectDialog.findViewById(R.id.tv_zfb);
            tv_select = selectDialog.findViewById(R.id.tv_select);
            tv_fqb = selectDialog.findViewById(R.id.tv_fqb);
            btn_helpsure = selectDialog.findViewById(R.id.btn_helpsure);
            layout_cancel.setOnClickListener(dialogClick);
            btn_helpsure.setOnClickListener(dialogClick);
            tv_zfb.setOnClickListener(dialogClick);
            tv_select.setOnClickListener(dialogClick);
            tv_fqb.setOnClickListener(dialogClick);
        } else {
            selectDialog.show();
        }
        pay_method = 5;
        selectPayTypeView();
        tv_server.setText("（手续费为每笔" + (Double.parseDouble(serviceCharge) * 100) + "%）");
        tv_fqb.setText("福祺宝：" + fuqibao);
    }

    int pay_method = 5;
    //付款类型    pay_method    5 支付宝 ，  = 3  是福祺宝  4 选择创业导师
    View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.layout_cancel:
                    selectDialog.dismiss();
                    break;
                case R.id.tv_zfb:
                    pay_method = 5;
                    selectPayTypeView();
                    break;
                case R.id.tv_select:
                    pay_method = 4;
                    selectPayTypeView();
                    break;
                case R.id.tv_fqb:
                    pay_method = 3;
                    selectPayTypeView();
                    break;
                case R.id.btn_helpsure://立即互祝
                    selectDialog.dismiss();
                    if (pay_method == 4) {
                        Intent intent = new Intent(mActivity, PionZFBSelectActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("inputmoney", inputmoney);
                        intent.putExtra("pay_method", pay_method);
                        startActivity(intent);
                    } else {
                        mPresent.entrepreneursuserRecharge(inputmoney, pay_method, "0");
                    }
                    break;
                default:

                    break;
            }
        }
    };

    private void selectPayTypeView() {
        tv_select.setTextColor(getResources().getColor(R.color.text_contents_color));
        tv_zfb.setTextColor(getResources().getColor(R.color.text_contents_color));
        tv_fqb.setTextColor(getResources().getColor(R.color.text_contents_color));
        tv_select.setBackgroundResource(R.drawable.corners_bg_goodgray);
        tv_zfb.setBackgroundResource(R.drawable.corners_bg_goodgray);
        tv_fqb.setBackgroundResource(R.drawable.corners_bg_goodgray);
        if (pay_method == 5) {
            tv_server.setVisibility(View.GONE);
            tv_zfb.setBackgroundResource(R.drawable.corners_bg_red);
            tv_zfb.setTextColor(getResources().getColor(R.color.white));
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_zfb, R.color.red);
        } else if (pay_method == 3) {
            tv_server.setVisibility(View.VISIBLE);
            tv_fqb.setBackgroundResource(R.drawable.corners_bg_red);
            tv_fqb.setTextColor(getResources().getColor(R.color.white));
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_fqb, R.color.red);
        } else if (pay_method == 4) {
            tv_server.setVisibility(View.GONE);
            tv_select.setBackgroundResource(R.drawable.corners_bg_red);
            tv_select.setTextColor(getResources().getColor(R.color.white));
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_select, R.color.red);
        }
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
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

    /**
     * 微信支付回调广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);
            Log.e("BroadcastReceiver", "errCode=" + errCode);
            if (errCode == WXPayEntryActivity.PAY_SUCCESS) {
                refreshData();
                ToastUtils.showToast("请购祝佑宝成功");
            } else if (errCode == WXPayEntryActivity.PAY_FAILE) {
                ToastUtils.showToast("支付失败");
            } else if (errCode == WXPayEntryActivity.PAY_CANCLE) {
                ToastUtils.showToast("支付取消");
            }
        }
    };
}
