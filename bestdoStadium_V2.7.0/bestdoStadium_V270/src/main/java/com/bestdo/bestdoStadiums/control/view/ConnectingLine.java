package com.bestdo.bestdoStadiums.control.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;

/**
 * Class representing the blue connecting line between the two thumbs.
 */
class ConnectingLine {

	// Member Variables ////////////////////////////////////////////////////////

	private Paint mPaint;

	private float mConnectingLineWeight;
	private float mY;

	// Constructor /////////////////////////////////////////////////////////////

	ConnectingLine(Context ctx, float y, float connectingLineWeight, int connectingLineColor) {

		Resources res = ctx.getResources();

		mConnectingLineWeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, connectingLineWeight,
				res.getDisplayMetrics());

		// Initialize the paint, set values
		mPaint = new Paint();
		mPaint.setColor(connectingLineColor);
		mPaint.setStrokeWidth(mConnectingLineWeight);
		mPaint.setAntiAlias(true);

		mY = y;
	}

	// Package-Private Methods /////////////////////////////////////////////////

	/**
	 * Draw the connecting line between the two thumbs.
	 * 
	 * @param canvas
	 *            the Canvas to draw to
	 * @param leftThumb
	 *            the left thumb
	 * @param rightThumb
	 *            the right thumb
	 */
	void draw(Canvas canvas, Thumb leftThumb, Thumb rightThumb) {
		canvas.drawLine(leftThumb.getX(), mY, rightThumb.getX(), mY, mPaint);
	}
}
