package com.longcheng.volunteer.modular.helpwith.myfamily.activity;

import android.app.Activity;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.utils.myview.MyDialog;

/**
 * 作者：jun on
 * 时间：2018/9/20 14:23
 * 意图：完善信息弹出
 */

public class PerfectInfoDialog {

    Activity mContext;
    Handler mHandler;
    int mHandlerID;
    private TextView tv_title;
    private TextView btn_ok;

    public PerfectInfoDialog(Activity mContext, Handler mHandler, int mHandlerID) {
        this.mContext = mContext;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
    }

    MyDialog selectDialog;

    public void showEditDialog(String title, String btncont) {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_family_edit);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = mContext.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_cancel = (ImageView) selectDialog.findViewById(R.id.iv_cancel);
            tv_title = (TextView) selectDialog.findViewById(R.id.tv_title);
            btn_ok = (TextView) selectDialog.findViewById(R.id.btn_ok);
            tv_title.setText(title);
            btn_ok.setText(btncont);
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    mHandler.sendEmptyMessage(mHandlerID);
                }
            });
        } else {
            tv_title.setText(title);
            btn_ok.setText(btncont);
            selectDialog.show();
        }
    }
}
