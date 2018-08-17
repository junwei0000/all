package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-15 下午7:18:58
 * @Description 类描述：引导页适配
 */
public class WelcomeGuideAdapter extends PagerAdapter {
	private ArrayList<View> viewList;

	public WelcomeGuideAdapter(ArrayList<View> viewList) {
		this.viewList = viewList;
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(viewList.get(position));
	};

	public int getItemPosition(Object object) {

		return super.getItemPosition(object);
	};

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(viewList.get(position));
		return viewList.get(position);
	}
}
