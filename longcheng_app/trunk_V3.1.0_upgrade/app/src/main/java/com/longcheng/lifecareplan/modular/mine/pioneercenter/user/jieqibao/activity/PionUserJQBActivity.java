package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.jieqibao.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.longcheng.lifecareplan.modular.mine.bill.activity.PionUserFQBEDuBillActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.PionUserJQBBillActivity;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity.PionRecoverCashContract;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity.PionRecoverCashPresenterImp;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean.AcountAfterBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 *
 */
public class PionUserJQBActivity extends BaseActivityMVP<PionRecoverCashContract.View, PionRecoverCashPresenterImp<PionRecoverCashContract.View>> implements PionRecoverCashContract.View {


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
    @BindView(R.id.tv_alljqbmoney)
    TextView tvAlljqbmoney;
    @BindView(R.id.tv_fqbedu)
    TextView tvFqbedu;
    @BindView(R.id.et_moneyrever)
    EditText etMoneyrever;
    @BindView(R.id.tv_convert)
    TextView tvConvert;
    @BindView(R.id.layout_top)
    LinearLayout layout_top;

    String fuqibao_limit = "0";
    String moneyCont;

    @Override
    public void onClick(View v) {
        Intent intent;
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.pagetop_layout_rigth:
                intent = new Intent(mActivity, PionUserJQBBillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.tv_convert:
                moneyCont = etMoneyrever.getText().toString();
                if (!TextUtils.isEmpty(moneyCont) && Double.valueOf(moneyCont) > 0) {
                    setDialog();
                } else {
                    ToastUtils.showToast("请输入转换福祺宝数量");
                }
                break;
            case R.id.layout_top:
                intent = new Intent(mActivity, PionUserFQBEDuBillActivity.class);
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
        return R.layout.pioneer_userjqb;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("节气宝");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        layout_top.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        tvConvert.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        pagetopIvRigth.setVisibility(View.GONE);
        pageTopTvRigth.setVisibility(View.VISIBLE);
        pageTopTvRigth.setText("账单");
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etMoneyrever, 9);
        etMoneyrever.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //还原数据
                if (etMoneyrever != null) {
                    moneyCont = etMoneyrever.getText().toString();
                    if (!TextUtils.isEmpty(moneyCont)) {
                        if (Double.parseDouble(moneyCont) > Double.parseDouble(fuqibao_limit)) {
                            moneyCont = "" + PriceUtils.getInstance().getStrWeiShu0(fuqibao_limit);
                            etMoneyrever.setText(moneyCont);
                            etMoneyrever.setSelection(moneyCont.length());
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
    }


    @Override
    public void initDataAfter() {
    }

    MyDialog selectDialog;

    public void setDialog() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_tixiandel);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_title = selectDialog.findViewById(R.id.tv_title);
            TextView tv_title2 = selectDialog.findViewById(R.id.tv_title2);
            TextView tv_title3 = selectDialog.findViewById(R.id.tv_title3);
            TextView tv_off = selectDialog.findViewById(R.id.tv_off);
            TextView tv_sure = selectDialog.findViewById(R.id.tv_sure);
            tv_sure.setText("确定转换");
            tv_title.setText("请确认是否转换为福祺宝");
            tv_title2.setVisibility(View.GONE);
            tv_title3.setVisibility(View.GONE);
            tv_title.setTextColor(getResources().getColor(R.color.red));
            tv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    mPresent.ConvertJQB(moneyCont);
                }
            });
        } else {
            selectDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        mPresent.getUserJQBInfo(UserUtils.getUserId(mContext));
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

    }

    @Override
    public void getwalletSuccess(AcountInfoDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            AcountAfterBean mEnergyAfterBean = responseBean.getData();
            String jieqibao = mEnergyAfterBean.getJieqibao();//
            fuqibao_limit = mEnergyAfterBean.getFuqibao_limit();//
            tvAlljqbmoney.setText(jieqibao);
            tvFqbedu.setText(fuqibao_limit);
        }
    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            ToastUtils.showToast("转换成功");
            refreshData();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
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

}
