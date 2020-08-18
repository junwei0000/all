package com.longcheng.lifecareplan.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.umeng.socialize.utils.Log;

import retrofit2.Call;

/**
 * 作者：MarkShuai
 * 时间：2017/11/21 15:11
 * 邮箱：MarkShuai@163.com
 * 意图：吐司工具类
 */
public class ToastUtils {
    private static Context context = ExampleApplication.getInstance().getApplicationContext();
    private static Toast toast;

    public static void showToast(int resID) {
        showToast(context, Toast.LENGTH_SHORT, resID);
    }

    public static void showToast(String text) {
        showToastNew(context, Toast.LENGTH_SHORT, text);
    }


    private static void showToast(Context ctx, int duration, int resID) {
        showToastNew(ctx, duration, ctx.getString(resID));
    }

    private static Handler mHandler = new Handler();
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (toast != null) {
                toast.cancel();
            }
        }
    };

    private static void showToastNew(Context ctx, int duration, String text) {
        if (text == null || text.equals("")) {
            return;
        }
        if (!TextUtils.isEmpty(text) && text.length() >= 20) {
            text = text.substring(0, text.length() / 2) + "\n" + text.substring(text.length() / 2);
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.toast_layout, null);
        TextView str = (TextView) view.findViewById(R.id.mbMessage);
        str.setText(text);
        toast = new Toast(context);
        toast.setView(view);
        toast.show();
        mHandler.postDelayed(runnable, 1500);
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

    /**
     * 在UI线程运行弹出
     */
    public static void showToastOnUiThread(final Activity ctx, final String text) {
        if (ctx != null) {
            ctx.runOnUiThread(() -> showToast(text));
        }
    }
}
