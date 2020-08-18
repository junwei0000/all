package com.longcheng.lifecareplan.utils.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 自定义纵向滚动条
 *
 * @author jun
 */
public class MyScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;

    public MyScrollView(Context paramContext) {
        super(paramContext);
    }

    public MyScrollView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public MyScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    protected void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
        if (this.scrollViewListener != null)
            this.scrollViewListener.onScrollChanged(this, scrollX, scrollY, oldScrollX, oldScrollY);
    }

    public void setScrollViewListener(ScrollViewListener paramScrollViewListener) {
        this.scrollViewListener = paramScrollViewListener;
    }

    public static abstract interface ScrollViewListener {
        public abstract void onScrollChanged(MyScrollView paramMyScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}