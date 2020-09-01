package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.longcheng.lifecareplan.modular.mine.bill.activity.PionUserFQBBillActivity;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.facerecognition.FaceRecognitionUtils;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean.AcountAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean.AcountItemBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.tx.PionTiXianRecordActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
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
    @BindView(R.id.tv_todayfqbnum)
    TextView tvTodayfqbnum;
    @BindView(R.id.et_moneysell)
    EditText etMoneysell;
    @BindView(R.id.tv_zfsrecord)
    TextView tvZfsrecord;
    @BindView(R.id.tv_jihuo)
    TextView tvJihuo;
    @BindView(R.id.tv_thirdmoney)
    TextView tv_thirdmoney;
    @BindView(R.id.layout_poni)
    LinearLayout layout_poni;


    ArrayList<AcountItemBean> proportionList = new ArrayList<>();
    String inputmoney = "", service_charge = "0", minservice_charge = "1", faceMoney = "1";
    String fuqibao = "0";
    String total_money = "0";
    String todayFuqibao = "0";
    FaceRecognitionUtils mFaceRecognitionUtils;
    boolean isfaceClick = false;
    String faceerrormsg = "暂无身份证信息";
    //1 需要刷脸认证  0 不需要刷脸
    int isFaceSwiping;
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
                ToastUtils.showToast("优化升级中");
                break;
//                inputmoney = etMoneysell.getText().toString();
//                if (isHaveMatch != 0) {
//                    ToastUtils.showToast("您已有匹配中订单");
//                    break;
//                }
//                String hour = DatesUtils.getInstance().getNowTime("H");
//                if (Integer.valueOf(hour) < 6 || Integer.valueOf(hour) > 21) {
//                    ToastUtils.showToast("敬售时间每天上午06:00到晚上21:00");
//                    break;
//                }
//                if (!TextUtils.isEmpty(inputmoney)) {
//                    if (Double.parseDouble(inputmoney) < minMony || Double.parseDouble(inputmoney) > maxMoony) {
//                        ToastUtils.showToast("每日敬售金额最低24福祺宝，最高3650福祺宝");
//                        break;
//                    }
//                    if (Double.parseDouble(inputmoney) > Double.parseDouble(todayFuqibao)) {
//                        ToastUtils.showToast("超出今日可敬售福祺宝");
//                        break;
//                    }
//                    if (Double.parseDouble(total_money) > Double.parseDouble(todayFuqibao)) {
//                        String mon = allWithdraw();
//                        ToastUtils.showToast("今日可敬售福祺宝" + mon);
//                        break;
//                    }
//                    if (isFaceSwiping == 1) {
//                        if (isfaceClick) {
//                            mFaceRecognitionUtils.requestCameraPerm();
//                        } else {
//                            ToastUtils.showToast(faceerrormsg);
//                        }
//                    } else {
//                        mHandler.sendEmptyMessage(FaceRecognitionUtils.faceSucecess);
//                    }
//                } else {
//                    ToastUtils.showToast("请输入福祺宝数量");
//                }
//                break;
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
        tvZfsrecord.setOnClickListener(this);
        tvJihuo.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        pagetopIvRigth.setVisibility(View.GONE);
        pageTopTvRigth.setVisibility(View.VISIBLE);
        pageTopTvRigth.setText("账单");
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etMoneysell, 5);
        etMoneysell.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //还原数据
                if (etMoneysell != null) {
                    inputmoney = etMoneysell.getText().toString();
                    getServiceCharge(inputmoney);
                    if (!TextUtils.isEmpty(inputmoney) && Double.parseDouble(inputmoney) > 0) {
                        String total_money_ = PriceUtils.getInstance().gteAddSumPrice(inputmoney, service_charge);
                        total_money = PriceUtils.getInstance().gteAddSumPrice(total_money_, faceMoney);
                        total_money = PriceUtils.getInstance().seePrice(total_money);
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
        String proportion = "2.4";
        if (!TextUtils.isEmpty(money) && Double.parseDouble(money) > 0) {
            if (proportionList != null && proportionList.size() > 0) {
                for (AcountItemBean mAcountItemBean : proportionList) {
                    int start = mAcountItemBean.getStart();
                    int end = mAcountItemBean.getEnd();
                    if (Double.valueOf(money) > start && Double.valueOf(money) <= end) {
                        proportion = mAcountItemBean.getRatio();
                        break;
                    }
                }
            }
            String proportion_ = PriceUtils.getInstance().gteMultiplySumPrice(proportion, "0.01");
            service_charge = PriceUtils.getInstance().gteMultiplySumPrice(money, proportion_);
            service_charge = PriceUtils.getInstance().getStrWeiShuUp(service_charge);
            Log.e("getServiceCharge", "service_charge=" + service_charge);
            if (!PriceUtils.getInstance().compareToPrice(minservice_charge, service_charge)) {
                service_charge = minservice_charge;
            }
            tvServicemoney.setText("温馨提示：当前手续费" + service_charge);
        } else {
            service_charge = "0";
            tvServicemoney.setText("温馨提示：");
        }


    }

    /**
     * 全部
     */
    private String allWithdraw() {
        String mon = PriceUtils.getInstance().gteSubtractSumPrice(faceMoney, fuqibao);
        getServiceCharge(mon);
        String apply_withdrawals_cash = PriceUtils.getInstance().gteSubtractSumPrice(service_charge, mon);
        apply_withdrawals_cash = PriceUtils.getInstance().seePrice(apply_withdrawals_cash);
        if (!TextUtils.isEmpty(apply_withdrawals_cash) && Double.parseDouble(apply_withdrawals_cash) < 0) {
            apply_withdrawals_cash = "0";
        }
        return apply_withdrawals_cash;
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
            proportionList = mEnergyAfterBean.getProportion();
            if (proportionList != null && proportionList.size() > 0) {
                layout_poni.removeAllViews();
                for (int i = 0; i < proportionList.size(); i++) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.pioneer_recovercashnew_item, null);
                    TextView tv_thirdmoney = view.findViewById(R.id.tv_thirdmoney);
                    String cont = (proportionList.get(i).getStart() + 1) + "-" + proportionList.get(i).getEnd() + "：";
                    if (i == 0) {
                        cont = proportionList.get(i).getEnd() + "以内：";
                    }
                    tv_thirdmoney.setText(Html.fromHtml(cont +
                            CommonUtil.getHtmlContent("#666666", "手续费" + proportionList.get(i).getRatio() + "%")));
                    layout_poni.addView(view);
                }
            }
            faceMoney = mEnergyAfterBean.getFaceMoney();
            tv_thirdmoney.setText(Html.fromHtml("第三方服务费：" + CommonUtil.getHtmlContent("#666666", "¥" + faceMoney)));
            todayFuqibao = mEnergyAfterBean.getTodayFuqibao();
            tvTodayfqbnum.setText(" 今日可敬售福祺宝额度" + todayFuqibao);
            isHaveMatch = mEnergyAfterBean.getIsHaveMatch();
            isFaceSwiping = mEnergyAfterBean.getIsFaceSwiping();
        }
    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
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
                }
            });
            mPayTypeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent intent = new Intent(mActivity, PionTiXianRecordActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            });
        } else {
            mPayTypeDialog.show();
        }
    }


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
