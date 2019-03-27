package com.longcheng.volunteer.utils.datepick;

import android.view.View;

public class DialogBean {

    private CharSequence title;

    private CharSequence lbtText;

    private CharSequence rbtText;

    private View contentView;

    private OnDialogClickListener lbtnListener;

    private OnDialogClickListener rbtnListener;

    private int style;// 风格

    public static final int STYLE_SIMPLE = 0;// 没有标题

    public static final int STYLE_TITLE = 1;// 有标题

    public interface OnDialogClickListener {
        boolean onClick(View v);
    }

    public DialogBean(CharSequence title, View contentView, OnDialogClickListener lbtnListener,
                      OnDialogClickListener rbtnListener, int style) {
        super();
        this.title = title;
        this.contentView = contentView;
        this.lbtnListener = lbtnListener;
        this.rbtnListener = rbtnListener;
        this.style = style;
    }

    public DialogBean(CharSequence title, CharSequence lbtText, CharSequence rbtText, View contentView,
                      OnDialogClickListener lbtnListener, OnDialogClickListener rbtnListener, int style) {
        super();
        this.title = title;
        this.lbtText = lbtText;
        this.rbtText = rbtText;
        this.contentView = contentView;
        this.lbtnListener = lbtnListener;
        this.rbtnListener = rbtnListener;
        this.style = style;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public CharSequence getLbtText() {
        return lbtText;
    }

    public void setLbtText(CharSequence lbtText) {
        this.lbtText = lbtText;
    }

    public CharSequence getRbtText() {
        return rbtText;
    }

    public void setRbtText(CharSequence rbtText) {
        this.rbtText = rbtText;
    }

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public OnDialogClickListener getLbtnListener() {
        return lbtnListener;
    }

    public void setLbtnListener(OnDialogClickListener lbtnListener) {
        this.lbtnListener = lbtnListener;
    }

    public OnDialogClickListener getRbtnListener() {
        return rbtnListener;
    }

    public void setRbtnListener(OnDialogClickListener rbtnListener) {
        this.rbtnListener = rbtnListener;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

}
