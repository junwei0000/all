package com.bestdo.bestdoStadiums.control.view;

import com.bestdo.bestdoStadiums.utils.ConfigUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;

/**
 * Represents a thumb in the RangeBar slider. This is the handle for the slider
 * that is pressed and slid.
 */
class Thumb {

	// Private Constants ///////////////////////////////////////////////////////

	// The radius (in dp) of the touchable area around the thumb. We are basing
	// this value off of the recommended 48dp Rhythm. See:
	// http://developer.android.com/design/style/metrics-grids.html#48dp-rhythm
	private float MINIMUM_TARGET_RADIUS_DP = 24;

	// Sets the default values for radius, normal, pressed if circle is to be
	// drawn but no value is given.
	private float DEFAULT_THUMB_RADIUS_DP = 14;

	// Corresponds to android.R.color.holo_blue_light.
	private int DEFAULT_THUMB_COLOR_NORMAL = 0xff33b5e5;
	private int DEFAULT_THUMB_COLOR_PRESSED = 0xff33b5e5;

	// Member Variables ////////////////////////////////////////////////////////

	// Radius (in pixels) of the touch area of the thumb.
	private float mTargetRadiusPx;

	// The normal and pressed images to display for the thumbs.
	private Bitmap mImageNormal;
	private Bitmap mImagePressed;

	// Variables to store half the width/height for easier calculation.
	private float mHalfWidthNormal;
	private float mHalfHeightNormal;

	private float mHalfWidthPressed;

	// Indicates whether this thumb is currently pressed and active.
	private boolean mIsPressed = false;

	// The y-position of the thumb in the parent view. This should not change.
	private float mY;

	// The current x-position of the thumb in the parent view.
	private float mX;

	// mPaint to draw the thumbs if attributes are selected
	private Paint mPaintNormal;
	private Paint mPaintPressed;

	// Radius of the new thumb if selected
	private float mThumbRadiusPx;

	// Toggle to select bitmap thumbImage or not
	private boolean mUseBitmap;

	// Colors of the thumbs if they are to be drawn
	private int mThumbColorNormal;
	private int mThumbColorPressed;
	Context context;

	// Constructors ////////////////////////////////////////////////////////////

	Thumb(Context ctx, float y, int thumbColorNormal, int thumbColorPressed, float thumbRadiusDP, int thumbImageNormal,
			int thumbImagePressed) {
		context = ctx;
		Resources res = ctx.getResources();

		mImageNormal = BitmapFactory.decodeResource(res, thumbImageNormal);
		mImagePressed = BitmapFactory.decodeResource(res, thumbImagePressed);

		// If any of the attributes are set, toggle bitmap off
		if (thumbRadiusDP == -1 && thumbColorNormal == -1 && thumbColorPressed == -1) {

			mUseBitmap = true;

		} else {

			mUseBitmap = false;

			// If one of the attributes are set, but the others aren't, set the
			// attributes to default
			if (thumbRadiusDP == -1)
				mThumbRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_THUMB_RADIUS_DP,
						res.getDisplayMetrics());
			else
				mThumbRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, thumbRadiusDP,
						res.getDisplayMetrics());

			if (thumbColorNormal == -1)
				mThumbColorNormal = DEFAULT_THUMB_COLOR_NORMAL;
			else
				mThumbColorNormal = thumbColorNormal;

			if (thumbColorPressed == -1)
				mThumbColorPressed = DEFAULT_THUMB_COLOR_PRESSED;
			else
				mThumbColorPressed = thumbColorPressed;

			// Creates the paint and sets the Paint values
			mPaintNormal = new Paint();
			mPaintNormal.setColor(mThumbColorNormal);
			mPaintNormal.setAntiAlias(true);

			mPaintPressed = new Paint();
			mPaintPressed.setColor(mThumbColorPressed);
			mPaintPressed.setAntiAlias(true);
		}

		mHalfWidthNormal = mImageNormal.getWidth() / 2f;
		mHalfHeightNormal = mImageNormal.getHeight() / 2f;

		mHalfWidthPressed = mImagePressed.getWidth() / 2f;

		// Sets the minimum touchable area, but allows it to expand based on
		// image size
		int targetRadius = (int) Math.max(MINIMUM_TARGET_RADIUS_DP, thumbRadiusDP);

		mTargetRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, targetRadius, res.getDisplayMetrics());

		mX = mHalfWidthNormal;
		mY = y;
	}

	// Package-Private Methods /////////////////////////////////////////////////

	float getHalfWidth() {
		return mHalfWidthNormal;
	}

	float getHalfHeight() {
		return mHalfHeightNormal;
	}

	void setX(float x) {
		mX = x;
	}

	float getX() {
		return mX;
	}

	boolean isPressed() {
		return mIsPressed;
	}

	void press() {
		mIsPressed = true;
	}

	void release() {
		mIsPressed = false;
	}

	/**
	 * Determines if the input coordinate is close enough to this thumb to
	 * consider it a press. 3.修改可触摸滑动区域
	 * 
	 * @param x
	 *            the x-coordinate of the user touch
	 * @param y
	 *            the y-coordinate of the user touch
	 * @return true if the coordinates are within this thumb's target area;
	 *         false otherwise
	 */
	boolean isInTargetZone(float x, float y) {

		if (Math.abs(x - mX) <= mTargetRadiusPx && Math.abs(y - mY) <= mTargetRadiusPx * 4) {
			return true;
		}
		return false;
	}

	/**
	 * Draws this thumb on the provided canvas.
	 * 
	 * @param canvas
	 *            Canvas to draw on; should be the Canvas passed into {#link
	 *            View#onDraw()}
	 */
	void draw(Canvas canvas) {
		int windth = ConfigUtils.getInstance().getPhoneWidHeigth(context).widthPixels;
		float my = mY - 2;
		if (windth > 700 && windth < 1000) {
			my = mY - 4;
		}
		if (windth >= 1000) {
			my = mY - 5;
		}
		// If a bitmap is to be printed. Determined by thumbRadius attribute.
		if (mUseBitmap) {

			Bitmap bitmap = (mIsPressed) ? mImagePressed : mImageNormal;

			if (mIsPressed) {
				float leftPressed = mX - mHalfWidthPressed;
				canvas.drawBitmap(bitmap, leftPressed, my, null);
			} else {
				float leftNormal = mX - mHalfWidthNormal;
				canvas.drawBitmap(bitmap, leftNormal, my, null);
			}

		} else {

			// Otherwise use a circle to display.
			if (mIsPressed)
				canvas.drawCircle(mX, my, mThumbRadiusPx, mPaintPressed);
			else
				canvas.drawCircle(mX, my, mThumbRadiusPx, mPaintNormal);
		}
	}
}
