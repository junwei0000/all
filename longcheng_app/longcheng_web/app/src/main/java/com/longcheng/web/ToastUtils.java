package com.longcheng.web;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 作者：MarkShuai
 * 时间：2017/11/21 15:11
 * 邮箱：MarkShuai@163.com
 * 意图：吐司工具类
 */
public class ToastUtils {
    private static Context context = ExampleApplication.getInstance();
    private static Toast toast;

    public static void showToast(int resID) {
        showToast(context, Toast.LENGTH_SHORT, resID);
    }

    public static void showToast(String text) {
        showToast(context, Toast.LENGTH_SHORT, text);
    }


    private static void showToast(Context ctx, int duration, int resID) {
        showToast(ctx, duration, ctx.getString(resID));
    }


    private static void showToast(Context ctx, int duration, String text) {
        if (text == null || text.equals("")) {
            return;
        }
        if (!TextUtils.isEmpty(text) && text.length() >= 20) {
            text = text.substring(0, text.length() / 2) + "\n" + text.substring(text.length() / 2);
        }
        if (toast == null) {
            toast = Toast.makeText(ctx, text, duration);
            //设置字体大小和距离底部的高度
            toast.setGravity(Gravity.BOTTOM, 0, DensityUtil.dip2px(ctx, 66));
            LinearLayout linearLayout = (LinearLayout) toast.getView();
            TextView messageTextView = (TextView) linearLayout.getChildAt(0);
            messageTextView.setTextSize(DensityUtil.dip2sp(ctx, 14));
            messageTextView.setGravity(Gravity.CENTER);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

}
