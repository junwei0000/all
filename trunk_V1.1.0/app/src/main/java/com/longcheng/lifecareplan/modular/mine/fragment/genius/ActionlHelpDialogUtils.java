package com.longcheng.lifecareplan.modular.mine.fragment.genius;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.adapter.DetailMoneyAdapter;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/30 16:05
 * 意图：
 * **************************天才行动互祝弹层***********************************
 */

public class ActionlHelpDialogUtils {
    MyDialog selectDialog;
    MyGridView detailhelp_gv_money;

    RelativeLayout detailhelp_relat_engry;
    TextView detailhelp_tv_engry;
    ImageView detailhelp_iv_engryselect;

    RelativeLayout detailhelp_relat_account;
    TextView detailhelp_tv_account;
    ImageView detailhelp_iv_accountselect;

    RelativeLayout detailhelp_relat_wx, detailhelp_relat_zfb;
    ImageView detailhelp_iv_wxselect, detailhelp_iv_zfbselect;

    TextView detailhelp_tv_money;

    Handler mHandler;
    int mHandlerID;
    /**
     * 支付方式互祝类型 asset (现金支付)， ability(生命能量支付)， wxpay(微信支付)
     */
    String payType = "wxpay";
    int selectmoney;
    int selectengery;
    ActionDetailMoneyAdapter mMoneyAdapter;
    private int ability;//生命能量
    private String asset;//金额
    Activity context;
    int is_applying_help;
    int mutual_help_money;
    List<DetailItemBean> mutual_help_money_all;
    private TextView btn_helpsure;
    private TextView detailhelp_tv_engrytitle;
    private TextView detailhelp_tv_accounttitle;
    private TextView detailhelp_tv_wxselecttitle;
    private TextView tv_zfbtitle;

    public ActionlHelpDialogUtils(Activity context, Handler mHandler, int mHandlerID) {
        this.mHandlerID = mHandlerID;
        this.mHandler = mHandler;
        this.context = context;
    }

    public void initData(DetailItemBean userInfo, List<DetailItemBean> blessings_list,
                         int is_applying_help, int mutual_help_money,
                         List<DetailItemBean> mutual_help_money_all) {
        if (userInfo != null) {
            ability = userInfo.getAbility();
            asset = userInfo.getAsset();
        }
        if (TextUtils.isEmpty(asset)) {
            asset = "0";
        }
        this.mutual_help_money_all = mutual_help_money_all;
        this.is_applying_help = is_applying_help;
        this.mutual_help_money = mutual_help_money;
    }


    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_geniushelpred);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = (LinearLayout) selectDialog.findViewById(R.id.layout_cancel);
            detailhelp_tv_money = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_money);
            detailhelp_gv_money = (MyGridView) selectDialog.findViewById(R.id.detailhelp_gv_money);

            detailhelp_relat_engry = (RelativeLayout) selectDialog.findViewById(R.id.detailhelp_relat_engry);
            detailhelp_tv_engrytitle = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_engrytitle);
            detailhelp_tv_engry = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_engry);
            detailhelp_iv_engryselect = (ImageView) selectDialog.findViewById(R.id.detailhelp_iv_engryselect);


            detailhelp_relat_account = (RelativeLayout) selectDialog.findViewById(R.id.detailhelp_relat_account);
            detailhelp_tv_accounttitle = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_accounttitle);
            detailhelp_tv_account = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_account);
            detailhelp_iv_accountselect = (ImageView) selectDialog.findViewById(R.id.detailhelp_iv_accountselect);

            detailhelp_relat_wx = (RelativeLayout) selectDialog.findViewById(R.id.detailhelp_relat_wx);
            detailhelp_iv_wxselect = (ImageView) selectDialog.findViewById(R.id.detailhelp_iv_wxselect);
            detailhelp_tv_wxselecttitle = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_wxselecttitle);
            detailhelp_relat_zfb = (RelativeLayout) selectDialog.findViewById(R.id.detailhelp_relat_zfb);
            detailhelp_iv_zfbselect = (ImageView) selectDialog.findViewById(R.id.detailhelp_iv_zfbselect);
            tv_zfbtitle = (TextView) selectDialog.findViewById(R.id.tv_zfbtitle);

            btn_helpsure = (TextView) selectDialog.findViewById(R.id.btn_helpsure);
            layout_cancel.setOnClickListener(dialogClick);
            btn_helpsure.setOnClickListener(dialogClick);
            detailhelp_relat_engry.setOnClickListener(dialogClick);
            detailhelp_relat_account.setOnClickListener(dialogClick);
            detailhelp_relat_wx.setOnClickListener(dialogClick);
            detailhelp_relat_zfb.setOnClickListener(dialogClick);
            detailhelp_gv_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (is_applying_help > 0) {
                        int money_ = mutual_help_money_all.get(position).getMoney();
                        if (money_ >= mutual_help_money) {
                            selectMonetPostion = position;
                            setGVMoney();
                        }
                    } else {
                        selectMonetPostion = position;
                        setGVMoney();
                    }
                }
            });
        } else {
            selectDialog.show();
        }
        detailhelp_tv_engry.setText(ability + "");
        detailhelp_tv_account.setText("¥" + asset + "");
        setapplingDefault();
        setGVMoney();
    }

    View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.layout_cancel:
                    selectDialog.dismiss();
                    break;
                case R.id.detailhelp_relat_engry:
                    if (ability >= selectengery) {
                        payType = "ability";
                        selectPayTypeView();
                    }
                    break;
                case R.id.detailhelp_relat_account:
                    if (Double.valueOf(asset) >= selectmoney) {
                        payType = "asset";
                        selectPayTypeView();
                    }
                    break;
                case R.id.detailhelp_relat_wx:
                    payType = "wxpay";
                    selectPayTypeView();
                    break;
                case R.id.detailhelp_relat_zfb:
                    payType = "alipay";
                    selectPayTypeView();
                    break;
                case R.id.btn_helpsure://立即互祝
                    if (selectDefaultStatus) {
                        selectDialog.dismiss();
                        Message message = new Message();
                        message.what = mHandlerID;
                        Bundle bundle = new Bundle();
                        bundle.putString("payType", payType);
                        bundle.putInt("selectmoney", selectmoney);
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                        message = null;
                    }
                    break;
                default:

                    break;
            }
        }
    };

    int selectMonetPostion;

    private void setGVMoney() {
        setEngery(mutual_help_money_all.get(selectMonetPostion));
        if (mMoneyAdapter == null) {
            mMoneyAdapter = new ActionDetailMoneyAdapter(context, mutual_help_money_all);
            mMoneyAdapter.setMutual_help_money(mutual_help_money);
            mMoneyAdapter.setIs_applying_help(is_applying_help);
            mMoneyAdapter.setSelectMonetPostion(selectMonetPostion);
            mMoneyAdapter.setSelectDefaultStatus(selectDefaultStatus);
            detailhelp_gv_money.setAdapter(mMoneyAdapter);
        } else {
            mMoneyAdapter.setSelectDefaultStatus(selectDefaultStatus);
            mMoneyAdapter.setMutual_help_money(mutual_help_money);
            mMoneyAdapter.setIs_applying_help(is_applying_help);
            mMoneyAdapter.setSelectMonetPostion(selectMonetPostion);
            mMoneyAdapter.setList(mutual_help_money_all);
            mMoneyAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 是否有默认
     */
    boolean selectDefaultStatus = false;

    private void setapplingDefault() {
        if (is_applying_help > 0) {
            for (int i = 0; i < mutual_help_money_all.size(); i++) {
                int money_ = mutual_help_money_all.get(i).getMoney();
                if (money_ == mutual_help_money) {
                    selectMonetPostion = i;
                    selectDefaultStatus = true;
                    break;
                }
            }
            //防止没有，选择大于mutual_help_money的一个
            if (mutual_help_money != mutual_help_money_all.get(selectMonetPostion).getMoney()) {
                for (int i = 0; i < mutual_help_money_all.size(); i++) {
                    int money_ = mutual_help_money_all.get(i).getMoney();
                    if (mutual_help_money < money_) {
                        selectMonetPostion = i;
                        selectDefaultStatus = true;
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < mutual_help_money_all.size(); i++) {
                if (mutual_help_money_all.get(i).getIs_default() == 1) {
                    selectMonetPostion = i;
                    selectDefaultStatus = true;
                    break;
                }
            }
        }
        //默认大于当前价格列表，不让祝福
        if (!selectDefaultStatus) {
            btn_helpsure.setBackgroundResource(R.drawable.corners_bg_logingray);
        }
    }

    /**
     * 设置显示选中规则
     *
     * @param mEnergyItemBean
     */
    private void setEngery(DetailItemBean mEnergyItemBean) {
        selectmoney = mEnergyItemBean.getMoney();
        selectengery = mEnergyItemBean.getAbility();
        detailhelp_tv_money.setText(""+selectengery);
        if (ability >= selectengery) {
            payType = "ability";
        } else if (Double.valueOf(asset) >= selectmoney) {
            payType = "asset";
        } else {
            payType = "wxpay";
        }


        selectPayTypeView();
    }


    private void selectPayTypeView() {

        detailhelp_tv_engrytitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_engry.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_wxselecttitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        tv_zfbtitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));

        detailhelp_iv_engryselect.setVisibility(View.GONE);
        detailhelp_relat_engry.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_accountselect.setVisibility(View.GONE);
        detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_wxselect.setVisibility(View.GONE);
        detailhelp_relat_wx.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_zfbselect.setVisibility(View.GONE);
        detailhelp_relat_zfb.setBackgroundResource(R.drawable.corners_bg_black);


        //设置默认
        if (ability < selectengery) {
            detailhelp_tv_engrytitle.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_tv_engry.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_relat_engry.setBackgroundResource(R.drawable.corners_bg_blackhealth);
        }
        if (Double.valueOf(asset) < selectmoney) {
            detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_blackhealth);
        }

        if (payType.equals("wxpay")) {
            detailhelp_tv_wxselecttitle.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_iv_wxselect.setVisibility(View.VISIBLE);
            detailhelp_relat_wx.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelp_relat_wx.setPadding(0, 0, 0, 0);
        } else if (payType.equals("asset")) {
            detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_iv_accountselect.setVisibility(View.VISIBLE);
            detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelp_relat_account.setPadding(0, 0, 0, 0);
        } else if (payType.equals("ability")) {
            detailhelp_tv_engrytitle.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_tv_engry.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_iv_engryselect.setVisibility(View.VISIBLE);
            detailhelp_relat_engry.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelp_relat_engry.setPadding(0, 0, 0, 0);
        } else if (payType.equals("alipay")) {
            tv_zfbtitle.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_iv_zfbselect.setVisibility(View.VISIBLE);
            detailhelp_relat_zfb.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelp_relat_zfb.setPadding(0, 0, 0, 0);
        }
    }


}
