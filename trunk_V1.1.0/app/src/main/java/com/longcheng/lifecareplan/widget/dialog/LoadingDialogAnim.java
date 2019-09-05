package com.longcheng.lifecareplan.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.ToastUtils;

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

    Context context;

    public LoadingDialogAnim(Context context) {
        super(context);
        this.context = context;
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
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        // Dialog宽度
//        lp.width = (int) (display.getWidth() * 0.7);
        Window window = getWindow();
//        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER);
        if (window.getDecorView() != null && window.getDecorView().getBackground() != null)
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
     * 设置加载中弹层显示时不让点击物理返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //----------------修改.1--- 防止无法返回，设置连续点两下就关闭页面-------------
        if ((System.currentTimeMillis() - clickBackTime) > 400) {
            clickBackTime = System.currentTimeMillis();
        } else {
            if (context != null)
                ((Activity) context).finish();
        }
        return true;
    }

    private long clickBackTime = 0;

    /**
     * show the dialog
     *
     * @param context    Context
     * @param message    String, show the message to user when isCancel is true.
     * @param cancelable boolean, true is can't dimiss，false is can dimiss
     */
    private static void show(Context context, String message, boolean cancelable) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (context instanceof Activity) {
                    if (((Activity) context).isFinishing()) {
                        return;
                    }
                }
                if (loadDialog != null && loadDialog.isShowing()) {
                    return;
                }
                loadDialog = new LoadingDialogAnim(context);
//                animationIV.setBackgroundResource(R.drawable.animation_loading);
//                animationDrawable = (AnimationDrawable) animationIV.getBackground();
//                animationIV.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        animationDrawable.start();
//                    }
//                });
                /**
                 * 防止OOM异常崩溃的问题
                 */
                SceneAnimation anim = new SceneAnimation(animationIV, meetPics, 20);
                loadDialog.show();
            }
        }, 0);

    }

    private static int[] meetPics = new int[]{
            R.mipmap.loading_1, R.mipmap.loading_2, R.mipmap.loading_3,
            R.mipmap.loading_4, R.mipmap.loading_5, R.mipmap.loading_6,
            R.mipmap.loading_7, R.mipmap.loading_8, R.mipmap.loading_9, R.mipmap.loading_10,
            R.mipmap.loading_11, R.mipmap.loading_12, R.mipmap.loading_13,
            R.mipmap.loading_14, R.mipmap.loading_15, R.mipmap.loading_16,
            R.mipmap.loading_17, R.mipmap.loading_18, R.mipmap.loading_19, R.mipmap.loading_20,
            R.mipmap.loading_21, R.mipmap.loading_22, R.mipmap.loading_23,
            R.mipmap.loading_24, R.mipmap.loading_25, R.mipmap.loading_26,
            R.mipmap.loading_27, R.mipmap.loading_28, R.mipmap.loading_29, R.mipmap.loading_30,
            R.mipmap.loading_31, R.mipmap.loading_32, R.mipmap.loading_33,
            R.mipmap.loading_34, R.mipmap.loading_35, R.mipmap.loading_36,
            R.mipmap.loading_37, R.mipmap.loading_38, R.mipmap.loading_39, R.mipmap.loading_40,
            R.mipmap.loading_41, R.mipmap.loading_42, R.mipmap.loading_43,
            R.mipmap.loading_44, R.mipmap.loading_45, R.mipmap.loading_46,
            R.mipmap.loading_47, R.mipmap.loading_48, R.mipmap.loading_49, R.mipmap.loading_50,
            R.mipmap.loading_51, R.mipmap.loading_52, R.mipmap.loading_53,
            R.mipmap.loading_54, R.mipmap.loading_55, R.mipmap.loading_56,
            R.mipmap.loading_57, R.mipmap.loading_58, R.mipmap.loading_59, R.mipmap.loading_60,
            R.mipmap.loading_61, R.mipmap.loading_62, R.mipmap.loading_63,
            R.mipmap.loading_64, R.mipmap.loading_65, R.mipmap.loading_66,
            R.mipmap.loading_67, R.mipmap.loading_68, R.mipmap.loading_69, R.mipmap.loading_70,
            R.mipmap.loading_71,
    };

    public static boolean showStatus() {
        if (loadDialog != null && loadDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    static Handler handler = new Handler();

    /**
     * dismiss the dialog
     */
    public static void dismiss(Context context) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (animationDrawable != null) {
                        animationDrawable.stop();
                        if (loadDialog != null) {
                            loadDialog.dismiss();
                            loadDialog = null;
                        }
                    }

                    if (context instanceof Activity) {
                        if (((Activity) context).isFinishing()) {
                            if (loadDialog != null) {
                                loadDialog.dismiss();
                                loadDialog = null;
                            }
                            return;
                        }
                    }

                    if (loadDialog != null && loadDialog.isShowing()) {
                        Context loadContext = loadDialog.getContext();
                        if (loadContext != null && loadContext instanceof Activity) {
                            if (((Activity) loadContext).isFinishing()) {
                                loadDialog.dismiss();
                                loadDialog = null;
                                return;
                            }

                        }
                        loadDialog.dismiss();
                        loadDialog = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (loadDialog != null) {
                        loadDialog.dismiss();
                        loadDialog = null;
                    }
                }
            }
        }, 200);//秒后执行Runnable中的run方法

    }
}
