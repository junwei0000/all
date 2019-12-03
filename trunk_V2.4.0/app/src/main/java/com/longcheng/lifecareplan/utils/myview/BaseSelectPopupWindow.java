package com.longcheng.lifecareplan.utils.myview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：jun on
 * 时间：2018/8/29 17:24
 * 意图：
 */

public class BaseSelectPopupWindow extends PopupWindow {

    private View popView;

    private View view;


    private OnHeadClickListener onHeadClickListener;


    private TextView tv_head;


    protected Context context;


    private boolean isOpenKeyboard = false;
    ;


    private boolean isShowTitle = true;

    private boolean isOkClose = true;


    protected int maxTextSize = 24;
    protected int minTextSize = 14;

    public BaseSelectPopupWindow(Context context, int layoutId) {

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.pop_view, null);
        tv_head = (TextView) popView.findViewById(R.id.tv_head);

        LinearLayout contentView = (LinearLayout) popView
                .findViewById(R.id.content);
        view = inflater.inflate(layoutId, null);
        contentView.addView(view, contentView.getLayoutParams());
        // btn_take_photo.setOnClickListener(itemsOnClick);
        // 设置SelectPicPopupWindow的View
        this.setContentView(popView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
//  this.setHeight(wm.getDefaultDisplay().getHeight() / 2);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框

  /*
   * popView.setOnTouchListener(new OnTouchListener() {
   *
   * public boolean onTouch(View v, MotionEvent event) {
   *
   * int height = popView.findViewById(R.id.pop_layout).getTop(); int
   * y=(int) event.getY(); if(event.getAction()==MotionEvent.ACTION_UP){
   * if(y<height){ dismiss(); } } return true; } });
   */

        (popView.findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        (popView.findViewById(R.id.btn_right)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onHeadClickListener != null) {
                    onHeadClickListener.okListener();
                }
                if (isOkClose) {
                    dismiss();
                }

            }
        });

        if (isOpenKeyboard) {
            openKeyboard();
        }


    }

    public boolean isShowTitle() {
        return isShowTitle;
    }

    public void setShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
        if (!isShowTitle) {
            ((RelativeLayout) tv_head.getParent()).setVisibility(View.GONE);
        }
    }

    /**
     * 打开软键盘
     */
    private void openKeyboard() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        }, 1000);
    }

    public boolean isOpenKeyboard() {
        return isOpenKeyboard;
    }

    public void setOpenKeyboard(boolean isOpenKeyboard) {
        this.isOpenKeyboard = isOpenKeyboard;
    }

    public OnHeadClickListener getOnHeadClickListener() {
        return onHeadClickListener;
    }

    public void setOnHeadClickListener(OnHeadClickListener onHeadClickListener) {
        this.onHeadClickListener = onHeadClickListener;
    }

    public interface OnHeadClickListener {
        public void okListener();
    }


    public void setTitle(String value) {
        if (tv_head != null) {
            tv_head.setText(value);
        }
    }

    public boolean isOkClose() {
        return isOkClose;
    }

    public void setOkClose(boolean isOkClose) {
        this.isOkClose = isOkClose;
    }

    public Context getContext() {
        return context;
    }
}