package com.longcheng.lifecareplan.modular.helpwith.nfcaction.activity;

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
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.adapter.NFCDetailMoneyAdapter;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailItemBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/30 16:05
 * 意图：
 * **************************详情互祝弹层***********************************
 */

public class NFCDetailHelpDialogUtils {
    MyDialog selectDialog;
    MyGridView detailhelp_gv_money;


    RelativeLayout detailhelp_relat_superengry;
    TextView detailhelp_tv_superengry;
    ImageView detailhelp_iv_superengryselect;
    TextView detailhelp_tv_superengrytitle;

    RelativeLayout detailhelp_relat_account;
    TextView detailhelp_tv_account;
    ImageView detailhelp_iv_accountselect;
    TextView detailhelp_tv_accounttitle;

    TextView detailhelp_tv_money;
    TextView btn_helpsure;

    private Handler mHandler;
    private int mHandlerID;
    private int payType = 2;
    private int selectmoney;
    private String mutual_help_money_id;
    private String selectengery = "0";
    private NFCDetailMoneyAdapter mMoneyAdapter;
    private String super_ability = "0";
    private String asset;//金额
    private Activity context;
    private List<NFCDetailItemBean> mutual_help_money_all;


    public NFCDetailHelpDialogUtils(Activity context, Handler mHandler, int mHandlerID) {
        this.mHandlerID = mHandlerID;
        this.mHandler = mHandler;
        this.context = context;
    }

    public void initData(NFCDetailItemBean userInfo,
                         List<NFCDetailItemBean> mutual_help_money_all) {
        if (userInfo != null) {
            super_ability = userInfo.getSuper_ability();
            asset = userInfo.getAsset();
        }
        if (TextUtils.isEmpty(asset)) {
            asset = "0";
        }
        this.mutual_help_money_all = mutual_help_money_all;
    }

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_detailnfc);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = selectDialog.findViewById(R.id.layout_cancel);
            detailhelp_tv_money = selectDialog.findViewById(R.id.detailhelp_tv_money);
            detailhelp_gv_money = selectDialog.findViewById(R.id.detailhelp_gv_money);

            detailhelp_relat_superengry = selectDialog.findViewById(R.id.detailhelp_relat_superengry);
            detailhelp_tv_superengrytitle = selectDialog.findViewById(R.id.detailhelp_tv_superengrytitle);
            detailhelp_tv_superengry = selectDialog.findViewById(R.id.detailhelp_tv_superengry);
            detailhelp_iv_superengryselect = selectDialog.findViewById(R.id.detailhelp_iv_superengryselect);

            detailhelp_relat_account = selectDialog.findViewById(R.id.detailhelp_relat_account);
            detailhelp_tv_accounttitle = selectDialog.findViewById(R.id.detailhelp_tv_accounttitle);
            detailhelp_tv_account = selectDialog.findViewById(R.id.detailhelp_tv_account);
            detailhelp_iv_accountselect = selectDialog.findViewById(R.id.detailhelp_iv_accountselect);
            btn_helpsure = selectDialog.findViewById(R.id.btn_helpsure);
            layout_cancel.setOnClickListener(dialogClick);
            btn_helpsure.setOnClickListener(dialogClick);
            detailhelp_relat_superengry.setOnClickListener(dialogClick);
            detailhelp_relat_account.setOnClickListener(dialogClick);
            detailhelp_gv_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectMonetPostion = position;
                    setGVMoney();
                }
            });
        } else {
            selectDialog.show();
        }
        detailhelp_tv_superengry.setText(super_ability + "");
        detailhelp_tv_account.setText("¥" + asset + "");
        for (int i = 0; i < mutual_help_money_all.size(); i++) {
            if (mutual_help_money_all.get(i).getIs_default() == 1) {
                selectMonetPostion = i;
                break;
            }
        }
        setGVMoney();
    }

    View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.layout_cancel:
                    selectDialog.dismiss();
                    break;
                case R.id.detailhelp_relat_superengry:
                    if (Double.valueOf(super_ability) >= Double.valueOf(selectengery)) {
                        payType = 2;
                        selectPayTypeView();
                    }
                    break;

                case R.id.detailhelp_relat_account:
                    if (Double.valueOf(asset) >= selectmoney) {
                        payType = 1;
                        selectPayTypeView();
                    }
                    break;
                case R.id.btn_helpsure://立即互祝
                    if (payType == 2) {
                        selectDialog.dismiss();
                        Message message = new Message();
                        message.what = mHandlerID;
                        Bundle bundle = new Bundle();
                        bundle.putInt("payType", payType);
                        bundle.putString("mutual_help_money_id", mutual_help_money_id);
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
            mMoneyAdapter = new NFCDetailMoneyAdapter(context, mutual_help_money_all);
            mMoneyAdapter.setSelectMonetPostion(selectMonetPostion);
            detailhelp_gv_money.setAdapter(mMoneyAdapter);
        } else {
            mMoneyAdapter.setSelectMonetPostion(selectMonetPostion);
            mMoneyAdapter.setList(mutual_help_money_all);
            mMoneyAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 设置显示选中规则
     *
     * @param mEnergyItemBean
     */
    private void setEngery(NFCDetailItemBean mEnergyItemBean) {
        selectmoney = mEnergyItemBean.getMoney();
        selectengery = mEnergyItemBean.getAbility();
        mutual_help_money_id = mEnergyItemBean.getMutual_help_money_id();
        detailhelp_tv_money.setText("（所需金额" + selectmoney + "元）");
        selectPayTypeView();
    }


    private void selectPayTypeView() {

        detailhelp_tv_superengrytitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_superengry.setTextColor(context.getResources().getColor(R.color.text_contents_color));

        detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.text_contents_color));

        detailhelp_iv_superengryselect.setVisibility(View.GONE);
        detailhelp_relat_superengry.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_accountselect.setVisibility(View.GONE);
        detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_black);

        if (Double.valueOf(super_ability) < Double.valueOf(selectengery)) {
            detailhelp_tv_superengrytitle.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_tv_superengry.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_relat_superengry.setBackgroundResource(R.drawable.corners_bg_black);
            payType = 1;
        }
        if ((Double.valueOf(asset) < selectmoney)) {
            detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_black);
        }
        if (Double.valueOf(super_ability) < Double.valueOf(selectengery) && (Double.valueOf(asset) < selectmoney)) {
            payType = 0;
            return;
        }
        if (payType == 1) {
            detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_iv_accountselect.setVisibility(View.VISIBLE);
            detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelp_relat_account.setPadding(0, 0, 0, 0);
        } else if (payType == 2) {
            detailhelp_tv_superengrytitle.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_tv_superengry.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_iv_superengryselect.setVisibility(View.VISIBLE);
            detailhelp_relat_superengry.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelp_relat_superengry.setPadding(0, 0, 0, 0);
        }
    }


}
