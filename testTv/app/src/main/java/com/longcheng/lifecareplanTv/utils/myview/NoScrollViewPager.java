package com.longcheng.lifecareplanTv.utils.myview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：jun on
 * 时间：2019/4/10 15:17
 * 意图：禁止ViewPager左右滑动
 * 向前翻页可以在按钮事件里调用
 * viewPager.arrowScroll(View.FOCUS_LEFT);
 * 向后翻页可以在按钮事件里调用
 * viewPager.arrowScroll(View.FOCUS_RIGHT);
 */

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }
}
