package com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity;

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
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.adapter.BaoZhangMoneyAdapter;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.LongClickButton;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/30 16:05
 * 意图：
 * **************************生活保障 天才无债互祝弹层***********************************
 */

public class BaoZhangDialogUtils {

    private RelativeLayout detailhelp_relat_account, detailhelp_relat_wx, detailhelp_relat_zfb;
    private ImageView detailhelp_iv_accountselect, detailhelp_iv_wxselect, detailhelp_iv_zfbselect;
    private TextView btn_helpsure;
    private TextView detailhelp_tv_accounttitle, detailhelp_tv_account, detailhelp_tv_wxselecttitle, tv_num;
    private TextView tv_zfbtitle;
    private EditText detailhelp_et_content;
    private LongClickButton tv_jian, tv_add;

    private MyDialog selectDialog;
    private MyGridView detailhelp_gv_money;
    private Handler mHandler;
    private int mHandlerID;
    /**
     * 支付方式互祝类型 4 (现金支付)， ability(生命能量支付)， 1(微信支付)  2(支付宝)
     */
    private String payType = "4";
    private int selectmoney;
    private int num = 1;
    private BaoZhangMoneyAdapter mMoneyAdapter;
    private Activity context;
    private List<DetailItemBean> mutual_help_money_all;

    private List<DetailItemBean> blessings_list;
    private String blessings;
    private String asset_debt;

    public BaoZhangDialogUtils(Activity context, Handler mHandler, int mHandlerID) {
        this.mHandlerID = mHandlerID;
        this.mHandler = mHandler;
        this.context = context;
    }


    public void initData(List<DetailItemBean> blessings_list, List<DetailItemBean> mutual_help_money_all, String asset_debt) {
        this.mutual_help_money_all = mutual_help_money_all;
        this.blessings_list = blessings_list;
        this.asset_debt = asset_debt;
        if (TextUtils.isEmpty(asset_debt)) {
            this.asset_debt = "0";
        }
    }

    private void setBless() {
        if (blessings_list != null && blessings_list.size() > 0) {
            int random = ConfigUtils.getINSTANCE().setRandom(blessings_list.size() - 1);
            blessings = blessings_list.get(random).getBlessings();
        }
        detailhelp_et_content.setText(blessings);
        if (!TextUtils.isEmpty(blessings)) {
            detailhelp_et_content.setSelection(detailhelp_et_content.getText().length());
        }
    }

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_baozhang);// 创建Dialog并设置样式主题
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

            LinearLayout layout_cancel = (LinearLayout) selectDialog.findViewById(R.id.layout_cancel);
            detailhelp_gv_money = (MyGridView) selectDialog.findViewById(R.id.detailhelp_gv_money);
            detailhelp_et_content = (EditText) selectDialog.findViewById(R.id.detailhelp_et_content);

            detailhelp_relat_account = (RelativeLayout) selectDialog.findViewById(R.id.detailhelp_relat_account);
            detailhelp_iv_accountselect = (ImageView) selectDialog.findViewById(R.id.detailhelp_iv_accountselect);
            detailhelp_tv_account = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_account);
            detailhelp_tv_accounttitle = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_accounttitle);
            detailhelp_relat_wx = (RelativeLayout) selectDialog.findViewById(R.id.detailhelp_relat_wx);
            detailhelp_iv_wxselect = (ImageView) selectDialog.findViewById(R.id.detailhelp_iv_wxselect);
            detailhelp_tv_wxselecttitle = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_wxselecttitle);
            detailhelp_relat_zfb = (RelativeLayout) selectDialog.findViewById(R.id.detailhelp_relat_zfb);
            detailhelp_iv_zfbselect = (ImageView) selectDialog.findViewById(R.id.detailhelp_iv_zfbselect);
            tv_zfbtitle = (TextView) selectDialog.findViewById(R.id.tv_zfbtitle);
            tv_jian = (LongClickButton) selectDialog.findViewById(R.id.tv_jian);
            tv_num = (TextView) selectDialog.findViewById(R.id.tv_num);
            tv_add = (LongClickButton) selectDialog.findViewById(R.id.tv_add);
            btn_helpsure = (TextView) selectDialog.findViewById(R.id.btn_helpsure);
            TextView tv_tishi = (TextView) selectDialog.findViewById(R.id.tv_tishi);
            if (mHandlerID == BaoZhangActitvty.LifeBasicAppPayment) {//基础保障用绿色
                tv_tishi.setVisibility(View.GONE);
                btn_helpsure.setBackgroundResource(R.color.lv);
                detailhelp_relat_account.setVisibility(View.VISIBLE);
            } else {
                tv_tishi.setVisibility(View.VISIBLE);
                btn_helpsure.setBackgroundResource(R.color.red);
                detailhelp_relat_account.setVisibility(View.GONE);
            }
            layout_cancel.setOnClickListener(dialogClick);
            tv_jian.setOnClickListener(dialogClick);
            tv_add.setOnClickListener(dialogClick);
            btn_helpsure.setOnClickListener(dialogClick);
            detailhelp_relat_account.setOnClickListener(dialogClick);
            detailhelp_relat_wx.setOnClickListener(dialogClick);
            detailhelp_relat_zfb.setOnClickListener(dialogClick);
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(detailhelp_et_content, 100);
            detailhelp_gv_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setBless();
                    selectMonetPostion = position;
                    setGVMoney();
                }
            });
            //连续减
            tv_jian.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
                @Override
                public void repeatAction() {
                    processClick(R.id.tv_jian);
                }
            }, 50);
            //连续加
            tv_add.setLongClickRepeatListener(new LongClickButton.LongClickRepeatListener() {
                @Override
                public void repeatAction() {
                    processClick(R.id.tv_add);
                }
            }, 50);
        } else {
            selectDialog.show();
        }
//        if (TextUtils.isEmpty(asset_debt) || (!TextUtils.isEmpty(asset_debt)) && asset_debt.equals("0")) {
//            payType = "1";
//        } else {
//            payType = "4";
//        }
//        if (mHandlerID == BaoZhangActitvty.LifeBasicAppPayment) {//基础保障初始化默认微信
//            payType = "1";
//        }
        payType = "1";
        num = 1;
        tv_num.setText("" + num);
        detailhelp_tv_account.setText(context.getResources().getString(R.string.mark_money) + asset_debt);
        setBless();
        selectPayTypeView();
        setapplingDefault();
        setGVMoney();
    }

    private View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            processClick(v.getId());
        }
    };
    private MyDialog AssetDialog;
    private TextView tv_asset, text_off, text_sure;

    /**
     * 余额支付显示弹层提示
     */
    public void showDialogAsset(String help_comment_content,
                                String pay_way, int money,
                                int help_number, Handler mAssetHandler, int mAssetHandlerID) {
        if (AssetDialog == null) {
            AssetDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_baozhangasset);// 创建Dialog并设置样式主题
            AssetDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = AssetDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            AssetDialog.show();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = AssetDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            AssetDialog.getWindow().setAttributes(p); //设置生效

            tv_asset = (TextView) AssetDialog.findViewById(R.id.tv_asset);
            text_off = (TextView) AssetDialog.findViewById(R.id.text_off);
            text_sure = (TextView) AssetDialog.findViewById(R.id.text_sure);
        } else {
            AssetDialog.show();
        }
        tv_asset.setText("" + (money * help_number));
        if (mAssetHandlerID == BaoZhangActitvty.LifeBasicAppPaymentAsset) {//基础保障用绿色
            text_off.setBackgroundResource(R.drawable.corners_bg_lvbian);
            text_sure.setBackgroundResource(R.drawable.corners_bg_lv);
        } else {
            text_off.setBackgroundResource(R.drawable.corners_bg_redbian);
            text_sure.setBackgroundResource(R.drawable.corners_bg_red);
        }
        text_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetDialog.dismiss();
            }
        });
        text_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetDialog.dismiss();
                Message message = new Message();
                message.what = mAssetHandlerID;
                Bundle bundle = new Bundle();
                bundle.putString("payType", pay_way);
                bundle.putInt("selectmoney", money);
                bundle.putInt("help_number", help_number);
                bundle.putString("help_comment_content", help_comment_content);
                message.setData(bundle);
                mAssetHandler.sendMessage(message);
                message = null;
            }
        });
    }

    /**
     * 处理事件
     *
     * @param id
     */
    private void processClick(int id) {
        switch (id) {
            case R.id.layout_cancel:
                selectDialog.dismiss();
                break;
            case R.id.tv_add:
                if (num < 100) {
                    num++;
                } else {
                    ToastUtils.showToast("已到最大奉献倍数");
                }
                tv_num.setText("" + num);
                break;
            case R.id.tv_jian:
                if (num > 1) {
                    num--;
                } else {
                    ToastUtils.showToast("已到最小奉献倍数");
                }
                tv_num.setText("" + num);
                break;
            case R.id.detailhelp_relat_wx:
                payType = "1";
                selectPayTypeView();
                break;
            case R.id.detailhelp_relat_zfb:
                payType = "2";
                selectPayTypeView();
                break;
            case R.id.detailhelp_relat_account:
                payType = "4";
                selectPayTypeView();
                break;
            case R.id.btn_helpsure://立即互祝
                if (payType.equals("4") && (Double.valueOf(selectmoney * num) > Double.valueOf(asset_debt))) {
                    ToastUtils.showToast("您的钱包余额不足");
                } else {
                    selectDialog.dismiss();
                    Message message = new Message();
                    message.what = mHandlerID;
                    Bundle bundle = new Bundle();
                    bundle.putString("payType", payType);
                    bundle.putInt("selectmoney", selectmoney);
                    bundle.putInt("help_number", num);
                    String help_comment_content = detailhelp_et_content.getText().toString().trim();
                    bundle.putString("help_comment_content", help_comment_content);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                    message = null;
                }
                break;
            default:

                break;
        }
    }

    private int selectMonetPostion;

    private void setGVMoney() {
        selectmoney = mutual_help_money_all.get(selectMonetPostion).getMoney();
        if (mMoneyAdapter == null) {
            mMoneyAdapter = new BaoZhangMoneyAdapter(context, mutual_help_money_all);
            mMoneyAdapter.setSelectMonetPostion(selectMonetPostion);
            mMoneyAdapter.setSelectDefaultStatus(true);
            mMoneyAdapter.setmHandlerID(mHandlerID);
            detailhelp_gv_money.setAdapter(mMoneyAdapter);
        } else {
            mMoneyAdapter.setmHandlerID(mHandlerID);
            mMoneyAdapter.setSelectDefaultStatus(true);
            mMoneyAdapter.setSelectMonetPostion(selectMonetPostion);
            mMoneyAdapter.setList(mutual_help_money_all);
            mMoneyAdapter.notifyDataSetChanged();
        }
    }

    private void setapplingDefault() {
        for (int i = 0; i < mutual_help_money_all.size(); i++) {
            if (mutual_help_money_all.get(i).getIs_default() == 1) {
                selectMonetPostion = i;
                break;
            }
        }
    }


    private void selectPayTypeView() {
        detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_iv_accountselect.setVisibility(View.GONE);
        detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_black);

        detailhelp_tv_wxselecttitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        tv_zfbtitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_iv_wxselect.setVisibility(View.GONE);
        detailhelp_relat_wx.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_zfbselect.setVisibility(View.GONE);
        detailhelp_relat_zfb.setBackgroundResource(R.drawable.corners_bg_black);

        if (mHandlerID == BaoZhangActitvty.LifeBasicAppPayment) {//基础保障用绿色
            detailhelp_iv_wxselect.setBackgroundResource(R.mipmap.pay_selcet_icon_lv);
            detailhelp_iv_zfbselect.setBackgroundResource(R.mipmap.pay_selcet_icon_lv);
            detailhelp_iv_accountselect.setBackgroundResource(R.mipmap.pay_selcet_icon_lv);
            if (payType.equals("1")) {
                detailhelp_tv_wxselecttitle.setTextColor(context.getResources().getColor(R.color.lv));
                detailhelp_iv_wxselect.setVisibility(View.VISIBLE);
                detailhelp_relat_wx.setBackgroundResource(R.drawable.corners_bg_lvbian);
                detailhelp_relat_wx.setPadding(0, 0, 0, 0);
            } else if (payType.equals("2")) {
                tv_zfbtitle.setTextColor(context.getResources().getColor(R.color.lv));
                detailhelp_iv_zfbselect.setVisibility(View.VISIBLE);
                detailhelp_relat_zfb.setBackgroundResource(R.drawable.corners_bg_lvbian);
                detailhelp_relat_zfb.setPadding(0, 0, 0, 0);
            } else if (payType.equals("4")) {
                detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.lv));
                detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.lv));
                detailhelp_iv_accountselect.setVisibility(View.VISIBLE);
                detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_lvbian);
                detailhelp_relat_account.setPadding(0, 0, 0, 0);
            }
        } else {
            detailhelp_iv_accountselect.setBackgroundResource(R.mipmap.pay_selcet_icon_red);
            detailhelp_iv_wxselect.setBackgroundResource(R.mipmap.pay_selcet_icon_red);
            detailhelp_iv_zfbselect.setBackgroundResource(R.mipmap.pay_selcet_icon_red);
            if (payType.equals("1")) {
                detailhelp_tv_wxselecttitle.setTextColor(context.getResources().getColor(R.color.red));
                detailhelp_iv_wxselect.setVisibility(View.VISIBLE);
                detailhelp_relat_wx.setBackgroundResource(R.drawable.corners_bg_redbian);
                detailhelp_relat_wx.setPadding(0, 0, 0, 0);
            } else if (payType.equals("2")) {
                tv_zfbtitle.setTextColor(context.getResources().getColor(R.color.red));
                detailhelp_iv_zfbselect.setVisibility(View.VISIBLE);
                detailhelp_relat_zfb.setBackgroundResource(R.drawable.corners_bg_redbian);
                detailhelp_relat_zfb.setPadding(0, 0, 0, 0);
            } else if (payType.equals("4")) {
                detailhelp_tv_account.setTextColor(context.getResources().getColor(R.color.red));
                detailhelp_tv_accounttitle.setTextColor(context.getResources().getColor(R.color.red));
                detailhelp_iv_accountselect.setVisibility(View.VISIBLE);
                detailhelp_relat_account.setBackgroundResource(R.drawable.corners_bg_redbian);
                detailhelp_relat_account.setPadding(0, 0, 0, 0);
            }
        }
    }


}
