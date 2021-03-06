package com.longcheng.lifecareplan.modular.helpwith.energydetail.activity;

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
 * **************************详情互祝弹层***********************************
 */

public class DetailHelpDialogUtils {
    MyDialog selectDialog;
    MyGridView detailhelp_gv_money;

    RelativeLayout detailhelp_relat_engry;
    TextView detailhelp_tv_engry;
    ImageView detailhelp_iv_engryselect;
    TextView detailhelp_tv_engrytitle;

    RelativeLayout detailhelp_relat_superengry;
    TextView detailhelp_tv_superengry;
    ImageView detailhelp_iv_superengryselect;
    TextView detailhelp_tv_superengrytitle;

    RelativeLayout detailhelp_relat_account;
    TextView detailhelp_tv_account;
    ImageView detailhelp_iv_accountselect;
    TextView detailhelp_tv_accounttitle;

    RelativeLayout detailhelp_relat_wx, detailhelp_relat_zfb;
    ImageView detailhelp_iv_wxselect, detailhelp_iv_zfbselect;
    TextView detailhelp_tv_wxselecttitle;
    TextView tv_zfbtitle;
    TextView detailhelp_tv_money;
    EditText detailhelp_et_content;
    TextView btn_helpsure;

    private String blessings;
    private Handler mHandler;
    private int mHandlerID;
    /**
     * 支付方式互祝类型 asset (现金支付)， ability(生命能量支付)， wxpay(微信支付)
     */
    private String payType = "super_ability";
    private int selectmoney;
    private String selectengery = "0";
    private DetailMoneyAdapter mMoneyAdapter;
    private String ability = "0";//生命能量
    private String super_ability = "0";
    private String asset;//金额
    private Activity context;
    private int is_applying_help;
    private int mutual_help_money;
    private List<DetailItemBean> mutual_help_money_all;
    private List<DetailItemBean> blessings_list;

    /**
     * 是否正常使用普通能量互祝  默认是1 可以使用
     */
    int is_normal_help = 1;

    public int getIs_normal_help() {
        return is_normal_help;
    }

    public void setIs_normal_help(int is_normal_help) {
        this.is_normal_help = is_normal_help;
    }

    public DetailHelpDialogUtils(Activity context, Handler mHandler, int mHandlerID) {
        this.mHandlerID = mHandlerID;
        this.mHandler = mHandler;
        this.context = context;
    }

    public void initData(DetailItemBean userInfo, List<DetailItemBean> blessings_list,
                         int is_applying_help, int mutual_help_money,
                         List<DetailItemBean> mutual_help_money_all) {
        if (userInfo != null) {
            super_ability = userInfo.getSuper_ability();
            ability = userInfo.getAbility();
            asset = userInfo.getAsset();
        }
        if (TextUtils.isEmpty(asset)) {
            asset = "0";
        }
        this.mutual_help_money_all = mutual_help_money_all;
        this.blessings_list = blessings_list;
        this.is_applying_help = is_applying_help;
        this.mutual_help_money = mutual_help_money;
        setBless();
    }

    private void setBless() {
        if (blessings_list != null && blessings_list.size() > 0) {
            int random = ConfigUtils.getINSTANCE().setRandom(blessings_list.size() - 1);
            blessings = blessings_list.get(random).getBlessings();
        }
    }

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_detailhelpred);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            final EditText et = new EditText(context);
            et.setHint("送一句祝福：永远幸福安康");
            selectDialog.setView(et);//给对话框添加一个EditText输入文本框
            selectDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            });
            selectDialog.show();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            LinearLayout layout_cancel = selectDialog.findViewById(R.id.layout_cancel);
            detailhelp_et_content = selectDialog.findViewById(R.id.detailhelp_et_content);
            detailhelp_tv_money = selectDialog.findViewById(R.id.detailhelp_tv_money);
            detailhelp_gv_money = selectDialog.findViewById(R.id.detailhelp_gv_money);

            detailhelp_relat_engry = selectDialog.findViewById(R.id.detailhelp_relat_engry);
            detailhelp_tv_engrytitle = selectDialog.findViewById(R.id.detailhelp_tv_engrytitle);
            detailhelp_tv_engry = selectDialog.findViewById(R.id.detailhelp_tv_engry);
            detailhelp_iv_engryselect = selectDialog.findViewById(R.id.detailhelp_iv_engryselect);


            detailhelp_relat_superengry = selectDialog.findViewById(R.id.detailhelp_relat_superengry);
            detailhelp_tv_superengrytitle = selectDialog.findViewById(R.id.detailhelp_tv_superengrytitle);
            detailhelp_tv_superengry = selectDialog.findViewById(R.id.detailhelp_tv_superengry);
            detailhelp_iv_superengryselect = selectDialog.findViewById(R.id.detailhelp_iv_superengryselect);

            detailhelp_relat_account = selectDialog.findViewById(R.id.detailhelp_relat_account);
            detailhelp_tv_accounttitle = selectDialog.findViewById(R.id.detailhelp_tv_accounttitle);
            detailhelp_tv_account = selectDialog.findViewById(R.id.detailhelp_tv_account);
            detailhelp_iv_accountselect = selectDialog.findViewById(R.id.detailhelp_iv_accountselect);

            detailhelp_relat_wx = selectDialog.findViewById(R.id.detailhelp_relat_wx);
            detailhelp_iv_wxselect = selectDialog.findViewById(R.id.detailhelp_iv_wxselect);
            detailhelp_tv_wxselecttitle = selectDialog.findViewById(R.id.detailhelp_tv_wxselecttitle);
            detailhelp_relat_zfb = selectDialog.findViewById(R.id.detailhelp_relat_zfb);
            detailhelp_iv_zfbselect = selectDialog.findViewById(R.id.detailhelp_iv_zfbselect);
            tv_zfbtitle = selectDialog.findViewById(R.id.tv_zfbtitle);

            btn_helpsure = selectDialog.findViewById(R.id.btn_helpsure);
            layout_cancel.setOnClickListener(dialogClick);
            btn_helpsure.setOnClickListener(dialogClick);
            detailhelp_relat_engry.setOnClickListener(dialogClick);
            detailhelp_relat_superengry.setOnClickListener(dialogClick);
            detailhelp_relat_account.setOnClickListener(dialogClick);
            detailhelp_relat_wx.setOnClickListener(dialogClick);
            detailhelp_relat_zfb.setOnClickListener(dialogClick);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(detailhelp_et_content, 100);
            detailhelp_gv_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setBless();
                    detailhelp_et_content.setText(blessings);
                    if (!TextUtils.isEmpty(blessings)) {
                        detailhelp_et_content.setSelection(detailhelp_et_content.getText().length());
                    }
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
        detailhelp_et_content.setText(blessings);
        if (!TextUtils.isEmpty(blessings)) {
            detailhelp_et_content.setSelection(detailhelp_et_content.getText().length());
        }
        detailhelp_tv_superengry.setText(super_ability + "");
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
                    if (is_applying_help == 0 && is_normal_help == 1) {
                        payType = "ability";
                        selectPayTypeView();
                    }

                    break;
                case R.id.detailhelp_relat_superengry:
                    payType = "super_ability";
                    selectPayTypeView();
                    break;

                case R.id.detailhelp_relat_account:
                    if (is_applying_help == 0 && is_normal_help == 1) {
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
                        String help_comment_content = detailhelp_et_content.getText().toString().trim();
                        Message message = new Message();
                        message.what = mHandlerID;
                        Bundle bundle = new Bundle();
                        bundle.putString("help_comment_content", help_comment_content);
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
            mMoneyAdapter = new DetailMoneyAdapter(context, mutual_help_money_all);
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
        detailhelp_tv_money.setText("（祝福金额：" + selectmoney + "元）");
        payType = "super_ability";
        selectPayTypeView();
    }


    private void selectPayTypeView() {

        detailhelp_tv_engrytitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_engry.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_superengrytitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_superengry.setTextColor(context.getResources().getColor(R.color.text_contents_color));

        detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_wxselecttitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        tv_zfbtitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));

        detailhelp_iv_superengryselect.setVisibility(View.GONE);
        detailhelp_relat_superengry.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_engryselect.setVisibility(View.GONE);
        detailhelp_relat_engry.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_accountselect.setVisibility(View.GONE);
        detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_wxselect.setVisibility(View.GONE);
        detailhelp_relat_wx.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_zfbselect.setVisibility(View.GONE);
        detailhelp_relat_zfb.setBackgroundResource(R.drawable.corners_bg_black);


        //设置默认
        if (is_normal_help != 1 || is_applying_help > 0 || (Double.valueOf(ability) < Double.valueOf(selectengery))) {
            detailhelp_tv_engrytitle.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_tv_engry.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_relat_engry.setBackgroundResource(R.drawable.corners_bg_black);
        }
        if (is_normal_help != 1 || is_applying_help > 0 || (Double.valueOf(asset) < selectmoney)) {
            detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_black);
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
        } else if (payType.equals("super_ability")) {
            detailhelp_tv_superengrytitle.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_tv_superengry.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_iv_superengryselect.setVisibility(View.VISIBLE);
            detailhelp_relat_superengry.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelp_relat_superengry.setPadding(0, 0, 0, 0);
        } else if (payType.equals("alipay")) {
            tv_zfbtitle.setTextColor(context.getResources().getColor(R.color.red));
            detailhelp_iv_zfbselect.setVisibility(View.VISIBLE);
            detailhelp_relat_zfb.setBackgroundResource(R.drawable.corners_bg_redbian);
            detailhelp_relat_zfb.setPadding(0, 0, 0, 0);
        }
    }


}
