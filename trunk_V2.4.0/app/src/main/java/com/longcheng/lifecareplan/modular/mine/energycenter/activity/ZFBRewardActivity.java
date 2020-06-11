package com.longcheng.lifecareplan.modular.mine.energycenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import com.longcheng.lifecareplan.base.BaseListActivity;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuDataBean;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuItemBean;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditListDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.pay.PayUtils;
import com.longcheng.lifecareplan.utils.pay.PayWXAfterBean;
import com.longcheng.lifecareplan.utils.pay.PayWXDataBean;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 能量中心--祝福宝奖励
 */
public class ZFBRewardActivity extends BaseListActivity<EnergyCenterContract.View, EnergyCenterPresenterImp<EnergyCenterContract.View>> implements EnergyCenterContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_zfb)
    TextView tvZfb;
    @BindView(R.id.tv_bili)
    TextView tvBili;
    @BindView(R.id.tv_zfbsheng)
    TextView tv_zfbsheng;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_money_toengry)
    TextView tvMoneyToengry;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    String unit_price = "0", zhufubao_number = "0", zhufubao_number_limit = "0";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_commit:
                if (haverank) {
                    if (Double.parseDouble(zhufubao_number) > 0) {
                        cardDialog();
                    } else {
                        ToastUtils.showToast("请输入祝福宝数量");
                    }
                } else {
                    ToastUtils.showToast("您未进入昨日排名");
                }
                break;
            case R.id.tv_record:
                Intent intent = new Intent(mActivity, RewardRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.energycenter_zfbreward;
    }

    @Override
    protected View getFooterView() {
        return null;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText("祝福宝奖励");
        tvCommit.setOnClickListener(this);
        tvRecord.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(etMoney, 9);
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //还原数据
                if (etMoney != null) {
                    zhufubao_number = etMoney.getText().toString();
                    if (TextUtils.isEmpty(zhufubao_number)) {
                        zhufubao_number = "0";
                        tvMoneyToengry.setText(zhufubao_number + "元");
                    } else {
                        if (haverank && (Double.parseDouble(zhufubao_number) > Double.parseDouble(zhufubao_number_limit))) {
                            zhufubao_number = zhufubao_number_limit;
                            etMoney.setText(zhufubao_number);
                            etMoney.setSelection(zhufubao_number.length());
                        }
                        String showEngry = PriceUtils.getInstance().gteMultiplySumPrice(zhufubao_number, unit_price);
                        showEngry = PriceUtils.getInstance().seePrice(showEngry);
                        tvMoneyToengry.setText(showEngry + "元");
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
        mPresent.zhufubaoRewardIndex();
    }

    MyDialog telDialog;
    ImageView iv_img;
    LinearLayout layout_card;
    TextView tv_bankname, tv_bankno;

    public void cardDialog() {
        if (telDialog == null) {
            telDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_energycenter_zfbbuy);// 创建Dialog并设置样式主题
            telDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = telDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            telDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = telDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            telDialog.getWindow().setAttributes(p); //设置生效
            layout_card = telDialog.findViewById(R.id.layout_card);
            iv_img = telDialog.findViewById(R.id.iv_img);
            tv_bankname = telDialog.findViewById(R.id.tv_bankname);
            tv_bankno = telDialog.findViewById(R.id.tv_bankno);
            TextView tv_change = telDialog.findViewById(R.id.tv_change);
            TextView tv_sure = telDialog.findViewById(R.id.tv_sure);
            layout_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telDialog.dismiss();
                    Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" +
                            MineFragment.ka_url);
                    startActivity(intent);
                }
            });
            tv_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telDialog.dismiss();
                    Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" +
                            MineFragment.ka_url);
                    startActivity(intent);
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (haveCard) {
                        telDialog.dismiss();
                        mPresent.zhufubaoRewardSubmit(Knp_team_bind_card.getKnp_team_bind_card_id(), zhufubao_number);
                    } else {
                        ToastUtils.showToast("请绑定银行卡");
                    }
                }
            });
        } else {
            telDialog.show();
        }
        if (haveCard) {
            layout_card.setBackgroundResource(R.drawable.corners_bg_red);
            iv_img.setBackgroundResource(R.mipmap.my_ty_icon);
            tv_bankname.setText(Knp_team_bind_card.getBank_name());
            tv_bankname.setTextColor(getResources().getColor(R.color.white));
            tv_bankno.setText(Knp_team_bind_card.getBank_no());
            tv_bankno.setVisibility(View.VISIBLE);
            layout_card.setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            layout_card.setGravity(Gravity.CENTER);
            layout_card.setBackgroundResource(R.drawable.corners_bg_goodgray);
            iv_img.setBackgroundResource(R.mipmap.my_zhufushi_add1);
            tv_bankname.setText("未绑定银行卡");
            tv_bankname.setTextColor(getResources().getColor(R.color.text_noclick_color));
            tv_bankno.setVisibility(View.GONE);
        }

    }


    @Override
    protected EnergyCenterPresenterImp<EnergyCenterContract.View> createPresent() {
        return new EnergyCenterPresenterImp<>(mActivity, this);
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
    public void ListSuccess(DaiFuDataBean responseBean, int backPage) {
    }

    @Override
    public void RefuseSuccess(EditListDataBean responseBean) {
        String status = responseBean.getStatus();
        ToastUtils.showToast(responseBean.getMsg());
        if (status.equals("200")) {
            Intent intent = new Intent(mActivity, RewardRecordActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            doFinish();
        }
    }

    @Override
    public void editAvatarSuccess(EditDataBean responseBean) {

    }

    DaiFuItemBean Knp_team_bind_card;

    boolean haverank = true;

    boolean haveCard = true;

    @Override
    public void backBankInfoSuccess(DaiFuDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            Knp_team_bind_card = responseBean.getData().getKnp_team_bind_card();
            haveCard = Knp_team_bind_card != null && !TextUtils.isEmpty(Knp_team_bind_card.getBank_no());
            DaiFuItemBean userInfo = responseBean.getData().getUser_zhufubao_reward_limit();
            String Ranking = userInfo.getRanking();
            if (TextUtils.isEmpty(Ranking)) {
                Ranking = "--";
                haverank = false;
            } else {
                haverank = true;
            }
            tvNum.setText(Ranking);
            zhufubao_number_limit = userInfo.getZhufubao_number_limit();
            if (TextUtils.isEmpty(zhufubao_number_limit)) {
                zhufubao_number_limit = "--";
            }
            tvZfb.setText(zhufubao_number_limit);
            String Balance_number_limit = userInfo.getBalance_number_limit();
            if (TextUtils.isEmpty(Balance_number_limit)) {
                Balance_number_limit = "--";
            }
            tv_zfbsheng.setText(Balance_number_limit);
            unit_price = userInfo.getUnit_price();
            tvBili.setText("1=" + unit_price);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void BuySuccess(PayWXDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            PayWXAfterBean payWeChatBean = responseBean.getData();
            PayUtils.getWeChatPayHtml(mContext, payWeChatBean);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }


    public void ListError() {
        ToastUtils.showToast(R.string.net_tishi);
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
