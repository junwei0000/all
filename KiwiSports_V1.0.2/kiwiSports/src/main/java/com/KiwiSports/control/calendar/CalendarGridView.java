package com.KiwiSports.control.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.KiwiSports.utils.MyGridView;

/**
 * @author daij
 * @version 1.0 自定义日历GridView
 */
public class CalendarGridView extends MyGridView implements
		AdapterView.OnItemLongClickListener {

	private Context mContext;

	public CalendarGridView(Context context) {
		super(context);
		this.mContext = context;
		initGridView();
	}

	public CalendarGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initGridView();
	}

	public CalendarGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initGridView();
	}

	/**
	 * 初始化GirdView
	 *            <参数类型> <参数说明>
	 * @return <返回值类型>
	 * @throws <异常>
	 */
	public void initGridView() {

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		return false;
	}

}