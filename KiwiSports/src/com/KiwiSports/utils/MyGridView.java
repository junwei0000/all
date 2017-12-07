package com.KiwiSports.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义GridView防止跟ScrollView滚动冲突
 * 
 * @author jun
 * 
 */
public class MyGridView extends GridView {

	/*
	 * 
	 * 在开发中用到了需要ScrollView嵌套GridView的情况。
	 * 由于这两款控件都自带滚动条，当他们碰到一起的时候便会出问题，即GridView会显示不全。
	 * 
	 * 解决办法，自定义一个GridView控件
	 */
	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
