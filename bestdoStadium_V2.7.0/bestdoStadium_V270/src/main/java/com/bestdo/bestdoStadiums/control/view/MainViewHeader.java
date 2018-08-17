package com.bestdo.bestdoStadiums.control.view;

import com.andview.refreshview.XRefreshViewState;
import com.andview.refreshview.base.XRefreshHeaderViewBase;
import com.bestdo.bestdoStadiums.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainViewHeader extends LinearLayout implements XRefreshHeaderViewBase {
	private LinearLayout refreshView;
	private ImageView mArrowImageView;
	private XRefreshViewState mState = XRefreshViewState.STATE_NORMAL;

	private RelativeLayout mHeaderViewContent;
	private long lastRefreshTime;
	private AnimationDrawable frameAnimation;

	public MainViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MainViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		refreshView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.refresh_head, null);
		addView(refreshView);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView) refreshView.findViewById(R.id.refresh_main_top_img);
		frameAnimation = (AnimationDrawable) context.getResources().getDrawable(R.anim.xiala_anim);
		mArrowImageView.setBackgroundDrawable(frameAnimation);
		frameAnimation.start();
	}

	public int getVisiableHeight() {
		return refreshView.getHeight();
	}

	@Override
	public int getHeaderContentHeight() {
		return mHeaderViewContent.getMeasuredHeight();
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		setVisibility(View.GONE);
	}

	public void show() {
		setVisibility(View.VISIBLE);
	}

	@Override
	public void setState(XRefreshViewState state) {
		if (state == mState)
			return;
		// setRefreshTime(lastRefreshTime);
		if (state == XRefreshViewState.STATE_REFRESHING) {
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.GONE);
		} else {
			mArrowImageView.setVisibility(View.VISIBLE);
		}

		switch (state) {
		case STATE_NORMAL:// 下拉刷新
			if (mState == XRefreshViewState.STATE_READY) {
				// mArrowImageView.startAnimation(mRotateDownAnim);
				frameAnimation.start();
			}
			if (mState == XRefreshViewState.STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			break;
		case STATE_READY:
			if (mState != XRefreshViewState.STATE_READY) {// 松开刷新数据
				// mArrowImageView.clearAnimation();
				// mArrowImageView.startAnimation(mRotateUpAnim);
			}
			break;
		case STATE_REFRESHING:// 正在加载
			break;
		default:
		}

		mState = state;
	}

	@Override
	public void setRefreshTime(long lastRefreshTime) {

	}
}
