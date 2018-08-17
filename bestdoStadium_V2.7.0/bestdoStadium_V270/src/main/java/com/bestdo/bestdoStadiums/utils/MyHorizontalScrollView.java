package com.bestdo.bestdoStadiums.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 自定义横向滚动条
 * 
 * @author jun
 * 
 */
public class MyHorizontalScrollView extends HorizontalScrollView {
	private ScrollViewListener scrollViewListener = null;

	public MyHorizontalScrollView(Context paramContext) {
		super(paramContext);
	}

	public MyHorizontalScrollView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MyHorizontalScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		if (this.scrollViewListener != null)
			this.scrollViewListener.onScrollChanged(this, paramInt1, paramInt2, paramInt3, paramInt4);
	}

	public void setScrollViewListener(ScrollViewListener paramScrollViewListener) {
		this.scrollViewListener = paramScrollViewListener;
	}

	public static abstract interface ScrollViewListener {
		public abstract void onScrollChanged(MyHorizontalScrollView paramMyHorizontalScrollView, int paramInt1,
				int paramInt2, int paramInt3, int paramInt4);
	}
}
