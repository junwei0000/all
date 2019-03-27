package com.longcheng.volunteer.utils.myview;

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

    protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        if (this.scrollViewListener != null)
            this.scrollViewListener.onScrollChanged(this, paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public void setScrollViewListener(ScrollViewListener paramScrollViewListener) {
        this.scrollViewListener = paramScrollViewListener;
    }

    public static abstract interface ScrollViewListener {
        public abstract void onScrollChanged(MyScrollView paramMyScrollView, int paramInt1, int paramInt2,
                                             int paramInt3, int paramInt4);
    }
}