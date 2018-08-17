package com.bestdo.bestdoStadiums.control.view;

import com.bestdo.bestdoStadiums.R;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by regan.chon on 2017/5/18.
 */

public class HuitanCustomView extends View {

	private Bitmap bitmap;
	private Paint mPaint;
	private ValueAnimator valueAnimator;

	public HuitanCustomView(Context context) {
		super(context);
		init();
	}

	public HuitanCustomView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setLayerType(LAYER_TYPE_SOFTWARE, null);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.about_icon);
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(5);
		final int mheight = bitmap.getHeight();
		valueAnimator = ValueAnimator.ofInt(0, mheight);
		valueAnimator.setDuration(1000);
		valueAnimator.setInterpolator(new BounceInterpolator());
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Integer value = (Integer) animation.getAnimatedValue();
				HuitanCustomView.this.height = mheight - value;
				invalidate();
			}
		});
	}

	private void bounce() {

	}

	int height;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(bitmap, 0, -height, mPaint);
	}

	public void show() {
		valueAnimator.start();
	}
}
