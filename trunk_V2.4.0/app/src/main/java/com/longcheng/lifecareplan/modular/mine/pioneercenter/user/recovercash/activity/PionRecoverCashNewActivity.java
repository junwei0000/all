package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
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
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.PushQueueAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.bill.activity.PionUserFQBBillActivity;
import com.longcheng.lifecareplan.modular.mine.emergencys.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.facerecognition.FaceRecognitionUtils;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.tx.PionTiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountAfterBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 提现
 */
public class PionRecoverCashNewActivity extends BaseActivityMVP<PionRecoverCashContract.View, PionRecoverCashPresenterImp<PionRecoverCashContract.View>> implements PionRecoverCashContract.View {


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
    @BindView(R.id.tv_servicemoney)
    TextView tvServicemoney;
    @BindView(R.id.tv_allfqbnum)
    TextView tvAllfqbnum;
    @BindView(R.id.tv_todayfqbnum)
    TextView tvTodayfqbnum;
    @BindView(R.id.et_moneysell)
    EditText etMoneysell;
    @BindView(R.id.tv_allsell)
    TextView tvAllsell;
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
    @BindView(R.id.tv_proportion)
    TextView tv_proportion;
    @BindView(R.id.tv_thirdmoney)
    TextView tv_thirdmoney;


    private String user_zhufubao_order_id;
    String inputmoney = "", proportion = "2.4", service_charge = "0", minservice_charge = "1", faceMoney = "1";
    String fuqibao = "0";
    String total_money = "0";
    FaceRecognitionUtils mFaceRecognitionUtils;
    boolean isfaceClick = false;
    String faceerrormsg = "暂无身份证信息";

    int isHaveMatch;
    int minMony = 24;
    int maxMoony = 3650;

    boolean clicckJiHuo = false;

    @Override
    public void onClick(View v) {
        Intent intent;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                intent = new Intent(mActivity, PionUserFQBBillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.tv_zfsrecord:
                intent = new Intent(mActivity, PionTiXianRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.tv_jihuo:
                inputmoney = etMoneysell.getText().toString();
                if (isHaveMatch != 0) {
                    ToastUtils.showToast("您已有匹配中订单");
                    break;
                }
                if (!TextUtils.isEmpty(inputmoney)) {
                    if (Double.parseDouble(inputmoney) < minMony) {
                        ToastUtils.showToast("每日敬售金额最低24福祺宝，最高3650福祺宝");
                        break;
                    }
                    if (Double.parseDouble(inputmoney) > maxMoony) {
                        ToastUtils.showToast("每日敬售金额最低24福祺宝，最高3650福祺宝");
                        break;
                    }
                    if (isfaceClick) {
                        mFaceRecognitionUtils.requestCameraPerm();
                    } else {
                        ToastUtils.showToast(faceerrormsg);
                    }
                } else {
                    ToastUtils.showToast("请输入福祺宝数量");
                }
                break;
            case R.id.item_tv_cancel:
                mPresent.cancelPiPei(user_zhufubao_order_id);
                break;
            case R.id.tv_allsell:
                allWithdraw();
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
        return R.layout.pioneer_recovercashnew;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("福祺宝");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        itemTvCancel.setOnClickListener(this);
        tvZfsrecord.setOnClickListener(this);
        tvJihuo.setOnClickListener(this);
        tvAllsell.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        pagetopIvRigth.setVisibility(View.GONE);
        pageTopTvRigth.setVisibility(View.VISIBLE);
        pageTopTvRigth.setText("账单");
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etMoneysell, 9);
        etMoneysell.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etMoneysell.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //还原数据
                if (etMoneysell != null) {
                    inputmoney = etMoneysell.getText().toString();
                    if (!TextUtils.isEmpty(inputmoney)) {
                        if (inputmoney.startsWith(".")) {
                            if (inputmoney.length() > 3) {
                                inputmoney = inputmoney.substring(0, 3);
                            }
                            String cont = "0" + inputmoney;
                            etMoneysell.setText(cont);
                            etMoneysell.setSelection(etMoneysell.getText().length());
                        }
                        inputmoney = etMoneysell.getText().toString();
                        if (inputmoney.contains(".") && !inputmoney.endsWith(".")) {
                            String sd = inputmoney.substring(inputmoney.indexOf(".") + 1);
                            if (sd.length() > 2) {
                                inputmoney = inputmoney.substring(0, inputmoney.indexOf(".") + 3);
                                etMoneysell.setText(inputmoney);
                                etMoneysell.setSelection(etMoneysell.getText().length());
                            }
                        }
                    }
                    inputmoney = etMoneysell.getText().toString();
                    getServiceCharge(inputmoney);
                    if (!TextUtils.isEmpty(inputmoney) && Double.parseDouble(inputmoney) > 0) {
                        String total_money_ = PriceUtils.getInstance().gteAddSumPrice(inputmoney, service_charge);
                        total_money = PriceUtils.getInstance().gteAddSumPrice(total_money_, faceMoney);
                        total_money = PriceUtils.getInstance().seePrice(total_money);
                        if (Double.parseDouble(fuqibao) < Double.parseDouble(total_money)) {
                            Log.e("allWithdraw", "inputmoney= " + total_money + "      " + clickallWithdrawStatus);
                            allWithdraw();
                        }
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
        //设置字符过滤
        etMoneysell.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Log.e("setFilters", "source=" + source + "  ;cashEtMoney=" + etMoneysell.getText().toString() + "  start=" + start + "  end=" + end + "  dstart=" + dstart + "  dend=" + dend);
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if (mlength == 3) {
                        etMoneysell.setSelection(dest.length());
                        return "";
                    }
                }
                Log.e("setFilters", "source=" + source + "  ;dest=" + dest);
                return null;
            }
        }});
    }

    /**
     * 标记是否点击 全额提现  ，避免走输入监听
     */
    boolean clickallWithdrawStatus = false;

    /**
     * 获取手续费
     *
     * @param money
     */
    private void getServiceCharge(String money) {
        if (clickallWithdrawStatus) {//防止全额提现时更新Editview，循环计算
            clickallWithdrawStatus = false;
            return;
        }
        if (!TextUtils.isEmpty(money) && Double.parseDouble(money) > 0) {
            String proportion_ = PriceUtils.getInstance().gteMultiplySumPrice(proportion, "0.01");
            service_charge = PriceUtils.getInstance().gteMultiplySumPrice(money, proportion_);
            service_charge = PriceUtils.getInstance().getStrWeiShuUp(service_charge);
            Log.e("getServiceCharge", "service_charge=" + service_charge);
            if (!PriceUtils.getInstance().compareToPrice(minservice_charge, service_charge)) {
                service_charge = minservice_charge;
            }
        } else {
            service_charge = "0";
        }

        tvServicemoney.setText("当前手续费：¥" + service_charge);
    }

    /**
     * 全部
     */
    private void allWithdraw() {
        String mon = PriceUtils.getInstance().gteSubtractSumPrice(faceMoney, fuqibao);
        getServiceCharge(mon);
        Log.e("allWithdraw", " " + mon + "      " + clickallWithdrawStatus);
        String apply_withdrawals_cash = PriceUtils.getInstance().gteSubtractSumPrice(service_charge, mon);
        apply_withdrawals_cash = PriceUtils.getInstance().seePrice(apply_withdrawals_cash);
        if (!TextUtils.isEmpty(apply_withdrawals_cash) && Double.parseDouble(apply_withdrawals_cash) < 0) {
            apply_withdrawals_cash = "0";
        }
        Log.e("allWithdraw", "apply_withdrawals_cash=" + apply_withdrawals_cash);
        clickallWithdrawStatus = true;
        etMoneysell.setText("" + apply_withdrawals_cash);
        etMoneysell.setSelection(etMoneysell.getText().length());
        total_money = fuqibao;
    }

    @Override
    public void initDataAfter() {
        mFaceRecognitionUtils = new FaceRecognitionUtils(mActivity, mHandler);
        mPresent.isCertify(UserUtils.getUserId(mContext));
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
    protected PionRecoverCashPresenterImp<PionRecoverCashContract.View> createPresent() {
        return new PionRecoverCashPresenterImp<>(mContext, this);
    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void getCardInfoSuccess(CertifyBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            isfaceClick = true;
            String card = responseBean.getData().getIdNo();
            String name = responseBean.getData().getName();
            mFaceRecognitionUtils.setCardInfo(card, name);
        } else {
            faceerrormsg = responseBean.getMsg();
            ToastUtils.showToast(faceerrormsg);
        }
    }

    @Override
    public void getwalletSuccess(AcountInfoDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            AcountAfterBean mEnergyAfterBean = responseBean.getData();
            fuqibao = mEnergyAfterBean.getAllowAsset();
            tvAllmoney.setText(fuqibao);
            proportion = mEnergyAfterBean.getProportion();
            faceMoney = mEnergyAfterBean.getFaceMoney();
            tv_proportion.setText("手续费为每笔" + proportion + "%");
            tv_thirdmoney.setText("第三方服务费：¥" + faceMoney);
            tvAllfqbnum.setText("可敬售福祺宝：" + fuqibao);
            String todayFuqibao = mEnergyAfterBean.getTodayFuqibao();
            tvTodayfqbnum.setText("今日可敬售：" + todayFuqibao);
            showPushView(mEnergyAfterBean);
            isHaveMatch = mEnergyAfterBean.getIsHaveMatch();
            if (isHaveMatch == 1) {
                Intent intent = new Intent(mActivity, PionRecoverMatchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                doFinish();
            } else if (isHaveMatch == 2) {
                Intent intent = new Intent(mActivity, PionTiXianRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                doFinish();
            }
        }
    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            refreshData();
            if (clicckJiHuo) {
                clicckJiHuo = false;
                showPayTypeDialog();
            }
            ToastUtils.showToast(responseBean.getMsg());
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    MyDialog mPayTypeDialog;

    /**
     * 显示填写支付方式提示
     */
    public void showPayTypeDialog() {
        if (mPayTypeDialog == null) {
            mPayTypeDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_ordertishi);// 创建Dialog并设置样式主题
            mPayTypeDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mPayTypeDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mPayTypeDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mPayTypeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            mPayTypeDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_ok = mPayTypeDialog.findViewById(R.id.tv_ok);
            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayTypeDialog.dismiss();
                    Intent intent = new Intent(mActivity, PionRecoverMatchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            });
        } else {
            mPayTypeDialog.show();
        }
    }

    private void showPushView(AcountAfterBean mEnergyAfterBean) {
        AcountItemBean daichong_order = mEnergyAfterBean.getUserMatchInfo();
        if (daichong_order != null && !TextUtils.isEmpty(daichong_order.getCreate_time())) {
            layoutDc.setVisibility(View.GONE);
            user_zhufubao_order_id = daichong_order.getUser_zhufubao_order_id();
            GlideDownLoadImage.getInstance().loadCircleImage(daichong_order.getCurrent_user_avatar(), itemIvThumb);
            String name = CommonUtil.setName(daichong_order.getCurrent_user_name());
            itemTvName.setText(name);
            itemTvTypetitle.setText("匹配中…");
            itemTvJieeqi.setText(daichong_order.getCurrent_user_jieqi_name());
            itemTvTime.setText(daichong_order.getCreate_time());
            itemTvMoney.setText(daichong_order.getPrice() + "福祺宝");

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

    int is_face_pay = 1;//默认为0  ， 1 为刷脸

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FaceRecognitionUtils.faceSucecess:
                    clicckJiHuo = true;
                    mPresent.UserFQBSell(total_money, service_charge, inputmoney, is_face_pay);
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mFaceRecognitionUtils != null)
            mFaceRecognitionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
