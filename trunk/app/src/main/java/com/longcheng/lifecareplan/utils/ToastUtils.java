package com.longcheng.lifecareplan.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.longcheng.lifecareplan.base.ExampleApplication;

import retrofit2.Call;

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

    public static void showLongToast(int resID) {
        showToast(context, Toast.LENGTH_LONG, resID);
    }


    public static void showLongToast(String text) {
        showToast(context, Toast.LENGTH_LONG, text);
    }

    private static void showToast(Context ctx, String text) {
        showToast(ctx, Toast.LENGTH_LONG, text);
    }

    private static void showToast(Context ctx, int duration, int resID) {
        showToast(ctx, duration, ctx.getString(resID));
    }


    private static void showToast(final Context ctx, int duration, final String text) {

        if (text == null || text.equals("")) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(ctx, text, duration);
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
            ctx.runOnUiThread(() -> showToast(ctx, text));
        }
    }
}
