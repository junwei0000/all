package com.longcheng.lifecareplan.modular.helpwith.autohelp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
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
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ValueSelectUtils;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.bean.AutoHelpAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.bean.AutoHelpDataBean;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.bean.AutoHelpItemBean;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import java.util.List;

import butterknife.BindView;

/**
 * 智能互祝
 */
public class AutoHelpActivity extends BaseActivityMVP<AutoHelpContract.View, AutoHelpPresenterImp<AutoHelpContract.View>> implements AutoHelpContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.iv_openimg)
    ImageView ivOpenimg;
    @BindView(R.id.tv_openstatus)
    TextView tvOpenstatus;
    @BindView(R.id.tv_open)
    TextView tvOpen;
    @BindView(R.id.tv_type)
    TextView tv_type;

    @BindView(R.id.tv_engry)
    TextView tvEngry;
    @BindView(R.id.iv_engryarrow)
    ImageView ivEngryarrow;
    @BindView(R.id.relat_engry)
    RelativeLayout relatEngry;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.iv_numarrow)
    ImageView ivNumarrow;
    @BindView(R.id.relat_num)
    RelativeLayout relatNum;
    @BindView(R.id.tv_everydayengry)
    TextView tvEverydayengry;
    @BindView(R.id.tv_todaynum)
    TextView tvTodaynum;
    @BindView(R.id.tv_allnum)
    TextView tvAllnum;


    private String user_id;
    /**
     * 是否开启 1：是 0：否
     */
    int is_automation_help;
    int automation_help_type = 4;

    int mutual_help_money_id;
    private String everyday_help_ability;

    int everyday_help_number;

    String[] mutualHelpMoneyListvalues;
    ValueSelectUtils mValueSelectUtils;
    private List<AutoHelpItemBean> mutualHelpMoneyList;

    String[] helpNumbersListvalues;
    ValueSelectUtils mhelpNumbersListUtils;
    private List<AutoHelpItemBean> helpNumbersList;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.relat_engry:
                if (mValueSelectUtils == null) {
                    mValueSelectUtils = new ValueSelectUtils(mActivity, mHandler, MoneyVALUE);
                }
                if (mutualHelpMoneyListvalues != null && mutualHelpMoneyListvalues.length > 0) {
                    mValueSelectUtils.showDialog(mutualHelpMoneyListvalues, "生命能量值");
                }
                break;
            case R.id.relat_num:
                if (mhelpNumbersListUtils == null) {
                    mhelpNumbersListUtils = new ValueSelectUtils(mActivity, mHandler, numVALUE);
                }
                if (helpNumbersListvalues != null && helpNumbersListvalues.length > 0) {
                    mhelpNumbersListUtils.showDialog(helpNumbersListvalues, "每日次数");
                }
                break;
            case R.id.tv_open:
                if (is_automation_help == 0) {
                    is_automation_help = 1;
                } else {
                    is_automation_help = 0;
                }
                saveUpdateAutoHelp();
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.helpwith_autohelp;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("智能互祝");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvOpen.setOnClickListener(this);
        relatEngry.setOnClickListener(this);
        relatNum.setOnClickListener(this);
    }

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstComIn)
            initDataAfter();
    }

    @Override
    public void initDataAfter() {
        is_automation_help = 0;
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getList(user_id);
    }

    private void saveUpdateAutoHelp() {
        mPresent.saveUpdateAutoHelp(user_id, is_automation_help, automation_help_type, mutual_help_money_id, everyday_help_number);
    }

    private final int MoneyVALUE = 2;
    private final int numVALUE = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case MoneyVALUE:
                    int selectindex = bundle.getInt("selectindex");
                    if (mutualHelpMoneyList != null && mutualHelpMoneyList.size() > 0) {
                        mutual_help_money_id = mutualHelpMoneyList.get(selectindex).getMutual_help_money_id();
                    }
                    String ability = bundle.getString("cont");
                    tvEngry.setText(ability);
                    saveUpdateAutoHelp();
                    break;
                case numVALUE:
                    int selectindexs = bundle.getInt("selectindex");
                    if (helpNumbersList != null && helpNumbersList.size() > 0) {
                        everyday_help_number = helpNumbersList.get(selectindexs).getKey();
                    }
                    String num = bundle.getString("cont");
                    tvNum.setText(num);
                    updateNum();
                    saveUpdateAutoHelp();
                    break;
            }
        }
    };


    @Override
    protected AutoHelpPresenterImp<AutoHelpContract.View> createPresent() {
        return new AutoHelpPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void ListSuccess(AutoHelpDataBean responseBean) {
        firstComIn = false;
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            AutoHelpAfterBean relationList = responseBean.getData();

            List<AutoHelpItemBean> automationHelpType = relationList.getAutomationHelpType();
            if (automationHelpType != null && automationHelpType.size() > 0) {
                automation_help_type = automationHelpType.get(0).getKey();
                tv_type.setText(automationHelpType.get(0).getValue());
            }

            mutualHelpMoneyList = relationList.getMutualHelpMoney();
            if (mutualHelpMoneyList != null && mutualHelpMoneyList.size() > 0) {
                mutualHelpMoneyListvalues = new String[mutualHelpMoneyList.size()];
                for (int i = 0; i < mutualHelpMoneyList.size(); i++) {
                    mutualHelpMoneyListvalues[i] = mutualHelpMoneyList.get(i).getAbility();
                }
                mutual_help_money_id = mutualHelpMoneyList.get(0).getMutual_help_money_id();
                everyday_help_ability = mutualHelpMoneyList.get(0).getAbility();
                tvEngry.setText(everyday_help_ability);
            }
            helpNumbersList = relationList.getHelpNumbers();
            if (helpNumbersList != null && helpNumbersList.size() > 0) {
                helpNumbersListvalues = new String[helpNumbersList.size()];
                for (int i = 0; i < helpNumbersList.size(); i++) {
                    helpNumbersListvalues[i] = helpNumbersList.get(i).getValue();
                }
                everyday_help_number = helpNumbersList.get(0).getKey();
                tvNum.setText(helpNumbersList.get(0).getValue());
            }
            AutoHelpItemBean automationHelpInfo = relationList.getAutomationHelpInfo();
            initData(automationHelpInfo);


        }
    }

    @Override
    public void saveUpdateSuccess(ResponseBean responseBean) {
        initDataAfter();
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("401")) {
            showPopupWindow();
        }
    }

    private void changeHelpStatus() {
        if (is_automation_help == 0) {
            ivOpenimg.setBackgroundResource(R.mipmap.blessing_icon_guan);
            tvOpenstatus.setText("智能互祝已关闭");
            tvOpen.setText("开启");
        } else {
            ivOpenimg.setBackgroundResource(R.mipmap.blessing_icon_open);
            tvOpenstatus.setText("智能互祝已开启");
            tvOpen.setText("关闭");
        }
    }

    private void initData(AutoHelpItemBean automationHelpInfo) {
        if (automationHelpInfo != null && !TextUtils.isEmpty(automationHelpInfo.getAutomation_help_id())) {
            is_automation_help = automationHelpInfo.getIs_automation_help();
            mutual_help_money_id = automationHelpInfo.getMutual_help_money_id();
            if (mutualHelpMoneyList != null && mutualHelpMoneyList.size() > 0) {
                for (int i = 0; i < mutualHelpMoneyList.size(); i++) {
                    int m = mutualHelpMoneyList.get(i).getMutual_help_money_id();
                    if (mutual_help_money_id == m) {
                        everyday_help_ability = mutualHelpMoneyList.get(i).getAbility();
                        tvEngry.setText(everyday_help_ability);
                        break;
                    }
                }
            }
            everyday_help_number = automationHelpInfo.getEveryday_help_number();
            if (helpNumbersList != null && helpNumbersList.size() > 0) {
                for (int i = 0; i < helpNumbersList.size(); i++) {
                    int m = helpNumbersList.get(i).getKey();
                    if (everyday_help_number == m) {
                        tvNum.setText(helpNumbersList.get(i).getValue());
                        break;
                    }
                }
            }
            updateNum();
            String showTnum = "今日已帮您送出  <font color=\"#ff443b\">" + automationHelpInfo.getEveryday_already_help_number() + "</font>  次祝福；";
            tvTodaynum.setText(Html.fromHtml(showTnum));
            String showTall = "自您开启智能互祝起，系统共帮您送出：<font color=\"#ff443b\">" + automationHelpInfo.getTotal_help_number()
                    + "</font>  次祝福，<font color=\"#ff443b\">" + automationHelpInfo.getTotal_ability() + "</font>  生命能量。";
            tvAllnum.setText(Html.fromHtml(showTall));
            //是否开启过智能互助 1是0否 开启过的才展示最下面的互助内容说明
            int is_automation_open_help = automationHelpInfo.getIs_automation_open_help();
            if (is_automation_open_help == 1) {
                tvEverydayengry.setVisibility(View.VISIBLE);
                tvTodaynum.setVisibility(View.VISIBLE);
                tvAllnum.setVisibility(View.VISIBLE);
            } else {
                tvEverydayengry.setVisibility(View.INVISIBLE);
                tvTodaynum.setVisibility(View.INVISIBLE);
                tvAllnum.setVisibility(View.INVISIBLE);
            }
        } else {
            tvEverydayengry.setVisibility(View.INVISIBLE);
            tvTodaynum.setVisibility(View.INVISIBLE);
            tvAllnum.setVisibility(View.INVISIBLE);
        }
        changeHelpStatus();
    }

    /**
     * 修改次数
     */
    private void updateNum() {
        if (everyday_help_number == 0) {
            tvEverydayengry.setText("根据您的设置每日需 —— 生命能量；");
        } else {
            String abilall = PriceUtils.getInstance().gteMultiplySumPrice(everyday_help_ability, "" + everyday_help_number);
            String showT = "根据您的设置每日需  <font color=\"#ff443b\">" + abilall + "</font>  生命能量；";
            tvEverydayengry.setText(Html.fromHtml(showT));
        }
    }

    MyDialog selectDialog;

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
            LinearLayout layout_cancel = (LinearLayout) selectDialog.findViewById(R.id.layout_cancel);
            TextView tv_cont1 = (TextView) selectDialog.findViewById(R.id.tv_cont1);
            TextView tv_cont2 = (TextView) selectDialog.findViewById(R.id.tv_cont2);
            TextView tv_cont3 = (TextView) selectDialog.findViewById(R.id.tv_cont3);
            TextView btn_ok = (TextView) selectDialog.findViewById(R.id.btn_ok);
            tv_cont1.setText("您的生命能量余额不足");
            tv_cont2.setVisibility(View.GONE);
            tv_cont3.setVisibility(View.VISIBLE);
            layout_cancel.setVisibility(View.VISIBLE);
            tv_cont3.setText("请激活后再重试~");
            tv_cont3.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
            btn_ok.setText("激活能量");
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivatEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    selectDialog.dismiss();
                }
            });
        } else {
            selectDialog.show();
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
            doFinish();
        }
        return false;
    }
}
