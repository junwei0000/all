package com.longcheng.lifecareplan.modular.mine.pioneercenter.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.Toolbar;
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
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerCounterDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.UserBankDataBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

/**
 * 24节气创业中心--账户平衡
 */
public class PioneerCounterpoiseActivity extends BaseActivityMVP<PioneerContract.View, PioneerPresenterImp<PioneerContract.View>> implements PioneerContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_zfballmoney)
    TextView tvZfballmoney;
    @BindView(R.id.tv_zfbyue)
    TextView tvZfbyue;
    @BindView(R.id.tv_zfbcounterpoisenum)
    TextView tvZfbcounterpoisenum;
    @BindView(R.id.tv_zfbbuy)
    TextView tvZfbbuy;
    @BindView(R.id.tv_fqballmoney)
    TextView tvFqballmoney;
    @BindView(R.id.tv_fqbyue)
    TextView tvFqbyue;
    @BindView(R.id.tv_fqbcounterpoisenum)
    TextView tvFqbcounterpoisenum;
    @BindView(R.id.tv_fqbsell)
    TextView tvFqbsell;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    private String zhufubao_balance_num = "0";
    private String fuqibao_balance_num = "0";
    int zhufubao_balance_status;
    int fuqibao_balance_status;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.pagetop_layout_rigth:
                showXuFeiDialog();
                break;
            case R.id.tv_zfbbuy:
                if (zhufubao_balance_status == -1) {
                    intent = new Intent(mContext, PioneerCounterpoiseZFbActivity_New.class);
                    intent.putExtra("zhufubao_balance_num", "" + zhufubao_balance_num);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                break;
            case R.id.tv_fqbsell:
                if (fuqibao_balance_status == -1) {
                    intent = new Intent(mContext, PioneerCounterpoiseSellActivity.class);
                    intent.putExtra("fuqibao_balance_num", "" + fuqibao_balance_num);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.pioneer_counterpoise;
    }

    @Override
    public void initView(View view) {
        ImmersionBarUtils.steepStatusBarDark(mActivity, toolbar);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("账户平衡");
        tvFqbsell.setOnClickListener(this);
        tvZfbbuy.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        pagetopIvRigth.setVisibility(View.GONE);
        pageTopTvRigth.setVisibility(View.VISIBLE);
        pageTopTvRigth.setText("查看规则");
        pageTopTvRigth.getPaint().setAntiAlias(true);//抗锯齿
        pageTopTvRigth.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvFqbsell, R.color.red);
        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvZfbbuy, R.color.red);
    }


    @Override
    public void initDataAfter() {
    }


    @Override
    protected PioneerPresenterImp<PioneerContract.View> createPresent() {
        return new PioneerPresenterImp<>(mActivity, this);
    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void MenuInfoSuccess(PioneerDataBean responseBean) {

    }

    @Override
    public void CounterInfoSuccess(PioneerCounterDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            PioneerCounterDataBean.PionCounterItemBean info = responseBean.getData();
            if (info != null) {
                zhufubao_balance_num = info.getZhufubao_balance_num();
                tvZfballmoney.setText(zhufubao_balance_num);
                tvZfbyue.setText(zhufubao_balance_num);
                tvZfbcounterpoisenum.setText(info.getZhufubao_limit_fixed());

                fuqibao_balance_num = info.getFuqibao_balance_num();
                tvFqballmoney.setText(fuqibao_balance_num);
                tvFqbyue.setText(info.getFuqibao());
                tvFqbcounterpoisenum.setText(info.getFuqibao_limit_fixed());

                zhufubao_balance_status = info.getZhufubao_balance_status();
                fuqibao_balance_status = info.getFuqibao_balance_status();
                if (zhufubao_balance_status == -1) {
                    tvZfbbuy.setBackgroundResource(R.drawable.corners_bg_login);
                } else {
                    tvZfbbuy.setBackgroundResource(R.drawable.corners_bg_logingray);
                }
                if (fuqibao_balance_status == -1) {
                    tvFqbsell.setBackgroundResource(R.drawable.corners_bg_login);
                } else {
                    tvFqbsell.setBackgroundResource(R.drawable.corners_bg_logingray);
                }
            }
            String time = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
            SharedPreferencesHelper.put(mActivity, "pioneer_balanceComTime", time);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void paySuccess(PayWXDataBean responseBean) {

    }

    @Override
    public void SellFQBSuccess(ResponseBean responseBean) {

    }

    @Override
    public void applyMoneyListSuccess(PioneerDataListBean responseBean) {

    }

    @Override
    public void backBankInfoSuccess(UserBankDataBean responseBean) {

    }

    @Override
    public void ListSuccess(PionOpenSetRecordDataBean responseBean, int pageback) {

    }

    @Override
    public void CZListSuccess(PioneerDataBean responseBean, int pageback) {

    }

    @Override
    public void Error() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    MyDialog mXuFeiDialog;

    public void showXuFeiDialog() {
        if (mXuFeiDialog == null) {
            mXuFeiDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_guize);// 创建Dialog并设置样式主题
            mXuFeiDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mXuFeiDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mXuFeiDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mXuFeiDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            mXuFeiDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = mXuFeiDialog.findViewById(R.id.layout_cancel);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mXuFeiDialog.dismiss();
                }
            });
        } else {
            mXuFeiDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.getCounterpoiseInfo();
    }

    private void back() {
        boolean pioneer_balancestatus = (boolean) SharedPreferencesHelper.get(mContext, "pioneer_balancestatus", false);
        if (!pioneer_balancestatus) {
            PioneerMenuActivity.menuActivity.doFinish();
        }
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
