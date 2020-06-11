package com.longcheng.lifecareplan.modular.helpwith.nfcaction.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 作者：jun on
 * 时间：2020/5/18 11:09
 * 意图：
 */
public class fullScreenVideoView extends VideoView {
    public fullScreenVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public fullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public fullScreenVideoView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//这里重写onMeasure的方法
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(0, widthMeasureSpec);//得到默认的大小（0，宽度测量规范）
        int height = getDefaultSize(0, heightMeasureSpec);//得到默认的大小（0，高度度测量规范）
        width = (int) (height / 1.7778);
        setMeasuredDimension(width, height); //设置测量尺寸,将高和宽放进去
    }
}
