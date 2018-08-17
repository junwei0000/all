package com.bestdo.bestdoStadiums.control.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomViewPager extends ViewPager {
	CustomViewPager mCustomPager;
	private boolean forSuper;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void forSuper(boolean forSuper) {
		this.forSuper = forSuper;
	}

	public void setViewPager(CustomViewPager customPager) {
		mCustomPager = customPager;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (!forSuper) {
			mCustomPager.forSuper(true);
			mCustomPager.onTouchEvent(arg0);
			mCustomPager.forSuper(false);
		}
		return super.onTouchEvent(arg0);
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		if (!forSuper) {
			mCustomPager.forSuper(true);
			mCustomPager.setCurrentItem(item, smoothScroll);
			mCustomPager.forSuper(false);
		}
		super.setCurrentItem(item, smoothScroll);
	}

	@Override
	public void setCurrentItem(int item) {
		if (!forSuper) {
			mCustomPager.forSuper(true);
			mCustomPager.setCurrentItem(item);
			mCustomPager.forSuper(false);
		}
		super.setCurrentItem(item);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (!forSuper) {

			mCustomPager.forSuper(true);
			mCustomPager.onInterceptTouchEvent(arg0);
			mCustomPager.forSuper(false);
		}
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int height = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height)
				height = h;
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

}
