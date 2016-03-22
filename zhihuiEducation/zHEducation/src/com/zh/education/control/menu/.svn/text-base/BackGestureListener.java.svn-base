package com.zh.education.control.menu;

import com.zh.education.control.activity.FragmentBaseActivity;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

/**
 * 
 * @author 作者：qyy
 * @date 创建时间：2016-1-5 下午5:19:39
 * @Description 类描述：返回手势监听接口
 */
public class BackGestureListener implements OnGestureListener {
	FragmentBaseActivity activity;

	public BackGestureListener(FragmentBaseActivity activity) {
		this.activity = activity;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if ((e2.getX() - e1.getX()) > 100
				&& Math.abs(e1.getY() - e2.getY()) < 60) {
			activity.onBackPressed();
			return true;
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

}
