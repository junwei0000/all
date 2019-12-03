package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;

import java.lang.reflect.Field;

/**
 * @author : Wells
 */
public class TestActivity extends AppCompatActivity {

    RelativeLayout layoutMain;
    SupplierEditText edtContent;

    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.test);
        initView();
    }


    void initView() {
        layoutMain = (RelativeLayout) findViewById(R.id.layout_main);
        edtContent = (SupplierEditText) findViewById(R.id.edt_content);
        layoutMain.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        edtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) mContext
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(layoutMain.getWindowToken(), 0);
                }
            }
        });
    }

    // 软键盘的显示状态
    private boolean ShowKeyboard;

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            // 应用可以显示的区域。此处包括应用占用的区域，包括标题栏不包括状态栏
            Rect r = new Rect();
            layoutMain.getWindowVisibleDisplayFrame(r);
            // 键盘最小高度
            int minKeyboardHeight = 150;
            // 获取状态栏高度
            int statusBarHeight = getStatusBarHeight(mContext);
            // 屏幕高度,不含虚拟按键的高度
            int screenHeight = layoutMain.getRootView().getHeight();
            // 在不显示软键盘时，height等于状态栏的高度
            int height = screenHeight - (r.bottom - r.top);


            if (ShowKeyboard) {
                // 如果软键盘是弹出的状态，并且height小于等于状态栏高度，
                // 说明这时软键盘已经收起
                if (height - statusBarHeight < minKeyboardHeight) {
                    ShowKeyboard = false;
                    Toast.makeText(mContext, "键盘隐藏了", Toast.LENGTH_SHORT).show();
                }
            } else {
                // 如果软键盘是收起的状态，并且height大于状态栏高度，
                // 说明这时软键盘已经弹出
                if (height - statusBarHeight > minKeyboardHeight) {
                    ShowKeyboard = true;
                    Toast.makeText(mContext, "键盘显示了", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
