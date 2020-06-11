package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.activity;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.adapter.DetailMoneyAdapter;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailItemBean;
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

public class LifeStyleDetailHelpDialogUtils {

    TextView detailhelp_tv_money, tv_title, tv_pricetitle;
    MyGridView detailhelp_gv_money;
    EditText detailhelp_et_content;
    private TextView btn_helpsure;

    MyDialog selectDialog;
    String blessings;
    Handler mHandler;
    int mHandlerID;
    int selectskb;
    DetailMoneyAdapter mMoneyAdapter;
    private String skb;//skb
    Activity context;
    int is_applying_help;
    int mutual_help_money;
    List<LifeStyleDetailItemBean> mutual_help_money_all;
    private String help_goods_skb_money_id = "";
    List<LifeStyleDetailItemBean> blessings_list;

    boolean SkbPayStatus;
    String super_ability;

    public LifeStyleDetailHelpDialogUtils(Activity context, Handler mHandler, int mHandlerID) {
        this.mHandlerID = mHandlerID;
        this.mHandler = mHandler;
        this.context = context;
    }

    public void initData(String super_ability, String skb, List<LifeStyleDetailItemBean> blessings_list,
                         int is_applying_help, int mutual_help_money,
                         List<LifeStyleDetailItemBean> mutual_help_money_all, boolean SkbPayStatus) {
        this.mutual_help_money_all = mutual_help_money_all;
        this.blessings_list = blessings_list;
        this.super_ability = super_ability;
        this.skb = skb;
        this.is_applying_help = is_applying_help;
        this.mutual_help_money = mutual_help_money;
        this.SkbPayStatus = SkbPayStatus;
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
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_detail_lifestylehelp);// 创建Dialog并设置样式主题
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

            tv_pricetitle = selectDialog.findViewById(R.id.tv_pricetitle);
            tv_title = selectDialog.findViewById(R.id.tv_title);
            LinearLayout layout_cancel = selectDialog.findViewById(R.id.layout_cancel);
            detailhelp_et_content = selectDialog.findViewById(R.id.detailhelp_et_content);
            detailhelp_tv_money = selectDialog.findViewById(R.id.detailhelp_tv_money);
            detailhelp_gv_money = selectDialog.findViewById(R.id.detailhelp_gv_money);

            btn_helpsure = selectDialog.findViewById(R.id.btn_helpsure);
            layout_cancel.setOnClickListener(dialogClick);
            btn_helpsure.setOnClickListener(dialogClick);
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
                        int money_ = mutual_help_money_all.get(position).getSkb_price();
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

        if (SkbPayStatus) {
            tv_title.setText("请选择寿康宝数量");
            tv_pricetitle.setText("可用寿康宝：");
            detailhelp_tv_money.setText(skb + "");
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, btn_helpsure, R.color.red);
        } else {
            tv_title.setText("祝福感言");
            tv_pricetitle.setText("可用超级生命能量：");
            detailhelp_tv_money.setText(super_ability + "");
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, btn_helpsure, R.color.engry_btn_bg);
        }

        detailhelp_et_content.setText(blessings);
        if (!TextUtils.isEmpty(blessings)) {
            detailhelp_et_content.setSelection(detailhelp_et_content.getText().length());
        }
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
                case R.id.btn_helpsure://立即互祝
                    if (selectDefaultStatus) {
                        selectDialog.dismiss();
                        String help_comment_content = detailhelp_et_content.getText().toString().trim();
                        Message message = new Message();
                        message.what = mHandlerID;
                        Bundle bundle = new Bundle();
                        bundle.putString("help_comment_content", help_comment_content);//祝福语
                        bundle.putInt("skb_price", selectskb);
                        bundle.putString("help_goods_skb_money_id", help_goods_skb_money_id + "");
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

    private void setGVMoney() {
        help_goods_skb_money_id = mutual_help_money_all.get(selectMonetPostion).getHelp_goods_skb_money_id();
        selectskb = mutual_help_money_all.get(selectMonetPostion).getSkb_price();
        if (mMoneyAdapter == null) {
            mMoneyAdapter = new DetailMoneyAdapter(context, mutual_help_money_all);
            mMoneyAdapter.setMutual_help_money(mutual_help_money);
            mMoneyAdapter.setIs_applying_help(is_applying_help);
            mMoneyAdapter.setSkbPayStatus(SkbPayStatus);
            mMoneyAdapter.setSelectMonetPostion(selectMonetPostion);
            mMoneyAdapter.setSelectDefaultStatus(selectDefaultStatus);
            detailhelp_gv_money.setAdapter(mMoneyAdapter);
        } else {
            mMoneyAdapter.setSkbPayStatus(SkbPayStatus);
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
    int selectMonetPostion;

    private void setapplingDefault() {
        if (is_applying_help > 0) {
            for (int i = 0; i < mutual_help_money_all.size(); i++) {
                int money_ = mutual_help_money_all.get(i).getSkb_price();
                if (money_ == mutual_help_money) {
                    selectMonetPostion = i;
                    selectDefaultStatus = true;
                    break;
                }
            }
            //防止没有，选择大于mutual_help_money的一个
            if (mutual_help_money != mutual_help_money_all.get(selectMonetPostion).getSkb_price()) {
                for (int i = 0; i < mutual_help_money_all.size(); i++) {
                    int money_ = mutual_help_money_all.get(i).getSkb_price();
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

}
