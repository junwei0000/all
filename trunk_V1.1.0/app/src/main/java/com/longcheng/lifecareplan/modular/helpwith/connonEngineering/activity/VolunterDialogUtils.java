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
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/30 16:05
 * 意图：
 * **************************志愿者互祝弹层***********************************
 */

public class VolunterDialogUtils {
    MyDialog selectDialog;

    RelativeLayout detailhelp_relat_wx, detailhelp_relat_zfb;
    ImageView detailhelp_iv_wxselect, detailhelp_iv_zfbselect;
    private TextView btn_helpsure;
    private TextView detailhelp_tv_wxselecttitle;
    private TextView tv_zfbtitle;
    Handler mHandler;
    int mHandlerID;
    /**
     * 支付方式互祝类型   1(微信支付) 2 支付宝
     */
    String payType = "1";
    Activity context;


    public VolunterDialogUtils(Activity context, Handler mHandler, int mHandlerID) {
        this.mHandlerID = mHandlerID;
        this.mHandler = mHandler;
        this.context = context;
    }

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(context, R.style.dialog, R.layout.dialog_doctor_pay);// 创建Dialog并设置样式主题
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
        } else {
            selectDialog.show();
        }
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
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                default:

                    break;
            }
        }
    };

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
