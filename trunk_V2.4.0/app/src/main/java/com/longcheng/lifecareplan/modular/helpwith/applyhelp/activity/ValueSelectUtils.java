package com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.myview.AddressPickerView;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.YwpAddressBean;
import com.longcheng.lifecareplan.utils.teetime.ArrayWheelAdapter;
import com.longcheng.lifecareplan.utils.teetime.WheelView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/22 17:55
 * 意图：
 */

public class ValueSelectUtils {
    MyDialog selectShengxDialog;
    Activity mContext;
    Handler mHandler;
    int mHandlerID;

    int selectindex;

    public ValueSelectUtils(Activity mContext, Handler mHandler, int mHandlerID) {
        this.mContext = mContext;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
    }


    public void showDialog(String[] hours_, String title) {
        if (selectShengxDialog == null) {
            selectShengxDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_action_shengxiao);// 创建Dialog并设置样式主题
            selectShengxDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectShengxDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectShengxDialog.show();
            WindowManager m = mContext.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectShengxDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectShengxDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_title = (TextView) selectShengxDialog.findViewById(R.id.tv_title);
            TextView tv_cancel = (TextView) selectShengxDialog.findViewById(R.id.tv_cancel);
            TextView tv_sure = (TextView) selectShengxDialog.findViewById(R.id.tv_sure);
            WheelView mWheelView = (WheelView) selectShengxDialog.findViewById(R.id.mWheelView);
            tv_title.setText(title);
            final String[] hours = hours_;
            mWheelView.setAdapter(new ArrayWheelAdapter<String>(hours, hours.length));
            mWheelView.setCurrentItem(selectindex);
            mWheelView.setVisibleItems(5);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectShengxDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectShengxDialog.dismiss();
                    int po = mWheelView.getCurrentItem();
                    selectindex = po;
                    Message message = new Message();
                    message.what = mHandlerID;
                    Bundle bundle = new Bundle();
                    bundle.putInt("selectindex", selectindex);
                    bundle.putString("cont", hours[po]);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                    message = null;
                }
            });
        } else {
            selectShengxDialog.show();
        }
    }
}
