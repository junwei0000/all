package com.bestdo.bestdoStadiums.control.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;

class Bar {
	private Paint mPaint;
	private float mLeftX;
	private float mRightX;
	private float mY;
	private Paint mLinePaint;
	private Paint mSelectPaint;
	private int leftThumbIndex;
	private int rightThumbIndex;
	private int nNumS;
	private float mTickDistance;
	private float mTickHeight;
	private float mTickStartY;
	private float mTickEndY;
	private float BarWeight;

	Bar(Context ctx, float x, float y, float length, int tickCount, int leftThumbIndex, int rightThumbIndex,
			int selectColor, float tickHeightDP, float BarWeight, int BarColor) {
		this.BarWeight = BarWeight;
		mLeftX = x;
		mRightX = x + length;
		mY = y;
		nNumS = tickCount - 1;
		this.leftThumbIndex = leftThumbIndex;
		this.rightThumbIndex = rightThumbIndex;
		mTickDistance = length / nNumS;
		mTickHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tickHeightDP,
				ctx.getResources().getDisplayMetrics());
		mTickStartY = mY - mTickHeight / 2f;
		mTickEndY = mY + mTickHeight / 2f;

		// Initialize the paint.
		mPaint = new Paint();
		mPaint.setColor(BarColor);
		mPaint.setStrokeWidth(BarWeight);
		mPaint.setAntiAlias(true);
		mLinePaint = new Paint();
		mLinePaint.setColor(BarColor);
		mLinePaint.setStrokeWidth(BarWeight / 2);
		mLinePaint.setAntiAlias(true);
		mSelectPaint = new Paint();
		mSelectPaint.setColor(selectColor);
		mSelectPaint.setStrokeWidth(BarWeight);
		mSelectPaint.setAntiAlias(true);
	}

	/**
	 * Draws the bar on the given Canvas.
	 * 
	 * @param canvas
	 *            Canvas to draw on; should be the Canvas passed into {#link
	 *            View#onDraw()}
	 */
	void draw(Canvas canvas) {

		canvas.drawLine(mLeftX, mY, mRightX, mY, mPaint);

		/*
		 * 1.自定义：画刻度
		 */
		drawTicks(canvas);
	}

	float getLeftX() {
		return mLeftX;
	}

	float getRightX() {
		return mRightX;
	}

	float getNearestTickCoordinate(Thumb thumb) {

		int nearestTickIndex = getNearestTickIndex(thumb);

		float nearestTickCoordinate = mLeftX + (nearestTickIndex * mTickDistance);

		return nearestTickCoordinate;
	}

	int getNearestTickIndex(Thumb thumb) {

		int nearestTickIndex = (int) ((thumb.getX() - mLeftX + mTickDistance / 2f) / mTickDistance);

		return nearestTickIndex;
	}

	void setTickCount(int tickCount) {

		float barLength = mRightX - mLeftX;

		nNumS = tickCount - 1;
		mTickDistance = barLength / nNumS;
	}

	/**
	 * Draws the tick marks on the bar. 1.自定义栏上的刻度线
	 * 
	 * @param canvas
	 *            Canvas to draw on; should be the Canvas passed into {#link
	 *            View#onDraw()}
	 */
	private void drawTicks(Canvas canvas) {
		// for (int i = 0; i < mNumSegments; i++) {
		// float x = i * mTickDistance + mLeftX;
		// canvas.drawLine(x, mTickStartY, x, mTickEndY, mPaint);
		// }
		// canvas.drawLine(mRightX, mTickStartY, mRightX, mTickEndY, mPaint);

		for (int i = 0; i <= nNumS; i++) {
			float x = i * mTickDistance + mLeftX;
			canvas.drawLine(x, mTickStartY, x, mTickEndY / 2, mLinePaint);
			if (i >= leftThumbIndex && i <= rightThumbIndex) {
				canvas.drawCircle(x, mTickStartY + (mTickEndY - mTickStartY) / 2, BarWeight + 2, mSelectPaint);
			} else {
				canvas.drawCircle(x, mTickStartY + (mTickEndY - mTickStartY) / 2, BarWeight + 2, mPaint);
			}
		}
	}
}
