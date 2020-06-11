package com.longcheng.lifecareplan.utils.myview;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * 自定义弹框
 *
 * @author jun
 */
public class MyDialog extends AlertDialog {

    private int layout;

    protected MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int theme, int layout) {
        super(context, theme);
        this.layout = layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        setContentView(layout);
    }

}
