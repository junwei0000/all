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
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.lifecareplan.modular.mine.fragment.genius.ActionDetailMoneyAdapter;
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

public class BaoZhangDialogUtils {
    MyDialog selectDialog;
    MyGridView detailhelp_gv_money;

    RelativeLayout detailhelp_relat_wx, detailhelp_relat_zfb;
    ImageView detailhelp_iv_wxselect, detailhelp_iv_zfbselect;

    Handler mHandler;
    int mHandlerID;
    /**
     * 支付方式互祝类型 asset (现金支付)， ability(生命能量支付)， wxpay(微信支付)
     */
    String payType = "1";
    int selectmoney;
    BaoZhangMoneyAdapter mMoneyAdapter;
    Activity context;
    List<DetailItemBean> mutual_help_money_all;
    private TextView btn_helpsure;
    private TextView detailhelp_tv_wxselecttitle;
    private TextView tv_zfbtitle;
    List<DetailItemBean> blessings_list;
    String blessings;
    private EditText detailhelp_et_content;

    public BaoZhangDialogUtils(Activity context, Handler mHandler, int mHandlerID) {
        this.mHandlerID = mHandlerID;
        this.mHandler = mHandler;
        this.context = context;
    }


    public void initData(List<DetailItemBean> blessings_list, List<DetailItemBean> mutual_help_money_all) {
        this.mutual_help_money_all = mutual_help_money_all;
        this.blessings_list = blessings_list;
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
            detailhelp_relat_wx = (RelativeLayout) selectDialog.findViewById(R.id.detailhelp_relat_wx);
            detailhelp_iv_wxselect = (ImageView) selectDialog.findViewById(R.id.detailhelp_iv_wxselect);
            detailhelp_tv_wxselecttitle = (TextView) selectDialog.findViewById(R.id.detailhelp_tv_wxselecttitle);
            detailhelp_relat_zfb = (RelativeLayout) selectDialog.findViewById(R.id.detailhelp_relat_zfb);
            detailhelp_iv_zfbselect = (ImageView) selectDialog.findViewById(R.id.detailhelp_iv_zfbselect);
            tv_zfbtitle = (TextView) selectDialog.findViewById(R.id.tv_zfbtitle);

            btn_helpsure = (TextView) selectDialog.findViewById(R.id.btn_helpsure);
            layout_cancel.setOnClickListener(dialogClick);
            btn_helpsure.setOnClickListener(dialogClick);
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
        } else {
            selectDialog.show();
        }
        setBless();
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
                case R.id.detailhelp_relat_wx:
                    payType = "1";
                    selectPayTypeView();
                    break;
                case R.id.detailhelp_relat_zfb:
                    payType = "2";
                    selectPayTypeView();
                    break;
                case R.id.btn_helpsure://立即互祝
                    selectDialog.dismiss();
                    Message message = new Message();
                    message.what = mHandlerID;
                    Bundle bundle = new Bundle();
                    bundle.putString("payType", payType);
                    bundle.putInt("selectmoney", selectmoney);
                    String help_comment_content = detailhelp_et_content.getText().toString().trim();
                    bundle.putString("help_comment_content", help_comment_content);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                default:

                    break;
            }
        }
    };

    int selectMonetPostion;

    private void setGVMoney() {
        selectmoney = mutual_help_money_all.get(selectMonetPostion).getMoney();
        if (mMoneyAdapter == null) {
            mMoneyAdapter = new BaoZhangMoneyAdapter(context, mutual_help_money_all);
            mMoneyAdapter.setSelectMonetPostion(selectMonetPostion);
            mMoneyAdapter.setSelectDefaultStatus(true);
            detailhelp_gv_money.setAdapter(mMoneyAdapter);
        } else {
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

        detailhelp_tv_wxselecttitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        tv_zfbtitle.setTextColor(context.getResources().getColor(R.color.text_contents_color));
        detailhelp_iv_wxselect.setVisibility(View.GONE);
        detailhelp_relat_wx.setBackgroundResource(R.drawable.corners_bg_black);
        detailhelp_iv_zfbselect.setVisibility(View.GONE);
        detailhelp_relat_zfb.setBackgroundResource(R.drawable.corners_bg_black);


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
        }
    }


}
