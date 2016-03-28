package com.zh.education.control.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-5 下午5:27:25
 * @Description 类描述：自定义更多栏目GridView
 */
public class ChannelItemOtherGridView extends GridView {

	public ChannelItemOtherGridView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
