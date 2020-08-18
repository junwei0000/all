package com.longcheng.volunteer.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.longcheng.volunteer.R;

/**
 * 作者：MarkMingShuai
 * 时间 2017-9-27 15:24
 * 邮箱：mark_mingshuai@163.com
 * 类的意图：LoadingDialog带动画
 */

public class LoadingDialogAnim extends Dialog {

    /**
     * LoadDialog
     */
    private static LoadingDialogAnim loadDialog;
    /**
     * cancelable, the dialog dimiss or undimiss flag
     */
    private boolean cancelable;
    /**
     * if the dialog don't dimiss, what is the tips.
     */
    private String tipMsg;

    private static ImageView animationIV;
    private static AnimationDrawable animationDrawable;//动画


    public LoadingDialogAnim(Context context) {
        super(context);
        this.getContext().setTheme(android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.dialog_loading);
        animationIV = (ImageView) findViewById(R.id.animationIV);
        setParams();

    }

    /**
     * @param
     * @name 设置ViewParams
     * @auhtor MarkMingShuai
     * @Data 2017-9-19 16:52
     */
    private void setParams() {
        this.setCanceledOnTouchOutside(false);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        // Dialog宽度
        lp.width = (int) (display.getWidth() * 0.7);
        Window window = getWindow();
        window.setAttributes(lp);
        window.getDecorView().getBackground().setAlpha(0);
    }

    /**
     * show the dialog
     *
     * @param context
     */
    public static void show(Context context) {
        show(context, null, true);
    }

    /**
     * show the dialog
     *
     * @param context    Context
     * @param message    String, show the message to user when isCancel is true.
     * @param cancelable boolean, true is can't dimiss，false is can dimiss
     */
    private static void show(Context context, String message, boolean cancelable) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new LoadingDialogAnim(context);
        animationIV.setImageResource(R.drawable.animation_loading);
        animationDrawable = (AnimationDrawable) animationIV.getDrawable();
        animationDrawable.start();
        loadDialog.show();
    }

    /**
     * dismiss the dialog
     */
    public static void dismiss(Context context) {
        try {

            if (animationDrawable != null) {
                animationDrawable.stop();
            }

            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }

            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }

                }

                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadDialog = null;
        }
    }
}
